create table configuracaoNivelCompetenciaColaborador 
(
  id bigint NOT NULL,
  colaborador_id bigint,
  faixasalarial_id bigint,
  data date
);--.go

ALTER TABLE configuracaoNivelCompetenciaColaborador ADD CONSTRAINT configuracaoNivelCompetenciaColaborador_pkey PRIMARY KEY (id);--.go
ALTER TABLE configuracaoNivelCompetenciaColaborador ADD CONSTRAINT configuracaoNivelCompetenciaColaborador_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE configuracaoNivelCompetenciaColaborador ADD CONSTRAINT configuracaoNivelCompetenciaColaborador_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);--.go
CREATE SEQUENCE configuracaoNivelCompetenciaColaborador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go