INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (546, 'ROLE_COLAB_LIST_EDITAR', 'Editar', '#', 4, false, 8);--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 546);--.go
alter sequence papel_sequence restart with 547;--.go

update papel set codigo='ROLE_COLAB_LIST' where id=8;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (547, 'ROLE_CAD_COLABORADOR', 'Outros acessos', '#', 20, false, 8);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (548, 'ROLE_COLAB_LIST_EXCLUIR', 'Excluir', '#', 5, false, 8);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (549, 'ROLE_COLAB_LIST_ENTREVISTA', 'Entrevista de desligamento', '#', 6, false, 8);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (549, 'ROLE_COLAB_LIST_ENTREVISTA', 'Entrevista de desligamento', '#', 6, false, 8);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (550, 'ROLE_COLAB_LIST_DESLIGAR', 'Desligar', '#', 8, false, 8);--.go




alter sequence papel_sequence restart with 550;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=545 WHERE atualizaPapeisIdsAPartirDe is null;--.go