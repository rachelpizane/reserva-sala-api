package edu.rachelpizane.reserva_sala.exception;

import edu.rachelpizane.reserva_sala.dto.ErrorResponseDTO;
import edu.rachelpizane.reserva_sala.enums.TipoErro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(
            NotFoundException ex) {

        ErrorResponseDTO response = new ErrorResponseDTO(
                TipoErro.NAO_ENCONTRADO.name(),
                List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ErrorResponseDTO response = new ErrorResponseDTO(
                TipoErro.DADOS_INVALIDOS.name(),
                errors);

        return ResponseEntity.badRequest().body(response);
    }
}
