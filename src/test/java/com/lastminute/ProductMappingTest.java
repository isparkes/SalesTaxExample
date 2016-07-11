package com.lastminute;

import com.lastminute.model.ProductMap;
import com.lastminute.service.ProductService;
import com.lastminute.test.ConfiguredUnitTest;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductMappingTest extends ConfiguredUnitTest {

  @Autowired
  ProductService productService;
  
  private final BigDecimal salesTaxExempted = new BigDecimal(0);
  private final BigDecimal salesTaxDefault = new BigDecimal(10);
  
  @Test
  public void checkProductCount() {
    int productCount = productService.getProductMapList().size();
    
    assertEquals(6,productCount);
  }
  
  @Test
  public void checkProductMapCompleteness() {
    List<ProductMap> productMapList = productService.getProductMapList();
    
    for (ProductMap productMap : productMapList) {
      assertNotNull(productMap.getProductName());
      assertNotNull(productMap.getCategoryName());
      assertNotNull(productMap.getTaxRateName());
      assertNotNull(productMap.getTaxRatePercent());
    }
  }
  
  @Test
  public void checkProductMapByName() {
    List<ProductMap> productMapList = productService.getProductMapList();
    
    for (ProductMap productMap : productMapList) {
      assertNotNull(productMap.getProductName());
      assertNotNull(productMap.getCategoryName());
      assertNotNull(productMap.getTaxRateName());
      assertNotNull(productMap.getTaxRatePercent());
    }
  }
  
  @Test
  public void checkProductMapBook() {
    ProductMap product = productService.getProductMapByProductName("BoOk");
    
    assertEquals("book",product.getProductName());
    assertEquals("books",product.getCategoryName());
    assertEquals("exempt",product.getTaxRateName());
    assertTrue(product.getTaxRatePercent().compareTo(salesTaxExempted) == 0);
  }
  
  @Test
  public void checkProductMapDefault() {
    ProductMap product = productService.getProductMapByProductName("music CD");
    
    assertEquals("music CD",product.getProductName());
    assertEquals("default_tax_rate",product.getCategoryName());
    assertEquals("default_tax_rate",product.getTaxRateName());
    assertTrue(product.getTaxRatePercent().compareTo(salesTaxDefault) == 0);
  }
  
  @Test
  public void checkProductMapFood() {
    ProductMap product = productService.getProductMapByProductName("    CHOCOLATE BAR   ");
    
    assertEquals("chocolate bar",product.getProductName());
    assertEquals("food",product.getCategoryName());
    assertEquals("exempt",product.getTaxRateName());
    assertTrue(product.getTaxRatePercent().compareTo(salesTaxExempted) == 0);
  }
  
  @Test
  public void checkProductMapMedicine() {
    ProductMap product = productService.getProductMapByProductName("packet of headache pills");
    
    assertEquals("packet of headache pills",product.getProductName());
    assertEquals("medicines",product.getCategoryName());
    assertEquals("exempt",product.getTaxRateName());
    assertTrue(product.getTaxRatePercent().compareTo(salesTaxExempted) == 0);
  }
  
  @Test
  public void checkProductMapNotFounf() {
    ProductMap product = productService.getProductMapByProductName("this is not the product you are looking for");
    
    assertNull(product);
  }
}
