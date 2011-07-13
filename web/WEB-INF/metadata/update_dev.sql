update parametrosdosistema set appversao = '1.1.50.42';--.go

alter table areaorganizacional add column emailsnotificacoes text;--.go
update papel set papelmae_id = 359 where id=510;--.go