insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (528, 'ROLE_REL_ACOMP_PERFORMANCE', 'Performance', '/avaliacao/periodoExperiencia/prepareRelatorioPerformanceAvaliacaoDesempenho.action', 6, true,486);--.go
alter sequence papel_sequence restart with 528;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=528 WHERE atualizaPapeisIdsAPartirDe is null;--.go