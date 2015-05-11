delete from experiencia 
where id not in (select min(id) from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, colaborador_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and empresa in (select empresa from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, colaborador_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and (colaborador_id in (select colaborador_id from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, colaborador_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1)  
or colaborador_id is null ) 
and candidato_id in (select candidato_id from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, colaborador_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and dataadmissao in (select dataadmissao from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, colaborador_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and nomemercado in (select nomemercado from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, colaborador_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1);--.go

delete from experiencia 
WHERE id not in (select min(id) from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and empresa in (select empresa from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and candidato_id in (select candidato_id from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and dataadmissao in (select dataadmissao from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id,  cargo_id, salario, motivosaida HAVING count(empresa) > 1) 
and nomemercado in (select nomemercado from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, cargo_id, salario, motivosaida HAVING count(empresa) > 1);--.go