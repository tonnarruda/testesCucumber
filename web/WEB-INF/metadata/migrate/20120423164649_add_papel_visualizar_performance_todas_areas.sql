INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (541, 'ROLE_PERFORMANCE_TODAS_AREAS', 'Visualizar Performance Funcional de todas as áreas', '#', 1, false, 8);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=541 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 541); --.go
alter sequence papel_sequence restart with 542;--.go

CREATE FUNCTION insert_perfil_padrao_visualizar_performance() RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select perfil_id as perfilId from perfil_papel where papeis_id=8
		LOOP
			INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (mviews.perfilId, 541);
		END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insert_perfil_padrao_visualizar_performance();--.go
drop function insert_perfil_padrao_visualizar_performance();--.go 