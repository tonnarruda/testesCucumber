update parametrosdosistema set appversao = '1.1.50.42';--.go

alter table areaorganizacional add column emailsnotificacoes text;--.go

create table migrations (name varchar(14) not null);--.go

update papel set papelmae_id = 359 where id=510;--.go

update empresa set mensagemmoduloexterno='Se você não é registrado, cadastre já seu currículo e tenha acesso às vagas disponíveis em nossa empresa.' where mensagemmoduloexterno is null or trim(mensagemmoduloexterno)='';--.go
