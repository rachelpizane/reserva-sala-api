package edu.rachelpizane.reserva_sala.mapper;

import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.model.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalaMapper {

    @Mapping(target = "id", ignore = true)
    Sala paraEntidade(SalaRequestDTO request);

    SalaResponseDTO paraDto(Sala sala);
}
