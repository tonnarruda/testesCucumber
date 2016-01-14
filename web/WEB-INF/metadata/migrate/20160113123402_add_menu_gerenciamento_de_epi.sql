INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (660, 'ROLE_GERENCIAMENTO_EPI', 'Gerenciamento de EPIs', '/sesmt/solicitacaoEpi/list.action', 2, true, 386);--.go

INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT distinct perfil_id, 660 FROM perfil_papel where papeis_id in(433,435);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (661, 'ROLE_GERENCIAMENTO_EPI_EDITAR_SOLICITACAO', 'Editar solicitação', '#', 2, false, 660);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (662, 'ROLE_GERENCIAMENTO_EPI_EXCLUIR_SOLICITACAO', 'Excluir solicitação', '#', 3, false, 660);--.go

update papel set papelmae_id = 660, menu = false, url='#', ordem = 1, nome = 'Inserir solicitação' where id = 433;--.go
update papel set papelmae_id = 660, menu = false, url='#', ordem = 4, nome = 'Entregar/Devolver' where id = 435;--.go

INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT distinct perfil_id, 661 FROM perfil_papel where papeis_id in(433);--.go
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT distinct perfil_id, 662 FROM perfil_papel where papeis_id in(433);--.go

alter sequence papel_sequence restart with 663;--.go
