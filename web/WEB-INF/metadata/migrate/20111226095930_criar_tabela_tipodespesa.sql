CREATE TABLE tipoDespesa (
	id bigint NOT NULL,
	descricao character varying(50)
);--.go

ALTER TABLE tipoDespesa ADD CONSTRAINT tipoDespesa_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE tipoDespesa_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
