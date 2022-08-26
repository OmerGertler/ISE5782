package lighting;

import primitives.*;

/**
 *  AmbientLight class served the scene by initialized the ambient light color and intensity
 *  The default color of the ambient light is black
 *  The ambient light effects all the objects's color in the scene equally 
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class AmbientLight extends Light {

    /**
     * Ambient default constructor
     * Defines the default color of the scene to black
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Ambient Light constructor 
     * The color of the ambient light scales by the attenuation
     * 
     * @param intensity the color of the light
     * @param kA the coefficient of attenuation of filler light
     */
    public AmbientLight(Color intensity, Double3 kA) {
        super(intensity.scale(kA));
    }
}
