/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import static com.hwaipy.quantity.SIBaseUnit.*;

/**
 *
 * @author Hwaipy
 */
public class Unit {

  private final String token;
  private final boolean hasPrefix;
  private final double factor;
  private final int[] powers = new int[7];

  public Unit(String token, boolean hasPrefix, double factor,
          int powerM, int powerKG, int powerS, int powerA,
          int powerK, int powerMOL, int powerCD) {
    this.token = token;
    this.hasPrefix = hasPrefix;
    this.factor = factor;
    powers[m.ordinal()] = powerM;
    powers[kg.ordinal()] = powerKG;
    powers[s.ordinal()] = powerS;
    powers[A.ordinal()] = powerA;
    powers[K.ordinal()] = powerK;
    powers[mol.ordinal()] = powerMOL;
    powers[cd.ordinal()] = powerCD;
  }

  public String getToken() {
    return token;
  }

  public boolean hasPrefix() {
    return hasPrefix;
  }

  public double getFactor() {
    return factor;
  }

  public int getPower(SIBaseUnit unit) {
    return powers[unit.ordinal()];
  }

  public int[] getPowers(SIBaseUnit... units) {
    int[] result = new int[units.length];
    for (int i = 0; i < units.length; i++) {
      result[i] = powers[units[i].ordinal()];
    }
    return result;
  }

  public int[] getPowers() {
    return getPowers(m, kg, s, A, K, mol, cd);
  }

  public static void main(String[] args) {
    System.out.println(SIBaseUnit.m.ordinal());
  }

}
