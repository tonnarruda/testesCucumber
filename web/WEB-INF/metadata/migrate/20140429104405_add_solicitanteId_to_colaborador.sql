ALTER TABLE colaborador ADD COLUMN solicitanteDemissao_id bigint;--.go
ALTER TABLE colaborador ADD CONSTRAINT colaborador_solicitanteDemissao_fk FOREIGN KEY (solicitanteDemissao_id) REFERENCES colaborador(id);--.go