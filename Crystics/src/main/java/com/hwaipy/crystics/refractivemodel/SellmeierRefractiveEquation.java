/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.refractivemodel;

import com.hwaipy.quantity.Quantity;
import com.hwaipy.quantity.Unit;
import com.hwaipy.quantity.Units;
import java.util.HashMap;

/**
 * @author Hwaipy 2015-3-18
 */
public abstract class SellmeierRefractiveEquation implements RefractiveEquation {

  protected final double[] coefficients;
  private final int derivativeDepth;

  public SellmeierRefractiveEquation(int derivativeDepth, double... coefficients) {
    this.coefficients = coefficients;
    this.derivativeDepth = derivativeDepth;
  }

  @Override
  public int getDerivativeDepth() {
    return derivativeDepth;
  }

  private static final HashMap<String, SellmeierRefractiveEquationFactory> registeredEquations = new HashMap<String, SellmeierRefractiveEquationFactory>();

  public static SellmeierRefractiveEquationFactory getFormulaFactory(String formulaID) {
    return registeredEquations.get(formulaID);
  }

  public static SellmeierRefractiveEquation newInstance(String formulaID, double... coefficients) {
    SellmeierRefractiveEquationFactory factory = registeredEquations.get(formulaID);
    if (factory == null) {
      throw new IllegalArgumentException("Formula ID " + formulaID + " does not exist.");
    }
    else {
      return factory.newInstance(coefficients);
    }
  }

  public static boolean hasFormula(String formulaID) {
    return registeredEquations.containsKey(formulaID);
  }

  public static void register(String formulaID, SellmeierRefractiveEquationFactory factory) {
    if (!registerOnAbsence(formulaID, factory)) {
      throw new IllegalArgumentException("Formula ID " + formulaID + " already exists.");
    }
  }

  public static boolean registerOnAbsence(String formulaID, SellmeierRefractiveEquationFactory factory) {
    synchronized (SellmeierRefractiveEquation.class) {
      if (registeredEquations.containsKey(formulaID)) {
        return false;
      }
      registeredEquations.put(formulaID, factory);
      return true;
    }
  }

  static {
    register("2", new SellmeierRefractiveEquationFactory() {

      @Override
      public SellmeierRefractiveEquation newInstance(double... coefficients) {
        return new SellmeierRefractiveEquationFormula2(coefficients);
      }

    });
    register("4", new SellmeierRefractiveEquationFactory() {

      @Override
      public SellmeierRefractiveEquation newInstance(double... coefficients) {
        return new SellmeierRefractiveEquationFormula4(coefficients);
      }

    });
  }

  private abstract static class FormulaRefractiveEquation extends SellmeierRefractiveEquation {

    public FormulaRefractiveEquation(double... coefficients) {
      super(2, coefficients);
    }

    @Override
    public Quantity getRefractive(Quantity waveLength, int derivative) {
      switch (derivative) {
        case 0:
          return n(waveLength);
        case 1:
          return dn_dlambda(waveLength);
        case 2:
          return d2n_dlambda2(waveLength);
        default:
          throw new UnsupportedOperationException("Dericative " + derivative + " not supported.");
      }
    }

    protected abstract Quantity n(Quantity waveLength);

    protected abstract Quantity dn_dlambda(Quantity waveLength);

    protected abstract Quantity d2n_dlambda2(Quantity waveLength);

  }

  private static class SellmeierRefractiveEquationFormula2 extends FormulaRefractiveEquation {

    public SellmeierRefractiveEquationFormula2(double... coefficients) {
      super(coefficients);
      if (coefficients.length != 7 && coefficients.length != 5) {
        throw new IllegalArgumentException("Formula 2 can only access 7 parameters.");
      }
    }

    @Override
    protected Quantity n(Quantity waveLength) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Quantity dn_dlambda(Quantity waveLength) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Quantity d2n_dlambda2(Quantity waveLength) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  }

  private static class SellmeierRefractiveEquationFormula4 extends FormulaRefractiveEquation {

    public SellmeierRefractiveEquationFormula4(double... coefficients) {
      super(coefficients);
      if (coefficients.length != 9) {
        throw new IllegalArgumentException("Formula 4 can only access 9 parameters.");
      }
    }

    @Override
    protected Quantity n(Quantity waveLength) {
      double λ = waveLength.getValue("µm");
      double λ2 = λ * λ;
      double[] c = coefficients;
      double n = Math.sqrt(c[0] + c[1] / (λ2 - c[3]) + c[5] / (λ2 - c[7]));
      return new Quantity(n, Units.DIMENSIONLESS);
    }

    @Override
    protected Quantity dn_dlambda(Quantity waveLength) {
      double λ = waveLength.getValue("µm");
      double λ2 = λ * λ;
      double[] c = coefficients;
      double n = n(waveLength).getValue("");
      double dndlamda = -λ / n * (c[1] / (Math.pow(λ2 - c[3], 2)) + c[5] / (Math.pow(λ2 - c[7], 2)));
      return new Quantity(dndlamda, Unit.of("µm^-1"));
    }

    @Override
    protected Quantity d2n_dlambda2(Quantity waveLength) {
      double λ = waveLength.getValue("µm");
      double λ2 = λ * λ;
      double[] c = coefficients;
      double n = n(waveLength).getValue("");
      double dndl = dn_dlambda(waveLength).getValue("µm^-1");
      double d2ndl2 = -dndl / n + 2 * λ / n
              * (c[1] / (Math.pow(λ2 - c[3], 3)) + c[5] / (Math.pow(λ2 - c[7], 3)));
      return new Quantity(d2ndl2, Unit.of("µm^-2"));
    }

  }
}
