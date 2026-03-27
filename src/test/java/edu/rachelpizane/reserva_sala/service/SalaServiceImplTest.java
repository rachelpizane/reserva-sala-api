package edu.rachelpizane.reserva_sala.service;

import edu.rachelpizane.reserva_sala.dto.SalaRequestDTO;
import edu.rachelpizane.reserva_sala.dto.SalaResponseDTO;
import edu.rachelpizane.reserva_sala.exception.NotFoundException;
import edu.rachelpizane.reserva_sala.mapper.SalaMapper;
import edu.rachelpizane.reserva_sala.mocks.SalaMock;
import edu.rachelpizane.reserva_sala.model.Sala;
import edu.rachelpizane.reserva_sala.repository.SalaRepository;
import edu.rachelpizane.reserva_sala.service.impl.SalaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SalaServiceImplTest {
    @Mock
    private SalaRepository repository;

    private SalaMapper mapper;

    private SalaServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SalaMapper.class);
        service = new SalaServiceImpl(mapper, repository);
    }

    @Nested
    class CadastrarSalaTests {
        @Test
        void deveCadastrarSalaCorretamente(){
            SalaRequestDTO request = SalaMock.umSalaRequestDto().build();
            Sala sala = SalaMock.umaSala().build();

            when(repository.save(any(Sala.class))).thenReturn(sala);

            SalaResponseDTO response = service.cadastrarSala(request);

            assertEquals(sala.getId(), response.id());
            verify(repository, times(1)).save(any(Sala.class));
        }
    }

    @Nested
    class BuscarSalaTests {
        @Test
        void deveBuscarSalaCorretamente(){
            UUID idSala = UUID.randomUUID();
            Sala sala = SalaMock.umaSala().build();

            when(repository.findById(idSala)).thenReturn(Optional.of(sala));

            SalaResponseDTO response = service.buscarSala(idSala);

            assertEquals(sala.getId(), response.id());
        }

        @Test
        void deveLancarNotFoundQuandoSalaNaoEncontrada(){
            UUID idInvalido = UUID.randomUUID();

            when(repository.findById(idInvalido)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> {
                service.buscarSala(idInvalido);
            });
        }
    }
}