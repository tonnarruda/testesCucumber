CREATE TABLE nivelCompetencia (
	id bigint NOT NULL,
	descricao character varying(20),
	ordem int,
	empresa_id bigint
);--.go

ALTER TABLE nivelCompetencia ADD CONSTRAINT nivelCompetencia_pkey PRIMARY KEY(id);--.go
ALTER TABLE nivelCompetencia ADD CONSTRAINT nivelCompetencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE nivelCompetencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go