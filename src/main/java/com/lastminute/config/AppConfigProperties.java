package com.lastminute.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * This class gives a summary of the configuration we are using on startup.
 * 
 * @author ian
 */
@Configuration
public class AppConfigProperties implements EnvironmentAware {

  private final static Logger logger = LoggerFactory.getLogger(AppConfigProperties.class);
  
  @Value("${dumpPropertiesOnStartup}")
  private boolean dumpPropertiesOnStartup;

  @Override
  public void setEnvironment(Environment environment) {
    ConfigurableEnvironment applicationEnv = (ConfigurableEnvironment) environment;

    MutablePropertySources mutablePropertySources = applicationEnv.getPropertySources();

    boolean useGit = false;
    for (PropertySource<?> source : mutablePropertySources) {
      if (source instanceof CompositePropertySource) {
        CompositePropertySource cps = (CompositePropertySource) source;
        for (PropertySource<?> ps : cps.getPropertySources()) {
          if (ps.getName().equalsIgnoreCase("configservice")) {
            CompositePropertySource mps = (CompositePropertySource) ps;
            for (PropertySource<?> rps : mps.getPropertySources()) {
              logger.info("Git Configuration source: " + rps.getName() + "(YAML profile: " + applicationEnv.getProperty("spring.cloud.config.env") + ", Branch: " + applicationEnv.getProperty("spring.cloud.config.label") + ")");
              useGit = true;
            }
          }
        }
      }
    }
    
    if (useGit == false) {
      logger.info("No Git Configuration source found, falling back to local config. Config server: " + applicationEnv.getProperty("spring.cloud.config.uri"));
    }
    
    if (dumpPropertiesOnStartup) {
      logger.info("-------------------------------------");
      logger.info("profiles: " + Arrays.toString(applicationEnv.getActiveProfiles()));
      logger.info("-------------------------------------");
      for (PropertySource<?> entry : applicationEnv.getPropertySources()) {
        String sourceName = entry.getName();
        if (entry instanceof EnumerablePropertySource) {
          EnumerablePropertySource<?> enumerable = (EnumerablePropertySource<?>) entry;
          Map<String, Object> map = new LinkedHashMap<>();
          for (String name : enumerable.getPropertyNames()) {
            map.put(sourceName, enumerable.getProperty(name));
            logger.info(sourceName + " : " + name + " - " + enumerable.getProperty(name));
          }
        }
      }
      logger.info("-------------------------------------");
    }
    
  }
}
