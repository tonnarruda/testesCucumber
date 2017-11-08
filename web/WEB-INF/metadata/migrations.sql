alter table parametrosdosistema add column transfereExamesCandidatoColaborador boolean DEFAULT false;--.go
CREATE OR REPLACE FUNCTION atualiza_sol_exames_desligados() RETURNS void AS $$     
	DECLARE 
		mv record; 
	BEGIN  
		FOR mv IN 
			select cpf, id, datadesligamento from colaborador 
			where cpf in (select c.cpf from colaborador c where c.datadesligamento is not null and c.cpf is not null group by c.cpf, c.empresa_id having count(*) > 1)
			and datadesligamento is not null 
			order by cpf, datadesligamento 
		LOOP 
			update solicitacaoexame set colaborador_id = mv.id where id in (
				select se.id from solicitacaoexame se
				inner join candidato c on c.id = se.candidato_id
				inner join colaborador co on co.candidato_id = c.id
				where se.data <= mv.datadesligamento
				and c.cpf = mv.cpf
				and se.data >= coalesce((select max(co2.datadesligamento) from colaborador co2 where co2.cpf = c.cpf and co2.datadesligamento < mv.datadesligamento),'1-1-1900')
			);
		END LOOP; 
	
	END;   
$$ LANGUAGE plpgsql;--.go

CREATE OR REPLACE FUNCTION transfereSolicitacaoExameCandidatoColaborador() RETURNS void AS $$   
DECLARE 
mv RECORD; 

BEGIN  
	execute('select atualiza_sol_exames_desligados()');
     FOR mv IN  
	select se.id as solicitacao_id,co.id as colaborador_id from solicitacaoexame se 
		inner join candidato ca on 
		ca.id=se.candidato_id
		inner join colaborador co on
		co.candidato_id=se.candidato_id

		where se.colaborador_id is null 
		and se.data >= (coalesce((select max(dataDesligamento) from colaborador co2 where co2.cpf = co.cpf),'01-01-1900'))
	LOOP

	update solicitacaoexame set colaborador_id=mv.colaborador_id where id = mv.solicitacao_id;
	
	END LOOP;
END;  
$$ LANGUAGE plpgsql;--.go 
select transfereSolicitacaoExameCandidatoColaborador();--.go 
drop function atualiza_sol_exames_desligados();--.go 
drop function transfereSolicitacaoExameCandidatoColaborador();--.go 