CREATE TABLE medidaseguranca (
	id bigint NOT NULL,
	descricao text NOT NULL,
	empresa_id bigint
);--.go

ALTER TABLE medidaseguranca ADD CONSTRAINT medidaseguranca_pkey PRIMARY KEY(id);--.go
ALTER TABLE medidaseguranca ADD CONSTRAINT medidaseguranca_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE medidaseguranca_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go