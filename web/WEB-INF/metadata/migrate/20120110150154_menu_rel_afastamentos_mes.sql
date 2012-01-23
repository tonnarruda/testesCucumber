update papel set ordem=ordem+1 where papelmae_id=387 and ordem > 11;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (536,'ROLE_REL_AFASTAMENTO', 'Resumo de Afastamentos', '/sesmt/colaboradorAfastamento/prepareRelatorioResumoAfastamentos.action', 12, true, 387);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=536 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 536);--.go
alter sequence papel_sequence restart with 537;--.go