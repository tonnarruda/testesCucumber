UPDATE avaliacaocurso SET avaliacao_id = null WHERE avaliacao_id NOT IN (SELECT id FROM avaliacao);--.go
ALTER TABLE avaliacaocurso DROP CONSTRAINT IF EXISTS avaliacaocurso_avaliacao_fk;--.go
ALTER TABLE ONLY avaliacaocurso ADD CONSTRAINT avaliacaocurso_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go