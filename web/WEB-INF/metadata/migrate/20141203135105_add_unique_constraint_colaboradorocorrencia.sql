CREATE INDEX colaboradorocorrencia_idx ON colaboradorocorrencia(colaborador_id, ocorrencia_id, dataini);--.go
DELETE FROM colaboradorocorrencia co WHERE co.id NOT IN (SELECT co2.id FROM colaboradorocorrencia co2 WHERE co2.colaborador_id = co.colaborador_id AND co2.ocorrencia_id = co.ocorrencia_id AND co2.dataini = co.dataini ORDER BY co2.providencia_id NULLS LAST LIMIT 1);--.go