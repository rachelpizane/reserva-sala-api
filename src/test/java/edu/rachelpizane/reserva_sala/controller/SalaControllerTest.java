package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.controller.impl.SalaController;
import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.enums.TipoErro;
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

import java.util.stream.Stream;

import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaController.class)
class SalaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalaService service;

    private static final String SALA_URL = "/salas";

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
}