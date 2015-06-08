CREATE TABLE configuracaoNivelCompetenciaFaixaSalarial( 
  id BIGINT NOT NULL, 
  faixasalarial_id BIGINT, 
  data DATE 
);--.go

ALTER TABLE configuracaoNivelCompetenciaFaixaSalarial ADD CONSTRAINT configuracaoNivelCompetenciaFaixaSalarial_pkey PRIMARY KEY (id);--.go
ALTER TABLE configuracaoNivelCompetenciaFaixaSalarial ADD CONSTRAINT configuracaoNivelCompetenciaFaixaSalarial_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);--.go
CREATE SEQUENCE configuracaoNivelCompetenciaFaixaSalarial_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE UNIQUE INDEX configuracaoNivelCompetenciaFaixaSalarial_data_faixasalarial_uk ON configuracaoNivelCompetenciaFaixaSalarial(data,faixasalarial_id); --.go

ALTER TABLE configuracaoNivelCompetencia ADD COLUMN configuracaoNivelCompetenciaFaixaSalarial_id BIGINT;--.go

ALTER TABLE configuracaoNivelCompetencia ADD CONSTRAINT configNivelCompetencia_configNivelCompetenciaFaixasalarial_fk FOREIGN KEY (configuracaoNivelCompetenciaFaixaSalarial_id) REFERENCES configuracaoNivelCompetenciaFaixaSalarial(id);--.go