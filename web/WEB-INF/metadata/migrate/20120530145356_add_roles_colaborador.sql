update papel set codigo='ROLE_COLAB_LIST' where id=8;--.go
delete from perfil_papel where papeis_id in (541,542);--.go
delete from papel where id in (541,542);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (546, 'ROLE_COLAB_LIST_DESLIGAR', 'Desligar', '#', 1, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (547, 'ROLE_COLAB_LIST_ENTREVISTA', 'Entrevista de desligamento', '#', 2, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (548, 'ROLE_COLAB_LIST_EDITAR', 'Editar', '#', 3, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (549, 'ROLE_COLAB_LIST_EXCLUIR', 'Excluir', '#', 4, false, 8);--.go
update papel set nome='Situação', ordem=5 where id=545;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (550, 'ROLE_COLAB_LIST_PERFORMANCE', 'Performance', '#', 6, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (551, 'ROLE_COLAB_LIST_NIVELCOMPETENCIA', 'Nível de competência', '#', 7, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (552, 'ROLE_COLAB_LIST_SOLICITACAO', 'Incluir em solicitação', '#', 8, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (553, 'ROLE_COLAB_LIST_DOCUMENTOANEXO', 'Documentos do colaborador', '#', 9, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (554, 'ROLE_COLAB_LIST_VISUALIZARCURRICULO', 'Visualizar currículo', '#', 10, false, 8);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (555, 'ROLE_CAD_COLABORADOR', 'Outros acessos', '#', 15, false, 8);--.go

alter sequence papel_sequence restart with 556;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=545 WHERE atualizaPapeisIdsAPartirDe is null;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 546 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 547 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 548 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 549 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 550 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 551 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 552 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 553 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 554 from perfil_papel where papeis_id =8;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 555 from perfil_papel where papeis_id =8;--.go