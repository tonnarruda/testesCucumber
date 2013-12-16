CREATE TABLE epcpcmat (
	id bigint NOT NULL,
	epc_id bigint,
	pcmat_id bigint,
	descricao text
);--.go

ALTER TABLE epcpcmat ADD CONSTRAINT epcpcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE epcpcmat ADD CONSTRAINT epcpcmat_epc_fk FOREIGN KEY (epc_id) REFERENCES epc(id);--.go
ALTER TABLE epcpcmat ADD CONSTRAINT epcpcmat_pcmat_fk FOREIGN KEY (pcmat_id) REFERENCES pcmat(id);--.go
CREATE SEQUENCE epcpcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go