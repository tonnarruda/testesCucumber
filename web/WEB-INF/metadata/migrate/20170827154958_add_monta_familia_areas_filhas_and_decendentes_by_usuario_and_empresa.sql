CREATE OR REPLACE FUNCTION monta_familia_areas_filhas_and_decendentes_by_usuario_and_empresa(usuarioId BIGINT, empresaId BIGINT) RETURNS  
TABLE(areaId BIGINT, areaNome TEXT, areaAtivo BOOLEAN, empresa_Nome TEXT, areaMaeId BIGINT) AS $$     
DECLARE   
mviews RECORD;  
BEGIN   
     FOR mviews IN    
		select area.id as area_id, e.nome as empresaNome from areaorganizacional as area     
		left join colaborador c on c.id = area.responsavel_id   
		left join colaborador co on co.id = area.coresponsavel_id   
		left join usuario u on u.id = c.usuario_id or u.id = co.usuario_id   
		left join empresa e on e.id = area.empresa_id  
		where u.id = usuarioId and area.empresa_id = empresaId   
		LOOP   
		RETURN QUERY   
		    (
			select ao.id,  cast(monta_familia_area(ao.id) as text) as nome, ao.ativo, CAST(mviews.empresaNome as text) as empNome, ao.areamae_id 
			from areaorganizacional ao inner join descendentes_areas_ids(mviews.area_id) on descendentes_areas_ids = ao.id
		    );  
      END LOOP; 
    RETURN;  
END;  
$$ LANGUAGE plpgsql;--.go 