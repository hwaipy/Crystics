/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.refractivemodel;

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

}
