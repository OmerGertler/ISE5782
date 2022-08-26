package unittests.renderer;

import org.junit.jupiter.api.Test;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image with depth of field effect
 * 
 * @author Noam Karmon
 */
public class DepthOfFieldTests {

        @Test
        public void Project() {
                Scene scene = new Scene("Test scene");
                Camera camera = new Camera(new Point(-120, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                                .setVPDistance(2000)
                                .setVPSize(1000, 1000)
                                .setDepthOfFiled(700, 100, 25);
                scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.2)));

                scene.geometries.add(

                                new Plane(new Vector(0, 5, 0), new Point(-200, 300, 0))
                                                .setEmission(new Color(100, 100, 100))//
                                                .setMaterial(new Material().setKd(1).setKs(1).setShininess(20)
                                                                .setKt(0.8))

                );
                for (int i = 1; i < 7; i++) {
                        scene.geometries.add(
                                        new Sphere(new Point(100 + -i * 100, 3, i * 200), 45) //
                                                        .setEmission(new Color(java.awt.Color.red))//
                                                        .setMaterial(new Material().setKd(0.5).setKs(0.5)
                                                                        .setShininess(30)), //

                                        new Sphere(new Point(100 + -i * 100, -70, i * 200), 30) //
                                                        .setEmission(new Color(java.awt.Color.BLUE)) //
                                                        .setMaterial(new Material().setKd(0.5).setKs(0.5)
                                                                        .setShininess(30)), //

                                        new Sphere(new Point(100 + -i * 100, -115, i * 200), 15) //
                                                        .setEmission(new Color(java.awt.Color.green)) //
                                                        .setMaterial(new Material().setKd(0.5).setKs(0.5)
                                                                        .setShininess(30))//
                        );
                }

                scene.lights.add(new DirectionalLight(new Color(50, 50, 50), new Vector(50, 180, 600)));
                scene.lights.add(new DirectionalLight(new Color(20, 0, 20), new Vector(50, 180, 600)));

                ImageWriter imageWriterRotated = new ImageWriter("withDepthOfFieldProject1", 2000, 2000);
                camera.setImageWriter(imageWriterRotated) //
                                .setRayTracer(new RayTracerBasic(scene)) //
                                .setDepthOfFiled(true)
                                .renderImage(); //
                camera.writeToImage();

                imageWriterRotated = new ImageWriter("withDepthOfFieldProject2", 2000, 2000);
                camera.setImageWriter(imageWriterRotated) //
                                .setRayTracer(new RayTracerBasic(scene))
                                .setDepthOfFiled(800, 100, 25) //
                                .setDepthOfFiled(true)
                                .renderImage(); //
                camera.writeToImage();

                imageWriterRotated = new ImageWriter("withoutDepthOfFieldProject", 2000, 2000);
                camera.setImageWriter(imageWriterRotated) //
                                .setRayTracer(new RayTracerBasic(scene)) //
                                .setDepthOfFiled(false)
                                .renderImage(); //
                camera.writeToImage();

        }

        @Test
        public void miniProject1() {
                Scene scene = new Scene("Test scene");
                Camera camera = new Camera(new Point(150, -150, 90), new Vector(-130, 170, -90),
                                new Vector(1.3, 1, 1 / 90d)) //
                                .setVPSize(500, 500).setVPDistance(1000).setDepthOfFiled(300, 100, 20);
                ; //

                scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

                scene.geometries.add( //
                                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                                new Point(75, 75, -150)) //
                                                .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140),
                                                new Point(75, 75, -150)) //
                                                .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                                new Sphere(new Point(30, 30, 0), 5) //
                                                .setEmission(new Color(java.awt.Color.BLUE)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                                new Sphere(new Point(60, 60, 0), 5) //
                                                .setEmission(new Color(java.awt.Color.BLUE)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Sphere(new Point(0, 0, 0), 5) //
                                                .setEmission(new Color(java.awt.Color.BLUE)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Sphere(new Point(-30, -30, 0), 5) //
                                                .setEmission(new Color(java.awt.Color.GRAY)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                                new Sphere(new Point(40, 40, 0), 5d) //
                                                .setEmission(new Color(java.awt.Color.RED)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                                new Sphere(new Point(70, 70, 0), 5d) //
                                                .setEmission(new Color(java.awt.Color.RED)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Sphere(new Point(10, 10, 0), 5d) //
                                                .setEmission(new Color(java.awt.Color.RED)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Sphere(new Point(-20, -20, 0), 5d) //
                                                .setEmission(new Color(java.awt.Color.RED)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                                new Sphere(new Point(35, 35, 10), 5d) //
                                                .setEmission(new Color(java.awt.Color.green)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                                new Sphere(new Point(65, 65, 10), 5d) //
                                                .setEmission(new Color(java.awt.Color.green)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Sphere(new Point(5, 5, 10), 5d) //
                                                .setEmission(new Color(java.awt.Color.green)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Sphere(new Point(-25, -25, 10), 5d) //
                                                .setEmission(new Color(java.awt.Color.green)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                                new Plane(new Point(2, 0, -5), new Point(-2, 0, -5), new Point(0, 2, -5)) //
                                                .setEmission(new Color(java.awt.Color.BLACK)) //
                                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));
                new Plane(new Point(-50, 0, -5), new Point(0, -50, -5), new Point(0, -5, 10)) //
                                .setEmission(new Color(java.awt.Color.ORANGE)) //
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));

                scene.lights.add( //
                                new SpotLight(new Color(700, 400, 400), new Point(-50, -50, 900), new Vector(1, 1, -5)) //
                                                .setKl(4E-4).setKq(2E-5));

                ImageWriter imageWriterRotated = new ImageWriter("aa", 1000, 1000);
                camera.setImageWriter(imageWriterRotated) //
                                .setRayTracer(new RayTracerBasic(scene)).setDepthOfFiled(true) //
                                .cameraPosition(new Point(20, 400, 80), new Point(30, 30, 5), 0)
                                .renderImage(); //
                camera.writeToImage();

                imageWriterRotated = new ImageWriter("aaa", 1000, 1000);
                camera.setImageWriter(imageWriterRotated) //
                                .setRayTracer(new RayTracerBasic(scene)).setDepthOfFiled(false) //
                                .cameraPosition(new Point(20, 400, 80), new Point(30, 30, 5), 0)
                                .renderImage(); //
                camera.writeToImage();

        }
}