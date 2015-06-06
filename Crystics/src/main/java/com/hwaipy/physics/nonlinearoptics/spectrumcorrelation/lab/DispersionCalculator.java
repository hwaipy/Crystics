package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation.lab;

import com.hwaipy.crystics.Mediums;
import com.hwaipy.crystics.MonochromaticWave;
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

/**
 *
 * @author Administrator 2015-6-5
 */
public class DispersionCalculator {

  private static final Quantity FIBER_LENGTH = Q("100km");//km
  private static final MonochromaticWave CENTER_S = MonochromaticWave.λ("1550nm");

  public static void main(String[] args) throws IOException {
//    double minOmigaS = 1549;
//    double maxOmigaS = 1551;
//    double minOmigaI = 1549;
//    double maxOmigaI = 1551;
    Quantity tou02 = Q("-28ps^2/km").multiply(FIBER_LENGTH);

    CorrelationFunction functionPump = new PumpFunction(775, 0.36);
    CorrelationFunction functionPhaseMatch = new QuasiPhaseMatchFunction(Mediums.getMediumByAlias("KTP"), 30, -45.04);
    CorrelationFunction functionJoin = new JointFunction(functionPhaseMatch, functionPump);
//    plot(functionJoin, "filtered", minOmigaS, maxOmigaS, minOmigaI, maxOmigaI);

    DispersionCalculator dc = new DispersionCalculator(functionJoin, tou02, Q("4ns"), Q("0s"), Q("0s"), Q("1549nm"), Q("1551nm"), 200, 200);
//    for (int i = -200; i <= 200; i++) {
//      double delta = 0.01145;
//      minOmigaS = 1550 - delta;
//      maxOmigaS = 1550 + delta;
//      minOmigaI = 1550 - delta;
//      maxOmigaI = 1550 + delta;
//      System.out.println((0.15 / 200 * i) + "\t" + HOM_dispertion4(functionJoin, 0.15 / 200 * i, 2800e-24));
//    }

//    dc.outputVFT0();
  }

  private final CorrelationFunction phy;
  private final Quantity tou02;
  private final Quantity Td;
  private final Quantity T;
  private final Quantity deltaT;
  private final Quantity w2Min;
  private final Quantity w2Max;
  private final int stepOfT0;
  private final int stepOfW;
  private final Quantity[] vT0;
  private final double[] vfT0;

  public DispersionCalculator(CorrelationFunction phy, Quantity tou02, Quantity Td, Quantity T, Quantity deltaT,
          Quantity w2min, Quantity w2max, int stepOfT0, int stepOfW) {
    this.phy = phy;
    this.tou02 = tou02;
    this.Td = Td;
    this.T = T;
    this.deltaT = deltaT;
    this.w2Min = w2min;
    this.w2Max = w2max;
    this.stepOfT0 = stepOfT0;
    this.stepOfW = stepOfW;
    vT0 = new Quantity[stepOfT0];
    vfT0 = new double[stepOfT0];

    init();
  }

  private void init() {
    //Init f(T0)
    Quantity binOfT0 = Td.multiply(2. / (vfT0.length - 1));
    for (int i = 0; i < vfT0.length; i++) {
      final Quantity T0 = binOfT0.multiply(i).minus(Td);
      DirectIntegrator.D1 fT0 = new DirectIntegrator.D1(w2Min, w2Max, stepOfW) {
        private final MonochromaticWave w1 = CENTER_S.shiftByAngularFrequency(T0.divide(tou02));

        @Override
        public Quantity value(Quantity w2) {
          double p = valueOfPhy(w1.λ(), w2);
          return new Quantity(p * p, U("m^-1"));
        }
      };
      vT0[i] = T0;
      vfT0[i] = fT0.integrate().getValue(Units.DIMENSIONLESS);
    }
  }

  public double calculateForV() {
    double r = 0;
    for (int i = 0; i < vT0.length; i++) {
      r += calculateForV(vT0[i], vfT0[i]);
    }
    return r;
  }

  public double calculateForV(Quantity T0, double fT0) {
    double[][] vPhy = new double[stepOfW][stepOfW];
    double[][] vPhyDT = new double[stepOfW][stepOfW];
    Quantity t1Min = T0.minus(T.divide(2));
    Quantity t1Max = T0.plus(T.divide(2));
    Quantity binT1 = t1Max.minus(t1Min).divide(stepOfW - 1);
    Quantity binW2 = w2Max.minus(w2Min).divide(stepOfW - 1);

    return 0;
  }

  public void outputVFT0() {
    System.out.println("ps\t");
    for (int i = 0; i < vfT0.length; i++) {
      Quantity binOfT0 = Td.multiply(2. / (vfT0.length - 1));
      final Quantity T0 = binOfT0.multiply(i).minus(Td);
      System.out.println(T0.getValue("ps") + "\t" + vfT0[i]);
    }
  }

//  public void speedTest() {
//    long t1 = System.nanoTime();
//    for (int i = 0; i < stepOfW * stepOfW; i++) {
//      valueOfPhy(w2min, w2max);
//    }
//    long t2 = System.nanoTime();
//    for (int i11 = 0; i11 < stepOfW; i11++) {
//      for (int i12 = 0; i12 < stepOfW; i12++) {
//        for (int i21 = 0; i21 < stepOfW; i21++) {
//          for (int i22 = 0; i22 < stepOfW; i22++) {
//            double d = vPhy[i11][i22] * vPhy[i11][i21] * vPhy[i12][i22] * vPhy[i12][i21];
//          }
//        }
//      }
//    }
//    long t3 = System.nanoTime();
//    System.out.println((t2 - t1) / 1.e9);
//    System.out.println((t3 - t2) / 1.e9);
//  }
  private double valueOfPhy(Quantity lamda1, Quantity lamda2) {
    return phy.value(lamda1.getValue("nm"), lamda2.getValue("nm"));
  }

  private static void plot(CorrelationFunction function, String name, double minOmigaS, double maxOmigaS, double minOmigaI, double maxOmigaI) throws IOException {
    CorrelationPloter correlationPloter = new CorrelationPloter(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, 1000, 1000);
    correlationPloter.calculate();
    BufferedImage image = correlationPloter.createImage();
    Path path = Paths.get("./input-output/");
    ImageIO.write(image, "png", new File(path.toFile(), name + ".png"));
  }
}
