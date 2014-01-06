CREATE TABLE medidariscofasepcmat (
	id bigint NOT NULL,
	medidaseguranca_id bigint,
	riscofasepcmat_id bigint
);--.go

ALTER TABLE medidariscofasepcmat ADD CONSTRAINT medidariscofasepcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE medidariscofasepcmat ADD CONSTRAINT medidariscofasepcmat_medidaseguranca_fk FOREIGN KEY (medidaseguranca_id) REFERENCES medidaseguranca(id);--.go
ALTER TABLE medidariscofasepcmat ADD CONSTRAINT medidariscofasepcmat_riscofasepcmat_fk FOREIGN KEY (riscofasepcmat_id) REFERENCES riscofasepcmat(id) ON DELETE CASCADE;--.go
CREATE SEQUENCE medidariscofasepcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go