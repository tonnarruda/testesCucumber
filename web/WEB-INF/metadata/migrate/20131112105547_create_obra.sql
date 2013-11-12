CREATE TABLE obra (
	id bigint NOT NULL,
	nome character varying(100),
	endereco character varying(200),
	empresa_id bigint
);--.go

ALTER TABLE obra ADD CONSTRAINT obra_pkey PRIMARY KEY(id);--.go
ALTER TABLE obra ADD CONSTRAINT obra_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE obra_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (605, 'ROLE_CAD_OBRA', 'Obras', '/sesmt/obra/list.action', 17, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 605);--.go
alter sequence papel_sequence restart with 606;--.go