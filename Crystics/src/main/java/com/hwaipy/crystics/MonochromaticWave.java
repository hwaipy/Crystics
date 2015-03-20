/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import static com.hwaipy.quantity.Constants.c;
import com.hwaipy.quantity.Quantity;

/**
 * @author Hwaipy 2015-3-21
 */
public class MonochromaticWave {

  private Quantity angularFrequency;

  private MonochromaticWave() {
  }

  private MonochromaticWave(Quantity angularFrequency) {
    this.angularFrequency = angularFrequency;
  }

  public Quantity getWaveLength() {
    return c.times(2 * Math.PI).divide(angularFrequency);
  }

  public void setWaveLength(Quantity wavelength) {
    wavelength.assertDimention("m");
    angularFrequency = c.times(2 * Math.PI).divide(wavelength);
  }

  public Quantity getFrequency() {
    return angularFrequency.divide(2 * Math.PI);
  }

  public void setFrequency(Quantity frequancy) {
    frequancy.assertDimention("Hz");
    angularFrequency = frequancy.times(2 * Math.PI);
  }

  public Quantity getAngularFrequency() {
    return angularFrequency;
  }

  public void setAngularFrequency(Quantity angularFrequency) {
    this.angularFrequency = angularFrequency;
  }

//  public Amount<WaveNumber> getWaveNumber(Medium medium, Axis axis) {
//    double n = medium.getIndex(this, axis);
//    return (Amount<WaveNumber>) Amount.ONE.times(2 * Math.PI * n).divide(getWaveLength());
//  }
  public static MonochromaticWave byWaveLength(Quantity wavelength) {
    MonochromaticWave monochromaticWave = new MonochromaticWave();
    monochromaticWave.setWaveLength(wavelength);
    return monochromaticWave;
  }

  public static MonochromaticWave byFrequency(Quantity frequency) {
    MonochromaticWave monochromaticWave = new MonochromaticWave();
    monochromaticWave.setFrequency(frequency);
    return monochromaticWave;
  }

  public static MonochromaticWave byAngularFrequency(Quantity angularFrequency) {
    return new MonochromaticWave(angularFrequency);
  }

}
