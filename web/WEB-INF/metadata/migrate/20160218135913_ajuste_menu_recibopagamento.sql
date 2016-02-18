update papel set nome = 'Recibos de Pagamentos', url = '#' where id = 572 ;--.go
update papel set papelmae_id = 572, nome = 'Adiantamento de Folha', ordem = 1 where id = 663 ;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (667, 'ROLE_REL_RECIBO_FOLHA', 'Folha', '/geral/colaborador/prepareReciboPagamento.action', 2, true, 572);--.go
update papel set papelmae_id = 572, nome = 'Complemento de Folha com Encargos', ordem = 3 where id = 664 ;--.go
update papel set papelmae_id = 572, nome = 'Férias',ordem = 4 where id = 666 ;--.go
update papel set papelmae_id = 572, nome = '13º Salário', ordem = 5 where id = 647 ;--.go
update papel set ordem = 2 where id = 656;--.go
update papel set ordem = ordem - 4 where papelmae_id = 377 and ordem >= 7;--.go

alter sequence papel_sequence restart with 668;--.go