package com.example.spacebookingweb.Configuration.Swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Moja aplikacja Spring Boot z dokumentacją OpenAPI")
                        .description("Ta aplikacja to przykład, jak korzystać z OpenAPI w Spring Boot")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
