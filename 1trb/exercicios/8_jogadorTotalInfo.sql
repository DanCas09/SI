rollback;
DROP PROCEDURE IF EXISTS dbo.jogadorTotalInfo;

--(l) Criar a vista jogadorTotalInfo que cria uma vista com determinadas informações de jogadores
-- não banidos
CREATE OR REPLACE VIEW dbo.jogadorTotalInfo  AS
SELECT j.id, j.estado, j.email, j.username,
    COUNT(DISTINCT p.id_partida) AS num_partidas,
    COALESCE(c.num_jogos, 0) AS num_jogos,
    CAST(SUM(p.pontuacao) AS bigint) AS pontuacao
FROM dbo.jogador j 
LEFT JOIN dbo.Pontuacao p ON j.id = p.id_jogador
LEFT JOIN (
    SELECT id_jogador, COUNT(DISTINCT id) AS num_jogos
    FROM dbo.Compra
    GROUP BY id_jogador
) c ON j.id = c.id_jogador
WHERE j.estado != 'Banido'
GROUP BY j.id, j.estado, j.email, j.username, c.num_jogos;
