CREATE SEQUENCE historicoextintor_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE historicoextintor (
	id bigint NOT NULL DEFAULT nextval('historicoextintor_sequence'),
	extintor_id bigint NOT NULL,
	estabelecimento_id bigint NOT NULL,
	localizacao varchar(50),
	data Date NOT NULL
);--.go

ALTER TABLE historicoextintor ADD CONSTRAINT historicoextintor_pkey PRIMARY KEY (id);--.go
ALTER TABLE historicoextintor ADD CONSTRAINT historicoextintor_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);--.go
ALTER TABLE historicoextintor ADD CONSTRAINT historicoextintor_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);--.go