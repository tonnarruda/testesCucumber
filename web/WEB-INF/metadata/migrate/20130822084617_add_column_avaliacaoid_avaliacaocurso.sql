alter table avaliacaocurso add column avaliacao_id bigint;--.go
ALTER TABLE ONLY avaliacaocurso ADD CONSTRAINT avaliacaocurso_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go 