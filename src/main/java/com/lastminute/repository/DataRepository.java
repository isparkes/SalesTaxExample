package com.lastminute.repository;

import com.lastminute.model.ImportDuty;
import com.lastminute.model.ProductMap;
import com.lastminute.model.mapper.ImportDutyRowMapper;
import com.lastminute.model.mapper.ProductRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Data repository for the Sales Tax Example. Given the complexity of the data
 * model, and the fact that it is a read only service, I have preferred hand
 * coded SQL and RowMappers to JPA. RowMappers are much easier to debug, and
 * come with less baggage than JPA.
 *
 * In a different service JPA might be more appropriate, but IMO, not here...
 *
 * @author ian
 */
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

  public List<ProductMap> getProductMapList() {
    JdbcTemplate jd = new JdbcTemplate(datasource);

    List<ProductMap> products = jd.query(
            "select   pr.product_name, "
            + "       pc.category_name, "
            + "       str.tax_rate_name, "
            + "       str.tax_rate_percent "
            + "from   products pr,"
            + "       product_category pc,"
            + "       sales_tax_rate str "
            + "where  pr.category_id = pc.ID "
            + "and    pc.tax_rate_id = str.ID "
            + "order by pr.ID", new ProductRowMapper());

    logger.debug("Retrieved " + products.size() + " product mappings.");

    return products;
  }

  public Map<String, ProductMap> getProductMapHashMap() {
    Map<String, ProductMap> result = new HashMap<>();
    for (ProductMap mapItem : getProductMapList()) {
      result.put(mapItem.getProductName().toLowerCase(), mapItem);
    }

    return result;
  }

  public List<ImportDuty> getImportDutyMapList() {
    JdbcTemplate jd = new JdbcTemplate(datasource);

    List<ImportDuty> importDuties = jd.query(
            "select   category_name, "
            + "       tax_rate_percent "
            + "from   import_duty_rate "
            + "order by ID", new ImportDutyRowMapper());

    logger.debug("Retrieved " + importDuties.size() + " product mappings.");

    return importDuties;
  }
  
  public Map<String, ImportDuty> getImportDutyMapHashMap() {
    Map<String, ImportDuty> result = new HashMap<>();
    for (ImportDuty mapItem : getImportDutyMapList()) {
      result.put(mapItem.getCategoryName().toLowerCase(), mapItem);
    }

    return result;
  }
}
