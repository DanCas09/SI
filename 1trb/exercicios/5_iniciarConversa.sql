--(i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) 
--dados o identificador de um jogador e o nome da conversa. O jogador deve ficar 
--automaticamente associado ? conversa e deve ser criada uma mensagem a informar que o 
--jogador criou a conversa. O procedimento deve devolver num par?metro de sa?da o 
--identificador da conversa criada. 


DROP PROCEDURE IF EXISTS dbo.iniciarConversa;

CREATE OR REPLACE PROCEDURE dbo.iniciarConversa (IN id_jogador INTEGER, IN nome_conversa VARCHAR(255), OUT id_conversa INTEGER)
LANGUAGE plpgsql
AS $$
DECLARE
    id_conversa_temp INTEGER;
    id_jogador_temp INTEGER;
    estado_temp VARCHAR(10);
BEGIN

  -- Define o nível de isolamento da transação
  SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
  
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
    BEGIN
        -- Creates a new conversation and associates the player with it
        INSERT INTO dbo.Conversa (nome) VALUES (nome_conversa) RETURNING id INTO id_conversa;
        INSERT INTO dbo.Conversa_jogador (id_conversa, id_jogador) VALUES (id_conversa, id_jogador);
        
        -- Creates a new message informing that the player started the conversation
        INSERT INTO dbo.Mensagem (id_conversa, id_jogador, data_mensagem, texto_mensagem) 
        VALUES (id_conversa, id_jogador, NOW(), 'Conversa iniciada por ' || (SELECT username FROM dbo.Jogador WHERE id = id_jogador));
    END;
END;
$$;

DO $$
DECLARE
    id_conversa INTEGER;
BEGIN
    -- Calls the stored procedure to initiate a conversation and gets the conversation id as output
    CALL dbo.iniciarConversa(1, 'Conversa banida', id_conversa);
    
    -- Displays the conversation id
    RAISE NOTICE 'A conversa foi iniciada com o id %', id_conversa;
END;
$$;
