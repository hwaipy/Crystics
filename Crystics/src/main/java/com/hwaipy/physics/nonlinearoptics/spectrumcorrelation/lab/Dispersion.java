/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.lab;

import com.hwaipy.physics.crystaloptics.Mediums;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.CorrelationFunction;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.CorrelationPloter;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.HOMVisibilityCalculator;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.JointFunction;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.PumpFunction;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.QuasiPhaseMatchFunction;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 * @author Hwaipy 2015-3-19
 */
public class Dispersion {

  private static double minOmigaS = 1548.5;
  private static double maxOmigaS = 1551.5;
  private static double minOmigaI = 1548.5;
  private static double maxOmigaI = 1551.5;

  public static void main(String[] args) throws IOException {
    CorrelationFunction functionPump = new PumpFunction(775, 0.36);
    CorrelationFunction functionPhaseMatch = new QuasiPhaseMatchFunction(Mediums.KTiOPO4, 30, -45.04);
    CorrelationFunction functionJoin = new JointFunction(functionPhaseMatch, functionPump);
    plot(functionJoin, "filtered");
//    for (int i = 0; i <= 200; i++) {
//    for (int i = 0; i >= -200; i--) {
    for (int i = -200; i <= 200; i++) {
//    System.out.println(HOM_traditional(functionJoin, 2e-12 * 5));
//    double delta = 10 * Math.pow(0.96, i);
      double delta = 0.01145;
//      System.out.print(i + "\t" + (1.5 / 500 * i) + "\t");
      minOmigaS = 1550 - delta;
      maxOmigaS = 1550 + delta;
      minOmigaI = 1550 - delta;
      maxOmigaI = 1550 + delta;
//      plot(functionJoin, "filtered");
//      System.out.println(_HOM(functionJoin));
//    System.out.println(HOM_dispertion(functionJoin, 40e-12));
//      System.out.println((0.1 * i) + "     " + HOM_dispertion2(functionJoin, 0.1 * i, 2800e-24));
//      System.out.println((0.15 / 200 * i) + "\t" + HOM_dispertion3(functionJoin, 0.15 / 200 * i, 2800e-24));
//      System.out.println((0.15 / 200 * i) + "\t" + HOM_traditional(functionJoin, 0.15 / 200 * i * 0.78 * 2800e-12));
//    System.out.println(HOM_traditional(functionJoin, 5e-12));
      System.out.println((0.15 / 200 * i) + "\t" + direct(0.15 / 200 * i * 0.78 * 2800));
    }
  }

  private static void plot(CorrelationFunction function, String name) throws IOException {
    CorrelationPloter correlationPloter = new CorrelationPloter(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, 1000, 1000);
    correlationPloter.calculate();
    BufferedImage image = correlationPloter.createImage();
    Path path = Paths.get("./input-output/");
    ImageIO.write(image, "png", new File(path.toFile(), name + ".png"));
  }

  private static double _HOM(CorrelationFunction function) throws IOException {
    HOMVisibilityCalculator homCalculator = new HOMVisibilityCalculator(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, 200);
    double visibility = homCalculator.calculate();
//    System.out.println("HOM Visibility is " + visibility);
    return visibility;
  }

  private static double HOM_traditional(CorrelationFunction function, double delta) throws IOException {
    Integrator E = new TRADITIONAL.EIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, delta);
    Integrator A = new TRADITIONAL.AIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI);
    double e = E.integrate(0.0001);
    double a = A.integrate(0.0001);
    return e / a;
  }

  private static double HOM_dispertion(CorrelationFunction function, double delta) throws IOException {
    Integrator E = new DISPERSION.EIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, delta);
    Integrator A = new DISPERSION.AIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI);
    double e = E.integrate(0.0001);
    double a = A.integrate(0.0001);
//    System.out.println(delta + "\t" + (e / a));
    return e / a;
  }

  private static double HOM_dispertion2(CorrelationFunction function, double delta, double t02) throws IOException {
    Integrator E = new DISPERSION2.EIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, delta, t02);
    Integrator A = new DISPERSION2.AIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI);
    double e = E.integrate(0.0001);
    double a = A.integrate(0.0001);
//    System.out.println(delta + "\t" + (e / a));
    return e / a;
  }

  private static double HOM_dispertion3(CorrelationFunction function, double delta, double t02) throws IOException {
    Integrator E = new DISPERSION3.EIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, delta, t02);
    Integrator A = new DISPERSION3.AIntegrator(function, minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, delta);
    double e = E.integrate(0.0001);
    double a = A.integrate(0.0001);
//    System.out.println(delta + "\t" + (e / a));
    return e / a;
  }

  private static double direct(double deltaT) {
    return 2 * (1 - Math.cos(50 * deltaT / 3000)) / Math.pow(deltaT * 50 / 3000, 2);
  }

  private static class DISPERSION3 {

    private static class EIntegrator extends Integrator {

      private final double delta;
      private final double t02;

      public EIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2, double delta, double t02) {
        super(function, min1, max1, min2, max2);
        this.delta = delta;
        this.t02 = t02;
      }

      @Override
      protected double function() {
        double t3 = getNextRandom(min1, max1);
        double t4 = getNextRandom(min1, max1);
        double w2 = getNextRandom(min2, max2);
        double w2p = getNextRandom(min2, max2);
        double E2 = function.value((t4 + delta), w2) * function.value(t3, w2p) * function.value((t3 + delta), w2) * function.value(t4, w2p);
        double E3 = 0.78e12 * (t3 - t4) * t02 * 0.78e12 * delta;
//        double E = E2 * Math.cos(E3);
        double E = Math.cos(E3);
//        System.out.println("E: " + E);
        return E;
      }
    }

    private static class AIntegrator extends Integrator {

      private final double deltaT;

      public AIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2, double deltaT) {
        super(function, min1, max1, min2, max2);
        this.deltaT = deltaT;
      }

      @Override
      protected double function() {
        double t4 = getNextRandom(min1, max1);
        double t3 = getNextRandom(min1, max1);
        double w2 = getNextRandom(min2, max2);
        double w2p = getNextRandom(min2, max2);
        double result = function.value(t4 + deltaT, w2) * function.value(t3, w2p);
//        System.out.println("A: " + result * result);
        return result * result;
      }

    }
  }

  private static class DISPERSION2 {

    private static class EIntegrator extends Integrator {

      private final double delta;
      private final double t02;

      public EIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2, double delta, double t02) {
        super(function, min1, max1, min2, max2);
        this.delta = delta;
        this.t02 = t02;
      }

      @Override
      protected double function() {
        double t3 = getNextRandom(min1, max1);
        double t4 = getNextRandom(min1, max1);
        double w2 = getNextRandom(min2, max2);
        double w2p = getNextRandom(min2, max2);
        double E1 = Math.pow(function.value(t4, w2) * function.value(t3, w2p), 2) - Math.pow(function.value((t4 + delta), w2) * function.value(t3, w2p), 2);
        double E2 = function.value((t4 + delta), w2) * function.value(t3, w2p) * function.value((t3 + delta), w2) * function.value(t4, w2p);
        double E3 = 0.78e12 * (t3 - t4) * t02 * 0.78e12 * delta;
        double E = E1 + E2 * Math.cos(E3);
        System.out.println(E1);
        System.out.println(E2);
        System.out.println("E" + E);
        return E;
      }
    }

    private static class AIntegrator extends Integrator {

      public AIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2) {
        super(function, min1, max1, min2, max2);
      }

      @Override
      protected double function() {
        double r1 = getNextRandom(min1, max1);
        double r1p = getNextRandom(min1, max1);
        double r2 = getNextRandom(min2, max2);
        double r2p = getNextRandom(min2, max2);
        double result = function.value(r1, r2) * function.value(r1p, r2p);
        System.out.println("A" + (result * result));
        return result * result;
      }

    }
  }

  private static class DISPERSION {

    private static class EIntegrator extends Integrator {

      private final double delta;

      public EIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2, double delta) {
        super(function, min1, max1, min2, max2);
        this.delta = delta;
      }

      @Override
      protected double function() {
        double r1 = getNextRandom(min1, max1);
        double r1p = getNextRandom(min1, max1);
        double r2 = getNextRandom(min2, max2);
        double r2p = getNextRandom(min2, max2);
        return function.value(r1, r2) * function.value(r1 + delta, r2p) * function.value(r1p - delta, r2) * function.value(r1p, r2p);
      }

    }

    private static class AIntegrator extends Integrator {

      public AIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2) {
        super(function, min1, max1, min2, max2);
      }

      @Override
      protected double function() {
        double r1 = getNextRandom(min1, max1);
        double r1p = getNextRandom(min1, max1);
        double r2 = getNextRandom(min2, max2);
        double r2p = getNextRandom(min2, max2);
        double result = function.value(r1, r2) * function.value(r1p, r2p);
        return result * result;
      }

    }
  }

  private static class TRADITIONAL {

    private static class EIntegrator extends Integrator {

      private final double delta;

      public EIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2, double delta) {
        super(function, min1, max1, min2, max2);
        this.delta = delta;
      }

      @Override
      protected double function() {
        double r1 = getNextRandom(min1, max1);
        double r1p = getNextRandom(min1, max1);
        double r2 = getNextRandom(min2, max2);
        double r2p = getNextRandom(min2, max2);
        return function.value(r1, r2) * function.value(r1, r2p) * function.value(r1p, r2) * function.value(r1p, r2p)
                * cos(delta * (convert(r2) - convert(r2p)));
      }

      private double convert(double lambda) {
        lambda = lambda / 1e9;
        return 3e8 / lambda * 2 * Math.PI;
      }

      private double cos(double theta) {
        int d = (int) (theta / Math.PI);
        theta -= Math.PI * d;
        return Math.cos(theta);
      }

    }

    private static class AIntegrator extends Integrator {

      public AIntegrator(CorrelationFunction function, double min1, double max1, double min2, double max2) {
        super(function, min1, max1, min2, max2);
      }

      @Override
      protected double function() {
        double r1 = getNextRandom(min1, max1);
        double r1p = getNextRandom(min1, max1);
        double r2 = getNextRandom(min2, max2);
        double r2p = getNextRandom(min2, max2);
        double result = function.value(r1, r2) * function.value(r1p, r2p);
        return result * result;
      }

    }
  }
}
