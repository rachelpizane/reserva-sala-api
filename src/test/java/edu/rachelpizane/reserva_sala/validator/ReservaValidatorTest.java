package edu.rachelpizane.reserva_sala.validator;

import edu.rachelpizane.reserva_sala.dto.ReservaRequestDTO;
import edu.rachelpizane.reserva_sala.enums.TipoErro;
import edu.rachelpizane.reserva_sala.exception.ConflictBusinessException;
import edu.rachelpizane.reserva_sala.mocks.ReservaMock;
import edu.rachelpizane.reserva_sala.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaValidatorTest {
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaValidator validator;

    @Nested
    class ValidarCadastroTests {
        private ReservaRequestDTO request;

        @BeforeEach
        void setUp() {
            request = ReservaMock.umReservaRequestDto().build();
        }

        @Test
        void deveLancarConflictBusinessExceptionQuandoConflitoHorario() {
            String mensagemEsperada = "A sala já possui uma reserva nesse período. Escolha outro horário.";

            when(reservaRepository.existeConflitoHorarios(
                    request.salaId(), request.inicio(), request.fim())).thenReturn(true);

            ConflictBusinessException ex = assertThrows(
                    ConflictBusinessException.class, () -> validator.validarCadastro(request));

            assertEquals(mensagemEsperada, ex.getMessage());
            assertEquals(TipoErro.CONFLITO_HORARIO, ex.getTipoErro());
        }

        @Test
        void naoDeveLancarExcecaoQuandoHorarioDisponivel() {
            when(reservaRepository.existeConflitoHorarios(
                    request.salaId(), request.inicio(), request.fim())).thenReturn(false);

            assertDoesNotThrow(() -> validator.validarCadastro(request));
        }
    }
}