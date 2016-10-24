
update candidatosolicitacao set status = 'C' where id in(
	select cs.id from colaborador c 
	join candidatosolicitacao cs on cs.solicitacao_id = c.solicitacao_id and cs.candidato_id = c.candidato_id 
	where c.solicitacao_id is not null and cs.solicitacao_id = c.solicitacao_id and c.desligado = false and cs.status != 'C'
); --.go