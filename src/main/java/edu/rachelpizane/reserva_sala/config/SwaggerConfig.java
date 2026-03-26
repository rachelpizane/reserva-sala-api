package edu.rachelpizane.reserva_sala.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Reserva de Salas API",
                version = "1.0",
                summary = "API para gerenciamento de reservas de salas de reunião."
        )
)
public class SwaggerConfig {
}
