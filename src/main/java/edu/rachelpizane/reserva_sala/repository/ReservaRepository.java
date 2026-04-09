package edu.rachelpizane.reserva_sala.repository;

import edu.rachelpizane.reserva_sala.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, UUID>  {

    @Query("""
    SELECT (COUNT(r) > 0)
    FROM Reserva r
    WHERE r.sala.id = :salaId
      AND :inicio < r.fim
      AND :fim > r.inicio
    """)
    boolean existeConflitoHorarios(
            UUID salaId,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    @Query("""
    SELECT r
    FROM Reserva r
    JOIN FETCH r.sala s
    WHERE r.inicio >= :inicio AND r.inicio < :fim
    ORDER BY r.inicio
    """)
    List<Reserva> buscarReservasEntreDatas(LocalDateTime inicio, LocalDateTime fim);

    @Query("""
    SELECT COUNT(r) > 0
    FROM Reserva r
    WHERE r.inicio >= :inicio AND r.inicio < :fim
    """)
    boolean existsReservaEntreDatas(LocalDateTime inicio, LocalDateTime fim);
}
