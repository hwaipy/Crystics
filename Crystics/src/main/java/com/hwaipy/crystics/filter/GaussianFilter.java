package com.hwaipy.crystics.filter;

import com.hwaipy.crystics.MonochromaticWave;
import com.hwaipy.quantity.Quantity;
import static com.hwaipy.quantity.Quantity.Q;
import com.hwaipy.quantity.Units;

/**
 *
 * @author Hwaipy
 */
public abstract class GaussianFilter implements OpticalFilter {

  private final double central;
  private final double sigma;

  private GaussianFilter(double central, double sigma) {
    this.central = central;
    this.sigma = sigma;
  }

  @Override
  public Quantity transmittance(MonochromaticWave wave) {
    double transmittance = Math.exp(-Math.pow((central - getCharacteristicValue(wave)) / sigma, 2) / 2);
    return new Quantity(transmittance, Units.DIMENSIONLESS);
  }

  protected abstract double getCharacteristicValue(MonochromaticWave wave);

  private static class AngularFrequencyGaussianFilter extends GaussianFilter {

    private AngularFrequencyGaussianFilter(Quantity centralAngularFrequency, Quantity sigmaAngularFrequency) {
      super(centralAngularFrequency.getValueInSI(), sigmaAngularFrequency.getValueInSI());
    }

    @Override
    protected double getCharacteristicValue(MonochromaticWave wave) {
      return wave.ω().getValueInSI();
    }
  }

  private static class WaveLengthGaussianFilter extends GaussianFilter {

    private WaveLengthGaussianFilter(Quantity centralWaveLength, Quantity sigmaWaveLength) {
      super(centralWaveLength.getValueInSI(), sigmaWaveLength.getValueInSI());
    }

    @Override
    protected double getCharacteristicValue(MonochromaticWave wave) {
      return wave.λ().getValueInSI();
    }
  }
  private static final double FACTOR_FWHM = 2 * Math.sqrt(2 * Math.log(2));

  public static GaussianFilter newInstanceByWaveLengthSigma(Quantity centralWaveLength, Quantity sigmaWaveLength) {
    return new WaveLengthGaussianFilter(centralWaveLength, sigmaWaveLength);
  }

  public static GaussianFilter newInstanceByWaveLengthSigma(MonochromaticWave centralWave, Quantity sigmaWaveLength) {
    return newInstanceByWaveLengthSigma(centralWave.λ(), sigmaWaveLength);
  }

  public static GaussianFilter newInstanceByWaveLengthSigma(String centralWaveLength, String sigmaWaveLength) {
    return newInstanceByWaveLengthSigma(Q(centralWaveLength), Q(sigmaWaveLength));
  }

  public static GaussianFilter newInstanceByAngularFrequencySigma(Quantity centralAngularFrequency, Quantity sigmaAngularFrequency) {
    return new AngularFrequencyGaussianFilter(centralAngularFrequency, sigmaAngularFrequency);
  }

  public static GaussianFilter newInstanceByAngularFrequencySigma(MonochromaticWave centralWave, Quantity sigmaAngularFrequency) {
    return newInstanceByAngularFrequencySigma(centralWave.getAngularFrequency(), sigmaAngularFrequency);
  }

  public static GaussianFilter newInstanceByAngularFrequencySigma(String centralAngularFrequency, String sigmaAngularFrequency) {
    return newInstanceByAngularFrequencySigma(Q(centralAngularFrequency), Q(sigmaAngularFrequency));
  }

  public static GaussianFilter newInstanceByFrequencySigma(Quantity centralFrequency, Quantity sigmaFrequency) {
    return newInstanceByAngularFrequencySigma(centralFrequency.multiply(2 * Math.PI), sigmaFrequency.multiply(2 * Math.PI));
  }

  public static GaussianFilter newInstanceByFrequencySigma(MonochromaticWave centralWave, Quantity sigmaFrequency) {
    return newInstanceByAngularFrequencySigma(centralWave.getFrequency(), sigmaFrequency);
  }

  public static GaussianFilter newInstanceByFrequencySigma(String centralFrequency, String sigmaFrequency) {
    return newInstanceByFrequencySigma(Q(centralFrequency), Q(sigmaFrequency));
  }

  public static GaussianFilter newInstanceByWaveLengthFWHM(Quantity centralWaveLength, Quantity waveLengthFWHM) {
    return newInstanceByWaveLengthSigma(centralWaveLength, waveLengthFWHM.divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByWaveLengthFWHM(MonochromaticWave centralWave, Quantity waveLengthFWHM) {
    return newInstanceByWaveLengthSigma(centralWave.λ(), waveLengthFWHM.divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByWaveLengthFWHM(String centralWaveLength, String waveLengthFWHM) {
    return newInstanceByWaveLengthSigma(Q(centralWaveLength), Q(waveLengthFWHM).divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByAngularFrequencyFWHM(Quantity centralAngularFrequency, Quantity angularFrequencyFWHM) {
    return newInstanceByAngularFrequencySigma(centralAngularFrequency, angularFrequencyFWHM.divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByAngularFrequencyFWHM(MonochromaticWave centralWave, Quantity angularFrequencyFWHM) {
    return newInstanceByAngularFrequencySigma(centralWave.ω(), angularFrequencyFWHM.divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByAngularFrequencyFWHM(String centralAngularFrequency, String angularFrequencyFWHM) {
    return newInstanceByAngularFrequencySigma(Q(centralAngularFrequency), Q(angularFrequencyFWHM).divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByFrequencyFWHM(Quantity centralFrequency, Quantity frequencyFWHM) {
    return newInstanceByFrequencySigma(centralFrequency, frequencyFWHM.divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByFrequencyFWHM(MonochromaticWave centralWave, Quantity frequencyFWHM) {
    return newInstanceByFrequencySigma(centralWave.ν(), frequencyFWHM.divide(FACTOR_FWHM));
  }

  public static GaussianFilter newInstanceByFrequencyFWHM(String centralFrequency, String frequencyFWHM) {
    return newInstanceByFrequencySigma(Q(centralFrequency), Q(frequencyFWHM).divide(FACTOR_FWHM));
  }
}
