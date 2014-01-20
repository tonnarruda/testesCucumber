UPDATE papel SET ordem = 9 where id = 22;--.go
UPDATE papel SET ordem = 10 where id = 56;--.go
UPDATE papel SET ordem = 11 where id = 556;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (611, 'ROLE_MOV_SOLICITACAO_IMPRIMIR', 'Imprimir', '#', 2, false, 21);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (612, 'ROLE_MOV_SOLICITACAO_EDITAR', 'Inserir/Editar', '#', 3, false, 21);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (613, 'ROLE_MOV_SOLICITACAO_EXCLUIR', 'Excluir', '#', 4, false, 21);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (614, 'ROLE_MOV_SOLICITACAO_ANUNCIAR', 'Anunciar', '#', 5, false, 21);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (615, 'ROLE_MOV_SOLICITACAO_ENCERRAR', 'Encerrar', '#', 6, false, 21);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (616, 'ROLE_MOV_SOLICITACAO_SUSPENDER', 'Suspender', '#', 7, false, 21);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (617, 'ROLE_MOV_SOLICITACAO_CLONAR', 'Clonar', '#', 8, false, 21);--.go 

alter sequence papel_sequence restart with 618;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 611 from perfil_papel where papeis_id = 21;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 612 from perfil_papel where papeis_id = 21;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 613 from perfil_papel where papeis_id = 21;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 614 from perfil_papel where papeis_id = 21;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 615 from perfil_papel where papeis_id = 21;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 616 from perfil_papel where papeis_id = 21;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 617 from perfil_papel where papeis_id = 21;--.go