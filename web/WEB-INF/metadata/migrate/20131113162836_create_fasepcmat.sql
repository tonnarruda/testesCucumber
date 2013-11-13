CREATE TABLE fasepcmat (
	id bigint NOT NULL,
	descricao character varying(200) NOT NULL,
	empresa_id bigint
);--.go

ALTER TABLE fasepcmat ADD CONSTRAINT fasepcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE fasepcmat ADD CONSTRAINT fasepcmat_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE fasepcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (607, 'ROLE_CAD_FASEPCMAT', 'Fases', '/sesmt/fasepcmat/list.action', 18, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 607);--.go
alter sequence papel_sequence restart with 608;--.go