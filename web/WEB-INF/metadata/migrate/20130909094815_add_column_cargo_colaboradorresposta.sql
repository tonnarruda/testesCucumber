alter table colaboradorresposta add column cargo_id bigint;--.go
ALTER TABLE colaboradorresposta ADD CONSTRAINT colaboradorresposta_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go

update colaboradorresposta set cargo_id = cid from 
(select cq.id as cqid, fs.cargo_id as cid 
from colaboradorquestionario cq 
inner join questionario q on cq.questionario_id = q.id 
inner join colaborador co on cq.colaborador_id = co.id 
inner join historicocolaborador hc on hc.colaborador_id = co.id  
inner join faixasalarial fs on hc.faixasalarial_id = fs.id 
where hc.data = ( select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = co.id and hc2.status = 1 ) 
) as sub where colaboradorquestionario_id = cqid;--.go