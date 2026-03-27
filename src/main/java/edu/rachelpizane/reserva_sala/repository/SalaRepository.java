package edu.rachelpizane.reserva_sala.repository;

import edu.rachelpizane.reserva_sala.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SalaRepository extends JpaRepository<Sala, UUID> {
}
