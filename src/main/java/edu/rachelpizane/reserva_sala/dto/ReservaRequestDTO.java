package edu.rachelpizane.reserva_sala.dto;

import edu.rachelpizane.reserva_sala.validation.horariocomercial.HorarioComercial;
import edu.rachelpizane.reserva_sala.validation.reservaperiodovalido.ReservaPeriodoValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@ReservaPeriodoValido
@Schema(description = "Dados para cadastro de reserva")
public record ReservaRequestDTO(
        @Schema(description = "Nome do organizador da reserva", example = "Maria Silva", maxLength = 100)
        @NotBlank(message = "Organizador é obrigatório")
        @Size(max = 100, message = "Organizador deve ter no máximo 100 caracteres")
        String organizador,

        @Schema(description = "Data e hora de início da reserva", example = "2026-04-01T09:00:00")
        @NotNull(message = "Início é obrigatório")
        @FutureOrPresent(message = "Início deve ser no presente ou futuro")
        @HorarioComercial
        LocalDateTime inicio,

        @Schema(description = "Data e hora de fim da reserva", example = "2026-04-01T10:00:00")
        @NotNull(message = "Fim é obrigatório")
        @HorarioComercial
        LocalDateTime fim,

        @Schema(description = "ID da sala a ser reservada", example = "b3b1c2d3-e4f5-6789-abcd-1234567890ef")
        @NotNull(message = "Sala é obrigatória")
        UUID salaId
) {}
