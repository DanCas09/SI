-------------------------------------------
-- Tests funcoesJogador criarJogador (d) --
-------------------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_funcoesJogador_criarJogador(
	emailJ VARCHAR(255),
	usernameJ VARCHAR(255),
	estadoJ VARCHAR(10),
	regiaoJ VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
DECLARE
	invalid_parameters BOOLEAN = FALSE;
	successful_test BOOLEAN;
BEGIN	
	BEGIN
		-- Chama o procedimento armazenado
    	CALL dbo.criarJogador(emailJ, usernameJ, estadoJ, regiaoJ);
    EXCEPTION
		-- Verifica se houve algum problema com os parametros passados
		WHEN invalid_parameter_value THEN
            invalid_parameters := TRUE;	
    END;
	
	-- Verifica se o Jogador foi corretamente adicionado
     IF EXISTS (SELECT 1 FROM dbo.Jogador WHERE email = emailJ) AND NOT invalid_parameters THEN
	  successful_test:= TRUE;
      RAISE NOTICE 'Teste (d): criarJogador: Resultado OK';
    ELSE
      IF invalid_parameters THEN
	  RAISE NOTICE 'Teste (d): parametros passados como parametro invalidos: Resultado FAIL';
	  ELSE
      RAISE NOTICE 'Teste (d): criarJogador: Resultado FAIL';
	  END IF;
    END IF;
	
	-- Remove o registro na tabela de jogador
	IF successful_test THEN
	 DELETE FROM dbo.Jogador
	 WHERE email = emailJ;
	END IF;
END;
$$;

CALL dbo.test_funcoesJogador_criarJogador('jogador8@gmail.com', 'jogador4', 'Inativo', 'Lisboa');


-----------------------------------------------
-- Tests funcoesJogador desativarJogador (d) --
-----------------------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_funcoesJogador_desativarJogador(
	idJ Integer
)
LANGUAGE plpgsql
AS $$
DECLARE
    estadoJ VARCHAR(10);
	invalid_parameters BOOLEAN;
BEGIN
    -- Guarda o estado do jogador a ser alterado
	SELECT estado INTO estadoJ
	  FROM dbo.Jogador
	  WHERE id = idJ;
	
	BEGIN
		-- Chama o procedimento armazenado
    	CALL dbo.desativarJogador(idj);
	EXCEPTION
		-- Verifica se houve algum problema com os parametros passados
		WHEN invalid_parameter_value THEN
            invalid_parameters := TRUE;	
    END;
    
	-- Verifica se o estado foi corretamente alterado para 'Inativo'
    IF 'Inativo' = (SELECT estado FROM dbo.Jogador WHERE id = idJ) THEN
      RAISE NOTICE 'Teste (d): desativarJogador: Resultado OK';
    ELSE
	  IF invalid_parameters THEN
	  RAISE NOTICE 'Teste (d): userId passado como parametro é invalido: Resultado FAIL';
	  ELSE
      RAISE NOTICE 'Teste (d): desativarJogador: Resultado FAIL';
	  END IF;
    END IF;
	
	--Altera o estado do jogador para o inicial
	UPDATE dbo.Jogador
	SET estado = estadoJ
	WHERE id = idJ;
END;
$$;

CALL dbo.test_funcoesJogador_desativarJogador(3);



-------------------------------------------
-- Tests funcoesJogador banirJogador (d) --
-------------------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_funcoesJogador_banirJogador(
	idJ Integer
)
LANGUAGE plpgsql
AS $$
DECLARE
    estadoJ VARCHAR(10);
	invalid_parameters BOOLEAN;
BEGIN

	-- Guarda o estado do jogador a ser alterado
	SELECT estado INTO estadoJ
	  FROM dbo.Jogador
	  WHERE id = idJ;
	  	
    BEGIN
		-- Chama o procedimento armazenado
    	CALL dbo.banirJogador(idj);
	EXCEPTION
		-- Verifica se houve algum problema com os parametros passados
		WHEN invalid_parameter_value THEN
            invalid_parameters := TRUE;	
    END;
    
	-- Verifica se o estado foi corretamente alterado para 'Banido'
    IF 'Banido' = (SELECT estado FROM dbo.Jogador WHERE id = idJ) THEN
      RAISE NOTICE 'Teste (d): banirJogador: Resultado OK';
    ELSE
       IF invalid_parameters THEN
	  RAISE NOTICE 'Teste (d): userId passado como parametro é invalido: Resultado FAIL';
	  ELSE
      RAISE NOTICE 'Teste (d): banirJogador: Resultado FAIL';
	  END IF;
    END IF;
	
	-- Altera o estado do jogador para o inicial
	UPDATE dbo.Jogador
	SET estado = estadoJ
	WHERE id = idJ;
END;
$$;

CALL dbo.test_funcoesJogador_banirJogador(3);


---------------------------------
-- Test totalPontosJogador (e) --
---------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_total_pontos_jogador()
LANGUAGE plpgsql
AS $$
DECLARE
    total_pontos_jogador INTEGER;
BEGIN
	-- Chama a funcao totalPontosJogador e guarda o retorno numa variavel
    SELECT dbo.totalPontosJogador(1) INTO total_pontos_jogador;
	
    -- Verifica se o total de pontos é o esperado
    IF total_pontos_jogador = (SELECT total_pontos FROM dbo.Estatisticas_jogador WHERE id_jogador = 1) THEN
        RAISE NOTICE 'Teste (e): totalPontosJogador: Resultado OK';
    ELSE
        RAISE NOTICE 'Teste (e): totalPontosJogador: Resultado FAIL';
    END IF;
END;
$$;

CALL dbo.test_total_pontos_jogador();

------------------------------
-- Test totalJogosJogador (f) --
------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_total_jogos_jogador()
LANGUAGE plpgsql
AS $$
DECLARE
    total_jogos_jogador INTEGER;
BEGIN
	-- Chama a funcao totalPontosJogador e guarda o retorno numa variavel
    SELECT dbo.totalJogosJogador(1) INTO total_jogos_jogador;
	
    -- Verifica se o total de pontos é o esperado
    IF total_jogos_jogador = (SELECT num_jogos_dif FROM dbo.Estatisticas_jogador WHERE id_jogador = 1) THEN
        RAISE NOTICE 'Teste (f): totalJogosJogador: Resultado OK';
    ELSE
        RAISE NOTICE 'Teste (f): totalJogosJogador: Resultado FAIL';
    END IF;
END;
$$;

CALL dbo.test_total_jogos_jogador();

------------------------------
-- Test PontosJogoPorJogador (g) --
------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_pontos_jogo_jogador()
LANGUAGE plpgsql
AS $$
DECLARE
    id_jogador1_param INTEGER;
	id_jogador2_param INTEGER;
	id_partida1_param INTEGER;
BEGIN
	-- Insere um jogador teste
	INSERT INTO dbo.Jogador (email, username, estado, regiao)
	VALUES 
	('test1@example.com', 'test1', 'Ativo', 'Lisboa') RETURNING id INTO id_jogador1_param;
	
	INSERT INTO dbo.Jogador (email, username, estado, regiao)
	VALUES 
	('test2@example.com', 'test2', 'Ativo', 'Lisboa')RETURNING id INTO id_jogador2_param;
	
	INSERT INTO dbo.Jogo (id, nome, url) VALUES
    ('test1', 'JogoTeste', 'http://www.jogoTeste.com');
	
	INSERT INTO dbo.Partida (id_jogo, data_ini, data_fim, regiao) VALUES
    ('test1', '2022-01-01 13:00:00', '2022-01-01 14:00:00', 'Lisboa') RETURNING id INTO id_partida1_param;
	
	INSERT INTO dbo.Pontuacao (id_partida, id_jogador, pontuacao) VALUES
    (id_partida1_param, id_jogador1_param , 100),
    (id_partida1_param, id_jogador2_param, 150);
    
    -- Verifica se o total de pontos é o esperado
    IF (SELECT COUNT(*) FROM dbo.PontosJogoPorJogador('test1')) = 2 
	THEN
    	RAISE NOTICE 'Teste (g): PontosJogoPorJogador: Resultado OK';
	ELSE
    	RAISE NOTICE 'Teste (g): PontosJogoPorJogador: Resultado FAIL';
	END IF;
	
	DELETE FROM dbo.Pontuacao WHERE id_partida = id_partida1_param;
	DELETE FROM dbo.Partida p WHERE p.id = id_partida1_param;
	DELETE FROM dbo.Jogo j WHERE j.id = 'test1';
	DELETE FROM dbo.Jogador jg WHERE jg.id = id_jogador1_param;
	DELETE FROM dbo.Jogador jg2 WHERE jg2.id = id_jogador2_param;
END;
$$;

CALL dbo.test_pontos_jogo_jogador();
------------------------------
-- Test associar_cracha (h) --
------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_associar_cracha(
	id_jogador_param INTEGER,
	id_jogo_param VARCHAR(10),
	nome_cracha_param VARCHAR(255)
) 
LANGUAGE plpgsql
AS $$
DECLARE
    id_cracha_param INTEGER;
    pontuacao_jogador INTEGER;
    pontuacao_cracha INTEGER;
BEGIN
    -- Obtém o ID do crachá com base no nome e no jogo
    SELECT c.id INTO id_cracha_param
    FROM dbo.Cracha c
    WHERE c.nome = nome_cracha_param AND c.id_jogo = id_jogo_param;

    -- Obtém a pontuação necessária para obter o crachá
    SELECT c.pontuacao INTO pontuacao_cracha
    FROM dbo.Cracha c
    WHERE c.id = id_cracha_param;

    -- Obtém a pontuação do jogador no jogo em questão
    SELECT p.total_pontos INTO pontuacao_jogador
    FROM dbo.PontosJogoPorJogador(id_jogo_param) p
    WHERE p.id_jogador = id_jogador_param;

    -- Remove o registro na tabela de associação, caso exista
    DELETE FROM dbo.Crachas_jogador  
    WHERE id_jogador = id_jogador_param AND id_cracha = id_cracha_param;

    -- Chama a procedure associarCracha
    CALL dbo.associarCracha(id_jogador_param, id_jogo_param, nome_cracha_param);

    -- Verifica se o crachá foi associado corretamente
    IF NOT EXISTS (
        SELECT 1
        FROM dbo.Crachas_jogador cj
        WHERE cj.id_jogador = id_jogador_param AND cj.id_cracha = id_cracha_param
    ) THEN
        RAISE NOTICE 'Teste (h): associarCracha: Resultado FAIL';
    ELSE
        RAISE NOTICE 'Teste (h): associarCracha: Resultado OK';
    END IF;
END;
$$;

-- Chama o procedimento test_associar_cracha 
CALL dbo.test_associar_cracha(1, 'jg1', 'Fantasma Branco');

------------------------------
-- Test iniciarConversa (i) --
------------------------------

CREATE OR REPLACE PROCEDURE dbo.test_iniciar_conversa() 
LANGUAGE plpgsql
AS $$
DECLARE
    id_conversa_param INTEGER;
	id_jogador_param INTEGER;
BEGIN
	-- Insere um jogador teste
	INSERT INTO dbo.Jogador (email, username, estado, regiao)
	VALUES 
	('testfunction@example.com', 'testfunction', 'Ativo', 'Lisboa');
	
	-- Obtem o id do jogador criado para efeito de teste
	SELECT dbo.Jogador.id FROM dbo.Jogador INTO id_jogador_param
	WHERE email = 'testfunction@example.com'
	AND username = 'testfunction';

	-- Remove o registo na tabela mnsagem caso exista
    DELETE FROM dbo.Mensagem  
    WHERE id_jogador = id_jogador_param AND id_conversa = id_conversa_param 
	AND texto_mensagem = 'Conversa iniciada por testfunction';
	
	-- Remove o registo na tabela cnversa_jgador caso exista
	DELETE FROM dbo.Conversa_Jogador
	WHERE id_jogador = id_jogador_param
	AND id_conversa = id_conversa_param;
	
	-- reove o registo na tabela cnversa caso exista
	DELETE FROM dbo.Conversa
	WHERE dbo.Conversa.id = id_conversa_param;
	
    -- Chama o procedimento iniciado de forma a inciar uma conversa
    CALL dbo.iniciarConversa(id_jogador_param, 'Conversa iniciada', id_conversa_param);

    -- Verifica se a conversa foi criada
    IF NOT EXISTS (
        SELECT 1 FROM dbo.Conversa WHERE id = id_conversa_param
    ) THEN
        RAISE NOTICE 'Teste (i): iniciarConversa: Resultado FAIL';
    END IF;

    -- Verifica se o jogador foi associado á conversa
    IF NOT EXISTS (
        SELECT 1 FROM dbo.Conversa_jogador WHERE id_conversa = id_conversa_param AND id_jogador = id_jogador_param
    ) THEN
        RAISE NOTICE 'Teste (i): iniciarConversa: Resultado FAIL';
    END IF;

    -- Verifica se a mensagem foi criada
    IF NOT EXISTS (
        SELECT 1 FROM dbo.Mensagem WHERE id_conversa = id_conversa_param AND id_jogador = id_jogador_param
    ) THEN
        RAISE NOTICE 'Teste (i): iniciarConversa: Resultado FAIL';
    END IF;

    RAISE NOTICE 'Teste (i): iniciarConversa: Resultado OK';
	
	-- Remove o registo na tabela mnsagem caso exista
    DELETE FROM dbo.Mensagem  
    WHERE id_jogador = id_jogador_param AND id_conversa = id_conversa_param 
	AND texto_mensagem = 'Conversa iniciada por testfunction';
	
	-- Remove  registo na tabela conversa_jogador caso exista
	DELETE FROM dbo.Conversa_Jogador
	WHERE id_jogador = id_jogador_param
	AND id_conversa = id_conversa_param;
	
	-- Remove o registo na tabela conversa caso exista
	DELETE FROM dbo.Conversa
	WHERE dbo.Conversa.id = id_conversa_param;
	
	-- Remove o registo na tabela jogador caso exista
	DELETE FROM dbo.Jogador 
	WHERE email = 'testfunction@example.com' AND username = 'testfunction';
END;
$$;

CALL dbo.test_iniciar_conversa();


------------------------------
-- Test juntarConversa (j) --
------------------------------
CREATE OR REPLACE PROCEDURE dbo.test_juntar_conversa(
	id_jogador_param INTEGER,
	id_conversa_param INTEGER
) 
LANGUAGE plpgsql
AS $$
DECLARE
	invalid_parameters BOOLEAN = FALSE;
	unique_violation BOOLEAN = FALSE;
	successful_test BOOLEAN;
BEGIN
	
	-- Chama o procedimento armazenado	
	BEGIN
		CALL dbo.juntarConversa(id_jogador_param, id_conversa_param);
	EXCEPTION
		-- Verifica se houve algum problema com os parametros passados
		WHEN invalid_parameter_value THEN
			invalid_parameters := TRUE;
		WHEN unique_violation THEN
			unique_violation:= TRUE;		
	END;
	
	-- Verifica se a conversa foi corretamente associada com o jogador
	IF EXISTS (SELECT 1 FROM dbo.conversa_jogador WHERE id_jogador = id_jogador_param AND id_conversa = id_conversa_param)
		AND NOT invalid_parameters AND NOT unique_violation THEN
		successful_test:= TRUE;
		RAISE NOTICE 'Teste (j): JuntarConversa: Resultado OK';
	ELSE
		IF invalid_parameters THEN
			RAISE NOTICE 'Teste (j): id_conversa ou id_jogador passados como parametro invalidos: Resultado FAIL';
		ELSEIF unique_violation THEN
			RAISE NOTICE 'Teste (j): conversa ja previamente associada a jogador: Resultado FAIL';
		ELSE
			RAISE NOTICE 'Teste (j): juntarConversa: Resultado FAIL';
		END IF;
	END IF;
	
	-- Remove o registo na tabela conversa_jogador caso exista
	IF successful_test THEN
	 DELETE FROM dbo.Conversa_Jogador
	 WHERE id_jogador = id_jogador_param
	 AND id_conversa = id_conversa_param;
	END IF; 
	
END;
$$;

CALL dbo.test_juntar_conversa(2, 2);

------------------------------
-- Test enviarMensagem (k) --
------------------------------

CREATE OR REPLACE PROCEDURE dbo.test_enviar_mensagem()
LANGUAGE plpgsql
AS $$
DECLARE
    id_jogador_param INTEGER;
	id_conversa_param INTEGER;
BEGIN
	-- Insere um jogador teste
	INSERT INTO dbo.Jogador (email, username, estado, regiao)
	VALUES 
	('test1@example.com', 'test1', 'Ativo', 'Lisboa') RETURNING id INTO id_jogador_param;
	
	INSERT INTO dbo.Conversa (nome) VALUES
  	('ConversaTeste') RETURNING id INTO id_conversa_param;
	
	INSERT INTO dbo.Conversa_jogador (id_conversa, id_jogador) VALUES
  	(id_conversa_param, id_jogador_param);
	
	CALL dbo.enviarMensagem(id_conversa_param, id_jogador_param, 'TESTE MESSAGE');

	-- Verifica se a mensagem foi criada
    IF NOT EXISTS (
        SELECT 1 FROM dbo.Mensagem m WHERE m.texto_mensagem = 'TESTE MESSAGE' AND m.id_jogador = id_jogador_param AND m.id_conversa = id_conversa_param
    ) THEN
        RAISE NOTICE 'Teste (k): enviarMensagem: Resultado FAIL';
	ELSE 	
		RAISE NOTICE 'Teste (k): enviarMensagem: Resultado OK';
    END IF;

   
	
	DELETE FROM dbo.Conversa_jogador cj WHERE cj.id_conversa = id_conversa_param AND cj.id_jogador = id_jogador_param;
	DELETE FROM dbo.Conversa c WHERE c.id = id_conversa_param;
	DELETE FROM dbo.Jogador jg WHERE jg.id = id_jogador_param;
END;
$$;

CALL dbo.test_enviar_mensagem();


------------------------------
-- Test jogadorTotalInfo (l) --
------------------------------

CREATE OR REPLACE PROCEDURE dbo.test_jogador_total_info()
AS $$
DECLARE
    id_jogador_var INTEGER;
	teste_jogador_id INTEGER;
	teste_num_partidas INTEGER;
	teste_num_jogos INTEGER;
	teste_pontuacao INTEGER;
	teste_estado VARCHAR(10);
	teste_email VARCHAR(255);
	teste_username VARCHAR(255);
	
BEGIN
    -- Insere um jogador teste e retorna o seu id
	INSERT INTO dbo.Jogador (email, username, estado, regiao)
    VALUES ('test@gmail.com', 'testPlayer', 'Ativo', 'Lisboa')
    RETURNING id INTO id_jogador_var;
	
	-- Insere os restantes dados a ser usados no teste conforme o id_jogador recebido
	CALL dbo.insert_test_values(id_jogador_var);

    -- Executa a vista e guarda as variaveis necessarias
    SELECT id, num_partidas, num_jogos, pontuacao, estado, email, username INTO teste_jogador_id, teste_num_partidas, teste_num_jogos, teste_pontuacao, teste_estado, teste_email, teste_username 
	FROM dbo.jogadorTotalInfo WHERE id = id_jogador_var;

    -- Verifica os resultados
    IF  id_jogador_var = teste_jogador_id AND teste_estado = 'Ativo' AND teste_email = 'test@gmail.com' AND teste_username = 'testPlayer' AND  teste_num_partidas = 3 AND teste_num_jogos = 4 AND teste_pontuacao = 500 THEN
        RAISE NOTICE 'Teste (l): jogadorTotalInfo: Resultado OK';
    ELSE
        RAISE NOTICE 'Teste (l): jogadorTotalInfo: Resultado FAIL';
    END IF;
	
	-- Apaga os dados inseridos no teste
	CALL dbo.delete_test_values(id_jogador_var);
END;
$$ LANGUAGE plpgsql;

CALL dbo.test_jogador_total_info();


--------------------------------------
-- Test trigger_associar_cracha (m) --
--------------------------------------

CREATE OR REPLACE PROCEDURE dbo.test_atribuir_crachas_trigger() 
LANGUAGE plpgsql
AS $$
DECLARE
    id_partida_param INTEGER;
	id_partidaMJ_param INTEGER;
    id_jogador_param INTEGER;
    id_cracha_param INTEGER;
BEGIN
	-- Insere um jogador teste
	INSERT INTO dbo.Jogador (email, username, estado, regiao) VALUES
    ('test@gmail.com', 'test', 'Ativo', 'Lisboa') RETURNING id INTO id_jogador_param;

	-- Insere uma partida teste
	INSERT INTO dbo.Partida (id_jogo, data_ini, data_fim, regiao) VALUES
    ('jg1', '2022-01-01 13:00:00', NULL, 'Lisboa') RETURNING id INTO id_partida_param;

    -- Insere uma pontuacao teste
    INSERT INTO dbo.Pontuacao (id_jogador, id_partida, pontuacao) 
    VALUES (id_jogador_param, id_partida_param, 500);
	
	-- Insere uma partida_multijogador teste
	INSERT INTO dbo.Partida_multijogador (id_partida, estado) VALUES
    (id_partida_param, 'Em curso') RETURNING id_partida INTO id_partidaMJ_param;
	
    -- Realiza um update na tabela partida_multijogador 
	UPDATE dbo.Partida_multijogador
	SET estado = 'Terminada'
	WHERE id_partida = id_partidaMJ_param AND estado = 'Em curso';

    -- Verifica se o cracha foi associado ao jogador
    IF NOT EXISTS (
        SELECT 1 FROM dbo.Crachas_Jogador WHERE id_jogador = id_jogador_param
    ) THEN
        RAISE NOTICE 'Teste (m): atribuir_crachas_trigger: Resultado FAIL';
		ELSE RAISE NOTICE 'Teste (m): atribuir_crachas_trigger: Resultado OK';
    END IF;

    -- Apaga as inserções realizadas para efeitos de teste
    DELETE FROM dbo.Crachas_Jogador WHERE id_jogador = id_jogador_param;
	DELETE FROM dbo.Partida_multijogador mj WHERE mj.id_partida = id_partidaMJ_param;
    DELETE FROM dbo.Partida p WHERE p.id = id_partida_param;
    DELETE FROM dbo.Pontuacao WHERE id_jogador = id_jogador_param AND id_partida = id_partida_param;
	DELETE FROM dbo.Jogador j WHERE j.id = id_jogador_param;
END;
$$;

CALL dbo.test_atribuir_crachas_trigger();

--------------------------------------
-- Test update_estado_trigger (n) --
--------------------------------------

CREATE OR REPLACE PROCEDURE dbo.test_update_estado_trigger() 
LANGUAGE plpgsql
AS $$
DECLARE
    id_jogador_var INTEGER;
	id_partida1_var INTEGER;
	id_partida2_var INTEGER;
	id_partida3_var INTEGER;
BEGIN
	 -- Insere um jogador teste e retorna o seu id
    INSERT INTO dbo.Jogador (email, username, estado, regiao)
	VALUES ('test@gmail.com', 'testPlayer', 'Ativo', 'Lisboa')
	RETURNING id INTO id_jogador_var;

	-- Insere os restantes dados a ser usados no teste conforme o id_jogador recebido
    CALL dbo.insert_test_values(id_jogador_var);

    -- Executa a vista e guarda as variaveis necessarias
    DELETE FROM dbo.jogadortotalinfo where id = id_jogador_var ;
	IF 'Banido' = (SELECT estado FROM dbo.Jogador WHERE id = id_jogador_var) THEN
      RAISE NOTICE 'Teste (n): update_estado_trigger: Resultado OK';
    ELSE
      RAISE NOTICE 'Teste (n): update_estado_trigger: Resultado FAIL';
    END IF;
	
	-- Apaga os dados inseridos no teste
	CALL dbo.delete_test_values(id_jogador_var);

END;
$$;

CALL dbo.test_update_estado_trigger();
