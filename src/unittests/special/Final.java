package unittests.special;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.*;
import static java.awt.Color.*;


public class Final {
    private final ImageWriter imageWriter = new ImageWriter("Final", 800, 800);

    private final Scene scene = new Scene("Final scene");

    //colors:
    private static final Color purple = new Color(155, 94, 127);
    private static final Color sand = new Color(245, 219, 126);
    private static final Color brightGreen = new Color(178, 219, 126);
    private static final Color darkRed = new Color(82, 4, 3);
    private static final Color topCol = new Color(157, 136, 140);

    //materials:
	private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);
	private static final Material windowMat = new Material().setKd(0.4).setKs(0.5).setKt(0.7).setShininess(20);

         // under windows:
    Polygon s1 = new Polygon(new Point(0, 0, 1), new Point(100, 0, 1), new Point(100, 0, 25), new Point(0, 0, 25));
    Polygon s2 = new Polygon(new Point(0, 0, 1), new Point(0, 100, 1), new Point(0, 100, 25), new Point(0, 0, 25));
    Polygon s3 = new Polygon(new Point(100, 100, 1), new Point(100, 0, 1), new Point(100, 0, 25), new Point(100, 100, 25));
    Polygon s4 = new Polygon(new Point(100, 100, 1), new Point(0, 100, 1), new Point(0, 100, 25), new Point(100, 100, 25));

    // above window:
    Polygon s5 = new Polygon(new Point(0, 0, 50), new Point(100, 0, 50), new Point(100, 0, 100), new Point(0, 0, 100));
    Polygon s6 = new Polygon(new Point(0, 0, 50), new Point(0, 100, 50), new Point(0, 100, 100), new Point(0, 0, 100));
    Polygon s7 = new Polygon(new Point(100, 100, 50), new Point(100, 0, 50), new Point(100, 0, 100), new Point(100, 100, 100));
    Polygon s8 = new Polygon(new Point(100, 100, 50), new Point(0, 100, 50), new Point(0, 100, 100),new Point(100, 100, 100));

    // window level:
    Polygon s9 = new Polygon(new Point(0, 0, 25), new Point(25, 0, 25), new Point(25, 0, 50), new Point(0, 0, 50));
    Polygon s10 = new Polygon(new Point(0, 0, 25), new Point(0, 25, 25), new Point(0, 25, 50), new Point(0, 0, 50));
    Polygon s11 = new Polygon(new Point(75, 0, 25), new Point(100, 0, 25), new Point(100, 0, 50), new Point(75, 0, 50));
    Polygon s12 = new Polygon(new Point(0, 75, 25), new Point(0, 100, 25), new Point(0, 100, 50), new Point(0, 75, 50));

    Polygon s13 = new Polygon(new Point(0, 100, 25), new Point(25, 100, 25), new Point(25, 100, 50), new Point(0, 100, 50));
    Polygon s14 = new Polygon(new Point(75, 100, 25), new Point(100, 100, 25), new Point(100, 100, 50), new Point(75, 100, 50));
    Polygon s15 = new Polygon(new Point(100, 25, 25), new Point(100, 0, 25), new Point(100, 0, 50), new Point(100, 25, 50));
    Polygon s16 = new Polygon(new Point(100, 75, 25), new Point(100, 100, 25), new Point(100, 100, 50), new Point(100, 75, 50));

    //top:
    Polygon s17 = new Polygon(new Point(0, 0, 100), new Point(100, 0, 100), new Point(100, 25, 100), new Point(0, 25, 100));
    Polygon s18 = new Polygon(new Point(0, 25, 100), new Point(0, 75, 100), new Point(25, 75, 100), new Point(25, 25, 100));
    Polygon s19 = new Polygon(new Point(75, 25, 100), new Point(100, 25, 100), new Point(100, 75, 100), new Point(75, 75, 100));
    Polygon s20 = new Polygon(new Point(100, 100, 100), new Point(0, 100, 100), new Point(0, 75, 100), new Point(100, 75, 100));

    //bottom:
    Polygon s21 = new Polygon(new Point(0, 0, 1), new Point(0, 100, 1), new Point(100, 100, 1), new Point(100, 0, 1));

    //top window:
    Polygon s22 = new Polygon(new Point(75, 25, 100), new Point(75, 75, 100), new Point(25, 75, 100), new Point(25, 25, 100));

    @Test
    public void finalTest(){
    //bird and flower:
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
    //flower:
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

    scene.geometries.add(new Plane(new Vector(0, 0, -1), new Point(0, 0, -100)).setEmission(new Color(DARK_GRAY)) //
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(20).setKt(0.5)), //
                            s1.setEmission(purple).setMaterial(mat), //
                             s2.setEmission(sand).setMaterial(mat), //
                             s3.setEmission(brightGreen).setMaterial(mat), //
                              s4.setEmission(darkRed).setMaterial(mat), //
                              s5.setEmission(purple).setMaterial(mat), //
                              s6.setEmission(sand).setMaterial(mat), //
                              s7.setEmission(brightGreen).setMaterial(mat), //
                              s8.setEmission(darkRed).setMaterial(mat), //
                              s9.setEmission(purple).setMaterial(mat), //
                              s10.setEmission(purple).setMaterial(mat), //
                              s11.setEmission(sand).setMaterial(mat), //
                              s12.setEmission(sand).setMaterial(mat), //
                              s13.setEmission(brightGreen).setMaterial(mat), //
                              s14.setEmission(brightGreen).setMaterial(mat), //
                              s15.setEmission(darkRed).setMaterial(mat), //
                              s16.setEmission(darkRed).setMaterial(mat), //
                              s17.setEmission(topCol).setMaterial(mat), //
                              s18.setEmission(topCol).setMaterial(mat), //
                              s19.setEmission(topCol).setMaterial(mat), //
                              s20.setEmission(topCol).setMaterial(mat), //
                              s21.setEmission(new Color(BLACK)).setMaterial(mat), //
                              s22.setEmission(new Color(RED)).setMaterial(windowMat)
                              );

        scene.lights.add(new PointLight(new Color(500, 500, 0), new Point(50, 50, 5)).setKl(4E-5).setKq(2E-7)); 
	scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
			.setKl(4E-5).setKq(2E-7));
	scene.lights.add(new SpotLight(new Color(600, 300, 300), new Point(-100, -100, 0), new Vector(-1, -1, -1)) //
			.setKl(4E-5).setKq(2E-7));

        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0)) //
        .setVPDistance(800).setVPSize(200, 200) //
        .setImageWriter(imageWriter).cameraPosition(new Point(-1000, 1500, 1000), new Point(0, 0, -50), 0) //
        .setMultithreading(3).setDebugPrint(0.1);

        camera.setActivateAA(false).setActiveASS(false).setDepthOfFiled(false);

        ImageWriter imageWriter = new ImageWriter("1", 800, 800);
		camera.setImageWriter(imageWriter).cameraPosition(new Point(-1000, 1000, 500), new Point(0, 0, -50), -45) //
				 .setMultithreading(3).setDebugPrint(0.1) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage();
		camera.writeToImage();

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.2)));
        ImageWriter imageWriterRotated = new ImageWriter("2", 800, 800);
                camera.setImageWriter(imageWriterRotated) //
                        .setMultithreading(3).setDebugPrint(0.1) //
                        .setRayTracer(new RayTracerBasic(scene)) //
                        .setDepthOfFiled(true) //
                        .setDepthOfFiled(700, 75, 20) //
                        .renderImage();
                camera.writeToImage();

	imageWriterRotated = new ImageWriter("3", 800, 800);
	camera.setImageWriter(imageWriterRotated) //
		.setMultithreading(3).setDebugPrint(0.1) //
		.setRayTracer(new RayTracerBasic(scene)) //
                .setDepthOfFiled(false) //
		.cameraPosition(new Point(1000, -1500, 1000), new Point(0, 0, -50), 90) //
                .setActivateAA(true).setActiveASS(true).setAssRecLevel(11) //
		.renderImage();
	camera.writeToImage();
    }

}
