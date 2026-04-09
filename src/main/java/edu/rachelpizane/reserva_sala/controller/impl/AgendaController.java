package edu.rachelpizane.reserva_sala.controller.impl;

import edu.rachelpizane.reserva_sala.controller.AgendaApi;
import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;
import edu.rachelpizane.reserva_sala.service.AgendaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class AgendaController implements AgendaApi {
    private final AgendaService service;

    @Override
    public ResponseEntity<AgendaSemanalResponseDTO> buscarAgendaSemanal(LocalDate data) {
        LocalDate dataRequest = Objects.isNull(data) ? LocalDate.now() : data;

        AgendaSemanalResponseDTO response = service.buscarAgendaSemanal(dataRequest);

        return ResponseEntity.ok(response);
    }
}
