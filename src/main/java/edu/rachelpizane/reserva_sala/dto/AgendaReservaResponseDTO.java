package edu.rachelpizane.reserva_sala.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
@Schema(description = "Dados principais da reserva de sala")
public record AgendaReservaResponseDTO(
        @Schema(description = "Identificador da reserva", example = "e7b8c9d2-1234-5678-9abc-def012345678")
        UUID id,

        @JsonFormat(pattern = "HH:mm")
        @Schema(description = "Hora de início da reserva", example = "09:00")
        LocalTime horaInicio,

        @JsonFormat(pattern = "HH:mm")
        @Schema(description = "Hora de fim da reserva", example = "10:00")
        LocalTime horaFim,

        @Schema(description = "Nome da sala reservada", example = "Sala de Reunião 1")
        String nomeSala
) {
}
