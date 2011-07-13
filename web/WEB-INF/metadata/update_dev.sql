update parametrosdosistema set appversao = '1.1.50.42';--.go

alter table areaorganizacional add column emailsnotificacoes text;--.go

create table migrations (name varchar(14) not null);--.go