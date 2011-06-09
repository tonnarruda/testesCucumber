update parametrosdosistema set appversao = '1.1.47.39';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (510, 'ROLE_MOV_PALOGRAFICO', 'Exame Palográfico', '/captacao/candidato/prepareExamePalografico.action', 3, true, 359);--.go
alter sequence papel_sequence restart with 511;--.go

alter table candidato add column examepalografico text;--.go

update historicocolaborador h1 set motivo='C' where h1.data = (select min(h2.data) from historicocolaborador h2 where h1.colaborador_id=h2.colaborador_id);--.go

update historicocolaborador set motivo='P' where motivo='I';--.go

alter table configuracaoimpressaocurriculo add column exibirexamepalografico boolean default false;--.go

update papel set papelmae_id = 463 where id=510;--.go