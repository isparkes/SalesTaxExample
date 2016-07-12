package com.lastminute;

import com.lastminute.test.ConfiguredUnitTest;
import com.lastminute.utils.SalesTaxNumberUtils;
import java.math.BigDecimal;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test basic rounding rules.
 * 
 * @author ian
 */
public class NumberUtilsTest extends ConfiguredUnitTest {
  
  @Test
  public void checkFiveCentRounding() {
    BigDecimal input = new BigDecimal("0.5625");
    
    BigDecimal result = SalesTaxNumberUtils.round4dpUpNearest5(input);
    
    assertTrue(this.checkBigDecimalResult("0.6", result));
  }

}
