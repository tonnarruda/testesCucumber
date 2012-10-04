update mensagem set tipo = 'F' where tipo = 'E';--.go
update mensagem set tipo = 'A' where tipo = 'I';--.go
delete from usuariomensagem where mensagem_id in (select id from mensagem where tipo not in ('C', 'P', 'A', 'T', 'F', 'S', 'U', 'D'));--.go
delete from mensagem where tipo not in ('C', 'P', 'A', 'T', 'F', 'S', 'U', 'D');--.go