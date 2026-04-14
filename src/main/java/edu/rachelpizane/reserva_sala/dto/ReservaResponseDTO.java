package edu.rachelpizane.reserva_sala.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Schema(description = "Dados de resposta da reserva")
public record ReservaResponseDTO(
        @Schema(description = "Identificador da reserva", example = "e7b8c9d2-1234-5678-9abc-def012345678")
        UUID id,

        @Schema(description = "Nome do organizador da reserva", example = "Maria Silva")
        String organizador,

        @Schema(description = "Data e hora de início da reserva", example = "2026-04-01T09:00:00")
        LocalDateTime inicio,

        @Schema(description = "Data e hora de fim da reserva", example = "2026-04-01T10:00:00")
        LocalDateTime fim,

        @Schema(description = "Dados da sala reservada")
        SalaResponseDTO sala
) {}
