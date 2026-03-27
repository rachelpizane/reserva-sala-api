package edu.rachelpizane.reserva_sala.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(description = "Dados para cadastro de sala")
public record SalaRequestDTO(

        @Schema(description = "Nome da sala", example = "Sala de Reunião 1", maxLength = 100)
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,

        @Schema(description = "Capacidade máxima da sala", example = "10")
        @NotNull(message = "Capacidade é obrigatório")
        @Min(value = 1, message = "Capacidade deve ser maior que zero")
        int capacidade,

        @Schema(description = "Localização da sala", example = "2º andar, Ala B", maxLength = 200)
        @Size(max = 200, message = "Localização da sala deve ter no máximo 200 caracteres")
        String localizacao,

        @Schema(description = "Descrição adicional da sala", example = "Sala equipada com projetor e ar-condicionado")
        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        String descricao
) {}
