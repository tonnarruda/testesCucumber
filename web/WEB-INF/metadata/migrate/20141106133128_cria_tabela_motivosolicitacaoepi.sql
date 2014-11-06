CREATE TABLE motivoSolicitacaoEpi (
	id bigint NOT NULL,
	descricao character varying(100)
);--.go

ALTER TABLE motivoSolicitacaoEpi ADD CONSTRAINT motivoSolicitacaoEpi_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE motivoSolicitacaoEpi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go