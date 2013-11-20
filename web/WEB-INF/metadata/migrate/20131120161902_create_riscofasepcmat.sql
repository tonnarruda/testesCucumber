CREATE TABLE riscofasepcmat (
	id bigint NOT NULL,
	risco_id bigint,
	fasepcmat_id bigint
);--.go

ALTER TABLE riscofasepcmat ADD CONSTRAINT riscofasepcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE riscofasepcmat ADD CONSTRAINT riscofasepcmat_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);--.go
ALTER TABLE riscofasepcmat ADD CONSTRAINT riscofasepcmat_fasepcmat_fk FOREIGN KEY (fasepcmat_id) REFERENCES fasepcmat(id);--.go
CREATE SEQUENCE riscofasepcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go