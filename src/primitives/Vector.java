package primitives;

/**
 * This class represents Vector in euclidean space with three Point parameters
 * (x, y, z)
 */
public class Vector extends Point {
    /**
     * X axis unit vector
     */
    public static final Vector X = new Vector(1, 0, 0);

    /**
     * Y axis unit vector
     */
    public static final Vector Y = new Vector(0, 1, 0);

    /**
     * Z axis unit vector
     */
    public static final Vector Z = new Vector(0, 0, 1);

    /**
     * Constructs a new vector with three coordinates forms as Double3
     * {@link primitives.Double3#Double3(double, double, double)}
     * 
     * @param xyz coordinates of the vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    /**
     * Constructs a new vector with three coordinates (x, y, z)
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    @Override
    public String toString() {
        return "Vector: " + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector))
            return false;
        Vector other = (Vector) obj;
        return xyz.equals(other.xyz);
    }

    /**
     * calculate addition with another Vector
     * 
     * @param Vector rhs
     * @return result vector
     */
    public Vector add(Vector rhs) {
        return new Vector(xyz.add(rhs.xyz));
    }

    /**
     * multiply vector by a scale
     * 
     * @param double rhs
     * @return scale multiplied vector
     */
    public Vector scale(double rhs) {
        return new Vector(xyz.scale(rhs));
    }

    /**
     * Does Cross-producing to the vector by another Vector
     * 
     * @param Vector rhs
     * @return cross product result vector
     */
    public Vector crossProduct(Vector rhs) {
        Double3 temp = new Double3(
                (xyz.d2 * rhs.xyz.d3) - (xyz.d3 * rhs.xyz.d2),
                (xyz.d3 * rhs.xyz.d1) - (xyz.d1 * rhs.xyz.d3),
                (xyz.d1 * rhs.xyz.d2) - (xyz.d2 * rhs.xyz.d1));
        return new Vector(temp);
    }

    /**
     * Calculate length of the Vector powered by 2
     * 
     * @return squared length
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * Calculate length of the Vector
     * 
     * @return length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Calculate the normalized Vector
     * 
     * @return normalized Vector
     */
    public Vector normalize() {
        return scale(1 / length());
    }

    /**
     * Does dot-productions to vector by another Vector
     * 
     * @param Vector rhs
     * @return dot-product result Vector
     */
    public double dotProduct(Vector rhs) {
        return this.xyz.d1 * rhs.xyz.d1 + this.xyz.d2 * rhs.xyz.d2 + this.xyz.d3 * rhs.xyz.d3;

    }

    /**
     * The function construct new vector after rotation calculating
     * with consideration of axis vector and rotation angle.
     * 
     * @param axis     of rotation
     * @param thetaRad of rotation in radians
     *
     * @return new rotated vector
     */
    public Vector vectorRotate(Vector axis, double thetaRad) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        double u = axis.getX();
        double v = axis.getY();
        double w = axis.getZ();

        double v1 = u * x + v * y + w * z;

        double xPrime = u * v1 * (1d - Math.cos(thetaRad))
                + x * Math.cos(thetaRad)
                + (-w * y + v * z) * Math.sin(thetaRad);

        double yPrime = v * v1 * (1d - Math.cos(thetaRad))
                + y * Math.cos(thetaRad)
                + (w * x - u * z) * Math.sin(thetaRad);

        double zPrime = w * v1 * (1d - Math.cos(thetaRad))
                + z * Math.cos(thetaRad)
                + (-v * x + u * y) * Math.sin(thetaRad);

        return new Vector(xPrime, yPrime, zPrime);
    }
}
