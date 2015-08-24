CREATE OR REPLACE FUNCTION atualiza_matricula_to_codigofortespessoal(id_empresas BIGINT[], percentualCompatibilidadeMatriculaCodigoFP DOUBLE PRECISION) RETURNS void AS $$     
	DECLARE 
		id_empresa BIGINT; 
	BEGIN  
		FOR id_empresa IN select unnest(id_empresas) LOOP 
			IF (cast ((select count(id) from colaborador where codigoac is not null and empresa_id = id_empresa) as double precision)) <> 0 
				and (select 
				(cast ((select count(id) from colaborador where matricula = codigoac and codigoac is not null and empresa_id = id_empresa) as double precision) 
				/ cast ((select count(id) from colaborador where codigoac is not null and empresa_id = id_empresa) as double precision))   
				>= cast(percentualCompatibilidadeMatriculaCodigoFP as double precision)/100)  
			THEN 
				update colaborador set matricula = codigoac where empresa_id = id_empresa;  

				IF (select acIntegra from empresa where id = id_empresa) THEN 
					update empresa set vincularMatriculaCodigoFortesPessoal = true where id = id_empresa; 
				END IF;  
			END IF;  
		END LOOP; 	
	END;   
$$ LANGUAGE plpgsql;--.go

select atualiza_matricula_to_codigofortespessoal(array[(select array_agg(id::BIGINT) from empresa)], 70);--.go