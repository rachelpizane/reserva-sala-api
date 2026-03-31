package edu.rachelpizane.reserva_sala.validation.reservaperiodovalido;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReservaPeriodoValidoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReservaPeriodoValido {
    String message() default "O fim deve ser no mesmo dia e após o início";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
