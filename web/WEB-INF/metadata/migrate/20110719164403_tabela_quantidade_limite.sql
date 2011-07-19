drop table quantidadeLimiteColaboradoresPorCargo;--.go

CREATE TABLE quantidadeLimiteColaboradoresPorCargo (
	id bigint NOT NULL,
	areaorganizacional_id bigint,
	cargo_id bigint,
	limite integer NOT NULL
);--.go

ALTER TABLE ONLY quantidadelimitecolaboradoresporcargo ADD CONSTRAINT quantidadelimitecolaboradoresporcargo_pkey PRIMARY KEY (id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go

ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_area_uk UNIQUE (cargo_id, areaorganizacional_id);--.go

CREATE SEQUENCE quantidadelimitecolaboradoresporcargo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go