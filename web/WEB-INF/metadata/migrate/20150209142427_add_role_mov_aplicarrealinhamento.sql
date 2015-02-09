insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (629, 'ROLE_MOV_APLICARREALINHAMENTO', 'Aplicar Realinhamento', '#', 1, false, 26); --.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 629 from perfil_papel where papeis_id = 26;
alter sequence papel_sequence restart with 630;--.go