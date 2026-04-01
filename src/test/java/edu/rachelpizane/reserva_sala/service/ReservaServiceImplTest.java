package edu.rachelpizane.reserva_sala.service;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.ReservaResponseDTO;
import edu.rachelpizane.reserva_sala.enums.TipoErro;
import edu.rachelpizane.reserva_sala.exception.ConflictBusinessException;
import edu.rachelpizane.reserva_sala.exception.NotFoundException;
import edu.rachelpizane.reserva_sala.mapper.ReservaMapper;
import edu.rachelpizane.reserva_sala.mapper.SalaMapper;
import edu.rachelpizane.reserva_sala.mocks.ReservaMock;
import edu.rachelpizane.reserva_sala.mocks.SalaMock;
import edu.rachelpizane.reserva_sala.model.Reserva;
import edu.rachelpizane.reserva_sala.model.Sala;
import edu.rachelpizane.reserva_sala.repository.ReservaRepository;
import edu.rachelpizane.reserva_sala.repository.SalaRepository;
import edu.rachelpizane.reserva_sala.service.impl.ReservaServiceImpl;
import edu.rachelpizane.reserva_sala.validator.ReservaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {
    @Mock
    private SalaRepository salaRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaValidator reservaValidator;

    private ReservaMapper reservaMapper;

    private SalaMapper salaMapper;

    private ReservaServiceImpl service;

    @BeforeEach
    void setUp() {
        reservaMapper = Mappers.getMapper(ReservaMapper.class);
        salaMapper = Mappers.getMapper(SalaMapper.class);

        ReflectionTestUtils.setField(reservaMapper, "salaMapper", salaMapper);

        service = new ReservaServiceImpl(reservaMapper, reservaValidator,reservaRepository, salaRepository);
    }

    @Nested
    class CadastrarReservaTests {
        @Test
        void deveCadastrarReservaCorretamente(){
            Sala sala = SalaMock.umaSala().build();

            ReservaRequestDTO request = ReservaMock.umReservaRequestDto().salaId(sala.getId()).build();
            Reserva reserva = ReservaMock.umaReserva().sala(sala).build();

            when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));
            when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

            ReservaResponseDTO response = service.cadastrarReserva(request);

            assertEquals(reserva.getId(), response.id());
            assertEquals(sala.getId(), response.sala().id());
            verify(reservaRepository , times(1)).save(any(Reserva.class));
        }

        @Test
        void deveLancarNotFoundQuandoSalaNaoEncontrada(){
            UUID idInvalido = UUID.randomUUID();
            ReservaRequestDTO request = ReservaMock.umReservaRequestDto().salaId(idInvalido).build();

            when(salaRepository.findById(idInvalido)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> {
                service.cadastrarReserva(request);
            });
        }

        @Test
        void deveLancarConflictBusinessQuandoConflitoHorario(){
            Sala sala = SalaMock.umaSala().build();
            ReservaRequestDTO request = ReservaMock.umReservaRequestDto().salaId(sala.getId()).build();

            when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));
            doThrow(new ConflictBusinessException(TipoErro.CONFLITO_HORARIO, "Conflito de Horário"))
                    .when(reservaValidator).validarCadastro(request);

            assertThrows(ConflictBusinessException.class, () -> {
                service.cadastrarReserva(request);
            });
        }
    }
}