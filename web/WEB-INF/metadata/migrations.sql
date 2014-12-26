UPDATE configuracaocampoextra set nome = 'textolongo1', descricao = 'Campo de Texto Longo 1', tipo = 'textolongo' where nome = 'texto9';--.go
UPDATE configuracaocampoextra set nome = 'textolongo2', descricao = 'Campo de Texto Longo 2', tipo = 'textolongo' where nome = 'texto10';--.go

alter table camposextras rename column texto9 to textolongo1;--.go
alter table camposextras rename column texto10 to textolongo2;--.go
alter table camposextras alter column textolongo1 TYPE text;--.go
alter table camposextras alter column textolongo2 TYPE text;--.go