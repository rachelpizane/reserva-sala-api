package edu.rachelpizane.reserva_sala.service;

import edu.rachelpizane.reserva_sala.dto.AgendaDiariaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;
import edu.rachelpizane.reserva_sala.mapper.AgendaMapper;
import edu.rachelpizane.reserva_sala.mocks.ReservaMock;
import edu.rachelpizane.reserva_sala.model.Reserva;
import edu.rachelpizane.reserva_sala.repository.ReservaRepository;
import edu.rachelpizane.reserva_sala.service.impl.AgendaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendaServiceImplTest {
    @Mock
    private ReservaRepository reservaRepository;

    private AgendaMapper mapper;

    private AgendaServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AgendaMapper.class);
        service = new AgendaServiceImpl(reservaRepository, mapper);
    }

    @Nested
    class buscarAgendaSemanalTests{

        @Test
        void deveBuscarAgendaSemanalCorretamente(){
            LocalDate dataRequest = LocalDate.of(2026, 4, 9);
            LocalDate dataInicio = LocalDate.of(2026, 4, 6);
            LocalDate dataFinal = LocalDate.of(2026, 4, 12);

            LocalDateTime inicioHora = dataRequest.atTime(9,0);
            Reserva reserva = ReservaMock.umaReserva().inicio(inicioHora).fim(inicioHora.plusHours(1)).build();

            when(reservaRepository.buscarReservasEntreDatas(any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenReturn(List.of(reserva));

            when(reservaRepository.existsReservaEntreDatas(any(LocalDateTime.class), any(LocalDateTime.class))).
                    thenReturn(false, true);

            AgendaSemanalResponseDTO response = service.buscarAgendaSemanal(dataRequest);

            AgendaDiariaResponseDTO agendaDiaria = response.agendasDiarias().stream()
                    .filter(agenda -> agenda.data().equals(inicioHora.toLocalDate()))
                    .findFirst()
                    .orElse(null);

            assertEquals(dataInicio, response.dataInicioSemana());
            assertEquals(dataFinal, response.dataFinalSemana());
            assertEquals(LocalDate.of(2026, 4, 13), response.dataProximaSemana());
            assertEquals(LocalDate.of(2026, 3, 30), response.dataAnteriorSemana());
            assertFalse(response.temProxima());
            assertTrue(response.temAnterior());

            assertThat(response.agendasDiarias()).hasSize(7);
            assertEquals(dataInicio,response.agendasDiarias().getFirst().data());
            assertEquals(dataFinal,response.agendasDiarias().getLast().data());

            assertThat(agendaDiaria.reservas()).hasSize(1);
            assertEquals(agendaDiaria.reservas().getFirst().id(), reserva.getId());
        }
    }
}