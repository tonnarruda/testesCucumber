INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (645, 'ROLE_INTEGRA_FORTES_PESSOAL', 'Integra com o Fortes Pessoal', '#', 1, false, 58); --.go
INSERT INTO perfil_papel(perfil_id, papeis_id) select perfil_id, 645 from perfil_papel where papeis_id = 58; --.go
ALTER sequence papel_sequence restart WITH 646; --.go