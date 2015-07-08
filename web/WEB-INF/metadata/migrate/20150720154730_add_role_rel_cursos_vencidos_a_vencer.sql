INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (634, 'ROLE_REL_CURSOS_VENCIDOS_A_VENCER', 'Cursos Vencidos e a Vencer', '/desenvolvimento/turma/prepareImprimirCursosVencidosAVencer.action', 15, true, 368);--.go 
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 634);--.go

ALTER sequence papel_sequence restart WITH 635;--.go 

