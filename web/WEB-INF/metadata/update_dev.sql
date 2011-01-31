update parametrosdosistema set appversao = '1.1.38.29';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (498, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Relatorio de investimento de T&D', '/desenvolvimento/turma/relatorioInvestimento.action', 12, true, 368);--.go
alter sequence papel_sequence restart with 499; --.go

update papel set nome = 'Acompanhamento do Período de Experiência e Avaliação de Desempenho' where id = 490;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=498 WHERE atualizaPapeisIdsAPartirDe is null;--.go