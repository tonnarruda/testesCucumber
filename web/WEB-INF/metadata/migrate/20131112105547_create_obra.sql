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
ALTER TABLE obra ADD CONSTRAINT obra_estado_fk FOREIGN KEY (estado_id) REFERENCES estado(id);--.go
CREATE SEQUENCE obra_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (605, 'ROLE_CAD_OBRA', 'Obras', '/sesmt/obra/list.action', 17, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 605);--.go
alter sequence papel_sequence restart with 606;--.go