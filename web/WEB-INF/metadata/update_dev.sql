update parametrosdosistema set appversao = '1.1.38.28';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (495, 'ROLE_VISUALIZAR_MSG', 'Visualizar Caixa de Mensagens', '#', 1, false, null);--.go

update papel set ordem=2,papelmae_id=null where id=411;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (496, 'ROLE_VISUALIZAR_SOLICITACAO_PESSOAL', 'Visualizar Solicitação de Pessoal', '#', 14, false, 357);--.go
alter sequence papel_sequence restart with 497;--.go

update candidato set disponivel = false where blacklist = true;--.go