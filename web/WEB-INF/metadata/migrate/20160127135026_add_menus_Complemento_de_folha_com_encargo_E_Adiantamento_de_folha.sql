update papel set ordem = ordem + 2 where papelmae_id = 377 and ordem > 3 ;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (663, 'ROLE_REL_ADIANTAMENTO_DE_FOLHA', 'Adiantamento de folha', '/geral/colaborador/prepareReciboPagamentoAdiantamentoDeFolha.action', 4, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 663);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (664, 'ROLE_REL_PAGAMENTO_COMPLEMENTAR', 'Complemento de folha com encargos', '/geral/colaborador/prepareReciboPagamentoComplementar.action', 5, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 664);--.go

alter sequence papel_sequence restart with 665;--.go