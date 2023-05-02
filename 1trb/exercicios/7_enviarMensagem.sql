DROP FUNCTION IF EXISTS dbo.enviarmensagem;

CREATE OR REPLACE PROCEDURE dbo.enviarmensagem(
    p_id_conversa INTEGER,
    p_id_jogador INTEGER,
    p_texto_mensagem VARCHAR(450)
) AS $$
BEGIN
    BEGIN
        INSERT INTO dbo.Mensagem (id_conversa, id_jogador, data_mensagem, texto_mensagem)
        VALUES (p_id_conversa, p_id_jogador, NOW(), p_texto_mensagem);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION 'Error inserting message: %', SQLERRM;
    END;
END;
$$ LANGUAGE plpgsql;