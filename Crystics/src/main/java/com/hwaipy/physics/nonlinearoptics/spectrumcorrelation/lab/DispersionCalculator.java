package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.lab;

import com.hwaipy.crystics.Mediums;
import com.hwaipy.crystics.MonochromaticWave;
import static com.hwaipy.crystics.MonochromaticWave.λ;
import static com.hwaipy.crystics.MonochromaticWave.ω;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.CorrelationFunction;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.CorrelationPloter;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.JointFunction;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.PumpFunction;
import com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.QuasiPhaseMatchFunction;
import com.hwaipy.quantity.Quantity;
import static com.hwaipy.quantity.Quantity.Q;
import static com.hwaipy.quantity.Unit.U;
import com.hwaipy.quantity.Units;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import static org.apache.commons.math3.util.FastMath.*;

/**
 *
 * @author Administrator 2015-6-5
 */
public class DispersionCalculator {

  private static final Quantity FIBER_LENGTH = Q("100km");
  private static final MonochromaticWave CENTER_S = MonochromaticWave.λ("1550nm");

  public static void main(String[] args) throws IOException {
    Quantity tou02 = Q("-28ps^2/km").multiply(FIBER_LENGTH);

    CorrelationFunction functionPump = new PumpFunction(775, 0.36);
    CorrelationFunction functionPhaseMatch = new QuasiPhaseMatchFunction(Mediums.getMediumByAlias("KTP"), 30, -45.04);
    CorrelationFunction functionJoin = new JointFunction(functionPhaseMatch, functionPump);
//    plot(functionJoin, "filtered", minOmigaS, maxOmigaS, minOmigaI, maxOmigaI);

    DispersionCalculator dc = new DispersionCalculator(functionJoin, tou02, Q("4ns"), Q("100ps"), Q("100ps"), Q("80ps"), λ("1550nm").ω(), λ("1551nm").ω(), λ("1549nm").ω(), 20, 20);

    double[] ae = dc.calculateForAE(Q("0s"));
    System.out.println(ae[1]);
    System.out.println(ae[0]);
    System.out.println(ae[1] / ae[0]);
//        dc.outputVFT0();
  }

  private final CorrelationFunction phy;
  private final Quantity tou02;
  private final Quantity Td;
  private final Quantity T;
  private final Quantity deltaT1;
  private final Quantity deltaT2;
  private final Quantity w2Min;
  private final Quantity w2Max;
  private final int stepOfT0;
  private final int stepOfW;
  private final Quantity centerW1;

  public DispersionCalculator(CorrelationFunction phy, Quantity tou02, Quantity Td, Quantity T,
          Quantity deltaT1, Quantity deltaT2, Quantity centerW1,
          Quantity w2min, Quantity w2max, int stepOfT0, int stepOfW) {
    this.phy = phy;
    this.tou02 = tou02;
    this.Td = Td;
    this.T = T;
    this.deltaT1 = deltaT1;
    this.deltaT2 = deltaT2;
    this.w2Min = w2min;
    this.w2Max = w2max;
    this.stepOfT0 = stepOfT0;
    this.stepOfW = stepOfW;
    this.centerW1 = centerW1;

//    init();
  }

  public double[] calculateFT0() {
    double[] vfT0 = new double[stepOfT0];
    Quantity binOfT0 = Td.multiply(2. / (vfT0.length - 1));
    for (int i = 0; i < vfT0.length; i++) {
      final Quantity T0 = binOfT0.multiply(i).minus(Td);
      DirectIntegrator.D1 fT0 = new DirectIntegrator.D1(w2Min, w2Max, stepOfW) {
        private final MonochromaticWave w1 = CENTER_S.shiftByAngularFrequency(T0.divide(tou02));

        @Override
        public Quantity value(Quantity w2) {
          double p = valueOfPhy(w1.ω(), w2);
          return new Quantity(p * p, U("Hz^-1"));
        }

      };
      vfT0[i] = fT0.integrate().getValue(Units.DIMENSIONLESS);
    }
    return vfT0;
  }

  public double[] calculateForAE(Quantity T0) {
    double[][] vPhy = new double[stepOfW][stepOfW];
    double[][] vPhyDT1 = new double[stepOfW][stepOfW];
    double[][] vPhyDT2 = new double[stepOfW][stepOfW];
    double[][] vPhyDT12 = new double[stepOfW][stepOfW];
    Quantity t1Min = T0.minus(T.divide(2));
    Quantity t1Max = T0.plus(T.divide(2));
    Quantity binT1 = t1Max.minus(t1Min).divide(stepOfW - 1);
    Quantity binW2 = w2Max.minus(w2Min).divide(stepOfW - 1);
    for (int i1 = 0; i1 < stepOfW; i1++) {
      for (int i2 = 0; i2 < stepOfW; i2++) {
        Quantity t1 = t1Min.plus(binT1.multiply(i1));
        Quantity w2 = w2Min.plus(binW2.multiply(i2));
        Quantity w1 = t1.divide(tou02).plus(centerW1);
        Quantity w1d1 = t1.plus(deltaT1).divide(tou02).plus(centerW1);
        Quantity w1d2 = t1.plus(deltaT2).divide(tou02).plus(centerW1);
        Quantity w1d12 = t1.plus(deltaT1).plus(deltaT2).divide(tou02).plus(centerW1);
        vPhy[i1][i2] = valueOfPhy(w1, w2);
        vPhyDT1[i1][i2] = valueOfPhy(w1d1, w2);
        vPhyDT2[i1][i2] = valueOfPhy(w1d2, w2);
        vPhyDT12[i1][i2] = valueOfPhy(w1d12, w2);
      }
    }

    double A = 0;
    double E = 0;
    for (int iTheta3 = 0; iTheta3 < stepOfW; iTheta3++) {
      for (int iTheta4 = 0; iTheta4 < stepOfW; iTheta4++) {
        for (int iW2 = 0; iW2 < stepOfW; iW2++) {
          for (int iW2p = 0; iW2p < stepOfW; iW2p++) {
            double v1 = vPhyDT12[iTheta4][iW2];
            double v2 = vPhy[iTheta3][iW2p];
            double v3 = vPhyDT1[iTheta3][iW2];
            double v4 = vPhyDT2[iTheta4][iW2p];
            A += pow(v1 * v2, 2) + pow(v3 * v4, 2);
            double e = v1 * v2 * v3 * v4;
            double c = binT1.multiply(iTheta3 - iTheta4).minus(deltaT2).multiply(deltaT1).divide(tou02).getValue(Units.DIMENSIONLESS);
            E += 2 * e * cos(c);
          }
        }
      }
    }

    return new double[]{A, E};
  }

  private double valueOfPhy(Quantity omega1, Quantity omega2) {
    return phy.value(ω(omega1).λ().getValue("nm"), ω(omega2).λ().getValue("nm"));
  }

  private static void plot(CorrelationFunction function, String name, double minOmigaS, double maxOmigaS, double minOmigaI, double maxOmigaI) throws IOException {
    CorrelationPloter correlationPloter = new CorrelationPloter(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, 1000, 1000);
    correlationPloter.calculate();
    BufferedImage image = correlationPloter.createImage();
    Path path = Paths.get("./input-output/");
    ImageIO.write(image, "png", new File(path.toFile(), name + ".png"));
  }

}
