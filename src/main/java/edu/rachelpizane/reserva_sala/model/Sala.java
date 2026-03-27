package edu.rachelpizane.reserva_sala.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private int capacidade;

    @Column(length = 200)
    private String localizacao;

    private String descricao;
}
