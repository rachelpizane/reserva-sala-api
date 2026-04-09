package edu.rachelpizane.reserva_sala.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
@Schema(description = "Dados da agenda de reservas diária")
public record AgendaDiariaResponseDTO(
        @Schema(description = "Data da reserva", example = "2025-04-06")
        LocalDate data,

        @Schema(description = "Lista de reservas agendadas")
        List<AgendaReservaResponseDTO> reservas
) {
}
