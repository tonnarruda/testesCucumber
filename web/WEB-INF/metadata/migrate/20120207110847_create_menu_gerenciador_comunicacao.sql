INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (540, 'ROLE_CAD_GERENCIADOR_COMUNICACAO', 'Gerenciador Comunicação', '/geral/gerenciadorComunicacao/list.action', 7, true, 390);--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 540); --.go
alter sequence papel_sequence restart with 541;--.go