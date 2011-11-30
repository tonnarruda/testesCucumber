INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (531, 'ROLE_SESMT_PAINEL_IND', 'Painel de Indicadores', '/sesmt/indicadores/painel.action', 6, true, 75); --.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 531); --.go
alter sequence papel_sequence restart with 532; --.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=531 WHERE atualizaPapeisIdsAPartirDe is null;--.go