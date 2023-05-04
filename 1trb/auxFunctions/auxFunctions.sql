-- Função auxiliar que recebe um id de um jogador e lança uma exceção caso este não
-- exista na base de dados
CREATE OR REPLACE FUNCTION dbo.throw_if_idJogador_does_not_exist(
  idJ INTEGER 
)
RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM dbo.Jogador WHERE id = idJ
    ) THEN
        RAISE invalid_parameter_value USING MESSAGE = 'invalid_input_value';
    END IF;
END;
$$;

-- Função auxiliar que recebe um email de um jogador e lança uma exceção caso este já
-- exista na base de dados
CREATE OR REPLACE FUNCTION dbo.throw_if_emailOrUsernameOfJogador_already_exists(
  emailJ VARCHAR(255),
  usernameJ VARCHAR(255)
)
RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM dbo.Jogador WHERE (email = emailJ OR username = usernameJ)
    ) THEN
        RAISE invalid_parameter_value USING MESSAGE = 'invalid_input_value';
    END IF;
END;
$$;

CREATE OR REPLACE FUNCTION dbo.throw_if_regiao_does_not_exist(
	nomeRegiao VARCHAR(255)
)
RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
	IF NOT EXISTS(
		SELECT 1
		FROM dbo.Regiao WHERE nome = nomeRegiao
	) THEN
		RAISE invalid_parameter_value USING MESSAGE = 'invalid_input_value';
	END IF;
END;
$$;


CREATE OR REPLACE FUNCTION dbo.throw_if_idConversa_does_not_exist(
    idC Integer
)
RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS(
        SELECT 1
        FROM dbo.Conversa WHERE id = idC
    ) THEN
        RAISE invalid_parameter_value USING MESSAGE = 'invalid_input_value';
    END IF;
END;
$$;


CREATE OR REPLACE FUNCTION dbo.throw_if_conversaJogador_already_exists(
idC INTEGER,
 IDJ INTEGER
)
RETURNS VOID
LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS(
        SELECT 1
        FROM dbo.Conversa_jogador WHERE id_conversa = idC and id_jogador = idJ
    ) THEN
        RAISE unique_violation USING MESSAGE = 'unique_violation';
    END IF;
END;
$$;


-- Procedimento armazenado que insere dados para teste
CREATE OR REPLACE PROCEDURE dbo.insert_test_values(
	id_jogador_var INTEGER
)
LANGUAGE plpgsql
AS $$
DECLARE
id_partida1_var INTEGER;
	id_partida2_var INTEGER;
	id_partida3_var INTEGER;
BEGIN

    -- Insere um jogo teste
INSERT INTO dbo.Jogo (id, nome, url)
VALUES ('jogoTst', 'JogoTeste', 'http://www.jogoteste.com');

-- Insere as partidas em que o jogador teste participou
INSERT INTO dbo.Partida (id_jogo, data_ini, data_fim, regiao)
VALUES ('jogoTst', '2022-01-01 13:00:00', '2022-01-01 14:00:00', 'Lisboa')
    RETURNING id INTO id_partida1_var;

INSERT INTO dbo.Partida (id_jogo, data_ini, data_fim, regiao)
VALUES ('jogoTst', '2023-04-05 15:20:00', '2023-04-05 16:00:00', 'Lisboa')
    RETURNING id INTO id_partida2_var;

INSERT INTO dbo.Partida (id_jogo, data_ini, data_fim, regiao)
VALUES ('jogoTst', '2022-05-05 20:20:00', '2022-05-06 21:00:00', 'Lisboa')
    RETURNING id INTO id_partida3_var;

-- Insere as pontuações que o jogador teste obteve
INSERT INTO dbo.Pontuacao (id_partida, id_jogador, pontuacao)
VALUES (id_partida1_var, id_jogador_var, 200);

INSERT INTO dbo.Pontuacao (id_partida, id_jogador, pontuacao)
VALUES (id_partida2_var, id_jogador_var, 100);

INSERT INTO dbo.Pontuacao (id_partida, id_jogador, pontuacao)
VALUES (id_partida3_var, id_jogador_var, 200);

-- Insere as compras de jogos efetuadas pelo jogador teste
INSERT INTO dbo.Compra (id_jogador, id_jogo, preco, data_compra)
VALUES (id_jogador_var, 'jogoTst', 19.99, '2023-03-04 12:00:00');

INSERT INTO dbo.Compra (id_jogador, id_jogo, preco, data_compra)
VALUES (id_jogador_var, 'jogoTst', 10.99, '2020-05-11 13:30:00');

INSERT INTO dbo.Compra (id_jogador, id_jogo, preco, data_compra)
VALUES (id_jogador_var, 'jogoTst', 19.99, '2021-11-01 10:40:00');

INSERT INTO dbo.Compra (id_jogador, id_jogo, preco, data_compra)
VALUES (id_jogador_var, 'jogoTst', 10.99, '2022-01-04 16:20:00');
END;
$$;


-- Procedimento armazenado que remove os dados armazenados para teste
CREATE OR REPLACE PROCEDURE dbo.delete_test_values(
	id_jogador_var INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
DELETE FROM dbo.Jogador j WHERE j.id = id_jogador_var;
DELETE FROM dbo.Compra c  WHERE c.id_jogador = id_jogador_var;
DELETE FROM dbo.Pontuacao p WHERE p.id_jogador = id_jogador_var;
DELETE FROM dbo.Partida p WHERE p.id_jogo = 'jogoTst';
DELETE FROM dbo.Jogo j WHERE j.id = 'jogoTst';
END;
$$;