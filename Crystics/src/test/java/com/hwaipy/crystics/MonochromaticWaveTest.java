/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

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
    double lambda1 = MonochromaticWave.byAngularFrequency(Quantity.of("2.41493790679487e3THz")).getWaveLength().getValue("nm");
    double lambda2 = MonochromaticWave.byFrequency(Quantity.of("384349305128205Hz")).getWaveLength().getValue("nm");
    double lambda3 = MonochromaticWave.byWaveLength(Quantity.of("780nm")).getWaveLength().getValue("nm");

    MonochromaticWave w4 = MonochromaticWave.byWaveLength(Quantity.of("1.55µm"));
    w4.setAngularFrequency(Quantity.of("2.41493790679487e3THz"));
    double lambda4 = w4.getWaveLength().getValue("nm");

    MonochromaticWave w5 = MonochromaticWave.byWaveLength(Quantity.of("1.55µm"));
    w5.setFrequency(Quantity.of("384349305128205Hz"));
    double lambda5 = w5.getWaveLength().getValue("nm");

    MonochromaticWave w6 = MonochromaticWave.byWaveLength(Quantity.of("1.55µm"));
    w6.setWaveLength(Quantity.of("780nm"));
    double lambda6 = w6.getWaveLength().getValue("nm");

    assertEquals(780, lambda1, 0.00001);
    assertEquals(780, lambda2, 0.00001);
    assertEquals(780, lambda3, 0.00001);
    assertEquals(780, lambda4, 0.00001);
    assertEquals(780, lambda5, 0.00001);
    assertEquals(780, lambda6, 0.00001);
  }

}
