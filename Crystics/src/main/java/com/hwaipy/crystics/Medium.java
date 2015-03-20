/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import com.hwaipy.crystics.refractivemodel.DefaultRefractiveModel;
import com.hwaipy.quantity.Quantity;
import com.hwaipy.quantity.Unit;
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

//  public double getRefractive(MonochromaticWave monochromaticWave) {
//    double lambda = monochromaticWave.getWaveLength().getValue(Unit.of("µm"));
//  }

}
