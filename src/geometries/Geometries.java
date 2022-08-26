package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

/**
 * This class represents an bundle of geometric bodies
 */
public class Geometries extends Intersectable {

    /**
     * List of geometries
     */
    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * Constructor with list of geometries
     * 
     * @param list of geometries
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Add geometries to the bundle of geometries
     * 
     * @param geometries to add to the list
     */
    public void add(Intersectable... geometries) {
        if (geometries.length > 0) this.geometries.addAll(List.of(geometries));
    }

    /**
     * This function returns all the {@link GeoPoint} intersection points with a ray
     * and the
     * geometries in the bundle
     * 
     * @param ray that may has intersection points with the geometries
     * @param max the maximum distance of the ray
     * @return list of the intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double max) {
        List<GeoPoint> intersections = null;
        for (var geo : geometries) {
            var geoIntersections = geo.findGeoIntersections(ray, max);
            if (geoIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>(geoIntersections);
                else
                    intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}
