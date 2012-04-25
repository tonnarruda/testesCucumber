INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (542, 'ROLE_PERFORMANCE_GESTOR_AREA', 'Visualizar Performance Funcional apenas da área cujo gestor é responsável', '#', 2, false, 8);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=542 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 542); --.go
alter sequence papel_sequence restart with 543;--.go