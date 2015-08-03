  alter table riscoambiente add column grauderisco character(1);--.go
  
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (636, 'ROLE_REL_MAPA_DE_RISCO', 'Mapa de Risco', '/sesmt/ambiente/prepareRelatorioMapaDeRisco.action', 19, true, 387);--.go 
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 636);--.go
ALTER sequence papel_sequence restart WITH 637;--.go 