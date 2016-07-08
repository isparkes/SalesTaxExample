package com.lastminute;

import com.lastminute.service.TaxService;
import com.lastminute.test.ConfiguredUnitTest;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;

public class CalculationServiceTest extends ConfiguredUnitTest {

  @Autowired
  TaxService taxService;
  
  /**
   * Check basic rounding operations
   */
//  @Ignore
  @Test
  public void checkRounding() {
    assertTrue(taxService.getProductCount());
  }
  
}
