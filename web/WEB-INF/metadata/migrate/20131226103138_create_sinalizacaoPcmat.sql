CREATE TABLE sinalizacaopcmat (
	id bigint NOT NULL,
	pcmat_id bigint,
	descricao character varying(200)
);--.go

ALTER TABLE sinalizacaopcmat ADD CONSTRAINT sinalizacaopcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE sinalizacaopcmat ADD CONSTRAINT sinalizacaopcmat_pcmat_fk FOREIGN KEY (pcmat_id) REFERENCES pcmat(id);--.go
CREATE SEQUENCE sinalizacaopcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go