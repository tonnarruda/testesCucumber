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

CREATE FUNCTION criar_historico_cncFaixa() RETURNS integer AS $$ 
	DECLARE
	    mv RECORD;
	    v_id BIGINT;
	BEGIN 
	    FOR mv IN 
			SELECT distinct(faixasalarial_id) FROM configuracaonivelcompetencia  
			WHERE faixasalarial_id IS NOT NULL AND configuracaonivelcompetenciacolaborador_id IS NULL AND candidato_id IS NULL ORDER BY 1 
			LOOP 
				v_id := nextval('configuracaoNivelCompetenciaFaixaSalarial_sequence'); 
				INSERT INTO configuracaoNivelCompetenciaFaixaSalarial values (v_id, mv.faixaSalarial_id, coalesce((SELECT min(data) FROM faixasalarialhistorico fsh WHERE mv.faixasalarial_id = fsh.faixasalarial_id), '2005-01-01'::date)); 
				UPDATE configuracaoNivelCompetencia set configuracaoNivelCompetenciaFaixaSalarial_id = v_id WHERE faixasalarial_id = mv.faixasalarial_id AND configuracaonivelcompetenciacolaborador_id IS NULL AND candidato_id IS NULL; 
			END LOOP; 
	    RETURN 1; 
	END; 
$$ LANGUAGE plpgsql;--.go
SELECT criar_historico_cncFaixa();--.go
DROP FUNCTION criar_historico_cncFaixa();--.go