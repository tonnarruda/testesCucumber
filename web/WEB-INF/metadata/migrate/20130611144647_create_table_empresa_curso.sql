CREATE TABLE curso_empresa (
    cursos_id bigint NOT NULL,
    empresasParticipantes_id bigint NOT NULL
);--.go

ALTER TABLE ONLY curso_empresa ADD CONSTRAINT curso_empresa_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id);--.go
ALTER TABLE ONLY curso_empresa ADD CONSTRAINT curso_empresa_empresa_fk FOREIGN KEY (empresasParticipantes_id) REFERENCES empresa(id);--.go