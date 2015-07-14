insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (633, 'ROLE_EDITA_DATA_SOLICITACAO', 'Editar data da solicitação', '#', 1, false, 612); --.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 633 from perfil_papel where papeis_id = 612;
alter sequence papel_sequence restart with 634;--.go