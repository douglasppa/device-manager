package com.douglas.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner().withUserConfiguration(OpenApiConfig.class);

    @Test
    void shouldLoadOpenApiBean() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(OpenAPI.class);
            OpenAPI openAPI = context.getBean(OpenAPI.class);
            assertThat(openAPI.getInfo().getTitle()).isEqualTo("Device Manager API");
            assertThat(openAPI.getExternalDocs().getUrl()).contains("github.com/douglasppa");
        });
    }
}
