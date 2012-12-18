CREATE TABLE reajustefaixasalarial (
    id bigint NOT NULL,
    tabelareajustecolaborador_id bigint NOT NULL,
    tipoatual integer NOT NULL,
    tipoproposto integer NOT NULL,
    indiceatual_id bigint,
    indiceproposto_id bigint,
    qtdindiceatual double precision,
    qtdindiceproposta double precision,
    valoratual double precision NOT NULL,
    valorproposto double precision NOT NULL
);--.go

ALTER TABLE ONLY reajustefaixasalarial ADD CONSTRAINT reajustefaixasalarial_pkey PRIMARY KEY (id);--.go
ALTER TABLE ONLY reajustefaixasalarial ADD CONSTRAINT reajustefaixasalarial_tabelareajustecolaborador_fk FOREIGN KEY (tabelareajustecolaborador_id) REFERENCES tabelareajustecolaborador(id);--.go
ALTER TABLE ONLY reajustefaixasalarial ADD CONSTRAINT reajustefaixasalarial_indiceatual_fk FOREIGN KEY (indiceatual_id) REFERENCES indice(id);--.go
ALTER TABLE ONLY reajustefaixasalarial ADD CONSTRAINT reajustefaixasalarial_indiceproposto_fk FOREIGN KEY (indiceproposto_id) REFERENCES indice(id);--.go

CREATE SEQUENCE reajustefaixasalarial_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;--.go