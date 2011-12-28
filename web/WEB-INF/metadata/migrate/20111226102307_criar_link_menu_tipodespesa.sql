INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (534, 'ROLE_TIPO_DESPESA', 'Tipo de Despesa', '/geral/tipoDespesa/list.action', 6, true, 366);--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 534); --.go
alter sequence papel_sequence restart with 535;--.go