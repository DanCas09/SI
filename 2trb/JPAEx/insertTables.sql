-- Regiao table
INSERT INTO dbo.Regiao (nome) VALUES
                                  ('Lisboa'),
                                  ('Porto'),
                                  ('Braga');

-- Jogador table
INSERT INTO dbo.Jogador (email, username, estado, regiao) VALUES
    ('jogador1@gmail.com', 'jogador1', 'Ativo', 'Lisboa'),
    ('jogador2@gmail.com', 'jogador2', 'Inativo', 'Porto'),
    ('jogador3@gmail.com', 'jogador3', 'Banido', 'Braga');


-- Jogo table
INSERT INTO dbo.Jogo (id, nome, url) VALUES
    ('jg1', 'Jogo1', 'http://www.jogo1.com'),
    ('jg2', 'Jogo2', 'http://www.jogo2.com');

-- Compra table
INSERT INTO dbo.Compra (id_jogador, id_jogo, preco, data_compra) VALUES
    (1, 'jg1', 10.99, '2022-01-01 12:00:00'),
    (2, 'jg2', 5.99, '2022-02-02 12:00:00'),
    (3, 'jg1', 8.99, '2022-03-03 12:00:00');

-- Partida table
INSERT INTO dbo.Partida (id_jogo, data_ini, data_fim, regiao) VALUES
    ('jg1', '2022-01-01 13:00:00', '2022-01-01 14:00:00', 'Lisboa'),
    ('jg2', '2022-02-02 13:00:00', NULL, 'Porto');

-- Partida_normal table
INSERT INTO dbo.Partida_normal (id_partida, grau_dificuldade) VALUES
    (1, 1),
    (2, 3);

-- Partida_multijogador table
INSERT INTO dbo.Partida_multijogador (id_partida, estado) VALUES
    (1, 'Em curso'),
    (2, 'A aguardar jogadores');

-- Pontuacao table
INSERT INTO dbo.Pontuacao (id_partida, id_jogador, pontuacao) VALUES
    (1, 1, 500),
    (1, 2, 50),
    (1, 3, 75),
    (2, 2, 200),
    (2, 3, 150);

-- Cracha table
INSERT INTO dbo.Cracha (id_jogo, nome, pontuacao, image_url) VALUES
    ('jg1', 'Fantasma Branco', 100, 'http://www.cracha1.com'),
	('jg1', 'Fantasma Azul', 150, 'http://www.cracha2.com'),
	('jg1', 'Fantasma Preto', 200, 'http://www.cracha3.com'),
	('jg2', 'Macaco Branco', 100, 'http://www.cracha4.com'),
    ('jg2', 'M', 500, 'http://www.cracha5.com');

/*
-- Crachas_jogador table
INSERT INTO dbo.Crachas_jogador (id_jogador, id_cracha) VALUES
    (1, 1),
    (2, 2),
    (3, 1),
    (3, 2);
	*/
INSERT INTO dbo.Amizade (id_jogador, id_amigo) VALUES
  (1, 2),
  (1, 3),
  (2, 3);

INSERT INTO dbo.Conversa (nome) VALUES
  ('Conversa 1'),
  ('Conversa 2'),
  ('Conversa 3');

INSERT INTO dbo.Conversa_jogador (id_conversa, id_jogador) VALUES
  (1, 1),
  (1, 2),
  (2, 1),
  (2, 3),
  (3, 2),
  (3, 3);

INSERT INTO dbo.Mensagem (id_conversa, id_jogador, data_mensagem, texto_mensagem) VALUES
  (1, 1, '2022-01-01 10:00:00', 'Olá!'),
  (1, 2, '2022-01-01 10:02:00', 'Oi!'),
  (1, 1, '2022-01-01 10:04:00', 'Como está?'),
  (1, 2, '2022-01-01 10:06:00', 'Estou bem, e tu?'),
  (2, 1, '2022-01-01 10:10:00', 'Olá, pessoal!'),
  (2, 3, '2022-01-01 10:12:00', 'Oi!'),
  (3, 2, '2022-01-01 10:20:00', 'Olá!'),
  (3, 3, '2022-01-01 10:22:00', 'Oi!');

INSERT INTO dbo.Estatisticas_jogador (id_jogador, num_partidas, num_jogos_dif, total_pontos) VALUES
  (1, 1, 1, 500),
  (2, 2, 2, 250),
  (3, 2, 2, 225);

INSERT INTO dbo.Estatisticas_jogo (id_jogo, num_partidas, num_jogadores, total_pontos) VALUES
  ('jg1', 20, 10, 5000),
  ('jg2', 30, 20, 8000);