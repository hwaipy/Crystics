package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation;

import com.hwaipy.crystics.Axis;
import com.hwaipy.crystics.Medium;
import com.hwaipy.crystics.MonochromaticWave;
import com.hwaipy.quantity.PhysicalConstants;
import com.hwaipy.quantity.Quantity;
import com.hwaipy.quantity.Unit;

/**
 *
 * @author Hwaipy
 */
public class QuasiPhaseMatchFunction extends CorrelationFunction {

  private final Medium medium;
  private final double lengthOfCrystal;
  private final double period;

  public QuasiPhaseMatchFunction(Medium medium, double lengthOfCrystalInMM, double periodInUM) {
    this.medium = medium;
    this.lengthOfCrystal = lengthOfCrystalInMM / 1000;
    this.period = periodInUM / 1e6;
  }

  @Override
  public double correlationValue(double lamdaSignal, double lamdaIdler) {
    return correlationValueDirect(lamdaSignal, lamdaIdler);
//        return correlationValueApproximate(lamdaSignal, lamdaIdler);
  }

  public double correlationValueDirect(double lamdaSignal, double lamdaIdler) {
    double lamdaPump = 1 / (1 / lamdaSignal + 1 / lamdaIdler);
    MonochromaticWave pump = MonochromaticWave.byWaveLength(new Quantity(lamdaPump, Unit.of("nm")));
    MonochromaticWave signal = MonochromaticWave.byWaveLength(new Quantity(lamdaSignal, Unit.of("nm")));
    MonochromaticWave idle = MonochromaticWave.byWaveLength(new Quantity(lamdaIdler, Unit.of("nm")));
    double kPump = pump.getWaveNumber(medium, Axis.Y).getValue("/m");
    double kSignal = signal.getWaveNumber(medium, Axis.Y).getValue("/m");
    double kIdler = idle.getWaveNumber(medium, Axis.Z).getValue("/m");

    double arg = lengthOfCrystal / 2 * (kPump - kSignal - kIdler - 2 * Math.PI / period);
    double result = Math.sin(arg) / arg;
    return result;
  }

  public double correlationValueApproximate(double lamdaSignal, double lamdaIdler) {
    double lamdaPump = 1 / (1 / lamdaSignal + 1 / lamdaIdler);
    MonochromaticWave pump = MonochromaticWave.byWaveLength(new Quantity(lamdaPump, Unit.of("nm")));
    MonochromaticWave signal = MonochromaticWave.byWaveLength(new Quantity(lamdaSignal, Unit.of("nm")));
    MonochromaticWave idle = MonochromaticWave.byWaveLength(new Quantity(lamdaIdler, Unit.of("nm")));
    double ngPump = medium.getGroupRefractive(pump, Axis.Y).getValue();
    double ngSignal = medium.getGroupRefractive(signal, Axis.Y).getValue();
    double ngIdle = medium.getGroupRefractive(idle, Axis.Z).getValue();
    MonochromaticWave pdcCenter = MonochromaticWave.byWaveLength(new Quantity(780, Unit.of("nm")));
    double omegaPDCCenter = pdcCenter.getAngularFrequency().getValue("Hz");
    double omegaPDCSignal = signal.getAngularFrequency().getValue("Hz");
    double omegaPDCIdle = idle.getAngularFrequency().getValue("Hz");
    double vo = omegaPDCSignal - omegaPDCCenter;
    double ve = omegaPDCIdle - omegaPDCCenter;
    double c = PhysicalConstants.c.getValue("m/s");
    double kpp = ngPump / c;
    double kop = ngSignal / c;
    double kep = ngIdle / c;
    double r = (vo * (kop - kpp) + ve * (kep - kpp)) * lengthOfCrystal / 2;
    return Math.sin(r) / r;
  }

//  public static void main(String[] args) {
//    MonochromaticWave pump1 = MonochromaticWave.byWaveLength(Amount.valueOf(780, Units.NANOMETRE));
//    MonochromaticWave pump2 = MonochromaticWave.byWaveLength(Amount.valueOf(780.1, Units.NANOMETRE));
//    System.out.println(pump2.getAngularFrequency().doubleValue(Units.TERAHERTZ) - pump1.getAngularFrequency().doubleValue(Units.TERAHERTZ));
//  }
}
