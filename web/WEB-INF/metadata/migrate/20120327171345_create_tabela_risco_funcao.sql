CREATE TABLE riscofuncao (
	id bigint NOT NULL,
    epceficaz boolean,
    historicofuncao_id bigint,
    risco_id bigint,
    periodicidadeexposicao character(1)
);
ALTER TABLE riscofuncao ADD CONSTRAINT riscofuncao_pkey PRIMARY KEY (id);
ALTER TABLE riscofuncao ADD CONSTRAINT riscofuncao_historicofuncao_fk FOREIGN KEY (historicofuncao_id) REFERENCES historicofuncao(id);
ALTER TABLE riscofuncao ADD CONSTRAINT riscofuncao_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);
CREATE SEQUENCE riscofuncao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;