package com.hwaipy.crystics;

import static com.hwaipy.quantity.PhysicalConstants.c;
import com.hwaipy.quantity.Quantity;
import static com.hwaipy.quantity.Quantity.Q;
import com.hwaipy.quantity.Units;

/**
 * @author Hwaipy 2015-3-21
 */
public class MonochromaticWave {

  private Quantity angularFrequency;
  private Quantity frequency;
  private Quantity waveLength;

  private MonochromaticWave() {
  }

  public Quantity λ() {
    return getWaveLength();
  }

  public Quantity getWaveLength() {
    if (waveLength == null) {
      fillWaveLength();
    }
    return waveLength;
  }

  public MonochromaticWave shiftλ(String dλ) {
    return shiftByWaveLength(Q(dλ));
  }

  public MonochromaticWave shiftλ(Quantity dλ) {
    return shiftByWaveLength(dλ);
  }

  public MonochromaticWave shiftByWaveLength(Quantity dλ) {
    return λ(λ().plus(dλ));
  }

  public Quantity ν() {
    return getFrequency();
  }

  public Quantity getFrequency() {
    if (frequency == null) {
      fillFrequency();
    }
    return frequency;
  }

  public MonochromaticWave shiftν(String dν) {
    return shiftByFrequency(Q(dν));
  }

  public MonochromaticWave shiftν(Quantity dν) {
    return shiftByFrequency(dν);
  }

  public MonochromaticWave shiftByFrequency(Quantity dν) {
    return ν(ν().plus(dν));
  }

  public MonochromaticWave sumFrequency(MonochromaticWave second) {
    return shiftByFrequency(second.ν());
  }

  public MonochromaticWave differenceFrequency(MonochromaticWave second) {
    return shiftByFrequency(second.ν().negate());
  }

  public MonochromaticWave doubleFrequency() {
    return shiftByFrequency(ν());
  }

  public Quantity ω() {
    return getAngularFrequency();
  }

  public Quantity getAngularFrequency() {
    if (angularFrequency == null) {
      fillAngularFrequency();
    }
    return angularFrequency;
  }

  public MonochromaticWave shiftω(String dω) {
    return shiftByAngularFrequency(Q(dω));
  }

  public MonochromaticWave shiftω(Quantity dω) {
    return shiftByAngularFrequency(dω);
  }

  public MonochromaticWave shiftByAngularFrequency(Quantity dω) {
    return ω(ω().plus(dω));
  }

  public Quantity k(Medium medium, Axis axis) {
    return getWaveNumber(medium, axis);
  }

  public Quantity getWaveNumber(Medium medium, Axis axis) {
    return medium.getWaveNumber(this, axis);
  }

  public int compareWaveLength(MonochromaticWave wave) {
    return this.getWaveLength().compareTo(wave.getWaveLength());
  }

  public int compareFrequency(MonochromaticWave wave) {
    return this.getAngularFrequency().compareTo(wave.getAngularFrequency());
  }

  private void fillWaveLength() {
    if (angularFrequency != null) {
      waveLength = c.multiply(2 * Math.PI).divide(angularFrequency);
    }
    else {
      waveLength = c.divide(frequency);
    }
  }

  private void fillFrequency() {
    if (angularFrequency != null) {
      frequency = angularFrequency.divide(2 * Math.PI);
    }
    else {
      frequency = c.divide(waveLength);
    }
  }

  private void fillAngularFrequency() {
    if (frequency != null) {
      angularFrequency = frequency.multiply(2 * Math.PI);
    }
    else {
      angularFrequency = c.multiply(2 * Math.PI).divide(waveLength);
    }
  }

  public static MonochromaticWave λ(String wavelength) {
    return byWaveLength(Q(wavelength));
  }

  public static MonochromaticWave λ(Quantity wavelength) {
    return byWaveLength(wavelength);
  }

  public static MonochromaticWave byWaveLength(Quantity waveLength) {
    waveLength.assertDimention(Units.m);
    MonochromaticWave monochromaticWave = new MonochromaticWave();
    monochromaticWave.waveLength = waveLength;
    return monochromaticWave;
  }

  public static MonochromaticWave ν(String frequancy) {
    return byFrequency(Q(frequancy));
  }

  public static MonochromaticWave ν(Quantity frequency) {
    return byFrequency(frequency);
  }

  public static MonochromaticWave byFrequency(Quantity frequency) {
    frequency.assertDimention(Units.Hz);
    MonochromaticWave monochromaticWave = new MonochromaticWave();
    monochromaticWave.frequency = frequency;
    return monochromaticWave;
  }

  public static MonochromaticWave ω(String angularFrequency) {
    return byAngularFrequency(Q(angularFrequency));
  }

  public static MonochromaticWave ω(Quantity angularFrequency) {
    return byAngularFrequency(angularFrequency);
  }

  public static MonochromaticWave byAngularFrequency(Quantity angularFrequency) {
    angularFrequency.assertDimention(Units.Hz);
    MonochromaticWave monochromaticWave = new MonochromaticWave();
    monochromaticWave.angularFrequency = angularFrequency;
    return monochromaticWave;
  }

}
