INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (632, 'ROLE_UTI', 'Inserir Nono Dígito em Celulares', '/geral/insereNonoDigito/prepareInsert.action', 18, true, 37);--.go 
alter sequence papel_sequence restart with 633;--.go 

insert into perfil_papel(perfil_id, papeis_id) values(1, 632);--.go 