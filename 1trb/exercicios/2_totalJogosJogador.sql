DROP FUNCTION IF EXISTS dbo.totalJogosJogador;

-- Create the function
CREATE FUNCTION dbo.totalJogosJogador(jogador_id INTEGER)
    RETURNS INTEGER
    LANGUAGE plpgsql
AS $$
DECLARE 
    total_jogos_ret INTEGER;
BEGIN
    IF NOT EXISTS(SELECT 1 FROM dbo.Jogador WHERE id = jogador_id) THEN
        RAISE EXCEPTION 'jogador with id % does not exist', jogador_id;
    END IF;
    
    SELECT COUNT(DISTINCT dbo.Partida.id_jogo)
    INTO total_jogos_ret
    FROM dbo.Pontuacao
    JOIN dbo.Partida ON dbo.Pontuacao.id_partida = dbo.Partida.id
    JOIN dbo.Jogo ON Partida.id_jogo = dbo.Jogo.id
    WHERE dbo.Pontuacao.id_jogador = jogador_id;

    IF total_jogos_ret IS NULL THEN
        total_jogos_ret := 0;
    END IF;

    RETURN total_jogos_ret;
END;
$$;


