alter table colaboradorperiodoexperienciaavaliacao add column tipo character(1);--.go
update colaboradorperiodoexperienciaavaliacao set tipo = 'C';--.go
alter table colaboradorperiodoexperienciaavaliacao alter column tipo set not null; --.go