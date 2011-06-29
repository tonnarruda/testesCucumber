-- versao 1.0.1.0
alter table candidato add column observacaorh text; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (397, 'ROLE_AREAFORMACAO', 'Área de Formação', '/geral/areaFormacao/list.action', 5, true, 357); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 397); --.go
alter sequence papel_sequence restart with 397; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (398, 'ROLE_REL_TURNOVER', 'Relatório de Turn Over', '/indicador/indicadorTurnOver/prepare.action', 5, true, 378); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 398); --.go
alter sequence papel_sequence restart with 398; --.go
alter table exame add column periodico boolean; --.go
update exame set periodico=true; --.go
alter table empresa add column logourl character varying(200); --.go
update empresa set logourl='fortes.gif'; --.go
alter table curso add conteudoprogramatico text; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (400, 'ROLE_REL_MOTIVO_DEMISSAO', 'Motivo Demissão', '/geral/motivoDemissao/prepareRelatorioMotivoDemissao.action', 25, true, 373); --.go
alter sequence papel_sequence restart with 400; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 400); --.go
update papel set nome='Motivos de Desligamento' where id=400; --.go
update parametrosdosistema set appversao = '1.0.1.0'; --.go


-- versao 1.0.1.1
update parametrosdosistema set appversao = '1.0.1.1'; --.go


-- versao 1.0.1.2
update parametrosdosistema set appversao = '1.0.1.2'; --.go


-- versao 1.0.1.3
update parametrosdosistema set appversao = '1.0.1.3'; --.go
alter table pesquisa add column aplicarporaspecto boolean; --.go


-- versao 1.0.1.4
update parametrosdosistema set appversao = '1.0.1.4'; --.go


-- versao 1.0.1.5
update parametrosdosistema set appversao = '1.0.1.5'; --.go


-- versao 1.0.2.0
alter table candidato add column deficiencia character(1); --.go
update candidato set deficiencia = '0'; --.go
alter table candidato alter column deficiencia set not null; --.go
alter table colaborador add column deficiencia character(1); --.go
update colaborador set deficiencia = '0'; --.go
alter table colaborador alter column deficiencia set not null; --.go
alter sequence parametrosdosistema_sequence restart with 2; --.go
update parametrosdosistema set appversao = '1.0.2.0'; --.go


-- versao 1.0.3.0
delete from colaboradorresposta; --.go
delete from resposta; --.go
delete from pergunta; --.go
delete from colaboradorpesquisa; --.go
delete from pesquisa; --.go
drop table colaboradorresposta; --.go
drop table resposta; --.go
drop table pergunta; --.go
drop table colaboradorpesquisa; --.go
drop table pesquisa; --.go
drop sequence colaboradorresposta_sequence; --.go
drop sequence resposta_sequence; --.go
drop sequence pergunta_sequence; --.go
drop sequence colaboradorpesquisa_sequence; --.go
drop sequence pesquisa_sequence; --.go
CREATE TABLE questionario (
    id bigint NOT NULL,
    cabecalho text,
	titulo text,
	liberado boolean,
	anonimo boolean,
	aplicarporaspecto boolean,
	tipo integer,
	datainicio date,
	datafim date,
	empresa_id bigint
); --.go
ALTER TABLE ONLY questionario ADD CONSTRAINT questionario_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY questionario ADD CONSTRAINT questionario_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id); --.go
CREATE SEQUENCE questionario_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE colaboradorquestionario (
    id bigint NOT NULL,
    colaborador_id bigint,
    questionario_id bigint,
    respondida boolean,
    respondidaEm date
); --.go
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id); --.go
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id); --.go
CREATE SEQUENCE colaboradorquestionario_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE aspecto (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint,
    questionario_id bigint
); --.go
ALTER TABLE ONLY aspecto ADD CONSTRAINT aspecto_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY aspecto ADD CONSTRAINT aspecto_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id); --.go
ALTER TABLE ONLY aspecto ADD CONSTRAINT aspecto_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id); --.go
CREATE SEQUENCE aspecto_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE pergunta (
    id bigint NOT NULL,
    ordem integer NOT NULL,
    texto text,
    comentario boolean NOT NULL,
    textocomentario text,
    tipo integer NOT NULL,
    aspecto_id bigint,
    questionario_id bigint,
    notaminima integer,
    notamaxima integer
); --.go
ALTER TABLE ONLY pergunta ADD CONSTRAINT pergunta_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY pergunta ADD CONSTRAINT pergunta_aspecto_fk FOREIGN KEY (aspecto_id) REFERENCES aspecto(id); --.go
ALTER TABLE ONLY pergunta ADD CONSTRAINT pergunta_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id); --.go
CREATE SEQUENCE pergunta_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE resposta (
    id bigint NOT NULL,
    texto text,
    ordem integer NOT NULL,
    pergunta_id bigint
); --.go
ALTER TABLE ONLY resposta ADD CONSTRAINT resposta_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY resposta ADD CONSTRAINT resposta_pergunta_fk FOREIGN KEY (pergunta_id) REFERENCES pergunta(id); --.go
CREATE SEQUENCE resposta_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE colaboradorresposta (
    id bigint NOT NULL,
    comentario text,
    valor integer NOT NULL,
    pergunta_id bigint,
    resposta_id bigint,
    colaboradorquestionario_id bigint,
    areaorganizacional_id bigint
); --.go
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_colaboradorquestionario_fk FOREIGN KEY (colaboradorquestionario_id) REFERENCES colaboradorquestionario(id); --.go
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_pergunta_fk FOREIGN KEY (pergunta_id) REFERENCES pergunta(id); --.go
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_resposta_fk FOREIGN KEY (resposta_id) REFERENCES resposta(id); --.go
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id); --.go
CREATE SEQUENCE colaboradorresposta_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE pesquisa (
    id bigint NOT NULL,
    questionario_id bigint
); --.go
ALTER TABLE ONLY pesquisa ADD CONSTRAINT pesquisa_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY pesquisa ADD CONSTRAINT pesquisa_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id); --.go
CREATE SEQUENCE pesquisa_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE avaliacao (
    id bigint NOT NULL,
    questionario_id bigint
); --.go
ALTER TABLE ONLY avaliacao ADD CONSTRAINT avaliacao_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY avaliacao ADD CONSTRAINT avaliacao_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id); --.go
CREATE SEQUENCE avaliacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE entrevista (
    id bigint NOT NULL,
    ativa boolean,
    questionario_id bigint
); --.go
ALTER TABLE ONLY entrevista ADD CONSTRAINT entrevista_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id); --.go
CREATE SEQUENCE entrevista_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
alter table candidato add column rgorgaoemissor character varying(10); --.go
alter table candidato add column rguf_id bigint; --.go
alter table candidato add column rgdataexpedicao date; --.go
alter table candidato add column titeleitnumero character varying(13); --.go
alter table candidato add column titeleitzona character varying(13); --.go
alter table candidato add column titeleitsecao character varying(13); --.go
alter table candidato add column certmilnumero character varying(12); --.go
alter table candidato add column certmiltipo character varying(5); --.go
alter table candidato add column certmilserie character varying(12); --.go
alter table candidato add column ctpsnumero character varying(8); --.go
alter table candidato add column ctpsserie character varying(5); --.go
alter table candidato add column ctpsdv character(1); --.go
alter table candidato add column ctpsuf_id bigint; --.go
alter table candidato add column ctpsdataexpedicao date; --.go
alter table colaborador add column rgorgaoemissor character varying(10); --.go
alter table colaborador add column rguf_id bigint; --.go
alter table colaborador add column rgdataexpedicao date; --.go
alter table colaborador add column numeroHab character varying(30); --.go
alter table colaborador add column registro character varying(30); --.go
alter table colaborador add column emissao date; --.go
alter table colaborador add column vencimento date; --.go
alter table colaborador add column categoria character varying(10); --.go
alter table colaborador add column titeleitnumero character varying(13); --.go
alter table colaborador add column titeleitzona character varying(13); --.go
alter table colaborador add column titeleitsecao character varying(13); --.go
alter table colaborador add column certmilnumero character varying(12); --.go
alter table colaborador add column certmiltipo character varying(5); --.go
alter table colaborador add column certmilserie character varying(12); --.go
alter table colaborador add column ctpsnumero character varying(8); --.go
alter table colaborador add column ctpsserie character varying(5); --.go
alter table colaborador add column ctpsdv character(1); --.go
alter table colaborador add column ctpsuf_id bigint; --.go
alter table colaborador add column ctpsdataexpedicao date; --.go
alter table empresa add column emailresprh character varying(120); --.go
alter table cargo alter column nomemercado set not null; --.go
update papel set url = '/pesquisa/avaliacao/list.action' where id = 55; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (401, 'ROLE_RESPONDER_PESQUISA', 'Respoder Pesquisa Por Outro Usuário', '#', 12, false, 353); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 401); --.go
delete from perfil_papel where perfil_papel.papeis_id=42; --.go
delete from papel where papel.id=42; --.go
update papel set codigo = 'ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO' where id=401; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (402, 'ROLE_MOV_QUESTIONARIO', 'Modelos de Entrevistas de Desligamento', '/pesquisa/entrevista/list.action', 6, true, 373); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 402); --.go
update papel set nome='Pesquisas' where id=28; --.go
update papel set nome='Avaliações' where id=55; --.go
update papel set nome='Motivos de Desligamento' where id=60; --.go
update papel set nome='Ocorrências' where id=62; --.go
update papel set nome='Ocorrências' where id=93; --.go
update papel set nome='Prioridades de Treinamento' where id=13; --.go
update papel set nome='Colaboradores sem Indicação de Trein.' where id=33; --.go
update papel set nome='Áreas de Formação' where id=397; --.go
update papel set nome='Motivos de Solicitação de Pessoal' where id=57; --.go
update papel set nome='Motivos de Preenchimentos de Vagas' where id=73; --.go
update papel set nome='Turnover' where id=398; --.go
update papel set nome='Ambientes' where id=76; --.go
update papel set nome='Tipos de EPI' where id=77; --.go
update papel set nome='Riscos' where id=79; --.go
update papel set nome='Levantamentos de Ruído' where id=81; --.go
update papel set nome='Levantamentos de Temperatura' where id=82; --.go
update papel set nome='Levantamentos de Iluminamento' where id=95; --.go
update papel set nome='Empresas' where id=58; --.go
update papel set nome='Estabelecimentos' where id=393; --.go
update papel set nome='Bairros' where id=394; --.go
update papel set nome='Configurações' where id=41; --.go
update parametrosdosistema set appversao = '1.0.3.0'; --.go


-- versao 1.0.4.0
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (403, 'ROLE_MOV_QUESTIONARIO', 'Resultados das Entrevistas', '/pesquisa/questionario/prepareResultadoEntrevista.action', 26, true, 373); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 403); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (404, 'ROLE_CAD_INDICE', 'Índices', '/cargosalario/indice/list.action', 8, true, 361); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 404); --.go
alter sequence papel_sequence restart with 405; --.go
CREATE TABLE indice (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    codigoac character varying(12)
); --.go
ALTER TABLE indice ADD CONSTRAINT indice_pkey PRIMARY KEY (id); --.go
CREATE SEQUENCE indice_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
alter table aspecto drop column empresa_id; --.go
update parametrosdosistema set appversao = '1.0.4.0'; --.go


-- versao 1.0.5.0
alter table empresa add column exibirsalario boolean; --.go
update empresa set exibirsalario=true; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (394, 'ROLE_CAD_BAIRRO', 'Bairros', '/geral/bairro/list.action', 9, true, 37); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 394); --.go
update parametrosdosistema set appversao = '1.0.5.0'; --.go


-- versao 1.0.5.1
update parametrosdosistema set appversao = '1.0.5.1'; --.go


-- versao 1.0.6.0
CREATE TABLE indicehistorico (
    id bigint NOT NULL,
    data date,
    valor double precision,
    indice_id bigint
); --.go
ALTER TABLE indicehistorico ADD CONSTRAINT indicehistorico_pkey PRIMARY KEY (id); --.go
ALTER TABLE indicehistorico ADD CONSTRAINT indicehistorico_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id); --.go
CREATE SEQUENCE indicehistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE faixasalarialhistorico (
    id bigint NOT NULL,
    data date,
    valor double precision,
    tipo integer NOT NULL,
    quantidade double precision,
    faixasalarial_id bigint,
    indice_id bigint
); --.go
ALTER TABLE faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_pkey PRIMARY KEY (id); --.go
ALTER TABLE faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id); --.go
ALTER TABLE faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id); --.go
CREATE SEQUENCE faixasalarialhistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
alter table reajustecolaborador add column tipoSalarioProposto Integer; --.go
alter table reajustecolaborador add column indiceproposto_id bigint; --.go
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_indiceproposto_fk FOREIGN KEY (indiceproposto_id) REFERENCES indice(id); --.go
alter table reajustecolaborador add column quantidadeIndiceProposto double precision; --.go
alter table reajustecolaborador add column tipoSalarioatual Integer; --.go
alter table reajustecolaborador add column indiceatual_id bigint; --.go
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_indiceatual_fk FOREIGN KEY (indiceatual_id) REFERENCES indice(id); --.go
alter table reajustecolaborador add column quantidadeIndiceatual double precision; --.go
update reajustecolaborador set tiposalarioatual = 3 where tiposalarioatual is null; --.go
alter table historicocolaborador add column tipoSalario Integer; --.go
alter table historicocolaborador add column indice_id bigint; --.go
alter table historicocolaborador add column quantidadeIndice double precision; --.go
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id); --.go
update historicocolaborador set quantidadeIndice = 0 where quantidadeindice is null; --.go
update historicocolaborador set tiposalario = 3 where tiposalario is null; --.go
alter table historicocolaborador alter column indice_id type bigint; --.go
alter table solicitacao add column faixasalarial_id bigint; --.go
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id); --.go
update solicitacao set faixasalarial_id = (select faixatabela.faixasalarial_id from faixatabela where faixatabela.id = solicitacao.faixatabela_id); --.go
ALTER TABLE solicitacao DROP CONSTRAINT solicitacao_faixatabela_fk; --.go
alter table solicitacao drop column faixatabela_id; --.go
alter table historicocolaborador add column faixasalarial_id bigint; --.go
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id); --.go
update historicocolaborador set faixasalarial_id = (select faixatabela.faixasalarial_id from faixatabela where faixatabela.id = historicocolaborador.faixatabela_id); --.go
ALTER TABLE historicocolaborador DROP CONSTRAINT historicocolaborador_faixatabela_fk; --.go
alter table historicocolaborador drop column faixatabela_id; --.go
alter table reajustecolaborador add column faixasalarialatual_id bigint; --.go
alter table reajustecolaborador add column faixasalarialproposta_id bigint; --.go
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_faixasalarialatual_fk FOREIGN KEY (faixasalarialatual_id) REFERENCES faixasalarial(id); --.go
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_faixasalarialproposta_fk FOREIGN KEY (faixasalarialproposta_id) REFERENCES faixasalarial(id); --.go
update reajustecolaborador set faixasalarialatual_id = (select faixatabela.faixasalarial_id from faixatabela where faixatabela.id = reajustecolaborador.faixatabelaatual_id); --.go
update reajustecolaborador set faixasalarialproposta_id = (select faixatabela.faixasalarial_id from faixatabela where faixatabela.id = reajustecolaborador.faixatabelaproposta_id); --.go
ALTER TABLE reajustecolaborador DROP CONSTRAINT reajustecolaborador_faixatabela_atual_fk; --.go
ALTER TABLE reajustecolaborador DROP CONSTRAINT reajustecolaborador_faixatabela_proposta_fk; --.go
alter table reajustecolaborador drop column faixatabelaatual_id; --.go
alter table reajustecolaborador drop column faixatabelaproposta_id; --.go
update reajustecolaborador set tiposalarioproposto = 3 where tiposalarioproposto is null; --.go
update reajustecolaborador set quantidadeindiceatual = 0 where quantidadeindiceatual is null; --.go
update reajustecolaborador set quantidadeindiceproposto = 0 where quantidadeindiceproposto is null; --.go
update faixasalarialhistorico set quantidade = 0 where quantidade is null; --.go
update historicocolaborador set quantidadeindice = 0 where quantidadeindice is null; --.go
update reajustecolaborador set tiposalarioatual = 3 where tiposalarioatual = 0 or tiposalarioatual is null; --.go
update reajustecolaborador set tiposalarioproposto = 3 where tiposalarioproposto = 0 or tiposalarioproposto is null; --.go
update reajustecolaborador set quantidadeindiceproposto = 0 where quantidadeindiceproposto is null; --.go
update faixasalarialhistorico set tipo = 3 where tipo is null; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (406, 'ROLE_MOV_TABELA', 'Análise de Tabela Salarial', '/cargosalario/faixaSalarialHistorico/analiseTabelaSalarialFiltro.action', 9, true, 361); --.go
alter sequence papel_sequence restart with 407; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 406); --.go
update papel set nome='Solicitação de Realinhamento de C&S' where id=49; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (407, 'ROLE_REL_PROJECAO_SALARIAL', 'Projeção Salarial', '/geral/colaborador/prepareProjecaoSalarialFiltro.action',27, true, 361); --.go
alter sequence papel_sequence restart with 408; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 407); --.go
alter table faixasalarialhistorico add column status Integer; --.go
update faixasalarialhistorico set status=1; --.go
update papel set nome = 'Tabelas de Realinhamentos' where id = 26; --.go
update papel set nome = 'Realinhamentos' where id = 35; --.go
delete from perfil_papel where papeis_id=53; --.go
delete from papel where id=53; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (408, 'ROLE_MOV_SOLICITACAO_REALINHAMENTO', 'Pode Solicitar Realinhamento', '--', 13, false, 361); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 408); --.go
alter sequence papel_sequence restart with 409; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (409, 'ROLE_UTI', 'Enviar Mensagem', '/geral/usuarioMensagem/prepareUpdate.action', 9, true, 37); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 409); --.go
alter sequence papel_sequence restart with 410; --.go
CREATE TABLE mensagem (
    id bigint NOT NULL,
    remetente character varying(100),
    link character varying(200),
    data timestamp,
    texto text
); --.go
ALTER TABLE mensagem ADD CONSTRAINT mensagem_pkey PRIMARY KEY (id); --.go
CREATE SEQUENCE mensagem_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE usuariomensagem (
    id bigint NOT NULL,
    usuario_id bigint,
    mensagem_id bigint,
    empresa_id bigint,
    lida boolean NOT NULL
); --.go
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_pkey PRIMARY KEY (id); --.go
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id); --.go
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_mensagem_fk FOREIGN KEY (mensagem_id) REFERENCES mensagem(id); --.go
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id); --.go
CREATE SEQUENCE usuariomensagem_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
alter table historicocolaborador add column reajustecolaborador_id bigint ; --.go
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_reajustecolaborador_fk FOREIGN KEY (reajustecolaborador_id) REFERENCES reajustecolaborador(id); --.go
CREATE FUNCTION ajusta_vinculo_historico_reajuste() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select hc.id as hcId, rc.id as rcId from historicocolaborador hc
		left join tabelareajustecolaborador trc on trc.id = hc.tabelareajustecolaborador_id
		left join reajustecolaborador rc on trc.id = rc.tabelareajustecolaborador_id
		where hc.colaborador_id = rc.colaborador_id
		LOOP
			EXECUTE ''UPDATE HISTORICOCOLABORADOR SET REAJUSTECOLABORADOR_ID = mviews.rcId  where id = mviews.hcId'';
    END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql; --.go
select ajusta_vinculo_historico_reajuste(); --.go
drop function ajusta_vinculo_historico_reajuste(); --.go
ALTER TABLE historicocolaborador DROP CONSTRAINT historicocolaborador_tabelareajustecolaborador_fk; --.go
alter table historicocolaborador drop column tabelareajustecolaborador_id; --.go
alter table historicocolaborador add column status Integer; --.go
update historicocolaborador set status=1; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (410, 'RECEBE_ALERTA_SETORPESSOAL', 'Recebe Mensagem do AC Pessoal', '', 12, false, 37); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 410); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (411, 'ROLE_VISUALIZAR_PENDENCIA_AC', 'Visualizar as pendências do AC', '', 13, false, 37); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 411); --.go
alter sequence papel_sequence restart with 412; --.go
CREATE FUNCTION ajusta_faixaTabela_faixaSalarial() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select ft.valor as ftValor, ft.faixasalarial_id as ftFaixaSalarialId, ts.vigencia as datavig from faixatabela ft
		left outer join tabelasalario ts on ts.id = ft.tabelasalario_id
		LOOP
			EXECUTE	''INSERT INTO FAIXASALARIALHISTORICO
			(ID, DATA, VALOR, TIPO, FAIXASALARIAL_ID, INDICE_ID, QUANTIDADE, STATUS)
			VALUES (nextval('' || quote_literal(''faixasalarialhistorico_sequence'') || '') ,''|| quote_literal(mviews.datavig) ||''
			, ''|| mviews.ftValor ||'', 3, ''|| mviews.ftFaixaSalarialId ||'', NULL, 0, 1)'';
    END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql; --.go
select ajusta_faixaTabela_faixaSalarial(); --.go
drop function ajusta_faixaTabela_faixaSalarial(); --.go
ALTER TABLE tabelareajustecolaborador DROP CONSTRAINT tabelareajustecolaborador_tabelasalario_fk; --.go
alter table tabelareajustecolaborador drop column tabelasalario_id; --.go
drop table faixatabela; --.go
drop table tabelasalario; --.go
alter table colaborador add column respondeuentrevista boolean; --.go
update colaborador set respondeuentrevista=false; --.go
CREATE FUNCTION ajusta_respondeu_entrevista() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select cq.colaborador_id as cqColaboradorId, cq.respondida from entrevista e
		left outer join questionario q on q.id = e.questionario_id
		left outer join colaboradorquestionario cq on cq.questionario_id = q.id
		where cq.respondida = true
		LOOP
			EXECUTE ''UPDATE COLABORADOR SET RESPONDEUENTREVISTA=true WHERE ID=mviews.cqColaboradorId'';
    END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql; --.go
select ajusta_respondeu_entrevista(); --.go
drop function ajusta_respondeu_entrevista(); --.go
delete from perfil_papel where papeis_id=34; --.go
delete from papel where id=34; --.go
delete from perfil_papel where papeis_id=25; --.go
delete from papel where id=25; --.go
alter table colaborador alter column respondeuentrevista set not null; --.go
update papel set nome='Índices' where id=404; --.go
update parametrosdosistema set appversao = '1.0.6.0'; --.go


-- versao 1.0.6.1
update parametrosdosistema set appversao = '1.0.6.1'; --.go


-- versao 1.0.7.0
update papel set nome='Cargos e Faixas' where id = 11; --.go
alter table candidato add column indicadopor character varying(100); --.go
alter table colaborador add column indicadopor character varying(100); --.go
alter table cargo add column ativo boolean default true; --.go
alter table areaorganizacional add column ativo boolean default true; --.go
update papel set papelmae_id=357,ordem=1 where id=358; --.go
update papel set papelmae_id=357,ordem=2 where id=359; --.go
update papel set papelmae_id=357,ordem=3 where id=360; --.go
update papel set papelmae_id=358,ordem=1 where id=2; --.go
update papel set papelmae_id=358,ordem=2 where id=3; --.go
update papel set papelmae_id=358,ordem=3 where id=6; --.go
update papel set papelmae_id=358,ordem=4 where id=4; --.go
update papel set papelmae_id=358,ordem=5 where id=397; --.go
update papel set papelmae_id=358,ordem=6 where id=57; --.go
update papel set papelmae_id=359,ordem=1 where id=21; --.go
update papel set papelmae_id=359,ordem=2 where id=50; --.go
update papel set papelmae_id=360,ordem=1 where id=46; --.go
update papel set papelmae_id=360,ordem=2 where id=48; --.go
update papel set papelmae_id=361,ordem=1 where id=362; --.go
update papel set papelmae_id=361,ordem=2 where id=363; --.go
update papel set papelmae_id=361,ordem=3 where id=364; --.go
update papel set papelmae_id=362,ordem=1 where id=9; --.go
update papel set papelmae_id=362,ordem=2 where id=10; --.go
update papel set papelmae_id=362,ordem=3 where id=5; --.go
update papel set papelmae_id=362,ordem=4 where id=11; --.go
update papel set papelmae_id=362,ordem=5 where id=404; --.go
update papel set papelmae_id=362,ordem=6 where id=405; --.go
update papel set papelmae_id=363,ordem=1 where id=26; --.go
update papel set papelmae_id=363,ordem=2 where id=49; --.go
update papel set papelmae_id=363,ordem=3 where id=395; --.go
update papel set papelmae_id=364,ordem=1 where id=54; --.go
update papel set papelmae_id=364,ordem=2 where id=35; --.go
update papel set papelmae_id=364,ordem=3 where id=396; --.go
update papel set papelmae_id=364,ordem=4 where id=406; --.go
update papel set papelmae_id=353,ordem=1 where id=355; --.go
update papel set papelmae_id=355,ordem=1 where id=28; --.go
update papel set papelmae_id=382,ordem=1 where id=384; --.go
update papel set papelmae_id=384,ordem=1 where id=55; --.go
update papel set papelmae_id=365,ordem=1 where id=366; --.go
update papel set papelmae_id=365,ordem=2 where id=367; --.go
update papel set papelmae_id=365,ordem=3 where id=368; --.go
update papel set papelmae_id=366,ordem=1 where id=13; --.go
update papel set papelmae_id=366,ordem=2 where id=23; --.go
update papel set papelmae_id=367,ordem=1 where id=64; --.go
update papel set papelmae_id=367,ordem=2 where id=72; --.go
update papel set papelmae_id=367,ordem=3 where id=71; --.go
update papel set papelmae_id=368,ordem=1 where id=31; --.go
update papel set papelmae_id=368,ordem=2 where id=32; --.go
update papel set papelmae_id=368,ordem=3 where id=33; --.go
update papel set papelmae_id=368,ordem=4 where id=43; --.go
update papel set papelmae_id=368,ordem=5 where id=61; --.go
update papel set papelmae_id=373,ordem=1 where id=374; --.go
update papel set papelmae_id=373,ordem=2 where id=376; --.go
update papel set papelmae_id=373,ordem=3 where id=377; --.go
update papel set papelmae_id=374,ordem=1 where id=15; --.go
update papel set papelmae_id=374,ordem=2 where id=16; --.go
update papel set papelmae_id=374,ordem=3 where id=12; --.go
update papel set papelmae_id=374,ordem=4 where id=60; --.go
update papel set papelmae_id=374,ordem=5 where id=402; --.go
update papel set papelmae_id=374,ordem=6 where id=62; --.go
update papel set papelmae_id=374,ordem=7 where id=8; --.go
update papel set papelmae_id=376,ordem=1 where id=29; --.go
update papel set papelmae_id=377,ordem=1 where id=93; --.go
update papel set papelmae_id=377,ordem=2 where id=400; --.go
update papel set papelmae_id=377,ordem=3 where id=403; --.go
update papel set papelmae_id=378,ordem=1 where id=381; --.go
update papel set papelmae_id=381,ordem=1 where id=73; --.go
update papel set papelmae_id=381,ordem=2 where id=69; --.go
update papel set papelmae_id=381,ordem=3 where id=47; --.go
update papel set papelmae_id=381,ordem=4 where id=70; --.go
update papel set papelmae_id=381,ordem=5 where id=398; --.go
update papel set papelmae_id=381,ordem=6 where id=36; --.go
update papel set papelmae_id=75,ordem=1 where id=385; --.go
update papel set papelmae_id=75,ordem=2 where id=386; --.go
update papel set papelmae_id=75,ordem=3 where id=387; --.go
update papel set papelmae_id=385,ordem=1 where id=76; --.go
update papel set papelmae_id=385,ordem=2 where id=77; --.go
update papel set papelmae_id=385,ordem=3 where id=78; --.go
update papel set papelmae_id=385,ordem=4 where id=86; --.go
update papel set papelmae_id=385,ordem=5 where id=79; --.go
update papel set papelmae_id=385,ordem=6 where id=89; --.go
update papel set papelmae_id=385,ordem=7 where id=90; --.go
update papel set papelmae_id=385,ordem=8 where id=91; --.go
update papel set papelmae_id=385,ordem=9 where id=87; --.go
update papel set papelmae_id=385,ordem=10 where id=83; --.go
update papel set papelmae_id=385,ordem=11 where id=80; --.go
update papel set papelmae_id=385,ordem=12 where id=84; --.go
update papel set papelmae_id=385,ordem=13 where id=92; --.go
update papel set papelmae_id=386,ordem=1 where id=88; --.go
update papel set papelmae_id=386,ordem=2 where id=81; --.go
update papel set papelmae_id=386,ordem=3 where id=82; --.go
update papel set papelmae_id=386,ordem=4 where id=95; --.go
update papel set papelmae_id=386,ordem=5 where id=66; --.go
update papel set papelmae_id=387,ordem=1 where id=388; --.go
update papel set papelmae_id=387,ordem=2 where id=389; --.go
update papel set papelmae_id=387,ordem=3 where id=85; --.go
update papel set papelmae_id=37,ordem=1 where id=390; --.go
update papel set papelmae_id=37,ordem=2 where id=391; --.go
update papel set papelmae_id=390,ordem=1 where id=58; --.go
update papel set papelmae_id=390,ordem=2 where id=18; --.go
update papel set papelmae_id=390,ordem=3 where id=393; --.go
update papel set papelmae_id=390,ordem=4 where id=19; --.go
update papel set papelmae_id=390,ordem=5 where id=394; --.go
update papel set papelmae_id=390,ordem=6 where id=408; --.go
update papel set papelmae_id=391,ordem=1 where id=38; --.go
update papel set papelmae_id=391,ordem=2 where id=41; --.go
update papel set papelmae_id=391,ordem=3 where id=39; --.go
update papel set papelmae_id=391,ordem=4 where id=44; --.go
CREATE FUNCTION ajusta_perfil_papel_pai() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select pp.perfil_id as perfilId,p.papelmae_id as papelIdMae, (select count(*) as cont from perfil_papel where perfil_id=pp.perfil_id and papeis_id=p.papelmae_id) as qtd from perfil_papel  as pp join papel p on p.id=pp.papeis_id where p.papelmae_id is not null group by pp.perfil_id,p.papelmae_id
		LOOP
		IF mviews.qtd = 0 THEN
			EXECUTE ''insert into perfil_papel(perfil_id, papeis_id) values(''|| mviews.perfilId ||'', ''|| mviews.papelIdMae ||'')'';
		END IF;
    END LOOP;
    RETURN 1;
END;
' LANGUAGE plpgsql; --.go
select ajusta_perfil_papel_pai(); --.go
drop function ajusta_perfil_papel_pai(); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (412, 'ROLE_CAD_INFO_PESSOAL', 'Atualizar meus dados', '/geral/colaborador/prepareUpdateInfoPessoais.action', 8, true, 374); --.go
alter sequence papel_sequence restart with 413; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 412); --.go
alter table parametrosdosistema add column acversaowebservicecompativel character varying(20); --.go
update parametrosdosistema set acversaowebservicecompativel='1.0.0.36'; --.go
alter table usuario add constraint usuario_login_uk unique (login); --.go
CREATE TABLE avaliacaocurso (
    id bigint NOT NULL,
    titulo character varying(100),
    tipo character(1),
    minimoaprovacao double precision
); --.go
ALTER TABLE avaliacaocurso ADD CONSTRAINT avaliacaocurso_pkey PRIMARY KEY (id); --.go
CREATE SEQUENCE avaliacaocurso_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
alter table curso add column cargahoraria character varying(20); --.go
alter table curso add column criterioAvaliacao text; --.go
CREATE TABLE curso_avaliacaocurso (
    curso_id bigint NOT NULL,
    avaliacaocursos_id bigint NOT NULL
); --.go
ALTER TABLE curso_avaliacaocurso ADD CONSTRAINT curso_avaliacaocurso_avaliacaocurso_fk FOREIGN KEY (avaliacaocursos_id) REFERENCES avaliacaocurso(id); --.go
ALTER TABLE curso_avaliacaocurso ADD CONSTRAINT curso_avaliacaocurso_curso_fk FOREIGN KEY (curso_id) REFERENCES curso(id); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (413, 'ROLE_MOV_AVALIACAO_CURSO', 'Avaliações dos Cursos ', '/desenvolvimento/avaliacaoCurso/list.action', 3, true, 366); --.go
alter sequence papel_sequence restart with 414; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 413); --.go
ALTER TABLE turma DROP COLUMN datarealizaini; --.go
ALTER TABLE turma DROP COLUMN datarealizafim; --.go
ALTER TABLE turma DROP COLUMN cargahoraria; --.go
ALTER TABLE turma ADD COLUMN instituicao character varying(100); --.go
ALTER TABLE turma ADD COLUMN horario character varying(20); --.go
ALTER TABLE turma ADD COLUMN realizada boolean; --.go
ALTER TABLE turma ADD COLUMN qtdparticipantesprevistos Integer; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (414, 'ROLE_MOV_PLANO_TREINAMENTO', 'Plano de Treinamento', '/desenvolvimento/turma/filtroPlanoTreinamento.action', 4, true, 367); --.go
alter sequence papel_sequence restart with 415; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 414); --.go
update turma set realizada = false; --.go
update parametrosdosistema set appversao = '1.0.7.0'; --.go


-- versao 1.0.7.1
update parametrosdosistema set appversao = '1.0.7.1'; --.go


-- versao 1.0.8.0
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (415, 'ROLE_REL_COLABORADOR_SEM_TREINAMENTO', 'Colaboradores sem Trein.', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action', 29, true, 365); --.go
alter sequence papel_sequence restart with 416; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 415); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (416, 'ROLE_REL_COLABORADOR_COM_TREINAMENTO', 'Colaboradores com Trein.', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action?comTreinamento=true', 30, true, 365); --.go
alter sequence papel_sequence restart with 417; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 416); --.go
alter table curso_avaliacaoCurso rename column curso_id to cursos_id; --.go
ALTER TABLE curso_avaliacaoCurso drop constraint curso_avaliacaocurso_curso_fk; --.go
ALTER TABLE curso_avaliacaocurso ADD CONSTRAINT curso_avaliacaocurso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id); --.go
CREATE TABLE aproveitamentoavaliacaocurso (
    id bigint NOT NULL,
    colaboradorturma_id bigint,
    avaliacaocurso_id bigint,
    valor double precision
); --.go
ALTER TABLE aproveitamentoavaliacaocurso ADD CONSTRAINT aproveitamentoavaliacaocurso_pkey PRIMARY KEY (id); --.go
ALTER TABLE aproveitamentoavaliacaocurso ADD CONSTRAINT aproveitamentoavaliacaocurso_colaboradorturma_fk FOREIGN KEY (colaboradorturma_id) REFERENCES colaboradorturma(id); --.go
ALTER TABLE aproveitamentoavaliacaocurso ADD CONSTRAINT aproveitamentoavaliacaocurso_avaliacaocurso_fk FOREIGN KEY (avaliacaocurso_id) REFERENCES avaliacaocurso(id); --.go
CREATE SEQUENCE aproveitamentoavaliacaocurso_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
update papel set url = '/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorSemIndicacao.action' where id = 33; --.go
update papel set nome='Colaboradores sem Treinamentos' where id=415; --.go
update papel set nome='Colaboradores com Treinamentos' where id=416; --.go
update parametrosdosistema set appversao = '1.0.8.0'; --.go


-- versao 1.0.8.1
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (417, 'ROLE_TRANSFERIR_FAIXAS_AC', 'Transferir Faixas entre Cargos', '', 1, false, NULL); --.go
alter sequence papel_sequence restart with 418; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (418, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Cronograma de Treinamento', '/desenvolvimento/turma/filtroCronogramaTreinamento.action', 8, true, 368); --.go
alter sequence papel_sequence restart with 419; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 417); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 418); --.go
update parametrosdosistema set appversao = '1.0.8.1'; --.go

-- versao 1.0.9.0
CREATE TABLE avaliacaoturma (
    id bigint NOT NULL,
    ativa boolean,
    questionario_id bigint
); --.go
ALTER TABLE avaliacaoturma ADD CONSTRAINT avaliacaoturma_pkey PRIMARY KEY (id); --.go
ALTER TABLE ONLY avaliacaoturma ADD CONSTRAINT avaliacaoturma_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id); --.go
CREATE SEQUENCE avaliacaoturma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
ALTER TABLE turma ADD COLUMN avaliacaoturma_id bigint; --.go
ALTER TABLE ONLY turma ADD CONSTRAINT turma_avaliacaoturma_fk FOREIGN KEY (avaliacaoturma_id) REFERENCES avaliacaoturma(id); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (419, 'ROLE_AVALIACAO_TURMA', 'Modelos de Avaliação de Turma', '/pesquisa/avaliacaoTurma/list.action', 4, true, 366); --.go
alter sequence papel_sequence restart with 420; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 419); --.go
ALTER TABLE colaboradorquestionario ADD COLUMN turma_id bigint; --.go
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_turma_fk FOREIGN KEY (turma_id) REFERENCES turma(id); --.go
ALTER TABLE parametrosdosistema ADD COLUMN uppercase boolean; --.go
update parametrosdosistema set uppercase=false; --.go
CREATE TABLE certificacao (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
); --.go
ALTER TABLE certificacao ADD CONSTRAINT certificacao_pkey PRIMARY KEY (id); --.go
ALTER TABLE certificacao ADD CONSTRAINT certificacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id); --.go
CREATE SEQUENCE certificacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
CREATE TABLE certificacao_curso (
    certificacaos_id bigint NOT NULL,
    cursos_id bigint NOT NULL
); --.go
ALTER TABLE certificacao_curso ADD CONSTRAINT certificacao_curso_certificacao_fk FOREIGN KEY (certificacaos_id) REFERENCES certificacao(id); --.go
ALTER TABLE certificacao_curso ADD CONSTRAINT certificacao_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id); --.go
CREATE TABLE faixasalarial_certificacao (
    faixasalarials_id bigint NOT NULL,
    certificacaos_id bigint NOT NULL
); --.go
ALTER TABLE faixasalarial_certificacao ADD CONSTRAINT faixasalarial_certificacao_certificacao_fk FOREIGN KEY (certificacaos_id) REFERENCES certificacao(id); --.go
ALTER TABLE faixasalarial_certificacao ADD CONSTRAINT faixasalarial_certificacao_faixasalarial_fk FOREIGN KEY (faixasalarials_id) REFERENCES faixasalarial(id); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (420, 'ROLE_CAD_CERTIFICACAO', 'Certificações', '/desenvolvimento/certificacao/list.action', 5, true, 366); --.go
alter sequence papel_sequence restart with 421; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 420); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (421, 'ROLE_REL_MATRIZ_TREINAMENTO', 'Matriz de Treinamentos', '/desenvolvimento/certificacao/matrizTreinamento.action', 31, true, 365); --.go
alter sequence papel_sequence restart with 422; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 421); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (422, 'ROLE_REL_HISTORICO_TREINAMENTOS', 'Histórico de Treinamentos', '/desenvolvimento/colaboradorTurma/prepareFiltroHistoricoTreinamentos.action', 32, true, 365); --.go
alter sequence papel_sequence restart with 423; --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 422); --.go
update parametrosdosistema set appversao = '1.0.9.0'; --.go


-- versao 1.0.10.0
alter table curso drop column cargahoraria; --.go
alter table curso add column cargahoraria integer; --.go
update curso set cargahoraria=0; --.go
ALTER TABLE empresa ADD COLUMN uf_id bigint; --.go
ALTER TABLE empresa ADD COLUMN cidade_id bigint; --.go
ALTER TABLE empresa ADD CONSTRAINT empresa_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);--.go
ALTER TABLE empresa ADD CONSTRAINT empresa_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);--.go
update empresa set uf_id=(select id from estado where sigla='CE'); --.go
update empresa set cidade_id=(select id from cidade where nome='Fortaleza'); --.go
ALTER TABLE colaborador ADD COLUMN name character varying(255); --.go
ALTER TABLE colaborador ADD COLUMN contenttype character varying(255); --.go
ALTER TABLE colaborador ADD COLUMN bytes bytea; --.go
ALTER TABLE colaborador ADD COLUMN size bigint; --.go
UPDATE papel set url = '/desenvolvimento/indicadores/list.action' WHERE id=365; --.go
update parametrosdosistema set appversao = '1.0.10.0'; --.go


-- versao 1.0.10.1
update parametrosdosistema set appversao = '1.0.10.1'; --.go


-- versao 1.0.11.0
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (423, 'ROLE_REL_ANIVERSARIANTES', 'Aniversariantes do mês', '/geral/colaborador/prepareRelatorioAniversariantes.action', 4, true, 377);--.go
alter sequence papel_sequence restart with 424;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 423);--.go
UPDATE papel set url='', papelmae_id=361 where codigo='ROLE_MOV_SOLICITACAO_REALINHAMENTO';--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (424, 'ROLE_REL_AVALIACAO_CANDIDATOS', 'Avaliações de Candidatos', '/captacao/candidato/prepareRelatorioAvaliacaoCandidatos.action', 3, true, 48);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 424);--.go
alter sequence papel_sequence restart with 425;--.go
alter table parametrosdosistema add column modulos text;--.go
update papel set nome ='Clínicas e Médicos Autorizados' where id=91;--.go
CREATE TABLE clinicaautorizada_exame (
    clinicaautorizada_id bigint NOT NULL,
    exames_id bigint NOT NULL
); --.go
ALTER TABLE clinicaautorizada_exame ADD CONSTRAINT clinicaautorizada_exame_exame_fk FOREIGN KEY (exames_id) REFERENCES exame(id); --.go
ALTER TABLE clinicaautorizada_exame ADD CONSTRAINT clinicaautorizada_exame_clinicaautorizada_fk FOREIGN KEY (clinicaautorizada_id) REFERENCES clinicaautorizada(id); --.go
CREATE TABLE prontuario (
    id bigint NOT NULL,
    descricao text,
    data date,
    colaborador_id bigint
);--.go
ALTER TABLE prontuario ADD CONSTRAINT prontuario_pkey PRIMARY KEY (id);--.go
ALTER TABLE prontuario ADD CONSTRAINT prontuario_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
CREATE SEQUENCE prontuario_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (425, 'ROLE_CAD_PRONTUARIO', 'Registro de Prontuário', '/sesmt/prontuario/list.action', 7, true, 386);--.go
alter sequence papel_sequence restart with 426;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 425);--.go
CREATE TABLE solicitacaoexame (
    id bigint NOT NULL,
    data date,
    motivo character(1),
    candidato_id bigint,
    colaborador_id bigint,
    medicocoordenador_id bigint,
    empresa_id bigint
);--.go
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_pkey PRIMARY KEY (id);--.go
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);--.go
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_medicocoordenador_fk FOREIGN KEY (medicocoordenador_id) REFERENCES medicocoordenador(id);--.go
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE solicitacaoexame_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (426, 'ROLE_CAD_SOLICITACAOEXAME', 'Solicitação de Exames', '/sesmt/solicitacaoExame/list.action', 0, true, 386);--.go
alter sequence papel_sequence restart with 427;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 426);--.go
CREATE TABLE fichaMedica (
    id bigint NOT NULL,
    ativa boolean,
    rodape text,
    questionario_id bigint
);--.go
ALTER TABLE fichaMedica ADD CONSTRAINT fichaMedica_pkey PRIMARY KEY (id);--.go
ALTER TABLE ONLY fichaMedica ADD CONSTRAINT fichaMedica_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);--.go
CREATE SEQUENCE fichaMedica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (427, 'ROLE_CAD_FICHAMEDICA', 'Modelos de Fichas Médicas', '/pesquisa/fichaMedica/list.action', 14, true, 385);--.go
alter sequence papel_sequence restart with 428;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 427);--.go
CREATE TABLE examesolicitacaoexame (
    id bigint NOT NULL,
    exame_id bigint,
    solicitacaoexame_id bigint,
    clinicaautorizada_id bigint
);--.go
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_pkey PRIMARY KEY (id);--.go
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_solicitacaoexame_fk FOREIGN KEY (solicitacaoexame_id) REFERENCES solicitacaoexame(id);--.go
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);--.go
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_clinicaautorizada_fk FOREIGN KEY (clinicaautorizada_id) REFERENCES clinicaautorizada(id);--.go
CREATE SEQUENCE examesolicitacaoexame_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1; --.go
alter table colaboradorquestionario add column candidato_id bigint;--.go
ALTER TABLE colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (428, 'ROLE_CAD_FICHAMEDICA', 'Fichas Médicas', '/pesquisa/fichaMedica/listPreenchida.action', 28, true, 386);--.go
alter sequence papel_sequence restart with 429;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 428);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (429, 'ROLE_REL_PRONTUARIO', 'Prontuário', '/sesmt/prontuario/prepareRelatorioProntuario.action', 4, true, 387);--.go
alter sequence papel_sequence restart with 430;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 429);--.go
alter table colaboradorresposta alter column valor drop not null; --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (430, 'ROLE_REL_ASO', 'ASOs', '/sesmt/exame/prepareImprimirAso.action', 5, true, 387);--.go
alter sequence papel_sequence restart with 431;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 430);--.go
ALTER TABLE medicocoordenador ADD COLUMN registro character varying(20);--.go
ALTER TABLE medicocoordenador ADD COLUMN especialidade character varying(100);--.go
ALTER TABLE medicocoordenador ADD COLUMN name character varying(255);--.go
ALTER TABLE medicocoordenador ADD COLUMN contenttype character varying(255);--.go
ALTER TABLE medicocoordenador ADD COLUMN bytes bytea;--.go
ALTER TABLE medicocoordenador ADD COLUMN size bigint;--.go
ALTER TABLE realizacaoexame ALTER COLUMN resultado TYPE character varying(20);--.go
alter table realizacaoexame drop constraint realizacaoexame_colaborador_fk;--.go
alter table realizacaoexame drop column colaborador_id;--.go
alter table realizacaoexame drop column natureza;--.go
alter table realizacaoexame drop constraint realizacaoexame_exame_fk;--.go
alter table realizacaoexame drop column exame_id;--.go
ALTER TABLE solicitacaoexame ALTER COLUMN motivo TYPE character varying(20);--.go
alter table examesolicitacaoexame ADD COLUMN realizacaoexame_id bigint;--.go
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_realizacaoexame_fk FOREIGN KEY (realizacaoexame_id) REFERENCES realizacaoexame(id);--.go
update parametrosdosistema set appversao = '1.0.11.0';--.go

-- versao 1.0.12.0
alter table realizacaoexame drop column empresa_id;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (431, 'ROLE_REL_EXAMES_PREVISTOS', 'Exames Previstos', '/sesmt/exame/prepareRelatorioExamesPrevistos.action', 6, true, 387);--.go
alter sequence papel_sequence restart with 432;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 431);--.go

ALTER TABLE epi ADD COLUMN fardamento boolean; --.go
UPDATE epi SET fardamento = 'false'; --.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (432, 'ROLE_REL_FICHA_EPI', 'Ficha de EPI', '/sesmt/epi/prepareImprimirFicha.action', 7, true, 387);--.go
alter sequence papel_sequence restart with 433;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 432);--.go

CREATE TABLE solicitacaoepi (
    id bigint NOT NULL,
    data date,
    entregue boolean,
    colaborador_id bigint,
    cargo_id bigint,
    empresa_id bigint
);--.go
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_pkey PRIMARY KEY (id);--.go
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE solicitacaoepi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (433, 'ROLE_CAD_SOLICITACAOEPI', 'Solicitação de EPIs', '/sesmt/solicitacaoEpi/list.action', 10, true, 386);--.go
alter sequence papel_sequence restart with 434;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 433);--.go

UPDATE papel set ordem=9 where id=428; --.go

CREATE TABLE solicitacaoepi_item (
	id bigint NOT NULL,
	epi_id bigint,
	solicitacaoepi_id bigint,
	qtdSolicitado integer NOT NULL,
	qtdEntregue integer NOT NULL
); --.go
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_pkey PRIMARY KEY (id);--.go
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_solicitacaoepi_fk FOREIGN KEY (solicitacaoepi_id) REFERENCES solicitacaoepi(id);--.go
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_epi_fk FOREIGN KEY (epi_id) REFERENCES epi(id);--.go
CREATE SEQUENCE solicitacaoepi_item_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

DELETE FROM perfil_papel where papeis_id=(SELECT id FROM papel WHERE codigo='ROLE_CAD_REALIZACAO_EXAME');--.go
DELETE FROM papel where id=(SELECT id FROM papel WHERE codigo='ROLE_CAD_REALIZACAO_EXAME');--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (434, 'ROLE_CAD_EPICAVENCER', 'EPIs com CA a Vencer', '/sesmt/epi/prepareImprimirVencimentoCa.action', 8, true, 387);--.go
alter sequence papel_sequence restart with 435;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 434);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (435, 'ROLE_CAD_ENTREGAEPI', 'Entrega de EPIs', '/sesmt/solicitacaoEpi/list.action', 10, true, 386);--.go
alter sequence papel_sequence restart with 436;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 435);--.go

ALTER TABLE prontuario ADD COLUMN usuario_id bigint;--.go
ALTER TABLE prontuario ADD CONSTRAINT prontuario_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (436, 'ROLE_REL_EPIVENCIMENTO', 'EPIs com Prazo a Vencer', '/sesmt/solicitacaoEpi/prepareRelatorioVencimentoEpi.action', 9, true, 387);--.go
alter sequence papel_sequence restart with 437;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 436);--.go

CREATE TABLE eleicao (
    id bigint NOT NULL,
    posse date,
    votacao date,
    horarioVotacaoIni character varying(20),
    horarioVotacaoFim character varying(20),
    qtdVotoNulo integer NOT NULL,
    qtdVotoBranco integer NOT NULL,
    inscricaoCandidatoIni date,
    inscricaoCandidatoFim date,
    localInscricao character varying(100),
    localVotacao character varying(100),
    empresa_id bigint
);--.go

ALTER TABLE eleicao ADD CONSTRAINT eleicao_pkey PRIMARY KEY(id);--.go
ALTER TABLE eleicao ADD CONSTRAINT eleicao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE eleicao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE etapaprocessoeleitoral (
    id bigint NOT NULL,
    nome character varying(100),
    prazolegal character varying(100),
    prazo integer NOT NULL,
    eleicao_id bigint,
    empresa_id bigint
);--.go
ALTER TABLE etapaprocessoeleitoral ADD CONSTRAINT etapaprocessoeleitoral_pkey PRIMARY KEY(id);--.go
ALTER TABLE etapaprocessoeleitoral ADD CONSTRAINT etapaprocessoeleitoral_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
ALTER TABLE etapaprocessoeleitoral ADD CONSTRAINT etapaprocessoeleitoral_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);--.go
CREATE SEQUENCE etapaprocessoeleitoral_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (437, 'ROLE_CAD_ETAPAPROCESSOELEITORAL', 'CIPA - Etapas do Processo Eleitoral', '/sesmt/etapaProcessoEleitoral/list.action', 15, true, 385);--.go
alter sequence papel_sequence restart with 438;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 437);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (438, 'ROLE_CAD_ELEICAO', 'CIPA - Eleições', '/sesmt/eleicao/list.action', 16, true, 385);--.go
alter sequence papel_sequence restart with 439;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 438);--.go

CREATE TABLE candidatoeleicao (
    id bigint NOT NULL,
    candidato_id bigint,
    eleicao_id bigint
);--.go

ALTER TABLE candidatoeleicao ADD CONSTRAINT candidatoeleicao_pkey PRIMARY KEY(id);--.go
ALTER TABLE candidatoeleicao ADD CONSTRAINT candidatoeleicao_candidato_fk FOREIGN KEY (candidato_id) REFERENCES colaborador(id);--.go
ALTER TABLE candidatoeleicao ADD CONSTRAINT candidatoeleicao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);--.go
CREATE SEQUENCE candidatoeleicao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE candidatoEleicao ADD COLUMN qtdVoto integer; --.go
ALTER TABLE candidatoEleicao ADD COLUMN eleito boolean; --.go

CREATE TABLE comissaoeleicao (
    id bigint NOT NULL,
    colaborador_id bigint,
    eleicao_id bigint,
	funcao character varying(20)
);--.go

ALTER TABLE comissaoeleicao ADD CONSTRAINT comissaoeleicao_pkey PRIMARY KEY(id);--.go
ALTER TABLE comissaoeleicao ADD CONSTRAINT comissaoeleicao_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE comissaoeleicao ADD CONSTRAINT comissaoeleicao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);--.go
CREATE SEQUENCE comissaoeleicao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
update parametrosdosistema set appversao = '1.0.12.0';--.go

-- versao 1.0.13.0
ALTER TABLE eleicao ADD COLUMN sindicato character varying(100); --.go
ALTER TABLE eleicao ADD COLUMN apuracao date; --.go
ALTER TABLE empresa ADD COLUMN atividade character varying(200); --.go
ALTER TABLE eleicao ADD COLUMN descricao character varying(100); --.go

CREATE TABLE comissao (
    id bigint NOT NULL,
	dataIni date,
	dataFim date,
    eleicao_id bigint
);--.go
ALTER TABLE comissao ADD CONSTRAINT comissao_pkey PRIMARY KEY(id);--.go
ALTER TABLE comissao ADD CONSTRAINT comissao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);--.go
CREATE SEQUENCE comissao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (439, 'ROLE_CAD_ELEICAO', 'CIPA - Comissões', '/sesmt/comissao/list.action', 17, true, 385);--.go
alter sequence papel_sequence restart with 440;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 439);--.go

CREATE TABLE comissaoperiodo (
    id bigint NOT NULL,
   	inicio date,
	fim date,
	comissao_id bigint
);--.go

ALTER TABLE comissaoperiodo ADD CONSTRAINT comissaoperiodo_pkey PRIMARY KEY(id);--.go
ALTER TABLE comissaoperiodo ADD CONSTRAINT comissaoperiodo_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);--.go
CREATE SEQUENCE comissaoperiodo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE comissaomembro (
    id bigint NOT NULL,
	funcao character varying(1),
	tipo character varying(1),
	colaborador_id bigint,
	comissaoperiodo_id bigint
);--.go

ALTER TABLE comissaomembro ADD CONSTRAINT comissaomembro_pkey PRIMARY KEY(id);--.go
ALTER TABLE comissaomembro ADD CONSTRAINT comissaomembro_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE comissaomembro ADD CONSTRAINT comissaomembro_comissaoperiodo_fk FOREIGN KEY (comissaoperiodo_id) REFERENCES comissaoperiodo(id);--.go
CREATE SEQUENCE comissaomembro_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
update parametrosdosistema set appversao = '1.0.13.0';--.go

-- versao 1.0.14.0
update parametrosdosistema set appversao = '1.0.14.0';--.go
ALTER TABLE clinicaautorizada ADD COLUMN endereco character varying(100);--.go

CREATE TABLE comissaoreuniao (
	id bigint NOT NULL,
	data date,
	descricao character varying(100),
	localizacao character varying(100),
	horario character varying(20),
	tipo character varying(1),
	ata text,
	comissao_id bigint
);--.go

ALTER TABLE comissaoreuniao ADD CONSTRAINT comissaoreuniao_pkey PRIMARY KEY(id);--.go
ALTER TABLE comissaoreuniao ADD CONSTRAINT comissaoreuniao_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);--.go
CREATE SEQUENCE comissaoreuniao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE comissaoplanotrabalho (
	id bigint NOT NULL,
	prazo date,
	descricao character varying(100),
	situacao character varying(12),
	prioridade character varying(1),
	parecer character varying(1),
	detalhes text,
	responsavel_id bigint,
	comissao_id bigint
);--.go

ALTER TABLE comissaoplanotrabalho ADD CONSTRAINT comissaoplanotrabalho_pkey PRIMARY KEY(id);--.go
ALTER TABLE comissaoplanotrabalho ADD CONSTRAINT comissaoplanotrabalho_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);--.go
ALTER TABLE comissaoplanotrabalho ADD CONSTRAINT comissaoplanotrabalho_responsavel_fk FOREIGN KEY (responsavel_id) REFERENCES colaborador(id);--.go
CREATE SEQUENCE comissaoplanotrabalho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE comissaoreuniaopresenca (
	id bigint NOT NULL,
	comissaoreuniao_id bigint,
	colaborador_id bigint,
	presente boolean,
	justificativaFalta character varying(100)
);--.go

ALTER TABLE comissaoreuniaopresenca ADD CONSTRAINT comissaoreuniaopresenca_pkey PRIMARY KEY(id);--.go
ALTER TABLE comissaoreuniaopresenca ADD CONSTRAINT comissaoreuniaopresenca_comissaoreuniao_fk FOREIGN KEY (comissaoreuniao_id) REFERENCES comissaoreuniao(id);--.go
ALTER TABLE comissaoreuniaopresenca ADD CONSTRAINT comissaoreuniaopresenca_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
CREATE SEQUENCE comissaoreuniaopresenca_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE etapaprocessoeleitoral ADD COLUMN data date;--.go
ALTER TABLE etapaprocessoeleitoral ALTER COLUMN prazo DROP NOT NULL;--.go


CREATE FUNCTION insere_etapa(CHARACTER, CHARACTER, INTEGER, BIGINT) RETURNS VOID AS '
BEGIN
	EXECUTE ''INSERT INTO ETAPAPROCESSOELEITORAL
			(ID, NOME, PRAZOLEGAL, PRAZO,ELEICAO_ID, EMPRESA_ID, DATA)
			VALUES (nextval('' || quote_literal(''etapaprocessoeleitoral_sequence'') || '') ,''|| quote_literal($1) ||'', ''|| quote_literal($2) ||'',''|| $3 ||'',NULL,''|| $4 ||'',NULL)'';

END
' LANGUAGE plpgsql; --.go

CREATE FUNCTION insere_etapas_eleicao() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select e.id as empresaId from empresa e
		LOOP
			PERFORM insere_etapa(''Edital de Convocação para eleição'',''60 dias antes da posse'',-60,mviews.empresaId);
			PERFORM insere_etapa(''Formação da Comissão Eleitoral'',''55 dias antes do término do mandato'',-55,mviews.empresaId);
			PERFORM insere_etapa(''Enviar cópia do Edital de Convocação ao sindicato'',''5 dias após a convocação da eleição'',-55,mviews.empresaId);
			PERFORM insere_etapa(''Início das inscrições dos candidatos'',''20 dias antes da eleição'',-50,mviews.empresaId);
			PERFORM insere_etapa(''Publicação do Edital de Inscrição de candidatos'',''45 dias antes do término do mandato'',-45,mviews.empresaId);
			PERFORM insere_etapa(''Término das inscrições dos candidatos'',''6 dias antes da eleição'',-36,mviews.empresaId);
			PERFORM insere_etapa(''Retirada do Edital de Inscrições'',''Dia seguinte ao encerramento das inscrições'',-35,mviews.empresaId);
			PERFORM insere_etapa(''Retirada do Edital de Convocação'',''No dia da eleição'',-30,mviews.empresaId);
			PERFORM insere_etapa(''Realização da eleição (votação)'',''30 dias antes do término do mandato anterior'',-30,mviews.empresaId);
			PERFORM insere_etapa(''Realização da apuração'',''Mesmo dia da eleição'',-30,mviews.empresaId);
			PERFORM insere_etapa(''Resultado da eleição - Ata da eleição'',''1 dia após a apuração'',-29,mviews.empresaId);
			PERFORM insere_etapa(''Curso para cipeiros (data mínima)'',''Depois da eleição'',-28,mviews.empresaId);
			PERFORM insere_etapa(''Comunicar ao sindicato o resultado e a data da posse'',''15 dias após a eleição'',-15,mviews.empresaId);
			PERFORM insere_etapa(''Curso para cipeiros (data máxima)'',''Antes da posse'',-2,mviews.empresaId);
			PERFORM insere_etapa(''Término do mandato anterior'',''1 ano depois da posse do mandato anterior'',0,mviews.empresaId);
			PERFORM insere_etapa(''Realização da Posse - Ata de Posse de novos membros'',''1º dia depois do mandato anterior'',0,mviews.empresaId);
			PERFORM insere_etapa(''Organização do Calendário de Reuniões Mensais'',''Na reunião da posse'',0,mviews.empresaId);
			PERFORM insere_etapa(''Registro da CIPA na DRT'',''Até 10 dias depois da posse'',5,mviews.empresaId);
    END LOOP;

    RETURN 1;
END;
' LANGUAGE plpgsql; --.go

select insere_etapas_eleicao(); --.go
drop function insere_etapas_eleicao(); --.go
drop function insere_etapa(CHARACTER, CHARACTER, INTEGER, BIGINT); --.go

-- versao 1.1.15.1
update parametrosdosistema set appversao = '1.1.15.1';--.go

CREATE FUNCTION to_ascii(bytea, name)
RETURNS text STRICT AS 'to_ascii_encname' LANGUAGE internal;--.go

CREATE OR REPLACE FUNCTION normalizar(a_string text)
RETURNS text AS '
BEGIN
	RETURN to_ascii(convert_to(a_string, ''latin1''), ''latin1'');
END
' LANGUAGE plpgsql;--.go

ALTER TABLE comissaoreuniao ADD COLUMN obsReuniaoAnterior text;--.go

ALTER TABLE eleicao RENAME COLUMN votacao TO votacaoIni;--.go
ALTER TABLE eleicao ADD COLUMN votacaoFim date;--.go

ALTER TABLE eleicao ADD COLUMN estabelecimento_id bigint;--.go
ALTER TABLE eleicao ADD CONSTRAINT eleicao_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);--.go

ALTER TABLE ocorrencia ADD COLUMN codigoac character varying(12);--.go

CREATE TABLE afastamento (
	id bigint NOT NULL,
	inss boolean,
	descricao character varying(100)
);--.go

ALTER TABLE afastamento ADD CONSTRAINT afastamento_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE afastamento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (440, 'ROLE_CAD_AFASTAMENTO', 'Afastamento', '/sesmt/afastamento/list.action', 18, true, 385);--.go
alter sequence papel_sequence restart with 441;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 440);--.go

-- versao 1.1.16.2
update parametrosdosistema set appversao = '1.1.16.2';--.go

CREATE TABLE cid (
	id bigint NOT NULL,
	codigo character varying(10),
	descricao character varying(100)
);--.go

ALTER TABLE cid ADD CONSTRAINT cid_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE cid_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

update parametrosdosistema set acversaowebservicecompativel='1.0.0.36';--.go

CREATE TABLE colaboradorafastamento (
	id bigint NOT NULL,
	inicio date,
	fim date,
	mediconome character varying(100),
	medicocrm  character varying(20),
	observacao text,
	afastamento_id bigint,
	colaborador_id bigint,
	cid_id bigint
);--.go

ALTER TABLE colaboradorafastamento ADD CONSTRAINT colaboradorafastamento_pkey PRIMARY KEY(id);--.go
ALTER TABLE colaboradorafastamento ADD CONSTRAINT colaboradorafastamento_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE colaboradorafastamento ADD CONSTRAINT colaboradorafastamento_afastamento_fk FOREIGN KEY (afastamento_id) REFERENCES afastamento(id);--.go
ALTER TABLE colaboradorafastamento ADD CONSTRAINT colaboradorafastamento_cid_fk FOREIGN KEY (cid_id) REFERENCES cid(id);--.go
CREATE SEQUENCE colaboradorafastamento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (441, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/list.action', 11, true, 386);--.go
alter sequence papel_sequence restart with 442;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 441);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (442, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/prepareRelatorioAfastamentos.action', 10, true, 387);--.go
alter sequence papel_sequence restart with 443;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 442);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (443, 'ROLE_CAT', 'CATs (Acidentes de Trabalho)', '/sesmt/cat/list.action', 12, true, 386);--.go
alter sequence papel_sequence restart with 444;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 443);--.go

alter table colaborador alter column ctpsserie type character varying(6);--.go
alter table candidato alter column ctpsserie type character varying(6);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (444, 'ROLE_CAD_CID', 'CID', '/sesmt/cid/list.action', 19, true, 385);--.go
alter sequence papel_sequence restart with 445;--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 444);--.go

update ocorrencia SET descricao = substring(descricao from 1 for 40) where length(descricao) > 40;--.go

ALTER TABLE ocorrencia ALTER COLUMN descricao TYPE character varying(40);--.go
ALTER TABLE ocorrencia ALTER COLUMN codigoAC TYPE character varying(3);--.go

ALTER TABLE solicitacao ADD COLUMN descricao character varying (100);--.go
UPDATE solicitacao set descricao='Solicitação de pessoal';--.go

ALTER TABLE papel DROP CONSTRAINT papel_papel_fk;--.go
ALTER TABLE perfil_papel DROP CONSTRAINT perfil_papel_papel_fk;--.go
TRUNCATE TABLE papel;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (382, 'ROLE_AVALDESEMPENHO', 'Aval. Desempenho', '#', 4, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (365, 'ROLE_T&D', 'T&D', '/desenvolvimento/indicadores/list.action', 5, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (373, 'ROLE_COLAB', 'Info. Funcionais', '#', 6, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (378, 'ROLE_INDICADORES', 'Indicadores', '#', 7, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (75, 'ROLE_SESMT', 'SESMT', '#', 8, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (37, 'ROLE_UTI', 'Utilitários', '#', 9, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (361, 'ROLE_C&S', 'C&S', '#', 2, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (353, 'ROLE_PES', 'Pesquisas', '#', 3, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (357, 'ROLE_R&S', 'R&S', '#', 1, true, NULL);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (358, 'ROLE_R&S_CAD', 'Cadastros', '#', 1, true, 357);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (2, 'ROLE_CAD_CANDIDATO', 'Candidatos', '/captacao/candidato/list.action', 1, true, 358);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (3, 'ROLE_CAD_ETAPA', 'Etapas Seletivas', '/captacao/etapaSeletiva/list.action', 2, true, 358);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (6, 'ROLE_CAD_BDS_EMPRESA', 'Empresas BDS', '/captacao/empresaBds/list.action', 3, true, 358);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (4, 'ROLE_CAD_AREA', 'Áreas de Interesse', '/geral/areaInteresse/list.action', 4, true, 358);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (397, 'ROLE_AREAFORMACAO', 'Áreas de Formação', '/geral/areaFormacao/list.action', 5, true, 358);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (57, 'ROLE_MOTIVO_SOLICITACAO', 'Motivos de Solicitação de Pessoal', '/captacao/motivoSolicitacao/list.action', 6, true, 358);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (359, 'ROLE_R&S_MOV', 'Movimentações', '#', 2, true, 357);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (21, 'ROLE_MOV_SOLICITACAO', 'Solicitação de Pessoal', '/captacao/solicitacao/list.action', 1, true, 359);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (50, 'ROLE_BD_SOLIDARIO', 'Banco de Dados Solidário', '/captacao/candidato/prepareBusca.action?BDS=true', 2, true, 359);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (360, 'ROLE_R&S_REL', 'Relatórios', '#', 3, true, 357);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (46, 'ROLE_REL_SOLICITACAO', 'Solicitações Abertas', '/captacao/solicitacao/prepareRelatorio.action', 1, true, 360);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (48, 'ROLE_REL_PROCESSO_SELETIVO', 'Processos Seletivos', '/captacao/solicitacao/prepareRelatorioProcessoSeletivo.action', 2, true, 360);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (424, 'ROLE_REL_AVALIACAO_CANDIDATOS', 'Avaliações de Candidatos', '/captacao/candidato/prepareRelatorioAvaliacaoCandidatos.action', 3, true, 360);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (56, 'ROLE_LIBERA_SOLICITACAO', 'Liberador de Solicitação', '#', 4, false, 357);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (45, 'ROLE_MOV_SOLICITACAO_SELECAO', 'Recrutador(a)', '#', 4, false, 357);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (22, 'ROLE_MOV_SOLICITACAO_CANDIDATO', 'Ver Candidatos da Solicitação', '#', 4, false, 357);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (362, 'ROLE_C&S_CAD', 'Cadastros', '#', 1, true, 361);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (9, 'ROLE_CAD_AREA', 'Áreas Organizacionais', '/geral/areaOrganizacional/list.action', 1, true, 362);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (10, 'ROLE_CAD_GRUPO', 'Grupos Ocupacionais', '/cargosalario/grupoOcupacional/list.action', 2, true, 362);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (5, 'ROLE_CAD_CONHECIMENTO', 'Conhecimentos', '/captacao/conhecimento/list.action', 3, true, 362);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (11, 'ROLE_CAD_CARGO', 'Cargos e Faixas', '/cargosalario/cargo/list.action', 4, true, 362);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (404, 'ROLE_CAD_INDICE', 'Índices', '/cargosalario/indice/list.action', 5, true, 362);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (363, 'ROLE_C&S_MOV', 'Movimentações', '#', 2, true, 361);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (26, 'ROLE_MOV_SIMULACAOREAJUSTE', 'Tabelas de Realinhamentos', '/cargosalario/tabelaReajusteColaborador/list.action', 1, true, 363);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (49, 'ROLE_MOV_SOLICITACAOREAJUSTE', 'Solicitação de Realinhamento de C&S', '/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action', 2, true, 363);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (395, 'ROLE_DISSIDIO', 'Reajuste Coletivo', '/cargosalario/reajusteColaborador/prepareDissidio.action', 3, true, 363);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (364, 'ROLE_C&S_REL', 'Relatórios', '#', 3, true, 361);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (54, 'ROLE_REL_CARGO', 'Cargos', '/cargosalario/cargo/prepareRelatorioCargo.action', 1, true, 364);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (35, 'ROLE_REL_SIMULACAOREAJUSTE', 'Realinhamentos', '/cargosalario/reajusteRelatorio/formFiltro.action', 2, true, 364);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (396, 'ROLE_REL_AREAORGANIZACIONAL', 'Áreas Organizacionais', '/geral/areaOrganizacionalRelatorio/formFiltro.action', 3, true, 364);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (406, 'ROLE_MOV_TABELA', 'Análise de Tabela Salarial', '/cargosalario/faixaSalarialHistorico/analiseTabelaSalarialFiltro.action', 4, true, 364);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (407, 'ROLE_REL_PROJECAO_SALARIAL', 'Projeção Salarial', '/geral/colaborador/prepareProjecaoSalarialFiltro.action',5, true, 364);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (417, 'ROLE_TRANSFERIR_FAIXAS_AC', 'Transferir Faixas entre Cargos', '', 4, false, 361);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (355, 'ROLE_PES_MOV', 'Movimentações', '#', 1, true, 353);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (28, 'ROLE_MOV_QUESTIONARIO', 'Pesquisas', '/pesquisa/pesquisa/list.action', 1, true, 355);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (401, 'ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO', 'Pode Respoder Pesquisa Por Outro Usuário', '#', 4, false, 353);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (384, 'ROLE_AVALDESEMPENHO_MOV', 'Movimentações', '#', 1, true, 382);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (55, 'ROLE_MOV_AVALIACAO', 'Avaliações', '/pesquisa/avaliacao/list.action', 1, true, 384);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (366, 'ROLE_T&D_CAD', 'Cadastros', '#', 1, true, 365);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (13, 'ROLE_CAD_PRIORIDADETREINAMENTO', 'Prioridades de Treinamento', '/desenvolvimento/prioridadeTreinamento/list.action', 1, true, 366);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (23, 'ROLE_MOV_CURSO', 'Cursos/Treinamentos', '/desenvolvimento/curso/list.action', 2, true, 366);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (413, 'ROLE_MOV_AVALIACAO_CURSO', 'Avaliações dos Cursos ', '/desenvolvimento/avaliacaoCurso/list.action', 3, true, 366);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (419, 'ROLE_AVALIACAO_TURMA', 'Modelos de Avaliação de Turma', '/pesquisa/avaliacaoTurma/list.action', 4, true, 366);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (420, 'ROLE_CAD_CERTIFICACAO', 'Certificações', '/desenvolvimento/certificacao/list.action', 5, true, 366);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (367, 'ROLE_T&D_MOV', 'Movimentações', '#', 2, true, 365);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (64, 'ROLE_MOV_CURSO_DNT', 'DNT', '/desenvolvimento/dnt/list.action', 1, true, 367);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (72, 'ROLE_MOV_CURSO_DNT_GESTOR', 'Preenchimento da DNT', '/desenvolvimento/dnt/list.action?gestor=true', 2, true, 367);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (71, 'ROLE_FREQUENCIA', 'Frequência', '/desenvolvimento/turma/prepareFrequencia.action', 3, true, 367);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (414, 'ROLE_MOV_PLANO_TREINAMENTO', 'Plano de Treinamento', '/desenvolvimento/turma/filtroPlanoTreinamento.action', 4, true, 367);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (368, 'ROLE_T&D_REL', 'Relatórios', '#', 3, true, 365);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (31, 'ROLE_REL_MATRIZ', 'Matriz de Qualificação', '/desenvolvimento/turma/prepareImprimirMatriz.action', 1, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (32, 'ROLE_REL_PLANO', 'Plano de Treinamento', '/desenvolvimento/turma/prepareImprimirTurma.action', 2, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (33, 'ROLE_REL_SEM_INDICACAO', 'Colaboradores sem Indicação de Trein.', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorSemIndicacao.action', 3, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (43, 'ROLE_REL_DESENVOLVIMENTO_LISTA_PRESENCA', 'Lista de Frequência', '/desenvolvimento/relatorioPresenca/prepareRelatorio.action', 4, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (61, 'ROLE_CERTIFICADO_CURSO', 'Certificados', '/desenvolvimento/turma/prepareImprimirCertificado.action', 5, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (418, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Cronograma de Treinamento', '/desenvolvimento/turma/filtroCronogramaTreinamento.action', 6, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (415, 'ROLE_REL_COLABORADOR_SEM_TREINAMENTO', 'Colaboradores sem Treinamentos', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action', 7, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (416, 'ROLE_REL_COLABORADOR_COM_TREINAMENTO', 'Colaboradores com Treinamentos', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action?comTreinamento=true', 8, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (421, 'ROLE_REL_MATRIZ_TREINAMENTO', 'Matriz de Treinamentos', '/desenvolvimento/certificacao/matrizTreinamento.action', 9, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (422, 'ROLE_REL_HISTORICO_TREINAMENTOS', 'Histórico de Treinamentos', '/desenvolvimento/colaboradorTurma/prepareFiltroHistoricoTreinamentos.action', 10, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (63, 'ROLE_MOV_TURMA', 'Pode Cadastrar Turma', '/desenvolvimento/turma/list.action', 4, false, 365);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (374, 'ROLE_COLAB_CAD', 'Cadastros', '#', 1, true, 373);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (15, 'ROLE_CAD_GRUPOGASTO', 'Grupos de Investimento', '/geral/grupoGasto/list.action', 1, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (16, 'ROLE_CAD_GASTO', 'Investimentos', '/geral/gasto/list.action', 2, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (12, 'ROLE_CAD_BENEFICIO', 'Benefícios', '/geral/beneficio/list.action', 3, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (60, 'ROLE_CAD_MOTIVO_DEMISSAO', 'Motivos de Desligamento', '/geral/motivoDemissao/list.action', 4, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (402, 'ROLE_MOV_QUESTIONARIO', 'Modelos de Entrevistas de Desligamento', '/pesquisa/entrevista/list.action', 5, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (62, 'ROLE_CAD_OCORRENCIA', 'Ocorrências', '/geral/ocorrencia/list.action', 6, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (8, 'ROLE_CAD_COLABORADOR', 'Colaboradores', '/geral/colaborador/list.action', 7, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (412, 'ROLE_CAD_INFO_PESSOAL', 'Atualizar meus dados', '/geral/colaborador/prepareUpdateInfoPessoais.action', 8, true, 374);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (376, 'ROLE_COLAB_MOV', 'Movimentações', '#', 2, true, 373);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (29, 'ROLE_MOV_GASTO_GASTOEMPRESA', 'Investimentos da Empresa', '/geral/gastoEmpresa/list.action', 11, true, 376);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (377, 'ROLE_COLAB_REL', 'Relatórios', '#', 3, true, 373);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (93, 'ROLE_REL_OCORRENCIA', 'Ocorrências', '/geral/ocorrencia/prepareRelatorioOcorrencia.action', 1, true, 377);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (400, 'ROLE_REL_MOTIVO_DEMISSAO', 'Motivos de Desligamento', '/geral/motivoDemissao/prepareRelatorioMotivoDemissao.action', 2, true, 377);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (403, 'ROLE_MOV_QUESTIONARIO', 'Resultados das Entrevistas', '/pesquisa/questionario/prepareResultadoEntrevista.action', 3, true, 377);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (423, 'ROLE_REL_ANIVERSARIANTES', 'Aniversariantes do mês', '/geral/colaborador/prepareRelatorioAniversariantes.action', 4, true, 377);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (381, 'ROLE_INDICADORES_REL', 'Relatórios', '#', 1, true, 378);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (73, 'ROLE_IND', 'Motivos de Preenchimentos de Vagas', '/indicador/duracaoPreenchimentoVaga/prepareMotivo.action', 1, true, 381);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (69, 'ROLE_IND', 'Duração para Preenchimento de Vagas', '/indicador/duracaoPreenchimentoVaga/prepare.action', 2, true, 381);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (47, 'ROLE_REL_PRODUTIVIDADE', 'Produtividade do Setor', '/captacao/produtividade/prepareProdutividade.action', 3, true, 381);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (70, 'ROLE_REL_PROMOCAO', 'Promoções', '/cargosalario/historicoColaborador/prepareRelatorioPromocoes.action', 4, true, 381);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (398, 'ROLE_REL_TURNOVER', 'Turnover', '/indicador/indicadorTurnOver/prepare.action', 5, true, 381);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (36, 'ROLE_REL_GASTOEMPRESA', 'Investimentos da Empresa', '/geral/gastoEmpresa/prepareImprimir.action', 6, true, 381);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (390, 'ROLE_UTI', 'Cadastros', '#', 1, true, 37);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (58, 'ROLE_UTI_EMPRESA', 'Empresas', '/geral/empresa/list.action', 1, true, 390);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (18, 'ROLE_CAD_PERFIL', 'Perfis', '/acesso/perfil/list.action', 2, true, 390);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (393, 'ROLE_CAD_ESTABELECIMENTO', 'Estabelecimentos', '/geral/estabelecimento/list.action', 3, true, 390);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (19, 'ROLE_CAD_USUARIO', 'Usuários', '/acesso/usuario/list.action', 4, true, 390);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (394, 'ROLE_CAD_BAIRRO', 'Bairros', '/geral/bairro/list.action', 5, true, 390);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (391, 'ROLE_UTI', 'Movimentações', '#', 2, true, 37);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (38, 'ROLE_UTI_SENHA', 'Alterar Senha', '/acesso/usuario/prepareUpdateSenhaUsuario.action', 1, true, 391);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (41, 'ROLE_UTI_CONFIGURACAO', 'Configurações', '/geral/parametrosDoSistema/prepareUpdate.action', 2, true, 391);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (39, 'ROLE_UTI_AUDITORIA', 'Auditoria', '/security/auditoria/prepareList.action', 3, true, 391);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (44, 'ROLE_UTI_HISTORICO_VERSAO', 'Histórico de Versões', '/geral/documentoVersao/list.action', 4, true, 391);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (409, 'ROLE_UTI', 'Enviar Mensagem', '/geral/usuarioMensagem/prepareUpdate.action', 5, true, 391);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (408, 'ROLE_MOV_SOLICITACAO_REALINHAMENTO', 'Pode Solicitar Realinhamento', '', 4, false, 361);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (410, 'RECEBE_ALERTA_SETORPESSOAL', 'Recebe Mensagem do AC Pessoal', '', 4, false, 37);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (411, 'ROLE_VISUALIZAR_PENDENCIA_AC', 'Visualizar as pendências do AC', '', 4, false, 37);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (59, 'ROLE_PESQUISA', 'Pode ver e responder Pesquisa', '#', 4, false, 353);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (385, 'ROLE_SESMT', 'Cadastros', '#', 1, true, 75);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (76, 'ROLE_CAD_AMBIENTE', 'Ambientes', '/sesmt/ambiente/list.action', 1, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (77, 'ROLE_CAD_TIPO_EPI', 'Tipos de EPI', '/sesmt/tipoEPI/list.action', 2, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (78, 'ROLE_CAD_EPI', 'EPI', '/sesmt/epi/list.action', 3, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (86, 'ROLE_CAD_EPC', 'EPC', '/sesmt/epc/list.action', 4, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (79, 'ROLE_CAD_RISCO', 'Riscos', '/sesmt/risco/list.action', 5, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (89, 'ROLE_CAD_ENGENHEIRO_TRABALHO', 'Engenheiros Responsáveis', '/sesmt/engenheiroResponsavel/list.action', 6, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (90, 'ROLE_CAD_MEDICO_COORDENADOR', 'Médicos Coordenadores', '/sesmt/medicoCoordenador/list.action', 7, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (91, 'ROLE_CAD_CLINICA_AUTORIZADA', 'Clínicas e Médicos Autorizados', '/sesmt/clinicaAutorizada/list.action', 8, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (87, 'ROLE_CAD_EXAME', 'Exames', '/sesmt/exame/list.action', 9, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (83, 'ROLE_GHE', 'GHE', '/sesmt/ghe/list.action', 10, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (80, 'ROLE_LTCAT', 'LTCAT', '/sesmt/LTCAT/list.action', 11, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (84, 'ROLE_PPRA', 'PPRA', '/sesmt/ppra/list.action', 12, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (92, 'ROLE_CAD_PCMSO', 'PCMSO', '/sesmt/pcmso/list.action', 13, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (427, 'ROLE_CAD_FICHAMEDICA', 'Modelos de Fichas Médicas', '/pesquisa/fichaMedica/list.action', 14, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (437, 'ROLE_CAD_ETAPAPROCESSOELEITORAL', 'CIPA - Etapas do Processo Eleitoral', '/sesmt/etapaProcessoEleitoral/list.action', 15, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (438, 'ROLE_CAD_ELEICAO', 'CIPA - Eleições', '/sesmt/eleicao/list.action', 16, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (439, 'ROLE_CAD_ELEICAO', 'CIPA - Comissões', '/sesmt/comissao/list.action', 17, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (440, 'ROLE_CAD_AFASTAMENTO', 'Tipos de Afastamentos', '/sesmt/afastamento/list.action', 18, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (444, 'ROLE_CAD_CID', 'CID', '/sesmt/cid/list.action', 19, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (386, 'ROLE_SESMT', 'Movimentações', '#', 2, true, 75);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (426, 'ROLE_CAD_SOLICITACAOEXAME', 'Solicitação de Exames', '/sesmt/solicitacaoExame/list.action', 1, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (81, 'ROLE_TAB_RUIDO', 'Levantamentos de Ruído', '/sesmt/TabelaRuido/list.action', 2, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (82, 'ROLE_TAB_TEMPERATURA', 'Levantamentos de Temperatura', '/sesmt/tabelaTemperatura/list.action', 3, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (95, 'ROLE_TAB_ILUMINAMENTO', 'Levantamentos de Iluminamento', '/sesmt/tabelaIluminamento/list.action', 4, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (66, 'ROLE_SESMT_MUDANCA_FUNCAO', 'Mudança de Função', '/sesmt/funcao/mudancaFuncaoFiltro.action', 5, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (425, 'ROLE_CAD_PRONTUARIO', 'Registro de Prontuário', '/sesmt/prontuario/list.action', 6, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (428, 'ROLE_CAD_FICHAMEDICA', 'Fichas Médicas', '/pesquisa/fichaMedica/listPreenchida.action', 7, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (433, 'ROLE_CAD_SOLICITACAOEPI', 'Solicitação de EPIs', '/sesmt/solicitacaoEpi/list.action', 8, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (435, 'ROLE_CAD_ENTREGAEPI', 'Entrega de EPIs', '/sesmt/solicitacaoEpi/list.action', 9, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (441, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/list.action', 10, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (443, 'ROLE_CAT', 'CATs (Acidentes de Trabalho)', '/sesmt/cat/list.action', 11, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (387, 'ROLE_SESMT', 'Relatórios', '#', 3, true, 75);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (388, 'ROLE_LTCAT', 'LTCAT', '/sesmt/LTCAT/prepareRelatorioLtcat.action', 1, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (389, 'ROLE_CAT_PCMSO', 'PCMSO', '/sesmt/pcmso/prepareRelatorioPcmso.action', 2, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (85, 'ROLE_PPP', 'PPP', '/sesmt/ppp/list.action', 3, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (429, 'ROLE_REL_PRONTUARIO', 'Prontuário', '/sesmt/prontuario/prepareRelatorioProntuario.action', 4, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (430, 'ROLE_REL_ASO', 'ASOs', '/sesmt/exame/prepareImprimirAso.action', 5, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (431, 'ROLE_REL_EXAMES_PREVISTOS', 'Exames Previstos', '/sesmt/exame/prepareRelatorioExamesPrevistos.action', 6, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (432, 'ROLE_REL_FICHA_EPI', 'Ficha de EPI', '/sesmt/epi/prepareImprimirFicha.action', 7, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (434, 'ROLE_CAD_EPICAVENCER', 'EPIs com CA a Vencer', '/sesmt/epi/prepareImprimirVencimentoCa.action', 8, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (436, 'ROLE_REL_EPIVENCIMENTO', 'EPIs com Prazo a Vencer', '/sesmt/solicitacaoEpi/prepareRelatorioVencimentoEpi.action', 9, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (442, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/prepareRelatorioAfastamentos.action', 10, true, 387);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (74, 'ROLE_FUNCAO', 'Funções', '/sesmt/funcao/list.action', 4, false, 75);--.go

ALTER TABLE papel ADD CONSTRAINT papel_papel_fk FOREIGN KEY (papelmae_id) REFERENCES papel(id);--.go
ALTER TABLE perfil_papel ADD CONSTRAINT perfil_papel_papel_fk FOREIGN KEY (papeis_id) REFERENCES papel(id);--.go

-- versao 1.1.17.3
CREATE TABLE extintor (
	id bigint NOT NULL,
	descricao character varying(100),
	localizacao character varying(50),
	tipo character varying(1),
	numeroCilindro int,
	capacidade character varying(10),
	fabricante character varying(50),
	periodoMaxRecarga int,
	periodoMaxInspecao int,
	periodoMaxHidrostatico int,
	ativo boolean,
	empresa_id bigint,
	estabelecimento_id bigint
);--.go

ALTER TABLE extintor ADD CONSTRAINT extintor_pkey PRIMARY KEY(id);--.go
ALTER TABLE extintor ADD CONSTRAINT extintor_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
ALTER TABLE extintor ADD CONSTRAINT extintor_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);--.go
CREATE SEQUENCE extintor_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (445, 'ROLE_CAD_EXTINTOR', 'Extintores', '/sesmt/extintor/list.action', 20, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 445);--.go
alter sequence papel_sequence restart with 446;--.go

CREATE TABLE extintorinspecao (
	id bigint NOT NULL,
	data date,
	empresaResponsavel character varying(50),
	observacao text,
	extintor_id bigint
);--.go

ALTER TABLE extintorinspecao ADD CONSTRAINT extintorinspecao_pkey PRIMARY KEY(id);--.go
ALTER TABLE extintorinspecao ADD CONSTRAINT extintorinspecao_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);--.go
CREATE SEQUENCE extintorinspecao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE extintorinspecaoitem (
	id bigint NOT NULL,
	descricao character varying(50)
);--.go
ALTER TABLE extintorinspecaoitem ADD CONSTRAINT extintorinspecaoitem_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE extintorinspecaoitem_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE extintorinspecao_extintorinspecaoitem (
	extintorinspecao_id bigint NOT NULL,
	itens_id bigint NOT NULL
);--.go
ALTER TABLE extintorinspecao_extintorinspecaoitem ADD CONSTRAINT extintorinspecao_extintorinspecaoitem_extintorinspecao_fk FOREIGN KEY (extintorinspecao_id) REFERENCES extintorinspecao(id);--.go
ALTER TABLE extintorinspecao_extintorinspecaoitem ADD CONSTRAINT extintorinspecao_extintorinspecaoitem_extintorinspecaoitem_fk FOREIGN KEY (itens_id) REFERENCES extintorinspecaoitem(id);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (446, 'ROLE_CAD_EXTINTOR', 'Extintores - Inspeção', '/sesmt/extintorInspecao/list.action', 12, true, 386);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 446);--.go
alter sequence papel_sequence restart with 447;--.go

alter table ocorrencia add column integraac boolean; --.go
update ocorrencia set integraac=false;--.go

INSERT INTO extintorinspecaoitem VALUES(1, 'Lacre');--.go
INSERT INTO extintorinspecaoitem VALUES(2, 'Selo');--.go
INSERT INTO extintorinspecaoitem VALUES(3, 'Trava');--.go
INSERT INTO extintorinspecaoitem VALUES(4, 'Manômetro');--.go
INSERT INTO extintorinspecaoitem VALUES(5, 'Sinalização Vertical');--.go
INSERT INTO extintorinspecaoitem VALUES(6, 'Sinalização Horizontal');--.go
INSERT INTO extintorinspecaoitem VALUES(7, 'Localização');--.go
INSERT INTO extintorinspecaoitem VALUES(8, 'Alça');--.go
INSERT INTO extintorinspecaoitem VALUES(9, 'Gatilho');--.go
INSERT INTO extintorinspecaoitem VALUES(10, 'Mangueira');--.go


CREATE TABLE extintormanutencao (
		id bigint NOT NULL,
		saida date,
		retorno date,
		motivo character varying(1),
		outroMotivo character varying(50),
		observacao text,
		extintor_id bigint
);--.go

ALTER TABLE extintormanutencao ADD CONSTRAINT extintormanutencao_pkey PRIMARY KEY(id);--.go
ALTER TABLE extintormanutencao ADD CONSTRAINT extintormanutencao_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);--.go
CREATE SEQUENCE extintormanutencao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE extintormanutencaoservico (
	id bigint NOT NULL,
	descricao character varying(50)
);--.go
ALTER TABLE extintormanutencaoservico ADD CONSTRAINT extintormanutencaoservico_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE extintormanutencaoservico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE extintormanutencao_extintormanutencaoservico (
	extintormanutencao_id bigint NOT NULL,
	servicos_id bigint NOT NULL
);--.go
ALTER TABLE extintormanutencao_extintormanutencaoservico ADD CONSTRAINT extintormanutencao_fk FOREIGN KEY (extintormanutencao_id) REFERENCES extintormanutencao(id);--.go
ALTER TABLE extintormanutencao_extintormanutencaoservico ADD CONSTRAINT extintormanutencaoservico_fk FOREIGN KEY (servicos_id) REFERENCES extintormanutencaoservico(id);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (447, 'ROLE_CAD_EXTINTOR', 'Extintores - Manutenção', '/sesmt/extintorManutencao/list.action', 13, true, 386);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 447);--.go
alter sequence papel_sequence restart with 448;--.go

INSERT INTO extintormanutencaoservico VALUES(1, 'Recarga');--.go
INSERT INTO extintormanutencaoservico VALUES(2, 'Pintura');--.go
INSERT INTO extintormanutencaoservico VALUES(3, 'Teste Hidrostático');--.go
INSERT INTO extintormanutencaoservico VALUES(4, 'Manômetro');--.go
INSERT INTO extintormanutencaoservico VALUES(5, 'Substituição de Gatilho');--.go
INSERT INTO extintormanutencaoservico VALUES(6, 'Válvula de Segurança');--.go
INSERT INTO extintormanutencaoservico VALUES(7, 'Substituição de Difusor');--.go
INSERT INTO extintormanutencaoservico VALUES(8, 'Válvula Completa');--.go
INSERT INTO extintormanutencaoservico VALUES(9, 'Mangote');--.go
INSERT INTO extintormanutencaoservico VALUES(10, 'Válvula Cilindro Adicional');--.go

UPDATE papel SET papelmae_id=353,nome='Pode ver e responder Pesquisa',ordem=4 WHERE id = 59;--.go

ALTER TABLE colaboradorafastamento DROP CONSTRAINT colaboradorafastamento_cid_fk;--.go
ALTER TABLE colaboradorafastamento DROP COLUMN cid_id;--.go
ALTER TABLE colaboradorafastamento ADD COLUMN cid character varying(10);--.go
DROP TABLE cid;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (448, 'ROLE_CAD_EXTINTOR', 'Extintores - Manutenção e Inspeção', '/sesmt/extintor/prepareRelatorio.action', 11, true, 387);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 448);--.go
alter sequence papel_sequence restart with 449;--.go

UPDATE colaborador SET matricula=codigoAC WHERE ((matricula is null OR matricula = '') AND codigoAC is not null);--.go
alter table epihistorico drop column emissao;--.go

UPDATE epihistorico SET validadeuso = (validadeuso*30);--.go
update parametrosdosistema set appversao = '1.1.17.3';--.go

-- versao 1.1.18.4
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (449, 'ROLE_CAD_FICHAMEDICA', 'Resultado de Fichas Médicas', '/pesquisa/questionario/prepareResultadoFichaMedica.action', 12, true, 387);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 449);--.go
alter sequence papel_sequence restart with 450;--.go

ALTER TABLE clinicaautorizada ADD COLUMN telefone character varying(10);--.go
ALTER TABLE clinicaautorizada ADD COLUMN horarioAtendimento character varying(30);--.go

ALTER TABLE reajustecolaborador ADD COLUMN observacao character varying(100);--.go

ALTER TABLE experiencia ADD COLUMN salario double precision;--.go
ALTER TABLE experiencia ADD COLUMN motivoSaida character varying(100);--.go

update parametrosdosistema set appversao = '1.1.18.4';--.go

-- versao 1.1.19.5
ALTER TABLE risco DROP COLUMN trajetoria;--.go
ALTER TABLE risco DROP COLUMN danos;--.go
ALTER TABLE risco DROP COLUMN tabelaRisco;--.go

ALTER TABLE ambiente ADD COLUMN estabelecimento_id bigint;--.go
ALTER TABLE ambiente ADD CONSTRAINT ambiente_estabelecimento_fk FOREIGN KEY (estabelecimento_id)  REFERENCES estabelecimento(id);--.go

DROP TABLE ambiente_areaorganizacional;--.go
DROP TABLE riscoghe;--.go

CREATE TABLE riscoambiente (
	id bigint NOT NULL,
    epceficaz boolean,
    historicoambiente_id bigint,
    risco_id bigint
);--.go

ALTER TABLE riscoambiente ADD CONSTRAINT riscoambiente_pkey PRIMARY KEY (id);--.go
ALTER TABLE riscoambiente ADD CONSTRAINT riscoambiente_historicoambiente_fk FOREIGN KEY (historicoambiente_id) REFERENCES historicoambiente(id);--.go
ALTER TABLE riscoambiente ADD CONSTRAINT riscoambiente_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);--.go
CREATE SEQUENCE riscoambiente_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE colaboradorocorrencia ALTER COLUMN observacao TYPE text;--.go

DROP TABLE epc_ambiente;--.go

CREATE TABLE historicoambiente_epc (
    historicoambiente_id bigint NOT NULL,
    epcs_id bigint NOT NULL
);--.go
ALTER TABLE historicoambiente_epc ADD CONSTRAINT historicoambiente_epc_histambiente_fk FOREIGN KEY (historicoambiente_id) REFERENCES historicoambiente(id);--.go
ALTER TABLE historicoambiente_epc ADD CONSTRAINT historicoambiente_epc_epc_fk FOREIGN KEY (epcs_id) REFERENCES epc(id);--.go

delete from perfil_papel where papeis_id in (select id from papel where codigo = ('ROLE_CAD_CID'));--.go
delete from papel where codigo = ('ROLE_CAD_CID');--.go

CREATE TABLE medicaorisco (
	id bigint NOT NULL,
    data date,
    ambiente_id bigint
);--.go

ALTER TABLE medicaorisco ADD CONSTRAINT medicaorisco_pkey PRIMARY KEY(id);--.go
ALTER TABLE medicaorisco ADD CONSTRAINT medicaorisco_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);--.go
CREATE SEQUENCE medicaorisco_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE riscomedicaorisco (
	id bigint NOT NULL,
	descricaoppra text,
	descricaoltcat text,
	intensidademedida character varying(20),
	tecnicautilizada character varying(100),
	medicaorisco_id bigint,
	risco_id bigint
);--.go

ALTER TABLE riscomedicaorisco ADD CONSTRAINT riscomedicaorisco_pkey PRIMARY KEY(id);--.go
ALTER TABLE riscomedicaorisco ADD CONSTRAINT riscomedicaorisco_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);--.go
ALTER TABLE riscomedicaorisco ADD CONSTRAINT riscomedicaorisco_medicaorisco_fk FOREIGN KEY (medicaorisco_id) REFERENCES medicaorisco(id);--.go
CREATE SEQUENCE riscomedicaorisco_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

delete from perfil_papel where papeis_id in (select id from papel where codigo = ('ROLE_TAB_RUIDO'));--.go
delete from papel where codigo = ('ROLE_TAB_RUIDO');--.go
delete from perfil_papel where papeis_id in (select id from papel where codigo = ('ROLE_TAB_TEMPERATURA'));--.go
delete from papel where codigo = ('ROLE_TAB_TEMPERATURA');--.go
delete from perfil_papel where papeis_id in (select id from papel where codigo = ('ROLE_TAB_ILUMINAMENTO'));--.go
delete from papel where codigo = ('ROLE_TAB_ILUMINAMENTO');--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (450, 'ROLE_CAD_MEDICAORISCO', 'Medição dos Riscos', '/sesmt/medicaoRisco/list.action', 2, true, 386);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 450);--.go

alter sequence papel_sequence restart with 451;--.go

delete from perfil_papel where papeis_id in (select id from papel where codigo = ('ROLE_LTCAT'));--.go
delete from papel where codigo = ('ROLE_LTCAT');--.go
delete from perfil_papel where papeis_id in (select id from papel where codigo = ('ROLE_GHE'));--.go
delete from papel where codigo = ('ROLE_GHE');--.go

UPDATE papel set codigo='ROLE_PPRA', nome='PPRA e LTCAT', url='/sesmt/ppra/prepareRelatorio.action' where id=388;--.go

ALTER TABLE historicoambiente ADD COLUMN tempoexposicao character varying(40);--.go

ALTER TABLE engenheiroresponsavel RENAME COLUMN apartirde to inicio;--.go
ALTER TABLE engenheiroresponsavel ADD COLUMN fim date;--.go
ALTER TABLE engenheiroresponsavel ADD COLUMN nit character varying(15);--.go

ALTER TABLE medicocoordenador RENAME COLUMN apartirde to inicio;--.go
ALTER TABLE medicocoordenador ADD COLUMN fim date;--.go
ALTER TABLE medicocoordenador ADD COLUMN nit character varying(15);--.go

DROP TABLE tabelatemperatura CASCADE;--.go
DROP SEQUENCE tabelatemperatura_sequence;--.go
DROP TABLE tabelatemperaturaitem;--.go
DROP SEQUENCE tabelatemperaturaitem_sequence;--.go
DROP TABLE tabelailuminamento CASCADE;--.go
DROP SEQUENCE tabelailuminamento_sequence;--.go
DROP TABLE tabelailuminamentoitem;--.go
DROP SEQUENCE tabelailuminamentoitem_sequence;--.go
DROP TABLE tabelaruido CASCADE;--.go
DROP SEQUENCE tabelaruido_sequence;--.go
DROP TABLE tabelaruidoitem;--.go
DROP SEQUENCE tabelaruidoitem_sequence;--.go
DROP TABLE ghe CASCADE;--.go
DROP SEQUENCE ghe_sequence;--.go
DROP TABLE ghehistorico CASCADE;--.go
DROP SEQUENCE ghehistorico_sequence;--.go
DROP TABLE ghehistorico_funcao CASCADE;--.go
DROP TABLE ppra CASCADE;--.go
DROP SEQUENCE ppra_sequence;--.go
DROP TABLE ltcat CASCADE;--.go
DROP SEQUENCE ltcat_sequence;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (451, 'ROLE_LOGGING', 'Logs', '/logging/list.action', 5, true, 37);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 451);--.go
alter sequence papel_sequence restart with 452;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (452, 'ROLE_CAD_EVENTO', 'Eventos', '/sesmt/evento/list.action', 21, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 452);--.go
alter sequence papel_sequence restart with 453;--.go

CREATE TABLE evento (
	id bigint NOT NULL,
	nome character varying(100)
);--.go

ALTER TABLE evento ADD CONSTRAINT evento_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE evento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE agenda (
	id bigint NOT NULL,
	data date,
	evento_id bigint,
	estabelecimento_id bigint
);--.go

ALTER TABLE agenda ADD CONSTRAINT agenda_pkey PRIMARY KEY(id);--.go
ALTER TABLE ONLY agenda ADD CONSTRAINT agenda_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id); --.go
ALTER TABLE ONLY agenda ADD CONSTRAINT agenda_evento_fk FOREIGN KEY (evento_id) REFERENCES evento(id); --.go
CREATE SEQUENCE agenda_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (453, 'ROLE_CAD_AGENDA', 'Agendas', '/sesmt/agenda/list.action', 22, true, 385);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 453);--.go
alter sequence papel_sequence restart with 454;--.go

update estabelecimento SET bairro = substring(bairro from 1 for 20) where length(bairro) > 20;--.go
update candidato SET bairro = substring(bairro from 1 for 20) where length(bairro) > 20;--.go
update colaborador SET bairro = substring(bairro from 1 for 20) where length(bairro) > 20;--.go
ALTER TABLE estabelecimento ALTER COLUMN bairro TYPE character varying(20);--.go
ALTER TABLE candidato ALTER COLUMN bairro TYPE character varying(20);--.go
ALTER TABLE colaborador ALTER COLUMN bairro TYPE character varying(20);--.go

delete from perfil_papel where papeis_id=92;--.go
delete from papel where id=92;--.go
update papel set url='/sesmt/pcmso/prepareRelatorio.action' where id=389;--.go
update papel set codigo='ROLE_CAD_PCMSO' where id=389;--.go
drop table pcmso;--.go
drop sequence pcmso_sequence;--.go

update papel set nome='Estatísticas de Vagas por Motivo' where id=73; --.go
update papel set nome='Turnover (rotatividade)' where id=398;--.go
UPDATE papel set url='#' where id=365;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (454, 'ROLE_T&D_REL', 'Painel de Indicadores', '/desenvolvimento/indicadores/list.action', 4, true, 365);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 454);--.go
alter sequence papel_sequence restart with 455;--.go

CREATE TABLE historicofuncao_epi (
    historicofuncao_id bigint NOT NULL,
    epis_id bigint NOT NULL
);--.go
ALTER TABLE historicofuncao_epi ADD CONSTRAINT historicofuncao_epi_epi_fk FOREIGN KEY (epis_id) REFERENCES epi(id);--.go
ALTER TABLE historicofuncao_epi ADD CONSTRAINT historicofuncao_epi_historicofuncao_fk FOREIGN KEY (historicofuncao_id) REFERENCES historicofuncao(id);--.go

ALTER TABLE auditoria ADD COLUMN chave character varying(255);--.go

ALTER TABLE cat ADD COLUMN gerouafastamento boolean DEFAULT false;--.go --bob

UPDATE papel set ordem=1 where id=77;--.go
UPDATE papel set ordem=2 where id=78;--.go
UPDATE papel set ordem=3 where id=86;--.go
UPDATE papel set ordem=4 where id=79;--.go
UPDATE papel set ordem=5 where id=76;--.go
UPDATE papel set ordem=6 where id=89;--.go
UPDATE papel set ordem=7 where id=445;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (455, 'ROLE_CAD_ELEICAO', 'CIPA', '#', 8, true, 385);--.go
UPDATE papel set ordem=1, nome='Etapas do Processo Eleitoral', papelmae_id=455 where id=437;--.go
UPDATE papel set ordem=2, nome='Eleições', papelmae_id=455 where id=438;--.go
UPDATE papel set ordem=3, nome='Comissões', papelmae_id=455 where id=439;--.go
UPDATE papel set ordem=10 where id=87;--.go
UPDATE papel set ordem=11 where id=90;--.go
UPDATE papel set ordem=12 where id=91;--.go
UPDATE papel set ordem=13 where id=427;--.go
UPDATE papel set ordem=14 where id=440;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (456, 'ROLE_CAD_PCMSO', 'PCMSO', '#', 9, true, 385);--.go
UPDATE papel set ordem=1, nome='Eventos', papelmae_id=456 where id=452;--.go
UPDATE papel set ordem=2, nome='Agenda', papelmae_id=456 where id=453;--.go

UPDATE papel set ordem=1 where id=450;--.go
UPDATE papel set ordem=2 where id=433;--.go
UPDATE papel set ordem=3 where id=435;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (457, 'ROLE_CAD_EXTINTOR', 'Extintores', '#', 4, true, 386);--.go
UPDATE papel set ordem=1, nome='Inspeção', papelmae_id=457 where id=446;--.go
UPDATE papel set ordem=2, nome='Manutenção', papelmae_id=457 where id=447;--.go
UPDATE papel set ordem=5 where id=426;--.go
UPDATE papel set ordem=6 where id=425;--.go
UPDATE papel set ordem=7 where id=428;--.go
UPDATE papel set ordem=8 where id=441;--.go
UPDATE papel set ordem=9 where id=443;--.go

UPDATE papel set ordem=1 where id=388;--.go
UPDATE papel set ordem=2 where id=85;--.go
UPDATE papel set ordem=3 where id=432;--.go
UPDATE papel set ordem=4 where id=434;--.go
UPDATE papel set ordem=5 where id=436;--.go
UPDATE papel set ordem=6 where id=448;--.go
UPDATE papel set ordem=7 where id=389;--.go
UPDATE papel set ordem=8 where id=429;--.go
UPDATE papel set ordem=9 where id=430;--.go
UPDATE papel set ordem=10 where id=431;--.go
UPDATE papel set ordem=11 where id=442;--.go
UPDATE papel set ordem=12 where id=449;--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 455);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 456);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 457);--.go
alter sequence papel_sequence restart with 458;--.go

UPDATE papel set ordem=2,papelmae_id=37 where id=38;--.go
UPDATE papel set ordem=3,papelmae_id=37 where id=41;--.go
UPDATE papel set ordem=4,papelmae_id=37 where id=39;--.go
UPDATE papel set ordem=5,papelmae_id=37 where id=44;--.go
UPDATE papel set ordem=6,papelmae_id=37 where id=409;--.go
UPDATE papel set ordem=7,papelmae_id=37 where id=451;--.go
DELETE FROM perfil_papel WHERE papeis_id=391;--.go
DELETE FROM papel WHERE id=391;--.go

update parametrosdosistema set appversao = '1.1.19.5';--.go

-- versao 1.1.20.6
update cargo SET cbocodigo = substring(cbocodigo from 1 for 6) where length(cbocodigo) > 6;--.go
ALTER TABLE cargo ALTER COLUMN cbocodigo TYPE character varying(6);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (458, 'ROLE_FUNCAO', 'Distribuição de Colaboradores por Função', '/sesmt/funcao/prepareRelatorioQtdPorFuncao.action', 13, true, 387);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 458);--.go
alter sequence papel_sequence restart with 459;--.go

update papel set ordem=5, papelmae_id=364 where id=407;--.go
update papel set ordem=7, papelmae_id=368 where id=415;--.go
update papel set ordem=8, papelmae_id=368 where id=416;--.go
update papel set ordem=9, papelmae_id=368 where id=421;--.go
update papel set ordem=10, papelmae_id=368 where id=422;--.go

alter table papel add column accesskey character varying(1);--.go

update papel set accesskey='T' where id=365;--.go
update papel set accesskey='v' where id=382;--.go
update papel set accesskey='C' where id=361;--.go
update papel set accesskey='R' where id=357;--.go
update papel set accesskey='P' where id=353;--.go
update papel set accesskey='I' where id=373;--.go
update papel set accesskey='n' where id=378;--.go
update papel set accesskey='S' where id=75;--.go
update papel set accesskey='U' where id=37;--.go

delete from perfil_papel where papeis_id=84;--.go
delete from papel where id=84;--.go
delete from perfil_papel where papeis_id=80;--.go
delete from papel where id=80;--.go
UPDATE papel set codigo='ROLE_PPRA', nome='PPRA e LTCAT', ordem=1, papelmae_id=387, url='/sesmt/ppra/prepareRelatorio.action' where id=388;--.go
update papel set ordem=10, papelmae_id=368 where id=422;--.go

CREATE TABLE configuracaoImpressaoCurriculo (
	id bigint NOT NULL,
	exibirConhecimento boolean default false,
	exibirCurso boolean default false,       
	exibirExperiencia boolean default false, 
	exibirInformacao boolean default false,  
	exibirObservacao boolean default false,  
	exibirHistorico boolean default false,   
	exibirIdioma boolean default false,   
	exibirFormacao boolean default false,   
	exibirInformacaoSocioEconomica boolean default false,   
	exibirAssinatura1 boolean default false, 
	assinatura1 character varying(50),        
	exibirAssinatura2 boolean default false, 
	assinatura2 character varying(50),       
	exibirAssinatura3 boolean default false, 
	assinatura3 character varying(50),
	usuario_id bigint,
	empresa_id bigint
);--.go       

ALTER TABLE configuracaoImpressaoCurriculo ADD CONSTRAINT configuracaoImpressaoCurriculo_pkey PRIMARY KEY(id);--.go
ALTER TABLE configuracaoImpressaoCurriculo ADD CONSTRAINT configuracaoImpressaoCurriculo_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
ALTER TABLE configuracaoImpressaoCurriculo ADD CONSTRAINT configuracaoImpressaoCurriculo_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);--.go
CREATE SEQUENCE configuracaoImpressaoCurriculo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

UPDATE papel set nome='Solicitações/Atendimentos Médicos' where id=426;--.go

ALTER TABLE parametrosdosistema ADD COLUMN exame_id bigint;--.go
ALTER TABLE parametrosdosistema ADD CONSTRAINT parametrosdosistema_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);--.go

ALTER TABLE examesolicitacaoexame ADD COLUMN periodicidade int;--.go
UPDATE examesolicitacaoexame ese SET periodicidade=(SELECT e.periodicidade from exame e where e.id=ese.exame_id);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (459, 'ROLE_CAD_SOLICITACAOEXAME', 'Atendimentos Médicos', '/sesmt/solicitacaoExame/prepareRelatorioAtendimentosMedicos.action', 14, true, 387);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 459);--.go
alter sequence papel_sequence restart with 460;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (460, 'ROLE_CAD_SOLICITACAOEXAME', 'Exames Realizados', '/sesmt/exame/prepareRelatorioExamesRealizados.action', 15, true, 387);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 460);--.go
alter sequence papel_sequence restart with 461;--.go

delete from perfil_papel where papeis_id=430;--.go
delete from papel where id=430;--.go

update parametrosdosistema set appversao = '1.1.20.6'; --.go

-- versao 1.1.21.7
ALTER TABLE parametrosdosistema ADD COLUMN atualizaPapeisIdsAPartirDe bigint DEFAULT null;--.go

ALTER TABLE eleicao ADD COLUMN horarioApuracao character varying(20); --.go
ALTER TABLE eleicao ADD COLUMN localApuracao character varying(100); --.go

update parametrosdosistema set appversao = '1.1.21.7'; --.go

-- versao 1.1.22.8
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (461, 'ROLE_R&S_IND', 'Indicadores', '#', 4, true, 357);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 461);--.go
UPDATE papel set ordem=1, papelmae_id=461 where id=73;--.go
UPDATE papel set ordem=2, papelmae_id=461 where id=69;--.go
UPDATE papel set ordem=3, papelmae_id=461 where id=47;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (462, 'ROLE_C&S_IND', 'Indicadores', '#', 4, true, 361);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 462);--.go
UPDATE papel set ordem=1, papelmae_id=462 where id=70;--.go
UPDATE papel set ordem=2, papelmae_id=462 where id=398;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (463, 'INATIVOS', 'Inativos', '#', 99, true, 37);--.go
UPDATE papel set menu=true, papelmae_id=463 where id=36;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=6;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=50;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=13;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=64;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=72;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=31;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=32;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=15;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=16;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=12;--.go
UPDATE papel set menu=true, papelmae_id=463 where id=29;--.go

delete from perfil_papel where papeis_id=36;--.go
delete from perfil_papel where papeis_id=6;--.go
delete from perfil_papel where papeis_id=50;--.go
delete from perfil_papel where papeis_id=13;--.go
delete from perfil_papel where papeis_id=64;--.go
delete from perfil_papel where papeis_id=72;--.go
delete from perfil_papel where papeis_id=31;--.go
delete from perfil_papel where papeis_id=32;--.go
delete from perfil_papel where papeis_id=15;--.go
delete from perfil_papel where papeis_id=16;--.go
delete from perfil_papel where papeis_id=12;--.go
delete from perfil_papel where papeis_id=29;--.go

delete from perfil_papel where papeis_id=381;--.go
delete from perfil_papel where papeis_id=378;--.go
delete from papel where id=381;--.go
delete from papel where id=378;--.go
delete from perfil_papel where papeis_id=376;--.go
delete from papel where id=376;--.go

alter sequence papel_sequence restart with 464;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (464, 'ROLE_IMPORTA_CADASTROS', 'Importar Cadastros', '/geral/empresa/prepareImportarCadastros.action', 8, true, 37);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 464);--.go

alter sequence papel_sequence restart with 465;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=464;--.go
UPDATE parametrosdosistema SET acversaowebservicecompativel = '1.0.1.38';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (465, 'ROLE_REL_COLABORADORES_CERTIFICACOES', 'Colaboradores x Certificações', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorCertificacao.action', 11, true, 368);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 465);--.go
alter sequence papel_sequence restart with 466;--.go
ALTER TABLE candidato ADD COLUMN nomecontato character varying(30); --.go
ALTER TABLE colaborador ADD COLUMN nomecontato character varying(30); --.go

UPDATE papel set nome='Relatório de Desligamento' where id=400;--.go
ALTER TABLE colaboradorresposta ADD COLUMN estabelecimento_id bigint; --.go 
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id); --.go

update parametrosdosistema set appversao = '1.1.22.8';--.go

-- versao 1.1.23.9
UPDATE papel set nome='Desligamentos' where id=400;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (466, 'ROLE_UTI_EMPRESA', 'Sobre...', '/geral/empresa/sobre.action', 7, true, 37);--.go
alter sequence papel_sequence restart with 467;--.go

ALTER TABLE candidato ADD COLUMN datacadastro date;--.go
UPDATE candidato SET datacadastro=dataatualizacao;--.go

update ClinicaAutorizada SET nome = substring(nome from 1 for 44) where length(nome) > 44;--.go
ALTER TABLE ClinicaAutorizada ALTER COLUMN nome TYPE character varying(44);--.go

update ClinicaAutorizada SET endereco = substring(endereco from 1 for 80) where length(endereco) > 80;--.go
ALTER TABLE ClinicaAutorizada ALTER COLUMN endereco TYPE character varying(80);--.go

ALTER TABLE ClinicaAutorizada ALTER COLUMN horarioAtendimento TYPE character varying(45);--.go

update papel set nome='Descrição de Cargos' where id=54;--.go

CREATE TABLE periodoExperiencia (
id bigint NOT NULL,
dias int,
empresa_id bigint
);--.go

ALTER TABLE periodoExperiencia ADD CONSTRAINT periodoExperiencia_pkey PRIMARY KEY(id);--.go
ALTER TABLE periodoExperiencia ADD CONSTRAINT periodoExperiencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE periodoExperiencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE parametrosdosistema ADD COLUMN diasLembretePeriodoExperiencia character varying(20);--.go
UPDATE parametrosdosistema SET diasLembretePeriodoExperiencia=3;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (467, 'ROLE_CAD_PERIODOEXPERIENCIA', 'Períodos de Acompanham. de Experiência', '/avaliacao/periodoExperiencia/list.action', 9, true, 374);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 467);--.go
alter sequence papel_sequence restart with 468;--.go

CREATE TABLE avaliacaoExperiencia (
id bigint NOT NULL,
titulo character varying(100),
cabecalho text,
ativo boolean,
empresa_id bigint
);--.go

ALTER TABLE avaliacaoExperiencia ADD CONSTRAINT avaliacaoExperiencia_pkey PRIMARY KEY(id);--.go
ALTER TABLE avaliacaoExperiencia ADD CONSTRAINT avaliacaoExperiencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (468, 'ROLE_CAD_PERIODOEXPERIENCIA', 'Modelos de Avaliação do Per. Experiência', '/avaliacao/avaliacaoExperiencia/list.action', 10, true, 374);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 468);--.go
alter sequence papel_sequence restart with 469;--.go

ALTER TABLE aspecto ADD COLUMN avaliacaoExperiencia_id bigint;--.go
ALTER TABLE aspecto ADD CONSTRAINT aspecto_avaliacaoExperiencia_fk FOREIGN KEY (avaliacaoExperiencia_id) REFERENCES avaliacaoExperiencia(id);--.go

ALTER TABLE pergunta ADD COLUMN peso integer;--.go
ALTER TABLE pergunta ADD COLUMN avaliacaoExperiencia_id bigint;--.go
ALTER TABLE pergunta ADD CONSTRAINT pergunta_avaliacaoExperiencia_fk FOREIGN KEY (avaliacaoExperiencia_id) REFERENCES avaliacaoExperiencia(id);--.go

ALTER TABLE resposta ADD COLUMN peso integer;--.go 

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (469, 'ROLE_COLAB_MOV', 'Movimentações', '#', 2, true, 373);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (470, 'ROLE_MOV_PERIODOEXPERIENCIA', 'Avaliação do Per. Experiência', '/pesquisa/colaboradorQuestionario/periodoExperienciaQuestionarioList.action', 1, true, 469);--.go
alter sequence papel_sequence restart with 471;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=470;--.go

ALTER TABLE colaboradorquestionario ADD COLUMN avaliacaoExperiencia_id bigint;--.go
ALTER TABLE colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_avaliacaoExperiencia_fk FOREIGN KEY (avaliacaoExperiencia_id) REFERENCES avaliacaoExperiencia(id);--.go --bob

update papel set papelmae_id=360 where id=424;--.go

update parametrosdosistema set appversao = '1.1.23.9';--.go

-- versao 1.1.24.10
drop table solicitacao_beneficio;--.go

UPDATE papel SET nome='Planejamentos de Realinhamentos' WHERE id=26;--.go

UPDATE colaboradorturma SET curso_id = (select t.curso_id FROM turma t WHERE t.id=turma_id) WHERE curso_id is null;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (471, 'ROLE_REL_COLAB_CARGO', 'Colaboradores por Cargo', '/cargosalario/cargo/prepareRelatorioColaboradorCargo.action', 2, true, 364);--.go
alter sequence papel_sequence restart with 472;--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 471);--.go

update papel set ordem=3 where id=35;--.go
update papel set ordem=4 where id=396;--.go
update papel set ordem=5 where id=406;--.go
update papel set ordem=6 where id=407;--.go

ALTER TABLE duracaopreenchimentovaga DROP CONSTRAINT duracaopreenchimentovaga_solicitacao_uk;--.go

update parametrosdosistema set appversao = '1.1.24.10';--.go

-- versao 1.1.25.11
ALTER TABLE colaboradorquestionario ADD COLUMN performance double precision;--.go

ALTER TABLE solicitacao DROP COLUMN experiencia;--.go
ALTER TABLE solicitacao DROP COLUMN horario;--.go
ALTER TABLE solicitacao DROP CONSTRAINT solicitacao_funcao_fk;--.go
ALTER TABLE solicitacao DROP CONSTRAINT solicitacao_ambiente_fk;--.go
ALTER TABLE solicitacao DROP COLUMN ambiente_id;--.go
ALTER TABLE solicitacao DROP COLUMN funcao_id;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (472, 'ROLE_REL_ADMITIDOS', 'Admitidos', '/geral/colaborador/prepareRelatorioAdmitidos.action', 5, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 472);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (473, 'ROLE_REL_SITUACOES', 'Situações', '/cargosalario/historicoColaborador/prepareRelatorioSituacoes.action', 7, true, 364);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 473);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (474, 'ROLE_COMPROU_SESMT', 'Comprou SESMT', '#', 0, false, null);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 474);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (475, 'ROLE_CAD_CLIENTE', 'Clientes', '/geral/cliente/list.action', 10, false, null);--.go
alter sequence papel_sequence restart with 476;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=471 WHERE atualizaPapeisIdsAPartirDe is null;--.go

alter table colaboradorquestionario add column observacao text;--.go

UPDATE papel SET papelmae_id=360 where id=424;--.go
update papel set papelmae_id=373 where id=462;--.go

ALTER TABLE parametrosdosistema ADD COLUMN exibirAbaDocumentos boolean DEFAULT true;--.go
ALTER TABLE parametrosdosistema ALTER COLUMN upperCase SET DEFAULT true;--.go
UPDATE parametrosdosistema SET upperCase=false where upperCase is null;--.go

CREATE TABLE cliente (
	id bigint not null,
	nome character varying(100) NOT NULL,
	enderecoInterno character varying(50),
	enderecoExterno character varying(50),
	senhaFortes character varying(20),
	versao character varying(10),
	dataAtualizacao date,
	modulosAdquiridos text,
	contatoGeral text,
	contatoTI text,
	observacao text
);--.go
CREATE SEQUENCE cliente_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
ALTER TABLE ONLY cliente ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);--.go

update areaorganizacional SET nome = substring(nome from 1 for 60) where length(nome) > 60;--.go
ALTER TABLE areaorganizacional ALTER COLUMN nome TYPE character varying(60);--.go

update indice SET nome = substring(nome from 1 for 40) where length(nome) > 40;--.go
ALTER TABLE indice ALTER COLUMN nome TYPE character varying(40);--.go

update cargo SET nome = substring(nome from 1 for 23) where length(nome) > 23;--.go
ALTER TABLE cargo ALTER COLUMN nome TYPE character varying(23);--.go

update faixasalarial SET nome = substring(nome from 1 for 6) where length(nome) > 6;--.go
ALTER TABLE faixasalarial ALTER COLUMN nome TYPE character varying(6);--.go

update estabelecimento SET nome = substring(nome from 1 for 30) where length(nome) > 30;--.go
ALTER TABLE estabelecimento ALTER COLUMN nome TYPE character varying(30);--.go

update estabelecimento SET logradouro = substring(logradouro from 1 for 40) where length(logradouro) > 40;--.go
ALTER TABLE estabelecimento ALTER COLUMN logradouro TYPE character varying(40);--.go

update estabelecimento SET complemento = substring(complemento from 1 for 20) where length(complemento) > 20;--.go
ALTER TABLE estabelecimento ALTER COLUMN complemento TYPE character varying(20);--.go

update empresa SET nome = substring(nome from 1 for 15) where length(nome) > 15;--.go
ALTER TABLE empresa ALTER COLUMN nome TYPE character varying(15);--.go

update empresa SET razaosocial = substring(razaosocial from 1 for 60) where length(razaosocial) > 60;--.go
ALTER TABLE empresa ALTER COLUMN razaosocial TYPE character varying(60);--.go

update parametrosdosistema set appversao = '1.1.25.11';--.go

-- versao 1.1.26.12
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (476, 'ROLE_FUNCAO', 'Funções', '/sesmt/funcao/listFiltro.action', 15, true, 385);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (477, 'ROLE_AMBIENTE', 'Ambientes e Funções do Colaborador', '/cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action', 5, true, 386);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (478, 'ROLE_VER_AREAS', 'Visualizar todas as Áreas Organizacionais', '#', 0, false, 363);--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 476);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 477);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 478);--.go

alter sequence papel_sequence restart with 479;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=476 WHERE atualizaPapeisIdsAPartirDe is null;--.go

ALTER TABLE comissaoperiodo ADD COLUMN aPartirDe date;--.go
UPDATE comissaoperiodo SET aPartirDe = inicio;--.go
ALTER TABLE comissaoperiodo DROP COLUMN inicio;--.go
ALTER TABLE comissaoperiodo DROP COLUMN fim;--.go

UPDATE papel SET papelmae_id=463 WHERE id=66;--.go
UPDATE papel SET nome='Colaboradores por Área Organizacional' WHERE id=396;--.go

ALTER TABLE eleicao ADD COLUMN textoAtaEleicao text;--.go

ALTER TABLE solicitacaoexame ADD COLUMN observacao character varying(100);--.go

ALTER TABLE comissao ADD COLUMN ataPosseTexto1 text;--.go
ALTER TABLE comissao ADD COLUMN ataPosseTexto2 text;--.go

ALTER TABLE bairro ADD COLUMN nome2 character varying(20);--.go
update bairro set nome2=substring(nome from 1 for 20);--.go
ALTER TABLE bairro DROP COLUMN nome;--.go
ALTER TABLE bairro RENAME COLUMN nome2 to nome;--.go

ALTER TABLE colaborador ADD COLUMN email2 character varying(40);--.go
update colaborador set email2=substring(email from 1 for 40);--.go
ALTER TABLE colaborador DROP COLUMN email;--.go
ALTER TABLE colaborador RENAME COLUMN email2 to email;--.go

ALTER TABLE colaborador ADD COLUMN categoria2 character varying(3);--.go
update colaborador set categoria2=substring(categoria from 1 for 3);--.go
ALTER TABLE colaborador DROP COLUMN categoria;--.go
ALTER TABLE colaborador RENAME COLUMN categoria2 to categoria;--.go

ALTER TABLE colaborador ADD COLUMN numeroHab2 character varying(11);--.go
update colaborador set numeroHab2=substring(numeroHab from 1 for 11);--.go
ALTER TABLE colaborador DROP COLUMN numeroHab;--.go
ALTER TABLE colaborador RENAME COLUMN numeroHab2 to numeroHab;--.go

ALTER TABLE colaborador ADD COLUMN conjuge2 character varying(40);--.go
update colaborador set conjuge2=substring(conjuge from 1 for 40);--.go
ALTER TABLE colaborador DROP COLUMN conjuge;--.go
ALTER TABLE colaborador RENAME COLUMN conjuge2 to conjuge;--.go

ALTER TABLE colaborador ADD COLUMN mae2 character varying(60);--.go
update colaborador set mae2=substring(mae from 1 for 60);--.go
ALTER TABLE colaborador DROP COLUMN mae;--.go
ALTER TABLE colaborador RENAME COLUMN mae2 to mae;--.go

ALTER TABLE colaborador ADD COLUMN pai2 character varying(60);--.go
update colaborador set pai2=substring(pai from 1 for 60);--.go
ALTER TABLE colaborador DROP COLUMN pai;--.go
ALTER TABLE colaborador RENAME COLUMN pai2 to pai;--.go

ALTER TABLE colaborador ADD COLUMN rg2 character varying(15);--.go
update colaborador set rg2=substring(rg from 1 for 15);--.go
ALTER TABLE colaborador DROP COLUMN rg;--.go
ALTER TABLE colaborador RENAME COLUMN rg2 to rg;--.go

ALTER TABLE colaborador ADD COLUMN pis2 character varying(11);--.go
update colaborador set pis2=substring(pis from 1 for 11);--.go
ALTER TABLE colaborador DROP COLUMN pis;--.go
ALTER TABLE colaborador RENAME COLUMN pis2 to pis;--.go

ALTER TABLE colaborador ADD COLUMN cpf2 character varying(11);--.go
update colaborador set cpf2=substring(cpf from 1 for 11);--.go
ALTER TABLE colaborador DROP COLUMN cpf;--.go
ALTER TABLE colaborador RENAME COLUMN cpf2 to cpf;--.go

ALTER TABLE colaborador ADD COLUMN complemento2 character varying(20);--.go
update colaborador set complemento2=substring(complemento from 1 for 20);--.go
ALTER TABLE colaborador DROP COLUMN complemento;--.go
ALTER TABLE colaborador RENAME COLUMN complemento2 to complemento;--.go

ALTER TABLE colaborador ADD COLUMN logradouro2 character varying(40);--.go
update colaborador set logradouro2=substring(logradouro from 1 for 40);--.go
ALTER TABLE colaborador DROP COLUMN logradouro;--.go
ALTER TABLE colaborador RENAME COLUMN logradouro2 to logradouro;--.go

ALTER TABLE colaborador ADD COLUMN nomeComercial2 character varying(30);--.go
update colaborador set nomeComercial2=substring(nomeComercial from 1 for 30);--.go
ALTER TABLE colaborador DROP COLUMN nomeComercial;--.go
ALTER TABLE colaborador RENAME COLUMN nomeComercial2 to nomeComercial;--.go

ALTER TABLE colaborador ADD COLUMN nome2 character varying(60);--.go
update colaborador set nome2=substring(nome from 1 for 60);--.go
ALTER TABLE colaborador DROP COLUMN nome;--.go
ALTER TABLE colaborador RENAME COLUMN nome2 to nome;--.go

ALTER TABLE candidato ADD COLUMN email2 character varying(40);--.go
update candidato set email2=substring(email from 1 for 40);--.go
ALTER TABLE candidato DROP COLUMN email;--.go
ALTER TABLE candidato RENAME COLUMN email2 to email;--.go

ALTER TABLE candidato ADD COLUMN categoria2 character varying(3);--.go
update candidato set categoria2=substring(categoria from 1 for 3);--.go
ALTER TABLE candidato DROP COLUMN categoria;--.go
ALTER TABLE candidato RENAME COLUMN categoria2 to categoria;--.go

ALTER TABLE candidato ADD COLUMN numeroHab2 character varying(11);--.go
update candidato set numeroHab2=substring(numeroHab from 1 for 11);--.go
ALTER TABLE candidato DROP COLUMN numeroHab;--.go
ALTER TABLE candidato RENAME COLUMN numeroHab2 to numeroHab;--.go

ALTER TABLE candidato ADD COLUMN conjuge2 character varying(40);--.go
update candidato set conjuge2=substring(conjuge from 1 for 40);--.go
ALTER TABLE candidato DROP COLUMN conjuge;--.go
ALTER TABLE candidato RENAME COLUMN conjuge2 to conjuge;--.go

ALTER TABLE candidato ADD COLUMN mae2 character varying(60);--.go
update candidato set mae2=substring(mae from 1 for 60);--.go
ALTER TABLE candidato DROP COLUMN mae;--.go
ALTER TABLE candidato RENAME COLUMN mae2 to mae;--.go

ALTER TABLE candidato ADD COLUMN pai2 character varying(60);--.go
update candidato set pai2=substring(pai from 1 for 60);--.go
ALTER TABLE candidato DROP COLUMN pai;--.go
ALTER TABLE candidato RENAME COLUMN pai2 to pai;--.go

ALTER TABLE candidato ADD COLUMN rg2 character varying(15);--.go
update candidato set rg2=substring(rg from 1 for 15);--.go
ALTER TABLE candidato DROP COLUMN rg;--.go
ALTER TABLE candidato RENAME COLUMN rg2 to rg;--.go

ALTER TABLE candidato ADD COLUMN pis2 character varying(11);--.go
update candidato set pis2=substring(pis from 1 for 11);--.go
ALTER TABLE candidato DROP COLUMN pis;--.go
ALTER TABLE candidato RENAME COLUMN pis2 to pis;--.go

ALTER TABLE candidato ADD COLUMN cpf2 character varying(11);--.go
update candidato set cpf2=substring(cpf from 1 for 11);--.go
ALTER TABLE candidato DROP COLUMN cpf;--.go
ALTER TABLE candidato RENAME COLUMN cpf2 to cpf;--.go

ALTER TABLE candidato ADD COLUMN complemento2 character varying(20);--.go
update candidato set complemento2=substring(complemento from 1 for 20);--.go
ALTER TABLE candidato DROP COLUMN complemento;--.go
ALTER TABLE candidato RENAME COLUMN complemento2 to complemento;--.go

ALTER TABLE candidato ADD COLUMN logradouro2 character varying(40);--.go
update candidato set logradouro2=substring(logradouro from 1 for 40);--.go
ALTER TABLE candidato DROP COLUMN logradouro;--.go
ALTER TABLE candidato RENAME COLUMN logradouro2 to logradouro;--.go

ALTER TABLE candidato ADD COLUMN nome2 character varying(60);--.go
update candidato set nome2=substring(nome from 1 for 60);--.go
ALTER TABLE candidato DROP COLUMN nome;--.go
ALTER TABLE candidato RENAME COLUMN nome2 to nome;--.go

update parametrosdosistema set appversao = '1.1.26.12';--.go

-- versao 1.1.26.13
update parametrosdosistema set acversaowebservicecompativel='1.0.1.39';--.go
update duracaopreenchimentovaga set qtdDiasPrimeiraContratacao=0 WHERE qtdDiasPrimeiraContratacao < 0;--.go

update parametrosdosistema set appversao = '1.1.26.13';--.go

-- versao 1.1.27.14
ALTER TABLE colaborador ADD COLUMN solicitacao_id bigint;--.go
ALTER TABLE colaborador ADD CONSTRAINT colaborador_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);--.go

UPDATE colaborador co1 SET solicitacao_id = (
	select max(s.id)
	from colaborador co 
	join candidato ca on co.candidato_id=ca.id 
	join candidatosolicitacao cs on cs.candidato_id=ca.id
	join solicitacao s on cs.solicitacao_id=s.id
	where s.data = (select max(s2.data) from solicitacao s2 
			join candidatosolicitacao cs2 on cs2.solicitacao_id=s2.id
			where cs2.candidato_id=ca.id
			)
	and co.id=co1.id
);--.go

DROP TABLE duracaopreenchimentovaga;--.go
DROP SEQUENCE duracaopreenchimentovaga_sequence;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (479, 'ROLE_CAD_PERIODOEXPERIENCIA', 'Estatística da Avaliação do Per. Experiência', '/avaliacao/avaliacaoExperiencia/prepareResultado.action', 6, true, 377);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 479);--.go
alter sequence papel_sequence restart with 480;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=479 WHERE atualizaPapeisIdsAPartirDe is null;--.go

update parametrosdosistema set appversao = '1.1.27.14';--.go

-- versao 1.1.27.15
update papel set nome='Motivos de Afastamentos' where id=440;--.go
update parametrosdosistema set appversao = '1.1.27.15';--.go

-- versao 1.1.28.16
ALTER TABLE extintor DROP COLUMN descricao;--.go
CREATE INDEX solicitacaoexame_fkey ON examesolicitacaoexame (solicitacaoexame_id);--.go

UPDATE papel set nome = 'Exibir informações do SESMT' where id=474;--.go
UPDATE papel SET nome='Tipos de Ocorrência' where id=62;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (480, 'ROLE_CAD_OCORRENCIA', 'Ocorrências', '/geral/colaboradorOcorrencia/list.action', 2, true, 469);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 480);--.go
alter sequence papel_sequence restart with 481;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=480 WHERE atualizaPapeisIdsAPartirDe is null;--.go

update parametrosdosistema set appversao = '1.1.28.16';--.go

-- versao 1.1.29.17
alter table anuncio add column exibirmoduloexterno boolean default false;--.go
update anuncio set exibirmoduloexterno=true;--.go 

UPDATE papel set url='/avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action' WHERE id=470;--.go

UPDATE papel set url='/sesmt/fichaMedica/list.action' WHERE id=427;--.go
UPDATE papel set url='/sesmt/fichaMedica/listPreenchida.action' WHERE id=428;--.go
UPDATE papel set url='/sesmt/fichaMedica/prepareResultadoFichaMedica.action' WHERE id=449;--.go

alter table solicitacao alter column idademaxima drop not null;--.go
alter table solicitacao alter column idademinima drop not null;--.go
UPDATE papel set nome='Medição dos Riscos nos Ambientes' where id=450;--.go
ALTER TABLE usuario ADD COLUMN ultimologin timestamp;--.go

update papel SET codigo='ROLE_MOV_AVALIACAO', ordem=2 where id=384;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (481, 'ROLE_CAD_AVALIACAO', 'Cadastros', '#', 1, true, 382);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (482, 'ROLE_CAD_AVALIACAO', 'Modelos de Avaliação', '/avaliacao/modelo/list.action', 1, true, 481);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 481);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 482);--.go
alter sequence papel_sequence restart with 483;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=481 WHERE atualizaPapeisIdsAPartirDe is null;--.go

UPDATE papel set url='/avaliacao/modelo/list.action' where id=468;--.go

DROP TABLE avaliacao;--.go

ALTER TABLE aspecto DROP CONSTRAINT aspecto_avaliacaoExperiencia_fk;--.go
ALTER TABLE pergunta DROP CONSTRAINT pergunta_avaliacaoExperiencia_fk;--.go
ALTER TABLE colaboradorquestionario DROP CONSTRAINT colaboradorquestionario_avaliacaoExperiencia_fk;--.go

ALTER TABLE avaliacaoExperiencia DROP CONSTRAINT avaliacaoExperiencia_pkey;--.go
ALTER TABLE avaliacaoExperiencia DROP CONSTRAINT avaliacaoExperiencia_empresa_fk;--.go
ALTER TABLE avaliacaoExperiencia RENAME TO avaliacao;--.go
ALTER TABLE avaliacao ADD CONSTRAINT avaliacao_pkey PRIMARY KEY(id);--.go
ALTER TABLE avaliacao ADD CONSTRAINT avaliacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go

ALTER TABLE aspecto RENAME COLUMN avaliacaoExperiencia_id to avaliacao_id;--.go
ALTER TABLE aspecto ADD CONSTRAINT aspecto_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go
ALTER TABLE pergunta RENAME COLUMN avaliacaoExperiencia_id to avaliacao_id;--.go
ALTER TABLE pergunta ADD CONSTRAINT pergunta_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go
ALTER TABLE colaboradorquestionario RENAME COLUMN avaliacaoExperiencia_id to avaliacao_id;--.go
ALTER TABLE colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go

update parametrosdosistema set appversao = '1.1.29.17';--.go

-- versao 1.1.30.18
UPDATE papel SET url='/avaliacao/desempenho/list.action' WHERE id=55;--.go
update papel set nome = 'Categorias de EPI' where id = 77;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (483, 'ROLE_MOV_AVALIACAO', 'Responder Avaliações', '/avaliacao/desempenho/avaliacaoDesempenhoQuestionarioList.action', 2, true, 384);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 483);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (484, 'ROLE_RESPONDE_AVALIACAO', 'Pode ver e responder Aval. Desempenho', '#', 3, false, 382);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 484);--.go

update papel set ordem=(ordem + 1) where papelmae_id=37 and ordem > 3;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (485, 'ROLE_CAMPO_EXTRA', 'Campos Extras', '/geral/configuracaoCampoExtra/prepareUpdate.action', 4, true, 37);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 485);--.go

alter sequence papel_sequence restart with 486;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=483 WHERE atualizaPapeisIdsAPartirDe is null;--.go

CREATE TABLE avaliacaodesempenho (
    id bigint NOT NULL,
	titulo character varying(100),
	inicio date,
	fim date,
	anonima boolean,
	avaliacao_id bigint
);--.go
ALTER TABLE ONLY avaliacaodesempenho ADD CONSTRAINT avaliacaodesempenho_pkey PRIMARY KEY (id);--.go
ALTER TABLE ONLY avaliacaodesempenho ADD CONSTRAINT avaliacaodesempenho_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go
CREATE SEQUENCE avaliacaodesempenho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE colaboradorquestionario ADD COLUMN avaliacaodesempenho_id bigint;--.go
ALTER TABLE colaboradorquestionario ADD COLUMN avaliador_id bigint;--.go
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_avaliacaodesempenho_fk FOREIGN KEY (avaliacaodesempenho_id) REFERENCES avaliacaodesempenho(id);--.go
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_avaliador_fk FOREIGN KEY (avaliador_id) REFERENCES colaborador(id);--.go
ALTER TABLE avaliacaodesempenho ADD COLUMN permiteautoavaliacao boolean;--.go

ALTER TABLE curso ADD COLUMN percentualminimofrequencia double precision;--.go

update mensagem set link='avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action?colaborador.id=' || substr(link, 91) where link like 'pesquisa/colaboradorQuestionario/periodoExperienciaQuestionarioList%';--.go 
ALTER TABLE avaliacaodesempenho ADD COLUMN liberada boolean;--.go

CREATE TABLE configuracaocampoextra (
	id bigint NOT NULL,
	ativo boolean,
	nome character varying(15),
	descricao character varying(30),
	titulo character varying(60),
	ordem integer not null,
	tipo character varying(60)
);--.go

ALTER TABLE configuracaocampoextra ADD CONSTRAINT configuracaocampoextra_pkey PRIMARY KEY (id);--.go

CREATE SEQUENCE configuracaocampoextra_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (1,'f','texto1','Campo de Texto 1', 1,'texto');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (2,'f','texto2','Campo de Texto 2', 1,'texto');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (3,'f','texto3','Campo de Texto 3', 1,'texto');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (4,'f','data1','Campo de Data 1', 1,'data');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (5,'f','data2','Campo de Data 2', 1,'data');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (6,'f','data3','Campo de Data 3', 1,'data');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (7,'f','valor1','Campo de Valor 1', 1,'valor');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (8,'f','valor2','Campo de Valor 2', 1,'valor');--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (9,'f','numero1','Campo de Numero', 1,'numero');--.go

alter sequence configuracaocampoextra_sequence restart with 10;--.go

ALTER TABLE parametrosdosistema ADD COLUMN emaildosuportetecnico character varying(40);--.go
ALTER TABLE parametrosdosistema ADD COLUMN campoExtraColaborador boolean;--.go
update parametrosdosistema set campoextracolaborador=false;--.go

CREATE TABLE camposExtras (
	id bigint NOT NULL,
	texto1 character varying(250),
	texto2 character varying(250),
	texto3 character varying(250),
	data1 date,
	data2 date,
	data3 date,
	valor1 double precision,
	valor2 double precision,
	numero1 Integer
);--.go

ALTER TABLE camposExtras ADD CONSTRAINT camposExtras_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE camposExtras_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

alter table colaborador add COLUMN camposextras_id bigint; --.go
ALTER TABLE colaborador ADD CONSTRAINT colaborador_camposextras_fk FOREIGN KEY (camposextras_id) REFERENCES camposextras(id); --.go

update parametrosdosistema set appversao = '1.1.30.18';--.go

-- versao 1.1.31.19
ALTER TABLE empresa ADD mensagemModuloExterno character varying(400);--.go

delete from perfil_papel where papeis_id=468;--.go
delete from papel where id=468;--.go
update papel set papelmae_id=384, ordem=4 where id=470;--.go
update papel set papelmae_id=384, ordem=3 where id=467;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (486, 'ROLE_REL_AVALIACAO', 'Relatórios', '#', 3, true, 382);--.go

update papel set papelmae_id=486, ordem=1 where id=479;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (487, 'ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO', 'Pode Respoder Avaliação Por Outro Usuário', '#', 0, false, 384);--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=486;--.go

update papel set nome='Resultado da Avaliação do Per. Experiência' where id=479;--.go

alter table historicocolaborador add column movimentoSalarialId bigint; --.go
update papel set nome='Resultado da Avaliação do Per. Experiência' where id=479;--.go

CREATE TABLE cargo_etapaseletiva (
    cargo_id bigint NOT NULL,
    etapaseletivas_id bigint NOT NULL
);--.go
ALTER TABLE cargo_etapaseletiva ADD CONSTRAINT cargo_etapaseletiva_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go 
ALTER TABLE cargo_etapaseletiva ADD CONSTRAINT cargo_etapaseletiva_etapaseletiva_fk FOREIGN KEY (etapaseletivas_id) REFERENCES etapaseletiva(id);--.go 
alter table cargo add column exibirModuloExterno boolean;--.go

alter table faixasalarial add column nomeACPessoal character varying(30);--.go

ALTER TABLE cargo ALTER COLUMN nome TYPE character varying(30);--.go
ALTER TABLE faixasalarial ALTER COLUMN nome TYPE character varying(30);--.go

alter table cargo add column atitude character varying(120);--.go

update cargo set exibirmoduloexterno = true;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (488, 'ROLE_CAT', 'CATs (Acidentes de Trabalho)', '/sesmt/cat/prepareRelatorioCats.action', 16, true, 387);--.go

alter sequence papel_sequence restart with 489;--.go

update cargo c set observacao = observacao || '  ' || selecao where selecao <> '';--.go

update parametrosdosistema set appversao = '1.1.31.19';--.go

-- versao 1.1.31.20
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (489, 'ROLE_REL_EPIVENCIMENTO', 'EPIs Entregues', '/sesmt/solicitacaoEpi/prepareRelatorioEntregaEpi.action', 4, true, 387);--.go
alter sequence papel_sequence restart with 490;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=489 WHERE atualizaPapeisIdsAPartirDe is null;--.go

update parametrosdosistema set appversao = '1.1.31.20';--.go

-- versao 1.1.32.21
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (490, 'ROLE_REL_ACOMPANHAMENTO_EXPERIENCIA', 'Periodo de Acompanhamento de Experiência', '/avaliacao/periodoExperiencia/prepareRelatorioAcopanhamentoExperiencia.action', 2, true, 486);--.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (491, 'ROLE_REL_ACOMP_RANKING_PERIODO_EXPERIENCIA', 'Ranking Performace Periodo de Experiência', '/avaliacao/periodoExperiencia/prepareRelatorioRankingPerformancePeriodoDeExperiencia.action', 3, true,486);--.go

alter sequence papel_sequence restart with 492;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=490 WHERE atualizaPapeisIdsAPartirDe is null;--.go

alter table parametrosdosistema add column codEmpresaSuporte character varying(10);--.go
alter table parametrosdosistema add column codClienteSuporte character varying(10);--.go

alter table colaborador add column dataatualizacao date;--.go
update colaborador set dataAtualizacao = '1900-01-01';--.go

update parametrosdosistema set acversaowebservicecompativel = '1.0.1.40';--.go

update parametrosdosistema set appversao = '1.1.32.21';--.go

-- versao 1.1.33.22
alter table configuracaocampoextra add column posicao Integer;--.go

update configuracaocampoextra set posicao = 1 where id = 1;--.go
update configuracaocampoextra set posicao = 2 where id = 2;--.go
update configuracaocampoextra set posicao = 3 where id = 3;--.go
update configuracaocampoextra set posicao = 11 where id = 4;--.go
update configuracaocampoextra set posicao = 12 where id = 5;--.go
update configuracaocampoextra set posicao = 13 where id = 6;--.go
update configuracaocampoextra set posicao = 14 where id = 7;--.go
update configuracaocampoextra set posicao = 15 where id = 8;--.go
update configuracaocampoextra set posicao = 16 where id = 9;--.go

insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (10,'f','texto4','Campo de Texto 4', 1,'texto',4);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (11,'f','texto5','Campo de Texto 5', 1,'texto',5);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (12,'f','texto6','Campo de Texto 6', 1,'texto',6);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (13,'f','texto7','Campo de Texto 7', 1,'texto',7);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (14,'f','texto8','Campo de Texto 8', 1,'texto',8);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (15,'f','texto9','Campo de Texto 9', 1,'texto',9);--.go
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo,posicao) values (16,'f','texto10','Campo de Texto 10', 1,'texto',10);--.go

ALTER TABLE camposExtras ADD COLUMN texto4 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto5 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto6 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto7 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto8 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto9 character varying(250);--.go
ALTER TABLE camposExtras ADD COLUMN texto10 character varying(250);--.go

update parametrosdosistema set appversao = '1.1.33.22';--.go

-- versao 1.1.34.23
ALTER TABLE cargo ALTER COLUMN atitude TYPE text;--.go

update parametrosdosistema set appversao = '1.1.34.23';--.go

-- versao 1.1.34.24
update parametrosdosistema set acversaowebservicecompativel = '1.0.1.42';--.go
update parametrosdosistema set appversao = '1.1.34.24';--.go

-- versao 1.1.35.25
ALTER TABLE reajusteColaborador ALTER COLUMN observacao TYPE text;--.go

CREATE TABLE ConfiguracaoPerformance (
    id bigint NOT NULL,
    usuario_id bigint,
	caixa character varying(10),
	ordem integer,
	aberta boolean
);--.go
ALTER TABLE ONLY configuracaoPerformance ADD CONSTRAINT configuracaoPerformance_pkey PRIMARY KEY (id);--.go
ALTER TABLE configuracaoPerformance ADD CONSTRAINT configuracaoPerformance_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);--.go
CREATE SEQUENCE configuracaoPerformance_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

update papel set nome = 'Relatório de Ranking de Performace de Avaliação de Desempenho' where id = 491;--.go

update parametrosdosistema set appversao = '1.1.35.25';--.go

-- versao 1.1.36.26

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (492, 'ROLE_MOV_SOLICITACAO', 'Modelos de Avaliação de Solicitação', '/avaliacao/modeloCandidato/list.action?modeloAvaliacao=S', 7, true, 358);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (493, 'ROLE_CAD_HABILIDADE', 'Habilidades', '/captacao/habilidade/list.action', 4, true, 362);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (494, 'ROLE_CAD_ATITUDE', 'Atitudes', '/captacao/atitude/list.action', 5, true, 362);--.go

alter sequence papel_sequence restart with 495;--.go

alter table avaliacao add column tipoModeloAvaliacao character(1);--.go
update avaliacao set tipoModeloAvaliacao='D';--.go
alter table avaliacao alter column tipoModeloAvaliacao set not null;--.go

update papel set nome = 'Avaliações de Desempenho' where id = 55;--.go
update papel set nome = 'Responder Avaliações de Desempenho' where id = 483;--.go
update papel set nome = 'Acompanhamento do Período de Experiência' where id = 470;--.go
update papel set nome = 'Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência' where id = 479;--.go 
update papel set nome = 'Acompanhamento do Período de Experiência' where id = 490;--.go
update papel set nome = 'Ranking de Performace das Avaliações de Desempenho' where id = 491;--.go
update papel set nome = 'Dias do Acompanhamento do Período de Experiência',  papelmae_id = 481, ordem = 2 where id = 467;--.go
update papel set nome = 'Modelos de Avaliação do Candidato' where id = 492;--.go
update papel set ordem = 6 where id = 11;--.go
update papel set ordem = 7 where id = 404;--.go

CREATE TABLE habilidade (
id bigint NOT NULL,
nome character varying(100),
empresa_id bigint
);--.go
ALTER TABLE habilidade ADD CONSTRAINT habilidade_pkey PRIMARY KEY(id);--.go
ALTER TABLE habilidade ADD CONSTRAINT habilidade_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE habilidade_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE atitude (
id bigint NOT NULL,
nome character varying(100),
empresa_id bigint
);--.go
ALTER TABLE atitude ADD CONSTRAINT atitude_pkey PRIMARY KEY(id);--.go
ALTER TABLE atitude ADD CONSTRAINT atitude_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go
CREATE SEQUENCE atitude_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE cargo_habilidade(
    cargo_id bigint NOT NULL,
    habilidades_id bigint NOT NULL
);--.go
ALTER TABLE cargo_habilidade ADD CONSTRAINT cargo_habilidade_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go 
ALTER TABLE cargo_habilidade ADD CONSTRAINT cargo_habilidade_habilidade_fk FOREIGN KEY (habilidades_id) REFERENCES habilidade(id);--.go

CREATE TABLE cargo_atitude(
    cargo_id bigint NOT NULL,
    atitudes_id bigint NOT NULL
);--.go
ALTER TABLE cargo_atitude ADD CONSTRAINT cargo_atitude_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);--.go 
ALTER TABLE cargo_atitude ADD CONSTRAINT cargo_atitude_atitude_fk FOREIGN KEY (atitudes_id) REFERENCES atitude(id);--.go

alter table solicitacao add column avaliacao_id bigint;--.go
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);--.go

alter table colaboradorquestionario add column solicitacao_id bigint;--.go
ALTER TABLE colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);--.go

update parametrosdosistema set appversao = '1.1.36.26';--.go

-- versao 1.1.37.27
alter table conhecimento add column observacao text; --.go
alter table habilidade add column observacao text; --.go
alter table atitude add column observacao text; --.go

update papel set nome = 'Modelos de Avaliação do Candidato' where id = 492;--.go
update papel set nome = 'Avaliações de Desempenho/Acomp. do Período de Experiência' where id = 482;--.go
update avaliacao set tipomodeloavaliacao = 'A' where tipomodeloavaliacao <> 'S';--.go

CREATE FUNCTION alter_trigger(CHARACTER, CHARACTER) RETURNS void AS '
BEGIN
	execute ''ALTER TABLE '' || $1 || '' '' || $2 || '' TRIGGER ALL'';
END;
' LANGUAGE plpgsql;--.go

update papel set nome = 'Análise das Etapas Seletivas' where id = 47;--.go

alter table empresa add column exibirDadosAmbiente boolean;--.go
update empresa set exibirDadosAmbiente=false;--.go

CREATE TABLE configuracaoRelatorioDinamico (
id bigint NOT NULL,
usuario_id bigint,
campos character varying(600),
titulo character varying(100)
);--.go

ALTER TABLE configuracaoRelatorioDinamico ADD CONSTRAINT configuracaoRelatorioDinamico_pkey PRIMARY KEY(id);--.go
ALTER TABLE configuracaoRelatorioDinamico ADD CONSTRAINT configuracaoRelatorioDinamico_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);--.go
CREATE SEQUENCE configuracaoRelatorioDinamico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE habilidade_areaorganizacional (
    habilidades_id bigint NOT NULL,
    areaOrganizacionals_id bigint NOT NULL
); --.go
ALTER TABLE habilidade_areaorganizacional ADD CONSTRAINT habilidade_areaorganizaciona_habilidade_fk FOREIGN KEY (habilidades_id) REFERENCES habilidade(id); --.go
ALTER TABLE habilidade_areaorganizacional ADD CONSTRAINT habilidade_areaorganizaciona_areaOrganizacional_fk FOREIGN KEY (areaOrganizacionals_id) REFERENCES areaOrganizacional(id); --.go

CREATE TABLE atitude_areaorganizacional (
    atitudes_id bigint NOT NULL,
    areaOrganizacionals_id bigint NOT NULL
); --.go
ALTER TABLE atitude_areaorganizacional ADD CONSTRAINT atitude_areaorganizaciona_atitude_fk FOREIGN KEY (atitudes_id) REFERENCES atitude(id); --.go
ALTER TABLE atitude_areaorganizacional ADD CONSTRAINT atitude_areaorganizaciona_areaOrganizacional_fk FOREIGN KEY (areaOrganizacionals_id) REFERENCES areaOrganizacional(id); --.go

CREATE TABLE codigoCBO (
codigo character varying(6) NOT NULL,
descricao character varying(200)
);--.go

alter table codigoCBO add constraint codigocbo_codigo_uk unique (codigo); --.go

insert into codigoCBO (codigo, descricao) values ('848505','Abatedor');--.go
insert into codigoCBO (codigo, descricao) values ('764305','Acabador de calçados');--.go
insert into codigoCBO (codigo, descricao) values ('766305','Acabador de embalagens (flexíveis e cartotécnicas)');--.go
insert into codigoCBO (codigo, descricao) values ('716105','Acabador de superfícies de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('376205','Acrobata');--.go
insert into codigoCBO (codigo, descricao) values ('322105','Acupunturista');--.go
insert into codigoCBO (codigo, descricao) values ('623005','Adestrador de animais');--.go
insert into codigoCBO (codigo, descricao) values ('252105','Administrador');--.go
insert into codigoCBO (codigo, descricao) values ('212305','Administrador de banco de dados');--.go
insert into codigoCBO (codigo, descricao) values ('510110','Administrador de edifícios');--.go
insert into codigoCBO (codigo, descricao) values ('252505','Administrador de fundos e carteiras de investimento');--.go
insert into codigoCBO (codigo, descricao) values ('212310','Administrador de redes');--.go
insert into codigoCBO (codigo, descricao) values ('212315','Administrador de sistemas operacionais');--.go
insert into codigoCBO (codigo, descricao) values ('241005','Advogado');--.go
insert into codigoCBO (codigo, descricao) values ('241015','Advogado (direito civil)');--.go
insert into codigoCBO (codigo, descricao) values ('241035','Advogado (direito do trabalho)');--.go
insert into codigoCBO (codigo, descricao) values ('241025','Advogado (direito penal)');--.go
insert into codigoCBO (codigo, descricao) values ('241020','Advogado (direito público)');--.go
insert into codigoCBO (codigo, descricao) values ('241030','Advogado (áreas especiais)');--.go
insert into codigoCBO (codigo, descricao) values ('241205','Advogado da uniao');--.go
insert into codigoCBO (codigo, descricao) values ('241010','Advogado de empresa');--.go
insert into codigoCBO (codigo, descricao) values ('721305','Afiador de cardas');--.go
insert into codigoCBO (codigo, descricao) values ('721310','Afiador de cutelaria');--.go
insert into codigoCBO (codigo, descricao) values ('721315','Afiador de ferramentas');--.go
insert into codigoCBO (codigo, descricao) values ('721320','Afiador de serras');--.go
insert into codigoCBO (codigo, descricao) values ('742105','Afinador de instrumentos musicais');--.go
insert into codigoCBO (codigo, descricao) values ('342120','Afretador');--.go
insert into codigoCBO (codigo, descricao) values ('354110','Agenciador de propaganda');--.go
insert into codigoCBO (codigo, descricao) values ('515105','Agente comunitário de saúde');--.go
insert into codigoCBO (codigo, descricao) values ('352205','Agente de defesa ambiental');--.go
insert into codigoCBO (codigo, descricao) values ('352405','Agente de direitos autorais');--.go
insert into codigoCBO (codigo, descricao) values ('342405','Agente de estaçao (ferrovia e metrô)');--.go
insert into codigoCBO (codigo, descricao) values ('254310','Agente de higiene e segurança');--.go
insert into codigoCBO (codigo, descricao) values ('215105','Agente de manobra e docagem');--.go
insert into codigoCBO (codigo, descricao) values ('517205','Agente de polícia federal');--.go
insert into codigoCBO (codigo, descricao) values ('517305','Agente de proteçao de aeroporto');--.go
insert into codigoCBO (codigo, descricao) values ('783105','Agente de pátio');--.go
insert into codigoCBO (codigo, descricao) values ('351315','Agente de recrutamento e seleçao');--.go
insert into codigoCBO (codigo, descricao) values ('352210','Agente de saúde pública');--.go
insert into codigoCBO (codigo, descricao) values ('517310','Agente de segurança');--.go
insert into codigoCBO (codigo, descricao) values ('517315','Agente de segurança penitenciária');--.go
insert into codigoCBO (codigo, descricao) values ('517220','Agente de trânsito');--.go
insert into codigoCBO (codigo, descricao) values ('354120','Agente de vendas de serviços');--.go
insert into codigoCBO (codigo, descricao) values ('354815','Agente de viagem');--.go
insert into codigoCBO (codigo, descricao) values ('352310','Agente fiscal de qualidade');--.go
insert into codigoCBO (codigo, descricao) values ('352315','Agente fiscal metrológico');--.go
insert into codigoCBO (codigo, descricao) values ('352320','Agente fiscal têxtil');--.go
insert into codigoCBO (codigo, descricao) values ('516505','Agente funerário');--.go
insert into codigoCBO (codigo, descricao) values ('253115','Agente publicitário');--.go
insert into codigoCBO (codigo, descricao) values ('632615','Ajudante de carvoaria');--.go
insert into codigoCBO (codigo, descricao) values ('342205','Ajudante de despachante aduaneiro');--.go
insert into codigoCBO (codigo, descricao) values ('783225','Ajudante de motorista');--.go
insert into codigoCBO (codigo, descricao) values ('741105','Ajustador de instrumentos de precisao');--.go
insert into codigoCBO (codigo, descricao) values ('725005','Ajustador ferramenteiro');--.go
insert into codigoCBO (codigo, descricao) values ('725010','Ajustador mecânico');--.go
insert into codigoCBO (codigo, descricao) values ('725015','Ajustador mecânico (usinagem em bancada e em máquinas-ferramentas)');--.go
insert into codigoCBO (codigo, descricao) values ('725020','Ajustador mecânico em bancada');--.go
insert into codigoCBO (codigo, descricao) values ('725025','Ajustador naval (reparo e construçao)');--.go
insert into codigoCBO (codigo, descricao) values ('841705','Alambiqueiro');--.go
insert into codigoCBO (codigo, descricao) values ('763005','Alfaiate');--.go
insert into codigoCBO (codigo, descricao) values ('784205','Alimentador de linha de produçao');--.go
insert into codigoCBO (codigo, descricao) values ('992105','Alinhador de pneus');--.go
insert into codigoCBO (codigo, descricao) values ('414105','Almoxarife');--.go
insert into codigoCBO (codigo, descricao) values ('761405','Alvejador (tecidos)');--.go
insert into codigoCBO (codigo, descricao) values ('711105','Amostrador de minérios');--.go
insert into codigoCBO (codigo, descricao) values ('252515','Analista de cobrança (instituiçoes financeiras)');--.go
insert into codigoCBO (codigo, descricao) values ('252525','Analista de crédito (instituiçoes financeiras)');--.go
insert into codigoCBO (codigo, descricao) values ('252530','Analista de crédito rural');--.go
insert into codigoCBO (codigo, descricao) values ('252510','Analista de câmbio');--.go
insert into codigoCBO (codigo, descricao) values ('212405','Analista de desenvolvimento de sistemas');--.go
insert into codigoCBO (codigo, descricao) values ('354305','Analista de exportaçao e importaçao');--.go
insert into codigoCBO (codigo, descricao) values ('413105','Analista de folha de pagamento');--.go
insert into codigoCBO (codigo, descricao) values ('261215','Analista de informaçoes (pesquisador de informaçoes de rede)');--.go
insert into codigoCBO (codigo, descricao) values ('252535','Analista de leasing');--.go
insert into codigoCBO (codigo, descricao) values ('253120','Analista de negócios');--.go
insert into codigoCBO (codigo, descricao) values ('253125','Analista de pesquisa de mercado');--.go
insert into codigoCBO (codigo, descricao) values ('252540','Analista de produtos bancários');--.go
insert into codigoCBO (codigo, descricao) values ('252405','Analista de recursos humanos');--.go
insert into codigoCBO (codigo, descricao) values ('212410','Analista de redes e de comunicaçao de dados');--.go
insert into codigoCBO (codigo, descricao) values ('351705','Analista de seguros (técnico)');--.go
insert into codigoCBO (codigo, descricao) values ('351710','Analista de sinistros');--.go
insert into codigoCBO (codigo, descricao) values ('212415','Analista de sistemas de automaçao');--.go
insert into codigoCBO (codigo, descricao) values ('212420','Analista de suporte computacional');--.go
insert into codigoCBO (codigo, descricao) values ('342105','Analista de transporte em comércio exterior');--.go
insert into codigoCBO (codigo, descricao) values ('252545','Analista financeiro (instituiçoes financeiras)');--.go
insert into codigoCBO (codigo, descricao) values ('261705','Ancora de rádio e televisao');--.go
insert into codigoCBO (codigo, descricao) values ('251105','Antropólogo');--.go
insert into codigoCBO (codigo, descricao) values ('613405','Apicultor');--.go
insert into codigoCBO (codigo, descricao) values ('715705','Aplicador de asfalto impermeabilizante (coberturas)');--.go
insert into codigoCBO (codigo, descricao) values ('752205','Aplicador serigráfico em vidros');--.go
insert into codigoCBO (codigo, descricao) values ('414205','Apontador de mao-de-obra');--.go
insert into codigoCBO (codigo, descricao) values ('414210','Apontador de produçao');--.go
insert into codigoCBO (codigo, descricao) values ('376325','Apresentador de circo');--.go
insert into codigoCBO (codigo, descricao) values ('376305','Apresentador de eventos');--.go
insert into codigoCBO (codigo, descricao) values ('376310','Apresentador de festas populares');--.go
insert into codigoCBO (codigo, descricao) values ('376315','Apresentador de programas de rádio');--.go
insert into codigoCBO (codigo, descricao) values ('376320','Apresentador de programas de televisao');--.go
insert into codigoCBO (codigo, descricao) values ('377210','Arbitro de atletismo');--.go
insert into codigoCBO (codigo, descricao) values ('377215','Arbitro de basquete');--.go
insert into codigoCBO (codigo, descricao) values ('377220','Arbitro de futebol');--.go
insert into codigoCBO (codigo, descricao) values ('377225','Arbitro de futebol de salao');--.go
insert into codigoCBO (codigo, descricao) values ('377230','Arbitro de judô');--.go
insert into codigoCBO (codigo, descricao) values ('377235','Arbitro de karatê');--.go
insert into codigoCBO (codigo, descricao) values ('377240','Arbitro de poló aquático');--.go
insert into codigoCBO (codigo, descricao) values ('377245','Arbitro de vôlei');--.go
insert into codigoCBO (codigo, descricao) values ('377205','Arbitro desportivo');--.go
insert into codigoCBO (codigo, descricao) values ('715305','Armador de estrutura de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('715315','Armador de estrutura de concreto armado');--.go
insert into codigoCBO (codigo, descricao) values ('414110','Armazenista');--.go
insert into codigoCBO (codigo, descricao) values ('325010','Aromista');--.go
insert into codigoCBO (codigo, descricao) values ('251110','Arqueólogo');--.go
insert into codigoCBO (codigo, descricao) values ('214105','Arquiteto de edificaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('214110','Arquiteto de interiores');--.go
insert into codigoCBO (codigo, descricao) values ('214115','Arquiteto de patrimônio');--.go
insert into codigoCBO (codigo, descricao) values ('214120','Arquiteto paisagista');--.go
insert into codigoCBO (codigo, descricao) values ('214125','Arquiteto urbanista');--.go
insert into codigoCBO (codigo, descricao) values ('261305','Arquivista');--.go
insert into codigoCBO (codigo, descricao) values ('415105','Arquivista de documentos');--.go
insert into codigoCBO (codigo, descricao) values ('261105','Arquivista pesquisador (jornalismo)');--.go
insert into codigoCBO (codigo, descricao) values ('763305','Arrematadeira');--.go
insert into codigoCBO (codigo, descricao) values ('752105','Artesao modelador (vidros)');--.go
insert into codigoCBO (codigo, descricao) values ('262405','Artista (artes visuais)');--.go
insert into codigoCBO (codigo, descricao) values ('376210','Artista aéreo');--.go
insert into codigoCBO (codigo, descricao) values ('376215','Artista de circo (outros)');--.go
insert into codigoCBO (codigo, descricao) values ('768305','Artífice do couro');--.go
insert into codigoCBO (codigo, descricao) values ('514105','Ascensorista');--.go
insert into codigoCBO (codigo, descricao) values ('724105','Assentador de canalizaçao (edificaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('261110','Assessor de imprensa');--.go
insert into codigoCBO (codigo, descricao) values ('411010','Assistente administrativo');--.go
insert into codigoCBO (codigo, descricao) values ('351715','Assistente comercial de seguros');--.go
insert into codigoCBO (codigo, descricao) values ('262805','Assistente de coreografia');--.go
insert into codigoCBO (codigo, descricao) values ('818105','Assistente de laboratório industrial');--.go
insert into codigoCBO (codigo, descricao) values ('354125','Assistente de vendas');--.go
insert into codigoCBO (codigo, descricao) values ('251605','Assistente social');--.go
insert into codigoCBO (codigo, descricao) values ('351720','Assistente técnico de seguros');--.go
insert into codigoCBO (codigo, descricao) values ('716505','Assoalhador');--.go
insert into codigoCBO (codigo, descricao) values ('516705','Astrólogo');--.go
insert into codigoCBO (codigo, descricao) values ('213305','Astrônomo');--.go
insert into codigoCBO (codigo, descricao) values ('421105','Atendente comercial (agência postal)');--.go
insert into codigoCBO (codigo, descricao) values ('322415','Atendente de Consultório Dentário');--.go
insert into codigoCBO (codigo, descricao) values ('413205','Atendente de agência');--.go
insert into codigoCBO (codigo, descricao) values ('515110','Atendente de enfermagem');--.go
insert into codigoCBO (codigo, descricao) values ('521130','Atendente de farmácia - balconista');--.go
insert into codigoCBO (codigo, descricao) values ('411015','Atendente de judiciário');--.go
insert into codigoCBO (codigo, descricao) values ('513435','Atendente de lanchonete');--.go
insert into codigoCBO (codigo, descricao) values ('516340','Atendente de lavanderia');--.go
insert into codigoCBO (codigo, descricao) values ('377105','Atleta profissional (outras modalidades)');--.go
insert into codigoCBO (codigo, descricao) values ('377110','Atleta profissional de futebol');--.go
insert into codigoCBO (codigo, descricao) values ('377115','Atleta profissional de golfe');--.go
insert into codigoCBO (codigo, descricao) values ('377120','Atleta profissional de luta');--.go
insert into codigoCBO (codigo, descricao) values ('377125','Atleta profissional de tênis');--.go
insert into codigoCBO (codigo, descricao) values ('262505','Ator');--.go
insert into codigoCBO (codigo, descricao) values ('211105','Atuário');--.go
insert into codigoCBO (codigo, descricao) values ('252205','Auditor (contadores e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('254205','Auditor-fiscal da previdência social');--.go
insert into codigoCBO (codigo, descricao) values ('254105','Auditor-fiscal da receita federal');--.go
insert into codigoCBO (codigo, descricao) values ('254305','Auditor-fiscal do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('261505','Autor-roteirista');--.go
insert into codigoCBO (codigo, descricao) values ('322420','Auxiliar de Prótese Dentária');--.go
insert into codigoCBO (codigo, descricao) values ('515205','Auxiliar de banco de sangue');--.go
insert into codigoCBO (codigo, descricao) values ('371105','Auxiliar de biblioteca');--.go
insert into codigoCBO (codigo, descricao) values ('411025','Auxiliar de cartório');--.go
insert into codigoCBO (codigo, descricao) values ('413110','Auxiliar de contabilidade');--.go
insert into codigoCBO (codigo, descricao) values ('763105','Auxiliar de corte (preparaçao da confecçao de roupas)');--.go
insert into codigoCBO (codigo, descricao) values ('331110','Auxiliar de desenvolvimento infantil');--.go
insert into codigoCBO (codigo, descricao) values ('322230','Auxiliar de enfermagem');--.go
insert into codigoCBO (codigo, descricao) values ('322235','Auxiliar de enfermagem do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('411005','Auxiliar de escritório, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('411035','Auxiliar de estatística');--.go
insert into codigoCBO (codigo, descricao) values ('515210','Auxiliar de farmácia de manipulaçao');--.go
insert into codigoCBO (codigo, descricao) values ('413115','Auxiliar de faturamento');--.go
insert into codigoCBO (codigo, descricao) values ('411020','Auxiliar de judiciário');--.go
insert into codigoCBO (codigo, descricao) values ('515215','Auxiliar de laboratório de análises clínicas');--.go
insert into codigoCBO (codigo, descricao) values ('818110','Auxiliar de laboratório de análises físico-químicas');--.go
insert into codigoCBO (codigo, descricao) values ('515220','Auxiliar de laboratório de imunobiológicos');--.go
insert into codigoCBO (codigo, descricao) values ('516345','Auxiliar de lavanderia');--.go
insert into codigoCBO (codigo, descricao) values ('782625','Auxiliar de maquinista de trem');--.go
insert into codigoCBO (codigo, descricao) values ('411030','Auxiliar de pessoal');--.go
insert into codigoCBO (codigo, descricao) values ('842120','Auxiliar de processamento de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('515225','Auxiliar de produçao farmacêutica');--.go
insert into codigoCBO (codigo, descricao) values ('766420','Auxiliar de radiologia (revelaçao fotográfica)');--.go
insert into codigoCBO (codigo, descricao) values ('322240','Auxiliar de saúde (navegaçao marítima)');--.go
insert into codigoCBO (codigo, descricao) values ('411040','Auxiliar de seguros');--.go
insert into codigoCBO (codigo, descricao) values ('411045','Auxiliar de serviços de importaçao e exportaçao');--.go
insert into codigoCBO (codigo, descricao) values ('351430','Auxiliar de serviços jurídicos');--.go
insert into codigoCBO (codigo, descricao) values ('992225','Auxiliar geral de conservaçao de vias permanentes (exceto trilhos)');--.go
insert into codigoCBO (codigo, descricao) values ('513501','Auxiliar nos serviços de alimentaçao');--.go
insert into codigoCBO (codigo, descricao) values ('325105','Auxiliar técnico em laboratório de farmácia');--.go
insert into codigoCBO (codigo, descricao) values ('324210','Auxiliar técnico em patologia clínica');--.go
insert into codigoCBO (codigo, descricao) values ('354415','Avaliador de bens móveis');--.go
insert into codigoCBO (codigo, descricao) values ('354410','Avaliador de imóveis');--.go
insert into codigoCBO (codigo, descricao) values ('352410','Avaliador de produtos do meio de comunicaçao');--.go
insert into codigoCBO (codigo, descricao) values ('224105','Avaliador físico');--.go
insert into codigoCBO (codigo, descricao) values ('613305','Avicultor');--.go
insert into codigoCBO (codigo, descricao) values ('848510','Açougueiro');--.go
insert into codigoCBO (codigo, descricao) values ('516205','Babá');--.go
insert into codigoCBO (codigo, descricao) values ('262810','Bailarino (exceto danças populares)');--.go
insert into codigoCBO (codigo, descricao) values ('992110','Balanceador');--.go
insert into codigoCBO (codigo, descricao) values ('414115','Balanceiro');--.go
insert into codigoCBO (codigo, descricao) values ('811705','Bamburista');--.go
insert into codigoCBO (codigo, descricao) values ('519315','Banhista de animais domésticos');--.go
insert into codigoCBO (codigo, descricao) values ('516105','Barbeiro');--.go
insert into codigoCBO (codigo, descricao) values ('513420','Barman');--.go
insert into codigoCBO (codigo, descricao) values ('751105','Bate-folha a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('511220','Bilheteiro (estaçoes de metrô, ferroviárias e assemelhadas)');--.go
insert into codigoCBO (codigo, descricao) values ('421110','Bilheteiro de transportes coletivos');--.go
insert into codigoCBO (codigo, descricao) values ('421115','Bilheteiro no serviço de diversoes');--.go
insert into codigoCBO (codigo, descricao) values ('261205','Biliotecário');--.go
insert into codigoCBO (codigo, descricao) values ('201105','Bioengenheiro');--.go
insert into codigoCBO (codigo, descricao) values ('201110','Biotecnologista');--.go
insert into codigoCBO (codigo, descricao) values ('221105','Biólogo');--.go
insert into codigoCBO (codigo, descricao) values ('731165','Bobinador eletricista, à mao');--.go
insert into codigoCBO (codigo, descricao) values ('731170','Bobinador eletricista, à máquina');--.go
insert into codigoCBO (codigo, descricao) values ('782815','Boiadeiro');--.go
insert into codigoCBO (codigo, descricao) values ('517105','Bombeiro de aeródromo');--.go
insert into codigoCBO (codigo, descricao) values ('517110','Bombeiro de segurança do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('765015','Boneleiro');--.go
insert into codigoCBO (codigo, descricao) values ('768205','Bordador, a  mao');--.go
insert into codigoCBO (codigo, descricao) values ('763310','Bordador, à máquina');--.go
insert into codigoCBO (codigo, descricao) values ('992115','Borracheiro');--.go
insert into codigoCBO (codigo, descricao) values ('724305','Brasador');--.go
insert into codigoCBO (codigo, descricao) values ('516110','Cabeleireiro');--.go
insert into codigoCBO (codigo, descricao) values ('722405','Cableador');--.go
insert into codigoCBO (codigo, descricao) values ('031205','Cabo bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('021205','Cabo da polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('113005','Cacique');--.go
insert into codigoCBO (codigo, descricao) values ('612605','Cafeicultor');--.go
insert into codigoCBO (codigo, descricao) values ('413210','Caixa de banco');--.go
insert into codigoCBO (codigo, descricao) values ('716605','Calafetador');--.go
insert into codigoCBO (codigo, descricao) values ('811710','Calandrista de borracha');--.go
insert into codigoCBO (codigo, descricao) values ('832105','Calandrista de papel');--.go
insert into codigoCBO (codigo, descricao) values ('715205','Calceteiro');--.go
insert into codigoCBO (codigo, descricao) values ('724405','Caldeireiro (chapas de cobre)');--.go
insert into codigoCBO (codigo, descricao) values ('724410','Caldeireiro (chapas de ferro e aço)');--.go
insert into codigoCBO (codigo, descricao) values ('513305','Camareira de teatro');--.go
insert into codigoCBO (codigo, descricao) values ('513310','Camareira de televisao');--.go
insert into codigoCBO (codigo, descricao) values ('513315','Camareiro  de hotel');--.go
insert into codigoCBO (codigo, descricao) values ('513320','Camareiro de embarcaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('782505','Caminhoneiro autônomo (rotas regionais e internacionais)');--.go
insert into codigoCBO (codigo, descricao) values ('711110','Canteiro');--.go
insert into codigoCBO (codigo, descricao) values ('030205','Capitao bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('020205','Capitao da polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('215110','Capitao de manobra da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('632610','Carbonizador');--.go
insert into codigoCBO (codigo, descricao) values ('715505','Carpinteiro');--.go
insert into codigoCBO (codigo, descricao) values ('715515','Carpinteiro (cenários)');--.go
insert into codigoCBO (codigo, descricao) values ('715510','Carpinteiro (esquadrias)');--.go
insert into codigoCBO (codigo, descricao) values ('715520','Carpinteiro (mineraçao)');--.go
insert into codigoCBO (codigo, descricao) values ('715530','Carpinteiro (telhados)');--.go
insert into codigoCBO (codigo, descricao) values ('777205','Carpinteiro de carretas');--.go
insert into codigoCBO (codigo, descricao) values ('777210','Carpinteiro de carrocerias');--.go
insert into codigoCBO (codigo, descricao) values ('715535','Carpinteiro de fôrmas para concreto');--.go
insert into codigoCBO (codigo, descricao) values ('715525','Carpinteiro de obras');--.go
insert into codigoCBO (codigo, descricao) values ('715540','Carpinteiro de obras civis de arte (pontes, túneis, barragens)');--.go
insert into codigoCBO (codigo, descricao) values ('777105','Carpinteiro naval (construçao de pequenas embarcaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('777110','Carpinteiro naval (embarcaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('777115','Carpinteiro naval (estaleiros)');--.go
insert into codigoCBO (codigo, descricao) values ('783205','Carregador (aeronaves)');--.go
insert into codigoCBO (codigo, descricao) values ('783210','Carregador (armazém)');--.go
insert into codigoCBO (codigo, descricao) values ('783215','Carregador (veículos de transportes terrestres)');--.go
insert into codigoCBO (codigo, descricao) values ('519905','Cartazeiro');--.go
insert into codigoCBO (codigo, descricao) values ('415205','Carteiro');--.go
insert into codigoCBO (codigo, descricao) values ('833205','Cartonageiro, a mao (caixas de papelao)');--.go
insert into codigoCBO (codigo, descricao) values ('833105','Cartonageiro, a máquina');--.go
insert into codigoCBO (codigo, descricao) values ('632605','Carvoeiro');--.go
insert into codigoCBO (codigo, descricao) values ('622005','Caseiro (agricultura)');--.go
insert into codigoCBO (codigo, descricao) values ('631005','Catador de caranguejos e siris');--.go
insert into codigoCBO (codigo, descricao) values ('631010','Catador de mariscos');--.go
insert into codigoCBO (codigo, descricao) values ('519205','Catador de material reciclável');--.go
insert into codigoCBO (codigo, descricao) values ('842225','Celofanista na fabricaçao de charutos');--.go
insert into codigoCBO (codigo, descricao) values ('723105','Cementador de metais');--.go
insert into codigoCBO (codigo, descricao) values ('374205','Cenotécnico (cinema, vídeo, televisao, teatro e espetáculos)');--.go
insert into codigoCBO (codigo, descricao) values ('262305','Cenógrafo carnavalesco e festas populares');--.go
insert into codigoCBO (codigo, descricao) values ('262325','Cenógrafo de TV');--.go
insert into codigoCBO (codigo, descricao) values ('262310','Cenógrafo de cinema');--.go
insert into codigoCBO (codigo, descricao) values ('262315','Cenógrafo de eventos');--.go
insert into codigoCBO (codigo, descricao) values ('262320','Cenógrafo de teatro');--.go
insert into codigoCBO (codigo, descricao) values ('752305','Ceramista');--.go
insert into codigoCBO (codigo, descricao) values ('752310','Ceramista (torno de pedal e motor)');--.go
insert into codigoCBO (codigo, descricao) values ('752315','Ceramista (torno semi-automático)');--.go
insert into codigoCBO (codigo, descricao) values ('752320','Ceramista modelador');--.go
insert into codigoCBO (codigo, descricao) values ('752325','Ceramista moldador');--.go
insert into codigoCBO (codigo, descricao) values ('752330','Ceramista prensador');--.go
insert into codigoCBO (codigo, descricao) values ('768210','Cerzidor');--.go
insert into codigoCBO (codigo, descricao) values ('776405','Cesteiro');--.go
insert into codigoCBO (codigo, descricao) values ('724415','Chapeador');--.go
insert into codigoCBO (codigo, descricao) values ('724430','Chapeador de aeronaves');--.go
insert into codigoCBO (codigo, descricao) values ('724420','Chapeador de carrocerias metálicas (fabricaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('724425','Chapeador naval');--.go
insert into codigoCBO (codigo, descricao) values ('768125','Chapeleiro (chapéus de palha)');--.go
insert into codigoCBO (codigo, descricao) values ('765010','Chapeleiro de senhoras');--.go
insert into codigoCBO (codigo, descricao) values ('842230','Charuteiro a mao');--.go
insert into codigoCBO (codigo, descricao) values ('523115','Chaveiro');--.go
insert into codigoCBO (codigo, descricao) values ('510130','Chefe de bar');--.go
insert into codigoCBO (codigo, descricao) values ('840120','Chefe de confeitaria');--.go
insert into codigoCBO (codigo, descricao) values ('351110','Chefe de contabilidade (técnico)');--.go
insert into codigoCBO (codigo, descricao) values ('510125','Chefe de cozinha');--.go
insert into codigoCBO (codigo, descricao) values ('342605','Chefe de estaçao portuária');--.go
insert into codigoCBO (codigo, descricao) values ('510120','Chefe de portaria de hotel');--.go
insert into codigoCBO (codigo, descricao) values ('342305','Chefe de serviço de transporte rodoviário (passageiros e cargas)');--.go
insert into codigoCBO (codigo, descricao) values ('353235','Chefe de serviços bancários');--.go
insert into codigoCBO (codigo, descricao) values ('513605','Churrasqueiro');--.go
insert into codigoCBO (codigo, descricao) values ('519105','Ciclista mensageiro');--.go
insert into codigoCBO (codigo, descricao) values ('251115','Cientista político');--.go
insert into codigoCBO (codigo, descricao) values ('831105','Cilindreiro na preparaçao de pasta para fabricaçao de papel');--.go
insert into codigoCBO (codigo, descricao) values ('813105','Cilindrista (petroquímica e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('316340','Cimentador (poços de petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('223204','Cirurgiao dentista - auditor');--.go
insert into codigoCBO (codigo, descricao) values ('223208','Cirurgiao dentista - clínico geral');--.go
insert into codigoCBO (codigo, descricao) values ('223212','Cirurgiao dentista - endodontista');--.go
insert into codigoCBO (codigo, descricao) values ('223216','Cirurgiao dentista - epidemiologista');--.go
insert into codigoCBO (codigo, descricao) values ('223220','Cirurgiao dentista - estomatologista');--.go
insert into codigoCBO (codigo, descricao) values ('223224','Cirurgiao dentista - implantodontista');--.go
insert into codigoCBO (codigo, descricao) values ('223228','Cirurgiao dentista - odontogeriatra');--.go
insert into codigoCBO (codigo, descricao) values ('223232','Cirurgiao dentista - odontologista legal');--.go
insert into codigoCBO (codigo, descricao) values ('223236','Cirurgiao dentista - odontopediatra');--.go
insert into codigoCBO (codigo, descricao) values ('223240','Cirurgiao dentista - ortopedista e ortodontista');--.go
insert into codigoCBO (codigo, descricao) values ('223244','Cirurgiao dentista - patologista bucal');--.go
insert into codigoCBO (codigo, descricao) values ('223248','Cirurgiao dentista - periodontista');--.go
insert into codigoCBO (codigo, descricao) values ('223256','Cirurgiao dentista - protesista');--.go
insert into codigoCBO (codigo, descricao) values ('223252','Cirurgiao dentista - protesiólogo bucomaxilofacial');--.go
insert into codigoCBO (codigo, descricao) values ('223260','Cirurgiao dentista - radiologista');--.go
insert into codigoCBO (codigo, descricao) values ('223264','Cirurgiao dentista - reabilitador oral');--.go
insert into codigoCBO (codigo, descricao) values ('223268','Cirurgiao dentista - traumatologista bucomaxilofacial');--.go
insert into codigoCBO (codigo, descricao) values ('223272','Cirurgiao dentista de saúde coletiva');--.go
insert into codigoCBO (codigo, descricao) values ('842215','Classificador de charutos');--.go
insert into codigoCBO (codigo, descricao) values ('762210','Classificador de couros');--.go
insert into codigoCBO (codigo, descricao) values ('761105','Classificador de fibras têxteis');--.go
insert into codigoCBO (codigo, descricao) values ('842115','Classificador de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('772105','Classificador de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('762105','Classificador de peles');--.go
insert into codigoCBO (codigo, descricao) values ('632105','Classificador de toras');--.go
insert into codigoCBO (codigo, descricao) values ('823305','Classificador e empilhador de tijolos refratários');--.go
insert into codigoCBO (codigo, descricao) values ('511215','Cobrador de transportes coletivos (exceto trem)');--.go
insert into codigoCBO (codigo, descricao) values ('421305','Cobrador externo');--.go
insert into codigoCBO (codigo, descricao) values ('421310','Cobrador interno');--.go
insert into codigoCBO (codigo, descricao) values ('415115','Codificador de dados');--.go
insert into codigoCBO (codigo, descricao) values ('765205','Colchoeiro (confecçao de colchoes)');--.go
insert into codigoCBO (codigo, descricao) values ('371205','Colecionador de selos e moedas');--.go
insert into codigoCBO (codigo, descricao) values ('514205','Coletor de lixo');--.go
insert into codigoCBO (codigo, descricao) values ('311705','Colorista de papel');--.go
insert into codigoCBO (codigo, descricao) values ('311710','Colorista têxtil');--.go
insert into codigoCBO (codigo, descricao) values ('215115','Comandante da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('261710','Comentarista de rádio e televisao');--.go
insert into codigoCBO (codigo, descricao) values ('141405','Comerciante atacadista');--.go
insert into codigoCBO (codigo, descricao) values ('141410','Comerciante varejista');--.go
insert into codigoCBO (codigo, descricao) values ('511110','Comissário de trem');--.go
insert into codigoCBO (codigo, descricao) values ('511105','Comissário de vôo');--.go
insert into codigoCBO (codigo, descricao) values ('413215','Compensador de banco');--.go
insert into codigoCBO (codigo, descricao) values ('262605','Compositor');--.go
insert into codigoCBO (codigo, descricao) values ('354205','Comprador');--.go
insert into codigoCBO (codigo, descricao) values ('781105','Condutor de processos robotizados de pintura');--.go
insert into codigoCBO (codigo, descricao) values ('781110','Condutor de processos robotizados de soldagem');--.go
insert into codigoCBO (codigo, descricao) values ('782820','Condutor de veículos a pedais');--.go
insert into codigoCBO (codigo, descricao) values ('782805','Condutor de veículos de traçao animal (ruas e estradas)');--.go
insert into codigoCBO (codigo, descricao) values ('341305','Condutor maquinista fluvial');--.go
insert into codigoCBO (codigo, descricao) values ('341310','Condutor maquinista marítimo');--.go
insert into codigoCBO (codigo, descricao) values ('742110','Confeccionador de acordeao');--.go
insert into codigoCBO (codigo, descricao) values ('765005','Confeccionador de artefatos de couro (exceto sapatos)');--.go
insert into codigoCBO (codigo, descricao) values ('833110','Confeccionador de bolsas, sacos e sacolas e papel, a máquina');--.go
insert into codigoCBO (codigo, descricao) values ('765215','Confeccionador de brinquedos de pano');--.go
insert into codigoCBO (codigo, descricao) values ('768630','Confeccionador de carimbos de borracha');--.go
insert into codigoCBO (codigo, descricao) values ('776410','Confeccionador de escovas, pincéis e produtos similares (a mao)');--.go
insert into codigoCBO (codigo, descricao) values ('776415','Confeccionador de escovas, pincéis e produtos similares (a máquina)');--.go
insert into codigoCBO (codigo, descricao) values ('742115','Confeccionador de instrumentos de corda');--.go
insert into codigoCBO (codigo, descricao) values ('742120','Confeccionador de instrumentos de percussao (pele, couro ou plástico)');--.go
insert into codigoCBO (codigo, descricao) values ('742125','Confeccionador de instrumentos de sopro (madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('742130','Confeccionador de instrumentos de sopro (metal)');--.go
insert into codigoCBO (codigo, descricao) values ('776420','Confeccionador de móveis de vime, junco e bambu');--.go
insert into codigoCBO (codigo, descricao) values ('742140','Confeccionador de piano');--.go
insert into codigoCBO (codigo, descricao) values ('811715','Confeccionador de pneumáticos');--.go
insert into codigoCBO (codigo, descricao) values ('833115','Confeccionador de sacos de celofane, a máquina');--.go
insert into codigoCBO (codigo, descricao) values ('765225','Confeccionador de velas náuticas, barracas e toldos');--.go
insert into codigoCBO (codigo, descricao) values ('811725','Confeccionador de velas por imersao');--.go
insert into codigoCBO (codigo, descricao) values ('811735','Confeccionador de velas por moldagem');--.go
insert into codigoCBO (codigo, descricao) values ('742135','Confeccionador de órgao');--.go
insert into codigoCBO (codigo, descricao) values ('848310','Confeiteiro');--.go
insert into codigoCBO (codigo, descricao) values ('414215','Conferente de carga e descarga');--.go
insert into codigoCBO (codigo, descricao) values ('413220','Conferente de serviços bancários');--.go
insert into codigoCBO (codigo, descricao) values ('516335','Conferente-expedidor de roupas (lavanderias)');--.go
insert into codigoCBO (codigo, descricao) values ('991410','Conservador de fachadas');--.go
insert into codigoCBO (codigo, descricao) values ('991105','Conservador de via permanente (trilhos)');--.go
insert into codigoCBO (codigo, descricao) values ('351115','Consultor contábil (técnico)');--.go
insert into codigoCBO (codigo, descricao) values ('241040','Consultor jurídico');--.go
insert into codigoCBO (codigo, descricao) values ('252210','Contador');--.go
insert into codigoCBO (codigo, descricao) values ('376220','Contorcionista');--.go
insert into codigoCBO (codigo, descricao) values ('760105','Contramestre de acabamento (indústria têxtil)');--.go
insert into codigoCBO (codigo, descricao) values ('341205','Contramestre de cabotagem');--.go
insert into codigoCBO (codigo, descricao) values ('760110','Contramestre de fiaçao (indústria têxtil)');--.go
insert into codigoCBO (codigo, descricao) values ('760115','Contramestre de malharia (indústria têxtil)');--.go
insert into codigoCBO (codigo, descricao) values ('760120','Contramestre de tecelagem (indústria têxtil)');--.go
insert into codigoCBO (codigo, descricao) values ('391115','Controlador de entrada e saída');--.go
insert into codigoCBO (codigo, descricao) values ('519910','Controlador de pragas');--.go
insert into codigoCBO (codigo, descricao) values ('342115','Controlador de serviços de máquinas e veículos');--.go
insert into codigoCBO (codigo, descricao) values ('342505','Controlador de tráfego aéreo');--.go
insert into codigoCBO (codigo, descricao) values ('412205','Contínuo');--.go
insert into codigoCBO (codigo, descricao) values ('215120','Coordenador de operaçoes de combate à poluiçao no meio aquaviário');--.go
insert into codigoCBO (codigo, descricao) values ('239405','Coordenador pedagógico');--.go
insert into codigoCBO (codigo, descricao) values ('513425','Copeiro');--.go
insert into codigoCBO (codigo, descricao) values ('513430','Copeiro de hospital');--.go
insert into codigoCBO (codigo, descricao) values ('766105','Copiador de chapa');--.go
insert into codigoCBO (codigo, descricao) values ('262815','Coreógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('030105','Coronel bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('020105','Coronel da polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('354605','Corretor de imóveis');--.go
insert into codigoCBO (codigo, descricao) values ('354505','Corretor de seguros');--.go
insert into codigoCBO (codigo, descricao) values ('253305','Corretor de valores, ativos financeiros, mercadorias e derivativos');--.go
insert into codigoCBO (codigo, descricao) values ('765105','Cortador de artefatos de couro (exceto roupas e calçados)');--.go
insert into codigoCBO (codigo, descricao) values ('768310','Cortador de calçados, a  mao (exceto solas)');--.go
insert into codigoCBO (codigo, descricao) values ('764105','Cortador de calçados, a  máquina (exceto solas e palmilhas)');--.go
insert into codigoCBO (codigo, descricao) values ('842220','Cortador de charutos');--.go
insert into codigoCBO (codigo, descricao) values ('773105','Cortador de laminados de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('712205','Cortador de pedras');--.go
insert into codigoCBO (codigo, descricao) values ('763110','Cortador de roupas');--.go
insert into codigoCBO (codigo, descricao) values ('764110','Cortador de solas e palmilhas, a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('765110','Cortador de tapeçaria');--.go
insert into codigoCBO (codigo, descricao) values ('752210','Cortador de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('768315','Costurador de artefatos de couro, a  mao (exceto roupas e calçados)');--.go
insert into codigoCBO (codigo, descricao) values ('765310','Costurador de artefatos de couro, a  máquina (exceto roupas e calçados)');--.go
insert into codigoCBO (codigo, descricao) values ('764205','Costurador de calçados, a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('763010','Costureira de peças sob encomenda');--.go
insert into codigoCBO (codigo, descricao) values ('763015','Costureira de reparaçao de roupas');--.go
insert into codigoCBO (codigo, descricao) values ('763020','Costureiro de roupa de couro e pele');--.go
insert into codigoCBO (codigo, descricao) values ('763205','Costureiro de roupas de couro e pele, a  máquina na  confecçao em série');--.go
insert into codigoCBO (codigo, descricao) values ('763210','Costureiro na confecçao em série');--.go
insert into codigoCBO (codigo, descricao) values ('763215','Costureiro, a  máquina  na confecçao em série');--.go
insert into codigoCBO (codigo, descricao) values ('841408','Cozinhador (conservaçao de alimentos)');--.go
insert into codigoCBO (codigo, descricao) values ('841416','Cozinhador de carnes');--.go
insert into codigoCBO (codigo, descricao) values ('841420','Cozinhador de frutas e legumes');--.go
insert into codigoCBO (codigo, descricao) values ('841730','Cozinhador de malte');--.go
insert into codigoCBO (codigo, descricao) values ('841428','Cozinhador de pescado');--.go
insert into codigoCBO (codigo, descricao) values ('513225','Cozinheiro de embarcaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('513220','Cozinheiro de hospital');--.go
insert into codigoCBO (codigo, descricao) values ('513210','Cozinheiro do serviço doméstico');--.go
insert into codigoCBO (codigo, descricao) values ('513205','Cozinheiro geral');--.go
insert into codigoCBO (codigo, descricao) values ('513215','Cozinheiro industrial');--.go
insert into codigoCBO (codigo, descricao) values ('613010','Criador de animais domésticos');--.go
insert into codigoCBO (codigo, descricao) values ('613410','Criador de animais produtores de veneno');--.go
insert into codigoCBO (codigo, descricao) values ('613105','Criador de asininos e muares');--.go
insert into codigoCBO (codigo, descricao) values ('613110','Criador de bovinos (corte)');--.go
insert into codigoCBO (codigo, descricao) values ('613115','Criador de bovinos (leite)');--.go
insert into codigoCBO (codigo, descricao) values ('613120','Criador de bubalinos (corte)');--.go
insert into codigoCBO (codigo, descricao) values ('613125','Criador de bubalinos (leite)');--.go
insert into codigoCBO (codigo, descricao) values ('631305','Criador de camaroes');--.go
insert into codigoCBO (codigo, descricao) values ('613205','Criador de caprinos');--.go
insert into codigoCBO (codigo, descricao) values ('613130','Criador de eqüínos');--.go
insert into codigoCBO (codigo, descricao) values ('631310','Criador de jacarés');--.go
insert into codigoCBO (codigo, descricao) values ('631315','Criador de mexilhoes');--.go
insert into codigoCBO (codigo, descricao) values ('631320','Criador de ostras');--.go
insert into codigoCBO (codigo, descricao) values ('613210','Criador de ovinos');--.go
insert into codigoCBO (codigo, descricao) values ('631325','Criador de peixes');--.go
insert into codigoCBO (codigo, descricao) values ('631330','Criador de quelônios');--.go
insert into codigoCBO (codigo, descricao) values ('631335','Criador de ras');--.go
insert into codigoCBO (codigo, descricao) values ('613215','Criador de suínos');--.go
insert into codigoCBO (codigo, descricao) values ('613005','Criador em pecuária polivalente');--.go
insert into codigoCBO (codigo, descricao) values ('768130','Crocheteiro, a  mao');--.go
insert into codigoCBO (codigo, descricao) values ('391105','Cronoanalista');--.go
insert into codigoCBO (codigo, descricao) values ('391110','Cronometrista');--.go
insert into codigoCBO (codigo, descricao) values ('261510','Crítico');--.go
insert into codigoCBO (codigo, descricao) values ('632110','Cubador de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('516210','Cuidador de idosos');--.go
insert into codigoCBO (codigo, descricao) values ('513415','Cumim');--.go
insert into codigoCBO (codigo, descricao) values ('613310','Cunicultor');--.go
insert into codigoCBO (codigo, descricao) values ('762205','Curtidor (couros e peles)');--.go
insert into codigoCBO (codigo, descricao) values ('376110','Dançarino popular');--.go
insert into codigoCBO (codigo, descricao) values ('376105','Dançarino tradicional');--.go
insert into codigoCBO (codigo, descricao) values ('412105','Datilógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('723205','Decapador');--.go
insert into codigoCBO (codigo, descricao) values ('752405','Decorador de cerâmica');--.go
insert into codigoCBO (codigo, descricao) values ('262905','Decorador de interiores de nível superior');--.go
insert into codigoCBO (codigo, descricao) values ('752410','Decorador de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('752415','Decorador de vidro à pincel');--.go
insert into codigoCBO (codigo, descricao) values ('242405','Defensor público');--.go
insert into codigoCBO (codigo, descricao) values ('848105','Defumador de carnes e pescados');--.go
insert into codigoCBO (codigo, descricao) values ('848405','Degustador de café');--.go
insert into codigoCBO (codigo, descricao) values ('842235','Degustador de charutos');--.go
insert into codigoCBO (codigo, descricao) values ('848410','Degustador de chá');--.go
insert into codigoCBO (codigo, descricao) values ('848415','Degustador de derivados de cacau');--.go
insert into codigoCBO (codigo, descricao) values ('848420','Degustador de vinhos ou licores');--.go
insert into codigoCBO (codigo, descricao) values ('242305','Delegado de polícia');--.go
insert into codigoCBO (codigo, descricao) values ('717005','Demolidor de edificaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('521120','Demonstrador de mercadorias');--.go
insert into codigoCBO (codigo, descricao) values ('111115','Deputado Estadual e Distrital');--.go
insert into codigoCBO (codigo, descricao) values ('111110','Deputado Federal');--.go
insert into codigoCBO (codigo, descricao) values ('762110','Descarnador de couros e peles, à maquina');--.go
insert into codigoCBO (codigo, descricao) values ('318010','Desenhista copista');--.go
insert into codigoCBO (codigo, descricao) values ('318015','Desenhista detalhista');--.go
insert into codigoCBO (codigo, descricao) values ('262410','Desenhista industrial (designer)');--.go
insert into codigoCBO (codigo, descricao) values ('318505','Desenhista projetista de arquitetura');--.go
insert into codigoCBO (codigo, descricao) values ('318510','Desenhista projetista de construçao civil');--.go
insert into codigoCBO (codigo, descricao) values ('318705','Desenhista projetista de eletricidade');--.go
insert into codigoCBO (codigo, descricao) values ('318605','Desenhista projetista de máquinas');--.go
insert into codigoCBO (codigo, descricao) values ('318710','Desenhista projetista eletrônico');--.go
insert into codigoCBO (codigo, descricao) values ('318610','Desenhista projetista mecânico');--.go
insert into codigoCBO (codigo, descricao) values ('318005','Desenhista técnico');--.go
insert into codigoCBO (codigo, descricao) values ('318105','Desenhista técnico (arquitetura)');--.go
insert into codigoCBO (codigo, descricao) values ('318405','Desenhista técnico (artes gráficas)');--.go
insert into codigoCBO (codigo, descricao) values ('318310','Desenhista técnico (calefaçao, ventilaçao e refrigeraçao)');--.go
insert into codigoCBO (codigo, descricao) values ('318110','Desenhista técnico (cartografia)');--.go
insert into codigoCBO (codigo, descricao) values ('318115','Desenhista técnico (construçao civil)');--.go
insert into codigoCBO (codigo, descricao) values ('318305','Desenhista técnico (eletricidade e eletrônica)');--.go
insert into codigoCBO (codigo, descricao) values ('318410','Desenhista técnico (ilustraçoes artísticas)');--.go
insert into codigoCBO (codigo, descricao) values ('318415','Desenhista técnico (ilustraçoes técnicas)');--.go
insert into codigoCBO (codigo, descricao) values ('318420','Desenhista técnico (indústria têxtil)');--.go
insert into codigoCBO (codigo, descricao) values ('318120','Desenhista técnico (instalaçoes hidrossanitárias)');--.go
insert into codigoCBO (codigo, descricao) values ('318425','Desenhista técnico (mobiliário)');--.go
insert into codigoCBO (codigo, descricao) values ('318210','Desenhista técnico aeronáutico');--.go
insert into codigoCBO (codigo, descricao) values ('318430','Desenhista técnico de embalagens, maquetes e leiautes');--.go
insert into codigoCBO (codigo, descricao) values ('318205','Desenhista técnico mecânico');--.go
insert into codigoCBO (codigo, descricao) values ('318215','Desenhista técnico naval');--.go
insert into codigoCBO (codigo, descricao) values ('841432','Desidratador de alimentos');--.go
insert into codigoCBO (codigo, descricao) values ('375105','Designer de interiores');--.go
insert into codigoCBO (codigo, descricao) values ('375110','Designer de vitrines');--.go
insert into codigoCBO (codigo, descricao) values ('316335','Desincrustador (poços de petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('848515','Desossador');--.go
insert into codigoCBO (codigo, descricao) values ('342210','Despachante aduaneiro');--.go
insert into codigoCBO (codigo, descricao) values ('511210','Despachante de transportes coletivos (exceto trem)');--.go
insert into codigoCBO (codigo, descricao) values ('423105','Despachante documentalista');--.go
insert into codigoCBO (codigo, descricao) values ('342510','Despachante operacional de vôo');--.go
insert into codigoCBO (codigo, descricao) values ('841735','Dessecador de malte');--.go
insert into codigoCBO (codigo, descricao) values ('811405','Destilador de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('811410','Destilador de produtos químicos (exceto petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('711115','Destroçador de pedra');--.go
insert into codigoCBO (codigo, descricao) values ('351805','Detetive profissional');--.go
insert into codigoCBO (codigo, descricao) values ('711120','Detonador');--.go
insert into codigoCBO (codigo, descricao) values ('223705','Dietista');--.go
insert into codigoCBO (codigo, descricao) values ('412110','Digitador');--.go
insert into codigoCBO (codigo, descricao) values ('123105','Diretor administrativo');--.go
insert into codigoCBO (codigo, descricao) values ('123110','Diretor administrativo e financeiro');--.go
insert into codigoCBO (codigo, descricao) values ('123305','Diretor comercial');--.go
insert into codigoCBO (codigo, descricao) values ('122705','Diretor comercial em operaçoes de intermediaçao financeira');--.go
insert into codigoCBO (codigo, descricao) values ('122505','Diretor de  produçao e operaçoes de alimentaçao');--.go
insert into codigoCBO (codigo, descricao) values ('122510','Diretor de  produçao e operaçoes de hotel');--.go
insert into codigoCBO (codigo, descricao) values ('122515','Diretor de  produçao e operaçoes de turismo');--.go
insert into codigoCBO (codigo, descricao) values ('262330','Diretor de arte');--.go
insert into codigoCBO (codigo, descricao) values ('262205','Diretor de cinema');--.go
insert into codigoCBO (codigo, descricao) values ('122725','Diretor de compliance');--.go
insert into codigoCBO (codigo, descricao) values ('122730','Diretor de crédito (exceto crédito imobiliário)');--.go
insert into codigoCBO (codigo, descricao) values ('122735','Diretor de crédito imobiliário');--.go
insert into codigoCBO (codigo, descricao) values ('122715','Diretor de crédito rural');--.go
insert into codigoCBO (codigo, descricao) values ('122720','Diretor de câmbio e comércio exterior');--.go
insert into codigoCBO (codigo, descricao) values ('372105','Diretor de fotografia');--.go
insert into codigoCBO (codigo, descricao) values ('131305','Diretor de instituiçao educacional da área privada');--.go
insert into codigoCBO (codigo, descricao) values ('131310','Diretor de instituiçao educacional pública');--.go
insert into codigoCBO (codigo, descricao) values ('122740','Diretor de leasing');--.go
insert into codigoCBO (codigo, descricao) values ('123805','Diretor de manutençao');--.go
insert into codigoCBO (codigo, descricao) values ('123310','Diretor de marketing');--.go
insert into codigoCBO (codigo, descricao) values ('122745','Diretor de mercado de capitais');--.go
insert into codigoCBO (codigo, descricao) values ('122405','Diretor de operaçoes comerciais (comércio atacadista e varejista)');--.go
insert into codigoCBO (codigo, descricao) values ('122605','Diretor de operaçoes de correios');--.go
insert into codigoCBO (codigo, descricao) values ('122305','Diretor de operaçoes de obras pública e civil');--.go
insert into codigoCBO (codigo, descricao) values ('122610','Diretor de operaçoes de serviços de armazenamento');--.go
insert into codigoCBO (codigo, descricao) values ('122615','Diretor de operaçoes de serviços de telecomunicaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('122620','Diretor de operaçoes de serviços de transporte');--.go
insert into codigoCBO (codigo, descricao) values ('123705','Diretor de pesquisa e desenvolvimento (P&D)');--.go
insert into codigoCBO (codigo, descricao) values ('121005','Diretor de planejamento estratégico');--.go
insert into codigoCBO (codigo, descricao) values ('122710','Diretor de produtos bancários');--.go
insert into codigoCBO (codigo, descricao) values ('122205','Diretor de produçao e operaçoes da indústria de transformaçao, extraçao mineral e utilidades');--.go
insert into codigoCBO (codigo, descricao) values ('122105','Diretor de produçao e operaçoes em empresa agropecuária');--.go
insert into codigoCBO (codigo, descricao) values ('122110','Diretor de produçao e operaçoes em empresa aqüícola');--.go
insert into codigoCBO (codigo, descricao) values ('122115','Diretor de produçao e operaçoes em empresa florestal');--.go
insert into codigoCBO (codigo, descricao) values ('122120','Diretor de produçao e operaçoes em empresa pesqueira');--.go
insert into codigoCBO (codigo, descricao) values ('262210','Diretor de programas de rádio');--.go
insert into codigoCBO (codigo, descricao) values ('262215','Diretor de programas de televisao');--.go
insert into codigoCBO (codigo, descricao) values ('122750','Diretor de recuperaçao de créditos em operaçoes de intermediaçao financeira');--.go
insert into codigoCBO (codigo, descricao) values ('123205','Diretor de recursos humanos');--.go
insert into codigoCBO (codigo, descricao) values ('261115','Diretor de redaçao');--.go
insert into codigoCBO (codigo, descricao) values ('123210','Diretor de relaçoes de trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('122755','Diretor de riscos de mercado');--.go
insert into codigoCBO (codigo, descricao) values ('131105','Diretor de serviços culturais');--.go
insert into codigoCBO (codigo, descricao) values ('123605','Diretor de serviços de informática');--.go
insert into codigoCBO (codigo, descricao) values ('131205','Diretor de serviços de saúde');--.go
insert into codigoCBO (codigo, descricao) values ('131110','Diretor de serviços sociais');--.go
insert into codigoCBO (codigo, descricao) values ('123405','Diretor de suprimentos');--.go
insert into codigoCBO (codigo, descricao) values ('123410','Diretor de suprimentos no serviço público');--.go
insert into codigoCBO (codigo, descricao) values ('123115','Diretor financeiro');--.go
insert into codigoCBO (codigo, descricao) values ('121010','Diretor geral de empresa e organizaçoes (exceto de interesse público)');--.go
insert into codigoCBO (codigo, descricao) values ('262220','Diretor teatral');--.go
insert into codigoCBO (codigo, descricao) values ('114105','Dirigente de partido político');--.go
insert into codigoCBO (codigo, descricao) values ('111410','Dirigente do serviço público estadual e distrital');--.go
insert into codigoCBO (codigo, descricao) values ('111405','Dirigente do serviço público federal');--.go
insert into codigoCBO (codigo, descricao) values ('111415','Dirigente do serviço público municipal');--.go
insert into codigoCBO (codigo, descricao) values ('114405','Dirigente e administrador de organizaçao da sociedade civil sem fins lucrativos');--.go
insert into codigoCBO (codigo, descricao) values ('114305','Dirigente e administrador de organizaçao religiosa');--.go
insert into codigoCBO (codigo, descricao) values ('114205','Dirigentes de entidades de trabalhadores');--.go
insert into codigoCBO (codigo, descricao) values ('114210','Dirigentes de entidades patronais');--.go
insert into codigoCBO (codigo, descricao) values ('261210','Documentalista');--.go
insert into codigoCBO (codigo, descricao) values ('376225','Domador de animais (circense)');--.go
insert into codigoCBO (codigo, descricao) values ('811810','Drageador (medicamentos)');--.go
insert into codigoCBO (codigo, descricao) values ('262820','Dramaturgo de dança');--.go
insert into codigoCBO (codigo, descricao) values ('251205','Economista');--.go
insert into codigoCBO (codigo, descricao) values ('251210','Economista agroindustrial');--.go
insert into codigoCBO (codigo, descricao) values ('251230','Economista ambiental');--.go
insert into codigoCBO (codigo, descricao) values ('251225','Economista do setor público');--.go
insert into codigoCBO (codigo, descricao) values ('251610','Economista doméstico');--.go
insert into codigoCBO (codigo, descricao) values ('251215','Economista financeiro');--.go
insert into codigoCBO (codigo, descricao) values ('251220','Economista industrial');--.go
insert into codigoCBO (codigo, descricao) values ('251235','Economista regional e urbano');--.go
insert into codigoCBO (codigo, descricao) values ('261120','Editor');--.go
insert into codigoCBO (codigo, descricao) values ('374405','Editor de TV  e vídeo');--.go
insert into codigoCBO (codigo, descricao) values ('261605','Editor de jornal');--.go
insert into codigoCBO (codigo, descricao) values ('261610','Editor de livro');--.go
insert into codigoCBO (codigo, descricao) values ('261615','Editor de mídia eletrônica');--.go
insert into codigoCBO (codigo, descricao) values ('261620','Editor de revista');--.go
insert into codigoCBO (codigo, descricao) values ('261625','Editor de revista científica');--.go
insert into codigoCBO (codigo, descricao) values ('766120','Editor de texto e imagem');--.go
insert into codigoCBO (codigo, descricao) values ('341315','Eletricista de bordo');--.go
insert into codigoCBO (codigo, descricao) values ('715615','Eletricista de instalaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('953105','Eletricista de instalaçoes (aeronaves)');--.go
insert into codigoCBO (codigo, descricao) values ('715605','Eletricista de instalaçoes (cenários)');--.go
insert into codigoCBO (codigo, descricao) values ('715610','Eletricista de instalaçoes (edifícios)');--.go
insert into codigoCBO (codigo, descricao) values ('953110','Eletricista de instalaçoes (embarcaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('953115','Eletricista de instalaçoes (veículos automotores e máquinas operatrizes, exceto aeronaves e embarcaç');--.go
insert into codigoCBO (codigo, descricao) values ('732105','Eletricista de manutençao de linhas elétricas, telefônicas e de comunicaçao de dados');--.go
insert into codigoCBO (codigo, descricao) values ('951105','Eletricista de manutençao eletroeletrônica');--.go
insert into codigoCBO (codigo, descricao) values ('954105','Eletromecânico de manutençao de elevadores');--.go
insert into codigoCBO (codigo, descricao) values ('954110','Eletromecânico de manutençao de escadas rolantes');--.go
insert into codigoCBO (codigo, descricao) values ('954115','Eletromecânico de manutençao de portas automáticas');--.go
insert into codigoCBO (codigo, descricao) values ('313105','Eletrotécnico');--.go
insert into codigoCBO (codigo, descricao) values ('313110','Eletrotécnico (produçao de energia)');--.go
insert into codigoCBO (codigo, descricao) values ('313115','Eletroténico na fabricaçao, montagem e instalaçao de máquinas e equipamentos');--.go
insert into codigoCBO (codigo, descricao) values ('784105','Embalador, a mao');--.go
insert into codigoCBO (codigo, descricao) values ('784110','Embalador, a máquina');--.go
insert into codigoCBO (codigo, descricao) values ('328105','Embalsamador');--.go
insert into codigoCBO (codigo, descricao) values ('732110','Emendador de cabos elétricos e telefônicos (aéreos e subterrâneos)');--.go
insert into codigoCBO (codigo, descricao) values ('421120','Emissor de passagens');--.go
insert into codigoCBO (codigo, descricao) values ('512105','Empregado  doméstico  nos serviços gerais');--.go
insert into codigoCBO (codigo, descricao) values ('512110','Empregado doméstico  arrumador');--.go
insert into codigoCBO (codigo, descricao) values ('512115','Empregado doméstico  faxineiro');--.go
insert into codigoCBO (codigo, descricao) values ('512120','Empregado doméstico diarista');--.go
insert into codigoCBO (codigo, descricao) values ('262105','Empresário de espetáculo');--.go
insert into codigoCBO (codigo, descricao) values ('724110','Encanador');--.go
insert into codigoCBO (codigo, descricao) values ('821405','Encarregado de acabamento de chapas e metais  (têmpera)');--.go
insert into codigoCBO (codigo, descricao) values ('760305','Encarregado de corte na confecçao do vestuário');--.go
insert into codigoCBO (codigo, descricao) values ('760310','Encarregado de costura na confecçao do vestuário');--.go
insert into codigoCBO (codigo, descricao) values ('992210','Encarregado de equipe de conservaçao de vias permanentes (exceto trilhos)');--.go
insert into codigoCBO (codigo, descricao) values ('313415','Encarregado de manutençao de instrumentos de controle, mediçao e similares');--.go
insert into codigoCBO (codigo, descricao) values ('950205','Encarregado de manutençao elétrica de veículos');--.go
insert into codigoCBO (codigo, descricao) values ('910105','Encarregado de manutençao mecânica de sistemas operacionais');--.go
insert into codigoCBO (codigo, descricao) values ('992205','Encarregado geral de operaçoes de conservaçao de vias permanentes (exceto trilhos)');--.go
insert into codigoCBO (codigo, descricao) values ('223505','Enfermeiro');--.go
insert into codigoCBO (codigo, descricao) values ('223510','Enfermeiro auditor');--.go
insert into codigoCBO (codigo, descricao) values ('223515','Enfermeiro de bordo');--.go
insert into codigoCBO (codigo, descricao) values ('223520','Enfermeiro de centro cirúrgico');--.go
insert into codigoCBO (codigo, descricao) values ('223525','Enfermeiro de terapia intensiva');--.go
insert into codigoCBO (codigo, descricao) values ('223530','Enfermeiro do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('223535','Enfermeiro nefrologista');--.go
insert into codigoCBO (codigo, descricao) values ('223540','Enfermeiro neonatologista');--.go
insert into codigoCBO (codigo, descricao) values ('223545','Enfermeiro obstétrico');--.go
insert into codigoCBO (codigo, descricao) values ('223550','Enfermeiro psiquiátrico');--.go
insert into codigoCBO (codigo, descricao) values ('223555','Enfermeiro puericultor e pediátrico');--.go
insert into codigoCBO (codigo, descricao) values ('223560','Enfermeiro sanitarista');--.go
insert into codigoCBO (codigo, descricao) values ('519305','Enfermeiro veterinário');--.go
insert into codigoCBO (codigo, descricao) values ('763115','Enfestador de roupas');--.go
insert into codigoCBO (codigo, descricao) values ('751005','Engastador (jóias)');--.go
insert into codigoCBO (codigo, descricao) values ('214425','Engenheiro aeronáutico');--.go
insert into codigoCBO (codigo, descricao) values ('214805','Engenheiro agrimensor');--.go
insert into codigoCBO (codigo, descricao) values ('222105','Engenheiro agrícola');--.go
insert into codigoCBO (codigo, descricao) values ('222110','Engenheiro agrônomo');--.go
insert into codigoCBO (codigo, descricao) values ('214810','Engenheiro cartógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('214205','Engenheiro civil');--.go
insert into codigoCBO (codigo, descricao) values ('214210','Engenheiro civil (aeroportos)');--.go
insert into codigoCBO (codigo, descricao) values ('214215','Engenheiro civil (edificaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('214220','Engenheiro civil (estruturas metálicas)');--.go
insert into codigoCBO (codigo, descricao) values ('214225','Engenheiro civil (ferrovias e metrovias)');--.go
insert into codigoCBO (codigo, descricao) values ('214230','Engenheiro civil (geotécnia)');--.go
insert into codigoCBO (codigo, descricao) values ('214235','Engenheiro civil (hidrologia)');--.go
insert into codigoCBO (codigo, descricao) values ('214240','Engenheiro civil (hidráulica)');--.go
insert into codigoCBO (codigo, descricao) values ('214245','Engenheiro civil (pontes e viadutos)');--.go
insert into codigoCBO (codigo, descricao) values ('214250','Engenheiro civil (portos e vias navegáveis)');--.go
insert into codigoCBO (codigo, descricao) values ('214255','Engenheiro civil (rodovias)');--.go
insert into codigoCBO (codigo, descricao) values ('214260','Engenheiro civil (saneamento)');--.go
insert into codigoCBO (codigo, descricao) values ('214270','Engenheiro civil (transportes e trânsito)');--.go
insert into codigoCBO (codigo, descricao) values ('214265','Engenheiro civil (túneis)');--.go
insert into codigoCBO (codigo, descricao) values ('212205','Engenheiro de aplicativos em computaçao');--.go
insert into codigoCBO (codigo, descricao) values ('214910','Engenheiro de controle de qualidade');--.go
insert into codigoCBO (codigo, descricao) values ('214355','Engenheiro de controle e automaçao');--.go
insert into codigoCBO (codigo, descricao) values ('212210','Engenheiro de equipamentos em computaçao');--.go
insert into codigoCBO (codigo, descricao) values ('214335','Engenheiro de manutençao de telecomunicaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('214605','Engenheiro de materiais');--.go
insert into codigoCBO (codigo, descricao) values ('214705','Engenheiro de minas');--.go
insert into codigoCBO (codigo, descricao) values ('214710','Engenheiro de minas (beneficiamento)');--.go
insert into codigoCBO (codigo, descricao) values ('214715','Engenheiro de minas (lavra a céu aberto)');--.go
insert into codigoCBO (codigo, descricao) values ('214720','Engenheiro de minas (lavra subterrânea)');--.go
insert into codigoCBO (codigo, descricao) values ('214725','Engenheiro de minas (pesquisa mineral)');--.go
insert into codigoCBO (codigo, descricao) values ('214730','Engenheiro de minas (planejamento)');--.go
insert into codigoCBO (codigo, descricao) values ('214735','Engenheiro de minas (processo)');--.go
insert into codigoCBO (codigo, descricao) values ('214740','Engenheiro de minas (projeto)');--.go
insert into codigoCBO (codigo, descricao) values ('222115','Engenheiro de pesca');--.go
insert into codigoCBO (codigo, descricao) values ('214905','Engenheiro de produçao');--.go
insert into codigoCBO (codigo, descricao) values ('214350','Engenheiro de redes de comunicaçao');--.go
insert into codigoCBO (codigo, descricao) values ('214920','Engenheiro de riscos');--.go
insert into codigoCBO (codigo, descricao) values ('214915','Engenheiro de segurança do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('214340','Engenheiro de telecomunicaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('214925','Engenheiro de tempos e movimentos');--.go
insert into codigoCBO (codigo, descricao) values ('214305','Engenheiro eletricista');--.go
insert into codigoCBO (codigo, descricao) values ('214315','Engenheiro eletricista de manutençao');--.go
insert into codigoCBO (codigo, descricao) values ('214320','Engenheiro eletricista de projetos');--.go
insert into codigoCBO (codigo, descricao) values ('214310','Engenheiro eletrônico');--.go
insert into codigoCBO (codigo, descricao) values ('214325','Engenheiro eletrônico de manutençao');--.go
insert into codigoCBO (codigo, descricao) values ('214330','Engenheiro eletrônico de projetos');--.go
insert into codigoCBO (codigo, descricao) values ('222120','Engenheiro florestal');--.go
insert into codigoCBO (codigo, descricao) values ('202105','Engenheiro mecatrônico');--.go
insert into codigoCBO (codigo, descricao) values ('214405','Engenheiro mecânico');--.go
insert into codigoCBO (codigo, descricao) values ('214415','Engenheiro mecânico (energia nuclear)');--.go
insert into codigoCBO (codigo, descricao) values ('214410','Engenheiro mecânico automotivo');--.go
insert into codigoCBO (codigo, descricao) values ('214420','Engenheiro mecânico industrial');--.go
insert into codigoCBO (codigo, descricao) values ('214610','Engenheiro metalurgista');--.go
insert into codigoCBO (codigo, descricao) values ('214430','Engenheiro naval');--.go
insert into codigoCBO (codigo, descricao) values ('214345','Engenheiro projetista de telecomunicaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('214505','Engenheiro químico');--.go
insert into codigoCBO (codigo, descricao) values ('214510','Engenheiro químico (indústria química)');--.go
insert into codigoCBO (codigo, descricao) values ('214515','Engenheiro químico (mineraçao, metalurgia, siderurgia, cimenteira e cerâmica)');--.go
insert into codigoCBO (codigo, descricao) values ('214520','Engenheiro químico (papel e celulose)');--.go
insert into codigoCBO (codigo, descricao) values ('214525','Engenheiro químico (petróleo e borracha)');--.go
insert into codigoCBO (codigo, descricao) values ('214530','Engenheiro químico (utilidades e meio ambiente)');--.go
insert into codigoCBO (codigo, descricao) values ('212215','Engenheiros de sistemas operacionais em computaçao');--.go
insert into codigoCBO (codigo, descricao) values ('519915','Engraxate');--.go
insert into codigoCBO (codigo, descricao) values ('262825','Ensaiador de dança');--.go
insert into codigoCBO (codigo, descricao) values ('775105','Entalhador  de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('424105','Entrevistador censitário e de pesquisas amostrais');--.go
insert into codigoCBO (codigo, descricao) values ('424110','Entrevistador de pesquisa de opiniao e mídia');--.go
insert into codigoCBO (codigo, descricao) values ('424115','Entrevistador de pesquisas de mercado');--.go
insert into codigoCBO (codigo, descricao) values ('424120','Entrevistador de preços');--.go
insert into codigoCBO (codigo, descricao) values ('762215','Enxugador de couros');--.go
insert into codigoCBO (codigo, descricao) values ('325005','Enólogo');--.go
insert into codigoCBO (codigo, descricao) values ('376230','Equilibrista');--.go
insert into codigoCBO (codigo, descricao) values ('821410','Escarfador');--.go
insert into codigoCBO (codigo, descricao) values ('391225','Escolhedor de papel');--.go
insert into codigoCBO (codigo, descricao) values ('711125','Escorador de minas');--.go
insert into codigoCBO (codigo, descricao) values ('351405','Escrevente');--.go
insert into codigoCBO (codigo, descricao) values ('261515','Escritor de ficçao');--.go
insert into codigoCBO (codigo, descricao) values ('261520','Escritor de nao ficçao');--.go
insert into codigoCBO (codigo, descricao) values ('424125','Escriturário  em  estatística');--.go
insert into codigoCBO (codigo, descricao) values ('413225','Escriturário de banco');--.go
insert into codigoCBO (codigo, descricao) values ('351420','Escrivao de polícia');--.go
insert into codigoCBO (codigo, descricao) values ('351415','Escrivao extra - judicial');--.go
insert into codigoCBO (codigo, descricao) values ('351410','Escrivao judicial');--.go
insert into codigoCBO (codigo, descricao) values ('516805','Esotérico');--.go
insert into codigoCBO (codigo, descricao) values ('201210','Especialista em calibraçoes metrológicas');--.go
insert into codigoCBO (codigo, descricao) values ('201215','Especialista em ensaios metrológicos');--.go
insert into codigoCBO (codigo, descricao) values ('201220','Especialista em instrumentaçao metrológica');--.go
insert into codigoCBO (codigo, descricao) values ('201225','Especialista em materiais de referência metrológica');--.go
insert into codigoCBO (codigo, descricao) values ('211110','Especialista em pesquisa operacional');--.go
insert into codigoCBO (codigo, descricao) values ('761410','Estampador de tecido');--.go
insert into codigoCBO (codigo, descricao) values ('211205','Estatístico');--.go
insert into codigoCBO (codigo, descricao) values ('211210','Estatístico (estatística aplicada)');--.go
insert into codigoCBO (codigo, descricao) values ('211215','Estatístico teórico');--.go
insert into codigoCBO (codigo, descricao) values ('776425','Esteireiro');--.go
insert into codigoCBO (codigo, descricao) values ('351515','Estenotipista');--.go
insert into codigoCBO (codigo, descricao) values ('841440','Esterilizador de alimentos');--.go
insert into codigoCBO (codigo, descricao) values ('516115','Esteticista');--.go
insert into codigoCBO (codigo, descricao) values ('519310','Esteticista de animais domésticos');--.go
insert into codigoCBO (codigo, descricao) values ('762305','Estirador de couros e peles (acabamento)');--.go
insert into codigoCBO (codigo, descricao) values ('762115','Estirador de couros e peles (preparaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('722410','Estirador de tubos de metal sem costura');--.go
insert into codigoCBO (codigo, descricao) values ('783220','Estivador');--.go
insert into codigoCBO (codigo, descricao) values ('765230','Estofador de avioes');--.go
insert into codigoCBO (codigo, descricao) values ('765235','Estofador de móveis');--.go
insert into codigoCBO (codigo, descricao) values ('732115','Examinador de cabos, linhas elétricas e telefônicas');--.go
insert into codigoCBO (codigo, descricao) values ('823210','Extrusor de fios ou fibras de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('223405','Farmacêutico');--.go
insert into codigoCBO (codigo, descricao) values ('223410','Farmacêutico bioquímico');--.go
insert into codigoCBO (codigo, descricao) values ('514210','Faxineiro');--.go
insert into codigoCBO (codigo, descricao) values ('524205','Feirante');--.go
insert into codigoCBO (codigo, descricao) values ('841715','Fermentador');--.go
insert into codigoCBO (codigo, descricao) values ('721105','Ferramenteiro');--.go
insert into codigoCBO (codigo, descricao) values ('721110','Ferramenteiro de mandris, calibradores e outros dispositivos');--.go
insert into codigoCBO (codigo, descricao) values ('841710','Filtrador de cerveja');--.go
insert into codigoCBO (codigo, descricao) values ('261405','Filólogo');--.go
insert into codigoCBO (codigo, descricao) values ('251405','Filósofo');--.go
insert into codigoCBO (codigo, descricao) values ('374410','Finalizador de filmes');--.go
insert into codigoCBO (codigo, descricao) values ('374415','Finalizador de vídeo');--.go
insert into codigoCBO (codigo, descricao) values ('342515','Fiscal de aviaçao civil (FAC)');--.go
insert into codigoCBO (codigo, descricao) values ('710225','Fiscal de pátio de usina de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('511205','Fiscal de transportes coletivos (exceto trem)');--.go
insert into codigoCBO (codigo, descricao) values ('254405','Fiscal de tributos estadual');--.go
insert into codigoCBO (codigo, descricao) values ('254410','Fiscal de tributos municipal');--.go
insert into codigoCBO (codigo, descricao) values ('223605','Fisioterapeuta');--.go
insert into codigoCBO (codigo, descricao) values ('415120','Fitotecário');--.go
insert into codigoCBO (codigo, descricao) values ('862105','Foguista (locomotivas a vapor)');--.go
insert into codigoCBO (codigo, descricao) values ('775110','Folheador de móveis de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('223810','Fonoaudiólogo');--.go
insert into codigoCBO (codigo, descricao) values ('722105','Forjador');--.go
insert into codigoCBO (codigo, descricao) values ('722110','Forjador a martelo');--.go
insert into codigoCBO (codigo, descricao) values ('722115','Forjador prensista');--.go
insert into codigoCBO (codigo, descricao) values ('823315','Forneiro (materiais de construçao)');--.go
insert into codigoCBO (codigo, descricao) values ('822105','Forneiro de cubilô');--.go
insert into codigoCBO (codigo, descricao) values ('822110','Forneiro de forno-poço');--.go
insert into codigoCBO (codigo, descricao) values ('822115','Forneiro de fundiçao (forno de reduçao)');--.go
insert into codigoCBO (codigo, descricao) values ('822120','Forneiro de reaquecimento e tratamento térmico na metalurgia');--.go
insert into codigoCBO (codigo, descricao) values ('822125','Forneiro de revérbero');--.go
insert into codigoCBO (codigo, descricao) values ('821205','Forneiro e operador (alto-forno)');--.go
insert into codigoCBO (codigo, descricao) values ('821210','Forneiro e operador (conversor a oxigênio)');--.go
insert into codigoCBO (codigo, descricao) values ('821215','Forneiro e operador (forno elétrico)');--.go
insert into codigoCBO (codigo, descricao) values ('821220','Forneiro e operador (refino de metais nao-ferrosos)');--.go
insert into codigoCBO (codigo, descricao) values ('821225','Forneiro e operador de forno de reduçao direta');--.go
insert into codigoCBO (codigo, descricao) values ('823215','Forneiro na fundiçao de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('823220','Forneiro no recozimento de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('723210','Fosfatizador');--.go
insert into codigoCBO (codigo, descricao) values ('261805','Fotógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('261810','Fotógrafo publicitário');--.go
insert into codigoCBO (codigo, descricao) values ('261815','Fotógrafo retratista');--.go
insert into codigoCBO (codigo, descricao) values ('521135','Frentista');--.go
insert into codigoCBO (codigo, descricao) values ('762120','Fuloneiro');--.go
insert into codigoCBO (codigo, descricao) values ('762310','Fuloneiro no acabamento de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('751110','Fundidor (joalheria e ourivesaria)');--.go
insert into codigoCBO (codigo, descricao) values ('722205','Fundidor de metais');--.go
insert into codigoCBO (codigo, descricao) values ('991305','Funileiro de veículos (reparaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('724435','Funileiro industrial');--.go
insert into codigoCBO (codigo, descricao) values ('213105','Físico');--.go
insert into codigoCBO (codigo, descricao) values ('213110','Físico (acústica)');--.go
insert into codigoCBO (codigo, descricao) values ('213115','Físico (atômica e molecular)');--.go
insert into codigoCBO (codigo, descricao) values ('213120','Físico (cosmologia)');--.go
insert into codigoCBO (codigo, descricao) values ('213125','Físico (estatística e matemática)');--.go
insert into codigoCBO (codigo, descricao) values ('213130','Físico (fluidos)');--.go
insert into codigoCBO (codigo, descricao) values ('213135','Físico (instrumentaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('213145','Físico (materiais)');--.go
insert into codigoCBO (codigo, descricao) values ('213140','Físico (matéria condensada)');--.go
insert into codigoCBO (codigo, descricao) values ('213150','Físico (medicina)');--.go
insert into codigoCBO (codigo, descricao) values ('213155','Físico (nuclear e reatores)');--.go
insert into codigoCBO (codigo, descricao) values ('213165','Físico (partículas e campos)');--.go
insert into codigoCBO (codigo, descricao) values ('213170','Físico (plasma)');--.go
insert into codigoCBO (codigo, descricao) values ('213175','Físico (térmica)');--.go
insert into codigoCBO (codigo, descricao) values ('213160','Físico (óptica)');--.go
insert into codigoCBO (codigo, descricao) values ('723215','Galvanizador');--.go
insert into codigoCBO (codigo, descricao) values ('519920','Gandula');--.go
insert into codigoCBO (codigo, descricao) values ('514110','Garagista');--.go
insert into codigoCBO (codigo, descricao) values ('514215','Gari');--.go
insert into codigoCBO (codigo, descricao) values ('711405','Garimpeiro');--.go
insert into codigoCBO (codigo, descricao) values ('513405','Garçom');--.go
insert into codigoCBO (codigo, descricao) values ('513410','Garçom (serviços de vinhos)');--.go
insert into codigoCBO (codigo, descricao) values ('631405','Gelador industrial');--.go
insert into codigoCBO (codigo, descricao) values ('631410','Gelador profissional');--.go
insert into codigoCBO (codigo, descricao) values ('201115','Geneticista');--.go
insert into codigoCBO (codigo, descricao) values ('213415','Geofísico');--.go
insert into codigoCBO (codigo, descricao) values ('213310','Geofísico espacial');--.go
insert into codigoCBO (codigo, descricao) values ('213420','Geoquímico');--.go
insert into codigoCBO (codigo, descricao) values ('142105','Gerente administrativo');--.go
insert into codigoCBO (codigo, descricao) values ('142305','Gerente comercial');--.go
insert into codigoCBO (codigo, descricao) values ('342520','Gerente da administraçao de aeroportos');--.go
insert into codigoCBO (codigo, descricao) values ('141710','Gerente de agência');--.go
insert into codigoCBO (codigo, descricao) values ('142415','Gerente de almoxarifado');--.go
insert into codigoCBO (codigo, descricao) values ('141515','Gerente de bar');--.go
insert into codigoCBO (codigo, descricao) values ('253205','Gerente de captaçao (fundos e investimentos institucionais)');--.go
insert into codigoCBO (codigo, descricao) values ('253210','Gerente de clientes especiais (private)');--.go
insert into codigoCBO (codigo, descricao) values ('142405','Gerente de compras');--.go
insert into codigoCBO (codigo, descricao) values ('142310','Gerente de comunicaçao');--.go
insert into codigoCBO (codigo, descricao) values ('253215','Gerente de contas - pessoa física e jurídica');--.go
insert into codigoCBO (codigo, descricao) values ('141720','Gerente de crédito e cobrança');--.go
insert into codigoCBO (codigo, descricao) values ('141725','Gerente de crédito imobiliário');--.go
insert into codigoCBO (codigo, descricao) values ('141730','Gerente de crédito rural');--.go
insert into codigoCBO (codigo, descricao) values ('141715','Gerente de câmbio e comércio exterior');--.go
insert into codigoCBO (codigo, descricao) values ('142210','Gerente de departamento pessoal');--.go
insert into codigoCBO (codigo, descricao) values ('142510','Gerente de desenvolvimento de sistemas');--.go
insert into codigoCBO (codigo, descricao) values ('342525','Gerente de empresa aérea em aeroportos');--.go
insert into codigoCBO (codigo, descricao) values ('253220','Gerente de grandes contas (corporate)');--.go
insert into codigoCBO (codigo, descricao) values ('141505','Gerente de hotel');--.go
insert into codigoCBO (codigo, descricao) values ('131315','Gerente de instituiçao educacional da área privada');--.go
insert into codigoCBO (codigo, descricao) values ('141615','Gerente de logística (armazenagem e distribuiçao)');--.go
insert into codigoCBO (codigo, descricao) values ('141415','Gerente de loja e supermercado');--.go
insert into codigoCBO (codigo, descricao) values ('142315','Gerente de marketing');--.go
insert into codigoCBO (codigo, descricao) values ('141610','Gerente de operaçoes de correios e telecomunicaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('141420','Gerente de operaçoes de serviços de assistência técnica');--.go
insert into codigoCBO (codigo, descricao) values ('141605','Gerente de operaçoes de transportes');--.go
insert into codigoCBO (codigo, descricao) values ('141520','Gerente de pensao');--.go
insert into codigoCBO (codigo, descricao) values ('142605','Gerente de pesquisa e desenvolvimento (P&D)');--.go
insert into codigoCBO (codigo, descricao) values ('141705','Gerente de produtos bancários');--.go
insert into codigoCBO (codigo, descricao) values ('142515','Gerente de produçao de tecnologia da informaçao');--.go
insert into codigoCBO (codigo, descricao) values ('141205','Gerente de produçao e operaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('141105','Gerente de produçao e operaçoes  aqüícolas');--.go
insert into codigoCBO (codigo, descricao) values ('141110','Gerente de produçao e operaçoes  florestais');--.go
insert into codigoCBO (codigo, descricao) values ('141115','Gerente de produçao e operaçoes agropecuárias');--.go
insert into codigoCBO (codigo, descricao) values ('141305','Gerente de produçao e operaçoes da construçao civil e obras públicas');--.go
insert into codigoCBO (codigo, descricao) values ('141120','Gerente de produçao e operaçoes pesqueiras');--.go
insert into codigoCBO (codigo, descricao) values ('142520','Gerente de projetos de tecnologia da informaçao');--.go
insert into codigoCBO (codigo, descricao) values ('142705','Gerente de projetos e serviços de manutençao');--.go
insert into codigoCBO (codigo, descricao) values ('141735','Gerente de recuperaçao de crédito');--.go
insert into codigoCBO (codigo, descricao) values ('142205','Gerente de recursos humanos');--.go
insert into codigoCBO (codigo, descricao) values ('142505','Gerente de rede');--.go
insert into codigoCBO (codigo, descricao) values ('141510','Gerente de restaurante');--.go
insert into codigoCBO (codigo, descricao) values ('142110','Gerente de riscos');--.go
insert into codigoCBO (codigo, descricao) values ('142525','Gerente de segurança de tecnologia da informaçao');--.go
insert into codigoCBO (codigo, descricao) values ('131115','Gerente de serviços culturais');--.go
insert into codigoCBO (codigo, descricao) values ('131210','Gerente de serviços de saúde');--.go
insert into codigoCBO (codigo, descricao) values ('131320','Gerente de serviços educacionais da área pública');--.go
insert into codigoCBO (codigo, descricao) values ('131120','Gerente de serviços sociais');--.go
insert into codigoCBO (codigo, descricao) values ('142530','Gerente de suporte técnico de tecnologia da informaçao');--.go
insert into codigoCBO (codigo, descricao) values ('142410','Gerente de suprimentos');--.go
insert into codigoCBO (codigo, descricao) values ('142320','Gerente de vendas');--.go
insert into codigoCBO (codigo, descricao) values ('142115','Gerente financeiro');--.go
insert into codigoCBO (codigo, descricao) values ('716405','Gesseiro');--.go
insert into codigoCBO (codigo, descricao) values ('251305','Geógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('213405','Geólogo');--.go
insert into codigoCBO (codigo, descricao) values ('213410','Geólogo de engenharia');--.go
insert into codigoCBO (codigo, descricao) values ('111230','Governador de Estado');--.go
insert into codigoCBO (codigo, descricao) values ('111235','Governador do Distrito Federal');--.go
insert into codigoCBO (codigo, descricao) values ('513115','Governanta de hotelaria');--.go
insert into codigoCBO (codigo, descricao) values ('751115','Gravador (joalheria e ourivesaria)');--.go
insert into codigoCBO (codigo, descricao) values ('712210','Gravador de inscriçoes em pedra');--.go
insert into codigoCBO (codigo, descricao) values ('766135','Gravador de matriz calcográfica');--.go
insert into codigoCBO (codigo, descricao) values ('766115','Gravador de matriz para flexografia (clicherista)');--.go
insert into codigoCBO (codigo, descricao) values ('766130','Gravador de matriz para rotogravura (eletromecânico e químico)');--.go
insert into codigoCBO (codigo, descricao) values ('766140','Gravador de matriz serigráfica');--.go
insert into codigoCBO (codigo, descricao) values ('712215','Gravador de relevos em pedra');--.go
insert into codigoCBO (codigo, descricao) values ('752220','Gravador de vidro a  esmeril');--.go
insert into codigoCBO (codigo, descricao) values ('752225','Gravador de vidro a  jato de areia');--.go
insert into codigoCBO (codigo, descricao) values ('752215','Gravador de vidro a  água-forte');--.go
insert into codigoCBO (codigo, descricao) values ('768705','Gravador, à mao (encadernaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('517215','Guarda-civil municipal');--.go
insert into codigoCBO (codigo, descricao) values ('513325','Guarda-roupeira de cinema');--.go
insert into codigoCBO (codigo, descricao) values ('519925','Guardador de veículos');--.go
insert into codigoCBO (codigo, descricao) values ('511405','Guia de turismo');--.go
insert into codigoCBO (codigo, descricao) values ('632005','Guia florestal');--.go
insert into codigoCBO (codigo, descricao) values ('782205','Guincheiro (construçao civil)');--.go
insert into codigoCBO (codigo, descricao) values ('841444','Hidrogenador de óleos e gorduras');--.go
insert into codigoCBO (codigo, descricao) values ('213425','Hidrogeólogo');--.go
insert into codigoCBO (codigo, descricao) values ('632115','Identificador florestal');--.go
insert into codigoCBO (codigo, descricao) values ('372110','Iluminador (televisao)');--.go
insert into codigoCBO (codigo, descricao) values ('215125','Imediato da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('772110','Impregnador de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('766205','Impressor (serigrafia)');--.go
insert into codigoCBO (codigo, descricao) values ('766210','Impressor calcográfico');--.go
insert into codigoCBO (codigo, descricao) values ('766310','Impressor de corte e vinco');--.go
insert into codigoCBO (codigo, descricao) values ('766215','Impressor de ofsete (plano e rotativo)');--.go
insert into codigoCBO (codigo, descricao) values ('766220','Impressor de rotativa');--.go
insert into codigoCBO (codigo, descricao) values ('766225','Impressor de rotogravura');--.go
insert into codigoCBO (codigo, descricao) values ('766230','Impressor digital');--.go
insert into codigoCBO (codigo, descricao) values ('766235','Impressor flexográfico');--.go
insert into codigoCBO (codigo, descricao) values ('766240','Impressor letterset');--.go
insert into codigoCBO (codigo, descricao) values ('766245','Impressor tampográfico');--.go
insert into codigoCBO (codigo, descricao) values ('766250','Impressor tipográfico');--.go
insert into codigoCBO (codigo, descricao) values ('623010','Inseminador');--.go
insert into codigoCBO (codigo, descricao) values ('334105','Inspetor de alunos de escola privada');--.go
insert into codigoCBO (codigo, descricao) values ('334110','Inspetor de alunos de escola pública');--.go
insert into codigoCBO (codigo, descricao) values ('342530','Inspetor de aviaçao civil');--.go
insert into codigoCBO (codigo, descricao) values ('761805','Inspetor de estamparia (produçao têxtil)');--.go
insert into codigoCBO (codigo, descricao) values ('391205','Inspetor de qualidade');--.go
insert into codigoCBO (codigo, descricao) values ('351725','Inspetor de risco');--.go
insert into codigoCBO (codigo, descricao) values ('342310','Inspetor de serviços de transportes rodoviários (passageiros e cargas)');--.go
insert into codigoCBO (codigo, descricao) values ('351730','Inspetor de sinistros');--.go
insert into codigoCBO (codigo, descricao) values ('314605','Inspetor de soldagem');--.go
insert into codigoCBO (codigo, descricao) values ('215130','Inspetor de terminal');--.go
insert into codigoCBO (codigo, descricao) values ('710215','Inspetor de terraplenagem');--.go
insert into codigoCBO (codigo, descricao) values ('991110','Inspetor de via permanente (trilhos)');--.go
insert into codigoCBO (codigo, descricao) values ('215135','Inspetor naval');--.go
insert into codigoCBO (codigo, descricao) values ('523105','Instalador de cortinas e persianas, portas sanfonadas e boxe');--.go
insert into codigoCBO (codigo, descricao) values ('715710','Instalador de isolantes acústicos');--.go
insert into codigoCBO (codigo, descricao) values ('715715','Instalador de isolantes térmicos (refrigeraçao e climatizaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('715720','Instalador de isolantes térmicos de caldeira e tubulaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('732120','Instalador de linhas elétricas de alta e baixa - tensao (rede aérea e subterrânea)');--.go
insert into codigoCBO (codigo, descricao) values ('715725','Instalador de material isolante, a mao (edificaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('715730','Instalador de material isolante, a máquina (edificaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('951305','Instalador de sistemas eletroeletrônicos de segurança');--.go
insert into codigoCBO (codigo, descricao) values ('523110','Instalador de som e acessórios de veículos');--.go
insert into codigoCBO (codigo, descricao) values ('724115','Instalador de tubulaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('724120','Instalador de tubulaçoes (aeronaves)');--.go
insert into codigoCBO (codigo, descricao) values ('724125','Instalador de tubulaçoes (embarcaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('724130','Instalador de tubulaçoes de gás combustível (produçao e distribuiçao)');--.go
insert into codigoCBO (codigo, descricao) values ('724135','Instalador de tubulaçoes de vapor (produçao e distribuiçao)');--.go
insert into codigoCBO (codigo, descricao) values ('732125','Instalador eletricista (traçao de veículos)');--.go
insert into codigoCBO (codigo, descricao) values ('731305','Instalador-reparador de equipamentos de comutaçao em telefonia');--.go
insert into codigoCBO (codigo, descricao) values ('731310','Instalador-reparador de equipamentos de energia em telefonia');--.go
insert into codigoCBO (codigo, descricao) values ('731315','Instalador-reparador de equipamentos de transmissao em telefonia');--.go
insert into codigoCBO (codigo, descricao) values ('731320','Instalador-reparador de linhas e aparelhos de telecomunicaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('731325','Instalador-reparador de redes e cabos telefônicos');--.go
insert into codigoCBO (codigo, descricao) values ('732130','Instalador-reparador de redes telefônicas e de comunicaçao de dados');--.go
insert into codigoCBO (codigo, descricao) values ('322225','Instrumentador cirúrgico');--.go
insert into codigoCBO (codigo, descricao) values ('233205','Instrutor de aprendizagem e treinamento agropecuário');--.go
insert into codigoCBO (codigo, descricao) values ('233210','Instrutor de aprendizagem e treinamento industrial');--.go
insert into codigoCBO (codigo, descricao) values ('333105','Instrutor de auto-escola');--.go
insert into codigoCBO (codigo, descricao) values ('333110','Instrutor de cursos livres');--.go
insert into codigoCBO (codigo, descricao) values ('215315','Instrutor de vôo');--.go
insert into codigoCBO (codigo, descricao) values ('261410','Intérprete');--.go
insert into codigoCBO (codigo, descricao) values ('351810','Investigador de polícia');--.go
insert into codigoCBO (codigo, descricao) values ('622010','Jardineiro');--.go
insert into codigoCBO (codigo, descricao) values ('751010','Joalheiro');--.go
insert into codigoCBO (codigo, descricao) values ('751015','Joalheiro (reparaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('524210','Jornaleiro (em banca de jornal)');--.go
insert into codigoCBO (codigo, descricao) values ('261125','Jornalista');--.go
insert into codigoCBO (codigo, descricao) values ('111340','Juiz auditor estadual - justiça militar');--.go
insert into codigoCBO (codigo, descricao) values ('111335','Juiz auditor federal - justiça militar');--.go
insert into codigoCBO (codigo, descricao) values ('111325','Juiz de direito');--.go
insert into codigoCBO (codigo, descricao) values ('111345','Juiz do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('111330','Juiz federal');--.go
insert into codigoCBO (codigo, descricao) values ('377130','Jóquei');--.go
insert into codigoCBO (codigo, descricao) values ('415125','Kardexista');--.go
insert into codigoCBO (codigo, descricao) values ('766405','Laboratorista fotográfico');--.go
insert into codigoCBO (codigo, descricao) values ('716510','Ladrilheiro');--.go
insert into codigoCBO (codigo, descricao) values ('841448','Lagareiro');--.go
insert into codigoCBO (codigo, descricao) values ('751120','Laminador de metais preciosos a  mao');--.go
insert into codigoCBO (codigo, descricao) values ('811745','Laminador de plástico');--.go
insert into codigoCBO (codigo, descricao) values ('751020','Lapidador (jóias)');--.go
insert into codigoCBO (codigo, descricao) values ('752230','Lapidador de vidros e cristais');--.go
insert into codigoCBO (codigo, descricao) values ('516305','Lavadeiro, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('516315','Lavador de artefatos de tapeçaria');--.go
insert into codigoCBO (codigo, descricao) values ('519930','Lavador de garrafas, vidros e outros utensílios');--.go
insert into codigoCBO (codigo, descricao) values ('761110','Lavador de la');--.go
insert into codigoCBO (codigo, descricao) values ('992120','Lavador de peças');--.go
insert into codigoCBO (codigo, descricao) values ('516405','Lavador de roupas');--.go
insert into codigoCBO (codigo, descricao) values ('516310','Lavador de roupas  a maquina');--.go
insert into codigoCBO (codigo, descricao) values ('519935','Lavador de veículos');--.go
insert into codigoCBO (codigo, descricao) values ('354405','Leiloeiro');--.go
insert into codigoCBO (codigo, descricao) values ('519940','Leiturista');--.go
insert into codigoCBO (codigo, descricao) values ('732135','Ligador de linhas telefônicas');--.go
insert into codigoCBO (codigo, descricao) values ('516320','Limpador a seco, à máquina');--.go
insert into codigoCBO (codigo, descricao) values ('991415','Limpador de fachadas');--.go
insert into codigoCBO (codigo, descricao) values ('516410','Limpador de roupas a seco, à mao');--.go
insert into codigoCBO (codigo, descricao) values ('514220','Limpador de vidros');--.go
insert into codigoCBO (codigo, descricao) values ('722210','Lingotador');--.go
insert into codigoCBO (codigo, descricao) values ('261415','Lingüista');--.go
insert into codigoCBO (codigo, descricao) values ('768610','Linotipista');--.go
insert into codigoCBO (codigo, descricao) values ('762315','Lixador de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('421315','Localizador (cobrador)');--.go
insert into codigoCBO (codigo, descricao) values ('261715','Locutor de rádio e televisao');--.go
insert into codigoCBO (codigo, descricao) values ('261720','Locutor publicitário de rádio e televisao');--.go
insert into codigoCBO (codigo, descricao) values ('919115','Lubrificador de embarcaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('919110','Lubrificador de veículos automotores (exceto embarcaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('919105','Lubrificador industrial');--.go
insert into codigoCBO (codigo, descricao) values ('224110','Ludomotricista');--.go
insert into codigoCBO (codigo, descricao) values ('775115','Lustrador de peças de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('716520','Lustrador de piso');--.go
insert into codigoCBO (codigo, descricao) values ('915215','Luthier (restauraçao de cordas arcadas)');--.go
insert into codigoCBO (codigo, descricao) values ('113010','Líder de comunidade caiçara');--.go
insert into codigoCBO (codigo, descricao) values ('722310','Macheiro, a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('722305','Macheiro, a mao');--.go
insert into codigoCBO (codigo, descricao) values ('516215','Mae social');--.go
insert into codigoCBO (codigo, descricao) values ('848520','Magarefe');--.go
insert into codigoCBO (codigo, descricao) values ('030110','Major bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('020115','Major da polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('376240','Malabarista');--.go
insert into codigoCBO (codigo, descricao) values ('841725','Malteiro (germinaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('516120','Manicure');--.go
insert into codigoCBO (codigo, descricao) values ('783110','Manobrador');--.go
insert into codigoCBO (codigo, descricao) values ('848215','Manteigueiro na fabricaçao de laticínio');--.go
insert into codigoCBO (codigo, descricao) values ('991205','Mantenedor de equipamentos de parques de diversoes e similares');--.go
insert into codigoCBO (codigo, descricao) values ('951310','Mantenedor de sistemas eletroeletrônicos de segurança');--.go
insert into codigoCBO (codigo, descricao) values ('771115','Maquetista na marcenaria');--.go
insert into codigoCBO (codigo, descricao) values ('516125','Maquiador');--.go
insert into codigoCBO (codigo, descricao) values ('516130','Maquiador de caracterizaçao');--.go
insert into codigoCBO (codigo, descricao) values ('374210','Maquinista de cinema e vídeo');--.go
insert into codigoCBO (codigo, descricao) values ('862110','Maquinista de embarcaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('374215','Maquinista de teatro e espetáculos');--.go
insert into codigoCBO (codigo, descricao) values ('782610','Maquinista de trem');--.go
insert into codigoCBO (codigo, descricao) values ('782615','Maquinista de trem metropolitano');--.go
insert into codigoCBO (codigo, descricao) values ('763315','Marcador de peças confeccionadas para bordar');--.go
insert into codigoCBO (codigo, descricao) values ('821415','Marcador de produtos (siderúrgico e metalúrgico)');--.go
insert into codigoCBO (codigo, descricao) values ('771105','Marceneiro');--.go
insert into codigoCBO (codigo, descricao) values ('775120','Marcheteiro');--.go
insert into codigoCBO (codigo, descricao) values ('782705','Marinheiro de convés (marítimo e fluviário)');--.go
insert into codigoCBO (codigo, descricao) values ('782710','Marinheiro de máquinas');--.go
insert into codigoCBO (codigo, descricao) values ('716525','Marmorista (construçao)');--.go
insert into codigoCBO (codigo, descricao) values ('516135','Massagista');--.go
insert into codigoCBO (codigo, descricao) values ('848315','Masseiro (massas alimentícias)');--.go
insert into codigoCBO (codigo, descricao) values ('211115','Matemático');--.go
insert into codigoCBO (codigo, descricao) values ('211120','Matemático aplicado');--.go
insert into codigoCBO (codigo, descricao) values ('762320','Matizador de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('510135','Maître');--.go
insert into codigoCBO (codigo, descricao) values ('914105','Mecânico de manutençao de aeronaves, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('913105','Mecânico de manutençao de aparelhos de levantamento');--.go
insert into codigoCBO (codigo, descricao) values ('919305','Mecânico de manutençao de aparelhos esportivos e de ginástica');--.go
insert into codigoCBO (codigo, descricao) values ('914405','Mecânico de manutençao de automóveis, motocicletas e veículos similares');--.go
insert into codigoCBO (codigo, descricao) values ('919310','Mecânico de manutençao de bicicletas e veículos similares');--.go
insert into codigoCBO (codigo, descricao) values ('911105','Mecânico de manutençao de bomba injetora (exceto de veículos automotores)');--.go
insert into codigoCBO (codigo, descricao) values ('911110','Mecânico de manutençao de bombas');--.go
insert into codigoCBO (codigo, descricao) values ('911115','Mecânico de manutençao de compressores de ar');--.go
insert into codigoCBO (codigo, descricao) values ('914410','Mecânico de manutençao de empilhadeiras e outros veículos de cargas leves');--.go
insert into codigoCBO (codigo, descricao) values ('913110','Mecânico de manutençao de equipamento de mineraçao');--.go
insert into codigoCBO (codigo, descricao) values ('954120','Mecânico de manutençao de instalaçoes mecânicas de edifícios');--.go
insert into codigoCBO (codigo, descricao) values ('914415','Mecânico de manutençao de motocicletas');--.go
insert into codigoCBO (codigo, descricao) values ('911120','Mecânico de manutençao de motores diesel (exceto de veículos automotores)');--.go
insert into codigoCBO (codigo, descricao) values ('914205','Mecânico de manutençao de motores e equipamentos navais');--.go
insert into codigoCBO (codigo, descricao) values ('913115','Mecânico de manutençao de máquinas agrícolas');--.go
insert into codigoCBO (codigo, descricao) values ('919205','Mecânico de manutençao de máquinas cortadoras de grama, roçadeiras, motosserras e similares');--.go
insert into codigoCBO (codigo, descricao) values ('913120','Mecânico de manutençao de máquinas de construçao e terraplenagem');--.go
insert into codigoCBO (codigo, descricao) values ('911310','Mecânico de manutençao de máquinas gráficas');--.go
insert into codigoCBO (codigo, descricao) values ('911315','Mecânico de manutençao de máquinas operatrizes (lavra de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('911320','Mecânico de manutençao de máquinas têxteis');--.go
insert into codigoCBO (codigo, descricao) values ('911305','Mecânico de manutençao de máquinas, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('911325','Mecânico de manutençao de máquinas-ferramentas (usinagem de metais)');--.go
insert into codigoCBO (codigo, descricao) values ('911125','Mecânico de manutençao de redutores');--.go
insert into codigoCBO (codigo, descricao) values ('914110','Mecânico de manutençao de sistema hidráulico de aeronaves (serviços de pista e hangar)');--.go
insert into codigoCBO (codigo, descricao) values ('914420','Mecânico de manutençao de tratores');--.go
insert into codigoCBO (codigo, descricao) values ('911130','Mecânico de manutençao de turbinas (exceto de aeronaves)');--.go
insert into codigoCBO (codigo, descricao) values ('911135','Mecânico de manutençao de turbocompressores');--.go
insert into codigoCBO (codigo, descricao) values ('914305','Mecânico de manutençao de veículos ferroviários');--.go
insert into codigoCBO (codigo, descricao) values ('911205','Mecânico de manutençao e instalaçao de aparelhos de climatizaçao e  refrigeraçao');--.go
insert into codigoCBO (codigo, descricao) values ('725705','Mecânico de refrigeraçao');--.go
insert into codigoCBO (codigo, descricao) values ('914425','Mecânico de veículos automotores a diesel (exceto tratores)');--.go
insert into codigoCBO (codigo, descricao) values ('341115','Mecânico de vôo');--.go
insert into codigoCBO (codigo, descricao) values ('725405','Mecânico montador de motores de aeronaves');--.go
insert into codigoCBO (codigo, descricao) values ('725410','Mecânico montador de motores de embarcaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('725415','Mecânico montador de motores de explosao e diesel');--.go
insert into codigoCBO (codigo, descricao) values ('725420','Mecânico montador de turboalimentadores');--.go
insert into codigoCBO (codigo, descricao) values ('113015','Membro de liderança quilombola');--.go
insert into codigoCBO (codigo, descricao) values ('111225','Membro superior do poder Executivo');--.go
insert into codigoCBO (codigo, descricao) values ('781705','Mergulhador profissional (raso e profundo)');--.go
insert into codigoCBO (codigo, descricao) values ('720105','Mestre (afiador de ferramentas)');--.go
insert into codigoCBO (codigo, descricao) values ('710205','Mestre (construçao civil)');--.go
insert into codigoCBO (codigo, descricao) values ('720205','Mestre (construçao naval)');--.go
insert into codigoCBO (codigo, descricao) values ('720210','Mestre (indústria de automotores e material de transportes)');--.go
insert into codigoCBO (codigo, descricao) values ('810205','Mestre (indústria de borracha e plástico)');--.go
insert into codigoCBO (codigo, descricao) values ('830105','Mestre (indústria de celulose, papel e papelao)');--.go
insert into codigoCBO (codigo, descricao) values ('770105','Mestre (indústria de madeira e mobiliário)');--.go
insert into codigoCBO (codigo, descricao) values ('720215','Mestre (indústria de máquinas e outros equipamentos mecânicos)');--.go
insert into codigoCBO (codigo, descricao) values ('810105','Mestre (indústria petroquímica e carboquímica)');--.go
insert into codigoCBO (codigo, descricao) values ('760125','Mestre (indústria têxtil e de confecçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('770110','Mestre carpinteiro');--.go
insert into codigoCBO (codigo, descricao) values ('820110','Mestre de aciaria');--.go
insert into codigoCBO (codigo, descricao) values ('820115','Mestre de alto-forno');--.go
insert into codigoCBO (codigo, descricao) values ('341210','Mestre de cabotagem');--.go
insert into codigoCBO (codigo, descricao) values ('720110','Mestre de caldeiraria');--.go
insert into codigoCBO (codigo, descricao) values ('720220','Mestre de construçao de fornos');--.go
insert into codigoCBO (codigo, descricao) values ('720115','Mestre de ferramentaria');--.go
insert into codigoCBO (codigo, descricao) values ('720120','Mestre de forjaria');--.go
insert into codigoCBO (codigo, descricao) values ('820120','Mestre de forno elétrico');--.go
insert into codigoCBO (codigo, descricao) values ('720125','Mestre de fundiçao');--.go
insert into codigoCBO (codigo, descricao) values ('720130','Mestre de galvanoplastia');--.go
insert into codigoCBO (codigo, descricao) values ('820125','Mestre de laminaçao');--.go
insert into codigoCBO (codigo, descricao) values ('710210','Mestre de linhas (ferrovias)');--.go
insert into codigoCBO (codigo, descricao) values ('720135','Mestre de pintura (tratamento de superfícies)');--.go
insert into codigoCBO (codigo, descricao) values ('810305','Mestre de produçao farmacêutica');--.go
insert into codigoCBO (codigo, descricao) values ('810110','Mestre de produçao química');--.go
insert into codigoCBO (codigo, descricao) values ('820105','Mestre de siderurgia');--.go
insert into codigoCBO (codigo, descricao) values ('720140','Mestre de soldagem');--.go
insert into codigoCBO (codigo, descricao) values ('720145','Mestre de trefilaçao de metais');--.go
insert into codigoCBO (codigo, descricao) values ('720150','Mestre de usinagem');--.go
insert into codigoCBO (codigo, descricao) values ('341215','Mestre fluvial');--.go
insert into codigoCBO (codigo, descricao) values ('720155','Mestre serralheiro');--.go
insert into codigoCBO (codigo, descricao) values ('723225','Metalizador (banho quente)');--.go
insert into codigoCBO (codigo, descricao) values ('723220','Metalizador a pistola');--.go
insert into codigoCBO (codigo, descricao) values ('213315','Meteorologista');--.go
insert into codigoCBO (codigo, descricao) values ('352305','Metrologista');--.go
insert into codigoCBO (codigo, descricao) values ('374140','Microfonista');--.go
insert into codigoCBO (codigo, descricao) values ('711130','Mineiro');--.go
insert into codigoCBO (codigo, descricao) values ('613415','Minhocultor');--.go
insert into codigoCBO (codigo, descricao) values ('111215','Ministro de Estado');--.go
insert into codigoCBO (codigo, descricao) values ('263105','Ministro de culto religioso');--.go
insert into codigoCBO (codigo, descricao) values ('111315','Ministro do  Superior Tribunal Militar');--.go
insert into codigoCBO (codigo, descricao) values ('111320','Ministro do  Superior Tribunal do Trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('111310','Ministro do Superior Tribunal de Justiça');--.go
insert into codigoCBO (codigo, descricao) values ('111305','Ministro do Supremo Tribunal Federal');--.go
insert into codigoCBO (codigo, descricao) values ('263110','Missionário');--.go
insert into codigoCBO (codigo, descricao) values ('841605','Misturador de café');--.go
insert into codigoCBO (codigo, descricao) values ('841630','Misturador de chá ou mate');--.go
insert into codigoCBO (codigo, descricao) values ('771110','Modelador de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('721115','Modelador de metais (fundiçao)');--.go
insert into codigoCBO (codigo, descricao) values ('318815','Modelista de calçados');--.go
insert into codigoCBO (codigo, descricao) values ('318810','Modelista de roupas');--.go
insert into codigoCBO (codigo, descricao) values ('376405','Modelo artístico');--.go
insert into codigoCBO (codigo, descricao) values ('376410','Modelo de modas');--.go
insert into codigoCBO (codigo, descricao) values ('376415','Modelo publicitário');--.go
insert into codigoCBO (codigo, descricao) values ('841615','Moedor de café');--.go
insert into codigoCBO (codigo, descricao) values ('841205','Moedor de sal');--.go
insert into codigoCBO (codigo, descricao) values ('752110','Moldador (vidros)');--.go
insert into codigoCBO (codigo, descricao) values ('823230','Moldador de abrasivos na fabricaçao de cerâmica, vidro e porcelana');--.go
insert into codigoCBO (codigo, descricao) values ('811750','Moldador de borracha por compressao');--.go
insert into codigoCBO (codigo, descricao) values ('715310','Moldador de corpos de prova em usinas de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('811760','Moldador de plástico por compressao');--.go
insert into codigoCBO (codigo, descricao) values ('811770','Moldador de plástico por injeçao');--.go
insert into codigoCBO (codigo, descricao) values ('722315','Moldador, a  mao');--.go
insert into codigoCBO (codigo, descricao) values ('722320','Moldador, a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('811105','Moleiro (tratamentos químicos e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('841105','Moleiro de cereais (exceto arroz)');--.go
insert into codigoCBO (codigo, descricao) values ('841110','Moleiro de especiarias');--.go
insert into codigoCBO (codigo, descricao) values ('712105','Moleiro de minérios');--.go
insert into codigoCBO (codigo, descricao) values ('422215','Monitor de teleatendimento');--.go
insert into codigoCBO (codigo, descricao) values ('768615','Monotipista');--.go
insert into codigoCBO (codigo, descricao) values ('715545','Montador de andaimes (edificaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('765315','Montador de artefatos de couro (exceto roupas e calçados)');--.go
insert into codigoCBO (codigo, descricao) values ('919315','Montador de bicicletas');--.go
insert into codigoCBO (codigo, descricao) values ('764210','Montador de calçados');--.go
insert into codigoCBO (codigo, descricao) values ('725305','Montador de equipamento de levantamento');--.go
insert into codigoCBO (codigo, descricao) values ('731150','Montador de equipamentos eletrônicos');--.go
insert into codigoCBO (codigo, descricao) values ('731105','Montador de equipamentos eletrônicos (aparelhos médicos)');--.go
insert into codigoCBO (codigo, descricao) values ('731110','Montador de equipamentos eletrônicos (computadores e equipamentos auxiliares)');--.go
insert into codigoCBO (codigo, descricao) values ('731205','Montador de equipamentos eletrônicos (estaçao de rádio, TV e equipamentos de radar)');--.go
insert into codigoCBO (codigo, descricao) values ('731140','Montador de equipamentos eletrônicos (instalaçoes de sinalizaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('731145','Montador de equipamentos eletrônicos (máquinas industriais)');--.go
insert into codigoCBO (codigo, descricao) values ('731135','Montador de equipamentos elétricos');--.go
insert into codigoCBO (codigo, descricao) values ('731120','Montador de equipamentos elétricos (aparelhos eletrodomésticos)');--.go
insert into codigoCBO (codigo, descricao) values ('731125','Montador de equipamentos elétricos (centrais elétricas)');--.go
insert into codigoCBO (codigo, descricao) values ('731155','Montador de equipamentos elétricos (elevadores e equipamentos similares)');--.go
insert into codigoCBO (codigo, descricao) values ('731115','Montador de equipamentos elétricos (instrumentos de mediçao)');--.go
insert into codigoCBO (codigo, descricao) values ('731130','Montador de equipamentos elétricos (motores e dínamos)');--.go
insert into codigoCBO (codigo, descricao) values ('731160','Montador de equipamentos elétricos (transformadores)');--.go
insert into codigoCBO (codigo, descricao) values ('725605','Montador de estruturas de aeronaves');--.go
insert into codigoCBO (codigo, descricao) values ('724205','Montador de estruturas metálicas');--.go
insert into codigoCBO (codigo, descricao) values ('724210','Montador de estruturas metálicas de embarcaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('374420','Montador de filmes');--.go
insert into codigoCBO (codigo, descricao) values ('766125','Montador de fotolito (analógico e digital)');--.go
insert into codigoCBO (codigo, descricao) values ('741115','Montador de instrumentos de precisao');--.go
insert into codigoCBO (codigo, descricao) values ('741110','Montador de instrumentos de óptica');--.go
insert into codigoCBO (codigo, descricao) values ('725205','Montador de máquinas');--.go
insert into codigoCBO (codigo, descricao) values ('725310','Montador de máquinas agrícolas');--.go
insert into codigoCBO (codigo, descricao) values ('725315','Montador de máquinas de minas e pedreiras');--.go
insert into codigoCBO (codigo, descricao) values ('725320','Montador de máquinas de terraplenagem');--.go
insert into codigoCBO (codigo, descricao) values ('725210','Montador de máquinas gráficas');--.go
insert into codigoCBO (codigo, descricao) values ('725215','Montador de máquinas operatrizes para madeira');--.go
insert into codigoCBO (codigo, descricao) values ('725220','Montador de máquinas têxteis');--.go
insert into codigoCBO (codigo, descricao) values ('725105','Montador de máquinas, motores e acessórios (montagem em série)');--.go
insert into codigoCBO (codigo, descricao) values ('725225','Montador de máquinas-ferramentas (usinagem de metais)');--.go
insert into codigoCBO (codigo, descricao) values ('774105','Montador de móveis e artefatos de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('725610','Montador de sistemas de combustível de aeronaves');--.go
insert into codigoCBO (codigo, descricao) values ('725505','Montador de veículos (linha de montagem)');--.go
insert into codigoCBO (codigo, descricao) values ('991310','Montador de veículos (reparaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('513110','Mordomo de hotelaria');--.go
insert into codigoCBO (codigo, descricao) values ('513105','Mordomo de residência');--.go
insert into codigoCBO (codigo, descricao) values ('716530','Mosaísta');--.go
insert into codigoCBO (codigo, descricao) values ('519110','Motociclista no transporte de documentos e pequenos volumes');--.go
insert into codigoCBO (codigo, descricao) values ('782510','Motorista de caminhao (rotas regionais e internacionais)');--.go
insert into codigoCBO (codigo, descricao) values ('782305','Motorista de carro de passeio');--.go
insert into codigoCBO (codigo, descricao) values ('782310','Motorista de furgao ou veículo similar');--.go
insert into codigoCBO (codigo, descricao) values ('782415','Motorista de trólebus');--.go
insert into codigoCBO (codigo, descricao) values ('782315','Motorista de táxi');--.go
insert into codigoCBO (codigo, descricao) values ('782405','Motorista de ônibus rodoviário');--.go
insert into codigoCBO (codigo, descricao) values ('782410','Motorista de ônibus urbano');--.go
insert into codigoCBO (codigo, descricao) values ('782515','Motorista operacional de guincho');--.go
insert into codigoCBO (codigo, descricao) values ('782620','Motorneiro');--.go
insert into codigoCBO (codigo, descricao) values ('782715','Moço de convés (marítimo e fluviário)');--.go
insert into codigoCBO (codigo, descricao) values ('782720','Moço de máquinas (marítimo e fluviário)');--.go
insert into codigoCBO (codigo, descricao) values ('261310','Museólogo');--.go
insert into codigoCBO (codigo, descricao) values ('262620','Musicólogo');--.go
insert into codigoCBO (codigo, descricao) values ('376235','Mágico');--.go
insert into codigoCBO (codigo, descricao) values ('223101','Médico acupunturista');--.go
insert into codigoCBO (codigo, descricao) values ('223102','Médico alergista e imunologista');--.go
insert into codigoCBO (codigo, descricao) values ('223103','Médico anatomopatologista');--.go
insert into codigoCBO (codigo, descricao) values ('223104','Médico anestesiologista');--.go
insert into codigoCBO (codigo, descricao) values ('223105','Médico angiologista');--.go
insert into codigoCBO (codigo, descricao) values ('223106','Médico cardiologista');--.go
insert into codigoCBO (codigo, descricao) values ('223107','Médico cirurgiao cardiovascular');--.go
insert into codigoCBO (codigo, descricao) values ('223108','Médico cirurgiao de cabeça e pescoço');--.go
insert into codigoCBO (codigo, descricao) values ('223109','Médico cirurgiao do aparelho digestivo');--.go
insert into codigoCBO (codigo, descricao) values ('223110','Médico cirurgiao geral');--.go
insert into codigoCBO (codigo, descricao) values ('223111','Médico cirurgiao pediátrico');--.go
insert into codigoCBO (codigo, descricao) values ('223112','Médico cirurgiao plástico');--.go
insert into codigoCBO (codigo, descricao) values ('223113','Médico cirurgiao torácico');--.go
insert into codigoCBO (codigo, descricao) values ('223114','Médico citopatologista');--.go
insert into codigoCBO (codigo, descricao) values ('223115','Médico clínico');--.go
insert into codigoCBO (codigo, descricao) values ('223116','Médico de saúde da família');--.go
insert into codigoCBO (codigo, descricao) values ('223117','Médico dermatologista');--.go
insert into codigoCBO (codigo, descricao) values ('223118','Médico do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('223119','Médico em eletroencefalografia');--.go
insert into codigoCBO (codigo, descricao) values ('223120','Médico em endoscopia');--.go
insert into codigoCBO (codigo, descricao) values ('223121','Médico em medicina de tráfego');--.go
insert into codigoCBO (codigo, descricao) values ('223122','Médico em medicina intensiva');--.go
insert into codigoCBO (codigo, descricao) values ('223123','Médico em medicina nuclear');--.go
insert into codigoCBO (codigo, descricao) values ('223124','Médico em radiologia e diagnóstico por imagem');--.go
insert into codigoCBO (codigo, descricao) values ('223125','Médico endocrinologista e metabologista');--.go
insert into codigoCBO (codigo, descricao) values ('223126','Médico fisiatra');--.go
insert into codigoCBO (codigo, descricao) values ('223127','Médico foniatra');--.go
insert into codigoCBO (codigo, descricao) values ('223128','Médico gastroenterologista');--.go
insert into codigoCBO (codigo, descricao) values ('223129','Médico generalista');--.go
insert into codigoCBO (codigo, descricao) values ('223130','Médico geneticista');--.go
insert into codigoCBO (codigo, descricao) values ('223131','Médico geriatra');--.go
insert into codigoCBO (codigo, descricao) values ('223132','Médico ginecologista e obstetra');--.go
insert into codigoCBO (codigo, descricao) values ('223133','Médico hematologista');--.go
insert into codigoCBO (codigo, descricao) values ('223134','Médico hemoterapeuta');--.go
insert into codigoCBO (codigo, descricao) values ('223135','Médico homeopata');--.go
insert into codigoCBO (codigo, descricao) values ('223136','Médico infectologista');--.go
insert into codigoCBO (codigo, descricao) values ('223137','Médico legista');--.go
insert into codigoCBO (codigo, descricao) values ('223138','Médico mastologista');--.go
insert into codigoCBO (codigo, descricao) values ('223139','Médico nefrologista');--.go
insert into codigoCBO (codigo, descricao) values ('223140','Médico neurocirurgiao');--.go
insert into codigoCBO (codigo, descricao) values ('223141','Médico neurofisiologista');--.go
insert into codigoCBO (codigo, descricao) values ('223142','Médico neurologista');--.go
insert into codigoCBO (codigo, descricao) values ('223143','Médico nutrologista');--.go
insert into codigoCBO (codigo, descricao) values ('223144','Médico oftalmologista');--.go
insert into codigoCBO (codigo, descricao) values ('223145','Médico oncologista');--.go
insert into codigoCBO (codigo, descricao) values ('223146','Médico ortopedista e traumatologista');--.go
insert into codigoCBO (codigo, descricao) values ('223147','Médico otorrinolaringologista');--.go
insert into codigoCBO (codigo, descricao) values ('223148','Médico patologista clínico');--.go
insert into codigoCBO (codigo, descricao) values ('223149','Médico pediatra');--.go
insert into codigoCBO (codigo, descricao) values ('223150','Médico perito');--.go
insert into codigoCBO (codigo, descricao) values ('223151','Médico pneumologista');--.go
insert into codigoCBO (codigo, descricao) values ('223152','Médico proctologista');--.go
insert into codigoCBO (codigo, descricao) values ('223153','Médico psiquiatra');--.go
insert into codigoCBO (codigo, descricao) values ('223154','Médico radioterapeuta');--.go
insert into codigoCBO (codigo, descricao) values ('223155','Médico reumatologista');--.go
insert into codigoCBO (codigo, descricao) values ('223156','Médico sanitarista');--.go
insert into codigoCBO (codigo, descricao) values ('223157','Médico urologista');--.go
insert into codigoCBO (codigo, descricao) values ('223305','Médico veterinário');--.go
insert into codigoCBO (codigo, descricao) values ('262610','Músico arranjador');--.go
insert into codigoCBO (codigo, descricao) values ('262705','Músico intérprete cantor');--.go
insert into codigoCBO (codigo, descricao) values ('262710','Músico intérprete instrumentista');--.go
insert into codigoCBO (codigo, descricao) values ('262615','Músico regente');--.go
insert into codigoCBO (codigo, descricao) values ('261725','Narrador em programas de rádio e televisao');--.go
insert into codigoCBO (codigo, descricao) values ('251545','Neuropsicólogo');--.go
insert into codigoCBO (codigo, descricao) values ('723110','Normalizador de metais e de compósitos');--.go
insert into codigoCBO (codigo, descricao) values ('516710','Numerólogo');--.go
insert into codigoCBO (codigo, descricao) values ('223710','Nutricionista');--.go
insert into codigoCBO (codigo, descricao) values ('010105','Oficial General da Aeronáutica');--.go
insert into codigoCBO (codigo, descricao) values ('010115','Oficial General da Marinha');--.go
insert into codigoCBO (codigo, descricao) values ('010110','Oficial General do Exército');--.go
insert into codigoCBO (codigo, descricao) values ('010205','Oficial da Aeronáutica');--.go
insert into codigoCBO (codigo, descricao) values ('010215','Oficial da Marinha');--.go
insert into codigoCBO (codigo, descricao) values ('351425','Oficial de justiça');--.go
insert into codigoCBO (codigo, descricao) values ('215140','Oficial de quarto de navegaçao da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('241305','Oficial de registro de contratos marítimos');--.go
insert into codigoCBO (codigo, descricao) values ('010210','Oficial do Exército');--.go
insert into codigoCBO (codigo, descricao) values ('241310','Oficial do registro civil de pessoas juridicas');--.go
insert into codigoCBO (codigo, descricao) values ('241315','Oficial do registro civil de pessoas naturais');--.go
insert into codigoCBO (codigo, descricao) values ('241320','Oficial do registro de distribuiçoes');--.go
insert into codigoCBO (codigo, descricao) values ('241325','Oficial do registro de imóveis');--.go
insert into codigoCBO (codigo, descricao) values ('241330','Oficial do registro de títulos e documentos');--.go
insert into codigoCBO (codigo, descricao) values ('215205','Oficial superior de máquinas da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('828105','Oleiro (fabricaçao de telhas)');--.go
insert into codigoCBO (codigo, descricao) values ('828110','Oleiro (fabricaçao de tijolos)');--.go
insert into codigoCBO (codigo, descricao) values ('761205','Operador de abertura (fiaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('766315','Operador de acabamento (indústria gráfica)');--.go
insert into codigoCBO (codigo, descricao) values ('722215','Operador de acabamento de peças fundidas');--.go
insert into codigoCBO (codigo, descricao) values ('821230','Operador de aciaria (basculamento de convertedor)');--.go
insert into codigoCBO (codigo, descricao) values ('821235','Operador de aciaria (dessulfuraçao de gusa)');--.go
insert into codigoCBO (codigo, descricao) values ('821240','Operador de aciaria (recebimento de gusa)');--.go
insert into codigoCBO (codigo, descricao) values ('811415','Operador de alambique de funcionamento contínuo (produtos químicos, exceto petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('712110','Operador de aparelho de flotaçao');--.go
insert into codigoCBO (codigo, descricao) values ('712115','Operador de aparelho de precipitaçao (minas de ouro ou prata)');--.go
insert into codigoCBO (codigo, descricao) values ('811420','Operador de aparelho de reaçao e conversao (produtos químicos, exceto petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('342535','Operador de atendimento aeroviário');--.go
insert into codigoCBO (codigo, descricao) values ('823135','Operador de atomizador');--.go
insert into codigoCBO (codigo, descricao) values ('823235','Operador de banho metálico de vidro por flutuaçao');--.go
insert into codigoCBO (codigo, descricao) values ('715105','Operador de bate-estacas');--.go
insert into codigoCBO (codigo, descricao) values ('862115','Operador de bateria de gás de hulha');--.go
insert into codigoCBO (codigo, descricao) values ('715405','Operador de betoneira');--.go
insert into codigoCBO (codigo, descricao) values ('761210','Operador de binadeira');--.go
insert into codigoCBO (codigo, descricao) values ('761215','Operador de bobinadeira');--.go
insert into codigoCBO (codigo, descricao) values ('821420','Operador de bobinadeira de tiras a quente, no acabamento de chapas e metais');--.go
insert into codigoCBO (codigo, descricao) values ('715410','Operador de bomba de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('831110','Operador de branqueador de pasta para fabricaçao de papel');--.go
insert into codigoCBO (codigo, descricao) values ('811115','Operador de britadeira (tratamentos químicos e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('811605','Operador de britador de coque');--.go
insert into codigoCBO (codigo, descricao) values ('712120','Operador de britador de mandíbulas');--.go
insert into codigoCBO (codigo, descricao) values ('821425','Operador de cabine de laminaçao (fio-máquina)');--.go
insert into codigoCBO (codigo, descricao) values ('421125','Operador de caixa');--.go
insert into codigoCBO (codigo, descricao) values ('813110','Operador de calandra (química, petroquímica e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('761415','Operador de calandras (tecidos)');--.go
insert into codigoCBO (codigo, descricao) values ('811205','Operador de calcinaçao (tratamento químico e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('862120','Operador de caldeira');--.go
insert into codigoCBO (codigo, descricao) values ('711205','Operador de caminhao (minas e pedreiras)');--.go
insert into codigoCBO (codigo, descricao) values ('761220','Operador de cardas');--.go
insert into codigoCBO (codigo, descricao) values ('711210','Operador de carregadeira');--.go
insert into codigoCBO (codigo, descricao) values ('811610','Operador de carro de apagamento e coque');--.go
insert into codigoCBO (codigo, descricao) values ('992215','Operador de ceifadeira na conservaçao de vias permanentes');--.go
insert into codigoCBO (codigo, descricao) values ('715415','Operador de central de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('373110','Operador de central de rádio');--.go
insert into codigoCBO (codigo, descricao) values ('861105','Operador de central hidrelétrica');--.go
insert into codigoCBO (codigo, descricao) values ('861115','Operador de central termoelétrica');--.go
insert into codigoCBO (codigo, descricao) values ('811305','Operador de centrifugadora (tratamentos químicos e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('821105','Operador de centro de controle');--.go
insert into codigoCBO (codigo, descricao) values ('342410','Operador de centro de controle (ferrovia e metrô)');--.go
insert into codigoCBO (codigo, descricao) values ('721405','Operador de centro de usinagem com comando numérico');--.go
insert into codigoCBO (codigo, descricao) values ('773505','Operador de centro de usinagem de madeira (CNC)');--.go
insert into codigoCBO (codigo, descricao) values ('761420','Operador de chamuscadeira de tecidos');--.go
insert into codigoCBO (codigo, descricao) values ('413230','Operador de cobrança bancária');--.go
insert into codigoCBO (codigo, descricao) values ('642005','Operador de colhedor florestal');--.go
insert into codigoCBO (codigo, descricao) values ('641005','Operador de colheitadeira');--.go
insert into codigoCBO (codigo, descricao) values ('715110','Operador de compactadora de solos');--.go
insert into codigoCBO (codigo, descricao) values ('862130','Operador de compressor de ar');--.go
insert into codigoCBO (codigo, descricao) values ('317205','Operador de computador (inclusive microcomputador)');--.go
insert into codigoCBO (codigo, descricao) values ('811120','Operador de concentraçao');--.go
insert into codigoCBO (codigo, descricao) values ('761225','Operador de conicaleira');--.go
insert into codigoCBO (codigo, descricao) values ('832110','Operador de cortadeira de papel');--.go
insert into codigoCBO (codigo, descricao) values ('841305','Operador de cristalizaçao na refinaçao de açucar');--.go
insert into codigoCBO (codigo, descricao) values ('841456','Operador de câmaras frias');--.go
insert into codigoCBO (codigo, descricao) values ('372115','Operador de câmera de televisao');--.go
insert into codigoCBO (codigo, descricao) values ('773305','Operador de desempenadeira na usinagem convencional de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('821250','Operador de desgaseificaçao');--.go
insert into codigoCBO (codigo, descricao) values ('811615','Operador de destilaçao e subprodutos de coque');--.go
insert into codigoCBO (codigo, descricao) values ('831115','Operador de digestor de pasta para fabricaçao de papel');--.go
insert into codigoCBO (codigo, descricao) values ('782210','Operador de docagem');--.go
insert into codigoCBO (codigo, descricao) values ('782105','Operador de draga');--.go
insert into codigoCBO (codigo, descricao) values ('782220','Operador de empilhadeira');--.go
insert into codigoCBO (codigo, descricao) values ('811620','Operador de enfornamento e desenfornamento de coque');--.go
insert into codigoCBO (codigo, descricao) values ('761348','Operador de engomadeira de urdume');--.go
insert into codigoCBO (codigo, descricao) values ('773310','Operador de entalhadeira (usinagem de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('811425','Operador de equipamento de destilaçao de álcool');--.go
insert into codigoCBO (codigo, descricao) values ('723305','Operador de equipamento de secagem de pintura');--.go
insert into codigoCBO (codigo, descricao) values ('723115','Operador de equipamento para resfriamento');--.go
insert into codigoCBO (codigo, descricao) values ('722325','Operador de equipamentos de preparaçao de areia');--.go
insert into codigoCBO (codigo, descricao) values ('841310','Operador de equipamentos de refinaçao de açúcar (processo contínuo)');--.go
insert into codigoCBO (codigo, descricao) values ('715115','Operador de escavadeira');--.go
insert into codigoCBO (codigo, descricao) values ('821430','Operador de escória e sucata');--.go
insert into codigoCBO (codigo, descricao) values ('752420','Operador de esmaltadeira');--.go
insert into codigoCBO (codigo, descricao) values ('752425','Operador de espelhamento');--.go
insert into codigoCBO (codigo, descricao) values ('712125','Operador de espessador');--.go
insert into codigoCBO (codigo, descricao) values ('761351','Operador de espuladeira');--.go
insert into codigoCBO (codigo, descricao) values ('862140','Operador de estaçao de bombeamento');--.go
insert into codigoCBO (codigo, descricao) values ('862205','Operador de estaçao de captaçao, tratamento e distribuiçao de água');--.go
insert into codigoCBO (codigo, descricao) values ('862305','Operador de estaçao de tratamento de água e efluentes');--.go
insert into codigoCBO (codigo, descricao) values ('811430','Operador de evaporador na destilaçao');--.go
insert into codigoCBO (codigo, descricao) values ('811625','Operador de exaustor (coqueria)');--.go
insert into codigoCBO (codigo, descricao) values ('811310','Operador de exploraçao de petróleo');--.go
insert into codigoCBO (codigo, descricao) values ('373115','Operador de externa (rádio)');--.go
insert into codigoCBO (codigo, descricao) values ('841620','Operador de extraçao de café solúvel');--.go
insert into codigoCBO (codigo, descricao) values ('813115','Operador de extrusora (química, petroquímica e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('761230','Operador de filatório');--.go
insert into codigoCBO (codigo, descricao) values ('811315','Operador de filtro de secagem (mineraçao)');--.go
insert into codigoCBO (codigo, descricao) values ('811320','Operador de filtro de tambor rotativo (tratamentos químicos e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('811325','Operador de filtro-esteira (mineraçao)');--.go
insert into codigoCBO (codigo, descricao) values ('811330','Operador de filtro-prensa (tratamentos químicos e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('811335','Operador de filtros de parafina (tratamentos químicos e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('841805','Operador de forno (fabricaçao de paes, biscoitos e similares)');--.go
insert into codigoCBO (codigo, descricao) values ('516605','Operador de forno (serviços funerários)');--.go
insert into codigoCBO (codigo, descricao) values ('862310','Operador de forno de incineraçao no tratamento de água, efluentes e resíduos industriais');--.go
insert into codigoCBO (codigo, descricao) values ('723120','Operador de forno de tratamento térmico de metais');--.go
insert into codigoCBO (codigo, descricao) values ('773315','Operador de fresadora (usinagem de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('721410','Operador de fresadora com comando numérico');--.go
insert into codigoCBO (codigo, descricao) values ('373120','Operador de gravaçao de rádio');--.go
insert into codigoCBO (codigo, descricao) values ('766320','Operador de guilhotina (corte de papel)');--.go
insert into codigoCBO (codigo, descricao) values ('782110','Operador de guindaste (fixo)');--.go
insert into codigoCBO (codigo, descricao) values ('782115','Operador de guindaste móvel');--.go
insert into codigoCBO (codigo, descricao) values ('761425','Operador de impermeabilizador de tecidos');--.go
insert into codigoCBO (codigo, descricao) values ('623315','Operador de incubadora');--.go
insert into codigoCBO (codigo, descricao) values ('391215','Operador de inspeçao de qualidade');--.go
insert into codigoCBO (codigo, descricao) values ('862515','Operador de instalaçao de ar-condicionado');--.go
insert into codigoCBO (codigo, descricao) values ('862405','Operador de instalaçao de extraçao, processamento, envasamento e distribuiçao de gases');--.go
insert into codigoCBO (codigo, descricao) values ('862505','Operador de instalaçao de refrigeraçao');--.go
insert into codigoCBO (codigo, descricao) values ('821435','Operador de jato abrasivo');--.go
insert into codigoCBO (codigo, descricao) values ('712130','Operador de jig (minas)');--.go
insert into codigoCBO (codigo, descricao) values ('761235','Operador de laminadeira e reunideira');--.go
insert into codigoCBO (codigo, descricao) values ('821305','Operador de laminador');--.go
insert into codigoCBO (codigo, descricao) values ('821310','Operador de laminador de barras a frio');--.go
insert into codigoCBO (codigo, descricao) values ('821315','Operador de laminador de barras a quente');--.go
insert into codigoCBO (codigo, descricao) values ('821320','Operador de laminador de metais nao-ferrosos');--.go
insert into codigoCBO (codigo, descricao) values ('821325','Operador de laminador de tubos');--.go
insert into codigoCBO (codigo, descricao) values ('831120','Operador de lavagem e depuraçao de pasta para fabricaçao de papel');--.go
insert into codigoCBO (codigo, descricao) values ('724605','Operador de laços de cabos de aço');--.go
insert into codigoCBO (codigo, descricao) values ('731180','Operador de linha de montagem (aparelhos eletrônicos)');--.go
insert into codigoCBO (codigo, descricao) values ('731175','Operador de linha de montagem (aparelhos elétricos)');--.go
insert into codigoCBO (codigo, descricao) values ('773320','Operador de lixadeira (usinagem de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('721415','Operador de mandriladora com comando numérico');--.go
insert into codigoCBO (codigo, descricao) values ('717010','Operador de martelete');--.go
insert into codigoCBO (codigo, descricao) values ('761240','Operador de maçaroqueira');--.go
insert into codigoCBO (codigo, descricao) values ('412115','Operador de mensagens de telecomunicaçoes (correios)');--.go
insert into codigoCBO (codigo, descricao) values ('841315','Operador de moenda na fabricaçao de açúcar');--.go
insert into codigoCBO (codigo, descricao) values ('773330','Operador de molduradora (usinagem de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('782125','Operador de monta-cargas (construçao civil)');--.go
insert into codigoCBO (codigo, descricao) values ('821330','Operador de montagem de cilindros e mancais');--.go
insert into codigoCBO (codigo, descricao) values ('715130','Operador de motoniveladora');--.go
insert into codigoCBO (codigo, descricao) values ('711235','Operador de motoniveladora (extraçao de minerais sólidos)');--.go
insert into codigoCBO (codigo, descricao) values ('632120','Operador de motosserra');--.go
insert into codigoCBO (codigo, descricao) values ('773405','Operador de máquina bordatriz');--.go
insert into codigoCBO (codigo, descricao) values ('722220','Operador de máquina centrifugadora de fundiçao');--.go
insert into codigoCBO (codigo, descricao) values ('415130','Operador de máquina copiadora (exceto operador de gráfica rápida)');--.go
insert into codigoCBO (codigo, descricao) values ('711215','Operador de máquina cortadora (minas e pedreiras)');--.go
insert into codigoCBO (codigo, descricao) values ('715120','Operador de máquina de abrir valas');--.go
insert into codigoCBO (codigo, descricao) values ('724505','Operador de máquina de cilindrar chapas');--.go
insert into codigoCBO (codigo, descricao) values ('761354','Operador de máquina de cordoalha');--.go
insert into codigoCBO (codigo, descricao) values ('833120','Operador de máquina de cortar e dobrar papelao');--.go
insert into codigoCBO (codigo, descricao) values ('773410','Operador de máquina de cortina dágua (produçao de móveis)');--.go
insert into codigoCBO (codigo, descricao) values ('763320','Operador de máquina de costura de acabamento');--.go
insert into codigoCBO (codigo, descricao) values ('724510','Operador de máquina de dobrar chapas');--.go
insert into codigoCBO (codigo, descricao) values ('721205','Operador de máquina de eletroerosao');--.go
insert into codigoCBO (codigo, descricao) values ('784120','Operador de máquina de envasar líquidos');--.go
insert into codigoCBO (codigo, descricao) values ('784115','Operador de máquina de etiquetar');--.go
insert into codigoCBO (codigo, descricao) values ('711220','Operador de máquina de extraçao contínua (minas de carvao)');--.go
insert into codigoCBO (codigo, descricao) values ('842210','Operador de máquina de fabricar charutos e cigarrilhas');--.go
insert into codigoCBO (codigo, descricao) values ('842305','Operador de máquina de fabricar cigarros');--.go
insert into codigoCBO (codigo, descricao) values ('832115','Operador de máquina de fabricar papel  (fase úmida)');--.go
insert into codigoCBO (codigo, descricao) values ('832120','Operador de máquina de fabricar papel (fase seca)');--.go
insert into codigoCBO (codigo, descricao) values ('832125','Operador de máquina de fabricar papel e papelao');--.go
insert into codigoCBO (codigo, descricao) values ('811815','Operador de máquina de fabricaçao de cosméticos');--.go
insert into codigoCBO (codigo, descricao) values ('811820','Operador de máquina de fabricaçao de produtos de higiene e limpeza (sabao, sabonete, detergente, abs');--.go
insert into codigoCBO (codigo, descricao) values ('722225','Operador de máquina de fundir sob pressao');--.go
insert into codigoCBO (codigo, descricao) values ('761430','Operador de máquina de lavar fios e tecidos');--.go
insert into codigoCBO (codigo, descricao) values ('722330','Operador de máquina de moldar automatizada');--.go
insert into codigoCBO (codigo, descricao) values ('811805','Operador de máquina de produtos farmacêuticos');--.go
insert into codigoCBO (codigo, descricao) values ('831125','Operador de máquina de secar celulose');--.go
insert into codigoCBO (codigo, descricao) values ('821110','Operador de máquina de sinterizar');--.go
insert into codigoCBO (codigo, descricao) values ('823240','Operador de máquina de soprar vidro');--.go
insert into codigoCBO (codigo, descricao) values ('773415','Operador de máquina de usinagem de madeira (produçao em série)');--.go
insert into codigoCBO (codigo, descricao) values ('773325','Operador de máquina de usinagem madeira, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('721420','Operador de máquina eletroerosao, à fio, com comando numérico');--.go
insert into codigoCBO (codigo, descricao) values ('823245','Operador de máquina extrusora de varetas e tubos de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('773205','Operador de máquina intercaladora e placas (compensados)');--.go
insert into codigoCBO (codigo, descricao) values ('811110','Operador de máquina misturadeira (tratamentos químicos e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('711225','Operador de máquina perfuradora (minas e pedreiras)');--.go
insert into codigoCBO (codigo, descricao) values ('711230','Operador de máquina perfuratriz');--.go
insert into codigoCBO (codigo, descricao) values ('723230','Operador de máquina recobridora de arame');--.go
insert into codigoCBO (codigo, descricao) values ('782120','Operador de máquina rodoferroviária');--.go
insert into codigoCBO (codigo, descricao) values ('641010','Operador de máquinas de beneficiamento de produtos agrícolas');--.go
insert into codigoCBO (codigo, descricao) values ('715125','Operador de máquinas de construçao civil e mineraçao');--.go
insert into codigoCBO (codigo, descricao) values ('841815','Operador de máquinas de fabricaçao de chocolates e achocolatados');--.go
insert into codigoCBO (codigo, descricao) values ('841810','Operador de máquinas de fabricaçao de doces, salgados e massas alimentícias');--.go
insert into codigoCBO (codigo, descricao) values ('773510','Operador de máquinas de usinar madeira (CNC)');--.go
insert into codigoCBO (codigo, descricao) values ('762325','Operador de máquinas do acabamento de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('991115','Operador de máquinas especiais em conservaçao de via permanente (trilhos)');--.go
insert into codigoCBO (codigo, descricao) values ('862150','Operador de máquinas fixas, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('642010','Operador de máquinas florestais estáticas');--.go
insert into codigoCBO (codigo, descricao) values ('721210','Operador de máquinas operatrizes');--.go
insert into codigoCBO (codigo, descricao) values ('721215','Operador de máquinas-ferramenta convencionais');--.go
insert into codigoCBO (codigo, descricao) values ('253225','Operador de negócios');--.go
insert into codigoCBO (codigo, descricao) values ('761245','Operador de open-end');--.go
insert into codigoCBO (codigo, descricao) values ('811630','Operador de painel de controle');--.go
insert into codigoCBO (codigo, descricao) values ('811505','Operador de painel de controle (refinaçao de petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('761250','Operador de passador (fiaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('715140','Operador de pavimentadora (asfalto, concreto e materiais similares)');--.go
insert into codigoCBO (codigo, descricao) values ('712135','Operador de peneiras hidráulicas');--.go
insert into codigoCBO (codigo, descricao) values ('761255','Operador de penteadeira');--.go
insert into codigoCBO (codigo, descricao) values ('773335','Operador de plaina desengrossadeira');--.go
insert into codigoCBO (codigo, descricao) values ('782130','Operador de ponte rolante');--.go
insert into codigoCBO (codigo, descricao) values ('773420','Operador de prensa de alta freqüência na usinagem de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('833125','Operador de prensa de embutir papelao');--.go
insert into codigoCBO (codigo, descricao) values ('784125','Operador de prensa de enfardamento');--.go
insert into codigoCBO (codigo, descricao) values ('823250','Operador de prensa de moldar vidro');--.go
insert into codigoCBO (codigo, descricao) values ('841460','Operador de preparaçao de graos vegetais (óleos e gorduras)');--.go
insert into codigoCBO (codigo, descricao) values ('811635','Operador de preservaçao e controle térmico');--.go
insert into codigoCBO (codigo, descricao) values ('813120','Operador de processo (química, petroquímica e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('841115','Operador de processo de moagem');--.go
insert into codigoCBO (codigo, descricao) values ('766150','Operador de processo de tratamento de imagem');--.go
insert into codigoCBO (codigo, descricao) values ('811005','Operador de processos químicos e petroquímicos');--.go
insert into codigoCBO (codigo, descricao) values ('813125','Operador de produçao (química, petroquímica e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('374305','Operador de projetor cinematográfico');--.go
insert into codigoCBO (codigo, descricao) values ('715135','Operador de pá carregadeira');--.go
insert into codigoCBO (codigo, descricao) values ('782135','Operador de pórtico rolante');--.go
insert into codigoCBO (codigo, descricao) values ('861110','Operador de quadro de distribuiçao de energia elétrica');--.go
insert into codigoCBO (codigo, descricao) values ('761435','Operador de rameuse');--.go
insert into codigoCBO (codigo, descricao) values ('811640','Operador de reator de coque de petróleo');--.go
insert into codigoCBO (codigo, descricao) values ('861120','Operador de reator nuclear');--.go
insert into codigoCBO (codigo, descricao) values ('832135','Operador de rebobinadeira na fabricaçao de papel e papelao');--.go
insert into codigoCBO (codigo, descricao) values ('372205','Operador de rede de teleprocessamento');--.go
insert into codigoCBO (codigo, descricao) values ('811645','Operador de refrigeraçao (coqueria)');--.go
insert into codigoCBO (codigo, descricao) values ('862510','Operador de refrigeraçao com amônia');--.go
insert into codigoCBO (codigo, descricao) values ('721425','Operador de retificadora com comando numérico');--.go
insert into codigoCBO (codigo, descricao) values ('761260','Operador de retorcedeira');--.go
insert into codigoCBO (codigo, descricao) values ('422220','Operador de rádio-chamada');--.go
insert into codigoCBO (codigo, descricao) values ('811010','Operador de sala de controle de instalaçoes químicas, petroquímicas e afins');--.go
insert into codigoCBO (codigo, descricao) values ('711410','Operador de salina (sal marinho)');--.go
insert into codigoCBO (codigo, descricao) values ('711240','Operador de schutthecar');--.go
insert into codigoCBO (codigo, descricao) values ('773340','Operador de serras (usinagem de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('773110','Operador de serras no desdobramento de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('811650','Operador de sistema de reversao (coqueria)');--.go
insert into codigoCBO (codigo, descricao) values ('766145','Operador de sistemas de prova (analógico e digital)');--.go
insert into codigoCBO (codigo, descricao) values ('711305','Operador de sonda de percussao');--.go
insert into codigoCBO (codigo, descricao) values ('711310','Operador de sonda rotativa');--.go
insert into codigoCBO (codigo, descricao) values ('861205','Operador de subestaçao');--.go
insert into codigoCBO (codigo, descricao) values ('782140','Operador de talha elétrica');--.go
insert into codigoCBO (codigo, descricao) values ('782630','Operador de teleférico (passageiros)');--.go
insert into codigoCBO (codigo, descricao) values ('422305','Operador de telemarketing ativo');--.go
insert into codigoCBO (codigo, descricao) values ('422310','Operador de telemarketing ativo e receptivo');--.go
insert into codigoCBO (codigo, descricao) values ('422315','Operador de telemarketing receptivo');--.go
insert into codigoCBO (codigo, descricao) values ('422320','Operador de telemarketing técnico');--.go
insert into codigoCBO (codigo, descricao) values ('821440','Operador de tesoura mecânica e máquina de corte, no acabamento de chapas e metais');--.go
insert into codigoCBO (codigo, descricao) values ('725510','Operador de time de montagem');--.go
insert into codigoCBO (codigo, descricao) values ('773345','Operador de torno automático (usinagem de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('721430','Operador de torno com comando numérico');--.go
insert into codigoCBO (codigo, descricao) values ('811510','Operador de transferência e estocagem - na refinaçao do petróleo');--.go
insert into codigoCBO (codigo, descricao) values ('373125','Operador de transmissor de rádio');--.go
insert into codigoCBO (codigo, descricao) values ('342110','Operador de transporte multimodal');--.go
insert into codigoCBO (codigo, descricao) values ('841320','Operador de tratamento de calda na refinaçao de açúcar');--.go
insert into codigoCBO (codigo, descricao) values ('811215','Operador de tratamento químico de materiais radioativos');--.go
insert into codigoCBO (codigo, descricao) values ('711245','Operador de trator (minas e pedreiras)');--.go
insert into codigoCBO (codigo, descricao) values ('715145','Operador de trator de lâmina');--.go
insert into codigoCBO (codigo, descricao) values ('642015','Operador de trator florestal');--.go
insert into codigoCBO (codigo, descricao) values ('782605','Operador de trem de metrô');--.go
insert into codigoCBO (codigo, descricao) values ('415210','Operador de triagem e transbordo');--.go
insert into codigoCBO (codigo, descricao) values ('773350','Operador de tupia (usinagem de madeira)');--.go
insert into codigoCBO (codigo, descricao) values ('354810','Operador de turismo');--.go
insert into codigoCBO (codigo, descricao) values ('761357','Operador de urdideira');--.go
insert into codigoCBO (codigo, descricao) values ('721220','Operador de usinagem convencional por abrasao');--.go
insert into codigoCBO (codigo, descricao) values ('862155','Operador de utilidade (produçao e distribuiçao de vapor, gás, óleo, combustível, energia, oxigênio)');--.go
insert into codigoCBO (codigo, descricao) values ('722230','Operador de vazamento (lingotamento)');--.go
insert into codigoCBO (codigo, descricao) values ('781305','Operador de veículos subaquáticos controlados remotamente');--.go
insert into codigoCBO (codigo, descricao) values ('723235','Operador de zincagem (processo eletrolítico)');--.go
insert into codigoCBO (codigo, descricao) values ('821245','Operador de área de corrida');--.go
insert into codigoCBO (codigo, descricao) values ('373105','Operador de áudio de continuidade (rádio)');--.go
insert into codigoCBO (codigo, descricao) values ('954125','Operador eletromecânico');--.go
insert into codigoCBO (codigo, descricao) values ('761005','Operador polivalente da indústria têxtil');--.go
insert into codigoCBO (codigo, descricao) values ('374310','Operador-mantenedor de projetor cinematográfico');--.go
insert into codigoCBO (codigo, descricao) values ('354820','Organizador de evento');--.go
insert into codigoCBO (codigo, descricao) values ('239410','Orientador educacional');--.go
insert into codigoCBO (codigo, descricao) values ('223615','Ortoptista');--.go
insert into codigoCBO (codigo, descricao) values ('751125','Ourives');--.go
insert into codigoCBO (codigo, descricao) values ('352415','Ouvidor (ombudsman) do meio de comunicaçao');--.go
insert into codigoCBO (codigo, descricao) values ('724310','Oxicortador a mao e a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('723240','Oxidador');--.go
insert into codigoCBO (codigo, descricao) values ('848305','Padeiro');--.go
insert into codigoCBO (codigo, descricao) values ('768620','Paginador');--.go
insert into codigoCBO (codigo, descricao) values ('762335','Palecionador de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('213430','Paleontólogo');--.go
insert into codigoCBO (codigo, descricao) values ('376245','Palhaço');--.go
insert into codigoCBO (codigo, descricao) values ('351815','Papiloscopista policial');--.go
insert into codigoCBO (codigo, descricao) values ('516810','Paranormal');--.go
insert into codigoCBO (codigo, descricao) values ('515115','Parteira leiga');--.go
insert into codigoCBO (codigo, descricao) values ('763325','Passadeira de peças confeccionadas');--.go
insert into codigoCBO (codigo, descricao) values ('516325','Passador de roupas em geral');--.go
insert into codigoCBO (codigo, descricao) values ('516415','Passador de roupas, à mao');--.go
insert into codigoCBO (codigo, descricao) values ('761360','Passamaneiro a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('848205','Pasteurizador');--.go
insert into codigoCBO (codigo, descricao) values ('716515','Pastilheiro');--.go
insert into codigoCBO (codigo, descricao) values ('341220','Patrao de pesca de alto-mar');--.go
insert into codigoCBO (codigo, descricao) values ('341225','Patrao de pesca na navegaçao interior');--.go
insert into codigoCBO (codigo, descricao) values ('239415','Pedagogo');--.go
insert into codigoCBO (codigo, descricao) values ('516140','Pedicure');--.go
insert into codigoCBO (codigo, descricao) values ('715210','Pedreiro');--.go
insert into codigoCBO (codigo, descricao) values ('715215','Pedreiro (chaminés industriais)');--.go
insert into codigoCBO (codigo, descricao) values ('715220','Pedreiro (material refratário)');--.go
insert into codigoCBO (codigo, descricao) values ('715225','Pedreiro (mineraçao)');--.go
insert into codigoCBO (codigo, descricao) values ('992220','Pedreiro de conservaçao de vias permanentes (exceto trilhos)');--.go
insert into codigoCBO (codigo, descricao) values ('715230','Pedreiro de edificaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('325015','Perfumista');--.go
insert into codigoCBO (codigo, descricao) values ('252215','Perito contábil');--.go
insert into codigoCBO (codigo, descricao) values ('204105','Perito criminal');--.go
insert into codigoCBO (codigo, descricao) values ('631015','Pescador artesanal de lagostas');--.go
insert into codigoCBO (codigo, descricao) values ('631020','Pescador artesanal de peixes e camaroes');--.go
insert into codigoCBO (codigo, descricao) values ('631105','Pescador artesanal de água doce');--.go
insert into codigoCBO (codigo, descricao) values ('631205','Pescador industrial');--.go
insert into codigoCBO (codigo, descricao) values ('631210','Pescador profissional');--.go
insert into codigoCBO (codigo, descricao) values ('203305','Pesquisador de clínica médica');--.go
insert into codigoCBO (codigo, descricao) values ('203205','Pesquisador de engenharia civil');--.go
insert into codigoCBO (codigo, descricao) values ('203210','Pesquisador de engenharia e tecnologia (outras áreas da engenharia)');--.go
insert into codigoCBO (codigo, descricao) values ('203215','Pesquisador de engenharia elétrica e eletrônica');--.go
insert into codigoCBO (codigo, descricao) values ('203220','Pesquisador de engenharia mecânica');--.go
insert into codigoCBO (codigo, descricao) values ('203225','Pesquisador de engenharia metalúrgica, de minas e de materiais');--.go
insert into codigoCBO (codigo, descricao) values ('203230','Pesquisador de engenharia química');--.go
insert into codigoCBO (codigo, descricao) values ('203310','Pesquisador de medicina básica');--.go
insert into codigoCBO (codigo, descricao) values ('203005','Pesquisador em biologia ambiental');--.go
insert into codigoCBO (codigo, descricao) values ('203010','Pesquisador em biologia animal');--.go
insert into codigoCBO (codigo, descricao) values ('203015','Pesquisador em biologia de microorganismos e parasitas');--.go
insert into codigoCBO (codigo, descricao) values ('203020','Pesquisador em biologia humana');--.go
insert into codigoCBO (codigo, descricao) values ('203025','Pesquisador em biologia vegetal');--.go
insert into codigoCBO (codigo, descricao) values ('203405','Pesquisador em ciências agronômicas');--.go
insert into codigoCBO (codigo, descricao) values ('203105','Pesquisador em ciências da computaçao e informática');--.go
insert into codigoCBO (codigo, descricao) values ('203515','Pesquisador em ciências da educaçao');--.go
insert into codigoCBO (codigo, descricao) values ('203410','Pesquisador em ciências da pesca e aqüicultura');--.go
insert into codigoCBO (codigo, descricao) values ('203110','Pesquisador em ciências da terra e meio ambiente');--.go
insert into codigoCBO (codigo, descricao) values ('203415','Pesquisador em ciências da zootecnia');--.go
insert into codigoCBO (codigo, descricao) values ('203420','Pesquisador em ciências florestais');--.go
insert into codigoCBO (codigo, descricao) values ('203505','Pesquisador em ciências sociais e humanas');--.go
insert into codigoCBO (codigo, descricao) values ('203510','Pesquisador em economia');--.go
insert into codigoCBO (codigo, descricao) values ('203115','Pesquisador em física');--.go
insert into codigoCBO (codigo, descricao) values ('203520','Pesquisador em história');--.go
insert into codigoCBO (codigo, descricao) values ('203120','Pesquisador em matemática');--.go
insert into codigoCBO (codigo, descricao) values ('203315','Pesquisador em medicina veterinária');--.go
insert into codigoCBO (codigo, descricao) values ('201205','Pesquisador em metrologia');--.go
insert into codigoCBO (codigo, descricao) values ('203525','Pesquisador em psicologia');--.go
insert into codigoCBO (codigo, descricao) values ('203125','Pesquisador em química');--.go
insert into codigoCBO (codigo, descricao) values ('203320','Pesquisador em saúde coletiva');--.go
insert into codigoCBO (codigo, descricao) values ('213435','Petrógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('761366','Picotador de cartoes jacquard');--.go
insert into codigoCBO (codigo, descricao) values ('341120','Piloto agrícola');--.go
insert into codigoCBO (codigo, descricao) values ('341105','Piloto comercial (exceto linhas aéreas)');--.go
insert into codigoCBO (codigo, descricao) values ('341110','Piloto comercial de helicóptero (exceto linhas aéreas)');--.go
insert into codigoCBO (codigo, descricao) values ('215305','Piloto de aeronaves');--.go
insert into codigoCBO (codigo, descricao) values ('377135','Piloto de competiçao automobilística');--.go
insert into codigoCBO (codigo, descricao) values ('215310','Piloto de ensaios em vôo');--.go
insert into codigoCBO (codigo, descricao) values ('341230','Piloto fluvial');--.go
insert into codigoCBO (codigo, descricao) values ('723310','Pintor a pincel e rolo (exceto obras e estruturas metálicas)');--.go
insert into codigoCBO (codigo, descricao) values ('752430','Pintor de cerâmica, a  pincel');--.go
insert into codigoCBO (codigo, descricao) values ('723315','Pintor de estruturas metálicas');--.go
insert into codigoCBO (codigo, descricao) values ('768625','Pintor de letreiros');--.go
insert into codigoCBO (codigo, descricao) values ('716610','Pintor de obras');--.go
insert into codigoCBO (codigo, descricao) values ('723320','Pintor de veículos (fabricaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('991315','Pintor de veículos (reparaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('723325','Pintor por imersao');--.go
insert into codigoCBO (codigo, descricao) values ('723330','Pintor, a  pistola (exceto obras e estruturas metálicas)');--.go
insert into codigoCBO (codigo, descricao) values ('524310','Pipoqueiro ambulante');--.go
insert into codigoCBO (codigo, descricao) values ('812105','Pirotécnico');--.go
insert into codigoCBO (codigo, descricao) values ('513610','Pizzaiolo');--.go
insert into codigoCBO (codigo, descricao) values ('391120','Planejista');--.go
insert into codigoCBO (codigo, descricao) values ('711325','Plataformista (petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('717015','Poceiro (edificaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('322110','Podólogo');--.go
insert into codigoCBO (codigo, descricao) values ('261525','Poeta');--.go
insert into codigoCBO (codigo, descricao) values ('517210','Policial rodoviário federal');--.go
insert into codigoCBO (codigo, descricao) values ('721325','Polidor de metais');--.go
insert into codigoCBO (codigo, descricao) values ('712220','Polidor de pedras');--.go
insert into codigoCBO (codigo, descricao) values ('517405','Porteiro (hotel)');--.go
insert into codigoCBO (codigo, descricao) values ('517410','Porteiro de edifícios');--.go
insert into codigoCBO (codigo, descricao) values ('517415','Porteiro de locais de diversao');--.go
insert into codigoCBO (codigo, descricao) values ('010305','Praça da Aeronáutica');--.go
insert into codigoCBO (codigo, descricao) values ('010315','Praça da Marinha');--.go
insert into codigoCBO (codigo, descricao) values ('010310','Praça do Exército');--.go
insert into codigoCBO (codigo, descricao) values ('111250','Prefeito');--.go
insert into codigoCBO (codigo, descricao) values ('762330','Prensador de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('841464','Prensador de frutas (exceto oleaginosas)');--.go
insert into codigoCBO (codigo, descricao) values ('724515','Prensista (operador de prensa)');--.go
insert into codigoCBO (codigo, descricao) values ('773210','Prensista de aglomerados');--.go
insert into codigoCBO (codigo, descricao) values ('773215','Prensista de compensados');--.go
insert into codigoCBO (codigo, descricao) values ('823130','Preparador de aditivos');--.go
insert into codigoCBO (codigo, descricao) values ('773220','Preparador de aglomerantes');--.go
insert into codigoCBO (codigo, descricao) values ('224115','Preparador de atleta');--.go
insert into codigoCBO (codigo, descricao) values ('823120','Preparador de barbotina');--.go
insert into codigoCBO (codigo, descricao) values ('764115','Preparador de calçados');--.go
insert into codigoCBO (codigo, descricao) values ('762340','Preparador de couros curtidos');--.go
insert into codigoCBO (codigo, descricao) values ('823125','Preparador de esmaltes (cerâmica)');--.go
insert into codigoCBO (codigo, descricao) values ('724220','Preparador de estruturas metálicas');--.go
insert into codigoCBO (codigo, descricao) values ('842205','Preparador de fumo na fabricaçao de charutos');--.go
insert into codigoCBO (codigo, descricao) values ('823105','Preparador de massa (fabricaçao de abrasivos)');--.go
insert into codigoCBO (codigo, descricao) values ('823110','Preparador de massa (fabricaçao de vidro)');--.go
insert into codigoCBO (codigo, descricao) values ('823115','Preparador de massa de argila');--.go
insert into codigoCBO (codigo, descricao) values ('766325','Preparador de matrizes de corte e vinco');--.go
insert into codigoCBO (codigo, descricao) values ('842105','Preparador de melado e essência de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('721225','Preparador de máquinas-ferramenta');--.go
insert into codigoCBO (codigo, descricao) values ('722235','Preparador de panelas (lingotamento)');--.go
insert into codigoCBO (codigo, descricao) values ('841468','Preparador de raçoes');--.go
insert into codigoCBO (codigo, descricao) values ('764120','Preparador de solas e palmilhas');--.go
insert into codigoCBO (codigo, descricao) values ('821445','Preparador de sucata e aparas');--.go
insert into codigoCBO (codigo, descricao) values ('311715','Preparador de tintas');--.go
insert into codigoCBO (codigo, descricao) values ('311720','Preparador de tintas (fábrica de tecidos)');--.go
insert into codigoCBO (codigo, descricao) values ('224120','Preparador físico');--.go
insert into codigoCBO (codigo, descricao) values ('111205','Presidente da República');--.go
insert into codigoCBO (codigo, descricao) values ('215210','Primeiro oficial de máquinas da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('020305','Primeiro tenente de polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('842110','Processador de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('241210','Procurador autárquico');--.go
insert into codigoCBO (codigo, descricao) values ('242410','Procurador da assistência judiciária');--.go
insert into codigoCBO (codigo, descricao) values ('241215','Procurador da fazenda nacional');--.go
insert into codigoCBO (codigo, descricao) values ('242205','Procurador da república');--.go
insert into codigoCBO (codigo, descricao) values ('242210','Procurador de justiça');--.go
insert into codigoCBO (codigo, descricao) values ('242215','Procurador de justiça militar');--.go
insert into codigoCBO (codigo, descricao) values ('241220','Procurador do estado');--.go
insert into codigoCBO (codigo, descricao) values ('241225','Procurador do município');--.go
insert into codigoCBO (codigo, descricao) values ('242220','Procurador do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('241230','Procurador federal');--.go
insert into codigoCBO (codigo, descricao) values ('241235','Procurador fundacional');--.go
insert into codigoCBO (codigo, descricao) values ('242225','Procurador regional da república');--.go
insert into codigoCBO (codigo, descricao) values ('242230','Procurador regional do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('611005','Produtor agropecuário, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('612005','Produtor agrícola polivalente');--.go
insert into codigoCBO (codigo, descricao) values ('262110','Produtor cinematográfico');--.go
insert into codigoCBO (codigo, descricao) values ('612705','Produtor da cultura de amendoim');--.go
insert into codigoCBO (codigo, descricao) values ('612710','Produtor da cultura de canola');--.go
insert into codigoCBO (codigo, descricao) values ('612715','Produtor da cultura de coco-da-baia');--.go
insert into codigoCBO (codigo, descricao) values ('612720','Produtor da cultura de dendê');--.go
insert into codigoCBO (codigo, descricao) values ('612725','Produtor da cultura de girassol');--.go
insert into codigoCBO (codigo, descricao) values ('612730','Produtor da cultura de linho');--.go
insert into codigoCBO (codigo, descricao) values ('612735','Produtor da cultura de mamona');--.go
insert into codigoCBO (codigo, descricao) values ('612740','Produtor da cultura de soja');--.go
insert into codigoCBO (codigo, descricao) values ('612205','Produtor de algodao');--.go
insert into codigoCBO (codigo, descricao) values ('612105','Produtor de arroz');--.go
insert into codigoCBO (codigo, descricao) values ('612610','Produtor de cacau');--.go
insert into codigoCBO (codigo, descricao) values ('612110','Produtor de cana-de-açúcar');--.go
insert into codigoCBO (codigo, descricao) values ('612115','Produtor de cereais de inverno');--.go
insert into codigoCBO (codigo, descricao) values ('612210','Produtor de curauá');--.go
insert into codigoCBO (codigo, descricao) values ('612615','Produtor de erva-mate');--.go
insert into codigoCBO (codigo, descricao) values ('612805','Produtor de especiarias');--.go
insert into codigoCBO (codigo, descricao) values ('612510','Produtor de espécies frutíferas rasteiras');--.go
insert into codigoCBO (codigo, descricao) values ('612515','Produtor de espécies frutíferas trepadeiras');--.go
insert into codigoCBO (codigo, descricao) values ('612405','Produtor de flores de corte');--.go
insert into codigoCBO (codigo, descricao) values ('612410','Produtor de flores em vaso');--.go
insert into codigoCBO (codigo, descricao) values ('612415','Produtor de forraçoes');--.go
insert into codigoCBO (codigo, descricao) values ('612620','Produtor de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('612120','Produtor de gramíneas forrageiras');--.go
insert into codigoCBO (codigo, descricao) values ('612625','Produtor de guaraná');--.go
insert into codigoCBO (codigo, descricao) values ('612215','Produtor de juta');--.go
insert into codigoCBO (codigo, descricao) values ('612125','Produtor de milho e sorgo');--.go
insert into codigoCBO (codigo, descricao) values ('612810','Produtor de plantas aromáticas e medicinais');--.go
insert into codigoCBO (codigo, descricao) values ('612420','Produtor de plantas ornamentais');--.go
insert into codigoCBO (codigo, descricao) values ('612220','Produtor de rami');--.go
insert into codigoCBO (codigo, descricao) values ('262115','Produtor de rádio');--.go
insert into codigoCBO (codigo, descricao) values ('612225','Produtor de sisal');--.go
insert into codigoCBO (codigo, descricao) values ('262120','Produtor de teatro');--.go
insert into codigoCBO (codigo, descricao) values ('262125','Produtor de televisao');--.go
insert into codigoCBO (codigo, descricao) values ('261130','Produtor de texto');--.go
insert into codigoCBO (codigo, descricao) values ('612505','Produtor de árvores frutíferas');--.go
insert into codigoCBO (codigo, descricao) values ('612320','Produtor na olericultura de frutos e sementes');--.go
insert into codigoCBO (codigo, descricao) values ('612305','Produtor na olericultura de legumes');--.go
insert into codigoCBO (codigo, descricao) values ('612310','Produtor na olericultura de raízes, bulbos e tubérculos');--.go
insert into codigoCBO (codigo, descricao) values ('612315','Produtor na olericultura de talos, folhas e flores');--.go
insert into codigoCBO (codigo, descricao) values ('631415','Proeiro');--.go
insert into codigoCBO (codigo, descricao) values ('231205','Professor da  educaçao de jovens e adultos do ensino fundamental (primeira a quarta série)');--.go
insert into codigoCBO (codigo, descricao) values ('233105','Professor da área de meio ambiente');--.go
insert into codigoCBO (codigo, descricao) values ('234750','Professor de Jornalismo');--.go
insert into codigoCBO (codigo, descricao) values ('234810','Professor de administraçao');--.go
insert into codigoCBO (codigo, descricao) values ('239205','Professor de alunos com deficiência auditiva e surdos');--.go
insert into codigoCBO (codigo, descricao) values ('239210','Professor de alunos com deficiência física');--.go
insert into codigoCBO (codigo, descricao) values ('239215','Professor de alunos com deficiência mental');--.go
insert into codigoCBO (codigo, descricao) values ('239220','Professor de alunos com deficiência múltipla');--.go
insert into codigoCBO (codigo, descricao) values ('239225','Professor de alunos com deficiência visual');--.go
insert into codigoCBO (codigo, descricao) values ('234705','Professor de antropologia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('233215','Professor de aprendizagem e treinamento comercial');--.go
insert into codigoCBO (codigo, descricao) values ('234305','Professor de arquitetura');--.go
insert into codigoCBO (codigo, descricao) values ('234710','Professor de arquivologia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234905','Professor de artes do espetáculo no ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('232105','Professor de artes no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234910','Professor de artes visuais no ensino superior (artes plásticas e multimídia)');--.go
insert into codigoCBO (codigo, descricao) values ('234215','Professor de astronomia (ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('234715','Professor de biblioteconomia do ensio superior');--.go
insert into codigoCBO (codigo, descricao) values ('232110','Professor de biologia no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234720','Professor de ciência política do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234405','Professor de ciências biológicas do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('231305','Professor de ciências exatas e naturais do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('234120','Professor de computaçao (no ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('234725','Professor de comunicaçao social do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234815','Professor de contabilidade');--.go
insert into codigoCBO (codigo, descricao) values ('262830','Professor de dança');--.go
insert into codigoCBO (codigo, descricao) values ('233110','Professor de desenho técnico');--.go
insert into codigoCBO (codigo, descricao) values ('234730','Professor de direito do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('232115','Professor de disciplinas pedagógicas no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234805','Professor de economia');--.go
insert into codigoCBO (codigo, descricao) values ('231310','Professor de educaçao artística do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('231315','Professor de educaçao física do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('232120','Professor de educaçao física no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234410','Professor de educaçao física no ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234415','Professor de enfermagem do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234310','Professor de engenharia');--.go
insert into codigoCBO (codigo, descricao) values ('234505','Professor de ensino superior na área de didática');--.go
insert into codigoCBO (codigo, descricao) values ('234510','Professor de ensino superior na área de orientaçao educacional');--.go
insert into codigoCBO (codigo, descricao) values ('234515','Professor de ensino superior na área de pesquisa educacional');--.go
insert into codigoCBO (codigo, descricao) values ('234520','Professor de ensino superior na área de prática de ensino');--.go
insert into codigoCBO (codigo, descricao) values ('234115','Professor de estatística (no ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('234420','Professor de farmácia e bioquímica');--.go
insert into codigoCBO (codigo, descricao) values ('234676','Professor de filologia e crítica textual');--.go
insert into codigoCBO (codigo, descricao) values ('234735','Professor de filosofia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('232125','Professor de filosofia no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234425','Professor de fisioterapia');--.go
insert into codigoCBO (codigo, descricao) values ('234430','Professor de fonoaudiologia');--.go
insert into codigoCBO (codigo, descricao) values ('234205','Professor de física (ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('232130','Professor de física no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234315','Professor de geofísica');--.go
insert into codigoCBO (codigo, descricao) values ('231320','Professor de geografia do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('234740','Professor de geografia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('232135','Professor de geografia no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234320','Professor de geologia');--.go
insert into codigoCBO (codigo, descricao) values ('231325','Professor de história do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('234745','Professor de história do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('232140','Professor de história no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234672','Professor de lingüística e lingüística aplicada');--.go
insert into codigoCBO (codigo, descricao) values ('234636','Professor de literatura alema');--.go
insert into codigoCBO (codigo, descricao) values ('234628','Professor de literatura brasileira');--.go
insert into codigoCBO (codigo, descricao) values ('234640','Professor de literatura comparada');--.go
insert into codigoCBO (codigo, descricao) values ('234660','Professor de literatura de línguas estrangeiras modernas');--.go
insert into codigoCBO (codigo, descricao) values ('234644','Professor de literatura espanhola');--.go
insert into codigoCBO (codigo, descricao) values ('234648','Professor de literatura francesa');--.go
insert into codigoCBO (codigo, descricao) values ('234652','Professor de literatura inglesa');--.go
insert into codigoCBO (codigo, descricao) values ('234656','Professor de literatura italiana');--.go
insert into codigoCBO (codigo, descricao) values ('234632','Professor de literatura portuguesa');--.go
insert into codigoCBO (codigo, descricao) values ('234604','Professor de língua alema');--.go
insert into codigoCBO (codigo, descricao) values ('232145','Professor de língua e literatura brasileira no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234620','Professor de língua espanhola');--.go
insert into codigoCBO (codigo, descricao) values ('231330','Professor de língua estrangeira moderna do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('232150','Professor de língua estrangeira moderna no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234612','Professor de língua francesa');--.go
insert into codigoCBO (codigo, descricao) values ('234616','Professor de língua inglesa');--.go
insert into codigoCBO (codigo, descricao) values ('234608','Professor de língua italiana');--.go
insert into codigoCBO (codigo, descricao) values ('234624','Professor de língua portuguesa');--.go
insert into codigoCBO (codigo, descricao) values ('231335','Professor de língua portuguesa do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('234668','Professor de línguas estrangeiras modernas');--.go
insert into codigoCBO (codigo, descricao) values ('234105','Professor de matemática aplicada (no ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('231340','Professor de matemática do ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('232155','Professor de matemática no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234110','Professor de matemática pura (no ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('234435','Professor de medicina');--.go
insert into codigoCBO (codigo, descricao) values ('234440','Professor de medicina veterinária');--.go
insert into codigoCBO (codigo, descricao) values ('234755','Professor de museologia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234915','Professor de música no ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234445','Professor de nutriçao');--.go
insert into codigoCBO (codigo, descricao) values ('331105','Professor de nível médio na educaçao infantil');--.go
insert into codigoCBO (codigo, descricao) values ('331205','Professor de nível médio no ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('331305','Professor de nível médio no ensino profissionalizante');--.go
insert into codigoCBO (codigo, descricao) values ('231210','Professor de nível superior do ensino fundamental (primeira a quarta série)');--.go
insert into codigoCBO (codigo, descricao) values ('231105','Professor de nível superior na educaçao infantil (quatro a seis anos)');--.go
insert into codigoCBO (codigo, descricao) values ('231110','Professor de nível superior na educaçao infantil (zero a três anos)');--.go
insert into codigoCBO (codigo, descricao) values ('234450','Professor de odontologia');--.go
insert into codigoCBO (codigo, descricao) values ('234664','Professor de outras línguas e literaturas');--.go
insert into codigoCBO (codigo, descricao) values ('234125','Professor de pesquisa operacional (no ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('234760','Professor de psicologia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('232160','Professor de psicologia no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234210','Professor de química (ensino superior)');--.go
insert into codigoCBO (codigo, descricao) values ('232165','Professor de química no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('234680','Professor de semiótica');--.go
insert into codigoCBO (codigo, descricao) values ('234765','Professor de serviço social do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('234770','Professor de sociologia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('232170','Professor de sociologia no ensino médio');--.go
insert into codigoCBO (codigo, descricao) values ('233135','Professor de tecnologia e cálculo técnico');--.go
insert into codigoCBO (codigo, descricao) values ('234684','Professor de teoria da literatura');--.go
insert into codigoCBO (codigo, descricao) values ('234455','Professor de terapia ocupacional');--.go
insert into codigoCBO (codigo, descricao) values ('233115','Professor de técnicas agrícolas');--.go
insert into codigoCBO (codigo, descricao) values ('233120','Professor de técnicas comerciais e secretariais');--.go
insert into codigoCBO (codigo, descricao) values ('233125','Professor de técnicas de enfermagem');--.go
insert into codigoCBO (codigo, descricao) values ('239420','Professor de técnicas e recursos audiovisuais');--.go
insert into codigoCBO (codigo, descricao) values ('233130','Professor de técnicas industriais');--.go
insert into codigoCBO (codigo, descricao) values ('234460','Professor de zootecnia do ensino superior');--.go
insert into codigoCBO (codigo, descricao) values ('233220','Professor instrutor de ensino e aprendizagem agroflorestal');--.go
insert into codigoCBO (codigo, descricao) values ('233225','Professor instrutor de ensino e aprendizagem em serviços');--.go
insert into codigoCBO (codigo, descricao) values ('332105','Professor leigo no ensino fundamental');--.go
insert into codigoCBO (codigo, descricao) values ('332205','Professor prático no ensino profissionalizante');--.go
insert into codigoCBO (codigo, descricao) values ('333115','Professores de cursos livres');--.go
insert into codigoCBO (codigo, descricao) values ('377140','Profissional de Atletismo');--.go
insert into codigoCBO (codigo, descricao) values ('519805','Profissional do sexo');--.go
insert into codigoCBO (codigo, descricao) values ('317105','Programador de internet');--.go
insert into codigoCBO (codigo, descricao) values ('317120','Programador de multimídia');--.go
insert into codigoCBO (codigo, descricao) values ('317115','Programador de máquinas - ferramenta com comando numérico');--.go
insert into codigoCBO (codigo, descricao) values ('317110','Programador de sistemas de informaçao');--.go
insert into codigoCBO (codigo, descricao) values ('766155','Programador visual gráfico');--.go
insert into codigoCBO (codigo, descricao) values ('318805','Projetista de móveis');--.go
insert into codigoCBO (codigo, descricao) values ('374135','Projetista de sistemas de áudio');--.go
insert into codigoCBO (codigo, descricao) values ('374120','Projetista de som');--.go
insert into codigoCBO (codigo, descricao) values ('242235','Promotor de justiça');--.go
insert into codigoCBO (codigo, descricao) values ('521115','Promotor de vendas');--.go
insert into codigoCBO (codigo, descricao) values ('354130','Promotor de vendas especializado');--.go
insert into codigoCBO (codigo, descricao) values ('322410','Protético dentário');--.go
insert into codigoCBO (codigo, descricao) values ('215145','Prático de portos da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('251550','Psicanalista');--.go
insert into codigoCBO (codigo, descricao) values ('239425','Psicopedagogo');--.go
insert into codigoCBO (codigo, descricao) values ('251510','Psicólogo clínico');--.go
insert into codigoCBO (codigo, descricao) values ('251515','Psicólogo do esporte');--.go
insert into codigoCBO (codigo, descricao) values ('251540','Psicólogo do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('251535','Psicólogo do trânsito');--.go
insert into codigoCBO (codigo, descricao) values ('251505','Psicólogo educacional');--.go
insert into codigoCBO (codigo, descricao) values ('251520','Psicólogo hospitalar');--.go
insert into codigoCBO (codigo, descricao) values ('251525','Psicólogo jurídico');--.go
insert into codigoCBO (codigo, descricao) values ('251530','Psicólogo social');--.go
insert into codigoCBO (codigo, descricao) values ('377145','Pugilista');--.go
insert into codigoCBO (codigo, descricao) values ('848210','Queijeiro na fabricaçao de laticínio');--.go
insert into codigoCBO (codigo, descricao) values ('322115','Quiropraxista');--.go
insert into codigoCBO (codigo, descricao) values ('213205','Químico');--.go
insert into codigoCBO (codigo, descricao) values ('213210','Químico industrial');--.go
insert into codigoCBO (codigo, descricao) values ('762125','Rachador de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('372210','Radiotelegrafista');--.go
insert into codigoCBO (codigo, descricao) values ('632010','Raizeiro');--.go
insert into codigoCBO (codigo, descricao) values ('762220','Rebaixador de couros');--.go
insert into codigoCBO (codigo, descricao) values ('821450','Rebarbador de metal');--.go
insert into codigoCBO (codigo, descricao) values ('724215','Rebitador a  martelo pneumático');--.go
insert into codigoCBO (codigo, descricao) values ('724230','Rebitador, a  mao');--.go
insert into codigoCBO (codigo, descricao) values ('421205','Recebedor de apostas (loteria)');--.go
insert into codigoCBO (codigo, descricao) values ('421210','Recebedor de apostas (turfe)');--.go
insert into codigoCBO (codigo, descricao) values ('422125','Recepcionista de banco');--.go
insert into codigoCBO (codigo, descricao) values ('519945','Recepcionista de casas de espetáculos');--.go
insert into codigoCBO (codigo, descricao) values ('422110','Recepcionista de consultório médico ou dentário');--.go
insert into codigoCBO (codigo, descricao) values ('422120','Recepcionista de hotel');--.go
insert into codigoCBO (codigo, descricao) values ('422115','Recepcionista de seguro saúde');--.go
insert into codigoCBO (codigo, descricao) values ('422105','Recepcionista, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('371410','Recreador');--.go
insert into codigoCBO (codigo, descricao) values ('371405','Recreador de acantonamento');--.go
insert into codigoCBO (codigo, descricao) values ('821335','Recuperador de guias e cilindros');--.go
insert into codigoCBO (codigo, descricao) values ('253110','Redator de publicidade');--.go
insert into codigoCBO (codigo, descricao) values ('261530','Redator de textos técnicos');--.go
insert into codigoCBO (codigo, descricao) values ('768120','Redeiro');--.go
insert into codigoCBO (codigo, descricao) values ('631420','Redeiro (pesca)');--.go
insert into codigoCBO (codigo, descricao) values ('841210','Refinador de sal');--.go
insert into codigoCBO (codigo, descricao) values ('841472','Refinador de óleo e gordura');--.go
insert into codigoCBO (codigo, descricao) values ('253105','Relaçoes públicas');--.go
insert into codigoCBO (codigo, descricao) values ('741120','Relojoeiro (fabricaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('741125','Relojoeiro (reparaçao)');--.go
insert into codigoCBO (codigo, descricao) values ('761363','Remetedor de fios');--.go
insert into codigoCBO (codigo, descricao) values ('731330','Reparador de aparelhos de telecomunicaçoes em laboratório');--.go
insert into codigoCBO (codigo, descricao) values ('954205','Reparador de aparelhos eletrodomésticos (exceto imagem e som)');--.go
insert into codigoCBO (codigo, descricao) values ('954305','Reparador de equipamentos de escritório');--.go
insert into codigoCBO (codigo, descricao) values ('915405','Reparador de equipamentos fotográficos');--.go
insert into codigoCBO (codigo, descricao) values ('915210','Reparador de instrumentos musicais');--.go
insert into codigoCBO (codigo, descricao) values ('954210','Reparador de rádio, TV e som');--.go
insert into codigoCBO (codigo, descricao) values ('521125','Repositor de mercadorias');--.go
insert into codigoCBO (codigo, descricao) values ('354705','Representante comercial autônomo');--.go
insert into codigoCBO (codigo, descricao) values ('261135','Repórter (exclusive rádio e televisao)');--.go
insert into codigoCBO (codigo, descricao) values ('261730','Repórter de rádio e televisao');--.go
insert into codigoCBO (codigo, descricao) values ('261820','Repóter fotográfico');--.go
insert into codigoCBO (codigo, descricao) values ('915205','Restaurador de instrumentos musicais (exceto cordas arcadas)');--.go
insert into codigoCBO (codigo, descricao) values ('768710','Restaurador de livros');--.go
insert into codigoCBO (codigo, descricao) values ('848525','Retalhador de carne');--.go
insert into codigoCBO (codigo, descricao) values ('766415','Revelador de filmes fotográficos, em cores');--.go
insert into codigoCBO (codigo, descricao) values ('766410','Revelador de filmes fotográficos, em preto e branco');--.go
insert into codigoCBO (codigo, descricao) values ('716615','Revestidor de interiores (papel, material plástico e emborrachados)');--.go
insert into codigoCBO (codigo, descricao) values ('716110','Revestidor de superfícies de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('261140','Revisor');--.go
insert into codigoCBO (codigo, descricao) values ('761810','Revisor de fios (produçao têxtil)');--.go
insert into codigoCBO (codigo, descricao) values ('761815','Revisor de tecidos acabados');--.go
insert into codigoCBO (codigo, descricao) values ('761820','Revisor de tecidos crus');--.go
insert into codigoCBO (codigo, descricao) values ('724225','Riscador de estruturas metálicas');--.go
insert into codigoCBO (codigo, descricao) values ('763120','Riscador de roupas');--.go
insert into codigoCBO (codigo, descricao) values ('514115','Sacristao');--.go
insert into codigoCBO (codigo, descricao) values ('848110','Salgador de alimentos');--.go
insert into codigoCBO (codigo, descricao) values ('848115','Salsicheiro (fabricaçao de lingüiça, salsicha e produtos similares)');--.go
insert into codigoCBO (codigo, descricao) values ('517115','Salva-vidas');--.go
insert into codigoCBO (codigo, descricao) values ('768320','Sapateiro (calçados sob medida)');--.go
insert into codigoCBO (codigo, descricao) values ('031110','Sargento bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('021110','Sargento da policia militar');--.go
insert into codigoCBO (codigo, descricao) values ('772115','Secador de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('252305','Secretária  executiva');--.go
insert into codigoCBO (codigo, descricao) values ('252315','Secretária trilíngüe');--.go
insert into codigoCBO (codigo, descricao) values ('252310','Secretário  bilíngüe');--.go
insert into codigoCBO (codigo, descricao) values ('111220','Secretário - Executivo');--.go
insert into codigoCBO (codigo, descricao) values ('215215','Segundo oficial de máquinas da marinha mercante');--.go
insert into codigoCBO (codigo, descricao) values ('020310','Segundo tenente de polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('768325','Seleiro');--.go
insert into codigoCBO (codigo, descricao) values ('111105','Senador');--.go
insert into codigoCBO (codigo, descricao) values ('516610','Sepultador');--.go
insert into codigoCBO (codigo, descricao) values ('613420','Sericultor');--.go
insert into codigoCBO (codigo, descricao) values ('632205','Seringueiro');--.go
insert into codigoCBO (codigo, descricao) values ('773115','Serrador de bordas no desdobramento de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('773120','Serrador de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('773125','Serrador de madeira (serra circular múltipla)');--.go
insert into codigoCBO (codigo, descricao) values ('773130','Serrador de madeira (serra de fita múltipla)');--.go
insert into codigoCBO (codigo, descricao) values ('724440','Serralheiro');--.go
insert into codigoCBO (codigo, descricao) values ('717020','Servente de obras');--.go
insert into codigoCBO (codigo, descricao) values ('623325','Sexador');--.go
insert into codigoCBO (codigo, descricao) values ('782145','Sinaleiro (ponte-rolante)');--.go
insert into codigoCBO (codigo, descricao) values ('251120','Sociólogo');--.go
insert into codigoCBO (codigo, descricao) values ('031210','Soldado bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('021210','Soldado da polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('724315','Soldador');--.go
insert into codigoCBO (codigo, descricao) values ('724320','Soldador a  oxigás');--.go
insert into codigoCBO (codigo, descricao) values ('991120','Soldador aluminotérmico em conservaçao de trilhos');--.go
insert into codigoCBO (codigo, descricao) values ('724325','Soldador elétrico');--.go
insert into codigoCBO (codigo, descricao) values ('711315','Sondador (poços de petróleo e gás)');--.go
insert into codigoCBO (codigo, descricao) values ('711320','Sondador de poços (exceto de petróleo e gás)');--.go
insert into codigoCBO (codigo, descricao) values ('821255','Soprador de convertedor');--.go
insert into codigoCBO (codigo, descricao) values ('752115','Soprador de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('242240','Subprocurador de justiça militar');--.go
insert into codigoCBO (codigo, descricao) values ('242245','Subprocurador-geral da república');--.go
insert into codigoCBO (codigo, descricao) values ('242250','Subprocurador-geral do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('031105','Subtenente bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('021105','Subtenente da policia militar');--.go
insert into codigoCBO (codigo, descricao) values ('215220','Superintendente técnico no transporte aquaviário');--.go
insert into codigoCBO (codigo, descricao) values ('760405','Supervisor  (indústria de calçados e artefatos de couro)');--.go
insert into codigoCBO (codigo, descricao) values ('410105','Supervisor administrativo');--.go
insert into codigoCBO (codigo, descricao) values ('342540','Supervisor da administraçao de aeroportos');--.go
insert into codigoCBO (codigo, descricao) values ('630105','Supervisor da aqüicultura');--.go
insert into codigoCBO (codigo, descricao) values ('760505','Supervisor da confecçao de artefatos de tecidos, couros e afins');--.go
insert into codigoCBO (codigo, descricao) values ('840110','Supervisor da indústria de bebidas');--.go
insert into codigoCBO (codigo, descricao) values ('840115','Supervisor da indústria de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('750205','Supervisor da indústria de minerais nao metálicos (exceto os derivados de petróleo e carvao)');--.go
insert into codigoCBO (codigo, descricao) values ('910205','Supervisor da manutençao e reparaçao de veículos leves');--.go
insert into codigoCBO (codigo, descricao) values ('910210','Supervisor da manutençao e reparaçao de veículos pesados');--.go
insert into codigoCBO (codigo, descricao) values ('740105','Supervisor da mecânica de precisao');--.go
insert into codigoCBO (codigo, descricao) values ('630110','Supervisor da área florestal');--.go
insert into codigoCBO (codigo, descricao) values ('760605','Supervisor das artes gráficas  (indústria editorial e gráfica)');--.go
insert into codigoCBO (codigo, descricao) values ('410205','Supervisor de almoxarifado');--.go
insert into codigoCBO (codigo, descricao) values ('510115','Supervisor de andar');--.go
insert into codigoCBO (codigo, descricao) values ('710105','Supervisor de apoio operacional na mineraçao');--.go
insert into codigoCBO (codigo, descricao) values ('510305','Supervisor de bombeiros');--.go
insert into codigoCBO (codigo, descricao) values ('420105','Supervisor de caixas e bilheteiros (exceto caixa de banco)');--.go
insert into codigoCBO (codigo, descricao) values ('342315','Supervisor de carga e descarga');--.go
insert into codigoCBO (codigo, descricao) values ('420110','Supervisor de cobrança');--.go
insert into codigoCBO (codigo, descricao) values ('420115','Supervisor de coletadores de apostas e de jogos');--.go
insert into codigoCBO (codigo, descricao) values ('354210','Supervisor de compras');--.go
insert into codigoCBO (codigo, descricao) values ('410215','Supervisor de contas a pagar');--.go
insert into codigoCBO (codigo, descricao) values ('720160','Supervisor de controle de tratamento térmico');--.go
insert into codigoCBO (codigo, descricao) values ('410220','Supervisor de controle patrimonial');--.go
insert into codigoCBO (codigo, descricao) values ('410225','Supervisor de crédito e cobrança');--.go
insert into codigoCBO (codigo, descricao) values ('760205','Supervisor de curtimento');--.go
insert into codigoCBO (codigo, descricao) values ('410210','Supervisor de câmbio');--.go
insert into codigoCBO (codigo, descricao) values ('412120','Supervisor de digitaçao e operaçao');--.go
insert into codigoCBO (codigo, descricao) values ('780105','Supervisor de embalagem e etiquetagem');--.go
insert into codigoCBO (codigo, descricao) values ('342545','Supervisor de empresa aérea em aeroportos');--.go
insert into codigoCBO (codigo, descricao) values ('239430','Supervisor de ensino');--.go
insert into codigoCBO (codigo, descricao) values ('420120','Supervisor de entrevistadores e recenseadores');--.go
insert into codigoCBO (codigo, descricao) values ('620110','Supervisor de exploraçao agropecuária');--.go
insert into codigoCBO (codigo, descricao) values ('620105','Supervisor de exploraçao agrícola');--.go
insert into codigoCBO (codigo, descricao) values ('620115','Supervisor de exploraçao pecuária');--.go
insert into codigoCBO (codigo, descricao) values ('710110','Supervisor de extraçao de sal');--.go
insert into codigoCBO (codigo, descricao) values ('740110','Supervisor de fabricaçao de instrumentos musicais');--.go
insert into codigoCBO (codigo, descricao) values ('820205','Supervisor de fabricaçao de produtos cerâmicos, porcelanatos e afins');--.go
insert into codigoCBO (codigo, descricao) values ('820210','Supervisor de fabricaçao de produtos de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('750105','Supervisor de joalheria');--.go
insert into codigoCBO (codigo, descricao) values ('510205','Supervisor de lavanderia');--.go
insert into codigoCBO (codigo, descricao) values ('860105','Supervisor de manutençao  (eletromecânica)');--.go
insert into codigoCBO (codigo, descricao) values ('910110','Supervisor de manutençao de aparelhos térmicos, de climatizaçao e de refrigeraçao');--.go
insert into codigoCBO (codigo, descricao) values ('910115','Supervisor de manutençao de bombas, motores, compressores e equipamentos de transmissao');--.go
insert into codigoCBO (codigo, descricao) values ('910120','Supervisor de manutençao de máquinas gráficas');--.go
insert into codigoCBO (codigo, descricao) values ('910125','Supervisor de manutençao de máquinas industriais têxteis');--.go
insert into codigoCBO (codigo, descricao) values ('910130','Supervisor de manutençao de máquinas operatrizes e de usinagem');--.go
insert into codigoCBO (codigo, descricao) values ('910910','Supervisor de manutençao de vias férreas');--.go
insert into codigoCBO (codigo, descricao) values ('950305','Supervisor de manutençao eletromecânica');--.go
insert into codigoCBO (codigo, descricao) values ('950110','Supervisor de manutençao eletromecânica industrial, comercial e predial');--.go
insert into codigoCBO (codigo, descricao) values ('950105','Supervisor de manutençao elétrica de alta tensao industrial');--.go
insert into codigoCBO (codigo, descricao) values ('730105','Supervisor de montagem e instalaçao eletroeletrônica');--.go
insert into codigoCBO (codigo, descricao) values ('860110','Supervisor de operaçao de fluidos (distribuiçao, captaçao, tratamento de água, gases, vapor)');--.go
insert into codigoCBO (codigo, descricao) values ('860115','Supervisor de operaçao elétrica (geraçao, transmissao e distribuiçao de energia elétrica)');--.go
insert into codigoCBO (codigo, descricao) values ('342610','Supervisor de operaçoes portuárias');--.go
insert into codigoCBO (codigo, descricao) values ('410230','Supervisor de orçamento');--.go
insert into codigoCBO (codigo, descricao) values ('710115','Supervisor de perfuraçao e desmonte');--.go
insert into codigoCBO (codigo, descricao) values ('840105','Supervisor de produçao da indústria alimentícia');--.go
insert into codigoCBO (codigo, descricao) values ('710120','Supervisor de produçao na mineraçao');--.go
insert into codigoCBO (codigo, descricao) values ('420125','Supervisor de recepcionistas');--.go
insert into codigoCBO (codigo, descricao) values ('910905','Supervisor de reparos linhas férreas');--.go
insert into codigoCBO (codigo, descricao) values ('420130','Supervisor de telefonistas');--.go
insert into codigoCBO (codigo, descricao) values ('420135','Supervisor de telemarketing e atendimento');--.go
insert into codigoCBO (codigo, descricao) values ('410235','Supervisor de tesouraria');--.go
insert into codigoCBO (codigo, descricao) values ('710125','Supervisor de transporte na mineraçao');--.go
insert into codigoCBO (codigo, descricao) values ('510105','Supervisor de transportes');--.go
insert into codigoCBO (codigo, descricao) values ('710220','Supervisor de usina de concreto');--.go
insert into codigoCBO (codigo, descricao) values ('520110','Supervisor de vendas comercial');--.go
insert into codigoCBO (codigo, descricao) values ('520105','Supervisor de vendas de serviços');--.go
insert into codigoCBO (codigo, descricao) values ('510310','Supervisor de vigilantes');--.go
insert into codigoCBO (codigo, descricao) values ('373220','Supervisor técnico operacional de sistemas de televisao e produtoras de vídeo');--.go
insert into codigoCBO (codigo, descricao) values ('752235','Surfassagista');--.go
insert into codigoCBO (codigo, descricao) values ('513615','Sushiman');--.go
insert into codigoCBO (codigo, descricao) values ('241335','Tabeliao de notas');--.go
insert into codigoCBO (codigo, descricao) values ('241340','Tabeliao de protestos');--.go
insert into codigoCBO (codigo, descricao) values ('511115','Taifeiro');--.go
insert into codigoCBO (codigo, descricao) values ('771120','Tanoeiro');--.go
insert into codigoCBO (codigo, descricao) values ('716535','Taqueiro');--.go
insert into codigoCBO (codigo, descricao) values ('351510','Taquígrafo');--.go
insert into codigoCBO (codigo, descricao) values ('328110','Taxidermista');--.go
insert into codigoCBO (codigo, descricao) values ('761303','Tecelao (redes)');--.go
insert into codigoCBO (codigo, descricao) values ('761306','Tecelao (rendas e bordados)');--.go
insert into codigoCBO (codigo, descricao) values ('761309','Tecelao (tear automático)');--.go
insert into codigoCBO (codigo, descricao) values ('761312','Tecelao (tear jacquard)');--.go
insert into codigoCBO (codigo, descricao) values ('768105','Tecelao (tear manual)');--.go
insert into codigoCBO (codigo, descricao) values ('761315','Tecelao (tear mecânico de maquineta)');--.go
insert into codigoCBO (codigo, descricao) values ('761318','Tecelao (tear mecânico de xadrez)');--.go
insert into codigoCBO (codigo, descricao) values ('761321','Tecelao (tear mecânico liso)');--.go
insert into codigoCBO (codigo, descricao) values ('761324','Tecelao (tear mecânico, exceto jacquard)');--.go
insert into codigoCBO (codigo, descricao) values ('761330','Tecelao de malhas (máquina circular)');--.go
insert into codigoCBO (codigo, descricao) values ('761333','Tecelao de malhas (máquina retilínea)');--.go
insert into codigoCBO (codigo, descricao) values ('761327','Tecelao de malhas, a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('761339','Tecelao de meias (máquina circular)');--.go
insert into codigoCBO (codigo, descricao) values ('761342','Tecelao de meias (máquina retilínea)');--.go
insert into codigoCBO (codigo, descricao) values ('761336','Tecelao de meias, a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('768110','Tecelao de tapetes, a  mao');--.go
insert into codigoCBO (codigo, descricao) values ('761345','Tecelao de tapetes, a  máquina');--.go
insert into codigoCBO (codigo, descricao) values ('214360','Tecnólogo em eletricidade');--.go
insert into codigoCBO (codigo, descricao) values ('214365','Tecnólogo em eletrônica');--.go
insert into codigoCBO (codigo, descricao) values ('422205','Telefonista');--.go
insert into codigoCBO (codigo, descricao) values ('422210','Teleoperador');--.go
insert into codigoCBO (codigo, descricao) values ('716205','Telhador (telhas de argila e materias similares)');--.go
insert into codigoCBO (codigo, descricao) values ('716210','Telhador (telhas de cimento-amianto)');--.go
insert into codigoCBO (codigo, descricao) values ('716215','Telhador (telhas metálicas)');--.go
insert into codigoCBO (codigo, descricao) values ('716220','Telhador (telhas pláticas)');--.go
insert into codigoCBO (codigo, descricao) values ('723125','Temperador de metais e de compósitos');--.go
insert into codigoCBO (codigo, descricao) values ('823255','Temperador de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('030305','Tenente do corpo de bombeiros militar');--.go
insert into codigoCBO (codigo, descricao) values ('030115','Tenente-coronel bombeiro militar');--.go
insert into codigoCBO (codigo, descricao) values ('020110','Tenente-coronel da polícia militar');--.go
insert into codigoCBO (codigo, descricao) values ('223620','Terapeuta ocupacional');--.go
insert into codigoCBO (codigo, descricao) values ('353230','Tesoureiro de banco');--.go
insert into codigoCBO (codigo, descricao) values ('263115','Teólogo');--.go
insert into codigoCBO (codigo, descricao) values ('311725','Tingidor de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('516330','Tingidor de roupas');--.go
insert into codigoCBO (codigo, descricao) values ('768605','Tipógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('376250','Titeriteiro');--.go
insert into codigoCBO (codigo, descricao) values ('312320','Topógrafo');--.go
insert into codigoCBO (codigo, descricao) values ('712225','Torneiro (lavra de pedra)');--.go
insert into codigoCBO (codigo, descricao) values ('773355','Torneiro na usinagem convencional de madeira');--.go
insert into codigoCBO (codigo, descricao) values ('841625','Torrador de cacau');--.go
insert into codigoCBO (codigo, descricao) values ('841610','Torrador de café');--.go
insert into codigoCBO (codigo, descricao) values ('711330','Torrista (petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('519320','Tosador de animais domésticos');--.go
insert into codigoCBO (codigo, descricao) values ('621005','Trabalhador agropecuário em geral');--.go
insert into codigoCBO (codigo, descricao) values ('623305','Trabalhador da avicultura de corte');--.go
insert into codigoCBO (codigo, descricao) values ('623310','Trabalhador da avicultura de postura');--.go
insert into codigoCBO (codigo, descricao) values ('623205','Trabalhador da caprinocultura');--.go
insert into codigoCBO (codigo, descricao) values ('622205','Trabalhador da cultura de algodao');--.go
insert into codigoCBO (codigo, descricao) values ('622105','Trabalhador da cultura de arroz');--.go
insert into codigoCBO (codigo, descricao) values ('622605','Trabalhador da cultura de cacau');--.go
insert into codigoCBO (codigo, descricao) values ('622610','Trabalhador da cultura de café');--.go
insert into codigoCBO (codigo, descricao) values ('622110','Trabalhador da cultura de cana-de-açúcar');--.go
insert into codigoCBO (codigo, descricao) values ('622615','Trabalhador da cultura de erva-mate');--.go
insert into codigoCBO (codigo, descricao) values ('622805','Trabalhador da cultura de especiarias');--.go
insert into codigoCBO (codigo, descricao) values ('622620','Trabalhador da cultura de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('622625','Trabalhador da cultura de guaraná');--.go
insert into codigoCBO (codigo, descricao) values ('622115','Trabalhador da cultura de milho e sorgo');--.go
insert into codigoCBO (codigo, descricao) values ('622810','Trabalhador da cultura de plantas aromáticas e medicinais');--.go
insert into codigoCBO (codigo, descricao) values ('622210','Trabalhador da cultura de sisal');--.go
insert into codigoCBO (codigo, descricao) values ('622120','Trabalhador da cultura de trigo, aveia, cevada e triticale');--.go
insert into codigoCBO (codigo, descricao) values ('622215','Trabalhador da cultura do rami');--.go
insert into codigoCBO (codigo, descricao) values ('623320','Trabalhador da cunicultura');--.go
insert into codigoCBO (codigo, descricao) values ('823320','Trabalhador da elaboraçao de pré-fabricados (cimento amianto)');--.go
insert into codigoCBO (codigo, descricao) values ('823325','Trabalhador da elaboraçao de pré-fabricados (concreto armado)');--.go
insert into codigoCBO (codigo, descricao) values ('632305','Trabalhador da exploraçao de andiroba');--.go
insert into codigoCBO (codigo, descricao) values ('632405','Trabalhador da exploraçao de açaí');--.go
insert into codigoCBO (codigo, descricao) values ('632310','Trabalhador da exploraçao de babaçu');--.go
insert into codigoCBO (codigo, descricao) values ('632315','Trabalhador da exploraçao de bacaba');--.go
insert into codigoCBO (codigo, descricao) values ('632320','Trabalhador da exploraçao de buriti');--.go
insert into codigoCBO (codigo, descricao) values ('632325','Trabalhador da exploraçao de carnaúba');--.go
insert into codigoCBO (codigo, descricao) values ('632410','Trabalhador da exploraçao de castanha');--.go
insert into codigoCBO (codigo, descricao) values ('632510','Trabalhador da exploraçao de cipós produtores de substâncias aromáticas, medicinais e tóxicas');--.go
insert into codigoCBO (codigo, descricao) values ('632330','Trabalhador da exploraçao de coco-da-praia');--.go
insert into codigoCBO (codigo, descricao) values ('632335','Trabalhador da exploraçao de copaíba');--.go
insert into codigoCBO (codigo, descricao) values ('632210','Trabalhador da exploraçao de espécies produtoras de gomas nao elásticas');--.go
insert into codigoCBO (codigo, descricao) values ('632515','Trabalhador da exploraçao de madeiras tanantes');--.go
insert into codigoCBO (codigo, descricao) values ('632340','Trabalhador da exploraçao de malva (paina)');--.go
insert into codigoCBO (codigo, descricao) values ('632345','Trabalhador da exploraçao de murumuru');--.go
insert into codigoCBO (codigo, descricao) values ('632350','Trabalhador da exploraçao de oiticica');--.go
insert into codigoCBO (codigo, descricao) values ('632355','Trabalhador da exploraçao de ouricuri');--.go
insert into codigoCBO (codigo, descricao) values ('632360','Trabalhador da exploraçao de pequi');--.go
insert into codigoCBO (codigo, descricao) values ('632365','Trabalhador da exploraçao de piaçava');--.go
insert into codigoCBO (codigo, descricao) values ('632415','Trabalhador da exploraçao de pinhao');--.go
insert into codigoCBO (codigo, descricao) values ('632420','Trabalhador da exploraçao de pupunha');--.go
insert into codigoCBO (codigo, descricao) values ('632520','Trabalhador da exploraçao de raízes produtoras de substâncias aromáticas, medicinais e tóxicas');--.go
insert into codigoCBO (codigo, descricao) values ('632215','Trabalhador da exploraçao de resinas');--.go
insert into codigoCBO (codigo, descricao) values ('632370','Trabalhador da exploraçao de tucum');--.go
insert into codigoCBO (codigo, descricao) values ('632505','Trabalhador da exploraçao de árvores e arbustos produtores de substâncias aromát., medic. e tóxicas');--.go
insert into codigoCBO (codigo, descricao) values ('632525','Trabalhador da extraçao de substâncias aromáticas, medicinais e tóxicas, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('812110','Trabalhador da fabricaçao de muniçao e explosivos');--.go
insert into codigoCBO (codigo, descricao) values ('823330','Trabalhador da fabricaçao de pedras artificiais');--.go
insert into codigoCBO (codigo, descricao) values ('811125','Trabalhador da fabricaçao de resinas e vernizes');--.go
insert into codigoCBO (codigo, descricao) values ('991405','Trabalhador da manutençao de edificaçoes');--.go
insert into codigoCBO (codigo, descricao) values ('623210','Trabalhador da ovinocultura');--.go
insert into codigoCBO (codigo, descricao) values ('623105','Trabalhador da pecuária (asininos e muares)');--.go
insert into codigoCBO (codigo, descricao) values ('623110','Trabalhador da pecuária (bovinos corte)');--.go
insert into codigoCBO (codigo, descricao) values ('623115','Trabalhador da pecuária (bovinos leite)');--.go
insert into codigoCBO (codigo, descricao) values ('623120','Trabalhador da pecuária (bubalinos)');--.go
insert into codigoCBO (codigo, descricao) values ('623125','Trabalhador da pecuária (eqüinos)');--.go
insert into codigoCBO (codigo, descricao) values ('623215','Trabalhador da suinocultura');--.go
insert into codigoCBO (codigo, descricao) values ('632125','Trabalhador de extraçao florestal, em geral');--.go
insert into codigoCBO (codigo, descricao) values ('841476','Trabalhador de fabricaçao de margarina');--.go
insert into codigoCBO (codigo, descricao) values ('848325','Trabalhador de fabricaçao de sorvete');--.go
insert into codigoCBO (codigo, descricao) values ('811130','Trabalhador de fabricaçao de tintas');--.go
insert into codigoCBO (codigo, descricao) values ('841720','Trabalhador de fabricaçao de vinhos');--.go
insert into codigoCBO (codigo, descricao) values ('623015','Trabalhador de pecuária polivalente');--.go
insert into codigoCBO (codigo, descricao) values ('841484','Trabalhador de preparaçao de pescados (limpeza)');--.go
insert into codigoCBO (codigo, descricao) values ('514225','Trabalhador de serviços de manutençao de edifícios e logradouros');--.go
insert into codigoCBO (codigo, descricao) values ('841505','Trabalhador de tratamento do leite e fabricaçao de laticínios e afins');--.go
insert into codigoCBO (codigo, descricao) values ('765405','Trabalhador do acabamento de artefatos de tecidos e couros');--.go
insert into codigoCBO (codigo, descricao) values ('848605','Trabalhador do beneficiamento de fumo');--.go
insert into codigoCBO (codigo, descricao) values ('623405','Trabalhador em criatórios de animais produtores de veneno');--.go
insert into codigoCBO (codigo, descricao) values ('623410','Trabalhador na apicultura');--.go
insert into codigoCBO (codigo, descricao) values ('622705','Trabalhador na cultura de amendoim');--.go
insert into codigoCBO (codigo, descricao) values ('622710','Trabalhador na cultura de canola');--.go
insert into codigoCBO (codigo, descricao) values ('622715','Trabalhador na cultura de coco-da-baía');--.go
insert into codigoCBO (codigo, descricao) values ('622720','Trabalhador na cultura de dendê');--.go
insert into codigoCBO (codigo, descricao) values ('622725','Trabalhador na cultura de mamona');--.go
insert into codigoCBO (codigo, descricao) values ('622730','Trabalhador na cultura de soja');--.go
insert into codigoCBO (codigo, descricao) values ('622735','Trabalhador na cultura do girassol');--.go
insert into codigoCBO (codigo, descricao) values ('622740','Trabalhador na cultura do linho');--.go
insert into codigoCBO (codigo, descricao) values ('823265','Trabalhador na fabricaçao de produtos abrasivos');--.go
insert into codigoCBO (codigo, descricao) values ('623415','Trabalhador na minhocultura');--.go
insert into codigoCBO (codigo, descricao) values ('622305','Trabalhador na olericultura (frutos e sementes)');--.go
insert into codigoCBO (codigo, descricao) values ('622310','Trabalhador na olericultura (legumes)');--.go
insert into codigoCBO (codigo, descricao) values ('622315','Trabalhador na olericultura (raízes, bulbos e tubérculos)');--.go
insert into codigoCBO (codigo, descricao) values ('622320','Trabalhador na olericultura (talos, folhas e flores)');--.go
insert into codigoCBO (codigo, descricao) values ('643005','Trabalhador na operaçao de sistema de irrigaçao localizada (microaspersao e gotejamento)');--.go
insert into codigoCBO (codigo, descricao) values ('643010','Trabalhador na operaçao de sistema de irrigaçao por aspersao (pivô central)');--.go
insert into codigoCBO (codigo, descricao) values ('643015','Trabalhador na operaçao de sistemas convencionais de irrigaçao por aspersao');--.go
insert into codigoCBO (codigo, descricao) values ('643020','Trabalhador na operaçao de sistemas de irrigaçao e aspersao (alto propelido)');--.go
insert into codigoCBO (codigo, descricao) values ('643025','Trabalhador na operaçao de sistemas de irrigaçao por superfície e drenagem');--.go
insert into codigoCBO (codigo, descricao) values ('622015','Trabalhador na produçao de mudas e sementes');--.go
insert into codigoCBO (codigo, descricao) values ('623420','Trabalhador na sericicultura');--.go
insert into codigoCBO (codigo, descricao) values ('622510','Trabalhador no cultivo de espécies frutíferas rasteiras');--.go
insert into codigoCBO (codigo, descricao) values ('622405','Trabalhador no cultivo de flores e folhagens de corte');--.go
insert into codigoCBO (codigo, descricao) values ('622410','Trabalhador no cultivo de flores em vaso');--.go
insert into codigoCBO (codigo, descricao) values ('622415','Trabalhador no cultivo de forraçoes');--.go
insert into codigoCBO (codigo, descricao) values ('622420','Trabalhador no cultivo de mudas');--.go
insert into codigoCBO (codigo, descricao) values ('622425','Trabalhador no cultivo de plantas ornamentais');--.go
insert into codigoCBO (codigo, descricao) values ('622515','Trabalhador no cultivo de trepadeiras frutíferas');--.go
insert into codigoCBO (codigo, descricao) values ('622505','Trabalhador no cultivo de árvores frutíferas');--.go
insert into codigoCBO (codigo, descricao) values ('764005','Trabalhador polivalente da confecçao de calçados');--.go
insert into codigoCBO (codigo, descricao) values ('762005','Trabalhador polivalente do curtimento de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('622020','Trabalhador volante da agricultura');--.go
insert into codigoCBO (codigo, descricao) values ('261420','Tradutor');--.go
insert into codigoCBO (codigo, descricao) values ('752120','Transformador de tubos de vidro');--.go
insert into codigoCBO (codigo, descricao) values ('724610','Trançador de cabos de aço');--.go
insert into codigoCBO (codigo, descricao) values ('376255','Trapezista');--.go
insert into codigoCBO (codigo, descricao) values ('623020','Tratador de animais');--.go
insert into codigoCBO (codigo, descricao) values ('641015','Tratorista agrícola');--.go
insert into codigoCBO (codigo, descricao) values ('712230','Traçador de pedras');--.go
insert into codigoCBO (codigo, descricao) values ('751130','Trefilador (joalheria e ourivesaria)');--.go
insert into codigoCBO (codigo, descricao) values ('811775','Trefilador de borracha');--.go
insert into codigoCBO (codigo, descricao) values ('722415','Trefilador de metais, à máquina');--.go
insert into codigoCBO (codigo, descricao) values ('224135','Treinador profissional de futebol');--.go
insert into codigoCBO (codigo, descricao) values ('768115','Tricoteiro, à mao');--.go
insert into codigoCBO (codigo, descricao) values ('782810','Tropeiro');--.go
insert into codigoCBO (codigo, descricao) values ('321110','Técnico agropecuário');--.go
insert into codigoCBO (codigo, descricao) values ('321105','Técnico agrícola');--.go
insert into codigoCBO (codigo, descricao) values ('254110','Técnico da receita federal');--.go
insert into codigoCBO (codigo, descricao) values ('314705','Técnico de acabamento em siderurgia');--.go
insert into codigoCBO (codigo, descricao) values ('314710','Técnico de aciaria em siderurgia');--.go
insert into codigoCBO (codigo, descricao) values ('325205','Técnico de alimentos');--.go
insert into codigoCBO (codigo, descricao) values ('317210','Técnico de apoio ao usuário de informática (helpdesk)');--.go
insert into codigoCBO (codigo, descricao) values ('395105','Técnico de apoio em pesquisa e desenvolvimento (exceto agropecuário e florestal)');--.go
insert into codigoCBO (codigo, descricao) values ('395110','Técnico de apoio em pesquisa e desenvolvimento agropecuário florestal');--.go
insert into codigoCBO (codigo, descricao) values ('301205','Técnico de apoio à bioengenharia');--.go
insert into codigoCBO (codigo, descricao) values ('311110','Técnico de celulose e papel');--.go
insert into codigoCBO (codigo, descricao) values ('313305','Técnico de comunicaçao de dados');--.go
insert into codigoCBO (codigo, descricao) values ('351105','Técnico de contabilidade');--.go
insert into codigoCBO (codigo, descricao) values ('311505','Técnico de controle de meio ambiente');--.go
insert into codigoCBO (codigo, descricao) values ('224125','Técnico de desporto individual e coletivo (exceto futebol)');--.go
insert into codigoCBO (codigo, descricao) values ('322205','Técnico de enfermagem');--.go
insert into codigoCBO (codigo, descricao) values ('322210','Técnico de enfermagem de terapia intensiva');--.go
insert into codigoCBO (codigo, descricao) values ('322215','Técnico de enfermagem do trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('322220','Técnico de enfermagem psiquiátrica');--.go
insert into codigoCBO (codigo, descricao) values ('312205','Técnico de estradas');--.go
insert into codigoCBO (codigo, descricao) values ('314715','Técnico de fundiçao em siderurgia');--.go
insert into codigoCBO (codigo, descricao) values ('391210','Técnico de garantia da qualidade');--.go
insert into codigoCBO (codigo, descricao) values ('322605','Técnico de imobilizaçao ortopédica');--.go
insert into codigoCBO (codigo, descricao) values ('301110','Técnico de laboratório de análises físico-químicas (materiais de construçao)');--.go
insert into codigoCBO (codigo, descricao) values ('224130','Técnico de laboratório e fiscalizaçao desportiva');--.go
insert into codigoCBO (codigo, descricao) values ('301105','Técnico de laboratório industrial');--.go
insert into codigoCBO (codigo, descricao) values ('314720','Técnico de laminaçao em siderurgia');--.go
insert into codigoCBO (codigo, descricao) values ('314405','Técnico de manutençao de sistemas e instrumentos');--.go
insert into codigoCBO (codigo, descricao) values ('313205','Técnico de manutençao eletrônica');--.go
insert into codigoCBO (codigo, descricao) values ('313210','Técnico de manutençao eletrônica (circuitos de máquinas com comando numérico)');--.go
insert into codigoCBO (codigo, descricao) values ('313120','Técnico de manutençao elétrica');--.go
insert into codigoCBO (codigo, descricao) values ('313125','Técnico de manutençao elétrica de máquina');--.go
insert into codigoCBO (codigo, descricao) values ('391135','Técnico de matéria-prima e material');--.go
insert into codigoCBO (codigo, descricao) values ('311510','Técnico de meteorologia');--.go
insert into codigoCBO (codigo, descricao) values ('316305','Técnico de mineraçao');--.go
insert into codigoCBO (codigo, descricao) values ('316310','Técnico de mineraçao (óleo e petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('312105','Técnico de obras civis');--.go
insert into codigoCBO (codigo, descricao) values ('813130','Técnico de operaçao (química, petroquímica e afins)');--.go
insert into codigoCBO (codigo, descricao) values ('353210','Técnico de operaçoes e serviços bancários - crédito imobiliário');--.go
insert into codigoCBO (codigo, descricao) values ('353215','Técnico de operaçoes e serviços bancários - crédito rural');--.go
insert into codigoCBO (codigo, descricao) values ('353205','Técnico de operaçoes e serviços bancários - câmbio');--.go
insert into codigoCBO (codigo, descricao) values ('353220','Técnico de operaçoes e serviços bancários - leasing');--.go
insert into codigoCBO (codigo, descricao) values ('353225','Técnico de operaçoes e serviços bancários - renda fixa e variável');--.go
insert into codigoCBO (codigo, descricao) values ('322505','Técnico de ortopedia');--.go
insert into codigoCBO (codigo, descricao) values ('391220','Técnico de painel de controle');--.go
insert into codigoCBO (codigo, descricao) values ('391125','Técnico de planejamento de produçao');--.go
insert into codigoCBO (codigo, descricao) values ('391130','Técnico de planejamento e programaçao da manutençao');--.go
insert into codigoCBO (codigo, descricao) values ('316325','Técnico de produçao em refino de petróleo');--.go
insert into codigoCBO (codigo, descricao) values ('313310','Técnico de rede (telecomunicaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('314725','Técnico de reduçao na siderurgia (primeira fusao)');--.go
insert into codigoCBO (codigo, descricao) values ('314730','Técnico de refratário em siderurgia');--.go
insert into codigoCBO (codigo, descricao) values ('351735','Técnico de resseguros');--.go
insert into codigoCBO (codigo, descricao) values ('312210','Técnico de saneamento');--.go
insert into codigoCBO (codigo, descricao) values ('351740','Técnico de seguros');--.go
insert into codigoCBO (codigo, descricao) values ('313315','Técnico de telecomunicaçoes (telefonia)');--.go
insert into codigoCBO (codigo, descricao) values ('313320','Técnico de transmissao (telecomunicaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('254415','Técnico de tributos estadual');--.go
insert into codigoCBO (codigo, descricao) values ('254420','Técnico de tributos municipal');--.go
insert into codigoCBO (codigo, descricao) values ('311515','Técnico de utilidade (produçao e distribuiçao de vapor, gases, óleos, combustíveis, energia)');--.go
insert into codigoCBO (codigo, descricao) values ('354135','Técnico de vendas');--.go
insert into codigoCBO (codigo, descricao) values ('319205','Técnico do mobiliário');--.go
insert into codigoCBO (codigo, descricao) values ('313130','Técnico eletricista');--.go
insert into codigoCBO (codigo, descricao) values ('313215','Técnico eletrônico');--.go
insert into codigoCBO (codigo, descricao) values ('351305','Técnico em administraçao');--.go
insert into codigoCBO (codigo, descricao) values ('351310','Técnico em administraçao de comércio exterior');--.go
insert into codigoCBO (codigo, descricao) values ('312305','Técnico em agrimensura');--.go
insert into codigoCBO (codigo, descricao) values ('354140','Técnico em atendimento e vendas');--.go
insert into codigoCBO (codigo, descricao) values ('314305','Técnico em automobilística');--.go
insert into codigoCBO (codigo, descricao) values ('371110','Técnico em biblioteconomia');--.go
insert into codigoCBO (codigo, descricao) values ('325305','Técnico em biotecnologia');--.go
insert into codigoCBO (codigo, descricao) values ('320105','Técnico em bioterismo');--.go
insert into codigoCBO (codigo, descricao) values ('311405','Técnico em borracha');--.go
insert into codigoCBO (codigo, descricao) values ('314610','Técnico em caldeiraria');--.go
insert into codigoCBO (codigo, descricao) values ('313405','Técnico em calibraçao');--.go
insert into codigoCBO (codigo, descricao) values ('319105','Técnico em calçados e artefatos de couro');--.go
insert into codigoCBO (codigo, descricao) values ('321310','Técnico em carcinicultura');--.go
insert into codigoCBO (codigo, descricao) values ('319110','Técnico em confecçoes do vestuário');--.go
insert into codigoCBO (codigo, descricao) values ('311115','Técnico em curtimento');--.go
insert into codigoCBO (codigo, descricao) values ('352420','Técnico em direitos autorais');--.go
insert into codigoCBO (codigo, descricao) values ('300305','Técnico em eletromecânica');--.go
insert into codigoCBO (codigo, descricao) values ('314615','Técnico em estruturas metálicas');--.go
insert into codigoCBO (codigo, descricao) values ('313505','Técnico em fotônica');--.go
insert into codigoCBO (codigo, descricao) values ('312310','Técnico em geodésia e cartografia');--.go
insert into codigoCBO (codigo, descricao) values ('316105','Técnico em geofísica');--.go
insert into codigoCBO (codigo, descricao) values ('316110','Técnico em geologia');--.go
insert into codigoCBO (codigo, descricao) values ('316115','Técnico em geoquímica');--.go
insert into codigoCBO (codigo, descricao) values ('316120','Técnico em geotecnia');--.go
insert into codigoCBO (codigo, descricao) values ('374105','Técnico em gravaçao de áudio');--.go
insert into codigoCBO (codigo, descricao) values ('312315','Técnico em hidrografia');--.go
insert into codigoCBO (codigo, descricao) values ('322405','Técnico em higiene dental');--.go
insert into codigoCBO (codigo, descricao) values ('320110','Técnico em histologia');--.go
insert into codigoCBO (codigo, descricao) values ('325310','Técnico em imunobiológicos');--.go
insert into codigoCBO (codigo, descricao) values ('374110','Técnico em instalaçao de equipamentos de áudio');--.go
insert into codigoCBO (codigo, descricao) values ('313410','Técnico em instrumentaçao');--.go
insert into codigoCBO (codigo, descricao) values ('325110','Técnico em laboratório de farmácia');--.go
insert into codigoCBO (codigo, descricao) values ('321205','Técnico em madeira');--.go
insert into codigoCBO (codigo, descricao) values ('915115','Técnico em manutençao de balanças');--.go
insert into codigoCBO (codigo, descricao) values ('313220','Técnico em manutençao de equipamentos de informática');--.go
insert into codigoCBO (codigo, descricao) values ('915305','Técnico em manutençao de equipamentos e instrumentos médico-hospitalares');--.go
insert into codigoCBO (codigo, descricao) values ('915110','Técnico em manutençao de hidrômetros');--.go
insert into codigoCBO (codigo, descricao) values ('915105','Técnico em manutençao de instrumentos de mediçao e precisao');--.go
insert into codigoCBO (codigo, descricao) values ('314410','Técnico em manutençao de máquinas');--.go
insert into codigoCBO (codigo, descricao) values ('374115','Técnico em masterizaçao de áudio');--.go
insert into codigoCBO (codigo, descricao) values ('311305','Técnico em materiais, produtos cerâmicos e vidros');--.go
insert into codigoCBO (codigo, descricao) values ('300105','Técnico em mecatrônica - automaçao da manufatura');--.go
insert into codigoCBO (codigo, descricao) values ('300110','Técnico em mecatrônica - robótica');--.go
insert into codigoCBO (codigo, descricao) values ('314105','Técnico em mecânica de precisao');--.go
insert into codigoCBO (codigo, descricao) values ('321315','Técnico em mitilicultura');--.go
insert into codigoCBO (codigo, descricao) values ('374130','Técnico em mixagem de áudio');--.go
insert into codigoCBO (codigo, descricao) values ('371210','Técnico em museologia');--.go
insert into codigoCBO (codigo, descricao) values ('324105','Técnico em métodos eletrográficos em encefalografia');--.go
insert into codigoCBO (codigo, descricao) values ('324110','Técnico em métodos gráficos em cardiologia');--.go
insert into codigoCBO (codigo, descricao) values ('373210','Técnico em operaçao de equipamento de exibiçao de televisao');--.go
insert into codigoCBO (codigo, descricao) values ('373205','Técnico em operaçao de equipamentos de produçao para televisao  e produtoras de vídeo');--.go
insert into codigoCBO (codigo, descricao) values ('373215','Técnico em operaçao de equipamentos de transmissao/recepçao de televisao');--.go
insert into codigoCBO (codigo, descricao) values ('322310','Técnico em optometria');--.go
insert into codigoCBO (codigo, descricao) values ('324205','Técnico em patologia clínica');--.go
insert into codigoCBO (codigo, descricao) values ('323105','Técnico em pecuária');--.go
insert into codigoCBO (codigo, descricao) values ('316320','Técnico em pesquisa mineral');--.go
insert into codigoCBO (codigo, descricao) values ('311205','Técnico em petroquímica');--.go
insert into codigoCBO (codigo, descricao) values ('321305','Técnico em piscicultura');--.go
insert into codigoCBO (codigo, descricao) values ('316330','Técnico em planejamento de lavra de minas');--.go
insert into codigoCBO (codigo, descricao) values ('311410','Técnico em plástico');--.go
insert into codigoCBO (codigo, descricao) values ('316315','Técnico em processamento mineral (exceto petróleo)');--.go
insert into codigoCBO (codigo, descricao) values ('371305','Técnico em programaçao visual');--.go
insert into codigoCBO (codigo, descricao) values ('324115','Técnico em radiologia e imagenologia');--.go
insert into codigoCBO (codigo, descricao) values ('321320','Técnico em ranicultura');--.go
insert into codigoCBO (codigo, descricao) values ('351505','Técnico em secretariado');--.go
insert into codigoCBO (codigo, descricao) values ('351605','Técnico em segurança no trabalho');--.go
insert into codigoCBO (codigo, descricao) values ('314620','Técnico em soldagem');--.go
insert into codigoCBO (codigo, descricao) values ('374125','Técnico em sonorizaçao');--.go
insert into codigoCBO (codigo, descricao) values ('311520','Técnico em tratamento de efluentes');--.go
insert into codigoCBO (codigo, descricao) values ('354805','Técnico em turismo');--.go
insert into codigoCBO (codigo, descricao) values ('322305','Técnico em óptica');--.go
insert into codigoCBO (codigo, descricao) values ('321210','Técnico florestal');--.go
insert into codigoCBO (codigo, descricao) values ('371310','Técnico gráfico');--.go
insert into codigoCBO (codigo, descricao) values ('314110','Técnico mecânico');--.go
insert into codigoCBO (codigo, descricao) values ('314310','Técnico mecânico (aeronaves)');--.go
insert into codigoCBO (codigo, descricao) values ('314115','Técnico mecânico (calefaçao, ventilaçao e refrigeraçao)');--.go
insert into codigoCBO (codigo, descricao) values ('314315','Técnico mecânico (embarcaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('314125','Técnico mecânico (motores)');--.go
insert into codigoCBO (codigo, descricao) values ('314120','Técnico mecânico (máquinas)');--.go
insert into codigoCBO (codigo, descricao) values ('314205','Técnico mecânico na fabricaçao de ferramentas');--.go
insert into codigoCBO (codigo, descricao) values ('314210','Técnico mecânico na manutençao de ferramentas');--.go
insert into codigoCBO (codigo, descricao) values ('391230','Técnico operacional de serviços de correios');--.go
insert into codigoCBO (codigo, descricao) values ('311105','Técnico químico');--.go
insert into codigoCBO (codigo, descricao) values ('301115','Técnico químico de petróleo');--.go
insert into codigoCBO (codigo, descricao) values ('311605','Técnico têxtil');--.go
insert into codigoCBO (codigo, descricao) values ('311610','Técnico têxtil (tratamentos químicos)');--.go
insert into codigoCBO (codigo, descricao) values ('311615','Técnico têxtil de fiaçao');--.go
insert into codigoCBO (codigo, descricao) values ('311620','Técnico têxtil de malharia');--.go
insert into codigoCBO (codigo, descricao) values ('311625','Técnico têxtil de tecelagem');--.go
insert into codigoCBO (codigo, descricao) values ('762345','Vaqueador de couros e peles');--.go
insert into codigoCBO (codigo, descricao) values ('776430','Vassoureiro');--.go
insert into codigoCBO (codigo, descricao) values ('524215','Vendedor  permissionário');--.go
insert into codigoCBO (codigo, descricao) values ('524305','Vendedor ambulante');--.go
insert into codigoCBO (codigo, descricao) values ('521110','Vendedor de comércio varejista');--.go
insert into codigoCBO (codigo, descricao) values ('521105','Vendedor em comércio atacadista');--.go
insert into codigoCBO (codigo, descricao) values ('524105','Vendedor em domicílio');--.go
insert into codigoCBO (codigo, descricao) values ('354145','Vendedor pracista');--.go
insert into codigoCBO (codigo, descricao) values ('111120','Vereador');--.go
insert into codigoCBO (codigo, descricao) values ('717025','Vibradorista');--.go
insert into codigoCBO (codigo, descricao) values ('111255','Vice-Prefeito');--.go
insert into codigoCBO (codigo, descricao) values ('111240','Vice-governador de Estado');--.go
insert into codigoCBO (codigo, descricao) values ('111245','Vice-governador do Distrito Federal');--.go
insert into codigoCBO (codigo, descricao) values ('111210','Vice-presidente da República');--.go
insert into codigoCBO (codigo, descricao) values ('716305','Vidraceiro');--.go
insert into codigoCBO (codigo, descricao) values ('716310','Vidraceiro (edificaçoes)');--.go
insert into codigoCBO (codigo, descricao) values ('716315','Vidraceiro (vitrais)');--.go
insert into codigoCBO (codigo, descricao) values ('517420','Vigia');--.go
insert into codigoCBO (codigo, descricao) values ('517320','Vigia florestal');--.go
insert into codigoCBO (codigo, descricao) values ('517325','Vigia portuário');--.go
insert into codigoCBO (codigo, descricao) values ('517330','Vigilante');--.go
insert into codigoCBO (codigo, descricao) values ('841740','Vinagreiro');--.go
insert into codigoCBO (codigo, descricao) values ('515120','Visitador sanitário');--.go
insert into codigoCBO (codigo, descricao) values ('215150','Vistoriador naval');--.go
insert into codigoCBO (codigo, descricao) values ('375115','Visual merchandiser');--.go
insert into codigoCBO (codigo, descricao) values ('632015','Viveirista florestal');--.go
insert into codigoCBO (codigo, descricao) values ('841745','Xaropeiro');--.go
insert into codigoCBO (codigo, descricao) values ('514120','Zelador de edifício');--.go
insert into codigoCBO (codigo, descricao) values ('223310','Zootecnista');--.go

update parametrosdosistema set appversao = '1.1.37.27';--.go

-- versao 1.1.38.28
alter table candidato add column idF2RH int;--.go

alter table solicitacao add column liberador_id bigint;--.go
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_liberador_fk FOREIGN KEY (liberador_id) REFERENCES usuario(id);--.go

update parametrosdosistema set appversao = '1.1.38.28';--.go

-- versao 1.1.38.29

update papel set ordem= ordem + 2 where papelmae_id is null and ordem > 0;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (495, 'ROLE_VISUALIZAR_MSG', 'Visualizar Caixa de Mensagens', '#', 1, false, null);--.go

update papel set ordem=2,papelmae_id=null where id=411;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (496, 'ROLE_VISUALIZAR_SOLICITACAO_PESSOAL', 'Visualizar Solicitação de Pessoal', '#', 14, false, 357);--.go

update candidato set disponivel = false where blacklist = true;--.go

update papel set ordem = ordem + 1 where papelmae_id = 75;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (497, 'ROLE_RECEBE_EXAMES_PREVISTOS', 'Recebe email de exames previstos', '#', 1, false, 75);--.go
alter sequence papel_sequence restart with 498;--.go

update parametrosdosistema set appversao = '1.1.38.29';--.go

-- versao 1.1.39.30

update papel set ordem=ordem + 1 where papelmae_id=362 and ordem >= 7;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (498, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Relatorio de investimento de T&D', '/desenvolvimento/turma/relatorioInvestimento.action', 12, true, 368);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (499, 'ROLE_CAD_FAIXA_SALARIAL', 'Exibir Faixa Salarial de Cargos', '', 7, false, 362); --.go
alter sequence papel_sequence restart with 500; --.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=498 WHERE atualizaPapeisIdsAPartirDe is null;--.go

update papel set nome = 'Acompanhamento do Período de Experiência e Avaliação de Desempenho' where id = 490;--.go
update papel set nome = 'Avaliações dos Alunos' where id = 413;--.go
update papel set nome = 'Modelos de Avaliação de Curso' where id = 419;--.go

CREATE FUNCTION ajusta_perfil_papel_faixa() RETURNS integer AS '
DECLARE
    mviews RECORD;
BEGIN
    FOR mviews IN
		select distinct perfil_id as perfilId from perfil_papel where papeis_id = 11 order by perfil_id
		LOOP
		  EXECUTE ''insert into perfil_papel(perfil_id, papeis_id) values(''|| mviews.perfilId ||'', ''|| 499 ||'')'';
		END LOOP;
    RETURN 1;
END;
' LANGUAGE plpgsql;--.go
select ajusta_perfil_papel_faixa();--.go
drop function ajusta_perfil_papel_faixa();--.go

alter table empresa add column logoCertificadoUrl varchar(200);--.go

update parametrosdosistema set appversao = '1.1.39.30';--.go

-- versao 1.1.40.31

alter table colaborador add column observacaodemissao text;--.go
update colaborador set observacaodemissao=observacao where motivodemissao_id is not null and observacao is not null;--.go

update parametrosdosistema set appversao = '1.1.40.31';--.go

-- versao 1.1.41.32

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (500, 'ROLE_SOLICITACAO_AGENDA', 'Agenda', '/captacao/solicitacao/agenda.action', 2, true, 359);--.go
alter sequence papel_sequence restart with 501;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=500 WHERE atualizaPapeisIdsAPartirDe is null;--.go

ALTER TABLE historicocandidato ADD COLUMN horaIni character varying(5);--.go
ALTER TABLE historicocandidato ADD COLUMN horaFim character varying(5);--.go

update historicocandidato set horaini='00:00', horafim='00:00';--.go

update parametrosdosistema set appversao = '1.1.41.32';--.go

-- versao 1.1.42.33

alter table historicocandidato add column aptoTmp character(1);--.go
update historicocandidato set aptoTmp='S' where apto=true;--.go
update historicocandidato set aptoTmp='N' where apto=false;--.go

alter table historicocandidato drop column apto;--.go
alter table historicocandidato add column apto character(1);--.go
update historicocandidato set apto=aptoTmp;--.go
alter table historicocandidato drop column aptoTmp;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (501, 'ROLE_CAD_GRUPOAC', 'Grupos AC', '/geral/grupoAC/list.action', 6, true, 390);--.go

alter sequence papel_sequence restart with 502;--.go

CREATE TABLE grupoac (
    id bigint NOT NULL,
    codigo character varying(3) NOT NULL,
    descricao character varying(20) NOT NULL,
    acurlsoap character varying(120),
    acurlwsdl character varying(120),
    acusuario character varying(100),
    acsenha character varying(30)
);--.go
ALTER TABLE grupoac ADD CONSTRAINT grupoac_pkey PRIMARY KEY (id);--.go
CREATE SEQUENCE grupoac_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
alter table GRUPOAC add constraint grupoac_codigo_uk unique (codigo);--.go

alter table empresa add column grupoac character(3);--.go
ALTER TABLE ONLY empresa ADD CONSTRAINT empresa_grupoac_fk FOREIGN KEY (grupoac) REFERENCES grupoac(codigo);--.go

alter table indice add column grupoac character(3);--.go
ALTER TABLE ONLY indice ADD CONSTRAINT indice_grupoac_fk FOREIGN KEY (grupoac) REFERENCES grupoac(codigo);--.go

INSERT INTO grupoac (id, codigo, descricao, acurlsoap, acurlwsdl, acusuario, acsenha) VALUES (1,'001','AC Padrão','http://localhost:1024/soap/IAcPessoal','http://localhost:1024/wsdl/IAcPessoal','ADMIN','');--.go
alter sequence grupoac_sequence restart with 2;--.go

update empresa set grupoac='001';--.go

update grupoac set acurlsoap = e.acurlsoap from grupoac g inner join empresa e on e.grupoac = g.codigo where e.acurlsoap != '' and e.acurlsoap is not null;--.go
update grupoac set acurlwsdl = e.acurlwsdl from grupoac g inner join empresa e on e.grupoac = g.codigo where e.acurlwsdl != '' and e.acurlwsdl is not null;--.go
update grupoac set acusuario = e.acusuario from grupoac g inner join empresa e on e.grupoac = g.codigo where e.acusuario != '' and e.acusuario is not null;--.go
update grupoac set acsenha = e.acsenha from grupoac g inner join empresa e on e.grupoac = g.codigo where e.acsenha != '' and e.acsenha is not null;--.go

alter table empresa drop column acurlsoap;--.go
alter table empresa drop column acurlwsdl;--.go
alter table empresa drop column acusuario;--.go
alter table empresa drop column acsenha;--.go

alter table empresa alter column exibirDadosAmbiente SET default false;--.go

alter table candidatosolicitacao add column status character(1);--.go
update candidatosolicitacao set status = 'I';--.go

update candidatosolicitacao set status = 'C' where id in (
	select 
	cs.id
		from colaborador co
		inner join candidato ca on ca.id = co.candidato_id
		inner join candidatosolicitacao cs on cs.solicitacao_id = co.solicitacao_id and cs.candidato_id = co.candidato_id 
	where 
		co.solicitacao_id is not null
);--.go

update parametrosdosistema set appversao = '1.1.42.33';--.go

-- versao 1.1.43.34

ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoVisivel text;--.go
ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoObrigatorio text;--.go
ALTER TABLE parametrosdosistema ADD COLUMN camposCandidatoTabs text;--.go

update parametrosdosistema set camposCandidatoVisivel='nome,nascimento,naturalidade,sexo,cpf,escolaridade,endereco,email,telefone,celular,nomeContato,parentes,estadoCivil,qtdFilhos,nomeConjuge,profConjuge,nomePai,profPai,nomeMae,profMae,pensao,possuiVeiculo,deficiencia,formacao,idioma,desCursos,funcaoPretendida,areasInteresse,conhecimentos,colocacao,expProfissional,infoAdicionais,identidade,cartairaHabilitacao,tituloEleitoral,certificadoMilitar,ctps', camposCandidatoObrigatorio='nome,cpf,escolaridade,ende,num,cidade,telefone',camposCandidatoTabs='abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais';--.go  

alter table parametrosdosistema add column emailCandidatoNaoApto boolean;--.go
update parametrosdosistema set emailCandidatoNaoApto=enviaremail;--.go
update parametrosdosistema set enviaremail=true;--.go

update papel set url='#',codigo='ROLE_CONFIGURACAO' where id=41;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (502, 'ROLE_UTI_CONFIGURACAO', 'Sistema', '/geral/parametrosDoSistema/prepareUpdate.action', 1, true, 41);--.go
update papel set papelmae_id=41,ordem=2 where id=485;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (503, 'ROLE_CONFIG_CANDIDATO_EXT', 'Cadastro de Candidato (externo)', '/geral/parametrosDoSistema/listCamposCandidato.action', 3, true, 41);--.go

alter sequence papel_sequence restart with 504;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=502;--.go

ALTER TABLE avaliacao ADD COLUMN periodoExperiencia_id bigint; --.go
ALTER TABLE avaliacao ADD CONSTRAINT avaliacao_periodoExperiencia_fk FOREIGN KEY (periodoExperiencia_id) REFERENCES periodoExperiencia(id);--.go

update papel set papelmae_id=null, ordem=2 where id=487;--.go
update papel set nome = 'Pode Responder Pesquisa Por Outro Usuário' where id=401;--.go
update papel set nome = 'Pode Responder Avaliação Por Outro Usuário' where id=487;--.go

alter table parametrosdosistema DROP COLUMN exibirAbaDocumentos;--.go
update parametrosdosistema set appversao = '1.1.43.34';--.go

-- versao 1.1.43.35

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (504, 'ROLE_INFO_PAINEL_IND', 'Painel de Indicadores', '/cargosalario/historicoColaborador/painelIndicadores.action', 3, true, 373);--.go

update papel set papelmae_id=377,ordem=7 where id=398;--.go
update papel set papelmae_id=463,ordem=6 where id=70;--.go
delete from perfil_papel where papeis_id=462;--.go
delete from papel where id=462;--.go

alter sequence papel_sequence restart with 505;--.go

update papel set nome = 'Análise das Etapas Seletivas' where id=48;--.go

update parametrosdosistema set acversaowebservicecompativel = '1.0.1.44';--.go

update parametrosdosistema set camposcandidatovisivel=replace(camposcandidatovisivel,'telefone','fone');--.go
update parametrosdosistema set camposcandidatoobrigatorio=replace(camposcandidatoobrigatorio,'telefone','fone');--.go

update parametrosdosistema set appversao = '1.1.43.35';--.go

-- versao 1.1.44.36
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (505, 'ROLE_C&S_PAINEL_IND', 'Painel de Indicadores', '/cargosalario/historicoColaborador/painelIndicadoresCargoSalario.action', 4, true, 361);--.go

alter table solicitacaoepi_item	add column dataEntrega date;--.go
update solicitacaoepi_item set dataentrega = (select se.data from solicitacaoepi se WHERE se.id=solicitacaoepi_id and se.entregue=true);--.go
update papel set nome = 'Análise das Etapas Seletivas' where id = 48;--.go

update papel set ordem=6 where id=398;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (506, 'ROLE_REL_LISTA_COLAB', 'Listagem de Colaboradores', '/geral/colaborador/prepareRelatorioDinamico.action', 7, true, 377);--.go
alter sequence papel_sequence restart with 507;--.go

update papel set papelmae_id=463  where id = 47;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=504 WHERE atualizaPapeisIdsAPartirDe is null;--.go

update parametrosdosistema set appversao = '1.1.44.36';--.go

-- versao 1.1.45.37
alter table PeriodoExperiencia add column descricao character varying(40);--.go
update PeriodoExperiencia set descricao='';--.go
update papel set nome = 'Acompanhamento do Período de Experiência' where id = 470;--.go
update parametrosdosistema set appversao = '1.1.45.37';--.go

-- versao 1.1.46.38
alter table cargo add column complementoConhecimento character varying(120);--.go

alter table candidato alter column colocacao type character varying(1);--.go
update parametrosdosistema set camposcandidatotabs = 'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais,abaCurriculo';--.go

ALTER TABLE candidato ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE colaborador ALTER COLUMN bairro TYPE character varying(85);--.go
ALTER TABLE bairro ALTER COLUMN nome TYPE character varying(85);--.go
ALTER TABLE estabelecimento ALTER COLUMN bairro TYPE character varying(85);--.go

update papel set nome = 'Acompanhamento do Período de Experiência' where id=490;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (507, 'ROLE_COMO_FICOU_SABENDO_VAGA', 'Como Ficou Sabendo da Vaga', '/geral/comoFicouSabendoVaga/list.action', 8, true, 358);--.go
alter sequence papel_sequence restart with 508;--.go

create table comoFicouSabendoVaga (id bigint not null, nome character varying(100));--.go
ALTER TABLE ONLY comoFicouSabendoVaga ADD CONSTRAINT comoFicouSabendoVaga_pkey PRIMARY KEY (id);--.go
CREATE SEQUENCE comoFicouSabendoVaga_sequence START WITH 2 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE candidato add COLUMN comoFicouSabendoVaga_id bigint;--.go
ALTER TABLE candidato ADD CONSTRAINT candidato_comoFicouSabendoVaga_fk FOREIGN KEY (comoFicouSabendoVaga_id) REFERENCES comoFicouSabendoVaga(id);--.go

ALTER TABLE candidato add COLUMN comoFicouSabendoVagaQual character varying(100);--.go
insert into comoFicouSabendoVaga (id, nome) values (1, 'Outro');--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (508, 'ROLE_COMO_FICOU_SABENDO_VAGA', 'Estatística de Divulgação da Vaga', '/geral/comoFicouSabendoVaga/prepareRelatorioComoFicouSabendoVaga.action', 4, true, 360);--.go
alter sequence papel_sequence restart with 509;--.go

ALTER TABLE empresa ADD COLUMN campoExtraColaborador boolean default false;--.go
update empresa set campoExtraColaborador=(select campoExtraColaborador from parametrosdosistema);--.go
ALTER TABLE parametrosdosistema DROP COLUMN campoExtraColaborador;--.go

alter table ConfiguracaoCampoExtra add column empresa_id bigint;--.go
ALTER TABLE ConfiguracaoCampoExtra ADD CONSTRAINT ConfiguracaoCampoExtra_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go

alter sequence configuracaocampoextra_sequence restart with 17;--.go
ALTER TABLE ConfiguracaoCampoExtra ALTER COLUMN id SET DEFAULT nextval('configuracaocampoextra_sequence');--.go
insert into configuracaocampoextra (ativo,nome,descricao,titulo,ordem,tipo,posicao,empresa_id) select c.ativo ,c.nome ,c.descricao ,c.titulo ,c.ordem ,c.tipo ,c.posicao ,e.id from ConfiguracaoCampoExtra c join empresa e on 1=1 order by e.id,c.posicao;--.go

ALTER TABLE ConfiguracaoCampoExtra ALTER COLUMN id drop DEFAULT;--.go

INSERT INTO extintorInspecaoItem (id, descricao) VALUES (11, 'Outro'); --.go
alter table extintorInspecao add column outroMotivo character(50);--.go

ALTER TABLE ConfiguracaoCampoExtra RENAME COLUMN ativo TO ativoColaborador;--.go
ALTER TABLE ConfiguracaoCampoExtra ADD COLUMN ativoCandidato boolean default false;--.go
ALTER TABLE empresa ADD COLUMN campoextracandidato boolean default false;--.go

ALTER TABLE periodoexperiencia ALTER COLUMN descricao SET NOT NULL;--.go

alter table candidato add COLUMN camposextras_id bigint;--.go
ALTER TABLE candidato ADD CONSTRAINT candidato_camposextras_fk FOREIGN KEY (camposextras_id) REFERENCES camposextras(id);--.go

update papel set nome = 'Estatística de Divulgação da Vaga' where id = 508;--.go
update parametrosdosistema set appversao = '1.1.46.38';--.go

-- versao 1.1.47.39
update papel set ordem=8 where id=506;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (509, 'ROLE_REL_ABSENTEISMO', 'Absenteísmo', '/geral/colaboradorOcorrencia/prepareRelatorioAbsenteismo.action', 7, true, 377);--.go
alter sequence papel_sequence restart with 510;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=509 WHERE atualizaPapeisIdsAPartirDe is null;--.go
ALTER TABLE empresa ALTER COLUMN grupoac TYPE character varying(3);--.go
alter table tabelareajustecolaborador add column dissidio boolean not null default false;--.go

alter table empresa add column emailCandidatoNaoApto boolean;--.go
update empresa set emailCandidatoNaoApto = p.emailCandidatoNaoApto from parametrosdosistema p;--.go
alter table parametrosdosistema drop column emailCandidatoNaoApto;--.go

alter table empresa add column mailnaoaptos text;--.go
update empresa set mailnaoaptos = p.mailnaoaptos from parametrosdosistema p;--.go
alter table parametrosdosistema drop column mailnaoaptos;--.go

ALTER TABLE empresa ADD COLUMN exame_id bigint;--.go
ALTER TABLE empresa ADD CONSTRAINT empresa_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);--.go
update empresa set exame_id = p.exame_id from parametrosdosistema p;--.go
alter table parametrosdosistema drop column exame_id;--.go

alter table ocorrencia add column absenteismo boolean not null default false;--.go
update parametrosdosistema set appversao = '1.1.47.39';--.go

-- versao 1.1.48.40

update historicocolaborador h1 set motivo='C' where h1.data = (select min(h2.data) from historicocolaborador h2 where h1.colaborador_id=h2.colaborador_id);--.go

update historicocolaborador set motivo='P' where motivo='I';--.go

alter table candidato add column examepalografico text;--.go
alter table configuracaoimpressaocurriculo add column exibirexamepalografico boolean default false;--.go

update parametrosdosistema set appversao = '1.1.48.40';--.go

-- versao 1.1.49.41
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (510, 'ROLE_MOV_PALOGRAFICO', 'Exame Palográfico', '/captacao/candidato/prepareExamePalografico.action', 3, true, 359);--.go
alter sequence papel_sequence restart with 511;--.go

update papel set papelmae_id = 463 where id=510;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (511, 'ROLE_REL_COLAB_SEM_REAJUSTE', 'Colaboradores sem Reajuste Salarial', '/cargosalario/historicoColaborador/prepareRelatorioUltimasPromocoes.action', 7, true, 364);--.go
alter sequence papel_sequence restart with 512;--.go

create view SituacaoColaborador as 
select  hc.id as historicoColaboradorId,
	greatest(hc.data,hfs_hc.data, hi_hfs_hc.data, hi_hc.data) as data,
	hc.tiposalario as tipo,
	COALESCE((hfs_hc.quantidade * hi_hfs_hc.valor), hfs_hc.valor, (hc.quantidadeindice * hi_hc.valor), hc.salario) as salario,
	c.id as cargo_id,
	hc.faixasalarial_id,
	hc.estabelecimento_id,
	hc.areaOrganizacional_id,
	hc.colaborador_id,
	hc.motivo
	from historicocolaborador hc
			left join faixasalarial fs_hc on fs_hc.id=hc.faixasalarial_id
			left join faixasalarialhistorico hfs_hc on hfs_hc.faixasalarial_id = fs_hc.id and hc.tiposalario=1
			left join cargo c on c.id = fs_hc.cargo_id 
			left join indice i_hfs_hc on i_hfs_hc.id = hfs_hc.indice_id and hfs_hc.tipo=2
			left join indicehistorico hi_hfs_hc on i_hfs_hc.id = hi_hfs_hc.indice_id 
			left join indice i_hc on i_hc.id = hc.indice_id and hc.tiposalario=2
			left join indicehistorico hi_hc on hi_hc.indice_id = i_hc.id 
			left join 
			(
				select hc2.data,hc2.colaborador_id as colabId,
				COALESCE((select min(data) from historicocolaborador hc3 where hc3.data > hc2.data and hc3.colaborador_id=hc2.colaborador_id), '01-01-2300') as dataProximo
				from historicocolaborador hc2
				where hc2.status <> 3 
				order by colabId, hc2.data 
			) as proximo on proximo.data = hc.data and proximo.colabId = hc.colaborador_id 
			left join
			(
				select hfs2.data, hfs2.faixasalarial_id as faixaId,
				COALESCE((select min(data) from faixasalarialhistorico fsh3 where fsh3.data > hfs2.data and fsh3.faixasalarial_id = hfs2.faixasalarial_id), '01-01-2300') as dataProximoHistFaixa
				from faixasalarialhistorico hfs2
				where hfs2.status <> 3  
				order by faixaId, hfs2.data
			) as proximaFaixa on proximaFaixa.data = hfs_hc.data and proximaFaixa.faixaId = hfs_hc.faixasalarial_id 
			left join
			(
				select hc3.id as histColabId,
				COALESCE((select max(data) from faixasalarialhistorico fsh where fsh.data <= hc3.data and fsh.faixasalarial_id = hc3.faixasalarial_id), '01-01-1900') as dataAtualFaixa
				from historicocolaborador as hc3 
				where hc3.tiposalario=1 and hc3.status <> 3
				order by hc3.id
			) as faixaAtual on faixaAtual.histColabId = hc.id 
			left join
			(
				select hfs3.id as histFaixaId,
				COALESCE((select max(data) from indicehistorico hi where hi.data <= hfs3.data and hi.indice_id = hfs3.indice_id), '01-01-1900') as dataAtualIndiceFaixa
				from faixasalarialhistorico as hfs3
				where hfs3.tipo=2 and hfs3.status <> 3
				order by hfs3.id
			) as indiceAtualFaixa on indiceAtualFaixa.histFaixaId = hfs_hc.id 
			left join
			(
				select hc4.id as histColabId,
				COALESCE((select max(data) from indicehistorico hi where hi.data <= hc4.data and hi.indice_id = hc4.indice_id), '01-01-1900') as dataAtualIndice
				from historicocolaborador as hc4 
				where hc4.tiposalario=2 and hc4.status <> 3
				order by hc4.id
			) as indiceAtual on indiceAtual.histColabId = hc.id		
where hc.status <> 3
and (hfs_hc.data < proximo.dataProximo or hfs_hc.data is null)
and (hi_hfs_hc.data < proximo.dataProximo or hi_hfs_hc.data is null)
and (hi_hfs_hc.data < proximaFaixa.dataProximoHistFaixa or hi_hfs_hc.data is null)
and (hfs_hc.data >= faixaAtual.dataAtualFaixa or hfs_hc.data is null)
and (hi_hfs_hc.data >= indiceAtualFaixa.dataAtualIndiceFaixa or hi_hfs_hc.data is null)
and (hi_hc.data < proximo.dataProximo or hi_hc.data is null)
and (hi_hc.data >= indiceAtual.dataAtualIndice or hi_hc.data is null)
order by hc.colaborador_id,hc.data,hfs_hc.data,hi_hfs_hc.data,hi_hc.data;--.go

update papel set papelmae_id=364,ordem=6 where id=70;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (512, 'ROLE_MOV_DISSIDIO', 'Ajuste de Situação (Dissídio)', '/cargosalario/historicoColaborador/prepareAjusteDissidio.action', 4, true, 363); --.go
alter sequence papel_sequence restart with 513;--.go

UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=510 WHERE atualizaPapeisIdsAPartirDe is null;--.go

update parametrosdosistema set appversao = '1.1.49.41';--.go

-- versao 1.1.50.42
alter table parametrosdosistema add column compartilharColaboradores boolean default true;--.go
alter table parametrosdosistema add column compartilharCandidatos boolean default true;--.go

update parametrosdosistema set appversao = '1.1.50.42';