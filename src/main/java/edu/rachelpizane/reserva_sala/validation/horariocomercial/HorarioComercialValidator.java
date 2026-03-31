package edu.rachelpizane.reserva_sala.validation.horariocomercial;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class HorarioComercialValidator implements ConstraintValidator<HorarioComercial, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true;
        LocalTime hora = value.toLocalTime();
        return !hora.isBefore(LocalTime.of(8, 0)) && !hora.isAfter(LocalTime.of(20, 0));
    }
}
