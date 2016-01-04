update papel set ordem = ordem + 1 where papelmae_id = 377 and ordem > 2 ;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (648, 'ROLE_REL_DECLARACAO_RENDIMENTOS', 'Declaração de Rendimentos', '/geral/colaborador/prepareDeclaracaoRendimentos.action', 3, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 648);--.go
alter sequence papel_sequence restart with 649;--.go

update parametrosdosistema set acversaowebservicecompativel = '1.1.53.1' where id = 1;--.go