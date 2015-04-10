/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preserve;

import com.hwaipy.crystics.MonochromaticWave;

/**
 * @author Hwaipy 2015-4-4
 */
public class PreservedMethods {

  public static final double pp = 42E-6;

  public static double[] getParaLamdas(double lamdaPump) {
//    double kPump = Light.k(lamdaPump, true);
//    double kPP = 2 * Math.PI / pp;
//    double minPhaseMismatch = Double.MAX_VALUE;
//    double matchestLamdaSignal = 0;
//    for (double lamdaSignal = 600e-9; lamdaSignal < 1.5e-6; lamdaSignal += 1e-10) {
//      double lamdaIdle = 1 / (1 / lamdaPump - 1 / lamdaSignal);
//      double kSignal = Light.k(lamdaSignal, true);
//      double kIdle = Light.k(lamdaIdle, false);
//      double phaseMismatch = Math.abs(kPump - kSignal - kIdle - kPP);
//      if (phaseMismatch < minPhaseMismatch) {
//        minPhaseMismatch = phaseMismatch;
//        matchestLamdaSignal = lamdaSignal;
//      }
//    }
//    return new double[]{matchestLamdaSignal, 1 / (1 / lamdaPump - 1 / matchestLamdaSignal)};
    return null;
  }

}
