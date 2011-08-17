delete from migrations where name = '20110816094853';
delete from papel where id=522;

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (522, 'ROLE_REL_ACOMP_RANKING_PERIODO_EXPERIENCIA', 'Ranking de Performance das Avaliações de Desempenho Agrupado por Modelo de Avaliação','/avaliacao/periodoExperiencia/prepareImpRankPerformAvDesempenho.action', 5, true,486);--.go
alter sequence papel_sequence restart with 523;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=522 WHERE atualizaPapeisIdsAPartirDe is null;--.go