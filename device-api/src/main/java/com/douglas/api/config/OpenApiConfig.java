package com.douglas.api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI deviceManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Device Manager API")
                        .description("REST API for managing devices and their states.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Douglas Pereira")
                                .email("douglas@email.com")
                                .url("https://github.com/douglasppa"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repository")
                        .url("https://github.com/douglasppa/device-manager"));
    }
}
