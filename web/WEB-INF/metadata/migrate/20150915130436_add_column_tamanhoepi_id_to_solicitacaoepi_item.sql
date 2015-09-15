ALTER TABLE solicitacaoepi_item ADD COLUMN tamanhoepi_id bigint;--.go 
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_tamanhoepi_fk FOREIGN KEY (tamanhoepi_id) REFERENCES tamanhoepi(id);--.go
