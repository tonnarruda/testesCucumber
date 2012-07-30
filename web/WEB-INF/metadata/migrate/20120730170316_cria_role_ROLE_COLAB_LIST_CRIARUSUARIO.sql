INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (558, 'ROLE_COLAB_LIST_CRIARUSUARIO', 'Criar acesso ao sistema', '#', 10, false, 8);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=558 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 558); --.go
alter sequence papel_sequence restart with 559;--.go

update papel set ordem=11 where id=554;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 558 from perfil_papel where papeis_id = 8;--.go