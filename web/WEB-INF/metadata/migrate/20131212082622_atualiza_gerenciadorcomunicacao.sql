delete from gerenciadorcomunicacao where operacao = 14 and gerenciadorcomunicacao.meiocomunicacao = 1 and gerenciadorcomunicacao.enviarpara = 13; --.go 
delete from gerenciadorcomunicacao where operacao = 14 and gerenciadorcomunicacao.meiocomunicacao = 1 and gerenciadorcomunicacao.enviarpara = 14; --.go

CREATE FUNCTION atualiza_gerenciador_comunicao() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select e.id as empresaId from empresa e
		LOOP
			EXECUTE	''INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('' || quote_literal(''gerenciadorComunicacao_sequence'') || ''), ''|| quote_literal(mviews.empresaId) ||'', 14, 1, 13)'';
		END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql;--.go
select atualiza_gerenciador_comunicao();--.go
drop function atualiza_gerenciador_comunicao();--.go
