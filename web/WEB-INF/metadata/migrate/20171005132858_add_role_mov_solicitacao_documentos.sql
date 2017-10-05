INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (707, 'ROLE_MOV_SOLICITACAO_DOCUMENTOS', 'Documentos da Solicitação de Pessoal', '#', 13, false, 21);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 707);--.go
alter sequence papel_sequence restart with 708; --.go

update documentoanexo set origem = 'E' where moduloexterno = true;--.go
ALTER TABLE documentoanexo DROP COLUMN moduloexterno;--.go