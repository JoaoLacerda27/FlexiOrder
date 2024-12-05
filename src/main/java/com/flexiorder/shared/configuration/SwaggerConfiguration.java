package com.flexiorder.shared.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "flexiorder", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP)
@OpenAPIDefinition(
        servers = {
                @Server(url = "/"),
        }
)
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("FlexiOrder API")
                .description("FlexiOrder API REST")
                .version("v1.0.0");
    }
}
