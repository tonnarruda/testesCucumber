update parametrosdosistema set appversao = '1.1.38.28';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (495, 'ROLE_VISUALIZAR_MSG', 'Visualizar Caixa de Mensagens', '#', 1, false, null);--.go

update papel set ordem=2,papelmae_id=null where id=411;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (496, 'ROLE_VISUALIZAR_SOLICITACAO_PESSOAL', 'Visualizar Solicitação de Pessoal', '#', 14, false, 357);--.go
alter sequence papel_sequence restart with 497;--.go

update candidato set disponivel = false where blacklist = true;--.go

update papel set ordem = ordem + 1 where papelmae_id = 75;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (497, 'ROLE_RECEBE_EXAMES_PREVISTOS', 'Recebe email de exames previstos', '#', 1, false, 75);--.go
alter sequence parametrosdosistema_sequence restart with 498; --.go
