package edu.rachelpizane.reserva_sala.controller;

import edu.rachelpizane.reserva_sala.controller.impl.AgendaController;
import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;
import edu.rachelpizane.reserva_sala.mocks.AgendaMock;
import edu.rachelpizane.reserva_sala.service.AgendaService;
import edu.rachelpizane.reserva_sala.utils.JsonUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AgendaController.class)
class AgendaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgendaService service;

    private static final String AGENDA_URL = "/agendas";

    @Nested
    class BuscarAgendaSemanalTest {

        @ParameterizedTest
        @MethodSource("parametrosValidosProvider")
        void deveBuscarAgendaSemanalComSucesso(String dataParam) throws Exception {
            LocalDate dataRequest = LocalDate.now();

            AgendaSemanalResponseDTO response = AgendaMock
                    .umaAgendaSemanal(dataRequest.with(DayOfWeek.MONDAY)).build();

            when(service.buscarAgendaSemanal(argThat(Objects::nonNull)))
                    .thenReturn(response);

            mockMvc.perform(get(AGENDA_URL)
                            .param("data", dataParam)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(JsonUtils.convertToJson(response)));
        }

        static Stream<String> parametrosValidosProvider() {
            return Stream.of(
                    LocalDate.now().toString(),
                    null
            );
        }
    }
}