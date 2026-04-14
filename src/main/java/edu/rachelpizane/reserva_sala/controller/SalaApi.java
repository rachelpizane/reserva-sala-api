package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.dto.ErrorResponseDTO;
import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResumoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = @ExampleObject(
                            name = "Dados Inválidos",
                            value = "{\"tipoErro\": \"DADOS_INVALIDOS\", \"mensagens\": [\"Nome é obrigatório.\"]}"
                    )
            )
    )
    @PostMapping
    ResponseEntity<SalaResponseDTO> cadastrarSala(@Valid @RequestBody(required = true) SalaRequestDTO request);

    @Operation(
            summary = "Buscar uma sala",
            description = "Busca uma sala pelo seu id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sala buscada com sucesso",
            content = @Content(
                    schema = @Schema(implementation = SalaResponseDTO.class)
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
    @GetMapping("/{id}")
    ResponseEntity<SalaResponseDTO> buscarSala(@PathVariable UUID id);

    @Operation(
            summary = "Buscar salas",
            description = "Retorna uma lista de salas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de salas buscadas com sucesso",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = SalaResumoDTO.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<SalaResumoDTO>> buscarSalas();
}
