package com.lastminute.repository;

import com.lastminute.model.ProductMap;
import com.lastminute.model.mapper.ProductRowMapper;
import java.util.List;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataRepository {

  private final static Logger logger = LoggerFactory.getLogger(DataRepository.class);

  @Autowired
  @Qualifier("datasource")
  private DataSource datasource;

  public long getProductRowCount() {
    JdbcTemplate jd = new JdbcTemplate(datasource);

    long currentProductCount = jd.queryForObject("select count(*) from products", Long.class);

    return currentProductCount;
  }

  public List<ProductMap> getProductMappings() {
    JdbcTemplate jd = new JdbcTemplate(datasource);

    List<ProductMap> products = jd.query(
            "select pr.product_name, "
          + "       pc.category_name, "
          + "       str.tax_rate_name, "
          + "       str.tax_rate_percent "
          + "from   products pr,"
          + "       product_category pc,"
          + "       sales_tax_rate str "
          + "where  pr.category_id = pc.ID "
          + "and    pc.tax_rate_id = str.ID", new ProductRowMapper());

    return products;
  }
}
