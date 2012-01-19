delete from turmatipodespesa;--.go
delete from tipodespesa;--.go
alter table TipoDespesa add column empresa_id bigint;--.go
ALTER TABLE TipoDespesa ADD CONSTRAINT TipoDespesa_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go