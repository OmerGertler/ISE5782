package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Sphere
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class SphereTest {
        /**
         * Test method for
         * {@link geometries.Sphere#Sphere(double, primitives.Point)}.
         */
        @Test
        void testGetNormal() {
                // ============ Equivalence Partitions Tests ==============
                // TC01: A simple case of a normal
                Sphere sphere = new Sphere(new Point(0, 0, -5), 2);
                double sqrt3 = Math.sqrt(1d / 3);
                assertEquals(new Vector(sqrt3, sqrt3, sqrt3).length(), sphere.getNormal(new Point(1, 0, 0)).length(),
                                0.0001,
                                "ERROR: normal result is incorrect");

        }

        /**
         * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
         */
        @Test
        public void testFindIntersections() {
                Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

                // ============ Equivalence Partitions Tests ==============

                // TC01: Ray's line is outside the sphere (0 points)
                assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                                "TC01: Ray's line out of sphere");

                // TC02: Ray starts before and crosses the sphere (2 points)
                Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
                Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
                List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                                new Vector(3, 1, 0)));
                assertEquals(2, result.size(), "TC02: Wrong number of points");
                if (result.get(0).getX() > result.get(1).getX())
                        result = List.of(result.get(1), result.get(0));
                assertEquals(List.of(p1, p2), result, "TC02: Ray crosses sphere");

                // TC03: Ray starts inside the sphere (1 point)
                assertEquals(sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                                List.of(new Point(2, 0, 0)), "TC03: Ray starts inside the sphere");

                // TC04: Ray starts after the sphere (0 points)
                assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                                "TC04: Ray starts after the sphere");

                // TC05: a simple case of a ray that intersects the sphere with max distance
                var intersection = sphere
                                .findGeoIntersections(new Ray(new Point(-1, 0, 0),
                                                new Vector(3, 1, 0)), 10);
                assertEquals(intersection.size(), 2, "TC01: ERROR: have to be only 1 intersection");

                // TC06: a simple case of a ray that intersects the sphere with max distance
                intersection = sphere
                                .findGeoIntersections(new Ray(new Point(-1, 0, 0),
                                                new Vector(3, 1, 0)), 0.5);
                assertNull(intersection, "TC01: ERROR: have to be only 1 intersection");

                // =============== Boundary Values Tests ==================

                // **** Group: Ray's line crosses the sphere (but not the center)
                // TC11: Ray starts at sphere and goes inside (1 points)
                assertEquals(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 0.5, 0.5))).size(),
                                1, "TC11: Ray starts at sphere and goes inside");

                // TC12: Ray starts at sphere and goes outside (0 points)
                assertNull(sphere.findIntersections(
                                new Ray(new Point(0.43, -0.69, 0.45), new Vector(-1.79, -3.36, 0.45))),
                                "TC12: Ray starts at sphere and goes outside");

                // **** Group: Ray's line goes through the center
                // TC13: Ray starts before the sphere (2 points)
                assertEquals(sphere.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(1, 0, 0))),
                                List.of(new Point(0, 0, 0), new Point(2, 0, 0)), "TC13: Ray starts before the sphere");

                // TC14: Ray starts at sphere and goes inside (1 points)
                assertEquals(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 0, 0))),
                                List.of(new Point(0, 0, 0)), "TC14: Ray starts at sphere and goes inside");

                // TC15: Ray starts inside (1 points)
                assertEquals(sphere.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(1, 0, 0))),
                                List.of(new Point(2, 0, 0)), "TC15: Ray starts inside");

                // TC16: Ray starts at the center (1 points)
                assertEquals(sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, 1))).size(),
                                1, "TC16: Ray starts at the center");

                // TC17: Ray starts at sphere and goes outside (0 points)
                assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                                "TC17: Ray starts at sphere and goes outside");

                // TC18: Ray starts after sphere (0 points)
                assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                                "TC18: Ray starts after sphere");

                // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
                // TC19: Ray starts before the tangent point
                assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), new Vector(1, 0, 0))),
                                "TC19: Ray starts before the tangent point");

                // TC20: Ray starts at the tangent point
                assertNull(sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 0, 0))),
                                "TC20: Ray starts at the tangent point");

                // TC21: Ray starts after the tangent point
                assertNull(sphere.findIntersections(new Ray(new Point(2, -1, 0), new Vector(1, 0, 0))),
                                "TC21: Ray starts after the tangent point");

                // **** Group: Special cases
                // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
                // center line
                assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0))),
                                "TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
        }

}
