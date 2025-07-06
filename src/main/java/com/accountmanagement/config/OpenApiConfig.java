package com.accountmanagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ApiResponse", new Schema<>().$ref("#/components/schemas/ApiResponse")))
                .info(new Info().title("Account Management API")
                        .description("Demo service for account registration, update and retrieval")
                        .contact(new Contact()
                                .name("Sergei Eensalu")
                                .email("sergei.eensalu@gmail.com")
                                .url("https://www.linkedin.com/in/sergeieensalu/"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Account Management Service Project")
                        .url("https://github.com/SergeiEensalu/account-management-service"));
    }
}

