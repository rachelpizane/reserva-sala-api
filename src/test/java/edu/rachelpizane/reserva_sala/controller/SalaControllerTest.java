package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.controller.impl.SalaController;
import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResumoDTO;
import edu.rachelpizane.reserva_sala.enums.TipoErro;
import edu.rachelpizane.reserva_sala.exception.NotFoundException;
import edu.rachelpizane.reserva_sala.mocks.SalaMock;
import edu.rachelpizane.reserva_sala.service.SalaService;
import edu.rachelpizane.reserva_sala.utils.JsonUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaController.class)
class SalaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalaService service;

    private static final String SALA_URL = "/salas";
    private static final String SALA_ID_URL = SALA_URL + "/{id}";

    @Nested
    class CadastrarSalaTests {
        @Test
        void deveCadastrarSalaCorretamente() throws Exception {
            SalaRequestDTO request = SalaMock.umSalaRequestDto().build();
            SalaResponseDTO response = SalaMock.umSalaResponseDto().build();

            when(service.cadastrarSala(request)).thenReturn(response);

            mockMvc.perform(post(SALA_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtils.convertToJson(request)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(JsonUtils.convertToJson(response)));
        }

        @ParameterizedTest
        @MethodSource("requestInvalidosProvider")
        void deveRetornarBadRequestParaDadosInvalidos(SalaRequestDTO requestInvalido) throws Exception {

            mockMvc.perform(post(SALA_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtils.convertToJson(requestInvalido)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.tipoErro").value(TipoErro.DADOS_INVALIDOS.name()))
                    .andExpect(jsonPath("$.mensagens.length()").value(greaterThan(0)));
        }

        static Stream<SalaRequestDTO> requestInvalidosProvider() {
            return Stream.of(
                    SalaMock.umSalaRequestDto().nome(null).build(),
                    SalaMock.umSalaRequestDto().nome("a".repeat(101)).build(),
                    SalaMock.umSalaRequestDto().capacidade(-1).build(),
                    SalaMock.umSalaRequestDto().localizacao("a".repeat(201)).build(),
                    SalaMock.umSalaRequestDto().descricao("a".repeat(256)).build()
            );
        }
    }

    @Nested
    class BuscarSalaTests {
        @Test
        void deveBuscarSalaComSucesso() throws Exception {
            SalaResponseDTO response = SalaMock.umSalaResponseDto().build();

            when(service.buscarSala(response.id())).thenReturn(response);

            mockMvc.perform(get(SALA_ID_URL, response.id())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(JsonUtils.convertToJson(response)));
        }

        @Test
        void deveRetornarNotFoundQuandoSalaNaoEncontrada() throws Exception {
            UUID idInvalido = UUID.randomUUID();

            when(service.buscarSala(idInvalido)).thenThrow(new NotFoundException("Sala não encontrada"));

            mockMvc.perform(get(SALA_ID_URL, idInvalido)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.tipoErro").value(TipoErro.NAO_ENCONTRADO.name()))
                    .andExpect(jsonPath("$.mensagens.length()").value(greaterThan(0)));
        }
    }

    @Nested
    class BuscarSalasTests {
        @ParameterizedTest
        @MethodSource("salasProvider")
        void deveBuscarSalasComSucesso(List<SalaResumoDTO> response) throws Exception {
            when(service.buscarSalas()).thenReturn(response);

            mockMvc.perform(get(SALA_URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(JsonUtils.convertToJson(response)));
        }

        static Stream<List<SalaResumoDTO>> salasProvider() {
            return Stream.of(
                    List.of(),
                    List.of(SalaMock.umSalaResumoDto().build())
            );
        }
    }
}