update papel set ordem = 1  where id=77;--.go
update papel set ordem = 3  where id=78;--.go
update papel set ordem = 4  where id=86;--.go
update papel set ordem = 5  where id=79;--.go
update papel set ordem = 6  where id=76;--.go
update papel set ordem = 7  where id=89;--.go
update papel set ordem = 8  where id=445;--.go
update papel set ordem = 9  where id=455;--.go
update papel set ordem = 10 where id=456;--.go
update papel set ordem = 11 where id=605;--.go
update papel set ordem = 12 where id=87;--.go
update papel set ordem = 13 where id=90;--.go
update papel set ordem = 14 where id=91;--.go
update papel set ordem = 15 where id=427;--.go
update papel set ordem = 16 where id=440;--.go
update papel set ordem = 17 where id=530;--.go
update papel set ordem = 18 where id=476;--.go
update papel set ordem = 19 where id=532;--.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (628, 'ROLE_CAD_MOTIVO_SOLICITACAO_EPI', 'Motivos de Solicitação de EPI', '/sesmt/motivoSolicitacaoEpi/list.action', 2, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 628);--.go
alter sequence papel_sequence restart with 629;--.go