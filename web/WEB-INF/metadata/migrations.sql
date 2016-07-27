UPDATE papel SET ordem = ordem + 1 WHERE papelmae_id = 377 AND ordem > 2 ;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (681,'ROLE_REL_FERIAS', 'Férias', '/geral/colaborador/prepareRelatorioFerias.action', 3, true, 377);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 681);--.go
ALTER sequence papel_sequence restart WITH 682;--.go

update parametrosdosistema set acversaowebservicecompativel='1.1.62.1';--.go
