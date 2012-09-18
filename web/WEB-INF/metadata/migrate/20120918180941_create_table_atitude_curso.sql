CREATE TABLE atitude_curso(
  atitudes_id bigint NOT NULL,
  cursos_id bigint NOT NULL
);--.go
ALTER TABLE atitude_curso ADD CONSTRAINT atitude_curso_atitude_fk FOREIGN KEY (atitudes_id) REFERENCES atitude (id); --.go
ALTER TABLE atitude_curso ADD CONSTRAINT atitude_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso (id); --.go
