INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (530, 'ROLE_CAD_NATUREZALESAO', 'Natureza da Lesão', '/sesmt/naturezaLesao/list.action', 14, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 530);--.go
alter sequence papel_sequence restart with 531;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=530 WHERE atualizaPapeisIdsAPartirDe is null;--.go