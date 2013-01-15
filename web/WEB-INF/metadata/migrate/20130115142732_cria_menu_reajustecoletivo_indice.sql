INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (570, 'ROLE_DISSIDIO', 'Índices', '/cargosalario/reajusteIndice/prepareDissidio.action', 3, true, NULL, 395);--.go
insert into perfil_papel select perfil_id, 570 from perfil_papel where papeis_id = 395;--.go
alter sequence papel_sequence restart with 571;--.go