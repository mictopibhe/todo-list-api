package pl.davidduke.todolistapi.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${api.version}")
    private String version;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TODO API")
                        .description("API for user registration and user task management")
                        .version(version)
                        .contact(
                                new Contact()
                                        .name("Oleksandr Davydiuk")
                                        .email("mictopibhe@gmail.com")
                                        .url("https://github.com/mictopibhe")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("httpBasic Authentication"))
                .schemaRequirement("httpBasic",
                        new SecurityScheme()
                                .name("httpBasic")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                );
    }
}
