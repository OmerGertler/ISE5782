package geometries;

import java.util.List;
import static primitives.Util.*;

import primitives.*;

/**
 * plane in euclidean space, based on point in the space and normal vector
 * {@link primitives.Point#Point(double, double, double)}
 * {@link primitives.Vector#Vector(double, double, double)}
 */
public class Plane extends Geometry {

    private final Point q0;
    private final Vector normal;

    /**
     * Plane constructor based on the normal vector and a point in the plane
     * 
     * @param normal vector of the plane
     * @param q0     point in the plane
     */
    public Plane(Vector normal, Point q0) {
        this.normal = normal.normalize();
        this.q0 = q0;
    }

    /**
     * Plane constructor based on three points
     * 
     * @param p1 1st point
     * @param p2 2nd point
     * @param p3 3rd point
     * @throws IllegalArgumentException when the points are on the same line
     */
    public Plane(Point p1, Point p2, Point p3) {
        normal = (p1.subtract(p2)).crossProduct(p1.subtract(p3)).normalize();
        q0 = p1;
    }

    /**
     * returns the Point delegate of a plane
     * 
     * @return point in the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * returns normal of a plane
     * 
     * @return normal of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * return a list of {@link GeoPoint} intersections between plane and ray
     * 
     * @param ray that may has intersection with the plane
     * @param max the maximum distance of the ray
     * @return list of intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double max) {

        Vector u;
        try {
            u = q0.subtract(ray.getP0());
        } catch (IllegalArgumentException ignore) {
            return null;
        }

        double nv = ray.getDir().dotProduct(normal);
        if (isZero(nv))
            return null;

        double t = alignZero(u.dotProduct(normal) / nv);
        Point point = ray.getPoint(t);
        return t > 0 && alignZero(max - t) >= 0 ? List.of(new GeoPoint(this, point)) : null;
    }

    @Override
    public String toString() {
        return "(" + q0.toString() + "," + normal.toString() + ")";
    }

    /**
     * return the normal vector of the plane
     * 
     * @param point point that may has normal vector of the plane
     * @return normal vector
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

}
