package geometries;

import primitives.*;

/**
 * This class represents Cylinder, based on radius, axis-ray and height
 */
public class Cylinder extends Tube {

    private final double height;

    /**
     * Cylinder constructor based on the radius and a point in the cylinder
     * 
     * @param radius  of cylinder
     * @param axisRay of cylinder
     * @param height  of cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;

    }

    /**
     * returns height of cylinder
     * 
     * @return height of cylinder;
     */
    public double getHeight() {
        return height;
    }

    /**
     * This function return the normal vector of the cylinder at the point
     * 
     * @param point point that may has normal vector of the cylinder
     * @return normal vector of the cylinder
     */
    @Override
    public Vector getNormal(Point point) {
        // find the normal vector on the bottom base
        Vector v1 = point.subtract(axisRay.getP0());
        if (v1.dotProduct(axisRay.getDir()) == 0) { // the vectors are orthogonal to each other
            if (v1.length() <= radius) { // the point is on the base of the cylinder
                return axisRay.getDir();
            }
        }
        // find the normal vector on the top base
        Vector v2 = axisRay.getDir().scale(axisRay.getDir().normalize().dotProduct(v1));
        if (v1.normalize().equals(v2.normalize())) {
            throw (new IllegalArgumentException("ERROR: Point is versicle to the ray"));
        } else if (v1.equals(v2)) {
            return axisRay.getDir();
        } else {
            Vector normal = v1.subtract(v2);
            return normal.length() == radius ? normal.normalize() : axisRay.getDir();
        }
    }

    @Override
    public String toString() {
        return "(" + super.toString() + "," + height + ")";
    }
}
