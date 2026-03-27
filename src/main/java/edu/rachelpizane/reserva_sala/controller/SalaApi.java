package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.dto.ErrorResponseDTO;
import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
        name = "Salas",
        description = "Operações para gerenciamento de salas"
)
@RequestMapping(value = "/salas")
public interface SalaApi {
    @Operation(
            summary = "Cadastrar uma nova sala",
            description = "Recebe os dados de uma sala e retorna a sala cadastrada"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Sala cadastrada com sucesso",
            content = @Content(
                    schema = @Schema(implementation = SalaResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @PostMapping
    ResponseEntity<SalaResponseDTO> cadastrarSala(@Valid @RequestBody(required = true) SalaRequestDTO request);

}
