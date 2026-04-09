package edu.rachelpizane.reserva_sala.mapper;

import edu.rachelpizane.reserva_sala.dto.AgendaDiariaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.AgendaReservaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;
import edu.rachelpizane.reserva_sala.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AgendaMapper {


    @Mapping(target = "nomeSala", source = "sala.nome")
    @Mapping(target = "horaInicio", source = "inicio", qualifiedByName = "formatarHora")
    @Mapping(target = "horaFim", source = "fim", qualifiedByName = "formatarHora")
    AgendaReservaResponseDTO paraAgendaReservaDTO(Reserva reserva);

    List<AgendaReservaResponseDTO> paraAgendaReservaDTOList(List<Reserva> reservas);

    default AgendaDiariaResponseDTO paraAgendaDiariaDTO(LocalDate data, List<Reserva> reservas) {
        return AgendaDiariaResponseDTO.builder()
                .data(data)
                .reservas(paraAgendaReservaDTOList(reservas))
                .build();
    }

    AgendaSemanalResponseDTO paraAgendaSemanalDTO(
            LocalDate dataInicioSemana,
            LocalDate dataFinalSemana,
            LocalDate dataProximaSemana,
            LocalDate dataAnteriorSemana,
            boolean temProxima,
            boolean temAnterior,
            List<AgendaDiariaResponseDTO> agendasDiarias
        );

    @Named("formatarHora")
    default LocalTime formatarHora(LocalDateTime datatime) {
        return datatime.toLocalTime();
    }
}
