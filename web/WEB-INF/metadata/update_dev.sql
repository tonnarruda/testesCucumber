update parametrosdosistema set appversao = '1.1.38.29';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (498, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Relatorio de investimento', '/desenvolvimento/turma/relatorioInvestimento.action', 11, true, 368);--.go
alter sequence parametrosdosistema_sequence restart with 499; --.go

update papel set nome = 'Acompanhamento do Período de Experiência e Avaliação de Desempenho' where id = 490;--.go