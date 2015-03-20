/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.refractivemodel;

/**
 * @author Hwaipy 2015-3-18
 */
public interface SellmeierRefractiveEquationFactory {

  /**
   *
   * @param coefficients
   * @return
   */
  public SellmeierRefractiveEquation newInstance(double... coefficients);

}
