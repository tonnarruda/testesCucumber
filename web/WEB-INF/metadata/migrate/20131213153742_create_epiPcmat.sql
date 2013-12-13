CREATE TABLE epipcmat (
	id bigint NOT NULL,
	epi_id bigint,
	pcmat_id bigint,
	atividades text
);--.go

ALTER TABLE epipcmat ADD CONSTRAINT epipcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE epipcmat ADD CONSTRAINT epipcmat_epi_fk FOREIGN KEY (epi_id) REFERENCES epi(id);--.go
ALTER TABLE epipcmat ADD CONSTRAINT epipcmat_pcmat_fk FOREIGN KEY (pcmat_id) REFERENCES pcmat(id);--.go
CREATE SEQUENCE epipcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go