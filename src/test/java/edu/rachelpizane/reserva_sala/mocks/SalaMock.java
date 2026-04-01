package edu.rachelpizane.reserva_sala.mocks;

import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResumoDTO;
import edu.rachelpizane.reserva_sala.model.Sala;

import java.util.UUID;

public class SalaMock {

    public static SalaResumoDTO.SalaResumoDTOBuilder umSalaResumoDto() {
        return SalaResumoDTO.builder()
                .id(UUID.randomUUID())
                .nome("Sala de Reunião 1")
                .capacidade(10);
    }

    public static SalaResponseDTO.SalaResponseDTOBuilder umSalaResponseDto() {
        return SalaResponseDTO.builder()
                .id(UUID.randomUUID())
                .nome("Sala de Reunião 1")
                .capacidade(10)
                .localizacao("2º andar, Ala B")
                .descricao("Sala equipada com projetor e ar-condicionado");
    }

    public static Sala.SalaBuilder umaSala() {
        return Sala.builder()
                .id(UUID.randomUUID())
                .nome("Sala de Reunião 1")
                .capacidade(10)
                .localizacao("2º andar, Ala B")
                .descricao("Sala equipada com projetor e ar-condicionado");
    }

    public static SalaRequestDTO.SalaRequestDTOBuilder umSalaRequestDto() {
        return SalaRequestDTO.builder()
                .nome("Sala de Reunião 1")
                .capacidade(10)
                .localizacao("2º andar, Ala B")
                .descricao("Sala equipada com projetor e ar-condicionado");
    }
}
