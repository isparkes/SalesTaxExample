package com.lastminute.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@Configuration
@Profile("unit-test")
public class UnitTestDataBaseConfig {

  private final static Logger logger = LoggerFactory.getLogger(UnitTestDataBaseConfig.class);

  @Value("${jdbc.driver}")
  private String jdbcDriver;
  @Value("${jdbc.url}")
  private String jdbcUrl;
  @Value("${jdbc.user}")
  private String jdbcUsername;
  @Value("${jdbc.password}")
  private String jdbcPassword;

  @Bean(name = "datasource")
  public DataSource getDataSource() throws Exception {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(jdbcDriver);
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(jdbcUsername);
    dataSource.setPassword(jdbcPassword);
    
    logger.debug("Using data source " + dataSource.toString());
    
    logger.debug("populating test data");
    populateDataSource(dataSource,jdbcDriver);
    
    return dataSource;
  }

  /**
   * Load test data. Only done in unit test and integration test profiles.
   * 
   * @param dataSource The data source to populate
   * @param driver The driver we are using, allows customisation for different dialects
   * @throws Exception 
   */
  private void populateDataSource(DataSource dataSource, String driver) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(connection, filteredEncodedResource("sql-scripts/drop-tables.sql", driver), true, true, "--", ";", "/*", "*/");
      ScriptUtils.executeSqlScript(connection, filteredEncodedResource("sql-scripts/create-tables.sql", driver), false, false, "--", ";", "/*", "*/");
      ScriptUtils.executeSqlScript(connection, filteredEncodedResource("sql-scripts/insert-test-data.sql", driver), false, false, "--", ";", "/*", "*/");
    } catch (ScriptException | SQLException | IOException e) {
      logger.error("Error populating data");
      throw e;
    }
  }

  // Apply SQL customisations for H2
  protected EncodedResource filteredEncodedResource(String location, String driver) throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    String content = IOUtils.toString(classLoader.getResourceAsStream(location));
    if (driver.contains(".h2.")) {
      content = content.replace("default current_timestamp", "default now()");
      content = content.replace("on update current_timestamp", "");
      content = content.replaceAll("ENGINE[ \\t]*=[ \\t]*[A-Za-z]*[ \\t]*DEFAULT[ \\t]*CHARSET=[A-Za-z0-9]*", "");
      content = content.replace("character set latin1", "");
    }
    EncodedResource encodedResource = new EncodedResource(new ByteArrayResource(content.getBytes()));
    return encodedResource;
  }

}
