INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (516, 'ROLE_CAD_NIVEL_COMPETENCIA', 'Nível de Competência', '/captacao/nivelCompetencia/list.action', 6, true, 362);--.go
alter sequence papel_sequence restart with 517;--.go

update papel set ordem=ordem+1 where papelmae_id=362 and ordem > 5;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=516 WHERE atualizaPapeisIdsAPartirDe is null;--.go