DROP INDEX IF EXISTS index_solicitacaoexame_data_empresa_id;--.go
CREATE INDEX index_solicitacaoexame_data_empresa_id ON solicitacaoexame (data,empresa_id);--.go