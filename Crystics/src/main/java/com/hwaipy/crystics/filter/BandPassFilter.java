package com.hwaipy.crystics.filter;

import com.hwaipy.crystics.MonochromaticWave;
import static com.hwaipy.crystics.MonochromaticWave.λ;
import static com.hwaipy.crystics.MonochromaticWave.ν;
import static com.hwaipy.crystics.MonochromaticWave.ω;
import com.hwaipy.quantity.Quantity;
import static com.hwaipy.quantity.Quantity.Q;
import com.hwaipy.quantity.Units;

/**
 *
 * @author Hwaipy
 */
public class BandPassFilter implements OpticalFilter {

  private final MonochromaticWave from;
  private final MonochromaticWave to;

  private BandPassFilter(MonochromaticWave from, MonochromaticWave to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public Quantity transmittance(MonochromaticWave wave) {
    double transmittance = (from.compareWaveLength(wave) <= 0 || to.compareWaveLength(wave) >= 0) ? 1 : 0;
    return new Quantity(transmittance, Units.DIMENSIONLESS);
  }

  public static BandPassFilter newInstance(MonochromaticWave from, MonochromaticWave to) {
    return new BandPassFilter(from, to);
  }

  public static BandPassFilter newInstanceByWaveLength(MonochromaticWave centralWave, Quantity width) {
    return new BandPassFilter(centralWave.shiftλ(width.divide(2).negate()), centralWave.shiftλ(width.divide(2)));
  }

  public static BandPassFilter newInstanceByWaveLength(Quantity centralWaveLength, Quantity width) {
    return newInstanceByWaveLength(λ(centralWaveLength), width);
  }

  public static BandPassFilter newInstanceByWaveLength(String centralWaveLength, String width) {
    return newInstanceByWaveLength(Q(centralWaveLength), Q(width));
  }

  public static BandPassFilter newInstanceByFrequency(MonochromaticWave centralWave, Quantity width) {
    return new BandPassFilter(centralWave.shiftν(width.divide(2).negate()), centralWave.shiftν(width.divide(2)));
  }

  public static BandPassFilter newInstanceByFrequency(Quantity centralFrequency, Quantity width) {
    return newInstanceByFrequency(ν(centralFrequency), width);
  }

  public static BandPassFilter newInstanceByFrequency(String centralFrequency, String width) {
    return newInstanceByFrequency(Q(centralFrequency), Q(width));
  }

  public static BandPassFilter newInstanceByAngularFrequency(MonochromaticWave centralWave, Quantity width) {
    return new BandPassFilter(centralWave.shiftω(width.divide(2).negate()), centralWave.shiftω(width.divide(2)));
  }

  public static BandPassFilter newInstanceByAngularFrequency(Quantity centralAngularFrequency, Quantity width) {
    return newInstanceByAngularFrequency(ω(centralAngularFrequency), width);
  }

  public static BandPassFilter newInstanceByAngularFrequency(String centralAngularFrequency, String width) {
    return newInstanceByAngularFrequency(Q(centralAngularFrequency), Q(width));
  }
}
