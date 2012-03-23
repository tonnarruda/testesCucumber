CREATE TABLE solicitacaoepiitementrega (
	id bigint NOT NULL,
	solicitacaoepiitem_id bigint NOT NULL,
	qtdEntregue integer NOT NULL,
	dataEntrega date NOT NULL
);--.go

ALTER TABLE solicitacaoepiitementrega ADD CONSTRAINT solicitacaoepiitementrega_pkey PRIMARY KEY (id);--.go
ALTER TABLE solicitacaoepiitementrega ADD CONSTRAINT solicitacaoepiitementrega_solicitacaoepi_item_fk FOREIGN KEY (solicitacaoepiitem_id) REFERENCES solicitacaoepi_item(id);--.go
CREATE SEQUENCE solicitacaoepiitementrega_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go