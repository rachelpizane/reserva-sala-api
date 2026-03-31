package edu.rachelpizane.reserva_sala.exception;

import edu.rachelpizane.reserva_sala.enums.TipoErro;
import lombok.Getter;

@Getter
public class ConflictBusinessException extends RuntimeException {
    private final TipoErro tipoErro;

    public ConflictBusinessException(TipoErro tipoErro, String message) {
        super(message);
        this.tipoErro = tipoErro;
    }
}
