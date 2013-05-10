update papel set ordem = ordem + 1 where papelmae_id = 377;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (572, 'ROLE_REL_RECIBO_PAGAMENTO', 'Recibo de pagamento', '/geral/colaborador/prepareReciboPagamento.action', 1, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 572);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=572 WHERE atualizaPapeisIdsAPartirDe is null;--.go
alter sequence papel_sequence restart with 573;--.go

update parametrosdosistema set acversaowebservicecompativel = '1.1.53.1' where id = 1;--.go