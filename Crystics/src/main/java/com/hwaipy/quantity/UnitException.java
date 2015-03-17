/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

/**
 *
 * @author Hwaipy 2015-3-17
 */
public class UnitException extends RuntimeException {

  /**
   * Creates a new instance of <code>UnitException</code> without detail message.
   */
  public UnitException() {
  }

  /**
   * Constructs an instance of <code>UnitException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public UnitException(String msg) {
    super(msg);
  }

}
