update parametrosdosistema set appversao = '1.1.32.21';--.go

alter table configuracaocampoextra add column posicao Integer; --.go

update configuracaocampoextra set posicao = 1 where id = 1; --.go
update configuracaocampoextra set posicao = 2 where id = 2; --.go
update configuracaocampoextra set posicao = 3 where id = 3; --.go
update configuracaocampoextra set posicao = 11 where id = 4; --.go
update configuracaocampoextra set posicao = 12 where id = 5; --.go
update configuracaocampoextra set posicao = 13 where id = 6; --.go
update configuracaocampoextra set posicao = 14 where id = 7; --.go
update configuracaocampoextra set posicao = 15 where id = 8; --.go
update configuracaocampoextra set posicao = 16 where id = 9; --.go

insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (10,'f','texto4','Campo de Texto 4', 1,'texto',4);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (11,'f','texto5','Campo de Texto 5', 1,'texto',5);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (12,'f','texto6','Campo de Texto 6', 1,'texto',6);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (13,'f','texto7','Campo de Texto 7', 1,'texto',7);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (14,'f','texto8','Campo de Texto 8', 1,'texto',8);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (15,'f','texto9','Campo de Texto 9', 1,'texto',9);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (16,'f','texto10','Campo de Texto 10', 1,'texto',10);--.go

ALTER TABLE camposExtras ADD COLUMN texto4 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto5 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto6 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto7 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto8 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto9 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto10 character varying(250);--.go
