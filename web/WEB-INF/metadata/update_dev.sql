update parametrosdosistema set appversao = '1.1.43.34';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (504, 'ROLE_INFO_PAINEL_IND', 'Painel', '/cargosalario/historicoColaborador/painelIndicadores.action', 3, true, 462);--.go
alter sequence papel_sequence restart with 505;--.go

update papel set nome = 'Acompanhamento do Período de Experiência' where id=490;--.go
update papel set nome = 'Painel de Indicadores', papelmae_id=373 where id=504;--.go
update papel set papelmae_id=377,ordem=7 where id=398;--.go
update papel set papelmae_id=463,ordem=6 where id=70;--.go
delete from perfil_papel where papeis_id=462;--.go
delete from papel where id=462;--go
