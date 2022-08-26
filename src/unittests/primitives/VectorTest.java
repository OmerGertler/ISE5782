package unittests.primitives;

import static primitives.Util.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * Testing Vector
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class VectorTest {
    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of add
        assertEquals("Error: TC01, add result is incorrect", new Vector(2, 3, 4),
                new Vector(1, 2, 3).add(new Vector(1, 1, 1)));

        // ============ Boundary Values Tests ==============
        // TC02: A case of add with a vector and minus vector
        assertThrows("Error: TC02, add result is incorrect", IllegalArgumentException.class, () -> {
            new Vector(1, 1, 1).add(new Vector(-1, -1, -1));
        });
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001,
                "Error: TC01, crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("Error: TC02, crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "Error: TC11, crossProduct() for parallel vectors does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        double vr = v1.dotProduct(v2);

        // TC01: Test that dot-product is proper (orthogonal vectors taken for
        // simplicity)
        assertTrue("Error: TC01, dotProduct() wrong result", isZero(vr));

        // TC02: Test dot-product result orthogonality to its operands
        assertTrue("Error: TC02, dotProduct() result is not orthogonal to 1st operand", isZero(v1.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test dot-product with zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.dotProduct(new Vector(0, 0, 0)),
                "Error: TC11, dotProduct() for parallel vectors does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of length
        assertEquals(5, new Vector(0, 3, 4).length(), 0.00001, "Error: TC01, length() wrong result");
        // TC02: A simple case of length with zero vector
        assertEquals(1, new Vector(1, 0, 0).length(), 0.00001, "Error: TC02, length() wrong result");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of lengthSquared
        assertEquals(25, new Vector(0, 3, 4).lengthSquared(), 0.00001, "Error: TC01, lengthSquared() wrong result");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of normalize
        assertEquals("Error: TC01, normalize() wrong result", new Vector(0.26726124, 0.53452248, 0.80178373).length(),
                new Vector(1, 2, 3).normalize().length(), 0.00001);

    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of scale
        assertEquals(new Vector(2, 4, 6), new Vector(1, 2, 3).scale(2), "Error: TC01, scale() wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: A simple case of zero scale
        assertThrows("Error: TC11, scale() with zero scale does not throw an exception", IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).scale(0));

    }

    /**
     * Test method for {@link primitives.Vector#sub(primitives.Vector)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of subtract
        assertEquals("ERROR: TC01, The vector is incorrect!", new Vector(1, 1, 1),
                new Point(2, 3, 4).subtract(new Point(1, 2, 3)));

        // ============ Boundary Values Tests ==============
        // TC11: A case of subtract with a point on the same point
        assertThrows("ERROR: TC11, The vector should be zero vector", IllegalArgumentException.class, () -> {
            new Point(1, 1, 1).subtract(new Point(1, 1, 1));
        });
    }
}
