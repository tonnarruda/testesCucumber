--
-- PostgreSQL database dump
--

SET client_encoding = 'LATIN1';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: 
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ambiente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ambiente (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.ambiente OWNER TO postgres;

--
-- Name: ambiente_areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ambiente_areaorganizacional (
    ambiente_id bigint NOT NULL,
    areasorganizacionais_id bigint NOT NULL
);


ALTER TABLE public.ambiente_areaorganizacional OWNER TO postgres;

--
-- Name: ambiente_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ambiente_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ambiente_sequence OWNER TO postgres;

--
-- Name: ambiente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ambiente_sequence', 72, true);


--
-- Name: anexo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE anexo (
    id bigint NOT NULL,
    nome character varying(100),
    observacao text,
    url character varying(120),
    origem character(1) NOT NULL,
    origemid bigint
);


ALTER TABLE public.anexo OWNER TO postgres;

--
-- Name: anexo_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE anexo_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.anexo_sequence OWNER TO postgres;

--
-- Name: anexo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('anexo_sequence', 1, false);


--
-- Name: anuncio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE anuncio (
    id bigint NOT NULL,
    titulo character varying(100),
    cabecalho text,
    informacoes text,
    mostraconhecimento boolean NOT NULL,
    mostrabeneficio boolean NOT NULL,
    mostrasalario boolean NOT NULL,
    mostracargo boolean NOT NULL,
    mostrasexo boolean NOT NULL,
    mostraidade boolean NOT NULL,
    solicitacao_id bigint
);


ALTER TABLE public.anuncio OWNER TO postgres;

--
-- Name: anuncio_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE anuncio_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.anuncio_sequence OWNER TO postgres;

--
-- Name: anuncio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('anuncio_sequence', 26, true);


--
-- Name: areaformacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE areaformacao (
    id bigint NOT NULL,
    nome character varying(100)
);


ALTER TABLE public.areaformacao OWNER TO postgres;

--
-- Name: areaformacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE areaformacao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.areaformacao_sequence OWNER TO postgres;

--
-- Name: areaformacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('areaformacao_sequence', 104, true);


--
-- Name: areainteresse; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE areainteresse (
    id bigint NOT NULL,
    nome character varying(100),
    observacao text,
    empresa_id bigint
);


ALTER TABLE public.areainteresse OWNER TO postgres;

--
-- Name: areainteresse_areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE areainteresse_areaorganizacional (
    areasinteresse_id bigint NOT NULL,
    areasorganizacionais_id bigint NOT NULL
);


ALTER TABLE public.areainteresse_areaorganizacional OWNER TO postgres;

--
-- Name: areainteresse_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE areainteresse_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.areainteresse_sequence OWNER TO postgres;

--
-- Name: areainteresse_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('areainteresse_sequence', 70, true);


--
-- Name: areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE areaorganizacional (
    id bigint NOT NULL,
    nome character varying(100),
    codigoac character varying(12),
    areamae_id bigint,
    responsavel_id bigint,
    empresa_id bigint
);


ALTER TABLE public.areaorganizacional OWNER TO postgres;

--
-- Name: areaorganizacional_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE areaorganizacional_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.areaorganizacional_sequence OWNER TO postgres;

--
-- Name: areaorganizacional_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('areaorganizacional_sequence', 314, true);


--
-- Name: aspecto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE aspecto (
    id bigint NOT NULL,
    nome character varying(100),
    questionario_id bigint
);


ALTER TABLE public.aspecto OWNER TO postgres;

--
-- Name: aspecto_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aspecto_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.aspecto_sequence OWNER TO postgres;

--
-- Name: aspecto_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('aspecto_sequence', 101, true);


--
-- Name: auditoria; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE auditoria (
    id bigint NOT NULL,
    data timestamp without time zone,
    operacao character varying(20),
    entidade character varying(50),
    dados text,
    usuario_id bigint,
    empresa_id bigint
);


ALTER TABLE public.auditoria OWNER TO postgres;

--
-- Name: auditoria_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE auditoria_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.auditoria_sequence OWNER TO postgres;

--
-- Name: auditoria_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('auditoria_sequence', 89, true);


--
-- Name: avaliacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avaliacao (
    id bigint NOT NULL,
    questionario_id bigint
);


ALTER TABLE public.avaliacao OWNER TO postgres;

--
-- Name: avaliacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE avaliacao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.avaliacao_sequence OWNER TO postgres;

--
-- Name: avaliacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('avaliacao_sequence', 20, true);


--
-- Name: bairro; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE bairro (
    id bigint NOT NULL,
    nome character varying(100),
    cidade_id bigint
);


ALTER TABLE public.bairro OWNER TO postgres;

--
-- Name: bairro_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bairro_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.bairro_sequence OWNER TO postgres;

--
-- Name: bairro_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('bairro_sequence', 81, true);


--
-- Name: beneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE beneficio (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.beneficio OWNER TO postgres;

--
-- Name: beneficio_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE beneficio_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.beneficio_sequence OWNER TO postgres;

--
-- Name: beneficio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('beneficio_sequence', 129, true);


--
-- Name: candidato; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidato (
    id bigint NOT NULL,
    nome character varying(100),
    senha character varying(30),
    name character varying(255),
    contenttype character varying(255),
    bytes bytea,
    size bigint,
    logradouro character varying(100),
    numero character varying(10),
    complemento character varying(100),
    bairro character varying(100),
    cep character varying(10),
    ddd character varying(5),
    fonefixo character varying(10),
    fonecelular character varying(10),
    email character varying(120),
    cpf character varying(15),
    pis character varying(30),
    rg character varying(30),
    naturalidade character varying(100),
    pai character varying(100),
    mae character varying(100),
    conjuge character varying(100),
    profissaopai character varying(100),
    profissaomae character varying(100),
    profissaoconjuge character varying(100),
    conjugetrabalha boolean NOT NULL,
    parentesamigos character varying(100),
    qtdfilhos integer NOT NULL,
    sexo character(1) NOT NULL,
    datanascimento date,
    escolaridade character varying(5),
    estadocivil character varying(5),
    pagapensao boolean NOT NULL,
    quantidade integer NOT NULL,
    valor double precision,
    possuiveiculo boolean NOT NULL,
    numerohab character varying(30),
    registro character varying(30),
    emissao date,
    vencimento date,
    categoria character varying(10),
    colocacao character(1) NOT NULL,
    pretencaosalarial double precision,
    disponivel boolean NOT NULL,
    blacklist boolean NOT NULL,
    contratado boolean NOT NULL,
    observacao text,
    observacaoblacklist text,
    cursos text,
    dataatualizacao date,
    origem character(1) NOT NULL,
    ocrtexto text,
    uf_id bigint,
    cidade_id bigint,
    observacaorh text,
    deficiencia character(1) NOT NULL,
    rgorgaoemissor character varying(10),
    rguf_id bigint,
    rgdataexpedicao date,
    titeleitnumero character varying(13),
    titeleitzona character varying(13),
    titeleitsecao character varying(13),
    certmilnumero character varying(12),
    certmiltipo character varying(5),
    certmilserie character varying(12),
    ctpsnumero character varying(8),
    ctpsserie character varying(5),
    ctpsdv character(1),
    ctpsuf_id bigint,
    ctpsdataexpedicao date,
    empresa_id bigint
);


ALTER TABLE public.candidato OWNER TO postgres;

--
-- Name: candidato_areainteresse; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidato_areainteresse (
    candidato_id bigint NOT NULL,
    areasinteresse_id bigint NOT NULL
);


ALTER TABLE public.candidato_areainteresse OWNER TO postgres;

--
-- Name: candidato_cargo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidato_cargo (
    candidato_id bigint NOT NULL,
    cargos_id bigint NOT NULL
);


ALTER TABLE public.candidato_cargo OWNER TO postgres;

--
-- Name: candidato_conhecimento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidato_conhecimento (
    candidato_id bigint NOT NULL,
    conhecimentos_id bigint NOT NULL
);


ALTER TABLE public.candidato_conhecimento OWNER TO postgres;

--
-- Name: candidato_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE candidato_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidato_sequence OWNER TO postgres;

--
-- Name: candidato_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidato_sequence', 648, true);


--
-- Name: candidatocurriculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidatocurriculo (
    id bigint NOT NULL,
    curriculo text,
    candidato_id bigint
);


ALTER TABLE public.candidatocurriculo OWNER TO postgres;

--
-- Name: candidatocurriculo_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE candidatocurriculo_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidatocurriculo_sequence OWNER TO postgres;

--
-- Name: candidatocurriculo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidatocurriculo_sequence', 26, true);


--
-- Name: candidatoidioma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidatoidioma (
    id bigint NOT NULL,
    nivel character(1) NOT NULL,
    candidato_id bigint,
    idioma_id bigint
);


ALTER TABLE public.candidatoidioma OWNER TO postgres;

--
-- Name: candidatoidioma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE candidatoidioma_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidatoidioma_sequence OWNER TO postgres;

--
-- Name: candidatoidioma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidatoidioma_sequence', 78, true);


--
-- Name: candidatosolicitacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidatosolicitacao (
    id bigint NOT NULL,
    triagem boolean NOT NULL,
    candidato_id bigint,
    solicitacao_id bigint
);


ALTER TABLE public.candidatosolicitacao OWNER TO postgres;

--
-- Name: candidatosolicitacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE candidatosolicitacao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidatosolicitacao_sequence OWNER TO postgres;

--
-- Name: candidatosolicitacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidatosolicitacao_sequence', 117, true);


--
-- Name: cargo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo (
    id bigint NOT NULL,
    nome character varying(100),
    nomemercado character varying(100) NOT NULL,
    missao text,
    competencias text,
    responsabilidades text,
    escolaridade character varying(5),
    experiencia text,
    recrutamento text,
    selecao text,
    observacao text,
    cbocodigo character varying(10),
    grupoocupacional_id bigint,
    empresa_id bigint
);


ALTER TABLE public.cargo OWNER TO postgres;

--
-- Name: cargo_areaformacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo_areaformacao (
    cargo_id bigint NOT NULL,
    areaformacaos_id bigint NOT NULL
);


ALTER TABLE public.cargo_areaformacao OWNER TO postgres;

--
-- Name: cargo_areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo_areaorganizacional (
    cargo_id bigint NOT NULL,
    areasorganizacionais_id bigint NOT NULL
);


ALTER TABLE public.cargo_areaorganizacional OWNER TO postgres;

--
-- Name: cargo_conhecimento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo_conhecimento (
    cargo_id bigint NOT NULL,
    conhecimentos_id bigint NOT NULL
);


ALTER TABLE public.cargo_conhecimento OWNER TO postgres;

--
-- Name: cargo_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cargo_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cargo_sequence OWNER TO postgres;

--
-- Name: cargo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cargo_sequence', 266, true);


--
-- Name: cat; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cat (
    id bigint NOT NULL,
    data date,
    numerocat character varying(20),
    observacao text,
    colaborador_id bigint
);


ALTER TABLE public.cat OWNER TO postgres;

--
-- Name: cat_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cat_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cat_sequence OWNER TO postgres;

--
-- Name: cat_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cat_sequence', 20, true);


--
-- Name: cidade; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cidade (
    id bigint NOT NULL,
    nome character varying(80) NOT NULL,
    codigoac character varying(12),
    uf_id bigint NOT NULL
);


ALTER TABLE public.cidade OWNER TO postgres;

--
-- Name: cidade_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cidade_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cidade_sequence OWNER TO postgres;

--
-- Name: cidade_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cidade_sequence', 5587, true);


--
-- Name: clinicaautorizada; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clinicaautorizada (
    id bigint NOT NULL,
    nome character varying(100),
    crm character varying(20),
    cnpj character varying(14),
    tipo character varying(5),
    data date,
    datainativa date,
    empresa_id bigint
);


ALTER TABLE public.clinicaautorizada OWNER TO postgres;

--
-- Name: clinicaautorizada_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE clinicaautorizada_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.clinicaautorizada_sequence OWNER TO postgres;

--
-- Name: clinicaautorizada_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('clinicaautorizada_sequence', 51, true);


--
-- Name: colaborador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaborador (
    id bigint NOT NULL,
    matricula character varying(20),
    nome character varying(100),
    nomecomercial character varying(100),
    desligado boolean NOT NULL,
    datadesligamento date,
    observacao text,
    dataadmissao date,
    logradouro character varying(100),
    numero character varying(10),
    complemento character varying(100),
    bairro character varying(100),
    cep character varying(10),
    cpf character varying(15),
    pis character varying(30),
    rg character varying(30),
    naturalidade character varying(100),
    pai character varying(100),
    mae character varying(100),
    conjuge character varying(100),
    profissaopai character varying(100),
    profissaomae character varying(100),
    profissaoconjuge character varying(100),
    conjugetrabalha boolean NOT NULL,
    parentesamigos character varying(100),
    qtdfilhos integer NOT NULL,
    sexo character(1) NOT NULL,
    datanascimento date,
    escolaridade character varying(5),
    estadocivil character varying(5),
    ddd character varying(5),
    fonefixo character varying(10),
    fonecelular character varying(10),
    email character varying(120),
    vinculo character varying(5),
    codigoac character varying(12),
    cursos text,
    regimerevezamento character varying(50),
    naointegraac boolean NOT NULL,
    empresa_id bigint,
    uf_id bigint,
    cidade_id bigint,
    usuario_id bigint,
    candidato_id bigint,
    motivodemissao_id bigint,
    deficiencia character(1) NOT NULL,
    rgorgaoemissor character varying(10),
    rguf_id bigint,
    rgdataexpedicao date,
    numerohab character varying(30),
    registro character varying(30),
    emissao date,
    vencimento date,
    categoria character varying(10),
    titeleitnumero character varying(13),
    titeleitzona character varying(13),
    titeleitsecao character varying(13),
    certmilnumero character varying(12),
    certmiltipo character varying(5),
    certmilserie character varying(12),
    ctpsnumero character varying(8),
    ctpsserie character varying(5),
    ctpsdv character(1),
    ctpsuf_id bigint,
    ctpsdataexpedicao date,
    respondeuentrevista boolean NOT NULL
);


ALTER TABLE public.colaborador OWNER TO postgres;

--
-- Name: colaborador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaborador_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaborador_sequence OWNER TO postgres;

--
-- Name: colaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaborador_sequence', 687, true);


--
-- Name: colaboradoridioma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradoridioma (
    id bigint NOT NULL,
    nivel character(1) NOT NULL,
    colaborador_id bigint,
    idioma_id bigint
);


ALTER TABLE public.colaboradoridioma OWNER TO postgres;

--
-- Name: colaboradoridioma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradoridioma_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradoridioma_sequence OWNER TO postgres;

--
-- Name: colaboradoridioma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradoridioma_sequence', 30, true);


--
-- Name: colaboradorocorrencia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorocorrencia (
    id bigint NOT NULL,
    dataini date,
    datafim date,
    observacao text,
    colaborador_id bigint,
    ocorrencia_id bigint
);


ALTER TABLE public.colaboradorocorrencia OWNER TO postgres;

--
-- Name: colaboradorocorrencia_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorocorrencia_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorocorrencia_sequence OWNER TO postgres;

--
-- Name: colaboradorocorrencia_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorocorrencia_sequence', 30, true);


--
-- Name: colaboradorpresenca; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorpresenca (
    id bigint NOT NULL,
    presenca boolean NOT NULL,
    colaboradorturma_id bigint,
    diaturma_id bigint
);


ALTER TABLE public.colaboradorpresenca OWNER TO postgres;

--
-- Name: colaboradorpresenca_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorpresenca_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorpresenca_sequence OWNER TO postgres;

--
-- Name: colaboradorpresenca_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorpresenca_sequence', 25, true);


--
-- Name: colaboradorquestionario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorquestionario (
    id bigint NOT NULL,
    colaborador_id bigint,
    questionario_id bigint,
    respondida boolean,
    respondidaem date
);


ALTER TABLE public.colaboradorquestionario OWNER TO postgres;

--
-- Name: colaboradorquestionario_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorquestionario_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorquestionario_sequence OWNER TO postgres;

--
-- Name: colaboradorquestionario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorquestionario_sequence', 136, true);


--
-- Name: colaboradorresposta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorresposta (
    id bigint NOT NULL,
    comentario text,
    valor integer NOT NULL,
    pergunta_id bigint,
    resposta_id bigint,
    colaboradorquestionario_id bigint,
    areaorganizacional_id bigint
);


ALTER TABLE public.colaboradorresposta OWNER TO postgres;

--
-- Name: colaboradorresposta_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorresposta_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorresposta_sequence OWNER TO postgres;

--
-- Name: colaboradorresposta_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorresposta_sequence', 128, true);


--
-- Name: colaboradorturma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorturma (
    id bigint NOT NULL,
    origemdnt boolean NOT NULL,
    aprovado boolean NOT NULL,
    colaborador_id bigint,
    prioridadetreinamento_id bigint,
    turma_id bigint,
    curso_id bigint,
    dnt_id bigint
);


ALTER TABLE public.colaboradorturma OWNER TO postgres;

--
-- Name: colaboradorturma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorturma_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorturma_sequence OWNER TO postgres;

--
-- Name: colaboradorturma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorturma_sequence', 90, true);


--
-- Name: conhecimento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conhecimento (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.conhecimento OWNER TO postgres;

--
-- Name: conhecimento_areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conhecimento_areaorganizacional (
    conhecimentos_id bigint NOT NULL,
    areaorganizacionals_id bigint NOT NULL
);


ALTER TABLE public.conhecimento_areaorganizacional OWNER TO postgres;

--
-- Name: conhecimento_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE conhecimento_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.conhecimento_sequence OWNER TO postgres;

--
-- Name: conhecimento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('conhecimento_sequence', 84, true);


--
-- Name: curso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE curso (
    id bigint NOT NULL,
    nome character varying(100),
    conteudoprogramatico text,
    empresa_id bigint
);


ALTER TABLE public.curso OWNER TO postgres;

--
-- Name: curso_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE curso_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.curso_sequence OWNER TO postgres;

--
-- Name: curso_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('curso_sequence', 107, true);


--
-- Name: dependente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dependente (
    id bigint NOT NULL,
    nome character varying(100),
    datanascimento date,
    seqac character varying(12),
    colaborador_id bigint
);


ALTER TABLE public.dependente OWNER TO postgres;

--
-- Name: dependente_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE dependente_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.dependente_sequence OWNER TO postgres;

--
-- Name: dependente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('dependente_sequence', 20, true);


--
-- Name: diaturma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE diaturma (
    id bigint NOT NULL,
    dia date,
    turma_id bigint
);


ALTER TABLE public.diaturma OWNER TO postgres;

--
-- Name: diaturma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE diaturma_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.diaturma_sequence OWNER TO postgres;

--
-- Name: diaturma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('diaturma_sequence', 20, true);


--
-- Name: dnt; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dnt (
    id bigint NOT NULL,
    nome character varying(100),
    data date,
    empresa_id bigint
);


ALTER TABLE public.dnt OWNER TO postgres;

--
-- Name: dnt_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE dnt_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.dnt_sequence OWNER TO postgres;

--
-- Name: dnt_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('dnt_sequence', 31, true);


--
-- Name: documentoanexo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE documentoanexo (
    id bigint NOT NULL,
    descricao character varying(100),
    data date,
    observacao text,
    url character varying(120),
    origem character(1) NOT NULL,
    origemid bigint,
    etapaseletiva_id bigint
);


ALTER TABLE public.documentoanexo OWNER TO postgres;

--
-- Name: documentoanexo_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE documentoanexo_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.documentoanexo_sequence OWNER TO postgres;

--
-- Name: documentoanexo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('documentoanexo_sequence', 31, true);


--
-- Name: duracaopreenchimentovaga; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE duracaopreenchimentovaga (
    id bigint NOT NULL,
    qtddiasprimeiracontratacao integer NOT NULL,
    solicitacao_id bigint,
    cargo_id bigint,
    areaorganizacional_id bigint
);


ALTER TABLE public.duracaopreenchimentovaga OWNER TO postgres;

--
-- Name: duracaopreenchimentovaga_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE duracaopreenchimentovaga_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.duracaopreenchimentovaga_sequence OWNER TO postgres;

--
-- Name: duracaopreenchimentovaga_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('duracaopreenchimentovaga_sequence', 35, true);


--
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empresa (
    id bigint NOT NULL,
    nome character varying(100),
    cnpj character varying(20),
    razaosocial character varying(100),
    codigoac character varying(12),
    emailremetente character varying(120),
    emailrespsetorpessoal character varying(120),
    emailresprh character varying(120),
    cnae character varying(20),
    grauderisco character varying(10),
    representantelegal character varying(100),
    nitrepresentantelegal character varying(100),
    horariotrabalho character varying(50),
    endereco character varying(100),
    acintegra boolean NOT NULL,
    acurlsoap character varying(120),
    acurlwsdl character varying(120),
    acusuario character varying(100),
    acsenha character varying(30),
    maxcandidatacargo integer NOT NULL,
    logourl character varying(200),
    exibirsalario boolean
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- Name: empresa_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE empresa_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.empresa_sequence OWNER TO postgres;

--
-- Name: empresa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('empresa_sequence', 886, true);


--
-- Name: empresabds; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empresabds (
    id bigint NOT NULL,
    nome character varying(100),
    contato character varying(100),
    fone character varying(10),
    email character varying(120),
    ddd character varying(5),
    empresa_id bigint
);


ALTER TABLE public.empresabds OWNER TO postgres;

--
-- Name: empresabds_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE empresabds_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.empresabds_sequence OWNER TO postgres;

--
-- Name: empresabds_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('empresabds_sequence', 24, true);


--
-- Name: engenheiroresponsavel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE engenheiroresponsavel (
    id bigint NOT NULL,
    nome character varying(100),
    apartirde date,
    crea character varying(20),
    empresa_id bigint
);


ALTER TABLE public.engenheiroresponsavel OWNER TO postgres;

--
-- Name: engenheiroresponsavel_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE engenheiroresponsavel_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.engenheiroresponsavel_sequence OWNER TO postgres;

--
-- Name: engenheiroresponsavel_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('engenheiroresponsavel_sequence', 26, true);


--
-- Name: entrevista; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE entrevista (
    id bigint NOT NULL,
    ativa boolean,
    questionario_id bigint
);


ALTER TABLE public.entrevista OWNER TO postgres;

--
-- Name: entrevista_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE entrevista_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.entrevista_sequence OWNER TO postgres;

--
-- Name: entrevista_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('entrevista_sequence', 47, true);


--
-- Name: epc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE epc (
    id bigint NOT NULL,
    codigo character varying(30),
    descricao character varying(100),
    empresa_id bigint
);


ALTER TABLE public.epc OWNER TO postgres;

--
-- Name: epc_ambiente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE epc_ambiente (
    epc_id bigint NOT NULL,
    ambientes_id bigint NOT NULL
);


ALTER TABLE public.epc_ambiente OWNER TO postgres;

--
-- Name: epc_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE epc_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.epc_sequence OWNER TO postgres;

--
-- Name: epc_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('epc_sequence', 26, true);


--
-- Name: epi; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE epi (
    id bigint NOT NULL,
    nome character varying(100),
    fabricante character varying(100),
    empresa_id bigint,
    tipoepi_id bigint
);


ALTER TABLE public.epi OWNER TO postgres;

--
-- Name: epi_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE epi_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.epi_sequence OWNER TO postgres;

--
-- Name: epi_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('epi_sequence', 40, true);


--
-- Name: epihistorico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE epihistorico (
    id bigint NOT NULL,
    atenuacao character varying(20),
    emissao date,
    vencimentoca date,
    validadeuso integer,
    ca character varying(20),
    data date,
    epi_id bigint
);


ALTER TABLE public.epihistorico OWNER TO postgres;

--
-- Name: epihistorico_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE epihistorico_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.epihistorico_sequence OWNER TO postgres;

--
-- Name: epihistorico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('epihistorico_sequence', 35, true);


--
-- Name: estabelecimento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estabelecimento (
    id bigint NOT NULL,
    nome character varying(100),
    logradouro character varying(100),
    numero character varying(10),
    complemento character varying(100),
    bairro character varying(100),
    cep character varying(10),
    complementocnpj character varying(10),
    codigoac character varying(12),
    uf_id bigint,
    cidade_id bigint,
    empresa_id bigint
);


ALTER TABLE public.estabelecimento OWNER TO postgres;

--
-- Name: estabelecimento_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE estabelecimento_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.estabelecimento_sequence OWNER TO postgres;

--
-- Name: estabelecimento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('estabelecimento_sequence', 245, true);


--
-- Name: estado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estado (
    id bigint NOT NULL,
    sigla character varying(2) NOT NULL,
    nome character varying(40) NOT NULL
);


ALTER TABLE public.estado OWNER TO postgres;

--
-- Name: estado_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE estado_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.estado_sequence OWNER TO postgres;

--
-- Name: estado_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('estado_sequence', 107, true);


--
-- Name: etapaseletiva; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE etapaseletiva (
    id bigint NOT NULL,
    nome character varying(100),
    ordem integer NOT NULL,
    empresa_id bigint
);


ALTER TABLE public.etapaseletiva OWNER TO postgres;

--
-- Name: etapaseletiva_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE etapaseletiva_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.etapaseletiva_sequence OWNER TO postgres;

--
-- Name: etapaseletiva_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('etapaseletiva_sequence', 185, true);


--
-- Name: exame; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE exame (
    id bigint NOT NULL,
    nome character varying(100),
    periodicidade integer NOT NULL,
    periodico boolean,
    empresa_id bigint
);


ALTER TABLE public.exame OWNER TO postgres;

--
-- Name: exame_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE exame_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.exame_sequence OWNER TO postgres;

--
-- Name: exame_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('exame_sequence', 63, true);


--
-- Name: experiencia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE experiencia (
    id bigint NOT NULL,
    empresa character varying(100),
    dataadmissao date,
    datadesligamento date,
    observacao text,
    nomemercado character varying(100),
    candidato_id bigint,
    colaborador_id bigint,
    cargo_id bigint
);


ALTER TABLE public.experiencia OWNER TO postgres;

--
-- Name: experiencia_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE experiencia_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.experiencia_sequence OWNER TO postgres;

--
-- Name: experiencia_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('experiencia_sequence', 70, true);


--
-- Name: faixasalarial; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE faixasalarial (
    id bigint NOT NULL,
    nome character varying(100),
    codigoac character varying(12),
    cargo_id bigint
);


ALTER TABLE public.faixasalarial OWNER TO postgres;

--
-- Name: faixasalarial_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE faixasalarial_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.faixasalarial_sequence OWNER TO postgres;

--
-- Name: faixasalarial_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('faixasalarial_sequence', 190, true);


--
-- Name: faixasalarialhistorico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE faixasalarialhistorico (
    id bigint NOT NULL,
    data date,
    valor double precision,
    tipo integer NOT NULL,
    faixasalarial_id bigint,
    indice_id bigint,
    quantidade double precision,
    status integer
);


ALTER TABLE public.faixasalarialhistorico OWNER TO postgres;

--
-- Name: faixasalarialhistorico_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE faixasalarialhistorico_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.faixasalarialhistorico_sequence OWNER TO postgres;

--
-- Name: faixasalarialhistorico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('faixasalarialhistorico_sequence', 63, true);


--
-- Name: faixatabela_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE faixatabela_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.faixatabela_sequence OWNER TO postgres;

--
-- Name: faixatabela_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('faixatabela_sequence', 100, true);


--
-- Name: formacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE formacao (
    id bigint NOT NULL,
    situacao character(1) NOT NULL,
    tipo character(1) NOT NULL,
    curso character varying(100),
    "local" character varying(100),
    conclusao character varying(30),
    candidato_id bigint,
    colaborador_id bigint,
    areaformacao_id bigint
);


ALTER TABLE public.formacao OWNER TO postgres;

--
-- Name: formacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE formacao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.formacao_sequence OWNER TO postgres;

--
-- Name: formacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('formacao_sequence', 55, true);


--
-- Name: funcao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE funcao (
    id bigint NOT NULL,
    nome character varying(100),
    cargo_id bigint
);


ALTER TABLE public.funcao OWNER TO postgres;

--
-- Name: funcao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE funcao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.funcao_sequence OWNER TO postgres;

--
-- Name: funcao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('funcao_sequence', 119, true);


--
-- Name: gasto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gasto (
    id bigint NOT NULL,
    nome character varying(100),
    naoimportar boolean NOT NULL,
    codigoac character varying(12),
    grupogasto_id bigint,
    empresa_id bigint
);


ALTER TABLE public.gasto OWNER TO postgres;

--
-- Name: gasto_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gasto_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.gasto_sequence OWNER TO postgres;

--
-- Name: gasto_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasto_sequence', 58, true);


--
-- Name: gastoempresa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gastoempresa (
    id bigint NOT NULL,
    mesano date,
    colaborador_id bigint,
    empresa_id bigint
);


ALTER TABLE public.gastoempresa OWNER TO postgres;

--
-- Name: gastoempresa_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gastoempresa_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.gastoempresa_sequence OWNER TO postgres;

--
-- Name: gastoempresa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gastoempresa_sequence', 34, true);


--
-- Name: gastoempresaitem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE gastoempresaitem (
    id bigint NOT NULL,
    valor double precision,
    gasto_id bigint,
    gastoempresa_id bigint
);


ALTER TABLE public.gastoempresaitem OWNER TO postgres;

--
-- Name: gastoempresaitem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE gastoempresaitem_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.gastoempresaitem_sequence OWNER TO postgres;

--
-- Name: gastoempresaitem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gastoempresaitem_sequence', 30, true);


--
-- Name: ghe; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ghe (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.ghe OWNER TO postgres;

--
-- Name: ghe_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ghe_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ghe_sequence OWNER TO postgres;

--
-- Name: ghe_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ghe_sequence', 157, true);


--
-- Name: ghehistorico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ghehistorico (
    id bigint NOT NULL,
    insalubre boolean NOT NULL,
    periculoso boolean NOT NULL,
    data date,
    ambiente_id bigint,
    ghe_id bigint
);


ALTER TABLE public.ghehistorico OWNER TO postgres;

--
-- Name: ghehistorico_funcao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ghehistorico_funcao (
    ghehistorico_id bigint NOT NULL,
    funcoes_id bigint NOT NULL
);


ALTER TABLE public.ghehistorico_funcao OWNER TO postgres;

--
-- Name: ghehistorico_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ghehistorico_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ghehistorico_sequence OWNER TO postgres;

--
-- Name: ghehistorico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ghehistorico_sequence', 137, true);


--
-- Name: grupogasto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE grupogasto (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.grupogasto OWNER TO postgres;

--
-- Name: grupogasto_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE grupogasto_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.grupogasto_sequence OWNER TO postgres;

--
-- Name: grupogasto_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('grupogasto_sequence', 37, true);


--
-- Name: grupoocupacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE grupoocupacional (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.grupoocupacional OWNER TO postgres;

--
-- Name: grupoocupacional_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE grupoocupacional_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.grupoocupacional_sequence OWNER TO postgres;

--
-- Name: grupoocupacional_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('grupoocupacional_sequence', 84, true);


--
-- Name: historicoambiente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicoambiente (
    id bigint NOT NULL,
    descricao text,
    data date,
    datainativo date,
    ambiente_id bigint
);


ALTER TABLE public.historicoambiente OWNER TO postgres;

--
-- Name: historicoambiente_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicoambiente_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicoambiente_sequence OWNER TO postgres;

--
-- Name: historicoambiente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicoambiente_sequence', 20, true);


--
-- Name: historicobeneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicobeneficio (
    id bigint NOT NULL,
    data date,
    valor double precision,
    paracolaborador double precision,
    paradependentedireto double precision,
    paradependenteindireto double precision,
    beneficio_id bigint
);


ALTER TABLE public.historicobeneficio OWNER TO postgres;

--
-- Name: historicobeneficio_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicobeneficio_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicobeneficio_sequence OWNER TO postgres;

--
-- Name: historicobeneficio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicobeneficio_sequence', 44, true);


--
-- Name: historicocandidato; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicocandidato (
    id bigint NOT NULL,
    data date,
    responsavel character varying(100),
    observacao text,
    apto boolean NOT NULL,
    etapaseletiva_id bigint,
    candidatosolicitacao_id bigint
);


ALTER TABLE public.historicocandidato OWNER TO postgres;

--
-- Name: historicocandidato_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicocandidato_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicocandidato_sequence OWNER TO postgres;

--
-- Name: historicocandidato_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicocandidato_sequence', 75, true);


--
-- Name: historicocolaborador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicocolaborador (
    id bigint NOT NULL,
    salario double precision,
    data date,
    motivo character varying(5),
    gfip character varying(2),
    colaborador_id bigint,
    areaorganizacional_id bigint,
    historicoanterior_id bigint,
    funcao_id bigint,
    ambiente_id bigint,
    estabelecimento_id bigint,
    tiposalario integer,
    indice_id bigint,
    faixasalarial_id bigint,
    quantidadeindice double precision,
    reajustecolaborador_id bigint,
    status integer
);


ALTER TABLE public.historicocolaborador OWNER TO postgres;

--
-- Name: historicocolaborador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicocolaborador_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicocolaborador_sequence OWNER TO postgres;

--
-- Name: historicocolaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicocolaborador_sequence', 473, true);


--
-- Name: historicocolaboradorbeneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicocolaboradorbeneficio (
    id bigint NOT NULL,
    data date,
    dataate date,
    colaborador_id bigint
);


ALTER TABLE public.historicocolaboradorbeneficio OWNER TO postgres;

--
-- Name: historicocolaboradorbeneficio_beneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicocolaboradorbeneficio_beneficio (
    historicocolaboradorbeneficio_id bigint NOT NULL,
    beneficios_id bigint NOT NULL
);


ALTER TABLE public.historicocolaboradorbeneficio_beneficio OWNER TO postgres;

--
-- Name: historicocolaboradorbeneficio_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicocolaboradorbeneficio_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicocolaboradorbeneficio_sequence OWNER TO postgres;

--
-- Name: historicocolaboradorbeneficio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicocolaboradorbeneficio_sequence', 80, true);


--
-- Name: historicofuncao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicofuncao (
    id bigint NOT NULL,
    data date,
    descricao text,
    funcao_id bigint
);


ALTER TABLE public.historicofuncao OWNER TO postgres;

--
-- Name: historicofuncao_exame; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicofuncao_exame (
    historicofuncao_id bigint NOT NULL,
    exames_id bigint NOT NULL
);


ALTER TABLE public.historicofuncao_exame OWNER TO postgres;

--
-- Name: historicofuncao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicofuncao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicofuncao_sequence OWNER TO postgres;

--
-- Name: historicofuncao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicofuncao_sequence', 112, true);


--
-- Name: idioma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE idioma (
    id bigint NOT NULL,
    nome character varying(100)
);


ALTER TABLE public.idioma OWNER TO postgres;

--
-- Name: idioma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE idioma_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.idioma_sequence OWNER TO postgres;

--
-- Name: idioma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('idioma_sequence', 40, true);


--
-- Name: indice; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indice (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    codigoac character varying(12)
);


ALTER TABLE public.indice OWNER TO postgres;

--
-- Name: indice_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indice_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.indice_sequence OWNER TO postgres;

--
-- Name: indice_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indice_sequence', 22, true);


--
-- Name: indicehistorico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indicehistorico (
    id bigint NOT NULL,
    data date,
    valor double precision,
    indice_id bigint
);


ALTER TABLE public.indicehistorico OWNER TO postgres;

--
-- Name: indicehistorico_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indicehistorico_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.indicehistorico_sequence OWNER TO postgres;

--
-- Name: indicehistorico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indicehistorico_sequence', 15, true);


--
-- Name: ltcat; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ltcat (
    id bigint NOT NULL,
    data date,
    laudotecnico text,
    descricaoarquitetonica text,
    introducaoambientes text,
    descricaosetores text,
    metodologia text,
    fundamentacaolegal text,
    introducaoavaliacaoriscos text,
    introducaoriscoruido text,
    introducaoriscocalor text,
    introducaoriscoquimico text,
    introducaoriscoergonomico text,
    introducaoriscoacidente text,
    tempoexposicao text,
    descricaoepc text,
    introducaoepi text,
    conclusao text,
    dadoslevantamentoresponsabilidade text,
    bloqueado boolean NOT NULL,
    empresa_id bigint,
    tabelaruido_id bigint,
    tabelatemperatura_id bigint
);


ALTER TABLE public.ltcat OWNER TO postgres;

--
-- Name: ltcat_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ltcat_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ltcat_sequence OWNER TO postgres;

--
-- Name: ltcat_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ltcat_sequence', 81, true);


--
-- Name: medicocoordenador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE medicocoordenador (
    id bigint NOT NULL,
    nome character varying(100),
    apartirde date,
    crm character varying(20),
    empresa_id bigint
);


ALTER TABLE public.medicocoordenador OWNER TO postgres;

--
-- Name: medicocoordenador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE medicocoordenador_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.medicocoordenador_sequence OWNER TO postgres;

--
-- Name: medicocoordenador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('medicocoordenador_sequence', 41, true);


--
-- Name: mensagem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mensagem (
    id bigint NOT NULL,
    remetente character varying(100),
    data timestamp without time zone,
    texto text,
    link character varying(200)
);


ALTER TABLE public.mensagem OWNER TO postgres;

--
-- Name: mensagem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mensagem_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.mensagem_sequence OWNER TO postgres;

--
-- Name: mensagem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mensagem_sequence', 3, true);


--
-- Name: motivodemissao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE motivodemissao (
    id bigint NOT NULL,
    motivo character varying(50),
    empresa_id bigint
);


ALTER TABLE public.motivodemissao OWNER TO postgres;

--
-- Name: motivodemissao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE motivodemissao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.motivodemissao_sequence OWNER TO postgres;

--
-- Name: motivodemissao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('motivodemissao_sequence', 35, true);


--
-- Name: motivosolicitacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE motivosolicitacao (
    id bigint NOT NULL,
    descricao character varying(100)
);


ALTER TABLE public.motivosolicitacao OWNER TO postgres;

--
-- Name: motivosolicitacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE motivosolicitacao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.motivosolicitacao_sequence OWNER TO postgres;

--
-- Name: motivosolicitacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('motivosolicitacao_sequence', 37, true);


--
-- Name: ocorrencia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ocorrencia (
    id bigint NOT NULL,
    descricao character varying(100),
    pontuacao integer NOT NULL,
    empresa_id bigint
);


ALTER TABLE public.ocorrencia OWNER TO postgres;

--
-- Name: ocorrencia_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ocorrencia_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ocorrencia_sequence OWNER TO postgres;

--
-- Name: ocorrencia_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ocorrencia_sequence', 48, true);


--
-- Name: papel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE papel (
    id bigint NOT NULL,
    codigo character varying(50),
    nome character varying(100),
    url character varying(120),
    ordem integer NOT NULL,
    menu boolean NOT NULL,
    papelmae_id bigint
);


ALTER TABLE public.papel OWNER TO postgres;

--
-- Name: papel_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE papel_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.papel_sequence OWNER TO postgres;

--
-- Name: papel_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('papel_sequence', 414, true);


--
-- Name: parametrosdosistema; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE parametrosdosistema (
    id bigint NOT NULL,
    mailnaoaptos text,
    appurl character varying(150),
    appcontext character varying(100),
    appversao character varying(20),
    emailsmtp character varying(100),
    emailport integer NOT NULL,
    emailuser character varying(50),
    emailpass character varying(50),
    atualizadorpath character varying(150),
    servidorremprot character varying(50),
    diaslembretepesquisa character varying(20),
    enviaremail boolean,
    atualizadosucesso boolean,
    perfilpadrao_id bigint
);


ALTER TABLE public.parametrosdosistema OWNER TO postgres;

--
-- Name: parametrosdosistema_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE parametrosdosistema_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.parametrosdosistema_sequence OWNER TO postgres;

--
-- Name: parametrosdosistema_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('parametrosdosistema_sequence', 26, true);


--
-- Name: pcmso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pcmso (
    id bigint NOT NULL,
    data date,
    metodologia text,
    cronograma text,
    empresa_id bigint
);


ALTER TABLE public.pcmso OWNER TO postgres;

--
-- Name: pcmso_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pcmso_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.pcmso_sequence OWNER TO postgres;

--
-- Name: pcmso_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pcmso_sequence', 26, true);


--
-- Name: perfil; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil (
    id bigint NOT NULL,
    nome character varying(100)
);


ALTER TABLE public.perfil OWNER TO postgres;

--
-- Name: perfil_papel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil_papel (
    perfil_id bigint NOT NULL,
    papeis_id bigint NOT NULL
);


ALTER TABLE public.perfil_papel OWNER TO postgres;

--
-- Name: perfil_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE perfil_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.perfil_sequence OWNER TO postgres;

--
-- Name: perfil_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('perfil_sequence', 24, true);


--
-- Name: pergunta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

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
);


ALTER TABLE public.pergunta OWNER TO postgres;

--
-- Name: pergunta_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pergunta_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.pergunta_sequence OWNER TO postgres;

--
-- Name: pergunta_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pergunta_sequence', 379, true);


--
-- Name: pesquisa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pesquisa (
    id bigint NOT NULL,
    questionario_id bigint
);


ALTER TABLE public.pesquisa OWNER TO postgres;

--
-- Name: pesquisa_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pesquisa_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.pesquisa_sequence OWNER TO postgres;

--
-- Name: pesquisa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pesquisa_sequence', 69, true);


--
-- Name: ppra; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ppra (
    id bigint NOT NULL,
    data date,
    folhaapresentacao text,
    elaborador text,
    docbase text,
    relatorioavalicao text,
    registros text,
    responsabilidade text,
    empresa_id bigint
);


ALTER TABLE public.ppra OWNER TO postgres;

--
-- Name: ppra_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ppra_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ppra_sequence OWNER TO postgres;

--
-- Name: ppra_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ppra_sequence', 21, true);


--
-- Name: prioridadetreinamento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE prioridadetreinamento (
    id bigint NOT NULL,
    descricao character varying(100),
    sigla character varying(5),
    numero integer NOT NULL
);


ALTER TABLE public.prioridadetreinamento OWNER TO postgres;

--
-- Name: prioridadetreinamento_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE prioridadetreinamento_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.prioridadetreinamento_sequence OWNER TO postgres;

--
-- Name: prioridadetreinamento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('prioridadetreinamento_sequence', 18, true);


--
-- Name: questionario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE questionario (
    id bigint NOT NULL,
    titulo text,
    cabecalho text,
    datainicio date,
    datafim date,
    liberado boolean,
    anonimo boolean,
    aplicarporaspecto boolean,
    tipo integer,
    empresa_id bigint
);


ALTER TABLE public.questionario OWNER TO postgres;

--
-- Name: questionario_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE questionario_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.questionario_sequence OWNER TO postgres;

--
-- Name: questionario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('questionario_sequence', 262, true);


--
-- Name: reajustecolaborador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reajustecolaborador (
    id bigint NOT NULL,
    salarioatual double precision,
    salarioproposto double precision,
    colaborador_id bigint,
    tabelareajustecolaborador_id bigint,
    areaorganizacionalatual_id bigint,
    areaorganizacionalproposta_id bigint,
    funcaoatual_id bigint,
    funcaoproposta_id bigint,
    ambienteatual_id bigint,
    ambienteproposto_id bigint,
    estabelecimentoatual_id bigint,
    estabelecimentoproposto_id bigint,
    tiposalarioproposto integer,
    indiceproposto_id bigint,
    tiposalarioatual integer,
    indiceatual_id bigint,
    faixasalarialatual_id bigint,
    faixasalarialproposta_id bigint,
    quantidadeindiceatual double precision,
    quantidadeindiceproposto double precision
);


ALTER TABLE public.reajustecolaborador OWNER TO postgres;

--
-- Name: reajustecolaborador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reajustecolaborador_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.reajustecolaborador_sequence OWNER TO postgres;

--
-- Name: reajustecolaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('reajustecolaborador_sequence', 62, true);


--
-- Name: realizacaoexame; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE realizacaoexame (
    id bigint NOT NULL,
    data date,
    observacao text,
    resultado integer NOT NULL,
    natureza integer NOT NULL,
    empresa_id bigint,
    colaborador_id bigint,
    exame_id bigint
);


ALTER TABLE public.realizacaoexame OWNER TO postgres;

--
-- Name: realizacaoexame_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE realizacaoexame_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.realizacaoexame_sequence OWNER TO postgres;

--
-- Name: realizacaoexame_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('realizacaoexame_sequence', 56, true);


--
-- Name: resposta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resposta (
    id bigint NOT NULL,
    texto text,
    ordem integer NOT NULL,
    pergunta_id bigint
);


ALTER TABLE public.resposta OWNER TO postgres;

--
-- Name: resposta_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE resposta_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.resposta_sequence OWNER TO postgres;

--
-- Name: resposta_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('resposta_sequence', 115, true);


--
-- Name: risco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE risco (
    id bigint NOT NULL,
    descricao character varying(100),
    danos text,
    trajetoria character varying(100),
    gruporisco character varying(5),
    tabelarisco integer,
    empresa_id bigint
);


ALTER TABLE public.risco OWNER TO postgres;

--
-- Name: risco_epi; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE risco_epi (
    risco_id bigint NOT NULL,
    epis_id bigint NOT NULL
);


ALTER TABLE public.risco_epi OWNER TO postgres;

--
-- Name: risco_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE risco_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.risco_sequence OWNER TO postgres;

--
-- Name: risco_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('risco_sequence', 32, true);


--
-- Name: riscoghe; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE riscoghe (
    id bigint NOT NULL,
    fonte text,
    trajetoria text,
    propagacao text,
    epieficaz boolean,
    epceficaz boolean,
    ghehistorico_id bigint,
    risco_id bigint
);


ALTER TABLE public.riscoghe OWNER TO postgres;

--
-- Name: riscoghe_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE riscoghe_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.riscoghe_sequence OWNER TO postgres;

--
-- Name: riscoghe_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('riscoghe_sequence', 30, true);


--
-- Name: solicitacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacao (
    id bigint NOT NULL,
    data date,
    dataencerramento date,
    quantidade integer NOT NULL,
    vinculo character varying(5),
    escolaridade character varying(5),
    experiencia text,
    remuneracao double precision,
    idademinima integer NOT NULL,
    idademaxima integer NOT NULL,
    horario character varying(30),
    sexo character varying(5),
    infocomplementares text,
    encerrada boolean NOT NULL,
    liberada boolean NOT NULL,
    suspensa boolean NOT NULL,
    obssuspensao text,
    motivosolicitacao_id bigint,
    areaorganizacional_id bigint,
    estabelecimento_id bigint,
    solicitante_id bigint,
    cidade_id bigint,
    funcao_id bigint,
    ambiente_id bigint,
    empresa_id bigint,
    faixasalarial_id bigint
);


ALTER TABLE public.solicitacao OWNER TO postgres;

--
-- Name: solicitacao_bairro; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacao_bairro (
    solicitacao_id bigint NOT NULL,
    bairros_id bigint NOT NULL
);


ALTER TABLE public.solicitacao_bairro OWNER TO postgres;

--
-- Name: solicitacao_beneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacao_beneficio (
    solicitacao_id bigint NOT NULL,
    beneficios_id bigint NOT NULL
);


ALTER TABLE public.solicitacao_beneficio OWNER TO postgres;

--
-- Name: solicitacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE solicitacao_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.solicitacao_sequence OWNER TO postgres;

--
-- Name: solicitacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('solicitacao_sequence', 203, true);


--
-- Name: solicitacaobds; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacaobds (
    id bigint NOT NULL,
    data date,
    tipo character(1) NOT NULL,
    escolaridade character(1) NOT NULL,
    experiencia text,
    sexo character(1) NOT NULL,
    idademinima integer NOT NULL,
    idademaxima integer NOT NULL,
    observacao text,
    cargo_id bigint,
    areaorganizacional_id bigint
);


ALTER TABLE public.solicitacaobds OWNER TO postgres;

--
-- Name: solicitacaobds_empresabds; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacaobds_empresabds (
    solicitacaobds_id bigint NOT NULL,
    empresasbdss_id bigint NOT NULL
);


ALTER TABLE public.solicitacaobds_empresabds OWNER TO postgres;

--
-- Name: solicitacaobds_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE solicitacaobds_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.solicitacaobds_sequence OWNER TO postgres;

--
-- Name: solicitacaobds_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('solicitacaobds_sequence', 20, true);


--
-- Name: tabelailuminamento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tabelailuminamento (
    id bigint NOT NULL,
    data date,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.tabelailuminamento OWNER TO postgres;

--
-- Name: tabelailuminamento_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelailuminamento_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelailuminamento_sequence OWNER TO postgres;

--
-- Name: tabelailuminamento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelailuminamento_sequence', 26, true);


--
-- Name: tabelailuminamentoitem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tabelailuminamentoitem (
    id bigint NOT NULL,
    valorlevantado double precision,
    valorreferencia double precision,
    tecnicautilizada integer NOT NULL,
    ghe_id bigint,
    tabelailuminamento_id bigint
);


ALTER TABLE public.tabelailuminamentoitem OWNER TO postgres;

--
-- Name: tabelailuminamentoitem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelailuminamentoitem_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelailuminamentoitem_sequence OWNER TO postgres;

--
-- Name: tabelailuminamentoitem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelailuminamentoitem_sequence', 20, true);


--
-- Name: tabelareajustecolaborador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tabelareajustecolaborador (
    id bigint NOT NULL,
    nome character varying(100),
    data date,
    observacao text,
    aprovada boolean NOT NULL,
    empresa_id bigint
);


ALTER TABLE public.tabelareajustecolaborador OWNER TO postgres;

--
-- Name: tabelareajustecolaborador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelareajustecolaborador_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelareajustecolaborador_sequence OWNER TO postgres;

--
-- Name: tabelareajustecolaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelareajustecolaborador_sequence', 66, true);


--
-- Name: tabelaruido; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tabelaruido (
    id bigint NOT NULL,
    data date,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.tabelaruido OWNER TO postgres;

--
-- Name: tabelaruido_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelaruido_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelaruido_sequence OWNER TO postgres;

--
-- Name: tabelaruido_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelaruido_sequence', 51, true);


--
-- Name: tabelaruidoitem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tabelaruidoitem (
    id bigint NOT NULL,
    nps double precision,
    npsatenuado double precision,
    tecnicautilizada integer NOT NULL,
    tabelaruido_id bigint,
    ghe_id bigint
);


ALTER TABLE public.tabelaruidoitem OWNER TO postgres;

--
-- Name: tabelaruidoitem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelaruidoitem_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelaruidoitem_sequence OWNER TO postgres;

--
-- Name: tabelaruidoitem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelaruidoitem_sequence', 45, true);


--
-- Name: tabelasalario_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelasalario_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelasalario_sequence OWNER TO postgres;

--
-- Name: tabelasalario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelasalario_sequence', 28, true);


--
-- Name: tabelatemperatura; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tabelatemperatura (
    id bigint NOT NULL,
    nome character varying(100),
    data date,
    empresa_id bigint
);


ALTER TABLE public.tabelatemperatura OWNER TO postgres;

--
-- Name: tabelatemperatura_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelatemperatura_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelatemperatura_sequence OWNER TO postgres;

--
-- Name: tabelatemperatura_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelatemperatura_sequence', 51, true);


--
-- Name: tabelatemperaturaitem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tabelatemperaturaitem (
    id bigint NOT NULL,
    ibutg double precision,
    atividade character varying(50),
    limitetolerancia double precision,
    tecnicautilizada integer NOT NULL,
    tabelatemperatura_id bigint,
    ghe_id bigint
);


ALTER TABLE public.tabelatemperaturaitem OWNER TO postgres;

--
-- Name: tabelatemperaturaitem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tabelatemperaturaitem_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelatemperaturaitem_sequence OWNER TO postgres;

--
-- Name: tabelatemperaturaitem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelatemperaturaitem_sequence', 40, true);


--
-- Name: tipoepi; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tipoepi (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.tipoepi OWNER TO postgres;

--
-- Name: tipoepi_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tipoepi_sequence
    START WITH 3
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tipoepi_sequence OWNER TO postgres;

--
-- Name: tipoepi_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tipoepi_sequence', 3, false);


--
-- Name: turma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE turma (
    id bigint NOT NULL,
    descricao character varying(100),
    cargahoraria integer,
    instrutor character varying(100),
    custo double precision,
    dataprevini date,
    dataprevfim date,
    datarealizaini date,
    datarealizafim date,
    empresa_id bigint,
    curso_id bigint
);


ALTER TABLE public.turma OWNER TO postgres;

--
-- Name: turma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE turma_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.turma_sequence OWNER TO postgres;

--
-- Name: turma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('turma_sequence', 110, true);


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id bigint NOT NULL,
    nome character varying(100),
    "login" character varying(30),
    senha character varying(30),
    acessosistema boolean NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: usuario_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuario_sequence OWNER TO postgres;

--
-- Name: usuario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_sequence', 118, true);


--
-- Name: usuarioempresa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarioempresa (
    id bigint NOT NULL,
    usuario_id bigint,
    perfil_id bigint,
    empresa_id bigint
);


ALTER TABLE public.usuarioempresa OWNER TO postgres;

--
-- Name: usuarioempresa_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuarioempresa_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuarioempresa_sequence OWNER TO postgres;

--
-- Name: usuarioempresa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuarioempresa_sequence', 47, true);


--
-- Name: usuariomensagem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuariomensagem (
    id bigint NOT NULL,
    usuario_id bigint,
    mensagem_id bigint,
    empresa_id bigint,
    lida boolean NOT NULL
);


ALTER TABLE public.usuariomensagem OWNER TO postgres;

--
-- Name: usuariomensagem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuariomensagem_sequence
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuariomensagem_sequence OWNER TO postgres;

--
-- Name: usuariomensagem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuariomensagem_sequence', 11, true);


--
-- Data for Name: ambiente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ambiente (id, nome, empresa_id) FROM stdin;
1	Sala de Desenvolvimento	1
2	Sala do Suporte	1
\.


--
-- Data for Name: ambiente_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ambiente_areaorganizacional (ambiente_id, areasorganizacionais_id) FROM stdin;
\.


--
-- Data for Name: anexo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY anexo (id, nome, observacao, url, origem, origemid) FROM stdin;
\.


--
-- Data for Name: anuncio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY anuncio (id, titulo, cabecalho, informacoes, mostraconhecimento, mostrabeneficio, mostrasalario, mostracargo, mostrasexo, mostraidade, solicitacao_id) FROM stdin;
\.


--
-- Data for Name: areaformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areaformacao (id, nome) FROM stdin;
1	Administrativa
2	Administrativo Comercial
3	Administrativo/ Operacional
4	Agronômica/ Engenharia Agronômica/ Agribusiness
5	Agropecuária/ Veterinária/ Agrobusiness
6	Arquitetura/ Decoração/ Urbanismo
7	Artes
8	Artes Gráficas
9	Atendimento ao Cliente/ Call Center/ Telemarketing
10	Automação Industrial/ Comercial
11	Aviação/ Aeronáutica
12	Bancária/ Private Corporate Bank
13	Biblioteconomia
14	Biologia
15	Biotecnologia/ Biomédicas/ Bioquímica
16	Comercial/ Vendas
17	Comércio Exterior/ Trade/ Importação/ Exportação
18	Compras
19	Construção Civil/ Engenharia Civil
20	Contabilidade
21	Departamento Pessoal
22	Desenho Industrial
23	Economia
24	Educação/ Ensino/ Idiomas
25	Enfermagem
26	Engenharia de Alimentos
27	Engenharia de Materiais
28	Engenharia de Minas
29	Engenharia de Produção/ Industrial
30	Engenharia Elétrica/ Eletrônica
31	Engenharia Mecânica/ Mecatrônica
32	Esportes/ Educação Física
33	Estatística /Matemática /Atuária
34	Estética Corporal
35	Farmácia
36	Financeira/ Administrativa
37	Fisioterapia
38	Fonoaudiologia
39	Geologia /Engenharia Agrimensura
40	Hotelaria/ Turismo
41	Industrial
42	Informática /TI / Engenharia da Computação
43	Internet/ E-Commerce/ E-Business/ Web/ Web Designer
44	Jornalismo
45	Jurídica
46	Logística/ Suprimentos
47	Manutenção
48	Marketing
49	Médico/ Hospitalar
50	Meio Ambiente/ Ecologia/ Engenharia de Meio Ambiente
51	Moda
52	Nutrição
53	Odontologia 
54	Psicologia Clínica/ Hospitalar
55	Publicidade e Propaganda
56	Qualidade
57	Química/ Engenharia Química
58	Recursos Humanos
59	Relações Internacionais
60	Relações Públicas
61	Restaurante
62	Secretariado
63	Segurança do Trabalho
64	Segurança Patrimonial
65	Seguros
66	Serviço Social
67	Técnica
68	Técnico-Comercial
69	Telecomunicações/ Engenharia de Telecomunicações
70	Terapia Ocupacional
71	Têxtil/ Engenharia Têxtil
72	Tradutor/ Intérprete
73	Transportes
74	Zootecnia
\.


--
-- Data for Name: areainteresse; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areainteresse (id, nome, observacao, empresa_id) FROM stdin;
1	Java	Java basico	1
2	MySQL	Mysql avançado	1
3	WebWork	Fundamentos webwork	1
\.


--
-- Data for Name: areainteresse_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areainteresse_areaorganizacional (areasinteresse_id, areasorganizacionais_id) FROM stdin;
\.


--
-- Data for Name: areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areaorganizacional (id, nome, codigoac, areamae_id, responsavel_id, empresa_id) FROM stdin;
1	Desenvolvimento	\N	\N	\N	1
2	Desenvolvimento Java	\N	1	\N	1
3	Desenvolvimento Delphi	\N	1	\N	1
4	Suporte	\N	\N	\N	1
5	Suporte Fiscal	\N	4	\N	1
6	Suporte Contabil	\N	4	\N	1
7	Suporte Corporativo	\N	4	\N	1
\.


--
-- Data for Name: aspecto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY aspecto (id, nome, questionario_id) FROM stdin;
25	Liderança	1
26	Comunicação	1
41	Chefia	3
42	Liderança	3
43	Trabalho	53
\.


--
-- Data for Name: auditoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY auditoria (id, data, operacao, entidade, dados, usuario_id, empresa_id) FROM stdin;
1	2008-11-18 11:49:03.134	update	ParametrosDoSistema	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.ParametrosDoSistema\nValor: ID 1\n	1	1
2	2008-11-18 13:25:06.911	save	ColaboradorQuestionario	[PARÂMETROS]\nTipo: com.fortes.rh.model.pesquisa.ColaboradorQuestionario\nValor: ID 48\n\n[RESULTADO]\nTipo: com.fortes.rh.model.pesquisa.ColaboradorQuestionario\nValor: ID 48\n	1	1
3	2008-11-18 13:41:14.785	update	ParametrosDoSistema	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.ParametrosDoSistema\nValor: ID 1\n	1	1
4	2008-11-18 14:03:15.762	update	ParametrosDoSistema	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.ParametrosDoSistema\nValor: ID 1\n	1	1
5	2008-11-18 14:13:10.841	update	ParametrosDoSistema	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.ParametrosDoSistema\nValor: ID 1\n	1	1
6	2008-11-18 14:33:38.675	save	Questionario	[PARÂMETROS]\nTipo: com.fortes.rh.model.pesquisa.Questionario\nValor: ID 53\n\n[RESULTADO]\nTipo: com.fortes.rh.model.pesquisa.Questionario\nValor: ID 53\n	1	1
7	2008-11-18 14:33:38.675	save	Entrevista	[PARÂMETROS]\nTipo: com.fortes.rh.model.pesquisa.Entrevista\nValor: ID 11\nTipo: com.fortes.rh.model.pesquisa.Questionario\nValor: ID 53\nTipo: com.fortes.rh.model.geral.Empresa\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.pesquisa.Entrevista\nValor: ID 11\n	1	1
8	2008-11-21 15:33:04.781	update	Colaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.Colaborador\nValor: ID 3\n	1	1
9	2008-12-03 09:19:52.337	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
10	2008-12-03 09:19:52.337	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
11	2008-12-03 09:19:53.728	update	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\n	1	1
12	2008-12-03 09:19:55.509	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
13	2008-12-03 09:19:56.978	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
14	2008-12-03 09:21:09.056	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
15	2008-12-03 09:21:09.056	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
16	2008-12-03 09:21:10.04	update	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\n	1	1
17	2008-12-03 09:21:11.712	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
18	2008-12-03 09:21:13.009	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
19	2008-12-03 09:21:15.134	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
21	2008-12-03 09:22:51.493	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
22	2008-12-03 09:22:54.556	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 2\n	1	1
23	2008-12-03 09:23:00.072	remove	Indice	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
24	2008-12-03 09:26:14.228	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
25	2008-12-03 09:26:14.228	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
26	2008-12-03 09:26:15.243	update	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\n	1	1
27	2008-12-03 09:26:16.90	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
28	2008-12-03 09:26:18.275	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
29	2008-12-03 09:26:20.275	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
30	2008-12-03 09:26:21.884	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
31	2008-12-03 09:26:21.947	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 2\n	1	1
32	2008-12-03 09:26:22.40	remove	Indice	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
33	2008-12-03 09:27:43.587	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
34	2008-12-03 09:27:43.587	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
35	2008-12-03 09:27:44.587	update	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\n	1	1
36	2008-12-03 09:27:46.212	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
37	2008-12-03 09:27:47.493	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
38	2008-12-03 09:27:49.556	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
39	2008-12-03 09:27:51.04	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
40	2008-12-03 09:27:51.087	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 2\n	1	1
41	2008-12-03 09:27:51.603	remove	Indice	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
42	2008-12-03 09:34:54.103	save	FaixaSalarialHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarialHistorico\nValor: ID 17\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarial\nValor: ID 131\n	1	1
43	2008-12-03 09:35:38.759	remove	FaixaSalarialHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 17\n	1	1
44	2008-12-03 09:39:15.197	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
45	2008-12-03 09:39:15.197	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
46	2008-12-03 09:39:16.04	update	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\n	1	1
47	2008-12-03 09:39:17.525	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
48	2008-12-03 09:39:19.025	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
49	2008-12-03 09:39:22.806	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
50	2008-12-03 09:39:24.259	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
51	2008-12-03 09:39:24.415	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 2\n	1	1
52	2008-12-03 09:39:24.79	remove	Indice	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 1\n	1	1
53	2008-12-03 09:39:25.993	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 3\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 3\n	1	1
54	2008-12-03 09:39:25.993	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 2\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 3\n	1	1
55	2008-12-03 09:44:49.915	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
56	2008-12-03 09:44:49.915	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
57	2008-12-03 09:46:04.103	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
58	2008-12-03 09:46:04.103	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 2\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
59	2008-12-03 09:46:05.056	update	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\n	1	1
60	2008-12-03 09:46:06.618	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
61	2008-12-03 09:46:07.915	update	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 2\n	1	1
62	2008-12-03 09:46:09.837	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 3\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 3\n	1	1
63	2008-12-03 09:46:11.228	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 2\n	1	1
64	2008-12-03 09:46:11.306	remove	IndiceHistorico	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 3\n	1	1
65	2008-12-03 09:46:11.806	remove	Indice	[PARÂMETROS]\nTipo: java.lang.Long\nValor: 2\n	1	1
66	2008-12-03 09:49:00.556	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
67	2008-12-03 09:49:00.556	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 1\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 1\n	1	1
68	2008-12-03 11:46:14.116	save	TabelaReajusteColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.TabelaReajusteColaborador\nValor: ID 49\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.TabelaReajusteColaborador\nValor: ID 49\n	1	1
69	2008-12-03 11:59:41.756	save	Bairro	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.Bairro\nValor: ID 66\n\n[RESULTADO]\nTipo: com.fortes.rh.model.geral.Bairro\nValor: ID 66\n	1	1
70	2008-12-03 11:59:41.928	update	HistoricoColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 1\n	1	1
71	2008-12-03 11:59:41.928	update	Colaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.Colaborador\nValor: ID 1\nTipo: com.fortes.rh.model.geral.AreaOrganizacional\nValor: ID 2\nTipo: com.fortes.rh.model.geral.Estabelecimento\nValor: ID 1\nTipo: java.lang.Double\nValor: 5555.0\nTipo: com.fortes.rh.model.cargosalario.FaixaTabela\nValor: ID 3\nTipo: java.util.ArrayList\nValor: []\nTipo: java.util.ArrayList\nValor: []\nTipo: java.util.ArrayList\nValor: []\nTipo: com.fortes.rh.model.geral.Empresa\nValor: ID 1\nTipo: java.lang.Boolean\nValor: true\n	1	1
72	2008-12-03 13:43:34.788	update	ParametrosDoSistema	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.ParametrosDoSistema\nValor: ID 1\n	1	1
73	2008-12-22 17:01:49.704	save	TabelaReajusteColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.TabelaReajusteColaborador\nValor: ID 50\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.TabelaReajusteColaborador\nValor: ID 50\n	1	1
74	2008-12-22 17:06:24.353	save	FaixaSalarialHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarialHistorico\nValor: ID 18\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarial\nValor: ID 3\n	1	1
75	2008-12-22 17:08:35.945	save	HistoricoColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 358\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 358\n	1	1
76	2008-12-22 17:09:09.664	save	FaixaSalarialHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarialHistorico\nValor: ID 19\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarial\nValor: ID 5\n	1	1
77	2008-12-22 17:12:05.095	save	FaixaSalarialHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarialHistorico\nValor: ID 20\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarial\nValor: ID 4\n	1	1
78	2008-12-22 17:38:17.706	save	FaixaSalarialHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarialHistorico\nValor: ID 21\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarial\nValor: ID 2\n	1	1
79	2008-12-22 17:38:40.871	save	FaixaSalarialHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarialHistorico\nValor: ID 22\nTipo: com.fortes.rh.model.cargosalario.FaixaSalarial\nValor: ID 1\n	1	1
80	2008-12-22 17:50:04.299	remove	ReajusteColaborador	[PARÂMETROS]\nTipo: Array\nValor:\n[\nTipo: java.lang.Long\nValor: 44\n]	1	1
81	2008-12-23 10:37:37.251	save	HistoricoColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 359\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 359\n	1	1
82	2009-02-10 16:36:20.729	save	HistoricoColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 360\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 360\n	1	1
83	2009-02-10 16:36:45.865	update	HistoricoColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 360\n	1	1
84	2009-02-10 16:36:45.865	update	Colaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.Colaborador\nValor: ID 534\nTipo: java.util.ArrayList\nValor: []\nTipo: java.util.ArrayList\nValor: []\nTipo: java.util.ArrayList\nValor: []\nTipo: com.fortes.rh.model.geral.Empresa\nValor: ID 1\nTipo: java.lang.Boolean\nValor: true\nTipo: java.lang.Double\nValor: 4242.33\n	1	1
85	2009-02-10 16:36:57.889	update	HistoricoColaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.HistoricoColaborador\nValor: ID 360\n	1	1
86	2009-02-10 16:36:57.889	update	Colaborador	[PARÂMETROS]\nTipo: com.fortes.rh.model.geral.Colaborador\nValor: ID 534\nTipo: java.util.ArrayList\nValor: []\nTipo: java.util.ArrayList\nValor: []\nTipo: java.util.ArrayList\nValor: []\nTipo: com.fortes.rh.model.geral.Empresa\nValor: ID 1\nTipo: java.lang.Boolean\nValor: true\nTipo: java.lang.Double\nValor: 4242.33\n	1	1
87	2009-02-11 13:53:01.225	save	IndiceHistorico	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 15\n\n[RESULTADO]\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 15\n	1	1
88	2009-02-11 13:53:01.272	save	Indice	[PARÂMETROS]\nTipo: com.fortes.rh.model.cargosalario.Indice\nValor: ID 22\nTipo: com.fortes.rh.model.cargosalario.IndiceHistorico\nValor: ID 15\n	1	1
89	2009-02-11 17:09:54.343	remove	ReajusteColaborador	[PARÂMETROS]\nTipo: Array\nValor:\n[\nTipo: java.lang.Long\nValor: 46\n]	1	1
\.


--
-- Data for Name: avaliacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY avaliacao (id, questionario_id) FROM stdin;
1	2
\.


--
-- Data for Name: bairro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bairro (id, nome, cidade_id) FROM stdin;
1	Aldeota	946
2	Bairro de Fátima	946
3	José Bonifácio	946
4	Alto do Bode	946
5	Floresta	946
66	V. M. Sátiro	946
\.


--
-- Data for Name: beneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY beneficio (id, nome, empresa_id) FROM stdin;
1	UNIMED - Uniplan	1
2	UNIMED - Multiplan	1
3	Academia B2	1
4	Odonto System	1
\.


--
-- Data for Name: candidato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato (id, nome, senha, name, contenttype, bytes, size, logradouro, numero, complemento, bairro, cep, ddd, fonefixo, fonecelular, email, cpf, pis, rg, naturalidade, pai, mae, conjuge, profissaopai, profissaomae, profissaoconjuge, conjugetrabalha, parentesamigos, qtdfilhos, sexo, datanascimento, escolaridade, estadocivil, pagapensao, quantidade, valor, possuiveiculo, numerohab, registro, emissao, vencimento, categoria, colocacao, pretencaosalarial, disponivel, blacklist, contratado, observacao, observacaoblacklist, cursos, dataatualizacao, origem, ocrtexto, uf_id, cidade_id, observacaorh, deficiencia, rgorgaoemissor, rguf_id, rgdataexpedicao, titeleitnumero, titeleitzona, titeleitsecao, certmilnumero, certmiltipo, certmilserie, ctpsnumero, ctpsserie, ctpsdv, ctpsuf_id, ctpsdataexpedicao, empresa_id) FROM stdin;
1	José Antonio da Silva	\N	\N	\N	\N	\N	Rua dos Pinheiros	222	\N	\N	\N	85	40040505	\N	franciscobarroso@grupofortes.com.br	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	0	M	\N	\N	\N	f	0	\N	f	\N	\N	\N	\N	\N	E	\N	t	f	f	\N	\N	\N	\N	C	\N	1	946	\N	0	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1
2	Antonio Selenium		\N	\N	\N	\N	rua teste	45		Aldeota	     -   	85	99999999		franciscobarroso@grupofortes.com.br	00000000000	\N	999999999999	Fortaleza							f		0	M	1975-10-11	09	01	f	0	0	f			\N	\N		E	0	t	f	f				2008-11-13	C	\N	1	946		0		\N	\N									\N	\N	\N	1
\.


--
-- Data for Name: candidato_areainteresse; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato_areainteresse (candidato_id, areasinteresse_id) FROM stdin;
1	1
1	2
1	3
\.


--
-- Data for Name: candidato_cargo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato_cargo (candidato_id, cargos_id) FROM stdin;
1	1
\.


--
-- Data for Name: candidato_conhecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato_conhecimento (candidato_id, conhecimentos_id) FROM stdin;
1	5
1	6
\.


--
-- Data for Name: candidatocurriculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidatocurriculo (id, curriculo, candidato_id) FROM stdin;
1	Texto do curriculo	1
\.


--
-- Data for Name: candidatoidioma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidatoidioma (id, nivel, candidato_id, idioma_id) FROM stdin;
1	I	1	1
2	I	1	2
3	I	1	3
\.


--
-- Data for Name: candidatosolicitacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidatosolicitacao (id, triagem, candidato_id, solicitacao_id) FROM stdin;
\.


--
-- Data for Name: cargo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo (id, nome, nomemercado, missao, competencias, responsabilidades, escolaridade, experiencia, recrutamento, selecao, observacao, cbocodigo, grupoocupacional_id, empresa_id) FROM stdin;
1	Desenvolvedor	Desenvolvedor	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	5	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	\N	1	1
2	Coordenador de Projeto	Coordenador de Projeto	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	6	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	\N	1	1
3	Gerente de Projeto	Gerente de Projeto	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	8	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	\N	1	1
4	Técnico de Suporte	Técnico de Suporte	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	5	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	\N	2	1
5	Consultor Técnico	Consultor Técnico	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	5	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	\N	2	1
6	Supervisor de Suporte	Supervisor de Suporte	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	5	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	Bla, Bla, Bla...	\N	2	1
\.


--
-- Data for Name: cargo_areaformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_areaformacao (cargo_id, areaformacaos_id) FROM stdin;
\.


--
-- Data for Name: cargo_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_areaorganizacional (cargo_id, areasorganizacionais_id) FROM stdin;
\.


--
-- Data for Name: cargo_conhecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_conhecimento (cargo_id, conhecimentos_id) FROM stdin;
\.


--
-- Data for Name: cat; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cat (id, data, numerocat, observacao, colaborador_id) FROM stdin;
\.


--
-- Data for Name: cidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cidade (id, nome, codigoac, uf_id) FROM stdin;
1	Alta Floresta D'Oeste	00015	23
2	Ariquemes	00023	23
3	Cabixi	00031	23
4	Cacoal	00049	23
5	Cerejeiras	00056	23
6	Colorado do Oeste	00064	23
7	Corumbiara	00072	23
8	Costa Marques	00080	23
9	Espigão D'Oeste	00098	23
10	Guajará-Mirim	00106	23
11	Jaru	00114	23
12	Ji-Paraná	00122	23
13	Machadinho D'Oeste	00130	23
14	Nova Brasilândia D'Oeste	00148	23
15	Ouro Preto do Oeste	00155	23
16	Pimenta Bueno	00189	23
17	Porto Velho	00205	23
18	Presidente Médici	00254	23
19	Rio Crespo	00262	23
20	Rolim de Moura	00288	23
21	Santa Luzia D'Oeste	00296	23
22	Vilhena	00304	23
23	São Miguel do Guaporé	00320	23
24	Nova Mamoré	00338	23
25	Alvorada D'Oeste	00346	23
26	Alto Alegre dos Parecis	00379	23
27	Alto Paraíso	00403	23
28	Buritis	00452	23
29	Novo Horizonte do Oeste	00502	23
30	Cacaulândia	00601	23
31	Campo Novo de Rondônia	00700	23
32	Candeias do Jamari	00809	23
33	Castanheiras	00908	23
34	Chupinguaia	00924	23
35	Cujubim	00940	23
36	Governador Jorge Teixeira	01005	23
37	Itapuã do Oeste	01104	23
38	Ministro Andreazza	01203	23
39	Mirante da Serra	01302	23
40	Monte Negro	01401	23
41	Nova União	01435	23
42	Parecis	01450	23
43	Pimenteiras do Oeste	01468	23
44	Primavera de Rondônia	01476	23
45	São Felipe D'Oeste	01484	23
46	São Francisco do Guaporé	01492	23
47	Seringueiras	01500	23
48	Teixeirópolis	01559	23
49	Theobroma	01609	23
50	Urupá	01708	23
51	Vale do Anari	01757	23
52	Vale do Paraíso	01807	23
53	Acrelândia	00013	8
54	Assis Brasil	00054	8
55	Brasiléia	00104	8
56	Bujari	00138	8
57	Capixaba	00179	8
58	Cruzeiro do Sul	00203	8
59	Epitaciolândia	00252	8
60	Feijó	00302	8
61	Jordão	00328	8
62	Mâncio Lima	00336	8
63	Manoel Urbano	00344	8
64	Marechal Thaumaturgo	00351	8
65	Plácido de Castro	00385	8
66	Porto Walter	00393	8
67	Rio Branco	00401	8
68	Rodrigues Alves	00427	8
69	Santa Rosa do Purus	00435	8
70	Senador Guiomard	00450	8
71	Sena Madureira	00500	8
72	Tarauacá	00609	8
73	Xapuri	00708	8
74	Porto Acre	00807	8
75	Alvarães	00029	10
76	Amaturá	00060	10
77	Anamã	00086	10
78	Anori	00102	10
79	Apuí	00144	10
80	Atalaia do Norte	00201	10
81	Autazes	00300	10
82	Barcelos	00409	10
83	Barreirinha	00508	10
84	Benjamin Constant	00607	10
85	Beruri	00631	10
86	Boa Vista do Ramos	00680	10
87	Boca do Acre	00706	10
88	Borba	00805	10
89	Caapiranga	00839	10
90	Canutama	00904	10
91	Carauari	01001	10
92	Careiro	01100	10
93	Careiro da Várzea	01159	10
94	Coari	01209	10
95	Codajás	01308	10
96	Eirunepé	01407	10
97	Envira	01506	10
98	Fonte Boa	01605	10
99	Guajará	01654	10
100	Humaitá	01704	10
101	Ipixuna	01803	10
102	Iranduba	01852	10
103	Itacoatiara	01902	10
104	Itamarati	01951	10
105	Itapiranga	02009	10
106	Japurá	02108	10
107	Juruá	02207	10
108	Jutaí	02306	10
109	Lábrea	02405	10
110	Manacapuru	02504	10
111	Manaquiri	02553	10
112	Manaus	02603	10
113	Manicoré	02702	10
114	Maraã	02801	10
115	Maués	02900	10
116	Nhamundá	03007	10
117	Nova Olinda do Norte	03106	10
118	Novo Airão	03205	10
119	Novo Aripuanã	03304	10
120	Parintins	03403	10
121	Pauini	03502	10
122	Presidente Figueiredo	03536	10
123	Rio Preto da Eva	03569	10
124	Santa Isabel do Rio Negro	03601	10
125	Santo Antônio do Içá	03700	10
126	São Gabriel da Cachoeira	03809	10
127	São Paulo de Olivença	03908	10
128	São Sebastião do Uatumã	03957	10
129	Silves	04005	10
130	Tabatinga	04062	10
131	Tapauá	04104	10
132	Tefé	04203	10
133	Tonantins	04237	10
134	Uarini	04260	10
135	Urucará	04302	10
136	Urucurituba	04401	10
137	Amajari	00027	24
138	Alto Alegre	00050	24
139	Boa Vista	00100	24
140	Bonfim	00159	24
141	Cantá	00175	24
142	Caracaraí	00209	24
143	Caroebe	00233	24
144	Iracema	00282	24
145	Mucajaí	00308	24
146	Normandia	00407	24
147	Pacaraima	00456	24
148	Rorainópolis	00472	24
149	São João da Baliza	00506	24
150	São Luiz	00605	24
151	Uiramutã	00704	24
152	Abaetetuba	00107	19
153	Abel Figueiredo	00131	19
154	Acará	00206	19
155	Afuá	00305	19
156	Água Azul do Norte	00347	19
157	Alenquer	00404	19
158	Almeirim	00503	19
159	Altamira	00602	19
160	Anajás	00701	19
161	Ananindeua	00800	19
162	Anapu	00859	19
163	Augusto Corrêa	00909	19
164	Aurora do Pará	00958	19
165	Aveiro	01006	19
166	Bagre	01105	19
167	Baião	01204	19
168	Bannach	01253	19
169	Barcarena	01303	19
170	Belém	01402	19
171	Belterra	01451	19
172	Benevides	01501	19
173	Bom Jesus do Tocantins	01576	19
174	Bonito	01600	19
175	Bragança	01709	19
176	Brasil Novo	01725	19
177	Brejo Grande do Araguaia	01758	19
178	Breu Branco	01782	19
179	Breves	01808	19
180	Bujaru	01907	19
181	Cachoeira do Piriá	01956	19
182	Cachoeira do Arari	02004	19
183	Cametá	02103	19
184	Canaã dos Carajás	02152	19
185	Capanema	02202	19
186	Capitão Poço	02301	19
187	Castanhal	02400	19
188	Chaves	02509	19
189	Colares	02608	19
190	Conceição do Araguaia	02707	19
191	Concórdia do Pará	02756	19
192	Cumaru do Norte	02764	19
193	Curionópolis	02772	19
194	Curralinho	02806	19
195	Curuá	02855	19
196	Curuçá	02905	19
197	Dom Eliseu	02939	19
198	Eldorado dos Carajás	02954	19
199	Faro	03002	19
200	Floresta do Araguaia	03044	19
201	Garrafão do Norte	03077	19
202	Goianésia do Pará	03093	19
203	Gurupá	03101	19
204	Igarapé-Açu	03200	19
205	Igarapé-Miri	03309	19
206	Inhangapi	03408	19
207	Ipixuna do Pará	03457	19
208	Irituia	03507	19
209	Itaituba	03606	19
210	Itupiranga	03705	19
211	Jacareacanga	03754	19
212	Jacundá	03804	19
213	Juruti	03903	19
214	Limoeiro do Ajuru	04000	19
215	Mãe do Rio	04059	19
216	Magalhães Barata	04109	19
217	Marabá	04208	19
218	Maracanã	04307	19
219	Marapanim	04406	19
220	Marituba	04422	19
221	Medicilândia	04455	19
222	Melgaço	04505	19
223	Mocajuba	04604	19
224	Moju	04703	19
225	Monte Alegre	04802	19
226	Muaná	04901	19
227	Nova Esperança do Piriá	04950	19
228	Nova Ipixuna	04976	19
229	Nova Timboteua	05007	19
230	Novo Progresso	05031	19
231	Novo Repartimento	05064	19
232	Óbidos	05106	19
233	Oeiras do Pará	05205	19
234	Oriximiná	05304	19
235	Ourém	05403	19
236	Ourilândia do Norte	05437	19
237	Pacajá	05486	19
238	Palestina do Pará	05494	19
239	Paragominas	05502	19
240	Parauapebas	05536	19
241	Pau D'Arco	05551	19
242	Peixe-Boi	05601	19
243	Piçarra	05635	19
244	Placas	05650	19
245	Ponta de Pedras	05700	19
246	Portel	05809	19
247	Porto de Moz	05908	19
248	Prainha	06005	19
249	Primavera	06104	19
250	Quatipuru	06112	19
251	Redenção	06138	19
252	Rio Maria	06161	19
253	Rondon do Pará	06187	19
254	Rurópolis	06195	19
255	Salinópolis	06203	19
256	Salvaterra	06302	19
257	Santa Bárbara do Pará	06351	19
258	Santa Cruz do Arari	06401	19
259	Santa Isabel do Pará	06500	19
260	Santa Luzia do Pará	06559	19
261	Santa Maria das Barreiras	06583	19
262	Santa Maria do Pará	06609	19
263	Santana do Araguaia	06708	19
264	Santarém	06807	19
265	Santarém Novo	06906	19
266	Santo Antônio do Tauá	07003	19
267	São Caetano de Odivelas	07102	19
268	São Domingos do Araguaia	07151	19
269	São Domingos do Capim	07201	19
270	São Félix do Xingu	07300	19
271	São Francisco do Pará	07409	19
272	São Geraldo do Araguaia	07458	19
273	São João da Ponta	07466	19
274	São João de Pirabas	07474	19
275	São João do Araguaia	07508	19
276	São Miguel do Guamá	07607	19
277	São Sebastião da Boa Vista	07706	19
278	Sapucaia	07755	19
279	Senador José Porfírio	07805	19
280	Soure	07904	19
281	Tailândia	07953	19
282	Terra Alta	07961	19
283	Terra Santa	07979	19
284	Tomé-Açu	08001	19
285	Tracuateua	08035	19
286	Trairão	08050	19
287	Tucumã	08084	19
288	Tucuruí	08100	19
289	Ulianópolis	08126	19
290	Uruará	08159	19
291	Vigia	08209	19
292	Viseu	08308	19
293	Vitória do Xingu	08357	19
294	Xinguara	08407	19
295	Serra do Navio	00055	11
296	Amapá	00105	11
297	Pedra Branca do Amaparí	00154	11
298	Calçoene	00204	11
299	Cutias	00212	11
300	Ferreira Gomes	00238	11
301	Itaubal	00253	11
302	Vitória do Jari	00279	11
303	Macapá	00303	11
304	Mazagão	00402	11
305	Oiapoque	00501	11
306	Porto Grande	00535	11
307	Pracuúba	00550	11
308	Santana	00600	11
309	Tartarugalzinho	00709	11
310	Laranjal do Jari	00808	11
311	Abreulândia	00251	27
312	Aguiarnópolis	00301	27
313	Aliança do Tocantins	00350	27
314	Almas	00400	27
315	Alvorada	00707	27
316	Ananás	01002	27
317	Angico	01051	27
318	Aparecida do Rio Negro	01101	27
319	Aragominas	01309	27
320	Araguacema	01903	27
321	Araguaçu	02000	27
322	Araguaína	02109	27
323	Araguanã	02158	27
324	Araguatins	02208	27
325	Arapoema	02307	27
326	Arraias	02406	27
327	Augustinópolis	02554	27
328	Aurora do Tocantins	02703	27
329	Axixá do Tocantins	02901	27
330	Babaçulândia	03008	27
331	Bandeirantes do Tocantins	03057	27
332	Barra do Ouro	03073	27
333	Barrolândia	03107	27
334	Bernardo Sayão	03206	27
335	Bom Jesus do Tocantins	03305	27
336	Brasilândia do Tocantins	03602	27
337	Brejinho de Nazaré	03701	27
338	Buriti do Tocantins	03800	27
339	Cachoeirinha	03826	27
340	Campos Lindos	03842	27
341	Cariri do Tocantins	03867	27
342	Carmolândia	03883	27
343	Carrasco Bonito	03891	27
344	Caseara	03909	27
345	Centenário	04105	27
346	Chapada de Areia	04600	27
347	Chapada da Natividade	05102	27
348	Colinas do Tocantins	05508	27
349	Combinado	05557	27
350	Conceição do Tocantins	05607	27
351	Couto de Magalhães	06001	27
352	Cristalândia	06100	27
353	Crixás do Tocantins	06258	27
354	Darcinópolis	06506	27
355	Dianópolis	07009	27
356	Divinópolis do Tocantins	07108	27
357	Dois Irmãos do Tocantins	07207	27
358	Dueré	07306	27
359	Esperantina	07405	27
360	Fátima	07553	27
361	Figueirópolis	07652	27
362	Filadélfia	07702	27
363	Formoso do Araguaia	08205	27
364	Fortaleza do Tabocão	08254	27
365	Goianorte	08304	27
366	Goiatins	09005	27
367	Guaraí	09302	27
368	Gurupi	09500	27
369	Ipueiras	09807	27
370	Itacajá	10508	27
371	Itaguatins	10706	27
372	Itapiratins	10904	27
373	Itaporã do Tocantins	11100	27
374	Jaú do Tocantins	11506	27
375	Juarina	11803	27
376	Lagoa da Confusão	11902	27
377	Lagoa do Tocantins	11951	27
378	Lajeado	12009	27
379	Lavandeira	12157	27
380	Lizarda	12405	27
381	Luzinópolis	12454	27
382	Marianópolis do Tocantins	12504	27
383	Mateiros	12702	27
384	Maurilândia do Tocantins	12801	27
385	Miracema do Tocantins	13205	27
386	Miranorte	13304	27
387	Monte do Carmo	13601	27
388	Monte Santo do Tocantins	13700	27
389	Palmeiras do Tocantins	13809	27
390	Muricilândia	13957	27
391	Natividade	14203	27
392	Nazaré	14302	27
393	Nova Olinda	14880	27
394	Nova Rosalândia	15002	27
395	Novo Acordo	15101	27
396	Novo Alegre	15150	27
397	Novo Jardim	15259	27
398	Oliveira de Fátima	15507	27
399	Palmeirante	15705	27
400	Palmeirópolis	15754	27
401	Paraíso do Tocantins	16109	27
402	Paranã	16208	27
403	Pau D'Arco	16307	27
404	Pedro Afonso	16505	27
405	Peixe	16604	27
406	Pequizeiro	16653	27
407	Colméia	16703	27
408	Pindorama do Tocantins	17008	27
409	Piraquê	17206	27
410	Pium	17503	27
411	Ponte Alta do Bom Jesus	17800	27
412	Ponte Alta do Tocantins	17909	27
413	Porto Alegre do Tocantins	18006	27
414	Porto Nacional	18204	27
415	Praia Norte	18303	27
416	Presidente Kennedy	18402	27
417	Pugmil	18451	27
418	Recursolândia	18501	27
419	Riachinho	18550	27
420	Rio da Conceição	18659	27
421	Rio dos Bois	18709	27
422	Rio Sono	18758	27
423	Sampaio	18808	27
424	Sandolândia	18840	27
425	Santa Fé do Araguaia	18865	27
426	Santa Maria do Tocantins	18881	27
427	Santa Rita do Tocantins	18899	27
428	Santa Rosa do Tocantins	18907	27
429	Santa Tereza do Tocantins	19004	27
430	Santa Terezinha do Tocantins	20002	27
431	São Bento do Tocantins	20101	27
432	São Félix do Tocantins	20150	27
433	São Miguel do Tocantins	20200	27
434	São Salvador do Tocantins	20259	27
435	São Sebastião do Tocantins	20309	27
436	São Valério da Natividade	20499	27
437	Silvanópolis	20655	27
438	Sítio Novo do Tocantins	20804	27
439	Sucupira	20853	27
440	Taguatinga	20903	27
441	Taipas do Tocantins	20937	27
442	Talismã	20978	27
443	Palmas	21000	27
444	Tocantínia	21109	27
445	Tocantinópolis	21208	27
446	Tupirama	21257	27
447	Tupiratins	21307	27
448	Wanderlândia	22081	27
449	Xambioá	22107	27
450	Açailândia	00055	16
451	Afonso Cunha	00105	16
452	Água Doce do Maranhão	00154	16
453	Alcântara	00204	16
454	Aldeias Altas	00303	16
455	Altamira do Maranhão	00402	16
456	Alto Alegre do Maranhão	00436	16
457	Alto Alegre do Pindaré	00477	16
458	Alto Parnaíba	00501	16
459	Amapá do Maranhão	00550	16
460	Amarante do Maranhão	00600	16
461	Anajatuba	00709	16
462	Anapurus	00808	16
463	Apicum-Açu	00832	16
464	Araguanã	00873	16
465	Araioses	00907	16
466	Arame	00956	16
467	Arari	01004	16
468	Axixá	01103	16
469	Bacabal	01202	16
470	Bacabeira	01251	16
471	Bacuri	01301	16
472	Bacurituba	01350	16
473	Balsas	01400	16
474	Barão de Grajaú	01509	16
475	Barra do Corda	01608	16
476	Barreirinhas	01707	16
477	Belágua	01731	16
478	Bela Vista do Maranhão	01772	16
479	Benedito Leite	01806	16
480	Bequimão	01905	16
481	Bernardo do Mearim	01939	16
482	Boa Vista do Gurupi	01970	16
483	Bom Jardim	02002	16
484	Bom Jesus das Selvas	02036	16
485	Bom Lugar	02077	16
486	Brejo	02101	16
487	Brejo de Areia	02150	16
488	Buriti	02200	16
489	Buriti Bravo	02309	16
490	Buriticupu	02325	16
491	Buritirana	02358	16
492	Cachoeira Grande	02374	16
493	Cajapió	02408	16
494	Cajari	02507	16
495	Campestre do Maranhão	02556	16
496	Cândido Mendes	02606	16
497	Cantanhede	02705	16
498	Capinzal do Norte	02754	16
499	Carolina	02804	16
500	Carutapera	02903	16
501	Caxias	03000	16
502	Cedral	03109	16
503	Central do Maranhão	03125	16
504	Centro do Guilherme	03158	16
505	Centro Novo do Maranhão	03174	16
506	Chapadinha	03208	16
507	Cidelândia	03257	16
508	Codó	03307	16
509	Coelho Neto	03406	16
510	Colinas	03505	16
511	Conceição do Lago-Açu	03554	16
512	Coroatá	03604	16
513	Cururupu	03703	16
514	Davinópolis	03752	16
515	Dom Pedro	03802	16
516	Duque Bacelar	03901	16
517	Esperantinópolis	04008	16
518	Estreito	04057	16
519	Feira Nova do Maranhão	04073	16
520	Fernando Falcão	04081	16
521	Formosa da Serra Negra	04099	16
522	Fortaleza dos Nogueiras	04107	16
523	Fortuna	04206	16
524	Godofredo Viana	04305	16
525	Gonçalves Dias	04404	16
526	Governador Archer	04503	16
527	Governador Edison Lobão	04552	16
528	Governador Eugênio Barros	04602	16
529	Governador Luiz Rocha	04628	16
530	Governador Newton Bello	04651	16
531	Governador Nunes Freire	04677	16
532	Graça Aranha	04701	16
533	Grajaú	04800	16
534	Guimarães	04909	16
535	Humberto de Campos	05005	16
536	Icatu	05104	16
537	Igarapé do Meio	05153	16
538	Igarapé Grande	05203	16
539	Imperatriz	05302	16
540	Itaipava do Grajaú	05351	16
541	Itapecuru Mirim	05401	16
542	Itinga do Maranhão	05427	16
543	Jatobá	05450	16
544	Jenipapo dos Vieiras	05476	16
545	João Lisboa	05500	16
546	Joselândia	05609	16
547	Junco do Maranhão	05658	16
548	Lago da Pedra	05708	16
549	Lago do Junco	05807	16
550	Lago Verde	05906	16
551	Lagoa do Mato	05922	16
552	Lago dos Rodrigues	05948	16
553	Lagoa Grande do Maranhão	05963	16
554	Lajeado Novo	05989	16
555	Lima Campos	06003	16
556	Loreto	06102	16
557	Luís Domingues	06201	16
558	Magalhães de Almeida	06300	16
559	Maracaçumé	06326	16
560	Marajá do Sena	06359	16
561	Maranhãozinho	06375	16
562	Mata Roma	06409	16
563	Matinha	06508	16
564	Matões	06607	16
565	Matões do Norte	06631	16
566	Milagres do Maranhão	06672	16
567	Mirador	06706	16
568	Miranda do Norte	06755	16
569	Mirinzal	06805	16
570	Monção	06904	16
571	Montes Altos	07001	16
572	Morros	07100	16
573	Nina Rodrigues	07209	16
574	Nova Colinas	07258	16
575	Nova Iorque	07308	16
576	Nova Olinda do Maranhão	07357	16
577	Olho d'Água das Cunhãs	07407	16
578	Olinda Nova do Maranhão	07456	16
579	Paço do Lumiar	07506	16
580	Palmeirândia	07605	16
581	Paraibano	07704	16
582	Parnarama	07803	16
583	Passagem Franca	07902	16
584	Pastos Bons	08009	16
585	Paulino Neves	08058	16
586	Paulo Ramos	08108	16
587	Pedreiras	08207	16
588	Pedro do Rosário	08256	16
589	Penalva	08306	16
590	Peri Mirim	08405	16
591	Peritoró	08454	16
592	Pindaré-Mirim	08504	16
593	Pinheiro	08603	16
594	Pio XII	08702	16
595	Pirapemas	08801	16
596	Poção de Pedras	08900	16
597	Porto Franco	09007	16
598	Porto Rico do Maranhão	09056	16
599	Presidente Dutra	09106	16
600	Presidente Juscelino	09205	16
601	Presidente Médici	09239	16
602	Presidente Sarney	09270	16
603	Presidente Vargas	09304	16
604	Primeira Cruz	09403	16
605	Raposa	09452	16
606	Riachão	09502	16
607	Ribamar Fiquene	09551	16
608	Rosário	09601	16
609	Sambaíba	09700	16
610	Santa Filomena do Maranhão	09759	16
611	Santa Helena	09809	16
612	Santa Inês	09908	16
613	Santa Luzia	10005	16
614	Santa Luzia do Paruá	10039	16
615	Santa Quitéria do Maranhão	10104	16
616	Santa Rita	10203	16
617	Santana do Maranhão	10237	16
618	Santo Amaro do Maranhão	10278	16
619	Santo Antônio dos Lopes	10302	16
620	São Benedito do Rio Preto	10401	16
621	São Bento	10500	16
622	São Bernardo	10609	16
623	São Domingos do Azeitão	10658	16
624	São Domingos do Maranhão	10708	16
625	São Félix de Balsas	10807	16
626	São Francisco do Brejão	10856	16
627	São Francisco do Maranhão	10906	16
628	São João Batista	11003	16
629	São João do Carú	11029	16
630	São João do Paraíso	11052	16
631	São João do Soter	11078	16
632	São João dos Patos	11102	16
633	São José de Ribamar	11201	16
634	São José dos Basílios	11250	16
635	São Luís	11300	16
636	São Luís Gonzaga do Maranhão	11409	16
637	São Mateus do Maranhão	11508	16
638	São Pedro da Água Branca	11532	16
639	São Pedro dos Crentes	11573	16
640	São Raimundo das Mangabeiras	11607	16
641	São Raimundo do Doca Bezerra	11631	16
642	São Roberto	11672	16
643	São Vicente Ferrer	11706	16
644	Satubinha	11722	16
645	Senador Alexandre Costa	11748	16
646	Senador La Rocque	11763	16
647	Serrano do Maranhão	11789	16
648	Sítio Novo	11805	16
649	Sucupira do Norte	11904	16
650	Sucupira do Riachão	11953	16
651	Tasso Fragoso	12001	16
652	Timbiras	12100	16
653	Timon	12209	16
654	Trizidela do Vale	12233	16
655	Tufilândia	12274	16
656	Tuntum	12308	16
657	Turiaçu	12407	16
658	Turilândia	12456	16
659	Tutóia	12506	16
660	Urbano Santos	12605	16
661	Vargem Grande	12704	16
662	Viana	12803	16
663	Vila Nova dos Martírios	12852	16
664	Vitória do Mearim	12902	16
665	Vitorino Freire	13009	16
666	Zé Doca	14007	16
667	Acauã	00053	21
668	Agricolândia	00103	21
669	Água Branca	00202	21
670	Alagoinha do Piauí	00251	21
671	Alegrete do Piauí	00277	21
672	Alto Longá	00301	21
673	Altos	00400	21
674	Alvorada do Gurguéia	00459	21
675	Amarante	00509	21
676	Angical do Piauí	00608	21
677	Anísio de Abreu	00707	21
678	Antônio Almeida	00806	21
679	Aroazes	00905	21
680	Arraial	01002	21
681	Assunção do Piauí	01051	21
682	Avelino Lopes	01101	21
683	Baixa Grande do Ribeiro	01150	21
684	Barra D'Alcântara	01176	21
685	Barras	01200	21
686	Barreiras do Piauí	01309	21
687	Barro Duro	01408	21
688	Batalha	01507	21
689	Bela Vista do Piauí	01556	21
690	Belém do Piauí	01572	21
691	Beneditinos	01606	21
692	Bertolínia	01705	21
693	Betânia do Piauí	01739	21
694	Boa Hora	01770	21
695	Bocaina	01804	21
696	Bom Jesus	01903	21
697	Bom Princípio do Piauí	01919	21
698	Bonfim do Piauí	01929	21
699	Boqueirão do Piauí	01945	21
700	Brasileira	01960	21
701	Brejo do Piauí	01988	21
702	Buriti dos Lopes	02000	21
703	Buriti dos Montes	02026	21
704	Cabeceiras do Piauí	02059	21
705	Cajazeiras do Piauí	02075	21
706	Cajueiro da Praia	02083	21
707	Caldeirão Grande do Piauí	02091	21
708	Campinas do Piauí	02109	21
709	Campo Alegre do Fidalgo	02117	21
710	Campo Grande do Piauí	02133	21
711	Campo Largo do Piauí	02174	21
712	Campo Maior	02208	21
713	Canavieira	02251	21
714	Canto do Buriti	02307	21
715	Capitão de Campos	02406	21
716	Capitão Gervásio Oliveira	02455	21
717	Caracol	02505	21
718	Caraúbas do Piauí	02539	21
719	Caridade do Piauí	02554	21
720	Castelo do Piauí	02604	21
721	Caxingó	02653	21
722	Cocal	02703	21
723	Cocal de Telha	02711	21
724	Cocal dos Alves	02729	21
725	Coivaras	02737	21
726	Colônia do Gurguéia	02752	21
727	Colônia do Piauí	02778	21
728	Conceição do Canindé	02802	21
729	Coronel José Dias	02851	21
730	Corrente	02901	21
731	Cristalândia do Piauí	03008	21
732	Cristino Castro	03107	21
733	Curimatá	03206	21
734	Currais	03230	21
735	Curralinhos	03255	21
736	Curral Novo do Piauí	03271	21
737	Demerval Lobão	03305	21
738	Dirceu Arcoverde	03354	21
739	Dom Expedito Lopes	03404	21
740	Domingos Mourão	03420	21
741	Dom Inocêncio	03453	21
742	Elesbão Veloso	03503	21
743	Eliseu Martins	03602	21
744	Esperantina	03701	21
745	Fartura do Piauí	03750	21
746	Flores do Piauí	03800	21
747	Floresta do Piauí	03859	21
748	Floriano	03909	21
749	Francinópolis	04006	21
750	Francisco Ayres	04105	21
751	Francisco Macedo	04154	21
752	Francisco Santos	04204	21
753	Fronteiras	04303	21
754	Geminiano	04352	21
755	Gilbués	04402	21
756	Guadalupe	04501	21
757	Guaribas	04550	21
758	Hugo Napoleão	04600	21
759	Ilha Grande	04659	21
760	Inhuma	04709	21
761	Ipiranga do Piauí	04808	21
762	Isaías Coelho	04907	21
763	Itainópolis	05003	21
764	Itaueira	05102	21
765	Jacobina do Piauí	05151	21
766	Jaicós	05201	21
767	Jardim do Mulato	05250	21
768	Jatobá do Piauí	05276	21
769	Jerumenha	05300	21
770	João Costa	05359	21
771	Joaquim Pires	05409	21
772	Joca Marques	05458	21
773	José de Freitas	05508	21
774	Juazeiro do Piauí	05516	21
775	Júlio Borges	05524	21
776	Jurema	05532	21
777	Lagoinha do Piauí	05540	21
778	Lagoa Alegre	05557	21
779	Lagoa do Barro do Piauí	05565	21
780	Lagoa de São Francisco	05573	21
781	Lagoa do Piauí	05581	21
782	Lagoa do Sítio	05599	21
783	Landri Sales	05607	21
784	Luís Correia	05706	21
785	Luzilândia	05805	21
786	Madeiro	05854	21
787	Manoel Emídio	05904	21
788	Marcolândia	05953	21
789	Marcos Parente	06001	21
790	Massapê do Piauí	06050	21
791	Matias Olímpio	06100	21
792	Miguel Alves	06209	21
793	Miguel Leão	06308	21
794	Milton Brandão	06357	21
795	Monsenhor Gil	06407	21
796	Monsenhor Hipólito	06506	21
797	Monte Alegre do Piauí	06605	21
798	Morro Cabeça no Tempo	06654	21
799	Morro do Chapéu do Piauí	06670	21
800	Murici dos Portelas	06696	21
801	Nazaré do Piauí	06704	21
802	Nossa Senhora de Nazaré	06753	21
803	Nossa Senhora dos Remédios	06803	21
804	Novo Oriente do Piauí	06902	21
805	Novo Santo Antônio	06951	21
806	Oeiras	07009	21
807	Olho D'Água do Piauí	07108	21
808	Padre Marcos	07207	21
809	Paes Landim	07306	21
810	Pajeú do Piauí	07355	21
811	Palmeira do Piauí	07405	21
812	Palmeirais	07504	21
813	Paquetá	07553	21
814	Parnaguá	07603	21
815	Parnaíba	07702	21
816	Passagem Franca do Piauí	07751	21
817	Patos do Piauí	07777	21
818	Paulistana	07801	21
819	Pavussu	07850	21
820	Pedro II	07900	21
821	Pedro Laurentino	07934	21
822	Nova Santa Rita	07959	21
823	Picos	08007	21
824	Pimenteiras	08106	21
825	Pio IX	08205	21
826	Piracuruca	08304	21
827	Piripiri	08403	21
828	Porto	08502	21
829	Porto Alegre do Piauí	08551	21
830	Prata do Piauí	08601	21
831	Queimada Nova	08650	21
832	Redenção do Gurguéia	08700	21
833	Regeneração	08809	21
834	Riacho Frio	08858	21
835	Ribeira do Piauí	08874	21
836	Ribeiro Gonçalves	08908	21
837	Rio Grande do Piauí	09005	21
838	Santa Cruz do Piauí	09104	21
839	Santa Cruz dos Milagres	09153	21
840	Santa Filomena	09203	21
841	Santa Luz	09302	21
842	Santana do Piauí	09351	21
843	Santa Rosa do Piauí	09377	21
844	Santo Antônio de Lisboa	09401	21
845	Santo Antônio dos Milagres	09450	21
846	Santo Inácio do Piauí	09500	21
847	São Braz do Piauí	09559	21
848	São Félix do Piauí	09609	21
849	São Francisco de Assis do Piau	09658	21
850	São Francisco do Piauí	09708	21
851	São Gonçalo do Gurguéia	09757	21
852	São Gonçalo do Piauí	09807	21
853	São João da Canabrava	09856	21
854	São João da Fronteira	09872	21
855	São João da Serra	09906	21
856	São João da Varjota	09955	21
857	São João do Arraial	09971	21
858	São João do Piauí	10003	21
859	São José do Divino	10052	21
860	São José do Peixe	10102	21
861	São José do Piauí	10201	21
862	São Julião	10300	21
863	São Lourenço do Piauí	10359	21
864	São Luis do Piauí	10375	21
865	São Miguel da Baixa Grande	10383	21
866	São Miguel do Fidalgo	10391	21
867	São Miguel do Tapuio	10409	21
868	São Pedro do Piauí	10508	21
869	São Raimundo Nonato	10607	21
870	Sebastião Barros	10623	21
871	Sebastião Leal	10631	21
872	Sigefredo Pacheco	10656	21
873	Simões	10706	21
874	Simplício Mendes	10805	21
875	Socorro do Piauí	10904	21
876	Sussuapara	10938	21
877	Tamboril do Piauí	10953	21
878	Tanque do Piauí	10979	21
879	Teresina	11001	21
880	União	11100	21
881	Uruçuí	11209	21
882	Valença do Piauí	11308	21
883	Várzea Branca	11357	21
884	Várzea Grande	11407	21
885	Vera Mendes	11506	21
886	Vila Nova do Piauí	11605	21
887	Wall Ferraz	11704	21
888	Abaiara	00101	1
889	Acarapé	00150	1
890	Acaraú	00200	1
891	Acopiara	00309	1
892	Aiuaba	00408	1
893	Alcântaras	00507	1
894	Altaneira	00606	1
895	Alto Santo	00705	1
896	Amontada	00754	1
897	Antonina do Norte	00804	1
898	Apuiarés	00903	1
899	Aquiraz	01000	1
900	Aracati	01109	1
901	Aracoiaba	01208	1
902	Ararendá	01257	1
903	Araripe	01307	1
904	Aratuba	01406	1
905	Arneiroz	01505	1
906	Assaré	01604	1
907	Aurora	01703	1
908	Baixio	01802	1
909	Banabuiú	01851	1
910	Barbalha	01901	1
911	Barreira	01950	1
912	Barro	02008	1
913	Barroquinha	02057	1
914	Baturité	02107	1
915	Beberibe	02206	1
916	Bela Cruz	02305	1
917	Boa Viagem	02404	1
918	Brejo Santo	02503	1
919	Camocim	02602	1
920	Campos Sales	02701	1
921	Canindé	02800	1
922	Capistrano	02909	1
923	Caridade	03006	1
924	Cariré	03105	1
925	Caririaçu	03204	1
926	Cariús	03303	1
927	Carnaubal	03402	1
928	Cascavel	03501	1
929	Catarina	03600	1
930	Catunda	03659	1
931	Caucaia	03709	1
932	Cedro	03808	1
933	Chaval	03907	1
934	Choró	03931	1
935	Chorozinho	03956	1
936	Coreaú	04004	1
937	Crateús	04103	1
938	Crato	04202	1
939	Croatá	04236	1
940	Cruz	04251	1
941	Deputado Irapuan Pinheiro	04269	1
942	Ererê	04277	1
943	Eusébio	04285	1
944	Farias Brito	04301	1
945	Forquilha	04350	1
946	Fortaleza	04400	1
947	Fortim	04459	1
948	Frecheirinha	04509	1
949	General Sampaio	04608	1
950	Graça	04657	1
951	Granja	04707	1
952	Granjeiro	04806	1
953	Groaíras	04905	1
954	Guaiúba	04954	1
955	Guaraciaba do Norte	05001	1
956	Guaramiranga	05100	1
957	Hidrolândia	05209	1
958	Horizonte	05233	1
959	Ibaretama	05266	1
960	Ibiapina	05308	1
961	Ibicuitinga	05332	1
962	Icapuí	05357	1
963	Icó	05407	1
964	Iguatu	05506	1
965	Independência	05605	1
966	Ipaporanga	05654	1
967	Ipaumirim	05704	1
968	Ipu	05803	1
969	Ipueiras	05902	1
970	Iracema	06009	1
971	Irauçuba	06108	1
972	Itaiçaba	06207	1
973	Itaitinga	06256	1
974	Itapagé	06306	1
975	Itapipoca	06405	1
976	Itapiúna	06504	1
977	Itarema	06553	1
978	Itatira	06603	1
979	Jaguaretama	06702	1
980	Jaguaribara	06801	1
981	Jaguaribe	06900	1
982	Jaguaruana	07007	1
983	Jardim	07106	1
984	Jati	07205	1
985	Jijoca de Jericoacoara	07254	1
986	Juazeiro do Norte	07304	1
987	Jucás	07403	1
988	Lavras da Mangabeira	07502	1
989	Limoeiro do Norte	07601	1
990	Madalena	07635	1
991	Maracanaú	07650	1
992	Maranguape	07700	1
993	Marco	07809	1
994	Martinópole	07908	1
995	Massapê	08005	1
996	Mauriti	08104	1
997	Meruoca	08203	1
998	Milagres	08302	1
999	Milhã	08351	1
1000	Miraíma	08377	1
1001	Missão Velha	08401	1
1002	Mombaça	08500	1
1003	Monsenhor Tabosa	08609	1
1004	Morada Nova	08708	1
1005	Moraújo	08807	1
1006	Morrinhos	08906	1
1007	Mucambo	09003	1
1008	Mulungu	09102	1
1009	Nova Olinda	09201	1
1010	Nova Russas	09300	1
1011	Novo Oriente	09409	1
1012	Ocara	09458	1
1013	Orós	09508	1
1014	Pacajus	09607	1
1015	Pacatuba	09706	1
1016	Pacoti	09805	1
1017	Pacujá	09904	1
1018	Palhano	10001	1
1019	Palmácia	10100	1
1020	Paracuru	10209	1
1021	Paraipaba	10258	1
1022	Parambu	10308	1
1023	Paramoti	10407	1
1024	Pedra Branca	10506	1
1025	Penaforte	10605	1
1026	Pentecoste	10704	1
1027	Pereiro	10803	1
1028	Pindoretama	10852	1
1029	Piquet Carneiro	10902	1
1030	Pires Ferreira	10951	1
1031	Poranga	11009	1
1032	Porteiras	11108	1
1033	Potengi	11207	1
1034	Potiretama	11231	1
1035	Quiterianópolis	11264	1
1036	Quixadá	11306	1
1037	Quixelô	11355	1
1038	Quixeramobim	11405	1
1039	Quixeré	11504	1
1040	Redenção	11603	1
1041	Reriutaba	11702	1
1042	Russas	11801	1
1043	Saboeiro	11900	1
1044	Salitre	11959	1
1045	Santana do Acaraú	12007	1
1046	Santana do Cariri	12106	1
1047	Santa Quitéria	12205	1
1048	São Benedito	12304	1
1049	São Gonçalo do Amarante	12403	1
1050	São João do Jaguaribe	12502	1
1051	São Luís do Curu	12601	1
1052	Senador Pompeu	12700	1
1053	Senador Sá	12809	1
1054	Sobral	12908	1
1055	Solonópole	13005	1
1056	Tabuleiro do Norte	13104	1
1057	Tamboril	13203	1
1058	Tarrafas	13252	1
1059	Tauá	13302	1
1060	Tejuçuoca	13351	1
1061	Tianguá	13401	1
1062	Trairi	13500	1
1063	Tururu	13559	1
1064	Ubajara	13609	1
1065	Umari	13708	1
1066	Umirim	13757	1
1067	Uruburetama	13807	1
1068	Uruoca	13906	1
1069	Varjota	13955	1
1070	Várzea Alegre	14003	1
1071	Viçosa do Ceará	14102	1
1072	Acari	00109	3
1073	Açu	00208	3
1074	Afonso Bezerra	00307	3
1075	Água Nova	00406	3
1076	Alexandria	00505	3
1077	Almino Afonso	00604	3
1078	Alto do Rodrigues	00703	3
1079	Angicos	00802	3
1080	Antônio Martins	00901	3
1081	Apodi	01008	3
1082	Areia Branca	01107	3
1083	Arês	01206	3
1084	Augusto Severo	01305	3
1085	Baía Formosa	01404	3
1086	Baraúna	01453	3
1087	Barcelona	01503	3
1088	Bento Fernandes	01602	3
1089	Bodó	01651	3
1090	Bom Jesus	01701	3
1091	Brejinho	01800	3
1092	Caiçara do Norte	01859	3
1093	Caiçara do Rio do Vento	01909	3
1094	Caicó	02006	3
1095	Campo Redondo	02105	3
1096	Canguaretama	02204	3
1097	Caraúbas	02303	3
1098	Carnaúba dos Dantas	02402	3
1099	Carnaubais	02501	3
1100	Ceará-Mirim	02600	3
1101	Cerro Corá	02709	3
1102	Coronel Ezequiel	02808	3
1103	Coronel João Pessoa	02907	3
1104	Cruzeta	03004	3
1105	Currais Novos	03103	3
1106	Doutor Severiano	03202	3
1107	Parnamirim	03251	3
1108	Encanto	03301	3
1109	Equador	03400	3
1110	Espírito Santo	03509	3
1111	Extremoz	03608	3
1112	Felipe Guerra	03707	3
1113	Fernando Pedroza	03756	3
1114	Florânia	03806	3
1115	Francisco Dantas	03905	3
1116	Frutuoso Gomes	04002	3
1117	Galinhos	04101	3
1118	Goianinha	04200	3
1119	Governador Dix-Sept Rosado	04309	3
1120	Grossos	04408	3
1121	Guamaré	04507	3
1122	Ielmo Marinho	04606	3
1123	Ipanguaçu	04705	3
1124	Ipueira	04804	3
1125	Itajá	04853	3
1126	Itaú	04903	3
1127	Jaçanã	05009	3
1128	Jandaíra	05108	3
1129	Janduís	05207	3
1130	Januário Cicco	05306	3
1131	Japi	05405	3
1132	Jardim de Angicos	05504	3
1133	Jardim de Piranhas	05603	3
1134	Jardim do Seridó	05702	3
1135	João Câmara	05801	3
1136	João Dias	05900	3
1137	José da Penha	06007	3
1138	Jucurutu	06106	3
1139	Lagoa d'Anta	06205	3
1140	Lagoa de Pedras	06304	3
1141	Lagoa de Velhos	06403	3
1142	Lagoa Nova	06502	3
1143	Lagoa Salgada	06601	3
1144	Lajes	06700	3
1145	Lajes Pintadas	06809	3
1146	Lucrécia	06908	3
1147	Luís Gomes	07005	3
1148	Macaíba	07104	3
1149	Macau	07203	3
1150	Major Sales	07252	3
1151	Marcelino Vieira	07302	3
1152	Martins	07401	3
1153	Maxaranguape	07500	3
1154	Messias Targino	07609	3
1155	Montanhas	07708	3
1156	Monte Alegre	07807	3
1157	Monte das Gameleiras	07906	3
1158	Mossoró	08003	3
1159	Natal	08102	3
1160	Nísia Floresta	08201	3
1161	Nova Cruz	08300	3
1162	Olho-d'Água do Borges	08409	3
1163	Ouro Branco	08508	3
1164	Paraná	08607	3
1165	Paraú	08706	3
1166	Parazinho	08805	3
1167	Parelhas	08904	3
1168	Rio do Fogo	08953	3
1169	Passa e Fica	09100	3
1170	Passagem	09209	3
1171	Patu	09308	3
1172	Santa Maria	09332	3
1173	Pau dos Ferros	09407	3
1174	Pedra Grande	09506	3
1175	Pedra Preta	09605	3
1176	Pedro Avelino	09704	3
1177	Pedro Velho	09803	3
1178	Pendências	09902	3
1179	Pilões	10009	3
1180	Poço Branco	10108	3
1181	Portalegre	10207	3
1182	Porto do Mangue	10256	3
1183	Presidente Juscelino	10306	3
1184	Pureza	10405	3
1185	Rafael Fernandes	10504	3
1186	Rafael Godeiro	10603	3
1187	Riacho da Cruz	10702	3
1188	Riacho de Santana	10801	3
1189	Riachuelo	10900	3
1190	Rodolfo Fernandes	11007	3
1191	Tibau	11056	3
1192	Ruy Barbosa	11106	3
1193	Santa Cruz	11205	3
1194	Santana do Matos	11403	3
1195	Santana do Seridó	11429	3
1196	Santo Antônio	11502	3
1197	São Bento do Norte	11601	3
1198	São Bento do Trairí	11700	3
1199	São Fernando	11809	3
1200	São Francisco do Oeste	11908	3
1201	São Gonçalo do Amarante	12005	3
1202	São João do Sabugi	12104	3
1203	São José de Mipibu	12203	3
1204	São José do Campestre	12302	3
1205	São José do Seridó	12401	3
1206	São Miguel	12500	3
1207	São Miguel de Touros	12559	3
1208	São Paulo do Potengi	12609	3
1209	São Pedro	12708	3
1210	São Rafael	12807	3
1211	São Tomé	12906	3
1212	São Vicente	13003	3
1213	Senador Elói de Souza	13102	3
1214	Senador Georgino Avelino	13201	3
1215	Serra de São Bento	13300	3
1216	Serra do Mel	13359	3
1217	Serra Negra do Norte	13409	3
1218	Serrinha	13508	3
1219	Serrinha dos Pintos	13557	3
1220	Severiano Melo	13607	3
1221	Sítio Novo	13706	3
1222	Taboleiro Grande	13805	3
1223	Taipu	13904	3
1224	Tangará	14001	3
1225	Tenente Ananias	14100	3
1226	Tenente Laurentino Cruz	14159	3
1227	Tibau do Sul	14209	3
1228	Timbaúba dos Batistas	14308	3
1229	Touros	14407	3
1230	Triunfo Potiguar	14456	3
1231	Umarizal	14506	3
1232	Upanema	14605	3
1233	Várzea	14704	3
1234	Venha-Ver	14753	3
1235	Vera Cruz	14803	3
1236	Viçosa	14902	3
1237	Vila Flor	15008	3
1238	Água Branca	00106	20
1239	Aguiar	00205	20
1240	Alagoa Grande	00304	20
1241	Alagoa Nova	00403	20
1242	Alagoinha	00502	20
1243	Alcantil	00536	20
1244	Algodão de Jandaíra	00577	20
1245	Alhandra	00601	20
1246	São João do Rio do Peixe	00700	20
1247	Amparo	00734	20
1248	Aparecida	00775	20
1249	Araçagi	00809	20
1250	Arara	00908	20
1251	Araruna	01005	20
1252	Areia	01104	20
1253	Areia de Baraúnas	01153	20
1254	Areial	01203	20
1255	Aroeiras	01302	20
1256	Assunção	01351	20
1257	Baía da Traição	01401	20
1258	Bananeiras	01500	20
1259	Baraúna	01534	20
1260	Barra de Santana	01575	20
1261	Barra de Santa Rosa	01609	20
1262	Barra de São Miguel	01708	20
1263	Bayeux	01807	20
1264	Belém	01906	20
1265	Belém do Brejo do Cruz	02003	20
1266	Bernardino Batista	02052	20
1267	Boa Ventura	02102	20
1268	Boa Vista	02151	20
1269	Bom Jesus	02201	20
1270	Bom Sucesso	02300	20
1271	Bonito de Santa Fé	02409	20
1272	Boqueirão	02508	20
1273	Igaracy	02607	20
1274	Borborema	02706	20
1275	Brejo do Cruz	02805	20
1276	Brejo dos Santos	02904	20
1277	Caaporã	03001	20
1278	Cabaceiras	03100	20
1279	Cabedelo	03209	20
1280	Cachoeira dos Índios	03308	20
1281	Cacimba de Areia	03407	20
1282	Cacimba de Dentro	03506	20
1283	Cacimbas	03555	20
1284	Caiçara	03605	20
1285	Cajazeiras	03704	20
1286	Cajazeirinhas	03753	20
1287	Caldas Brandão	03803	20
1288	Camalaú	03902	20
1289	Campina Grande	04009	20
1290	Capim	04033	20
1291	Caraúbas	04074	20
1292	Carrapateira	04108	20
1293	Casserengue	04157	20
1294	Catingueira	04207	20
1295	Catolé do Rocha	04306	20
1296	Caturité	04355	20
1297	Conceição	04405	20
1298	Condado	04504	20
1299	Conde	04603	20
1300	Congo	04702	20
1301	Coremas	04801	20
1302	Coxixola	04850	20
1303	Cruz do Espírito Santo	04900	20
1304	Cubati	05006	20
1305	Cuité	05105	20
1306	Cuitegi	05204	20
1307	Cuité de Mamanguape	05238	20
1308	Curral de Cima	05279	20
1309	Curral Velho	05303	20
1310	Damião	05352	20
1311	Desterro	05402	20
1312	Vista Serrana	05501	20
1313	Diamante	05600	20
1314	Dona Inês	05709	20
1315	Duas Estradas	05808	20
1316	Emas	05907	20
1317	Esperança	06004	20
1318	Fagundes	06103	20
1319	Frei Martinho	06202	20
1320	Gado Bravo	06251	20
1321	Guarabira	06301	20
1322	Gurinhém	06400	20
1323	Gurjão	06509	20
1324	Ibiara	06608	20
1325	Imaculada	06707	20
1326	Ingá	06806	20
1327	Itabaiana	06905	20
1328	Itaporanga	07002	20
1329	Itapororoca	07101	20
1330	Itatuba	07200	20
1331	Jacaraú	07309	20
1332	Jericó	07408	20
1333	João Pessoa	07507	20
1334	Juarez Távora	07606	20
1335	Juazeirinho	07705	20
1336	Junco do Seridó	07804	20
1337	Juripiranga	07903	20
1338	Juru	08000	20
1339	Lagoa	08109	20
1340	Lagoa de Dentro	08208	20
1341	Lagoa Seca	08307	20
1342	Lastro	08406	20
1343	Livramento	08505	20
1344	Logradouro	08554	20
1345	Lucena	08604	20
1346	Mãe d'Água	08703	20
1347	Malta	08802	20
1348	Mamanguape	08901	20
1349	Manaíra	09008	20
1350	Marcação	09057	20
1351	Mari	09107	20
1352	Marizópolis	09156	20
1353	Massaranduba	09206	20
1354	Mataraca	09305	20
1355	Matinhas	09339	20
1356	Mato Grosso	09370	20
1357	Maturéia	09396	20
1358	Mogeiro	09404	20
1359	Montadas	09503	20
1360	Monte Horebe	09602	20
1361	Monteiro	09701	20
1362	Mulungu	09800	20
1363	Natuba	09909	20
1364	Nazarezinho	10006	20
1365	Nova Floresta	10105	20
1366	Nova Olinda	10204	20
1367	Nova Palmeira	10303	20
1368	Olho d'Água	10402	20
1369	Olivedos	10501	20
1370	Ouro Velho	10600	20
1371	Parari	10659	20
1372	Passagem	10709	20
1373	Patos	10808	20
1374	Paulista	10907	20
1375	Pedra Branca	11004	20
1376	Pedra Lavrada	11103	20
1377	Pedras de Fogo	11202	20
1378	Piancó	11301	20
1379	Picuí	11400	20
1380	Pilar	11509	20
1381	Pilões	11608	20
1382	Pilõezinhos	11707	20
1383	Pirpirituba	11806	20
1384	Pitimbu	11905	20
1385	Pocinhos	12002	20
1386	Poço Dantas	12036	20
1387	Poço de José de Moura	12077	20
1388	Pombal	12101	20
1389	Prata	12200	20
1390	Princesa Isabel	12309	20
1391	Puxinanã	12408	20
1392	Queimadas	12507	20
1393	Quixabá	12606	20
1394	Remígio	12705	20
1395	Pedro Régis	12721	20
1396	Riachão	12747	20
1397	Riachão do Bacamarte	12754	20
1398	Riachão do Poço	12762	20
1399	Riacho de Santo Antônio	12788	20
1400	Riacho dos Cavalos	12804	20
1401	Rio Tinto	12903	20
1402	Salgadinho	13000	20
1403	Salgado de São Félix	13109	20
1404	Santa Cecília	13158	20
1405	Santa Cruz	13208	20
1406	Santa Helena	13307	20
1407	Santa Inês	13356	20
1408	Santa Luzia	13406	20
1409	Santana de Mangueira	13505	20
1410	Santana dos Garrotes	13604	20
1411	Santarém	13653	20
1412	Santa Rita	13703	20
1413	Santa Teresinha	13802	20
1414	Santo André	13851	20
1415	São Bento	13901	20
1416	São Bentinho	13927	20
1417	São Domingos do Cariri	13943	20
1418	São Domingos de Pombal	13968	20
1419	São Francisco	13984	20
1420	São João do Cariri	14008	20
1421	São João do Tigre	14107	20
1422	São José da Lagoa Tapada	14206	20
1423	São José de Caiana	14305	20
1424	São José de Espinharas	14404	20
1425	São José dos Ramos	14453	20
1426	São José de Piranhas	14503	20
1427	São José de Princesa	14552	20
1428	São José do Bonfim	14602	20
1429	São José do Brejo do Cruz	14651	20
1430	São José do Sabugi	14701	20
1431	São José dos Cordeiros	14800	20
1432	São Mamede	14909	20
1433	São Miguel de Taipu	15005	20
1434	São Sebastião de Lagoa de Roca	15104	20
1435	São Sebastião do Umbuzeiro	15203	20
1436	Sapé	15302	20
1437	Seridó	15401	20
1438	Serra Branca	15500	20
1439	Serra da Raiz	15609	20
1440	Serra Grande	15708	20
1441	Serra Redonda	15807	20
1442	Serraria	15906	20
1443	Sertãozinho	15930	20
1444	Sobrado	15971	20
1445	Solânea	16003	20
1446	Soledade	16102	20
1447	Sossêgo	16151	20
1448	Sousa	16201	20
1449	Sumé	16300	20
1450	Campo de Santana	16409	20
1451	Taperoá	16508	20
1452	Tavares	16607	20
1453	Teixeira	16706	20
1454	Tenório	16755	20
1455	Triunfo	16805	20
1456	Uiraúna	16904	20
1457	Umbuzeiro	17001	20
1458	Várzea	17100	20
1459	Vieirópolis	17209	20
1460	Zabelê	17407	20
1461	Abreu e Lima	00054	2
1462	Afogados da Ingazeira	00104	2
1463	Afrânio	00203	2
1464	Agrestina	00302	2
1465	Água Preta	00401	2
1466	Águas Belas	00500	2
1467	Alagoinha	00609	2
1468	Aliança	00708	2
1469	Altinho	00807	2
1470	Amaraji	00906	2
1471	Angelim	01003	2
1472	Araçoiaba	01052	2
1473	Araripina	01102	2
1474	Arcoverde	01201	2
1475	Barra de Guabiraba	01300	2
1476	Barreiros	01409	2
1477	Belém de Maria	01508	2
1478	Belém de São Francisco	01607	2
1479	Belo Jardim	01706	2
1480	Betânia	01805	2
1481	Bezerros	01904	2
1482	Bodocó	02001	2
1483	Bom Conselho	02100	2
1484	Bom Jardim	02209	2
1485	Bonito	02308	2
1486	Brejão	02407	2
1487	Brejinho	02506	2
1488	Brejo da Madre de Deus	02605	2
1489	Buenos Aires	02704	2
1490	Buíque	02803	2
1491	Cabo de Santo Agostinho	02902	2
1492	Cabrobó	03009	2
1493	Cachoeirinha	03108	2
1494	Caetés	03207	2
1495	Calçado	03306	2
1496	Calumbi	03405	2
1497	Camaragibe	03454	2
1498	Camocim de São Félix	03504	2
1499	Camutanga	03603	2
1500	Canhotinho	03702	2
1501	Capoeiras	03801	2
1502	Carnaíba	03900	2
1503	Carnaubeira da Penha	03926	2
1504	Carpina	04007	2
1505	Caruaru	04106	2
1506	Casinhas	04155	2
1507	Catende	04205	2
1508	Cedro	04304	2
1509	Chã de Alegria	04403	2
1510	Chã Grande	04502	2
1511	Condado	04601	2
1512	Correntes	04700	2
1513	Cortês	04809	2
1514	Cumaru	04908	2
1515	Cupira	05004	2
1516	Custódia	05103	2
1517	Dormentes	05152	2
1518	Escada	05202	2
1519	Exu	05301	2
1520	Feira Nova	05400	2
1521	Fernando de Noronha	05459	2
1522	Ferreiros	05509	2
1523	Flores	05608	2
1524	Floresta	05707	2
1525	Frei Miguelinho	05806	2
1526	Gameleira	05905	2
1527	Garanhuns	06002	2
1528	Glória do Goitá	06101	2
1529	Goiana	06200	2
1530	Granito	06309	2
1531	Gravatá	06408	2
1532	Iati	06507	2
1533	Ibimirim	06606	2
1534	Ibirajuba	06705	2
1535	Igarassu	06804	2
1536	Iguaraci	06903	2
1537	Inajá	07000	2
1538	Ingazeira	07109	2
1539	Ipojuca	07208	2
1540	Ipubi	07307	2
1541	Itacuruba	07406	2
1542	Itaíba	07505	2
1543	Itamaracá	07604	2
1544	Itambé	07653	2
1545	Itapetim	07703	2
1546	Itapissuma	07752	2
1547	Itaquitinga	07802	2
1548	Jaboatão dos Guararapes	07901	2
1549	Jaqueira	07950	2
1550	Jataúba	08008	2
1551	Jatobá	08057	2
1552	João Alfredo	08107	2
1553	Joaquim Nabuco	08206	2
1554	Jucati	08255	2
1555	Jupi	08305	2
1556	Jurema	08404	2
1557	Lagoa do Carro	08453	2
1558	Lagoa do Itaenga	08503	2
1559	Lagoa do Ouro	08602	2
1560	Lagoa dos Gatos	08701	2
1561	Lagoa Grande	08750	2
1562	Lajedo	08800	2
1563	Limoeiro	08909	2
1564	Macaparana	09006	2
1565	Machados	09105	2
1566	Manari	09154	2
1567	Maraial	09204	2
1568	Mirandiba	09303	2
1569	Moreno	09402	2
1570	Nazaré da Mata	09501	2
1571	Olinda	09600	2
1572	Orobó	09709	2
1573	Orocó	09808	2
1574	Ouricuri	09907	2
1575	Palmares	10004	2
1576	Palmeirina	10103	2
1577	Panelas	10202	2
1578	Paranatama	10301	2
1579	Parnamirim	10400	2
1580	Passira	10509	2
1581	Paudalho	10608	2
1582	Paulista	10707	2
1583	Pedra	10806	2
1584	Pesqueira	10905	2
1585	Petrolândia	11002	2
1586	Petrolina	11101	2
1587	Poção	11200	2
1588	Pombos	11309	2
1589	Primavera	11408	2
1590	Quipapá	11507	2
1591	Quixaba	11533	2
1592	Recife	11606	2
1593	Riacho das Almas	11705	2
1594	Ribeirão	11804	2
1595	Rio Formoso	11903	2
1596	Sairé	12000	2
1597	Salgadinho	12109	2
1598	Salgueiro	12208	2
1599	Saloá	12307	2
1600	Sanharó	12406	2
1601	Santa Cruz	12455	2
1602	Santa Cruz da Baixa Verde	12471	2
1603	Santa Cruz do Capibaribe	12505	2
1604	Santa Filomena	12554	2
1605	Santa Maria da Boa Vista	12604	2
1606	Santa Maria do Cambucá	12703	2
1607	Santa Terezinha	12802	2
1608	São Benedito do Sul	12901	2
1609	São Bento do Una	13008	2
1610	São Caitano	13107	2
1611	São João	13206	2
1612	São Joaquim do Monte	13305	2
1613	São José da Coroa Grande	13404	2
1614	São José do Belmonte	13503	2
1615	São José do Egito	13602	2
1616	São Lourenço da Mata	13701	2
1617	São Vicente Ferrer	13800	2
1618	Serra Talhada	13909	2
1619	Serrita	14006	2
1620	Sertânia	14105	2
1621	Sirinhaém	14204	2
1622	Moreilândia	14303	2
1623	Solidão	14402	2
1624	Surubim	14501	2
1625	Tabira	14600	2
1626	Tacaimbó	14709	2
1627	Tacaratu	14808	2
1628	Tamandaré	14857	2
1629	Taquaritinga do Norte	15003	2
1630	Terezinha	15102	2
1631	Terra Nova	15201	2
1632	Timbaúba	15300	2
1633	Toritama	15409	2
1634	Tracunhaém	15508	2
1635	Trindade	15607	2
1636	Triunfo	15706	2
1637	Tupanatinga	15805	2
1638	Tuparetama	15904	2
1639	Venturosa	16001	2
1640	Verdejante	16100	2
1641	Vertente do Lério	16183	2
1642	Vertentes	16209	2
1643	Vicência	16308	2
1644	Vitória de Santo Antão	16407	2
1645	Xexéu	16506	2
1646	Água Branca	00102	9
1647	Anadia	00201	9
1648	Arapiraca	00300	9
1649	Atalaia	00409	9
1650	Barra de Santo Antônio	00508	9
1651	Barra de São Miguel	00607	9
1652	Batalha	00706	9
1653	Belém	00805	9
1654	Belo Monte	00904	9
1655	Boca da Mata	01001	9
1656	Branquinha	01100	9
1657	Cacimbinhas	01209	9
1658	Cajueiro	01308	9
1659	Campestre	01357	9
1660	Campo Alegre	01407	9
1661	Campo Grande	01506	9
1662	Canapi	01605	9
1663	Capela	01704	9
1664	Carneiros	01803	9
1665	Chã Preta	01902	9
1666	Coité do Nóia	02009	9
1667	Colônia Leopoldina	02108	9
1668	Coqueiro Seco	02207	9
1669	Coruripe	02306	9
1670	Craíbas	02355	9
1671	Delmiro Gouveia	02405	9
1672	Dois Riachos	02504	9
1673	Estrela de Alagoas	02553	9
1674	Feira Grande	02603	9
1675	Feliz Deserto	02702	9
1676	Flexeiras	02801	9
1677	Girau do Ponciano	02900	9
1678	Ibateguara	03007	9
1679	Igaci	03106	9
1680	Igreja Nova	03205	9
1681	Inhapi	03304	9
1682	Jacaré dos Homens	03403	9
1683	Jacuípe	03502	9
1684	Japaratinga	03601	9
1685	Jaramataia	03700	9
1686	Joaquim Gomes	03809	9
1687	Jundiá	03908	9
1688	Junqueiro	04005	9
1689	Lagoa da Canoa	04104	9
1690	Limoeiro de Anadia	04203	9
1691	Maceió	04302	9
1692	Major Isidoro	04401	9
1693	Maragogi	04500	9
1694	Maravilha	04609	9
1695	Marechal Deodoro	04708	9
1696	Maribondo	04807	9
1697	Mar Vermelho	04906	9
1698	Mata Grande	05002	9
1699	Matriz de Camaragibe	05101	9
1700	Messias	05200	9
1701	Minador do Negrão	05309	9
1702	Monteirópolis	05408	9
1703	Murici	05507	9
1704	Novo Lino	05606	9
1705	Olho d'Água das Flores	05705	9
1706	Olho d'Água do Casado	05804	9
1707	Olho d'Água Grande	05903	9
1708	Olivença	06000	9
1709	Ouro Branco	06109	9
1710	Palestina	06208	9
1711	Palmeira dos Índios	06307	9
1712	Pão de Açúcar	06406	9
1713	Pariconha	06422	9
1714	Paripueira	06448	9
1715	Passo de Camaragibe	06505	9
1716	Paulo Jacinto	06604	9
1717	Penedo	06703	9
1718	Piaçabuçu	06802	9
1719	Pilar	06901	9
1720	Pindoba	07008	9
1721	Piranhas	07107	9
1722	Poço das Trincheiras	07206	9
1723	Porto Calvo	07305	9
1724	Porto de Pedras	07404	9
1725	Porto Real do Colégio	07503	9
1726	Quebrangulo	07602	9
1727	Rio Largo	07701	9
1728	Roteiro	07800	9
1729	Santa Luzia do Norte	07909	9
1730	Santana do Ipanema	08006	9
1731	Santana do Mundaú	08105	9
1732	São Brás	08204	9
1733	São José da Laje	08303	9
1734	São José da Tapera	08402	9
1735	São Luís do Quitunde	08501	9
1736	São Miguel dos Campos	08600	9
1737	São Miguel dos Milagres	08709	9
1738	São Sebastião	08808	9
1739	Satuba	08907	9
1740	Senador Rui Palmeira	08956	9
1741	Tanque d'Arca	09004	9
1742	Taquarana	09103	9
1743	Teotônio Vilela	09152	9
1744	Traipu	09202	9
1745	União dos Palmares	09301	9
1746	Viçosa	09400	9
1747	Amparo de São Francisco	00100	26
1748	Aquidabã	00209	26
1749	Aracaju	00308	26
1750	Arauá	00407	26
1751	Areia Branca	00506	26
1752	Barra dos Coqueiros	00605	26
1753	Boquim	00670	26
1754	Brejo Grande	00704	26
1755	Campo do Brito	01009	26
1756	Canhoba	01108	26
1757	Canindé de São Francisco	01207	26
1758	Capela	01306	26
1759	Carira	01405	26
1760	Carmópolis	01504	26
1761	Cedro de São João	01603	26
1762	Cristinápolis	01702	26
1763	Cumbe	01900	26
1764	Divina Pastora	02007	26
1765	Estância	02106	26
1766	Feira Nova	02205	26
1767	Frei Paulo	02304	26
1768	Gararu	02403	26
1769	General Maynard	02502	26
1770	Gracho Cardoso	02601	26
1771	Ilha das Flores	02700	26
1772	Indiaroba	02809	26
1773	Itabaiana	02908	26
1774	Itabaianinha	03005	26
1775	Itabi	03104	26
1776	Itaporanga d'Ajuda	03203	26
1777	Japaratuba	03302	26
1778	Japoatã	03401	26
1779	Lagarto	03500	26
1780	Laranjeiras	03609	26
1781	Macambira	03708	26
1782	Malhada dos Bois	03807	26
1783	Malhador	03906	26
1784	Maruim	04003	26
1785	Moita Bonita	04102	26
1786	Monte Alegre de Sergipe	04201	26
1787	Muribeca	04300	26
1788	Neópolis	04409	26
1789	Nossa Senhora Aparecida	04458	26
1790	Nossa Senhora da Glória	04508	26
1791	Nossa Senhora das Dores	04607	26
1792	Nossa Senhora de Lourdes	04706	26
1793	Nossa Senhora do Socorro	04805	26
1794	Pacatuba	04904	26
1795	Pedra Mole	05000	26
1796	Pedrinhas	05109	26
1797	Pinhão	05208	26
1798	Pirambu	05307	26
1799	Poço Redondo	05406	26
1800	Poço Verde	05505	26
1801	Porto da Folha	05604	26
1802	Propriá	05703	26
1803	Riachão do Dantas	05802	26
1804	Riachuelo	05901	26
1805	Ribeirópolis	06008	26
1806	Rosário do Catete	06107	26
1807	Salgado	06206	26
1808	Santa Luzia do Itanhy	06305	26
1809	Santana do São Francisco	06404	26
1810	Santa Rosa de Lima	06503	26
1811	Santo Amaro das Brotas	06602	26
1812	São Cristóvão	06701	26
1813	São Domingos	06800	26
1814	São Francisco	06909	26
1815	São Miguel do Aleixo	07006	26
1816	Simão Dias	07105	26
1817	Siriri	07204	26
1818	Telha	07303	26
1819	Tobias Barreto	07402	26
1820	Tomar do Geru	07501	26
1821	Umbaúba	07600	26
1822	Abaíra	00108	12
1823	Abaré	00207	12
1824	Acajutiba	00306	12
1825	Adustina	00355	12
1826	Água Fria	00405	12
1827	Érico Cardoso	00504	12
1828	Aiquara	00603	12
1829	Alagoinhas	00702	12
1830	Alcobaça	00801	12
1831	Almadina	00900	12
1832	Amargosa	01007	12
1833	Amélia Rodrigues	01106	12
1834	América Dourada	01155	12
1835	Anagé	01205	12
1836	Andaraí	01304	12
1837	Andorinha	01353	12
1838	Angical	01403	12
1839	Anguera	01502	12
1840	Antas	01601	12
1841	Antônio Cardoso	01700	12
1842	Antônio Gonçalves	01809	12
1843	Aporá	01908	12
1844	Apuarema	01957	12
1845	Aracatu	02005	12
1846	Araças	02054	12
1847	Araci	02104	12
1848	Aramari	02203	12
1849	Arataca	02252	12
1850	Aratuípe	02302	12
1851	Aurelino Leal	02401	12
1852	Baianópolis	02500	12
1853	Baixa Grande	02609	12
1854	Banzaê	02658	12
1855	Barra	02708	12
1856	Barra da Estiva	02807	12
1857	Barra do Choça	02906	12
1858	Barra do Mendes	03003	12
1859	Barra do Rocha	03102	12
1860	Barreiras	03201	12
1861	Barro Alto	03235	12
1862	Barro Preto	03300	12
1863	Belmonte	03409	12
1864	Belo Campo	03508	12
1865	Biritinga	03607	12
1866	Boa Nova	03706	12
1867	Boa Vista do Tupim	03805	12
1868	Bom Jesus da Lapa	03904	12
1869	Bom Jesus da Serra	03953	12
1870	Boninal	04001	12
1871	Bonito	04050	12
1872	Boquira	04100	12
1873	Botuporã	04209	12
1874	Brejões	04308	12
1875	Brejolândia	04407	12
1876	Brotas de Macaúbas	04506	12
1877	Brumado	04605	12
1878	Buerarema	04704	12
1879	Buritirama	04753	12
1880	Caatiba	04803	12
1881	Cabaceiras do Paraguaçu	04852	12
1882	Cachoeira	04902	12
1883	Caculé	05008	12
1884	Caém	05107	12
1885	Caetanos	05156	12
1886	Caetité	05206	12
1887	Cafarnaum	05305	12
1888	Cairu	05404	12
1889	Caldeirão Grande	05503	12
1890	Camacan	05602	12
1891	Camaçari	05701	12
1892	Camamu	05800	12
1893	Campo Alegre de Lourdes	05909	12
1894	Campo Formoso	06006	12
1895	Canápolis	06105	12
1896	Canarana	06204	12
1897	Canavieiras	06303	12
1898	Candeal	06402	12
1899	Candeias	06501	12
1900	Candiba	06600	12
1901	Cândido Sales	06709	12
1902	Cansanção	06808	12
1903	Canudos	06824	12
1904	Capela do Alto Alegre	06857	12
1905	Capim Grosso	06873	12
1906	Caraíbas	06899	12
1907	Caravelas	06907	12
1908	Cardeal da Silva	07004	12
1909	Carinhanha	07103	12
1910	Casa Nova	07202	12
1911	Castro Alves	07301	12
1912	Catolândia	07400	12
1913	Catu	07509	12
1914	Caturama	07558	12
1915	Central	07608	12
1916	Chorrochó	07707	12
1917	Cícero Dantas	07806	12
1918	Cipó	07905	12
1919	Coaraci	08002	12
1920	Cocos	08101	12
1921	Conceição da Feira	08200	12
1922	Conceição do Almeida	08309	12
1923	Conceição do Coité	08408	12
1924	Conceição do Jacuípe	08507	12
1925	Conde	08606	12
1926	Condeúba	08705	12
1927	Contendas do Sincorá	08804	12
1928	Coração de Maria	08903	12
1929	Cordeiros	09000	12
1930	Coribe	09109	12
1931	Coronel João Sá	09208	12
1932	Correntina	09307	12
1933	Cotegipe	09406	12
1934	Cravolândia	09505	12
1935	Crisópolis	09604	12
1936	Cristópolis	09703	12
1937	Cruz das Almas	09802	12
1938	Curaçá	09901	12
1939	Dário Meira	10008	12
1940	Dias d'Ávila	10057	12
1941	Dom Basílio	10107	12
1942	Dom Macedo Costa	10206	12
1943	Elísio Medrado	10305	12
1944	Encruzilhada	10404	12
1945	Entre Rios	10503	12
1946	Esplanada	10602	12
1947	Euclides da Cunha	10701	12
1948	Eunápolis	10727	12
1949	Fátima	10750	12
1950	Feira da Mata	10776	12
1951	Feira de Santana	10800	12
1952	Filadélfia	10859	12
1953	Firmino Alves	10909	12
1954	Floresta Azul	11006	12
1955	Formosa do Rio Preto	11105	12
1956	Gandu	11204	12
1957	Gavião	11253	12
1958	Gentio do Ouro	11303	12
1959	Glória	11402	12
1960	Gongogi	11501	12
1961	Governador Mangabeira	11600	12
1962	Guajeru	11659	12
1963	Guanambi	11709	12
1964	Guaratinga	11808	12
1965	Heliópolis	11857	12
1966	Iaçu	11907	12
1967	Ibiassucê	12004	12
1968	Ibicaraí	12103	12
1969	Ibicoara	12202	12
1970	Ibicuí	12301	12
1971	Ibipeba	12400	12
1972	Ibipitanga	12509	12
1973	Ibiquera	12608	12
1974	Ibirapitanga	12707	12
1975	Ibirapuã	12806	12
1976	Ibirataia	12905	12
1977	Ibitiara	13002	12
1978	Ibititá	13101	12
1979	Ibotirama	13200	12
1980	Ichu	13309	12
1981	Igaporã	13408	12
1982	Igrapiúna	13457	12
1983	Iguaí	13507	12
1984	Ilhéus	13606	12
1985	Inhambupe	13705	12
1986	Ipecaetá	13804	12
1987	Ipiaú	13903	12
1988	Ipirá	14000	12
1989	Ipupiara	14109	12
1990	Irajuba	14208	12
1991	Iramaia	14307	12
1992	Iraquara	14406	12
1993	Irará	14505	12
1994	Irecê	14604	12
1995	Itabela	14653	12
1996	Itaberaba	14703	12
1997	Itabuna	14802	12
1998	Itacaré	14901	12
1999	Itaeté	15007	12
2000	Itagi	15106	12
2001	Itagibá	15205	12
2002	Itagimirim	15304	12
2003	Itaguaçu da Bahia	15353	12
2004	Itaju do Colônia	15403	12
2005	Itajuípe	15502	12
2006	Itamaraju	15601	12
2007	Itamari	15700	12
2008	Itambé	15809	12
2009	Itanagra	15908	12
2010	Itanhém	16005	12
2011	Itaparica	16104	12
2012	Itapé	16203	12
2013	Itapebi	16302	12
2014	Itapetinga	16401	12
2015	Itapicuru	16500	12
2016	Itapitanga	16609	12
2017	Itaquara	16708	12
2018	Itarantim	16807	12
2019	Itatim	16856	12
2020	Itiruçu	16906	12
2021	Itiúba	17003	12
2022	Itororó	17102	12
2023	Ituaçu	17201	12
2024	Ituberá	17300	12
2025	Iuiú	17334	12
2026	Jaborandi	17359	12
2027	Jacaraci	17409	12
2028	Jacobina	17508	12
2029	Jaguaquara	17607	12
2030	Jaguarari	17706	12
2031	Jaguaripe	17805	12
2032	Jandaíra	17904	12
2033	Jequié	18001	12
2034	Jeremoabo	18100	12
2035	Jiquiriçá	18209	12
2036	Jitaúna	18308	12
2037	João Dourado	18357	12
2038	Juazeiro	18407	12
2039	Jucuruçu	18456	12
2040	Jussara	18506	12
2041	Jussari	18555	12
2042	Jussiape	18605	12
2043	Lafaiete Coutinho	18704	12
2044	Lagoa Real	18753	12
2045	Laje	18803	12
2046	Lajedão	18902	12
2047	Lajedinho	19009	12
2048	Lajedo do Tabocal	19058	12
2049	Lamarão	19108	12
2050	Lapão	19157	12
2051	Lauro de Freitas	19207	12
2052	Lençóis	19306	12
2053	Licínio de Almeida	19405	12
2054	Livramento de Nossa Senhora	19504	12
2055	Macajuba	19603	12
2056	Macarani	19702	12
2057	Macaúbas	19801	12
2058	Macururé	19900	12
2059	Madre de Deus	19926	12
2060	Maetinga	19959	12
2061	Maiquinique	20007	12
2062	Mairi	20106	12
2063	Malhada	20205	12
2064	Malhada de Pedras	20304	12
2065	Manoel Vitorino	20403	12
2066	Mansidão	20452	12
2067	Maracás	20502	12
2068	Maragogipe	20601	12
2069	Maraú	20700	12
2070	Marcionílio Souza	20809	12
2071	Mascote	20908	12
2072	Mata de São João	21005	12
2073	Matina	21054	12
2074	Medeiros Neto	21104	12
2075	Miguel Calmon	21203	12
2076	Milagres	21302	12
2077	Mirangaba	21401	12
2078	Mirante	21450	12
2079	Monte Santo	21500	12
2080	Morpará	21609	12
2081	Morro do Chapéu	21708	12
2082	Mortugaba	21807	12
2083	Mucugê	21906	12
2084	Mucuri	22003	12
2085	Mulungu do Morro	22052	12
2086	Mundo Novo	22102	12
2087	Muniz Ferreira	22201	12
2088	Muquém de São Francisco	22250	12
2089	Muritiba	22300	12
2090	Mutuípe	22409	12
2091	Nazaré	22508	12
2092	Nilo Peçanha	22607	12
2093	Nordestina	22656	12
2094	Nova Canaã	22706	12
2095	Nova Fátima	22730	12
2096	Nova Ibiá	22755	12
2097	Nova Itarana	22805	12
2098	Nova Redenção	22854	12
2099	Nova Soure	22904	12
2100	Nova Viçosa	23001	12
2101	Novo Horizonte	23035	12
2102	Novo Triunfo	23050	12
2103	Olindina	23100	12
2104	Oliveira dos Brejinhos	23209	12
2105	Ouriçangas	23308	12
2106	Ourolândia	23357	12
2107	Palmas de Monte Alto	23407	12
2108	Palmeiras	23506	12
2109	Paramirim	23605	12
2110	Paratinga	23704	12
2111	Paripiranga	23803	12
2112	Pau Brasil	23902	12
2113	Paulo Afonso	24009	12
2114	Pé de Serra	24058	12
2115	Pedrão	24108	12
2116	Pedro Alexandre	24207	12
2117	Piatã	24306	12
2118	Pilão Arcado	24405	12
2119	Pindaí	24504	12
2120	Pindobaçu	24603	12
2121	Pintadas	24652	12
2122	Piraí do Norte	24678	12
2123	Piripá	24702	12
2124	Piritiba	24801	12
2125	Planaltino	24900	12
2126	Planalto	25006	12
2127	Poções	25105	12
2128	Pojuca	25204	12
2129	Ponto Novo	25253	12
2130	Porto Seguro	25303	12
2131	Potiraguá	25402	12
2132	Prado	25501	12
2133	Presidente Dutra	25600	12
2134	Presidente Jânio Quadros	25709	12
2135	Presidente Tancredo Neves	25758	12
2136	Queimadas	25808	12
2137	Quijingue	25907	12
2138	Quixabeira	25931	12
2139	Rafael Jambeiro	25956	12
2140	Remanso	26004	12
2141	Retirolândia	26103	12
2142	Riachão das Neves	26202	12
2143	Riachão do Jacuípe	26301	12
2144	Riacho de Santana	26400	12
2145	Ribeira do Amparo	26509	12
2146	Ribeira do Pombal	26608	12
2147	Ribeirão do Largo	26657	12
2148	Rio de Contas	26707	12
2149	Rio do Antônio	26806	12
2150	Rio do Pires	26905	12
2151	Rio Real	27002	12
2152	Rodelas	27101	12
2153	Ruy Barbosa	27200	12
2154	Salinas da Margarida	27309	12
2155	Salvador	27408	12
2156	Santa Bárbara	27507	12
2157	Santa Brígida	27606	12
2158	Santa Cruz Cabrália	27705	12
2159	Santa Cruz da Vitória	27804	12
2160	Santa Inês	27903	12
2161	Santaluz	28000	12
2162	Santa Luzia	28059	12
2163	Santa Maria da Vitória	28109	12
2164	Santana	28208	12
2165	Santanópolis	28307	12
2166	Santa Rita de Cássia	28406	12
2167	Santa Teresinha	28505	12
2168	Santo Amaro	28604	12
2169	Santo Antônio de Jesus	28703	12
2170	Santo Estêvão	28802	12
2171	São Desidério	28901	12
2172	São Domingos	28950	12
2173	São Félix	29008	12
2174	São Félix do Coribe	29057	12
2175	São Felipe	29107	12
2176	São Francisco do Conde	29206	12
2177	São Gabriel	29255	12
2178	São Gonçalo dos Campos	29305	12
2179	São José da Vitória	29354	12
2180	São José do Jacuípe	29370	12
2181	São Miguel das Matas	29404	12
2182	São Sebastião do Passé	29503	12
2183	Sapeaçu	29602	12
2184	Sátiro Dias	29701	12
2185	Saubara	29750	12
2186	Saúde	29800	12
2187	Seabra	29909	12
2188	Sebastião Laranjeiras	30006	12
2189	Senhor do Bonfim	30105	12
2190	Serra do Ramalho	30154	12
2191	Sento Sé	30204	12
2192	Serra Dourada	30303	12
2193	Serra Preta	30402	12
2194	Serrinha	30501	12
2195	Serrolândia	30600	12
2196	Simões Filho	30709	12
2197	Sítio do Mato	30758	12
2198	Sítio do Quinto	30766	12
2199	Sobradinho	30774	12
2200	Souto Soares	30808	12
2201	Tabocas do Brejo Velho	30907	12
2202	Tanhaçu	31004	12
2203	Tanque Novo	31053	12
2204	Tanquinho	31103	12
2205	Taperoá	31202	12
2206	Tapiramutá	31301	12
2207	Teixeira de Freitas	31350	12
2208	Teodoro Sampaio	31400	12
2209	Teofilândia	31509	12
2210	Teolândia	31608	12
2211	Terra Nova	31707	12
2212	Tremedal	31806	12
2213	Tucano	31905	12
2214	Uauá	32002	12
2215	Ubaíra	32101	12
2216	Ubaitaba	32200	12
2217	Ubatã	32309	12
2218	Uibaí	32408	12
2219	Umburanas	32457	12
2220	Una	32507	12
2221	Urandi	32606	12
2222	Uruçuca	32705	12
2223	Utinga	32804	12
2224	Valença	32903	12
2225	Valente	33000	12
2226	Várzea da Roça	33059	12
2227	Várzea do Poço	33109	12
2228	Várzea Nova	33158	12
2229	Varzedo	33174	12
2230	Vera Cruz	33208	12
2231	Vereda	33257	12
2232	Vitória da Conquista	33307	12
2233	Wagner	33406	12
2234	Wanderley	33455	12
2235	Wenceslau Guimarães	33505	12
2236	Xique-Xique	33604	12
2237	Abadia dos Dourados	00104	7
2238	Abaeté	00203	7
2239	Abre Campo	00302	7
2240	Acaiaca	00401	7
2241	Açucena	00500	7
2242	Água Boa	00609	7
2243	Água Comprida	00708	7
2244	Aguanil	00807	7
2245	Águas Formosas	00906	7
2246	Águas Vermelhas	01003	7
2247	Aimorés	01102	7
2248	Aiuruoca	01201	7
2249	Alagoa	01300	7
2250	Albertina	01409	7
2251	Além Paraíba	01508	7
2252	Alfenas	01607	7
2253	Alfredo Vasconcelos	01631	7
2254	Almenara	01706	7
2255	Alpercata	01805	7
2256	Alpinópolis	01904	7
2257	Alterosa	02001	7
2258	Alto Caparaó	02050	7
2259	Alto Rio Doce	02100	7
2260	Alvarenga	02209	7
2261	Alvinópolis	02308	7
2262	Alvorada de Minas	02407	7
2263	Amparo do Serra	02506	7
2264	Andradas	02605	7
2265	Cachoeira de Pajeú	02704	7
2266	Andrelândia	02803	7
2267	Angelândia	02852	7
2268	Antônio Carlos	02902	7
2269	Antônio Dias	03009	7
2270	Antônio Prado de Minas	03108	7
2271	Araçaí	03207	7
2272	Aracitaba	03306	7
2273	Araçuaí	03405	7
2274	Araguari	03504	7
2275	Arantina	03603	7
2276	Araponga	03702	7
2277	Araporã	03751	7
2278	Arapuá	03801	7
2279	Araújos	03900	7
2280	Araxá	04007	7
2281	Arceburgo	04106	7
2282	Arcos	04205	7
2283	Areado	04304	7
2284	Argirita	04403	7
2285	Aricanduva	04452	7
2286	Arinos	04502	7
2287	Astolfo Dutra	04601	7
2288	Ataléia	04700	7
2289	Augusto de Lima	04809	7
2290	Baependi	04908	7
2291	Baldim	05004	7
2292	Bambuí	05103	7
2293	Bandeira	05202	7
2294	Bandeira do Sul	05301	7
2295	Barão de Cocais	05400	7
2296	Barão de Monte Alto	05509	7
2297	Barbacena	05608	7
2298	Barra Longa	05707	7
2299	Barroso	05905	7
2300	Bela Vista de Minas	06002	7
2301	Belmiro Braga	06101	7
2302	Belo Horizonte	06200	7
2303	Belo Oriente	06309	7
2304	Belo Vale	06408	7
2305	Berilo	06507	7
2306	Bertópolis	06606	7
2307	Berizal	06655	7
2308	Betim	06705	7
2309	Bias Fortes	06804	7
2310	Bicas	06903	7
2311	Biquinhas	07000	7
2312	Boa Esperança	07109	7
2313	Bocaina de Minas	07208	7
2314	Bocaiúva	07307	7
2315	Bom Despacho	07406	7
2316	Bom Jardim de Minas	07505	7
2317	Bom Jesus da Penha	07604	7
2318	Bom Jesus do Amparo	07703	7
2319	Bom Jesus do Galho	07802	7
2320	Bom Repouso	07901	7
2321	Bom Sucesso	08008	7
2322	Bonfim	08107	7
2323	Bonfinópolis de Minas	08206	7
2324	Bonito de Minas	08255	7
2325	Borda da Mata	08305	7
2326	Botelhos	08404	7
2327	Botumirim	08503	7
2328	Brasilândia de Minas	08552	7
2329	Brasília de Minas	08602	7
2330	Brás Pires	08701	7
2331	Braúnas	08800	7
2332	Brasópolis	08909	7
2333	Brumadinho	09006	7
2334	Bueno Brandão	09105	7
2335	Buenópolis	09204	7
2336	Bugre	09253	7
2337	Buritis	09303	7
2338	Buritizeiro	09402	7
2339	Cabeceira Grande	09451	7
2340	Cabo Verde	09501	7
2341	Cachoeira da Prata	09600	7
2342	Cachoeira de Minas	09709	7
2343	Cachoeira Dourada	09808	7
2344	Caetanópolis	09907	7
2345	Caeté	10004	7
2346	Caiana	10103	7
2347	Cajuri	10202	7
2348	Caldas	10301	7
2349	Camacho	10400	7
2350	Camanducaia	10509	7
2351	Cambuí	10608	7
2352	Cambuquira	10707	7
2353	Campanário	10806	7
2354	Campanha	10905	7
2355	Campestre	11002	7
2356	Campina Verde	11101	7
2357	Campo Azul	11150	7
2358	Campo Belo	11200	7
2359	Campo do Meio	11309	7
2360	Campo Florido	11408	7
2361	Campos Altos	11507	7
2362	Campos Gerais	11606	7
2363	Canaã	11705	7
2364	Canápolis	11804	7
2365	Cana Verde	11903	7
2366	Candeias	12000	7
2367	Cantagalo	12059	7
2368	Caparaó	12109	7
2369	Capela Nova	12208	7
2370	Capelinha	12307	7
2371	Capetinga	12406	7
2372	Capim Branco	12505	7
2373	Capinópolis	12604	7
2374	Capitão Andrade	12653	7
2375	Capitão Enéas	12703	7
2376	Capitólio	12802	7
2377	Caputira	12901	7
2378	Caraí	13008	7
2379	Caranaíba	13107	7
2380	Carandaí	13206	7
2381	Carangola	13305	7
2382	Caratinga	13404	7
2383	Carbonita	13503	7
2384	Careaçu	13602	7
2385	Carlos Chagas	13701	7
2386	Carmésia	13800	7
2387	Carmo da Cachoeira	13909	7
2388	Carmo da Mata	14006	7
2389	Carmo de Minas	14105	7
2390	Carmo do Cajuru	14204	7
2391	Carmo do Paranaíba	14303	7
2392	Carmo do Rio Claro	14402	7
2393	Carmópolis de Minas	14501	7
2394	Carneirinho	14550	7
2395	Carrancas	14600	7
2396	Carvalhópolis	14709	7
2397	Carvalhos	14808	7
2398	Casa Grande	14907	7
2399	Cascalho Rico	15003	7
2400	Cássia	15102	7
2401	Conceição da Barra de Minas	15201	7
2402	Cataguases	15300	7
2403	Catas Altas	15359	7
2404	Catas Altas da Noruega	15409	7
2405	Catuji	15458	7
2406	Catuti	15474	7
2407	Caxambu	15508	7
2408	Cedro do Abaeté	15607	7
2409	Central de Minas	15706	7
2410	Centralina	15805	7
2411	Chácara	15904	7
2412	Chalé	16001	7
2413	Chapada do Norte	16100	7
2414	Chapada Gaúcha	16159	7
2415	Chiador	16209	7
2416	Cipotânea	16308	7
2417	Claraval	16407	7
2418	Claro dos Poções	16506	7
2419	Cláudio	16605	7
2420	Coimbra	16704	7
2421	Coluna	16803	7
2422	Comendador Gomes	16902	7
2423	Comercinho	17009	7
2424	Conceição da Aparecida	17108	7
2425	Conceição das Pedras	17207	7
2426	Conceição das Alagoas	17306	7
2427	Conceição de Ipanema	17405	7
2428	Conceição do Mato Dentro	17504	7
2429	Conceição do Pará	17603	7
2430	Conceição do Rio Verde	17702	7
2431	Conceição dos Ouros	17801	7
2432	Cônego Marinho	17836	7
2433	Confins	17876	7
2434	Congonhal	17900	7
2435	Congonhas	18007	7
2436	Congonhas do Norte	18106	7
2437	Conquista	18205	7
2438	Conselheiro Lafaiete	18304	7
2439	Conselheiro Pena	18403	7
2440	Consolação	18502	7
2441	Contagem	18601	7
2442	Coqueiral	18700	7
2443	Coração de Jesus	18809	7
2444	Cordisburgo	18908	7
2445	Cordislândia	19005	7
2446	Corinto	19104	7
2447	Coroaci	19203	7
2448	Coromandel	19302	7
2449	Coronel Fabriciano	19401	7
2450	Coronel Murta	19500	7
2451	Coronel Pacheco	19609	7
2452	Coronel Xavier Chaves	19708	7
2453	Córrego Danta	19807	7
2454	Córrego do Bom Jesus	19906	7
2455	Córrego Fundo	19955	7
2456	Córrego Novo	20003	7
2457	Couto de Magalhães de Minas	20102	7
2458	Crisólita	20151	7
2459	Cristais	20201	7
2460	Cristália	20300	7
2461	Cristiano Otoni	20409	7
2462	Cristina	20508	7
2463	Crucilândia	20607	7
2464	Cruzeiro da Fortaleza	20706	7
2465	Cruzília	20805	7
2466	Cuparaque	20839	7
2467	Curral de Dentro	20870	7
2468	Curvelo	20904	7
2469	Datas	21001	7
2470	Delfim Moreira	21100	7
2471	Delfinópolis	21209	7
2472	Delta	21258	7
2473	Descoberto	21308	7
2474	Desterro de Entre Rios	21407	7
2475	Desterro do Melo	21506	7
2476	Diamantina	21605	7
2477	Diogo de Vasconcelos	21704	7
2478	Dionísio	21803	7
2479	Divinésia	21902	7
2480	Divino	22009	7
2481	Divino das Laranjeiras	22108	7
2482	Divinolândia de Minas	22207	7
2483	Divinópolis	22306	7
2484	Divisa Alegre	22355	7
2485	Divisa Nova	22405	7
2486	Divisópolis	22454	7
2487	Dom Bosco	22470	7
2488	Dom Cavati	22504	7
2489	Dom Joaquim	22603	7
2490	Dom Silvério	22702	7
2491	Dom Viçoso	22801	7
2492	Dona Eusébia	22900	7
2493	Dores de Campos	23007	7
2494	Dores de Guanhães	23106	7
2495	Dores do Indaiá	23205	7
2496	Dores do Turvo	23304	7
2497	Doresópolis	23403	7
2498	Douradoquara	23502	7
2499	Durandé	23528	7
2500	Elói Mendes	23601	7
2501	Engenheiro Caldas	23700	7
2502	Engenheiro Navarro	23809	7
2503	Entre Folhas	23858	7
2504	Entre Rios de Minas	23908	7
2505	Ervália	24005	7
2506	Esmeraldas	24104	7
2507	Espera Feliz	24203	7
2508	Espinosa	24302	7
2509	Espírito Santo do Dourado	24401	7
2510	Estiva	24500	7
2511	Estrela Dalva	24609	7
2512	Estrela do Indaiá	24708	7
2513	Estrela do Sul	24807	7
2514	Eugenópolis	24906	7
2515	Ewbank da Câmara	25002	7
2516	Extrema	25101	7
2517	Fama	25200	7
2518	Faria Lemos	25309	7
2519	Felício dos Santos	25408	7
2520	São Gonçalo do Rio Preto	25507	7
2521	Felisburgo	25606	7
2522	Felixlândia	25705	7
2523	Fernandes Tourinho	25804	7
2524	Ferros	25903	7
2525	Fervedouro	25952	7
2526	Florestal	26000	7
2527	Formiga	26109	7
2528	Formoso	26208	7
2529	Fortaleza de Minas	26307	7
2530	Fortuna de Minas	26406	7
2531	Francisco Badaró	26505	7
2532	Francisco Dumont	26604	7
2533	Francisco Sá	26703	7
2534	Franciscópolis	26752	7
2535	Frei Gaspar	26802	7
2536	Frei Inocêncio	26901	7
2537	Frei Lagonegro	26950	7
2538	Fronteira	27008	7
2539	Fronteira dos Vales	27057	7
2540	Fruta de Leite	27073	7
2541	Frutal	27107	7
2542	Funilândia	27206	7
2543	Galiléia	27305	7
2544	Gameleiras	27339	7
2545	Glaucilândia	27354	7
2546	Goiabeira	27370	7
2547	Goianá	27388	7
2548	Gonçalves	27404	7
2549	Gonzaga	27503	7
2550	Gouveia	27602	7
2551	Governador Valadares	27701	7
2552	Grão Mogol	27800	7
2553	Grupiara	27909	7
2554	Guanhães	28006	7
2555	Guapé	28105	7
2556	Guaraciaba	28204	7
2557	Guaraciama	28253	7
2558	Guaranésia	28303	7
2559	Guarani	28402	7
2560	Guarará	28501	7
2561	Guarda-Mor	28600	7
2562	Guaxupé	28709	7
2563	Guidoval	28808	7
2564	Guimarânia	28907	7
2565	Guiricema	29004	7
2566	Gurinhatã	29103	7
2567	Heliodora	29202	7
2568	Iapu	29301	7
2569	Ibertioga	29400	7
2570	Ibiá	29509	7
2571	Ibiaí	29608	7
2572	Ibiracatu	29657	7
2573	Ibiraci	29707	7
2574	Ibirité	29806	7
2575	Ibitiúra de Minas	29905	7
2576	Ibituruna	30002	7
2577	Icaraí de Minas	30051	7
2578	Igarapé	30101	7
2579	Igaratinga	30200	7
2580	Iguatama	30309	7
2581	Ijaci	30408	7
2582	Ilicínea	30507	7
2583	Imbé de Minas	30556	7
2584	Inconfidentes	30606	7
2585	Indaiabira	30655	7
2586	Indianópolis	30705	7
2587	Ingaí	30804	7
2588	Inhapim	30903	7
2589	Inhaúma	31000	7
2590	Inimutaba	31109	7
2591	Ipaba	31158	7
2592	Ipanema	31208	7
2593	Ipatinga	31307	7
2594	Ipiaçu	31406	7
2595	Ipuiúna	31505	7
2596	Iraí de Minas	31604	7
2597	Itabira	31703	7
2598	Itabirinha de Mantena	31802	7
2599	Itabirito	31901	7
2600	Itacambira	32008	7
2601	Itacarambi	32107	7
2602	Itaguara	32206	7
2603	Itaipé	32305	7
2604	Itajubá	32404	7
2605	Itamarandiba	32503	7
2606	Itamarati de Minas	32602	7
2607	Itambacuri	32701	7
2608	Itambé do Mato Dentro	32800	7
2609	Itamogi	32909	7
2610	Itamonte	33006	7
2611	Itanhandu	33105	7
2612	Itanhomi	33204	7
2613	Itaobim	33303	7
2614	Itapagipe	33402	7
2615	Itapecerica	33501	7
2616	Itapeva	33600	7
2617	Itatiaiuçu	33709	7
2618	Itaú de Minas	33758	7
2619	Itaúna	33808	7
2620	Itaverava	33907	7
2621	Itinga	34004	7
2622	Itueta	34103	7
2623	Ituiutaba	34202	7
2624	Itumirim	34301	7
2625	Iturama	34400	7
2626	Itutinga	34509	7
2627	Jaboticatubas	34608	7
2628	Jacinto	34707	7
2629	Jacuí	34806	7
2630	Jacutinga	34905	7
2631	Jaguaraçu	35001	7
2632	Jaíba	35050	7
2633	Jampruca	35076	7
2634	Janaúba	35100	7
2635	Januária	35209	7
2636	Japaraíba	35308	7
2637	Japonvar	35357	7
2638	Jeceaba	35407	7
2639	Jenipapo de Minas	35456	7
2640	Jequeri	35506	7
2641	Jequitaí	35605	7
2642	Jequitibá	35704	7
2643	Jequitinhonha	35803	7
2644	Jesuânia	35902	7
2645	Joaíma	36009	7
2646	Joanésia	36108	7
2647	João Monlevade	36207	7
2648	João Pinheiro	36306	7
2649	Joaquim Felício	36405	7
2650	Jordânia	36504	7
2651	José Gonçalves de Minas	36520	7
2652	José Raydan	36553	7
2653	Josenópolis	36579	7
2654	Nova União	36603	7
2655	Juatuba	36652	7
2656	Juiz de Fora	36702	7
2657	Juramento	36801	7
2658	Juruaia	36900	7
2659	Juvenília	36959	7
2660	Ladainha	37007	7
2661	Lagamar	37106	7
2662	Lagoa da Prata	37205	7
2663	Lagoa dos Patos	37304	7
2664	Lagoa Dourada	37403	7
2665	Lagoa Formosa	37502	7
2666	Lagoa Grande	37536	7
2667	Lagoa Santa	37601	7
2668	Lajinha	37700	7
2669	Lambari	37809	7
2670	Lamim	37908	7
2671	Laranjal	38005	7
2672	Lassance	38104	7
2673	Lavras	38203	7
2674	Leandro Ferreira	38302	7
2675	Leme do Prado	38351	7
2676	Leopoldina	38401	7
2677	Liberdade	38500	7
2678	Lima Duarte	38609	7
2679	Limeira do Oeste	38625	7
2680	Lontra	38658	7
2681	Luisburgo	38674	7
2682	Luislândia	38682	7
2683	Luminárias	38708	7
2684	Luz	38807	7
2685	Machacalis	38906	7
2686	Machado	39003	7
2687	Madre de Deus de Minas	39102	7
2688	Malacacheta	39201	7
2689	Mamonas	39250	7
2690	Manga	39300	7
2691	Manhuaçu	39409	7
2692	Manhumirim	39508	7
2693	Mantena	39607	7
2694	Maravilhas	39706	7
2695	Mar de Espanha	39805	7
2696	Maria da Fé	39904	7
2697	Mariana	40001	7
2698	Marilac	40100	7
2699	Mário Campos	40159	7
2700	Maripá de Minas	40209	7
2701	Marliéria	40308	7
2702	Marmelópolis	40407	7
2703	Martinho Campos	40506	7
2704	Martins Soares	40530	7
2705	Mata Verde	40555	7
2706	Materlândia	40605	7
2707	Mateus Leme	40704	7
2708	Matias Barbosa	40803	7
2709	Matias Cardoso	40852	7
2710	Matipó	40902	7
2711	Mato Verde	41009	7
2712	Matozinhos	41108	7
2713	Matutina	41207	7
2714	Medeiros	41306	7
2715	Medina	41405	7
2716	Mendes Pimentel	41504	7
2717	Mercês	41603	7
2718	Mesquita	41702	7
2719	Minas Novas	41801	7
2720	Minduri	41900	7
2721	Mirabela	42007	7
2722	Miradouro	42106	7
2723	Miraí	42205	7
2724	Miravânia	42254	7
2725	Moeda	42304	7
2726	Moema	42403	7
2727	Monjolos	42502	7
2728	Monsenhor Paulo	42601	7
2729	Montalvânia	42700	7
2730	Monte Alegre de Minas	42809	7
2731	Monte Azul	42908	7
2732	Monte Belo	43005	7
2733	Monte Carmelo	43104	7
2734	Monte Formoso	43153	7
2735	Monte Santo de Minas	43203	7
2736	Montes Claros	43302	7
2737	Monte Sião	43401	7
2738	Montezuma	43450	7
2739	Morada Nova de Minas	43500	7
2740	Morro da Garça	43609	7
2741	Morro do Pilar	43708	7
2742	Munhoz	43807	7
2743	Muriaé	43906	7
2744	Mutum	44003	7
2745	Muzambinho	44102	7
2746	Nacip Raydan	44201	7
2747	Nanuque	44300	7
2748	Naque	44359	7
2749	Natalândia	44375	7
2750	Natércia	44409	7
2751	Nazareno	44508	7
2752	Nepomuceno	44607	7
2753	Ninheira	44656	7
2754	Nova Belém	44672	7
2755	Nova Era	44706	7
2756	Nova Lima	44805	7
2757	Nova Módica	44904	7
2758	Nova Ponte	45000	7
2759	Nova Porteirinha	45059	7
2760	Nova Resende	45109	7
2761	Nova Serrana	45208	7
2762	Novo Cruzeiro	45307	7
2763	Novo Oriente de Minas	45356	7
2764	Novorizonte	45372	7
2765	Olaria	45406	7
2766	Olhos-d'Água	45455	7
2767	Olímpio Noronha	45505	7
2768	Oliveira	45604	7
2769	Oliveira Fortes	45703	7
2770	Onça de Pitangui	45802	7
2771	Oratórios	45851	7
2772	Orizânia	45877	7
2773	Ouro Branco	45901	7
2774	Ouro Fino	46008	7
2775	Ouro Preto	46107	7
2776	Ouro Verde de Minas	46206	7
2777	Padre Carvalho	46255	7
2778	Padre Paraíso	46305	7
2779	Paineiras	46404	7
2780	Pains	46503	7
2781	Pai Pedro	46552	7
2782	Paiva	46602	7
2783	Palma	46701	7
2784	Palmópolis	46750	7
2785	Papagaios	46909	7
2786	Paracatu	47006	7
2787	Pará de Minas	47105	7
2788	Paraguaçu	47204	7
2789	Paraisópolis	47303	7
2790	Paraopeba	47402	7
2791	Passabém	47501	7
2792	Passa Quatro	47600	7
2793	Passa Tempo	47709	7
2794	Passa-Vinte	47808	7
2795	Passos	47907	7
2796	Patis	47956	7
2797	Patos de Minas	48004	7
2798	Patrocínio	48103	7
2799	Patrocínio do Muriaé	48202	7
2800	Paula Cândido	48301	7
2801	Paulistas	48400	7
2802	Pavão	48509	7
2803	Peçanha	48608	7
2804	Pedra Azul	48707	7
2805	Pedra Bonita	48756	7
2806	Pedra do Anta	48806	7
2807	Pedra do Indaiá	48905	7
2808	Pedra Dourada	49002	7
2809	Pedralva	49101	7
2810	Pedras de Maria da Cruz	49150	7
2811	Pedrinópolis	49200	7
2812	Pedro Leopoldo	49309	7
2813	Pedro Teixeira	49408	7
2814	Pequeri	49507	7
2815	Pequi	49606	7
2816	Perdigão	49705	7
2817	Perdizes	49804	7
2818	Perdões	49903	7
2819	Periquito	49952	7
2820	Pescador	50000	7
2821	Piau	50109	7
2822	Piedade de Caratinga	50158	7
2823	Piedade de Ponte Nova	50208	7
2824	Piedade do Rio Grande	50307	7
2825	Piedade dos Gerais	50406	7
2826	Pimenta	50505	7
2827	Pingo-d'Água	50539	7
2828	Pintópolis	50570	7
2829	Piracema	50604	7
2830	Pirajuba	50703	7
2831	Piranga	50802	7
2832	Piranguçu	50901	7
2833	Piranguinho	51008	7
2834	Pirapetinga	51107	7
2835	Pirapora	51206	7
2836	Piraúba	51305	7
2837	Pitangui	51404	7
2838	Piumhi	51503	7
2839	Planura	51602	7
2840	Poço Fundo	51701	7
2841	Poços de Caldas	51800	7
2842	Pocrane	51909	7
2843	Pompéu	52006	7
2844	Ponte Nova	52105	7
2845	Ponto Chique	52131	7
2846	Ponto dos Volantes	52170	7
2847	Porteirinha	52204	7
2848	Porto Firme	52303	7
2849	Poté	52402	7
2850	Pouso Alegre	52501	7
2851	Pouso Alto	52600	7
2852	Prados	52709	7
2853	Prata	52808	7
2854	Pratápolis	52907	7
2855	Pratinha	53004	7
2856	Presidente Bernardes	53103	7
2857	Presidente Juscelino	53202	7
2858	Presidente Kubitschek	53301	7
2859	Presidente Olegário	53400	7
2860	Alto Jequitibá	53509	7
2861	Prudente de Morais	53608	7
2862	Quartel Geral	53707	7
2863	Queluzito	53806	7
2864	Raposos	53905	7
2865	Raul Soares	54002	7
2866	Recreio	54101	7
2867	Reduto	54150	7
2868	Resende Costa	54200	7
2869	Resplendor	54309	7
2870	Ressaquinha	54408	7
2871	Riachinho	54457	7
2872	Riacho dos Machados	54507	7
2873	Ribeirão das Neves	54606	7
2874	Ribeirão Vermelho	54705	7
2875	Rio Acima	54804	7
2876	Rio Casca	54903	7
2877	Rio Doce	55009	7
2878	Rio do Prado	55108	7
2879	Rio Espera	55207	7
2880	Rio Manso	55306	7
2881	Rio Novo	55405	7
2882	Rio Paranaíba	55504	7
2883	Rio Pardo de Minas	55603	7
2884	Rio Piracicaba	55702	7
2885	Rio Pomba	55801	7
2886	Rio Preto	55900	7
2887	Rio Vermelho	56007	7
2888	Ritápolis	56106	7
2889	Rochedo de Minas	56205	7
2890	Rodeiro	56304	7
2891	Romaria	56403	7
2892	Rosário da Limeira	56452	7
2893	Rubelita	56502	7
2894	Rubim	56601	7
2895	Sabará	56700	7
2896	Sabinópolis	56809	7
2897	Sacramento	56908	7
2898	Salinas	57005	7
2899	Salto da Divisa	57104	7
2900	Santa Bárbara	57203	7
2901	Santa Bárbara do Leste	57252	7
2902	Santa Bárbara do Monte Verde	57278	7
2903	Santa Bárbara do Tugúrio	57302	7
2904	Santa Cruz de Minas	57336	7
2905	Santa Cruz de Salinas	57377	7
2906	Santa Cruz do Escalvado	57401	7
2907	Santa Efigênia de Minas	57500	7
2908	Santa Fé de Minas	57609	7
2909	Santa Helena de Minas	57658	7
2910	Santa Juliana	57708	7
2911	Santa Luzia	57807	7
2912	Santa Margarida	57906	7
2913	Santa Maria de Itabira	58003	7
2914	Santa Maria do Salto	58102	7
2915	Santa Maria do Suaçuí	58201	7
2916	Santana da Vargem	58300	7
2917	Santana de Cataguases	58409	7
2918	Santana de Pirapama	58508	7
2919	Santana do Deserto	58607	7
2920	Santana do Garambéu	58706	7
2921	Santana do Jacaré	58805	7
2922	Santana do Manhuaçu	58904	7
2923	Santana do Paraíso	58953	7
2924	Santana do Riacho	59001	7
2925	Santana dos Montes	59100	7
2926	Santa Rita de Caldas	59209	7
2927	Santa Rita de Jacutinga	59308	7
2928	Santa Rita de Minas	59357	7
2929	Santa Rita de Ibitipoca	59407	7
2930	Santa Rita do Itueto	59506	7
2931	Santa Rita do Sapucaí	59605	7
2932	Santa Rosa da Serra	59704	7
2933	Santa Vitória	59803	7
2934	Santo Antônio do Amparo	59902	7
2935	Santo Antônio do Aventureiro	60009	7
2936	Santo Antônio do Grama	60108	7
2937	Santo Antônio do Itambé	60207	7
2938	Santo Antônio do Jacinto	60306	7
2939	Santo Antônio do Monte	60405	7
2940	Santo Antônio do Retiro	60454	7
2941	Santo Antônio do Rio Abaixo	60504	7
2942	Santo Hipólito	60603	7
2943	Santos Dumont	60702	7
2944	São Bento Abade	60801	7
2945	São Brás do Suaçuí	60900	7
2946	São Domingos das Dores	60959	7
2947	São Domingos do Prata	61007	7
2948	São Félix de Minas	61056	7
2949	São Francisco	61106	7
2950	São Francisco de Paula	61205	7
2951	São Francisco de Sales	61304	7
2952	São Francisco do Glória	61403	7
2953	São Geraldo	61502	7
2954	São Geraldo da Piedade	61601	7
2955	São Geraldo do Baixio	61650	7
2956	São Gonçalo do Abaeté	61700	7
2957	São Gonçalo do Pará	61809	7
2958	São Gonçalo do Rio Abaixo	61908	7
2959	São Gonçalo do Sapucaí	62005	7
2960	São Gotardo	62104	7
2961	São João Batista do Glória	62203	7
2962	São João da Lagoa	62252	7
2963	São João da Mata	62302	7
2964	São João da Ponte	62401	7
2965	São João das Missões	62450	7
2966	São João del Rei	62500	7
2967	São João do Manhuaçu	62559	7
2968	São João do Manteninha	62575	7
2969	São João do Oriente	62609	7
2970	São João do Pacuí	62658	7
2971	São João do Paraíso	62708	7
2972	São João Evangelista	62807	7
2973	São João Nepomuceno	62906	7
2974	São Joaquim de Bicas	62922	7
2975	São José da Barra	62948	7
2976	São José da Lapa	62955	7
2977	São José da Safira	63003	7
2978	São José da Varginha	63102	7
2979	São José do Alegre	63201	7
2980	São José do Divino	63300	7
2981	São José do Goiabal	63409	7
2982	São José do Jacuri	63508	7
2983	São José do Mantimento	63607	7
2984	São Lourenço	63706	7
2985	São Miguel do Anta	63805	7
2986	São Pedro da União	63904	7
2987	São Pedro dos Ferros	64001	7
2988	São Pedro do Suaçuí	64100	7
2989	São Romão	64209	7
2990	São Roque de Minas	64308	7
2991	São Sebastião da Bela Vista	64407	7
2992	São Sebastião da Vargem Alegre	64431	7
2993	São Sebastião do Anta	64472	7
2994	São Sebastião do Maranhão	64506	7
2995	São Sebastião do Oeste	64605	7
2996	São Sebastião do Paraíso	64704	7
2997	São Sebastião do Rio Preto	64803	7
2998	São Sebastião do Rio Verde	64902	7
2999	São Tiago	65008	7
3000	São Tomás de Aquino	65107	7
3001	São Thomé das Letras	65206	7
3002	São Vicente de Minas	65305	7
3003	Sapucaí-Mirim	65404	7
3004	Sardoá	65503	7
3005	Sarzedo	65537	7
3006	Setubinha	65552	7
3007	Sem-Peixe	65560	7
3008	Senador Amaral	65578	7
3009	Senador Cortes	65602	7
3010	Senador Firmino	65701	7
3011	Senador José Bento	65800	7
3012	Senador Modestino Gonçalves	65909	7
3013	Senhora de Oliveira	66006	7
3014	Senhora do Porto	66105	7
3015	Senhora dos Remédios	66204	7
3016	Sericita	66303	7
3017	Seritinga	66402	7
3018	Serra Azul de Minas	66501	7
3019	Serra da Saudade	66600	7
3020	Serra dos Aimorés	66709	7
3021	Serra do Salitre	66808	7
3022	Serrania	66907	7
3023	Serranópolis de Minas	66956	7
3024	Serranos	67004	7
3025	Serro	67103	7
3026	Sete Lagoas	67202	7
3027	Silveirânia	67301	7
3028	Silvianópolis	67400	7
3029	Simão Pereira	67509	7
3030	Simonésia	67608	7
3031	Sobrália	67707	7
3032	Soledade de Minas	67806	7
3033	Tabuleiro	67905	7
3034	Taiobeiras	68002	7
3035	Taparuba	68051	7
3036	Tapira	68101	7
3037	Tapiraí	68200	7
3038	Taquaraçu de Minas	68309	7
3039	Tarumirim	68408	7
3040	Teixeiras	68507	7
3041	Teófilo Otoni	68606	7
3042	Timóteo	68705	7
3043	Tiradentes	68804	7
3044	Tiros	68903	7
3045	Tocantins	69000	7
3046	Tocos do Moji	69059	7
3047	Toledo	69109	7
3048	Tombos	69208	7
3049	Três Corações	69307	7
3050	Três Marias	69356	7
3051	Três Pontas	69406	7
3052	Tumiritinga	69505	7
3053	Tupaciguara	69604	7
3054	Turmalina	69703	7
3055	Turvolândia	69802	7
3056	Ubá	69901	7
3057	Ubaí	70008	7
3058	Ubaporanga	70057	7
3059	Uberaba	70107	7
3060	Uberlândia	70206	7
3061	Umburatiba	70305	7
3062	Unaí	70404	7
3063	União de Minas	70438	7
3064	Uruana de Minas	70479	7
3065	Urucânia	70503	7
3066	Urucuia	70529	7
3067	Vargem Alegre	70578	7
3068	Vargem Bonita	70602	7
3069	Vargem Grande do Rio Pardo	70651	7
3070	Varginha	70701	7
3071	Varjão de Minas	70750	7
3072	Várzea da Palma	70800	7
3073	Varzelândia	70909	7
3074	Vazante	71006	7
3075	Verdelândia	71030	7
3076	Veredinha	71071	7
3077	Veríssimo	71105	7
3078	Vermelho Novo	71154	7
3079	Vespasiano	71204	7
3080	Viçosa	71303	7
3081	Vieiras	71402	7
3082	Mathias Lobato	71501	7
3083	Virgem da Lapa	71600	7
3084	Virgínia	71709	7
3085	Virginópolis	71808	7
3086	Virgolândia	71907	7
3087	Visconde do Rio Branco	72004	7
3088	Volta Grande	72103	7
3089	Wenceslau Braz	72202	7
3090	Afonso Cláudio	00102	14
3091	Águia Branca	00136	14
3092	Água Doce do Norte	00169	14
3093	Alegre	00201	14
3094	Alfredo Chaves	00300	14
3095	Alto Rio Novo	00359	14
3096	Anchieta	00409	14
3097	Apiacá	00508	14
3098	Aracruz	00607	14
3099	Atilio Vivacqua	00706	14
3100	Baixo Guandu	00805	14
3101	Barra de São Francisco	00904	14
3102	Boa Esperança	01001	14
3103	Bom Jesus do Norte	01100	14
3104	Brejetuba	01159	14
3105	Cachoeiro de Itapemirim	01209	14
3106	Cariacica	01308	14
3107	Castelo	01407	14
3108	Colatina	01506	14
3109	Conceição da Barra	01605	14
3110	Conceição do Castelo	01704	14
3111	Divino de São Lourenço	01803	14
3112	Domingos Martins	01902	14
3113	Dores do Rio Preto	02009	14
3114	Ecoporanga	02108	14
3115	Fundão	02207	14
3116	Guaçuí	02306	14
3117	Guarapari	02405	14
3118	Ibatiba	02454	14
3119	Ibiraçu	02504	14
3120	Ibitirama	02553	14
3121	Iconha	02603	14
3122	Irupi	02652	14
3123	Itaguaçu	02702	14
3124	Itapemirim	02801	14
3125	Itarana	02900	14
3126	Iúna	03007	14
3127	Jaguaré	03056	14
3128	Jerônimo Monteiro	03106	14
3129	João Neiva	03130	14
3130	Laranja da Terra	03163	14
3131	Linhares	03205	14
3132	Mantenópolis	03304	14
3133	Marataízes	03320	14
3134	Marechal Floriano	03346	14
3135	Marilândia	03353	14
3136	Mimoso do Sul	03403	14
3137	Montanha	03502	14
3138	Mucurici	03601	14
3139	Muniz Freire	03700	14
3140	Muqui	03809	14
3141	Nova Venécia	03908	14
3142	Pancas	04005	14
3143	Pedro Canário	04054	14
3144	Pinheiros	04104	14
3145	Piúma	04203	14
3146	Ponto Belo	04252	14
3147	Presidente Kennedy	04302	14
3148	Rio Bananal	04351	14
3149	Rio Novo do Sul	04401	14
3150	Santa Leopoldina	04500	14
3151	Santa Maria de Jetibá	04559	14
3152	Santa Teresa	04609	14
3153	São Domingos do Norte	04658	14
3154	São Gabriel da Palha	04708	14
3155	São José do Calçado	04807	14
3156	São Mateus	04906	14
3157	São Roque do Canaã	04955	14
3158	Serra	05002	14
3159	Sooretama	05010	14
3160	Vargem Alta	05036	14
3161	Venda Nova do Imigrante	05069	14
3162	Viana	05101	14
3163	Vila Pavão	05150	14
3164	Vila Valério	05176	14
3165	Vila Velha	05200	14
3166	Vitória	05309	14
3167	Angra dos Reis	00100	4
3168	Aperibé	00159	4
3169	Araruama	00209	4
3170	Areal	00225	4
3171	Armação dos Búzios	00233	4
3172	Arraial do Cabo	00258	4
3173	Barra do Piraí	00308	4
3174	Barra Mansa	00407	4
3175	Belford Roxo	00456	4
3176	Bom Jardim	00506	4
3177	Bom Jesus do Itabapoana	00605	4
3178	Cabo Frio	00704	4
3179	Cachoeiras de Macacu	00803	4
3180	Cambuci	00902	4
3181	Carapebus	00936	4
3182	Comendador Levy Gasparian	00951	4
3183	Campos dos Goytacazes	01009	4
3184	Cantagalo	01108	4
3185	Cardoso Moreira	01157	4
3186	Carmo	01207	4
3187	Casimiro de Abreu	01306	4
3188	Conceição de Macabu	01405	4
3189	Cordeiro	01504	4
3190	Duas Barras	01603	4
3191	Duque de Caxias	01702	4
3192	Engenheiro Paulo de Frontin	01801	4
3193	Guapimirim	01850	4
3194	Iguaba Grande	01876	4
3195	Itaboraí	01900	4
3196	Itaguaí	02007	4
3197	Italva	02056	4
3198	Itaocara	02106	4
3199	Itaperuna	02205	4
3200	Itatiaia	02254	4
3201	Japeri	02270	4
3202	Laje do Muriaé	02304	4
3203	Macaé	02403	4
3204	Macuco	02452	4
3205	Magé	02502	4
3206	Mangaratiba	02601	4
3207	Maricá	02700	4
3208	Mendes	02809	4
3209	Miguel Pereira	02908	4
3210	Miracema	03005	4
3211	Natividade	03104	4
3212	Nilópolis	03203	4
3213	Niterói	03302	4
3214	Nova Friburgo	03401	4
3215	Nova Iguaçu	03500	4
3216	Paracambi	03609	4
3217	Paraíba do Sul	03708	4
3218	Parati	03807	4
3219	Paty do Alferes	03856	4
3220	Petrópolis	03906	4
3221	Pinheiral	03955	4
3222	Piraí	04003	4
3223	Porciúncula	04102	4
3224	Porto Real	04110	4
3225	Quatis	04128	4
3226	Queimados	04144	4
3227	Quissamã	04151	4
3228	Resende	04201	4
3229	Rio Bonito	04300	4
3230	Rio Claro	04409	4
3231	Rio das Flores	04508	4
3232	Rio das Ostras	04524	4
3233	Rio de Janeiro	04557	4
3234	Santa Maria Madalena	04607	4
3235	Santo Antônio de Pádua	04706	4
3236	São Francisco de Itabapoana	04755	4
3237	São Fidélis	04805	4
3238	São Gonçalo	04904	4
3239	São João da Barra	05000	4
3240	São João de Meriti	05109	4
3241	São José de Ubá	05133	4
3242	São José do Vale do Rio Preto	05158	4
3243	São Pedro da Aldeia	05208	4
3244	São Sebastião do Alto	05307	4
3245	Sapucaia	05406	4
3246	Saquarema	05505	4
3247	Seropédica	05554	4
3248	Silva Jardim	05604	4
3249	Sumidouro	05703	4
3250	Tanguá	05752	4
3251	Teresópolis	05802	4
3252	Trajano de Morais	05901	4
3253	Três Rios	06008	4
3254	Valença	06107	4
3255	Varre-Sai	06156	4
3256	Vassouras	06206	4
3257	Volta Redonda	06305	4
3258	Adamantina	00105	5
3259	Adolfo	00204	5
3260	Aguaí	00303	5
3261	Águas da Prata	00402	5
3262	Águas de Lindóia	00501	5
3263	Águas de Santa Bárbara	00550	5
3264	Águas de São Pedro	00600	5
3265	Agudos	00709	5
3266	Alambari	00758	5
3267	Alfredo Marcondes	00808	5
3268	Altair	00907	5
3269	Altinópolis	01004	5
3270	Alto Alegre	01103	5
3271	Alumínio	01152	5
3272	Álvares Florence	01202	5
3273	Álvares Machado	01301	5
3274	Álvaro de Carvalho	01400	5
3275	Alvinlândia	01509	5
3276	Americana	01608	5
3277	Américo Brasiliense	01707	5
3278	Américo de Campos	01806	5
3279	Amparo	01905	5
3280	Analândia	02002	5
3281	Andradina	02101	5
3282	Angatuba	02200	5
3283	Anhembi	02309	5
3284	Anhumas	02408	5
3285	Aparecida	02507	5
3286	Aparecida d'Oeste	02606	5
3287	Apiaí	02705	5
3288	Araçariguama	02754	5
3289	Araçatuba	02804	5
3290	Araçoiaba da Serra	02903	5
3291	Aramina	03000	5
3292	Arandu	03109	5
3293	Arapeí	03158	5
3294	Araraquara	03208	5
3295	Araras	03307	5
3296	Arco-Íris	03356	5
3297	Arealva	03406	5
3298	Areias	03505	5
3299	Areiópolis	03604	5
3300	Ariranha	03703	5
3301	Artur Nogueira	03802	5
3302	Arujá	03901	5
3303	Aspásia	03950	5
3304	Assis	04008	5
3305	Atibaia	04107	5
3306	Auriflama	04206	5
3307	Avaí	04305	5
3308	Avanhandava	04404	5
3309	Avaré	04503	5
3310	Bady Bassitt	04602	5
3311	Balbinos	04701	5
3312	Bálsamo	04800	5
3313	Bananal	04909	5
3314	Barão de Antonina	05005	5
3315	Barbosa	05104	5
3316	Bariri	05203	5
3317	Barra Bonita	05302	5
3318	Barra do Chapéu	05351	5
3319	Barra do Turvo	05401	5
3320	Barretos	05500	5
3321	Barrinha	05609	5
3322	Barueri	05708	5
3323	Bastos	05807	5
3324	Batatais	05906	5
3325	Bauru	06003	5
3326	Bebedouro	06102	5
3327	Bento de Abreu	06201	5
3328	Bernardino de Campos	06300	5
3329	Bertioga	06359	5
3330	Bilac	06409	5
3331	Birigui	06508	5
3332	Biritiba-Mirim	06607	5
3333	Boa Esperança do Sul	06706	5
3334	Bocaina	06805	5
3335	Bofete	06904	5
3336	Boituva	07001	5
3337	Bom Jesus dos Perdões	07100	5
3338	Bom Sucesso de Itararé	07159	5
3339	Borá	07209	5
3340	Boracéia	07308	5
3341	Borborema	07407	5
3342	Borebi	07456	5
3343	Botucatu	07506	5
3344	Bragança Paulista	07605	5
3345	Braúna	07704	5
3346	Brejo Alegre	07753	5
3347	Brodowski	07803	5
3348	Brotas	07902	5
3349	Buri	08009	5
3350	Buritama	08108	5
3351	Buritizal	08207	5
3352	Cabrália Paulista	08306	5
3353	Cabreúva	08405	5
3354	Caçapava	08504	5
3355	Cachoeira Paulista	08603	5
3356	Caconde	08702	5
3357	Cafelândia	08801	5
3358	Caiabu	08900	5
3359	Caieiras	09007	5
3360	Caiuá	09106	5
3361	Cajamar	09205	5
3362	Cajati	09254	5
3363	Cajobi	09304	5
3364	Cajuru	09403	5
3365	Campina do Monte Alegre	09452	5
3366	Campinas	09502	5
3367	Campo Limpo Paulista	09601	5
3368	Campos do Jordão	09700	5
3369	Campos Novos Paulista	09809	5
3370	Cananéia	09908	5
3371	Canas	09957	5
3372	Cândido Mota	10005	5
3373	Cândido Rodrigues	10104	5
3374	Canitar	10153	5
3375	Capão Bonito	10203	5
3376	Capela do Alto	10302	5
3377	Capivari	10401	5
3378	Caraguatatuba	10500	5
3379	Carapicuíba	10609	5
3380	Cardoso	10708	5
3381	Casa Branca	10807	5
3382	Cássia dos Coqueiros	10906	5
3383	Castilho	11003	5
3384	Catanduva	11102	5
3385	Catiguá	11201	5
3386	Cedral	11300	5
3387	Cerqueira César	11409	5
3388	Cerquilho	11508	5
3389	Cesário Lange	11607	5
3390	Charqueada	11706	5
3391	Clementina	11904	5
3392	Colina	12001	5
3393	Colômbia	12100	5
3394	Conchal	12209	5
3395	Conchas	12308	5
3396	Cordeirópolis	12407	5
3397	Coroados	12506	5
3398	Coronel Macedo	12605	5
3399	Corumbataí	12704	5
3400	Cosmópolis	12803	5
3401	Cosmorama	12902	5
3402	Cotia	13009	5
3403	Cravinhos	13108	5
3404	Cristais Paulista	13207	5
3405	Cruzália	13306	5
3406	Cruzeiro	13405	5
3407	Cubatão	13504	5
3408	Cunha	13603	5
3409	Descalvado	13702	5
3410	Diadema	13801	5
3411	Dirce Reis	13850	5
3412	Divinolândia	13900	5
3413	Dobrada	14007	5
3414	Dois Córregos	14106	5
3415	Dolcinópolis	14205	5
3416	Dourado	14304	5
3417	Dracena	14403	5
3418	Duartina	14502	5
3419	Dumont	14601	5
3420	Echaporã	14700	5
3421	Eldorado	14809	5
3422	Elias Fausto	14908	5
3423	Elisiário	14924	5
3424	Embaúba	14957	5
3425	Embu	15004	5
3426	Embu-Guaçu	15103	5
3427	Emilianópolis	15129	5
3428	Engenheiro Coelho	15152	5
3429	Espírito Santo do Pinhal	15186	5
3430	Espírito Santo do Turvo	15194	5
3431	Estrela d'Oeste	15202	5
3432	Estrela do Norte	15301	5
3433	Euclides da Cunha Paulista	15350	5
3434	Fartura	15400	5
3435	Fernandópolis	15509	5
3436	Fernando Prestes	15608	5
3437	Fernão	15657	5
3438	Ferraz de Vasconcelos	15707	5
3439	Flora Rica	15806	5
3440	Floreal	15905	5
3441	Flórida Paulista	16002	5
3442	Florínia	16101	5
3443	Franca	16200	5
3444	Francisco Morato	16309	5
3445	Franco da Rocha	16408	5
3446	Gabriel Monteiro	16507	5
3447	Gália	16606	5
3448	Garça	16705	5
3449	Gastão Vidigal	16804	5
3450	Gavião Peixoto	16853	5
3451	General Salgado	16903	5
3452	Getulina	17000	5
3453	Glicério	17109	5
3454	Guaiçara	17208	5
3455	Guaimbê	17307	5
3456	Guaíra	17406	5
3457	Guapiaçu	17505	5
3458	Guapiara	17604	5
3459	Guará	17703	5
3460	Guaraçaí	17802	5
3461	Guaraci	17901	5
3462	Guarani d'Oeste	18008	5
3463	Guarantã	18107	5
3464	Guararapes	18206	5
3465	Guararema	18305	5
3466	Guaratinguetá	18404	5
3467	Guareí	18503	5
3468	Guariba	18602	5
3469	Guarujá	18701	5
3470	Guarulhos	18800	5
3471	Guatapará	18859	5
3472	Guzolândia	18909	5
3473	Herculândia	19006	5
3474	Holambra	19055	5
3475	Hortolândia	19071	5
3476	Iacanga	19105	5
3477	Iacri	19204	5
3478	Iaras	19253	5
3479	Ibaté	19303	5
3480	Ibirá	19402	5
3481	Ibirarema	19501	5
3482	Ibitinga	19600	5
3483	Ibiúna	19709	5
3484	Icém	19808	5
3485	Iepê	19907	5
3486	Igaraçu do Tietê	20004	5
3487	Igarapava	20103	5
3488	Igaratá	20202	5
3489	Iguape	20301	5
3490	Ilhabela	20400	5
3491	Ilha Comprida	20426	5
3492	Ilha Solteira	20442	5
3493	Indaiatuba	20509	5
3494	Indiana	20608	5
3495	Indiaporã	20707	5
3496	Inúbia Paulista	20806	5
3497	Ipauçu	20905	5
3498	Iperó	21002	5
3499	Ipeúna	21101	5
3500	Ipiguá	21150	5
3501	Iporanga	21200	5
3502	Ipuã	21309	5
3503	Iracemápolis	21408	5
3504	Irapuã	21507	5
3505	Irapuru	21606	5
3506	Itaberá	21705	5
3507	Itaí	21804	5
3508	Itajobi	21903	5
3509	Itaju	22000	5
3510	Itanhaém	22109	5
3511	Itaóca	22158	5
3512	Itapecerica da Serra	22208	5
3513	Itapetininga	22307	5
3514	Itapeva	22406	5
3515	Itapevi	22505	5
3516	Itapira	22604	5
3517	Itapirapuã Paulista	22653	5
3518	Itápolis	22703	5
3519	Itaporanga	22802	5
3520	Itapuí	22901	5
3521	Itapura	23008	5
3522	Itaquaquecetuba	23107	5
3523	Itararé	23206	5
3524	Itariri	23305	5
3525	Itatiba	23404	5
3526	Itatinga	23503	5
3527	Itirapina	23602	5
3528	Itirapuã	23701	5
3529	Itobi	23800	5
3530	Itu	23909	5
3531	Itupeva	24006	5
3532	Ituverava	24105	5
3533	Jaborandi	24204	5
3534	Jaboticabal	24303	5
3535	Jacareí	24402	5
3536	Jaci	24501	5
3537	Jacupiranga	24600	5
3538	Jaguariúna	24709	5
3539	Jales	24808	5
3540	Jambeiro	24907	5
3541	Jandira	25003	5
3542	Jardinópolis	25102	5
3543	Jarinu	25201	5
3544	Jaú	25300	5
3545	Jeriquara	25409	5
3546	Joanópolis	25508	5
3547	João Ramalho	25607	5
3548	José Bonifácio	25706	5
3549	Júlio Mesquita	25805	5
3550	Jumirim	25854	5
3551	Jundiaí	25904	5
3552	Junqueirópolis	26001	5
3553	Juquiá	26100	5
3554	Juquitiba	26209	5
3555	Lagoinha	26308	5
3556	Laranjal Paulista	26407	5
3557	Lavínia	26506	5
3558	Lavrinhas	26605	5
3559	Leme	26704	5
3560	Lençóis Paulista	26803	5
3561	Limeira	26902	5
3562	Lindóia	27009	5
3563	Lins	27108	5
3564	Lorena	27207	5
3565	Lourdes	27256	5
3566	Louveira	27306	5
3567	Lucélia	27405	5
3568	Lucianópolis	27504	5
3569	Luís Antônio	27603	5
3570	Luiziânia	27702	5
3571	Lupércio	27801	5
3572	Lutécia	27900	5
3573	Macatuba	28007	5
3574	Macaubal	28106	5
3575	Macedônia	28205	5
3576	Magda	28304	5
3577	Mairinque	28403	5
3578	Mairiporã	28502	5
3579	Manduri	28601	5
3580	Marabá Paulista	28700	5
3581	Maracaí	28809	5
3582	Marapoama	28858	5
3583	Mariápolis	28908	5
3584	Marília	29005	5
3585	Marinópolis	29104	5
3586	Martinópolis	29203	5
3587	Matão	29302	5
3588	Mauá	29401	5
3589	Mendonça	29500	5
3590	Meridiano	29609	5
3591	Mesópolis	29658	5
3592	Miguelópolis	29708	5
3593	Mineiros do Tietê	29807	5
3594	Miracatu	29906	5
3595	Mira Estrela	30003	5
3596	Mirandópolis	30102	5
3597	Mirante do Paranapanema	30201	5
3598	Mirassol	30300	5
3599	Mirassolândia	30409	5
3600	Mococa	30508	5
3601	Moji das Cruzes	30607	5
3602	Mogi Guaçu	30706	5
3603	Moji-Mirim	30805	5
3604	Mombuca	30904	5
3605	Monções	31001	5
3606	Mongaguá	31100	5
3607	Monte Alegre do Sul	31209	5
3608	Monte Alto	31308	5
3609	Monte Aprazível	31407	5
3610	Monte Azul Paulista	31506	5
3611	Monte Castelo	31605	5
3612	Monteiro Lobato	31704	5
3613	Monte Mor	31803	5
3614	Morro Agudo	31902	5
3615	Morungaba	32009	5
3616	Motuca	32058	5
3617	Murutinga do Sul	32108	5
3618	Nantes	32157	5
3619	Narandiba	32207	5
3620	Natividade da Serra	32306	5
3621	Nazaré Paulista	32405	5
3622	Neves Paulista	32504	5
3623	Nhandeara	32603	5
3624	Nipoã	32702	5
3625	Nova Aliança	32801	5
3626	Nova Campina	32827	5
3627	Nova Canaã Paulista	32843	5
3628	Nova Castilho	32868	5
3629	Nova Europa	32900	5
3630	Nova Granada	33007	5
3631	Nova Guataporanga	33106	5
3632	Nova Independência	33205	5
3633	Novais	33254	5
3634	Nova Luzitânia	33304	5
3635	Nova Odessa	33403	5
3636	Novo Horizonte	33502	5
3637	Nuporanga	33601	5
3638	Ocauçu	33700	5
3639	Óleo	33809	5
3640	Olímpia	33908	5
3641	Onda Verde	34005	5
3642	Oriente	34104	5
3643	Orindiúva	34203	5
3644	Orlândia	34302	5
3645	Osasco	34401	5
3646	Oscar Bressane	34500	5
3647	Osvaldo Cruz	34609	5
3648	Ourinhos	34708	5
3649	Ouroeste	34757	5
3650	Ouro Verde	34807	5
3651	Pacaembu	34906	5
3652	Palestina	35002	5
3653	Palmares Paulista	35101	5
3654	Palmeira d'Oeste	35200	5
3655	Palmital	35309	5
3656	Panorama	35408	5
3657	Paraguaçu Paulista	35507	5
3658	Paraibuna	35606	5
3659	Paraíso	35705	5
3660	Paranapanema	35804	5
3661	Paranapuã	35903	5
3662	Parapuã	36000	5
3663	Pardinho	36109	5
3664	Pariquera-Açu	36208	5
3665	Parisi	36257	5
3666	Patrocínio Paulista	36307	5
3667	Paulicéia	36406	5
3668	Paulínia	36505	5
3669	Paulistânia	36570	5
3670	Paulo de Faria	36604	5
3671	Pederneiras	36703	5
3672	Pedra Bela	36802	5
3673	Pedranópolis	36901	5
3674	Pedregulho	37008	5
3675	Pedreira	37107	5
3676	Pedrinhas Paulista	37156	5
3677	Pedro de Toledo	37206	5
3678	Penápolis	37305	5
3679	Pereira Barreto	37404	5
3680	Pereiras	37503	5
3681	Peruíbe	37602	5
3682	Piacatu	37701	5
3683	Piedade	37800	5
3684	Pilar do Sul	37909	5
3685	Pindamonhangaba	38006	5
3686	Pindorama	38105	5
3687	Pinhalzinho	38204	5
3688	Piquerobi	38303	5
3689	Piquete	38501	5
3690	Piracaia	38600	5
3691	Piracicaba	38709	5
3692	Piraju	38808	5
3693	Pirajuí	38907	5
3694	Pirangi	39004	5
3695	Pirapora do Bom Jesus	39103	5
3696	Pirapozinho	39202	5
3697	Pirassununga	39301	5
3698	Piratininga	39400	5
3699	Pitangueiras	39509	5
3700	Planalto	39608	5
3701	Platina	39707	5
3702	Poá	39806	5
3703	Poloni	39905	5
3704	Pompéia	40002	5
3705	Pongaí	40101	5
3706	Pontal	40200	5
3707	Pontalinda	40259	5
3708	Pontes Gestal	40309	5
3709	Populina	40408	5
3710	Porangaba	40507	5
3711	Porto Feliz	40606	5
3712	Porto Ferreira	40705	5
3713	Potim	40754	5
3714	Potirendaba	40804	5
3715	Pracinha	40853	5
3716	Pradópolis	40903	5
3717	Praia Grande	41000	5
3718	Pratânia	41059	5
3719	Presidente Alves	41109	5
3720	Presidente Bernardes	41208	5
3721	Presidente Epitácio	41307	5
3722	Presidente Prudente	41406	5
3723	Presidente Venceslau	41505	5
3724	Promissão	41604	5
3725	Quadra	41653	5
3726	Quatá	41703	5
3727	Queiroz	41802	5
3728	Queluz	41901	5
3729	Quintana	42008	5
3730	Rafard	42107	5
3731	Rancharia	42206	5
3732	Redenção da Serra	42305	5
3733	Regente Feijó	42404	5
3734	Reginópolis	42503	5
3735	Registro	42602	5
3736	Restinga	42701	5
3737	Ribeira	42800	5
3738	Ribeirão Bonito	42909	5
3739	Ribeirão Branco	43006	5
3740	Ribeirão Corrente	43105	5
3741	Ribeirão do Sul	43204	5
3742	Ribeirão dos Índios	43238	5
3743	Ribeirão Grande	43253	5
3744	Ribeirão Pires	43303	5
3745	Ribeirão Preto	43402	5
3746	Riversul	43501	5
3747	Rifaina	43600	5
3748	Rincão	43709	5
3749	Rinópolis	43808	5
3750	Rio Claro	43907	5
3751	Rio das Pedras	44004	5
3752	Rio Grande da Serra	44103	5
3753	Riolândia	44202	5
3754	Rosana	44251	5
3755	Roseira	44301	5
3756	Rubiácea	44400	5
3757	Rubinéia	44509	5
3758	Sabino	44608	5
3759	Sagres	44707	5
3760	Sales	44806	5
3761	Sales Oliveira	44905	5
3762	Salesópolis	45001	5
3763	Salmourão	45100	5
3764	Saltinho	45159	5
3765	Salto	45209	5
3766	Salto de Pirapora	45308	5
3767	Salto Grande	45407	5
3768	Sandovalina	45506	5
3769	Santa Adélia	45605	5
3770	Santa Albertina	45704	5
3771	Santa Bárbara d'Oeste	45803	5
3772	Santa Branca	46009	5
3773	Santa Clara d'Oeste	46108	5
3774	Santa Cruz da Conceição	46207	5
3775	Santa Cruz da Esperança	46256	5
3776	Santa Cruz das Palmeiras	46306	5
3777	Santa Cruz do Rio Pardo	46405	5
3778	Santa Ernestina	46504	5
3779	Santa Fé do Sul	46603	5
3780	Santa Gertrudes	46702	5
3781	Santa Isabel	46801	5
3782	Santa Lúcia	46900	5
3783	Santa Maria da Serra	47007	5
3784	Santa Mercedes	47106	5
3785	Santana da Ponte Pensa	47205	5
3786	Santana de Parnaíba	47304	5
3787	Santa Rita d'Oeste	47403	5
3788	Santa Rita do Passa Quatro	47502	5
3789	Santa Rosa de Viterbo	47601	5
3790	Santa Salete	47650	5
3791	Santo Anastácio	47700	5
3792	Santo André	47809	5
3793	Santo Antônio da Alegria	47908	5
3794	Santo Antônio de Posse	48005	5
3795	Santo Antônio do Aracanguá	48054	5
3796	Santo Antônio do Jardim	48104	5
3797	Santo Antônio do Pinhal	48203	5
3798	Santo Expedito	48302	5
3799	Santópolis do Aguapeí	48401	5
3800	Santos	48500	5
3801	São Bento do Sapucaí	48609	5
3802	São Bernardo do Campo	48708	5
3803	São Caetano do Sul	48807	5
3804	São Carlos	48906	5
3805	São Francisco	49003	5
3806	São João da Boa Vista	49102	5
3807	São João das Duas Pontes	49201	5
3808	São João de Iracema	49250	5
3809	São João do Pau d'Alho	49300	5
3810	São Joaquim da Barra	49409	5
3811	São José da Bela Vista	49508	5
3812	São José do Barreiro	49607	5
3813	São José do Rio Pardo	49706	5
3814	São José do Rio Preto	49805	5
3815	São José dos Campos	49904	5
3816	São Lourenço da Serra	49953	5
3817	São Luís do Paraitinga	50001	5
3818	São Manuel	50100	5
3819	São Miguel Arcanjo	50209	5
3820	São Paulo	50308	5
3821	São Pedro	50407	5
3822	São Pedro do Turvo	50506	5
3823	São Roque	50605	5
3824	São Sebastião	50704	5
3825	São Sebastião da Grama	50803	5
3826	São Simão	50902	5
3827	São Vicente	51009	5
3828	Sarapuí	51108	5
3829	Sarutaiá	51207	5
3830	Sebastianópolis do Sul	51306	5
3831	Serra Azul	51405	5
3832	Serrana	51504	5
3833	Serra Negra	51603	5
3834	Sertãozinho	51702	5
3835	Sete Barras	51801	5
3836	Severínia	51900	5
3837	Silveiras	52007	5
3838	Socorro	52106	5
3839	Sorocaba	52205	5
3840	Sud Mennucci	52304	5
3841	Sumaré	52403	5
3842	Suzano	52502	5
3843	Suzanápolis	52551	5
3844	Tabapuã	52601	5
3845	Tabatinga	52700	5
3846	Taboão da Serra	52809	5
3847	Taciba	52908	5
3848	Taguaí	53005	5
3849	Taiaçu	53104	5
3850	Taiúva	53203	5
3851	Tambaú	53302	5
3852	Tanabi	53401	5
3853	Tapiraí	53500	5
3854	Tapiratiba	53609	5
3855	Taquaral	53658	5
3856	Taquaritinga	53708	5
3857	Taquarituba	53807	5
3858	Taquarivaí	53856	5
3859	Tarabai	53906	5
3860	Tarumã	53955	5
3861	Tatuí	54003	5
3862	Taubaté	54102	5
3863	Tejupá	54201	5
3864	Teodoro Sampaio	54300	5
3865	Terra Roxa	54409	5
3866	Tietê	54508	5
3867	Timburi	54607	5
3868	Torre de Pedra	54656	5
3869	Torrinha	54706	5
3870	Trabiju	54755	5
3871	Tremembé	54805	5
3872	Três Fronteiras	54904	5
3873	Tuiuti	54953	5
3874	Tupã	55000	5
3875	Tupi Paulista	55109	5
3876	Turiúba	55208	5
3877	Turmalina	55307	5
3878	Ubarana	55356	5
3879	Ubatuba	55406	5
3880	Ubirajara	55505	5
3881	Uchoa	55604	5
3882	União Paulista	55703	5
3883	Urânia	55802	5
3884	Uru	55901	5
3885	Urupês	56008	5
3886	Valentim Gentil	56107	5
3887	Valinhos	56206	5
3888	Valparaíso	56305	5
3889	Vargem	56354	5
3890	Vargem Grande do Sul	56404	5
3891	Vargem Grande Paulista	56453	5
3892	Várzea Paulista	56503	5
3893	Vera Cruz	56602	5
3894	Vinhedo	56701	5
3895	Viradouro	56800	5
3896	Vista Alegre do Alto	56909	5
3897	Vitória Brasil	56958	5
3898	Votorantim	57006	5
3899	Votuporanga	57105	5
3900	Zacarias	57154	5
3901	Chavantes	57204	5
3902	Estiva Gerbi	57303	5
3903	Abatiá	00103	22
3904	Adrianópolis	00202	22
3905	Agudos do Sul	00301	22
3906	Almirante Tamandaré	00400	22
3907	Altamira do Paraná	00459	22
3908	Altônia	00509	22
3909	Alto Paraná	00608	22
3910	Alto Piquiri	00707	22
3911	Alvorada do Sul	00806	22
3912	Amaporã	00905	22
3913	Ampére	01002	22
3914	Anahy	01051	22
3915	Andirá	01101	22
3916	Ângulo	01150	22
3917	Antonina	01200	22
3918	Antônio Olinto	01309	22
3919	Apucarana	01408	22
3920	Arapongas	01507	22
3921	Arapoti	01606	22
3922	Arapuã	01655	22
3923	Araruna	01705	22
3924	Araucária	01804	22
3925	Ariranha do Ivaí	01853	22
3926	Assaí	01903	22
3927	Assis Chateaubriand	02000	22
3928	Astorga	02109	22
3929	Atalaia	02208	22
3930	Balsa Nova	02307	22
3931	Bandeirantes	02406	22
3932	Barbosa Ferraz	02505	22
3933	Barracão	02604	22
3934	Barra do Jacaré	02703	22
3935	Bela Vista da Caroba	02752	22
3936	Bela Vista do Paraíso	02802	22
3937	Bituruna	02901	22
3938	Boa Esperança	03008	22
3939	Boa Esperança do Iguaçu	03024	22
3940	Boa Ventura de São Roque	03040	22
3941	Boa Vista da Aparecida	03057	22
3942	Bocaiúva do Sul	03107	22
3943	Bom Jesus do Sul	03156	22
3944	Bom Sucesso	03206	22
3945	Bom Sucesso do Sul	03222	22
3946	Borrazópolis	03305	22
3947	Braganey	03354	22
3948	Brasilândia do Sul	03370	22
3949	Cafeara	03404	22
3950	Cafelândia	03453	22
3951	Cafezal do Sul	03479	22
3952	Califórnia	03503	22
3953	Cambará	03602	22
3954	Cambé	03701	22
3955	Cambira	03800	22
3956	Campina da Lagoa	03909	22
3957	Campina do Simão	03958	22
3958	Campina Grande do Sul	04006	22
3959	Campo Bonito	04055	22
3960	Campo do Tenente	04105	22
3961	Campo Largo	04204	22
3962	Campo Magro	04253	22
3963	Campo Mourão	04303	22
3964	Cândido de Abreu	04402	22
3965	Candói	04428	22
3966	Cantagalo	04451	22
3967	Capanema	04501	22
3968	Capitão Leônidas Marques	04600	22
3969	Carambeí	04659	22
3970	Carlópolis	04709	22
3971	Cascavel	04808	22
3972	Castro	04907	22
3973	Catanduvas	05003	22
3974	Centenário do Sul	05102	22
3975	Cerro Azul	05201	22
3976	Céu Azul	05300	22
3977	Chopinzinho	05409	22
3978	Cianorte	05508	22
3979	Cidade Gaúcha	05607	22
3980	Clevelândia	05706	22
3981	Colombo	05805	22
3982	Colorado	05904	22
3983	Congonhinhas	06001	22
3984	Conselheiro Mairinck	06100	22
3985	Contenda	06209	22
3986	Corbélia	06308	22
3987	Cornélio Procópio	06407	22
3988	Coronel Domingos Soares	06456	22
3989	Coronel Vivida	06506	22
3990	Corumbataí do Sul	06555	22
3991	Cruzeiro do Iguaçu	06571	22
3992	Cruzeiro do Oeste	06605	22
3993	Cruzeiro do Sul	06704	22
3994	Cruz Machado	06803	22
3995	Cruzmaltina	06852	22
3996	Curitiba	06902	22
3997	Curiúva	07009	22
3998	Diamante do Norte	07108	22
3999	Diamante do Sul	07124	22
4000	Diamante D'Oeste	07157	22
4001	Dois Vizinhos	07207	22
4002	Douradina	07256	22
4003	Doutor Camargo	07306	22
4004	Enéas Marques	07405	22
4005	Engenheiro Beltrão	07504	22
4006	Esperança Nova	07520	22
4007	Entre Rios do Oeste	07538	22
4008	Espigão Alto do Iguaçu	07546	22
4009	Farol	07553	22
4010	Faxinal	07603	22
4011	Fazenda Rio Grande	07652	22
4012	Fênix	07702	22
4013	Fernandes Pinheiro	07736	22
4014	Figueira	07751	22
4015	Floraí	07801	22
4016	Flor da Serra do Sul	07850	22
4017	Floresta	07900	22
4018	Florestópolis	08007	22
4019	Flórida	08106	22
4020	Formosa do Oeste	08205	22
4021	Foz do Iguaçu	08304	22
4022	Francisco Alves	08320	22
4023	Francisco Beltrão	08403	22
4024	Foz do Jordão	08452	22
4025	General Carneiro	08502	22
4026	Godoy Moreira	08551	22
4027	Goioerê	08601	22
4028	Goioxim	08650	22
4029	Grandes Rios	08700	22
4030	Guaíra	08809	22
4031	Guairaçá	08908	22
4032	Guamiranga	08957	22
4033	Guapirama	09005	22
4034	Guaporema	09104	22
4035	Guaraci	09203	22
4036	Guaraniaçu	09302	22
4037	Guarapuava	09401	22
4038	Guaraqueçaba	09500	22
4039	Guaratuba	09609	22
4040	Honório Serpa	09658	22
4041	Ibaiti	09708	22
4042	Ibema	09757	22
4043	Ibiporã	09807	22
4044	Icaraíma	09906	22
4045	Iguaraçu	10003	22
4046	Iguatu	10052	22
4047	Imbaú	10078	22
4048	Imbituva	10102	22
4049	Inácio Martins	10201	22
4050	Inajá	10300	22
4051	Indianópolis	10409	22
4052	Ipiranga	10508	22
4053	Iporã	10607	22
4054	Iracema do Oeste	10656	22
4055	Irati	10706	22
4056	Iretama	10805	22
4057	Itaguajé	10904	22
4058	Itaipulândia	10953	22
4059	Itambaracá	11001	22
4060	Itambé	11100	22
4061	Itapejara d'Oeste	11209	22
4062	Itaperuçu	11258	22
4063	Itaúna do Sul	11308	22
4064	Ivaí	11407	22
4065	Ivaiporã	11506	22
4066	Ivaté	11555	22
4067	Ivatuba	11605	22
4068	Jaboti	11704	22
4069	Jacarezinho	11803	22
4070	Jaguapitã	11902	22
4071	Jaguariaíva	12009	22
4072	Jandaia do Sul	12108	22
4073	Janiópolis	12207	22
4074	Japira	12306	22
4075	Japurá	12405	22
4076	Jardim Alegre	12504	22
4077	Jardim Olinda	12603	22
4078	Jataizinho	12702	22
4079	Jesuítas	12751	22
4080	Joaquim Távora	12801	22
4081	Jundiaí do Sul	12900	22
4082	Juranda	12959	22
4083	Jussara	13007	22
4084	Kaloré	13106	22
4085	Lapa	13205	22
4086	Laranjal	13254	22
4087	Laranjeiras do Sul	13304	22
4088	Leópolis	13403	22
4089	Lidianópolis	13429	22
4090	Lindoeste	13452	22
4091	Loanda	13502	22
4092	Lobato	13601	22
4093	Londrina	13700	22
4094	Luiziana	13734	22
4095	Lunardelli	13759	22
4096	Lupionópolis	13809	22
4097	Mallet	13908	22
4098	Mamborê	14005	22
4099	Mandaguaçu	14104	22
4100	Mandaguari	14203	22
4101	Mandirituba	14302	22
4102	Manfrinópolis	14351	22
4103	Mangueirinha	14401	22
4104	Manoel Ribas	14500	22
4105	Marechal Cândido Rondon	14609	22
4106	Maria Helena	14708	22
4107	Marialva	14807	22
4108	Marilândia do Sul	14906	22
4109	Marilena	15002	22
4110	Mariluz	15101	22
4111	Maringá	15200	22
4112	Mariópolis	15309	22
4113	Maripá	15358	22
4114	Marmeleiro	15408	22
4115	Marquinho	15457	22
4116	Marumbi	15507	22
4117	Matelândia	15606	22
4118	Matinhos	15705	22
4119	Mato Rico	15739	22
4120	Mauá da Serra	15754	22
4121	Medianeira	15804	22
4122	Mercedes	15853	22
4123	Mirador	15903	22
4124	Miraselva	16000	22
4125	Missal	16059	22
4126	Moreira Sales	16109	22
4127	Morretes	16208	22
4128	Munhoz de Melo	16307	22
4129	Nossa Senhora das Graças	16406	22
4130	Nova Aliança do Ivaí	16505	22
4131	Nova América da Colina	16604	22
4132	Nova Aurora	16703	22
4133	Nova Cantu	16802	22
4134	Nova Esperança	16901	22
4135	Nova Esperança do Sudoeste	16950	22
4136	Nova Fátima	17008	22
4137	Nova Laranjeiras	17057	22
4138	Nova Londrina	17107	22
4139	Nova Olímpia	17206	22
4140	Nova Santa Bárbara	17214	22
4141	Nova Santa Rosa	17222	22
4142	Nova Prata do Iguaçu	17255	22
4143	Nova Tebas	17271	22
4144	Novo Itacolomi	17297	22
4145	Ortigueira	17305	22
4146	Ourizona	17404	22
4147	Ouro Verde do Oeste	17453	22
4148	Paiçandu	17503	22
4149	Palmas	17602	22
4150	Palmeira	17701	22
4151	Palmital	17800	22
4152	Palotina	17909	22
4153	Paraíso do Norte	18006	22
4154	Paranacity	18105	22
4155	Paranaguá	18204	22
4156	Paranapoema	18303	22
4157	Paranavaí	18402	22
4158	Pato Bragado	18451	22
4159	Pato Branco	18501	22
4160	Paula Freitas	18600	22
4161	Paulo Frontin	18709	22
4162	Peabiru	18808	22
4163	Perobal	18857	22
4164	Pérola	18907	22
4165	Pérola d'Oeste	19004	22
4166	Piên	19103	22
4167	Pinhais	19152	22
4168	Pinhalão	19202	22
4169	Pinhal de São Bento	19251	22
4170	Pinhão	19301	22
4171	Piraí do Sul	19400	22
4172	Piraquara	19509	22
4173	Pitanga	19608	22
4174	Pitangueiras	19657	22
4175	Planaltina do Paraná	19707	22
4176	Planalto	19806	22
4177	Ponta Grossa	19905	22
4178	Pontal do Paraná	19954	22
4179	Porecatu	20002	22
4180	Porto Amazonas	20101	22
4181	Porto Barreiro	20150	22
4182	Porto Rico	20200	22
4183	Porto Vitória	20309	22
4184	Prado Ferreira	20333	22
4185	Pranchita	20358	22
4186	Presidente Castelo Branco	20408	22
4187	Primeiro de Maio	20507	22
4188	Prudentópolis	20606	22
4189	Quarto Centenário	20655	22
4190	Quatiguá	20705	22
4191	Quatro Barras	20804	22
4192	Quatro Pontes	20853	22
4193	Quedas do Iguaçu	20903	22
4194	Querência do Norte	21000	22
4195	Quinta do Sol	21109	22
4196	Quitandinha	21208	22
4197	Ramilândia	21257	22
4198	Rancho Alegre	21307	22
4199	Rancho Alegre D'Oeste	21356	22
4200	Realeza	21406	22
4201	Rebouças	21505	22
4202	Renascença	21604	22
4203	Reserva	21703	22
4204	Reserva do Iguaçu	21752	22
4205	Ribeirão Claro	21802	22
4206	Ribeirão do Pinhal	21901	22
4207	Rio Azul	22008	22
4208	Rio Bom	22107	22
4209	Rio Bonito do Iguaçu	22156	22
4210	Rio Branco do Ivaí	22172	22
4211	Rio Branco do Sul	22206	22
4212	Rio Negro	22305	22
4213	Rolândia	22404	22
4214	Roncador	22503	22
4215	Rondon	22602	22
4216	Rosário do Ivaí	22651	22
4217	Sabáudia	22701	22
4218	Salgado Filho	22800	22
4219	Salto do Itararé	22909	22
4220	Salto do Lontra	23006	22
4221	Santa Amélia	23105	22
4222	Santa Cecília do Pavão	23204	22
4223	Santa Cruz de Monte Castelo	23303	22
4224	Santa Fé	23402	22
4225	Santa Helena	23501	22
4226	Santa Inês	23600	22
4227	Santa Isabel do Ivaí	23709	22
4228	Santa Izabel do Oeste	23808	22
4229	Santa Lúcia	23824	22
4230	Santa Maria do Oeste	23857	22
4231	Santa Mariana	23907	22
4232	Santa Mônica	23956	22
4233	Santana do Itararé	24004	22
4234	Santa Tereza do Oeste	24020	22
4235	Santa Terezinha de Itaipu	24053	22
4236	Santo Antônio da Platina	24103	22
4237	Santo Antônio do Caiuá	24202	22
4238	Santo Antônio do Paraíso	24301	22
4239	Santo Antônio do Sudoeste	24400	22
4240	Santo Inácio	24509	22
4241	São Carlos do Ivaí	24608	22
4242	São Jerônimo da Serra	24707	22
4243	São João	24806	22
4244	São João do Caiuá	24905	22
4245	São João do Ivaí	25001	22
4246	São João do Triunfo	25100	22
4247	São Jorge d'Oeste	25209	22
4248	São Jorge do Ivaí	25308	22
4249	São Jorge do Patrocínio	25357	22
4250	São José da Boa Vista	25407	22
4251	São José das Palmeiras	25456	22
4252	São José dos Pinhais	25506	22
4253	São Manoel do Paraná	25555	22
4254	São Mateus do Sul	25605	22
4255	São Miguel do Iguaçu	25704	22
4256	São Pedro do Iguaçu	25753	22
4257	São Pedro do Ivaí	25803	22
4258	São Pedro do Paraná	25902	22
4259	São Sebastião da Amoreira	26009	22
4260	São Tomé	26108	22
4261	Sapopema	26207	22
4262	Sarandi	26256	22
4263	Saudade do Iguaçu	26272	22
4264	Sengés	26306	22
4265	Serranópolis do Iguaçu	26355	22
4266	Sertaneja	26405	22
4267	Sertanópolis	26504	22
4268	Siqueira Campos	26603	22
4269	Sulina	26652	22
4270	Tamarana	26678	22
4271	Tamboara	26702	22
4272	Tapejara	26801	22
4273	Tapira	26900	22
4274	Teixeira Soares	27007	22
4275	Telêmaco Borba	27106	22
4276	Terra Boa	27205	22
4277	Terra Rica	27304	22
4278	Terra Roxa	27403	22
4279	Tibagi	27502	22
4280	Tijucas do Sul	27601	22
4281	Toledo	27700	22
4282	Tomazina	27809	22
4283	Três Barras do Paraná	27858	22
4284	Tunas do Paraná	27882	22
4285	Tuneiras do Oeste	27908	22
4286	Tupãssi	27957	22
4287	Turvo	27965	22
4288	Ubiratã	28005	22
4289	Umuarama	28104	22
4290	União da Vitória	28203	22
4291	Uniflor	28302	22
4292	Uraí	28401	22
4293	Wenceslau Braz	28500	22
4294	Ventania	28534	22
4295	Vera Cruz do Oeste	28559	22
4296	Verê	28609	22
4297	Vila Alta	28625	22
4298	Doutor Ulysses	28633	22
4299	Virmond	28658	22
4300	Vitorino	28708	22
4301	Xambrê	28807	22
4302	Abdon Batista	00051	25
4303	Abelardo Luz	00101	25
4304	Agrolândia	00200	25
4305	Agronômica	00309	25
4306	Água Doce	00408	25
4307	Águas de Chapecó	00507	25
4308	Águas Frias	00556	25
4309	Águas Mornas	00606	25
4310	Alfredo Wagner	00705	25
4311	Alto Bela Vista	00754	25
4312	Anchieta	00804	25
4313	Angelina	00903	25
4314	Anita Garibaldi	01000	25
4315	Anitápolis	01109	25
4316	Antônio Carlos	01208	25
4317	Apiúna	01257	25
4318	Arabutã	01273	25
4319	Araquari	01307	25
4320	Araranguá	01406	25
4321	Armazém	01505	25
4322	Arroio Trinta	01604	25
4323	Arvoredo	01653	25
4324	Ascurra	01703	25
4325	Atalanta	01802	25
4326	Aurora	01901	25
4327	Balneário Arroio do Silva	01950	25
4328	Balneário Camboriú	02008	25
4329	Balneário Barra do Sul	02057	25
4330	Balneário Gaivota	02073	25
4331	Bandeirante	02081	25
4332	Barra Bonita	02099	25
4333	Barra Velha	02107	25
4334	Bela Vista do Toldo	02131	25
4335	Belmonte	02156	25
4336	Benedito Novo	02206	25
4337	Biguaçu	02305	25
4338	Blumenau	02404	25
4339	Bocaina do Sul	02438	25
4340	Bombinhas	02453	25
4341	Bom Jardim da Serra	02503	25
4342	Bom Jesus	02537	25
4343	Bom Jesus do Oeste	02578	25
4344	Bom Retiro	02602	25
4345	Botuverá	02701	25
4346	Braço do Norte	02800	25
4347	Braço do Trombudo	02859	25
4348	Brunópolis	02875	25
4349	Brusque	02909	25
4350	Caçador	03006	25
4351	Caibi	03105	25
4352	Calmon	03154	25
4353	Camboriú	03204	25
4354	Capão Alto	03253	25
4355	Campo Alegre	03303	25
4356	Campo Belo do Sul	03402	25
4357	Campo Erê	03501	25
4358	Campos Novos	03600	25
4359	Canelinha	03709	25
4360	Canoinhas	03808	25
4361	Capinzal	03907	25
4362	Capivari de Baixo	03956	25
4363	Catanduvas	04004	25
4364	Caxambu do Sul	04103	25
4365	Celso Ramos	04152	25
4366	Cerro Negro	04178	25
4367	Chapadão do Lageado	04194	25
4368	Chapecó	04202	25
4369	Cocal do Sul	04251	25
4370	Concórdia	04301	25
4371	Cordilheira Alta	04350	25
4372	Coronel Freitas	04400	25
4373	Coronel Martins	04459	25
4374	Corupá	04509	25
4375	Correia Pinto	04558	25
4376	Criciúma	04608	25
4377	Cunha Porã	04707	25
4378	Cunhataí	04756	25
4379	Curitibanos	04806	25
4380	Descanso	04905	25
4381	Dionísio Cerqueira	05001	25
4382	Dona Emma	05100	25
4383	Doutor Pedrinho	05159	25
4384	Entre Rios	05175	25
4385	Ermo	05191	25
4386	Erval Velho	05209	25
4387	Faxinal dos Guedes	05308	25
4388	Flor do Sertão	05357	25
4389	Florianópolis	05407	25
4390	Formosa do Sul	05431	25
4391	Forquilhinha	05456	25
4392	Fraiburgo	05506	25
4393	Frei Rogério	05555	25
4394	Galvão	05605	25
4395	Garopaba	05704	25
4396	Garuva	05803	25
4397	Gaspar	05902	25
4398	Governador Celso Ramos	06009	25
4399	Grão Pará	06108	25
4400	Gravatal	06207	25
4401	Guabiruba	06306	25
4402	Guaraciaba	06405	25
4403	Guaramirim	06504	25
4404	Guarujá do Sul	06603	25
4405	Guatambú	06652	25
4406	Herval d'Oeste	06702	25
4407	Ibiam	06751	25
4408	Ibicaré	06801	25
4409	Ibirama	06900	25
4410	Içara	07007	25
4411	Ilhota	07106	25
4412	Imaruí	07205	25
4413	Imbituba	07304	25
4414	Imbuia	07403	25
4415	Indaial	07502	25
4416	Iomerê	07577	25
4417	Ipira	07601	25
4418	Iporã do Oeste	07650	25
4419	Ipuaçu	07684	25
4420	Ipumirim	07700	25
4421	Iraceminha	07759	25
4422	Irani	07809	25
4423	Irati	07858	25
4424	Irineópolis	07908	25
4425	Itá	08005	25
4426	Itaiópolis	08104	25
4427	Itajaí	08203	25
4428	Itapema	08302	25
4429	Itapiranga	08401	25
4430	Itapoá	08450	25
4431	Ituporanga	08500	25
4432	Jaborá	08609	25
4433	Jacinto Machado	08708	25
4434	Jaguaruna	08807	25
4435	Jaraguá do Sul	08906	25
4436	Jardinópolis	08955	25
4437	Joaçaba	09003	25
4438	Joinville	09102	25
4439	José Boiteux	09151	25
4440	Jupiá	09177	25
4441	Lacerdópolis	09201	25
4442	Lages	09300	25
4443	Laguna	09409	25
4444	Lajeado Grande	09458	25
4445	Laurentino	09508	25
4446	Lauro Muller	09607	25
4447	Lebon Régis	09706	25
4448	Leoberto Leal	09805	25
4449	Lindóia do Sul	09854	25
4450	Lontras	09904	25
4451	Luiz Alves	10001	25
4452	Luzerna	10035	25
4453	Macieira	10050	25
4454	Mafra	10100	25
4455	Major Gercino	10209	25
4456	Major Vieira	10308	25
4457	Maracajá	10407	25
4458	Maravilha	10506	25
4459	Marema	10555	25
4460	Massaranduba	10605	25
4461	Matos Costa	10704	25
4462	Meleiro	10803	25
4463	Mirim Doce	10852	25
4464	Modelo	10902	25
4465	Mondaí	11009	25
4466	Monte Carlo	11058	25
4467	Monte Castelo	11108	25
4468	Morro da Fumaça	11207	25
4469	Morro Grande	11256	25
4470	Navegantes	11306	25
4471	Nova Erechim	11405	25
4472	Nova Itaberaba	11454	25
4473	Nova Trento	11504	25
4474	Nova Veneza	11603	25
4475	Novo Horizonte	11652	25
4476	Orleans	11702	25
4477	Otacílio Costa	11751	25
4478	Ouro	11801	25
4479	Ouro Verde	11850	25
4480	Paial	11876	25
4481	Painel	11892	25
4482	Palhoça	11900	25
4483	Palma Sola	12007	25
4484	Palmeira	12056	25
4485	Palmitos	12106	25
4486	Papanduva	12205	25
4487	Paraíso	12239	25
4488	Passo de Torres	12254	25
4489	Passos Maia	12270	25
4490	Paulo Lopes	12304	25
4491	Pedras Grandes	12403	25
4492	Penha	12502	25
4493	Peritiba	12601	25
4494	Petrolândia	12700	25
4495	Piçarras	12809	25
4496	Pinhalzinho	12908	25
4497	Pinheiro Preto	13005	25
4498	Piratuba	13104	25
4499	Planalto Alegre	13153	25
4500	Pomerode	13203	25
4501	Ponte Alta	13302	25
4502	Ponte Alta do Norte	13351	25
4503	Ponte Serrada	13401	25
4504	Porto Belo	13500	25
4505	Porto União	13609	25
4506	Pouso Redondo	13708	25
4507	Praia Grande	13807	25
4508	Presidente Castelo Branco	13906	25
4509	Presidente Getúlio	14003	25
4510	Presidente Nereu	14102	25
4511	Princesa	14151	25
4512	Quilombo	14201	25
4513	Rancho Queimado	14300	25
4514	Rio das Antas	14409	25
4515	Rio do Campo	14508	25
4516	Rio do Oeste	14607	25
4517	Rio dos Cedros	14706	25
4518	Rio do Sul	14805	25
4519	Rio Fortuna	14904	25
4520	Rio Negrinho	15000	25
4521	Rio Rufino	15059	25
4522	Riqueza	15075	25
4523	Rodeio	15109	25
4524	Romelândia	15208	25
4525	Salete	15307	25
4526	Saltinho	15356	25
4527	Salto Veloso	15406	25
4528	Sangão	15455	25
4529	Santa Cecília	15505	25
4530	Santa Helena	15554	25
4531	Santa Rosa de Lima	15604	25
4532	Santa Rosa do Sul	15653	25
4533	Santa Terezinha	15679	25
4534	Santa Terezinha do Progresso	15687	25
4535	Santiago do Sul	15695	25
4536	Santo Amaro da Imperatriz	15703	25
4537	São Bernardino	15752	25
4538	São Bento do Sul	15802	25
4539	São Bonifácio	15901	25
4540	São Carlos	16008	25
4541	São Cristovão do Sul	16057	25
4542	São Domingos	16107	25
4543	São Francisco do Sul	16206	25
4544	São João do Oeste	16255	25
4545	São João Batista	16305	25
4546	São João do Itaperiú	16354	25
4547	São João do Sul	16404	25
4548	São Joaquim	16503	25
4549	São José	16602	25
4550	São José do Cedro	16701	25
4551	São José do Cerrito	16800	25
4552	São Lourenço do Oeste	16909	25
4553	São Ludgero	17006	25
4554	São Martinho	17105	25
4555	São Miguel da Boa Vista	17154	25
4556	São Miguel do Oeste	17204	25
4557	São Pedro de Alcântara	17253	25
4558	Saudades	17303	25
4559	Schroeder	17402	25
4560	Seara	17501	25
4561	Serra Alta	17550	25
4562	Siderópolis	17600	25
4563	Sombrio	17709	25
4564	Sul Brasil	17758	25
4565	Taió	17808	25
4566	Tangará	17907	25
4567	Tigrinhos	17956	25
4568	Tijucas	18004	25
4569	Timbé do Sul	18103	25
4570	Timbó	18202	25
4571	Timbó Grande	18251	25
4572	Três Barras	18301	25
4573	Treviso	18350	25
4574	Treze de Maio	18400	25
4575	Treze Tílias	18509	25
4576	Trombudo Central	18608	25
4577	Tubarão	18707	25
4578	Tunápolis	18756	25
4579	Turvo	18806	25
4580	União do Oeste	18855	25
4581	Urubici	18905	25
4582	Urupema	18954	25
4583	Urussanga	19002	25
4584	Vargeão	19101	25
4585	Vargem	19150	25
4586	Vargem Bonita	19176	25
4587	Vidal Ramos	19200	25
4588	Videira	19309	25
4589	Vitor Meireles	19358	25
4590	Witmarsum	19408	25
4591	Xanxerê	19507	25
4592	Xavantina	19606	25
4593	Xaxim	19705	25
4594	Zortéa	19853	25
4595	Água Santa	00059	6
4596	Agudo	00109	6
4597	Ajuricaba	00208	6
4598	Alecrim	00307	6
4599	Alegrete	00406	6
4600	Alegria	00455	6
4601	Alpestre	00505	6
4602	Alto Alegre	00554	6
4603	Alto Feliz	00570	6
4604	Alvorada	00604	6
4605	Amaral Ferrador	00638	6
4606	Ametista do Sul	00646	6
4607	André da Rocha	00661	6
4608	Anta Gorda	00703	6
4609	Antônio Prado	00802	6
4610	Arambaré	00851	6
4611	Araricá	00877	6
4612	Aratiba	00901	6
4613	Arroio do Meio	01008	6
4614	Arroio do Sal	01057	6
4615	Arroio dos Ratos	01107	6
4616	Arroio do Tigre	01206	6
4617	Arroio Grande	01305	6
4618	Arvorezinha	01404	6
4619	Augusto Pestana	01503	6
4620	Áurea	01552	6
4621	Bagé	01602	6
4622	Balneário Pinhal	01636	6
4623	Barão	01651	6
4624	Barão de Cotegipe	01701	6
4625	Barão do Triunfo	01750	6
4626	Barracão	01800	6
4627	Barra do Guarita	01859	6
4628	Barra do Quaraí	01875	6
4629	Barra do Ribeiro	01909	6
4630	Barra do Rio Azul	01925	6
4631	Barra Funda	01958	6
4632	Barros Cassal	02006	6
4633	Benjamin Constant do Sul	02055	6
4634	Bento Gonçalves	02105	6
4635	Boa Vista das Missões	02154	6
4636	Boa Vista do Buricá	02204	6
4637	Boa Vista do Sul	02253	6
4638	Bom Jesus	02303	6
4639	Bom Princípio	02352	6
4640	Bom Progresso	02378	6
4641	Bom Retiro do Sul	02402	6
4642	Boqueirão do Leão	02451	6
4643	Bossoroca	02501	6
4644	Braga	02600	6
4645	Brochier	02659	6
4646	Butiá	02709	6
4647	Caçapava do Sul	02808	6
4648	Cacequi	02907	6
4649	Cachoeira do Sul	03004	6
4650	Cachoeirinha	03103	6
4651	Cacique Doble	03202	6
4652	Caibaté	03301	6
4653	Caiçara	03400	6
4654	Camaquã	03509	6
4655	Camargo	03558	6
4656	Cambará do Sul	03608	6
4657	Campestre da Serra	03673	6
4658	Campina das Missões	03707	6
4659	Campinas do Sul	03806	6
4660	Campo Bom	03905	6
4661	Campo Novo	04002	6
4662	Campos Borges	04101	6
4663	Candelária	04200	6
4664	Cândido Godói	04309	6
4665	Candiota	04358	6
4666	Canela	04408	6
4667	Canguçu	04507	6
4668	Canoas	04606	6
4669	Capão da Canoa	04630	6
4670	Capão do Leão	04663	6
4671	Capivari do Sul	04671	6
4672	Capela de Santana	04689	6
4673	Capitão	04697	6
4674	Carazinho	04705	6
4675	Caraá	04713	6
4676	Carlos Barbosa	04804	6
4677	Carlos Gomes	04853	6
4678	Casca	04903	6
4679	Caseiros	04952	6
4680	Catuípe	05009	6
4681	Caxias do Sul	05108	6
4682	Centenário	05116	6
4683	Cerrito	05124	6
4684	Cerro Branco	05132	6
4685	Cerro Grande	05157	6
4686	Cerro Grande do Sul	05173	6
4687	Cerro Largo	05207	6
4688	Chapada	05306	6
4689	Charqueadas	05355	6
4690	Charrua	05371	6
4691	Chiapeta	05405	6
4692	Chuí	05439	6
4693	Chuvisca	05447	6
4694	Cidreira	05454	6
4695	Ciríaco	05504	6
4696	Colinas	05587	6
4697	Colorado	05603	6
4698	Condor	05702	6
4699	Constantina	05801	6
4700	Coqueiros do Sul	05850	6
4701	Coronel Barros	05871	6
4702	Coronel Bicaco	05900	6
4703	Cotiporã	05959	6
4704	Coxilha	05975	6
4705	Crissiumal	06007	6
4706	Cristal	06056	6
4707	Cristal do Sul	06072	6
4708	Cruz Alta	06106	6
4709	Cruzeiro do Sul	06205	6
4710	David Canabarro	06304	6
4711	Derrubadas	06320	6
4712	Dezesseis de Novembro	06353	6
4713	Dilermando de Aguiar	06379	6
4714	Dois Irmãos	06403	6
4715	Dois Irmãos das Missões	06429	6
4716	Dois Lajeados	06452	6
4717	Dom Feliciano	06502	6
4718	Dom Pedro de Alcântara	06551	6
4719	Dom Pedrito	06601	6
4720	Dona Francisca	06700	6
4721	Doutor Maurício Cardoso	06734	6
4722	Doutor Ricardo	06759	6
4723	Eldorado do Sul	06767	6
4724	Encantado	06809	6
4725	Encruzilhada do Sul	06908	6
4726	Engenho Velho	06924	6
4727	Entre-Ijuís	06932	6
4728	Entre Rios do Sul	06957	6
4729	Erebango	06973	6
4730	Erechim	07005	6
4731	Ernestina	07054	6
4732	Herval	07104	6
4733	Erval Grande	07203	6
4734	Erval Seco	07302	6
4735	Esmeralda	07401	6
4736	Esperança do Sul	07450	6
4737	Espumoso	07500	6
4738	Estação	07559	6
4739	Estância Velha	07609	6
4740	Esteio	07708	6
4741	Estrela	07807	6
4742	Estrela Velha	07815	6
4743	Eugênio de Castro	07831	6
4744	Fagundes Varela	07864	6
4745	Farroupilha	07906	6
4746	Faxinal do Soturno	08003	6
4747	Faxinalzinho	08052	6
4748	Fazenda Vilanova	08078	6
4749	Feliz	08102	6
4750	Flores da Cunha	08201	6
4751	Floriano Peixoto	08250	6
4752	Fontoura Xavier	08300	6
4753	Formigueiro	08409	6
4754	Fortaleza dos Valos	08458	6
4755	Frederico Westphalen	08508	6
4756	Garibaldi	08607	6
4757	Garruchos	08656	6
4758	Gaurama	08706	6
4759	General Câmara	08805	6
4760	Gentil	08854	6
4761	Getúlio Vargas	08904	6
4762	Giruá	09001	6
4763	Glorinha	09050	6
4764	Gramado	09100	6
4765	Gramado dos Loureiros	09126	6
4766	Gramado Xavier	09159	6
4767	Gravataí	09209	6
4768	Guabiju	09258	6
4769	Guaíba	09308	6
4770	Guaporé	09407	6
4771	Guarani das Missões	09506	6
4772	Harmonia	09555	6
4773	Herveiras	09571	6
4774	Horizontina	09605	6
4775	Hulha Negra	09654	6
4776	Humaitá	09704	6
4777	Ibarama	09753	6
4778	Ibiaçá	09803	6
4779	Ibiraiaras	09902	6
4780	Ibirapuitã	09951	6
4781	Ibirubá	10009	6
4782	Igrejinha	10108	6
4783	Ijuí	10207	6
4784	Ilópolis	10306	6
4785	Imbé	10330	6
4786	Imigrante	10363	6
4787	Independência	10405	6
4788	Inhacorá	10413	6
4789	Ipê	10439	6
4790	Ipiranga do Sul	10462	6
4791	Iraí	10504	6
4792	Itaara	10538	6
4793	Itacurubi	10553	6
4794	Itapuca	10579	6
4795	Itaqui	10603	6
4796	Itatiba do Sul	10702	6
4797	Ivorá	10751	6
4798	Ivoti	10801	6
4799	Jaboticaba	10850	6
4800	Jacutinga	10900	6
4801	Jaguarão	11007	6
4802	Jaguari	11106	6
4803	Jaquirana	11122	6
4804	Jari	11130	6
4805	Jóia	11155	6
4806	Júlio de Castilhos	11205	6
4807	Lagoão	11254	6
4808	Lagoa dos Três Cantos	11270	6
4809	Lagoa Vermelha	11304	6
4810	Lajeado	11403	6
4811	Lajeado do Bugre	11429	6
4812	Lavras do Sul	11502	6
4813	Liberato Salzano	11601	6
4814	Lindolfo Collor	11627	6
4815	Linha Nova	11643	6
4816	Machadinho	11700	6
4817	Maçambara	11718	6
4818	Mampituba	11734	6
4819	Manoel Viana	11759	6
4820	Maquiné	11775	6
4821	Maratá	11791	6
4822	Marau	11809	6
4823	Marcelino Ramos	11908	6
4824	Mariana Pimentel	11981	6
4825	Mariano Moro	12005	6
4826	Marques de Souza	12054	6
4827	Mata	12104	6
4828	Mato Castelhano	12138	6
4829	Mato Leitão	12153	6
4830	Maximiliano de Almeida	12203	6
4831	Minas do Leão	12252	6
4832	Miraguaí	12302	6
4833	Montauri	12351	6
4834	Monte Alegre dos Campos	12377	6
4835	Monte Belo do Sul	12385	6
4836	Montenegro	12401	6
4837	Mormaço	12427	6
4838	Morrinhos do Sul	12443	6
4839	Morro Redondo	12450	6
4840	Morro Reuter	12476	6
4841	Mostardas	12500	6
4842	Muçum	12609	6
4843	Muitos Capões	12617	6
4844	Muliterno	12625	6
4845	Não-Me-Toque	12658	6
4846	Nicolau Vergueiro	12674	6
4847	Nonoai	12708	6
4848	Nova Alvorada	12757	6
4849	Nova Araçá	12807	6
4850	Nova Bassano	12906	6
4851	Nova Boa Vista	12955	6
4852	Nova Bréscia	13003	6
4853	Nova Candelária	13011	6
4854	Nova Esperança do Sul	13037	6
4855	Nova Hartz	13060	6
4856	Nova Pádua	13086	6
4857	Nova Palma	13102	6
4858	Nova Petrópolis	13201	6
4859	Nova Prata	13300	6
4860	Nova Ramada	13334	6
4861	Nova Roma do Sul	13359	6
4862	Nova Santa Rita	13375	6
4863	Novo Cabrais	13391	6
4864	Novo Hamburgo	13409	6
4865	Novo Machado	13425	6
4866	Novo Tiradentes	13441	6
4867	Novo Barreiro	13490	6
4868	Osório	13508	6
4869	Paim Filho	13607	6
4870	Palmares do Sul	13656	6
4871	Palmeira das Missões	13706	6
4872	Palmitinho	13805	6
4873	Panambi	13904	6
4874	Pantano Grande	13953	6
4875	Paraí	14001	6
4876	Paraíso do Sul	14027	6
4877	Pareci Novo	14035	6
4878	Parobé	14050	6
4879	Passa Sete	14068	6
4880	Passo do Sobrado	14076	6
4881	Passo Fundo	14100	6
4882	Paverama	14159	6
4883	Pedro Osório	14209	6
4884	Pejuçara	14308	6
4885	Pelotas	14407	6
4886	Picada Café	14423	6
4887	Pinhal	14456	6
4888	Pinhal Grande	14472	6
4889	Pinheirinho do Vale	14498	6
4890	Pinheiro Machado	14506	6
4891	Pirapó	14555	6
4892	Piratini	14605	6
4893	Planalto	14704	6
4894	Poço das Antas	14753	6
4895	Pontão	14779	6
4896	Ponte Preta	14787	6
4897	Portão	14803	6
4898	Porto Alegre	14902	6
4899	Porto Lucena	15008	6
4900	Porto Mauá	15057	6
4901	Porto Vera Cruz	15073	6
4902	Porto Xavier	15107	6
4903	Pouso Novo	15131	6
4904	Presidente Lucena	15149	6
4905	Progresso	15156	6
4906	Protásio Alves	15172	6
4907	Putinga	15206	6
4908	Quaraí	15305	6
4909	Quevedos	15321	6
4910	Quinze de Novembro	15354	6
4911	Redentora	15404	6
4912	Relvado	15453	6
4913	Restinga Seca	15503	6
4914	Rio dos Índios	15552	6
4915	Rio Grande	15602	6
4916	Rio Pardo	15701	6
4917	Riozinho	15750	6
4918	Roca Sales	15800	6
4919	Rodeio Bonito	15909	6
4920	Rolante	16006	6
4921	Ronda Alta	16105	6
4922	Rondinha	16204	6
4923	Roque Gonzales	16303	6
4924	Rosário do Sul	16402	6
4925	Sagrada Família	16428	6
4926	Saldanha Marinho	16436	6
4927	Salto do Jacuí	16451	6
4928	Salvador das Missões	16477	6
4929	Salvador do Sul	16501	6
4930	Sananduva	16600	6
4931	Santa Bárbara do Sul	16709	6
4932	Santa Clara do Sul	16758	6
4933	Santa Cruz do Sul	16808	6
4934	Santa Maria	16907	6
4935	Santa Maria do Herval	16956	6
4936	Santana da Boa Vista	17004	6
4937	Santana do Livramento	17103	6
4938	Santa Rosa	17202	6
4939	Santa Tereza	17251	6
4940	Santa Vitória do Palmar	17301	6
4941	Santiago	17400	6
4942	Santo Ângelo	17509	6
4943	Santo Antônio do Palma	17558	6
4944	Santo Antônio da Patrulha	17608	6
4945	Santo Antônio das Missões	17707	6
4946	Santo Antônio do Planalto	17756	6
4947	Santo Augusto	17806	6
4948	Santo Cristo	17905	6
4949	Santo Expedito do Sul	17954	6
4950	São Borja	18002	6
4951	São Domingos do Sul	18051	6
4952	São Francisco de Assis	18101	6
4953	São Francisco de Paula	18200	6
4954	São Gabriel	18309	6
4955	São Jerônimo	18408	6
4956	São João da Urtiga	18424	6
4957	São João do Polêsine	18432	6
4958	São Jorge	18440	6
4959	São José das Missões	18457	6
4960	São José do Herval	18465	6
4961	São José do Hortêncio	18481	6
4962	São José do Inhacorá	18499	6
4963	São José do Norte	18507	6
4964	São José do Ouro	18606	6
4965	São José dos Ausentes	18622	6
4966	São Leopoldo	18705	6
4967	São Lourenço do Sul	18804	6
4968	São Luiz Gonzaga	18903	6
4969	São Marcos	19000	6
4970	São Martinho	19109	6
4971	São Martinho da Serra	19125	6
4972	São Miguel das Missões	19158	6
4973	São Nicolau	19208	6
4974	São Paulo das Missões	19307	6
4975	São Pedro da Serra	19356	6
4976	São Pedro do Butiá	19372	6
4977	São Pedro do Sul	19406	6
4978	São Sebastião do Caí	19505	6
4979	São Sepé	19604	6
4980	São Valentim	19703	6
4981	São Valentim do Sul	19711	6
4982	São Valério do Sul	19737	6
4983	São Vendelino	19752	6
4984	São Vicente do Sul	19802	6
4985	Sapiranga	19901	6
4986	Sapucaia do Sul	20008	6
4987	Sarandi	20107	6
4988	Seberi	20206	6
4989	Sede Nova	20230	6
4990	Segredo	20263	6
4991	Selbach	20305	6
4992	Senador Salgado Filho	20321	6
4993	Sentinela do Sul	20354	6
4994	Serafina Corrêa	20404	6
4995	Sério	20453	6
4996	Sertão	20503	6
4997	Sertão Santana	20552	6
4998	Sete de Setembro	20578	6
4999	Severiano de Almeida	20602	6
5000	Silveira Martins	20651	6
5001	Sinimbu	20677	6
5002	Sobradinho	20701	6
5003	Soledade	20800	6
5004	Tabaí	20859	6
5005	Tapejara	20909	6
5006	Tapera	21006	6
5007	Tapes	21105	6
5008	Taquara	21204	6
5009	Taquari	21303	6
5010	Taquaruçu do Sul	21329	6
5011	Tavares	21352	6
5012	Tenente Portela	21402	6
5013	Terra de Areia	21436	6
5014	Teutônia	21451	6
5015	Tiradentes do Sul	21477	6
5016	Toropi	21493	6
5017	Torres	21501	6
5018	Tramandaí	21600	6
5019	Travesseiro	21626	6
5020	Três Arroios	21634	6
5021	Três Cachoeiras	21667	6
5022	Três Coroas	21709	6
5023	Três de Maio	21808	6
5024	Três Forquilhas	21832	6
5025	Três Palmeiras	21857	6
5026	Três Passos	21907	6
5027	Trindade do Sul	21956	6
5028	Triunfo	22004	6
5029	Tucunduva	22103	6
5030	Tunas	22152	6
5031	Tupanci do Sul	22186	6
5032	Tupanciretã	22202	6
5033	Tupandi	22251	6
5034	Tuparendi	22301	6
5035	Turuçu	22327	6
5036	Ubiretama	22343	6
5037	União da Serra	22350	6
5038	Unistalda	22376	6
5039	Uruguaiana	22400	6
5040	Vacaria	22509	6
5041	Vale Verde	22525	6
5042	Vale do Sol	22533	6
5043	Vale Real	22541	6
5044	Vanini	22558	6
5045	Venâncio Aires	22608	6
5046	Vera Cruz	22707	6
5047	Veranópolis	22806	6
5048	Vespasiano Correa	22855	6
5049	Viadutos	22905	6
5050	Viamão	23002	6
5051	Vicente Dutra	23101	6
5052	Victor Graeff	23200	6
5053	Vila Flores	23309	6
5054	Vila Lângaro	23358	6
5055	Vila Maria	23408	6
5056	Vila Nova do Sul	23457	6
5057	Vista Alegre	23507	6
5058	Vista Alegre do Prata	23606	6
5059	Vista Gaúcha	23705	6
5060	Vitória das Missões	23754	6
5061	Xangri-lá	23804	6
5062	Água Clara	00203	17
5063	Alcinópolis	00252	17
5064	Amambaí	00609	17
5065	Anastácio	00708	17
5066	Anaurilândia	00807	17
5067	Angélica	00856	17
5068	Antônio João	00906	17
5069	Aparecida do Taboado	01003	17
5070	Aquidauana	01102	17
5071	Aral Moreira	01243	17
5072	Bandeirantes	01508	17
5073	Bataguassu	01904	17
5074	Bataiporã	02001	17
5075	Bela Vista	02100	17
5076	Bodoquena	02159	17
5077	Bonito	02209	17
5078	Brasilândia	02308	17
5079	Caarapó	02407	17
5080	Camapuã	02605	17
5081	Campo Grande	02704	17
5082	Caracol	02803	17
5083	Cassilândia	02902	17
5084	Chapadão do Sul	02951	17
5085	Corguinho	03108	17
5086	Coronel Sapucaia	03157	17
5087	Corumbá	03207	17
5088	Costa Rica	03256	17
5089	Coxim	03306	17
5090	Deodápolis	03454	17
5091	Dois Irmãos do Buriti	03488	17
5092	Douradina	03504	17
5093	Dourados	03702	17
5094	Eldorado	03751	17
5095	Fátima do Sul	03801	17
5096	Glória de Dourados	04007	17
5097	Guia Lopes da Laguna	04106	17
5098	Iguatemi	04304	17
5099	Inocência	04403	17
5100	Itaporã	04502	17
5101	Itaquiraí	04601	17
5102	Ivinhema	04700	17
5103	Japorã	04809	17
5104	Jaraguari	04908	17
5105	Jardim	05004	17
5106	Jateí	05103	17
5107	Juti	05152	17
5108	Ladário	05202	17
5109	Laguna Carapã	05251	17
5110	Maracaju	05400	17
5111	Miranda	05608	17
5112	Mundo Novo	05681	17
5113	Naviraí	05707	17
5114	Nioaque	05806	17
5115	Nova Alvorada do Sul	06002	17
5116	Nova Andradina	06200	17
5117	Novo Horizonte do Sul	06259	17
5118	Paranaíba	06309	17
5119	Paranhos	06358	17
5120	Pedro Gomes	06408	17
5121	Ponta Porã	06606	17
5122	Porto Murtinho	06903	17
5123	Ribas do Rio Pardo	07109	17
5124	Rio Brilhante	07208	17
5125	Rio Negro	07307	17
5126	Rio Verde de Mato Grosso	07406	17
5127	Rochedo	07505	17
5128	Santa Rita do Pardo	07554	17
5129	São Gabriel do Oeste	07695	17
5130	Sete Quedas	07703	17
5131	Selvíria	07802	17
5132	Sidrolândia	07901	17
5133	Sonora	07935	17
5134	Tacuru	07950	17
5135	Taquarussu	07976	17
5136	Terenos	08008	17
5137	Três Lagoas	08305	17
5138	Vicentina	08404	17
5139	Acorizal	00102	18
5140	Água Boa	00201	18
5141	Alta Floresta	00250	18
5142	Alto Araguaia	00300	18
5143	Alto Boa Vista	00359	18
5144	Alto Garças	00409	18
5145	Alto Paraguai	00508	18
5146	Alto Taquari	00607	18
5147	Apiacás	00805	18
5148	Araguaiana	01001	18
5149	Araguainha	01209	18
5150	Araputanga	01258	18
5151	Arenápolis	01308	18
5152	Aripuanã	01407	18
5153	Barão de Melgaço	01605	18
5154	Barra do Bugres	01704	18
5155	Barra do Garças	01803	18
5156	Brasnorte	01902	18
5157	Cáceres	02504	18
5158	Campinápolis	02603	18
5159	Campo Novo do Parecis	02637	18
5160	Campo Verde	02678	18
5161	Campos de Júlio	02686	18
5162	Canabrava do Norte	02694	18
5163	Canarana	02702	18
5164	Carlinda	02793	18
5165	Castanheira	02850	18
5166	Chapada dos Guimarães	03007	18
5167	Cláudia	03056	18
5168	Cocalinho	03106	18
5169	Colíder	03205	18
5170	Comodoro	03304	18
5171	Confresa	03353	18
5172	Cotriguaçu	03379	18
5173	Cuiabá	03403	18
5174	Denise	03452	18
5175	Diamantino	03502	18
5176	Dom Aquino	03601	18
5177	Feliz Natal	03700	18
5178	Figueirópolis D'Oeste	03809	18
5179	Gaúcha do Norte	03858	18
5180	General Carneiro	03908	18
5181	Glória D'Oeste	03957	18
5182	Guarantã do Norte	04104	18
5183	Guiratinga	04203	18
5184	Indiavaí	04500	18
5185	Itaúba	04559	18
5186	Itiquira	04609	18
5187	Jaciara	04807	18
5188	Jangada	04906	18
5189	Jauru	05002	18
5190	Juara	05101	18
5191	Juína	05150	18
5192	Juruena	05176	18
5193	Juscimeira	05200	18
5194	Lambari D'Oeste	05234	18
5195	Lucas do Rio Verde	05259	18
5196	Luciára	05309	18
5197	Vila Bela da Santíssima Trinda	05507	18
5198	Marcelândia	05580	18
5199	Matupá	05606	18
5200	Mirassol d'Oeste	05622	18
5201	Nobres	05903	18
5202	Nortelândia	06000	18
5203	Nossa Senhora do Livramento	06109	18
5204	Nova Bandeirantes	06158	18
5205	Nova Lacerda	06182	18
5206	Nova Brasilândia	06208	18
5207	Nova Canaã do Norte	06216	18
5208	Nova Mutum	06224	18
5209	Nova Olímpia	06232	18
5210	Nova Ubiratã	06240	18
5211	Nova Xavantina	06257	18
5212	Novo Mundo	06265	18
5213	Novo Horizonte do Norte	06273	18
5214	Novo São Joaquim	06281	18
5215	Paranaíta	06299	18
5216	Paranatinga	06307	18
5217	Pedra Preta	06372	18
5218	Peixoto de Azevedo	06422	18
5219	Planalto da Serra	06455	18
5220	Poconé	06505	18
5221	Pontal do Araguaia	06653	18
5222	Ponte Branca	06703	18
5223	Pontes e Lacerda	06752	18
5224	Porto Alegre do Norte	06778	18
5225	Porto dos Gaúchos	06802	18
5226	Porto Esperidião	06828	18
5227	Porto Estrela	06851	18
5228	Poxoréo	07008	18
5229	Primavera do Leste	07040	18
5230	Querência	07065	18
5231	São José dos Quatro Marcos	07107	18
5232	Reserva do Cabaçal	07156	18
5233	Ribeirão Cascalheira	07180	18
5234	Ribeirãozinho	07198	18
5235	Rio Branco	07206	18
5236	Santa Carmem	07248	18
5237	Santo Afonso	07263	18
5238	São José do Povo	07297	18
5239	São José do Rio Claro	07305	18
5240	São José do Xingu	07354	18
5241	São Pedro da Cipa	07404	18
5242	Rondonópolis	07602	18
5243	Rosário Oeste	07701	18
5244	Salto do Céu	07750	18
5245	Santa Terezinha	07776	18
5246	Santo Antônio do Leverger	07800	18
5247	São Félix do Araguaia	07859	18
5248	Sapezal	07875	18
5249	Sinop	07909	18
5250	Sorriso	07925	18
5251	Tabaporã	07941	18
5252	Tangará da Serra	07958	18
5253	Tapurah	08006	18
5254	Terra Nova do Norte	08055	18
5255	Tesouro	08105	18
5256	Torixoréu	08204	18
5257	União do Sul	08303	18
5258	Várzea Grande	08402	18
5259	Vera	08501	18
5260	Vila Rica	08600	18
5261	Nova Guarita	08808	18
5262	Nova Marilândia	08857	18
5263	Nova Maringá	08907	18
5264	Nova Monte verde	08956	18
5265	Abadia de Goiás	00050	15
5266	Abadiânia	00100	15
5267	Acreúna	00134	15
5268	Adelândia	00159	15
5269	Água Fria de Goiás	00175	15
5270	Água Limpa	00209	15
5271	Águas Lindas de Goiás	00258	15
5272	Alexânia	00308	15
5273	Aloândia	00506	15
5274	Alto Horizonte	00555	15
5275	Alto Paraíso de Goiás	00605	15
5276	Alvorada do Norte	00803	15
5277	Amaralina	00829	15
5278	Americano do Brasil	00852	15
5279	Amorinópolis	00902	15
5280	Anápolis	01108	15
5281	Anhanguera	01207	15
5282	Anicuns	01306	15
5283	Aparecida de Goiânia	01405	15
5284	Aparecida do Rio Doce	01454	15
5285	Aporé	01504	15
5286	Araçu	01603	15
5287	Aragarças	01702	15
5288	Aragoiânia	01801	15
5289	Araguapaz	02155	15
5290	Arenópolis	02353	15
5291	Aruanã	02502	15
5292	Aurilândia	02601	15
5293	Avelinópolis	02809	15
5294	Baliza	03104	15
5295	Barro Alto	03203	15
5296	Bela Vista de Goiás	03302	15
5297	Bom Jardim de Goiás	03401	15
5298	Bom Jesus de Goiás	03500	15
5299	Bonfinópolis	03559	15
5300	Bonópolis	03575	15
5301	Brazabrantes	03609	15
5302	Britânia	03807	15
5303	Buriti Alegre	03906	15
5304	Buriti de Goiás	03939	15
5305	Buritinópolis	03962	15
5306	Cabeceiras	04003	15
5307	Cachoeira Alta	04102	15
5308	Cachoeira de Goiás	04201	15
5309	Cachoeira Dourada	04250	15
5310	Caçu	04300	15
5311	Caiapônia	04409	15
5312	Caldas Novas	04508	15
5313	Caldazinha	04557	15
5314	Campestre de Goiás	04607	15
5315	Campinaçu	04656	15
5316	Campinorte	04706	15
5317	Campo Alegre de Goiás	04805	15
5318	Campos Belos	04904	15
5319	Campos Verdes	04953	15
5320	Carmo do Rio Verde	05000	15
5321	Castelândia	05059	15
5322	Catalão	05109	15
5323	Caturaí	05208	15
5324	Cavalcante	05307	15
5325	Ceres	05406	15
5326	Cezarina	05455	15
5327	Chapadão do Céu	05471	15
5328	Cidade Ocidental	05497	15
5329	Cocalzinho de Goiás	05513	15
5330	Colinas do Sul	05521	15
5331	Córrego do Ouro	05703	15
5332	Corumbá de Goiás	05802	15
5333	Corumbaíba	05901	15
5334	Cristalina	06206	15
5335	Cristianópolis	06305	15
5336	Crixás	06404	15
5337	Cromínia	06503	15
5338	Cumari	06602	15
5339	Damianópolis	06701	15
5340	Damolândia	06800	15
5341	Davinópolis	06909	15
5342	Diorama	07105	15
5343	Doverlândia	07253	15
5344	Edealina	07352	15
5345	Edéia	07402	15
5346	Estrela do Norte	07501	15
5347	Faina	07535	15
5348	Fazenda Nova	07600	15
5349	Firminópolis	07808	15
5350	Flores de Goiás	07907	15
5351	Formosa	08004	15
5352	Formoso	08103	15
5353	Divinópolis de Goiás	08301	15
5354	Goianápolis	08400	15
5355	Goiandira	08509	15
5356	Goianésia	08608	15
5357	Goiânia	08707	15
5358	Goianira	08806	15
5359	Goiás	08905	15
5360	Goiatuba	09101	15
5361	Gouvelândia	09150	15
5362	Guapó	09200	15
5363	Guaraíta	09291	15
5364	Guarani de Goiás	09408	15
5365	Guarinos	09457	15
5366	Heitoraí	09606	15
5367	Hidrolândia	09705	15
5368	Hidrolina	09804	15
5369	Iaciara	09903	15
5370	Inaciolândia	09937	15
5371	Indiara	09952	15
5372	Inhumas	10000	15
5373	Ipameri	10109	15
5374	Iporá	10208	15
5375	Israelândia	10307	15
5376	Itaberaí	10406	15
5377	Itaguari	10562	15
5378	Itaguaru	10604	15
5379	Itajá	10802	15
5380	Itapaci	10901	15
5381	Itapirapuã	11008	15
5382	Itapuranga	11206	15
5383	Itarumã	11305	15
5384	Itauçu	11404	15
5385	Itumbiara	11503	15
5386	Ivolândia	11602	15
5387	Jandaia	11701	15
5388	Jaraguá	11800	15
5389	Jataí	11909	15
5390	Jaupaci	12006	15
5391	Jesúpolis	12055	15
5392	Joviânia	12105	15
5393	Jussara	12204	15
5394	Leopoldo de Bulhões	12303	15
5395	Luziânia	12501	15
5396	Mairipotaba	12600	15
5397	Mambaí	12709	15
5398	Mara Rosa	12808	15
5399	Marzagão	12907	15
5400	Matrinchã	12956	15
5401	Maurilândia	13004	15
5402	Mimoso de Goiás	13053	15
5403	Minaçu	13087	15
5404	Mineiros	13103	15
5405	Moiporá	13400	15
5406	Monte Alegre de Goiás	13509	15
5407	Montes Claros de Goiás	13707	15
5408	Montividiu	13756	15
5409	Montividiu do Norte	13772	15
5410	Morrinhos	13806	15
5411	Morro Agudo de Goiás	13855	15
5412	Mossâmedes	13905	15
5413	Mozarlândia	14002	15
5414	Mundo Novo	14051	15
5415	Mutunópolis	14101	15
5416	Nazário	14408	15
5417	Nerópolis	14507	15
5418	Niquelândia	14606	15
5419	Nova América	14705	15
5420	Nova Aurora	14804	15
5421	Nova Crixás	14838	15
5422	Nova Glória	14861	15
5423	Nova Iguaçu de Goiás	14879	15
5424	Nova Roma	14903	15
5425	Nova Veneza	15009	15
5426	Novo Brasil	15207	15
5427	Novo Gama	15231	15
5428	Novo Planalto	15256	15
5429	Orizona	15306	15
5430	Ouro Verde de Goiás	15405	15
5431	Ouvidor	15504	15
5432	Padre Bernardo	15603	15
5433	Palestina de Goiás	15652	15
5434	Palmeiras de Goiás	15702	15
5435	Palmelo	15801	15
5436	Palminópolis	15900	15
5437	Panamá	16007	15
5438	Paranaiguara	16304	15
5439	Paraúna	16403	15
5440	Perolândia	16452	15
5441	Petrolina de Goiás	16809	15
5442	Pilar de Goiás	16908	15
5443	Piracanjuba	17104	15
5444	Piranhas	17203	15
5445	Pirenópolis	17302	15
5446	Pires do Rio	17401	15
5447	Planaltina	17609	15
5448	Pontalina	17708	15
5449	Porangatu	18003	15
5450	Porteirão	18052	15
5451	Portelândia	18102	15
5452	Posse	18300	15
5453	Professor Jamil	18391	15
5454	Quirinópolis	18508	15
5455	Rialma	18607	15
5456	Rianápolis	18706	15
5457	Rio Quente	18789	15
5458	Rio Verde	18805	15
5459	Rubiataba	18904	15
5460	Sanclerlândia	19001	15
5461	Santa Bárbara de Goiás	19100	15
5462	Santa Cruz de Goiás	19209	15
5463	Santa Fé de Goiás	19258	15
5464	Santa Helena de Goiás	19308	15
5465	Santa Isabel	19357	15
5466	Santa Rita do Araguaia	19407	15
5467	Santa Rita do Novo Destino	19456	15
5468	Santa Rosa de Goiás	19506	15
5469	Santa Tereza de Goiás	19605	15
5470	Santa Terezinha de Goiás	19704	15
5471	Santo Antônio da Barra	19712	15
5472	Santo Antônio de Goiás	19738	15
5473	Santo Antônio do Descoberto	19753	15
5474	São Domingos	19803	15
5475	São Francisco de Goiás	19902	15
5476	São João d'Aliança	20009	15
5477	São João da Paraúna	20058	15
5478	São Luís de Montes Belos	20108	15
5479	São Luíz do Norte	20157	15
5480	São Miguel do Araguaia	20207	15
5481	São Miguel do Passa Quatro	20264	15
5482	São Patrício	20280	15
5483	São Simão	20405	15
5484	Senador Canedo	20454	15
5485	Serranópolis	20504	15
5486	Silvânia	20603	15
5487	Simolândia	20686	15
5488	Sítio d'Abadia	20702	15
5489	Taquaral de Goiás	21007	15
5490	Teresina de Goiás	21080	15
5491	Terezópolis de Goiás	21197	15
5492	Três Ranchos	21304	15
5493	Trindade	21403	15
5494	Trombas	21452	15
5495	Turvânia	21502	15
5496	Turvelândia	21551	15
5497	Uirapuru	21577	15
5498	Uruaçu	21601	15
5499	Uruana	21700	15
5500	Urutaí	21809	15
5501	Valparaíso de Goiás	21858	15
5502	Varjão	21908	15
5503	Vianópolis	22005	15
5504	Vicentinópolis	22054	15
5505	Vila Boa	22203	15
5506	Vila Propício	22302	15
5507	Brasília	00108	13
\.


--
-- Data for Name: clinicaautorizada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY clinicaautorizada (id, nome, crm, cnpj, tipo, data, datainativa, empresa_id) FROM stdin;
1	São Carlos	\N	4545	01	2006-01-01	\N	1
\.


--
-- Data for Name: colaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaborador (id, matricula, nome, nomecomercial, desligado, datadesligamento, observacao, dataadmissao, logradouro, numero, complemento, bairro, cep, cpf, pis, rg, naturalidade, pai, mae, conjuge, profissaopai, profissaomae, profissaoconjuge, conjugetrabalha, parentesamigos, qtdfilhos, sexo, datanascimento, escolaridade, estadocivil, ddd, fonefixo, fonecelular, email, vinculo, codigoac, cursos, regimerevezamento, naointegraac, empresa_id, uf_id, cidade_id, usuario_id, candidato_id, motivodemissao_id, deficiencia, rgorgaoemissor, rguf_id, rgdataexpedicao, numerohab, registro, emissao, vencimento, categoria, titeleitnumero, titeleitzona, titeleitsecao, certmilnumero, certmiltipo, certmilserie, ctpsnumero, ctpsserie, ctpsdv, ctpsuf_id, ctpsdataexpedicao, respondeuentrevista) FROM stdin;
2	2345	Moésio Medeiros da Silva	Moésio Medeiros	f	\N	\N	2005-05-09	Rua Santa Liduína, 65	\N	\N	V. M. Sátiro	60713590	82194793372	\N	\N	\N	\N	\N	Maria Evanda da Penha	\N	\N	\N	f	\N	1	M	1979-10-29	5	2	\N	3483.2370	8868.9744	franciscobarroso@grupofortes.com.br	\N	\N	\N	\N	t	1	1	946	3	\N	\N	0	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f
3	3456	Israel Charles Freitas	Charles Freitas	t	2008-11-05	teste	2005-05-09	Rua Santa Liduína, 65	\N	\N	V. M. Sátiro	60713590	82194793372	\N	\N	\N	\N	\N	Maria Evanda da Penha	\N	\N	\N	f	\N	1	M	1979-10-29	5	2	\N	3483.2370	8868.9744	franciscobarroso@grupofortes.com.br	\N	\N	\N	\N	t	1	1	946	4	\N	2	0	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f
532	2121212121432343	Maria do Selenium	Maria do Selenium	f	\N		2008-12-01	rua do Teste	25				15582653503										f		0	F	2008-12-01	09	01	85	89989898			E				f	1	1	946	\N	\N	\N	0		\N	\N			\N	\N										\N	\N	\N	f
533	1212121212	Ze do Selenium	Ze do Selenium	f	\N		2008-12-01	rua teste2	45				55882105226										f		0	M	1966-03-22	07	01	85	78787878			E				f	1	1	946	\N	\N	\N	0		\N	\N			\N	\N										\N	\N	\N	f
4	4567	Robertson Bob Freitas	Robertson Bob	f	\N	\N	2005-05-09	Rua Santa Liduína, 65	\N	\N	V. M. Sátiro	60713590	82194793372	\N	\N	\N	\N	\N	Maria Evanda da Penha	\N	\N	\N	f	\N	1	M	1979-10-29	5	2	\N	3483.2370	8868.9744	franciscobarroso@grupofortes.com.br	\N	\N	\N	\N	t	1	1	946	5	\N	\N	0	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	t
1	1234	Igo Gomes Coelho	Igo Coelho	f	\N		2005-05-09	Rua Santa Liduína, 65	222		V. M. Sátiro	60713590	82194793372						Maria Evanda da Penha				f		1	M	1979-10-29	01	01	22	3483.2370	8868.9744	franciscobarroso@grupofortes.com.br	E				f	1	1	946	2	\N	\N	0		\N	\N			\N	\N										\N	\N	\N	f
534		ZX teste ac	ZX teste ac	f	\N		2009-02-01	Logradouro	52				71831835053										f		0	M	1985-02-10	01	03	52	52222200			E	1122356			f	1	1	946	\N	\N	\N	0		\N	\N			\N	\N										\N	\N	\N	f
\.


--
-- Data for Name: colaboradoridioma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradoridioma (id, nivel, colaborador_id, idioma_id) FROM stdin;
\.


--
-- Data for Name: colaboradorocorrencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorocorrencia (id, dataini, datafim, observacao, colaborador_id, ocorrencia_id) FROM stdin;
\.


--
-- Data for Name: colaboradorpresenca; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorpresenca (id, presenca, colaboradorturma_id, diaturma_id) FROM stdin;
\.


--
-- Data for Name: colaboradorquestionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorquestionario (id, colaborador_id, questionario_id, respondida, respondidaem) FROM stdin;
25	3	1	t	\N
26	1	1	t	\N
27	2	1	t	\N
28	4	1	f	\N
48	4	3	t	2008-11-18
\.


--
-- Data for Name: colaboradorresposta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorresposta (id, comentario, valor, pergunta_id, resposta_id, colaboradorquestionario_id, areaorganizacional_id) FROM stdin;
20	sim ele é um bom lider	0	95	25	25	1
21	não gosto dele	0	95	26	26	2
22	ele separou por projetos	0	96	\N	25	1
23	todo mundo trabalha onde quer	0	96	\N	26	2
24	é muito organizado	0	96	\N	27	4
25		0	98	\N	27	4
26		10	98	\N	25	1
27		10	98	\N	26	2
28		5	98	\N	27	2
48	\N	0	155	46	48	1
49	Ela é boa	9	157	\N	48	1
50	é um cara muito legal.	0	153	\N	48	1
51	liderança com qualidade	0	156	\N	48	1
52	\N	9	154	\N	48	1
\.


--
-- Data for Name: colaboradorturma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorturma (id, origemdnt, aprovado, colaborador_id, prioridadetreinamento_id, turma_id, curso_id, dnt_id) FROM stdin;
\.


--
-- Data for Name: conhecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conhecimento (id, nome, empresa_id) FROM stdin;
1	Windows	1
2	Word	1
3	Excel	1
4	SQL	1
5	Delphi	1
6	Java	1
7	Folha de Pagamento	1
8	Controle Fiscal	1
9	Remuneração variável	1
\.


--
-- Data for Name: conhecimento_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conhecimento_areaorganizacional (conhecimentos_id, areaorganizacionals_id) FROM stdin;
\.


--
-- Data for Name: curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY curso (id, nome, conteudoprogramatico, empresa_id) FROM stdin;
1	Java	Curso de Java	1
2	Atendimento ao Cliente	Curso de Atendimento ao cliente	1
\.


--
-- Data for Name: dependente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY dependente (id, nome, datanascimento, seqac, colaborador_id) FROM stdin;
\.


--
-- Data for Name: diaturma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY diaturma (id, dia, turma_id) FROM stdin;
\.


--
-- Data for Name: dnt; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY dnt (id, nome, data, empresa_id) FROM stdin;
1	DNT 2008	2008-10-01	1
\.


--
-- Data for Name: documentoanexo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY documentoanexo (id, descricao, data, observacao, url, origem, origemid, etapaseletiva_id) FROM stdin;
\.


--
-- Data for Name: duracaopreenchimentovaga; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY duracaopreenchimentovaga (id, qtddiasprimeiracontratacao, solicitacao_id, cargo_id, areaorganizacional_id) FROM stdin;
\.


--
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empresa (id, nome, cnpj, razaosocial, codigoac, emailremetente, emailrespsetorpessoal, emailresprh, cnae, grauderisco, representantelegal, nitrepresentantelegal, horariotrabalho, endereco, acintegra, acurlsoap, acurlwsdl, acusuario, acsenha, maxcandidatacargo, logourl, exibirsalario) FROM stdin;
1	Empresa Padrão	00000000	Empresa Padrão		franciscobarroso@grupofortes.com.br	franciscobarroso@grupofortes.com.br	rhfortesrh@teste.com.br							f	url ws	url wsdl	admi	\N	5	fortes.gif	t
\.


--
-- Data for Name: empresabds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empresabds (id, nome, contato, fone, email, ddd, empresa_id) FROM stdin;
1	Empresa BDS	Francisco José	2222222	franciscobarroso@grupofortes.com.br	85	1
\.


--
-- Data for Name: engenheiroresponsavel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY engenheiroresponsavel (id, nome, apartirde, crea, empresa_id) FROM stdin;
1	Andre Souza	2007-01-01	98654656	1
\.


--
-- Data for Name: entrevista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY entrevista (id, ativa, questionario_id) FROM stdin;
1	t	3
11	t	53
\.


--
-- Data for Name: epc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY epc (id, codigo, descricao, empresa_id) FROM stdin;
1	TD87F	Toldo	1
\.


--
-- Data for Name: epc_ambiente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY epc_ambiente (epc_id, ambientes_id) FROM stdin;
\.


--
-- Data for Name: epi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY epi (id, nome, fabricante, empresa_id, tipoepi_id) FROM stdin;
\.


--
-- Data for Name: epihistorico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY epihistorico (id, atenuacao, emissao, vencimentoca, validadeuso, ca, data, epi_id) FROM stdin;
\.


--
-- Data for Name: estabelecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY estabelecimento (id, nome, logradouro, numero, complemento, bairro, cep, complementocnpj, codigoac, uf_id, cidade_id, empresa_id) FROM stdin;
1	Estabelecimento Padrão	\N	\N	\N	\N	\N	0000	\N	\N	\N	1
\.


--
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY estado (id, sigla, nome) FROM stdin;
1	CE	Ceará
2	PE	Pernambuco
3	RN	Rio Grande do Norte
4	RJ	Rio de Janeiro
5	SP	São Paulo
6	RS	Rio Grande do Sul
7	MG	Minas Gerais
8	AC	Acre
9	AL	Alagoas
10	AM	Amazonas
11	AP	Amapá
12	BA	Bahia
13	DF	Distrito Federal
14	ES	Espírito Santo
15	GO	Goiás
16	MA	Maranhão
17	MS	Mato Grosso do Sul
18	MT	Mato Grosso
19	PA	Pará
20	PB	Paraíba
21	PI	Piauí
22	PR	Paraná
23	RO	Rondônia
24	RR	Roraima
25	SC	Santa Catarina
26	SE	Sergipe
27	TO	Tocantins
\.


--
-- Data for Name: etapaseletiva; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY etapaseletiva (id, nome, ordem, empresa_id) FROM stdin;
1	Entrevista Coletiva	1	1
2	Referencias	2	1
3	Teste Psicologico	3	1
4	Teste Pratico	4	1
5	Entrevista com a chefia	5	1
\.


--
-- Data for Name: exame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY exame (id, nome, periodicidade, periodico, empresa_id) FROM stdin;
1	ASA	0	f	1
2	Admissional	0	f	1
3	Demissional	0	f	1
\.


--
-- Data for Name: experiencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY experiencia (id, empresa, dataadmissao, datadesligamento, observacao, nomemercado, candidato_id, colaborador_id, cargo_id) FROM stdin;
\.


--
-- Data for Name: faixasalarial; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY faixasalarial (id, nome, codigoac, cargo_id) FROM stdin;
1	I	\N	1
2	II	\N	1
3	III	\N	1
4	I	\N	2
5	II	\N	2
6	I	\N	3
7	II	\N	3
8	I	\N	4
9	II	\N	4
10	III	\N	4
11	I	\N	5
12	II	\N	5
13	III	\N	5
14	I	\N	6
15	II	\N	6
16	III	\N	6
\.


--
-- Data for Name: faixasalarialhistorico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY faixasalarialhistorico (id, data, valor, tipo, faixasalarial_id, indice_id, quantidade, status) FROM stdin;
18	2008-12-01	2500	3	3	\N	0	1
19	2008-12-01	3500	3	5	\N	0	1
20	2008-12-01	3200	3	4	\N	0	1
21	2008-12-01	2200	3	2	\N	0	1
22	2008-12-01	2000	3	1	\N	0	1
23	2006-07-01	999	3	1	\N	0	1
24	2006-07-01	999	3	2	\N	0	1
25	2006-07-01	999	3	3	\N	0	1
26	2006-07-01	999	3	4	\N	0	1
27	2006-07-01	999	3	5	\N	0	1
28	2006-07-01	999	3	6	\N	0	1
29	2006-07-01	999	3	7	\N	0	1
30	2006-07-01	999	3	8	\N	0	1
31	2006-07-01	999	3	9	\N	0	1
32	2006-07-01	999	3	10	\N	0	1
33	2006-07-01	999	3	11	\N	0	1
34	2006-07-01	999	3	12	\N	0	1
35	2006-07-01	999	3	13	\N	0	1
36	2006-07-01	999	3	14	\N	0	1
37	2006-07-01	999	3	15	\N	0	1
38	2006-07-01	999	3	16	\N	0	1
\.


--
-- Data for Name: formacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY formacao (id, situacao, tipo, curso, "local", conclusao, candidato_id, colaborador_id, areaformacao_id) FROM stdin;
\.


--
-- Data for Name: funcao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY funcao (id, nome, cargo_id) FROM stdin;
\.


--
-- Data for Name: gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gasto (id, nome, naoimportar, codigoac, grupogasto_id, empresa_id) FROM stdin;
1	Salario	t	\N	1	1
2	Auxilio-Transporte	t	\N	2	1
3	Comissão	t	\N	2	1
\.


--
-- Data for Name: gastoempresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gastoempresa (id, mesano, colaborador_id, empresa_id) FROM stdin;
1	2008-01-01	1	1
2	2008-01-01	2	1
3	2008-01-01	3	1
4	2008-01-01	4	1
\.


--
-- Data for Name: gastoempresaitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gastoempresaitem (id, valor, gasto_id, gastoempresa_id) FROM stdin;
\.


--
-- Data for Name: ghe; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ghe (id, nome, empresa_id) FROM stdin;
1	Desenvolvimento	1
2	Suporte	1
\.


--
-- Data for Name: ghehistorico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ghehistorico (id, insalubre, periculoso, data, ambiente_id, ghe_id) FROM stdin;
1	f	f	2008-01-01	1	1
2	f	f	2008-01-01	2	2
\.


--
-- Data for Name: ghehistorico_funcao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ghehistorico_funcao (ghehistorico_id, funcoes_id) FROM stdin;
\.


--
-- Data for Name: grupogasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY grupogasto (id, nome, empresa_id) FROM stdin;
1	Obrigatório	1
2	Voluntário	1
\.


--
-- Data for Name: grupoocupacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY grupoocupacional (id, nome, empresa_id) FROM stdin;
1	Informática Desenvolvimento	1
2	Informática Suporte	1
\.


--
-- Data for Name: historicoambiente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicoambiente (id, descricao, data, datainativo, ambiente_id) FROM stdin;
\.


--
-- Data for Name: historicobeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicobeneficio (id, data, valor, paracolaborador, paradependentedireto, paradependenteindireto, beneficio_id) FROM stdin;
1	2005-05-09	55	50	50	25	1
2	2005-05-09	55	50	50	25	2
3	2005-05-09	55	50	50	25	3
4	2005-05-09	55	50	50	25	4
\.


--
-- Data for Name: historicocandidato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocandidato (id, data, responsavel, observacao, apto, etapaseletiva_id, candidatosolicitacao_id) FROM stdin;
\.


--
-- Data for Name: historicocolaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocolaborador (id, salario, data, motivo, gfip, colaborador_id, areaorganizacional_id, historicoanterior_id, funcao_id, ambiente_id, estabelecimento_id, tiposalario, indice_id, faixasalarial_id, quantidadeindice, reajustecolaborador_id, status) FROM stdin;
2	5555	2005-07-09	D	\N	2	1	\N	\N	\N	1	3	\N	13	0	\N	1
3	5555	2006-01-09	P	\N	3	1	\N	\N	\N	1	3	\N	3	0	\N	1
4	5555	2006-07-09	D	\N	4	1	\N	\N	\N	1	3	\N	4	0	\N	1
5	5555	2006-09-09	C	\N	2	1	\N	\N	\N	1	3	\N	5	0	\N	1
6	5555	2006-09-09	C	\N	3	1	\N	\N	\N	1	3	\N	12	0	\N	1
7	5555	2006-09-09	C	\N	4	1	\N	\N	\N	1	3	\N	10	0	\N	1
1	5555	2005-05-09	C	\N	1	2	\N	\N	\N	1	3	\N	3	0	\N	1
358	3000	2008-12-22	C	\N	532	2	\N	\N	\N	1	3	\N	3	0	\N	1
359	2500	2008-12-23	C	\N	533	2	\N	\N	\N	1	3	\N	3	0	\N	1
360	4242.3299999999999	2009-02-01	C	\N	534	2	\N	\N	\N	1	3	\N	11	0	\N	2
\.


--
-- Data for Name: historicocolaboradorbeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocolaboradorbeneficio (id, data, dataate, colaborador_id) FROM stdin;
\.


--
-- Data for Name: historicocolaboradorbeneficio_beneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocolaboradorbeneficio_beneficio (historicocolaboradorbeneficio_id, beneficios_id) FROM stdin;
\.


--
-- Data for Name: historicofuncao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicofuncao (id, data, descricao, funcao_id) FROM stdin;
\.


--
-- Data for Name: historicofuncao_exame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicofuncao_exame (historicofuncao_id, exames_id) FROM stdin;
\.


--
-- Data for Name: idioma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY idioma (id, nome) FROM stdin;
1	Inglês
2	Espanhol
3	Francês
4	Alemão
5	Italiano
\.


--
-- Data for Name: indice; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indice (id, nome, codigoac) FROM stdin;
1	Salario Minimo	
22	Salario selenium	
\.


--
-- Data for Name: indicehistorico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indicehistorico (id, data, valor, indice_id) FROM stdin;
1	2008-12-03	500	1
15	2020-03-20	2000	22
\.


--
-- Data for Name: ltcat; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ltcat (id, data, laudotecnico, descricaoarquitetonica, introducaoambientes, descricaosetores, metodologia, fundamentacaolegal, introducaoavaliacaoriscos, introducaoriscoruido, introducaoriscocalor, introducaoriscoquimico, introducaoriscoergonomico, introducaoriscoacidente, tempoexposicao, descricaoepc, introducaoepi, conclusao, dadoslevantamentoresponsabilidade, bloqueado, empresa_id, tabelaruido_id, tabelatemperatura_id) FROM stdin;
1	2008-01-01	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	1	1	1
\.


--
-- Data for Name: medicocoordenador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY medicocoordenador (id, nome, apartirde, crm, empresa_id) FROM stdin;
1	Luiz Queiroz	2006-01-02	54546	1
\.


--
-- Data for Name: mensagem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mensagem (id, remetente, data, texto, link) FROM stdin;
1	Ze do Selenium	2009-01-10 10:10:00	Esta é uma mensagem para fazer testes funcionais com o selenium	\N
\.


--
-- Data for Name: motivodemissao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY motivodemissao (id, motivo, empresa_id) FROM stdin;
1	Justa Causa	1
2	Acordo	1
\.


--
-- Data for Name: motivosolicitacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY motivosolicitacao (id, descricao) FROM stdin;
1	Aumento de quadro
2	Substituição
\.


--
-- Data for Name: ocorrencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ocorrencia (id, descricao, pontuacao, empresa_id) FROM stdin;
1	Atraso não justificado	1	1
2	Falta	3	1
3	Insubordinação	5	1
\.


--
-- Data for Name: papel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY papel (id, codigo, nome, url, ordem, menu, papelmae_id) FROM stdin;
382	ROLE_AVALDESEMPENHO	Aval. Desempenho	#	4	t	\N
365	ROLE_T&D	T&D	#	5	t	\N
373	ROLE_COLAB	Info. Funcionais	#	6	t	\N
378	ROLE_INDICADORES	Indicadores	#	7	t	\N
75	ROLE_SESMT	SESMT	#	8	t	\N
37	ROLE_UTI	Utilitários	#	9	t	\N
361	ROLE_C&S	C&S	#	2	t	\N
353	ROLE_PES	Pesquisas	#	3	t	\N
357	ROLE_R&S	R&S	#	1	t	\N
9	ROLE_CAD_AREA	Áreas Organizacionais	/geral/areaOrganizacional/list.action	2	t	361
59	ROLE_PESQUISA	Ver Pesquisa	--	11	f	37
11	ROLE_CAD_CARGO	Cargos	/cargosalario/cargo/list.action	5	t	361
5	ROLE_CAD_CONHECIMENTO	Conhecimentos	/captacao/conhecimento/list.action	4	t	361
10	ROLE_CAD_GRUPO	Grupos Ocupacionais	/cargosalario/grupoOcupacional/list.action	3	t	361
54	ROLE_REL_CARGO	Cargos	/cargosalario/cargo/prepareRelatorioCargo.action	21	t	361
2	ROLE_CAD_CANDIDATO	Candidatos	/captacao/candidato/list.action	2	t	357
3	ROLE_CAD_ETAPA	Etapas Seletivas	/captacao/etapaSeletiva/list.action	3	t	357
21	ROLE_MOV_SOLICITACAO	Solicitação de Pessoal	/captacao/solicitacao/list.action	11	t	357
50	ROLE_BD_SOLIDARIO	Banco de Dados Solidário	/captacao/candidato/prepareBusca.action?BDS=true	12	t	357
6	ROLE_CAD_BDS_EMPRESA	Empresas BDS	/captacao/empresaBds/list.action	4	t	357
57	ROLE_MOTIVO_SOLICITACAO	Motivos de Solicitação de Pessoal	/captacao/motivoSolicitacao/list.action	6	t	357
31	ROLE_REL_MATRIZ	Matriz de Qualificação	/desenvolvimento/turma/prepareImprimirMatriz.action	22	t	365
32	ROLE_REL_PLANO	Plano de Treinamento	/desenvolvimento/turma/prepareImprimirTurma.action	24	t	365
33	ROLE_REL_SEM_INDICACAO	Colaboradores sem Indicação de Trein.	/desenvolvimento/turma/prepareImprimirTurmaSemPlano.action	25	t	365
63	ROLE_MOV_TURMA	Turma	/desenvolvimento/turma/list.action	13	f	365
28	ROLE_MOV_QUESTIONARIO	Pesquisas	/pesquisa/pesquisa/list.action	11	t	353
55	ROLE_MOV_AVALIACAO	Avaliações	/pesquisa/avaliacao/list.action	11	t	382
8	ROLE_CAD_COLABORADOR	Colaboradores	/geral/colaborador/list.action	7	t	373
60	ROLE_CAD_MOTIVO_DEMISSAO	Motivos de Desligamento	/geral/motivoDemissao/list.action	5	t	373
62	ROLE_CAD_OCORRENCIA	Ocorrências	/geral/ocorrencia/list.action	6	t	373
56	ROLE_LIBERA_SOLICITACAO	Liberador de Solicitação	--	12	f	373
74	ROLE_FUNCAO	Funções	/sesmt/funcao/list.action	2	f	75
70	ROLE_REL_PROMOCAO	Promoções	/cargosalario/historicoColaborador/prepareRelatorioPromocoes.action	5	t	378
73	ROLE_IND	Motivos de Preenchimentos de Vagas	/indicador/duracaoPreenchimentoVaga/prepareMotivo.action	2	t	378
13	ROLE_CAD_PRIORIDADETREINAMENTO	Prioridades de Treinamento	/desenvolvimento/prioridadeTreinamento/list.action	2	t	365
72	ROLE_MOV_CURSO_DNT_GESTOR	Preenchimento da DNT	/desenvolvimento/dnt/list.action?gestor=true	14	t	365
43	ROLE_REL_DESENVOLVIMENTO_LISTA_PRESENCA	Lista de Freqüência	/desenvolvimento/relatorioPresenca/prepareRelatorio.action	26	t	365
22	ROLE_MOV_SOLICITACAO_CANDIDATO	Ver Candidatos da Solicitação	--	13	f	357
45	ROLE_MOV_SOLICITACAO_SELECAO	Recrutador(a)	--	14	f	357
4	ROLE_CAD_AREA	Áreas de Interesse	/geral/areaInteresse/list.action	5	t	357
46	ROLE_REL_SOLICITACAO	Solicitações Abertas	/captacao/solicitacao/prepareRelatorio.action	21	t	357
48	ROLE_REL_PROCESSO_SELETIVO	Processos Seletivos	/captacao/solicitacao/prepareRelatorioProcessoSeletivo.action	24	t	357
12	ROLE_CAD_BENEFICIO	Benefícios	/geral/beneficio/list.action	4	t	373
64	ROLE_MOV_CURSO_DNT	DNT	/desenvolvimento/dnt/list.action	12	t	365
16	ROLE_CAD_GASTO	Investimentos	/geral/gasto/list.action	3	t	373
15	ROLE_CAD_GRUPOGASTO	Grupos de Investimento	/geral/grupoGasto/list.action	2	t	373
61	ROLE_CERTIFICADO_CURSO	Certificados	/desenvolvimento/turma/prepareImprimirCertificado.action	28	t	365
71	ROLE_FREQUENCIA	Freqüência	/desenvolvimento/turma/prepareFrequencia.action	15	t	365
69	ROLE_IND	Duração para Preenchimento de Vagas	/indicador/duracaoPreenchimentoVaga/prepare.action	3	t	378
358	ROLE_R&S_CAD	Cadastros	#	1	t	357
359	ROLE_R&S_MOV	Movimentações	#	10	t	357
360	ROLE_R&S_REL	Relatórios	#	20	t	357
362	ROLE_C&S_CAD	Cadastros	#	1	t	361
363	ROLE_C&S_MOV	Movimentações	#	10	t	361
364	ROLE_C&S_REL	Relatórios	#	20	t	361
366	ROLE_T&D_CAD	Cadastros	#	1	t	365
367	ROLE_T&D_MOV	Movimentações	#	10	t	365
368	ROLE_T&D_REL	Relatórios	#	20	t	365
374	ROLE_COLAB_CAD	Cadastros	#	1	t	373
376	ROLE_COLAB_MOV	Movimentações	#	10	t	373
377	ROLE_COLAB_REL	Relatórios	#	20	t	373
381	ROLE_INDICADORES_REL	Relatórios	#	1	t	378
384	ROLE_AVALDESEMPENHO_MOV	Movimentações	#	10	t	382
355	ROLE_PES_MOV	Movimentações	#	10	t	353
47	ROLE_REL_PRODUTIVIDADE	Produtividade do Setor	/captacao/produtividade/prepareProdutividade.action	4	t	378
23	ROLE_MOV_CURSO	Cursos/Treinamentos	/desenvolvimento/curso/list.action	3	t	365
29	ROLE_MOV_GASTO_GASTOEMPRESA	Investimentos da Empresa	/geral/gastoEmpresa/list.action	11	t	373
36	ROLE_REL_GASTOEMPRESA	Investimentos da Empresa	/geral/gastoEmpresa/prepareImprimir.action	6	t	378
93	ROLE_REL_OCORRENCIA	Ocorrências	/geral/ocorrencia/prepareRelatorioOcorrencia.action	21	t	373
76	ROLE_CAD_AMBIENTE	Ambientes	/sesmt/ambiente/list.action	2	t	75
77	ROLE_CAD_TIPO_EPI	Tipos de EPI	/sesmt/tipoEPI/list.action	3	t	75
78	ROLE_CAD_EPI	EPI	/sesmt/epi/list.action	4	t	75
86	ROLE_CAD_EPC	EPC	/sesmt/epc/list.action	5	t	75
79	ROLE_CAD_RISCO	Riscos	/sesmt/risco/list.action	6	t	75
386	ROLE_SESMT	Movimentações	#	20	t	75
80	ROLE_LTCAT	LTCAT	/sesmt/LTCAT/list.action	12	t	75
81	ROLE_TAB_RUIDO	Levantamentos de Ruído	/sesmt/TabelaRuido/list.action	23	t	75
82	ROLE_TAB_TEMPERATURA	Levantamentos de Temperatura	/sesmt/tabelaTemperatura/list.action	24	t	75
95	ROLE_TAB_ILUMINAMENTO	Levantamentos de Iluminamento	/sesmt/tabelaIluminamento/list.action	25	t	75
83	ROLE_GHE	GHE	/sesmt/ghe/list.action	11	t	75
84	ROLE_PPRA	PPRA	/sesmt/ppra/list.action	13	t	75
92	ROLE_CAD_PCMSO	PCMSO	/sesmt/pcmso/list.action	15	t	75
87	ROLE_CAD_EXAME	Exames	/sesmt/exame/list.action	10	t	75
91	ROLE_CAD_CLINICA_AUTORIZADA	Clínicas Autorizadas	/sesmt/clinicaAutorizada/list.action	9	t	75
387	ROLE_SESMT	Relatórios	#	30	t	75
89	ROLE_CAD_ENGENHEIRO_TRABALHO	Engenheiros Responsáveis	/sesmt/engenheiroResponsavel/list.action	7	t	75
90	ROLE_CAD_MEDICO_COORDENADOR	Médicos Coordenadores	/sesmt/medicoCoordenador/list.action	8	t	75
385	ROLE_SESMT	Cadastros	#	1	t	75
388	ROLE_LTCAT	LTCAT	/sesmt/LTCAT/prepareRelatorioLtcat.action	31	t	75
389	ROLE_CAT_PCMSO	PCMSO	/sesmt/pcmso/prepareRelatorioPcmso.action	32	t	75
390	ROLE_UTI	Cadastros	#	1	t	37
391	ROLE_UTI	Movimentações	#	20	t	37
38	ROLE_UTI_SENHA	Alterar Senha	/acesso/usuario/prepareUpdateSenhaUsuario.action	21	t	37
39	ROLE_UTI_AUDITORIA	Auditoria	/security/auditoria/prepareList.action	23	t	37
41	ROLE_UTI_CONFIGURACAO	Configurações	/geral/parametrosDoSistema/prepareUpdate.action	22	t	37
44	ROLE_UTI_HISTORICO_VERSAO	Histórico de Versões	/geral/documentoVersao/list.action	26	t	37
58	ROLE_UTI_EMPRESA	Empresas	/geral/empresa/list.action	2	t	37
18	ROLE_CAD_PERFIL	Perfis	/acesso/perfil/list.action	6	t	37
19	ROLE_CAD_USUARIO	Usuários	/acesso/usuario/list.action	7	t	37
66	ROLE_SESMT_MUDANCA_FUNCAO	Mudança de Função	/sesmt/funcao/mudancaFuncaoFiltro.action	26	t	75
85	ROLE_PPP	PPP	/sesmt/ppp/list.action	33	t	75
393	ROLE_CAD_ESTABELECIMENTO	Estabelecimentos	/geral/estabelecimento/list.action	3	t	37
394	ROLE_CAD_BAIRRO	Bairros	/geral/bairro/list.action	9	t	37
395	ROLE_DISSIDIO	Reajuste Coletivo	/cargosalario/reajusteColaborador/prepareDissidio.action	13	t	361
396	ROLE_REL_AREAORGANIZACIONAL	Áreas Organizacionais	/geral/areaOrganizacionalRelatorio/formFiltro.action	26	t	361
397	ROLE_AREAFORMACAO	Áreas de Formação	/geral/areaFormacao/list.action	5	t	357
398	ROLE_REL_TURNOVER	Turnover	/indicador/indicadorTurnOver/prepare.action	5	t	378
400	ROLE_REL_MOTIVO_DEMISSAO	Motivos de Desligamento	/geral/motivoDemissao/prepareRelatorioMotivoDemissao.action	25	t	373
401	ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO	Respoder Pesquisa Por Outro Usuário	#	12	f	353
402	ROLE_MOV_QUESTIONARIO	Modelos de Entrevistas de Desligamento	/pesquisa/entrevista/list.action	6	t	373
403	ROLE_MOV_QUESTIONARIO	Resultados das Entrevistas	/pesquisa/questionario/prepareResultadoEntrevista.action	26	t	373
404	ROLE_CAD_INDICE	Índices	/cargosalario/indice/list.action	8	t	361
405	ROLE_MOV_TABELA	Análise de Tabela Salarial	/cargosalario/faixaSalarialHistorico/analiseTabelaSalarialFiltro.action	9	t	361
49	ROLE_MOV_SOLICITACAOREAJUSTE	Solicitação de Realinhamento de C&S	/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action	12	t	361
406	ROLE_REL_PROJECAO_SALARIAL	Projeção Salarial	/geral/colaborador/prepareProjecaoSalarialFiltro.action	27	t	361
26	ROLE_MOV_SIMULACAOREAJUSTE	Tabelas de Realinhamentos	/cargosalario/tabelaReajusteColaborador/list.action	11	t	361
35	ROLE_REL_SIMULACAOREAJUSTE	Realinhamentos	/cargosalario/reajusteRelatorio/formFiltro.action	24	t	361
407	ROLE_MOV_SOLICITACAO_REALINHAMENTO	Pode Solicitar Realinhamento	--	13	f	361
408	ROLE_UTI	Enviar Mensagem	/geral/usuarioMensagem/prepareUpdate.action	9	t	37
409	RECEBE_ALERTA_SETORPESSOAL	Recebe Mensagem do Fortes Pessoal		12	f	37
410	ROLE_VISUALIZAR_PENDENCIA_AC	Visualizar as pendências do AC		13	f	37
\.


--
-- Data for Name: parametrosdosistema; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY parametrosdosistema (id, mailnaoaptos, appurl, appcontext, appversao, emailsmtp, emailport, emailuser, emailpass, atualizadorpath, servidorremprot, diaslembretepesquisa, enviaremail, atualizadosucesso, perfilpadrao_id) FROM stdin;
1		http://localhost:8080/fortesrh	/fortesrh	1.0.5.0		25	franciscobarroso@grupofortes.com.br	\N	TESTE	TESTE	1	f	\N	1
\.


--
-- Data for Name: pcmso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pcmso (id, data, metodologia, cronograma, empresa_id) FROM stdin;
1	2008-01-01	\N	\N	1
\.


--
-- Data for Name: perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil (id, nome) FROM stdin;
1	Administrador
2	Usuário
\.


--
-- Data for Name: perfil_papel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil_papel (perfil_id, papeis_id) FROM stdin;
1	2
1	3
1	4
1	5
1	6
1	8
1	9
1	10
1	11
1	12
1	13
1	15
1	16
1	18
1	19
1	21
1	22
1	23
1	26
1	28
1	29
1	31
1	32
1	33
1	35
1	36
1	37
1	38
1	39
1	41
1	43
1	44
1	45
1	46
1	47
1	48
1	49
1	50
1	54
1	55
1	56
1	57
1	58
1	59
1	60
1	61
1	62
1	63
1	64
1	66
1	69
1	70
1	71
1	72
1	73
1	74
1	75
1	76
1	77
1	78
1	79
1	80
1	81
1	82
1	83
1	84
1	85
1	86
1	87
1	89
1	90
1	91
1	92
1	93
1	95
1	353
1	355
1	357
1	358
1	359
1	360
1	361
1	362
1	363
1	364
1	365
1	366
1	367
1	368
1	373
1	374
1	376
1	377
1	378
1	381
1	382
1	384
1	385
1	386
1	387
1	388
1	389
1	390
1	391
1	393
1	394
1	395
1	396
1	397
1	398
1	400
1	401
1	402
2	37
2	38
1	404
1	404
1	394
1	405
1	405
1	406
1	408
1	409
1	410
\.


--
-- Data for Name: pergunta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pergunta (id, ordem, texto, comentario, textocomentario, tipo, aspecto_id, questionario_id, notaminima, notamaxima) FROM stdin;
95	1	Você tem um bom lider?	t	Justifique sua resposta:	1	25	1	1	10
96	2	Como o lider organiza sua equipe?	f		3	25	1	1	10
97	3	Existe comunicação na sua equipe?	t	Justifique sua resposta:	1	26	1	1	10
98	4	De uma nota de 0 a 10 para sua equipe.	f		4	26	1	0	10
99	5	Fale sobre seu trabalho.	f		3	\N	1	1	10
155	1	Gostou da fortes?	f		1	\N	3	1	10
153	3	Descreva seu chefe.	f		3	41	3	1	10
154	5	De um nota para seu chefe.	f		4	41	3	1	10
156	4	Como era executada a liderança na sua equipe?	f		3	42	3	1	10
157	2	De uma nota para sua função	t	Justifique sua resposta:	4	\N	3	1	10
158	1	O que voce acha do seu trabalho?	f		3	43	53	1	10
159	2	O que fazer para melhorar o seu ambiente de trabalho?	f		3	43	53	1	10
\.


--
-- Data for Name: pesquisa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pesquisa (id, questionario_id) FROM stdin;
1	1
\.


--
-- Data for Name: ppra; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ppra (id, data, folhaapresentacao, elaborador, docbase, relatorioavalicao, registros, responsabilidade, empresa_id) FROM stdin;
1	2008-01-01	\N	\N	\N	\N	\N	\N	1
\.


--
-- Data for Name: prioridadetreinamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY prioridadetreinamento (id, descricao, sigla, numero) FROM stdin;
1	Minima	MI	1
2	Média	ME	2
3	Alta	AL	3
\.


--
-- Data for Name: questionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY questionario (id, titulo, cabecalho, datainicio, datafim, liberado, anonimo, aplicarporaspecto, tipo, empresa_id) FROM stdin;
2	Avaliação Invertida	Analisar os superiores ou pares em situa&ccedil;&otilde;es do dia a dia, para tra&ccedil;ar o perfil de lideran&ccedil;a de cada um.&nbsp;&nbsp;. /n Analise cada uma das 12 situa&ccedil;&otilde;es abaixo e escolha uma das quatro alternativas sugeridas que melhor corresponda &agrave; maneira como o(a) seu(sua) superior(a) ou Par agiria diante daquela situa&ccedil;&atilde;o. N&atilde;o deixe nenhuma sem responder.	2007-01-24	2007-01-31	f	f	f	2	1
3	Entrevista de Desligamento	Analisar os superiores ou pares em situa&ccedil;&otilde;es do dia a dia, para tra&ccedil;ar o perfil de lideran&ccedil;a de cada um.&nbsp;&nbsp;. /n Analise cada uma das 12 situa&ccedil;&otilde;es abaixo e escolha uma das quatro alternativas sugeridas que melhor corresponda &agrave; maneira como o(a) seu(sua) superior(a) ou Par agiria diante daquela situa&ccedil;&atilde;o. N&atilde;o deixe nenhuma sem responder.	\N	\N	f	f	f	1	1
1	Perfil de Liderança	Analisar os superiores ou pares em situa&ccedil;&otilde;es do dia a dia, para tra&ccedil;ar o perfil de lideran&ccedil;a de cada um.&nbsp;&nbsp;. /n Analise cada uma das 12 situa&ccedil;&otilde;es abaixo e escolha uma das quatro alternativas sugeridas que melhor corresponda &agrave; maneira como o(a) seu(sua) superior(a) ou Par agiria diante daquela situa&ccedil;&atilde;o. N&atilde;o deixe nenhuma sem responder.	2007-01-24	2007-01-31	t	f	f	2	1
53	Entrevista de Desligamento 2	\N	\N	\N	f	f	f	1	1
\.


--
-- Data for Name: reajustecolaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY reajustecolaborador (id, salarioatual, salarioproposto, colaborador_id, tabelareajustecolaborador_id, areaorganizacionalatual_id, areaorganizacionalproposta_id, funcaoatual_id, funcaoproposta_id, ambienteatual_id, ambienteproposto_id, estabelecimentoatual_id, estabelecimentoproposto_id, tiposalarioproposto, indiceproposto_id, tiposalarioatual, indiceatual_id, faixasalarialatual_id, faixasalarialproposta_id, quantidadeindiceatual, quantidadeindiceproposto) FROM stdin;
41	5555	0	1	49	2	2	\N	\N	\N	\N	1	1	2	1	3	\N	3	11	0	5
45	2500	2500	533	50	2	2	\N	\N	\N	\N	1	1	3	\N	3	\N	3	4	\N	0
47	4242.3299999999999	4242.3299999999999	534	50	2	2	\N	\N	\N	\N	1	1	3	\N	3	\N	11	11	0	0
\.


--
-- Data for Name: realizacaoexame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY realizacaoexame (id, data, observacao, resultado, natureza, empresa_id, colaborador_id, exame_id) FROM stdin;
1	2008-02-02	tudo normal	0	2	1	1	1
\.


--
-- Data for Name: resposta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY resposta (id, texto, ordem, pergunta_id) FROM stdin;
25	Sim	1	95
26	Não	2	95
27	Sim	1	97
28	Não	2	97
46	Sim	1	155
47	Não	2	155
\.


--
-- Data for Name: risco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY risco (id, descricao, danos, trajetoria, gruporisco, tabelarisco, empresa_id) FROM stdin;
1	Queimadura	Danos físicos	direto	01	2	1
2	Aspersão de produtos quimicos	Danos físicos causados por quimica	indireto	02	2	1
\.


--
-- Data for Name: risco_epi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY risco_epi (risco_id, epis_id) FROM stdin;
\.


--
-- Data for Name: riscoghe; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY riscoghe (id, fonte, trajetoria, propagacao, epieficaz, epceficaz, ghehistorico_id, risco_id) FROM stdin;
\.


--
-- Data for Name: solicitacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacao (id, data, dataencerramento, quantidade, vinculo, escolaridade, experiencia, remuneracao, idademinima, idademaxima, horario, sexo, infocomplementares, encerrada, liberada, suspensa, obssuspensao, motivosolicitacao_id, areaorganizacional_id, estabelecimento_id, solicitante_id, cidade_id, funcao_id, ambiente_id, empresa_id, faixasalarial_id) FROM stdin;
1	2008-11-06	\N	1	E	08		999	0	0		I		f	t	f	\N	1	1	1	1	946	\N	\N	1	1
\.


--
-- Data for Name: solicitacao_bairro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacao_bairro (solicitacao_id, bairros_id) FROM stdin;
\.


--
-- Data for Name: solicitacao_beneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacao_beneficio (solicitacao_id, beneficios_id) FROM stdin;
\.


--
-- Data for Name: solicitacaobds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacaobds (id, data, tipo, escolaridade, experiencia, sexo, idademinima, idademaxima, observacao, cargo_id, areaorganizacional_id) FROM stdin;
\.


--
-- Data for Name: solicitacaobds_empresabds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacaobds_empresabds (solicitacaobds_id, empresasbdss_id) FROM stdin;
\.


--
-- Data for Name: tabelailuminamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelailuminamento (id, data, nome, empresa_id) FROM stdin;
1	2008-01-01	Tabela 2008.1	1
\.


--
-- Data for Name: tabelailuminamentoitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelailuminamentoitem (id, valorlevantado, valorreferencia, tecnicautilizada, ghe_id, tabelailuminamento_id) FROM stdin;
\.


--
-- Data for Name: tabelareajustecolaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelareajustecolaborador (id, nome, data, observacao, aprovada, empresa_id) FROM stdin;
49	Tabela de 2008	2008-01-01		f	1
50	Tabela 2009	2009-01-01	Tabela de reajuste para o ano de 2009.	f	1
\.


--
-- Data for Name: tabelaruido; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelaruido (id, data, nome, empresa_id) FROM stdin;
1	2008-01-01	Tabela 2008.1	1
\.


--
-- Data for Name: tabelaruidoitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelaruidoitem (id, nps, npsatenuado, tecnicautilizada, tabelaruido_id, ghe_id) FROM stdin;
\.


--
-- Data for Name: tabelatemperatura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelatemperatura (id, nome, data, empresa_id) FROM stdin;
1	Tabela 2008.1	2008-01-01	1
\.


--
-- Data for Name: tabelatemperaturaitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelatemperaturaitem (id, ibutg, atividade, limitetolerancia, tecnicautilizada, tabelatemperatura_id, ghe_id) FROM stdin;
\.


--
-- Data for Name: tipoepi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tipoepi (id, nome, empresa_id) FROM stdin;
1	Bota de borracha	1
2	Protetor Auricular	1
\.


--
-- Data for Name: turma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY turma (id, descricao, cargahoraria, instrutor, custo, dataprevini, dataprevfim, datarealizaini, datarealizafim, empresa_id, curso_id) FROM stdin;
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario (id, nome, "login", senha, acessosistema) FROM stdin;
1	Fortes	fortes	MTIzNA==	t
2	Igo Coelho	igo	MTIzNA==	t
3	Moésio Medeiros	moesio	MTIzNA==	t
4	Israel Araújo	charles	MTIzNA==	t
5	Robertson Freitas	bob	MTIzNA==	t
\.


--
-- Data for Name: usuarioempresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuarioempresa (id, usuario_id, perfil_id, empresa_id) FROM stdin;
1	1	1	1
2	2	1	1
3	3	1	1
4	4	1	1
\.


--
-- Data for Name: usuariomensagem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuariomensagem (id, usuario_id, mensagem_id, empresa_id, lida) FROM stdin;
1	1	1	1	t
\.


--
-- Name: ambiente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ambiente
    ADD CONSTRAINT ambiente_pkey PRIMARY KEY (id);


--
-- Name: anexo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY anexo
    ADD CONSTRAINT anexo_pkey PRIMARY KEY (id);


--
-- Name: anuncio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY anuncio
    ADD CONSTRAINT anuncio_pkey PRIMARY KEY (id);


--
-- Name: anuncio_solicitacao_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY anuncio
    ADD CONSTRAINT anuncio_solicitacao_uk UNIQUE (solicitacao_id);


--
-- Name: areaformacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY areaformacao
    ADD CONSTRAINT areaformacao_pkey PRIMARY KEY (id);


--
-- Name: areainteresse_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY areainteresse
    ADD CONSTRAINT areainteresse_pkey PRIMARY KEY (id);


--
-- Name: areaorganizacional_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY areaorganizacional
    ADD CONSTRAINT areaorganizacional_pkey PRIMARY KEY (id);


--
-- Name: aspecto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY aspecto
    ADD CONSTRAINT aspecto_pkey PRIMARY KEY (id);


--
-- Name: auditoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY auditoria
    ADD CONSTRAINT auditoria_pkey PRIMARY KEY (id);


--
-- Name: avaliacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avaliacao
    ADD CONSTRAINT avaliacao_pkey PRIMARY KEY (id);


--
-- Name: bairro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bairro
    ADD CONSTRAINT bairro_pkey PRIMARY KEY (id);


--
-- Name: beneficio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY beneficio
    ADD CONSTRAINT beneficio_pkey PRIMARY KEY (id);


--
-- Name: candidato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY candidato
    ADD CONSTRAINT candidato_pkey PRIMARY KEY (id);


--
-- Name: candidatocurriculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY candidatocurriculo
    ADD CONSTRAINT candidatocurriculo_pkey PRIMARY KEY (id);


--
-- Name: candidatoidioma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY candidatoidioma
    ADD CONSTRAINT candidatoidioma_pkey PRIMARY KEY (id);


--
-- Name: candidatosolicitacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY candidatosolicitacao
    ADD CONSTRAINT candidatosolicitacao_pkey PRIMARY KEY (id);


--
-- Name: cargo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cargo
    ADD CONSTRAINT cargo_pkey PRIMARY KEY (id);


--
-- Name: cat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cat
    ADD CONSTRAINT cat_pkey PRIMARY KEY (id);


--
-- Name: cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);


--
-- Name: clinicaautorizada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clinicaautorizada
    ADD CONSTRAINT clinicaautorizada_pkey PRIMARY KEY (id);


--
-- Name: colaborador_candidato_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_candidato_uk UNIQUE (candidato_id);


--
-- Name: colaborador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_pkey PRIMARY KEY (id);


--
-- Name: colaborador_usuario_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_usuario_uk UNIQUE (usuario_id);


--
-- Name: colaboradoridioma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaboradoridioma
    ADD CONSTRAINT colaboradoridioma_pkey PRIMARY KEY (id);


--
-- Name: colaboradorocorrencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaboradorocorrencia
    ADD CONSTRAINT colaboradorocorrencia_pkey PRIMARY KEY (id);


--
-- Name: colaboradorpresenca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaboradorpresenca
    ADD CONSTRAINT colaboradorpresenca_pkey PRIMARY KEY (id);


--
-- Name: colaboradorquestionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_pkey PRIMARY KEY (id);


--
-- Name: colaboradorresposta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaboradorresposta
    ADD CONSTRAINT colaboradorresposta_pkey PRIMARY KEY (id);


--
-- Name: colaboradorturma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaboradorturma
    ADD CONSTRAINT colaboradorturma_pkey PRIMARY KEY (id);


--
-- Name: conhecimento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY conhecimento
    ADD CONSTRAINT conhecimento_pkey PRIMARY KEY (id);


--
-- Name: curso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY curso
    ADD CONSTRAINT curso_pkey PRIMARY KEY (id);


--
-- Name: dependente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dependente
    ADD CONSTRAINT dependente_pkey PRIMARY KEY (id);


--
-- Name: diaturma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY diaturma
    ADD CONSTRAINT diaturma_pkey PRIMARY KEY (id);


--
-- Name: dnt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dnt
    ADD CONSTRAINT dnt_pkey PRIMARY KEY (id);


--
-- Name: documentoanexo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documentoanexo
    ADD CONSTRAINT documentoanexo_pkey PRIMARY KEY (id);


--
-- Name: duracaopreenchimentovaga_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY duracaopreenchimentovaga
    ADD CONSTRAINT duracaopreenchimentovaga_pkey PRIMARY KEY (id);


--
-- Name: duracaopreenchimentovaga_solicitacao_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY duracaopreenchimentovaga
    ADD CONSTRAINT duracaopreenchimentovaga_solicitacao_uk UNIQUE (solicitacao_id);


--
-- Name: empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (id);


--
-- Name: empresabds_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empresabds
    ADD CONSTRAINT empresabds_pkey PRIMARY KEY (id);


--
-- Name: engenheiroresponsavel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY engenheiroresponsavel
    ADD CONSTRAINT engenheiroresponsavel_pkey PRIMARY KEY (id);


--
-- Name: epc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY epc
    ADD CONSTRAINT epc_pkey PRIMARY KEY (id);


--
-- Name: epi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY epi
    ADD CONSTRAINT epi_pkey PRIMARY KEY (id);


--
-- Name: epihistorico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY epihistorico
    ADD CONSTRAINT epihistorico_pkey PRIMARY KEY (id);


--
-- Name: estabelecimento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estabelecimento
    ADD CONSTRAINT estabelecimento_pkey PRIMARY KEY (id);


--
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id);


--
-- Name: etapaseletiva_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY etapaseletiva
    ADD CONSTRAINT etapaseletiva_pkey PRIMARY KEY (id);


--
-- Name: exame_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY exame
    ADD CONSTRAINT exame_pkey PRIMARY KEY (id);


--
-- Name: experiencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY experiencia
    ADD CONSTRAINT experiencia_pkey PRIMARY KEY (id);


--
-- Name: faixasalarial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY faixasalarial
    ADD CONSTRAINT faixasalarial_pkey PRIMARY KEY (id);


--
-- Name: faixasalarialhistorico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY faixasalarialhistorico
    ADD CONSTRAINT faixasalarialhistorico_pkey PRIMARY KEY (id);


--
-- Name: formacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY formacao
    ADD CONSTRAINT formacao_pkey PRIMARY KEY (id);


--
-- Name: funcao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY funcao
    ADD CONSTRAINT funcao_pkey PRIMARY KEY (id);


--
-- Name: gasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gasto
    ADD CONSTRAINT gasto_pkey PRIMARY KEY (id);


--
-- Name: gastoempresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gastoempresa
    ADD CONSTRAINT gastoempresa_pkey PRIMARY KEY (id);


--
-- Name: gastoempresaitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gastoempresaitem
    ADD CONSTRAINT gastoempresaitem_pkey PRIMARY KEY (id);


--
-- Name: ghe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ghe
    ADD CONSTRAINT ghe_pkey PRIMARY KEY (id);


--
-- Name: ghehistorico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ghehistorico
    ADD CONSTRAINT ghehistorico_pkey PRIMARY KEY (id);


--
-- Name: grupogasto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grupogasto
    ADD CONSTRAINT grupogasto_pkey PRIMARY KEY (id);


--
-- Name: grupoocupacional_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grupoocupacional
    ADD CONSTRAINT grupoocupacional_pkey PRIMARY KEY (id);


--
-- Name: historicoambiente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historicoambiente
    ADD CONSTRAINT historicoambiente_pkey PRIMARY KEY (id);


--
-- Name: historicobeneficio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historicobeneficio
    ADD CONSTRAINT historicobeneficio_pkey PRIMARY KEY (id);


--
-- Name: historicocandidato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historicocandidato
    ADD CONSTRAINT historicocandidato_pkey PRIMARY KEY (id);


--
-- Name: historicocolaborador_data_colaborador_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_data_colaborador_uk UNIQUE (data, colaborador_id);


--
-- Name: historicocolaborador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_pkey PRIMARY KEY (id);


--
-- Name: historicocolaboradorbeneficio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historicocolaboradorbeneficio
    ADD CONSTRAINT historicocolaboradorbeneficio_pkey PRIMARY KEY (id);


--
-- Name: historicofuncao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY historicofuncao
    ADD CONSTRAINT historicofuncao_pkey PRIMARY KEY (id);


--
-- Name: idioma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY idioma
    ADD CONSTRAINT idioma_pkey PRIMARY KEY (id);


--
-- Name: indice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indice
    ADD CONSTRAINT indice_pkey PRIMARY KEY (id);


--
-- Name: indicehistorico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY indicehistorico
    ADD CONSTRAINT indicehistorico_pkey PRIMARY KEY (id);


--
-- Name: ltcat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ltcat
    ADD CONSTRAINT ltcat_pkey PRIMARY KEY (id);


--
-- Name: medicocoordenador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY medicocoordenador
    ADD CONSTRAINT medicocoordenador_pkey PRIMARY KEY (id);


--
-- Name: mensagem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT mensagem_pkey PRIMARY KEY (id);


--
-- Name: motivodemissao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY motivodemissao
    ADD CONSTRAINT motivodemissao_pkey PRIMARY KEY (id);


--
-- Name: motivosolicitacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY motivosolicitacao
    ADD CONSTRAINT motivosolicitacao_pkey PRIMARY KEY (id);


--
-- Name: ocorrencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ocorrencia
    ADD CONSTRAINT ocorrencia_pkey PRIMARY KEY (id);


--
-- Name: papel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY papel
    ADD CONSTRAINT papel_pkey PRIMARY KEY (id);


--
-- Name: parametrosdosistema_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parametrosdosistema
    ADD CONSTRAINT parametrosdosistema_pkey PRIMARY KEY (id);


--
-- Name: pcmso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pcmso
    ADD CONSTRAINT pcmso_pkey PRIMARY KEY (id);


--
-- Name: perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil
    ADD CONSTRAINT perfil_pkey PRIMARY KEY (id);


--
-- Name: pergunta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pergunta
    ADD CONSTRAINT pergunta_pkey PRIMARY KEY (id);


--
-- Name: pesquisa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pesquisa
    ADD CONSTRAINT pesquisa_pkey PRIMARY KEY (id);


--
-- Name: ppra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ppra
    ADD CONSTRAINT ppra_pkey PRIMARY KEY (id);


--
-- Name: prioridadetreinamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY prioridadetreinamento
    ADD CONSTRAINT prioridadetreinamento_pkey PRIMARY KEY (id);


--
-- Name: questionario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionario
    ADD CONSTRAINT questionario_pkey PRIMARY KEY (id);


--
-- Name: reajustecolaborador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_pkey PRIMARY KEY (id);


--
-- Name: realizacaoexame_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY realizacaoexame
    ADD CONSTRAINT realizacaoexame_pkey PRIMARY KEY (id);


--
-- Name: resposta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT resposta_pkey PRIMARY KEY (id);


--
-- Name: risco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY risco
    ADD CONSTRAINT risco_pkey PRIMARY KEY (id);


--
-- Name: riscoghe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY riscoghe
    ADD CONSTRAINT riscoghe_pkey PRIMARY KEY (id);


--
-- Name: solicitacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_pkey PRIMARY KEY (id);


--
-- Name: solicitacaobds_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY solicitacaobds
    ADD CONSTRAINT solicitacaobds_pkey PRIMARY KEY (id);


--
-- Name: tabelailuminamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelailuminamento
    ADD CONSTRAINT tabelailuminamento_pkey PRIMARY KEY (id);


--
-- Name: tabelailuminamentoitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelailuminamentoitem
    ADD CONSTRAINT tabelailuminamentoitem_pkey PRIMARY KEY (id);


--
-- Name: tabelailuminamentoitem_tabelailuminamento_ghe_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelailuminamentoitem
    ADD CONSTRAINT tabelailuminamentoitem_tabelailuminamento_ghe_uk UNIQUE (tabelailuminamento_id, ghe_id);


--
-- Name: tabelareajustecolaborador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelareajustecolaborador
    ADD CONSTRAINT tabelareajustecolaborador_pkey PRIMARY KEY (id);


--
-- Name: tabelaruido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelaruido
    ADD CONSTRAINT tabelaruido_pkey PRIMARY KEY (id);


--
-- Name: tabelaruidoitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelaruidoitem
    ADD CONSTRAINT tabelaruidoitem_pkey PRIMARY KEY (id);


--
-- Name: tabelaruidoitem_tabelaruido_ghe_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelaruidoitem
    ADD CONSTRAINT tabelaruidoitem_tabelaruido_ghe_uk UNIQUE (tabelaruido_id, ghe_id);


--
-- Name: tabelatemperatura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelatemperatura
    ADD CONSTRAINT tabelatemperatura_pkey PRIMARY KEY (id);


--
-- Name: tabelatemperaturaitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelatemperaturaitem
    ADD CONSTRAINT tabelatemperaturaitem_pkey PRIMARY KEY (id);


--
-- Name: tabelatemperaturaitem_tabelatemperatura_ghe_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelatemperaturaitem
    ADD CONSTRAINT tabelatemperaturaitem_tabelatemperatura_ghe_uk UNIQUE (tabelatemperatura_id, ghe_id);


--
-- Name: tipoepi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipoepi
    ADD CONSTRAINT tipoepi_pkey PRIMARY KEY (id);


--
-- Name: turma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY turma
    ADD CONSTRAINT turma_pkey PRIMARY KEY (id);


--
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: usuarioempresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuarioempresa
    ADD CONSTRAINT usuarioempresa_pkey PRIMARY KEY (id);


--
-- Name: usuariomensagem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuariomensagem
    ADD CONSTRAINT usuariomensagem_pkey PRIMARY KEY (id);


--
-- Name: ambiente_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ambiente
    ADD CONSTRAINT ambiente_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: ambienteareaorganizacional_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ambiente_areaorganizacional
    ADD CONSTRAINT ambienteareaorganizacional_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);


--
-- Name: ambienteareaorganizacional_areasorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ambiente_areaorganizacional
    ADD CONSTRAINT ambienteareaorganizacional_areasorganizacional_fk FOREIGN KEY (areasorganizacionais_id) REFERENCES areaorganizacional(id);


--
-- Name: anuncio_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY anuncio
    ADD CONSTRAINT anuncio_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: areainteresse_areaorganizacional_areasinteresse_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY areainteresse_areaorganizacional
    ADD CONSTRAINT areainteresse_areaorganizacional_areasinteresse_fk FOREIGN KEY (areasinteresse_id) REFERENCES areainteresse(id);


--
-- Name: areainteresse_areaorganizacional_areasorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY areainteresse_areaorganizacional
    ADD CONSTRAINT areainteresse_areaorganizacional_areasorganizacional_fk FOREIGN KEY (areasorganizacionais_id) REFERENCES areaorganizacional(id);


--
-- Name: areainteresse_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY areainteresse
    ADD CONSTRAINT areainteresse_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: areaorganizacional_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY areaorganizacional
    ADD CONSTRAINT areaorganizacional_areaorganizacional_fk FOREIGN KEY (areamae_id) REFERENCES areaorganizacional(id);


--
-- Name: areaorganizacional_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY areaorganizacional
    ADD CONSTRAINT areaorganizacional_colaborador_fk FOREIGN KEY (responsavel_id) REFERENCES colaborador(id);


--
-- Name: areaorganizacional_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY areaorganizacional
    ADD CONSTRAINT areaorganizacional_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: aspecto_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aspecto
    ADD CONSTRAINT aspecto_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: auditoria_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY auditoria
    ADD CONSTRAINT auditoria_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: auditoria_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY auditoria
    ADD CONSTRAINT auditoria_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: avaliacao_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacao
    ADD CONSTRAINT avaliacao_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: bairro_cidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bairro
    ADD CONSTRAINT bairro_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- Name: beneficio_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY beneficio
    ADD CONSTRAINT beneficio_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: candidato_areainteresse_areainteresse_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato_areainteresse
    ADD CONSTRAINT candidato_areainteresse_areainteresse_fk FOREIGN KEY (areasinteresse_id) REFERENCES areainteresse(id);


--
-- Name: candidato_areainteresse_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato_areainteresse
    ADD CONSTRAINT candidato_areainteresse_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: candidato_cargo_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato_cargo
    ADD CONSTRAINT candidato_cargo_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: candidato_cargo_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato_cargo
    ADD CONSTRAINT candidato_cargo_cargo_fk FOREIGN KEY (cargos_id) REFERENCES cargo(id);


--
-- Name: candidato_cidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato
    ADD CONSTRAINT candidato_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- Name: candidato_conhecimento_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato_conhecimento
    ADD CONSTRAINT candidato_conhecimento_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: candidato_conhecimento_conhecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato_conhecimento
    ADD CONSTRAINT candidato_conhecimento_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento(id);


--
-- Name: candidato_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato
    ADD CONSTRAINT candidato_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: candidato_estado_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidato
    ADD CONSTRAINT candidato_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);


--
-- Name: candidatocurriculo_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidatocurriculo
    ADD CONSTRAINT candidatocurriculo_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: candidatoidioma_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidatoidioma
    ADD CONSTRAINT candidatoidioma_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: candidatoidioma_idioma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidatoidioma
    ADD CONSTRAINT candidatoidioma_idioma_fk FOREIGN KEY (idioma_id) REFERENCES idioma(id);


--
-- Name: candidatosolicitacao_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidatosolicitacao
    ADD CONSTRAINT candidatosolicitacao_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: candidatosolicitacao_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidatosolicitacao
    ADD CONSTRAINT candidatosolicitacao_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: cargo_areaformacao_areaformacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_areaformacao
    ADD CONSTRAINT cargo_areaformacao_areaformacao_fk FOREIGN KEY (areaformacaos_id) REFERENCES areaformacao(id);


--
-- Name: cargo_areaformacao_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_areaformacao
    ADD CONSTRAINT cargo_areaformacao_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: cargo_areaorganizacional_areasorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_areaorganizacional
    ADD CONSTRAINT cargo_areaorganizacional_areasorganizacional_fk FOREIGN KEY (areasorganizacionais_id) REFERENCES areaorganizacional(id);


--
-- Name: cargo_areaorganizacional_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_areaorganizacional
    ADD CONSTRAINT cargo_areaorganizacional_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: cargo_conhecimento_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_conhecimento
    ADD CONSTRAINT cargo_conhecimento_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: cargo_conhecimento_conhecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_conhecimento
    ADD CONSTRAINT cargo_conhecimento_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento(id);


--
-- Name: cargo_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo
    ADD CONSTRAINT cargo_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: cargo_grupoocupacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo
    ADD CONSTRAINT cargo_grupoocupacional_fk FOREIGN KEY (grupoocupacional_id) REFERENCES grupoocupacional(id);


--
-- Name: cat_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cat
    ADD CONSTRAINT cat_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: cidade_estado_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT cidade_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);


--
-- Name: clinicaautorizada_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY clinicaautorizada
    ADD CONSTRAINT clinicaautorizada_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: colaborador_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: colaborador_cidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- Name: colaborador_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: colaborador_estado_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);


--
-- Name: colaborador_motivodemissao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_motivodemissao_fk FOREIGN KEY (motivodemissao_id) REFERENCES motivodemissao(id);


--
-- Name: colaborador_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: colaboradoridioma_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradoridioma
    ADD CONSTRAINT colaboradoridioma_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: colaboradoridioma_idioma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradoridioma
    ADD CONSTRAINT colaboradoridioma_idioma_fk FOREIGN KEY (idioma_id) REFERENCES idioma(id);


--
-- Name: colaboradorocorrencia_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorocorrencia
    ADD CONSTRAINT colaboradorocorrencia_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: colaboradorocorrencia_ocorrencia_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorocorrencia
    ADD CONSTRAINT colaboradorocorrencia_ocorrencia_fk FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencia(id);


--
-- Name: colaboradorpresenca_colaboradorturma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorpresenca
    ADD CONSTRAINT colaboradorpresenca_colaboradorturma_fk FOREIGN KEY (colaboradorturma_id) REFERENCES colaboradorturma(id);


--
-- Name: colaboradorpresenca_diaturma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorpresenca
    ADD CONSTRAINT colaboradorpresenca_diaturma_fk FOREIGN KEY (diaturma_id) REFERENCES diaturma(id);


--
-- Name: colaboradorquestionario_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: colaboradorquestionario_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: colaboradorresposta_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorresposta
    ADD CONSTRAINT colaboradorresposta_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);


--
-- Name: colaboradorresposta_colaboradorquestionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorresposta
    ADD CONSTRAINT colaboradorresposta_colaboradorquestionario_fk FOREIGN KEY (colaboradorquestionario_id) REFERENCES colaboradorquestionario(id);


--
-- Name: colaboradorresposta_pergunta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorresposta
    ADD CONSTRAINT colaboradorresposta_pergunta_fk FOREIGN KEY (pergunta_id) REFERENCES pergunta(id);


--
-- Name: colaboradorresposta_resposta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorresposta
    ADD CONSTRAINT colaboradorresposta_resposta_fk FOREIGN KEY (resposta_id) REFERENCES resposta(id);


--
-- Name: colaboradorturma_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorturma
    ADD CONSTRAINT colaboradorturma_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: colaboradorturma_curso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorturma
    ADD CONSTRAINT colaboradorturma_curso_fk FOREIGN KEY (curso_id) REFERENCES curso(id);


--
-- Name: colaboradorturma_dnt_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorturma
    ADD CONSTRAINT colaboradorturma_dnt_fk FOREIGN KEY (dnt_id) REFERENCES dnt(id);


--
-- Name: colaboradorturma_prioridadetreinamento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorturma
    ADD CONSTRAINT colaboradorturma_prioridadetreinamento_fk FOREIGN KEY (prioridadetreinamento_id) REFERENCES prioridadetreinamento(id);


--
-- Name: colaboradorturma_turma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorturma
    ADD CONSTRAINT colaboradorturma_turma_fk FOREIGN KEY (turma_id) REFERENCES turma(id);


--
-- Name: conhecimento_areaorganizacional_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conhecimento_areaorganizacional
    ADD CONSTRAINT conhecimento_areaorganizacional_areaorganizacional_fk FOREIGN KEY (areaorganizacionals_id) REFERENCES areaorganizacional(id);


--
-- Name: conhecimento_areaorganizacional_conhecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conhecimento_areaorganizacional
    ADD CONSTRAINT conhecimento_areaorganizacional_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento(id);


--
-- Name: conhecimento_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY conhecimento
    ADD CONSTRAINT conhecimento_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: curso_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY curso
    ADD CONSTRAINT curso_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: dependente_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dependente
    ADD CONSTRAINT dependente_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: diaturma_turma; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY diaturma
    ADD CONSTRAINT diaturma_turma FOREIGN KEY (turma_id) REFERENCES turma(id);


--
-- Name: dnt_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY dnt
    ADD CONSTRAINT dnt_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: documentoanexo_etapaseletiva_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documentoanexo
    ADD CONSTRAINT documentoanexo_etapaseletiva_fk FOREIGN KEY (etapaseletiva_id) REFERENCES etapaseletiva(id);


--
-- Name: duracaopreenchimentovaga_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY duracaopreenchimentovaga
    ADD CONSTRAINT duracaopreenchimentovaga_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);


--
-- Name: duracaopreenchimentovaga_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY duracaopreenchimentovaga
    ADD CONSTRAINT duracaopreenchimentovaga_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: duracaopreenchimentovaga_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY duracaopreenchimentovaga
    ADD CONSTRAINT duracaopreenchimentovaga_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: empresabds_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empresabds
    ADD CONSTRAINT empresabds_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: engenheiroresponsavel_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY engenheiroresponsavel
    ADD CONSTRAINT engenheiroresponsavel_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: entrevista_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY entrevista
    ADD CONSTRAINT entrevista_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: epc_ambiente_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY epc_ambiente
    ADD CONSTRAINT epc_ambiente_ambiente_fk FOREIGN KEY (ambientes_id) REFERENCES ambiente(id);


--
-- Name: epc_ambiente_epc_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY epc_ambiente
    ADD CONSTRAINT epc_ambiente_epc_fk FOREIGN KEY (epc_id) REFERENCES epc(id);


--
-- Name: epc_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY epc
    ADD CONSTRAINT epc_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: epi_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY epi
    ADD CONSTRAINT epi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: epi_tipoepi_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY epi
    ADD CONSTRAINT epi_tipoepi_fk FOREIGN KEY (tipoepi_id) REFERENCES tipoepi(id);


--
-- Name: epihistorico_epi_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY epihistorico
    ADD CONSTRAINT epihistorico_epi_fk FOREIGN KEY (epi_id) REFERENCES epi(id);


--
-- Name: estabelecimento_cidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estabelecimento
    ADD CONSTRAINT estabelecimento_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- Name: estabelecimento_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estabelecimento
    ADD CONSTRAINT estabelecimento_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: estabelecimento_estado_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estabelecimento
    ADD CONSTRAINT estabelecimento_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);


--
-- Name: etapaseletiva_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY etapaseletiva
    ADD CONSTRAINT etapaseletiva_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: exame_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY exame
    ADD CONSTRAINT exame_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: experiencia_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY experiencia
    ADD CONSTRAINT experiencia_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: experiencia_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY experiencia
    ADD CONSTRAINT experiencia_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: experiencia_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY experiencia
    ADD CONSTRAINT experiencia_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: faixasalarial_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faixasalarial
    ADD CONSTRAINT faixasalarial_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: faixasalarialhistorico_faixasalarial_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faixasalarialhistorico
    ADD CONSTRAINT faixasalarialhistorico_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);


--
-- Name: faixasalarialhistorico_indice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faixasalarialhistorico
    ADD CONSTRAINT faixasalarialhistorico_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);


--
-- Name: formacao_areaformacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formacao
    ADD CONSTRAINT formacao_areaformacao_fk FOREIGN KEY (areaformacao_id) REFERENCES areaformacao(id);


--
-- Name: formacao_candidato; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formacao
    ADD CONSTRAINT formacao_candidato FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: formacao_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY formacao
    ADD CONSTRAINT formacao_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: funcao_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY funcao
    ADD CONSTRAINT funcao_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: gasto_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasto
    ADD CONSTRAINT gasto_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: gasto_grupogasto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gasto
    ADD CONSTRAINT gasto_grupogasto_fk FOREIGN KEY (grupogasto_id) REFERENCES grupogasto(id);


--
-- Name: gastoempresa_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gastoempresa
    ADD CONSTRAINT gastoempresa_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: gastoempresa_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gastoempresa
    ADD CONSTRAINT gastoempresa_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: gastoempresaitem_gasto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gastoempresaitem
    ADD CONSTRAINT gastoempresaitem_gasto_fk FOREIGN KEY (gasto_id) REFERENCES gasto(id);


--
-- Name: gastoempresaitem_gastoempresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY gastoempresaitem
    ADD CONSTRAINT gastoempresaitem_gastoempresa_fk FOREIGN KEY (gastoempresa_id) REFERENCES gastoempresa(id);


--
-- Name: ghe_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghe
    ADD CONSTRAINT ghe_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: ghehistorico_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghehistorico
    ADD CONSTRAINT ghehistorico_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);


--
-- Name: ghehistorico_funcao_funcao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghehistorico_funcao
    ADD CONSTRAINT ghehistorico_funcao_funcao_fk FOREIGN KEY (funcoes_id) REFERENCES funcao(id);


--
-- Name: ghehistorico_funcao_ghehistorico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghehistorico_funcao
    ADD CONSTRAINT ghehistorico_funcao_ghehistorico_fk FOREIGN KEY (ghehistorico_id) REFERENCES ghehistorico(id);


--
-- Name: ghehistorico_ghe_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ghehistorico
    ADD CONSTRAINT ghehistorico_ghe_fk FOREIGN KEY (ghe_id) REFERENCES ghe(id);


--
-- Name: grupogasto_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY grupogasto
    ADD CONSTRAINT grupogasto_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: grupoocupacional_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY grupoocupacional
    ADD CONSTRAINT grupoocupacional_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: historicoambiente_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicoambiente
    ADD CONSTRAINT historicoambiente_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);


--
-- Name: historicobeneficio_beneficio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicobeneficio
    ADD CONSTRAINT historicobeneficio_beneficio_fk FOREIGN KEY (beneficio_id) REFERENCES beneficio(id);


--
-- Name: historicocandidato_candidatosolicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocandidato
    ADD CONSTRAINT historicocandidato_candidatosolicitacao_fk FOREIGN KEY (candidatosolicitacao_id) REFERENCES candidatosolicitacao(id);


--
-- Name: historicocandidato_etapaseletiva_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocandidato
    ADD CONSTRAINT historicocandidato_etapaseletiva_fk FOREIGN KEY (etapaseletiva_id) REFERENCES etapaseletiva(id);


--
-- Name: historicocolaborador_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);


--
-- Name: historicocolaborador_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);


--
-- Name: historicocolaborador_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: historicocolaborador_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);


--
-- Name: historicocolaborador_faixasalarial_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);


--
-- Name: historicocolaborador_funcao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_funcao_fk FOREIGN KEY (funcao_id) REFERENCES funcao(id);


--
-- Name: historicocolaborador_historicocolaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_historicocolaborador_fk FOREIGN KEY (historicoanterior_id) REFERENCES historicocolaborador(id);


--
-- Name: historicocolaborador_indice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);


--
-- Name: historicocolaborador_reajustecolaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaborador
    ADD CONSTRAINT historicocolaborador_reajustecolaborador_fk FOREIGN KEY (reajustecolaborador_id) REFERENCES reajustecolaborador(id);


--
-- Name: historicocolaboradorbeneficio_beneficio_beneficio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaboradorbeneficio_beneficio
    ADD CONSTRAINT historicocolaboradorbeneficio_beneficio_beneficio_fk FOREIGN KEY (beneficios_id) REFERENCES beneficio(id);


--
-- Name: historicocolaboradorbeneficio_beneficio_hcbeneficio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaboradorbeneficio_beneficio
    ADD CONSTRAINT historicocolaboradorbeneficio_beneficio_hcbeneficio_fk FOREIGN KEY (historicocolaboradorbeneficio_id) REFERENCES historicocolaboradorbeneficio(id);


--
-- Name: historicocolaboradorbeneficio_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicocolaboradorbeneficio
    ADD CONSTRAINT historicocolaboradorbeneficio_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: historicofuncao_exame_exame_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicofuncao_exame
    ADD CONSTRAINT historicofuncao_exame_exame_fk FOREIGN KEY (exames_id) REFERENCES exame(id);


--
-- Name: historicofuncao_exame_historicofuncao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicofuncao_exame
    ADD CONSTRAINT historicofuncao_exame_historicofuncao_fk FOREIGN KEY (historicofuncao_id) REFERENCES historicofuncao(id);


--
-- Name: historicofuncao_funcao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicofuncao
    ADD CONSTRAINT historicofuncao_funcao_fk FOREIGN KEY (funcao_id) REFERENCES funcao(id);


--
-- Name: indicehistorico_indice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indicehistorico
    ADD CONSTRAINT indicehistorico_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);


--
-- Name: ltcat_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ltcat
    ADD CONSTRAINT ltcat_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: ltcat_tabelaruido_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ltcat
    ADD CONSTRAINT ltcat_tabelaruido_fk FOREIGN KEY (tabelaruido_id) REFERENCES tabelaruido(id);


--
-- Name: ltcat_tabelatemperatura_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ltcat
    ADD CONSTRAINT ltcat_tabelatemperatura_fk FOREIGN KEY (tabelatemperatura_id) REFERENCES tabelatemperatura(id);


--
-- Name: medicocoordenador_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY medicocoordenador
    ADD CONSTRAINT medicocoordenador_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: motivodemissao_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY motivodemissao
    ADD CONSTRAINT motivodemissao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: ocorrencia_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ocorrencia
    ADD CONSTRAINT ocorrencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: papel_papel_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY papel
    ADD CONSTRAINT papel_papel_fk FOREIGN KEY (papelmae_id) REFERENCES papel(id);


--
-- Name: parametrosdosistema_perfil_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parametrosdosistema
    ADD CONSTRAINT parametrosdosistema_perfil_fk FOREIGN KEY (perfilpadrao_id) REFERENCES perfil(id);


--
-- Name: pcmso_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pcmso
    ADD CONSTRAINT pcmso_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: perfil_papel_papel_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_papel
    ADD CONSTRAINT perfil_papel_papel_fk FOREIGN KEY (papeis_id) REFERENCES papel(id);


--
-- Name: perfil_papel_perfil_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_papel
    ADD CONSTRAINT perfil_papel_perfil_fk FOREIGN KEY (perfil_id) REFERENCES perfil(id);


--
-- Name: pergunta_aspecto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pergunta
    ADD CONSTRAINT pergunta_aspecto_fk FOREIGN KEY (aspecto_id) REFERENCES aspecto(id);


--
-- Name: pergunta_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pergunta
    ADD CONSTRAINT pergunta_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: pesquisa_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pesquisa
    ADD CONSTRAINT pesquisa_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: ppra_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ppra
    ADD CONSTRAINT ppra_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: questionario_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY questionario
    ADD CONSTRAINT questionario_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: reajustecolaborador_ambiente_atual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_ambiente_atual_fk FOREIGN KEY (ambienteatual_id) REFERENCES ambiente(id);


--
-- Name: reajustecolaborador_ambiente_proposto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_ambiente_proposto_fk FOREIGN KEY (ambienteproposto_id) REFERENCES ambiente(id);


--
-- Name: reajustecolaborador_areaorganizacional_atual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_areaorganizacional_atual_fk FOREIGN KEY (areaorganizacionalatual_id) REFERENCES areaorganizacional(id);


--
-- Name: reajustecolaborador_areaorganizacional_proposta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_areaorganizacional_proposta_fk FOREIGN KEY (areaorganizacionalproposta_id) REFERENCES areaorganizacional(id);


--
-- Name: reajustecolaborador_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: reajustecolaborador_estabelecimento_atual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_estabelecimento_atual_fk FOREIGN KEY (estabelecimentoatual_id) REFERENCES estabelecimento(id);


--
-- Name: reajustecolaborador_estabelecimento_proposto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_estabelecimento_proposto_fk FOREIGN KEY (estabelecimentoproposto_id) REFERENCES estabelecimento(id);


--
-- Name: reajustecolaborador_faixasalarialatual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_faixasalarialatual_fk FOREIGN KEY (faixasalarialatual_id) REFERENCES faixasalarial(id);


--
-- Name: reajustecolaborador_faixasalarialproposta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_faixasalarialproposta_fk FOREIGN KEY (faixasalarialproposta_id) REFERENCES faixasalarial(id);


--
-- Name: reajustecolaborador_funcao_atual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_funcao_atual_fk FOREIGN KEY (funcaoatual_id) REFERENCES funcao(id);


--
-- Name: reajustecolaborador_funcao_proposta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_funcao_proposta_fk FOREIGN KEY (funcaoproposta_id) REFERENCES funcao(id);


--
-- Name: reajustecolaborador_indiceatual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_indiceatual_fk FOREIGN KEY (indiceatual_id) REFERENCES indice(id);


--
-- Name: reajustecolaborador_indiceproposto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_indiceproposto_fk FOREIGN KEY (indiceproposto_id) REFERENCES indice(id);


--
-- Name: reajustecolaborador_tabelareajustecolaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY reajustecolaborador
    ADD CONSTRAINT reajustecolaborador_tabelareajustecolaborador_fk FOREIGN KEY (tabelareajustecolaborador_id) REFERENCES tabelareajustecolaborador(id);


--
-- Name: realizacaoexame_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY realizacaoexame
    ADD CONSTRAINT realizacaoexame_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: realizacaoexame_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY realizacaoexame
    ADD CONSTRAINT realizacaoexame_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: realizacaoexame_exame_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY realizacaoexame
    ADD CONSTRAINT realizacaoexame_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);


--
-- Name: resposta_pergunta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resposta
    ADD CONSTRAINT resposta_pergunta_fk FOREIGN KEY (pergunta_id) REFERENCES pergunta(id);


--
-- Name: risco_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY risco
    ADD CONSTRAINT risco_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: risco_epi_epi_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY risco_epi
    ADD CONSTRAINT risco_epi_epi_fk FOREIGN KEY (epis_id) REFERENCES epi(id);


--
-- Name: risco_epi_risco_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY risco_epi
    ADD CONSTRAINT risco_epi_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);


--
-- Name: riscoghe_ghehistorico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY riscoghe
    ADD CONSTRAINT riscoghe_ghehistorico_fk FOREIGN KEY (ghehistorico_id) REFERENCES ghehistorico(id);


--
-- Name: riscoghe_risco_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY riscoghe
    ADD CONSTRAINT riscoghe_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);


--
-- Name: solicitacao_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);


--
-- Name: solicitacao_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);


--
-- Name: solicitacao_bairro_bairro_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao_bairro
    ADD CONSTRAINT solicitacao_bairro_bairro_fk FOREIGN KEY (bairros_id) REFERENCES bairro(id);


--
-- Name: solicitacao_bairro_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao_bairro
    ADD CONSTRAINT solicitacao_bairro_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: solicitacao_beneficio_beneficio_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao_beneficio
    ADD CONSTRAINT solicitacao_beneficio_beneficio_fk FOREIGN KEY (beneficios_id) REFERENCES beneficio(id);


--
-- Name: solicitacao_beneficio_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao_beneficio
    ADD CONSTRAINT solicitacao_beneficio_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: solicitacao_cidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- Name: solicitacao_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: solicitacao_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);


--
-- Name: solicitacao_funcao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_funcao_fk FOREIGN KEY (funcao_id) REFERENCES funcao(id);


--
-- Name: solicitacao_motivosolicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_motivosolicitacao_fk FOREIGN KEY (motivosolicitacao_id) REFERENCES motivosolicitacao(id);


--
-- Name: solicitacao_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_usuario_fk FOREIGN KEY (solicitante_id) REFERENCES usuario(id);


--
-- Name: solicitacaobds_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaobds
    ADD CONSTRAINT solicitacaobds_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);


--
-- Name: solicitacaobds_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaobds
    ADD CONSTRAINT solicitacaobds_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: solicitacaobds_empresabds_empresabds_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaobds_empresabds
    ADD CONSTRAINT solicitacaobds_empresabds_empresabds_fk FOREIGN KEY (empresasbdss_id) REFERENCES empresabds(id);


--
-- Name: solicitacaobds_empresabds_solicitacaobds_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaobds_empresabds
    ADD CONSTRAINT solicitacaobds_empresabds_solicitacaobds_fk FOREIGN KEY (solicitacaobds_id) REFERENCES solicitacaobds(id);


--
-- Name: tabelailuminamento_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelailuminamento
    ADD CONSTRAINT tabelailuminamento_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: tabelailuminamentoitem_ghe_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelailuminamentoitem
    ADD CONSTRAINT tabelailuminamentoitem_ghe_fk FOREIGN KEY (ghe_id) REFERENCES ghe(id);


--
-- Name: tabelailuminamentoitem_tabelailuminamento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelailuminamentoitem
    ADD CONSTRAINT tabelailuminamentoitem_tabelailuminamento_fk FOREIGN KEY (tabelailuminamento_id) REFERENCES tabelailuminamento(id);


--
-- Name: tabelareajustecolaborador_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelareajustecolaborador
    ADD CONSTRAINT tabelareajustecolaborador_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: tabelaruido_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelaruido
    ADD CONSTRAINT tabelaruido_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: tabelaruidoitem_ghe_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelaruidoitem
    ADD CONSTRAINT tabelaruidoitem_ghe_fk FOREIGN KEY (ghe_id) REFERENCES ghe(id);


--
-- Name: tabelaruidoitem_tabelaruido_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelaruidoitem
    ADD CONSTRAINT tabelaruidoitem_tabelaruido_fk FOREIGN KEY (tabelaruido_id) REFERENCES tabelaruido(id);


--
-- Name: tabelatemperatura_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelatemperatura
    ADD CONSTRAINT tabelatemperatura_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: tabelatemperaturaitem_ghe_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelatemperaturaitem
    ADD CONSTRAINT tabelatemperaturaitem_ghe_fk FOREIGN KEY (ghe_id) REFERENCES ghe(id);


--
-- Name: tabelatemperaturaitem_tabelatemperatura_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelatemperaturaitem
    ADD CONSTRAINT tabelatemperaturaitem_tabelatemperatura_fk FOREIGN KEY (tabelatemperatura_id) REFERENCES tabelatemperatura(id);


--
-- Name: tipoepi_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipoepi
    ADD CONSTRAINT tipoepi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: turma_curso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turma
    ADD CONSTRAINT turma_curso_fk FOREIGN KEY (curso_id) REFERENCES curso(id);


--
-- Name: turma_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turma
    ADD CONSTRAINT turma_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: usuarioempresa_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuarioempresa
    ADD CONSTRAINT usuarioempresa_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: usuarioempresa_perfil_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuarioempresa
    ADD CONSTRAINT usuarioempresa_perfil_fk FOREIGN KEY (perfil_id) REFERENCES perfil(id);


--
-- Name: usuarioempresa_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuarioempresa
    ADD CONSTRAINT usuarioempresa_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: usuariomensagem_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuariomensagem
    ADD CONSTRAINT usuariomensagem_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: usuariomensagem_mensagem_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuariomensagem
    ADD CONSTRAINT usuariomensagem_mensagem_fk FOREIGN KEY (mensagem_id) REFERENCES mensagem(id);


--
-- Name: usuariomensagem_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuariomensagem
    ADD CONSTRAINT usuariomensagem_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

