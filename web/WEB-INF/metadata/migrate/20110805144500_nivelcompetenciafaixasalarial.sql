create table configuracaoNivelCompetencia 
(
  id bigint NOT NULL,
  faixasalarial_id bigint,
  nivelcompetencia_id bigint NOT NULL,
  competencia_id bigint,
  candidato_id bigint,
  tipocompetencia character
);--.go

ALTER TABLE configuracaoNivelCompetencia ADD CONSTRAINT configuracaoNivelCompetencia_pkey PRIMARY KEY (id);--.go
ALTER TABLE configuracaoNivelCompetencia ADD CONSTRAINT configuracaoNivelCompetencia_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);--.go
ALTER TABLE configuracaoNivelCompetencia ADD CONSTRAINT configuracaoNivelCompetencia_nivelcompetencia_fk FOREIGN KEY (nivelcompetencia_id) REFERENCES nivelcompetencia(id);--.go
ALTER TABLE configuracaoNivelCompetencia ADD CONSTRAINT configuracaoNivelCompetencia_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);--.go
CREATE SEQUENCE configuracaoNivelCompetencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go