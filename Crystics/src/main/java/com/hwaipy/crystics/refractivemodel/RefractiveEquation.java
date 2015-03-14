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
public class RefractiveEquation {

  private static double µm;
  static double λ = 1;

  public static void main(String[] args) {
    System.out.println(RefractiveEquation.µm);
    double µm = 0;
    double Ω = 1;
    double λ = 1;
    RefractiveEquation.λ = 100;
    System.out.println(Ω);
  }

}
