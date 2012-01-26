update solicitacao 
	set descricao = c.nome
from faixasalarial f, cargo c
where faixasalarial_id = f.id
	and f.cargo_id = c.id
	and descricao is null;--.go
