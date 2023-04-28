DROP FUNCTION IF EXISTS dbo.totalPontosJogador;
CREATE FUNCTION dbo.totalPontosJogador(jogador_id INTEGER)
    RETURNS INTEGER
    LANGUAGE plpgsql
AS $$
DECLARE 
    total_pontos_ret INTEGER := 0;
BEGIN
    IF NOT EXISTS(SELECT 1 FROM dbo.Jogador j WHERE j.id = jogador_id) THEN
        RAISE EXCEPTION 'jogador with id % does not exist', jogador_id;
    END IF;
    
    SELECT SUM(pontuacao)
    INTO total_pontos_ret
    FROM dbo.Pontuacao
    WHERE id_jogador = jogador_id;

    RETURN total_pontos_ret;
END;
$$;	

SELECT dbo.totalPontosJogador(3) -- replace 1 with the identifier of a player
