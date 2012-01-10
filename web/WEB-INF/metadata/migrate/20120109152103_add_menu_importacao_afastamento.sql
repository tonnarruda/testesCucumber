INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (535,'ROLE_IMPORTACAO_AFASTAMENTO', 'Importar Afastamentos', '/importacao/prepareImportarAfastamentos.action', 10, true, 37);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=535 WHERE atualizaPapeisIdsAPartirDe is null;--.go
alter sequence papel_sequence restart with 536;--.go