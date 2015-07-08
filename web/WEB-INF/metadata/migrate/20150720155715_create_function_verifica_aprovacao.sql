CREATE OR REPLACE FUNCTION verifica_aprovacao(id_curso BIGINT, id_turma BIGINT, id_colaboradorturma BIGINT, percentualMinimoFrequencia DOUBLE PRECISION) RETURNS BOOLEAN AS $$  
DECLARE aprovado BOOLEAN; 
BEGIN 

	select (
			(
				coalesce(cast( (select count(avaliacaocursos_id) from curso_avaliacaocurso where cursos_id = id_curso group by cursos_id) as Integer ), 0) = 0 
			 	or coalesce(( select count(avaliacaocursos_id) from curso_avaliacaocurso where cursos_id = id_curso group by cursos_id), 0)  =
				coalesce((select rct.qtdavaliacoesaprovadaspornota from View_CursoNota as rct where colaboradorturma_id = id_colaboradorturma), 0) 
			 ) = true
			 and 
				case when (coalesce((select count(dia) from diaturma where turma_id = id_turma group by turma_id), 0)) > 0 THEN
				(
					(
						 cast(coalesce((select count(id) from colaboradorpresenca where presenca=true and colaboradorturma_id = id_colaboradorturma group by colaboradorturma_id), 0) as DOUBLE PRECISION) / 
						 cast(coalesce((select count(dia) from diaturma where turma_id = id_turma group by turma_id), 0) as DOUBLE PRECISION)
					 ) * 100 
			    ) >= percentualMinimoFrequencia
				else 
					true
				end 
			) as situacao INTO aprovado;
	
	RETURN aprovado;
	
END; 
 
$$ LANGUAGE plpgsql; --.go  