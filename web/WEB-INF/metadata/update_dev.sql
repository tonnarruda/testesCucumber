update parametrosdosistema set appversao = '1.1.50.42';--.go

alter table usuario add column superadmin boolean default false;--.go

update papel set codigo = 'ROLE_REL_ACOMP_EXPERIENCIA_PREVISTO', nome = 'Acompanhamento de Experiência Previsto', url = '/avaliacao/periodoExperiencia/prepareRelatorioAcompanhamentoExperienciaPrevisto.action' where id = 490;--.go
update papel set nome = 'Ranking de Performance das Avaliações de Desempenho', ordem = 4 where id = 491;--.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (513, 'ROLE_REL_ACOMP_PERIODO_EXPERIENCIA', 'Acompanhamento do Período de Experiência', '/avaliacao/periodoExperiencia/prepareRelatorioAcompanhamentoExperiencia.action', 3, true, 486);--.go
alter sequence papel_sequence restart with 514;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=513 WHERE atualizaPapeisIdsAPartirDe is null;--.go