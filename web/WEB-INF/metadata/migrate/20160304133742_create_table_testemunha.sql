CREATE TABLE testemunha (
	id bigint NOT NULL,
    nome character varying(100),
    ddd character varying(2),
    telefone character varying(9),
    logradouro character varying(100),
    numero character varying(10),
    complemento character varying(20),
    bairro character varying(100),
    cep character varying(8),
    municipio character varying(100),
    uf character varying(2)
); --.go

ALTER TABLE testemunha ADD CONSTRAINT testemunha_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE testemunha_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE cat ADD COLUMN testemunha1_id bigint;--.go
ALTER TABLE cat ADD CONSTRAINT testemunha1_fk FOREIGN KEY (testemunha1_id) REFERENCES testemunha(id);--.go

ALTER TABLE cat ADD COLUMN testemunha2_id bigint;--.go
ALTER TABLE cat ADD CONSTRAINT testemunha2_fk FOREIGN KEY (testemunha2_id) REFERENCES testemunha(id);--.go