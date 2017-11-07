
CREATE TABLE usuarioajudaesocial (
	id bigint NOT NULL,
	usuario_id bigint NOT NULL,
	telaAjuda character varying(40) NOT NULL 
);--.go

ALTER TABLE usuarioajudaesocial ADD CONSTRAINT usuarioajudaesocial_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE usuarioajudaesocial_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE ONLY usuarioajudaesocial ADD CONSTRAINT usuarioajudaesocial_usario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);--.go