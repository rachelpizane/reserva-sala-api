package edu.rachelpizane.reserva_sala.service;

import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;

import java.time.LocalDate;

public interface AgendaService {
    AgendaSemanalResponseDTO buscarAgendaSemanal(LocalDate data);
}
