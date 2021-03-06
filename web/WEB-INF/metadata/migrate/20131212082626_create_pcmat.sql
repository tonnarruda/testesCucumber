CREATE TABLE pcmat (
	id bigint NOT NULL,
	apartirde date NOT NULL,
	datainiobra date NOT NULL,
	datafimobra date NOT NULL,
	qtdfuncionarios integer,
	obra_id bigint,
	objetivo text,
	textocondicoestrabalho text,
	textoareasvivencia text,
	textoatividadesseguranca text,
	textoepis text,
	textoepcs text,
	textosinalizacao text
);--.go

ALTER TABLE pcmat ADD CONSTRAINT pcmat_pkey PRIMARY KEY(id);--.go
ALTER TABLE pcmat ADD CONSTRAINT pcmat_obra_fk FOREIGN KEY (obra_id) REFERENCES obra(id);--.go
CREATE SEQUENCE pcmat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go