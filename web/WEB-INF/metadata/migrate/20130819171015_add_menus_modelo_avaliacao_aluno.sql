update papel set ordem = ordem + 1 where papelmae_id = 366 and ordem >= 4;--.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (589, 'ROLE_CAD_MOD_AVALIACAO_ALUNO', 'Modelos de Avaliação de Aluno', '/avaliacao/modelo/list.action?modeloAvaliacao=L', 4, true, 366);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (590, 'ROLE_REL_RANKING_AVALIACAO_ALUNO', 'Ranking de Performance das Avaliações dos Alunos', '/desenvolvimento/avaliacaoCurso/prepareRelatorioRankingAvaliacaoAluno.action', 14, true, 368);--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 589);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 590);--.go

alter sequence papel_sequence restart with 581;--.go
