package com.lastminute.service.impl;

import com.lastminute.model.BasketRequest;
import com.lastminute.model.BasketResponse;
import com.lastminute.utils.SalesTaxNumberUtils;
import com.lastminute.model.LineItem;
import com.lastminute.service.ProductService;
import com.lastminute.service.TaxService;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceImpl implements TaxService {

  private final static Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);

  private final BigDecimal ONE_HUNDRED = new BigDecimal(100);

  @Autowired
  ProductService productService;

  @Override
  public BigDecimal calculateTaxAmount(BigDecimal amount, BigDecimal salesTaxPercent, BigDecimal importDutyPercent) {
    BigDecimal includedSalesTax = SalesTaxNumberUtils.round4dpUpNearest5(amount.multiply(salesTaxPercent).divide(ONE_HUNDRED));
    BigDecimal includedImportDuty = SalesTaxNumberUtils.round4dpUpNearest5(amount.multiply(importDutyPercent).divide(ONE_HUNDRED));

    logger.debug("TaxCalc: Input " + amount.toString() + " -> Sales Tax: " + includedSalesTax.toString() + ", import duty: " + includedImportDuty.toString());

    // Default rounding to 4dp
    return includedSalesTax.add(SalesTaxNumberUtils.round4dp(includedImportDuty));
  }

  @Override
  public LineItem getLineItemFromInput(String input) {
    if (input == null || input.trim().isEmpty()) {
      return null;
    } else {
      try {
      Scanner dataScanner = new Scanner(input);

      // Quantity
      int qty = dataScanner.nextInt();

      // product description, stripping "imported"
      String productDesc = "";
      boolean imported = false;
      boolean foundAt = false;
      while (dataScanner.hasNext() && !foundAt) {
        String tmpProductNamePart = dataScanner.next();
        if (tmpProductNamePart.equalsIgnoreCase("at")) {
          foundAt = true;
        } else {
          if (tmpProductNamePart.equalsIgnoreCase("imported")) {
            imported = true;
          } else {
            productDesc += tmpProductNamePart;
            productDesc += " ";
          }
        }
      }

      // Original cost
      double originalCost = dataScanner.nextDouble();

      // Put it all together
      LineItem lineItem = new LineItem();
      if (imported) {
        lineItem.setImportDuty(productService.getImportDutyByProductName("imported"));
      } else {
        lineItem.setImportDuty(productService.getImportDutyByProductName("domestic"));
      }

      // Impose our internal rounding to 4dp, make sure we have all 4 dp
      lineItem.setOriginalPrice(SalesTaxNumberUtils.round4dp(new BigDecimal(originalCost)).setScale(4));

      lineItem.setProduct(productService.getProductMapByProductName(productDesc));

      lineItem.setQuantity(qty);

      return lineItem;
      } catch (NoSuchElementException ex) {
        logger.error("Could not parse input <"+input+">. No such element.");
        return null;
      }
    }
  }

  @Override
  public void calculateTaxAndCosts(LineItem lineItem) {
    // Calculate out the total net price
    lineItem.setTotalNetPrice(lineItem.getOriginalPrice().multiply(new BigDecimal(lineItem.getQuantity())));

    BigDecimal taxContent = SalesTaxNumberUtils.round4dp(calculateTaxAmount(lineItem.getTotalNetPrice(), lineItem.getProduct().getTaxRatePercent(), lineItem.getImportDuty().getTaxRatePercent()));

    lineItem.setCalculatedTaxContent(taxContent);
    lineItem.setCalculatedTotalPrice(lineItem.getTotalNetPrice().add(taxContent));
  }

  @Override
  public BasketResponse performTaxationOnBasket(BasketRequest basketRequestBody) {
    BasketResponse response = new BasketResponse();
    for (String item : basketRequestBody.getContents()) {
      LineItem lineItem = getLineItemFromInput(item);
      calculateTaxAndCosts(lineItem);
      response.addLineItem(lineItem);
    }
    return response;
  }
}
