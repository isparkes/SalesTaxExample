package com.lastminute.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lastminute.utils.SalesTaxNumberUtils;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * A single line item, which has the original input price and the information
 * needed for output.
 * 
 * @author ian
 */
@Component
public class LineItem {
	@NotNull
  private ProductMap product;
  
	@NotNull
  private ImportDuty importDuty;
  
	@NotNull
  private BigDecimal originalPrice;

	@NotNull
  private BigDecimal totalNetPrice;

	@NotNull
  private BigDecimal calculatedTaxContent;

	@NotNull
  private BigDecimal calculatedTotalPrice;

	@NotNull
  private int quantity;
  
  /**
   * @return the product
   */
  public ProductMap getProduct() {
    return product;
  }

  /**
   * @param product the product to set
   */
  public void setProduct(ProductMap product) {
    this.product = product;
  }

  /**
   * @return the importDuty
   */
  public ImportDuty getImportDuty() {
    return importDuty;
  }

  /**
   * @param importDuty the importDuty to set
   */
  public void setImportDuty(ImportDuty importDuty) {
    this.importDuty = importDuty;
  }

  /**
   * @return the originalPrice
   */
  public BigDecimal getOriginalPrice() {
    return originalPrice;
  }

  /**
   * @param originalPrice the originalPrice to set
   */
  public void setOriginalPrice(BigDecimal originalPrice) {
    this.originalPrice = originalPrice;
  }


  /**
   * @return the totalNetPrice
   */
  public BigDecimal getTotalNetPrice() {
    return totalNetPrice;
  }

  /**
   * @param totalNetPrice the totalNetPrice to set
   */
  public void setTotalNetPrice(BigDecimal totalNetPrice) {
    this.totalNetPrice = totalNetPrice;
  }
  
  /**
   * @return the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * @param quantity the quantity to set
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * @return the CalculatedTaxContent
   */
  public BigDecimal getCalculatedTaxContent() {
    return calculatedTaxContent;
  }

  /**
   * @param calculatedTaxContent the CalculatedTaxContent to set
   */
  public void setCalculatedTaxContent(BigDecimal calculatedTaxContent) {
    this.calculatedTaxContent = calculatedTaxContent;
  }

  /**
   * @return the CalculatedTotalPrice
   */
  public BigDecimal getCalculatedTotalPrice() {
    return calculatedTotalPrice;
  }

  /**
   * @param calculatedTotalPrice the calculatedTotalPrice to set
   */
  public void setCalculatedTotalPrice(BigDecimal calculatedTotalPrice) {
    this.calculatedTotalPrice = calculatedTotalPrice;
  }
  
  @JsonIgnore
  public String getReceiptDescription() {
    StringBuilder builder = new StringBuilder();
    builder.append("").append(getQuantity()).append(" ");
    if (getImportDuty().getCategoryName().equalsIgnoreCase("imported")) {
      builder.append(getImportDuty().getCategoryName()).append(" ");
    }
    builder.append(getProduct().getProductName()).append(": ").append(SalesTaxNumberUtils.round2dp(calculatedTotalPrice).toString());
    return builder.toString();
  }
}
