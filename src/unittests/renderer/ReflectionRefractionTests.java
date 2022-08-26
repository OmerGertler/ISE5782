/**
 * 
 */
package unittests.renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene))
				.renderImage().writeToImage();

	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 0,
						100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 20,
						20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //

				.renderImage(); //
		camera.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);//.setMultithreading(3).setDebugPrint(0.1);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}

	// *****************our picture for Tatgil7****************

	// picture of flower and bird + camera rotation
	@Test
	public void ourPicture() {
		Camera camera = new Camera(new Point(0, 0, 1500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1500).setMultithreading(3).setDebugPrint(0.1);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Plane(new Vector(0, 0, -1), new Point(0, 0, -100)).setEmission(new Color(DARK_GRAY)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(20).setKt(0.5)),
				new Triangle(new Point(0, -20, -105), new Point(130, -50, -125), new Point(60, 30, -50)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
				new Triangle(new Point(-20, 100, -105), new Point(150, 130, -125), new Point(60, 65, -50)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
				new Triangle(new Point(40, 40, -50), new Point(40, 60, -50), new Point(0, 50, -50)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(85, 50, -50), new Point(95, 50, -50), new Point(105, 70, -70)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
						.setEmission(new Color(GREEN)), //
				new Triangle(new Point(85, 50, -50), new Point(95, 50, -50), new Point(105, 30, -70)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
						.setEmission(new Color(GREEN)), //
				new Sphere(new Point(60, 50, -50), 15d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)), //
				new Sphere(new Point(60, 50, -50), 25d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.2)),
				new Sphere(new Point(-50, -50, -50), 10d).setEmission(new Color(YELLOW)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.5)),
				new Sphere(new Point(-50, -70, -50), 10d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.5)),
				new Sphere(new Point(-50, -30, -50), 10d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.5)),
				new Sphere(new Point(-66, -60, -50), 10d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.5)),
				new Sphere(new Point(-66, -40, -50), 10d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.5)),
				new Sphere(new Point(-34, -60, -50), 10d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.5)),
				new Sphere(new Point(-34, -40, -50), 10d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.5)),
				new Triangle(new Point(-50, -50, -75), new Point(-88, -65, -75), new Point(-100, -60, -75)) //
						.setMaterial(new Material().setKd(0.5).setKd(0.5).setShininess(40)) //
						.setEmission(new Color(GREEN)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));
		scene.lights.add(new SpotLight(new Color(600, 300, 300), new Point(-100, -100, 0), new Vector(-1, -1, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("birdAndFlower", 800, 800);
		camera.setImageWriter(imageWriter) //
				 .setMultithreading(3).setDebugPrint(0.1) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage();
		camera.writeToImage();

		ImageWriter imageWriterRotated = new ImageWriter("birdAndFlowerRot", 800, 800);
		camera.setImageWriter(imageWriterRotated) //
				 .setMultithreading(3).setDebugPrint(0.1) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.cameraPosition(new Point(-1500, 1000, 1000), new Point(0, 0, -50), 270)
				.renderImage();
		camera.writeToImage();

		imageWriterRotated = new ImageWriter("birdAndFlowerRot2", 800, 800);
		camera.setImageWriter(imageWriterRotated) //
			 .setMultithreading(3).setDebugPrint(0.1) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.cameraPosition(new Point(1000, -1500, 1000), new Point(0, 0, -50), 90)
				.renderImage();
		camera.writeToImage();
	}

}
