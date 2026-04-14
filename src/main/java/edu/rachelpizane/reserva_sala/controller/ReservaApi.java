package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "Reservas",
        description = "Operações para gerenciamento de reservas"
)
@RequestMapping(value = "/reservas")
public interface ReservaApi {
    @Operation(
            summary = "Cadastrar uma nova reserva",
            description = "Recebe os dados de uma reserva e retorna a reserva cadastrada"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Reserva cadastrada com sucesso",
            content = @Content(
                    schema = @Schema(implementation = ReservaResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                            name = "Dados Inválidos",
                            value = "{\"tipoErro\": \"DADOS_INVALIDOS\", \"mensagens\": [\"Organizador é obrigatório.\"]}"
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sala não encontrada",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                            name = "Sala Não Encontrada",
                            value = "{\"tipoErro\": \"NAO_ENCONTRADO\", \"mensagens\": [\"Sala não encontrada\"]}"
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "A sala já está reservada para o período informado",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                            name = "Conflito de Reserva",
                            value = "{\"tipoErro\": \"CONFLITO_HORARIO\", \"mensagens\": [\"A sala já possui uma reserva nesse período. Escolha outro horário.\"]}"
                    )
            )
    )
    @PostMapping
    ResponseEntity<ReservaResponseDTO> cadastrarReserva(@Valid @RequestBody(required = true) ReservaRequestDTO request);

    @Operation(
            summary = "Buscar uma reserva",
            description = "Busca uma reserva pelo seu id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reserva buscada com sucesso",
            content = @Content(
                    schema = @Schema(implementation = ReservaResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Reserva não encontrada",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                            name = "Reserva Não Encontrada",
                            value = "{\"tipoErro\": \"NAO_ENCONTRADO\", \"mensagens\": [\"Reserva não encontrada\"]}"
                    )
            )
    )
    @GetMapping("/{id}")
    ResponseEntity<ReservaResponseDTO> buscarReserva(@PathVariable UUID id);
}
