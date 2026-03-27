package edu.rachelpizane.reserva_sala.service;

import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;

import java.util.UUID;

public interface SalaService {
    SalaResponseDTO cadastrarSala(SalaRequestDTO request);

    SalaResponseDTO buscarSala(UUID id);
}
