CREATE TABLE tipoDocumento (
id bigint NOT NULL,
descricao character varying(50)
);--.go

ALTER TABLE tipoDocumento ADD CONSTRAINT tipoDocumento_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE tipoDocumento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go