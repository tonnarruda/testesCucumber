CREATE OR REPLACE FUNCTION removeAreaFormacaoDuplicada() RETURNS integer AS $$
DECLARE 
    mviews RECORD; 
BEGIN 
    FOR mviews IN 
		select id,nome from areaformacao  where  id in ( select min(id) from areaformacao  group by nome having count(nome) > 1) order by nome, id
		LOOP 
			update formacao set areaformacao_id = mviews.id where areaformacao_id in (select id from areaformacao where nome = mviews.nome);
			update cargo_areaformacao set areaformacaos_id = mviews.id where areaformacaos_id in (select id from areaformacao where nome = mviews.nome);
			delete from areaformacao where id in (select id from areaformacao where nome = mviews.nome and id <> mviews.id);
		END LOOP; 
		
    RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select removeAreaFormacaoDuplicada();--.go
drop function removeAreaFormacaoDuplicada();--.go