package com.hwaipy.crystics;

import static com.hwaipy.crystics.CharacteristicLines.C;
import static com.hwaipy.crystics.CharacteristicLines.Cp;
import static com.hwaipy.crystics.CharacteristicLines.D;
import static com.hwaipy.crystics.CharacteristicLines.F;
import static com.hwaipy.crystics.CharacteristicLines.Fp;
import static com.hwaipy.crystics.CharacteristicLines.d;
import static com.hwaipy.crystics.CharacteristicLines.e;
import com.hwaipy.quantity.Quantity;

/**
 * @author Hwaipy 2015-3-21
 */
public class AbbeNumber {

  public static Quantity VD(Medium medium, Axis axis) {
    Quantity nD = medium.getRefractive(D, axis);
    Quantity nF = medium.getRefractive(F, axis);
    Quantity nC = medium.getRefractive(C, axis);
    return nD.minus(Quantity.of("1")).divide(nF.minus(nC));
  }

  public static Quantity Vd(Medium medium, Axis axis) {
    Quantity nd = medium.getRefractive(d, axis);
    Quantity nF = medium.getRefractive(F, axis);
    Quantity nC = medium.getRefractive(C, axis);
    return nd.minus(Quantity.of("1")).divide(nF.minus(nC));
  }

  public static Quantity Ve(Medium medium, Axis axis) {
    Quantity ne = medium.getRefractive(e, axis);
    Quantity nF_ = medium.getRefractive(Fp, axis);
    Quantity nC_ = medium.getRefractive(Cp, axis);
    return ne.minus(Quantity.of("1")).divide(nF_.minus(nC_));
  }

}
