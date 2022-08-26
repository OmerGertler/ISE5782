package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * This class represents sphere in the space, based on center point and radius
 * {@link primitives.Point#Point(double, double, double)}
 */
public class Sphere extends Geometry {

    private final Point center;
    private final double radius;
    private final double radiusSqr;

    /**
     * Sphere constructor based on the radius and the center point of the sphere
     * 
     * @param center of the sphere
     * @param radius of the sphere
     */
    public Sphere(Point center, double radius) {
        this.radius = radius;
        this.center = center;
        this.radiusSqr = radius * radius;
    }

    /**
     * returns the center point of sphere
     * 
     * @return center point
     */
    public Point getCenter() {
        return center;
    }

    /**
     * returns radius of sphere
     * 
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * This function returns all {@link GeoPoint} intersections between a ray and
     * the sphere
     * 
     * @param ray that may has intersection with the sphere
     * @param max the maximum distance of the ray
     * @return list of intersection points or null if there is no intersection
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double max) {
        Vector v = ray.getDir();
        Point p = ray.getP0();

        Vector u;
        try {
            u = center.subtract(p);
        } catch (IllegalArgumentException ignore) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        double tm = v.dotProduct(u);
        double dSqr = u.lengthSquared() - (tm * tm);
        double thSqr = radiusSqr - dSqr;
        if (alignZero(thSqr) <= 0)
            return null;

        double th = Math.sqrt(thSqr);
        double t2 = alignZero(tm + th);

        if (t2 <= 0) // both points are behind the ray
            return null;

        double t1 = alignZero(tm - th);
        if (alignZero(t1 - max) > 0) // both points are beyond the maximum distance
            return null;

        if (alignZero(t2 - max) > 0) // far point is beyond the maximum distance
            return t1 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t1)));

        else // far point is inside the required interval
            return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2)))
                    : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }

    /**
     * This function returns the normal vector of the sphere
     * 
     * @param p point that may has normal vector of the sphere
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public String toString() {
        return "(" + center.toString() + "," + radius + ")";
    }

}
