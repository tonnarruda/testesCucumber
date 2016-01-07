
alter table certificacao add column periodicidade integer;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (653,'ROLE_AVALIACAO_PRATICA', 'Avaliação Prática', '/avaliacao/avaliacaoPratica/list.action', 8, true, 366);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 653);--.go
alter sequence papel_sequence restart with 654; --.go

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

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (654,'ROLE_COLABORADOR_AVALIACAO_PRATICA', 'Notas da Avaliação Prática', '/desenvolvimento/colaboradorAvaliacaoPratica/prepare.action', 6, true, 367);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 654);--.go
alter sequence papel_sequence restart with 655; --.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (655, 'ROLE_REL_CERTIFICADOS_VENCIDOS_A_VENCER', 'Certificações Vencidas e a Vencer', '/desenvolvimento/certificacao/prepareImprimirCertificadosVencidosAVencer.action', 17, true, 368);--.go 
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 655);--.go
ALTER sequence papel_sequence restart WITH 656;--.go

ALTER TABLE empresa ADD COLUMN controlarVencimentoCertificacaoPor integer default 1;--.go

update papel set help='Esta permissão estará visível se a configuração realizada no cadastro da empresa na opção "Controlar vencimento da certificação" estiver por: Periodicidade do curso' where id = 635;--.go

ALTER TABLE certificacao ADD COLUMN certificacaoPreRequisito_id bigint;--.go
ALTER TABLE certificacao ADD CONSTRAINT certificacao_certificacaoPreRequisito_fk FOREIGN KEY (certificacaoPreRequisito_id) REFERENCES certificacao(id);--.go

CREATE TABLE colaboradorCertificacao (
	id bigint NOT NULL,
	colaborador_id bigint,
	certificacao_id bigint,
	data date
);--.go

ALTER TABLE colaboradorCertificacao ADD CONSTRAINT colaboradorCertificacao_pkey PRIMARY KEY(id);--.go
ALTER TABLE colaboradorCertificacao ADD CONSTRAINT colaborador_id_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE colaboradorCertificacao ADD CONSTRAINT certificacao_id_fk FOREIGN KEY (certificacao_id) REFERENCES certificacao(id);--.go
CREATE SEQUENCE colaboradorCertificacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE OR REPLACE FUNCTION verifica_certificacao(id_certificado BIGINT, id_colaborador BIGINT) RETURNS BOOLEAN AS $$  
BEGIN 
return (

		select (
				(select (select Array(select cursos_id from certificacao_curso where certificacaos_id = id_certificado order by cursos_id)) =
		  		(select Array(
			  				select cu.id from colaboradorturma ct inner join turma t on t.id = ct.turma_id 
			  				and t.dataprevfim = (select max(dataprevfim) from turma t2 where t2.curso_id = t.curso_id and t2.realizada 
							and dataprevfim > (coalesce((select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id where cc.colaborador_id = id_colaborador and cc.certificacao_id = id_certificado group by ce.periodicidade), '01/01/2000')))
							inner join curso cu on cu.id = t.curso_id
							where ct.colaborador_id = id_colaborador
							and t.realizada
							and cu.id in (select cursos_id from certificacao_curso where certificacaos_id = id_certificado)
							and verifica_aprovacao(cu.id, t.id, ct.id, cu.percentualminimofrequencia)
							order by cu.id
							)
				)
			)
		and
		(
			select (select Array(select avaliacoespraticas_id from certificacao_avaliacaopratica where certificacao_id = id_certificado order by avaliacoespraticas_id)) =
			(select Array( 
						select caval.avaliacaopratica_id from colaboradoravaliacaopratica caval where caval.colaborador_id = id_colaborador
						and caval.certificacao_id = id_certificado
						and caval.nota >= (select aval.notaMinima from avaliacaopratica aval where aval.id = caval.avaliacaopratica_id)
						and caval.data > (coalesce((select max(data) + cast((coalesce(ce.periodicidade,0) || ' month') as interval) from colaboradorcertificacao  cc inner join certificacao ce on ce.id = cc.certificacao_id where cc.colaborador_id = id_colaborador and cc.certificacao_id = id_certificado group by ce.periodicidade), '01/01/2000'))
						order by caval.avaliacaopratica_id)))
		) 
		as situacao 
	);
END; 
$$ LANGUAGE plpgsql; --.go 

CREATE TABLE colaboradorCertificacao_colaboradorTurma (
	colaboradorcertificacao_id bigint NOT NULL,
	colaboradoresTurmas_id bigint NOT NULL
); --.go

ALTER TABLE colaboradorCertificacao_colaboradorTurma ADD CONSTRAINT colaboradorCertificacao_colaboradorTurma_colabCertificacao_fk FOREIGN KEY (colaboradorcertificacao_id) REFERENCES colaboradorcertificacao(id);--.go
ALTER TABLE colaboradorCertificacao_colaboradorTurma ADD CONSTRAINT colaboradorCertificacao_colaboradorTurma_colabTurmas_fk FOREIGN KEY (colaboradoresTurmas_id) REFERENCES colaboradorTurma(id);--.go

ALTER TABLE colaboradorAvaliacaoPratica ADD COLUMN colaboradorcertificacao_id bigint;--.go
ALTER TABLE colaboradorAvaliacaoPratica ADD CONSTRAINT colabAvaliacaoPratica_colabCertificacao_fk FOREIGN KEY (colaboradorcertificacao_id) REFERENCES colaboradorcertificacao(id);--.go