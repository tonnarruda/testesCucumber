
update cat 
set   funcaocolaborador_id = hc.funcao_id, ambientecolaborador_id = hc.ambiente_id
from  colaborador c
inner join historicocolaborador hc on hc.colaborador_id = c.id
where cat.colaborador_id = c.id
and   hc.data = (select hc2.data 
                   from historicocolaborador hc2 
                  where hc2.data <= cat.data 
                    and hc2.colaborador_id = c.id 
               order by hc2.data desc 
                  limit 1);--.go