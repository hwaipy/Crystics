/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import static com.hwaipy.quantity.SIBaseUnit.*;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Hwaipy
 */
public class Unit {

  private final String token;
  private final boolean hasPrefix;
  private final double factor;
  private final int[] powers = new int[7];

  public Unit(String token, boolean hasPrefix, double factor,
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

  public String getToken() {
    return token;
  }

  public boolean hasPrefix() {
    return hasPrefix;
  }

  public double getFactor() {
    return factor;
  }

  public int getPower(SIBaseUnit unit) {
    return powers[unit.ordinal()];
  }

  public int[] getPowers(SIBaseUnit... units) {
    int[] result = new int[units.length];
    for (int i = 0; i < units.length; i++) {
      result[i] = powers[units[i].ordinal()];
    }
    return result;
  }

  public int[] getPowers() {
    return getPowers(m, kg, s, A, K, mol, cd);
  }

  public Unit prefix(UnitPrefix prefix) {
    if (this.hasPrefix) {
      return new UnitBuilder(this).setHasPrefix(false)
              .setFactor(factor * prefix.getFactor()).createUnit();
    }
    else {
      throw new UnitException("Unit " + this.getToken() + " can not has prefix.");
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    Unit unit = (Unit) obj;
    return new EqualsBuilder().append(this.token, unit.token)
            .append(this.hasPrefix, unit.hasPrefix)
            .append(this.factor, unit.factor)
            .append(this.powers, unit.powers).isEquals();
  }

  public boolean equalsDimension(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    Unit unit = (Unit) obj;
    return new EqualsBuilder().append(this.powers, unit.powers).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(11, 21).append(this.token)
            .append(this.hasPrefix)
            .append(this.factor)
            .append(this.powers)
            .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("token", token)
            .append("hasPrefix", hasPrefix).append("factor", factor)
            .append("powers", powers).build();
  }

  public String toDimensionString() {
    String dimensionString = doGetDimensionString();
    return dimensionString.length() == 0 ? "1" : dimensionString;
  }

  public String toUnitString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (factor != 1) {
      stringBuilder.append(factor);
    }
    stringBuilder.append(doGetDimensionString());
    if (stringBuilder.length() == 0) {
      return "1";
    }
    return stringBuilder.toString();
  }

  private String doGetDimensionString() {
    StringBuilder stringBuilder = new StringBuilder();
    ArrayList<Integer> powersList = new ArrayList<Integer>();
    SIBaseUnit[] baseUnits = SIBaseUnit.values();
    int countOfSiBaseUnit = baseUnits.length;
    for (int i = 0; i < powers.length; i++) {
      powersList.add((powers[i] + 1) * countOfSiBaseUnit - i - 1);
    }
    Collections.sort(powersList);
    Collections.reverse(powersList);
    boolean firstDimension = true;
    for (Integer powerIndex : powersList) {
      int power = powerIndex / countOfSiBaseUnit;
      int unitIndex = countOfSiBaseUnit - powerIndex % countOfSiBaseUnit - 1;
      if (unitIndex >= countOfSiBaseUnit) {
        power -= 1;
        unitIndex -= countOfSiBaseUnit;
      }
      SIBaseUnit unit = baseUnits[unitIndex];
      if (power != 0) {
        if (firstDimension) {
          firstDimension = false;
        }
        else {
          stringBuilder.append("⋅");
        }
        stringBuilder.append(unit);
        if (power != 1) {
          stringBuilder.append("^").append(power);
        }
      }
    }
    return stringBuilder.toString();
  }

  public Unit times(Unit unit) {
    return new UnitBuilder(this).times(unit).createUnit();
  }

  public Unit times(Unit unit, int power) {
    return new UnitBuilder(this).times(unit, power).createUnit();
  }

  public Unit devide(Unit unit) {
    return new UnitBuilder(this).devide(unit).createUnit();
  }

  public Unit devide(Unit unit, int power) {
    return new UnitBuilder(this).devide(unit, power).createUnit();
  }

}
