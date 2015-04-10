package com.hwaipy.crystics;

import static com.hwaipy.crystics.Axis.X;
import static com.hwaipy.crystics.Axis.Y;
import static com.hwaipy.crystics.Axis.Z;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static com.hwaipy.crystics.MonochromaticWave.λ;
import static com.hwaipy.quantity.PhysicalConstants.c;
import com.hwaipy.quantity.Quantity;
import static org.junit.Assert.*;

/**
 *
 * @author Hwaipy
 */
public class MediumsTest {

  public MediumsTest() {
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
  public void testInitialRegisteredOfKTP() {
    Medium m1 = Mediums.getMediumBySymbol("KTiOPO4");
    Medium m2 = Mediums.getMediumByName("potassium titanyl phosphate");
    Medium m3 = Mediums.getMediumByAlias("KTP");
    assertTrue(m1 == m2);
    assertTrue(m1 == m3);
    assertEquals("KTiOPO4", m1.getSymbol());
    assertEquals("potassium titanyl phosphate", m1.getName());
    assertEquals("KTP", m1.getAlias().iterator().next());
  }

  @Test
  public void testRefractivesOfKTP() {
    Medium KTP = Mediums.getMediumBySymbol("KTiOPO4");

    assertEquals(1.7680, KTP.getRefractive(λ("0.5876µm"), Axis.X).getValue(""), 0.0001);
    assertEquals(1.7379, KTP.getRefractive(λ("1.064µm"), Axis.X).getValue(""), 0.0001);
    assertEquals(1.7778, KTP.getRefractive(λ("0.5876µm"), Axis.Y).getValue(""), 0.0001);
    assertEquals(1.7455, KTP.getRefractive(λ("1.064µm"), Axis.Y).getValue(""), 0.0001);
    assertEquals(1.8737, KTP.getRefractive(λ("0.5876µm"), Axis.Z).getValue(""), 0.0001);
    assertEquals(1.8297, KTP.getRefractive(λ("1.064µm"), Axis.Z).getValue(""), 0.0001);

    assertEquals(25.56, KTP.getAbbeNumverVd(Axis.X).getValue(""), 0.01);
    assertEquals(23.65, KTP.getAbbeNumverVd(Axis.Y).getValue(""), 0.01);
    assertEquals(19.38, KTP.getAbbeNumverVd(Axis.Z).getValue(""), 0.01);

    MonochromaticWave w = λ("780nm");
    MonochromaticWave w_ = λ("780.00000001nm");
    Quantity dω = w.ω().minus(w_.ω());
    Quantity dk = w.k(KTP, Y).minus(w_.k(KTP, Y));
    Quantity groupIndex = c.divide(dω).times(dk);
    assertEquals(groupIndex.getValue(""), KTP.N(w, Y).getValue(""), 0.01);
    assertEquals(210.47, KTP.getGVD(w, Y).getValue("fs^2/mm"), 0.01);
  }

  @Test
  public void testMediumsInfo() {
    Medium fibre = Mediums.getMediumByName("Fused silica");
    MonochromaticWave w780 = λ("780nm");
    MonochromaticWave w1550 = λ("1550nm");
    assertEquals(1.4537, fibre.n(w780, X).getValue(""), 0.0001);
    assertEquals(1.4537, fibre.n(w780, Y).getValue(""), 0.0001);
    assertEquals(1.4537, fibre.n(w780, Z).getValue(""), 0.0001);
    assertEquals(1.4440, fibre.n(w1550, Z).getValue(""), 0.0001);

    Quantity gvd = fibre.getGVD(w1550, X);
    assertEquals(-27.947, gvd.getValue("fs^2/mm"), 0.001);
  }

}
