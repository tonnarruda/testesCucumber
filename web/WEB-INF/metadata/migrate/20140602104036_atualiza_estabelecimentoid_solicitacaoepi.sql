update solicitacaoepi set estabelecimento_id = hc.estabelecimento_id
 from historicocolaborador  hc
 where solicitacaoepi.colaborador_id = hc.colaborador_id
 and hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = solicitacaoepi.colaborador_id and hc2.data <= solicitacaoepi.data); --.go

update solicitacaoepi set estabelecimento_id = hc.estabelecimento_id
 from historicocolaborador  hc
 where solicitacaoepi.colaborador_id = hc.colaborador_id
 and hc.data = (select min(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = solicitacaoepi.colaborador_id)
 and solicitacaoepi.estabelecimento_id is null; --.go