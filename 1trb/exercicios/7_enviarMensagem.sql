DROP FUNCTION IF EXISTS dbo.enviarmensagem;
DROP FUNCTION IF EXISTS dbo.enviarmensagem_handler;
DROP FUNCTION IF EXISTS dbo.enviarmensagem_logic;

CREATE OR REPLACE PROCEDURE dbo.enviarMensagem_logic(
    IN p_id_conversa INTEGER,
    IN p_id_jogador INTEGER,
    IN p_texto_mensagem VARCHAR(450)
) 
LANGUAGE plpgsql
AS $$
 BEGIN

	IF NOT EXISTS (
        SELECT 1
        FROM dbo.Conversa c
        WHERE c.id = p_id_conversa
    ) THEN
        RAISE EXCEPTION 'conversa não existe';
    END IF; 
	
	IF NOT EXISTS (
        SELECT 1
        FROM dbo.Jogador j
        WHERE j.id = p_id_jogador
    ) THEN
        RAISE EXCEPTION 'jogador não existe';
    END IF; 
	
	IF NOT EXISTS (
        SELECT 1
        FROM dbo.conversa_jogador cj
        WHERE cj.id_conversa = p_id_conversa AND cj.id_jogador = p_id_jogador
    ) THEN
        RAISE EXCEPTION 'jogador não pertence a essa conversa';
    END IF; 
	
	
	INSERT INTO dbo.Mensagem (id_conversa, id_jogador, data_mensagem, texto_mensagem)
    VALUES (p_id_conversa, p_id_jogador, NOW(), p_texto_mensagem);
    EXCEPTION
        WHEN unique_violation THEN
            RAISE EXCEPTION 'Error inserting message: %', SQLERRM;
 END;
$$;


-- Procedure para gerir erros
CREATE OR REPLACE PROCEDURE dbo.enviarMensagem_error_handler(
    IN p_id_conversa INTEGER,
    IN p_id_jogador INTEGER,
    IN p_texto_mensagem VARCHAR(450)
)
LANGUAGE plpgsql
AS $$
BEGIN
   BEGIN
    -- Call the internal procedure
    CALL dbo.associarCracha_logic(p_id_conversa, p_id_jogador, p_texto_mensagem );
    
  EXCEPTION 
    WHEN OTHERS THEN
	-- Raise the exception with the error message from the internal procedure
      RAISE NOTICE '%', SQLERRM;
	  
      -- Rollback the transaction on exception
      ROLLBACK;
  END;
END;
$$;


-- Procedure principal, para estabelecer o nível de isolamento e chamar as outras procedures
CREATE OR REPLACE PROCEDURE dbo.enviarMensagem(
    IN p_id_conversa INTEGER,
    IN p_id_jogador INTEGER,
    IN p_texto_mensagem VARCHAR(450)
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Definir o nível de isolamento da transação
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    -- Chamada à procedure de lógica
    CALL dbo.enviarMensagem_logic(p_id_conversa, p_id_jogador, p_texto_mensagem);
    
END;
$$;