
CREATE TABLE configuracaoNivelCompetenciaCandidato (
	id bigint NOT NULL,
	data date NOT NULL,
	candidato_id bigint,
	solicitacao_id bigint,
	configuracaoNivelCompetenciaFaixaSalarial_id bigint 
);--.go
    
ALTER TABLE configuracaoNivelCompetenciaCandidato ADD CONSTRAINT configuracaoNivelCompetenciaCandidato_pkey PRIMARY KEY(id);--.go
ALTER TABLE configuracaoNivelCompetenciaCandidato ADD CONSTRAINT candidato_id_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);--.go
ALTER TABLE configuracaoNivelCompetenciaCandidato ADD CONSTRAINT solicitacao_id_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);--.go
CREATE SEQUENCE configuracaoNivelCompetenciaCandidato_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE configuracaonivelcompetencia ADD COLUMN configuracaoNivelCompetenciaCandidato_id bigint;--.go
ALTER TABLE configuracaonivelcompetencia ADD CONSTRAINT configuracaonivelcompetencia_cncCandidato_id_fk FOREIGN KEY (configuracaoNivelCompetenciaCandidato_id) REFERENCES configuracaoNivelCompetenciaCandidato(id);--.go

CREATE FUNCTION create_configuracaoNivelCompetenciaCandidato () RETURNS integer AS $$
DECLARE
    data_solicitacao DATE;	
    mviews RECORD;
BEGIN
    FOR mviews IN 
    select distinct candidato_id as candidatoId, solicitacao_id as solicitacaoId, faixaSalarial_id as faixaSalarialId from configuracaonivelcompetencia where candidato_id is not null order by solicitacao_id
	LOOP
	     data_solicitacao := (select data from solicitacao where id = mviews.solicitacaoId); 		
	     insert into configuracaoNivelCompetenciaCandidato(id, data, candidato_id, solicitacao_id, configuracaoNivelCompetenciaFaixaSalarial_id) 
	     values ( nextval('configuracaoNivelCompetenciaCandidato_sequence'), data_solicitacao, mviews.candidatoId, mviews.solicitacaoId, 
		     (select cncf.id from configuracaonivelcompetenciafaixasalarial cncf where cncf.faixaSalarial_id = mviews.faixaSalarialId and cncf.data = 
																						(select max(cncf2.data) from configuracaonivelcompetenciafaixasalarial cncf2 
																							where cncf2.faixaSalarial_id = mviews.faixaSalarialId and cncf2.data <= data_solicitacao
																						)
			) 
		);
	END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
SELECT create_configuracaoNivelCompetenciaCandidato();--.go
DROP FUNCTION create_configuracaoNivelCompetenciaCandidato();--.go

CREATE FUNCTION inserir_configuracaoNivelCompetenciaCandidato_in_cnc () RETURNS integer AS $$
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN 
	select id, candidato_id as candidatoId, solicitacao_id as solicitacaoId from configuracaoNivelCompetenciaCandidato
    LOOP
	update configuracaoNivelCompetencia set configuracaoNivelCompetenciaCandidato_id = mviews.id where candidato_id = mviews.candidatoId and solicitacao_id = mviews.solicitacaoId;   
    END LOOP;
    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go
SELECT inserir_configuracaoNivelCompetenciaCandidato_in_cnc();--.go
DROP FUNCTION inserir_configuracaoNivelCompetenciaCandidato_in_cnc();--.go


alter table configuracaonivelcompetencia drop column candidato_id, drop column solicitacao_id;--.go    		

--LEMBRAR DE REMOVER A MIGRATE JÁ CRIADA E CRIAR NOVAMENTE QUANDO FOR LEVAR PARA O BRANCH DEV 
DELETE FROM gerenciadorcomunicacao WHERE operacao = 39;--.go
    		
    		