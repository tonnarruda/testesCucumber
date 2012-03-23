update gerenciadorcomunicacao set enviarpara = 1 where operacao=17 and meiocomunicacao=1 and enviarpara=16;--.go

insert into gerenciadorcomunicacao_usuario
 (select g.id, ue.usuario_id from usuarioempresa ue
 join perfil_papel pp on pp.perfil_id=ue.perfil_id
 left join gerenciadorcomunicacao g on g.empresa_id = ue.empresa_id and g.operacao=17 and g.meiocomunicacao=1 and g.enviarpara=1
 where pp.papeis_id=478 and g.id is not null
 order by g.id, ue.usuario_id);--.go