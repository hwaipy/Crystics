/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import java.util.LinkedList;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.commons.lang3.tuple.Triple;
import static org.junit.Assert.*;

/**
 *
 * @author Hwaipy
 */
public class UnitTest {

  public UnitTest() {
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
  public void testToUnitStringAndToDimensionList() {
    LinkedList<Triple<Unit, String, String>> unitList = new LinkedList<Triple<Unit, String, String>>();
    LinkedList<Triple<Unit, String, String>> unitDList = new LinkedList<Triple<Unit, String, String>>();
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.m, "", "m"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.g, "0.001", "kg"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.s, "", "s"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.A, "", "A"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.K, "", "K"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.mol, "", "mol"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.cd, "", "cd"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.DIMENSIONLESS, "", "1"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.rad, "", "1"));
    unitDList.add(new ImmutableTriple<Unit, String, String>(Units.º, "0.017453292519943295", "1"));
    unitDList.add(new ImmutableTriple<Unit, String, String>(Units.deg, "0.017453292519943295", "1"));
    unitDList.add(new ImmutableTriple<Unit, String, String>(Units.arcmin, "2.908882086657216E-4", "1"));
    unitDList.add(new ImmutableTriple<Unit, String, String>(Units.arcsec, "4.84813681109536E-6", "1"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.sr, "", "1"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Hz, "", "s^-1"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.N, "", "m⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Pa, "", "kg⋅m^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.J, "", "m^2⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.W, "", "m^2⋅kg⋅s^-3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.C, "", "s⋅A"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.V, "", "m^2⋅kg⋅A^-1⋅s^-3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.F, "", "s^4⋅A^2⋅kg^-1⋅m^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Ω, "", "m^2⋅kg⋅A^-2⋅s^-3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Ohm, "", "m^2⋅kg⋅A^-2⋅s^-3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.S, "", "s^3⋅A^2⋅kg^-1⋅m^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Wb, "", "m^2⋅kg⋅A^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.T, "", "kg⋅A^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.H, "", "m^2⋅kg⋅s^-2⋅A^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.ºC, "", "K"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.degC, "", "K"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.lm, "", "cd"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.lx, "", "cd⋅m^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Bq, "", "s^-1"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Gy, "", "m^2⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Sv, "", "m^2⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.kat, "", "mol⋅s^-1"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.d, "86400.0", "s"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.h, "3600.0", "s"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.min, "60.0", "s"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.ºF, "0.5555555555555556", "K"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.degF, "0.5555555555555556", "K"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.cal, "4.1868", "m^2⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.eV, "1.602176565E-19", "m^2⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Btu, "1055.056", "m^2⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.erg, "1.0E-7", "m^2⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.dyn, "1.0E-5", "m⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.lbf, "4.448222", "m⋅kg⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.in, "0.0254", "m"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.ft, "0.3048", "m"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.mi, "1609.344", "m"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.atm, "101325.0", "kg⋅m^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.bar, "100000.0", "kg⋅m^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.torr, "133.3224", "kg⋅m^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.mmHg, "133.3224", "kg⋅m^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Ci, "3.7E10", "s^-1"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.acre, "4046.864798", "m^2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.acr, "100.0", "m^2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.nit, "", "cd⋅m^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.nits, "", "cd⋅m^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.sb, "10000.0", "cd⋅m^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.Mx, "1.0E-8", "m^2⋅kg⋅A^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.G, "1.0E-4", "kg⋅A^-1⋅s^-2"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.u, "1.660538921E-27", "kg"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.lb, "0.45359237", "kg"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.slug, "14.593903", "kg"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.hp, "745.6998715822702", "m^2⋅kg⋅s^-3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.gal, "0.003785411784", "m^3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.L, "0.001", "m^3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.pint, "4.73176473E-4", "m^3"));
    unitList.add(new ImmutableTriple<Unit, String, String>(Units.qt, "9.46352946E-4", "m^3"));
    for (Triple<Unit, String, String> triple : unitList) {
      assertEquals(triple.getRight(), triple.getLeft().toDimensionString());
      assertEquals(triple.getMiddle() + triple.getRight(), triple.getLeft().toUnitString());
    }
    for (Triple<Unit, String, String> triple : unitDList) {
      assertEquals(triple.getRight(), triple.getLeft().toDimensionString());
      assertEquals(triple.getMiddle(), triple.getLeft().toUnitString());
    }
  }

  @Test
  public void testCalculationList() {
    assertEquals(new UnitBuilder(null, false, 1, 2, 1, -4, -1, 0, 0, 0).createUnit(), Units.Hz.times(Units.V));
    assertEquals(new UnitBuilder(null, false, 1. / 60, 2, 1, -4, -1, 0, 0, 0).createUnit(), Units.V.devide(Units.min));
    assertEquals(new UnitBuilder(null, false, 1. / 3600, 2, 1, -5, -1, 0, 0, 0).createUnit(), Units.V.devide(Units.min, 2));
  }

}
