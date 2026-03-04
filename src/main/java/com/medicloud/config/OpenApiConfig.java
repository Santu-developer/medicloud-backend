package com.medicloud.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.0 Configuration for MediCloud ERP.
 * <p>
 * Swagger UI available at: /swagger-ui/index.html
 * OpenAPI JSON spec at:    /v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private int serverPort;

    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

    @Bean
    public OpenAPI medicloudOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MediCloud ERP API")
                        .description("""
                                Cloud-based Medical Store ERP SaaS Backend.
                                
                                **Features:** Multi-tenant architecture, billing, inventory management,
                                subscription management, role-based access control (OWNER / ADMIN / STAFF / SUPER_ADMIN).
                                
                                **Authentication:** All protected endpoints require a JWT Bearer token.
                                Use the **Authorize** button to enter your token.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MediCloud Dev Team")
                                .email("support@medicloud.com"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://medicloud.com/terms")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Local Development Server")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT token obtained from /auth/login or /superadmin/login")));
    }
}
