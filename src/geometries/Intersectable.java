package geometries;

import java.util.List;

import primitives.*;

/**
 * This class served all geometries for trace rays and find intersections
 * 
 * @author Noam Karmon & Omer Gertler
 */
public abstract class Intersectable {

    /**
     * This function returns all the intersection points of the geometry
     * 
     * @param ray that may has intersection with the geometry
     * @return list of intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Helping class (contained in {@link Intersectable})
     * Attributes a point to the geometric type to which it belongs
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * constructor for helping class GeoPoint
         * initialize the fields with parameters
         * 
         * @param geometry initialize geometry field
         * @param point    initialize point field
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public String toString() {
            return ("geometry: " + geometry + ", point: " + point);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof GeoPoint other))
                return false;
            return geometry == other.geometry && point.equals(other.point);
        }
    }

    /**
     * The function returns list of intersection points between a ray and geometry
     * 
     * @param ray that may has intersection with a geometry
     * @return a list of points that intersect a geometry
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * The function returns list of intersection points between a ray and geometry
     * 
     * @param ray         that may has intersection with a geometry
     * @param maxDistance the maximum distance of the ray
     * @return a list of points that intersect a geometry
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Helper function for {@link #findGeoIntersections}
     * The function returns list of intersection points between a ray and geometry
     * 
     * @param ray that may has intersection with a geometry
     * @return a list of points that intersect a geometry
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
