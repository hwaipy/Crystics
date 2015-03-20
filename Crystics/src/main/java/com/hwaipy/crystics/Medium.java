/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import com.hwaipy.crystics.refractivemodel.DefaultRefractiveModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang3.builder.EqualsBuilder;

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

//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj) {
//      return true;
//    }
//    if (obj == null || this.getClass() != obj.getClass()) {
//      return false;
//    }
//    Medium medium = (Medium) obj;
//    return new EqualsBuilder().append(this.symbol, medium.getSymbol())
//            .append(this.name, medium.getName())
//            .append(this.factor, unit.factor)
//            .append(this.powers, unit.powers).isEquals();
//  }
}
