INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (566, 'ROLE_DISSIDIO', 'Faixas Salariais', '/cargosalario/reajusteFaixaSalarial/prepareDissidio.action', 2, true, NULL, 395);--.go
insert into perfil_papel select perfil_id, 566 from perfil_papel where papeis_id = 395;--.go
alter sequence papel_sequence restart with 567;--.go