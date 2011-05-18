update parametrosdosistema set appversao = '1.1.45.37';--.go

alter table cargo add column complementoConhecimento character varying(120);--.go

alter table candidato alter column colocacao type character varying(1);--.go
update parametrosdosistema set camposcandidatotabs = 'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais,abaCurriculo';--.go

ALTER TABLE candidato ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE colaborador ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE bairro ALTER COLUMN nome TYPE character varying(85);--.go
ALTER TABLE estabelecimento ALTER COLUMN bairro TYPE character varying(85);--.go

update papel set nome = 'Acompanhamento do Período de Experiência' where id=490;--.go

ALTER TABLE candidato add COLUMN comoFicouSabendoVaga_id bigint;--.go
ALTER TABLE candidato ADD CONSTRAINT candidato_comoFicouSabendoVaga_fk FOREIGN KEY (comoFicouSabendoVaga_id) REFERENCES comoFicouSabendoVaga(id); --.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (507, 'ROLE_COMO_FICOU_SABENDO_VAGA', 'Como Ficou Sabendo da Vaga', '/geral/comoFicouSabendoVaga/list.action', 8, true, 358);--.go
alter sequence papel_sequence restart with 508;--.go

create table comoFicouSabendoVaga (id bigint not null, nome character varying(100));--.go
ALTER TABLE ONLY comoFicouSabendoVaga ADD CONSTRAINT comoFicouSabendoVaga_pkey PRIMARY KEY (id); --.go
CREATE SEQUENCE comoFicouSabendoVaga_sequence START WITH 2 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go

insert into comoFicouSabendoVaga (id, nome) values (1, 'Outro');--.go

ALTER TABLE candidato add COLUMN comoFicouSabendoVagaQual character varying(100);--.go