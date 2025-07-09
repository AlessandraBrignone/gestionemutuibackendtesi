package it.bribank.gestioneMutui.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestione Mutui")
                        .description("Documentazione API per Anagrafica e gestione dati utente")
                        .version("1.0.0"));
    }
}
