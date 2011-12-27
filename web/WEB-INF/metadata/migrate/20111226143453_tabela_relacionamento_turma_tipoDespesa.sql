CREATE TABLE turmatipodespesa (
    id bigint NOT NULL,
	turma_id bigint NOT NULL,
    tipodespesa_id bigint NOT NULL,
    despesa double precision
);--.go

ALTER TABLE turmaTipoDespesa ADD CONSTRAINT turmaTipoDespesa_pkey PRIMARY KEY(id);--.go
ALTER TABLE turmaTipoDespesa ADD CONSTRAINT turma__tipodespesa_fk FOREIGN KEY (turma_id) REFERENCES turma(id);--.go
ALTER TABLE turmaTipoDespesa ADD CONSTRAINT turma_tipodespesa_tipodespesas_fk FOREIGN KEY (tipodespesa_id) REFERENCES tipodespesa(id);--.go
CREATE SEQUENCE turmaTipoDespesa_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

