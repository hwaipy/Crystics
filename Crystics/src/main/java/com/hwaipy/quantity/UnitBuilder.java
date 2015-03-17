/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import static com.hwaipy.quantity.SIBaseUnit.*;

/**
 * @author Hwaipy 2015-3-16
 */
public class UnitBuilder {

  private String token = null;
  private boolean hasPrefix;
  private double factor = 1;
  private final int[] powers = new int[7];

  public UnitBuilder() {
  }

  public UnitBuilder(Unit unit) {
    doTimes(unit, 1);
  }

  public UnitBuilder(String token, boolean hasPrefix, double factor,
          int powerM, int powerKG, int powerS, int powerA,
          int powerK, int powerMOL, int powerCD) {
    this.token = token;
    this.hasPrefix = hasPrefix;
    this.factor = factor;
    powers[m.ordinal()] = powerM;
    powers[kg.ordinal()] = powerKG;
    powers[s.ordinal()] = powerS;
    powers[A.ordinal()] = powerA;
    powers[K.ordinal()] = powerK;
    powers[mol.ordinal()] = powerMOL;
    powers[cd.ordinal()] = powerCD;
  }

  public UnitBuilder setToken(String token) {
    this.token = token;
    return this;
  }

  public UnitBuilder setHasPrefix(boolean hasPrefix) {
    this.hasPrefix = hasPrefix;
    return this;
  }

  public UnitBuilder setFactor(double factor) {
    this.factor = factor;
    return this;
  }

  public UnitBuilder setPower(SIBaseUnit baseUnit, int power) {
    powers[baseUnit.ordinal()] = power;
    return this;
  }

  public UnitBuilder appandPower(SIBaseUnit baseUnit, int power) {
    powers[baseUnit.ordinal()] += power;
    return this;
  }

  public UnitBuilder times(Unit unit) {
    doTimes(unit, 1);
    return this;
  }

  public UnitBuilder times(Unit unit, int power) {
    doTimes(unit, power);
    return this;
  }

  private void doTimes(Unit unit, int power) {
    factor *= Math.pow(unit.getFactor(), power);
    for (SIBaseUnit baseUnit : SIBaseUnit.values()) {
      powers[baseUnit.ordinal()] += unit.getPower(baseUnit) * power;
    }
  }

  public UnitBuilder devide(Unit unit) {
    doDevide(unit, 1);
    return this;
  }

  public UnitBuilder devide(Unit unit, int power) {
    doDevide(unit, power);
    return this;
  }

  private void doDevide(Unit unit, int power) {
    factor /= Math.pow(unit.getFactor(), power);
    for (SIBaseUnit baseUnit : SIBaseUnit.values()) {
      powers[baseUnit.ordinal()] -= unit.getPower(baseUnit) * power;
    }
  }

  public Unit createUnit() {
    return new Unit(token, hasPrefix, factor, powers[m.ordinal()],
            powers[kg.ordinal()], powers[s.ordinal()], powers[A.ordinal()],
            powers[K.ordinal()], powers[mol.ordinal()], powers[cd.ordinal()]);
  }

  public UnitBuilder register() {
    Units.register(createUnit());
    return this;
  }

}
