CREATE TABLE naturezaLesao (
id bigint NOT NULL,
descricao character varying(100),
empresa_id bigint NOT NULL  
);--.go
ALTER TABLE naturezaLesao ADD CONSTRAINT naturezaLesao_pkey PRIMARY KEY(id);--.go
ALTER TABLE naturezaLesao ADD CONSTRAINT naturezaLesao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE naturezaLesao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go