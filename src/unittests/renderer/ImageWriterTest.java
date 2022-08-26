package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.*;

/**
 * Testing ImageWriter Class
 * 
 * @author Noam karmon & Omer Gertler
 *
 */
public class ImageWriterTest {
    /**
     * Test method for {@Link WriteToImage()}
     */
    @Test
    void testWriteToImage() {
        int nX = 801;
        int nY = 501;
        int vX = nX / 16;
        int vY = nY / 10;
        ImageWriter imageWriter = new ImageWriter("test", nX, nY);
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % vY == 0 || j % vX == 0) {
                    imageWriter.writePixel(j, i, new Color(RED));
                } else {
                    imageWriter.writePixel(j, i, new Color(GREEN));
                }
            }
        }
        imageWriter.writeToImage();
    }
}
