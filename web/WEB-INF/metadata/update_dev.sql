update parametrosdosistema set appversao = '1.1.49.41';

alter table parametrosdosistema add column compartilharColaboradores boolean default true;--.go
alter table parametrosdosistema add column compartilharCandidatos boolean default true;--.go