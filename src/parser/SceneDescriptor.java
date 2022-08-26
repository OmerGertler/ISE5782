package parser;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lighting.AmbientLight;
import primitives.Color;
import primitives.*;
import geometries.*;
import scene.Scene;

/**
 * This class is used to store the information of the scene by json format.
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class SceneDescriptor {
    Scene scene;

    /**
     * Scene default constructor
     * 
     * @param jsonObject the json object
     */
    public SceneDescriptor(JSONObject jsonObject) {
        scene = new Scene("");
        parse(jsonObject);
    }

    /**
     * getter of the scene
     * 
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * This function get json object and parse it to the scene.
     * 
     * @param jsonObject - the json object to parse
     * @return the scene
     */
    public void parse(JSONObject jsonObject) {
        // get the scene
        JSONObject sceneObject = (JSONObject) jsonObject.get("scene");
        // get the background color
        String backgroundColor = (String) sceneObject.get("background-color");
        scene.setBackground(getColor(backgroundColor));
        // get the ambient light
        JSONObject ambientLightObject = (JSONObject) sceneObject.get("ambient-light");
        scene.setAmbientLight(getAmbientLight(ambientLightObject));
        // get the geometries
        JSONArray geometriesArray = (JSONArray) sceneObject.get("geometries");
        scene.setGeometries(getGeometries(geometriesArray));

    }

    /**
     * This function get the color String and return the color.
     * 
     * @param color the color string to parse
     * @return the Color object
     */
    public Color getColor(String color) {
        String[] rgb = color.split(" ");
        return new Color(Double.parseDouble(rgb[0]), Double.parseDouble(rgb[1]), Double.parseDouble(rgb[2]));
    }

    /**
     * This function get the point String and return the point.
     * 
     * @param point the point string to parse
     * @return the Point object
     */
    public Point getPoint(String point) {
        String[] pointArray = point.split(" ");
        return new Point(Double.parseDouble(pointArray[0]), Double.parseDouble(pointArray[1]),
                Double.parseDouble(pointArray[2]));
    }

    /**
     * This function get the vector String and return the vector.
     * 
     * @param vector the vector string to parse
     * @return the Vector object
     */
    public Vector getVector(String vector) {
        String[] vectorArray = vector.split(" ");
        return new Vector(Double.parseDouble(vectorArray[0]), Double.parseDouble(vectorArray[1]),
                Double.parseDouble(vectorArray[2]));
    }

    /**
     * This function get the double3 string and return the double3.
     * 
     * @param double3 the double3 string to parse
     * @return the double3 object
     */
    public Double3 getDouble3(String double3) {
        String[] double3Array = double3.split(" ");
        return new Double3(Double.parseDouble(double3Array[0]), Double.parseDouble(double3Array[1]),
                Double.parseDouble(double3Array[2]));
    }

    /**
     * This function get the ambient light String and return the ambient-light.
     * 
     * @param ambientLight the json object to parse
     * @return the scene object
     */
    public AmbientLight getAmbientLight(JSONObject ambientLight) {
        // get the ambient light color
        String ambientLightColorString = (String) ambientLight.get("color");
        Color ambientLightColor = getColor(ambientLightColorString);
        // get the ambient light k
        String k = (String) ambientLight.get("k");
        Double3 kAmbientLightDouble = getDouble3(k);
        // return the ambient light
        return new AmbientLight(ambientLightColor, kAmbientLightDouble);
    }

    /**
     * THis function get the geometries array and return the geometries.
     * 
     * @param geometriesArray the geometries array to parse
     * @return the geometries object
     */
    @SuppressWarnings("unchecked")
    public Geometries getGeometries(JSONArray geometriesArray) {
        Geometries geometries = new Geometries();
        Iterator<JSONObject> iterator = geometriesArray.iterator();
        // iterate over the geometries array and add the geometries to the geometries
        // object
        while (iterator.hasNext()) {
            JSONObject geometryObject = iterator.next();
            // check if the geometry is a sphere or a triangle
            if (geometryObject.get("type").equals("sphere")) {
                // get and add the sphere

                geometries.add(getSphere((JSONObject) geometryObject.get("sphere")));
            }
            if (geometryObject.get("type").equals("triangle")) {
                // get and add the triangle
                geometries.add(getTriangle((JSONObject) geometryObject.get("triangle")));
            }
        }
        return geometries;
    }

    /**
     * this function get the sphere object and return the sphere.
     * 
     * @param sphereObject the sphere object to parse
     * @return the sphere object
     */
    public Sphere getSphere(JSONObject sphereObject) {
        // get the center of the sphere
        String centerString = (String) sphereObject.get("center");
        Point center = getPoint(centerString);
        // get the radius of the sphere
        String radiusString = (String) sphereObject.get("radius");
        double radius = Double.parseDouble(radiusString);
        // return the sphere
        return new Sphere(center, radius);
    }

    /**
     * This function get the triangle object and return the triangle.
     * 
     * @param triangleObject the triangle object to parse
     * @return the triangle object
     */
    public Triangle getTriangle(JSONObject triangleObject) {
        // get the points of the triangle
        String point1String = (String) triangleObject.get("point1");
        Point point1 = getPoint(point1String);
        String point2String = (String) triangleObject.get("point2");
        Point point2 = getPoint(point2String);
        String point3String = (String) triangleObject.get("point3");
        Point point3 = getPoint(point3String);
        // return the triangle
        return new Triangle(point1, point2, point3);
    }

}
