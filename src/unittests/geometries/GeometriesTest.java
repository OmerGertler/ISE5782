package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Geometries
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class GeometriesTest {
        /**
         * Test method for
         * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
         */
        @Test
        void testFindIntersections() {
                Geometries geometries = new Geometries();
                Ray ray = new Ray(new Point(3, 4, 2), new Vector(1, 2, 5));

                // =============== Boundary Values Tests ==================
                // TC01: The geometries list is empty
                assertEquals("TC01: ERROR: The geometries list should be empty", geometries.findIntersections(ray),
                                null);

                // TC02: The geometries list is not empty but there is no intersection
                geometries.add(new Triangle(new Point(2, 0, 5), new Point(2, 3, 5), new Point(7, 2, 1)));
                geometries.add(new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                new Point(-1, 1, 1)));
                geometries.add(new Sphere(new Point(0, 0, 0), 2));
                assertNull("TC02: ERROR: The geometries list should be without intersection",
                                geometries.findIntersections(ray));

                // TC03: The geometries list is not empty and there is one intersection
                geometries.add(new Triangle(new Point(4, 5, 2), new Point(5, 6, 7), new Point(2, 4, 3)));
                assertEquals("TC03: ERROR: The geometries list should be with one intersection!",
                                geometries.findIntersections(ray).size(), 1);

                // TC04: The geometries list is not empty and all of them intersect
                geometries = new Geometries();
                geometries.add(new Sphere(new Point(3, 9, 6), 5));
                geometries.add(new Sphere(new Point(3, 5, 3), 6));
                geometries.add(new Plane(new Vector(1, 5, 4), new Point(3, 4, 4)));
                geometries.add(new Triangle(new Point(4, 5, 2), new Point(5, 6, 7), new Point(2, 4, 3)));
                assertEquals("TC04: ERROR: All the shapes must to intersect", geometries.findIntersections(ray).size(), 5);

                // TC05: a simple case of a ray that intersects the geometries with max distance
                var intersection = geometries
                                .findGeoIntersections(ray, 3);
                assertEquals(intersection.size(), 3, "TC01: ERROR: have to be only 1 intersection");

                // TC06: a simple case of a ray that intersects the geometries with max distance
                intersection = geometries
                                .findGeoIntersections(ray, 0.5);
                assertNull(intersection, "TC01: ERROR: have to be only 1 intersection");
                // ============ Equivalence Partitions Tests ==============
                // TC11: The geometries list is not empty and not all of them intersect
                geometries.add(new Triangle(new Point(20, 18, 17), new Point(17, 15, 17), new Point(20, 20, 20)));
                assertEquals("TC11: ERROR: Not all geometries need to be intersect ",
                                geometries.findIntersections(ray).size(), 5);

        }
}
