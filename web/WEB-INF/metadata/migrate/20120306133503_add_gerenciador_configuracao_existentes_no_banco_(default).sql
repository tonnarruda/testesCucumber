delete from gerenciadorcomunicacao;--.go
CREATE FUNCTION insert_gerenciador_comunicao() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select e.id as empresaId from empresa e
		LOOP
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 4, 2, 3)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 5, 2, 4)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 6, 2, 5)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 7, 2, 6)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 8, 2, 7)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 1, 2, 7)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 2, 2, 7)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 9, 2, 7)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 10, 2, 9)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 9, 1, 10)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 9, 1, 11)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 12, 2, 8)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 13, 1, 13)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 14, 1, 13)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 16, 2, 12)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 15, 2, 15)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 11, 2, 6)'';
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 3, 1, 14)'';   
		END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql;--.go
select insert_gerenciador_comunicao();--.go
drop function insert_gerenciador_comunicao();--.go 