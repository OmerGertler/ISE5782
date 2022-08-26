package unittests.geometries;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Tubes
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of a normal
        Tube tube = new Tube(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        assertEquals("TC01: ERROR: The normal is not correct", tube.getNormal(new Point(0, 2, 5)),
                new Vector(0, 1, 0));

        // ========== Boundary Values Tests ==================
        // TC11: A Right angle with the ray source point
        tube = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        assertEquals("TC01: ERROR:  A Right angle with the ray source point", tube.getNormal(new Point(1, 0, 0)),
                new Vector(1, 0, 0));
    }
}
