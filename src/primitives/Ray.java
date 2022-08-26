package primitives;

import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a Ray based on Point and Vector
 */
public class Ray {

    private final Point p0;
    private final Vector dir;
    private static final double DELTA = 0.1;

    /**
     * Constructor to initialize Ray based on Point and Vector
     * 
     * @param p0  Point of the Ray
     * @param dir Vector of the Ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Constructor to initialize Ray based on Point and Vector
     * 
     * @param p0        Point of the Ray
     * @param direction Vector of the Ray - must be normalized
     * @param n         vector of the normal
     */
    public Ray(Point p0, Vector direction, Vector n) {
        this.p0 = p0.add(n.scale(n.dotProduct(direction) > 0 ? DELTA : -DELTA));
        this.dir = direction.normalize();
    }

    /**
     * This function returns a Point on the ray at a given distance from the ray
     * source (the t parameter)
     * 
     * @param distance from the ray source
     * @return Point on the Ray by a the given distance
     */
    public Point getPoint(double t) {
        try {
            return isZero(t) ? p0 : p0.add(dir.scale(t));
        } catch (IllegalArgumentException e) {
            return p0;
        }
    }

    /**
     * returns the source point of ray
     * 
     * @return source point (p0)
     */
    public Point getP0() {
        return p0;
    }

    /**
     * returns direction vector of ray
     * 
     * @return direction vector (dir)
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * this function return the close point to the beginning of the ray
     * 
     * @param points list of points
     * @return the closest point to the beginning of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * this function return the close {@link GeoPoint} point to the beginning of the
     * ray
     * 
     * @param list of GeoPoint points
     * @return the closest GeoPoint point to the beginning of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        GeoPoint closestGeoPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (GeoPoint item : points) {
            double distance = item.point.distance(p0);
            if (distance < minDistance) {
                minDistance = distance;
                closestGeoPoint = item;
            }
        }
        return closestGeoPoint;
    }

    /**
     * auxiliary function to randomly scatter points within a circular surface.
     * returns a list of rays which related to the surface.
     *
     * @param center  - center point of the circular surface.
     * @param vUp     - upper vector of circular surface.
     * @param vRight  - right vector of circular surface.
     * @param radius  - radius of circular surface. (mostly aperture)
     * @param numRays - number of rays we create in the circular surface.
     * @param dist    - distance between the view plane and the focal plane
     * @return list of rays from the area of the aperture to the focal point
     */
    public List<Ray> randomRaysInCircle(Point center, Vector vUp, Vector vRight, double radius, int numRays,
            double dist) {
        List<Ray> rays = new LinkedList<>();
        rays.add(this); // including the original ray
        if (radius == 0)
            return rays;
        Point focalPoint = getPoint(dist);
        for (int i = 1; i < numRays; ++i) {
            double cosTheta = -1 + (Math.random() * 2); // between 0 to pie
            double sinTheta = Math.sqrt(1 - cosTheta * cosTheta); // by Pitagoras theorem
            double d = -radius + (Math.random() * (2 * radius)); // In a circle or in diameter
            // Move from polar to Cartesian system:
            double x_move = d * cosTheta;
            double y_move = d * sinTheta;
            // define a new starting point for the new ray
            Point newP0 = center;
            if (!isZero(x_move)) {
                newP0 = newP0.add(vRight.scale(x_move));
            }
            if (!isZero(y_move)) {
                newP0 = newP0.add(vUp.scale(y_move));
            }
            rays.add(new Ray(newP0, (focalPoint.subtract(newP0))));
        }
        return rays;
    }

    /**
     * this function return a beam of rays in the pixel by DOF
     *
     * @param center  - center point of the circular surface.
     * @param vUp     - upper vector of circular surface.
     * @param vRight  - right vector of circular surface.
     * @param radius  - radius of circular surface. (mostly aperture)
     * @param numRays - number of rays we create in the circular surface.
     * @param dist    - distance between the view plane and the focal plane
     * @return list of rays from the area of the aperture to the focal point
     */
    public List<Ray> raysInGrid(Point center, Vector vUp, Vector vRight, double radius, int numRays,
            double dist) {
        List<Ray> rays = new LinkedList<>();

        rays.add(this); // including the original ray
        if (radius == 0)
            return rays;

        Point focalPoint = getPoint(dist);
        int sqrtRays = (int) Math.floor(Math.sqrt(numRays));

        for (int i = 0; i < sqrtRays; ++i) {
            for (int j = 0; j < sqrtRays; ++j) {
                // use the radius to move the point in the pixel
                double x_move = i * radius / sqrtRays;
                double y_move = j * radius / sqrtRays;
                // define a new starting point for the new ray
                Point newP0 = center;
                if (!isZero(x_move)) {
                    newP0 = newP0.add(vRight.scale(x_move));
                }
                if (!isZero(y_move)) {
                    newP0 = newP0.add(vUp.scale(y_move));
                }
                rays.add(new Ray(newP0, (focalPoint.subtract(newP0))));
            }
        }
        return rays;

    }

    @Override
    public String toString() {
        return "Ray [p0=" + p0 + ", dir=" + dir + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Ray other))
            return false;
        return p0.equals(other.p0) && dir.equals(other.dir);
    }

}
