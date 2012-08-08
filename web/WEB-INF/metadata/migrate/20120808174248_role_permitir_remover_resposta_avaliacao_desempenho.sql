INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (560, 'ROLE_AVAL_DESEMP_DELETE_RESPOSTA', 'Apagar Respostas', '#', 1, false, 55);--.go
alter sequence papel_sequence restart with 561;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=559 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 560 from perfil_papel where papeis_id =55;--.go
update papel set codigo = 'ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO' where id = 484;--.go


