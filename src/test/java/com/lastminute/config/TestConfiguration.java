package com.lastminute.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
  "com.lastminute.test"
})

public class TestConfiguration extends AppConfig {
}
