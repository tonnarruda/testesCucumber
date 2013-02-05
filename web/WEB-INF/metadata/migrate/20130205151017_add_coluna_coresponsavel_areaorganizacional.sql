ALTER TABLE areaorganizacional ADD COLUMN coResponsavel_id bigint;--go

ALTER TABLE ONLY areaorganizacional ADD CONSTRAINT areaorganizacional_coResponsavel_fk FOREIGN KEY (coResponsavel_id) REFERENCES colaborador(id);--go