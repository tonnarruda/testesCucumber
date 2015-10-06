update papel set ordem=ordem+1 where papelmae_id is null and ordem > 3;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (644, 'ROLE_MOV_AVALIACAO_EDITAR_ACOMPANHAMENTO', 'Editar respostas do acompanhamento do período de experiência por meio de caixa de mensagem ou email', '', 4, false, NULL);--.go  
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT id, 644 FROM perfil;--.go

alter sequence papel_sequence restart with 645;--.go