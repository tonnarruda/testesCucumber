INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (527, 'ROLE_IND', 'Painel', '/indicador/duracaoPreenchimentoVaga/painel.action', 1, true, 461);--.go
update papel set ordem=2 where id=73;--.go
update papel set ordem=3 where id=69;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=527 WHERE atualizaPapeisIdsAPartirDe is null;--.go