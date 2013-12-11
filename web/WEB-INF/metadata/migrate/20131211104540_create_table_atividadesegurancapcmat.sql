CREATE TABLE atividadeSegurancaPcmat (
	id bigint NOT NULL,
	nome character varying(200),
	data date,
	cargaHoraria integer,
	pcmat_id bigint
);--.go

ALTER TABLE atividadeSegurancaPcmat ADD CONSTRAINT atividadeSegurancaPcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE atividadeSegurancaPcmat ADD CONSTRAINT atividadeSegurancaPcmat_pcmat_fk FOREIGN KEY (pcmat_id) REFERENCES pcmat(id);--.go
CREATE SEQUENCE atividadeSegurancaPcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go