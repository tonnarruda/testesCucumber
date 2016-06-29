
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (675,'ROLE_MOV_ORDEM_DE_SERVICO', 'Gerenciamento de Ordem de Serviço', '/sesmt/ordemDeServico/listGerenciamentoOS.action', 11, true, 386);--.go

CREATE TABLE ordemDeServico (
	id bigint NOT NULL,
	colaborador_id bigint NOT NULL,
	nomeColaborador character varying(100),
	dataAdmisaoColaborador date,
	codigoCBO character varying(6),
	nomeFuncao character varying(100),
	nomeEmpresa character varying(100),
	data date NOT NULL,
	revisao integer NOT NULL,
	atividades text,
	riscos text,
	epis text,
	medidaspreventivas text,
	treinamentos text,
	normasInternas text,
	procedimentoEmCasoDeAcidente text,
	termoDeResponsabilidade text,
	informacoesAdicionais text,
	impressa boolean DEFAULT false,
	empresacnpj character varying(20),
	nomeEstabelecimento character varying(30),
	nomeCargo character varying(30),
	estabelecimentoComplementoCNPJ character varying(10),
	estabelecimentoEndereco text
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

alter table historicoFuncao add column normasInternas text;--.go

alter table empresa add column procedimentoEmCasoDeAcidente text;--.go
alter table empresa add column termoDeResponsabilidade text;--.go