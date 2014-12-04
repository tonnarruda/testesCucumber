-- Replica a providencia para todos os itens da duplicacao --.go
UPDATE colaboradorocorrencia co SET providencia_id = ( SELECT MAX(co2.providencia_id) FROM colaboradorocorrencia co2 WHERE co2.colaborador_id = co.colaborador_id AND co2.ocorrencia_id = co.ocorrencia_id AND co2.dataini = co.dataini );--.go

-- Resolve as duplicacoes --.go
DELETE FROM colaboradorocorrencia WHERE id NOT IN ( SELECT MIN(id) FROM colaboradorocorrencia GROUP BY dataini, colaborador_id, ocorrencia_id );--.go

-- Cria a constraint --.go
ALTER TABLE colaboradorocorrencia ADD CONSTRAINT colaboradorocorrencia_colaborador_ocorrencia_data_uk UNIQUE (colaborador_id, ocorrencia_id, dataini);--.go