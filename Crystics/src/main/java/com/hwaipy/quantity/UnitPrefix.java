/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

/**
 * @author Hwaipy 2015-3-15
 */
public class UnitPrefix {

  private final String prefix;
  private final double factor;
  private final String name;

  public UnitPrefix(String prefix, String name, double factor) {
    this.prefix = prefix;
    this.name = name;
    this.factor = factor;
  }

  public String getPrefix() {
    return prefix;
  }

  public double getFactor() {
    return factor;
  }

  public String getName() {
    return name;
  }

}
