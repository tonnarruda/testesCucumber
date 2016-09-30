INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (685, 'EXIBIR_SALARIO_CONTRATUAL_REL_COLAB', 'Exibir "Salário Contratual" na opção campos disponíveis', '#', 1, false, 506);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (686, 'EXIBIR_SALARIO_PERFORMANCE', 'Exibir "Salário" no quadro Trajetória Profissional na Empresa', '#', 1, false, 550);--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 685 from perfil_papel where papeis_id = 506;--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 686 from perfil_papel where papeis_id = 550;--.go

ALTER sequence papel_sequence restart WITH 687;--.go