INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (544, 'EXIBIR_COMPETENCIA_SOLICITACAO', 'Visualizar competência dos candidatos da solicitação', '#', 11, false, 359);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=544 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 544); --.go
alter sequence papel_sequence restart with 545;--.go