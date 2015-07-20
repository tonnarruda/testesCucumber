CREATE FUNCTION remove_respostas_duplicadas() RETURNS integer AS $$
DECLARE
	mviews RECORD;
	BEGIN
	FOR mviews IN 	
		WITH questionario_duplicado AS (
			SELECT colaborador_id, avaliacaodesempenho_id, avaliador_id FROM colaboradorquestionario cq1 WHERE avaliacaodesempenho_id IS NOT NULL 
			GROUP BY cq1.colaborador_id, cq1.avaliacaodesempenho_id, cq1.avaliador_id HAVING COUNT(cq1.colaborador_id)>1
		)
		SELECT  DISTINCT ON(colaborador_id, avaliacaodesempenho_id, avaliador_id) cq.id, colaborador_id, avaliacaodesempenho_id, avaliador_id FROM colaboradorquestionario cq
			WHERE cq.colaborador_id IN(SELECT colaborador_id FROM questionario_duplicado) AND cq.avaliacaodesempenho_id IN (SELECT avaliacaodesempenho_id FROM questionario_duplicado)
			AND cq.avaliador_id IN (SELECT avaliador_id FROM questionario_duplicado) 
		LOOP
			IF (SELECT NOT EXISTS(
				SELECT COUNT(cr.pergunta_id) FROM colaboradorresposta cr 
					INNER JOIN colaboradorquestionario cq ON cq.id = cr.colaboradorquestionario_id
					WHERE cq.colaborador_id = mviews.colaborador_id AND avaliacaodesempenho_id = mviews.avaliacaodesempenho_id AND avaliador_id = mviews.avaliador_id
					GROUP BY cq.colaborador_id, cq.avaliacaodesempenho_id, cq.avaliador_id, cr.pergunta_id, cr.resposta_id
					HAVING COUNT(cr.pergunta_id) = 1)
				) = true 
			THEN
				DELETE FROM colaboradorresposta WHERE colaboradorquestionario_id = mviews.id;
				UPDATE colaboradorquestionario SET respondida = false WHERE id = mviews.id;   			
			END IF;				
		END LOOP;
	RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
SELECT remove_respostas_duplicadas();--.go
DROP FUNCTION remove_respostas_duplicadas();--.go

DELETE FROM colaboradorquestionario WHERE id IN ( 
WITH questionario_duplicado AS ( 
	SELECT colaborador_id, avaliacaodesempenho_id, avaliador_id FROM colaboradorquestionario cq1 WHERE avaliacaodesempenho_id IS NOT NULL 
	GROUP BY cq1.colaborador_id, cq1.avaliacaodesempenho_id, cq1.avaliador_id HAVING COUNT(cq1.colaborador_id)>1 
     ) 
SELECT  DISTINCT ON(colaborador_id, avaliacaodesempenho_id, avaliador_id) cq.id 
FROM colaboradorquestionario cq 
WHERE cq.colaborador_id IN(SELECT colaborador_id FROM questionario_duplicado) AND cq.avaliacaodesempenho_id IN (SELECT avaliacaodesempenho_id FROM questionario_duplicado) 
AND cq.avaliador_id IN (SELECT avaliador_id FROM questionario_duplicado) AND cq.respondida= false   
);--.go