DROP PROCEDURE IF EXISTS dbo.juntarConversa;

--(j) Criar o procedimento juntarConversa que recebe como parâmetro o identificador de um 
--jogador e o identificador de uma conversa e associa-os  
CREATE OR REPLACE PROCEDURE dbo.juntarConversa (
  IN idJ INTEGER,
  IN idC INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Verifica se o jogador existe
    PERFORM dbo.throw_if_idJogador_does_not_exist(idJ);
    
    -- Verifica se a conversa existe
    PERFORM dbo.throw_if_idConversa_does_not_exist(idC);
	
	-- Verifica se a conversa já está associada ao jogador
    PERFORM dbo.throw_if_conversaJogador_already_exists(idC, idJ);

    -- Tenta inserir um novo valor na tabela de associação de conversa e jogador
    INSERT INTO dbo.conversa_jogador (id_jogador,id_conversa) VALUES (idJ, idC);

END;
$$;




