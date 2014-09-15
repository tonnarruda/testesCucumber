ALTER TABLE mensagem ADD COLUMN avaliacao_id BIGINT;--.go
ALTER TABLE ONLY mensagem ADD CONSTRAINT mensagem_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go