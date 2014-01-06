CREATE TABLE obra (
	id bigint NOT NULL,
	nome character varying(100) NOT NULL,
	tipoobra character varying(100) NOT NULL,
	logradouro character varying(200),
	numero character varying(10),
	complemento character varying(200),
	bairro character varying(100),
	cep character varying(8),
	cidade_id bigint,
	uf_id bigint,
	empresa_id bigint
);--.go

ALTER TABLE obra ADD CONSTRAINT obra_pkey PRIMARY KEY(id);--.go
ALTER TABLE obra ADD CONSTRAINT obra_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
ALTER TABLE obra ADD CONSTRAINT obra_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);--.go
ALTER TABLE obra ADD CONSTRAINT obra_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);--.go
CREATE SEQUENCE obra_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go