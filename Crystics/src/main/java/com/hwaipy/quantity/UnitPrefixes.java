/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Hwaipy 2015-3-15
 */
public class UnitPrefixes {

  private static final HashMap<String, UnitPrefix> prefixMap = new HashMap();
  private static final HashMap<String, UnitPrefix> nameMap = new HashMap();

  public static void register(UnitPrefix unitPrefix) {
    synchronized (UnitPrefix.class) {
      String prefix = unitPrefix.getPrefix();
      String name = unitPrefix.getName();
      if (prefixMap.containsKey(prefix)) {
        throw new IllegalArgumentException("Prefix " + prefix + " exists.");
      }
      if (nameMap.containsKey(name)) {
        throw new IllegalArgumentException("Prefix name " + name + " exists.");
      }
      prefixMap.put(prefix, unitPrefix);
      nameMap.put(name, unitPrefix);
    }
  }

  public static Collection<UnitPrefix> getRegisteredPrefixes() {
    return Collections.unmodifiableCollection(prefixMap.values());
  }

  public static final UnitPrefix deca = new UnitPrefixBuilder("da", "deca", 1e1).register().createUnitPrefix();
  public static final UnitPrefix hecto = new UnitPrefixBuilder("h", "hecto", 1e2).register().createUnitPrefix();
  public static final UnitPrefix kilo = new UnitPrefixBuilder("k", "kilo", 1e3).register().createUnitPrefix();
  public static final UnitPrefix mega = new UnitPrefixBuilder("M", "mega", 1e6).register().createUnitPrefix();
  public static final UnitPrefix giga = new UnitPrefixBuilder("G", "giga", 1e9).register().createUnitPrefix();
  public static final UnitPrefix tera = new UnitPrefixBuilder("T", "tera", 1e12).register().createUnitPrefix();
  public static final UnitPrefix peta = new UnitPrefixBuilder("P", "peta", 1e15).register().createUnitPrefix();
  public static final UnitPrefix exa = new UnitPrefixBuilder("E", "exa", 1e18).register().createUnitPrefix();
  public static final UnitPrefix zetta = new UnitPrefixBuilder("Z", "zetta", 1e21).register().createUnitPrefix();
  public static final UnitPrefix yotta = new UnitPrefixBuilder("Y", "yotta", 1e24).register().createUnitPrefix();
  public static final UnitPrefix deci = new UnitPrefixBuilder("d", "deci", 1e-1).register().createUnitPrefix();
  public static final UnitPrefix centi = new UnitPrefixBuilder("c", "centi", 1e-2).register().createUnitPrefix();
  public static final UnitPrefix milli = new UnitPrefixBuilder("m", "milli", 1e-3).register().createUnitPrefix();
  public static final UnitPrefix micro = new UnitPrefixBuilder("µ", "micro", 1e-6).register().createUnitPrefix();
  public static final UnitPrefix nano = new UnitPrefixBuilder("n", "nano", 1e-9).register().createUnitPrefix();
  public static final UnitPrefix pico = new UnitPrefixBuilder("p", "pico", 1e-12).register().createUnitPrefix();
  public static final UnitPrefix femto = new UnitPrefixBuilder("f", "femto", 1e-15).register().createUnitPrefix();
  public static final UnitPrefix atto = new UnitPrefixBuilder("a", "atto", 1e-18).register().createUnitPrefix();
  public static final UnitPrefix zepto = new UnitPrefixBuilder("z", "zepto", 1e-21).register().createUnitPrefix();
  public static final UnitPrefix yocto = new UnitPrefixBuilder("y", "yocto", 1e-24).register().createUnitPrefix();
}
