update papel set ordem = 1 where id = 480;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (604, 'ROLE_MOV_PROVIDENCIA', 'Providências', '/geral/ocorrenciaProvidencia/list.action', 2, true, 469);--.go

alter sequence papel_sequence restart with 605;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 604 from perfil_papel where papeis_id = 480;--.go