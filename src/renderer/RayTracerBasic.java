package renderer;

import java.util.List;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import static geometries.Intersectable.GeoPoint;

/**
 * The class extends {@link RayTracerBase}
 * Find the intersections of ray with the scene objects and returns the color of
 * the intersections.
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class RayTracerBasic extends RayTracerBase {

    // #region fields

    /**
     * constant attenuation value, initialized for max transparency of shading-rays
     * calculation
     * (1, means no attenuation)
     */
    private static final Double3 INITIAL_K = new Double3(1.0);

    /**
     * constant value of max recursive level of transparency calculation
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * stop condition of the recursive transparency calculation,
     * when the attenuation coefficient is very small.
     * (the max recursive level of reflection).
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    // #endregion

    /**
     * RayTracerBase constructor, using {@link Scene} parameter
     * 
     * @param the scene that contain the traced ray
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * The function finds {@link GeoPoint} points intersections of the {@link Ray}
     * with the scene objects and returns the color of the closest intersection
     * 
     * @param ray traced ray
     * @return the color of the intersection point
     */
    @Override
    Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * The function calculates the color of {@link GeoPoint} point on a geometry
     * as it sees from the camera position, with consideration of the ambient-light
     * and the transparency of the object.
     * The final color is combination of the material color and the attributes of
     * the object,
     * the ambient-light color, the distance from the camera and sometimes other
     * object in the scene.
     * (Using {@link MAX_CALC_COLOR_LEVEL} and {@link INITIAL_K}).
     * 
     * @param geoPoint a point on a geometry with its geometry
     * @param ray      the ray that constructed from the camera and intersected the
     *                 geometry
     * @return the color of the point
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * The function calculates the color of {@link GeoPoint} point on a geometry
     * as it sees from the camera position (helping method for
     * {@link #calcColor(GeoPoint, Ray)}).
     * 
     * @param intersection, a {@link GeoPoint} point on a geometry
     * @param ray           the ray that constructed from the camera and intersected
     *                      the geometry
     * @param level         of recursion for the transparency and global effects
     *                      calculation
     * @param k,            initial attenuation coefficient value (between 0-1)
     * @return the color of the point on the geometry
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Helping method for color calculation of a {@link GeoPoint} point on a
     * geometry
     * as it sees from the camera point of view.
     * The function is modeling transparent objects (with various
     * opacity levels) and reflecting surfaces such as mirrors.
     * 
     * @param gp,   the observed point on the geometry
     * @param ray   from the camera that intersect the geometry
     * @param level of recursion for the global effects calculation
     * @param k,    initial attenuation coefficient value (between 0-1)
     * @return the color of the point with consideration of global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(ray, n, gp.point), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(ray, n, gp.point), level, k, material.kT));
    }

    /**
     * Helping method for color calculation of a {@link GeoPoint} point on a
     * geometry
     * as it sees from the camera point of view.
     * Calculating the global effects:
     * (Helping method for {@link #calcGlobalEffects(GeoPoint, Ray, int, double)})
     * 
     * @param ray   from the camera that intersect the geometry
     * @param level of recursion
     * @param kx    the material reflection\refraction coefficient value (between
     *              0-1)
     * @param kkx,  product of the global and local attenuation coefficient
     * @return the color of the point with consideration of global effect
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        var kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx));
    }

    /**
     * Helping method for color calculation of a {@link GeoPoint} point on a
     * geometry
     * as it sees from the camera point of view.
     * Calculating the local effects: the diffusion and the specular factors.
     * 
     * @param gp, the observed {@link GeoPoint} point on the geometry
     * @param ray from the camera that intersect the geometry
     * @param k,  the attenuation coefficient value
     * @return the color of the point with consideration of local effects
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = gp.geometry.getMaterial();
        Double3 kD = material.kD;
        Double3 kS = material.kS;
        double nShininess = material.nShininess;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (ktr.product(k).higherThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(kD, nl)), iL.scale(calcSpecular(kS,
                            nShininess, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * The function calculates the diffuse component of the color
     * 
     * @param kD is the diffuse component of the material
     * @param nl the dot product of the normal and the light vector
     * @return the diffuse component of the color
     */
    private Double3 calcDiffusive(Double3 kD, double nl) {
        return kD.scale(Math.abs(nl));
    }

    /**
     * this function calculates the specular component of the color
     * 
     * @param kS         is the specular component of the material
     * @param nShininess the normal of the geometry
     * @param n          the normal of the geometry
     * @param l          the light vector
     * @param nl         the dot product of the normal and the light vector
     * @param v          the view vector
     * @return the specular component of the color
     * 
     */
    private Double3 calcSpecular(Double3 kS, double nShininess, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl)).normalize(); // opposite to reflection
        double factor = -v.dotProduct(r); // -v*reflection
        return Util.alignZero(factor) <= 0 ? Double3.ZERO : kS.scale(Math.pow(factor, nShininess));
    }

    /**
     * The function checks transparency shading between a {@link GeoPoint} point and
     * the light source.
     * For each intersection which is closer to the
     * point than the light source multiply ktr by {@link Material#kT} of its
     * geometry.
     * The returned value is the transparency value between 1 (no Shaded at all) and
     * 0 (full shaded).
     * 
     * @param gp a {@link GeoPoint} point on a geometry
     * @param ls is the checked light source
     * @param l  the light {@link Vector} direction
     * @param n  the normal of the geometry
     * @return the transparency value of the point
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        double lightDistance = ls.getDistance(geoPoint.point);
        var intersections = scene.geometries.findGeoIntersections(new Ray(geoPoint.point, lightDirection, n),
                lightDistance);
        if (intersections == null)
            return Double3.ONE;

        var ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            if (Util.alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }
        return ktr;
    }

    /**
     * The function calculates the ray reflection of these rays will move the
     * starting point of the ray on the regular geometry line towards the new ray.
     * 
     * @param ray   the ray to be reflected from light source
     * @param n     the normal of the geometry
     * @param point the intersected point on the geometry
     */
    private Ray constructReflectedRay(Ray ray, Vector n, Point point) {
        Vector v = ray.getDir();
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n))).normalize();
        return new Ray(point, r, n);
    }

    /**
     * The function calculates the ray transparency of these rays will move the
     * starting point of the ray on the regular geometry line towards the new ray.
     * 
     * @param ray   the ray to be transparency from light source
     * @param n     the normal of the geometry
     * @param point the intersected point on the geometry
     */
    private Ray constructRefractedRay(Ray ray, Vector n, Point point) {
        return new Ray(point, ray.getDir(), n);
    }

    /**
     * this function find the closest intersection point of the ray with the
     * geometry
     * 
     * @param ray the traced ray
     * @return the closest intersection point between the ray and the geometry
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, Double.POSITIVE_INFINITY);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }

}
