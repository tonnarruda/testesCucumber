update papel set url = '#' where id = 395;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (565, 'ROLE_DISSIDIO', 'Colaboradores', '/cargosalario/reajusteColaborador/prepareDissidio.action', 1, true, NULL, 395);--.go
insert into perfil_papel select perfil_id, 565 from perfil_papel where papeis_id = 395;--.go
alter sequence papel_sequence restart with 566;--.go