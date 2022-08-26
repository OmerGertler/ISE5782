package geometries;

import java.util.List;

import primitives.*;

/**
 * This class represents a tube in euclidean space, based on radius and axis-ray
 */
public class Tube extends Geometry {

    protected final Ray axisRay;
    protected final double radius;

    /**
     * Tube constructor based on the radius and a point in the tube
     * 
     * @param radius
     * @param axisRay
     */
    public Tube(double radius, Ray axisRay) {
        this.radius = radius;
        this.axisRay = axisRay;
    }

    /**
     * returns Axis-Ray of Tube
     * 
     * @return Ray (axisRay)
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * returns radius of a tube
     * 
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * This function returns all intersections {@link GeoPoint} between a ray and
     * the tube
     * 
     * @param ray that may has intersection with the tube
     * @param max the maximum distance of the ray
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double max) {
        return null;
    }

    /**
     * This function returns the normal vector of the tube
     * 
     * @param p point that may has normal vector of the tube
     */
    @Override
    public Vector getNormal(Point point) {
        if (point.equals(axisRay.getP0()))
            return axisRay.getDir();

        Vector v = point.subtract(axisRay.getP0());
        double t = axisRay.getDir().dotProduct(v);
        return point.subtract(axisRay.getPoint(t)).normalize();
    }

    @Override
    public String toString() {
        return "(" + axisRay.toString() + "," + radius + ")";
    }
}
