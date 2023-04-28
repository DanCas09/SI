CREATE OR REPLACE FUNCTION dbo.atribuir_crachas()
RETURNS TRIGGER AS $$
DECLARE
    jog_id INTEGER;
    cracha_id INTEGER;
    pontuacao_temp INTEGER;
	jogadores RECORD;
	badges RECORD;
BEGIN
    IF NEW.estado = 'Terminada' THEN
        -- loop through all players in the game
        FOR jogadores IN SELECT * FROM dbo.Pontuacao p WHERE p.id_partida = NEW.id_partida LOOP
            jog_id := jogadores.id_jogador;
            pontuacao_temp := jogadores.pontuacao;
            -- find all badges that the player has not yet earned
            FOR badges IN SELECT * FROM dbo.Cracha c WHERE c.pontuacao <= pontuacao_temp AND c.id NOT IN (SELECT id_cracha FROM dbo.Crachas_Jogador jc WHERE jc.id_jogador = jog_id) LOOP
                cracha_id := badges.id;
                -- award the badge to the player
                INSERT INTO dbo.Crachas_Jogador (id_jogador, id_cracha) VALUES (jog_id, cracha_id);
            END LOOP;
        END LOOP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER atribuir_crachas_trigger
AFTER UPDATE ON dbo.Partida_multijogador
FOR EACH ROW
EXECUTE FUNCTION dbo.atribuir_crachas();
