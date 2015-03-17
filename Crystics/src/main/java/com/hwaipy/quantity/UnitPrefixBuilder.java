/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

/**
 * @author Hwaipy 2015-3-16
 */
public class UnitPrefixBuilder {

  private String prefix;
  private double factor;
  private String name;

  public UnitPrefixBuilder() {
  }

  public UnitPrefixBuilder(String prefix, String name, double factor) {
    this.prefix = prefix;
    this.name = name;
    this.factor = factor;
  }

  public UnitPrefixBuilder setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  public UnitPrefixBuilder setFactor(double factor) {
    this.factor = factor;
    return this;
  }

  public UnitPrefixBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public UnitPrefix createUnitPrefix() {
    return new UnitPrefix(prefix, name, factor);
  }

  public UnitPrefixBuilder register() {
    UnitPrefixes.register(createUnitPrefix());
    return this;
  }

}
