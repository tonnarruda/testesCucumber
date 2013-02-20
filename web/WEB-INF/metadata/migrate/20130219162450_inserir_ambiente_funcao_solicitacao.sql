alter table solicitacao add column ambiente_id bigint;--.go
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);--.go

alter table solicitacao add column funcao_id bigint;--.go
ALTER TABLE solicitacao ADD CONSTRAINT funcao_ambiente_fk FOREIGN KEY (funcao_id) REFERENCES funcao(id);--.go