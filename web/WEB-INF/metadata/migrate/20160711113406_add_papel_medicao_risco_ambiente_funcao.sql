INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (676, 'ROLE_CAD_MEDICAORISCO_AMBIENTE', 'Ambiente', '/sesmt/medicaoRisco/list.action?controlaRiscoPor=A', 1, true, 450);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (677, 'ROLE_CAD_MEDICAORISCO_FUNCAO', 'Função', '/sesmt/medicaoRisco/list.action?controlaRiscoPor=F', 2, true, 450);--.go 

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 676 from perfil_papel where papeis_id = 450;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 677 from perfil_papel where papeis_id = 450;--.go

alter sequence papel_sequence restart with 678;--.go