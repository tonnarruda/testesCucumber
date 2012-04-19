create view SituacaoAvaliacaoTurma as 
select 
totais.id as turma_id, 
case 
when totais.qtdAvaliacoes = totais.qtdLiberada then 'L'  
when totais.qtdLiberada > 0 then 'P' 
else 'B' end as status   
from 
(select t.id, count(a.id) as qtdAvaliacoes, (select count(tat3.id) from turma_avaliacaoturma tat3 where tat3.turma_id = t.id and tat3.liberada = true) as qtdLiberada 
from turma t  
left join turma_avaliacaoturma tat on tat.turma_id = t.id  
left join avaliacaoturma a on tat.avaliacaoturma_id = a.id  
group by t.id) as totais;--.go