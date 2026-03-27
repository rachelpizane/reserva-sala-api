package edu.rachelpizane.reserva_sala.service;

import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;

public interface SalaService {
    SalaResponseDTO cadastrarSala(SalaRequestDTO request);
}
