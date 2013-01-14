INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (567, 'ROLE_MOV_SOLICITACAOREAJUSTE_COLABORADOR', 'Colaborador', '/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action', 1, true, NULL, 49);--.go
insert into perfil_papel select perfil_id, 567 from perfil_papel where papeis_id = 49;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (568, 'ROLE_MOV_SOLICITACAOREAJUSTE_FAIXASALARIAL', 'Faixa Salarial', '/cargosalario/reajusteFaixaSalarial/prepareInsert.action', 2, true, NULL, 49);--.go
insert into perfil_papel select perfil_id, 568 from perfil_papel where papeis_id = 49;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (569, 'ROLE_MOV_SOLICITACAOREAJUSTE_INDICE', 'Índice', '/cargosalario/reajusteIndice/prepareInsert.action', 3, true, NULL, 49);--.go
insert into perfil_papel select perfil_id, 569 from perfil_papel where papeis_id = 49;--.go

alter sequence papel_sequence restart with 570;--.go

update papel set nome = 'Solicitação de Realinhamento de C&S', url = '#' where id = 49;--.go