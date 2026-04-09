package edu.rachelpizane.reserva_sala.service.impl;

import edu.rachelpizane.reserva_sala.dto.AgendaDiariaResponseDTO;
import edu.rachelpizane.reserva_sala.dto.AgendaSemanalResponseDTO;
import edu.rachelpizane.reserva_sala.mapper.AgendaMapper;
import edu.rachelpizane.reserva_sala.model.Reserva;
import edu.rachelpizane.reserva_sala.repository.ReservaRepository;
import edu.rachelpizane.reserva_sala.service.AgendaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AgendaServiceImpl implements AgendaService {

    private final ReservaRepository reservaRepository;
    private final AgendaMapper mapper;

    @Override
    public AgendaSemanalResponseDTO buscarAgendaSemanal(LocalDate dataRequest) {
        LocalDate dataInicioSemana = dataRequest.with(DayOfWeek.MONDAY);
        LocalDate dataFinalSemana = dataRequest.with(DayOfWeek.SUNDAY);
        LocalDate dataProximaSemana = dataInicioSemana.plusWeeks(1);
        LocalDate dataAnteriorSemana = dataInicioSemana.minusWeeks(1);

        List<AgendaDiariaResponseDTO> agendasDiarias = buscarAgendasDiarias(dataInicioSemana, dataFinalSemana);

        return  mapper.paraAgendaSemanalDTO(
                dataInicioSemana,
                dataFinalSemana,
                dataProximaSemana,
                dataAnteriorSemana,
                existeReservaDuranteSemana(dataProximaSemana),
                existeReservaDuranteSemana(dataAnteriorSemana),
                agendasDiarias
        );
    }

    private boolean existeReservaDuranteSemana(LocalDate inicio) {
        return reservaRepository.existsReservaEntreDatas(
                inicio.atStartOfDay(),
                inicio.plusWeeks(1).plusDays(1).atStartOfDay()
        );
    }

    private List<AgendaDiariaResponseDTO> buscarAgendasDiarias(LocalDate dataInicioSemana, LocalDate dataFinalSemana){
        Map<LocalDate, List<Reserva>> reservasAgrupadoPorData = buscarReservasAgrupadoPorData(
                dataInicioSemana, dataFinalSemana);

        return montarAgendasDiarias(dataInicioSemana, dataFinalSemana, reservasAgrupadoPorData);
    }

    private List<AgendaDiariaResponseDTO> montarAgendasDiarias(
            LocalDate dataInicioSemana, LocalDate dataFinalSemana, Map<LocalDate, List<Reserva>> reservasAgrupadoPorData) {

        List<AgendaDiariaResponseDTO> agendasDiarias = new ArrayList<>();

        for (LocalDate data = dataInicioSemana; !data.isAfter(dataFinalSemana); data = data.plusDays(1)) {
            List<Reserva> reservas = reservasAgrupadoPorData.containsKey(data) ?
                    reservasAgrupadoPorData.get(data) : List.of();

            AgendaDiariaResponseDTO agendaDiaria = mapper.paraAgendaDiariaDTO(data, reservas);
            agendasDiarias.add(agendaDiaria);
        }

        return agendasDiarias;
    }

    private Map<LocalDate, List<Reserva>> buscarReservasAgrupadoPorData(
            LocalDate dataInicio, LocalDate dataFinal){
        List<Reserva> reservasSemana = buscarReservasEntreDatas(dataInicio, dataFinal);

        return reservasSemana.stream()
                .collect(Collectors.groupingBy(reserva -> reserva.getInicio().toLocalDate()));
    }

    private List<Reserva> buscarReservasEntreDatas(LocalDate dataInicio, LocalDate dataFinal) {
        return reservaRepository.buscarReservasEntreDatas(
                dataInicio.atStartOfDay(),
                dataFinal.plusDays(1).atStartOfDay()
        );
    }
}
