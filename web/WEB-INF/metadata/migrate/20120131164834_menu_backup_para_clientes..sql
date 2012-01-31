INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (539,'ROLE_BACKUP', 'Cópia de Segurança', '/backup/list.action', 11, true, 37);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=539 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 539);--.go
alter sequence papel_sequence restart with 540;--.go