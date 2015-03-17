/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

/**
 * @author Hwaipy 2015-3-17
 */
public class UnitParseException extends UnitException {

  /**
   * Creates a new instance of <code>UnitDimensionMissmatchException</code> without detail message.
   */
  public UnitParseException() {
  }

  /**
   * Constructs an instance of <code>UnitDimensionMissmatchException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public UnitParseException(String msg) {
    super(msg);
  }

}
