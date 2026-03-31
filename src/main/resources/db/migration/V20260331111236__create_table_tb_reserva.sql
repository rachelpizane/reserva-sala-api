CREATE TABLE IF NOT EXISTS tb_reserva (
      id UUID PRIMARY KEY,
      organizador VARCHAR(100) NOT NULL,
      inicio TIMESTAMP NOT NULL,
      fim TIMESTAMP NOT NULL,
      id_sala UUID NOT NULL,
	FOREIGN KEY (id_sala) REFERENCES tb_sala (id) ON DELETE CASCADE
);