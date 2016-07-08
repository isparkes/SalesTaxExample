package com.lastminute;

import com.lastminute.model.ImportDuty;
import com.lastminute.service.ProductService;
import com.lastminute.test.ConfiguredUnitTest;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportDutyTest extends ConfiguredUnitTest {

  @Autowired
  ProductService productService;
  
  private final BigDecimal importDutyExempted = new BigDecimal(0);
  private final BigDecimal importDutyDefault = new BigDecimal(5);
  
  @Test
  public void checkImportDutyCount() {
    int productCount = productService.getImportDutyMapList().size();
    
    assertEquals(2,productCount);
  }

  @Test
  public void checkImportDutyMapCompleteness() {
    List<ImportDuty> importDutyMapList = productService.getImportDutyMapList();
    
    for (ImportDuty importDutyMap : importDutyMapList) {
      assertNotNull(importDutyMap.getCategoryName());
      assertNotNull(importDutyMap.getTaxRatePercent());
    }
  }
  
  @Test
  public void checkImportDutyMapImported1() {
    ImportDuty importDuty = productService.getImportDutyByProductName("imported packet of headache pills");
    
    assertEquals("imported",importDuty.getCategoryName());
    assertTrue(importDuty.getTaxRatePercent().compareTo(importDutyDefault) == 0);
  }
  
  @Test
  public void checkImportDutyMapImported2() {
    ImportDuty importDuty = productService.getImportDutyByProductName("blah blah ImpORTed blah");
    
    assertEquals("imported",importDuty.getCategoryName());
    assertTrue(importDuty.getTaxRatePercent().compareTo(importDutyDefault) == 0);
  }
  
  @Test
  public void checkImportDutyMapDomestic1() {
    ImportDuty importDuty = productService.getImportDutyByProductName("");
    
    assertEquals("domestic",importDuty.getCategoryName());
    assertTrue(importDuty.getTaxRatePercent().compareTo(importDutyExempted) == 0);
  }
  
  @Test
  public void checkImportDutyMapDomestic2() {
    ImportDuty importDuty = productService.getImportDutyByProductName("something you can't buy abroad");
    
    assertEquals("domestic",importDuty.getCategoryName());
    assertTrue(importDuty.getTaxRatePercent().compareTo(importDutyExempted) == 0);
  }
}
