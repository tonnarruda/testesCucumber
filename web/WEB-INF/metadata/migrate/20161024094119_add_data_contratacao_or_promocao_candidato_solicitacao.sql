
ALTER TABLE solicitacao ADD COLUMN dataprevisaoencerramento DATE DEFAULT NULL;--.go
ALTER TABLE candidatosolicitacao ADD COLUMN dataContratacaoOrPromocao DATE DEFAULT NULL;--.go

CREATE OR REPLACE FUNCTION insertDatacontratacaoorpromocao() RETURNS integer AS $$
	DECLARE 
	mv RECORD; 
BEGIN 
	FOR mv IN 
		select case when cs.status = 'C' then c.dataAdmissao else (
					select min(hc.data) from historicocolaborador hc where
						hc.estabelecimento_id = s.estabelecimento_id and hc.areaorganizacional_id = s.areaorganizacional_id
						and hc.data >= s.data
					) end as data, cs.id  as candidatosolicitacaoId
			from candidatosolicitacao cs join solicitacao s on s.id = cs.solicitacao_id join colaborador c on cs.candidato_id = c.candidato_id where cs.status = 'C' or cs.status = 'P'
	LOOP 
		UPDATE candidatosolicitacao SET datacontratacaoorpromocao = mv.data WHERE id = mv.candidatosolicitacaoId;
	END LOOP; 

	RETURN 1; 
END; 
$$ LANGUAGE plpgsql;--.go

select insertDataContratacaoOrPromocao();--.go
drop FUNCTION insertDataContratacaoOrPromocao();--.go