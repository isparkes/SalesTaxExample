package com.lastminute.config;

import java.io.InputStream;
import java.net.URL;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import({DataBaseConfig.class})
@ComponentScan(basePackages = {
  "com.lastminute.config",
  "com.lastminute.service"
})

public class AppConfig {
  
  private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @PostConstruct
  private void init() {
    logger.info("Application version: " + getApplicationVersion());
  }

  /**
   * Get the global version from the version file.
   *
   * @return the application version
   */
  private String getApplicationVersion() {
    boolean foundVersion = false;
    boolean foundBuild = false;
    boolean foundDate = true;
    String versionID = null;
    String buildVer = null;
    String buildDate = null;
    InputStream input;

    URL versionResourceFile = getClass().getResource("/VersionFile.txt");

    if (versionResourceFile == null) {
      return null;
    }

    input = getClass().getResourceAsStream("/VersionFile.txt");
    try (java.util.Scanner s = new java.util.Scanner(input)) {
	    s.useDelimiter("\\A");
	    while (s.hasNext()) {
	      String result = s.nextLine();
	      if (result.startsWith("BUILD_VERSION:")) {
	        foundVersion = true;
	        versionID = result.replaceAll("BUILD_VERSION:", "").trim();
	      }
	
	      if (result.startsWith("BUILD_HASH:")) {
	        foundBuild = true;
	        buildVer = result.replaceAll("BUILD_HASH:", "").trim();
	      }
	
	      if (result.startsWith("BUILD_DATE:")) {
	        foundDate = true;
	        buildDate = result.replaceAll("BUILD_DATE:", "").trim();
	      }
	    }
    }

    if (foundVersion && foundBuild && foundDate) {
      return "Version: " + versionID + " (" + buildDate + "), hash (" + buildVer + ")";
    } else {
      return "Version information not found";
    }
  }
}
