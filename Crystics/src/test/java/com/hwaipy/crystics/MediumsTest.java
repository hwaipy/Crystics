/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

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
  public void testInitialRegistered() {
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
  public void testRefractives() {
    Medium KTP = Mediums.getMediumBySymbol("KTiOPO4");
  }

}
