INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (620, 'ROLE_MOV_APROV_REPROV_SOL_DESLIGAMENTO', 'Solicitações de Desligamento', '/geral/colaborador/prepareAprovarReprovarSolicitacaoDesligamento.action', 3, true, 469);--.go
alter sequence papel_sequence restart with 621;--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 620);--.go