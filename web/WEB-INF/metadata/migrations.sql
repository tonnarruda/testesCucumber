
delete FROM  aproveitamentoavaliacaocurso where id not in(
select distinct on(colaboradorturma_id, avaliacaocurso_id)id from aproveitamentoavaliacaocurso where colaboradorturma_id in(
select colaboradorturma_id from aproveitamentoavaliacaocurso group by colaboradorturma_id, avaliacaocurso_id having count(*) >= 1)
and avaliacaocurso_id in(select avaliacaocurso_id from aproveitamentoavaliacaocurso group by colaboradorturma_id, avaliacaocurso_id having count(*) >= 1));--.go

ALTER TABLE colaboradorturma ADD COLUMN motivoReprovacao CHARACTER VARYING(20);--.go

CREATE OR REPLACE FUNCTION insertMotivoReprovacao() RETURNS integer AS $$   
DECLARE  
reprovadoPorFalta BOOLEAN;
reprovadoPorNota BOOLEAN; 
mv RECORD;  
BEGIN  
	FOR mv IN  
		select ct.colaborador_id as colabId, ct.turma_id as turmaId, ct.curso_id as cursoId, ct.id as colabTurmaId, * from colaboradorturma ct where ct.aprovado = false
	LOOP 	 
		
		select (coalesce(cast( (select count(avaliacaocursos_id) from curso_avaliacaocurso where cursos_id = mv.cursoId group by cursos_id) as Integer ), 0) = 0      
		 or coalesce(( select count(avaliacaocursos_id) from curso_avaliacaocurso where cursos_id = mv.cursoId group by cursos_id), 0)  =    
		 coalesce((select rct.qtdavaliacoesaprovadaspornota from View_CursoNota as rct where colaboradorturma_id = mv.colabTurmaId), 0)) = false into reprovadoPorNota;

		select  case when (coalesce((select count(dia) from diaturma where turma_id = mv.turmaId group by turma_id), 0)) > 0 
			THEN    (     (       cast(coalesce((select count(id) from colaboradorpresenca 
			where presenca=true and colaboradorturma_id = mv.colabTurmaId group by colaboradorturma_id), 0) as DOUBLE PRECISION) 
			/        cast(coalesce((select count(dia) from diaturma where turma_id = mv.turmaId group by turma_id), 0) as DOUBLE PRECISION)      ) * 100     ) >= 
			coalesce((select percentualMinimoFrequencia from curso where id = mv.cursoId), 0)    else      true    end  = false into reprovadoPorFalta;

		if reprovadoPorNota and reprovadoPorFalta then
			update colaboradorturma set motivoReprovacao = 'NOTA_FREQUENCIA' where id = mv.colabTurmaId;
		elseif reprovadoPorNota then
			update colaboradorturma set motivoReprovacao = 'NOTA' where id = mv.colabTurmaId;
		elseif reprovadoPorFalta then
			update colaboradorturma set motivoReprovacao = 'FREQUENCIA' where id = mv.colabTurmaId;
		else 
			update colaboradorturma set motivoReprovacao = 'REPROVADO' where id = mv.colabTurmaId;
		end if;
		
	END LOOP;  
	RETURN 1;  
END;  
$$ LANGUAGE plpgsql;--.go
select insertMotivoReprovacao();--.go
drop function insertMotivoReprovacao();--.go

drop function verifica_aprovacao(bigint, bigint, bigint);--.go
CREATE OR REPLACE FUNCTION verifica_aprovacao(id_curso bigint, id_turma bigint, id_colaboradorturma bigint)
  RETURNS table(turmaRealizada Boolean, aprovadoPorNota Boolean, aprovadoPorFalta Boolean) AS $$
BEGIN
  RETURN Query select (SELECT cast(t.realizada as Boolean) from turma t where t.id = id_turma) as turmaRealizada, (select (coalesce(cast( (select count(avaliacaocursos_id) 
	from curso_avaliacaocurso where cursos_id = id_curso group by cursos_id) as Integer ), 0) = 0      
  		 or coalesce(( select count(avaliacaocursos_id) from curso_avaliacaocurso where cursos_id = id_curso group by cursos_id), 0)  =    
  		 coalesce((select rct.qtdavaliacoesaprovadaspornota from View_CursoNota as rct where colaboradorturma_id = id_colaboradorturma), 0))) as aprovadoPorNota,
  		 ( 
  select  case when (coalesce((select count(dia) from diaturma where turma_id = id_turma group by turma_id), 0)) > 0 
			THEN    (     (       cast(coalesce((select count(id) from colaboradorpresenca 
			where presenca=true and colaboradorturma_id = id_colaboradorturma group by colaboradorturma_id), 0) as DOUBLE PRECISION) 
			/        cast(coalesce((select count(dia) from diaturma where turma_id = id_turma group by turma_id), 0) as DOUBLE PRECISION)      ) * 100  ) >= 
			coalesce((select percentualMinimoFrequencia from curso where id = id_curso), 0)    else      true    end) as aprovadoPorFalta;
END;
$$ LANGUAGE plpgsql;--.go
