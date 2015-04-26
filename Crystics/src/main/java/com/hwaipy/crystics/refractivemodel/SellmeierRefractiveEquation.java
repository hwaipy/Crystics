package com.hwaipy.crystics.refractivemodel;

import com.hwaipy.quantity.Quantity;
import com.hwaipy.quantity.Unit;
import com.hwaipy.quantity.Units;
import java.util.HashMap;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

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

  private static final HashMap<String, SellmeierRefractiveEquationFactory> registeredEquations = new HashMap<>();

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
    register("Vacuum", new SellmeierRefractiveEquationFactory() {

      @Override
      public SellmeierRefractiveEquation newInstance(double... coefficients) {
        return new SellmeierRefractiveEquationFormulaVacuum();
      }

    });
    register("1", new SellmeierRefractiveEquationFactory() {

      @Override
      public SellmeierRefractiveEquation newInstance(double... coefficients) {
        return new SellmeierRefractiveEquationFormula1(coefficients);
      }

    });
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
      double xRealValue = waveLength.getValue("µm");
      int params = 1;
      DerivativeStructure x = new DerivativeStructure(params, derivative, 0, xRealValue);
      DerivativeStructure y = refractiveEquation(x);
      if (derivative == 0) {
        return new Quantity(y.getValue(), Units.DIMENSIONLESS);
      }
      else if (derivative > 0) {
        return new Quantity(y.getPartialDerivative(derivative), Unit.of("µm^-" + derivative));
      }
      else {
        throw new UnsupportedOperationException("Dericative " + derivative + " should not below 0.");
      }
    }

    protected abstract DerivativeStructure refractiveEquation(DerivativeStructure waveLengthMicroMeter);

  }

  private static class SellmeierRefractiveEquationFormulaVacuum extends FormulaRefractiveEquation {

    public SellmeierRefractiveEquationFormulaVacuum() {
      super(null);
    }

    @Override
    protected DerivativeStructure refractiveEquation(DerivativeStructure waveLengthMicroMeter) {
      DerivativeStructure λ = waveLengthMicroMeter;
      return λ.divide(λ);
    }

  }

  private static class SellmeierRefractiveEquationFormula1 extends FormulaRefractiveEquation {

    private final double C2_2 = coefficients[2] * coefficients[2];
    private final double C1C2_2 = coefficients[1] * C2_2;
    private final double C4_2 = coefficients[4] * coefficients[4];
    private final double C3C4_2 = coefficients[3] * C4_2;
    private final double C6_2 = coefficients[6] * coefficients[6];
    private final double C5C6_2 = coefficients[5] * C6_2;
    private final double C = 1 + coefficients[1] + coefficients[3] + coefficients[5];

    public SellmeierRefractiveEquationFormula1(double... coefficients) {
      super(coefficients);
      if (coefficients.length != 7) {
        throw new IllegalArgumentException("Formula 4 can only access 9 parameters.");
      }
    }

    @Override
    protected DerivativeStructure refractiveEquation(DerivativeStructure waveLengthMicroMeter) {
      DerivativeStructure λ = waveLengthMicroMeter;
      DerivativeStructure λ2 = λ.multiply(λ);
      DerivativeStructure A1 = λ2.add(-C2_2).reciprocal().multiply(C1C2_2);
      DerivativeStructure A2 = λ2.add(-C4_2).reciprocal().multiply(C3C4_2);
      DerivativeStructure A3 = λ2.add(-C6_2).reciprocal().multiply(C5C6_2);
      return A1.add(A2).add(A3).add(C).sqrt();
    }

  }

  private static class SellmeierRefractiveEquationFormula2 extends FormulaRefractiveEquation {

    public SellmeierRefractiveEquationFormula2(double... coefficients) {
      super(coefficients);
      if (coefficients.length != 7 && coefficients.length != 5) {
        throw new IllegalArgumentException("Formula 2 can only access 7 parameters.");
      }
    }

    @Override
    protected DerivativeStructure refractiveEquation(DerivativeStructure waveLengthMicroMeter) {
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
    protected DerivativeStructure refractiveEquation(DerivativeStructure waveLengthMicroMeter) {
      DerivativeStructure λ = waveLengthMicroMeter;
      DerivativeStructure λ2 = λ.multiply(λ);
      double[] c = coefficients;
      DerivativeStructure A1 = λ2.add(-c[3]).reciprocal().multiply(c[1]);
      DerivativeStructure A2 = λ2.add(-c[7]).reciprocal().multiply(c[5]);
      return A1.add(A2).add(c[0]).sqrt();
    }

  }
}
