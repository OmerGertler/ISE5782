package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * This class represents a triangle in euclidean space,
 * based on three point,
 * using Polygon constrictor {@link geometries.Polygon#Polygon(Point...)}
 */
public class Triangle extends Polygon {

    /**
     * constructor of triangle based on three Points
     * (using super constructor of Polygon)
     * 
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * return a list of intersections between triangle and ray
     * find intersections using barycentric method
     * I is the traced point on the ray
     * if (0 < a, b < 1) => I is inside the triangle
     * 
     * @param ray that may has intersection with the triangle
     * @param max the maximum distance of the ray
     * @return list of intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double max) {
        List<GeoPoint> intersections = plane.findGeoIntersections(ray, max);
        if (intersections == null)
            return null;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double s1 = alignZero(v.dotProduct(n1));
        if (s1 == 0)
            return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double s2 = v.dotProduct(n2);
        if (s1 * s2 <= 0)
            return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double s3 = v.dotProduct(n3);
        if (s1 * s3 <= 0)
            return null;

        intersections.get(0).geometry = this;
        return intersections;
    }
}
