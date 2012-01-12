CREATE TABLE providencia (
id bigint NOT NULL,
descricao character varying(100),
empresa_id bigint
);--.go

ALTER TABLE providencia ADD CONSTRAINT providencia_pkey PRIMARY KEY(id);--.go
ALTER TABLE providencia ADD CONSTRAINT providencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE providencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go