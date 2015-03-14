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
public class SellmeierRefractiveModel implements RefractiveModel {

  private final Range range;

  private final Reference reference;

  public SellmeierRefractiveModel(Range range, Reference reference) {
    this.range = range;
    this.reference = reference;
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
