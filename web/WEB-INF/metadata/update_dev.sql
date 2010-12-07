update parametrosdosistema set appversao = '1.1.34.24';--.go

ALTER TABLE reajusteColaborador ALTER COLUMN observacao TYPE text;--go

CREATE TABLE ConfiguracaoPerformance (
    id bigint NOT NULL,
    usuario_id bigint,
	caixa character varying(10),
	ordem integer,
	aberta boolean
); --.go
ALTER TABLE ONLY configuracaoPerformance ADD CONSTRAINT configuracaoPerformance_pkey PRIMARY KEY (id); --.go
ALTER TABLE configuracaoPerformance ADD CONSTRAINT configuracaoPerformance_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id); --.go
CREATE SEQUENCE configuracaoPerformance_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go