package com.lastminute;

import com.lastminute.model.LineItem;
import com.lastminute.service.TaxService;
import com.lastminute.test.ConfiguredUnitTest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import static org.junit.Assert.assertEquals;
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
    assertTrue(checkBigDecimalResult("12.49", lineItem.getOriginalPrice()));
  }
  
  @Test
  public void checkProductParsingDomesticMusicCD() {
    LineItem lineItem = taxService.getLineItemFromInput("1 music CD at 14.99");
    
    assertEquals(1,lineItem.getQuantity());
    assertEquals("music CD",lineItem.getProduct().getProductName());
    assertEquals("default_tax_rate",lineItem.getProduct().getCategoryName());
    assertEquals("domestic",lineItem.getImportDuty().getCategoryName());
    assertTrue(checkBigDecimalResult("14.99",lineItem.getOriginalPrice()));
  }
  
  @Test
  public void checkProductParsingImportedChocolates1() {
    LineItem lineItem = taxService.getLineItemFromInput("1 imported box of chocolates at 10.00");
    
    assertEquals(1,lineItem.getQuantity());
    assertEquals("box of chocolates",lineItem.getProduct().getProductName());
    assertEquals("food",lineItem.getProduct().getCategoryName());
    assertEquals("imported",lineItem.getImportDuty().getCategoryName());
    assertTrue(lineItem.getOriginalPrice().compareTo(new BigDecimal(10.00)) == 0);
  }
  
  @Test
  public void checkProductParsingImportedChocolates2() {
    LineItem lineItem = taxService.getLineItemFromInput("1 box of imported chocolates at 11.25");
    
    assertEquals(1,lineItem.getQuantity());
    assertEquals("box of chocolates",lineItem.getProduct().getProductName());
    assertEquals("food",lineItem.getProduct().getCategoryName());
    assertEquals("imported",lineItem.getImportDuty().getCategoryName());
    assertTrue(checkBigDecimalResult("11.25",lineItem.getOriginalPrice()));
  }
  
  @Test
  public void performCalculationProductParsingImportedChocolates() {
    LineItem lineItem = taxService.getLineItemFromInput("1 box of imported chocolates at 11.25");
    taxService.calculateTaxAndCosts(lineItem);
    
    assertTrue(checkBigDecimalResult("11.25",lineItem.getOriginalPrice()));
    assertTrue(checkBigDecimalResult("11.25",lineItem.getTotalNetPrice()));
    
    // import duty only at 5% = 0.5625
    assertTrue(checkBigDecimalResult("0.5625",lineItem.getCalculatedTaxContent()));
    
    // 11.25 + 0.5625 = 11.8125
    assertTrue(checkBigDecimalResult("11.8125",lineItem.getCalculatedTotalPrice()));
  }
  
  @Test
  public void performCalculationProductParsingDomesticPerfume() {
    LineItem lineItem = taxService.getLineItemFromInput("1 bottle of perfume at 18.99");
    taxService.calculateTaxAndCosts(lineItem);
    
    assertTrue(checkBigDecimalResult("18.99",lineItem.getOriginalPrice()));
    assertTrue(checkBigDecimalResult("18.99",lineItem.getTotalNetPrice()));
    
    // sales tax at 10% = 1.899
    assertTrue(checkBigDecimalResult("1.899",lineItem.getCalculatedTaxContent()));
    
    // 18.99 + 1.899 = 20.889
    assertTrue(checkBigDecimalResult("20.889",lineItem.getCalculatedTotalPrice()));
  }
}
