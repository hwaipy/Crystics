package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation;

import com.hwaipy.crystics.MonochromaticWave;
import com.hwaipy.crystics.filter.OpticalFilter;
import com.hwaipy.quantity.Quantity;
import com.hwaipy.quantity.Unit;
import com.hwaipy.quantity.Units;
import java.util.ArrayList;

/**
 *
 * @author Hwaipy
 */
public abstract class CorrelationFunction {

  private final ArrayList<OpticalFilter> pumpFilters = new ArrayList<>();
  private final ArrayList<OpticalFilter> signalFilters = new ArrayList<>();
  private final ArrayList<OpticalFilter> idleFilters = new ArrayList<>();

  public void filterPump(OpticalFilter filter) {
    pumpFilters.add(filter);
  }

  public void filterSignal(OpticalFilter filter) {
    signalFilters.add(filter);
  }

  public void filterIdle(OpticalFilter filter) {
    idleFilters.add(filter);
  }

  public double value(double signalLamda, double idleLamda) {
    double result = correlationValue(signalLamda, idleLamda);
    double pumpLamda = 1 / (1 / signalLamda + 1 / idleLamda);
    for (OpticalFilter pumpFilter : pumpFilters) {
      result *= Math.sqrt(pumpFilter.transmittance(MonochromaticWave.λ(new Quantity(pumpLamda, Unit.of("nm")))).getValue(Units.DIMENSIONLESS));
    }
    for (OpticalFilter signalFilter : signalFilters) {
      result *= Math.sqrt(signalFilter.transmittance(MonochromaticWave.λ(new Quantity(signalLamda, Unit.of("nm")))).getValue(Units.DIMENSIONLESS));
    }
    for (OpticalFilter idleFilter : idleFilters) {
      result *= Math.sqrt(idleFilter.transmittance(MonochromaticWave.λ(new Quantity(idleLamda, Unit.of("nm")))).getValue(Units.DIMENSIONLESS));
    }
    return result;
  }

  protected abstract double correlationValue(double signalLamda, double idleLamda);

}
