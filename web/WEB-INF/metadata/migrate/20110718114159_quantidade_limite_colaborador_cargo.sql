CREATE TABLE quantidadeLimiteColaboradoresPorCargo (
areaorganizacional_id bigint,
cargo_id bigint,
limite integer NOT NULL
);--.go

ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_area_uk UNIQUE (cargo_id, areaorganizacional_id);--.go