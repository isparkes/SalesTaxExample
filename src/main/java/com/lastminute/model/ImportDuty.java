package com.lastminute.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * A single import duty tax entry.
 * 
 * @author ian
 */
@Component
public class ImportDuty {
	@NotNull
  private String categoryName;
  
	@NotNull
  private BigDecimal taxRatePercent;

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
