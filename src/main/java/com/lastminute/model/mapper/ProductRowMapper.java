package com.lastminute.model.mapper;

import com.lastminute.model.ProductMap;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<ProductMap> {

  @Override
  public ProductMap mapRow(ResultSet rs, int line) throws SQLException {
    ProductMap productMapRow = new ProductMap();
    productMapRow.setProductName(rs.getString("product_name"));
    productMapRow.setCategoryName(rs.getString("category_name"));
    productMapRow.setTaxRateName(rs.getString("tax_rate_name"));
    productMapRow.setTaxRatePercent(rs.getBigDecimal("tax_rate_percent"));
    
    return productMapRow;
  }
}
