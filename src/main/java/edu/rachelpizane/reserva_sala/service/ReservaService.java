package edu.rachelpizane.reserva_sala.service;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.ReservaResponseDTO;

import java.util.UUID;

public interface ReservaService {
    ReservaResponseDTO cadastrarReserva(ReservaRequestDTO request);

    ReservaResponseDTO buscarReserva(UUID id);
}
