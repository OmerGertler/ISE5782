package geometries;

import primitives.*;

/**
 * Geometry is abstract class that served the Geometric characters
 * The class extends {@link Intersectable} interface
 * Each Geometry has emission {@link Color} and unique {@link Material}
 * 
 * @author Noam Karmon & Omer Gertler
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Getter of the normal of a generic Geometry
     * 
     * @param point that may has normal vector of the geometry
     * @return normal of Geometry
     */
    public abstract Vector getNormal(Point point);

    /**
     * Getter of the emission color of the geometry
     * 
     * @return emission {@link Color}
     */
    public Color getEmission() {
        return this.emission;
    }

    /**
     * Setter of the emission color of the geometry
     * 
     * @return Geometry with updated emission color
     */
    public Geometry setEmission(Color color) {
        this.emission = color;
        return this;
    }

    /**
     * Getter of the material field of the {@link Geometry} object
     * 
     * @return the material of the geometry.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Setter of the material field of the {@link Geometry} object
     * 
     * @param material of the geometry.
     * @return teh geometry.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
