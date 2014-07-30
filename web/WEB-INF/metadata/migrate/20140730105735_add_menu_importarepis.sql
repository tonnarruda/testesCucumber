INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (621, 'ROLE_IMPORTACAO_EPI', 'Importar EPIs', '/importacao/prepareImportarEPIs.action', 13, true, 37);--.go
alter sequence papel_sequence restart with 622;--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 621);--.go	