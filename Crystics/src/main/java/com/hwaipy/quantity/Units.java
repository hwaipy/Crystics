/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import java.util.HashMap;

/**
 * @author Hwaipy 2015-3-16
 */
public class Units {

  public static final HashMap<String, Unit> unitMap = new HashMap();

  public static void register(Unit unit) {
    System.out.println(unit);
    synchronized (UnitPrefix.class) {
      String name = unit.getToken();
      if (unitMap.containsKey(name)) {
        throw new IllegalArgumentException("Unit " + name + " exists.");
      }
      unitMap.put(name, unit);
    }
  }

  //SI Base Units
  public static final Unit m = new UnitBuilder("m", true, 1, 1, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit kg = new UnitBuilder("m", true, 1, 0, 1, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit s = new UnitBuilder("m", true, 1, 0, 0, 1, 0, 0, 0, 0).register().createUnit();
  public static final Unit A = new UnitBuilder("m", true, 1, 0, 0, 0, 1, 0, 0, 0).register().createUnit();
  public static final Unit K = new UnitBuilder("m", true, 1, 0, 0, 0, 0, 1, 0, 0).register().createUnit();
  public static final Unit mol = new UnitBuilder("m", true, 1, 0, 0, 0, 0, 0, 1, 0).register().createUnit();
  public static final Unit cd = new UnitBuilder("m", true, 1, 0, 0, 0, 0, 0, 0, 1).register().createUnit();

  //SiUnits
  public static final Unit DIMENSIONLESS = new UnitBuilder("DIMENSIONLESS", false, 1, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit rad = new UnitBuilder("rad", true, 1, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit sr = new UnitBuilder("sr", true, 1, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit Hz = new UnitBuilder("Hz", true, 1, 0, 0, -1, 0, 0, 0, 0).register().createUnit();
  public static final Unit N = new UnitBuilder("N", true, 1, 1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit Pa = new UnitBuilder("Pa", true, 1, -1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit J = new UnitBuilder("J", true, 1, 2, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit W = new UnitBuilder("W", true, 1, 2, 1, -3, 0, 0, 0, 0).register().createUnit();
  public static final Unit C = new UnitBuilder("C", true, 1, 0, 0, 1, 1, 0, 0, 0).register().createUnit();
  public static final Unit V = new UnitBuilder("V", true, 1, 2, 1, -3, -1, 0, 0, 0).register().createUnit();
  public static final Unit F = new UnitBuilder("F", true, 1, -2, -1, 4, 2, 0, 0, 0).register().createUnit();
  public static final Unit Ω = new UnitBuilder("Ω", true, 1, 2, 1, -3, -2, 0, 0, 0).register().createUnit();
  public static final Unit S = new UnitBuilder("S", true, 1, -2, -1, 3, 2, 0, 0, 0).register().createUnit();
  public static final Unit Wb = new UnitBuilder("Wb", true, 1, 2, 1, -2, -1, 0, 0, 0).register().createUnit();
  public static final Unit T = new UnitBuilder("T", true, 1, 0, 1, -2, -1, 0, 0, 0).register().createUnit();
  public static final Unit H = new UnitBuilder("H", true, 1, 2, 1, -2, -2, 0, 0, 0).register().createUnit();
  public static final Unit ºC = new UnitBuilder("°C", true, 1, 0, 0, 0, 0, 1, 0, 0).register().createUnit();
  public static final Unit lm = new UnitBuilder("lm", true, 1, 0, 0, 0, 0, 0, 0, 1).register().createUnit();
  public static final Unit lx = new UnitBuilder("lx", true, 1, -2, 0, 0, 0, 0, 0, 1).register().createUnit();
  public static final Unit Bq = new UnitBuilder("Bq", true, 1, 0, 0, -1, 0, 0, 0, 0).register().createUnit();
  public static final Unit Gy = new UnitBuilder("Gy", true, 1, 2, 0, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit Sv = new UnitBuilder("Sv", true, 1, 2, 0, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit kat = new UnitBuilder("kat", true, 1, 0, 0, -1, 0, 0, 1, 0).register().createUnit();
}
