update parametrosdosistema set appversao = '1.1.35.25';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (492, 'ROLE_MOV_SOLICITACAO', 'Modelos de Avaliação de Solicitação', '/avaliacao/modeloCandidato/list.action?modeloAvaliacao=S', 7, true, 358);--.go

alter sequence papel_sequence restart with 493;--.go

update papel set nome = 'Relatório de Ranking de Performace de Avaliação de Desempenho' where id = 491;--.go

update papel set nome = 'Dias do Acompanhamento do Período de Experiência',  papelmae_id = 481, ordem = 2 where id = 467;--.go

alter table avaliacao add column tipoModeloAvaliacao character(1);--.go
update avaliacao set tipoModeloAvaliacao='D';--.go
alter table avaliacao alter column tipoModeloAvaliacao set not null;--.go