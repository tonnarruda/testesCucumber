INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (557, 'ROLE_COLAB_VER_TODOS', 'Visualizar Todos os Colaboradores', '#', 11, false, 8);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=545 WHERE atualizaPapeisIdsAPartirDe is null;--.go
alter sequence papel_sequence restart with 558;--.go