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
      throw new UnitDimensionMissmatchException(this.unit.toDimensionString() + " can not be converted to " + unit.toDimensionString());
    }
  }

  public double getValue(String unit) {
    return getValue(Unit.of(unit));
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

  public Quantity times(double d) {
    return new Quantity(value * d, unit);
  }

  public Quantity times(Quantity quantity) {
    return new Quantity(value * quantity.getValue(), unit.times(quantity.getUnit()));
  }

  public Quantity divide(double d) {
    return new Quantity(value / d, unit);
  }

  public Quantity divide(Quantity quantity) {
    return new Quantity(value / quantity.getValue(), unit.divide(quantity.getUnit()));
  }

  public Quantity power(int power) {
    return new Quantity(Math.pow(value, power), Units.DIMENSIONLESS.times(unit, power));
  }

  public void assertDimention(String dimention) {
    unit.assertDimension(dimention);
  }

  public static Quantity of(String quantityString) {
    return Q(quantityString);
  }

  public static Quantity Q(String quantityString) {
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
