DELETE FROM usuariomensagem where mensagem_id in(SELECT id from mensagem  where link like '%colaborador.id=%' 
and (cast(substring(link,'colaborador.id=([0-9]{1,9})')as integer)) in (select id from colaborador  where desligado = true));--.go

delete from mensagem  where link like '%colaborador.id=%' and (cast(substring(link,'colaborador.id=([0-9]{1,9})')as integer)) in (select id from colaborador  where desligado = true);--.go