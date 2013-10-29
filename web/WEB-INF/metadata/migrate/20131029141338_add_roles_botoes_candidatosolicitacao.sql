INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (598, 'ROLE_CAND_SOLICITACAO_IMPRIMIR', 'Imprimir', '#', 9, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (599, 'ROLE_CAND_SOLICITACAO_TRIAGEM', 'Triagem', '#', 10, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (600, 'ROLE_CAND_SOLICITACAO_INSERIRETAPAGRUPO', 'Inserir Etapas em Grupo', '#', 11, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (601, 'ROLE_CAND_SOLICITACAO_RESULTADOAVALIACAO', 'Resultado da Avaliação', '#', 12, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (602, 'ROLE_CAND_SOLICITACAO_TRANSFCANDIDATO', 'Transf. Candidatos', '#', 13, false, 22);--.go 
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (603, 'ROLE_CAND_SOLICITACAO_TRIAGEMMODULOEXTERNO', 'Triagem Módulo Externo', '#', 14, false, 22);--.go 

alter sequence papel_sequence restart with 604;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 598 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 599 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 600 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 601 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 602 from perfil_papel where papeis_id = 22;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 603 from perfil_papel where papeis_id = 22;--.go
