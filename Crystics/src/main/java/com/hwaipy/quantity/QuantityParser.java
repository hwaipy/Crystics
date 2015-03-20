/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.quantity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

/**
 * @author Hwaipy 2015-3-17
 */
class QuantityParser {

  private static final String AVAILABLE_UNIT_SEPARATOR_TIMES = "⋅*";
  private static final String AVAILABLE_UNIT_SEPARATOR_DIVIDE = "/";
  private final String quantityString;

  public QuantityParser(String quantityString) {
    this.quantityString = quantityString;
  }

  public Quantity parse() {
    Pattern factorPattern = Pattern.compile("^[+-]*[0-9]*(\\.[0-9]*)?([eE][+-]?[0-9]+)?");
    Matcher matcher = factorPattern.matcher(quantityString);
    boolean find = matcher.find();
    String numberString = matcher.group();
    double factor = numberString.isEmpty() ? 1 : Double.parseDouble(numberString);
    String unitString = quantityString.substring(matcher.end());
    Unit unit = parseUnit(unitString);
    return unit == null ? null : new Quantity(factor, unit);
  }

  public Unit parseUnit(String unitString) {
    UnitBuilder unitBuilder = new UnitBuilder();
    if (unitString.isEmpty()) {
      return unitBuilder.createUnit();
    }
    try {
      Pattern separatorPattern = Pattern.compile("(["
              + AVAILABLE_UNIT_SEPARATOR_TIMES + AVAILABLE_UNIT_SEPARATOR_DIVIDE + "])");
      Matcher matcher = separatorPattern.matcher(unitString);
      int position = 0;
      boolean up = true;
      while (true) {
        boolean find = matcher.find();
        int cursor = find ? matcher.start() : unitString.length();
        if (cursor != 0) {
          String subUnitString = unitString.substring(position, cursor);
          String[] split = subUnitString.split("\\^");
          if (split.length > 2) {
            throw new UnitParseException("Invalid unit: " + subUnitString);
          }
          String subUnitToken = split[0];
          int power = split.length == 2 ? Integer.parseInt(split[1]) : 1;
          if (!up) {
            power = -power;
          }
          Unit unit = parseUnitToken(subUnitToken);
          unitBuilder.times(unit, power);
        }
        String separator = find ? matcher.group(0) : null;
        up = find ? AVAILABLE_UNIT_SEPARATOR_TIMES.contains(separator) : true;
        position = cursor + 1;
        if (!find) {
          break;
        }
      }
    } catch (RuntimeException ex) {
//      LoggerFactory.getLogger(QuantityParser.class).warn("Error in parsing unitString.", ex);
      return null;
    }
    return unitBuilder.createUnit();
  }

  private Unit parseUnitToken(String subUnitToken) {
    ArrayList<Unit> units = new ArrayList<Unit>();
    for (int split = 0; split < subUnitToken.length(); split++) {
      String prefixString = subUnitToken.substring(0, split);
      String tokenString = subUnitToken.substring(split);
      UnitPrefix prefix = UnitPrefixes.get(prefixString);
      Unit unit = Units.get(tokenString);
      if (prefix != null && unit != null && (prefix == UnitPrefixes.none || unit.hasPrefix())) {
        units.add(unit.prefix(prefix));
      }
    }
    if (units.isEmpty()) {
      throw new UnitParseException("Unit token [" + subUnitToken + "] can not be parsed.");
    }
    if (units.size() >= 2) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(units.size()).append(" ambiguity on parsing [").append(subUnitToken)
              .append("]: ");
      for (Unit unit : units) {
        stringBuilder.append("{").append(unit.toUnitString()).append("}");
      }
      LoggerFactory.getLogger(QuantityParser.class).warn(stringBuilder.toString());
    }
    return units.get(0);
  }

}
