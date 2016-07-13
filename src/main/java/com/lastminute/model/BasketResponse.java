package com.lastminute.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.utils.SalesTaxNumberUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a "basket" class, in which we can hold line items
 * for evaluation.
 * 
 * @author ian
 */
@JsonPropertyOrder({ "contents", "salesTax", "total"})
public class BasketResponse {

  @JsonIgnore
  private List<LineItem> contents = new ArrayList<>();
  
  @JsonProperty("contents")
  public List<String> getOutputContents() {
    ArrayList<String> outputContents = new ArrayList<>();
    for (LineItem item : contents) {
      outputContents.add(item.getReceiptDescription());
    }
    return outputContents;
  }
  
  private BigDecimal salesTax = new BigDecimal(0);
  private BigDecimal total = new BigDecimal(0);
  
  public void addLineItem(LineItem newLineItem) {
    getContents().add(newLineItem);
    setSalesTax(getSalesTax().add(newLineItem.getCalculatedTaxContent()));
    setTotal(getTotal().add(newLineItem.getCalculatedTotalPrice()));
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    // Spit out line items
    for (LineItem item : getContents()) {
      builder.append(item.getQuantity()).append(" ");
      if (item.getImportDuty().getCategoryName().equalsIgnoreCase("imported")) {
        builder.append(item.getImportDuty().getCategoryName()).append(" ");
      }
      builder.append(item.getProduct().getProductName()).append(":");
      builder.append(SalesTaxNumberUtils.round2dp(item.getCalculatedTotalPrice())).append("\n");
    }
    
    // basket totals
    builder.append("Sales Taxes: ").append(SalesTaxNumberUtils.round2dp(getSalesTax())).append("\n");
    builder.append("Total: ").append(SalesTaxNumberUtils.round2dp(getTotal())).append("\n");
    
    return builder.toString();
  }
  
  public List<String> toStrings() {
    List<String> result = new ArrayList<>();
    
    // Spit out line items
    for (LineItem item : getContents()) {
      StringBuilder builder = new StringBuilder();
      builder.append(item.getQuantity()).append(" ");
      if (item.getImportDuty().getCategoryName().equalsIgnoreCase("imported")) {
        builder.append(item.getImportDuty().getCategoryName()).append(" ");
      }
      builder.append(item.getProduct().getProductName()).append(":");
      builder.append(SalesTaxNumberUtils.round2dp(item.getCalculatedTotalPrice()));
      result.add(builder.toString());
    }
    
    // basket totals
    result.add("Sales Taxes: " + SalesTaxNumberUtils.round2dp(getSalesTax()));
    result.add("Total: " + SalesTaxNumberUtils.round2dp(getTotal()));
    
    return result;
  }
  
  @JsonIgnore
  public int getItemCount() {
    return getContents().size();
  }
  
  public String toJsonString() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    String jsonValue = mapper.writeValueAsString(this);
    
    return jsonValue;
  }  
  
  public static BasketResponse fromJSONString(String jsonString) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    
    return mapper.readValue(jsonString, BasketResponse.class);
  }

  /**
   * @return the contents
   */
  public List<LineItem> getContents() {
    return contents;
  }

  /**
   * @param contents the contents to set
   */
  public void setContents(List<LineItem> contents) {
    this.contents = contents;
  }

  /**
   * @return the salesTax, only expose to 2dp
   */
  public BigDecimal getSalesTax() {
    return SalesTaxNumberUtils.round2dp(salesTax);
  }

  /**
   * @param salesTax the salesTax to set
   */
  public void setSalesTax(BigDecimal salesTax) {
    this.salesTax = salesTax;
  }

  /**
   * @return the total, only expose to 2dp
   */
  public BigDecimal getTotal() {
    return SalesTaxNumberUtils.round2dp(total);
  }

  /**
   * @param total the total to set
   */
  public void setTotal(BigDecimal total) {
    this.total = total;
  }
  
}
