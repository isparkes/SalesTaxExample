package com.lastminute.service;

import java.math.BigDecimal;

/**
 * Sales Tax core calculation service.
 * 
 * @author ian
 */
public interface TaxService {
  
  /**
   * Calculate the included sales tax amount based on the gross sales amount,
   * and the sales tax and import duty. Presented as BigDecimals, because 
   * working with doubles tends to accumulate rounding errors (if we end up
   * handling large numbers/amounts).
   * 
   * 
   * @param grossAmount
   * @param salesTaxPercent
   * @param importDutyPercent
   * @return 
   */
  public BigDecimal calculateTaxAmount(BigDecimal grossAmount, BigDecimal salesTaxPercent, BigDecimal importDutyPercent);  
}
