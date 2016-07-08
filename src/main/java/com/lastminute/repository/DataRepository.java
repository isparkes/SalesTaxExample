package com.lastminute.repository;

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

    // do the lookup into the overrides first
    long currentProductCount = jd.queryForObject("select count(*) from products", Long.class);

    return currentProductCount;
  }

}
