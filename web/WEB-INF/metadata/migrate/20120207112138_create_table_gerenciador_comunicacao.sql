CREATE TABLE gerenciadorComunicacao (
id bigint NOT NULL,
operacao int,
meioComunicacao int,
enviarPara int,
destinatario character varying(200),
empresa_id bigint
);--.go

ALTER TABLE gerenciadorComunicacao ADD CONSTRAINT gerenciadorComunicacao_pkey PRIMARY KEY(id);--.go
ALTER TABLE gerenciadorComunicacao ADD CONSTRAINT gerenciadorComunicacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE gerenciadorComunicacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go