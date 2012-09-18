CREATE TABLE habilidade_curso(
  habilidades_id bigint NOT NULL,
  cursos_id bigint NOT NULL
);--.go
ALTER TABLE habilidade_curso ADD CONSTRAINT habilidade_curso_habilidade_fk FOREIGN KEY (habilidades_id) REFERENCES habilidade (id); --.go
ALTER TABLE habilidade_curso ADD CONSTRAINT habilidade_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso (id); --.go
