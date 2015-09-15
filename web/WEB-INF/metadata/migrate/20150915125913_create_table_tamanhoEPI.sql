CREATE TABLE tamanhoepi (
	id bigint NOT NULL,
    descricao character varying(30) NOT NULL
); --.go

ALTER TABLE tamanhoepi ADD CONSTRAINT tamanhoepi_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE tamanhoepi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
