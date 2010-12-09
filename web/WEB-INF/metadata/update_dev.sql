update parametrosdosistema set appversao = '1.1.35.25';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (492, 'ROLE_MOV_SOLICITACAO', 'Modelos de Avaliação de Solicitação', '/avaliacaoCandidato/modelo/list.action?tipoModeloAvaliacao=S', 7, true, 358);--.go

alter sequence papel_sequence restart with 493;--.go

alter table avaliacao add column tipoModeloAvaliacao character(1);--.go
update avaliacao set tipoModeloAvaliacao='D';--.go
alter table avaliacao alter column tipoModeloAvaliacao set not null;--.go
