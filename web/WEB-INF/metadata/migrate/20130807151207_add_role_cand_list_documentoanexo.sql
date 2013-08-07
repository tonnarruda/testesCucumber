INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (588, 'ROLE_CAND_LIST_DOCUMENTOANEXO', 'Documentos do Candidato', '#', 4, false, 2); --.go
alter sequence papel_sequence restart with 589;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 588 from perfil_papel where papeis_id = 2;--.go