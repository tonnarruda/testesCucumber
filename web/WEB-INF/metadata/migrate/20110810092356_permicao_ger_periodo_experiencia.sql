INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (518,'GERENCIA_MSG_PERIODOEXPERIENCIA', 'Gerenciar Mensagens de Aviso Perodo de Experincia', '#', 5, false, 382);--.go
alter sequence papel_sequence restart with 519;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=517 WHERE atualizaPapeisIdsAPartirDe is null;--.go