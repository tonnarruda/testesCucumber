CREATE TABLE pausapreenchimentovagas (
id bigint NOT NULL,
solicitacao_id bigint NOT NULL,
dataPausa Date,
dataReinicio Date
);--.go 

ALTER TABLE pausapreenchimentovagas ADD CONSTRAINT pausapreenchimentovagas_pkey PRIMARY KEY(id);--.go 
ALTER TABLE pausapreenchimentovagas ADD CONSTRAINT pausapreenchimentovagas_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);--.go 

CREATE SEQUENCE pausapreenchimentovagas_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go 
