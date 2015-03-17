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
import static com.hwaipy.quantity.UnitPrefixs.*;

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

  /**
   * Test of getValue method, of class Quantity.
   */
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

}
