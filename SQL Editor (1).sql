select hc.data,* 
from historicocolaborador hc 
inner join indice i on i.id = hc.indice_id
left join indicehistorico ih on ih.indice_id = i.id
where hc.indice_id = 4630 
and hc.tiposalario = 2
--and ih.data = (select max(ih2.data) from IndiceHistorico ih2 where ih2.indice_id = i.id and ih2.data <= '2010-03-12' and ih2.data != '2010-03-12')
--and hc.data < '2002-04-01'
and hc.colaborador_id = 176280

select hc.data,* 
from historicocolaborador hc 
where hc.indice_id = 4630 
and hc.tiposalario = 2
and hc.data < '2012-03-12'
and hc.colaborador_id = 176280
order by data

--select * from indicehistorico ih 
--select * from historicocolaborador where id = 210459 
--update historicocolaborador set colaborador_id = 176265  where id = 210459
select * from indicehistorico ih 
where ih.indice_id = 4630
order by data

select data from indicehistorico ih where ih.indice_id = 4630 order by data limit 2 
select min(data) from indicehistorico ih where ih.indice_id = 4630 
select min(data) from indicehistorico ih where ih.indice_id = 4630 and data > (select min(data) from indicehistorico ih2 where ih2.indice_id = 4630 )

2001-04-01 2002-04-01

