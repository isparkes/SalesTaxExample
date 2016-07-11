package com.lastminute;

import com.lastminute.model.LineItem;
import com.lastminute.service.TaxService;
import com.lastminute.test.ConfiguredUnitTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests the parsing of raw line items.
 * 
 * @author ian
 */
public class ProductParsingTest extends ConfiguredUnitTest {

  @Autowired
  TaxService taxService;
  
  @Test
  public void checkProductParsingDomesticBook() {
    LineItem lineItem = taxService.getLineItemFromInput("1 book at 12.49");
    
    assertEquals(1,lineItem.getQuantity());
    assertEquals("book",lineItem.getProduct().getProductName());
    assertEquals("books",lineItem.getProduct().getCategoryName());
    assertEquals("domestic",lineItem.getImportDuty().getCategoryName());
    assertTrue(checkBigDecimalResult("12.4900", lineItem.getOriginalPrice()));
  }
  
  @Test
  public void checkProductParsingDomesticMusicCD() {
    LineItem lineItem = taxService.getLineItemFromInput("1 music CD at 14.99");
    
    assertEquals(1,lineItem.getQuantity());
    assertEquals("music CD",lineItem.getProduct().getProductName());
    assertEquals("default_tax_rate",lineItem.getProduct().getCategoryName());
    assertEquals("domestic",lineItem.getImportDuty().getCategoryName());
    assertTrue(checkBigDecimalResult("14.9900",lineItem.getOriginalPrice()));
  }
  
  @Test
  public void checkProductParsingImportedChocolates1() {
    LineItem lineItem = taxService.getLineItemFromInput("1 imported box of chocolates at 10.00");
    
    assertEquals(1,lineItem.getQuantity());
    assertEquals("box of chocolates",lineItem.getProduct().getProductName());
    assertEquals("food",lineItem.getProduct().getCategoryName());
    assertEquals("imported",lineItem.getImportDuty().getCategoryName());
    assertTrue(checkBigDecimalResult("10.0000",lineItem.getOriginalPrice()));
  }
  
  @Test
  public void checkProductParsingImportedChocolates2() {
    LineItem lineItem = taxService.getLineItemFromInput("1 box of imported chocolates at 11.25");
    
    assertEquals(1,lineItem.getQuantity());
    assertEquals("box of chocolates",lineItem.getProduct().getProductName());
    assertEquals("food",lineItem.getProduct().getCategoryName());
    assertEquals("imported",lineItem.getImportDuty().getCategoryName());
    assertTrue(checkBigDecimalResult("11.2500",lineItem.getOriginalPrice()));
  }
  
  @Test
  public void performCalculationProductParsingImportedChocolates() {
    LineItem lineItem = taxService.getLineItemFromInput("1 box of imported chocolates at 11.25");
    taxService.calculateTaxAndCosts(lineItem);
    
    assertTrue(checkBigDecimalResult("11.2500",lineItem.getOriginalPrice()));
    assertTrue(checkBigDecimalResult("11.2500",lineItem.getTotalNetPrice()));
    
    // import duty only at 5% = 0.5625, BUT rounded UP to nearest 0.05 = 0.60
    assertTrue(checkBigDecimalResult("0.6000",lineItem.getCalculatedTaxContent()));
    
    // 11.25 + 0.60 = 11.8125
    assertTrue(checkBigDecimalResult("11.8500",lineItem.getCalculatedTotalPrice()));
  }
  
  @Test
  public void performCalculationProductParsingDomesticPerfume() {
    LineItem lineItem = taxService.getLineItemFromInput("1 bottle of perfume at 18.99");
    taxService.calculateTaxAndCosts(lineItem);
    
    assertTrue(checkBigDecimalResult("18.9900",lineItem.getOriginalPrice()));
    assertTrue(checkBigDecimalResult("18.9900",lineItem.getTotalNetPrice()));
    
    // sales tax at 10% = 1.899, sales tax rounding = 1.90
    assertTrue(checkBigDecimalResult("1.9000",lineItem.getCalculatedTaxContent()));
    
    // 18.99 + 1.90 = 20.89, rounded to 2 dp = 20.89
    assertTrue(checkBigDecimalResult("20.8900",lineItem.getCalculatedTotalPrice()));
  }
  
  @Test
  public void checkProductParsingNullInput() {
    LineItem lineItem = taxService.getLineItemFromInput(null);
    assertNull(lineItem);
  }
  
  @Test
  public void checkProductParsingNoInput() {
    LineItem lineItem = taxService.getLineItemFromInput("     ");
    assertNull(lineItem);
  }
  
  @Test
  public void checkProductParsingMalformedInput1() {
    LineItem lineItem = taxService.getLineItemFromInput("     1 ");
    assertNull(lineItem);
  }
  
  @Test
  public void checkProductParsingMalformedInput2() {
    LineItem lineItem = taxService.getLineItemFromInput("Not a quantity");
    assertNull(lineItem);
  }
}
