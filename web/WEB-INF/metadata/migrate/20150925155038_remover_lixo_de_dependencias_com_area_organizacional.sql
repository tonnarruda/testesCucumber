CREATE OR REPLACE FUNCTION deleta_lixo_dependencias_com_area_organizacional() RETURNS integer AS $$ 
DECLARE 
	id_empresa RECORD; 
	BEGIN  
	FOR id_empresa IN SELECT id FROM empresa
		LOOP 
			delete from areainteresse_areaorganizacional where areasorganizacionais_id in(select id from areaorganizacional where empresa_id != id_empresa.id)
															   and areasinteresse_id in (select id from  areainteresse where empresa_id = id_empresa.id);

			delete from conhecimento_areaorganizacional where areaorganizacionals_id in(select id from areaorganizacional where empresa_id != id_empresa.id)
															  and conhecimentos_id in (select id from  conhecimento where empresa_id = id_empresa.id);


			delete from habilidade_areaorganizacional where areaorganizacionals_id in(select id from areaorganizacional where empresa_id != id_empresa.id)
															and habilidades_id in (select id from habilidade where empresa_id = id_empresa.id);


			delete from atitude_areaorganizacional where areaorganizacionals_id in(select id from areaorganizacional where empresa_id != id_empresa.id)
														 and atitudes_id in (select id from  atitude where empresa_id = id_empresa.id);
		END LOOP;   
	RETURN 1;
END;  
$$ LANGUAGE plpgsql;--.go
SELECT deleta_lixo_dependencias_com_area_organizacional();--.go
DROP FUNCTION deleta_lixo_dependencias_com_area_organizacional();--.go