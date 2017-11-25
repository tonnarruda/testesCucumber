
ALTER TABLE historicoAmbiente ADD COLUMN nomeAmbiente character varying(100);--.go
ALTER TABLE historicoAmbiente ADD COLUMN cnpjEstabelecimentoDeTerceiros character varying(14);--.go

ALTER TABLE historicoAmbiente ADD COLUMN estabelecimento_id bigint; --.go
ALTER TABLE ONLY historicoAmbiente ADD CONSTRAINT historicoAmbiente_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);--.go

ALTER TABLE historicoAmbiente ADD COLUMN localAmbiente integer default 1; --.go

CREATE OR REPLACE FUNCTION insert_nomeAmbiente_e_estabelecimento_em_historicoAmbiente() RETURNS void AS $$   
DECLARE  
mv RECORD;  
BEGIN  
	FOR mv IN  
		select id,nome,estabelecimento_id from ambiente
	LOOP 	 
		update historicoambiente set nomeAmbiente = mv.nome, estabelecimento_id = mv.estabelecimento_id where ambiente_id = mv.id;
	END LOOP;  
END;  
$$ LANGUAGE plpgsql;--.go
SELECT insert_nomeAmbiente_e_estabelecimento_em_historicoAmbiente();--.go
DROP FUNCTION insert_nomeAmbiente_e_estabelecimento_em_historicoAmbiente();--.go

ALTER TABLE ambiente DROP COLUMN estabelecimento_id; --.go