CREATE TABLE faturamentoMensal (
id bigint NOT NULL,
mesAno date,
valor double precision,
empresa_id bigint
);--.go

ALTER TABLE faturamentoMensal ADD CONSTRAINT faturamentoMensal_pkey PRIMARY KEY(id);--.go
ALTER TABLE faturamentoMensal ADD CONSTRAINT faturamentoMensal_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE faturamentoMensal_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go