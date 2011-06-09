update parametrosdosistema set appversao = '1.1.48.40';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (510, 'ROLE_MOV_PALOGRAFICO', 'Exame Palográfico', '/captacao/candidato/prepareExamePalografico.action', 3, true, 359);--.go
alter sequence papel_sequence restart with 511;--.go

update papel set papelmae_id = 463 where id=510;--.go