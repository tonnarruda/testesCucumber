CREATE TABLE noticia (
	id bigint NOT NULL,
	texto text NOT NULL,
	criticidade int NOT NULL,
	link character varying(255) NOT NULL,
	publicada boolean NOT NULL
);--.go

ALTER TABLE noticia ADD CONSTRAINT noticia_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE noticia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go


CREATE TABLE usuarionoticia (
	id bigint NOT NULL,
	usuario_id bigint NOT NULL,
	noticia_id bigint NOT NULL
);--.go

ALTER TABLE usuarionoticia ADD CONSTRAINT usuarionoticia_pkey PRIMARY KEY(id);--.go
ALTER TABLE ONLY usuarionoticia ADD CONSTRAINT usuarionoticia_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);--go
ALTER TABLE ONLY usuarionoticia ADD CONSTRAINT usuarionoticia_noticia_fk FOREIGN KEY (noticia_id) REFERENCES noticia(id);--go
CREATE SEQUENCE usuarionoticia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go