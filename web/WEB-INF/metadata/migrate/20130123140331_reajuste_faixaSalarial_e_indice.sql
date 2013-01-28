update papel set url = '#' where id = 395;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (565, 'ROLE_DISSIDIO', 'Colaboradores', '/cargosalario/reajusteColaborador/prepareDissidio.action', 1, true, NULL, 395);--.go

CREATE TABLE reajustefaixasalarial (
    id bigint NOT NULL,
    faixasalarial_id bigint NOT NULL,
    tabelareajustecolaborador_id bigint NOT NULL,
    valoratual numeric(8,2) NOT NULL,
    valorproposto numeric(8,2) NOT NULL
);--.go

ALTER TABLE ONLY reajustefaixasalarial ADD CONSTRAINT reajustefaixasalarial_pkey PRIMARY KEY (id);--.go
ALTER TABLE ONLY reajustefaixasalarial ADD CONSTRAINT reajustefaixasalarial_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);--.go
ALTER TABLE ONLY reajustefaixasalarial ADD CONSTRAINT reajustefaixasalarial_tabelareajustecolaborador_fk FOREIGN KEY (tabelareajustecolaborador_id) REFERENCES tabelareajustecolaborador(id);--.go

CREATE SEQUENCE reajustefaixasalarial_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;--.go

alter table faixasalarialhistorico add column reajustefaixasalarial_id bigint;--.go
ALTER TABLE ONLY faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_reajustefaixasalarial_fk FOREIGN KEY (reajustefaixasalarial_id) REFERENCES reajustefaixasalarial(id);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (566, 'ROLE_DISSIDIO', 'Faixas Salariais', '/cargosalario/reajusteFaixaSalarial/prepareDissidio.action', 2, true, NULL, 395);--.go
insert into perfil_papel select perfil_id, 566 from perfil_papel where papeis_id = 395;--.go
alter sequence papel_sequence restart with 567;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (567, 'ROLE_MOV_SOLICITACAOREAJUSTE_COLABORADOR', 'Colaborador', '/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action', 1, true, NULL, 49);--.go
insert into perfil_papel select perfil_id, 567 from perfil_papel where papeis_id = 49;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (568, 'ROLE_MOV_SOLICITACAOREAJUSTE_FAIXASALARIAL', 'Faixa Salarial', '/cargosalario/reajusteFaixaSalarial/prepareInsert.action', 2, true, NULL, 49);--.go
insert into perfil_papel select perfil_id, 568 from perfil_papel where papeis_id = 49;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (569, 'ROLE_MOV_SOLICITACAOREAJUSTE_INDICE', 'Índice', '/cargosalario/reajusteIndice/prepareInsert.action', 3, true, NULL, 49);--.go
insert into perfil_papel select perfil_id, 569 from perfil_papel where papeis_id = 49;--.go

CREATE TABLE reajusteindice (
    id bigint NOT NULL,
    indice_id bigint NOT NULL,
    tabelareajustecolaborador_id bigint NOT NULL,
    valoratual numeric(8,2) NOT NULL,
    valorproposto numeric(8,2) NOT NULL
);--.go

ALTER TABLE ONLY reajusteindice ADD CONSTRAINT reajusteindice_pkey PRIMARY KEY (id);--.go
ALTER TABLE ONLY reajusteindice ADD CONSTRAINT reajusteindice_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);--.go
ALTER TABLE ONLY reajusteindice ADD CONSTRAINT reajusteindice_tabelareajustecolaborador_fk FOREIGN KEY (tabelareajustecolaborador_id) REFERENCES tabelareajustecolaborador(id);--.go

CREATE SEQUENCE reajusteindice_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;--.go

alter table indicehistorico add column reajusteindice_id bigint;--.go
ALTER TABLE ONLY indicehistorico ADD CONSTRAINT indicehistorico_reajusteindice_fk FOREIGN KEY (reajusteindice_id) REFERENCES reajusteindice(id);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (570, 'ROLE_DISSIDIO', 'Índices', '/cargosalario/reajusteIndice/prepareDissidio.action', 3, true, NULL, 395);--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=565 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel select perfil_id, 565 from perfil_papel where papeis_id = 395;--.go
insert into perfil_papel select perfil_id, 570 from perfil_papel where papeis_id = 395;--.go
alter sequence papel_sequence restart with 571;--.go
