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

  public Quantity λ() {
    return getWaveLength();
  }

  public Quantity getWaveLength() {
    return c.times(2 * Math.PI).divide(angularFrequency);
  }

  public void setWaveLength(Quantity wavelength) {
    wavelength.assertDimention("m");
    angularFrequency = c.times(2 * Math.PI).divide(wavelength);
  }

  public Quantity ν() {
    return getFrequency();
  }

  public Quantity getFrequency() {
    return angularFrequency.divide(2 * Math.PI);
  }

  public void setFrequency(Quantity frequancy) {
    frequancy.assertDimention("Hz");
    angularFrequency = frequancy.times(2 * Math.PI);
  }

  public Quantity ω() {
    return getAngularFrequency();
  }

  public Quantity getAngularFrequency() {
    return angularFrequency;
  }

  public void setAngularFrequency(Quantity angularFrequency) {
    this.angularFrequency = angularFrequency;
  }

  public Quantity k(Medium medium, Axis axis) {
    return getWaveNumber(medium, axis);
  }

  public Quantity getWaveNumber(Medium medium, Axis axis) {
    return medium.getWaveNumber(this, axis);
  }

  public static MonochromaticWave λ(String wavelength) {
    return byWaveLength(Quantity.of(wavelength));
  }

  public static MonochromaticWave λ(Quantity wavelength) {
    return byWaveLength(wavelength);
  }

  public static MonochromaticWave byWaveLength(Quantity wavelength) {
    MonochromaticWave monochromaticWave = new MonochromaticWave();
    monochromaticWave.setWaveLength(wavelength);
    return monochromaticWave;
  }

  public static MonochromaticWave ν(String frequancy) {
    return byFrequency(Quantity.of(frequancy));
  }

  public static MonochromaticWave ν(Quantity frequency) {
    return byFrequency(frequency);
  }

  public static MonochromaticWave byFrequency(Quantity frequency) {
    MonochromaticWave monochromaticWave = new MonochromaticWave();
    monochromaticWave.setFrequency(frequency);
    return monochromaticWave;
  }

  public static MonochromaticWave ω(String angularFrequency) {
    return byAngularFrequency(Quantity.of(null));
  }

  public static MonochromaticWave ω(Quantity angularFrequency) {
    return byAngularFrequency(angularFrequency);
  }

  public static MonochromaticWave byAngularFrequency(Quantity angularFrequency) {
    return new MonochromaticWave(angularFrequency);
  }

}
