CREATE TABLE IF NOT EXISTS tb_sala (
      id UUID PRIMARY KEY,
      nome VARCHAR(100) NOT NULL,
      capacidade INTEGER NOT NULL,
      localizacao VARCHAR(200),
      descricao VARCHAR(255)
);