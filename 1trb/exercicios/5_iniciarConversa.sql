--(i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) 
--dados o identificador de um jogador e o nome da conversa. O jogador deve ficar 
--automaticamente associado ? conversa e deve ser criada uma mensagem a informar que o 
--jogador criou a conversa. O procedimento deve devolver num par?metro de sa?da o 
--identificador da conversa criada. 
DROP PROCEDURE IF EXISTS dbo.iniciarConversa;
DROP PROCEDURE IF EXISTS dbo.iniciarConversa_trans;
DROP PROCEDURE IF EXISTS dbo.iniciarConversa_handler;
DROP PROCEDURE IF EXISTS dbo.iniciarConversa_logic;

CREATE OR REPLACE PROCEDURE dbo.iniciarConversa_logic (
	IN id_jogador INTEGER, 
	IN nome_conversa VARCHAR(255),
	OUT id_conversa INTEGER)
LANGUAGE plpgsql
AS $$
DECLARE
    id_conversa_temp INTEGER;
    id_jogador_temp INTEGER;
    estado_temp VARCHAR(10);
BEGIN

    -- Verifies if the player is valid and active
    SELECT dbo.Jogador.id, dbo.Jogador.estado INTO id_jogador_temp, estado_temp FROM dbo.Jogador WHERE id = id_jogador;
    IF id_jogador_temp IS NULL OR id_jogador_temp <> id_jogador OR estado_temp <> 'Ativo' THEN
        RAISE EXCEPTION 'Jogador banido ou inativo';
    END IF;
    
    -- Verifies if the conversation name already exists
--     SELECT id INTO id_conversa_temp FROM dbo.Conversa WHERE nome = nome_conversa;
--     IF id_conversa_temp IS NOT NULL THEN
--         RAISE EXCEPTION 'Já existe uma conversa com esse nome';
--     END IF;
    
    -- Starts a new transaction to ensure data consistency
        -- Creates a new conversation and associates the player with it
        INSERT INTO dbo.Conversa (nome) VALUES (nome_conversa) RETURNING id INTO id_conversa;
        INSERT INTO dbo.Conversa_jogador (id_conversa, id_jogador) VALUES (id_conversa, id_jogador);
        
        -- Creates a new message informing that the player started the conversation
        INSERT INTO dbo.Mensagem (id_conversa, id_jogador, data_mensagem, texto_mensagem) 
        VALUES (id_conversa, id_jogador, NOW(), 'Conversa iniciada por ' || (SELECT username FROM dbo.Jogador WHERE id = id_jogador));
END;
$$;

CREATE OR REPLACE PROCEDURE dbo.iniciarConversa_handler (
	IN id_jogador INTEGER, 
	IN nome_conversa VARCHAR(255),
	OUT id_conversa INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
	BEGIN
    -- Call the internal procedure
    CALL dbo.iniciarConversa_logic(id_jogador, nome_conversa, id_conversa);
    
  EXCEPTION 
    WHEN OTHERS THEN
	-- Raise the exception with the error message from the internal procedure
      RAISE EXCEPTION '%', SQLERRM;
	  
      -- Rollback the transaction on exception
      ROLLBACK;
  END;
END;
$$;

CREATE OR REPLACE PROCEDURE dbo.iniciarConversaProcedure (
	IN id_jogador INTEGER, 
	IN nome_conversa VARCHAR(255),
	OUT id_conversa INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
	--COMMIT; --rollback; -- ou commit. Termina transação corrente e inicia outra
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED; 
	CALL dbo.iniciarConversa_handler(id_jogador, nome_conversa, id_conversa);
	-- RAISE NOTICE 'A conversa foi iniciada com o id %', id_conversa;
END;
$$;


CREATE OR REPLACE FUNCTION dbo.iniciarConversa(
    id_jogador INTEGER,
    nome_conversa VARCHAR(255)
) RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    id_conversa INTEGER;
BEGIN
    -- Calls the iniciarConversa procedure to initiate a conversation
    CALL dbo.iniciarConversaProcedure(id_jogador, nome_conversa, id_conversa);
	
	RAISE NOTICE 'A conversa foi iniciada com o id %', id_conversa;

    -- Returns the conversation id
    RETURN id_conversa;
END;
$$;