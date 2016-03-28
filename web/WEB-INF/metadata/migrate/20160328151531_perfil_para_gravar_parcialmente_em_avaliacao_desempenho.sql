INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (671, 'ROLE_MOV_AVALIACAO_GRAVAR_PARCIALMENTE', 'Gravar Parcialmente.', '#', 3, false, 483);--.go
alter sequence papel_sequence restart with 672; --.go
insert into perfil_papel (perfil_id, papeis_id) select perfil_id,671 from perfil_papel where papeis_id = 483;--.go