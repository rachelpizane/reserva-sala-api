package edu.rachelpizane.reserva_sala.service.impl;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.ReservaResponseDTO;
import edu.rachelpizane.reserva_sala.exception.NotFoundException;
import edu.rachelpizane.reserva_sala.mapper.ReservaMapper;
import edu.rachelpizane.reserva_sala.model.Reserva;
import edu.rachelpizane.reserva_sala.model.Sala;
import edu.rachelpizane.reserva_sala.repository.ReservaRepository;
import edu.rachelpizane.reserva_sala.repository.SalaRepository;
import edu.rachelpizane.reserva_sala.service.ReservaService;
import edu.rachelpizane.reserva_sala.validator.ReservaValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaMapper mapper;
    private final ReservaValidator validator;
    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;

    @Override
    public ReservaResponseDTO cadastrarReserva(ReservaRequestDTO request) {
        Sala sala = buscarSala(request.salaId());
        validator.validarCadastro(request);

        Reserva reserva = mapper.paraEntidade(request);
        reserva.setSala(sala);
        Reserva reservaSalva = reservaRepository.save(reserva);

        return mapper.paraDto(reservaSalva);
    }

    private Sala buscarSala(UUID id){
        return salaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Sala não encontrada"));
    }
}
