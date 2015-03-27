/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.refractivemodel;

import com.hwaipy.crystics.Axis;
import com.hwaipy.quantity.Quantity;
import com.hwaipy.references.Reference;

/**
 *
 * @author Hwaipy
 */
public class DefaultRefractiveModel implements RefractiveModel {

  private final Range range;
  private final Reference reference;
  private final RefractiveEquation reX;
  private final RefractiveEquation reY;
  private final RefractiveEquation reZ;

  public DefaultRefractiveModel(Range range, Reference reference,
          RefractiveEquation reX, RefractiveEquation reY, RefractiveEquation reZ) {
    this.range = range;
    this.reference = reference;
    this.reX = reX;
    this.reY = reY;
    this.reZ = reZ;
  }

  @Override
  public Reference getReference() {
    return reference;
  }

  @Override
  public Range getRange() {
    return range;
  }

  public Quantity getRefractive(Quantity lambda, int derivative, Axis axis) {
    return getRefractiveEquation(axis).getRefractive(lambda, derivative);
  }

  private RefractiveEquation getRefractiveEquation(Axis axis) {
    switch (axis) {
      case X:
        return reX;
      case Y:
        return reY;
      case Z:
        return reZ;
      case O:
        if (reX != reY) {
          throw new IllegalArgumentException("Biaxial medium has no O axis.");
        }
        return reZ;
      case E:
        if (reX != reY) {
          throw new IllegalArgumentException("Biaxial medium has no E axis.");
        }
        return reX;
      default:
        throw new IllegalArgumentException("Unknown axis: " + axis);
    }
  }

}
