package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.lab;

import com.hwaipy.quantity.Quantity;

/**
 *
 * @author Administrator 2015-6-5
 */
public class DirectIntegrator {

  static abstract class D1 {

    private final Quantity min;
    private final Quantity max;
    private final Quantity bin;
    private final int step;

    protected D1(Quantity min, Quantity max, int step) {
      min.assertDimention(max.getUnit());
      this.min = min;
      this.max = max;
      this.step = step;
      this.bin = max.minus(min).divide(step - 1);
    }

    public Quantity integrate() {
      Quantity r = value(0);
      for (int i = 1; i < step; i++) {
        r = r.plus(value(i));
      }
      return r.multiply(bin);
    }

    private Quantity value(int step) {
      Quantity x = min.plus(bin.multiply(step));
      return value(x);
    }

    public abstract Quantity value(Quantity x);
  }

  static abstract class D2 {

    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final double xBin;
    private final double yBin;
    private final int step;

    protected D2(double xMin, double xMax, double yMin, double yMax, int step) {
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
      this.step = step;
      this.xBin = (xMax - xMin) / (step - 1);
      this.yBin = (yMax - yMin) / (step - 1);
    }

    public double integrate() {
      double r = 0;
      for (int i = 0; i < step; i++) {
        for (int j = 0; j < step; j++) {
          r += value(i, j);
        }
      }
      return r * xBin * yBin;
    }

    private double value(int xStep, int yStep) {
      double x = xMin + xStep * xBin;
      double y = yMin + yStep * yBin;
      return value(x, y);
    }

    public abstract double value(double x, double y);
  }
}
