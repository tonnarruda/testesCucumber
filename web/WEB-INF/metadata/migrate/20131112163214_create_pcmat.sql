CREATE TABLE pcmat (
	id bigint NOT NULL,
	apartirde date NOT NULL,
	tipoobra character varying(100) NOT NULL,
	datainiobra date NOT NULL,
	datafimobra date NOT NULL,
	obra_id bigint
);--.go

ALTER TABLE pcmat ADD CONSTRAINT pcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE pcmat ADD CONSTRAINT pcmat_obra_fk FOREIGN KEY (obra_id) REFERENCES obra(id);--.go
CREATE SEQUENCE pcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (606, 'ROLE_MOV_PCMAT', 'PCMAT', '/sesmt/pcmat/list.action', 10, true, 386);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 606);--.go
alter sequence papel_sequence restart with 607;--.go