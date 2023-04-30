--(d) Cria a o procedimento armazenado criarJogador que recebe como parâmetro o email, região e 
--username e cria um jogador
DROP PROCEDURE IF EXISTS dbo.criarJogador;
CREATE PROCEDURE dbo.criarJogador(emailJ VARCHAR(255), usernameJ VARCHAR(255), estadoJ VARCHAR(10), regiaoJ VARCHAR(25))
    LANGUAGE plpgsql
AS $$
DECLARE
	var1 VARCHAR(255);
BEGIN
	PERFORM dbo.throw_if_emailOrUsernameOfJogador_already_exists(emailJ, usernameJ);
    INSERT INTO dbo.Jogador (email, username, estado, regiao)
    VALUES (emailJ, usernameJ, estadoJ, regiaoJ);
END;
$$;

-- Cria o procedimento armazenado desativarJogador que recebe um id de um jogador e altera o
-- seu estado para 'Inativo'
DROP PROCEDURE IF EXISTS dbo.desativarJogador;
CREATE PROCEDURE dbo.desativarJogador(idJ Integer)
    LANGUAGE plpgsql
AS $$
BEGIN
	PERFORM dbo.throw_if_idJogador_does_not_exist(idJ);
    UPDATE dbo.Jogador SET estado = 'Inativo' WHERE id = idJ;
END;
$$;


-- Cria o procedimento armazenado banirJogador que recebe um id de um jogador e altera o
-- seu estado para 'Banido'.
DROP PROCEDURE IF EXISTS dbo.banirJogador;
CREATE PROCEDURE dbo.banirJogador(idJ Integer)
    LANGUAGE plpgsql
AS $$
BEGIN
	PERFORM dbo.throw_if_idJogador_does_not_exist(idJ);
    UPDATE dbo.Jogador SET estado = 'Banido' WHERE id = idJ;
END;
$$;
