alter table solicitacaoepi add column situacaosolicitacaoepi character(1) not null default 'A';--.go

update solicitacaoepi se set situacaosolicitacaoepi = (select case when sum(sei.qtdentregue) = sum(sei.qtdsolicitado) then 'E' when sum(sei.qtdentregue) > 0 then 'P' else 'A' end  from solicitacaoepi_item sei where sei.solicitacaoepi_id = se.id group by sei.solicitacaoepi_id );--.go

alter table solicitacaoepi drop column entregue;--.go