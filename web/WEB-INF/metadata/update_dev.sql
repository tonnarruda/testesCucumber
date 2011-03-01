update parametrosdosistema set appversao = '1.1.41.32';--.go

alter table historicocandidato add column aptoTmp character(1);--.go

update historicocandidato set aptoTmp='S' where apto=true;--.go
update historicocandidato set aptoTmp='N' where apto=false;--.go

alter table historicocandidato drop column apto;--.go

alter table historicocandidato add column apto character(1);--.go

update historicocandidato set apto=aptoTmp;--.go

alter table historicocandidato drop column aptoTmp;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (501, 'ROLE_CAD_GRUPOAC', 'Grupos AC', '/geral/grupoAC/list.action', 6, true, 390);--.go

alter sequence papel_sequence restart with 502;--.go

CREATE TABLE grupoac (
    id bigint NOT NULL,
    codigo character varying(3) NOT NULL,
    descricao character varying(20) NOT NULL
);--.go
ALTER TABLE grupoac ADD CONSTRAINT grupoac_pkey PRIMARY KEY (id);--.go
CREATE SEQUENCE grupoac_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
alter table GRUPOAC add constraint grupoac_codigo_uk unique (codigo);--.go

alter table empresa add column grupoac character(3);--.go