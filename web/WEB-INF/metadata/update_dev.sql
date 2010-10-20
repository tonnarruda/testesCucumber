update parametrosdosistema set appversao = '1.1.31.20';--.go


insert into papel values (nextval('papel_sequence'), 'ROLE_REL_ACOMPANHAMENTO_EXPERIENCIA', 'Periodo de Acompanhamento de Experiência',
 '/avaliacao/periodoExperiencia/prepareRelatorioAcopanhamentoExperiencia.action', 2, 't', 486);--.go

insert into papel values (nextval('papel_sequence'), 'ROLE_REL_ACOMP_RANKING_PERIODO_EXPERIENCIA', 'Ranking Performace Periodo de Experiência',
 '/avaliacao/periodoExperiencia/prepareRelatorioRankingPerformancePeriodoDeExperiencia.action', 3, 't',486);--.go
 
alter table parametrosdosistema add column codEmpresaSuporte character varying(10);--go
alter table parametrosdosistema add column codClienteSuporte character varying(10);--go

alter table colaborador add column dataatualizacao date;--go
update colaborador set dataAtualizacao = '1900-01-01'; --.go