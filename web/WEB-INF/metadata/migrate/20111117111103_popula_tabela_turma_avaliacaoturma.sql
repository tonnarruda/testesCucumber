INSERT INTO turma_avaliacaoturma (turma_id, avaliacaoturmas_id) 
SELECT 
t.id, 
t.avaliacaoturma_id 
FROM turma t
WHERE t.avaliacaoturma_id is not null;--.go