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
    synchronized (UnitPrefix.class) {
      String name = unit.getToken();
      if (name == null || name.length() == 0) {
        throw new IllegalArgumentException("Con not register an anonymous Unit.");
      }
      if (unitMap.containsKey(name)) {
        throw new IllegalArgumentException("Unit " + name + " exists.");
      }
      unitMap.put(name, unit);
    }
  }

  //SI Base Units
  public static final Unit m = new UnitBuilder("m", true, 1, 1, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit g = new UnitBuilder("g", true, 1e-3, 0, 1, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit s = new UnitBuilder("s", true, 1, 0, 0, 1, 0, 0, 0, 0).register().createUnit();
  public static final Unit A = new UnitBuilder("A", true, 1, 0, 0, 0, 1, 0, 0, 0).register().createUnit();
  public static final Unit K = new UnitBuilder("K", true, 1, 0, 0, 0, 0, 1, 0, 0).register().createUnit();
  public static final Unit mol = new UnitBuilder("mol", true, 1, 0, 0, 0, 0, 0, 1, 0).register().createUnit();
  public static final Unit cd = new UnitBuilder("cd", true, 1, 0, 0, 0, 0, 0, 0, 1).register().createUnit();

  //Units
  public static final Unit DIMENSIONLESS = new UnitBuilder("DIMENSIONLESS", false, 1, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit rad = new UnitBuilder("rad", true, 1, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit º = new UnitBuilder("°", false, Math.PI / 180, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit deg = new UnitBuilder("deg", false, Math.PI / 180, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit arcmin = new UnitBuilder("′", false, Math.PI / 180 / 60, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit arcsec = new UnitBuilder("″", false, Math.PI / 180 / 3600, 0, 0, 0, 0, 0, 0, 0).register().createUnit();
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
  public static final Unit Ohm = new UnitBuilder("Ohm", true, 1, 2, 1, -3, -2, 0, 0, 0).register().createUnit();
  public static final Unit S = new UnitBuilder("S", true, 1, -2, -1, 3, 2, 0, 0, 0).register().createUnit();
  public static final Unit Wb = new UnitBuilder("Wb", true, 1, 2, 1, -2, -1, 0, 0, 0).register().createUnit();
  public static final Unit T = new UnitBuilder("T", true, 1, 0, 1, -2, -1, 0, 0, 0).register().createUnit();
  public static final Unit H = new UnitBuilder("H", true, 1, 2, 1, -2, -2, 0, 0, 0).register().createUnit();
  public static final Unit ºC = new UnitBuilder("°C", false, 1, 0, 0, 0, 0, 1, 0, 0).register().createUnit();
  public static final Unit degC = new UnitBuilder("degC", false, 1, 0, 0, 0, 0, 1, 0, 0).register().createUnit();
  public static final Unit lm = new UnitBuilder("lm", true, 1, 0, 0, 0, 0, 0, 0, 1).register().createUnit();
  public static final Unit lx = new UnitBuilder("lx", true, 1, -2, 0, 0, 0, 0, 0, 1).register().createUnit();
  public static final Unit Bq = new UnitBuilder("Bq", true, 1, 0, 0, -1, 0, 0, 0, 0).register().createUnit();
  public static final Unit Gy = new UnitBuilder("Gy", true, 1, 2, 0, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit Sv = new UnitBuilder("Sv", true, 1, 2, 0, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit kat = new UnitBuilder("kat", true, 1, 0, 0, -1, 0, 0, 1, 0).register().createUnit();
  public static final Unit d = new UnitBuilder("d", false, 86400, 0, 0, 1, 0, 0, 0, 0).register().createUnit();
  public static final Unit h = new UnitBuilder("h", false, 3600, 0, 0, 1, 0, 0, 0, 0).register().createUnit();
  public static final Unit min = new UnitBuilder("min", false, 60, 0, 0, 1, 0, 0, 0, 0).register().createUnit();
  public static final Unit ºF = new UnitBuilder("°F", false, 5. / 9, 0, 0, 0, 0, 1, 0, 0).register().createUnit();
  public static final Unit degF = new UnitBuilder("degF", false, 5. / 9, 0, 0, 0, 0, 1, 0, 0).register().createUnit();
  public static final Unit cal = new UnitBuilder("cal", true, 4.1868, 2, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit eV = new UnitBuilder("eV", true, 1.602176565e-19, 2, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit Btu = new UnitBuilder("Btu", false, 1055.056, 2, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit erg = new UnitBuilder("erg", true, 1e-7, 2, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit dyn = new UnitBuilder("dyn", true, 1e-5, 1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit lbf = new UnitBuilder("lbf", false, 4.448222, 1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit in = new UnitBuilder("in", false, 0.0254, 1, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit ft = new UnitBuilder("ft", false, 0.3048, 1, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit mi = new UnitBuilder("mi", false, 1609.344, 1, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit atm = new UnitBuilder("atm", false, 1.01325e5, -1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit bar = new UnitBuilder("bar", true, 1e5, -1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit torr = new UnitBuilder("torr", true, 133.3224, -1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit mmHg = new UnitBuilder("mmHg", false, 133.3224, -1, 1, -2, 0, 0, 0, 0).register().createUnit();
  public static final Unit Ci = new UnitBuilder("Ci", true, 3.7e10, 0, 0, -1, 0, 0, 0, 0).register().createUnit();
  public static final Unit acre = new UnitBuilder("acre", false, 4046.864798, 2, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit acr = new UnitBuilder("acr", true, 100, 2, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit nit = new UnitBuilder("nit", true, 1, -2, 0, 0, 0, 0, 0, 1).register().createUnit();
  public static final Unit nits = new UnitBuilder("nits", true, 1, -2, 0, 0, 0, 0, 0, 1).register().createUnit();
  public static final Unit sb = new UnitBuilder("sb", true, 1e4, -2, 0, 0, 0, 0, 0, 1).register().createUnit();
  public static final Unit Mx = new UnitBuilder("Mx", true, 1e-8, 2, 1, -2, -1, 0, 0, 0).register().createUnit();
  public static final Unit G = new UnitBuilder("G", true, 1e-4, 0, 1, -2, -1, 0, 0, 0).register().createUnit();
  public static final Unit u = new UnitBuilder("u", true, 1.660538921e-27, 0, 1, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit lb = new UnitBuilder("lb", false, 0.45359237, 0, 1, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit slug = new UnitBuilder("slug", false, 14.593903, 0, 1, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit hp = new UnitBuilder("hp", false, 745.69987158227022, 2, 1, -3, 0, 0, 0, 0).register().createUnit();
  public static final Unit gal = new UnitBuilder("gal", false, 0.003785411784, 3, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit L = new UnitBuilder("L", true, 0.001, 3, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit pint = new UnitBuilder("pint", false, 4.73176473e-4, 3, 0, 0, 0, 0, 0, 0).register().createUnit();
  public static final Unit qt = new UnitBuilder("qt", false, 9.46352946e-4, 3, 0, 0, 0, 0, 0, 0).register().createUnit();
}
