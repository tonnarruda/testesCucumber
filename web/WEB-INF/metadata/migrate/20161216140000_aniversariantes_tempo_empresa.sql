
UPDATE papel SET ordem = ordem+1 WHERE papelmae_id = 377 AND ordem > 7;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (701,'ROLE_REL_ANIVERSARIANTES_POR_TEMPO_DE_EMPRESA', 'Aniversariantes por tempo de empresa', '/geral/colaborador/prepareRelatorioAniversariantesPorTempoDeEmpresa.action', 8, true, 377);--.go

INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 701);--.go
ALTER sequence papel_sequence restart WITH 702;--.go