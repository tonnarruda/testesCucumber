CREATE OR REPLACE FUNCTION criar_historico_cncCandidatoPorSolicitacao() RETURNS integer AS $$   
	DECLARE 
	    mv RECORD; 
	BEGIN 
	    FOR mv IN 
			SELECT * FROM configuracaonivelcompetencia cnc WHERE cnc.candidato_id IS NOT NULL
			LOOP  
				INSERT INTO configuracaonivelcompetencia 
					SELECT nextval('configuracaonivelcompetencia_sequence'), mv.faixasalarial_id, mv.nivelcompetencia_id, mv.competencia_id, mv.candidato_id, mv.tipocompetencia, null, null, s.id
					FROM candidatosolicitacao cs
					INNER JOIN solicitacao s on s.id = cs.solicitacao_id
					WHERE cs.candidato_id = mv.candidato_id AND s.faixasalarial_id = mv.faixasalarial_id AND cs.triagem = false;
			END LOOP;  
	    RETURN 1;  
	END;  
$$ LANGUAGE plpgsql;--.go 

SELECT criar_historico_cncCandidatoPorSolicitacao();--.go
DROP FUNCTION criar_historico_cncCandidatoPorSolicitacao();--.go
DELETE FROM configuracaonivelcompetencia WHERE candidato_id IS NOT NULL AND solicitacao_id IS NULL;--.go
