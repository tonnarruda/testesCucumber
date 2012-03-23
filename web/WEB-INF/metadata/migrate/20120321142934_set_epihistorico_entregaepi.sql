update solicitacaoepiitementrega ent set epihistorico_id = (
select eh.id from solicitacaoepi_item item 
inner join epihistorico eh on eh.epi_id = item.epi_id
where item.id=ent.solicitacaoepiitem_id  
and eh.data = (select max(data) from epihistorico eh2 where eh2.epi_id = eh.epi_id limit 1)
);--.go