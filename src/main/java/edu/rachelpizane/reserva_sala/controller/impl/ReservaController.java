package edu.rachelpizane.reserva_sala.controller.impl;

import edu.rachelpizane.reserva_sala.controller.ReservaApi;
import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.ReservaResponseDTO;
import edu.rachelpizane.reserva_sala.service.ReservaService;
import edu.rachelpizane.reserva_sala.utils.UriUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@AllArgsConstructor
@RestController
public class ReservaController implements ReservaApi {

    private final ReservaService service;

    @Override
    public ResponseEntity<ReservaResponseDTO> cadastrarReserva(ReservaRequestDTO request) {
        ReservaResponseDTO response = service.cadastrarReserva(request);
        URI location = UriUtils.construirLocation(response.id());

        return ResponseEntity.created(location).body(response);
    }
}
