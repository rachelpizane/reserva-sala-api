package edu.rachelpizane.reserva_sala.controller.impl;

import edu.rachelpizane.reserva_sala.controller.SalaApi;
import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.service.SalaService;
import edu.rachelpizane.reserva_sala.utils.UriUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class SalaController implements SalaApi {
    private final SalaService service;

    @Override
    public ResponseEntity<SalaResponseDTO> cadastrarSala(SalaRequestDTO request) {
        SalaResponseDTO response = service.cadastrarSala(request);
        URI location = UriUtils.construirLocation(response.id());

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<SalaResponseDTO> buscarSala(UUID id) {
        SalaResponseDTO response = service.buscarSala(id);

        return ResponseEntity.ok(response);
    }
}
