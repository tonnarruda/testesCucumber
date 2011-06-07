update parametrosdosistema set appversao = '1.1.47.39';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (510, 'ROLE_MOV_PALOGRAFICO', 'Exame Palográfico', '/captacao/candidato/prepareExamePalografico.action', 3, true, 359);--.go
alter sequence papel_sequence restart with 511;--.go

alter table candidato add column examepalografico text;--.go