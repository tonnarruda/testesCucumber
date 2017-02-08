CREATE OR REPLACE FUNCTION insertCandidatoSolicitacaoInHistoricoColaborador() RETURNS void AS $$ 
DECLARE  
mv RECORD;  
BEGIN  
	IF '20170201083928' NOT IN (SELECT name FROM migrations) THEN
		FOR mv IN  
		select sub.id as candidatoSolicitacao_id,   
		case  
		when sub.status = 'C' then (select hc.id from historicocolaborador hc where hc.motivo = 'C' and hc.colaborador_id = sub.colab_id) 
		when sub.status = 'P' then (with hist_promocao as ( select hc.id  as hist_id from historicocolaborador hc  
															where hc.data = (select min(hc2.data) from historicocolaborador hc2  
																				where hc2.data >= sub.data 
																				and hc2.estabelecimento_id = sub.estabelecimento_id  
																			    and hc2.areaorganizacional_id = sub.areaorganizacional_id  
																			    and hc2.colaborador_id = sub.colab_id 
																		    ) 
															and hc.estabelecimento_id = sub.estabelecimento_id 
															and hc.areaorganizacional_id = sub.areaorganizacional_id  
															and hc.colaborador_id = sub.colab_id 
															) 
									select case when (select exists (select hist_promocao.hist_id from hist_promocao) as promocao )  
									then (select hist_promocao.hist_id from hist_promocao) 
									else (with hist_promocao2 as( select hc.id as hist_id from historicocolaborador as hc  
																	where hc.data = (select min(hc2.data) from historicocolaborador hc2  
																						where hc2.data >= sub.data  
																						and hc2.colaborador_id = sub.colab_id 
																						and hc2.motivo = 'P' 
																		 			) 
																	and hc.colaborador_id = sub.colab_id 
																	and hc.motivo = 'P' 										
												     			) 
											select case when(select exists (select hist_promocao2.hist_id from hist_promocao2) as promocao2) 
											then (select hist_promocao2.hist_id from hist_promocao2) 
											else (select hc.id from historicocolaborador hc  
													where hc.motivo = 'P'  
													and hc.colaborador_id = sub.colab_id 
													and hc.data = (select min(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = sub.colab_id and hc2.motivo = 'P') 
												) 
											end 
										) 
									end 
					  				) 
		when sub.status = 'I' or sub.status = 'A' then (with historico_id as (	select hc.id as id from historicocolaborador hc  
																				where hc.data = (select min(hc2.data) from historicocolaborador hc2  
																									where hc2.data >= sub.data  
																									and hc2.estabelecimento_id = sub.estabelecimento_id  
																									and hc2.areaorganizacional_id = sub.areaorganizacional_id  
																									and hc2.colaborador_id = hc.colaborador_id 
																								 ) 
																				and hc.estabelecimento_id = sub.estabelecimento_id 
																				and hc.areaorganizacional_id = sub.areaorganizacional_id  
																				and hc.colaborador_id = sub.colab_id 
																			 )  
														select case when ( select exists (select  historico_id.id from historico_id) as t) 
														then (	select historico_id.id from historico_id) 
														else ( 	with historico_id2 as(select hc.id as id2 from historicocolaborador as hc  
																					  where hc.data = (	select min(hc2.data) from historicocolaborador hc2  
																										where hc2.data >= sub.data  
																										and hc2.colaborador_id = sub.colab_id 
																							    	  ) 
																						and hc.colaborador_id = sub.colab_id 
																	     			) 
																select case when (select exists (select historico_id2.id2 from historico_id2) as historico) 
														       	then (select historico_id2.id2 from historico_id2) 
														       	else (select hc4.id from historicocolaborador hc4 where hc4.motivo = 'C' and hc4.colaborador_id = sub.colab_id) 
														       	end 
															) 
														end  
					       								) 	
		end as historico_id 
		from (	select c.solicitacao_id as sol_id, c.candidato_id as cand_id, c.id as colab_id, cs.status, cs.id,  
				s.data, s.estabelecimento_id, s.areaorganizacional_id  
				from colaborador c 
				join candidatosolicitacao cs on cs.candidato_id = c.candidato_id and cs.solicitacao_id = c.solicitacao_id 
				join solicitacao s on s.id = cs.solicitacao_id 
		 	 ) as sub 
		order by sub.sol_id 
		LOOP  
			UPDATE historicocolaborador hc set candidatosolicitacao_id = mv.candidatoSolicitacao_id where hc.id = mv.historico_id; 
		END LOOP;  
	ELSE
		RAISE NOTICE 'Já possui migrate 20170201083928';
	END IF;
END;  
$$ LANGUAGE plpgsql;--.go
select insertCandidatoSolicitacaoInHistoricoColaborador();--.go
drop FUNCTION insertCandidatoSolicitacaoInHistoricoColaborador();--.go

ALTER TABLE colaborador DROP COLUMN IF EXISTS solicitacao_id; --.go