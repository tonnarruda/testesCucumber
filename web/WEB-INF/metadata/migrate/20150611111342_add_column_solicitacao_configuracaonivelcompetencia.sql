alter table configuracaonivelcompetencia add column solicitacao_id bigint;--.go
ALTER TABLE ONLY configuracaonivelcompetencia ADD CONSTRAINT configuracaonivelcompetencia_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);--.go 