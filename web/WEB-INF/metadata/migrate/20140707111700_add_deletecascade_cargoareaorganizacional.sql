ALTER TABLE cargo_areaorganizacional DROP CONSTRAINT cargo_areaorganizacional_cargo_fk;--.go 
ALTER TABLE cargo_areaorganizacional ADD CONSTRAINT cargo_areaorganizacional_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id) ON DELETE CASCADE;--.go