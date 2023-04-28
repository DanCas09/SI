DROP PROCEDURE IF EXISTS dbo.juntarConversa;

--(j) Criar o procedimento juntarConversa que recebe como parâmetro o identificador de um 
--jogador e o identeficador de uma conversa e associa-os  
CREATE OR REPLACE PROCEDURE dbo.juntarConversa (
  IN id_jogador_param INTEGER,
  IN id_conversa_param INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Verifica se o jogador existe
    IF NOT EXISTS (SELECT 1 FROM dbo.Jogador WHERE id = id_jogador_param) THEN
        RAISE EXCEPTION 'Jogador com id % não existe', id_jogador_param;
    END IF;
    
    -- Verifica se a conversa existe
    IF NOT EXISTS (SELECT 1 FROM dbo.Conversa WHERE id = id_conversa_param) THEN
        RAISE EXCEPTION 'Conversa com id % não existe', id_conversa_param;
    END IF;

    -- Tenta inserir um novo valor na tabela de associação de conversa e jogador
    INSERT INTO dbo.conversa_jogador (id_jogador,id_conversa) 
    VALUES (id_jogador_param, id_conversa_param);

    -- Verifica se houve violação de chave primária
    EXCEPTION
        WHEN unique_violation THEN
          -- Rollback da transação em caso de violação de chave primária
          RAISE EXCEPTION 'Jogador já está associado a esta conversa';
    
	RAISE NOTICE 'Jogador % adicionado com sucesso à conversa %', id_jogador, id_conversa;
    -- Commit da transação
    COMMIT;
END;
$$;

CALL dbo.juntarConversa(1,1);
