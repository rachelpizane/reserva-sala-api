package edu.rachelpizane.reserva_sala.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Resumo dos dados da sala")
public record SalaResumoDTO(
        @Schema(description = "Identificador da sala", example = "e7b8c9d2-1234-5678-9abc-def012345678")
        UUID id,

        @Schema(description = "Nome da sala", example = "Sala de Reunião 1")
        String nome,

        @Schema(description = "Capacidade máxima da sala", example = "10")
        int capacidade
) {}
