update parametrosdosistema set appversao = '1.1.42.33';--.go

ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoVisivel text;--.go
ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoObrigatorio text;--.go
ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoTabs text;--.go

alter table parametrosdosistema add column emailCandidatoNaoApto boolean;--.go
update parametrosdosistema set emailCandidatoNaoApto=enviaremail;--.go
update parametrosdosistema set enviaremail=true;--.go

update papel set url='#',codigo='ROLE_CONFIGURACAO' where id=41;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (502, 'ROLE_UTI_CONFIGURACAO', 'Sistema', '/geral/parametrosDoSistema/prepareUpdate.action', 1, true, 41);--.go
update papel set papelmae_id=41,ordem=2 where id=485;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (503, 'ROLE_CONFIG_CANDIDATO_EXT', 'Cadastro de Candidato (externo)', '/geral/parametrosDoSistema/listCamposCandidato.action', 3, true, 41);--.go

alter sequence papel_sequence restart with 504;--.go