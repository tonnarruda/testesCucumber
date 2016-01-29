INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (665, 'ROLE_REL_EPI_DEVOLVIDO', 'EPIs Devolvidos', '/sesmt/solicitacaoEpi/prepareRelatorioDevolucaoEpi.action', 4, true, 657);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 665);--.go

update papel set ordem = 5 where id = 432;--.go

alter sequence papel_sequence restart with 666;--.go