CREATE FUNCTION insert_gerenciador_comunicao() RETURNS integer AS $$
DECLARE
    mviews RECORD;
    gerenciadorComunicacaoId INTEGER;
BEGIN
 
    FOR mviews IN
	  select e.id as empresaId from empresa e
	LOOP
		gerenciadorComunicacaoId := nextval('gerenciadorComunicacao_sequence');
		INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara, qtddiaslembrete, permitirresponderavaliacao) VALUES (gerenciadorComunicacaoId, mviews.empresaId, 30, 2, 7, null, false);
	END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insert_gerenciador_comunicao();--.go
drop function insert_gerenciador_comunicao();--.go