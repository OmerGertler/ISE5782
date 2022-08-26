package unittests.geometries;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Triangle
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class TriangleTest {
        /**
         * Test method for
         * {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}.
         */
        @Test
        void testGetNormal() {
                // ============ Equivalence Partitions Tests ==============
                // TC01: A simple case of a normal
                Triangle triangle = new Triangle(new Point(0, 0, -5), new Point(1, 0, 0), new Point(0, 1, 0));
                double sqrt3 = Math.sqrt(1d / 3);
                assertEquals(new Vector(sqrt3, sqrt3, sqrt3).length(), triangle.getNormal(new Point(1, 0, 0)).length(),
                                0.0001,
                                "TC01: ERROR: normal result is incorrect");
        }

        @Test
        void testFindIntersections() {
                Triangle triangle = new Triangle(new Point(0, 1, 1), new Point(1, 0, 1), new Point(0, 0, 1));
                List<Point> intersections = triangle
                                .findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0.23, 0.14, 1)));
                // ============ Equivalence Partitions Tests ==============
                // TC01: a simple case of a ray that intersects the triangle
                assertEquals(intersections.size(), 1, "TC01: ERROR: have to be only 1 intersection");

                // TC02: a simple case of a ray that does not intersect the triangle against the
                // edge
                assertNull("TC02: ERROR: have to be no intersection",
                                triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0.23, 0.14, -1))));

                // TC03: a simple case of a ray that does not intersect the triangle against the
                // vertex
                assertNull("TC03: ERROR: have to be no intersection",
                                triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0.23, 0.14, 0))));

                // TC04: a simple case of a ray that intersects the triangle with max distance
                var intersection = triangle
                                .findGeoIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0.23, 0.14, 1)), 10);
                assertEquals(intersection.size(), 1, "TC01: ERROR: have to be only 1 intersection");

                // TC05: a simple case of a ray that intersects the triangle with max distance
                intersection = triangle
                                .findGeoIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0.23, 0.14, 1)), 0.5);
                assertNull(intersection, "TC01: ERROR: have to be only 1 intersection");

                // =============== Boundary Values Tests ==================
                // TC11: a ray that intersects the triangle on the vertex
                assertNull("TC11: ERROR: have to be no intersection on the vertex",
                                triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0.23, 0.14, 0.5))));

                // TC12: a ray that intersects the triangle on the edge
                assertNull("TC12: ERROR: have to be no intersection on the edge",
                                triangle.findIntersections(
                                                new Ray(new Point(0, 0.5, 0), new Vector(0.23, 0.14, 0.25))));

                // TC13: a ray on the continuance of the edge\
                assertNull("TC13: ERROR: have to be no intersection on the edge",
                                triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 2, 1))));

        }
}
