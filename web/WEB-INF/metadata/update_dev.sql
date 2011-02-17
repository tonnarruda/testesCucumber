update parametrosdosistema set appversao = '1.1.40.31';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (500, 'ROLE_SOLICITACAO_AGENDA', 'Agenda', '/captacao/solicitacao/agenda.action', 2, true, 359);--.go
alter sequence papel_sequence restart with 501;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=500 WHERE atualizaPapeisIdsAPartirDe is null;--.go