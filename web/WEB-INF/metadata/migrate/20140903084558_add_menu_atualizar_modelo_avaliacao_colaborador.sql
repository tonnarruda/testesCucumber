insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (624, 'ROLE_MOV_ATUALIZAR_MODELO_AVALIACAO_COLABORADOR', 'Atualizar Modelos de Avaliação', '/geral/colaborador/prepareAtualizarModeloAvaliacao.action', 4, true, 469);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 624);--.go

alter sequence papel_sequence restart with 625;--.go