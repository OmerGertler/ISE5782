package lighting;

import primitives.*;

/**
 * PointLight class is a light source (like lamb for example)
 * The position and intensity of the light source are changing the effect of the
 * light in the scene
 * The class extends {@link Light} and implements {@link LightSource} interface
 */
public class PointLight extends Light implements LightSource {

    //#region fields
    /**
     * the position of the light source in the scene
     */
    private final Point position; 

    // ****************attenuation factors****************
    /**
     * linear func value of attenuation
     */
    private double kC = 1d;

    /**
     * quadrant func value of attenuation
     */
    private double kL = 0d;

    /**
     * constant func value of attenuation
     */
    private double kQ = 0d;
    //#endregion

    /**
     * Constructor of PointLight
     * initialize the intensity color and the position of the light source in the
     * scene.
     * 
     * @param intensity of the light source
     * @param position of the light source in the scene  
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d2 = p.distanceSquared(position);
        double factor = kC + kL * Math.sqrt(d2) + kQ * d2;
        return intensity.scale(1d / factor);
    }

    @Override
    public Vector getL(Point p) {
        return (p.subtract(position)).normalize();
    }

    /**
     * kC setter initialized the kC parameter and returns the object
     * 
     * @param kc is the kC value
     * @return the object with the initialized kC value
     */
    public PointLight setKc(double kc) {
        kC = kc;
        return this;
    }

    /**
     * kL setter initialized the kL parameter and returns the object
     * 
     * @param kl is the kL value
     * @return the object with the initialized kL value
     */
    public PointLight setKl(double kl) {
        kL = kl;
        return this;
    }

    /**
     * kQ setter initialized the kQ parameter and returns the object
     * 
     * @param kq is the kQ value
     * @return the object with the initialized kQ value
     */
    public PointLight setKq(double kq) {
        kQ = kq;
        return this;
    }

    @Override
    public double getDistance(Point p) {
        return p.distance(position);
    }
}