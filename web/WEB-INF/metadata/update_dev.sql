update parametrosdosistema set appversao = '1.1.45.37';--.go

alter table cargo add column complementoConhecimento character varying(120);--.go

alter table candidato alter column colocacao type character varying(1);--.go
update parametrosdosistema set camposcandidatotabs = 'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais,abaCurriculo';--.go

ALTER TABLE candidato ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE colaborador ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE bairro ALTER COLUMN nome TYPE character varying(85);--.go
ALTER TABLE estabelecimento ALTER COLUMN bairro TYPE character varying(85);--.go

update papel set nome = 'Acompanhamento do Período de Experiência' where id=490;--.go

ALTER TABLE candidato add COLUMN comoFicouSabendoVaga character varying(30);--.go