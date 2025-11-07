package com.alura.foro_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para la documentación de la API.
 * Proporciona información detallada sobre los endpoints y modelos de datos.
 * Sigue el patrón Configuration para centralizar la configuración de documentación.
 */
@Configuration
public class OpenApiConfig {
    
    /**
     * Configura la información de la API OpenAPI.
     * 
     * @return configuración de OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Foro Alura API")
                        .description("API REST para el foro de la plataforma Alura. " +
                                   "Permite a los estudiantes publicar dudas y preguntas sobre cursos específicos.")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo")
                ));
    }
}
