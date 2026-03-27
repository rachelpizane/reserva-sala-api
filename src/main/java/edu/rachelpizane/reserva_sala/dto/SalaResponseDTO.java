package edu.rachelpizane.reserva_sala.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados de resposta da sala")
public record SalaResponseDTO(
        @Schema(description = "Identificador da sala", example = "b3b1c2d4-5e6f-7a8b-9c0d-1e2f3a4b5c6d")
        UUID id,

        @Schema(description = "Nome da sala", example = "Sala de Reunião 1")
        String nome,

        @Schema(description = "Capacidade máxima da sala", example = "10")
        int capacidade,

        @Schema(description = "Localização da sala", example = "2º andar, Ala B")
        String localizacao,

        @Schema(description = "Descrição adicional da sala", example = "Sala equipada com projetor e ar-condicionado")
        String descricao
) {}