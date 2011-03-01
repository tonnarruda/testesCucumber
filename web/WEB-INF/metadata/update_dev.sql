update parametrosdosistema set appversao = '1.1.41.32';--.go

alter table historicocandidato add column aptoTmp character(1);--.go

update historicocandidato set aptoTmp='S' where apto=true;--.go
update historicocandidato set aptoTmp='N' where apto=false;--.go

alter table historicocandidato drop column apto;--.go

alter table historicocandidato add column apto character(1);--.go

update historicocandidato set apto=aptoTmp;--.go

alter table historicocandidato drop column aptoTmp;--.go