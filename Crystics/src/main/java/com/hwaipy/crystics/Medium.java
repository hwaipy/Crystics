package com.hwaipy.crystics;

import com.hwaipy.crystics.refractivemodel.DefaultRefractiveModel;
import com.hwaipy.quantity.PhysicalConstants;
import com.hwaipy.quantity.Quantity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Hwaipy
 */
public class Medium {

  private final String symbol;
  private final String name;
  private final ArrayList<String> aliasList = new ArrayList<String>();
  private final DefaultRefractiveModel refractiveModel;

  public Medium(String symbol, String name, ArrayList<String> aliasList, DefaultRefractiveModel refractiveModel) {
    this.symbol = symbol;
    this.name = name;
    this.aliasList.addAll(aliasList);
    this.refractiveModel = refractiveModel;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getName() {
    return name;
  }

  public Collection<String> getAlias() {
    return Collections.unmodifiableCollection(aliasList);
  }

  public Quantity n(MonochromaticWave monochromaticWave, Axis axis) {
    return getRefractive(monochromaticWave, axis);
  }

  public Quantity getRefractive(MonochromaticWave monochromaticWave, Axis axis) {
    Quantity lambda = monochromaticWave.getWaveLength();
    return refractiveModel.getRefractive(lambda, 0, axis);
  }

  public Quantity N(MonochromaticWave monochromaticWave, Axis axis) {
    return getGroupRefractive(monochromaticWave, axis);
  }

  public Quantity getGroupRefractive(MonochromaticWave monochromaticWave, Axis axis) {
    Quantity lambda = monochromaticWave.getWaveLength();
    Quantity n = refractiveModel.getRefractive(lambda, 0, axis);
    Quantity dndl = refractiveModel.getRefractive(lambda, 1, axis);
    return n.minus(dndl.multiply(lambda));
  }

  public Quantity getGVD(MonochromaticWave monochromaticWave, Axis axis) {
    Quantity lambda = monochromaticWave.getWaveLength();
    Quantity d2ndl2 = refractiveModel.getRefractive(lambda, 2, axis);
    Quantity gvd = d2ndl2.multiply(lambda.power(3)).divide(2 * Math.PI).divide(PhysicalConstants.c.power(2));
    return gvd;
  }

  public Quantity getWaveNumber(MonochromaticWave monochromaticWave, Axis axis) {
    Quantity n = getRefractive(monochromaticWave, axis);
    return n.multiply(2 * Math.PI).divide(monochromaticWave.getWaveLength());
  }

  public Quantity getAbbeNumverVD(Axis axis) {
    return AbbeNumber.VD(this, axis);
  }

  public Quantity getAbbeNumverVd(Axis axis) {
    return AbbeNumber.Vd(this, axis);
  }

  public Quantity getAbbeNumverVe(Axis axis) {
    return AbbeNumber.Ve(this, axis);
  }

}
