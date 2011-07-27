update parametrosdosistema set appversao = '1.1.51.43';--.go

-- versao 1.1.52.44

CREATE TABLE configuracaoLimiteColaborador (
id bigint NOT NULL,
descricao character varying(100) NOT NULL,
areaorganizacional_id bigint
);--.go

ALTER TABLE configuracaoLimiteColaborador ADD CONSTRAINT configuracaoLimiteColaborador_pkey PRIMARY KEY(id);--.go
ALTER TABLE configuracaoLimiteColaborador ADD CONSTRAINT configuracaoLimiteColaborador_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);--.go
ALTER TABLE configuracaoLimiteColaborador ADD CONSTRAINT configuracaoLimiteColaborador_areaorganizacional_uk UNIQUE (areaorganizacional_id);--.go
CREATE SEQUENCE configuracaoLimiteColaborador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
-- migration 20110718095432
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (514, 'ROLE_CONFIG_LIMITE_COLABORADOR', 'Limite de Colab. por Cargo', '/geral/configuracaoLimiteColaborador/list.action', 4, true, 41);--.go

alter sequence papel_sequence restart with 515;--.go
-- migration 20110718103503
CREATE TABLE quantidadeLimiteColaboradoresPorCargo (
areaorganizacional_id bigint,
cargo_id bigint,
limite integer NOT NULL
);--.go

ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_area_uk UNIQUE (cargo_id, areaorganizacional_id);--.go
-- migration 20110718114159
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=514;--.go
-- migration 20110719093045
drop table quantidadeLimiteColaboradoresPorCargo;--.go

CREATE TABLE quantidadeLimiteColaboradoresPorCargo (
	id bigint NOT NULL,
	areaorganizacional_id bigint,
	cargo_id bigint,
	limite integer NOT NULL
);--.go

ALTER TABLE ONLY quantidadelimitecolaboradoresporcargo ADD CONSTRAINT quantidadelimitecolaboradoresporcargo_pkey PRIMARY KEY (id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);--.go
ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go

ALTER TABLE quantidadeLimiteColaboradoresPorCargo ADD CONSTRAINT quantidadeLimiteColaboradoresPorCargo_cargo_area_uk UNIQUE (cargo_id, areaorganizacional_id);--.go

CREATE SEQUENCE quantidadelimitecolaboradoresporcargo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
-- migration 20110719164403
alter table pesquisa add column exibirPerformanceProfissional boolean default false;--.go
-- migration 20110722151723
alter table empresa add column emailresplimitecontrato character varying(120) default '';--.go
-- migration 20110725093103
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (515, 'ROLE_INVESTIMENTO_TREINAMENTO_COLAB', 'Investimento por Colaborador', '/desenvolvimento/turma/relatorioInvestimentoPorColaborador.action', 13, true, 368);--.go
alter sequence papel_sequence restart with 515;--.go
-- migration 20110726092155
update parametrosdosistema set appversao = '1.1.52.44';--.go
