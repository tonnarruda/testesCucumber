INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (636, 'ROLE_EDITA_DATA_STATUS_SOLICITACAO', 'Editar data do status da solicitação', '#', 1, false, 56); --.go
INSERT INTO perfil_papel(perfil_id, papeis_id) select perfil_id, 636 from perfil_papel where papeis_id = 56; --.go
ALTER sequence papel_sequence restart WITH 637; --.go