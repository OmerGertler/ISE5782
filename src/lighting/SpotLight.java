package lighting;

import primitives.*;

/**
 * The class defined a spot-light
 * The class extends {@link PointLight} class, with addition of the direction of
 * the light
 */
public class SpotLight extends PointLight {

    private final Vector direction;
    private double narrowBeam = 1;

    /**
     * Constructor of a spot light object
     *
     * @param intensity, the color of the light source
     * @param position   of the light source
     * @param direction  of the light
     */
    public SpotLight(Color intensity, Point position, Vector dir) {
        super(intensity, position);
        this.direction = dir.normalize();
    }

    /**
     * this function set the narrow beam of the spot light
     * if the beam is less than 1 its wider else it is narrower
     * 
     * @param narrowBeam
     * @return this spot light object
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * this function Override the getIntensity function of the parent class
     * and return the intensity of the light source of the spot light
     * 
     * @param p is the point in the scene
     * @return the color of the light source
     */
    @Override
    public Color getIntensity(Point p) {
        double dp = Util.alignZero(direction.dotProduct(getL(p)));
        if (dp <= 0)
            return Color.BLACK;
        if (narrowBeam != 1)
            dp = Math.pow(dp, narrowBeam);
        return super.getIntensity(p).scale(dp);
    }
}
