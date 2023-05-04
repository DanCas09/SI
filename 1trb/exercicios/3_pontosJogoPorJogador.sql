--(g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo 
--e devolve uma tabela com duas colunas (identificador de jogador, total de pontos) em que 
--cada linha contém o identificador de um jogador e o total de pontos que esse jogador teve 
--nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo. 


DROP FUNCTION IF EXISTS dbo.PontosJogoPorJogador;

CREATE OR REPLACE FUNCTION dbo.PontosJogoPorJogador(IN jogo_referencia VARCHAR(10))
RETURNS TABLE(id_jogador INTEGER, total_pontos INTEGER) AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM dbo.Jogo WHERE id = jogo_referencia) THEN
        RAISE EXCEPTION 'jogo with % does not exist', jogo_referencia;
    END IF;

    RETURN QUERY
        SELECT dbo.Pontuacao.id_jogador, CAST(SUM(dbo.Pontuacao.pontuacao) AS INTEGER) as total_pontos
        FROM dbo.Pontuacao
        JOIN dbo.Partida ON dbo.Partida.id = dbo.Pontuacao.id_partida
        WHERE dbo.Partida.id_jogo = jogo_referencia
        GROUP BY dbo.Pontuacao.id_jogador;
END;
$$ LANGUAGE plpgsql;



