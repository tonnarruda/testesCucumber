UPDATE papel set codigo='ROLE_CX_MENSAGEM_RES', papelmae_id =495,nome = 'R&S', ordem = 1  where id = 496;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (581, 'ROLE_CX_MENSAGEM_CES', 'C&S', '', 1, false, 495); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (582, 'ROLE_CX_MENSAGEM_PESQUISAS', 'Pesquisas', '', 2, false, 495); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (583, 'ROLE_CX_MENSAGEM_AV_DESMPENHO', 'Aval. Desempenho', '', 3, false, 495); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (584, 'ROLE_CX_MENSAGEM_TED', 'T&D', '', 4, false, 495); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (585, 'ROLE_CX_MENSAGEM_INFO_FUNCIONAIS', 'Info. Funcionais', '', 5, false, 495); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (586, 'ROLE_CX_MENSAGEM_SESMT', 'SESMT', '', 6, false, 495); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (587, 'ROLE_CX_MENSAGEM_UTILITARIOS', 'Utilitários', '', 7, false, 495); --.go
alter sequence papel_sequence restart with 588;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 581 from perfil_papel where papeis_id in (495);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 582 from perfil_papel where papeis_id in (495);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 583 from perfil_papel where papeis_id in (495);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 584 from perfil_papel where papeis_id in (495);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 585 from perfil_papel where papeis_id in (495);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 586 from perfil_papel where papeis_id in (495);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 587 from perfil_papel where papeis_id in (495);