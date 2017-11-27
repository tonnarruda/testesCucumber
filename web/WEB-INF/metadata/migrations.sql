CREATE OR REPLACE FUNCTION insertCandidatoSolicitacaoInHistoricoColaborador() RETURNS void AS $$ 
DECLARE  
mv RECORD;  
BEGIN 
	FOR mv IN  select distinct cs.id as candidatoSolicitacao_id, hc.id as historico_id
		from colaborador c
		join historicocolaborador hc on hc.colaborador_id = c.id
		join candidatosolicitacao cs on cs.candidato_id = c.candidato_id
		join solicitacao s on s.id = cs.solicitacao_id

		where cs.status = 'P' or cs.status = 'C' and datacontratacaoorpromocao is not null
		and hc.candidatosolicitacao_id is null

		and hc.data = (select min(hc2.data) from historicocolaborador hc2 
					where hc2.data >= s.data 
					and hc2.estabelecimento_id = s.estabelecimento_id   
					and hc2.areaorganizacional_id = s.areaorganizacional_id  
					and hc2.faixasalarial_id = s.faixasalarial_id
					and hc2.colaborador_id = c.id 
				)
		and hc.estabelecimento_id = s.estabelecimento_id
		and hc.areaorganizacional_id = s.areaorganizacional_id  
		and hc.faixasalarial_id = s.faixasalarial_id
		order by hc.id
	LOOP  
		UPDATE historicocolaborador hc set candidatosolicitacao_id = mv.candidatoSolicitacao_id where hc.id = mv.historico_id; 
	END LOOP;  
END;  
$$ LANGUAGE plpgsql;--.go
select insertCandidatoSolicitacaoInHistoricoColaborador();--.go
drop FUNCTION insertCandidatoSolicitacaoInHistoricoColaborador();--.go

