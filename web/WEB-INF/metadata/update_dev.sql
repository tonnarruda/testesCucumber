update parametrosdosistema set appversao = '1.1.37.27';--.go


alter table candidato add column idF2RH int; --.go

alter table solicitacao add column liberador_id bigint; --.go
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_usuario_fk FOREIGN KEY (liberador_id) REFERENCES usuario(id); --.go
