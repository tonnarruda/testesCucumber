alter table riscoambiente add column grauderisco character(1);--.go
  
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id, help) VALUES (642, 'ROLE_REL_MAPA_DE_RISCO', 'Mapa de Risco', '/sesmt/ambiente/prepareRelatorioMapaDeRisco.action', 19, true, 387, 'O Mapa de Risco só poderá ser emitido se a empresa em que o usuário está logado controlar os riscos por ambiente.');--.go 
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 642);--.go
ALTER sequence papel_sequence restart WITH 643;--.go 