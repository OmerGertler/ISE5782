package primitives;

/**
 * This class represents a point in coordinate system with three parameters:
 * x for the x-line
 * y for the y-line
 * z for the z-line
 */
public class Point {

    protected final Double3 xyz;
    public final static Point ZERO = new Point(0, 0, 0);

    /**
     * Constructor to initialize Point based object with three coordinates
     * 
     * @param x coordinate value
     * @param y coordinate value
     * @param z coordinate value
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to initialize Point based object with Double3
     * 
     * @param xyz coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * get the x value of the point
     * 
     * @return x coordinate value
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * get the y value of the point
     * 
     * @return y coordinate value
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * get the z value of the point
     * 
     * @return z coordinate value
     */
    public double getZ() {
        return xyz.d3;
    }

    /**
     * Addition with another Vector
     * 
     * @param Vector right handle side operand for product
     * @return add result Point
     */
    public Point add(Vector rhs) {
        return new Point(xyz.add(rhs.xyz));
    }

    /**
     * perform subtract with Point
     * 
     * @param Point right handle side operand for product
     * @return subtract result Vector
     */
    public Vector subtract(Point rhs) {
        return new Vector(xyz.subtract(rhs.xyz));
    }

    /**
     * returns the distance to another point
     * 
     * @param Point right handle side operand for product
     * @return distance to another point
     */
    public double distanceSquared(Point rhs) {
        double dx = xyz.d1 - rhs.xyz.d1;
        double dy = xyz.d2 - rhs.xyz.d2;
        double dz = xyz.d3 - rhs.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * returns the squared distance to another point
     * 
     * @param Point right handle side operand for product
     * @return squared distance to another point
     */
    public double distance(Point rhs) {
        return Math.sqrt(distanceSquared(rhs));
    }

    @Override
    public String toString() {
        return "Point [" + xyz.d1 + "," + xyz.d2 + "," + xyz.d3 + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point other))
            return false;
        return xyz.equals(other.xyz);
    }

}
