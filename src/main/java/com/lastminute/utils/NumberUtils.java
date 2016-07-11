package com.lastminute.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Normally would expect these to come from a centralized handling library. 
 * Trivial implementations given here.
 * 
 * @author ian
 */
public class NumberUtils {

  public static BigDecimal round2dp(BigDecimal input) {
    return input.round(new MathContext(2, RoundingMode.HALF_UP));
  }
  
  public static BigDecimal round4dp(BigDecimal input) {
    return input.round(new MathContext(4, RoundingMode.HALF_UP));
  }
}
