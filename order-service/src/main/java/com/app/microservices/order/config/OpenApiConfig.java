package com.app.microservices.order.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI orderServiceAPI(){
        return new OpenAPI().info(new Info()
                .title("Order Service API")
                .description("This is the Rest API for Order Service")
                .license(new License().name("MIT"))
                .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Order Service Wiki Documentation")
                        .url("https://order-service-dummy-url.com/docs"));
    }
}
