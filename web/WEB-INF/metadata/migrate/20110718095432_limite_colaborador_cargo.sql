CREATE TABLE configuracaoLimiteColaborador (
id bigint NOT NULL,
descricao character varying(100) NOT NULL,
areaorganizacional_id bigint
);--.go

ALTER TABLE configuracaoLimiteColaborador ADD CONSTRAINT configuracaoLimiteColaborador_pkey PRIMARY KEY(id);--.go
ALTER TABLE configuracaoLimiteColaborador ADD CONSTRAINT configuracaoLimiteColaborador_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);--.go
ALTER TABLE configuracaoLimiteColaborador ADD CONSTRAINT configuracaoLimiteColaborador_areaorganizacional_uk UNIQUE (areaorganizacional_id);--.go
CREATE SEQUENCE configuracaoLimiteColaborador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go