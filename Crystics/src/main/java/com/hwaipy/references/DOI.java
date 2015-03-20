/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.references;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 *
 * @author Hwaipy
 */
public class DOI {

  private final String doiString;

  public DOI(String doiString) {
    this.doiString = doiString;
  }

  public String getDOIString() {
    return doiString;
  }

}
