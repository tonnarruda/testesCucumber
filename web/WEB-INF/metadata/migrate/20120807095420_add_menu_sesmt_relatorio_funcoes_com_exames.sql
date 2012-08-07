INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (559, 'ROLE_REL_FUNCAO_EXAME', 'Funções com Exames', '/sesmt/funcao/prepareRelatorioFuncoesExames.action', 18, true, NULL, 387);
insert into perfil_papel(perfil_id, papeis_id) values (1, 559); --.go
alter sequence papel_sequence restart with 560; --.go
