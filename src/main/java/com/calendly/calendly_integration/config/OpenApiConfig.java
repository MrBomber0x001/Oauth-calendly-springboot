package com.calendly.calendly_integration.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Calendly Integration API",
                version = "1.0",
                description = "API for integration with Calendly - Usef for POC purposes only"
        )
)
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Calendly Integration API")
                        .version("1.0")
                        .description("Spring Boot API for Calendly Integration"));
    }
}
