insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (573, 'ROLE_CAD_OCORRENCIA_INSERIR', 'Inserir', '#', 1, false, 62);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (574, 'ROLE_CAD_OCORRENCIA_EDITAR', 'Editar', '#', 2, false, 62);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (575, 'ROLE_CAD_OCORRENCIA_EXCLUIR', 'Excluir', '#', 3, false, 62);--.go

update papel set codigo = 'ROLE_MOV_OCORRENCIA' where id = 480;--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (576, 'ROLE_MOV_OCORRENCIA_INSERIR', 'Inserir', '#', 1, false, 480);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (577, 'ROLE_MOV_OCORRENCIA_EDITAR', 'Editar', '#', 2, false, 480);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (578, 'ROLE_MOV_OCORRENCIA_EXCLUIR', 'Excluir', '#', 3, false, 480);--.go

alter sequence papel_sequence restart with 579;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 573 from perfil_papel where papeis_id = 62;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 574 from perfil_papel where papeis_id = 62;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 575 from perfil_papel where papeis_id = 62;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 576 from perfil_papel where papeis_id = 480;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 577 from perfil_papel where papeis_id = 480;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 578 from perfil_papel where papeis_id = 480;--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 573);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 574);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 575);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 576);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 577);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 578);--.go
