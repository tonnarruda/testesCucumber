
alter table certificacao add column periodicidade integer;--.go


--Verificar id do papel quando for criar a migrate
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (646,'ROLE_AVALIACAO_PRATICA', 'Avaliação Prática', '/avaliacao/avaliacaoPratica/list.action', 8, true, 366);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 646);--.go
alter sequence papel_sequence restart with 647;--.go

CREATE TABLE avaliacaoPratica (
id bigint NOT NULL,
titulo character varying(100),
notaMinima double precision,
empresa_id bigint
);--.go

ALTER TABLE avaliacaoPratica ADD CONSTRAINT avaliacaoPratica_pkey PRIMARY KEY(id);--.go
ALTER TABLE avaliacaoPratica ADD CONSTRAINT avaliacaoPratica_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE avaliacaoPratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE certificacao_avaliacaopratica (
	certificacao_id bigint NOT NULL,
	avaliacoesPraticas_id bigint NOT NULL
); --.go

ALTER TABLE certificacao_avaliacaopratica ADD CONSTRAINT certificacao_avaliacaopratica_certificacao_fk FOREIGN KEY (certificacao_id) REFERENCES certificacao(id);--.go
ALTER TABLE certificacao_avaliacaopratica ADD CONSTRAINT certificacao_avaliacaopratica_avaliacaoPraticas_fk FOREIGN KEY (avaliacoespraticas_id) REFERENCES avaliacaopratica(id);--.go
CREATE SEQUENCE certificacao_avaliacaopratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

----

CREATE TABLE colaboradorAvaliacaoPratica (
id bigint NOT NULL,
avaliacaoPratica_id bigint,
certificacao_id bigint,
colaborador_id bigint,
data date,
nota double precision
);--.go

ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT colaboradorAvaliacaoPratica_pkey PRIMARY KEY(id);--.go
ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT cap_avaliacaoPratica_fk FOREIGN KEY (avaliacaoPratica_id) REFERENCES avaliacaoPratica(id);--.go
ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT cap_certificacao_fk FOREIGN KEY (certificacao_id) REFERENCES certificacao(id);--.go
ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT cap_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
CREATE SEQUENCE colaboradorAvaliacaoPratica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

--OBS: Remover colaboradorAvaliacaoPratica em remover colaborador com dependências 


--Verificar id do papel quando for criar a migrate
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (647,'ROLE_COLABORADOR_AVALIACAO_PRATICA', 'Notas da Avaliação Prática', '/desenvolvimento/colaboradorAvaliacaoPratica/prepare.action', 6, true, 367);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 647);--.go
alter sequence papel_sequence restart with 648;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id, help) VALUES (648,'ROLE_REL_CERTIFICACOES_VENCIDAS_A_VENCER', 'Certificações Vencidas e a Vencer', '/desenvolvimento/turma/prepareImprimirCertificacoesVencidasAVencer.action', 16, true, 368,'Esta permissão estará visível se a configuração realizada no cadastro da empresa na opção "Controlar vencimento da certificação" estiver por: Periodicidade da certificação');--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 648);--.go
ALTER sequence papel_sequence restart WITH 649;--.go

ALTER TABLE empresa ADD COLUMN controlarVencimentoCertificacaoPor integer default 1;--.go

update papel set help='Esta permissão estará visível se a configuração realizada no cadastro da empresa na opção "Controlar vencimento da certificação" estiver por: Periodicidade do curso' where id = 635;--.go