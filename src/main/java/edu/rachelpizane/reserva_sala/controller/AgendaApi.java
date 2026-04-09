package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(
        name = "Agendas",
        description = "Operações para gerenciamento de agendas"
)
@RequestMapping(value = "/agendas")
public interface AgendaApi {

    @Operation(
            summary = "Buscar agenda semanal das reservas",
            description = "A busca da agenda semanal retorna todas as reservas de salas em um intervalo de 7 dias, "
                    + "começando na segunda-feira da semana da data informada"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Agenda semanal buscada com sucesso",
            content = @Content(
                    schema = @Schema(implementation = AgendaSemanalResponseDTO.class)
            )
    )
    @GetMapping()
    ResponseEntity<AgendaSemanalResponseDTO> buscarAgendaSemanal(

            @Parameter(description = "Data de referência", example = "2026-04-06")
            @RequestParam(required = false)
            LocalDate data
    );
}
