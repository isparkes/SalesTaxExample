package com.lastminute.model;

import com.lastminute.utils.NumberUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a "basket" class, in which we can hold line items
 * for evaluation.
 * 
 * @author ian
 */
public class Basket {
  List<LineItem> contents = new ArrayList<>();
  BigDecimal salesTax = new BigDecimal(0);
  BigDecimal total = new BigDecimal(0);
  String input;
  
  public void addLineItem(LineItem newLineItem) {
    contents.add(newLineItem);
    salesTax = salesTax.add(newLineItem.getCalculatedTaxContent());
    total = total.add(newLineItem.getCalculatedTotalPrice());
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    // Spit out line items
    for (LineItem item : contents) {
      builder.append(item.getQuantity()).append(" ");
      if (item.getImportDuty().getCategoryName().equalsIgnoreCase("imported")) {
        builder.append(item.getImportDuty().getCategoryName()).append(" ");
      }
      builder.append(item.getProduct().getProductName()).append(":");
      builder.append(NumberUtils.round2dp(item.getCalculatedTotalPrice())).append("\n");
    }
    
    // basket totals
    builder.append("Sales Taxes: ").append(NumberUtils.round2dp(salesTax)).append("\n");
    builder.append("Total: ").append(NumberUtils.round2dp(total)).append("\n");
    
    return builder.toString();
  }
}
