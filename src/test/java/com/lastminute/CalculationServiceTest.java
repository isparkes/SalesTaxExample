package com.lastminute;

import com.lastminute.service.TaxService;
import com.lastminute.test.ConfiguredUnitTest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;

public class CalculationServiceTest extends ConfiguredUnitTest {

  @Autowired
  TaxService taxService;
  
  private final BigDecimal salesTaxDefault = new BigDecimal(10);
  private final BigDecimal salesTaxExempted = new BigDecimal(0);
    
  private final BigDecimal importDutyImported = new BigDecimal(5);
  private final BigDecimal importDutyDomestic = new BigDecimal(0);
    
  @Test
  public void checkTaxCalculationExemptedDomestic() {
    BigDecimal grossAmount = new BigDecimal(12.49);
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxExempted, importDutyDomestic);
    assertTrue(salesTaxAmount.compareTo(BigDecimal.ZERO) == 0);
  }
  
  @Test
  public void checkTaxCalculationDefaultDomestic() {
    BigDecimal grossAmount = new BigDecimal(14.99);
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxDefault, importDutyDomestic);
    
    BigDecimal expected = new BigDecimal(1.4990).round(new MathContext(4, RoundingMode.HALF_UP));
    assertTrue(salesTaxAmount.compareTo(expected) == 0);
  }
  
  @Test
  public void checkTaxCalculationExemptedImported() {
    BigDecimal grossAmount = new BigDecimal(10.00);
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxExempted, importDutyImported);
    
    BigDecimal expected = new BigDecimal(0.50).round(new MathContext(4, RoundingMode.HALF_UP));
    assertTrue(salesTaxAmount.compareTo(expected) == 0);
  }
  
  @Test
  public void checkTaxCalculationDefaultImported() {
    BigDecimal grossAmount = new BigDecimal(47.50).round(new MathContext(4, RoundingMode.HALF_UP));
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxDefault, importDutyImported);
    
    BigDecimal expected = new BigDecimal(7.125).round(new MathContext(4, RoundingMode.HALF_UP));
    assertTrue(salesTaxAmount.compareTo(expected) == 0);
  }
}
