package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.controller.impl.ReservaController;
import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.ReservaResponseDTO;
import edu.rachelpizane.reserva_sala.enums.TipoErro;
import edu.rachelpizane.reserva_sala.exception.ConflictBusinessException;
import edu.rachelpizane.reserva_sala.exception.NotFoundException;
import edu.rachelpizane.reserva_sala.mocks.ReservaMock;
import edu.rachelpizane.reserva_sala.service.ReservaService;
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

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService service;

    private static final String RESERVA_URL = "/reservas";
    private static final String RESERVA_ID_URL = RESERVA_URL + "/{id}";

    @Nested
    class CadastrarReservaTests {
        @Test
        void deveCadastrarReservaCorretamente() throws Exception {
            ReservaRequestDTO request = ReservaMock.umReservaRequestDto().build();
            ReservaResponseDTO response = ReservaMock.umReservaResponseDto().build();

            when(service.cadastrarReserva(request)).thenReturn(response);

            mockMvc.perform(post(RESERVA_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtils.convertToJson(request)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(JsonUtils.convertToJson(response)));
        }

        @ParameterizedTest
        @MethodSource("requestInvalidosProvider")
        void deveRetornarBadRequestParaDadosInvalidos(ReservaRequestDTO requestInvalido) throws Exception {
            mockMvc.perform(post(RESERVA_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtils.convertToJson(requestInvalido)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.tipoErro").value(TipoErro.DADOS_INVALIDOS.name()))
                    .andExpect(jsonPath("$.mensagens.length()").value(greaterThan(0)));
        }

        static Stream<ReservaRequestDTO> requestInvalidosProvider() {
            LocalDateTime inicio = LocalDateTime.now().withHour(9);
            LocalDateTime fim = LocalDateTime.now().withHour(10);

            return Stream.of(
                    ReservaMock.umReservaRequestDto().organizador(null).build(),
                    ReservaMock.umReservaRequestDto().organizador("a".repeat(101)).build(),
                    ReservaMock.umReservaRequestDto().inicio(null).build(),
                    ReservaMock.umReservaRequestDto().inicio(inicio.withHour(7)).build(),
                    ReservaMock.umReservaRequestDto().inicio(inicio.minusDays(1)).build(),
                    ReservaMock.umReservaRequestDto().fim(null).build(),
                    ReservaMock.umReservaRequestDto().fim(inicio.plusDays(1)).build(),
                    ReservaMock.umReservaRequestDto().fim(inicio.minusHours(1)).build(),
                    ReservaMock.umReservaRequestDto().fim(fim.withHour(21)).build(),
                    ReservaMock.umReservaRequestDto().salaId(null).build(),
                    ReservaMock.umReservaRequestDto().inicio(null).fim(null).build()
            );
        }

        @Test
        void deveRetornarNotFoundQuandoSalaNaoEncontrada() throws Exception {
            UUID idInvalido = UUID.randomUUID();
            ReservaRequestDTO request = ReservaMock.umReservaRequestDto().salaId(idInvalido).build();

            when(service.cadastrarReserva(request))
                    .thenThrow(new NotFoundException("Sala não encontrada"));

            mockMvc.perform(post(RESERVA_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtils.convertToJson(request)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.tipoErro").value(TipoErro.NAO_ENCONTRADO.name()))
                    .andExpect(jsonPath("$.mensagens.length()").value(greaterThan(0)));
        }

        @Test
        void deveRetornarConflictQuandoConflitoHorario() throws Exception {
            UUID idInvalido = UUID.randomUUID();
            ReservaRequestDTO request = ReservaMock.umReservaRequestDto().salaId(idInvalido).build();

            when(service.cadastrarReserva(request))
                    .thenThrow(new ConflictBusinessException(TipoErro.CONFLITO_HORARIO, "Conflito de Horário"));

            mockMvc.perform(post(RESERVA_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtils.convertToJson(request)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.tipoErro").value(TipoErro.CONFLITO_HORARIO.name()))
                    .andExpect(jsonPath("$.mensagens.length()").value(greaterThan(0)));
        }
    }

    @Nested
    class BuscarReservaTests {
        @Test
        void deveBuscarReservaComSucesso() throws Exception {
            ReservaResponseDTO response = ReservaMock.umReservaResponseDto().build();

            when(service.buscarReserva(response.id())).thenReturn(response);

            mockMvc.perform(get(RESERVA_ID_URL, response.id())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(JsonUtils.convertToJson(response)));
        }

        @Test
        void deveRetornarNotFoundQuandoReservaNaoEncontrada() throws Exception {
            UUID idInvalido = UUID.randomUUID();

            when(service.buscarReserva(idInvalido)).thenThrow(new NotFoundException("Reserva não encontrada"));

            mockMvc.perform(get(RESERVA_ID_URL, idInvalido)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.tipoErro").value(TipoErro.NAO_ENCONTRADO.name()))
                    .andExpect(jsonPath("$.mensagens.length()").value(greaterThan(0)));
        }
    }
}