/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import static com.hwaipy.crystics.MonochromaticWave.λ;
import com.hwaipy.crystics.refractivemodel.DefaultRefractiveModel;
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
    return refractiveModel.getRefractive(lambda, axis);
  }

  public Quantity N(MonochromaticWave monochromaticWave, Axis axis) {
    return getGroupRefractive(monochromaticWave, axis);
  }

  public Quantity getGroupRefractive(MonochromaticWave monochromaticWave, Axis axis) {
    Quantity lambda = monochromaticWave.getWaveLength();
    return refractiveModel.getGroupRefractive(lambda, axis);
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
    MonochromaticWave λ1 = λ("1550nm");
    MonochromaticWave λ2 = λ("1551nm");
    System.out.println(λ1.ω());
    System.out.println(λ2.ω());
    System.out.println(λ2.ω().minus(λ1.ω()));
//    Quantity ω1 = Quantity.of("2.414937906806222E15s^-1");
//    Quantity dω = Quantity.of("0.00000001E15s^-1");
//    Quantity ω2 = ω1.add(dω);
//    Quantity ω3 = ω2.add(dω);
//    MonochromaticWave w1 = ω(ω1);
//    MonochromaticWave w2 = ω(ω2);
//    MonochromaticWave w3 = ω(ω3);
//w1.k
  }

}
