INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (537, 'ROLE_PROVIDENCIA', 'Providências', '/geral/providencia/list.action', 10, true, 374);--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 537);--.go
alter sequence papel_sequence restart with 538;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=537 WHERE atualizaPapeisIdsAPartirDe is null;--.go