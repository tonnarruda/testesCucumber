delete from comissaoreuniaopresenca where id in (
	select crp.id 
	from comissaoreuniao cr, comissaoperiodo cp, comissao c, comissaoreuniaopresenca crp 
	where cp.comissao_id = c.id 
		and cr.comissao_id = c.id 
		and cr.data >= cp.apartirde 
		and crp.comissaoreuniao_id = cr.id
		and cr.data < coalesce((select min(apartirde) from comissaoperiodo where comissao_id = c.id and apartirde > cp.apartirde), c.dataFim) 
		and cp.id not in (select comissaoperiodo_id from comissaomembro where colaborador_id = crp.colaborador_id and comissaoperiodo_id = cp.id)
);--.go