/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.hwaipy.quantity.Units.*;
import static com.hwaipy.quantity.UnitPrefixes.*;
import java.util.Collection;

/**
 *
 * @author Hwaipy
 */
public class QuantityTest {

  public QuantityTest() {
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
  public void testCalculation() {
    Quantity timeStart = new Quantity(30, s);
    Quantity timeStop = new Quantity(35, s);
    Quantity work = new Quantity(50000, J);

    Quantity duration = timeStop.minus(timeStart);
    assertEquals(5, duration.getValueInSI(), 0.0);
    assertEquals(5, duration.getValue(s), 0.0);
    assertEquals(5000000000.0, duration.getValue(s.prefix(nano)), 0.0);
    assertEquals(0.005, duration.getValue(s.prefix(kilo)), 0.0);
  }

  @Test
  public void testParser() {
    Collection<Unit> registeredUnits = Units.getRegisteredUnits();
    for (Unit unit : registeredUnits) {
      Quantity parsedQuantity = new QuantityParser(unit.toUnitString()).parse();
      assertTrue(unit.equalsDimension(parsedQuantity.getUnit()));
      assertEquals(unit.getFactor(), parsedQuantity.getValueInSI(), 0.0);
    }
    assertEquals(new Quantity(3e8, m.divide(s)), Quantity.of("3e8m/s"));
    assertEquals(new Quantity(16.7e-12, m.times(A)), Quantity.of("16.7E0mm*nA"));
  }

  @Test
  public void testAssertDimention() {
    Constants.c.assertDimention("m/s");
  }

  @Test(expected = UnitDimensionMissmatchException.class)
  public void testAssertDimentionException() {
    Constants.c.assertDimention("m");
  }

}
