/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import com.hwaipy.crystics.input.SellmeierXMLLoader;
import static com.hwaipy.crystics.input.SellmeierXMLLoader.load;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Hwaipy 2015-3-19
 */
public class Mediums {

  private static final HashMap<String, ArrayList<Medium>> symbolMap = new HashMap<String, ArrayList<Medium>>();
  private static final HashMap<String, ArrayList<Medium>> nameMap = new HashMap<String, ArrayList<Medium>>();
  private static final HashMap<String, ArrayList<Medium>> aliasMap = new HashMap<String, ArrayList<Medium>>();
  private static final ArrayList<Medium> mediumsList = new ArrayList<Medium>();

  public static Medium getMediumBySymbol(String symbol) {
    ArrayList<Medium> list = symbolMap.get(symbol);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  public static Collection<Medium> getMediumsBySymbol(String symbol) {
    ArrayList<Medium> list = symbolMap.get(symbol);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return Collections.unmodifiableCollection(list);
  }

  public static Medium getMediumByName(String name) {
    ArrayList<Medium> list = nameMap.get(name);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  public static Collection<Medium> getMediumsByName(String name) {
    ArrayList<Medium> list = nameMap.get(name);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return Collections.unmodifiableCollection(list);
  }

  public static Medium getMediumByAlias(String alias) {
    ArrayList<Medium> list = aliasMap.get(alias);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  public static Collection<Medium> getMediumsByAlias(String alias) {
    ArrayList<Medium> list = aliasMap.get(alias);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return Collections.unmodifiableCollection(list);
  }

  public static Collection<Medium> getMediums() {
    return Collections.unmodifiableCollection(mediumsList);
  }

  public static void register(Medium medium) {
    String symbol = medium.getSymbol();
    String name = medium.getName();
    Collection<String> aliasList = medium.getAlias();
    synchronized (Mediums.class) {
      mediumsList.add(medium);
      register(medium, symbol, symbolMap);
      register(medium, name, nameMap);
      for (String alias : aliasList) {
        register(medium, alias, aliasMap);
      }
    }

  }

  private static void register(Medium medium, String id, HashMap<String, ArrayList<Medium>> map) {
    ArrayList<Medium> mappedList = map.get(id);
    if (mappedList == null) {
      mappedList = new ArrayList<Medium>();
      map.put(id, mappedList);
    }
    mappedList.add(medium);
  }

  static {
    load(SellmeierXMLLoader.class.getResourceAsStream("/com/hwaipy/crystics/Mediums.xml"));
  }

}
