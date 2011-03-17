update parametrosdosistema set appversao = '1.1.42.33';--.go

alter table parametrosdosistema add column emailCandidatoNaoApto boolean;
update parametrosdosistema set emailCandidatoNaoApto=enviaremail;
update parametrosdosistema set enviaremail=true;