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