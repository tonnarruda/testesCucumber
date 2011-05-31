update parametrosdosistema set appversao = '1.1.46.38';--.go

alter table colaboradorocorrencia add column absenteismo boolean not null default false;--.go

update papel set ordem=8 where id=506;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (509, 'ROLE_REL_ABSENTEISMO', 'Absenteísmo', '/geral/colaboradorOcorrencia/prepareRelatorioAbsenteismo.action', 7, true, 377);--.go
alter sequence papel_sequence restart with 510;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=509 WHERE atualizaPapeisIdsAPartirDe is null;--.go

ALTER TABLE empresa ALTER COLUMN grupoac TYPE character varying(3);--.go

alter table tabelareajustecolaborador add column dissidio boolean not null default false;--.go