package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Planes
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class PlaneTest {

    @Test
    void testConstructor() {
        // ============ Boundary Values Tests ==============
        // TC11: First & second point are merged
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(new Point(2, 1, 0), new Point(2, 1, 0), new Point(7, 1, 3));
        }, "ERROR: TC01 first & second point are merged");

        // TC12: First & second point are are the same line
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(new Point(2, 1, 0), new Point(5, 0, 3), new Point(3.5, 0.5, 1.5));
        }, "ERROR: TC02 first & second point are are the same line");

    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of a normal
        Plane plane = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3).length(), plane.getNormal(new Point(1, 0, 0)).length(),
                "ERROR: TC01 normal result is incorrect");
    }

    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Vector(0, 1, 0), new Point(-2, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Not intersect plane
        Ray ray = new Ray(new Point(0, 0, 3), new Vector(0, -2, -3));
        assertNull(plane.findIntersections(ray), "TC01: ERROR: There should be no intersections points");

        // TC02: Ray intersects plane
        ray = new Ray(new Point(4, 2, 0), new Vector(-5, -1, 3));
        assertEquals(plane.findIntersections(ray).get(0), new Point(-6, 0, 6),
                "TC02: ERROR: Ray intersects the plane, there has to be no intersections points");

        // TC05: a simple case of a ray that intersects the plane with max distance
        var intersection = plane
                .findGeoIntersections(ray, 50);
        assertEquals(intersection.size(), 1, "TC01: ERROR: have to be only 1 intersection");

        // TC06: a simple case of a ray that intersects the plane with max distance
        intersection = plane
                .findGeoIntersections(ray, 0.5);
        assertNull(intersection, "TC01: ERROR: have to be only 1 intersection");

        // =============== Boundary Values Tests ==================

        // TC11: Ray is included in the plane
        ray = new Ray(new Point(-4, 0, 0), new Vector(4, 0, 3));
        assertNull(plane.findIntersections(ray),
                "TC11: ERROR: Ray included in the plane, there has to be no intersections");

        // TC12: Ray is parallel to the plane
        ray = new Ray(new Point(-4, 1, 0), new Vector(4, 0, 3));
        assertNull(plane.findIntersections(ray),
                "TC12: ERROR: Ray parallel to the plane, there shouldn't be intersections");

        // TC13: Ray is orthogonal before
        ray = new Ray(new Point(0, 2, 0), new Vector(0, 4, 0));
        assertNull(plane.findIntersections(ray), "TC13: ERROR: Ray is orthogonal before, there should be null");

        // TC14: Ray is orthogonal in
        ray = new Ray(new Point(2, 0, 0), new Vector(0, -8, 0));
        assertNull(plane.findIntersections(ray), "TC14: ERROR: Ray is orthogonal in the plane, there should be null");

        // TC15: Ray is orthogonal after
        ray = new Ray(new Point(0, -4, 0), new Vector(0, -4, 0));
        assertNull(plane.findIntersections(ray), "TC15: ERROR: Ray is orthogonal before, there should be null");

        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        ray = new Ray(new Point(0, 0, 9), new Vector(1.66, 1, 1));
        assertNull(plane.findIntersections(ray),
                "TC16: ERROR: Ray is neither orthogonal nor parallel to and begins at the plane, there should be null");

        // TC17: BVA Ray is neither orthogonal nor parallel to the plane and begins in
        // the same point which appears as reference point in the plane
        ray = new Ray(new Point(-2, 0, 0), new Vector(1, 1, 1));
        assertNull(plane.findIntersections(ray),
                "TC17: ERROR: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane, there should be null");
    }

}
