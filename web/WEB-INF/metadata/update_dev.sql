update parametrosdosistema set appversao = '1.1.36.26';--.go

alter table conhecimento add column observacao text; --.go
alter table habilidade add column observacao text; --.go
alter table atitude add column observacao text; --.go

update papel set nome = 'Modelos de Avaliação do Candidato' where id = 492;--.go
update papel set nome = 'Avaliações de Desempenho/Acomp. do Período de Experiência' where id = 482;--.go
update avaliacao set tipomodeloavaliacao = 'A' where tipomodeloavaliacao <> 'S';--.go

CREATE FUNCTION alter_trigger(CHARACTER, CHARACTER) RETURNS void AS '
BEGIN
	execute ''ALTER TABLE '' || $1 || '' '' || $2 || '' TRIGGER ALL'';
END;
' LANGUAGE plpgsql;--.go

update papel set nome = 'Análise das Etapas Seletivas' where id = 47;--.go