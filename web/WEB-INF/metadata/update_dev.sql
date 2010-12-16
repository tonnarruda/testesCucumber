update parametrosdosistema set appversao = '1.1.35.25';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (492, 'ROLE_MOV_SOLICITACAO', 'Modelos de Avaliação de Solicitação', '/avaliacao/modeloCandidato/list.action?modeloAvaliacao=S', 7, true, 358);--.go

alter sequence papel_sequence restart with 493;--.go

update papel set nome = 'Relatório de Ranking de Performace de Avaliação de Desempenho' where id = 491;--.go

update papel set nome = 'Dias do Acompanhamento do Período de Experiência',  papelmae_id = 481, ordem = 2 where id = 467;--.go

alter table avaliacao add column tipoModeloAvaliacao character(1);--.go
update avaliacao set tipoModeloAvaliacao='D';--.go
alter table avaliacao alter column tipoModeloAvaliacao set not null;--.go

update papel set nome = 'Avaliações de Desempenho' where id = 55;--.go
update papel set nome = 'Responder Avaliações de Desempenho' where id = 483;--.go
update papel set nome = 'Acompanhamento do Período de Experiência' where id = 470;--.go
update papel set nome = 'Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência' where id = 479;--.go 
update papel set nome = 'Acompanhamento do Período de Experiência' where id = 490;--.go
update papel set nome = 'Ranking de Performace das Avaliações de Desempenho' where id = 491;--.go

CREATE TABLE habilidade (
id bigint NOT NULL,
nome character varying(100),
empresa_id bigint
);--.go

ALTER TABLE habilidade ADD CONSTRAINT habilidade_pkey PRIMARY KEY(id);--.go
ALTER TABLE habilidade ADD CONSTRAINT habilidade_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE habilidade_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE atitude (
id bigint NOT NULL,
nome character varying(100),
empresa_id bigint
);--.go

ALTER TABLE atitude ADD CONSTRAINT atitude_pkey PRIMARY KEY(id);--.go
ALTER TABLE atitude ADD CONSTRAINT atitude_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE atitude_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

update papel set ordem = 6 where id = 11;--.go
update papel set ordem = 7 where id = 404;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (493, 'ROLE_CAD_HABILIDADE', 'Habilidades', '/captacao/habilidade/list.action', 4, true, 362);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (494, 'ROLE_CAD_ATITUDE', 'Atitudes', '/captacao/atitude/list.action', 5, true, 362);--.go
alter sequence papel_sequence restart with 495;

CREATE TABLE cargo_habilidade(
    cargo_id bigint NOT NULL,
    habilidades_id bigint NOT NULL
);--.go
ALTER TABLE cargo_habilidade ADD CONSTRAINT cargo_habilidade_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go 
ALTER TABLE cargo_habilidade ADD CONSTRAINT cargo_habilidade_habilidade_fk FOREIGN KEY (habilidades_id) REFERENCES habilidade(id);--.go

CREATE TABLE cargo_atitude(
    cargo_id bigint NOT NULL,
    atitudes_id bigint NOT NULL
);--.go
ALTER TABLE cargo_atitude ADD CONSTRAINT cargo_atitude_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go 
ALTER TABLE cargo_atitude ADD CONSTRAINT cargo_atitude_atitude_fk FOREIGN KEY (atitudes_id) REFERENCES atitude(id);--.go

alter table solicitacao add column avaliacao_id bigint;--.go
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go

alter table colaboradorquestionario add column solicitacao_id bigint;--.go
ALTER TABLE colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);--.go

update papel set nome = 'Modelos de Avaliação do Candidato' where id = 492;--.go

update papel set nome = 'Avaliações de Desempenho/Acomp. do Período de Experiência' where id = 482;--.go
update avaliacao set tipomodeloavaliacao = 'A' where tipomodeloavaliacao <> 'S';--.go