update areaorganizacional set ativo = true where id in (select distinct(areamae_id) from areaorganizacional ao where exists(select id from areaorganizacional ao2 where ao.areamae_id = ao2.id) and ao.ativo = true);--.go