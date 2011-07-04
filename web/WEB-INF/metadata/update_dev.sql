update parametrosdosistema set appversao = '1.1.50.42';--.go

update papel set codigo = 'ROLE_REL_ACOMP_EXPERIENCIA_PREVISTO', nome = 'Acompanhamento de Experiência Previsto', url = '/avaliacao/periodoExperiencia/prepareRelatorioAcompanhamentoExperienciaPrevisto.action' where id = 490; --.go
update papel set nome = 'Ranking de Performance das Avaliações de Desempenho', ordem = 4 where id = 491; --.go