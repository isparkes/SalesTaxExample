package com.lastminute;

import com.lastminute.service.TaxService;
import com.lastminute.test.ConfiguredUnitTest;
import java.math.BigDecimal;
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
    BigDecimal grossAmount = new BigDecimal("12.49");
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxExempted, importDutyDomestic);
    assertTrue(salesTaxAmount.compareTo(BigDecimal.ZERO) == 0);
  }
  
  @Test
  public void checkTaxCalculationDefaultDomestic() {
    BigDecimal grossAmount = new BigDecimal("14.99");
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxDefault, importDutyDomestic);
    
    assertTrue(checkBigDecimalResult("1.5000", salesTaxAmount));
  }
  
  @Test
  public void checkTaxCalculationExemptedImported() {
    BigDecimal grossAmount = new BigDecimal("10.00");
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxExempted, importDutyImported);
    
    assertTrue(checkBigDecimalResult("0.5000",salesTaxAmount));
  }
  
  @Test
  public void checkTaxCalculationDefaultImported() {
    BigDecimal grossAmount = new BigDecimal("47.50");
    
    BigDecimal salesTaxAmount = taxService.calculateTaxAmount(grossAmount, salesTaxDefault, importDutyImported);
    
    assertTrue(checkBigDecimalResult("7.1500",salesTaxAmount));
  }
}
