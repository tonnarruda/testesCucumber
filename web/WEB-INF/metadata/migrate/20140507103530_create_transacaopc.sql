CREATE TABLE transacaopc (
	id BIGINT NOT NULL,
	codigoUrl SMALLINT NOT NULL,
	json TEXT NOT NULL,
	data TIMESTAMP NOT NULL default current_timestamp
);--.go

ALTER TABLE transacaopc ADD CONSTRAINT transacaopc_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE transacaopc_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go