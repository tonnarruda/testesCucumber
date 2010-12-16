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