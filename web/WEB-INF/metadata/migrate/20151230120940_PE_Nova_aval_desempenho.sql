CREATE TABLE criterioavaliacaocompetencia (
	id bigint NOT NULL,
    descricao character varying(100) NOT NULL,
    conhecimento_id bigint,
    habilidade_id bigint,
    atitude_id bigint
); --.go

ALTER TABLE criterioavaliacaocompetencia ADD CONSTRAINT criterioavaliacaocompetencia_pkey PRIMARY KEY(id);--.go
ALTER TABLE criterioavaliacaocompetencia ADD CONSTRAINT criterioavaliacaocompetencia_conhecimento_fk FOREIGN KEY (conhecimento_id) REFERENCES conhecimento(id);--.go
ALTER TABLE criterioavaliacaocompetencia ADD CONSTRAINT criterioavaliacaocompetencia_habilidade_fk FOREIGN KEY (habilidade_id) REFERENCES habilidade(id);--.go
ALTER TABLE criterioavaliacaocompetencia ADD CONSTRAINT criterioavaliacaocompetencia_atitude_fk FOREIGN KEY (atitude_id) REFERENCES atitude(id);--.go
CREATE SEQUENCE criterioavaliacaocompetencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

---------------

CREATE TABLE configuracaonivelcompetenciacriterio (
	id bigint NOT NULL,
	criterio_id bigint,
    criterio_descricao character varying(100) NOT NULL,
    configuracaonivelcompetencia_id bigint,
    nivelcompetencia_id bigint
); --.go

ALTER TABLE configuracaonivelcompetenciacriterio ADD CONSTRAINT configuracaonivelcompetenciacriterio_pkey PRIMARY KEY(id);--.go
ALTER TABLE configuracaonivelcompetenciacriterio ADD CONSTRAINT configuracaonivelcompetenciacriterio_cnc_fk FOREIGN KEY (configuracaonivelcompetencia_id) REFERENCES configuracaonivelcompetencia(id);--.go
ALTER TABLE configuracaonivelcompetenciacriterio ADD CONSTRAINT configuracaonivelcompetenciacriterio_nivelcompetencia_fk FOREIGN KEY (nivelcompetencia_id) REFERENCES nivelcompetencia(id);--.go
CREATE SEQUENCE configuracaonivelcompetenciacriterio_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE ConfiguracaoNivelCompetencia ADD COLUMN pesocompetencia smallint;--.go
update configuracaonivelcompetencia set pesocompetencia = 1 where configuracaonivelcompetenciafaixasalarial_id is not null;--.go
------

ALTER TABLE empresa ADD COLUMN mostrarPerformanceAvalDesempenho boolean default false;--.go

---------------

--verificar id dos papeis ao criar migrate
update papel set url = null where id = 516;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (650, 'ROLE_CAD_NIVEL_COMPETENCIA', 'Cadastros', '/captacao/nivelCompetencia/list.action', 1, true, 516);--.go
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT perfil_id, 650 FROM perfil_papel where papeis_id = 516;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (651, 'ROLE_CAD_NIVEL_COMPETENCIA', 'Historicos', '/captacao/nivelCompetenciaHistorico/list.action', 2, true, 516);--.go
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT perfil_id, 651 FROM perfil_papel where papeis_id = 516;--.go

alter sequence papel_sequence restart with 652;--.go

---------------

CREATE TABLE nivelCompetenciaHistorico (
	id bigint NOT NULL,
	data date NOT NULL,
	empresa_id bigint NOT NULL
);--.go

ALTER TABLE nivelCompetenciaHistorico ADD CONSTRAINT nivelCompetenciaHistorico_pkey PRIMARY KEY(id);--.go
ALTER TABLE nivelCompetenciaHistorico ADD CONSTRAINT nivelCompetenciaHistorico_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE nivelCompetenciaHistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
CREATE UNIQUE INDEX nivelCompetenciaHistorico_data_empresa_uk ON nivelCompetenciaHistorico(data,empresa_id);--.go

CREATE TABLE ConfigHistoricoNivel (
    id bigint NOT NULL,
    nivelCompetencia_id bigint NOT NULL,
    nivelCompetenciaHistorico_id bigint NOT NULL,
    ordem int,
	percentual double precision
);--.go
ALTER TABLE ConfigHistoricoNivel ADD CONSTRAINT ConfigHistoricoNivel_pkey PRIMARY KEY(id);--.go
ALTER TABLE ConfigHistoricoNivel ADD CONSTRAINT ConfigHistoricoNivel_nivelCompetencia_fk FOREIGN KEY (nivelCompetencia_id) REFERENCES nivelCompetencia(id);--.go 
ALTER TABLE ConfigHistoricoNivel ADD CONSTRAINT ConfigHistoricoNivel_nivelCompetenciaHistorico_fk FOREIGN KEY (nivelCompetenciaHistorico_id) REFERENCES nivelCompetenciaHistorico(id);--.go
CREATE SEQUENCE ConfigHistoricoNivel_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

----------------

ALTER TABLE configuracaoNivelCompetenciaFaixaSalarial ADD COLUMN nivelCompetenciaHistorico_id BIGINT;--.go
ALTER TABLE configuracaoNivelCompetenciaFaixaSalarial ADD CONSTRAINT configNivelCompFaixaSalarial_nivelCompetenciaHistorico_fk FOREIGN KEY (nivelCompetenciaHistorico_id) REFERENCES nivelCompetenciaHistorico(id);--.go

----------------

CREATE OR REPLACE FUNCTION criaConfigHistoricoNivel() RETURNS integer AS $$
DECLARE 
	mv RECORD; 
	nch_id BIGINT;
BEGIN 
    FOR mv IN 
		select distinct empresa_id from nivelCompetencia
		LOOP 
			nch_id := nextval('nivelCompetenciaHistorico_sequence');

			INSERT INTO nivelCompetenciaHistorico(id,data,empresa_id) values(nch_id,'2005-01-01',mv.empresa_id);--.go
			INSERT INTO ConfigHistoricoNivel(id,nivelCompetencia_id,nivelCompetenciaHistorico_id,ordem) SELECT nextval('ConfigHistoricoNivel_sequence'),id, nch_id ,ordem FROM nivelcompetencia WHERE empresa_id = mv.empresa_id;--.go
			UPDATE configuracaoNivelCompetenciaFaixaSalarial SET nivelcompetenciahistorico_id = nch_id  WHERE faixasalarial_id  IN (SELECT fs.id FROM faixasalarial fs JOIN cargo c ON fs.cargo_id = c.id AND c.empresa_id = mv.empresa_id );--.go
		END LOOP; 
		
    RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go
select criaConfigHistoricoNivel();--.go
drop function criaConfigHistoricoNivel();--.GO

ALTER TABLE nivelCompetencia drop COLUMN ordem;--.go

----------

CREATE TABLE participanteavaliacaodesempenho (
	id bigint NOT NULL,
    colaborador_id bigint,
    avaliacaodesempenho_id bigint,
    tipo character
); --.go

ALTER TABLE participanteavaliacaodesempenho ADD CONSTRAINT participanteavaliacaodesempenho_pkey PRIMARY KEY(id);--.go
ALTER TABLE participanteavaliacaodesempenho ADD CONSTRAINT participanteavaliacaodesempenho_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE participanteavaliacaodesempenho ADD CONSTRAINT participanteavaliacaodesempenho_avaliacaodesempenho_fk FOREIGN KEY (avaliacaodesempenho_id) REFERENCES avaliacaodesempenho(id);--.go
CREATE SEQUENCE participanteavaliacaodesempenho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go


----------------

drop index if exists index_colaboradorquestionario_colaborador;--.go
create index index_colaboradorquestionario_colaborador on colaboradorquestionario (avaliador_id,avaliacaodesempenho_id,colaborador_id);--.go

CREATE FUNCTION relaciona_avaliado_avaliador() RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select distinct cq.avaliador_id as avaliadorId, cq.avaliacaodesempenho_id as avaliacaoDesempenhoId, ad.avaliacao_id as avaliacaoId, 
			cq_a.colaborador_id as colaboradorId
			from colaboradorquestionario cq
			left join avaliacaodesempenho ad on ad.id = cq.avaliacaodesempenho_id 
			left join colaboradorquestionario cq_a on cq_a.avaliacaodesempenho_id = cq.avaliacaodesempenho_id and cq_a.avaliador_id is null
			where
				cq.avaliacaodesempenho_id is not null
				and cq.avaliador_id is not null
				and cq.colaborador_id is null
				and cq_a.colaborador_id is not null
				and not(cq.avaliador_id = cq_a.colaborador_id and ad.permiteautoavaliacao = true)
		LOOP
			insert into colaboradorquestionario(id, colaborador_id, avaliacao_id, avaliacaodesempenho_id, avaliador_id, respondida) 
			values ( nextval('colaboradorquestionario_sequence'), mviews.colaboradorId, mviews.avaliacaoId, mviews.avaliacaoDesempenhoId, mviews.avaliadorId, false);
		END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select relaciona_avaliado_avaliador();--.go
drop function relaciona_avaliado_avaliador();--.go 

delete from colaboradorquestionario where (colaborador_id is null or avaliador_id is null) and avaliacaodesempenho_id is not null;

drop index if exists index_colaboradorquestionario_colaborador;--.go

---------------

CREATE FUNCTION insert_participante_avaliador() RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select distinct avaliador_id as colaboradorId, avaliacaodesempenho_id as avaliacaoDesempenhoId, 'R' as tipo from colaboradorquestionario where avaliacaodesempenho_id is not null
		LOOP
			insert into participanteavaliacaodesempenho(id, colaborador_id, avaliacaodesempenho_id, tipo) values (nextval('participanteavaliacaodesempenho_sequence'), mviews.colaboradorId, mviews.avaliacaoDesempenhoId, mviews.tipo);
		END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insert_participante_avaliador();--.go
drop function insert_participante_avaliador();--.go 

---------------

CREATE FUNCTION insert_participante_avaliado() RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select distinct colaborador_id as colaboradorId, avaliacaodesempenho_id as avaliacaoDesempenhoId, 'A' as tipo from colaboradorquestionario where avaliacaodesempenho_id is not null and colaborador_id is not null
		LOOP
			insert into participanteavaliacaodesempenho(id, colaborador_id, avaliacaodesempenho_id, tipo) values ( nextval('participanteavaliacaodesempenho_sequence'), mviews.colaboradorId, mviews.avaliacaoDesempenhoId, mviews.tipo);
		END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insert_participante_avaliado();--.go
drop function insert_participante_avaliado();--.go

---------------
ALTER TABLE participanteavaliacaodesempenho ADD COLUMN produtividade double precision; --.go

---------------
ALTER TABLE colaboradorquestionario ADD COLUMN pesoAvaliador integer default 1; --.go

---------
DELETE from colaboradorquestionario where id in 
(select cq.id from colaboradorquestionario cq
	left join avaliacaodesempenho ad on ad.id = cq.avaliacaodesempenho_id 
	left join colaboradorquestionario cq_a on cq_a.avaliacaodesempenho_id = cq.avaliacaodesempenho_id and cq_a.avaliador_id is null
	where
		cq.colaborador_id is null
		and not(cq.avaliador_id = cq_a.colaborador_id and ad.permiteautoavaliacao = true)
);--.go

----------

CREATE TABLE configuracaocompetenciaavaliacaodesempenho (
	id bigint NOT NULL,
    avaliador_id bigint,
    avaliacaodesempenho_id bigint,
    configuracaonivelcompetenciafaixasalarial_id bigint,
    competencia_id bigint not null,
    tipoCompetencia character
); --.go

ALTER TABLE configuracaocompetenciaavaliacaodesempenho ADD CONSTRAINT configuracaocompetenciaavaliacaodesempenho_pkey PRIMARY KEY(id);--.go
ALTER TABLE configuracaocompetenciaavaliacaodesempenho ADD CONSTRAINT configuracaocompetenciaavaliacaodesempenho_avaliador_fk FOREIGN KEY (avaliador_id) REFERENCES colaborador(id);--.go
ALTER TABLE configuracaocompetenciaavaliacaodesempenho ADD CONSTRAINT configuracaocompetenciaavaliacaodesempenho_configuracaonivelcompetenciafaixasalarial_fk FOREIGN KEY (configuracaonivelcompetenciafaixasalarial_id) REFERENCES configuracaonivelcompetenciafaixasalarial(id);--.go
ALTER TABLE configuracaocompetenciaavaliacaodesempenho ADD CONSTRAINT configuracaocompetenciaavaliacaodesempenho_avaliacaodesempenho_fk FOREIGN KEY (avaliacaodesempenho_id) REFERENCES avaliacaodesempenho(id);--.go
CREATE SEQUENCE configuracaocompetenciaavaliacaodesempenho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

------

---------Abaixo em 04/12/2015

DELETE FROM configuracaoNivelCompetenciaColaborador WHERE colaborador_id is null or faixasalarial_id is null;--.go
ALTER TABLE configuracaoNivelCompetenciaColaborador ADD COLUMN ConfiguracaoNivelCompetenciaFaixaSalarial_id bigint;--.go
ALTER TABLE configuracaoNivelCompetenciaColaborador ADD CONSTRAINT cncc_cncf_fk FOREIGN KEY (ConfiguracaoNivelCompetenciaFaixaSalarial_id) REFERENCES ConfiguracaoNivelCompetenciaFaixaSalarial(id);--.go

CREATE FUNCTION insere_cncfid_em_cncc() RETURNS integer AS $$
DECLARE
    mv RECORD;
BEGIN
    FOR mv IN select distinct faixasalarial_id, data from configuracaoNivelCompetenciaColaborador where faixasalarial_id is not null and colaborador_id is not null order by faixasalarial_id, data
	LOOP
		update configuracaoNivelCompetenciaColaborador set ConfiguracaoNivelCompetenciaFaixaSalarial_id =
			(select id from ConfiguracaoNivelCompetenciaFaixaSalarial cncf
			where
			cncf.faixasalarial_id = mv.faixasalarial_id 
			and cncf.data = (
			    select
				max(data) 
			    from
				ConfiguracaoNivelCompetenciaFaixaSalarial cncf2 
			    where
				cncf2.faixasalarial_id = cncf.faixasalarial_id 
				and cncf2.data <= mv.data
			) limit 1)
		where faixasalarial_id = mv.faixasalarial_id 
		and data = mv.data;
	END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insere_cncfid_em_cncc();--.go
drop function insere_cncfid_em_cncc();--.go

UPDATE colaboradorquestionario SET configuracaonivelcompetenciacolaborador_id = null WHERE configuracaonivelcompetenciacolaborador_id IN(SELECT id FROM configuracaoNivelCompetenciaColaborador WHERE ConfiguracaoNivelCompetenciaFaixaSalarial_id IS NULL AND colaboradorquestionario_id IS NOT NULL);--.go

DELETE FROM configuracaoNivelCompetenciaColaborador WHERE ConfiguracaoNivelCompetenciaFaixaSalarial_id is null;--.go

------15/12/2015
ALTER TABLE colaboradorQuestionario ADD COLUMN respondidaParcialmente boolean NOT NULL DEFAULT false; --.go
------

ALTER TABLE avaliacao ADD COLUMN respostasCompactas boolean NOT NULL DEFAULT false; --.go

----- 21/12/2015
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (652, 'ROLE_REL_ANALISE_COMPETENCIAS_COLABORADOR', 'Análise de Desempenho das Competências do Colaborador', '/avaliacao/desempenho/prepareAnaliseDesempenhoCompetenciaColaborador.action', 7, true, 486);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 652);--.go
alter sequence papel_sequence restart with 653;--.go

-----

------21/12/2015
ALTER TABLE avaliacaodesempenho ADD COLUMN exiberesultadoautoavaliacao boolean default false;--.go
ALTER TABLE avaliacaodesempenho ADD COLUMN empresa_id bigint;--.go
ALTER TABLE avaliacaodesempenho ADD CONSTRAINT avaliacaodesempenho_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go

CREATE FUNCTION insere_empresa_em_avaliacaodesempenho() RETURNS integer AS $$
DECLARE
    mv RECORD;
BEGIN
    FOR mv IN select id, empresa_id, exiberesultadoautoavaliacao from avaliacao  where exiberesultadoautoavaliacao
	LOOP
		update avaliacaodesempenho set empresa_id = mv.empresa_id, exiberesultadoautoavaliacao = mv.exiberesultadoautoavaliacao where avaliacao_id = mv.id;  	
	END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
select insere_empresa_em_avaliacaodesempenho();--.go
drop function insere_empresa_em_avaliacaodesempenho();--.go

--------
