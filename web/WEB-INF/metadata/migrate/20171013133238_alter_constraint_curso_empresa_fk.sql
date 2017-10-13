ALTER TABLE ONLY curso DROP CONSTRAINT IF EXISTS curso_empresa;--.go
ALTER TABLE ONLY curso ADD CONSTRAINT curso_empresa_FK FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go