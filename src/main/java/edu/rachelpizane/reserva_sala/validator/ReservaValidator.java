package edu.rachelpizane.reserva_sala.validator;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.enums.TipoErro;
import edu.rachelpizane.reserva_sala.exception.ConflictBusinessException;
import edu.rachelpizane.reserva_sala.repository.ReservaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ReservaValidator {
    private final ReservaRepository repository;

    public void validarCadastro(ReservaRequestDTO request) {
        validarHorarioDisponivel(request);
    }

    private void validarHorarioDisponivel(ReservaRequestDTO request) {
        if(repository.existeConflitoHorarios(request.salaId(), request.inicio(), request.fim())) {
            throw new ConflictBusinessException(
                    TipoErro.CONFLITO_HORARIO, "A sala já possui uma reserva nesse período. Escolha outro horário.");
        }
    }
}
