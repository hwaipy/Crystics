/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

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

  public Unit getUnit() {
    return unit;
  }

  public Quantity add(Quantity quantity) {
    if (unit.equalsDimension(quantity.getUnit())) {

      return null;
    }
    else {
      throw new UnitDimensionMissmatchException(unit.toDimensionString() + "can not add with " + quantity.getUnit().toDimensionString());
    }
  }

}
