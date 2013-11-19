CREATE TABLE fasepcmat (
	id bigint NOT NULL,
	descricao character varying(200) NOT NULL,
	empresa_id bigint
);--.go

ALTER TABLE fasepcmat ADD CONSTRAINT fasepcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE fasepcmat ADD CONSTRAINT fasepcmat_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE fasepcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go