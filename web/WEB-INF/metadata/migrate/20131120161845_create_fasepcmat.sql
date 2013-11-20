CREATE TABLE fasepcmat (
	id bigint NOT NULL,
	fase_id bigint,
	pcmat_id bigint
);--.go

ALTER TABLE fasepcmat ADD CONSTRAINT fasepcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE fasepcmat ADD CONSTRAINT fasepcmat_fase_fk FOREIGN KEY (fase_id) REFERENCES fase(id);--.go
ALTER TABLE fasepcmat ADD CONSTRAINT fasepcmat_pcmat_fk FOREIGN KEY (pcmat_id) REFERENCES pcmat(id);--.go
CREATE SEQUENCE fasepcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go