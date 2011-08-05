create table nivelcompetenciafaixasalarial 
(
  id bigint NOT NULL,
  faixasalarial_id bigint NOT NULL,
  nivelcompetencia_id bigint NOT NULL,
  competencia_id bigint,
  tipocompetencia character
);--.go

ALTER TABLE nivelcompetenciafaixasalarial ADD CONSTRAINT nivelcompetenciafaixasalarial_pkey PRIMARY KEY (id);--.go
ALTER TABLE nivelcompetenciafaixasalarial ADD CONSTRAINT nivelcompetenciafaixasalarial_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);--.go
ALTER TABLE nivelcompetenciafaixasalarial ADD CONSTRAINT nivelcompetenciafaixasalarial_nivelcompetencia_fk FOREIGN KEY (nivelcompetencia_id) REFERENCES nivelcompetencia(id);--.go
CREATE SEQUENCE nivelcompetenciafaixasalarial_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go