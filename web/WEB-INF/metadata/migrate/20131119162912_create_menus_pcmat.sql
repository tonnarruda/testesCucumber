UPDATE papel SET ordem = ordem + 1 WHERE papelmae_id = 385 AND ordem > 9;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (605, 'ROLE_CAD_PCMAT', 'PCMAT', '#', 10, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 605);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (606, 'ROLE_CAD_OBRA', 'Obras', '/sesmt/obra/list.action', 1, true, 605);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 606);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (607, 'ROLE_CAD_FASEPCMAT', 'Fases', '/sesmt/fasepcmat/list.action', 2, true, 605);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 607);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (608, 'ROLE_CAD_MEDIDASEGURANCA', 'Medidas de Segurança', '/sesmt/medidaseguranca/list.action', 3, true, 605);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 608);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (609, 'ROLE_MOV_PCMAT', 'PCMAT', '/sesmt/pcmat/list.action', 10, true, 386);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 609);--.go

alter sequence papel_sequence restart with 610;--.go