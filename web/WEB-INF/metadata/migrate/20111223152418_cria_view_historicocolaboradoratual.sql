create view historicocolaboradoratual as 
select hc.* 
from historicocolaborador hc 
where hc.data = coalesce(
		(select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = hc.colaborador_id and hc2.status=hc.status and hc2.data <= current_date),
		(select min(hc3.data) from historicocolaborador hc3 where hc3.colaborador_id = hc.colaborador_id and hc3.status=hc.status and hc3.data > current_date));--.go