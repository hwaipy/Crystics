/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author Hwaipy
 */
public class Quantity {

  private final double value;
  private final Unit unit;

  public Quantity(double value, Unit unit) {
    this.value = value;
    this.unit = unit;
  }

  public double getValue() {
    return value;
  }

  public double getValueInSI() {
    return value * unit.getFactor();
  }

  public Unit getUnit() {
    return unit;
  }

  public double getValue(Unit unit) {
    if (this.unit.equalsDimension(unit)) {
      return this.value * this.unit.getFactor() / unit.getFactor();
    }
    else {
      throw new UnitDimensionMissmatchException(this.unit.toDimensionString() + "can not convert to " + unit.toDimensionString());
    }
  }

  public Quantity add(Quantity quantity) {
    if (unit.equalsDimension(quantity.getUnit())) {
      double value2 = quantity.getValueInSI();
      return new Quantity(value + value2 / unit.getFactor(), unit);
    }
    else {
      throw new UnitDimensionMissmatchException(unit.toDimensionString() + "can not add with " + quantity.getUnit().toDimensionString());
    }
  }

  public Quantity minus(Quantity quantity) {
    if (unit.equalsDimension(quantity.getUnit())) {
      double value2 = quantity.getValueInSI();
      return new Quantity(value - value2 / unit.getFactor(), unit);
    }
    else {
      throw new UnitDimensionMissmatchException(unit.toDimensionString() + "can not minus with " + quantity.getUnit().toDimensionString());
    }
  }

  public Quantity times(Quantity quantity) {
    return new Quantity(value * quantity.getValue(), unit.times(quantity.getUnit()));
  }

  public Quantity devide(Quantity quantity) {
    return new Quantity(value / quantity.getValue(), unit.devide(quantity.getUnit()));
  }

  public Quantity power(int power) {
    return new Quantity(Math.pow(value, power), Units.DIMENSIONLESS.times(unit, power));
  }

  public static Quantity of(String quantityString) {
    return new QuantityParser(quantityString).parse();
  }

  @Override
  public String toString() {
    return getValueInSI() + unit.toDimensionString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    Quantity quantity = (Quantity) obj;
    return getUnit().equalsDimension(quantity.getUnit())
            && getValueInSI() == quantity.getValueInSI();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(23, 29).append(this.getValueInSI())
            .append(this.getUnit().toDimensionString())
            .toHashCode();
  }

}
