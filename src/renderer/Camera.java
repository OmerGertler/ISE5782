package renderer;

import primitives.*;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Camera class for rendering a scene with a given camera and image size
 * 
 * @author Noam Karmon & Omer Gertler
 */
public class Camera {

    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    private int pixelSize = 4; // the size of the pixel for AA calculation
    private boolean activateAA = true; // active super sampling (SS) if true
    private boolean activeASS = true; // active adaptive super sampling (ASS) if false
    private int recLevelForASS = 4; // recursion level for super sampling

    private int threadsCount = 0;
    private double printInterval;

    // --------- fields DOF -------
    private boolean depthOfField = false;
    private double focalDistance = 0;
    private int numOfRays = 1;
    private double apertureSize = 1;

    // ***************** Constructors ********************** //
    /**
     * Constructor
     * 
     * @param p0  - the camera position
     * @param vUp - the vector camera's up direction
     * @param vTo - the vector camera's towards direction
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {

        if (!Util.isZero(vUp.dotProduct(vTo))) {
            throw (new IllegalArgumentException("ERROR: The vectors must be vertical"));
        }
        this.vRight = vTo.crossProduct(vUp).normalize();
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
    }

    // #region Getters/Setters
    // ***************** Getters/Setters ********************** //
    /**
     * Getter for the camera position {@link Point}
     * 
     * @return the camera position
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter of the camera's up direction {@link Vector}
     * 
     * @return camera's up direction vector
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Getter of the camera's towards direction {@link Vector}
     * 
     * @return camera's towards direction vector
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * Getter of the camera's right direction {@link Vector}
     * 
     * @return camera's right direction vector
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * Getter of the width of the image
     * 
     * @return the width of the image
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter of the width of the image
     * 
     * @return the height of the image
     */
    public double getHeight() {
        return height;
    }

    /**
     * Getter of the distance from the camera to the image plane
     * 
     * @return the distance from the camera to the image plane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * This function return the camera after set the new view plane
     * 
     * @param width  - the new width of the image
     * @param height - the new height of the image
     * 
     * @return the camera after set the new view plane
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * This function return the camera after set a new distance
     * 
     * @param distance - the new distance
     */
    public Camera setVPDistance(double distance) {
        if (Util.isZero(distance)) {
            throw new IllegalArgumentException("ERROR: The distance cannot be zero");
        }
        this.distance = distance;
        return this;
    }

    /**
     * The setter initialize rendering-progress printing time interval in seconds
     *
     * @param interval - the interval of printing
     * @return This Camera object
     */
    public Camera setDebugPrint(double printInterval) {
        this.printInterval = printInterval;
        return this;
    }

    /**
     * Set multi threading functionality for accelerating the rendering speed.
     * Initialize the number of threads.
     * The defaultive value is 0 (no threads).
     * The recommended value for the multithreading is 3.
     *
     * @param threads, the threads amount
     * @return This Camera object
     */
    public Camera setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("threads parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        return this;
    }

    /**
     * set level of recursion for adaptive super sampling.
     * default value is 3 (recommended).
     * 
     * @param recLevel - level of recursion
     * @return this camera
     */
    public Camera setAssRecLevel(int recLevel) {
        if (recLevel < 0)
            throw new IllegalArgumentException("recLevel parameter must be 0 or higher");
        this.recLevelForASS = recLevel;
        return this;
    }

    /**
     * set on/off adaptive super sampling.
     * default value is true (ASS on).
     * 
     * @param active - true/false
     * @return this camera
     */
    public Camera setActiveASS(boolean activeASS) {
        this.activeASS = activeASS;
        return this;
    }

    // #endregion

    /**
     * This function return a ray from the camera to the point (x,y) on the view
     * plane
     * 
     * @param nX - the x coordinate on the view plane (width)
     * @param nY - the y coordinate on the view plane (height)
     * @param j  - the j coordinate on the Pixel (height)
     * @param i  - the i coordinate on the pixel (width)
     * @return a ray from the camera to the point (x,y) on the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        // the point from the camera to the point (x,y) on the view plane
        Point pij = pixelCenter(nX, nY, j, i);

        // Vij is the vector from the camera to the point (x,y) on the view
        Vector vij = pij.subtract(p0);

        // the ray from the camera to the point (x,y) on the view plane
        return new Ray(p0, vij);
    }

    /**
     * Image writer setter and return the camera
     * 
     * @param imageWriter - the image writer
     * @return the camera after set the image writer
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Ray tracer setter and return the camera
     * 
     * @param rayTracerBase - the ray tracer
     * @return the camera after set the ray tracer
     */
    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * This function checks if all the parameters are valid for the camera
     */
    public Camera renderImage() {
        if (p0 == null)
            throw new MissingResourceException("ERROR: The camera position is null", "Camera", "p0");
        if (vUp == null)
            throw new MissingResourceException("ERROR: The camera up direction is null", "Camera", "vUp");
        if (vTo == null)
            throw new MissingResourceException("ERROR: The camera towards direction is null", "Camera", "vTo");
        if (vRight == null)
            throw new MissingResourceException("ERROR: The camera right direction is null", "Camera", "vRight");
        if (width == 0)
            throw new MissingResourceException("ERROR: The width of the image is zero", "Camera", "width");
        if (height == 0)
            throw new MissingResourceException("ERROR: The height of the image is zero", "Camera", "height");
        if (distance == 0)
            throw new MissingResourceException("ERROR: The distance from the camera to the image plane is zero",
                    "Camera", "distance");
        if (imageWriter == null)
            throw new MissingResourceException("ERROR: The image writer is null", "Camera", "imageWriter");
        if (rayTracerBase == null)
            throw new MissingResourceException("ERROR: The ray tracer base is null", "Camera", "rayTracerBase");

        int nY = imageWriter.getNy();
        int nX = imageWriter.getNx();

        // cast beam multi-threading
        if (threadsCount > 0) {
            Pixel.initialize(nY, nX, printInterval);
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                        if (activateAA)
                            castAABeam(nX, nY, pixel.col, pixel.row, pixelSize);
                        else
                            castRay(nX, nY, pixel.col, pixel.row);
                    }
                }).start();
            }
            Pixel.waitToFinish();
        }

        // cast beam without multi-threading
        else {
            if (!activateAA || pixelSize == 1)
                for (int i = 0; i < nY; i++)
                    for (int j = 0; j < nX; j++)
                        castRay(nX, nY, j, i);
            else
                for (int i = 0; i < nX; i++) {
                    for (int j = 0; j < nY; j++) {
                        castAABeam(nX, nY, j, i, pixelSize);
                    }
                }
        }
        return this;
    }

    // #region adaptive super sampling (ASS)

        /**
     * .
     * This function is calculating color of point on the geometry in the scene by
     * using ASS.
     * 
     * @param nX - the x coordinate on the view plane (width)
     * @param nY - the y coordinate on the view plane (height)
     * @param j  - the j coordinate on the Pixel (height)
     * @param i  - the i coordinate on the pixel (width)
     * @return color of point on the geometry in the scene
     */
    private Color adaptiveSS(int nX, int nY, int j, int i) {
        HashMap<Point, Color> pintColorDict = new HashMap<Point, Color>(); // dictionary for ASS
        Point pij = pixelCenter(nX, nY, j, i);
        double rX = width / nX / 2;
        double rY = height / nY / 2;

        Point leftDownPoint = pij.add(vRight.scale(-rX)).add(vUp.scale(-rY));
        Color leftDownColor = getPointColorFromDict(leftDownPoint, pintColorDict).get(leftDownPoint);

        Point rightDownPoint = pij.add(vRight.scale(rX)).add(vUp.scale(-rY));
        Color rightDownColor = getPointColorFromDict(rightDownPoint, pintColorDict).get(rightDownPoint);

        Point leftUpPoint = pij.add(vRight.scale(-rX)).add(vUp.scale(rY));
        Color leftUpColor = getPointColorFromDict(leftUpPoint, pintColorDict).get(leftUpPoint);

        Point rightUpPoint = pij.add(vRight.scale(rX)).add(vUp.scale(rY));
        Color rightUpColor = getPointColorFromDict(rightUpPoint, pintColorDict).get(rightUpPoint);

        return adaptiveSSHelper(1, pij, rX, rY, leftUpColor, leftDownColor, rightDownColor, rightUpColor, pintColorDict);
    }

    /**
     * This function is helping function of {@link #adaptiveSS(int, int, int, int)}
     * for
     * calculating color of point on the geometry in the scene by using ASS with
     * recursion.
     * 
     * @param recLevel       - the recursion level
     * @param pij            - the center point of the pixel
     * @param rX-            the width of the pixel
     * @param rY-            the height of the pixel
     * @param leftUpColor    - the color of the left up point
     * @param leftDownColor  - the color of the left down point
     * @param rightDownColor - the color of the right down point
     * @param rightUpColor   - the color of the right up point
     * @return color of point on the geometry in the scene
     */
    private Color adaptiveSSHelper(int recLevel, Point pij, double rX, double rY,
            Color leftUpColor, Color leftDownColor, Color rightDownColor, Color rightUpColor, HashMap<Point, Color> pintColorDict) {

        if (recLevel == recLevelForASS) {
            return rightDownColor.add(leftUpColor).add(rightUpColor).add(leftDownColor).reduce(4);
        }
        if (rightUpColor.equals(leftUpColor) && rightUpColor.equals(leftDownColor) && rightUpColor.equals(rightDownColor))
            return rightUpColor;
        else {
            Point rightPoint = pij.add(vRight.scale(rX)); 
            Color rightColor = getPointColorFromDict(rightPoint, pintColorDict).get(rightPoint);

            Point leftPoint = pij.add(vRight.scale(-rX)); 
            Color leftColor = getPointColorFromDict(leftPoint, pintColorDict).get(leftPoint);

            Point upPoint = pij.add(vUp.scale(rY)); 
            Color upColor = getPointColorFromDict(upPoint, pintColorDict).get(upPoint);

            Point downPoint = pij.add(vUp.scale(-rY)); 
            Color downColor = getPointColorFromDict(downPoint, pintColorDict).get(downPoint);

            Color centerColor = getPointColorFromDict(pij, pintColorDict).get(pij);

            rX /= 2;
            rY /= 2;
            leftUpColor = adaptiveSSHelper(recLevel + 1, pij.add(vUp.scale(rY / 2)).add(vRight.scale(-rX / 2)), //
                         rX, rY, leftUpColor, upColor, leftColor, centerColor, pintColorDict);
            rightUpColor = adaptiveSSHelper(recLevel + 1, pij.add(vUp.scale(rY / 2)).add(vRight.scale(rX / 2)), // 
                        rX, rY, upColor, rightUpColor, centerColor, leftColor, pintColorDict);
            leftDownColor = adaptiveSSHelper(recLevel + 1, pij.add(vUp.scale(-rY / 2)).add(vRight.scale(-rX / 2)), //
                        rX, rY, leftColor, centerColor, leftDownColor, downColor, pintColorDict);
            rightDownColor = adaptiveSSHelper(recLevel + 1, pij.add(vUp.scale(-rY / 2)).add(vRight.scale(rX / 2)), //
                        rX, rY, centerColor, rightColor, downColor, rightDownColor, pintColorDict);
            return rightDownColor.add(leftUpColor).add(rightUpColor).add(leftDownColor).reduce(4);
        }
    }

    /**
     * Helping function, return the color of the point on the geometry in the scene.
     * If the point's color is not in the dictionary, calculate the color and add it
     * to the dictionary.
     * 
     * @param point - the point on the geometry in the scene
     * @return the color of the point on the geometry in the scene
     */
    private HashMap<Point, Color> getPointColorFromDict(Point point, HashMap<Point, Color> pointColorDict) {
        if (!(pointColorDict.containsKey(point))) 
            pointColorDict.put(point, rayTracerBase.traceRay(new Ray(p0, point.subtract(p0))));
        return pointColorDict;
    }

    // #endregion

    // #region beam for AA

    /**
     * This function return a beam (list of rays) from the camera to a point (x,y)
     * on the view plane.
     * The amount of the rays is pixelSize * pixelSize.
     * The rays are casted to a random point on the pixel.
     *
     * @param nX        - the x coordinate on the view plane (width)
     * @param nY        - the y coordinate on the view plane (height)
     * @param j         - the j coordinate on the Pixel (height)
     * @param i         - the i coordinate on the pixel (width)
     * @param pixelSize - the size of the pixel
     * @return a ray from the camera to the point (x,y) on the view plane
     */
    public LinkedList<Ray> constructBeam(int nX, int nY, int j, int i, double pixelSize) {

        // the point from the camera to the point (x,y) on the view plane
        Point pij = pixelCenter(nX, nY, j, i);

        double ry = height / nY;
        double rx = width / nX;

        var rayList = new LinkedList<Ray>();
        rayList.add(constructRay(nX, nY, j, i)); // The main ray

        Point pixStart = pij.add(vRight.scale(-rx / 2)).add(vUp.scale(ry / 2)); // up left corner of pixel

        for (double row = 0; row < pixelSize; row++) {
            for (double col = 0; col < pixelSize; col++) {
                rayList.add(randomPointRay(pixStart, col / pixelSize, -row / pixelSize));
            }
        }
        return rayList;
    }

    /**
     * This function calculate ad return the center pixel
     * 
     * @param nX        - the x coordinate on the view plane (width)
     * @param nY        - the y coordinate on the view plane (height)
     * @param j         - the j coordinate on the Pixel (height)
     * @param i         - the i coordinate on the pixel (width)
     * @param pixelSize - the size of the pixel
     * @return the center pixel
     */
    private Point pixelCenter(int nX, int nY, int j, int i) {
        // the point from the camera to the point (x,y) on the view plane
        Point pc = p0.add(vTo.scale(distance));

        double ry = height / nY;
        double rx = width / nX;

        double yi = -1 * (i - alignZero(((nY - 1) / 2d))) * ry;
        double xj = (j - alignZero((nX - 1) / 2d)) * rx;

        Point pij = pc;

        if (!Util.isZero(xj))
            pij = pij.add(vRight.scale(xj));

        if (!Util.isZero(yi))
            pij = pij.add(vUp.scale(yi));
        return pij;
    }

    /**
     * This function return aRay from the camera to a random point on the pixel.
     * the pixel is divided by row and col.
     * The new point is between the original point and the col and row values
     * 
     * @param pixStart, the original point on the pixel
     * @param col,      the col coordinate on the pixel
     * @param row,      the row coordinate on the pixel
     * @return ray from the camera to a random point on the pixel
     */
    private Ray randomPointRay(Point pixStart, double col, double row) {
        Point point = pixStart;
        if (!isZero(col)) // only move on X axis
            point = point.add(vRight.scale(random(0, col)));
        if (!isZero(row)) // only move on Y axis
            point = point.add(vUp.scale(random(row, 0)));

        return new Ray(p0, point.subtract(p0));
    }

    /***
     * Casting beam of rays from the camera in order to color a pixel.
     * The result is average of the colors of the rays in the beam.
     * 
     * @param nX        resolution on X axis (number of pixels in row)
     * @param nY        resolution on Y axis (number of pixels in column)
     * @param col       pixel's column number (pixel index in row)
     * @param row       pixel's row number (pixel index in column)
     * @param pixelSize - the size of the pixel
     */
    private void castAABeam(int nX, int nY, int col, int row, int pixelSize) {
        Color color = Color.BLACK;
        List<Ray> beam = constructBeam(nX, nY, col, row, pixelSize);
        if (activeASS) {
            color = adaptiveSS(nX, nY, col, row);
            imageWriter.writePixel(col, row, color);
        } else {
            beam.addAll(constructRays(nX, nY, col, row));
            for (Ray ray : beam)
                color = color.add(this.rayTracerBase.traceRay(ray));
            imageWriter.writePixel(col, row, color.reduce(beam.size()));
        }
    }

    /**
     * setter of activateAA field, true if activate or false if not
     * 
     * @param activate boolean value
     * @return this
     */
    public Camera setActivateAA(boolean activate) {
        activateAA = activate;
        return this;
    }

    /**
     * Setter of the pixelSize field (the size of the pixel)
     * 
     * @param size of the pixel
     * @return the camera
     */
    public Camera setPixelSize(int size) {
        pixelSize = size;
        return this;
    }

    // #endregion

    /**
     * Cast ray from camera in order to color a pixel
     * 
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {

        List<Ray> rays = this.constructRays(nX, nY, col, row);

        Color color = Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(this.rayTracerBase.traceRay(ray));
        }
        if (rays.size() > 1)
            imageWriter.writePixel(col, row, color.reduce(rays.size()));
        else
            imageWriter.writePixel(col, row, color);
    }

    /**
     * Create grid of lines to draw the view plane
     * 
     * @param interval - the interval between the lines
     * @param color    - the color of the lines
     * @throws MissingResourceException - if the image writer is null
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) {
            throw new MissingResourceException("ERROR: The image writer is null", "Camera", "imageWriter");
        }

        int ny = imageWriter.getNy();
        int nx = imageWriter.getNx();
        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Activates the appropriate image maker's method
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("ERROR: The image writer is null", "Camera", "imageWriter");
        }
        imageWriter.writeToImage();
    }

    /**
     * This function set new camera position with rotation
     * 
     * @param newPosition - the new position
     * @param target      - the target point
     * @param angle       - the angle of rotation
     * @return the camera after set the new position
     */
    public Camera cameraPosition(Point newPosition, Point target, double angle) {
        p0 = newPosition;
        vTo = target.subtract(newPosition).normalize();
        try {
            vRight = vTo.crossProduct(Vector.Y).normalize();
            vUp = vTo.crossProduct(vRight).normalize();

        } catch (IllegalArgumentException e) {
            vUp = Vector.Z;
            vRight = vTo.crossProduct(vUp).normalize();
        }
        return angle == 0 ? this : rotateCamera(angle);
    }

    /**
     * This function set new camera position with rotation
     * 
     * @param angle - the angle of rotation
     * @return the camera after set the new position
     */
    public Camera rotateCamera(double angle) {
        angle = Math.toRadians(angle);
        vUp = vUp.vectorRotate(vTo, angle);
        vRight = vUp.crossProduct(vTo).normalize();
        return this;
    }

    // --------- functions DOF -------
    // #region DOF

    /**
     * check if the camera is in depth of field and return the correct number of
     * rays
     * 
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     * @return
     */
    public List<Ray> constructRays(int nX, int nY, int col, int row) {
        return depthOfField ? constructRaysDof(nX, nY, col, row)
                : List.of(constructRay(nX, nY, col, row));
    }

    /**
     * This function set the depth of field parameters
     * 
     * @param focalDistance - the distance of the focus.
     * @param apertureSize  - the radius of the aperture.
     * @param numOfRays     - number of rays that will be in the beam from every
     *                      pixels area (in addition to the original ray).
     */
    public Camera setDepthOfFiled(double focalDistance, double apertureSize, int numOfRays) {
        this.focalDistance = focalDistance;
        this.apertureSize = apertureSize;
        this.numOfRays = numOfRays;
        return this;
    }

    /**
     * Set boolean to false or true to disable depth of field
     * 
     * @param depthOfField - boolean to disable depth of field
     */
    public Camera setDepthOfFiled(boolean depthOfField) {
        this.depthOfField = depthOfField;
        return this;
    }

    /**
     * this function gets the view plane size and a selected pixel,
     * and return the rays from the view plane which intersects the focal plane
     *
     * @param nX - amount of columns in view plane (number of pixels)
     * @param nY - amount of rows in view plane (number of pixels)
     * @param j  - X's index
     * @param i  - Y's index
     * @return - the list of rays which goes from the pixel through the focal plane
     */
    public List<Ray> constructRaysDof(int nX, int nY, int j, int i) {
        // the returned list of rays
        List<Ray> rays = new ArrayList<>();
        // add the center ray to the list
        Ray centerRay = constructRay(nX, nY, j, i);
        rays.add(centerRay);
        // calculate the actual size of a pixel
        // pixel height is the division of the view plane height in the number of rows
        // of pixels
        double pixelHeight = alignZero(height / nY); // Ry = h/Ny
        // pixel width is the division of the view plane width in the number of columns
        // of pixels
        double pixelWidth = alignZero(width / nX); // Rx = w/Nx
        // if more then one ray is emitted (DOF effect)
        if (numOfRays != 1) {
            List<Ray> tempRays = new LinkedList<>();
            // apertureSize is the value of how many pixels it spreads on
            double apertureRadius = Math.sqrt(apertureSize * (pixelHeight * pixelWidth)) / 2d;
            for (Ray ray : rays) {
                // creating list of focal rays
                tempRays.addAll(
                        // ray.randomRaysInCircle(ray.getP0(), vUp, vRight, apertureRadius, numOfRays,
                        // focalDistance));
                        ray.raysInGrid(ray.getP0(), vUp, vRight, apertureRadius, numOfRays,
                                focalDistance));
            }
            // the original rays included in the temp rays
            rays = tempRays;
        }
        return rays;
    }
    // #endregion

}
