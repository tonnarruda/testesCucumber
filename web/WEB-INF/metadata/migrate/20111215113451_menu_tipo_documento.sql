INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (533, 'ROLE_TIPO_DOCUMENTO', 'Tipo do Documento', '/geral/tipoDocumento/list.action', 9, true, 374);--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 533); --.go
alter sequence papel_sequence restart with 534;--.go