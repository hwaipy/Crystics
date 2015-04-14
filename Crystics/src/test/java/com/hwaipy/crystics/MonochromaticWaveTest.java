package com.hwaipy.crystics;

import static com.hwaipy.crystics.MonochromaticWave.λ;
import static com.hwaipy.crystics.MonochromaticWave.ν;
import static com.hwaipy.crystics.MonochromaticWave.ω;
import com.hwaipy.quantity.Quantity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hwaipy
 */
public class MonochromaticWaveTest {

  public MonochromaticWaveTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testGetWaveLength() {
    MonochromaticWave w1 = ω("2.41493790679487e3THz");
    MonochromaticWave w2 = ν("384349305128205Hz");
    MonochromaticWave w3 = λ("780nm");

    double λ1 = w1.λ().getValue("nm");
    double ν1 = w1.ν().getValue("THz");
    double ω1 = w1.ω().getValue("THz");
    double λ2 = w2.λ().getValue("nm");
    double ν2 = w2.ν().getValue("THz");
    double ω2 = w2.ω().getValue("THz");
    double λ3 = w3.λ().getValue("nm");
    double ν3 = w3.ν().getValue("THz");
    double ω3 = w3.ω().getValue("THz");

    assertEquals(780, λ1, 0.00001);
    assertEquals(780, λ2, 0.00001);
    assertEquals(780, λ3, 0.00001);
    assertEquals(384.349305128205, ν1, 0.00001);
    assertEquals(384.349305128205, ν2, 0.00001);
    assertEquals(384.349305128205, ν3, 0.00001);
    assertEquals(2.41493790679487e3, ω1, 0.00001);
    assertEquals(2.41493790679487e3, ω2, 0.00001);
    assertEquals(2.41493790679487e3, ω3, 0.00001);
  }
}
