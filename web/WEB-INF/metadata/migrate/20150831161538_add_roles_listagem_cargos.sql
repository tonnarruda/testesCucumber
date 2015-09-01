insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (637, 'ROLE_CAD_CARGO_INSERIR', 'Inserir', '#', 1, false, 11); --.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (638, 'ROLE_CAD_CARGO_EDITAR', 'Editar', '#', 2, false, 11); --.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (639, 'ROLE_CAD_CARGO_EXCLUIR', 'Excluir', '#', 3, false, 11); --.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (640, 'ROLE_CAD_CARGO_FUNCOES', 'Exibir Funções', '#', 4, false, 11); --.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (641, 'ROLE_CAD_CARGO_IMPRIMIR', 'Imprimir', '#', 5, false, 11); --.go

update papel set papelmae_id = 11, ordem = 6 where id = 499;
update papel set papelmae_id = 11, ordem = 7 where id = 417;

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 637 from perfil_papel where papeis_id = 11;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 638 from perfil_papel where papeis_id = 11;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 639 from perfil_papel where papeis_id = 11;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 640 from perfil_papel where papeis_id = 11;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 641 from perfil_papel where papeis_id = 11;--.go
alter sequence papel_sequence restart with 642;--.go