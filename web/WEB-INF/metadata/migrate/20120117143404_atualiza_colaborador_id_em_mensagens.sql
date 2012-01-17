update mensagem 
	set colaborador_id = cast(substring(substring(link from 'colaborador.id=[0-9]*') , 16) as bigint)
where 
	colaborador_id is null 
	and link ~ 'colaborador.id';--.go