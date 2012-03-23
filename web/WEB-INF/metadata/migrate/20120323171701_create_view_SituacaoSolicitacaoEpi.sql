CREATE OR REPLACE VIEW SituacaoSolicitacaoEpi AS 
select sub.*, 
case  
	when sub.qtdSolicitado <= sub.qtdEntregue  then 'E' 
	when sub.qtdEntregue > 0 and sub.qtdEntregue < sub.qtdSolicitado  then 'P' 
	when sub.qtdEntregue = 0  then 'A' 
end as situacao 
from ( 
		select se.id, se.empresa_id as empresaId,est.id as estabelecimentoId, est.nome as estabelecimentoNome, c.matricula as colaboradorMatricula, c.nome as colaboradorNome, hc.status, se.data, ca.nome as cargoNome, (select sum(sei2.qtdSolicitado) from solicitacaoepi_item sei2 where sei2.solicitacaoepi_id = se.id) as qtdSolicitado, coalesce(sum(seie.qtdEntregue), 0) as qtdEntregue   
		from solicitacaoepi as se  
		left join solicitacaoepi_item as sei on sei.solicitacaoepi_id=se.id   
		left join solicitacaoepiitementrega seie on seie.solicitacaoepiitem_id=sei.id   
		left join colaborador as c on se.colaborador_id=c.id  
		left join historicocolaborador as hc on c.id=hc.colaborador_id   
		left join estabelecimento as est on hc.estabelecimento_id=est.id   
		left join cargo as ca on se.cargo_id=ca.id  
		where hc.data = (select max(hc2.data) from historicocolaborador as hc2 where hc2.colaborador_id = c.id) 
		group by se.id, se.empresa_id, est.id, est.nome, c.matricula, c.id, c.nome, hc.status, se.data, ca.id, ca.nome 
	) as sub;--.go 
 