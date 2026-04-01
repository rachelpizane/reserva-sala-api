package edu.rachelpizane.reserva_sala.validation.reservaperiodovalido;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class ReservaPeriodoValidoValidator implements ConstraintValidator<ReservaPeriodoValido, ReservaRequestDTO> {

    @Override
    public boolean isValid(ReservaRequestDTO dto, ConstraintValidatorContext context) {
        if (dto.inicio() == null || dto.fim() == null) return true;

        LocalDateTime inicio = dto.inicio();
        LocalDateTime fim = dto.fim();

        return fim.toLocalDate().equals(inicio.toLocalDate()) && fim.isAfter(inicio);
    }
}