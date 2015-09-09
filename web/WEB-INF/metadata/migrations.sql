CREATE TABLE tamanhoepi (
	id bigint NOT NULL,
    descricao character varying(30) NOT NULL
); --.go

ALTER TABLE tamanhoepi ADD CONSTRAINT tamanhoepi_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE tamanhoepi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

----------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE tipo_tamanhoepi (
	id bigint NOT NULL,
	tipoepi_id bigint NOT NULL,
    tamanhoEPIs_id bigint NOT NULL,
    ativo boolean NOT NULL default true
); --.go

ALTER TABLE tipo_tamanhoepi ADD CONSTRAINT tipo_tamanhoepi_tipoepi_fk FOREIGN KEY (tipoepi_id) REFERENCES tipoepi(id);--.go
ALTER TABLE tipo_tamanhoepi ADD CONSTRAINT tipo_tamanhoepi_tamanhoepi_fk FOREIGN KEY (tamanhoEPIs_id) REFERENCES tamanhoepi(id);--.go
CREATE SEQUENCE tipo_tamanhoepi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

----------------------------------------------------------------------------------------------------------------------------------

UPDATE papel set ordem = ordem + 1 where papelmae_id = 385;--.go  
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (637, 'ROLE_CAD_TAMANHO_EPI', 'Tamanhos de EPI/Fardamento', '/sesmt/tamanhoEPI/list.action', 1, true, 385);--.go  
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 637);--.go
ALTER sequence papel_sequence restart WITH 638;--.go 

----------------------------------------------------------------------------------------------------------------------------------

ALTER TABLE solicitacaoepi_item ADD COLUMN tamanhoepi_id bigint;--.go 
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_tamanhoepi_fk FOREIGN KEY (tamanhoepi_id) REFERENCES tamanhoepi(id);--.go
