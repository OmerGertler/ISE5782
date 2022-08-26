package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Cylinders
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class CylinderTest {

        /**
         * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
         */
        @Test
        void testGetNormal() {
                Cylinder cylinder = new Cylinder(2, new Ray(new Point(1, 0, 0), new Vector(0, 0, 2)), 5);
                // ============ Equivalence Partitions Tests ==============
                // TC01: Normal test on the side of the cylinder
                assertEquals("ERROR TC01: Normal test on the tube of the cylinder", new Vector(0, 1, 0),
                                cylinder.getNormal(new Point(1, 2, 2)));

                // TC02: Normal test on the top of the cylinder
                assertEquals("TC02: ERROR: Normal test on the top of the cylinder", new Vector(0, 0, 1),
                                cylinder.getNormal(new Point(2, 0, 5)));

                // TC03: Normal test on the bottom of the cylinder
                assertEquals("TC03: ERROR: Normal test on the bottom of the cylinder", new Vector(0, 0, 1),
                                cylinder.getNormal(new Point(1, 1, 0)));

                // ========== Boundary Values Tests ==================
                // TC11: A test in the center base of the top of the cylinder
                assertThrows(IllegalArgumentException.class, () -> {
                        cylinder.getNormal(new Point(1, 0, 5));
                }, "TC11: ERROR: A test on the top of the cylinder");

                // TC12: A test in the center base of the bottom of the cylinder
                assertThrows(IllegalArgumentException.class, () -> {
                        cylinder.getNormal(new Point(1, 0, 0));
                }, "TC12: ERROR: A test on the bottom of the cylinder");

                // ***** group of tests of touching points between the cylinder surround and the
                // bottom base *****
                // TC13: A test on the touching point between the cylinder surround and the
                // bottom base
                assertEquals("TC13: ERROR: Normal test on the tube of the cylinder", new Vector(0, 0, 1),
                                cylinder.getNormal(new Point(3, 0, 0)));

                // TC14: A test on the touching point between the cylinder surround and the top
                // base
                assertEquals("TC14: ERROR: Normal test on the tube of the cylinder", new Vector(1, 0, 0),
                                cylinder.getNormal(new Point(3, 0, 5)));
        }
}
