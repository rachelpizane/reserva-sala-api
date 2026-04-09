package edu.rachelpizane.reserva_sala.dto;

import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Builder
@Schema(description = "Dados da agenda de reservas semanal")
public record AgendaSemanalResponseDTO(
        @Schema(description = "Data de início da semana (Segunda)", example = "2025-04-06")
        LocalDate dataInicioSemana,

        @Schema(description = "Data do final da semana (Domingo)", example = "2025-04-12")
        LocalDate dataFinalSemana,

        @Schema(description = "Data de ínicio da próxima semana (Segunda) ", example = "2025-04-13")
        LocalDate dataProximaSemana,

        @Schema(description = "Data de ínicio da semana anterior (Segunda)", example = "2025-03-30")
        LocalDate dataAnteriorSemana,

        @Schema(description = "Indica se existe agenda para a próxima semana", example = "false")
        boolean temProxima,

        @Schema(description = "Indica se existe agenda na semana anterior", example = "true")
        boolean temAnterior,

        @Schema(description = "Lista de agendas diárias da semana")
        List<AgendaDiariaResponseDTO> agendasDiarias
) {
}
