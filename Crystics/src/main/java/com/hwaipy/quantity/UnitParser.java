/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hwaipy 2015-3-17
 */
class UnitParser {

  private static final String AVAILABLE_UNIT_SEPARATOR_TIMES = "⋅*";
  private static final String AVAILABLE_UNIT_SEPARATOR_DEVIDE = "/";
  private final String unitString;

  public UnitParser(String unitString) {
    this.unitString = unitString;
  }

  public Unit parse() {
    try {
      UnitBuilder unitBuilder = new UnitBuilder();
      Pattern separatorPattern = Pattern.compile("(["
              + AVAILABLE_UNIT_SEPARATOR_TIMES + AVAILABLE_UNIT_SEPARATOR_DEVIDE + "])");
      Matcher matcher = separatorPattern.matcher(unitString);
      int position = 0;
      boolean up = true;
      while (true) {
        boolean find = matcher.find();
        int cursor = find ? matcher.start() : unitString.length();
        String subUnitString = unitString.substring(position, cursor);
        String separator = find ? matcher.group(0) : null;
        String[] split = subUnitString.split("\\^");
        if (split.length > 2) {
          throw new UnitParseException("Invalid unit: " + subUnitString);
        }
        String subUnitToken = split[0];
        int power = split.length == 2 ? Integer.parseInt(split[1]) : 1;

        up = AVAILABLE_UNIT_SEPARATOR_TIMES.contains(separator);
        position = cursor + 1;
        if (!find) {
          break;
        }
      }
    } catch (RuntimeException runtimeException) {

    }

//    String[] units = unitString.split("[" + AVAILABLE_UNIT_SEPARATOR + "]");
    return null;
  }

  public static void main(String[] args) {
    new UnitParser("m^2⋅kg⋅A^-1⋅s^-2").parse();
  }

}
