INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (668,'ROLE_COLABORADOR_AVALIACAO_PRATICA', 'Avaliação Prática', '#', 6, true, 367);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 668);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (669,'ROLE_COLABORADOR_AVALIACAO_PRATICA', 'Notas em Lote', '/desenvolvimento/colaboradorAvaliacaoPratica/prepareLote.action', 2, true, 668);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 669);--.go

update papel set nome = 'Notas',ordem = 1,papelmae_id = 668  where id = 654;--.go

alter sequence papel_sequence restart with 670; --.go