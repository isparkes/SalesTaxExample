package com.lastminute.service.impl;

import com.lastminute.repository.DataRepository;
import com.lastminute.service.TaxService;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceImpl implements TaxService {

  private final static Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);

  @Autowired
  private DataRepository dataRepository;
  
  private final BigDecimal ONE_HUNDRED = new BigDecimal(100); 
  @Override
  public boolean getProductCount() {
    dataRepository.getProductRowCount();
    return true;
  }

  @Override
  public BigDecimal calculateTaxAmount(BigDecimal grossAmount, BigDecimal salesTaxPercent, BigDecimal importDutyPercent) {
    BigDecimal includedSalesTax = grossAmount.multiply(salesTaxPercent).divide(ONE_HUNDRED);
    BigDecimal includedImportDuty = grossAmount.multiply(importDutyPercent).divide(ONE_HUNDRED);
    
    logger.debug("TaxCalc: Input " + grossAmount.toString() + " -> Sales Tax: " + includedSalesTax.toString() + ", import duty: " + includedImportDuty.toString());
    
    // Default rounding to 4dp
    return includedSalesTax.add(includedImportDuty).round(new MathContext(4, RoundingMode.HALF_UP));
  }

}
