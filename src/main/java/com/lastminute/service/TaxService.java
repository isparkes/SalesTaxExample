package com.lastminute.service;

import com.lastminute.model.LineItem;
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
  
  /**
   * Main entry point for each line. We attempt to parse the input to
   * build an internal representation which we can use for output and taxation.
   * 
   * @param input The string we are handling
   * @return The parsed line item, ready for taxation and output
   */
  public LineItem getLineItemFromInput(String input);

  /**
   * perform the tax calculation on the given line item.
   * 
   * @param lineItem the item we are working on
   */
  public void calculateTaxAndCosts(LineItem lineItem);
  
}
