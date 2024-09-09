package com.fathom.services.marketplace.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "spring.flyway.enabled", havingValue = "true")
@Configuration
public class FlywayConfiguration {

  @Bean
  public FlywayMigrationStrategy migrationStrategy() {
    return Flyway::migrate;
  }
}
