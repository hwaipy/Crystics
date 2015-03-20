/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.refractivemodel;

import com.hwaipy.quantity.Quantity;
import java.util.HashMap;

/**
 * @author Hwaipy 2015-3-18
 */
public abstract class SellmeierRefractiveEquation implements RefractiveEquation {

  protected final double[] coefficients;

  public SellmeierRefractiveEquation(double... coefficients) {
    this.coefficients = coefficients;
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

  private static class SellmeierRefractiveEquationFormula2 extends SellmeierRefractiveEquation {

    public SellmeierRefractiveEquationFormula2(double... coefficients) {
      super(coefficients);
      if (coefficients.length != 7) {
        throw new IllegalArgumentException("Formula 2 can only access 7 parameters.");
      }
    }

    @Override
    public Quantity getRefractive(Quantity waveLength) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  }

  private static class SellmeierRefractiveEquationFormula4 extends SellmeierRefractiveEquation {

    public SellmeierRefractiveEquationFormula4(double... coefficients) {
      super(coefficients);
      if (coefficients.length != 9) {
        throw new IllegalArgumentException("Formula 4 can only access 9 parameters.");
      }
    }

    @Override
    public Quantity getRefractive(Quantity waveLength) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  }
}
