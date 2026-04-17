package edu.rachelpizane.reserva_sala.service.impl;

import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResumoDTO;
import edu.rachelpizane.reserva_sala.exception.NotFoundException;
import edu.rachelpizane.reserva_sala.mapper.SalaMapper;
import edu.rachelpizane.reserva_sala.model.Sala;
import edu.rachelpizane.reserva_sala.repository.SalaRepository;
import edu.rachelpizane.reserva_sala.service.SalaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public SalaResponseDTO buscarSala(UUID id){
        return repository.findById(id)
                .map(mapper::paraDto)
                .orElseThrow(() -> new NotFoundException("Sala não encontrada"));
    }

    @Override
    public List<SalaResumoDTO> buscarSalas(){
        return repository.findAllByOrderByNomeAsc();
    }
}
