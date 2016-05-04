
drop index if exists index_curso_avaliacaocurso_curso;--.go
create index index_curso_avaliacaocurso_curso on curso_avaliacaocurso (cursos_id);--.go

drop index if exists index_diaturma_turma;--.go
create index index_diaturma_turma on diaturma (turma_id);--.go

drop index if exists index_colaboradorpresenca;--.go
create index index_colaboradorpresenca on colaboradorpresenca (presenca, colaboradorturma_id);--.go

drop index if exists index_colaboradorturma;--.go
CREATE INDEX index_colaboradorturma ON colaboradorturma (curso_id, colaborador_id);--.go

drop index if exists index_colaborador_id;--.go
create index index_colaborador_id on colaborador (id);--.go

drop index if exists index_turma_id;--.go
create index index_turma_id on turma (id);--.go

drop index if exists index_turma_id_realizada;--.go
create index index_turma_id_realizada on turma (id, realizada);--.go

drop index if exists index_curso_id;--.go
create index index_curso_id on curso (id);--.go

drop index if exists index_hc_colaboradorId;--.go
create index index_hc_colaboradorId on historicoColaborador (colaborador_id);--.go

drop index if exists index_hc_colaboradorId_status;--.go
create index index_hc_colaboradorId_status on historicoColaborador (colaborador_id, status);--.go

drop index if exists index_area_organizacional_id;--.go
create index index_area_organizacional_id on areaOrganizacional (id);--.go

drop index if exists index_colaboradorturma_colaborador_id;--.go
CREATE INDEX index_colaboradorturma_colaborador_id ON colaboradorturma (colaborador_id);--.go

drop index if exists index_colaboradorturma_turma_id;--.go
CREATE INDEX index_colaboradorturma_turma_id ON colaboradorturma (turma_id);--.go

drop index if exists index_colaboradorturma_curso_id;--.go
CREATE INDEX index_colaboradorturma_curso_id ON colaboradorturma (curso_id);--.go


drop function verifica_aprovacao(id_curso BIGINT, id_turma BIGINT, id_colaboradorturma BIGINT, percentualMinimoFrequencia DOUBLE PRECISION);

CREATE OR REPLACE FUNCTION verifica_aprovacao(id_curso BIGINT, id_turma BIGINT, id_colaboradorturma BIGINT) RETURNS BOOLEAN AS $$  
	DECLARE aprovado BOOLEAN; 
	BEGIN 
	select (
			(select realizada from turma where id = id_turma)
			and
			(
				coalesce(cast( (select count(avaliacaocursos_id) from curso_avaliacaocurso where cursos_id = id_curso group by cursos_id) as Integer ), 0) = 0 
			 	or coalesce(( select count(avaliacaocursos_id) from curso_avaliacaocurso where cursos_id = id_curso group by cursos_id), 0)  =
				coalesce((select rct.qtdavaliacoesaprovadaspornota from View_CursoNota as rct where colaboradorturma_id = id_colaboradorturma), 0) 
			 ) 
			 and 
				case when (coalesce((select count(dia) from diaturma where turma_id = id_turma group by turma_id), 0)) > 0 THEN
				(
					(
						 cast(coalesce((select count(id) from colaboradorpresenca where presenca=true and colaboradorturma_id = id_colaboradorturma group by colaboradorturma_id), 0) as DOUBLE PRECISION) / 
						 cast(coalesce((select count(dia) from diaturma where turma_id = id_turma group by turma_id), 0) as DOUBLE PRECISION)
					 ) * 100 
				) >= coalesce((select percentualMinimoFrequencia from curso where id = id_curso), 0)
				else 
					true
				end 
			) as situacao INTO aprovado;
	RETURN aprovado;
END; 
$$ LANGUAGE plpgsql; --.go

CREATE FUNCTION update_aprovacao_colaboradorTurma() RETURNS integer AS $$
DECLARE
	mviews RECORD;
	BEGIN
	FOR mviews IN SELECT ct.id as colaboradorTurmaId, ct.curso_id, ct.turma_id from colaboradorTurma ct 
		LOOP
			update colaboradorturma  set aprovado = (select verifica_aprovacao(mviews.curso_id, mviews.turma_id, mviews.colaboradorTurmaid)) where id = mviews.colaboradorTurmaId;
		END LOOP;
	RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
SELECT update_aprovacao_colaboradorTurma();--.go
DROP FUNCTION update_aprovacao_colaboradorTurma();--.go

CREATE OR REPLACE FUNCTION verifica_certificacao(id_certificado BIGINT, id_colaborador BIGINT) RETURNS BOOLEAN AS $$  
BEGIN 
return (

		select (
				(select (select Array(select distinct cursos_id from certificacao_curso where certificacaos_id = id_certificado order by cursos_id)) =
		  		(select Array(
			  				select distinct cu.id from colaboradorturma ct inner join turma t on t.id = ct.turma_id 
			  				and t.dataprevfim = (select max(dataprevfim) from turma t2 where t2.curso_id = t.curso_id and t2.realizada 
							and dataprevfim >= (coalesce((select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id where cc.colaborador_id = id_colaborador and cc.certificacao_id = id_certificado group by ce.periodicidade), '01/01/2000')))
							inner join curso cu on cu.id = t.curso_id
							where ct.colaborador_id = id_colaborador
							and t.realizada
							and cu.id in (select cursos_id from certificacao_curso where certificacaos_id = id_certificado)
							and ct.aprovado
							order by cu.id 
							) 
				) 
			) 
		and 
		( 
			select (select Array(select distinct avaliacoespraticas_id from certificacao_avaliacaopratica where certificacao_id = id_certificado order by avaliacoespraticas_id)) = 
			(select Array( 
						select distinct caval.avaliacaopratica_id from colaboradoravaliacaopratica caval where caval.colaborador_id = id_colaborador
						and caval.certificacao_id = id_certificado
						and caval.nota >= (select aval.notaMinima from avaliacaopratica aval where aval.id = caval.avaliacaopratica_id)
						and caval.data > (coalesce((select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id where cc.colaborador_id = id_colaborador and cc.certificacao_id = id_certificado group by ce.periodicidade), '01/01/2000'))
						order by caval.avaliacaopratica_id)))
		) 
		as situacao 
	);
END; 
$$ LANGUAGE plpgsql; --.go 