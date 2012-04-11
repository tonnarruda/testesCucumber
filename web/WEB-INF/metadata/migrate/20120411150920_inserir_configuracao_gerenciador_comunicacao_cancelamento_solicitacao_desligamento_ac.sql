CREATE FUNCTION insert_gerenciador_comunicao_cancel_solicitacao_desligamento_ac() RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select e.id as empresaId from empresa e
		LOOP
			INSERT INTO gerenciadorcomunicacao (id, empresa_id, operacao, meiocomunicacao, enviarpara) VALUES (nextval('gerenciadorComunicacao_sequence'), mviews.empresaId, 21, 1, 13);
		END LOOP;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insert_gerenciador_comunicao_cancel_solicitacao_desligamento_ac();--.go
drop function insert_gerenciador_comunicao_cancel_solicitacao_desligamento_ac();--.go 