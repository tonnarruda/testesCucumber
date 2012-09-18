CREATE TABLE conhecimento_curso(
  conhecimentos_id bigint NOT NULL,
  cursos_id bigint NOT NULL
);--.go
ALTER TABLE conhecimento_curso ADD CONSTRAINT conhecimento_curso_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento (id); --.go
ALTER TABLE conhecimento_curso ADD CONSTRAINT conhecimento_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso (id); --.go
