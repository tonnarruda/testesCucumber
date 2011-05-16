--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- Name: alter_trigger(character, character); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION alter_trigger(character, character) RETURNS void
    LANGUAGE plpgsql
    AS $_$
BEGIN
	execute 'ALTER TABLE ' || $1 || ' ' || $2 || ' TRIGGER ALL';
END;
$_$;


ALTER FUNCTION public.alter_trigger(character, character) OWNER TO postgres;

--
-- Name: normalizar(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION normalizar(a_string text) RETURNS text
    LANGUAGE plpgsql
    AS $$
BEGIN
	RETURN to_ascii(convert_to(a_string, 'latin1'), 'latin1');
END
$$;


ALTER FUNCTION public.normalizar(a_string text) OWNER TO postgres;

--
-- Name: to_ascii(bytea, name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION to_ascii(bytea, name) RETURNS text
    LANGUAGE internal STRICT
    AS $$to_ascii_encname$$;


ALTER FUNCTION public.to_ascii(bytea, name) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: afastamento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE afastamento (
    id bigint NOT NULL,
    inss boolean,
    descricao character varying(100)
);


ALTER TABLE public.afastamento OWNER TO postgres;

--
-- Name: afastamento_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE afastamento_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.afastamento_sequence OWNER TO postgres;

--
-- Name: agenda; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE agenda (
    id bigint NOT NULL,
    data date,
    evento_id bigint,
    estabelecimento_id bigint
);


ALTER TABLE public.agenda OWNER TO postgres;

--
-- Name: agenda_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE agenda_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.agenda_sequence OWNER TO postgres;

--
-- Name: ambiente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ambiente (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint,
    estabelecimento_id bigint
);


ALTER TABLE public.ambiente OWNER TO postgres;

--
-- Name: ambiente_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ambiente_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ambiente_sequence OWNER TO postgres;

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
    exibirmoduloexterno boolean DEFAULT false,
    solicitacao_id bigint
);


ALTER TABLE public.anuncio OWNER TO postgres;

--
-- Name: anuncio_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE anuncio_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.anuncio_sequence OWNER TO postgres;

--
-- Name: aproveitamentoavaliacaocurso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE aproveitamentoavaliacaocurso (
    id bigint NOT NULL,
    colaboradorturma_id bigint,
    avaliacaocurso_id bigint,
    valor double precision
);


ALTER TABLE public.aproveitamentoavaliacaocurso OWNER TO postgres;

--
-- Name: aproveitamentoavaliacaocurso_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aproveitamentoavaliacaocurso_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.aproveitamentoavaliacaocurso_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.areaformacao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.areainteresse_sequence OWNER TO postgres;

--
-- Name: areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE areaorganizacional (
    id bigint NOT NULL,
    nome character varying(60),
    codigoac character varying(12),
    areamae_id bigint,
    responsavel_id bigint,
    empresa_id bigint,
    ativo boolean DEFAULT true
);


ALTER TABLE public.areaorganizacional OWNER TO postgres;

--
-- Name: areaorganizacional_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE areaorganizacional_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.areaorganizacional_sequence OWNER TO postgres;

--
-- Name: aspecto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE aspecto (
    id bigint NOT NULL,
    nome character varying(100),
    questionario_id bigint,
    avaliacao_id bigint
);


ALTER TABLE public.aspecto OWNER TO postgres;

--
-- Name: aspecto_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE aspecto_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.aspecto_sequence OWNER TO postgres;

--
-- Name: atitude; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE atitude (
    id bigint NOT NULL,
    nome character varying(100),
    observacao text,
    empresa_id bigint
);


ALTER TABLE public.atitude OWNER TO postgres;

--
-- Name: atitude_areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE atitude_areaorganizacional (
    atitudes_id bigint NOT NULL,
    areaorganizacionals_id bigint NOT NULL
);


ALTER TABLE public.atitude_areaorganizacional OWNER TO postgres;

--
-- Name: atitude_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE atitude_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.atitude_sequence OWNER TO postgres;

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
    chave character varying(255),
    empresa_id bigint
);


ALTER TABLE public.auditoria OWNER TO postgres;

--
-- Name: auditoria_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE auditoria_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.auditoria_sequence OWNER TO postgres;

--
-- Name: avaliacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avaliacao (
    id bigint NOT NULL,
    titulo character varying(100),
    cabecalho text,
    ativo boolean,
    empresa_id bigint,
    tipomodeloavaliacao character(1) NOT NULL,
    periodoexperiencia_id bigint
);


ALTER TABLE public.avaliacao OWNER TO postgres;

--
-- Name: avaliacaocurso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avaliacaocurso (
    id bigint NOT NULL,
    titulo character varying(100),
    tipo character(1),
    minimoaprovacao double precision
);


ALTER TABLE public.avaliacaocurso OWNER TO postgres;

--
-- Name: avaliacaocurso_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE avaliacaocurso_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.avaliacaocurso_sequence OWNER TO postgres;

--
-- Name: avaliacaodesempenho; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avaliacaodesempenho (
    id bigint NOT NULL,
    titulo character varying(100),
    inicio date,
    fim date,
    anonima boolean,
    permiteautoavaliacao boolean,
    liberada boolean,
    avaliacao_id bigint
);


ALTER TABLE public.avaliacaodesempenho OWNER TO postgres;

--
-- Name: avaliacaodesempenho_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE avaliacaodesempenho_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.avaliacaodesempenho_sequence OWNER TO postgres;

--
-- Name: avaliacaoturma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE avaliacaoturma (
    id bigint NOT NULL,
    ativa boolean,
    questionario_id bigint
);


ALTER TABLE public.avaliacaoturma OWNER TO postgres;

--
-- Name: avaliacaoturma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE avaliacaoturma_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.avaliacaoturma_sequence OWNER TO postgres;

--
-- Name: bairro; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE bairro (
    id bigint NOT NULL,
    nome character varying(20),
    cidade_id bigint
);


ALTER TABLE public.bairro OWNER TO postgres;

--
-- Name: bairro_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bairro_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.bairro_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.beneficio_sequence OWNER TO postgres;

--
-- Name: camposextras; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE camposextras (
    id bigint NOT NULL,
    texto1 character varying(250),
    texto2 character varying(250),
    texto3 character varying(250),
    data1 date,
    data2 date,
    data3 date,
    valor1 double precision,
    valor2 double precision,
    numero1 integer,
    texto4 character varying(250),
    texto5 character varying(250),
    texto6 character varying(250),
    texto7 character varying(250),
    texto8 character varying(250),
    texto9 character varying(250),
    texto10 character varying(250)
);


ALTER TABLE public.camposextras OWNER TO postgres;

--
-- Name: camposextras_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE camposextras_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.camposextras_sequence OWNER TO postgres;

--
-- Name: candidato; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidato (
    id bigint NOT NULL,
    nome character varying(60),
    senha character varying(30),
    name character varying(255),
    contenttype character varying(255),
    bytes bytea,
    size bigint,
    logradouro character varying(40),
    numero character varying(10),
    complemento character varying(20),
    bairro character varying(20),
    cep character varying(10),
    ddd character varying(5),
    fonefixo character varying(10),
    fonecelular character varying(10),
    email character varying(40),
    cpf character varying(11),
    pis character varying(11),
    rg character varying(15),
    naturalidade character varying(100),
    pai character varying(60),
    mae character varying(60),
    conjuge character varying(40),
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
    numerohab character varying(11),
    registro character varying(30),
    emissao date,
    vencimento date,
    categoria character varying(3),
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
    ctpsserie character varying(6),
    ctpsdv character(1),
    ctpsuf_id bigint,
    ctpsdataexpedicao date,
    empresa_id bigint,
    indicadopor character varying(100),
    nomecontato character varying(30),
    datacadastro date,
    idf2rh integer
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidato_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidatocurriculo_sequence OWNER TO postgres;

--
-- Name: candidatoeleicao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidatoeleicao (
    id bigint NOT NULL,
    qtdvoto integer,
    eleito boolean,
    candidato_id bigint,
    eleicao_id bigint
);


ALTER TABLE public.candidatoeleicao OWNER TO postgres;

--
-- Name: candidatoeleicao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE candidatoeleicao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidatoeleicao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidatoidioma_sequence OWNER TO postgres;

--
-- Name: candidatosolicitacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE candidatosolicitacao (
    id bigint NOT NULL,
    triagem boolean NOT NULL,
    candidato_id bigint,
    solicitacao_id bigint,
    status character(1)
);


ALTER TABLE public.candidatosolicitacao OWNER TO postgres;

--
-- Name: candidatosolicitacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE candidatosolicitacao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.candidatosolicitacao_sequence OWNER TO postgres;

--
-- Name: cargo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo (
    id bigint NOT NULL,
    nome character varying(30),
    nomemercado character varying(100) NOT NULL,
    missao text,
    competencias text,
    responsabilidades text,
    escolaridade character varying(5),
    experiencia text,
    recrutamento text,
    selecao text,
    observacao text,
    cbocodigo character varying(6),
    grupoocupacional_id bigint,
    empresa_id bigint,
    ativo boolean DEFAULT true,
    exibirmoduloexterno boolean,
    atitude text
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
-- Name: cargo_atitude; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo_atitude (
    cargo_id bigint NOT NULL,
    atitudes_id bigint NOT NULL
);


ALTER TABLE public.cargo_atitude OWNER TO postgres;

--
-- Name: cargo_conhecimento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo_conhecimento (
    cargo_id bigint NOT NULL,
    conhecimentos_id bigint NOT NULL
);


ALTER TABLE public.cargo_conhecimento OWNER TO postgres;

--
-- Name: cargo_etapaseletiva; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo_etapaseletiva (
    cargo_id bigint NOT NULL,
    etapaseletivas_id bigint NOT NULL
);


ALTER TABLE public.cargo_etapaseletiva OWNER TO postgres;

--
-- Name: cargo_habilidade; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cargo_habilidade (
    cargo_id bigint NOT NULL,
    habilidades_id bigint NOT NULL
);


ALTER TABLE public.cargo_habilidade OWNER TO postgres;

--
-- Name: cargo_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cargo_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cargo_sequence OWNER TO postgres;

--
-- Name: cat; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cat (
    id bigint NOT NULL,
    data date,
    numerocat character varying(20),
    observacao text,
    gerouafastamento boolean DEFAULT false,
    colaborador_id bigint
);


ALTER TABLE public.cat OWNER TO postgres;

--
-- Name: cat_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cat_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cat_sequence OWNER TO postgres;

--
-- Name: certificacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE certificacao (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);


ALTER TABLE public.certificacao OWNER TO postgres;

--
-- Name: certificacao_curso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE certificacao_curso (
    certificacaos_id bigint NOT NULL,
    cursos_id bigint NOT NULL
);


ALTER TABLE public.certificacao_curso OWNER TO postgres;

--
-- Name: certificacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE certificacao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.certificacao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cidade_sequence OWNER TO postgres;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cliente (
    id bigint NOT NULL,
    nome character varying(100) NOT NULL,
    enderecointerno character varying(50),
    enderecoexterno character varying(50),
    senhafortes character varying(20),
    versao character varying(10),
    dataatualizacao date,
    modulosadquiridos text,
    contatogeral text,
    contatoti text,
    observacao text
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: cliente_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cliente_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.cliente_sequence OWNER TO postgres;

--
-- Name: clinicaautorizada; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clinicaautorizada (
    id bigint NOT NULL,
    nome character varying(44),
    crm character varying(20),
    cnpj character varying(14),
    tipo character varying(5),
    data date,
    datainativa date,
    empresa_id bigint,
    endereco character varying(80),
    telefone character varying(10),
    horarioatendimento character varying(45)
);


ALTER TABLE public.clinicaautorizada OWNER TO postgres;

--
-- Name: clinicaautorizada_exame; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clinicaautorizada_exame (
    clinicaautorizada_id bigint NOT NULL,
    exames_id bigint NOT NULL
);


ALTER TABLE public.clinicaautorizada_exame OWNER TO postgres;

--
-- Name: clinicaautorizada_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE clinicaautorizada_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.clinicaautorizada_sequence OWNER TO postgres;

--
-- Name: codigocbo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE codigocbo (
    codigo character varying(6) NOT NULL,
    descricao character varying(200)
);


ALTER TABLE public.codigocbo OWNER TO postgres;

--
-- Name: colaborador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaborador (
    id bigint NOT NULL,
    matricula character varying(20),
    nome character varying(60),
    nomecomercial character varying(30),
    desligado boolean NOT NULL,
    datadesligamento date,
    observacao text,
    dataadmissao date,
    logradouro character varying(40),
    numero character varying(10),
    complemento character varying(20),
    bairro character varying(20),
    cep character varying(10),
    cpf character varying(11),
    pis character varying(11),
    rg character varying(15),
    naturalidade character varying(100),
    pai character varying(60),
    mae character varying(60),
    conjuge character varying(40),
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
    email character varying(40),
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
    numerohab character varying(11),
    registro character varying(30),
    emissao date,
    vencimento date,
    categoria character varying(3),
    titeleitnumero character varying(13),
    titeleitzona character varying(13),
    titeleitsecao character varying(13),
    certmilnumero character varying(12),
    certmiltipo character varying(5),
    certmilserie character varying(12),
    ctpsnumero character varying(8),
    ctpsserie character varying(6),
    ctpsdv character(1),
    ctpsuf_id bigint,
    ctpsdataexpedicao date,
    respondeuentrevista boolean NOT NULL,
    indicadopor character varying(100),
    name character varying(255),
    contenttype character varying(255),
    bytes bytea,
    size bigint,
    nomecontato character varying(30),
    camposextras_id bigint,
    solicitacao_id bigint,
    dataatualizacao date,
    observacaodemissao text
);


ALTER TABLE public.colaborador OWNER TO postgres;

--
-- Name: colaborador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaborador_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaborador_sequence OWNER TO postgres;

--
-- Name: colaboradorafastamento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorafastamento (
    id bigint NOT NULL,
    inicio date,
    fim date,
    mediconome character varying(100),
    medicocrm character varying(20),
    observacao text,
    cid character varying(10),
    afastamento_id bigint,
    colaborador_id bigint
);


ALTER TABLE public.colaboradorafastamento OWNER TO postgres;

--
-- Name: colaboradorafastamento_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorafastamento_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorafastamento_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradoridioma_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorocorrencia_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorpresenca_sequence OWNER TO postgres;

--
-- Name: colaboradorquestionario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorquestionario (
    id bigint NOT NULL,
    colaborador_id bigint,
    questionario_id bigint,
    respondida boolean,
    respondidaem date,
    turma_id bigint,
    candidato_id bigint,
    avaliacao_id bigint,
    performance double precision,
    observacao text,
    avaliacaodesempenho_id bigint,
    avaliador_id bigint,
    solicitacao_id bigint
);


ALTER TABLE public.colaboradorquestionario OWNER TO postgres;

--
-- Name: colaboradorquestionario_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorquestionario_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorquestionario_sequence OWNER TO postgres;

--
-- Name: colaboradorresposta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE colaboradorresposta (
    id bigint NOT NULL,
    comentario text,
    valor integer,
    pergunta_id bigint,
    resposta_id bigint,
    colaboradorquestionario_id bigint,
    areaorganizacional_id bigint,
    estabelecimento_id bigint
);


ALTER TABLE public.colaboradorresposta OWNER TO postgres;

--
-- Name: colaboradorresposta_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE colaboradorresposta_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorresposta_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.colaboradorturma_sequence OWNER TO postgres;

--
-- Name: comissao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comissao (
    id bigint NOT NULL,
    dataini date,
    datafim date,
    atapossetexto1 text,
    atapossetexto2 text,
    eleicao_id bigint
);


ALTER TABLE public.comissao OWNER TO postgres;

--
-- Name: comissao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comissao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.comissao_sequence OWNER TO postgres;

--
-- Name: comissaoeleicao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comissaoeleicao (
    id bigint NOT NULL,
    colaborador_id bigint,
    eleicao_id bigint,
    funcao character varying(20)
);


ALTER TABLE public.comissaoeleicao OWNER TO postgres;

--
-- Name: comissaoeleicao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comissaoeleicao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.comissaoeleicao_sequence OWNER TO postgres;

--
-- Name: comissaomembro; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comissaomembro (
    id bigint NOT NULL,
    funcao character varying(1),
    tipo character varying(1),
    colaborador_id bigint,
    comissaoperiodo_id bigint
);


ALTER TABLE public.comissaomembro OWNER TO postgres;

--
-- Name: comissaomembro_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comissaomembro_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.comissaomembro_sequence OWNER TO postgres;

--
-- Name: comissaoperiodo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comissaoperiodo (
    id bigint NOT NULL,
    apartirde date,
    comissao_id bigint
);


ALTER TABLE public.comissaoperiodo OWNER TO postgres;

--
-- Name: comissaoperiodo_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comissaoperiodo_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.comissaoperiodo_sequence OWNER TO postgres;

--
-- Name: comissaoplanotrabalho; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

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
);


ALTER TABLE public.comissaoplanotrabalho OWNER TO postgres;

--
-- Name: comissaoplanotrabalho_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comissaoplanotrabalho_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.comissaoplanotrabalho_sequence OWNER TO postgres;

--
-- Name: comissaoreuniao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comissaoreuniao (
    id bigint NOT NULL,
    data date,
    descricao character varying(100),
    localizacao character varying(100),
    horario character varying(20),
    tipo character varying(1),
    ata text,
    comissao_id bigint,
    obsreuniaoanterior text
);


ALTER TABLE public.comissaoreuniao OWNER TO postgres;

--
-- Name: comissaoreuniao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comissaoreuniao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.comissaoreuniao_sequence OWNER TO postgres;

--
-- Name: comissaoreuniaopresenca; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE comissaoreuniaopresenca (
    id bigint NOT NULL,
    comissaoreuniao_id bigint,
    colaborador_id bigint,
    presente boolean,
    justificativafalta character varying(100)
);


ALTER TABLE public.comissaoreuniaopresenca OWNER TO postgres;

--
-- Name: comissaoreuniaopresenca_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE comissaoreuniaopresenca_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.comissaoreuniaopresenca_sequence OWNER TO postgres;

--
-- Name: configuracaocampoextra; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracaocampoextra (
    id bigint NOT NULL,
    ativo boolean,
    nome character varying(15),
    descricao character varying(30),
    titulo character varying(60),
    ordem integer NOT NULL,
    tipo character varying(60),
    posicao integer
);


ALTER TABLE public.configuracaocampoextra OWNER TO postgres;

--
-- Name: configuracaocampoextra_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configuracaocampoextra_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.configuracaocampoextra_sequence OWNER TO postgres;

--
-- Name: configuracaoimpressaocurriculo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracaoimpressaocurriculo (
    id bigint NOT NULL,
    exibirconhecimento boolean DEFAULT false,
    exibircurso boolean DEFAULT false,
    exibirexperiencia boolean DEFAULT false,
    exibirinformacao boolean DEFAULT false,
    exibirobservacao boolean DEFAULT false,
    exibirhistorico boolean DEFAULT false,
    exibiridioma boolean DEFAULT false,
    exibirformacao boolean DEFAULT false,
    exibirinformacaosocioeconomica boolean DEFAULT false,
    exibirassinatura1 boolean DEFAULT false,
    assinatura1 character varying(50),
    exibirassinatura2 boolean DEFAULT false,
    assinatura2 character varying(50),
    exibirassinatura3 boolean DEFAULT false,
    assinatura3 character varying(50),
    usuario_id bigint,
    empresa_id bigint
);


ALTER TABLE public.configuracaoimpressaocurriculo OWNER TO postgres;

--
-- Name: configuracaoimpressaocurriculo_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configuracaoimpressaocurriculo_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.configuracaoimpressaocurriculo_sequence OWNER TO postgres;

--
-- Name: configuracaoperformance; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracaoperformance (
    id bigint NOT NULL,
    usuario_id bigint,
    caixa character varying(10),
    ordem integer,
    aberta boolean
);


ALTER TABLE public.configuracaoperformance OWNER TO postgres;

--
-- Name: configuracaoperformance_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configuracaoperformance_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.configuracaoperformance_sequence OWNER TO postgres;

--
-- Name: configuracaorelatoriodinamico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracaorelatoriodinamico (
    id bigint NOT NULL,
    usuario_id bigint,
    campos character varying(600),
    titulo character varying(100)
);


ALTER TABLE public.configuracaorelatoriodinamico OWNER TO postgres;

--
-- Name: configuracaorelatoriodinamico_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configuracaorelatoriodinamico_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.configuracaorelatoriodinamico_sequence OWNER TO postgres;

--
-- Name: conhecimento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE conhecimento (
    id bigint NOT NULL,
    nome character varying(100),
    observacao text,
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.conhecimento_sequence OWNER TO postgres;

--
-- Name: curso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE curso (
    id bigint NOT NULL,
    nome character varying(100),
    conteudoprogramatico text,
    empresa_id bigint,
    cargahoraria integer,
    percentualminimofrequencia double precision,
    criterioavaliacao text
);


ALTER TABLE public.curso OWNER TO postgres;

--
-- Name: curso_avaliacaocurso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE curso_avaliacaocurso (
    cursos_id bigint NOT NULL,
    avaliacaocursos_id bigint NOT NULL
);


ALTER TABLE public.curso_avaliacaocurso OWNER TO postgres;

--
-- Name: curso_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE curso_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.curso_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.dependente_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.diaturma_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.dnt_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.documentoanexo_sequence OWNER TO postgres;

--
-- Name: eleicao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE eleicao (
    id bigint NOT NULL,
    posse date,
    votacaoini date,
    votacaofim date,
    horariovotacaoini character varying(20),
    horariovotacaofim character varying(20),
    qtdvotonulo integer NOT NULL,
    qtdvotobranco integer NOT NULL,
    inscricaocandidatoini date,
    inscricaocandidatofim date,
    localinscricao character varying(100),
    localvotacao character varying(100),
    empresa_id bigint,
    sindicato character varying(100),
    apuracao date,
    horarioapuracao character varying(20),
    localapuracao character varying(100),
    descricao character varying(100),
    textoataeleicao text,
    estabelecimento_id bigint
);


ALTER TABLE public.eleicao OWNER TO postgres;

--
-- Name: eleicao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE eleicao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.eleicao_sequence OWNER TO postgres;

--
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empresa (
    id bigint NOT NULL,
    nome character varying(15),
    cnpj character varying(20),
    razaosocial character varying(60),
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
    maxcandidatacargo integer NOT NULL,
    logourl character varying(200),
    exibirsalario boolean NOT NULL,
    uf_id bigint,
    cidade_id bigint,
    atividade character varying(200),
    mensagemmoduloexterno character varying(400),
    exibirdadosambiente boolean DEFAULT false,
    logocertificadourl character varying(200),
    grupoac character(3)
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- Name: empresa_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE empresa_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.empresa_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.empresabds_sequence OWNER TO postgres;

--
-- Name: engenheiroresponsavel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE engenheiroresponsavel (
    id bigint NOT NULL,
    nome character varying(100),
    inicio date,
    crea character varying(20),
    fim date,
    nit character varying(15),
    empresa_id bigint
);


ALTER TABLE public.engenheiroresponsavel OWNER TO postgres;

--
-- Name: engenheiroresponsavel_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE engenheiroresponsavel_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.engenheiroresponsavel_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.entrevista_sequence OWNER TO postgres;

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
-- Name: epc_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE epc_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.epc_sequence OWNER TO postgres;

--
-- Name: epi; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE epi (
    id bigint NOT NULL,
    nome character varying(100),
    fabricante character varying(100),
    empresa_id bigint,
    tipoepi_id bigint,
    fardamento boolean NOT NULL
);


ALTER TABLE public.epi OWNER TO postgres;

--
-- Name: epi_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE epi_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.epi_sequence OWNER TO postgres;

--
-- Name: epihistorico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE epihistorico (
    id bigint NOT NULL,
    atenuacao character varying(20),
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.epihistorico_sequence OWNER TO postgres;

--
-- Name: estabelecimento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estabelecimento (
    id bigint NOT NULL,
    nome character varying(30),
    logradouro character varying(40),
    numero character varying(10),
    complemento character varying(20),
    bairro character varying(20),
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.estabelecimento_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.estado_sequence OWNER TO postgres;

--
-- Name: etapaprocessoeleitoral; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE etapaprocessoeleitoral (
    id bigint NOT NULL,
    nome character varying(100),
    prazolegal character varying(100),
    prazo integer,
    data date,
    eleicao_id bigint,
    empresa_id bigint
);


ALTER TABLE public.etapaprocessoeleitoral OWNER TO postgres;

--
-- Name: etapaprocessoeleitoral_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE etapaprocessoeleitoral_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.etapaprocessoeleitoral_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.etapaseletiva_sequence OWNER TO postgres;

--
-- Name: evento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE evento (
    id bigint NOT NULL,
    nome character varying(100)
);


ALTER TABLE public.evento OWNER TO postgres;

--
-- Name: evento_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE evento_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.evento_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.exame_sequence OWNER TO postgres;

--
-- Name: examesolicitacaoexame; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE examesolicitacaoexame (
    id bigint NOT NULL,
    periodicidade integer,
    exame_id bigint,
    solicitacaoexame_id bigint,
    clinicaautorizada_id bigint,
    realizacaoexame_id bigint
);


ALTER TABLE public.examesolicitacaoexame OWNER TO postgres;

--
-- Name: examesolicitacaoexame_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE examesolicitacaoexame_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.examesolicitacaoexame_sequence OWNER TO postgres;

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
    salario double precision,
    motivosaida character varying(100),
    candidato_id bigint,
    colaborador_id bigint,
    cargo_id bigint
);


ALTER TABLE public.experiencia OWNER TO postgres;

--
-- Name: experiencia_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE experiencia_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.experiencia_sequence OWNER TO postgres;

--
-- Name: extintor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extintor (
    id bigint NOT NULL,
    localizacao character varying(50),
    tipo character varying(1),
    numerocilindro integer,
    capacidade character varying(10),
    fabricante character varying(50),
    periodomaxrecarga integer,
    periodomaxinspecao integer,
    periodomaxhidrostatico integer,
    ativo boolean,
    empresa_id bigint,
    estabelecimento_id bigint
);


ALTER TABLE public.extintor OWNER TO postgres;

--
-- Name: extintor_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE extintor_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.extintor_sequence OWNER TO postgres;

--
-- Name: extintorinspecao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extintorinspecao (
    id bigint NOT NULL,
    data date,
    empresaresponsavel character varying(50),
    observacao text,
    extintor_id bigint
);


ALTER TABLE public.extintorinspecao OWNER TO postgres;

--
-- Name: extintorinspecao_extintorinspecaoitem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extintorinspecao_extintorinspecaoitem (
    extintorinspecao_id bigint NOT NULL,
    itens_id bigint NOT NULL
);


ALTER TABLE public.extintorinspecao_extintorinspecaoitem OWNER TO postgres;

--
-- Name: extintorinspecao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE extintorinspecao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.extintorinspecao_sequence OWNER TO postgres;

--
-- Name: extintorinspecaoitem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extintorinspecaoitem (
    id bigint NOT NULL,
    descricao character varying(50)
);


ALTER TABLE public.extintorinspecaoitem OWNER TO postgres;

--
-- Name: extintorinspecaoitem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE extintorinspecaoitem_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.extintorinspecaoitem_sequence OWNER TO postgres;

--
-- Name: extintormanutencao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extintormanutencao (
    id bigint NOT NULL,
    saida date,
    retorno date,
    motivo character varying(1),
    outromotivo character varying(50),
    observacao text,
    extintor_id bigint
);


ALTER TABLE public.extintormanutencao OWNER TO postgres;

--
-- Name: extintormanutencao_extintormanutencaoservico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extintormanutencao_extintormanutencaoservico (
    extintormanutencao_id bigint NOT NULL,
    servicos_id bigint NOT NULL
);


ALTER TABLE public.extintormanutencao_extintormanutencaoservico OWNER TO postgres;

--
-- Name: extintormanutencao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE extintormanutencao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.extintormanutencao_sequence OWNER TO postgres;

--
-- Name: extintormanutencaoservico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE extintormanutencaoservico (
    id bigint NOT NULL,
    descricao character varying(50)
);


ALTER TABLE public.extintormanutencaoservico OWNER TO postgres;

--
-- Name: extintormanutencaoservico_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE extintormanutencaoservico_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.extintormanutencaoservico_sequence OWNER TO postgres;

--
-- Name: faixasalarial; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE faixasalarial (
    id bigint NOT NULL,
    nome character varying(30),
    codigoac character varying(12),
    cargo_id bigint,
    nomeacpessoal character varying(30)
);


ALTER TABLE public.faixasalarial OWNER TO postgres;

--
-- Name: faixasalarial_certificacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE faixasalarial_certificacao (
    faixasalarials_id bigint NOT NULL,
    certificacaos_id bigint NOT NULL
);


ALTER TABLE public.faixasalarial_certificacao OWNER TO postgres;

--
-- Name: faixasalarial_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE faixasalarial_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.faixasalarial_sequence OWNER TO postgres;

--
-- Name: faixasalarialhistorico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE faixasalarialhistorico (
    id bigint NOT NULL,
    data date,
    valor double precision,
    tipo integer NOT NULL,
    quantidade double precision,
    faixasalarial_id bigint,
    indice_id bigint,
    status integer
);


ALTER TABLE public.faixasalarialhistorico OWNER TO postgres;

--
-- Name: faixasalarialhistorico_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE faixasalarialhistorico_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.faixasalarialhistorico_sequence OWNER TO postgres;

--
-- Name: fichamedica; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fichamedica (
    id bigint NOT NULL,
    ativa boolean,
    rodape text,
    questionario_id bigint
);


ALTER TABLE public.fichamedica OWNER TO postgres;

--
-- Name: fichamedica_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fichamedica_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.fichamedica_sequence OWNER TO postgres;

--
-- Name: formacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE formacao (
    id bigint NOT NULL,
    situacao character(1) NOT NULL,
    tipo character(1) NOT NULL,
    curso character varying(100),
    local character varying(100),
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.formacao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.funcao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.gasto_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.gastoempresa_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.gastoempresaitem_sequence OWNER TO postgres;

--
-- Name: grupoac; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE grupoac (
    id bigint NOT NULL,
    codigo character varying(3) NOT NULL,
    descricao character varying(20) NOT NULL,
    acurlsoap character varying(120),
    acurlwsdl character varying(120),
    acusuario character varying(100),
    acsenha character varying(30)
);


ALTER TABLE public.grupoac OWNER TO postgres;

--
-- Name: grupoac_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE grupoac_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.grupoac_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.grupogasto_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.grupoocupacional_sequence OWNER TO postgres;

--
-- Name: habilidade; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE habilidade (
    id bigint NOT NULL,
    nome character varying(100),
    observacao text,
    empresa_id bigint
);


ALTER TABLE public.habilidade OWNER TO postgres;

--
-- Name: habilidade_areaorganizacional; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE habilidade_areaorganizacional (
    habilidades_id bigint NOT NULL,
    areaorganizacionals_id bigint NOT NULL
);


ALTER TABLE public.habilidade_areaorganizacional OWNER TO postgres;

--
-- Name: habilidade_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE habilidade_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.habilidade_sequence OWNER TO postgres;

--
-- Name: historicoambiente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicoambiente (
    id bigint NOT NULL,
    descricao text,
    data date,
    datainativo date,
    tempoexposicao character varying(40),
    ambiente_id bigint
);


ALTER TABLE public.historicoambiente OWNER TO postgres;

--
-- Name: historicoambiente_epc; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicoambiente_epc (
    historicoambiente_id bigint NOT NULL,
    epcs_id bigint NOT NULL
);


ALTER TABLE public.historicoambiente_epc OWNER TO postgres;

--
-- Name: historicoambiente_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicoambiente_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicoambiente_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicobeneficio_sequence OWNER TO postgres;

--
-- Name: historicocandidato; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicocandidato (
    id bigint NOT NULL,
    data date,
    responsavel character varying(100),
    observacao text,
    etapaseletiva_id bigint,
    candidatosolicitacao_id bigint,
    horaini character varying(5),
    horafim character varying(5),
    apto character(1)
);


ALTER TABLE public.historicocandidato OWNER TO postgres;

--
-- Name: historicocandidato_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicocandidato_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicocandidato_sequence OWNER TO postgres;

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
    quantidadeindice double precision,
    faixasalarial_id bigint,
    reajustecolaborador_id bigint,
    status integer,
    movimentosalarialid bigint
);


ALTER TABLE public.historicocolaborador OWNER TO postgres;

--
-- Name: historicocolaborador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE historicocolaborador_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicocolaborador_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicocolaboradorbeneficio_sequence OWNER TO postgres;

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
-- Name: historicofuncao_epi; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE historicofuncao_epi (
    historicofuncao_id bigint NOT NULL,
    epis_id bigint NOT NULL
);


ALTER TABLE public.historicofuncao_epi OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.historicofuncao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.idioma_sequence OWNER TO postgres;

--
-- Name: indice; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE indice (
    id bigint NOT NULL,
    nome character varying(40) NOT NULL,
    codigoac character varying(12),
    grupoac character(3)
);


ALTER TABLE public.indice OWNER TO postgres;

--
-- Name: indice_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE indice_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.indice_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.indicehistorico_sequence OWNER TO postgres;

--
-- Name: medicaorisco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE medicaorisco (
    id bigint NOT NULL,
    data date,
    ambiente_id bigint
);


ALTER TABLE public.medicaorisco OWNER TO postgres;

--
-- Name: medicaorisco_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE medicaorisco_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.medicaorisco_sequence OWNER TO postgres;

--
-- Name: medicocoordenador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE medicocoordenador (
    id bigint NOT NULL,
    nome character varying(100),
    inicio date,
    crm character varying(20),
    empresa_id bigint,
    registro character varying(20),
    especialidade character varying(100),
    name character varying(255),
    contenttype character varying(255),
    bytes bytea,
    size bigint,
    fim date,
    nit character varying(15)
);


ALTER TABLE public.medicocoordenador OWNER TO postgres;

--
-- Name: medicocoordenador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE medicocoordenador_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.medicocoordenador_sequence OWNER TO postgres;

--
-- Name: mensagem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mensagem (
    id bigint NOT NULL,
    remetente character varying(100),
    link character varying(200),
    data timestamp without time zone,
    texto text
);


ALTER TABLE public.mensagem OWNER TO postgres;

--
-- Name: mensagem_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mensagem_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.mensagem_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.motivodemissao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.motivosolicitacao_sequence OWNER TO postgres;

--
-- Name: ocorrencia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ocorrencia (
    id bigint NOT NULL,
    descricao character varying(40),
    pontuacao integer NOT NULL,
    codigoac character varying(3),
    integraac boolean,
    empresa_id bigint
);


ALTER TABLE public.ocorrencia OWNER TO postgres;

--
-- Name: ocorrencia_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ocorrencia_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.ocorrencia_sequence OWNER TO postgres;

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
    accesskey character varying(1),
    papelmae_id bigint
);


ALTER TABLE public.papel OWNER TO postgres;

--
-- Name: papel_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE papel_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.papel_sequence OWNER TO postgres;

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
    perfilpadrao_id bigint,
    exame_id bigint,
    acversaowebservicecompativel character varying(20),
    uppercase boolean DEFAULT false,
    modulos text,
    atualizapapeisidsapartirde bigint,
    diaslembreteperiodoexperiencia character varying(20),
    emaildosuportetecnico character varying(40),
    campoextracolaborador boolean,
    codempresasuporte character varying(10),
    codclientesuporte character varying(10),
    camposcandidatovisivel text,
    camposcandidatoobrigatorio text,
    camposcandidatotabs text,
    emailcandidatonaoapto boolean
);


ALTER TABLE public.parametrosdosistema OWNER TO postgres;

--
-- Name: parametrosdosistema_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE parametrosdosistema_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.parametrosdosistema_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.perfil_sequence OWNER TO postgres;

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
    notamaxima integer,
    peso integer,
    avaliacao_id bigint
);


ALTER TABLE public.pergunta OWNER TO postgres;

--
-- Name: pergunta_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pergunta_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.pergunta_sequence OWNER TO postgres;

--
-- Name: periodoexperiencia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE periodoexperiencia (
    id bigint NOT NULL,
    dias integer,
    empresa_id bigint
);


ALTER TABLE public.periodoexperiencia OWNER TO postgres;

--
-- Name: periodoexperiencia_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE periodoexperiencia_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.periodoexperiencia_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.pesquisa_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.prioridadetreinamento_sequence OWNER TO postgres;

--
-- Name: prontuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE prontuario (
    id bigint NOT NULL,
    descricao text,
    data date,
    colaborador_id bigint,
    usuario_id bigint
);


ALTER TABLE public.prontuario OWNER TO postgres;

--
-- Name: prontuario_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE prontuario_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.prontuario_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.questionario_sequence OWNER TO postgres;

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
    quantidadeindiceproposto double precision,
    tiposalarioatual integer,
    indiceatual_id bigint,
    quantidadeindiceatual double precision,
    observacao text,
    faixasalarialatual_id bigint,
    faixasalarialproposta_id bigint
);


ALTER TABLE public.reajustecolaborador OWNER TO postgres;

--
-- Name: reajustecolaborador_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reajustecolaborador_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.reajustecolaborador_sequence OWNER TO postgres;

--
-- Name: realizacaoexame; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE realizacaoexame (
    id bigint NOT NULL,
    data date,
    observacao text,
    resultado character varying(20)
);


ALTER TABLE public.realizacaoexame OWNER TO postgres;

--
-- Name: realizacaoexame_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE realizacaoexame_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.realizacaoexame_sequence OWNER TO postgres;

--
-- Name: resposta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resposta (
    id bigint NOT NULL,
    texto text,
    ordem integer NOT NULL,
    pergunta_id bigint,
    peso integer
);


ALTER TABLE public.resposta OWNER TO postgres;

--
-- Name: resposta_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE resposta_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.resposta_sequence OWNER TO postgres;

--
-- Name: risco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE risco (
    id bigint NOT NULL,
    descricao character varying(100),
    gruporisco character varying(5),
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.risco_sequence OWNER TO postgres;

--
-- Name: riscoambiente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE riscoambiente (
    id bigint NOT NULL,
    epceficaz boolean,
    historicoambiente_id bigint,
    risco_id bigint
);


ALTER TABLE public.riscoambiente OWNER TO postgres;

--
-- Name: riscoambiente_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE riscoambiente_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.riscoambiente_sequence OWNER TO postgres;

--
-- Name: riscomedicaorisco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE riscomedicaorisco (
    id bigint NOT NULL,
    descricaoppra text,
    descricaoltcat text,
    intensidademedida character varying(20),
    tecnicautilizada character varying(100),
    medicaorisco_id bigint,
    risco_id bigint
);


ALTER TABLE public.riscomedicaorisco OWNER TO postgres;

--
-- Name: riscomedicaorisco_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE riscomedicaorisco_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.riscomedicaorisco_sequence OWNER TO postgres;

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
    remuneracao double precision,
    idademinima integer,
    idademaxima integer,
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
    empresa_id bigint,
    faixasalarial_id bigint,
    descricao character varying(100),
    avaliacao_id bigint,
    liberador_id bigint
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
-- Name: solicitacao_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE solicitacao_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.solicitacao_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.solicitacaobds_sequence OWNER TO postgres;

--
-- Name: solicitacaoepi; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacaoepi (
    id bigint NOT NULL,
    data date,
    entregue boolean,
    colaborador_id bigint,
    cargo_id bigint,
    empresa_id bigint
);


ALTER TABLE public.solicitacaoepi OWNER TO postgres;

--
-- Name: solicitacaoepi_item; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacaoepi_item (
    id bigint NOT NULL,
    epi_id bigint,
    solicitacaoepi_id bigint,
    qtdsolicitado integer NOT NULL,
    qtdentregue integer NOT NULL
);


ALTER TABLE public.solicitacaoepi_item OWNER TO postgres;

--
-- Name: solicitacaoepi_item_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE solicitacaoepi_item_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.solicitacaoepi_item_sequence OWNER TO postgres;

--
-- Name: solicitacaoepi_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE solicitacaoepi_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.solicitacaoepi_sequence OWNER TO postgres;

--
-- Name: solicitacaoexame; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE solicitacaoexame (
    id bigint NOT NULL,
    data date,
    motivo character varying(20),
    observacao character varying(100),
    candidato_id bigint,
    colaborador_id bigint,
    medicocoordenador_id bigint,
    empresa_id bigint
);


ALTER TABLE public.solicitacaoexame OWNER TO postgres;

--
-- Name: solicitacaoexame_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE solicitacaoexame_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.solicitacaoexame_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tabelareajustecolaborador_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tipoepi_sequence OWNER TO postgres;

--
-- Name: turma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE turma (
    id bigint NOT NULL,
    descricao character varying(100),
    instrutor character varying(100),
    custo double precision,
    dataprevini date,
    dataprevfim date,
    empresa_id bigint,
    instituicao character varying(100),
    horario character varying(20),
    realizada boolean,
    qtdparticipantesprevistos integer,
    curso_id bigint,
    avaliacaoturma_id bigint
);


ALTER TABLE public.turma OWNER TO postgres;

--
-- Name: turma_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE turma_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.turma_sequence OWNER TO postgres;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id bigint NOT NULL,
    nome character varying(100),
    login character varying(30),
    senha character varying(30),
    acessosistema boolean NOT NULL,
    ultimologin timestamp without time zone
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: usuario_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuario_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuarioempresa_sequence OWNER TO postgres;

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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuariomensagem_sequence OWNER TO postgres;

--
-- Name: afastamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY afastamento
    ADD CONSTRAINT afastamento_pkey PRIMARY KEY (id);


--
-- Name: agenda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_pkey PRIMARY KEY (id);


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
-- Name: aproveitamentoavaliacaocurso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY aproveitamentoavaliacaocurso
    ADD CONSTRAINT aproveitamentoavaliacaocurso_pkey PRIMARY KEY (id);


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
-- Name: atitude_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY atitude
    ADD CONSTRAINT atitude_pkey PRIMARY KEY (id);


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
-- Name: avaliacaocurso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avaliacaocurso
    ADD CONSTRAINT avaliacaocurso_pkey PRIMARY KEY (id);


--
-- Name: avaliacaodesempenho_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avaliacaodesempenho
    ADD CONSTRAINT avaliacaodesempenho_pkey PRIMARY KEY (id);


--
-- Name: avaliacaoturma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avaliacaoturma
    ADD CONSTRAINT avaliacaoturma_pkey PRIMARY KEY (id);


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
-- Name: camposextras_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY camposextras
    ADD CONSTRAINT camposextras_pkey PRIMARY KEY (id);


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
-- Name: candidatoeleicao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY candidatoeleicao
    ADD CONSTRAINT candidatoeleicao_pkey PRIMARY KEY (id);


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
-- Name: certificacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY certificacao
    ADD CONSTRAINT certificacao_pkey PRIMARY KEY (id);


--
-- Name: cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);


--
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- Name: clinicaautorizada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clinicaautorizada
    ADD CONSTRAINT clinicaautorizada_pkey PRIMARY KEY (id);


--
-- Name: codigocbo_codigo_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY codigocbo
    ADD CONSTRAINT codigocbo_codigo_uk UNIQUE (codigo);


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
-- Name: colaboradorafastamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY colaboradorafastamento
    ADD CONSTRAINT colaboradorafastamento_pkey PRIMARY KEY (id);


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
-- Name: comissao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comissao
    ADD CONSTRAINT comissao_pkey PRIMARY KEY (id);


--
-- Name: comissaoeleicao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comissaoeleicao
    ADD CONSTRAINT comissaoeleicao_pkey PRIMARY KEY (id);


--
-- Name: comissaomembro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comissaomembro
    ADD CONSTRAINT comissaomembro_pkey PRIMARY KEY (id);


--
-- Name: comissaoperiodo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comissaoperiodo
    ADD CONSTRAINT comissaoperiodo_pkey PRIMARY KEY (id);


--
-- Name: comissaoplanotrabalho_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comissaoplanotrabalho
    ADD CONSTRAINT comissaoplanotrabalho_pkey PRIMARY KEY (id);


--
-- Name: comissaoreuniao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comissaoreuniao
    ADD CONSTRAINT comissaoreuniao_pkey PRIMARY KEY (id);


--
-- Name: comissaoreuniaopresenca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comissaoreuniaopresenca
    ADD CONSTRAINT comissaoreuniaopresenca_pkey PRIMARY KEY (id);


--
-- Name: configuracaocampoextra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracaocampoextra
    ADD CONSTRAINT configuracaocampoextra_pkey PRIMARY KEY (id);


--
-- Name: configuracaoimpressaocurriculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracaoimpressaocurriculo
    ADD CONSTRAINT configuracaoimpressaocurriculo_pkey PRIMARY KEY (id);


--
-- Name: configuracaoperformance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracaoperformance
    ADD CONSTRAINT configuracaoperformance_pkey PRIMARY KEY (id);


--
-- Name: configuracaorelatoriodinamico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracaorelatoriodinamico
    ADD CONSTRAINT configuracaorelatoriodinamico_pkey PRIMARY KEY (id);


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
-- Name: eleicao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY eleicao
    ADD CONSTRAINT eleicao_pkey PRIMARY KEY (id);


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
-- Name: etapaprocessoeleitoral_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY etapaprocessoeleitoral
    ADD CONSTRAINT etapaprocessoeleitoral_pkey PRIMARY KEY (id);


--
-- Name: etapaseletiva_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY etapaseletiva
    ADD CONSTRAINT etapaseletiva_pkey PRIMARY KEY (id);


--
-- Name: evento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id);


--
-- Name: exame_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY exame
    ADD CONSTRAINT exame_pkey PRIMARY KEY (id);


--
-- Name: examesolicitacaoexame_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY examesolicitacaoexame
    ADD CONSTRAINT examesolicitacaoexame_pkey PRIMARY KEY (id);


--
-- Name: experiencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY experiencia
    ADD CONSTRAINT experiencia_pkey PRIMARY KEY (id);


--
-- Name: extintor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY extintor
    ADD CONSTRAINT extintor_pkey PRIMARY KEY (id);


--
-- Name: extintorinspecao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY extintorinspecao
    ADD CONSTRAINT extintorinspecao_pkey PRIMARY KEY (id);


--
-- Name: extintorinspecaoitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY extintorinspecaoitem
    ADD CONSTRAINT extintorinspecaoitem_pkey PRIMARY KEY (id);


--
-- Name: extintormanutencao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY extintormanutencao
    ADD CONSTRAINT extintormanutencao_pkey PRIMARY KEY (id);


--
-- Name: extintormanutencaoservico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY extintormanutencaoservico
    ADD CONSTRAINT extintormanutencaoservico_pkey PRIMARY KEY (id);


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
-- Name: fichamedica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fichamedica
    ADD CONSTRAINT fichamedica_pkey PRIMARY KEY (id);


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
-- Name: grupoac_codigo_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grupoac
    ADD CONSTRAINT grupoac_codigo_uk UNIQUE (codigo);


--
-- Name: grupoac_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grupoac
    ADD CONSTRAINT grupoac_pkey PRIMARY KEY (id);


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
-- Name: habilidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY habilidade
    ADD CONSTRAINT habilidade_pkey PRIMARY KEY (id);


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
-- Name: medicaorisco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY medicaorisco
    ADD CONSTRAINT medicaorisco_pkey PRIMARY KEY (id);


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
-- Name: periodoexperiencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY periodoexperiencia
    ADD CONSTRAINT periodoexperiencia_pkey PRIMARY KEY (id);


--
-- Name: pesquisa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pesquisa
    ADD CONSTRAINT pesquisa_pkey PRIMARY KEY (id);


--
-- Name: prioridadetreinamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY prioridadetreinamento
    ADD CONSTRAINT prioridadetreinamento_pkey PRIMARY KEY (id);


--
-- Name: prontuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY prontuario
    ADD CONSTRAINT prontuario_pkey PRIMARY KEY (id);


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
-- Name: riscoambiente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY riscoambiente
    ADD CONSTRAINT riscoambiente_pkey PRIMARY KEY (id);


--
-- Name: riscomedicaorisco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY riscomedicaorisco
    ADD CONSTRAINT riscomedicaorisco_pkey PRIMARY KEY (id);


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
-- Name: solicitacaoepi_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY solicitacaoepi_item
    ADD CONSTRAINT solicitacaoepi_item_pkey PRIMARY KEY (id);


--
-- Name: solicitacaoepi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY solicitacaoepi
    ADD CONSTRAINT solicitacaoepi_pkey PRIMARY KEY (id);


--
-- Name: solicitacaoexame_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY solicitacaoexame
    ADD CONSTRAINT solicitacaoexame_pkey PRIMARY KEY (id);


--
-- Name: tabelareajustecolaborador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tabelareajustecolaborador
    ADD CONSTRAINT tabelareajustecolaborador_pkey PRIMARY KEY (id);


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
-- Name: usuario_login_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_login_uk UNIQUE (login);


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
-- Name: solicitacaoexame_fkey; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX solicitacaoexame_fkey ON examesolicitacaoexame USING btree (solicitacaoexame_id);


--
-- Name: agenda_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);


--
-- Name: agenda_evento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_evento_fk FOREIGN KEY (evento_id) REFERENCES evento(id);


--
-- Name: ambiente_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ambiente
    ADD CONSTRAINT ambiente_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: ambiente_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ambiente
    ADD CONSTRAINT ambiente_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);


--
-- Name: anuncio_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY anuncio
    ADD CONSTRAINT anuncio_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: aproveitamentoavaliacaocurso_avaliacaocurso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aproveitamentoavaliacaocurso
    ADD CONSTRAINT aproveitamentoavaliacaocurso_avaliacaocurso_fk FOREIGN KEY (avaliacaocurso_id) REFERENCES avaliacaocurso(id);


--
-- Name: aproveitamentoavaliacaocurso_colaboradorturma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aproveitamentoavaliacaocurso
    ADD CONSTRAINT aproveitamentoavaliacaocurso_colaboradorturma_fk FOREIGN KEY (colaboradorturma_id) REFERENCES colaboradorturma(id);


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
-- Name: aspecto_avaliacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aspecto
    ADD CONSTRAINT aspecto_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);


--
-- Name: aspecto_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY aspecto
    ADD CONSTRAINT aspecto_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: atitude_areaorganizaciona_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY atitude_areaorganizacional
    ADD CONSTRAINT atitude_areaorganizaciona_areaorganizacional_fk FOREIGN KEY (areaorganizacionals_id) REFERENCES areaorganizacional(id);


--
-- Name: atitude_areaorganizaciona_atitude_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY atitude_areaorganizacional
    ADD CONSTRAINT atitude_areaorganizaciona_atitude_fk FOREIGN KEY (atitudes_id) REFERENCES atitude(id);


--
-- Name: atitude_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY atitude
    ADD CONSTRAINT atitude_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


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
-- Name: avaliacao_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacao
    ADD CONSTRAINT avaliacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: avaliacao_periodoexperiencia_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacao
    ADD CONSTRAINT avaliacao_periodoexperiencia_fk FOREIGN KEY (periodoexperiencia_id) REFERENCES periodoexperiencia(id);


--
-- Name: avaliacaodesempenho_avaliacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacaodesempenho
    ADD CONSTRAINT avaliacaodesempenho_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);


--
-- Name: avaliacaoturma_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY avaliacaoturma
    ADD CONSTRAINT avaliacaoturma_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


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
-- Name: candidatoeleicao_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidatoeleicao
    ADD CONSTRAINT candidatoeleicao_candidato_fk FOREIGN KEY (candidato_id) REFERENCES colaborador(id);


--
-- Name: candidatoeleicao_eleicao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY candidatoeleicao
    ADD CONSTRAINT candidatoeleicao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);


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
-- Name: cargo_atitude_atitude_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_atitude
    ADD CONSTRAINT cargo_atitude_atitude_fk FOREIGN KEY (atitudes_id) REFERENCES atitude(id);


--
-- Name: cargo_atitude_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_atitude
    ADD CONSTRAINT cargo_atitude_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


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
-- Name: cargo_etapaseletiva_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_etapaseletiva
    ADD CONSTRAINT cargo_etapaseletiva_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: cargo_etapaseletiva_etapaseletiva_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_etapaseletiva
    ADD CONSTRAINT cargo_etapaseletiva_etapaseletiva_fk FOREIGN KEY (etapaseletivas_id) REFERENCES etapaseletiva(id);


--
-- Name: cargo_grupoocupacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo
    ADD CONSTRAINT cargo_grupoocupacional_fk FOREIGN KEY (grupoocupacional_id) REFERENCES grupoocupacional(id);


--
-- Name: cargo_habilidade_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_habilidade
    ADD CONSTRAINT cargo_habilidade_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: cargo_habilidade_habilidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cargo_habilidade
    ADD CONSTRAINT cargo_habilidade_habilidade_fk FOREIGN KEY (habilidades_id) REFERENCES habilidade(id);


--
-- Name: cat_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cat
    ADD CONSTRAINT cat_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: certificacao_curso_certificacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY certificacao_curso
    ADD CONSTRAINT certificacao_curso_certificacao_fk FOREIGN KEY (certificacaos_id) REFERENCES certificacao(id);


--
-- Name: certificacao_curso_curso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY certificacao_curso
    ADD CONSTRAINT certificacao_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id);


--
-- Name: certificacao_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY certificacao
    ADD CONSTRAINT certificacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


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
-- Name: clinicaautorizada_exame_clinicaautorizada_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY clinicaautorizada_exame
    ADD CONSTRAINT clinicaautorizada_exame_clinicaautorizada_fk FOREIGN KEY (clinicaautorizada_id) REFERENCES clinicaautorizada(id);


--
-- Name: clinicaautorizada_exame_exame_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY clinicaautorizada_exame
    ADD CONSTRAINT clinicaautorizada_exame_exame_fk FOREIGN KEY (exames_id) REFERENCES exame(id);


--
-- Name: colaborador_camposextras_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_camposextras_fk FOREIGN KEY (camposextras_id) REFERENCES camposextras(id);


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
-- Name: colaborador_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: colaborador_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: colaboradorafastamento_afastamento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorafastamento
    ADD CONSTRAINT colaboradorafastamento_afastamento_fk FOREIGN KEY (afastamento_id) REFERENCES afastamento(id);


--
-- Name: colaboradorafastamento_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorafastamento
    ADD CONSTRAINT colaboradorafastamento_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


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
-- Name: colaboradorquestionario_avaliacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);


--
-- Name: colaboradorquestionario_avaliacaodesempenho_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_avaliacaodesempenho_fk FOREIGN KEY (avaliacaodesempenho_id) REFERENCES avaliacaodesempenho(id);


--
-- Name: colaboradorquestionario_avaliador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_avaliador_fk FOREIGN KEY (avaliador_id) REFERENCES colaborador(id);


--
-- Name: colaboradorquestionario_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


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
-- Name: colaboradorquestionario_solicitacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);


--
-- Name: colaboradorquestionario_turma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorquestionario
    ADD CONSTRAINT colaboradorquestionario_turma_fk FOREIGN KEY (turma_id) REFERENCES turma(id);


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
-- Name: colaboradorresposta_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY colaboradorresposta
    ADD CONSTRAINT colaboradorresposta_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);


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
-- Name: comissao_eleicao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissao
    ADD CONSTRAINT comissao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);


--
-- Name: comissaoeleicao_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoeleicao
    ADD CONSTRAINT comissaoeleicao_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: comissaoeleicao_eleicao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoeleicao
    ADD CONSTRAINT comissaoeleicao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);


--
-- Name: comissaomembro_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaomembro
    ADD CONSTRAINT comissaomembro_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: comissaomembro_comissaoperiodo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaomembro
    ADD CONSTRAINT comissaomembro_comissaoperiodo_fk FOREIGN KEY (comissaoperiodo_id) REFERENCES comissaoperiodo(id);


--
-- Name: comissaoperiodo_comissao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoperiodo
    ADD CONSTRAINT comissaoperiodo_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);


--
-- Name: comissaoplanotrabalho_comissao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoplanotrabalho
    ADD CONSTRAINT comissaoplanotrabalho_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);


--
-- Name: comissaoplanotrabalho_responsavel_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoplanotrabalho
    ADD CONSTRAINT comissaoplanotrabalho_responsavel_fk FOREIGN KEY (responsavel_id) REFERENCES colaborador(id);


--
-- Name: comissaoreuniao_comissao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoreuniao
    ADD CONSTRAINT comissaoreuniao_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);


--
-- Name: comissaoreuniaopresenca_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoreuniaopresenca
    ADD CONSTRAINT comissaoreuniaopresenca_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: comissaoreuniaopresenca_comissaoreuniao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY comissaoreuniaopresenca
    ADD CONSTRAINT comissaoreuniaopresenca_comissaoreuniao_fk FOREIGN KEY (comissaoreuniao_id) REFERENCES comissaoreuniao(id);


--
-- Name: configuracaoimpressaocurriculo_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuracaoimpressaocurriculo
    ADD CONSTRAINT configuracaoimpressaocurriculo_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: configuracaoimpressaocurriculo_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuracaoimpressaocurriculo
    ADD CONSTRAINT configuracaoimpressaocurriculo_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: configuracaoperformance_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuracaoperformance
    ADD CONSTRAINT configuracaoperformance_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: configuracaorelatoriodinamico_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuracaorelatoriodinamico
    ADD CONSTRAINT configuracaorelatoriodinamico_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


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
-- Name: curso_avaliacaocurso_avaliacaocurso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY curso_avaliacaocurso
    ADD CONSTRAINT curso_avaliacaocurso_avaliacaocurso_fk FOREIGN KEY (avaliacaocursos_id) REFERENCES avaliacaocurso(id);


--
-- Name: curso_avaliacaocurso_curso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY curso_avaliacaocurso
    ADD CONSTRAINT curso_avaliacaocurso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id);


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
-- Name: eleicao_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eleicao
    ADD CONSTRAINT eleicao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: eleicao_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY eleicao
    ADD CONSTRAINT eleicao_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);


--
-- Name: empresa_cidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);


--
-- Name: empresa_estado_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);


--
-- Name: empresa_grupoac_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_grupoac_fk FOREIGN KEY (grupoac) REFERENCES grupoac(codigo);


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
-- Name: etapaprocessoeleitoral_eleicao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY etapaprocessoeleitoral
    ADD CONSTRAINT etapaprocessoeleitoral_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);


--
-- Name: etapaprocessoeleitoral_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY etapaprocessoeleitoral
    ADD CONSTRAINT etapaprocessoeleitoral_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


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
-- Name: examesolicitacaoexame_clinicaautorizada_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY examesolicitacaoexame
    ADD CONSTRAINT examesolicitacaoexame_clinicaautorizada_fk FOREIGN KEY (clinicaautorizada_id) REFERENCES clinicaautorizada(id);


--
-- Name: examesolicitacaoexame_exame_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY examesolicitacaoexame
    ADD CONSTRAINT examesolicitacaoexame_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);


--
-- Name: examesolicitacaoexame_realizacaoexame_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY examesolicitacaoexame
    ADD CONSTRAINT examesolicitacaoexame_realizacaoexame_fk FOREIGN KEY (realizacaoexame_id) REFERENCES realizacaoexame(id);


--
-- Name: examesolicitacaoexame_solicitacaoexame_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY examesolicitacaoexame
    ADD CONSTRAINT examesolicitacaoexame_solicitacaoexame_fk FOREIGN KEY (solicitacaoexame_id) REFERENCES solicitacaoexame(id);


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
-- Name: extintor_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintor
    ADD CONSTRAINT extintor_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: extintor_estabelecimento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintor
    ADD CONSTRAINT extintor_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);


--
-- Name: extintorinspecao_extintor_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintorinspecao
    ADD CONSTRAINT extintorinspecao_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);


--
-- Name: extintorinspecao_extintorinspecaoitem_extintorinspecao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintorinspecao_extintorinspecaoitem
    ADD CONSTRAINT extintorinspecao_extintorinspecaoitem_extintorinspecao_fk FOREIGN KEY (extintorinspecao_id) REFERENCES extintorinspecao(id);


--
-- Name: extintorinspecao_extintorinspecaoitem_extintorinspecaoitem_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintorinspecao_extintorinspecaoitem
    ADD CONSTRAINT extintorinspecao_extintorinspecaoitem_extintorinspecaoitem_fk FOREIGN KEY (itens_id) REFERENCES extintorinspecaoitem(id);


--
-- Name: extintormanutencao_extintor_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintormanutencao
    ADD CONSTRAINT extintormanutencao_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);


--
-- Name: extintormanutencao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintormanutencao_extintormanutencaoservico
    ADD CONSTRAINT extintormanutencao_fk FOREIGN KEY (extintormanutencao_id) REFERENCES extintormanutencao(id);


--
-- Name: extintormanutencaoservico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY extintormanutencao_extintormanutencaoservico
    ADD CONSTRAINT extintormanutencaoservico_fk FOREIGN KEY (servicos_id) REFERENCES extintormanutencaoservico(id);


--
-- Name: faixasalarial_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faixasalarial
    ADD CONSTRAINT faixasalarial_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: faixasalarial_certificacao_certificacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faixasalarial_certificacao
    ADD CONSTRAINT faixasalarial_certificacao_certificacao_fk FOREIGN KEY (certificacaos_id) REFERENCES certificacao(id);


--
-- Name: faixasalarial_certificacao_faixasalarial_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faixasalarial_certificacao
    ADD CONSTRAINT faixasalarial_certificacao_faixasalarial_fk FOREIGN KEY (faixasalarials_id) REFERENCES faixasalarial(id);


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
-- Name: fichamedica_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fichamedica
    ADD CONSTRAINT fichamedica_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


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
-- Name: habilidade_areaorganizaciona_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY habilidade_areaorganizacional
    ADD CONSTRAINT habilidade_areaorganizaciona_areaorganizacional_fk FOREIGN KEY (areaorganizacionals_id) REFERENCES areaorganizacional(id);


--
-- Name: habilidade_areaorganizaciona_habilidade_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY habilidade_areaorganizacional
    ADD CONSTRAINT habilidade_areaorganizaciona_habilidade_fk FOREIGN KEY (habilidades_id) REFERENCES habilidade(id);


--
-- Name: habilidade_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY habilidade
    ADD CONSTRAINT habilidade_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: historicoambiente_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicoambiente
    ADD CONSTRAINT historicoambiente_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);


--
-- Name: historicoambiente_epc_epc_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicoambiente_epc
    ADD CONSTRAINT historicoambiente_epc_epc_fk FOREIGN KEY (epcs_id) REFERENCES epc(id);


--
-- Name: historicoambiente_epc_histambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicoambiente_epc
    ADD CONSTRAINT historicoambiente_epc_histambiente_fk FOREIGN KEY (historicoambiente_id) REFERENCES historicoambiente(id);


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
-- Name: historicofuncao_epi_epi_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicofuncao_epi
    ADD CONSTRAINT historicofuncao_epi_epi_fk FOREIGN KEY (epis_id) REFERENCES epi(id);


--
-- Name: historicofuncao_epi_historicofuncao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY historicofuncao_epi
    ADD CONSTRAINT historicofuncao_epi_historicofuncao_fk FOREIGN KEY (historicofuncao_id) REFERENCES historicofuncao(id);


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
-- Name: indice_grupoac_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indice
    ADD CONSTRAINT indice_grupoac_fk FOREIGN KEY (grupoac) REFERENCES grupoac(codigo);


--
-- Name: indicehistorico_indice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY indicehistorico
    ADD CONSTRAINT indicehistorico_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);


--
-- Name: medicaorisco_ambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY medicaorisco
    ADD CONSTRAINT medicaorisco_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);


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
-- Name: parametrosdosistema_exame_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parametrosdosistema
    ADD CONSTRAINT parametrosdosistema_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);


--
-- Name: parametrosdosistema_perfil_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY parametrosdosistema
    ADD CONSTRAINT parametrosdosistema_perfil_fk FOREIGN KEY (perfilpadrao_id) REFERENCES perfil(id);


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
-- Name: pergunta_avaliacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pergunta
    ADD CONSTRAINT pergunta_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);


--
-- Name: pergunta_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pergunta
    ADD CONSTRAINT pergunta_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: periodoexperiencia_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY periodoexperiencia
    ADD CONSTRAINT periodoexperiencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: pesquisa_questionario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pesquisa
    ADD CONSTRAINT pesquisa_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);


--
-- Name: prontuario_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY prontuario
    ADD CONSTRAINT prontuario_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: prontuario_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY prontuario
    ADD CONSTRAINT prontuario_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);


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
-- Name: riscoambiente_historicoambiente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY riscoambiente
    ADD CONSTRAINT riscoambiente_historicoambiente_fk FOREIGN KEY (historicoambiente_id) REFERENCES historicoambiente(id);


--
-- Name: riscoambiente_risco_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY riscoambiente
    ADD CONSTRAINT riscoambiente_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);


--
-- Name: riscomedicaorisco_medicaorisco_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY riscomedicaorisco
    ADD CONSTRAINT riscomedicaorisco_medicaorisco_fk FOREIGN KEY (medicaorisco_id) REFERENCES medicaorisco(id);


--
-- Name: riscomedicaorisco_risco_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY riscomedicaorisco
    ADD CONSTRAINT riscomedicaorisco_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);


--
-- Name: solicitacao_areaorganizacional_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);


--
-- Name: solicitacao_avaliacao_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);


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
-- Name: solicitacao_faixasalarial_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);


--
-- Name: solicitacao_liberador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT solicitacao_liberador_fk FOREIGN KEY (liberador_id) REFERENCES usuario(id);


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
-- Name: solicitacaoepi_cargo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoepi
    ADD CONSTRAINT solicitacaoepi_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


--
-- Name: solicitacaoepi_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoepi
    ADD CONSTRAINT solicitacaoepi_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: solicitacaoepi_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoepi
    ADD CONSTRAINT solicitacaoepi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: solicitacaoepi_item_epi_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoepi_item
    ADD CONSTRAINT solicitacaoepi_item_epi_fk FOREIGN KEY (epi_id) REFERENCES epi(id);


--
-- Name: solicitacaoepi_item_solicitacaoepi_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoepi_item
    ADD CONSTRAINT solicitacaoepi_item_solicitacaoepi_fk FOREIGN KEY (solicitacaoepi_id) REFERENCES solicitacaoepi(id);


--
-- Name: solicitacaoexame_candidato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoexame
    ADD CONSTRAINT solicitacaoexame_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


--
-- Name: solicitacaoexame_colaborador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoexame
    ADD CONSTRAINT solicitacaoexame_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);


--
-- Name: solicitacaoexame_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoexame
    ADD CONSTRAINT solicitacaoexame_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: solicitacaoexame_medicocoordenador_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY solicitacaoexame
    ADD CONSTRAINT solicitacaoexame_medicocoordenador_fk FOREIGN KEY (medicocoordenador_id) REFERENCES medicocoordenador(id);


--
-- Name: tabelareajustecolaborador_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tabelareajustecolaborador
    ADD CONSTRAINT tabelareajustecolaborador_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: tipoepi_empresa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipoepi
    ADD CONSTRAINT tipoepi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);


--
-- Name: turma_avaliacaoturma_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turma
    ADD CONSTRAINT turma_avaliacaoturma_fk FOREIGN KEY (avaliacaoturma_id) REFERENCES avaliacaoturma(id);


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

