ALTER TABLE funcao ADD COLUMN empresa_id bigint; --.go
ALTER TABLE ONLY funcao ADD CONSTRAINT funcao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
ALTER TABLE funcao add COLUMN codigocbo character varying(6);--.go

update papel set url = '/sesmt/funcao/list.action' where id = 476; --.go
delete from perfil_papel where papeis_id in(66, 640);--.go
delete from papel where id in(66, 640);--.go

CREATE OR REPLACE FUNCTION insert_empresa_em_funcao() RETURNS void AS $$   
DECLARE  
mv RECORD;  
BEGIN  
	FOR mv IN  
		select  id as cargoId, empresa_id as empresaId from cargo
	LOOP 	 
		update funcao set empresa_id = mv.empresaId where cargo_id = mv.cargoId;
	END LOOP;  
END;  
$$ LANGUAGE plpgsql;--.go
SELECT insert_empresa_em_funcao();--.go
DROP FUNCTION insert_empresa_em_funcao();--.go

ALTER TABLE ordemdeservico RENAME column codigocbo TO codigocbocargo;--.go
ALTER TABLE ordemdeservico ADD COLUMN codigocbofuncao character varying(6);--.go
ALTER TABLE ordemdeservico ALTER COLUMN nomeCargo TYPE character varying(100);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (800, 'ROLE_RELACIONADOR_FUNCAO_FP', 'Relacionar Função com Fortes Pessoal', '/sesmt/funcao/prepareRelacionarFuncaoFP.action', 19, true, 37);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 800);--.go

ALTER SEQUENCE papel_sequence RESTART WITH 801;--.go

ALTER TABLE funcao ADD COLUMN codigoac character varying(12);--.go
ALTER TABLE funcao ADD CONSTRAINT unique_empresaId_codigoac UNIQUE(empresa_id,codigoac);--.go