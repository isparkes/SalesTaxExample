package com.lastminute.test;

import java.math.BigDecimal;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

public abstract class BasicTest {
	protected void assertNotEmpty(String message, Collection<?> collection) {
		assertNotNull(message, collection);
		assertTrue(message, collection.size()>0);
	}
  
  // Helper to simplify BigDecimal test result handling. When creating
  // expected value BigDecimals from doubles, the expected result undergoes 
  // an imprecise conversion, causing tests to fail. This method is a "different"
  // way to evaluate the BigDecimaöl result.
  protected boolean checkBigDecimalResult(String expected, BigDecimal result) {
    return result.toString().equals(expected);
  }
}
