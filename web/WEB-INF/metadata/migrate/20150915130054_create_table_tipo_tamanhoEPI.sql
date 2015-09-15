
CREATE TABLE tipo_tamanhoepi (
	id bigint NOT NULL,
	tipoepi_id bigint NOT NULL,
    tamanhoEPIs_id bigint NOT NULL,
    ativo boolean NOT NULL default true
); --.go

ALTER TABLE tipo_tamanhoepi ADD CONSTRAINT tipo_tamanhoepi_tipoepi_fk FOREIGN KEY (tipoepi_id) REFERENCES tipoepi(id);--.go
ALTER TABLE tipo_tamanhoepi ADD CONSTRAINT tipo_tamanhoepi_tamanhoepi_fk FOREIGN KEY (tamanhoEPIs_id) REFERENCES tamanhoepi(id);--.go
CREATE SEQUENCE tipo_tamanhoepi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
