CREATE TABLE fase (
	id bigint NOT NULL,
	descricao character varying(200) NOT NULL,
	empresa_id bigint
);--.go

ALTER TABLE fase ADD CONSTRAINT fase_pkey PRIMARY KEY(id);--.go
ALTER TABLE fase ADD CONSTRAINT fase_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE fase_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go