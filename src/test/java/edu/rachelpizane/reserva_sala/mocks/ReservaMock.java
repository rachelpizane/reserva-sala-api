package edu.rachelpizane.reserva_sala.mocks;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.ReservaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResumoDTO;
import edu.rachelpizane.reserva_sala.model.Reserva;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservaMock {

    public static ReservaResponseDTO.ReservaResponseDTOBuilder umReservaResponseDto() {
        return ReservaResponseDTO.builder()
                .id(UUID.randomUUID())
                .organizador("Maria Silva")
                .inicio(LocalDateTime.now().plusDays(1).withHour(9))
                .fim(LocalDateTime.now().plusDays(1).withHour(10))
                .sala(SalaResumoDTO.builder()
                        .id(UUID.randomUUID())
                        .nome("Sala de Reunião 1")
                        .build());
    }

    public static Reserva.ReservaBuilder umaReserva() {
        return Reserva.builder()
                .id(UUID.randomUUID())
                .organizador("Maria Silva")
                .inicio(LocalDateTime.now().plusDays(1).withHour(9))
                .fim(LocalDateTime.now().plusDays(1).withHour(10))
                .sala(SalaMock.umaSala().build());
    }

    public static ReservaRequestDTO.ReservaRequestDTOBuilder umReservaRequestDto() {
        return ReservaRequestDTO.builder()
                .organizador("Maria Silva")
                .inicio(LocalDateTime.now().plusDays(1).withHour(9))
                .fim(LocalDateTime.now().plusDays(1).withHour(10))
                .salaId(UUID.randomUUID());
    }
}
