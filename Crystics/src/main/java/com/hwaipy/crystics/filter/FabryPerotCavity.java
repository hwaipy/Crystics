package com.hwaipy.crystics.filter;

import com.hwaipy.crystics.MonochromaticWave;
import static com.hwaipy.crystics.MonochromaticWave.*;
import com.hwaipy.crystics.filter.OpticalFilter;
import com.hwaipy.quantity.Quantity;
import static com.hwaipy.quantity.Quantity.Q;
import static com.hwaipy.quantity.Units.*;
import static org.apache.commons.math3.util.FastMath.*;

/**
 * TODO: medium of FPC
 * @author Hwaipy
 */
public class FabryPerotCavity implements OpticalFilter {

  private static final Quantity n = Q("1");
  private final Quantity R;
  private final Quantity l;
  private final Quantity F;
  private final Quantity finesse;
  private Quantity theta = Q("0");

  public FabryPerotCavity(Quantity R, Quantity l) {
    R.assertDimention("");
    l.assertDimention("m");
    this.R = R;
    this.l = l;
    F = R.multiply(4).divide(Q("1").minus(R).power(2));
    finesse = new Quantity(Math.PI / 2 / Math.asin(1 / Math.sqrt(F.getValueInSI())),
            DIMENSIONLESS);
  }

  public void rotate(Quantity deltaTheta) {
    theta = theta.plus(deltaTheta);
  }

  public Quantity getFSR(MonochromaticWave wave) {
    Quantity λ0 = wave.λ();
    return λ0.power(2).divide(n.multiply(2).multiply(l).multiply(cos(theta.getValueInSI())).plus(λ0));
  }

  public void setTheta(Quantity theta) {
    theta.assertDimention(DIMENSIONLESS);
    this.theta = theta;
  }

  public Quantity getR() {
    return R;
  }

  public Quantity getL() {
    return l;
  }

  public Quantity getF() {
    return F;
  }

  public Quantity getFinesse() {
    return finesse;
  }

  public Quantity getTheta() {
    return theta;
  }

  @Override
  public Quantity transmittance(MonochromaticWave wave) {
    Quantity λ = wave.λ();
    Quantity delta = l.multiply(4 * Math.PI * cos(theta.getValueInSI())).multiply(n).divide(λ);
    return F.multiply(pow(sin(delta.getValueInSI() / 2), 2)).plus(Q("1")).reciprocal();
  }

  public void centreAt(MonochromaticWave wave) {
    Quantity λ = wave.λ();
    Quantity delta = l.multiply(4 * PI).divide(λ);
    double d = delta.getValueInSI() % (2 * PI);
    delta = delta.minus(new Quantity(d, DIMENSIONLESS));
    theta = new Quantity(acos(delta.multiply(λ).divide(l).divide(4 * PI).getValueInSI()), DIMENSIONLESS);
  }

  public static FabryPerotCavity newInstanceByWaveLengthFWHM(Quantity centralWaveLength, Quantity waveLengthFWHM, Quantity waveLengthFSR) {
    Quantity λ0 = centralWaveLength;
    Quantity FWHM = waveLengthFWHM;
    Quantity FSR = waveLengthFSR;
    Quantity finesse = FSR.divide(FWHM);
    Quantity l = λ0.power(2).divide(FSR).minus(λ0).divide(2);
    Quantity F = new Quantity(pow(1 / sin(PI / 2 / finesse.getValueInSI()), 2), DIMENSIONLESS);
    Quantity R = F.reciprocal().multiply(2).minus(F.reciprocal().plus(F.power(2).reciprocal()).sqrt().multiply(2)).plus(Q("1"));
    FabryPerotCavity fpc = new FabryPerotCavity(R, l);
    fpc.centreAt(λ(λ0));
    return fpc;
  }

  public static FabryPerotCavity newInstanceByWaveLengthFWHM(String centralWaveLength, String waveLengthFWHM, String waveLengthFSR) {
    return newInstanceByWaveLengthFWHM(Q(centralWaveLength), Q(waveLengthFWHM), Q(waveLengthFSR));
  }

  public static FabryPerotCavity newInstanceByAngularFrequencyFWHM(Quantity centralAngularFrequency, Quantity angularFrequencyFWHM, Quantity angularFrequencyFSR) {
    Quantity λb1 = MonochromaticWave.ω(centralAngularFrequency.minus(angularFrequencyFSR.divide(2))).λ();
    Quantity λb2 = MonochromaticWave.ω(centralAngularFrequency.plus(angularFrequencyFSR.divide(2))).λ();
    Quantity λh1 = MonochromaticWave.ω(centralAngularFrequency.minus(angularFrequencyFWHM.divide(2))).λ();
    Quantity λh2 = MonochromaticWave.ω(centralAngularFrequency.plus(angularFrequencyFWHM.divide(2))).λ();
    Quantity λ0 = MonochromaticWave.ω(centralAngularFrequency).λ();
    Quantity FWHM = λh2.minus(λh1);
    Quantity FSR = λb2.minus(λb1);
    return newInstanceByWaveLengthFWHM(λ0, FWHM, FSR);
  }

  public static FabryPerotCavity newInstanceByAngularFrequencyFWHM(String centralAngularFrequency, String angularFrequencyFWHM, String angularFrequencyFSR) {
    return newInstanceByAngularFrequencyFWHM(Q(centralAngularFrequency), Q(angularFrequencyFWHM), Q(angularFrequencyFSR));
  }

  public static FabryPerotCavity newInstanceByFrequencyFWHM(Quantity centralFrequency, Quantity frequencyFWHM, Quantity frequencyFSR) {
    Quantity λb1 = MonochromaticWave.ν(centralFrequency.minus(frequencyFSR.divide(2))).λ();
    Quantity λb2 = MonochromaticWave.ν(centralFrequency.plus(frequencyFSR.divide(2))).λ();
    Quantity λh1 = MonochromaticWave.ν(centralFrequency.minus(frequencyFWHM.divide(2))).λ();
    Quantity λh2 = MonochromaticWave.ν(centralFrequency.plus(frequencyFWHM.divide(2))).λ();
    Quantity λ0 = MonochromaticWave.ν(centralFrequency).λ();
    Quantity FWHM = λh2.minus(λh1);
    Quantity FSR = λb2.minus(λb1);
    return newInstanceByWaveLengthFWHM(λ0, FWHM, FSR);
  }

  public static FabryPerotCavity newInstanceByFrequencyFWHM(String centralFrequency, String frequencyFWHM, String frequencyFSR) {
    return newInstanceByFrequencyFWHM(Q(centralFrequency), Q(frequencyFWHM), Q(frequencyFSR));
  }

}
