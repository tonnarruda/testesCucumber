update parametrosdosistema set appversao = '1.1.45.37';--.go

alter table cargo add column complementoConhecimento character varying(120);--.go

alter table candidato alter column colocacao type character varying(1);--.go
update parametrosdosistema set camposcandidatotabs = 'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais,abaCurriculo';--.go