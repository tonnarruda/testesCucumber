CREATE OR REPLACE FUNCTION removeFormacaoDuplicada() RETURNS integer AS $$
DECLARE 
    mviews RECORD; 
BEGIN 
    FOR mviews IN 
	select  id,curso,situacao,local,conclusao,areaformacao_id 
	from formacao  
	where id in (select min(id) from formacao group by curso,situacao,local,conclusao,areaformacao_id having count(curso) >1)
	order by curso,local
	LOOP 
		delete from formacao where id in 
		(select id from formacao 
		where curso = mviews.curso 
		and situacao=mviews.situacao
		and local=mviews.local 
		and conclusao=mviews.conclusao
		and areaformacao_id=mviews.areaformacao_id  
		and id <> mviews.id);
	END LOOP; 
    RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select removeFormacaoDuplicada();--.go
drop function removeFormacaoDuplicada();--.go