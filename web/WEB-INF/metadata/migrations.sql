	ALTER TABLE solicitacaoexame ADD COLUMN faixasalarial_id bigint; --.go
	ALTER TABLE ONLY solicitacaoexame ADD CONSTRAINT solicitacaoexame_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id); --.go
	
	ALTER TABLE solicitacaoexame ADD COLUMN candidatosolicitacao_id bigint; --.go
	ALTER TABLE ONLY solicitacaoexame ADD CONSTRAINT solicitacaoexame_candidatosolicitacao_fk FOREIGN KEY (candidatosolicitacao_id) REFERENCES candidatosolicitacao(id); --.go