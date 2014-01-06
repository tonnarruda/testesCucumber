CREATE TABLE areaVivenciaPcmat (
	id bigint NOT NULL,
	areaVivencia_id bigint,
	pcmat_id bigint,
	descricao text
);--.go

ALTER TABLE areaVivenciaPcmat ADD CONSTRAINT areaVivenciaPcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE areaVivenciaPcmat ADD CONSTRAINT areaVivenciaPcmat_areaVivencia_fk FOREIGN KEY (areaVivencia_id) REFERENCES areaVivencia(id);--.go
ALTER TABLE areaVivenciaPcmat ADD CONSTRAINT areaVivenciaPcmat_pcmat_fk FOREIGN KEY (pcmat_id) REFERENCES pcmat(id);--.go
CREATE SEQUENCE areaVivenciaPcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go