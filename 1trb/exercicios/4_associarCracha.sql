DROP PROCEDURE IF EXISTS dbo.associarCracha;

CREATE OR REPLACE PROCEDURE dbo.associarCracha (
  IN id_jogador_param INTEGER,
  IN id_jogo_param VARCHAR(10),
  IN nome_cracha_param VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
DECLARE
  id_cracha INTEGER;
  pontuacao_jogador INTEGER;
  pontuacao_cracha INTEGER;
  
BEGIN
	
  -- Define o nível de isolamento da transação
  SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
  
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
  SELECT c.id INTO id_cracha
  FROM dbo.Cracha c
  WHERE c.nome = nome_cracha_param AND c.id_jogo = id_jogo_param;
  
  -- Obtém a pontuação necessária para obter o crachá
  SELECT c.pontuacao INTO pontuacao_cracha
  FROM dbo.Cracha c
  WHERE c.id = id_cracha;
  
  -- Obtém a pontuação do jogador no jogo em questão
  SELECT p.total_pontos INTO pontuacao_jogador 
  FROM dbo.PontosJogoPorJogador(id_jogo_param) p
  WHERE p.id_jogador = id_jogador_param;
  
  -- Verifica se o jogador atende aos requisitos para obter o crachá
  IF pontuacao_jogador >= pontuacao_cracha THEN
    BEGIN
  
      -- Tenta inserir o registro na tabela de associação
      INSERT INTO dbo.Crachas_jogador (id_jogador, id_cracha)
      VALUES (id_jogador_param, id_cracha);
      
      -- Verifica se houve violação de chave primária
      EXCEPTION
        WHEN unique_violation THEN
          -- Rollback da transação em caso de violação de chave primária
          RAISE EXCEPTION 'Jogador já possui esse crachá';
          
      -- Commit da transação
      COMMIT;
    END;
  ELSE RAISE EXCEPTION 'Jogador não possui pontuação suficiente para obter esse cracha';
  END IF;
END;
$$;

CALL dbo.associarCracha(1, 'jg1', 'Fantasma Branco');