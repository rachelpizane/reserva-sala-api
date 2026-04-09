package edu.rachelpizane.reserva_sala.mocks;

import edu.rachelpizane.reserva_sala.dto.AgendaDiariaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.AgendaReservaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AgendaMock {
    public static AgendaReservaResponseDTO.AgendaReservaResponseDTOBuilder umaAgendaReserva() {
        return AgendaReservaResponseDTO.builder()
                .id(UUID.randomUUID())
                .horaInicio(LocalTime.of(9, 0))
                .horaFim(LocalTime.of(10, 0))
                .nomeSala("Sala de Reunião 1");
    }

    public static AgendaDiariaResponseDTO.AgendaDiariaResponseDTOBuilder umaAgendaDiaria(LocalDate data) {
        return AgendaDiariaResponseDTO.builder()
                .data(data)
                .reservas(List.of(umaAgendaReserva().build()));
    }

    public static AgendaSemanalResponseDTO.AgendaSemanalResponseDTOBuilder umaAgendaSemanal(LocalDate inicioSemana) {
        LocalDate finalSemana = inicioSemana.plusWeeks(1).minusDays(1);

        return AgendaSemanalResponseDTO.builder()
                .dataInicioSemana(inicioSemana)
                .dataFinalSemana(finalSemana)
                .dataProximaSemana(inicioSemana.plusWeeks(1))
                .dataAnteriorSemana(inicioSemana.minusWeeks(1))
                .temProxima(true)
                .temAnterior(false)
                .agendasDiarias(montarAgendasDiarias(inicioSemana, finalSemana));
    }

    private static List<AgendaDiariaResponseDTO> montarAgendasDiarias(LocalDate inicioSemana, LocalDate finalSemana) {
        List<AgendaDiariaResponseDTO> diarias = new ArrayList<>();

        for (LocalDate d = inicioSemana; !d.isAfter(finalSemana); d = d.plusDays(1)) {
            diarias.add(umaAgendaDiaria(d).build());
        }

        return diarias;
    }
}
