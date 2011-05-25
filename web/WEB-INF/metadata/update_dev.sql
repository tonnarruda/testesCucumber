update parametrosdosistema set appversao = '1.1.45.37';--.go

alter table cargo add column complementoConhecimento character varying(120);--.go

alter table candidato alter column colocacao type character varying(1);--.go
update parametrosdosistema set camposcandidatotabs = 'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais,abaCurriculo';--.go

ALTER TABLE candidato ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE colaborador ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE bairro ALTER COLUMN nome TYPE character varying(85);--.go
ALTER TABLE estabelecimento ALTER COLUMN bairro TYPE character varying(85);--.go

update papel set nome = 'Acompanhamento do Período de Experiência' where id=490;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (507, 'ROLE_COMO_FICOU_SABENDO_VAGA', 'Como Ficou Sabendo da Vaga', '/geral/comoFicouSabendoVaga/list.action', 8, true, 358);--.go
alter sequence papel_sequence restart with 508;--.go

create table comoFicouSabendoVaga (id bigint not null, nome character varying(100));--.go
ALTER TABLE ONLY comoFicouSabendoVaga ADD CONSTRAINT comoFicouSabendoVaga_pkey PRIMARY KEY (id); --.go
CREATE SEQUENCE comoFicouSabendoVaga_sequence START WITH 2 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go

ALTER TABLE candidato add COLUMN comoFicouSabendoVaga_id bigint;--.go
ALTER TABLE candidato ADD CONSTRAINT candidato_comoFicouSabendoVaga_fk FOREIGN KEY (comoFicouSabendoVaga_id) REFERENCES comoFicouSabendoVaga(id); --.go

ALTER TABLE candidato add COLUMN comoFicouSabendoVagaQual character varying(100);--.go
insert into comoFicouSabendoVaga (id, nome) values (1, 'Outro');--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (508, 'ROLE_COMO_FICOU_SABENDO_VAGA', 'Estatística de Divulgação da Vaga', '/geral/comoFicouSabendoVaga/prepareRelatorioComoFicouSabendoVaga.action', 4, true, 360);--.go
alter sequence papel_sequence restart with 509;--.go

ALTER TABLE empresa ADD COLUMN campoExtraColaborador boolean;--.go
update empresa set campoExtraColaborador=(select campoExtraColaborador from parametrosdosistema);--.go
ALTER TABLE parametrosdosistema DROP COLUMN campoExtraColaborador;--.go

alter table ConfiguracaoCampoExtra add column empresa_id bigint;--.go
ALTER TABLE ConfiguracaoCampoExtra ADD CONSTRAINT ConfiguracaoCampoExtra_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go

alter sequence configuracaocampoextra_sequence restart with 17;--.go
ALTER TABLE ConfiguracaoCampoExtra ALTER COLUMN id SET DEFAULT nextval('configuracaocampoextra_sequence');--.go
insert into configuracaocampoextra (ativo,nome,descricao,titulo,ordem,tipo,posicao,empresa_id) select c.ativo ,c.nome ,c.descricao ,c.titulo ,c.ordem ,c.tipo ,c.posicao ,e.id from ConfiguracaoCampoExtra c join empresa e on 1=1 order by e.id,c.posicao;--.go

ALTER TABLE ConfiguracaoCampoExtra ALTER COLUMN id drop DEFAULT;--.go

--ALTERAR inserts do ConfiguracaoCampoExtra, precisamos passar empresaPadrão 1
--colocar no papocs empresaPadrão

INSERT INTO extintorInspecaoItem (id, descricao) VALUES (11, 'Outro'); --.go
alter table extintorInspecao add column outroMotivo character(50);--.go

ALTER TABLE ConfiguracaoCampoExtra RENAME COLUMN ativo TO ativoColaborador;--.go
ALTER TABLE ConfiguracaoCampoExtra ADD COLUMN ativoCandidato boolean default false;--.go
ALTER TABLE empresa ADD COLUMN campoextracandidato boolean default false;--.go

update papel set nome = 'Estatística de Divulgação da Vaga' where id = 508;--.go