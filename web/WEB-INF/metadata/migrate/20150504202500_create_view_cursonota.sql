create view View_CursoNota as 
select 
	ct.id as colaboradorturma_id,
	ct.colaborador_id as colaborador_id, 
	ct.turma_id as turma_id, 
	count(ct.id) as qtdavaliacoescurso, 
	sum( cast((( (case when ac.tipo = 'a' then (cq.performance * 100) else aac.valor end) >= ac.minimoaprovacao) or ac.minimoaprovacao is null) as int)  ) as qtdavaliacoesaprovadaspornota, 
	sum( case when ac.tipo = 'a' then (cq.performance * 100) else aac.valor end ) as nota 
from 
	avaliacaocurso ac 
cross join 
	colaboradorturma ct 
left join 
	aproveitamentoavaliacaocurso aac on ac.id = aac.avaliacaocurso_id and ct.id = aac.colaboradorturma_id 
left join 
	colaboradorquestionario cq on ac.id = cq.avaliacaocurso_id and ac.avaliacao_id = cq.avaliacao_id and ct.colaborador_id = cq.colaborador_id 
where (ct.id = aac.colaboradorturma_id or (ct.colaborador_id = cq.colaborador_id and ct.turma_id = cq.turma_id)) 
group by ct.id, ct.colaborador_id, ct.turma_id
order by ct.id; --.go