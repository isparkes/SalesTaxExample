package com.lastminute.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * The information that we return from the interface to the outside world about
 * a single customer.
 * 
 * @author ian
 */
@Component
public class ProductMap {
	@NotNull
  private String productName;
  private String categoryName;
  private String taxRateName;
  
	@NotNull
  private BigDecimal taxRatePercent;

  /**
   * @return the productName
   */
  public String getProductName() {
    return productName;
  }

  /**
   * @param productName the productName to set
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }

  /**
   * @return the categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * @param categoryName the categoryName to set
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  /**
   * @return the taxRateName
   */
  public String getTaxRateName() {
    return taxRateName;
  }

  /**
   * @param taxRateName the taxRateName to set
   */
  public void setTaxRateName(String taxRateName) {
    this.taxRateName = taxRateName;
  }

  /**
   * @return the taxRatePercent
   */
  public BigDecimal getTaxRatePercent() {
    return taxRatePercent;
  }

  /**
   * @param taxRatePercent the taxRatePercent to set
   */
  public void setTaxRatePercent(BigDecimal taxRatePercent) {
    this.taxRatePercent = taxRatePercent;
  }
  
}
