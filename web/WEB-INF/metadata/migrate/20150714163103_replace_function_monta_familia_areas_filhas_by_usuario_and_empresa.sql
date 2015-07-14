CREATE OR REPLACE FUNCTION monta_familia_areas_filhas_by_usuario_and_empresa(usuarioId BIGINT, empresaId BIGINT) RETURNS TABLE(areaId BIGINT, areaNome TEXT, areaAtivo BOOLEAN) AS $$  
DECLARE 
mviews RECORD;
BEGIN 
     FOR mviews IN 
		select area.id as area_id from areaorganizacional as area  
		left join colaborador c on c.id = area.responsavel_id
		left join colaborador co on co.id = area.coresponsavel_id 
		left join usuario u on u.id = c.usuario_id or u.id = co.usuario_id 
		where u.id = usuarioId and area.empresa_id = empresaId 
		LOOP 
		RETURN QUERY 
		    (select id, cast(monta_familia_area(id) as text) as nome, ativo from areaorganizacional where id = mviews.area_id) union 
		    ((WITH RECURSIVE areaorganizacional_recursiva AS ( 
		        SELECT id, nome, areamae_id, CAST(nome AS TEXT) AS nomeHierarquico, ativo 
		        FROM areaorganizacional WHERE areamae_id = mviews.area_id 
		        UNION ALL  
		        SELECT ao.id, ao.nome, ao.areamae_id, CAST((ao_r.nomeHierarquico || ' > ' || ao.nome) AS TEXT) AS nomeHierarquico, ao.ativo  
		        FROM areaorganizacional ao  
		        INNER JOIN areaorganizacional_recursiva ao_r ON ao.areamae_id = ao_r.id  
		    ) SELECT id, cast(((select monta_familia_area(id) as nome from areaorganizacional where id = mviews.area_id ) || ' > ' || nomeHierarquico) as text) as nome, ativo FROM areaorganizacional_recursiva) ORDER BY nome); 
      END LOOP; 
    RETURN;  
END;  
$$ LANGUAGE plpgsql; --.go 