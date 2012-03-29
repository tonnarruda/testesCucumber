alter table medicaorisco add column funcao_id bigint;--.go
ALTER TABLE medicaorisco ADD CONSTRAINT medicaorisco_funcao_fk FOREIGN KEY (funcao_id) REFERENCES funcao(id);--.go