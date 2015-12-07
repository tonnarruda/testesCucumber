update papel set ordem = ordem + 1 where papelmae_id = 377 and ordem > 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (647, 'ROLE_REL_RECIBO_DECIMO_TERCEIRO', 'Recibo de 13º salário', '/geral/colaborador/prepareReciboDeDecimoTerceiro.action', 2, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 647);--.go
alter sequence papel_sequence restart with 648;--.go

update parametrosdosistema set acversaowebservicecompativel = '1.1.58.1' where id = 1;--.go