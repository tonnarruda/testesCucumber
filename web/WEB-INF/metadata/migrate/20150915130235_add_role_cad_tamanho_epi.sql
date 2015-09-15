UPDATE papel set ordem = ordem + 1 where papelmae_id = 385;--.go  
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (643, 'ROLE_CAD_TAMANHO_EPI', 'Tamanhos de EPI/Fardamento', '/sesmt/tamanhoEPI/list.action', 1, true, 385);--.go  
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 643);--.go
ALTER sequence papel_sequence restart WITH 644;--.go 