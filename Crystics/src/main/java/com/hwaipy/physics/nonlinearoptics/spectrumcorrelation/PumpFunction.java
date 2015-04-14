package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation;

import com.hwaipy.crystics.filter.GaussianFilter;
import com.hwaipy.quantity.Quantity;
import com.hwaipy.quantity.Unit;

/**
 *
 * @author Hwaipy
 */
public class PumpFunction extends CorrelationFunction {

  public PumpFunction(double wavelength, double FWHM) {
    filterPump(GaussianFilter.newInstanceByWaveLengthSigma(new Quantity(wavelength, Unit.U("nm")), new Quantity(FWHM, Unit.U("nm")).divide(2.35)));
  }

  @Override
  public double correlationValue(double lamdaSignal, double lamdaIdler) {
    return 1;
  }

}
