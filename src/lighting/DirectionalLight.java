package lighting;

import primitives.*;

/**
 * DirectionalLight class defined directional light sources like sun for example
 * Teh class inherits {@link Light} and implements {@link LightSource}
 */
public class DirectionalLight extends Light implements LightSource {

    private final Vector direction;

    /**
     * DirectionalLight constructor
     * initialize direction direction of the light source and the intensity
     * 
     * @param color of the intensity
     */
    public DirectionalLight(Color intensity, Vector dir) {
        super(intensity);
        this.direction = dir.normalize();
    }

    /**
     * Get light intensity at a {@link Point} in the scene
     * 
     * @param p the observed point
     */
    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    /**
     * Get the light direction {@link Vector} from the light-source to a
     * 
     * @param p the observed point in the scene
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    /**
     * getDistance method returns the distance between the light source and the
     * {@link Point} in the scene
     * 
     * @param p
     * @return the distance
     */
    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }

}
