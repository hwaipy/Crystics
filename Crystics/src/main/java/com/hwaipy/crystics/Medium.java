/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import static com.hwaipy.crystics.MonochromaticWave.λ;
import com.hwaipy.crystics.refractivemodel.DefaultRefractiveModel;
import com.hwaipy.quantity.Constants;
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
    return n.minus(dndl.times(lambda));
  }

  public Quantity getGVD(MonochromaticWave monochromaticWave, Axis axis) {
    Quantity lambda = monochromaticWave.getWaveLength();
    Quantity n = refractiveModel.getRefractive(lambda, 0, axis);
    Quantity dndl = refractiveModel.getRefractive(lambda, 1, axis);
    Quantity d2ndl2 = refractiveModel.getRefractive(lambda, 2, axis);
    Quantity gvd = d2ndl2.times(lambda.power(3)).divide(2 * Math.PI).divide(Constants.c.power(2));
    System.out.println(gvd.getValue("fs^2/mm"));
    return n.minus(dndl.times(lambda));
  }

  public Quantity getWaveNumber(MonochromaticWave monochromaticWave, Axis axis) {
    Quantity n = getRefractive(monochromaticWave, axis);
    return n.times(2 * Math.PI).divide(monochromaticWave.getWaveLength());
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

  public static void main(String[] args) {
    Medium KTP = Mediums.getMediumByAlias("KTP");
    KTP.getGVD(λ("780nm"), Axis.X);
  }

}
