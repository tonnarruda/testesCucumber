DELETE FROM colaboradorocorrencia WHERE id NOT IN ( SELECT MIN(id) FROM colaboradorocorrencia GROUP BY dataini, colaborador_id, ocorrencia_id );--.go
ALTER TABLE colaboradorocorrencia ADD CONSTRAINT colaboradorocorrencia_colaborador_ocorrencia_data_uk UNIQUE (colaborador_id, ocorrencia_id, dataini);--.go