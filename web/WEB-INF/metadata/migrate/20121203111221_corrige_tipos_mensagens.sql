update mensagem set tipo = 'F' where tipo = 'E';
update mensagem set tipo = 'F' where tipo = 'D';
update mensagem set tipo = 'A' where tipo = 'I';
delete from usuariomensagem where mensagem_id in (select id from mensagem where tipo not in ('C', 'P', 'A', 'T', 'F', 'S', 'U', 'R') or tipo is null);
delete from mensagem where tipo not in ('C', 'P', 'A', 'T', 'F', 'S', 'U', 'R') or tipo is null;