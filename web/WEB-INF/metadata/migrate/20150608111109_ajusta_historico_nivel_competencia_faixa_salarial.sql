
delete from configuracaoNivelCompetencia where faixaSalarial_id not in (select id from faixaSalarial);--.go 
CREATE FUNCTION criar_historico_cncFaixa() RETURNS integer AS $$   
	DECLARE 
	    mv RECORD; 
	    v_id BIGINT; 
	BEGIN 
	    FOR mv IN 
			SELECT distinct(faixasalarial_id) FROM configuracaonivelcompetencia   
			WHERE faixasalarial_id IS NOT NULL AND configuracaonivelcompetenciacolaborador_id IS NULL AND candidato_id IS NULL ORDER BY 1  
			LOOP  
				v_id := nextval('configuracaoNivelCompetenciaFaixaSalarial_sequence');  
				INSERT INTO configuracaoNivelCompetenciaFaixaSalarial values (v_id, mv.faixaSalarial_id, coalesce((SELECT min(data) FROM faixasalarialhistorico fsh WHERE mv.faixasalarial_id = fsh.faixasalarial_id), '2005-01-01'::date));  
				UPDATE configuracaoNivelCompetencia set configuracaoNivelCompetenciaFaixaSalarial_id = v_id WHERE faixasalarial_id = mv.faixasalarial_id AND configuracaonivelcompetenciacolaborador_id IS NULL AND candidato_id IS NULL;  
			END LOOP;  
	    RETURN 1;  
	END;  
$$ LANGUAGE plpgsql;--.go 
SELECT criar_historico_cncFaixa();--.go
DROP FUNCTION criar_historico_cncFaixa();--.go