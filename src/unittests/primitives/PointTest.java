package unittests.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import primitives.*;

/**
 * Testing Point
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class PointTest {
    Point p1 = new Point(1, 2, 3);

    /**
     * Test method for {@link primitives.Point#Point(double, double, double)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test operations with points and vectors
        assertEquals("ERROR: add result is incorrect", new Point(2, 3, 4), p1.add(new Vector(1, 1, 1)));
    }

    /**
     * Test method for {@link primitives.Point#Point(double, double, double)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of subtract
        assertEquals("ERROR: subtract result is incorrect", new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p1));

        // ============ Boundary Values Tests ==============
        // TC11: A case of subtract with a point on the same point
        assertThrows("ERROR: TC11 ", IllegalArgumentException.class, () -> {
            new Point(1, 1, 1).subtract(new Point(1, 1, 1));
        });
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(geometries.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of a DistanceSquared
        assertEquals("ERROR: distanceSquared result is incorrect", 5, p1.distanceSquared(new Point(1, 1, 1)), 0.0001);

        // ============ Boundary Values Tests ==============
        // TC02: A case of a DistanceSquared with a point on the same point
        assertEquals("ERROR: distanceSquared result is incorrect", 0, p1.distanceSquared(p1), 0.0001);
    }

    /**
     * Test method for {@link primitives.Point#distance(geometries.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of a Distance
        assertEquals("ERROR: distance result is incorrect", Math.sqrt(5), p1.distance(new Point(1, 1, 1)), 0.0001);

        // ============ Boundary Values Tests ==============
        // TC02: A case of a Distance with a point on the same point
        assertEquals("ERROR: distance result is incorrect", 0, p1.distance(p1), 0.0001);

    }

}
