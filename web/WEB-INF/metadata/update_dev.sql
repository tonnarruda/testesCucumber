update parametrosdosistema set appversao = '1.1.42.33';--.go

ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoVisivel text;--.go
ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoObrigatorio text;--.go
ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoTabs text;--.go

alter table parametrosdosistema add column emailCandidatoNaoApto boolean;--.go
update parametrosdosistema set emailCandidatoNaoApto=enviaremail;--.go
update parametrosdosistema set enviaremail=true;--.go