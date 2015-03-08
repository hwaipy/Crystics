/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hwaipy.crystics;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 *
 * @author Hwaipy
 */
public class TryLuaJ {

  public static void main(String[] args) {
    Globals globals = JsePlatform.standardGlobals();
    LuaValue chunk = globals.load("a=123*123");
    for (int i = 0; i < 10000000; i++) {
//      chunk.call();
      int a = 123 * 123;
    }
  }

}
