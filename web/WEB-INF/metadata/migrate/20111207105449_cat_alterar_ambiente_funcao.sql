alter table cat drop column ambiente_id;--.go
alter table cat add column ambienteColaborador_id bigint;--.go
ALTER TABLE cat ADD CONSTRAINT cat_ambienteColaborador_fk FOREIGN KEY (ambienteColaborador_id) REFERENCES ambiente(id);--.go
alter table cat add column funcaoColaborador_id bigint;--.go
ALTER TABLE cat ADD CONSTRAINT cat_funcaoColaborador_fk FOREIGN KEY (funcaoColaborador_id) REFERENCES funcao(id);--.go
alter table cat add column local character varying(100);--.go