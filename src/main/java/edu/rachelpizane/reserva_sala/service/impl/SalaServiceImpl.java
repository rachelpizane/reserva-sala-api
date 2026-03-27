package edu.rachelpizane.reserva_sala.service.impl;

import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.mapper.SalaMapper;
import edu.rachelpizane.reserva_sala.model.Sala;
import edu.rachelpizane.reserva_sala.repository.SalaRepository;
import edu.rachelpizane.reserva_sala.service.SalaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SalaServiceImpl implements SalaService {

    private final SalaMapper mapper;
    private final SalaRepository repository;

    @Override
    public SalaResponseDTO cadastrarSala(SalaRequestDTO request) {
        Sala salaSalva = repository.save(mapper.paraEntidade(request));

        return mapper.paraDto(salaSalva);
    }
}
