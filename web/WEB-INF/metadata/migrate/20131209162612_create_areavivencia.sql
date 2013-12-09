CREATE TABLE areavivencia (
	id bigint NOT NULL,
	nome character varying(150) NOT NULL,
	empresa_id bigint
);--.go

ALTER TABLE areavivencia ADD CONSTRAINT areavivencia_pkey PRIMARY KEY(id);--.go
ALTER TABLE areavivencia ADD CONSTRAINT areavivencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE areavivencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go