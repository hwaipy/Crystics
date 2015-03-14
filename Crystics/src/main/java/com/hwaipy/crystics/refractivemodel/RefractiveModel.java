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
public interface RefractiveModel {

  public Reference getReference();

  public Range getRange();

}
