update papel set ordem = ordem+1 where papelmae_id = 37 and ordem > 10;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (558, 'ROLE_EXPORTACAO_TREINAMENTOS_TRU', 'Exportar Treinamentos para o TRU', '/exportacao/prepareExportacaoTreinamentos.action', 11, true, NULL, 37);
insert into perfil_papel(perfil_id, papeis_id) values (1, 558); --.go
alter sequence papel_sequence restart with 559; --.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=558 WHERE atualizaPapeisIdsAPartirDe is null;--.go
