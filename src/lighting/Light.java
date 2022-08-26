package lighting;

import primitives.Color;

/**
 * abstract class Light supports classes that represents internal and external
 * light sources
 * like sun, spot light ect'.
 */
abstract class Light {

    protected final Color intensity;

    /**
     * Constructor os the class Light, initialized the intensity field
     * 
     * @param color of the intensity
     */
    protected Light(Color color) {
        intensity = color;
    }

    /**
     * getter of the intensity color field
     * 
     * @return {@link Color} intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
