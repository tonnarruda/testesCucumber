INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (702, 'ROLE_VISUALIZAR_ANEXO_COLAB_LOGADO', 'Visualizar "Documentos" do colaborador logado', '#', 13, false, 8);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) select perfil_id, 702 from perfil_papel where papeis_id = 8;--.go
ALTER sequence papel_sequence restart WITH 703;--.go