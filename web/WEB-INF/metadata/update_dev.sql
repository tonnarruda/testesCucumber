update parametrosdosistema set appversao = '1.1.31.20';--.go

alter table comissaomembro add column dataEnt date;--.go

insert into papel values (nextval('papel_sequence'), 'ROLE_REL_ACOMPANHAMENTO_EXPERIENCIA', 'Periodo de Acompanhamento de Experiência',
 '/avaliacao/periodoExperiencia/prepareRelatorioAcopanhamentoExperiencia.action', 2, 't', null, 486);--.go

insert into papel values (nextval('papel_sequence'), 'ROLE_REL_ACOMP_RANKING_PERIODO_EXPERIENCIA', 'Ranking Performace Periodo de Experiência',
 '/avaliacao/periodoExperiencia/prepareRelatorioRankingPerformancePeriodoDeExperiencia.action', 3, 't', null, 486);--.go