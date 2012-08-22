CREATE FUNCTION insert_gerenciador_comunicao() RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select e.id as empresaId from empresa e
		LOOP
			INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara, qtddiaslembrete) VALUES (nextval('gerenciadorComunicacao_sequence'), mviews.empresaId, 25, 2, 7, '7');
		END LOOP;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insert_gerenciador_comunicao();--.go
drop function insert_gerenciador_comunicao();--.go 