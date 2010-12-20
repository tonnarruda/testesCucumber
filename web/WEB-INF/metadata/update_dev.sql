update parametrosdosistema set appversao = '1.1.36.26';--.go

alter table conhecimento add column observacao text; --.go
alter table habilidade add column observacao text; --.go
alter table atitude add column observacao text; --.go

update papel set nome = 'Modelos de Avaliação do Candidato' where id = 492;--.go
update papel set nome = 'Avaliações de Desempenho/Acomp. do Período de Experiência' where id = 482;--.go
update avaliacao set tipomodeloavaliacao = 'A' where tipomodeloavaliacao <> 'S';--.go
