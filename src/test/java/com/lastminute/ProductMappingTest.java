package com.lastminute;

import com.lastminute.model.ProductMap;
import com.lastminute.service.ProductService;
import com.lastminute.test.ConfiguredUnitTest;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductMappingTest extends ConfiguredUnitTest {

  @Autowired
  ProductService productService;
  
  @Test
  public void checkProductCount() {
    int productCount = productService.getproductMap().size();
    
    assertEquals(6,productCount);
  }
  
  
  @Test
  public void checkProducts() {
    List<ProductMap> productMap = productService.getproductMap();
  }
  
}
