delete from experiencia
where id not in (select max(id) from experiencia group by empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, cargo_id, salario, motivosaida)