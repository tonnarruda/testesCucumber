
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


------25/11/2015 --- CONSULTA ABAIXO UM POUCO LENTA. DECIDIR O QUE FAZER

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

-----

CREATE OR REPLACE FUNCTION verifica_certificacao(id_certificado BIGINT, id_coalborador BIGINT) RETURNS BOOLEAN AS $$  
DECLARE 
 certificado BOOLEAN; 
 mv RECORD; 
BEGIN 
FOR mv IN with RECURSIVE certificacaoId_recursivo AS(
			select id, certificacaoprerequisito_id from certificacao  where id = id_certificado 
			union
			select c.id, c.certificacaoprerequisito_id from certificacao c
			INNER JOIN certificacaoId_recursivo certr ON c.id = certr.certificacaoprerequisito_id 
				
		)SELECT id as cetId FROM certificacaoId_recursivo
	LOOP
		select (
				(select (select Array(select cursos_id from certificacao_curso where certificacaos_id = mv.cetId order by cursos_id)) =
		  		(select Array(
			  				select cu.id from colaboradorturma ct inner join turma t on t.id = ct.turma_id 
			  				and t.dataprevfim = (select max(dataprevfim) from turma t2 where t2.curso_id = t.curso_id and t2.realizada 
							and dataprevfim > (coalesce((select max(data) from colaboradorcertificacao  where colaborador_id = id_coalborador and certificacao_id = mv.cetId), '01/01/2000')))
							inner join curso cu on cu.id = t.curso_id
							where ct.colaborador_id = id_coalborador
							and t.realizada
							and cu.id in (select cursos_id from certificacao_curso where certificacaos_id = mv.cetId)
							and verifica_aprovacao(cu.id, t.id, ct.id, cu.percentualminimofrequencia)
							order by cu.id
							)
				)
			)
		and
		(
			select (select Array(select avaliacoespraticas_id from certificacao_avaliacaopratica where certificacao_id = mv.cetId order by avaliacoespraticas_id)) =
			(select Array( 
						select caval.avaliacaopratica_id from colaboradoravaliacaopratica caval where caval.colaborador_id = id_coalborador
						and caval.certificacao_id = mv.cetId
						and caval.nota >= (select aval.notaMinima from avaliacaopratica aval where aval.id = caval.avaliacaopratica_id)
						and caval.data > (coalesce((select max(data) from colaboradorcertificacao  where colaborador_id = id_coalborador and certificacao_id = mv.cetId), '01/01/2000'))
						order by caval.avaliacaopratica_id)))
		) 
		as situacao INTO certificado;

		IF certificado = false THEN
			RETURN false;
		END IF;

	END LOOP; 
	
	RETURN true;
END; 
$$ LANGUAGE plpgsql; --.go 

---------------------

-- 08/12/2015 -- VERIFICAR ID QUANDO CRIAR MIGRATIONS

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (700, 'ROLE_REL_CERTIFICADOS_VENCIDOS_A_VENCER', 'Cerificados Vencidos e a Vencer', '/desenvolvimento/certificacao/prepareImprimirCertificadosVencidosAVencer.action', 17, true, 368);--.go 
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 700);--.go
ALTER sequence papel_sequence restart WITH 701;--.go

------ 
