package scene;

import java.util.LinkedList;
import java.util.List;

import geometries.*;
import lighting.*;
import primitives.*;

/**
 * Scene class defined the background color and the 3D shapes.
 * Including all details of the scene: the name of the scene,
 * the geometries and light sources of the scene, 
 * as well as the background color and the ambient light.
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Scene default constructor, initialize the name field
     * @param name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Setter of the background color of the scene
     * 
     * @param color of the scene background
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter of the ambient light of the scene
     * 
     * @param ambientLight of the scene
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter of the geometries list of the scene
     * 
     * @param geometries, the scene's geometries list
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Setter of lights, the light-sources list of the scene
     * 
     * @param lights list of light sources
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
