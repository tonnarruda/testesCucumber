INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (706, 'ROLE_MOV_SOLICITACAO_ANEXAR', 'Anexar', '#', 14, false, 21);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 706);--.go
alter sequence papel_sequence restart with 707; --.go

--Drop column documento anexo
update documentoanexo set origem = 'E' where moduloexterno = true;--.go
ALTER TABLE documentoanexo DROP COLUMN moduloexterno;--.go