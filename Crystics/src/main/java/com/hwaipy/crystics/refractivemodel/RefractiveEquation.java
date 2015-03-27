/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics.refractivemodel;

import com.hwaipy.quantity.Quantity;

/**
 *
 * @author Hwaipy
 */
public interface RefractiveEquation {

  public Quantity getRefractive(Quantity waveLength, int derivative);

  public int getDerivativeDepth();

}
