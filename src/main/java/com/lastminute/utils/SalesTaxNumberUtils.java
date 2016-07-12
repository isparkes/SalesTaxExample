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
public class SalesTaxNumberUtils {

  private static final BigDecimal twenty = new BigDecimal("20");

  public static BigDecimal round2dp(BigDecimal input) {
    return input.setScale(2, RoundingMode.HALF_UP);
  }
  
  public static BigDecimal round4dp(BigDecimal input) {
    return input.setScale(4, RoundingMode.HALF_UP);
  }
  
  // Sales tax rounding, round up to nearest 0.05
  // http://stackoverflow.com/questions/11815135/best-method-to-round-up-to-the-nearest-0-05-in-java/11815163
  public static BigDecimal round4dpUpNearest5(BigDecimal input) {
    return input.multiply(twenty).setScale(0, RoundingMode.UP).divide(twenty);
  }
}
