INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (532, 'ROLE_COMPOSICAO_SESMT', 'Composição do SESMT', '/sesmt/composicaoSesmt/list.action', 16, true, 385); --.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 532); --.go
alter sequence papel_sequence restart with 533; --.go