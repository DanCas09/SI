DROP PROCEDURE IF EXISTS dbo.associarCracha;
DROP PROCEDURE IF EXISTS dbo.associarCracha_handler;
DROP PROCEDURE IF EXISTS dbo.associarCracha_logic;

CREATE OR REPLACE PROCEDURE dbo.associarCracha_logic (
  IN id_jogador_param INTEGER,
  IN id_jogo_param VARCHAR(10),
  IN nome_cracha_param VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
DECLARE
  id_cracha_param INTEGER;
  pontuacao_jogador INTEGER;
  pontuacao_cracha INTEGER;
  
BEGIN
	
  -- verifica parâmetros jogador/jogo
  IF NOT EXISTS (
        SELECT 1
        FROM dbo.Compra c
        WHERE c.id_jogador = id_jogador_param AND c.id_jogo = id_jogo_param
    ) THEN
        RAISE EXCEPTION 'jogador não possui esse jogo';
    END IF; 
	
  -- verifica parâmetros jogo/cracha
  IF NOT EXISTS (
        SELECT 1
        FROM dbo.Jogo j, dbo.Cracha c
        WHERE j.id = id_jogo_param AND c.nome = nome_cracha_param
    ) THEN
        RAISE EXCEPTION 'id de jogo inválido ou nome de cracha inválido';
    END IF; 
  
  -- Obtém o ID do crachá com base no nome e no jogo
  SELECT c.id INTO id_cracha_param
  FROM dbo.Cracha c
  WHERE c.nome = nome_cracha_param AND c.id_jogo = id_jogo_param;
  
  -- Obtém a pontuação necessária para obter o crachá
  SELECT c.pontuacao INTO pontuacao_cracha
  FROM dbo.Cracha c
  WHERE c.id = id_cracha_param;
  
  -- Obtém a pontuação do jogador no jogo em questão
  SELECT p.total_pontos INTO pontuacao_jogador 
  FROM dbo.PontosJogoPorJogador(id_jogo_param) p
  WHERE p.id_jogador = id_jogador_param;
  
  -- Verifica se o jogador atende aos requisitos para obter o crachá
  IF pontuacao_jogador >= pontuacao_cracha THEN
	  
	    IF NOT EXISTS (
        SELECT 1
        FROM dbo.Crachas_jogador cj
        WHERE cj.id_jogador =  id_jogador_param 
			AND cj.id_cracha = id_cracha_param
    ) THEN
        	INSERT INTO dbo.Crachas_jogador (id_jogador, id_cracha)
      		VALUES (id_jogador_param, id_cracha_param);
		ELSE RAISE EXCEPTION 'Jogador já possui esse crachá';
    END IF;

  ELSE RAISE EXCEPTION 'Jogador não possui pontuação suficiente para obter esse cracha';
  END IF;
END;
$$;


CREATE OR REPLACE PROCEDURE dbo.associarCracha_handler (
  IN id_jogador_param INTEGER,
  IN id_jogo_param VARCHAR(10),
  IN nome_cracha_param VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
  
  BEGIN
    -- Call the internal procedure
    CALL dbo.associarCracha_logic(id_jogador_param, id_jogo_param, nome_cracha_param);
    
  EXCEPTION 
    WHEN OTHERS THEN
	-- Raise the exception with the error message from the internal procedure
      RAISE NOTICE '%', SQLERRM;
	  
      -- Rollback the transaction on exception
      ROLLBACK;
  END;
END;
$$;


CREATE OR REPLACE PROCEDURE dbo.associarCracha (
  IN id_jogador_param INTEGER,
  IN id_jogo_param VARCHAR(10),
  IN nome_cracha_param VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN 
	--COMMIT; --rollback; -- ou commit. Termina transação corrente e inicia outra
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED; 
	CALL dbo.associarCracha_handler(id_jogador_param, id_jogo_param, nome_cracha_param);

END;
$$;

-- Teste --
 CALL dbo.associarCracha(1, 'jg1', 'Fantasma Branco');

