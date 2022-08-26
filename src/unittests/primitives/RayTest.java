package unittests.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;

public class RayTest {

    /**
     * Test method for
     * {@link primitives.Ray#Ray(primitives.Point, primitives.Vector)}
     */
    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Get distance from ray source with positive point
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(2, 0, 0));
        assertEquals("Error: TC01, ray direction is incorrect", new Point(4, 0, 0), ray.getPoint(3));

        // TC02: Get distance from ray source with negative point
        assertEquals("Error: TC02, ray direction is incorrect", new Point(-2, 0, 0), ray.getPoint(-3));

        // =================== Boundary Values Tests ==================
        // TC11: Get distance from ray source with zero distance
        assertEquals("Error: TC11, Should the same point!", new Point(1, 0, 0), ray.getPoint(0));

    }

    /**
     * Test method for {@link primitives.Ray#getP0()}
     */
    @Test
    void testGetDir() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A ray with positive direction
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(2, 0, 0));
        assertEquals("Error: TC01, ray direction is incorrect", new Vector(1, 0, 0), ray.getDir());

        // TC02: A ray with negative direction
        ray = new Ray(new Point(1, 0, 0), new Vector(-2, 0, 0));
        assertEquals("Error: TC02, ray direction is incorrect", new Vector(-1, 0, 0), ray.getDir());

        // =================== Boundary Values Tests ==================
        // TC11: Get direction of a ray with zero direction
        assertThrows("Error: TC11, Should throw an exception!", IllegalArgumentException.class, () -> {
            new Ray(new Point(1, 0, 0), new Vector(0, 0, 0));
        });

    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List<Point> points)}
     */
    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: The close point to the beginning of the ray in the middle of the list
        Ray ray = new Ray(new Point(2, 0, 0), new Vector(2, 0, 0));
        List<Point> points = new LinkedList<>();
        points.add(new Point(1, 0, 0));
        points.add(new Point(2.1, 0, 0));
        points.add(new Point(3, 0, 0));
        assertEquals("Error: TC01, Should return the point in the middle of the list!", new Point(2.1, 0, 0),
                ray.findClosestPoint(points));

        // ============ Boundary Values Tests ==============
        // TC11: The close point to the beginning of the ray in the first of the list
        ray = new Ray(new Point(0.9, 0, 0), new Vector(2, 0, 0));
        assertEquals("Error: TC11, Should return the first point in the list!", new Point(1, 0, 0),
                ray.findClosestPoint(points));

        // TC12: The close point to the beginning of the ray in the last of the list
        ray = new Ray(new Point(3.1, 0, 0), new Vector(2, 0, 0));
        assertEquals("Error: TC12, Should return the last point in the list!", new Point(3, 0, 0),
                ray.findClosestPoint(points));

        // TC13: The list are empty
        points = new LinkedList<>();
        assertNull("Error: TC13, Should return null!", ray.findClosestPoint(points));

    }

}
