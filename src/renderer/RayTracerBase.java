package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class to find the intersections of ray with the scene
 * 
 * @author Noam Karmon & Omer Gertler
 */
public abstract class RayTracerBase {
    protected final Scene scene;

    /**
     * RayTracerBase constructor
     * 
     * @param scene the scene to find the intersections of the ray with the scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;

    }

    /**
     * Abstract trace ray function
     * 
     * @param ray the ray to trace
     * @return the color of the intersection
     */
    abstract Color traceRay(Ray ray);

}
