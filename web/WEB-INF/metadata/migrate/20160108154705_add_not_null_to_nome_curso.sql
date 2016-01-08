update curso set nome = '.' where nome is null; --.go
alter table curso alter column nome SET NOT NULL; --.go