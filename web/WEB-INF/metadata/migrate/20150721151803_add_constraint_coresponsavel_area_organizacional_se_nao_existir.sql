update areaorganizacional set coresponsavel_id = null where coresponsavel_id not in (select id from colaborador);--.go
ALTER TABLE areaorganizacional DROP CONSTRAINT IF EXISTS areaorganizacional_coresponsavel_fk;--.go
ALTER TABLE ONLY areaorganizacional ADD CONSTRAINT areaorganizacional_coResponsavel_fk FOREIGN KEY (coResponsavel_id) REFERENCES colaborador(id);--.go