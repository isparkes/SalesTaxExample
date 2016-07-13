package com.lastminute;

import com.lastminute.model.BasketResponse;
import com.lastminute.model.LineItem;
import com.lastminute.service.TaxService;
import com.lastminute.test.ConfiguredUnitTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Put it all together and see what we got.
 * 
 * @author ian
 */
public class FinalTest extends ConfiguredUnitTest {
  
  private final static Logger logger = LoggerFactory.getLogger("console");
  
  @Autowired
  TaxService taxService;

  @Test
  public void basket1() {
    String input1 = "1 book at 12.49";
    String input2 = "1 music CD at 14.99";
    String input3 = "1 chocolate bar at 0.85";
    logger.info(" ************************  INPUT  ***********************");
    logger.info(input1);
    logger.info(input2);
    logger.info(input3);
    
    BasketResponse basket = new BasketResponse();
    
    LineItem item1 = taxService.getLineItemFromInput(input1);
    taxService.calculateTaxAndCosts(item1);
    basket.addLineItem(item1);
    LineItem item2 = taxService.getLineItemFromInput(input2);
    taxService.calculateTaxAndCosts(item2);
    basket.addLineItem(item2);
    LineItem item3 = taxService.getLineItemFromInput(input3);
    taxService.calculateTaxAndCosts(item3);
    basket.addLineItem(item3);
    
    logger.info("***********************  RESULTS  **********************");
    for (String line : basket.toStrings()) {
      logger.info(line);
    }
  }

  @Test
  public void basket2() {
    String input1 = "1 imported box of chocolates at 10.00";
    String input2 = "1 imported bottle of perfume at 47.50";
    logger.info(" ************************  INPUT  ***********************");
    logger.info(input1);
    logger.info(input2);
    
    BasketResponse basket = new BasketResponse();
    
    LineItem item1 = taxService.getLineItemFromInput(input1);
    taxService.calculateTaxAndCosts(item1);
    basket.addLineItem(item1);
    LineItem item2 = taxService.getLineItemFromInput(input2);
    taxService.calculateTaxAndCosts(item2);
    basket.addLineItem(item2);
    
    logger.info("***********************  RESULTS  **********************");
    for (String line : basket.toStrings()) {
      logger.info(line);
    }
  }
  
  @Test
  public void basket3() {
    String input1 = "1 imported bottle of perfume at 27.99";
    String input2 = "1 bottle of perfume at 18.99";
    String input3 = "1 packet of headache pills at 9.75";
    String input4 = "1 box of imported chocolates at 11.25";
    logger.info(" ************************  INPUT  ***********************");
    logger.info(input1);
    logger.info(input2);
    logger.info(input3);
    logger.info(input4);
    
    BasketResponse basket = new BasketResponse();
    
    LineItem item1 = taxService.getLineItemFromInput(input1);
    taxService.calculateTaxAndCosts(item1);
    basket.addLineItem(item1);
    LineItem item2 = taxService.getLineItemFromInput(input2);
    taxService.calculateTaxAndCosts(item2);
    basket.addLineItem(item2);
    LineItem item3 = taxService.getLineItemFromInput(input3);
    taxService.calculateTaxAndCosts(item3);
    basket.addLineItem(item3);
    LineItem item4 = taxService.getLineItemFromInput(input4);
    taxService.calculateTaxAndCosts(item4);
    basket.addLineItem(item4);
    
    logger.info("***********************  RESULTS  **********************");
    for (String line : basket.toStrings()) {
      logger.info(line);
    }
    logger.info("********************************************************");
  }
}
