CREATE TABLE dbo.Jogador (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  username VARCHAR(255) UNIQUE NOT NULL,
  estado VARCHAR(10) NOT NULL,
  regiao VARCHAR(255) NOT NULL,
  CONSTRAINT check_status CHECK (estado IN ('Ativo', 'Inativo', 'Banido'))
);

CREATE TABLE dbo.Regiao (
  nome VARCHAR(255) PRIMARY KEY
);

CREATE TABLE dbo.Jogo (
  id VARCHAR(10) PRIMARY KEY,
  nome VARCHAR(255) UNIQUE NOT NULL,
  url VARCHAR(255) NOT NULL
);

CREATE TABLE dbo.Compra (
  id SERIAL PRIMARY KEY,
  id_jogador INTEGER NOT NULL REFERENCES dbo.Jogador (id) ON DELETE CASCADE,
  id_jogo VARCHAR(10) NOT NULL REFERENCES dbo.Jogo (id) ON DELETE CASCADE,
  preco DECIMAL(10, 2) NOT NULL,
  data_compra TIMESTAMP NOT NULL
);

CREATE TABLE dbo.Partida (
  id SERIAL PRIMARY KEY,
  id_jogo VARCHAR(10) NOT NULL REFERENCES dbo.Jogo (id) ON DELETE CASCADE,
  data_ini TIMESTAMP NOT NULL,
  data_fim TIMESTAMP,
  regiao VARCHAR(255) NOT NULL,
  CHECK (data_fim >= data_ini)
);

CREATE TABLE dbo.Partida_normal (
  id_partida INT PRIMARY KEY REFERENCES dbo.Partida(id) ON DELETE CASCADE,
  grau_dificuldade INT NOT NULL,
  CONSTRAINT check_grau_dificuldade CHECK (grau_dificuldade >= 1 AND grau_dificuldade <= 3)
);

CREATE TABLE dbo.Partida_multijogador (
  id_partida INT PRIMARY KEY REFERENCES dbo.Partida(id) ON DELETE CASCADE,
  estado VARCHAR(50) NOT NULL,
  CONSTRAINT check_estado CHECK (estado IN ('Por iniciar', 'A aguardar jogadores', 'Em curso' , 'Terminada'))
);

CREATE TABLE dbo.Pontuacao (
  id_partida INTEGER NOT NULL REFERENCES dbo.Partida (id) ON DELETE CASCADE,
  id_jogador INTEGER NOT NULL REFERENCES dbo.Jogador (id) ON DELETE CASCADE,
  pontuacao INTEGER,
  PRIMARY KEY (id_partida, id_jogador)
);

CREATE TABLE dbo.Cracha (
  id SERIAL PRIMARY KEY,
  id_jogo VARCHAR(10) NOT NULL REFERENCES dbo.Jogo (id) ON DELETE CASCADE,
  nome VARCHAR(255) UNIQUE NOT NULL,
  pontuacao INTEGER NOT NULL,
  image_url VARCHAR(255) NOT NULL
);

CREATE TABLE dbo.Crachas_jogador (
  id_jogador INTEGER NOT NULL REFERENCES dbo.Jogador (id) ON DELETE CASCADE,
  id_cracha INTEGER NOT NULL REFERENCES dbo.Cracha (id) ON DELETE CASCADE,
  PRIMARY KEY (id_jogador, id_cracha)
);

CREATE TABLE dbo.Amizade (
  id_jogador INTEGER NOT NULL REFERENCES dbo.Jogador (id) ON DELETE CASCADE,
  id_amigo INTEGER NOT NULL REFERENCES dbo.Jogador (id) ON DELETE CASCADE,
  PRIMARY KEY (id_jogador, id_amigo)
);

CREATE TABLE dbo.Conversa (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL
);

CREATE TABLE dbo.Conversa_jogador (
  id_conversa INTEGER NOT NULL REFERENCES dbo.Conversa (id) ON DELETE CASCADE,
  id_jogador INTEGER NOT NULL REFERENCES dbo.Jogador (id) ON DELETE CASCADE,
  PRIMARY KEY(id_conversa, id_jogador)
);

CREATE TABLE dbo.Mensagem (
  id SERIAL PRIMARY KEY,
  id_conversa INTEGER NOT NULL,
  id_jogador INTEGER NOT NULL,
  data_mensagem TIMESTAMP NOT NULL,
  texto_mensagem VARCHAR(450) NOT NULL,
  FOREIGN KEY (id_conversa) REFERENCES dbo.Conversa (id) ON DELETE CASCADE,
  FOREIGN KEY (id_jogador) REFERENCES dbo.Jogador (id) ON DELETE CASCADE
);

CREATE TABLE dbo.Estatisticas_jogador (
  id_jogador INTEGER NOT NULL,
  num_partidas INTEGER NOT NULL,
  num_jogos_dif INTEGER NOT NULL,
  total_pontos INTEGER NOT NULL,
  PRIMARY KEY (id_jogador),
  FOREIGN KEY (id_jogador) REFERENCES dbo.Jogador (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE dbo.Estatisticas_jogo (
  id_jogo VARCHAR(10) NOT NULL,
  num_partidas INTEGER NOT NULL,
  num_jogadores INTEGER NOT NULL,
  total_pontos INTEGER NOT NULL,
  PRIMARY KEY (id_jogo),
  FOREIGN KEY (id_jogo) REFERENCES dbo.Jogo (id) ON UPDATE CASCADE ON DELETE CASCADE
);