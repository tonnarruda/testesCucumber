INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (706, 'ROLE_REL_ANALISE_COMPETENCIAS_ORGANIZACAO', 'Análise de Desempenho das Competências da Organização', '/avaliacao/desempenho/prepareAnaliseDesempenhoCompetenciaOrganizacao.action', 8, true, 486);
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 706);

ALTER SEQUENCE papel_sequence RESTART WITH 707;--.go