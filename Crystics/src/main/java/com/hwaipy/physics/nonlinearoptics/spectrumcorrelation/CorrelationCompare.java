package com.hwaipy.physics.nonlinearoptics.spectrumcorrelation;

import com.hwaipy.crystics.filter.GaussianFilter;
import com.hwaipy.crystics.filter.BandPassFilter;
import com.hwaipy.crystics.filter.OpticalFilter;
import com.hwaipy.crystics.Mediums;
import com.hwaipy.crystics.MonochromaticWave;
import com.hwaipy.crystics.filter.FabryPerotCavity;
import com.hwaipy.quantity.Quantity;
import static com.hwaipy.quantity.Quantity.Q;
import com.hwaipy.quantity.Unit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author Hwaipy
 */
public class CorrelationCompare {

  private static final double minOmigaS = 779.5;
  private static final double maxOmigaS = 780.5;
  private static final double minOmigaI = 779.5;
  private static final double maxOmigaI = 780.5;
  private static final int width = 200;
  private static final int height = 200;
  private static final OpticalFilter bandPass780_3 = BandPassFilter.newInstanceByWaveLength("780nm", "3nm");
  private static final OpticalFilter bandPass780_1 = BandPassFilter.newInstanceByWaveLength("780nm", "1nm");
  private static final OpticalFilter gaussian390_02 = GaussianFilter.newInstanceByWaveLengthSigma(Q("390nm"), Q("0.2nm").divide(2.35));
  private static final OpticalFilter gaussian390_0015 = GaussianFilter.newInstanceByWaveLengthSigma(Q("390nm"), Q("0.015").divide(2.35));
  private static final OpticalFilter gaussian780_0030 = GaussianFilter.newInstanceByWaveLengthSigma(Q("780nm"), Q("0.030").divide(2.35));
  private static final OpticalFilter fpEtalon390_0015 = new FabryPerotCavity(Q("0.855"), Q("0.2535mm"));
  private static final OpticalFilter fpEtalon780_0030 = new FabryPerotCavity(Q("0.855"), Q("0.507mm"));

  public static void main(String[] args) throws IOException {
    double sigmaPumpA = 0.968;//nm
    double sigmaPumpI = sigmaPumpA / Math.sqrt(2);
    double FWHMPumpI = sigmaPumpI * 2.35;
//        System.out.println(FWHMPumpI);
    CorrelationFunction functionPump = new PumpFunction(390, FWHMPumpI);
    CorrelationFunction functionPhaseMatch = new QuasiPhaseMatchFunction(Mediums.getMediumByAlias("KTP"), 1, 7.9482);
    CorrelationFunction functionJoin = new JointFunction(functionPhaseMatch, functionPump);
    MonochromaticWave pdcCenter = MonochromaticWave.byWaveLength(new Quantity(780, Unit.of("nm")));
    Quantity pdcCenterOmega = pdcCenter.getAngularFrequency();
    Quantity deltaOmega = new Quantity(0.76927, Unit.of("THz"));
    Quantity pdcOmega1 = pdcCenterOmega.plus(deltaOmega);
    Quantity pdcOmega2 = pdcOmega1.plus(deltaOmega);
    double pdcWL1 = MonochromaticWave.byAngularFrequency(pdcOmega1).getWaveLength().getValue(Unit.of("nm"));
    double pdcWL2 = MonochromaticWave.byAngularFrequency(pdcOmega2).getWaveLength().getValue(Unit.of("nm"));
//        System.out.println(pdcWL);

//        double v = functionJoin.value(pdcWL, pdcWL);
//        System.out.println(v);
//        System.out.println("Pump: " + functionPump.value(pdcWL, pdcWL));
    HOMVisibilityCalculator homCalculator = new HOMVisibilityCalculator(pdcWL2, pdcWL1, pdcWL2, pdcWL1, functionJoin, 2);
    double visibility = homCalculator.calculate();
    System.out.println("HOM Visibility is " + visibility);
//        HOM(functionJoin);
  }

  private static void plot(CorrelationFunction function, String name) throws IOException {
    CorrelationPloter correlationPloter = new CorrelationPloter(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, width, height);
    correlationPloter.calculate();
    BufferedImage image = correlationPloter.createImage();
    Path path = Paths.get("./input-output/");
    ImageIO.write(image, "png", new File(path.toFile(), name + ".png"));
  }

  private static void purity(CorrelationFunction function) throws IOException {
    PurityCalculator purityCalculator = new PurityCalculator(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, width, height);
    double purity = purityCalculator.calculate(true);
    System.out.println("Purity is " + purity);
    System.out.println("g2 = " + (1 + purity));
  }

  private static void HOM(CorrelationFunction function) throws IOException {
    HOMVisibilityCalculator homCalculator = new HOMVisibilityCalculator(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, width);
    double visibility = homCalculator.calculate();
    System.out.println("HOM Visibility is " + visibility);
  }

  private static void HOMTest(CorrelationFunction function, double lamda) throws IOException {
//        HOMVisibilityCalculator homCalculator = new HOMVisibilityCalculator(minOmigaS, maxOmigaS, minOmigaI, maxOmigaI, function, width);
//        double visibility = homCalculator.calculate();
//        System.out.println("HOM Visibility is " + visibility);
    double c = function.value(lamda, lamda);
    double v = (c * c * c * c);
    System.out.println(v);
  }

}
