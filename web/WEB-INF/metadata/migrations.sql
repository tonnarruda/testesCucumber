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

-----

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

-------------

--verificar id dos papeis ao criar migrate
update papel set url = null where id = 516;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (650, 'ROLE_CAD_NIVEL_COMPETENCIA', 'Cadastros', '/captacao/nivelCompetencia/list.action', 1, true, 516);--.go
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT id, 650 FROM perfil where papeis_id = 516;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (651, 'ROLE_CAD_NIVEL_COMPETENCIA', 'Historicos', '/captacao/nivelCompetencia/listHistoricos.action', 2, true, 516);--.go
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT id, 651 FROM perfil where papeis_id = 516;--.go

alter sequence papel_sequence restart with 652;--.go

---------- 

CREATE TABLE nivelCompetenciaHistorico (
	id bigint NOT NULL,
	data date NOT NULL
);--.go

ALTER TABLE nivelCompetenciaHistorico ADD CONSTRAINT nivelCompetenciaHistorico_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE nivelCompetenciaHistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE configNivelCompetenciaHistorico (
    id bigint NOT NULL,
    nivelCompetencia_id bigint NOT NULL,
    nivelCompetenciaHistorico_id bigint NOT NULL,
    ordem int,
	percentual double precision
);--.go
ALTER TABLE configNivelCompetenciaHistorico ADD CONSTRAINT configNivelCompetenciaHistorico_pkey PRIMARY KEY(id);--.go
ALTER TABLE configNivelCompetenciaHistorico ADD CONSTRAINT configNivelCompetenciaHistorico_nivelCompetencia_fk FOREIGN KEY (nivelCompetencia_id) REFERENCES nivelCompetencia(id);--.go 
ALTER TABLE configNivelCompetenciaHistorico ADD CONSTRAINT configNivelCompetenciaHistorico_nivelCompetenciaHistorico_fk FOREIGN KEY (nivelCompetenciaHistorico_id) REFERENCES nivelCompetenciaHistorico(id);--.go
CREATE SEQUENCE configNivelCompetenciaHistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO nivelCompetenciaHistorico(id,data) select id,'2005-01-01' from empresa;--.go
INSERT INTO configNivelCompetenciaHistorico(id,nivelCompetencia_id,nivelCompetenciaHistorico_id,ordem,percentual) select nextval('configNivelCompetenciaHistorico_sequence'),id,1,ordem,percentual from nivelcompetencia;--.go

ALTER TABLE nivelCompetencia drop COLUMN ordem;--.go
ALTER TABLE nivelCompetencia drop COLUMN percentual;--.go