INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (543, 'EXIBIR_SALARIO_RELAT_COLAB_CARGO', 'Mostrar opção Exibir Salário', '#', 1, false, 471);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=543 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 543); --.go
alter sequence papel_sequence restart with 544;--.go