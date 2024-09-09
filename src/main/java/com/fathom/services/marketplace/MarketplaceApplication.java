package com.fathom.services.marketplace;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Marketplace service",
            description =
                "This application used to manage marketplace items and marketplace comments. ",
            license = @License(name = "Apache 2.0", url = "https://fathoms.io"),
            contact =
                @Contact(
                    name = "Fathom Solutions",
                    email = "contact@fathoms.io",
                    url = "https://fathom.io")))
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MarketplaceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MarketplaceApplication.class, args);
  }
}
