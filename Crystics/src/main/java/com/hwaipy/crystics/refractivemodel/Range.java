/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.refractivemodel;

/**
 *
 * @author Hwaipy
 */
public class Range {

  private final double minimal;
  private final double maximal;

  public Range(double minimal, double maximal) {
    this.minimal = minimal;
    this.maximal = maximal;
  }

  public double getMinimal() {
    return minimal;
  }

  public double getMaximal() {
    return maximal;
  }

}
