package edu.rachelpizane.reserva_sala.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dados de resposta para erros da API")
public record ErrorResponseDTO(
        @Schema(
                description = "Tipo de erro ocorrido",
                example = "DADOS_INVALIDOS"
        )
        String tipoErro,

        @Schema(description = "Lista de mensagens que detallha os erros ocorridos")
        List<String> mensagens) {
}
