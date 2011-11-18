CREATE TABLE turma_avaliacaoturma (
    turma_id bigint NOT NULL,
    avaliacaoturmas_id bigint NOT NULL
); --.go
ALTER TABLE turma_avaliacaoturma ADD CONSTRAINT turma_avaliacaoturma_avaliacaoturma_fk FOREIGN KEY (avaliacaoturmas_id) REFERENCES avaliacaoturma(id); --.go
ALTER TABLE turma_avaliacaoturma ADD CONSTRAINT turma_avaliacaoturma_turma_fk FOREIGN KEY (turma_id) REFERENCES turma(id); --.go
