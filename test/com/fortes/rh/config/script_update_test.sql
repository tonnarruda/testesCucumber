-- versao 1.0.1.0
alter table candidato add column observacaorh text; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (397, 'ROLE_AREAFORMACAO', 'Área de Formação', '/geral/areaFormacao/list.action', 5, true, 357); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 397); --.go
alter sequence papel_sequence restart with 397; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (398, 'ROLE_REL_TURNOVER', 'Relatório de Turn Over', '/indicador/indicadorTurnOver/prepare.action', 5, true, 378); --.go

-- versao 1.0.1.1
update parametrosdosistema set appversao = '1.0.1.1'; --.go


-- versao 1.0.1.2
update parametrosdosistema set appversao = '1.0.1.2'; --.go


-- versao 1.0.1.3
update parametrosdosistema set appversao = '1.0.1.3'; --.go
alter table pesquisa add column aplicarporaspecto boolean; --.go


-- versao 1.0.1.4
update parametrosdosistema set appversao = '1.0.1.4'; --.go


-- versao 1.0.2.0
alter table candidato add column deficiencia character(1); --.go
alter table candidato alter column deficiencia set not null; --.go
update parametrosdosistema set appversao = '1.0.2.0'; --.go
CREATE TABLE colaboradorresposta (
    id bigint NOT NULL,
    comentario text,
    valor integer NOT NULL,
    pergunta_id bigint,
    resposta_id bigint,
    colaboradorquestionario_id bigint,
    areaorganizacional_id bigint
); --.go

-- versao 1.0.7.1
update parametrosdosistema set appversao = '1.0.7.1'; --.go