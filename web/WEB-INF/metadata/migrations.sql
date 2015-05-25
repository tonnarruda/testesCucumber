create table configuracaoNivelCompetenciaFaixaSalarial 
(
  id bigint NOT NULL,
  faixasalarial_id bigint,
  data date
);--.go

ALTER TABLE configuracaoNivelCompetenciaFaixaSalarial ADD CONSTRAINT configuracaoNivelCompetenciaFaixaSalarial_pkey PRIMARY KEY (id);--.go
ALTER TABLE configuracaoNivelCompetenciaFaixaSalarial ADD CONSTRAINT configuracaoNivelCompetenciaFaixaSalarial_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);--.go
CREATE SEQUENCE configuracaoNivelCompetenciaFaixaSalarial_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

alter table configuracaoNivelCompetencia add column configuracaoNivelCompetenciaFaixaSalarial_id bigint;

ALTER TABLE configuracaoNivelCompetencia ADD CONSTRAINT configNivelCompetencia_configNivelCompetenciaFaixasalarial_fk FOREIGN KEY (configuracaoNivelCompetenciaFaixaSalarial_id) REFERENCES configuracaoNivelCompetenciaFaixaSalarial(id);--.go;--.go

CREATE FUNCTION criar_historico_cncFaixa() RETURNS integer AS $$
	DECLARE
	    mv RECORD;
	    v_id bigint;
	BEGIN
	    FOR mv IN
			SELECT distinct(faixasalarial_id) from configuracaonivelcompetencia 
			where faixasalarial_id is not null and configuracaonivelcompetenciacolaborador_id is null and candidato_id is null order by 1
			LOOPR
				v_id := nextvaRl('configuracaoNivelCompetenciaFaixaSalarial_sequence');
				insert into configuracaoNivelCompetenciaFaixaSalarial values (v_id, mv.faixaSalarial_id, coalesce((select min(data) from faixasalarialhistorico fsh where mv.faixasalarial_id = fsh.faixasalarial_id), '2005-01-01'::date));
				update configuracaoNivelCompetencia set configuracaoNivelCompetenciaFaixaSalarial_id = v_id where faixasalarial_id = mv.faixasalarial_id and configuracaonivelcompetenciacolaborador_id is null and candidato_id is null;
			END LOOP;
	    RETURN 1;
	END;
$$ LANGUAGE plpgsql;--.go
select criar_historico_cncFaixa();--.go
drop function criar_historico_cncFaixa();--.go



