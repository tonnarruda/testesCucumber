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

ALTER TABLE nivelCompetencia ADD COLUMN percentual double precision;--.go

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

