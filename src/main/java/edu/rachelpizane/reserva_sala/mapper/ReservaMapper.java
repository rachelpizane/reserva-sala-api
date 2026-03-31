package edu.rachelpizane.reserva_sala.mapper;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.ReservaResponseDTO;
import edu.rachelpizane.reserva_sala.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SalaMapper.class})
public interface ReservaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sala", ignore = true)
    Reserva paraEntidade(ReservaRequestDTO request);

    ReservaResponseDTO paraDto(Reserva reserva);
}
