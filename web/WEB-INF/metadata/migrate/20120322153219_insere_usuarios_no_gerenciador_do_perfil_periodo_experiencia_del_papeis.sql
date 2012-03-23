insert into gerenciadorcomunicacao_usuario
 (select g.id, ue.usuario_id from usuarioempresa ue
 join perfil_papel pp on pp.perfil_id=ue.perfil_id
 left join gerenciadorcomunicacao g on g.empresa_id = ue.empresa_id and g.operacao=9 and g.meiocomunicacao=1 and g.enviarpara=1
 where pp.papeis_id=518 and g.id is not null
 order by g.id, ue.usuario_id);--.go

delete from perfil_papel where papeis_id in (517,518);--.go
delete from papel where id in (517,518);--.go

