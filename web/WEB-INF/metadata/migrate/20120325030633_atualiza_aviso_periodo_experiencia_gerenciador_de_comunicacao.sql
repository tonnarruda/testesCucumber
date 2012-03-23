update gerenciadorcomunicacao set enviarpara = 1 where operacao=10 and meiocomunicacao=2 and enviarpara=9;--.go

insert into gerenciadorcomunicacao_usuario
 (select g.id, ue.usuario_id from usuarioempresa ue
 join perfil_papel pp on pp.perfil_id=ue.perfil_id
 left join gerenciadorcomunicacao g on g.empresa_id = ue.empresa_id and g.operacao=10 and g.meiocomunicacao=2 and g.enviarpara=1
 where pp.papeis_id=497 and g.id is not null
 order by g.id, ue.usuario_id);--.go

delete from perfil_papel where papeis_id in (497);--.go
delete from papel where id in (497);--.go