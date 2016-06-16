
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (673,'ROLE_MOV_ORDEM_DE_SERVICO', 'Gerenciamento de Ordem de Serviço', '/sesmt/ordemDeServico/listGerenciamentoOS.action', 11, true, 386);--.go
alter sequence papel_sequence restart with 674;--.go


CREATE TABLE ordemDeServico (
	id bigint NOT NULL,
	colaborador_id bigint NOT NULL,
	nomeColaborador character varying(100),
	dataAdmisaoColaborador date,
	codigoCBO character varying(6),
	nomeFuncao character varying(100),
	nomeEmpresa character varying(100),
	data timestamp without time zone,
	revisao double precision,
	atividades text,
	riscos text,
	epis text,
	medidasDePrevencao text,
	treinamentos text,
	normasInternas text,
	procedimentoEmCasoDeAcidente text,
	termoDeResponsabilidade text
);--.go

ALTER TABLE ordemDeServico ADD CONSTRAINT ordemDeServico_pkey PRIMARY KEY(id);--.go
ALTER TABLE ordemDeServico ADD CONSTRAINT ordemDeServico_colaborador_id_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
CREATE SEQUENCE ordemDeServico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE historicofuncao_curso (
    historicofuncao_id bigint NOT NULL,
    cursos_id bigint NOT NULL
);--.go

ALTER TABLE historicofuncao_curso ADD CONSTRAINT historicofuncao_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id);--.go
ALTER TABLE historicofuncao_curso ADD CONSTRAINT historicofuncao_curso_historicofuncao_fk FOREIGN KEY (historicofuncao_id) REFERENCES historicofuncao(id);--.go

alter table empresa add column normasInternas text;--.go
alter table empresa add column procedimentoEmCasoDeAcidente text;--.go
alter table empresa add column termoDeResponsabilidade text;--.go