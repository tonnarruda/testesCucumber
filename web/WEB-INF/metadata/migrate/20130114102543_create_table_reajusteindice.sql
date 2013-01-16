CREATE TABLE reajusteindice (
    id bigint NOT NULL,
    indice_id bigint NOT NULL,
    tabelareajustecolaborador_id bigint NOT NULL,
    valoratual numeric(8,2) NOT NULL,
    valorproposto numeric(8,2) NOT NULL
);--.go

ALTER TABLE ONLY reajusteindice ADD CONSTRAINT reajusteindice_pkey PRIMARY KEY (id);--.go
ALTER TABLE ONLY reajusteindice ADD CONSTRAINT reajusteindice_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);--.go
ALTER TABLE ONLY reajusteindice ADD CONSTRAINT reajusteindice_tabelareajustecolaborador_fk FOREIGN KEY (tabelareajustecolaborador_id) REFERENCES tabelareajustecolaborador(id);--.go

CREATE SEQUENCE reajusteindice_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;--.go