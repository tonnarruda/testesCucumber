insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (580, 'ROLE_REL_FORMACAOESCOLAR', 'Formação Escolar', '/geral/colaborador/prepareRelatorioFormacaoEscolar.action', 10, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 580);--.go
alter sequence papel_sequence restart with 581;--.go
