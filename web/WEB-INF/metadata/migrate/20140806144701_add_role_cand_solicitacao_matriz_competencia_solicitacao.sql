insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (622, 'ROLE_CAND_SOL_MATRIZ_COMPETENCIA_SOLICITACAO', 'Imprimir Matriz de Competências', '#', 15, false, 22);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 622);--.go
alter sequence papel_sequence restart with 623;--.go