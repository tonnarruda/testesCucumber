
alter table certificacao add column periodicidade integer;--.go


--Verificar id do papel quando for criar a migrate
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (647,'ROLE_AVALIACAO_PRATICA', 'Avaliação Prática', '/avaliacao/avaliacaoPratica/list.action', 8, true, 366);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 647);--.go

CREATE TABLE avaliacaoPratica (
id bigint NOT NULL,
titulo character varying(100),
notaMinima double precision,
empresa_id bigint
);--.go

ALTER TABLE avaliacaoPratica ADD CONSTRAINT avaliacaoPratica_pkey PRIMARY KEY(id);--.go
ALTER TABLE avaliacaoPratica ADD CONSTRAINT avaliacaoPratica_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE avaliacaoPratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE certificacao_avaliacaopratica (
	certificacao_id bigint NOT NULL,
	avaliacoesPraticas_id bigint NOT NULL
); --.go

ALTER TABLE certificacao_avaliacaopratica ADD CONSTRAINT certificacao_avaliacaopratica_certificacao_fk FOREIGN KEY (certificacao_id) REFERENCES certificacao(id);--.go
ALTER TABLE certificacao_avaliacaopratica ADD CONSTRAINT certificacao_avaliacaopratica_avaliacaoPraticas_fk FOREIGN KEY (avaliacoespraticas_id) REFERENCES avaliacaopratica(id);--.go
CREATE SEQUENCE certificacao_avaliacaopratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

----

CREATE TABLE colaboradorAvaliacaoPratica (
id bigint NOT NULL,
avaliacaoPratica_id bigint,
certificacao_id bigint,
colaborador_id bigint,
data date,
nota double precision
);--.go

ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT colaboradorAvaliacaoPratica_pkey PRIMARY KEY(id);--.go
ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT cap_avaliacaoPratica_fk FOREIGN KEY (avaliacaoPratica_id) REFERENCES avaliacaoPratica(id);--.go
ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT cap_certificacao_fk FOREIGN KEY (certificacao_id) REFERENCES certificacao(id);--.go
ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT cap_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
CREATE SEQUENCE colaboradorAvaliacaoPratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

--OBS: Remover colaboradorAvaliacaoPratica em remover colaborador com dependências 

--Verificar id do papel quando for criar a migrate
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (648,'ROLE_COLABORADOR_AVALIACAO_PRATICA', 'Notas da Avaliação Prática', '/desenvolvimento/colaboradorAvaliacaoPratica/prepare.action', 6, true, 367);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 648);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id, help) VALUES (649,'ROLE_REL_CERTIFICACOES_VENCIDAS_A_VENCER', 'Certificações Vencidas e a Vencer', '/desenvolvimento/turma/prepareImprimirCertificacoesVencidasAVencer.action', 16, true, 368,'Esta permissão estará visível se a configuração realizada no cadastro da empresa na opção "Controlar vencimento da certificação" estiver por: Periodicidade da certificação');--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 649);--.go
ALTER sequence papel_sequence restart WITH 650;--.go

-------
ALTER TABLE empresa ADD COLUMN controlarVencimentoCertificacaoPor integer default 1;--.go

------
update papel set help='Esta permissão estará visível se a configuração realizada no cadastro da empresa na opção "Controlar vencimento da certificação" estiver por: Periodicidade do curso' where id = 635;--.go

------
ALTER TABLE certificacao ADD COLUMN certificacaoPreRequisito_id bigint;--.go
ALTER TABLE certificacao ADD CONSTRAINT certificacao_certificacaoPreRequisito_fk FOREIGN KEY (certificacaoPreRequisito_id) REFERENCES certificacao(id);--.go

------
CREATE TABLE colaboradorCertificacao (
id bigint NOT NULL,
colaborador_id bigint,
certificacao_id bigint,
data date
);--.go

ALTER TABLE colaboradorCertificacao ADD CONSTRAINT colaboradorCertificacao_pkey PRIMARY KEY(id);--.go
ALTER TABLE colaboradorCertificacao ADD CONSTRAINT colaborador_id_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE colaboradorCertificacao ADD CONSTRAINT certificacao_id_fk FOREIGN KEY (certificacao_id) REFERENCES certificacao(id);--.go
CREATE SEQUENCE colaboradorCertificacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go


------25/11/2015

CREATE OR REPLACE FUNCTION certificaColaboradores() RETURNS integer AS $$
DECLARE 
    mvCertificado RECORD; 
    mvColaborador RECORD;
BEGIN 
	FOR mvCertificado IN select id from certificacao LOOP 
		FOR mvColaborador IN 	select distinct col.id as id
					from certificacao_curso  cc
					inner join turma t on cc.cursos_id = t.curso_id 
					inner join colaboradorTurma ct on ct.turma_id = t.id
					inner join colaborador col on col.id = ct.colaborador_id
					where cc.certificacaos_id = mvCertificado.id

		LOOP

			IF  	(select (select Array(select cursos_id from certificacao_curso where certificacaos_id = mvCertificado.id order by cursos_id))
				=
				(select Array(select cu.id
				from colaboradorturma ct
				inner join turma t on t.id = ct.turma_id and t.dataprevfim = (select max(dataprevfim) from turma t2 where t2.curso_id = t.curso_id and t2.realizada)
				inner join curso cu on cu.id = t.curso_id
				where ct.colaborador_id = mvColaborador.id
				and t.realizada
				and cu.id in (select cursos_id from certificacao_curso where certificacaos_id = mvCertificado.id)
				and verifica_aprovacao(cu.id, t.id, ct.id, cu.percentualminimofrequencia)
				order by cu.id)))
			
			THEN 
				insert into colaboradorCertificacao (id,colaborador_id, certificacao_id,data) 
				values (nextval('colaboradorCertificacao_sequence'), mvColaborador.id, mvCertificado.id, current_date);
			END IF;

		END LOOP; 
	END LOOP; 
		
    RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select certificaColaboradores();--.go
drop function certificaColaboradores();--.go

------

CREATE OR REPLACE FUNCTION verifica_aprovacao(id_curso BIGINT, id_turma BIGINT, id_colaboradorturma BIGINT, percentualMinimoFrequencia DOUBLE PRECISION) RETURNS BOOLEAN AS $$  
DECLARE aprovado BOOLEAN; 
BEGIN 

	select (
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
				) >= coalesce(percentualMinimoFrequencia, 0)
				else 
					true
				end 
			) as situacao INTO aprovado;
	
	RETURN aprovado;
	
END; 
 
$$ LANGUAGE plpgsql; --.go  

---Abaixo incompleto

select max(data) from colaboradorcertificacao  where colaborador_id = 74369


		(select (select Array(select cursos_id from certificacao_curso where certificacaos_id = 4140 order by cursos_id))
				=
				(select Array(select cu.id
				from colaboradorturma ct
				inner join turma t on t.id = ct.turma_id and t.dataprevfim = 
						(select max(dataprevfim) from turma t2 where t2.curso_id = t.curso_id and t2.realizada 
						and 
						dataprevfim >= 
						(coalesce((select max(data) from colaboradorcertificacao  where colaborador_id = 74369), dataprevfim))
						)
				inner join curso cu on cu.id = t.curso_id
				where ct.colaborador_id = 74369
				and t.realizada
				and cu.id in (select cursos_id from certificacao_curso where certificacaos_id = 4140)
				and verifica_aprovacao(cu.id, t.id, ct.id, cu.percentualminimofrequencia)
				order by cu.id)))



select (select Array(select avaliacoespraticas_id from certificacao_avaliacaopratica where certificacao_id = 4140 order by avaliacoespraticas_id))
=
(select Array( select caval.avaliacaopratica_id
from colaboradoravaliacaopratica caval
where caval.colaborador_id = 74369
and caval.certificacao_id = 4140
and caval.nota >= (select aval.notaMinima from avaliacaopratica aval where aval.id = caval.avaliacaopratica_id)
and caval.data >= (coalesce((select max(data) from colaboradorcertificacao  where colaborador_id = 74369), caval.data))
order by caval.avaliacaopratica_id))





CREATE OR REPLACE FUNCTION verifica_certificacao(id_certificado BIGINT, id_coalborador BIGINT) RETURNS BOOLEAN AS $$  
DECLARE aprovado BOOLEAN; 

