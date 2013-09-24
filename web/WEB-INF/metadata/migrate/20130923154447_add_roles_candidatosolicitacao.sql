update papel set nome = 'Candidatos da Seleção' where id = 22;--.go
update papel set ordem = 3, nome = 'Competências', codigo = 'ROLE_CAND_SOLICITACAO_COMPETENCIAS' where id = 544;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (591, 'ROLE_CAND_SOLICITACAO_LISTA', 'Ver Listagem dos Candidatos', '#', 1, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (592, 'ROLE_CAND_SOLICITACAO_HISTORICO', 'Histórico', '#', 2, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (593, 'ROLE_CAND_SOLICITACAO_VISUALIZARCURRICULO', 'Visualizar Currículo', '#', 4, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (594, 'ROLE_CAND_SOLICITACAO_CONTRATAR', 'Contratar/Promover', '#', 5, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (595, 'ROLE_CAND_SOLICITACAO_EXCLUIR', 'Excluir', '#', 6, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (596, 'ROLE_CAND_SOLICITACAO_DOCUMENTOANEXO', 'Documentos Anexos', '#', 7, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (597, 'ROLE_CAND_SOLICITACAO_AVALIACOES', 'Avaliações da Solicitação', '#', 8, false, 22);--.go 

alter sequence papel_sequence restart with 598;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 591 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 592 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 593 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 594 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 595 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 596 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 597 from perfil_papel where papeis_id = 22;--.go
