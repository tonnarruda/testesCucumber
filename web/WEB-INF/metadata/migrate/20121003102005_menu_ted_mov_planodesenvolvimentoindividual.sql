INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (563,'ROLE_MOV_PLANO_DESENVOLVIMENTO_INDIVIDUAL', 'Plano de Desenvolvimento Individual (PDI)', '/desenvolvimento/turma/preparePdi.action', 5, true, NULL, 367);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=563 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 563);--.go
alter sequence papel_sequence restart with 564;--.go