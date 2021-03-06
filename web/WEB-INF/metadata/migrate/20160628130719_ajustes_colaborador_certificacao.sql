
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

drop index if exists index_avaliacaoPratica_id;--.go
CREATE INDEX index_avaliacaoPratica_id ON avaliacaoPratica (id);--.go

drop index if exists index_colabAvPratica_id;--.go
CREATE INDEX index_colabAvPratica_id ON ColaboradorAvaliacaoPratica (id);--.go

drop index if exists index_colabAvPratica_certificacao_id;--.go
CREATE INDEX index_colabAvPratica_certificacao_id ON ColaboradorAvaliacaoPratica (certificacao_id);--.go

drop index if exists index_colabAvPratica_colaborador_id;--.go
CREATE INDEX index_colabAvPratica_colaborador_id ON ColaboradorAvaliacaoPratica (colaborador_id);--.go

drop index if exists index_colabCertificacao_id;--.go
CREATE INDEX index_colabCertificacao_id ON colaboradorcertificacao (id);--.go

drop index if exists index_colabCertificacao_data;--.go
CREATE INDEX index_colabCertificacao_data ON colaboradorcertificacao (data);--.go

drop index if exists index_colabCertificacao_colaborador_certificacao;--.go
CREATE INDEX index_colabCertificacao_colaborador_certificacao ON colaboradorcertificacao (colaborador_id, certificacao_id);--.go

drop index if exists index_colabCertificacao_colaborador;--.go
CREATE INDEX index_colabCertificacao_colaborador ON colaboradorcertificacao (colaborador_id);--.go

drop index if exists index_colabCertificacao_certificacao;--.go
CREATE INDEX index_colabCertificacao_certificacao ON colaboradorcertificacao (certificacao_id);--.go

drop index if exists index_turma_curso;--.go
CREATE INDEX index_turma_curso on turma (curso_id);--.go

drop index if exists index_turma_dataPrevFim;--.go
CREATE INDEX index_turma_dataPrevFim on turma (dataPrevFim);--.go

drop index if exists index_certificacao_curso;--.go
CREATE INDEX index_certificacao_curso on certificacao_curso (certificacaos_id, cursos_id);--.go

drop index if exists index_hc_area;--.go
CREATE INDEX index_hc_area on historicocolaborador (areaorganizacional_id);--.go

drop index if exists index_hc_faixa;--.go
CREATE INDEX index_hc_faixa on historicocolaborador (faixasalarial_id);--.go

drop index if exists index_hc_estabelecimento;--.go
CREATE INDEX index_hc_estabelecimento on historicocolaborador (estabelecimento_id);--.go

drop index if exists index_faixasalarial_cargo;--.go
CREATE INDEX index_faixasalarial_cargo on faixasalarial (cargo_id);--.go

drop index if exists index_faixasalarial;--.go
CREATE INDEX index_faixasalarial on faixasalarial (id);--.go

drop function if exists verifica_aprovacao(id_curso BIGINT, id_turma BIGINT, id_colaboradorturma BIGINT, percentualMinimoFrequencia DOUBLE PRECISION);--.go

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

drop function if exists update_aprovacao_colaboradorTurma();--.go

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

drop function if exists verifica_certificacao(id_certificado BIGINT, id_colaborador BIGINT);--.go

CREATE OR REPLACE FUNCTION verifica_certificacao(id_certificado BIGINT, id_colaborador BIGINT) RETURNS BOOLEAN AS $$  
BEGIN 
return (

		select (
				(select (select Array(select distinct cursos_id from certificacao_curso where certificacaos_id = id_certificado order by cursos_id)) =
		  		(select Array(
								select distinct t.curso_id from colaboradorturma ct 
								inner join turma t on t.id = ct.turma_id 
								and t.dataprevfim = (select max(dataprevfim)  from colaboradorturma ct2 inner join turma t2 on t2.id = ct2.turma_id 
								where t2.curso_id = t.curso_id and t2.realizada and ct2.colaborador_id = id_colaborador
								and dataprevfim >= (coalesce((select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) 
								from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id where cc.colaborador_id = id_colaborador 
								and cc.certificacao_id = id_certificado group by ce.periodicidade), '01/01/2000')))
								where ct.colaborador_id = id_colaborador
								and t.realizada
								and t.curso_id in (select cursos_id from certificacao_curso where certificacaos_id = id_certificado)
								and ct.aprovado
								order by t.curso_id
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

delete from perfil_papel where papeis_id in (463,50,64,6,16,72,15,12,475,66,47,36,29,31,32,13,529,618,634);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (673, 'USUARIO_FORTES', 'Usuário Fortes', '#', 17, true, 37);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (674, 'REPROCESSA_CERTIFICACAO', 'Reprocessar Certificações', '/desenvolvimento/certificacao/prepareReprocessaCertificacao.action', 4, true, 673);--.go
update papel set papelmae_id = 673 where id in (463, 529, 618, 634);--.go
update papel set ordem = 1 where id = 529;--.go
update papel set ordem = 2 where id = 618;--.go
update papel set ordem = 3 where id = 634;--.go
alter sequence papel_sequence restart with 676;--.go