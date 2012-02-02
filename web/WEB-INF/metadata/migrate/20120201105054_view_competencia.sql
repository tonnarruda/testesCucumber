create view competencia as 
select id, nome, empresa_id, observacao, 'C' as tipo from conhecimento union 
select id, nome, empresa_id, observacao, 'H' as tipo from habilidade union 
select id, nome, empresa_id, observacao, 'A' as tipo from atitude 
order by id;--.go