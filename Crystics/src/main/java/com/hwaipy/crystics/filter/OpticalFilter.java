package com.hwaipy.crystics.filter;

import com.hwaipy.crystics.MonochromaticWave;
import com.hwaipy.quantity.Quantity;

/**
 *
 * @author Hwaipy
 */
public interface OpticalFilter {

  public Quantity transmittance(MonochromaticWave wave);
}
