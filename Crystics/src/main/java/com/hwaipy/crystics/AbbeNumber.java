/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import static com.hwaipy.crystics.MonochromaticWave.λ;
import com.hwaipy.quantity.Quantity;

/**
 * @author Hwaipy 2015-3-21
 */
public class AbbeNumber {

  private static final Quantity F_ = Quantity.of("480.0nm");
  private static final Quantity F = Quantity.of("486.1nm");
  private static final Quantity e = Quantity.of("546.073nm");
  private static final Quantity d = Quantity.of("587.5618nm");
  private static final Quantity D = Quantity.of("589.3nm");
  private static final Quantity C_ = Quantity.of("653.8nm");
  private static final Quantity C = Quantity.of("656.3nm");

  public static Quantity VD(Medium medium, Axis axis) {
    Quantity nD = medium.getRefractive(λ(D), axis);
    Quantity nF = medium.getRefractive(λ(F), axis);
    Quantity nC = medium.getRefractive(λ(C), axis);
    return nD.minus(Quantity.of("1")).divide(nF.minus(nC));
  }

  public static Quantity Vd(Medium medium, Axis axis) {
    Quantity nd = medium.getRefractive(λ(d), axis);
    Quantity nF = medium.getRefractive(λ(F), axis);
    Quantity nC = medium.getRefractive(λ(C), axis);
    return nd.minus(Quantity.of("1")).divide(nF.minus(nC));
  }

  public static Quantity Ve(Medium medium, Axis axis) {
    Quantity ne = medium.getRefractive(λ(e), axis);
    Quantity nF_ = medium.getRefractive(λ(F_), axis);
    Quantity nC_ = medium.getRefractive(λ(C_), axis);
    return ne.minus(Quantity.of("1")).divide(nF_.minus(nC_));
  }

}
