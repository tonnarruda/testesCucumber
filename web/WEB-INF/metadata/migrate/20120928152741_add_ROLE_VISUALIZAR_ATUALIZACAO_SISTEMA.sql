update papel set ordem=ordem+1 where papelmae_id is null and ordem >= 3;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (562, 'ROLE_VISUALIZAR_ATUALIZACAO_SISTEMA', 'Visualizar mensagem de atualização do sistema', '', 3, false, NULL);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 562);--.go
UPDATE parametrosdosistema SET modulos = cast(encode(decode(modulos, 'base64')||',562', 'base64') as text);--.go
alter sequence papel_sequence restart with 563;--.go
