CREATE TABLE turma_documentoanexo (
	id bigint NOT NULL,
    turma_id bigint NOT NULL,
    documentoanexos_id bigint NOT NULL
); --.go
ALTER TABLE turma_documentoanexo ADD CONSTRAINT turma_documentoanexo_documentoanexo_fk FOREIGN KEY (documentoanexos_id) REFERENCES documentoanexo(id); --.go
ALTER TABLE turma_documentoanexo ADD CONSTRAINT turma_documentoanexo_turma_fk FOREIGN KEY (turma_id) REFERENCES turma(id); --.go
CREATE SEQUENCE turma_documentoanexo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go