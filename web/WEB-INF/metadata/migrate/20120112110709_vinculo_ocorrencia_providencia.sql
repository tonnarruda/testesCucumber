alter table colaboradorocorrencia add column providencia_id bigint;--.go
ALTER TABLE colaboradorocorrencia ADD CONSTRAINT colaboradorocorrencia_providencia_fk FOREIGN KEY (providencia_id) REFERENCES providencia(id);--.go