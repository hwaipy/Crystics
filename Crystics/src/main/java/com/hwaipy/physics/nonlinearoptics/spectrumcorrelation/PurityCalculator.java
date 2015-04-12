package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author Hwaipy
 */
public class PurityCalculator {

  private final double minArg1;
  private final double maxArg1;
  private final double minArg2;
  private final double maxArg2;
  private final double[][] results;
  private final CorrelationFunction function;
  private final int width;
  private final int height;

  public PurityCalculator(double minArg1, double maxArg1, double minArg2, double maxArg2,
          CorrelationFunction function, int width, int height) {
    this.minArg1 = minArg1;
    this.maxArg1 = maxArg1;
    this.minArg2 = minArg2;
    this.maxArg2 = maxArg2;
    results = new double[height][width];
    this.function = function;
    this.width = width;
    this.height = height;
  }

  public double calculate(boolean signal) {
    //result第一维是arg2，第二维是arg1
    double stepOfArg1 = (maxArg1 - minArg1) / (width - 1);
    double stepOfArg2 = (maxArg2 - minArg2) / (height - 1);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        double arg1 = minArg1 + x * stepOfArg1;
        double arg2 = minArg2 + y * stepOfArg2;
        double result = function.value(arg1, arg2);
        results[y][x] = result;
      }
    }
    RealMatrix correlationMatrix = MatrixUtils.createRealMatrix(results);
    int dimension = correlationMatrix.getRowDimension();
    double squreSum = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        double value = correlationMatrix.getEntry(i, j);
        squreSum += value * value;
      }
    }
    double normFactor = 1 / Math.sqrt(squreSum);
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        correlationMatrix.multiplyEntry(i, j, normFactor);
      }
    }
    if (!signal) {
      correlationMatrix = correlationMatrix.transpose();
    }
    RealMatrix result = MatrixUtils.createRealMatrix(new double[dimension][dimension]);
    for (int indexP2 = 0; indexP2 < dimension; indexP2++) {
      RealVector rowVector = correlationMatrix.getRowVector(indexP2);
      RealMatrix rou = rowVector.outerProduct(rowVector);
      result = result.add(rou);
    }
    result = result.multiply(result);
    double trace = result.getTrace();
    return trace;
  }

}
