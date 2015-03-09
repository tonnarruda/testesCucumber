UPDATE experiencia set cargo_id = null where cargo_id not in(select id from cargo);--.go
ALTER TABLE experiencia DROP CONSTRAINT IF EXISTS experiencia_cargo_fk;--.go
ALTER TABLE ONLY experiencia ADD CONSTRAINT experiencia_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go
