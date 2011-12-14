
CREATE TABLE composicaoSesmt (
	id bigint NOT NULL,
	empresa_id bigint,
	data Date NOT NULL,
	qtdTecnicosSeguranca integer, 
	qtdEngenheirosSeguranca integer,
	qtdAuxiliaresEnfermagem integer,
	qtdEnfermeiros integer,         
	qtdMedicos integer              
);--.go

ALTER TABLE composicaoSesmt ADD CONSTRAINT composicaoSesmt_pkey PRIMARY KEY(id);--.go
ALTER TABLE composicaoSesmt ADD CONSTRAINT composicaoSesmt_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE composicaoSesmt_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go