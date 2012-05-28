INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (545, 'ROLE_CAD_HISTORICOCOLABORADOR', 'Situações do Colaborador', '#', 3, false, 8);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=545 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 545); --.go
alter sequence papel_sequence restart with 546;--.go

insert into perfil_papel select perfil_id, 545 from perfil_papel where papeis_id = 8;--.go