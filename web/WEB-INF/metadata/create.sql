CREATE PROCEDURAL LANGUAGE plpgsql;
ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

CREATE TABLE estado (
    id bigint NOT NULL,
    sigla character varying(2) NOT NULL,
    nome character varying(40) NOT NULL
);
ALTER TABLE estado ADD CONSTRAINT estado_pkey PRIMARY KEY (id);
CREATE SEQUENCE estado_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE cidade (
    id bigint NOT NULL,
    nome character varying(80) NOT NULL,
    codigoac character varying(12),
    uf_id bigint NOT NULL
);
ALTER TABLE cidade ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);
ALTER TABLE cidade ADD CONSTRAINT cidade_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);
CREATE SEQUENCE cidade_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
    acurlsoap character varying(120),
    acurlwsdl character varying(120),
    acusuario character varying(100),
    acsenha character varying(30),
    maxcandidatacargo integer NOT NULL,
    logourl character varying(200),
	exibirsalario boolean NOT NULL,
	uf_id bigint,
	cidade_id bigint,
	atividade character varying(200),
	mensagemModuloExterno character varying(400)
);
ALTER TABLE empresa ADD CONSTRAINT empresa_pkey PRIMARY KEY (id);
ALTER TABLE empresa ADD CONSTRAINT empresa_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);
ALTER TABLE empresa ADD CONSTRAINT empresa_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);
CREATE SEQUENCE empresa_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
ALTER TABLE estabelecimento ADD CONSTRAINT estabelecimento_pkey PRIMARY KEY (id);
ALTER TABLE estabelecimento ADD CONSTRAINT estabelecimento_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);
ALTER TABLE estabelecimento ADD CONSTRAINT estabelecimento_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE estabelecimento ADD CONSTRAINT estabelecimento_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);
CREATE SEQUENCE estabelecimento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
ALTER TABLE papel ADD CONSTRAINT papel_pkey PRIMARY KEY (id);
ALTER TABLE papel ADD CONSTRAINT papel_papel_fk FOREIGN KEY (papelmae_id) REFERENCES papel(id);
CREATE SEQUENCE papel_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE perfil (
    id bigint NOT NULL,
    nome character varying(100)
);
ALTER TABLE perfil ADD CONSTRAINT perfil_pkey PRIMARY KEY (id);
CREATE SEQUENCE perfil_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE perfil_papel (
    perfil_id bigint NOT NULL,
    papeis_id bigint NOT NULL
);
ALTER TABLE perfil_papel ADD CONSTRAINT perfil_papel_papel_fk FOREIGN KEY (papeis_id) REFERENCES papel(id);
ALTER TABLE perfil_papel ADD CONSTRAINT perfil_papel_perfil_fk FOREIGN KEY (perfil_id) REFERENCES perfil(id);


CREATE TABLE usuario (
    id bigint NOT NULL,
    nome character varying(100),
    login character varying(30),
    senha character varying(30),
    acessosistema boolean NOT NULL,
    ultimologin timestamp
);
ALTER TABLE usuario ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);
alter table usuario add constraint usuario_login_uk unique (login);
CREATE SEQUENCE usuario_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE usuarioempresa (
    id bigint NOT NULL,
    usuario_id bigint,
    perfil_id bigint,
    empresa_id bigint
);
ALTER TABLE usuarioempresa ADD CONSTRAINT usuarioempresa_pkey PRIMARY KEY (id);
ALTER TABLE usuarioempresa ADD CONSTRAINT usuarioempresa_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE usuarioempresa ADD CONSTRAINT usuarioempresa_perfil_fk FOREIGN KEY (perfil_id) REFERENCES perfil(id);
ALTER TABLE usuarioempresa ADD CONSTRAINT usuarioempresa_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);
CREATE SEQUENCE usuarioempresa_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
ALTER TABLE auditoria ADD CONSTRAINT auditoria_pkey PRIMARY KEY (id);
ALTER TABLE auditoria ADD CONSTRAINT auditoria_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE auditoria ADD CONSTRAINT auditoria_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);
CREATE SEQUENCE auditoria_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
    datacadastro date
);
ALTER TABLE candidato ADD CONSTRAINT candidato_pkey PRIMARY KEY (id);
ALTER TABLE candidato ADD CONSTRAINT candidato_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);
ALTER TABLE candidato ADD CONSTRAINT candidato_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE candidato ADD CONSTRAINT candidato_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);
CREATE SEQUENCE candidato_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE motivodemissao (
    id bigint NOT NULL,
    motivo character varying(50),
    empresa_id bigint
);
ALTER TABLE motivodemissao ADD CONSTRAINT motivodemissao_pkey PRIMARY KEY (id);
ALTER TABLE motivodemissao ADD CONSTRAINT motivodemissao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE motivodemissao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE configuracaocampoextra (
	id bigint NOT NULL,
	ativo boolean,
	nome character varying(15),
	descricao character varying(30),
	titulo character varying(60),
	ordem integer not null,
	tipo character varying(60),
	posicao Integer
);

ALTER TABLE configuracaocampoextra ADD CONSTRAINT configuracaocampoextra_pkey PRIMARY KEY (id);
CREATE SEQUENCE configuracaocampoextra_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
	numero1 Integer,
	texto4 character varying(250),
	texto5 character varying(250),
	texto6 character varying(250),
	texto7 character varying(250),
	texto8 character varying(250),
	texto9 character varying(250),
	texto10 character varying(250)
);

ALTER TABLE camposExtras ADD CONSTRAINT camposExtras_pkey PRIMARY KEY(id);
CREATE SEQUENCE camposExtras_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
    numeroHab character varying(11),
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
    respondeuentrevista boolean not null,
    indicadopor character varying(100),
    name character varying(255),
	contenttype character varying(255),
	bytes bytea,
	size bigint,
	nomecontato character varying(30),
	camposextras_id bigint,
	solicitacao_id bigint,
	dataatualizacao date
);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_pkey PRIMARY KEY (id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_candidato_uk UNIQUE (candidato_id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_usuario_uk UNIQUE (usuario_id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_motivodemissao_fk FOREIGN KEY (motivodemissao_id) REFERENCES motivodemissao(id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_estado_fk FOREIGN KEY (uf_id) REFERENCES estado(id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE colaborador ADD CONSTRAINT colaborador_camposextras_fk FOREIGN KEY (camposextras_id) REFERENCES camposextras(id);
CREATE SEQUENCE colaborador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE areaorganizacional (
    id bigint NOT NULL,
    nome character varying(60),
    codigoac character varying(12),
    areamae_id bigint,
    responsavel_id bigint,
    empresa_id bigint,
    ativo boolean default true
);
ALTER TABLE areaorganizacional ADD CONSTRAINT areaorganizacional_pkey PRIMARY KEY (id);
ALTER TABLE areaorganizacional ADD CONSTRAINT areaorganizacional_areaorganizacional_fk FOREIGN KEY (areamae_id) REFERENCES areaorganizacional(id);
ALTER TABLE areaorganizacional ADD CONSTRAINT areaorganizacional_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE areaorganizacional ADD CONSTRAINT areaorganizacional_colaborador_fk FOREIGN KEY (responsavel_id) REFERENCES colaborador(id);
CREATE SEQUENCE areaorganizacional_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE areaformacao (
    id bigint NOT NULL,
    nome character varying(100)
);
ALTER TABLE areaformacao ADD CONSTRAINT areaformacao_pkey PRIMARY KEY (id);
CREATE SEQUENCE areaformacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE areainteresse (
    id bigint NOT NULL,
    nome character varying(100),
    observacao text,
    empresa_id bigint
);
ALTER TABLE areainteresse ADD CONSTRAINT areainteresse_pkey PRIMARY KEY (id);
ALTER TABLE areainteresse ADD CONSTRAINT areainteresse_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE areainteresse_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE areainteresse_areaorganizacional (
    areasinteresse_id bigint NOT NULL,
    areasorganizacionais_id bigint NOT NULL
);
ALTER TABLE areainteresse_areaorganizacional ADD CONSTRAINT areainteresse_areaorganizacional_areasinteresse_fk FOREIGN KEY (areasinteresse_id) REFERENCES areainteresse(id);
ALTER TABLE areainteresse_areaorganizacional ADD CONSTRAINT areainteresse_areaorganizacional_areasorganizacional_fk FOREIGN KEY (areasorganizacionais_id) REFERENCES areaorganizacional(id);


CREATE TABLE candidato_areainteresse (
    candidato_id bigint NOT NULL,
    areasinteresse_id bigint NOT NULL
);
ALTER TABLE candidato_areainteresse ADD CONSTRAINT candidato_areainteresse_areainteresse_fk FOREIGN KEY (areasinteresse_id) REFERENCES areainteresse(id);
ALTER TABLE candidato_areainteresse ADD CONSTRAINT candidato_areainteresse_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);


CREATE TABLE bairro (
    id bigint NOT NULL,
    nome character varying(20),
    cidade_id bigint
);
ALTER TABLE bairro ADD CONSTRAINT bairro_pkey PRIMARY KEY (id);
ALTER TABLE bairro ADD CONSTRAINT bairro_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);
CREATE SEQUENCE bairro_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE beneficio (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);
ALTER TABLE beneficio ADD CONSTRAINT beneficio_pkey PRIMARY KEY (id);
ALTER TABLE beneficio ADD CONSTRAINT beneficio_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE beneficio_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE historicobeneficio (
    id bigint NOT NULL,
    data date,
    valor double precision,
    paracolaborador double precision,
    paradependentedireto double precision,
    paradependenteindireto double precision,
    beneficio_id bigint
);
ALTER TABLE historicobeneficio ADD CONSTRAINT historicobeneficio_pkey PRIMARY KEY (id);
ALTER TABLE historicobeneficio ADD CONSTRAINT historicobeneficio_beneficio_fk FOREIGN KEY (beneficio_id) REFERENCES beneficio(id);
CREATE SEQUENCE historicobeneficio_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE historicocolaboradorbeneficio (
    id bigint NOT NULL,
    data date,
    dataate date,
    colaborador_id bigint
);
ALTER TABLE historicocolaboradorbeneficio ADD CONSTRAINT historicocolaboradorbeneficio_pkey PRIMARY KEY (id);
ALTER TABLE historicocolaboradorbeneficio ADD CONSTRAINT historicocolaboradorbeneficio_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
CREATE SEQUENCE historicocolaboradorbeneficio_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE historicocolaboradorbeneficio_beneficio (
    historicocolaboradorbeneficio_id bigint NOT NULL,
    beneficios_id bigint NOT NULL
);
ALTER TABLE historicocolaboradorbeneficio_beneficio ADD CONSTRAINT historicocolaboradorbeneficio_beneficio_beneficio_fk FOREIGN KEY (beneficios_id) REFERENCES beneficio(id);
ALTER TABLE historicocolaboradorbeneficio_beneficio ADD CONSTRAINT historicocolaboradorbeneficio_beneficio_hcbeneficio_fk FOREIGN KEY (historicocolaboradorbeneficio_id) REFERENCES historicocolaboradorbeneficio(id);

CREATE TABLE grupoocupacional (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);
ALTER TABLE grupoocupacional ADD CONSTRAINT grupoocupacional_pkey PRIMARY KEY (id);
ALTER TABLE grupoocupacional ADD CONSTRAINT grupoocupacional_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE grupoocupacional_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
    ativo boolean default true,
    exibirModuloExterno boolean,
    atitude text
);
ALTER TABLE cargo ADD CONSTRAINT cargo_pkey PRIMARY KEY (id);
ALTER TABLE cargo ADD CONSTRAINT cargo_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE cargo ADD CONSTRAINT cargo_grupoocupacional_fk FOREIGN KEY (grupoocupacional_id) REFERENCES grupoocupacional(id);
CREATE SEQUENCE cargo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE faixasalarial (
    id bigint NOT NULL,
    nome character varying(30),
    codigoac character varying(12),
    cargo_id bigint,
    nomeACPessoal character varying(30)
);
CREATE SEQUENCE faixasalarial_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
ALTER TABLE faixasalarial ADD CONSTRAINT faixasalarial_pkey PRIMARY KEY (id);
ALTER TABLE faixasalarial ADD CONSTRAINT faixasalarial_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


CREATE TABLE conhecimento (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);
ALTER TABLE conhecimento ADD CONSTRAINT conhecimento_pkey PRIMARY KEY (id);
ALTER TABLE conhecimento ADD CONSTRAINT conhecimento_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE conhecimento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE conhecimento_areaorganizacional (
    conhecimentos_id bigint NOT NULL,
    areaorganizacionals_id bigint NOT NULL
);
ALTER TABLE conhecimento_areaorganizacional ADD CONSTRAINT conhecimento_areaorganizacional_areaorganizacional_fk FOREIGN KEY (areaorganizacionals_id) REFERENCES areaorganizacional(id);
ALTER TABLE conhecimento_areaorganizacional ADD CONSTRAINT conhecimento_areaorganizacional_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento(id);


CREATE TABLE candidato_conhecimento (
    candidato_id bigint NOT NULL,
    conhecimentos_id bigint NOT NULL
);
ALTER TABLE candidato_conhecimento ADD CONSTRAINT candidato_conhecimento_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE candidato_conhecimento ADD CONSTRAINT candidato_conhecimento_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento(id);


CREATE TABLE cargo_conhecimento (
    cargo_id bigint NOT NULL,
    conhecimentos_id bigint NOT NULL
);
ALTER TABLE cargo_conhecimento ADD CONSTRAINT cargo_conhecimento_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);
ALTER TABLE cargo_conhecimento ADD CONSTRAINT cargo_conhecimento_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento(id);


CREATE TABLE cargo_areaformacao (
    cargo_id bigint NOT NULL,
    areaformacaos_id bigint NOT NULL
);
ALTER TABLE cargo_areaformacao ADD CONSTRAINT cargo_areaformacao_areaformacao_fk FOREIGN KEY (areaformacaos_id) REFERENCES areaformacao(id);
ALTER TABLE cargo_areaformacao ADD CONSTRAINT cargo_areaformacao_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


CREATE TABLE cargo_areaorganizacional (
    cargo_id bigint NOT NULL,
    areasorganizacionais_id bigint NOT NULL
);
ALTER TABLE cargo_areaorganizacional ADD CONSTRAINT cargo_areaorganizacional_areasorganizacional_fk FOREIGN KEY (areasorganizacionais_id) REFERENCES areaorganizacional(id);
ALTER TABLE cargo_areaorganizacional ADD CONSTRAINT cargo_areaorganizacional_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);


CREATE TABLE candidato_cargo (
    candidato_id bigint NOT NULL,
    cargos_id bigint NOT NULL
);
ALTER TABLE ONLY candidato_cargo ADD CONSTRAINT candidato_cargo_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE ONLY candidato_cargo ADD CONSTRAINT candidato_cargo_cargo_fk FOREIGN KEY (cargos_id) REFERENCES cargo(id);


CREATE TABLE motivosolicitacao (
    id bigint NOT NULL,
    descricao character varying(100)
);
ALTER TABLE motivosolicitacao ADD CONSTRAINT motivosolicitacao_pkey PRIMARY KEY (id);
CREATE SEQUENCE motivosolicitacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE ambiente (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint,
    estabelecimento_id bigint
);
ALTER TABLE ambiente ADD CONSTRAINT ambiente_pkey PRIMARY KEY (id);
ALTER TABLE ambiente ADD CONSTRAINT ambiente_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE ambiente ADD CONSTRAINT ambiente_estabelecimento_fk FOREIGN KEY (estabelecimento_id)  REFERENCES estabelecimento(id);
CREATE SEQUENCE ambiente_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE historicoambiente (
    id bigint NOT NULL,
    descricao text,
    data date,
    datainativo date,
    tempoexposicao character varying(40),
    ambiente_id bigint
);
ALTER TABLE historicoambiente ADD CONSTRAINT historicoambiente_pkey PRIMARY KEY (id);
ALTER TABLE historicoambiente ADD CONSTRAINT historicoambiente_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);
CREATE SEQUENCE historicoambiente_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE funcao (
    id bigint NOT NULL,
    nome character varying(100),
    cargo_id bigint
);
ALTER TABLE funcao ADD CONSTRAINT funcao_pkey PRIMARY KEY (id);
ALTER TABLE funcao ADD CONSTRAINT funcao_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);
CREATE SEQUENCE funcao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE historicofuncao (
    id bigint NOT NULL,
    data date,
    descricao text,
    funcao_id bigint
);
ALTER TABLE historicofuncao ADD CONSTRAINT historicofuncao_pkey PRIMARY KEY (id);
ALTER TABLE historicofuncao ADD CONSTRAINT historicofuncao_funcao_fk FOREIGN KEY (funcao_id) REFERENCES funcao(id);
CREATE SEQUENCE historicofuncao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
    descricao character varying(100)
);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_pkey PRIMARY KEY (id);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_cidade_fk FOREIGN KEY (cidade_id) REFERENCES cidade(id);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_motivosolicitacao_fk FOREIGN KEY (motivosolicitacao_id) REFERENCES motivosolicitacao(id);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_usuario_fk FOREIGN KEY (solicitante_id) REFERENCES usuario(id);
ALTER TABLE solicitacao ADD CONSTRAINT solicitacao_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);
CREATE SEQUENCE solicitacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

-- fk de solicitacao_id em colaborador
ALTER TABLE colaborador ADD CONSTRAINT colaborador_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);

CREATE TABLE solicitacao_bairro (
    solicitacao_id bigint NOT NULL,
    bairros_id bigint NOT NULL
);
ALTER TABLE solicitacao_bairro ADD CONSTRAINT solicitacao_bairro_bairro_fk FOREIGN KEY (bairros_id) REFERENCES bairro(id);
ALTER TABLE solicitacao_bairro ADD CONSTRAINT solicitacao_bairro_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);

CREATE TABLE anexo (
    id bigint NOT NULL,
    nome character varying(100),
    observacao text,
    url character varying(120),
    origem character(1) NOT NULL,
    origemid bigint
);
ALTER TABLE anexo ADD CONSTRAINT anexo_pkey PRIMARY KEY (id);
CREATE SEQUENCE anexo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE dependente (
    id bigint NOT NULL,
    nome character varying(100),
    datanascimento date,
    seqac character varying(12),
    colaborador_id bigint
);
ALTER TABLE dependente ADD CONSTRAINT dependente_pkey PRIMARY KEY (id);
ALTER TABLE dependente ADD CONSTRAINT dependente_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
CREATE SEQUENCE dependente_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE candidatocurriculo (
    id bigint NOT NULL,
    curriculo text,
    candidato_id bigint
);
ALTER TABLE candidatocurriculo ADD CONSTRAINT candidatocurriculo_pkey PRIMARY KEY (id);
ALTER TABLE candidatocurriculo ADD CONSTRAINT candidatocurriculo_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
CREATE SEQUENCE candidatocurriculo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE experiencia (
    id bigint NOT NULL,
    empresa character varying(100),
    dataadmissao date,
    datadesligamento date,
    observacao text,
    nomemercado character varying(100),
    salario double precision,
    motivoSaida character varying(100),
    candidato_id bigint,
    colaborador_id bigint,
    cargo_id bigint
);

ALTER TABLE experiencia ADD CONSTRAINT experiencia_pkey PRIMARY KEY (id);
ALTER TABLE experiencia ADD CONSTRAINT experiencia_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE experiencia ADD CONSTRAINT experiencia_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);
ALTER TABLE experiencia ADD CONSTRAINT experiencia_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
CREATE SEQUENCE experiencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
ALTER TABLE formacao ADD CONSTRAINT formacao_pkey PRIMARY KEY (id);
ALTER TABLE formacao ADD CONSTRAINT formacao_areaformacao_fk FOREIGN KEY (areaformacao_id) REFERENCES areaformacao(id);
ALTER TABLE formacao ADD CONSTRAINT formacao_candidato FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE formacao ADD CONSTRAINT formacao_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
CREATE SEQUENCE formacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE idioma (
    id bigint NOT NULL,
    nome character varying(100)
);
ALTER TABLE idioma ADD CONSTRAINT idioma_pkey PRIMARY KEY (id);
CREATE SEQUENCE idioma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE colaboradoridioma (
    id bigint NOT NULL,
    nivel character(1) NOT NULL,
    colaborador_id bigint,
    idioma_id bigint
);
ALTER TABLE colaboradoridioma ADD CONSTRAINT colaboradoridioma_pkey PRIMARY KEY (id);
ALTER TABLE colaboradoridioma ADD CONSTRAINT colaboradoridioma_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE colaboradoridioma ADD CONSTRAINT colaboradoridioma_idioma_fk FOREIGN KEY (idioma_id) REFERENCES idioma(id);
CREATE SEQUENCE colaboradoridioma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE candidatoidioma (
    id bigint NOT NULL,
    nivel character(1) NOT NULL,
    candidato_id bigint,
    idioma_id bigint
);
ALTER TABLE candidatoidioma ADD CONSTRAINT candidatoidioma_pkey PRIMARY KEY (id);
ALTER TABLE candidatoidioma ADD CONSTRAINT candidatoidioma_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE candidatoidioma ADD CONSTRAINT candidatoidioma_idioma_fk FOREIGN KEY (idioma_id) REFERENCES idioma(id);
CREATE SEQUENCE candidatoidioma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE candidatosolicitacao (
    id bigint NOT NULL,
    triagem boolean NOT NULL,
    candidato_id bigint,
    solicitacao_id bigint
);
ALTER TABLE candidatosolicitacao ADD CONSTRAINT candidatosolicitacao_pkey PRIMARY KEY (id);
ALTER TABLE candidatosolicitacao ADD CONSTRAINT candidatosolicitacao_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE candidatosolicitacao ADD CONSTRAINT candidatosolicitacao_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);
CREATE SEQUENCE candidatosolicitacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
    exibirmoduloexterno boolean default false,
    solicitacao_id bigint
    
);
ALTER TABLE anuncio ADD CONSTRAINT anuncio_pkey PRIMARY KEY (id);
ALTER TABLE anuncio ADD CONSTRAINT anuncio_solicitacao_uk UNIQUE (solicitacao_id);
ALTER TABLE anuncio ADD CONSTRAINT anuncio_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id);
CREATE SEQUENCE anuncio_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE etapaseletiva (
    id bigint NOT NULL,
    nome character varying(100),
    ordem integer NOT NULL,
    empresa_id bigint
);
ALTER TABLE etapaseletiva ADD CONSTRAINT etapaseletiva_pkey PRIMARY KEY (id);
ALTER TABLE etapaseletiva ADD CONSTRAINT etapaseletiva_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE etapaseletiva_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE historicocandidato (
    id bigint NOT NULL,
    data date,
    responsavel character varying(100),
    observacao text,
    apto boolean NOT NULL,
    etapaseletiva_id bigint,
    candidatosolicitacao_id bigint
);
ALTER TABLE historicocandidato ADD CONSTRAINT historicocandidato_pkey PRIMARY KEY (id);
ALTER TABLE historicocandidato ADD CONSTRAINT historicocandidato_candidatosolicitacao_fk FOREIGN KEY (candidatosolicitacao_id) REFERENCES candidatosolicitacao(id);
ALTER TABLE historicocandidato ADD CONSTRAINT historicocandidato_etapaseletiva_fk FOREIGN KEY (etapaseletiva_id) REFERENCES etapaseletiva(id);
CREATE SEQUENCE historicocandidato_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
ALTER TABLE documentoanexo ADD CONSTRAINT documentoanexo_pkey PRIMARY KEY (id);
ALTER TABLE documentoanexo ADD CONSTRAINT documentoanexo_etapaseletiva_fk FOREIGN KEY (etapaseletiva_id) REFERENCES etapaseletiva(id);
CREATE SEQUENCE documentoanexo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE empresabds (
    id bigint NOT NULL,
    nome character varying(100),
    contato character varying(100),
    fone character varying(10),
    email character varying(120),
    ddd character varying(5),
    empresa_id bigint
);
ALTER TABLE empresabds ADD CONSTRAINT empresabds_pkey PRIMARY KEY (id);
ALTER TABLE empresabds ADD CONSTRAINT empresabds_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE empresabds_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
ALTER TABLE solicitacaobds ADD CONSTRAINT solicitacaobds_pkey PRIMARY KEY (id);
ALTER TABLE solicitacaobds ADD CONSTRAINT solicitacaobds_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);
ALTER TABLE solicitacaobds ADD CONSTRAINT solicitacaobds_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);
CREATE SEQUENCE solicitacaobds_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE solicitacaobds_empresabds (
    solicitacaobds_id bigint NOT NULL,
    empresasbdss_id bigint NOT NULL
);
ALTER TABLE ONLY solicitacaobds_empresabds ADD CONSTRAINT solicitacaobds_empresabds_empresabds_fk FOREIGN KEY (empresasbdss_id) REFERENCES empresabds(id);
ALTER TABLE ONLY solicitacaobds_empresabds ADD CONSTRAINT solicitacaobds_empresabds_solicitacaobds_fk FOREIGN KEY (solicitacaobds_id) REFERENCES solicitacaobds(id);


CREATE TABLE ocorrencia (
    id bigint NOT NULL,
    descricao character varying(40),
    pontuacao integer NOT NULL,
    codigoac character varying(3),
    integraac boolean,
    empresa_id bigint
);
ALTER TABLE ocorrencia ADD CONSTRAINT ocorrencia_pkey PRIMARY KEY (id);
ALTER TABLE ocorrencia ADD CONSTRAINT ocorrencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE ocorrencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE colaboradorocorrencia (
    id bigint NOT NULL,
    dataini date,
    datafim date,
    observacao text,
    colaborador_id bigint,
    ocorrencia_id bigint
);
ALTER TABLE colaboradorocorrencia ADD CONSTRAINT colaboradorocorrencia_pkey PRIMARY KEY (id);
ALTER TABLE colaboradorocorrencia ADD CONSTRAINT colaboradorocorrencia_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE colaboradorocorrencia ADD CONSTRAINT colaboradorocorrencia_ocorrencia_fk FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencia(id);
CREATE SEQUENCE colaboradorocorrencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE curso (
    id bigint NOT NULL,
    nome character varying(100),
    conteudoprogramatico text,
    empresa_id bigint,
    cargahoraria integer,
    percentualminimofrequencia double precision,
    criterioAvaliacao text
);
ALTER TABLE curso ADD CONSTRAINT curso_pkey PRIMARY KEY (id);
ALTER TABLE curso ADD CONSTRAINT curso_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE curso_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE prioridadetreinamento (
    id bigint NOT NULL,
    descricao character varying(100),
    sigla character varying(5),
    numero integer NOT NULL
);
ALTER TABLE prioridadetreinamento ADD CONSTRAINT prioridadetreinamento_pkey PRIMARY KEY (id);
CREATE SEQUENCE prioridadetreinamento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE dnt (
    id bigint NOT NULL,
    nome character varying(100),
    data date,
    empresa_id bigint
);
ALTER TABLE dnt ADD CONSTRAINT dnt_pkey PRIMARY KEY (id);
ALTER TABLE dnt ADD CONSTRAINT dnt_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE dnt_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE grupogasto (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);
ALTER TABLE grupogasto ADD CONSTRAINT grupogasto_pkey PRIMARY KEY (id);
ALTER TABLE grupogasto ADD CONSTRAINT grupogasto_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE grupogasto_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE gasto (
    id bigint NOT NULL,
    nome character varying(100),
    naoimportar boolean NOT NULL,
    codigoac character varying(12),
    grupogasto_id bigint,
    empresa_id bigint
);
ALTER TABLE gasto ADD CONSTRAINT gasto_pkey PRIMARY KEY (id);
ALTER TABLE gasto ADD CONSTRAINT gasto_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE gasto ADD CONSTRAINT gasto_grupogasto_fk FOREIGN KEY (grupogasto_id) REFERENCES grupogasto(id);
CREATE SEQUENCE gasto_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE gastoempresa (
    id bigint NOT NULL,
    mesano date,
    colaborador_id bigint,
    empresa_id bigint
);
ALTER TABLE gastoempresa ADD CONSTRAINT gastoempresa_pkey PRIMARY KEY (id);
ALTER TABLE gastoempresa ADD CONSTRAINT gastoempresa_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE gastoempresa ADD CONSTRAINT gastoempresa_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE gastoempresa_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE gastoempresaitem (
    id bigint NOT NULL,
    valor double precision,
    gasto_id bigint,
    gastoempresa_id bigint
);
ALTER TABLE gastoempresaitem ADD CONSTRAINT gastoempresaitem_pkey PRIMARY KEY (id);
ALTER TABLE gastoempresaitem ADD CONSTRAINT gastoempresaitem_gasto_fk FOREIGN KEY (gasto_id) REFERENCES gasto(id);
ALTER TABLE gastoempresaitem ADD CONSTRAINT gastoempresaitem_gastoempresa_fk FOREIGN KEY (gastoempresa_id) REFERENCES gastoempresa(id);
CREATE SEQUENCE gastoempresaitem_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE tabelareajustecolaborador (
    id bigint NOT NULL,
    nome character varying(100),
    data date,
    observacao text,
    aprovada boolean NOT NULL,
    empresa_id bigint
);
ALTER TABLE tabelareajustecolaborador ADD CONSTRAINT tabelareajustecolaborador_pkey PRIMARY KEY (id);
ALTER TABLE tabelareajustecolaborador ADD CONSTRAINT tabelareajustecolaborador_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE tabelareajustecolaborador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE engenheiroresponsavel (
    id bigint NOT NULL,
    nome character varying(100),
    inicio date,
    crea character varying(20),
    fim date,
    nit character varying(15),
    empresa_id bigint
);

ALTER TABLE engenheiroresponsavel ADD CONSTRAINT engenheiroresponsavel_pkey PRIMARY KEY (id);
ALTER TABLE engenheiroresponsavel ADD CONSTRAINT engenheiroresponsavel_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE engenheiroresponsavel_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE cat (
    id bigint NOT NULL,
    data date,
    numerocat character varying(20),
    observacao text,
    gerouafastamento boolean DEFAULT false,
    colaborador_id bigint
);
ALTER TABLE cat ADD CONSTRAINT cat_pkey PRIMARY KEY (id);
ALTER TABLE cat ADD CONSTRAINT cat_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
CREATE SEQUENCE cat_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
	horarioAtendimento character varying(45)
);

ALTER TABLE clinicaautorizada ADD CONSTRAINT clinicaautorizada_pkey PRIMARY KEY (id);
ALTER TABLE clinicaautorizada ADD CONSTRAINT clinicaautorizada_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE clinicaautorizada_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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

ALTER TABLE medicocoordenador ADD CONSTRAINT medicocoordenador_pkey PRIMARY KEY (id);
ALTER TABLE medicocoordenador ADD CONSTRAINT medicocoordenador_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE medicocoordenador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE epc (
    id bigint NOT NULL,
    codigo character varying(30),
    descricao character varying(100),
    empresa_id bigint
);
ALTER TABLE epc ADD CONSTRAINT epc_pkey PRIMARY KEY (id);
ALTER TABLE epc ADD CONSTRAINT epc_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE epc_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE tipoepi (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);
ALTER TABLE tipoepi ADD CONSTRAINT tipoepi_pkey PRIMARY KEY (id);
ALTER TABLE tipoepi ADD CONSTRAINT tipoepi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE tipoepi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE epi (
    id bigint NOT NULL,
    nome character varying(100),
    fabricante character varying(100),
    empresa_id bigint,
    tipoepi_id bigint,
    fardamento boolean NOT NULL
);
ALTER TABLE epi ADD CONSTRAINT epi_pkey PRIMARY KEY (id);
ALTER TABLE epi ADD CONSTRAINT epi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE epi ADD CONSTRAINT epi_tipoepi_fk FOREIGN KEY (tipoepi_id) REFERENCES tipoepi(id);
CREATE SEQUENCE epi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE epihistorico (
    id bigint NOT NULL,
    atenuacao character varying(20),
    vencimentoca date,
    validadeuso integer,
    ca character varying(20),
    data date,
    epi_id bigint
);
ALTER TABLE epihistorico ADD CONSTRAINT epihistorico_pkey PRIMARY KEY (id);
ALTER TABLE epihistorico ADD CONSTRAINT epihistorico_epi_fk FOREIGN KEY (epi_id) REFERENCES epi(id);
CREATE SEQUENCE epihistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE exame (
    id bigint NOT NULL,
    nome character varying(100),
    periodicidade integer NOT NULL,
    periodico boolean,
    empresa_id bigint
);
ALTER TABLE exame ADD CONSTRAINT exame_pkey PRIMARY KEY (id);
ALTER TABLE exame ADD CONSTRAINT exame_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE exame_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE historicofuncao_exame (
    historicofuncao_id bigint NOT NULL,
    exames_id bigint NOT NULL
);
ALTER TABLE historicofuncao_exame ADD CONSTRAINT historicofuncao_exame_exame_fk FOREIGN KEY (exames_id) REFERENCES exame(id);
ALTER TABLE historicofuncao_exame ADD CONSTRAINT historicofuncao_exame_historicofuncao_fk FOREIGN KEY (historicofuncao_id) REFERENCES historicofuncao(id);


CREATE TABLE realizacaoexame (
    id bigint NOT NULL,
    data date,
    observacao text,
    resultado character varying(20)
);
ALTER TABLE realizacaoexame ADD CONSTRAINT realizacaoexame_pkey PRIMARY KEY (id);
CREATE SEQUENCE realizacaoexame_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE risco (
    id bigint NOT NULL,
    descricao character varying(100),
    gruporisco character varying(5),
    empresa_id bigint
);
ALTER TABLE risco ADD CONSTRAINT risco_pkey PRIMARY KEY (id);
ALTER TABLE risco ADD CONSTRAINT risco_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE risco_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE risco_epi (
    risco_id bigint NOT NULL,
    epis_id bigint NOT NULL
);
ALTER TABLE risco_epi ADD CONSTRAINT risco_epi_epi_fk FOREIGN KEY (epis_id) REFERENCES epi(id);
ALTER TABLE risco_epi ADD CONSTRAINT risco_epi_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);

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
ALTER TABLE ONLY questionario ADD CONSTRAINT questionario_pkey PRIMARY KEY (id);
ALTER TABLE ONLY questionario ADD CONSTRAINT questionario_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE questionario_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE avaliacao (
	id bigint NOT NULL,
	titulo character varying(100),
	cabecalho text,
	ativo boolean,
	empresa_id bigint
);

ALTER TABLE avaliacao ADD CONSTRAINT avaliacao_pkey PRIMARY KEY(id);
ALTER TABLE avaliacao ADD CONSTRAINT avaliacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
--nao tem sequence, ta ok.

CREATE TABLE aspecto (
    id bigint NOT NULL,
    nome character varying(100),
    questionario_id bigint,
    avaliacao_id bigint
);
ALTER TABLE ONLY aspecto ADD CONSTRAINT aspecto_pkey PRIMARY KEY (id);
ALTER TABLE ONLY aspecto ADD CONSTRAINT aspecto_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);
ALTER TABLE ONLY aspecto ADD CONSTRAINT aspecto_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);
CREATE SEQUENCE aspecto_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
ALTER TABLE ONLY pergunta ADD CONSTRAINT pergunta_pkey PRIMARY KEY (id);
ALTER TABLE ONLY pergunta ADD CONSTRAINT pergunta_aspecto_fk FOREIGN KEY (aspecto_id) REFERENCES aspecto(id);
ALTER TABLE ONLY pergunta ADD CONSTRAINT pergunta_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);
ALTER TABLE ONLY pergunta ADD CONSTRAINT pergunta_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);
CREATE SEQUENCE pergunta_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE resposta (
    id bigint NOT NULL,
    texto text,
    ordem integer NOT NULL,
    pergunta_id bigint,
    peso integer
);
ALTER TABLE ONLY resposta ADD CONSTRAINT resposta_pkey PRIMARY KEY (id);
ALTER TABLE ONLY resposta ADD CONSTRAINT resposta_pergunta_fk FOREIGN KEY (pergunta_id) REFERENCES pergunta(id);
CREATE SEQUENCE resposta_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE pesquisa (
    id bigint NOT NULL,
    questionario_id bigint
);
ALTER TABLE ONLY pesquisa ADD CONSTRAINT pesquisa_pkey PRIMARY KEY (id);
ALTER TABLE ONLY pesquisa ADD CONSTRAINT pesquisa_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);
CREATE SEQUENCE pesquisa_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE avaliacaoturma (
    id bigint NOT NULL,
    ativa boolean,
    questionario_id bigint
);
ALTER TABLE avaliacaoturma ADD CONSTRAINT avaliacaoturma_pkey PRIMARY KEY (id);
ALTER TABLE ONLY avaliacaoturma ADD CONSTRAINT avaliacaoturma_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);
CREATE SEQUENCE avaliacaoturma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
	qtdparticipantesprevistos Integer,
    curso_id bigint,
    avaliacaoturma_id bigint
);
ALTER TABLE turma ADD CONSTRAINT turma_pkey PRIMARY KEY (id);
ALTER TABLE turma ADD CONSTRAINT turma_curso_fk FOREIGN KEY (curso_id) REFERENCES curso(id);
ALTER TABLE turma ADD CONSTRAINT turma_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE turma ADD CONSTRAINT turma_avaliacaoturma_fk FOREIGN KEY (avaliacaoturma_id) REFERENCES avaliacaoturma(id);
CREATE SEQUENCE turma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE diaturma (
    id bigint NOT NULL,
    dia date,
    turma_id bigint
);
ALTER TABLE diaturma ADD CONSTRAINT diaturma_pkey PRIMARY KEY (id);
ALTER TABLE diaturma ADD CONSTRAINT diaturma_turma FOREIGN KEY (turma_id) REFERENCES turma(id);
CREATE SEQUENCE diaturma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
ALTER TABLE ONLY avaliacaodesempenho ADD CONSTRAINT avaliacaodesempenho_pkey PRIMARY KEY (id);
ALTER TABLE ONLY avaliacaodesempenho ADD CONSTRAINT avaliacaodesempenho_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);
CREATE SEQUENCE avaliacaodesempenho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE colaboradorquestionario (
    id bigint NOT NULL,
    colaborador_id bigint,
    questionario_id bigint,
    respondida boolean,
    respondidaEm date,
    turma_id bigint,
    candidato_id bigint,
    avaliacao_id bigint,
    performance double precision,
    observacao text,
    avaliacaodesempenho_id bigint,
    avaliador_id bigint
);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_pkey PRIMARY KEY (id);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_turma_fk FOREIGN KEY (turma_id) REFERENCES turma(id);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao(id);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_avaliacaodesempenho_fk FOREIGN KEY (avaliacaodesempenho_id) REFERENCES avaliacaodesempenho(id);
ALTER TABLE ONLY colaboradorquestionario ADD CONSTRAINT colaboradorquestionario_avaliador_fk FOREIGN KEY (avaliador_id) REFERENCES colaborador(id);

CREATE SEQUENCE colaboradorquestionario_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_pkey PRIMARY KEY (id);
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_colaboradorquestionario_fk FOREIGN KEY (colaboradorquestionario_id) REFERENCES colaboradorquestionario(id);
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_pergunta_fk FOREIGN KEY (pergunta_id) REFERENCES pergunta(id);
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_resposta_fk FOREIGN KEY (resposta_id) REFERENCES resposta(id);
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);
ALTER TABLE ONLY colaboradorresposta ADD CONSTRAINT colaboradorresposta_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);
CREATE SEQUENCE colaboradorresposta_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
ALTER TABLE colaboradorturma ADD CONSTRAINT colaboradorturma_pkey PRIMARY KEY (id);
ALTER TABLE colaboradorturma ADD CONSTRAINT colaboradorturma_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE colaboradorturma ADD CONSTRAINT colaboradorturma_curso_fk FOREIGN KEY (curso_id) REFERENCES curso(id);
ALTER TABLE colaboradorturma ADD CONSTRAINT colaboradorturma_dnt_fk FOREIGN KEY (dnt_id) REFERENCES dnt(id);
ALTER TABLE colaboradorturma ADD CONSTRAINT colaboradorturma_prioridadetreinamento_fk FOREIGN KEY (prioridadetreinamento_id) REFERENCES prioridadetreinamento(id);
ALTER TABLE colaboradorturma ADD CONSTRAINT colaboradorturma_turma_fk FOREIGN KEY (turma_id) REFERENCES turma(id);
CREATE SEQUENCE colaboradorturma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE colaboradorpresenca (
    id bigint NOT NULL,
    presenca boolean NOT NULL,
    colaboradorturma_id bigint,
    diaturma_id bigint
);
ALTER TABLE colaboradorpresenca ADD CONSTRAINT colaboradorpresenca_pkey PRIMARY KEY (id);
ALTER TABLE colaboradorpresenca ADD CONSTRAINT colaboradorpresenca_colaboradorturma_fk FOREIGN KEY (colaboradorturma_id) REFERENCES colaboradorturma(id);
ALTER TABLE colaboradorpresenca ADD CONSTRAINT colaboradorpresenca_diaturma_fk FOREIGN KEY (diaturma_id) REFERENCES diaturma(id);
CREATE SEQUENCE colaboradorpresenca_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE entrevista (
    id bigint NOT NULL,
    ativa boolean,
    questionario_id bigint
);
ALTER TABLE ONLY entrevista ADD CONSTRAINT entrevista_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);
CREATE SEQUENCE entrevista_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE indice (
    id bigint NOT NULL,
    nome character varying(40) NOT NULL,
    codigoac character varying(12)
);
ALTER TABLE indice ADD CONSTRAINT indice_pkey PRIMARY KEY (id);
CREATE SEQUENCE indice_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE indicehistorico (
    id bigint NOT NULL,
    data date,
    valor double precision,
    indice_id bigint
);
ALTER TABLE indicehistorico ADD CONSTRAINT indicehistorico_pkey PRIMARY KEY (id);
ALTER TABLE indicehistorico ADD CONSTRAINT indicehistorico_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);
CREATE SEQUENCE indicehistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE faixasalarialhistorico (
    id bigint NOT NULL,
    data date,
    valor double precision,
    tipo integer NOT NULL,
    quantidade double precision,
    faixasalarial_id bigint,
    indice_id bigint,
    status Integer
);
ALTER TABLE faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_pkey PRIMARY KEY (id);
ALTER TABLE faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);
ALTER TABLE faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);
CREATE SEQUENCE faixasalarialhistorico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


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
    tipoSalarioProposto Integer,
    indiceproposto_id bigint,
    quantidadeIndiceProposto double precision,
    tipoSalarioatual Integer,
    indiceatual_id bigint,
	quantidadeIndiceatual double precision,
	observacao character varying(100),
	faixasalarialatual_id bigint,
	faixasalarialproposta_id bigint
);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_pkey PRIMARY KEY (id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_ambiente_atual_fk FOREIGN KEY (ambienteatual_id) REFERENCES ambiente(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_ambiente_proposto_fk FOREIGN KEY (ambienteproposto_id) REFERENCES ambiente(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_areaorganizacional_atual_fk FOREIGN KEY (areaorganizacionalatual_id) REFERENCES areaorganizacional(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_areaorganizacional_proposta_fk FOREIGN KEY (areaorganizacionalproposta_id) REFERENCES areaorganizacional(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_estabelecimento_atual_fk FOREIGN KEY (estabelecimentoatual_id) REFERENCES estabelecimento(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_estabelecimento_proposto_fk FOREIGN KEY (estabelecimentoproposto_id) REFERENCES estabelecimento(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_funcao_atual_fk FOREIGN KEY (funcaoatual_id) REFERENCES funcao(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_funcao_proposta_fk FOREIGN KEY (funcaoproposta_id) REFERENCES funcao(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_tabelareajustecolaborador_fk FOREIGN KEY (tabelareajustecolaborador_id) REFERENCES tabelareajustecolaborador(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_indiceproposto_fk FOREIGN KEY (indiceproposto_id) REFERENCES indice(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_indiceatual_fk FOREIGN KEY (indiceatual_id) REFERENCES indice(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_faixasalarialatual_fk FOREIGN KEY (faixasalarialatual_id) REFERENCES faixasalarial(id);
ALTER TABLE reajustecolaborador ADD CONSTRAINT reajustecolaborador_faixasalarialproposta_fk FOREIGN KEY (faixasalarialproposta_id) REFERENCES faixasalarial(id);
CREATE SEQUENCE reajustecolaborador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
    tipoSalario Integer,
    indice_id bigint,
    quantidadeIndice double precision,
    faixasalarial_id bigint,
    reajustecolaborador_id bigint,
    status Integer,
    movimentoSalarialId bigint
);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_pkey PRIMARY KEY (id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_data_colaborador_uk UNIQUE (data, colaborador_id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_areaorganizacional_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_funcao_fk FOREIGN KEY (funcao_id) REFERENCES funcao(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_historicocolaborador_fk FOREIGN KEY (historicoanterior_id) REFERENCES historicocolaborador(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_indice_fk FOREIGN KEY (indice_id) REFERENCES indice(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_faixasalarial_fk FOREIGN KEY (faixasalarial_id) REFERENCES faixasalarial(id);
ALTER TABLE historicocolaborador ADD CONSTRAINT historicocolaborador_reajustecolaborador_fk FOREIGN KEY (reajustecolaborador_id) REFERENCES reajustecolaborador(id);
CREATE SEQUENCE historicocolaborador_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE certificacao (
    id bigint NOT NULL,
    nome character varying(100),
    empresa_id bigint
);
ALTER TABLE certificacao ADD CONSTRAINT certificacao_pkey PRIMARY KEY (id);
ALTER TABLE certificacao ADD CONSTRAINT certificacao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE certificacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE certificacao_curso (
    certificacaos_id bigint NOT NULL,
    cursos_id bigint NOT NULL
);
ALTER TABLE certificacao_curso ADD CONSTRAINT certificacao_curso_certificacao_fk FOREIGN KEY (certificacaos_id) REFERENCES certificacao(id);
ALTER TABLE certificacao_curso ADD CONSTRAINT certificacao_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id);

CREATE TABLE faixasalarial_certificacao (
    faixasalarials_id bigint NOT NULL,
    certificacaos_id bigint NOT NULL
);
ALTER TABLE faixasalarial_certificacao ADD CONSTRAINT faixasalarial_certificacao_certificacao_fk FOREIGN KEY (certificacaos_id) REFERENCES certificacao(id);
ALTER TABLE faixasalarial_certificacao ADD CONSTRAINT faixasalarial_certificacao_faixasalarial_fk FOREIGN KEY (faixasalarials_id) REFERENCES faixasalarial(id);

CREATE TABLE mensagem (
    id bigint NOT NULL,
    remetente character varying(100),
    link character varying(200),
    data timestamp,
    texto text
);
ALTER TABLE mensagem ADD CONSTRAINT mensagem_pkey PRIMARY KEY (id);
CREATE SEQUENCE mensagem_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE usuariomensagem (
    id bigint NOT NULL,
    usuario_id bigint,
    mensagem_id bigint,
    empresa_id bigint,
    lida boolean NOT NULL
);
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_pkey PRIMARY KEY (id);
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_mensagem_fk FOREIGN KEY (mensagem_id) REFERENCES mensagem(id);
ALTER TABLE usuariomensagem ADD CONSTRAINT usuariomensagem_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);
CREATE SEQUENCE usuariomensagem_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE avaliacaocurso (
    id bigint NOT NULL,
    titulo character varying(100),
    tipo character(1),
    minimoaprovacao double precision
);
ALTER TABLE avaliacaocurso ADD CONSTRAINT avaliacaocurso_pkey PRIMARY KEY (id);
CREATE SEQUENCE avaliacaocurso_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE curso_avaliacaocurso (
    cursos_id bigint NOT NULL,
    avaliacaocursos_id bigint NOT NULL
);
ALTER TABLE curso_avaliacaocurso ADD CONSTRAINT curso_avaliacaocurso_avaliacaocurso_fk FOREIGN KEY (avaliacaocursos_id) REFERENCES avaliacaocurso(id);
ALTER TABLE curso_avaliacaocurso ADD CONSTRAINT curso_avaliacaocurso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso(id);

CREATE TABLE aproveitamentoavaliacaocurso (
    id bigint NOT NULL,
    colaboradorturma_id bigint,
    avaliacaocurso_id bigint,
    valor double precision
);
ALTER TABLE aproveitamentoavaliacaocurso ADD CONSTRAINT aproveitamentoavaliacaocurso_pkey PRIMARY KEY (id);
ALTER TABLE aproveitamentoavaliacaocurso ADD CONSTRAINT aproveitamentoavaliacaocurso_colaboradorturma_fk FOREIGN KEY (colaboradorturma_id) REFERENCES colaboradorturma(id);
ALTER TABLE aproveitamentoavaliacaocurso ADD CONSTRAINT aproveitamentoavaliacaocurso_avaliacaocurso_fk FOREIGN KEY (avaliacaocurso_id) REFERENCES avaliacaocurso(id);
CREATE SEQUENCE aproveitamentoavaliacaocurso_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE clinicaautorizada_exame (
    clinicaautorizada_id bigint NOT NULL,
    exames_id bigint NOT NULL
);
ALTER TABLE clinicaautorizada_exame ADD CONSTRAINT clinicaautorizada_exame_exame_fk FOREIGN KEY (exames_id) REFERENCES exame(id);
ALTER TABLE clinicaautorizada_exame ADD CONSTRAINT clinicaautorizada_exame_clinicaautorizada_fk FOREIGN KEY (clinicaautorizada_id) REFERENCES clinicaautorizada(id);

CREATE TABLE prontuario (
    id bigint NOT NULL,
    descricao text,
    data date,
    colaborador_id bigint,
    usuario_id bigint
);
ALTER TABLE prontuario ADD CONSTRAINT prontuario_pkey PRIMARY KEY (id);
ALTER TABLE prontuario ADD CONSTRAINT prontuario_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE prontuario ADD CONSTRAINT prontuario_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);
CREATE SEQUENCE prontuario_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_pkey PRIMARY KEY (id);
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_candidato_fk FOREIGN KEY (candidato_id) REFERENCES candidato(id);
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_medicocoordenador_fk FOREIGN KEY (medicocoordenador_id) REFERENCES medicocoordenador(id);
ALTER TABLE solicitacaoexame ADD CONSTRAINT solicitacaoexame_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE solicitacaoexame_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE fichaMedica (
    id bigint NOT NULL,
    ativa boolean,
    rodape text,
    questionario_id bigint
);
ALTER TABLE fichaMedica ADD CONSTRAINT fichaMedica_pkey PRIMARY KEY (id);
ALTER TABLE ONLY fichaMedica ADD CONSTRAINT fichaMedica_questionario_fk FOREIGN KEY (questionario_id) REFERENCES questionario(id);
CREATE SEQUENCE fichaMedica_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE examesolicitacaoexame (
    id bigint NOT NULL,
    periodicidade int,
    exame_id bigint,
    solicitacaoexame_id bigint,
    clinicaautorizada_id bigint,
    realizacaoexame_id bigint
);
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_pkey PRIMARY KEY (id);
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_solicitacaoexame_fk FOREIGN KEY (solicitacaoexame_id) REFERENCES solicitacaoexame(id);
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_clinicaautorizada_fk FOREIGN KEY (clinicaautorizada_id) REFERENCES clinicaautorizada(id);
ALTER TABLE examesolicitacaoexame ADD CONSTRAINT examesolicitacaoexame_realizacaoexame_fk FOREIGN KEY (realizacaoexame_id) REFERENCES realizacaoexame(id);
CREATE INDEX CONCURRENTLY solicitacaoexame_fkey ON examesolicitacaoexame (solicitacaoexame_id);
CREATE SEQUENCE examesolicitacaoexame_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE solicitacaoepi (
    id bigint NOT NULL,
    data date,
    entregue boolean,
    colaborador_id bigint,
    cargo_id bigint,
    empresa_id bigint
);
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_pkey PRIMARY KEY (id);
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id);
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE solicitacaoepi ADD CONSTRAINT solicitacaoepi_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE solicitacaoepi_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE solicitacaoepi_item (
	id bigint NOT NULL,
	epi_id bigint,
	solicitacaoepi_id bigint,
	qtdSolicitado integer NOT NULL,
	qtdEntregue integer NOT NULL
);
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_pkey PRIMARY KEY (id);
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_solicitacaoepi_fk FOREIGN KEY (solicitacaoepi_id) REFERENCES solicitacaoepi(id);
ALTER TABLE solicitacaoepi_item ADD CONSTRAINT solicitacaoepi_item_epi_fk FOREIGN KEY (epi_id) REFERENCES epi(id);
CREATE SEQUENCE solicitacaoepi_item_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE eleicao (
    id bigint NOT NULL,
    posse date,
    votacaoIni date,
    votacaoFim date,
    horarioVotacaoIni character varying(20),
    horarioVotacaoFim character varying(20),
    qtdVotoNulo integer NOT NULL,
    qtdVotoBranco integer NOT NULL,
    inscricaoCandidatoIni date,
    inscricaoCandidatoFim date,
    localInscricao character varying(100),
    localVotacao character varying(100),
    empresa_id bigint,
    sindicato character varying(100),
    apuracao date,
    horarioApuracao character varying(20),
    localApuracao character varying(100),
    descricao character varying(100),
    textoAtaEleicao text,
    estabelecimento_id bigint
);

ALTER TABLE eleicao ADD CONSTRAINT eleicao_pkey PRIMARY KEY(id);
ALTER TABLE eleicao ADD CONSTRAINT eleicao_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE eleicao ADD CONSTRAINT eleicao_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);
CREATE SEQUENCE eleicao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE etapaprocessoeleitoral (
    id bigint NOT NULL,
    nome character varying(100),
    prazolegal character varying(100),
    prazo integer,
    data date,
    eleicao_id bigint,
    empresa_id bigint
);

ALTER TABLE etapaprocessoeleitoral ADD CONSTRAINT etapaprocessoeleitoral_pkey PRIMARY KEY(id);
ALTER TABLE etapaprocessoeleitoral ADD CONSTRAINT etapaprocessoeleitoral_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE etapaprocessoeleitoral ADD CONSTRAINT etapaprocessoeleitoral_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);
CREATE SEQUENCE etapaprocessoeleitoral_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE candidatoeleicao (
    id bigint NOT NULL,
    qtdVoto integer,
    eleito boolean,
    candidato_id bigint,
    eleicao_id bigint
);

ALTER TABLE candidatoeleicao ADD CONSTRAINT candidatoeleicao_pkey PRIMARY KEY(id);
ALTER TABLE candidatoeleicao ADD CONSTRAINT candidatoeleicao_candidato_fk FOREIGN KEY (candidato_id) REFERENCES colaborador(id);
ALTER TABLE candidatoeleicao ADD CONSTRAINT candidatoeleicao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);
CREATE SEQUENCE candidatoeleicao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE comissaoeleicao (
    id bigint NOT NULL,
    colaborador_id bigint,
    eleicao_id bigint,
	funcao character varying(20)
);

ALTER TABLE comissaoeleicao ADD CONSTRAINT comissaoeleicao_pkey PRIMARY KEY(id);
ALTER TABLE comissaoeleicao ADD CONSTRAINT comissaoeleicao_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE comissaoeleicao ADD CONSTRAINT comissaoeleicao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);
CREATE SEQUENCE comissaoeleicao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE comissao (
    id bigint NOT NULL,
	dataIni date,
	dataFim date,
	ataPosseTexto1 text,
	ataPosseTexto2 text,
    eleicao_id bigint
);
ALTER TABLE comissao ADD CONSTRAINT comissao_pkey PRIMARY KEY(id);
ALTER TABLE comissao ADD CONSTRAINT comissao_eleicao_fk FOREIGN KEY (eleicao_id) REFERENCES eleicao(id);
CREATE SEQUENCE comissao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE comissaoperiodo (
    id bigint NOT NULL,
   	aPartirDe date,
	comissao_id bigint
);

ALTER TABLE comissaoperiodo ADD CONSTRAINT comissaoperiodo_pkey PRIMARY KEY(id);
ALTER TABLE comissaoperiodo ADD CONSTRAINT comissaoperiodo_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);
CREATE SEQUENCE comissaoperiodo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE comissaomembro (
    id bigint NOT NULL,
	funcao character varying(1),
	tipo character varying(1),
	colaborador_id bigint,
	comissaoperiodo_id bigint
);

ALTER TABLE comissaomembro ADD CONSTRAINT comissaomembro_pkey PRIMARY KEY(id);
ALTER TABLE comissaomembro ADD CONSTRAINT comissaomembro_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE comissaomembro ADD CONSTRAINT comissaomembro_comissaoperiodo_fk FOREIGN KEY (comissaoperiodo_id) REFERENCES comissaoperiodo(id);
CREATE SEQUENCE comissaomembro_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE comissaoreuniao (
	id bigint NOT NULL,
	data date,
	descricao character varying(100),
	localizacao character varying(100),
	horario character varying(20),
	tipo character varying(1),
	ata text,
	comissao_id bigint,
	obsReuniaoAnterior text
);

ALTER TABLE comissaoreuniao ADD CONSTRAINT comissaoreuniao_pkey PRIMARY KEY(id);
ALTER TABLE comissaoreuniao ADD CONSTRAINT comissaoreuniao_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);
CREATE SEQUENCE comissaoreuniao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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

ALTER TABLE comissaoplanotrabalho ADD CONSTRAINT comissaoplanotrabalho_pkey PRIMARY KEY(id);
ALTER TABLE comissaoplanotrabalho ADD CONSTRAINT comissaoplanotrabalho_comissao_fk FOREIGN KEY (comissao_id) REFERENCES comissao(id);
ALTER TABLE comissaoplanotrabalho ADD CONSTRAINT comissaoplanotrabalho_responsavel_fk FOREIGN KEY (responsavel_id) REFERENCES colaborador(id);
CREATE SEQUENCE comissaoplanotrabalho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE comissaoreuniaopresenca (
	id bigint NOT NULL,
	comissaoreuniao_id bigint,
	colaborador_id bigint,
	presente boolean,
	justificativaFalta character varying(100)
);

ALTER TABLE comissaoreuniaopresenca ADD CONSTRAINT comissaoreuniaopresenca_pkey PRIMARY KEY(id);
ALTER TABLE comissaoreuniaopresenca ADD CONSTRAINT comissaoreuniaopresenca_comissaoreuniao_fk FOREIGN KEY (comissaoreuniao_id) REFERENCES comissaoreuniao(id);
ALTER TABLE comissaoreuniaopresenca ADD CONSTRAINT comissaoreuniaopresenca_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
CREATE SEQUENCE comissaoreuniaopresenca_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE afastamento (
	id bigint NOT NULL,
	inss boolean,
	descricao character varying(100)
);

ALTER TABLE afastamento ADD CONSTRAINT afastamento_pkey PRIMARY KEY(id);
CREATE SEQUENCE afastamento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE colaboradorafastamento (
	id bigint NOT NULL,
	inicio date,
	fim date,
	mediconome character varying(100),
	medicocrm  character varying(20),
	observacao text,
	cid character varying(10),
	afastamento_id bigint,
	colaborador_id bigint
);

ALTER TABLE colaboradorafastamento ADD CONSTRAINT colaboradorafastamento_pkey PRIMARY KEY(id);
ALTER TABLE colaboradorafastamento ADD CONSTRAINT colaboradorafastamento_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);
ALTER TABLE colaboradorafastamento ADD CONSTRAINT colaboradorafastamento_afastamento_fk FOREIGN KEY (afastamento_id) REFERENCES afastamento(id);
CREATE SEQUENCE colaboradorafastamento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE FUNCTION insere_etapa(CHARACTER, CHARACTER, INTEGER, BIGINT) RETURNS VOID AS '
BEGIN
	EXECUTE ''INSERT INTO ETAPAPROCESSOELEITORAL
			(ID, NOME, PRAZOLEGAL, PRAZO,ELEICAO_ID, EMPRESA_ID, DATA)
			VALUES (nextval('' || quote_literal(''etapaprocessoeleitoral_sequence'') || '') ,''|| quote_literal($1) ||'', ''|| quote_literal($2) ||'',''|| $3 ||'',NULL,''|| $4 ||'',NULL)'';

END
' LANGUAGE plpgsql;

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
' LANGUAGE plpgsql;

select insere_etapas_eleicao();
drop function insere_etapas_eleicao();
drop function insere_etapa(CHARACTER, CHARACTER, INTEGER, BIGINT);

CREATE FUNCTION to_ascii(bytea, name)
RETURNS text STRICT AS 'to_ascii_encname' LANGUAGE internal;

CREATE OR REPLACE FUNCTION normalizar(a_string text)
RETURNS text AS '
BEGIN
	RETURN to_ascii(convert_to(a_string, ''latin1''), ''latin1'');
END
' LANGUAGE plpgsql;

CREATE TABLE extintor (
	id bigint NOT NULL,
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
);

ALTER TABLE extintor ADD CONSTRAINT extintor_pkey PRIMARY KEY(id);
ALTER TABLE extintor ADD CONSTRAINT extintor_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE extintor ADD CONSTRAINT extintor_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);
CREATE SEQUENCE extintor_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE extintorinspecao (
	id bigint NOT NULL,
	data date,
	empresaResponsavel character varying(50),
	observacao text,
	extintor_id bigint
);

ALTER TABLE extintorinspecao ADD CONSTRAINT extintorinspecao_pkey PRIMARY KEY(id);
ALTER TABLE extintorinspecao ADD CONSTRAINT extintorinspecao_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);
CREATE SEQUENCE extintorinspecao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE extintorinspecaoitem (
	id bigint NOT NULL,
	descricao character varying(50)
);
ALTER TABLE extintorinspecaoitem ADD CONSTRAINT extintorinspecaoitem_pkey PRIMARY KEY(id);
CREATE SEQUENCE extintorinspecaoitem_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE extintorinspecao_extintorinspecaoitem (
	extintorinspecao_id bigint NOT NULL,
	itens_id bigint NOT NULL
);
ALTER TABLE extintorinspecao_extintorinspecaoitem ADD CONSTRAINT extintorinspecao_extintorinspecaoitem_extintorinspecao_fk FOREIGN KEY (extintorinspecao_id) REFERENCES extintorinspecao(id);
ALTER TABLE extintorinspecao_extintorinspecaoitem ADD CONSTRAINT extintorinspecao_extintorinspecaoitem_extintorinspecaoitem_fk FOREIGN KEY (itens_id) REFERENCES extintorinspecaoitem(id);

CREATE TABLE extintormanutencao (
		id bigint NOT NULL,
		saida date,
		retorno date,
		motivo character varying(1),
		outroMotivo character varying(50),
		observacao text,
		extintor_id bigint
);

ALTER TABLE extintormanutencao ADD CONSTRAINT extintormanutencao_pkey PRIMARY KEY(id);
ALTER TABLE extintormanutencao ADD CONSTRAINT extintormanutencao_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);
CREATE SEQUENCE extintormanutencao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE extintormanutencaoservico (
	id bigint NOT NULL,
	descricao character varying(50)
);
ALTER TABLE extintormanutencaoservico ADD CONSTRAINT extintormanutencaoservico_pkey PRIMARY KEY(id);
CREATE SEQUENCE extintormanutencaoservico_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE extintormanutencao_extintormanutencaoservico (
	extintormanutencao_id bigint NOT NULL,
	servicos_id bigint NOT NULL
);
ALTER TABLE extintormanutencao_extintormanutencaoservico ADD CONSTRAINT extintormanutencao_fk FOREIGN KEY (extintormanutencao_id) REFERENCES extintormanutencao(id);
ALTER TABLE extintormanutencao_extintormanutencaoservico ADD CONSTRAINT extintormanutencaoservico_fk FOREIGN KEY (servicos_id) REFERENCES extintormanutencaoservico(id);

CREATE TABLE riscoambiente (
	id bigint NOT NULL,
    epceficaz boolean,
    historicoambiente_id bigint,
    risco_id bigint
);
ALTER TABLE riscoambiente ADD CONSTRAINT riscoambiente_pkey PRIMARY KEY (id);
ALTER TABLE riscoambiente ADD CONSTRAINT riscoambiente_historicoambiente_fk FOREIGN KEY (historicoambiente_id) REFERENCES historicoambiente(id);
ALTER TABLE riscoambiente ADD CONSTRAINT riscoambiente_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);
CREATE SEQUENCE riscoambiente_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE historicoambiente_epc (
    historicoambiente_id bigint NOT NULL,
    epcs_id bigint NOT NULL
);
ALTER TABLE historicoambiente_epc ADD CONSTRAINT historicoambiente_epc_histambiente_fk FOREIGN KEY (historicoambiente_id) REFERENCES historicoambiente(id);
ALTER TABLE historicoambiente_epc ADD CONSTRAINT historicoambiente_epc_epc_fk FOREIGN KEY (epcs_id) REFERENCES epc(id);


CREATE TABLE medicaorisco (
	id bigint NOT NULL,
    data date,
    ambiente_id bigint
);

ALTER TABLE medicaorisco ADD CONSTRAINT medicaorisco_pkey PRIMARY KEY(id);
ALTER TABLE medicaorisco ADD CONSTRAINT medicaorisco_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);
CREATE SEQUENCE medicaorisco_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE riscomedicaorisco (
	id bigint NOT NULL,
	descricaoppra text,
	descricaoltcat text,
	intensidademedida character varying(20),
	tecnicautilizada character varying(100),
	medicaorisco_id bigint,
	risco_id bigint
);

ALTER TABLE riscomedicaorisco ADD CONSTRAINT riscomedicaorisco_pkey PRIMARY KEY(id);
ALTER TABLE riscomedicaorisco ADD CONSTRAINT riscomedicaorisco_risco_fk FOREIGN KEY (risco_id) REFERENCES risco(id);
ALTER TABLE riscomedicaorisco ADD CONSTRAINT riscomedicaorisco_medicaorisco_fk FOREIGN KEY (medicaorisco_id) REFERENCES medicaorisco(id);
CREATE SEQUENCE riscomedicaorisco_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;


CREATE TABLE evento (
	id bigint NOT NULL,
	nome character varying(100)
);

ALTER TABLE evento ADD CONSTRAINT evento_pkey PRIMARY KEY(id);
CREATE SEQUENCE evento_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE agenda (
	id bigint NOT NULL,
	data date,
	evento_id bigint,
	estabelecimento_id bigint
);

ALTER TABLE agenda ADD CONSTRAINT agenda_pkey PRIMARY KEY(id);
ALTER TABLE ONLY agenda ADD CONSTRAINT agenda_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id); 
ALTER TABLE ONLY agenda ADD CONSTRAINT agenda_evento_fk FOREIGN KEY (evento_id) REFERENCES evento(id); 
CREATE SEQUENCE agenda_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE historicofuncao_epi (
    historicofuncao_id bigint NOT NULL,
    epis_id bigint NOT NULL
);
ALTER TABLE historicofuncao_epi ADD CONSTRAINT historicofuncao_epi_epi_fk FOREIGN KEY (epis_id) REFERENCES epi(id);
ALTER TABLE historicofuncao_epi ADD CONSTRAINT historicofuncao_epi_historicofuncao_fk FOREIGN KEY (historicofuncao_id) REFERENCES historicofuncao(id);

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
);       

ALTER TABLE configuracaoImpressaoCurriculo ADD CONSTRAINT configuracaoImpressaoCurriculo_pkey PRIMARY KEY(id);
ALTER TABLE configuracaoImpressaoCurriculo ADD CONSTRAINT configuracaoImpressaoCurriculo_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
ALTER TABLE configuracaoImpressaoCurriculo ADD CONSTRAINT configuracaoImpressaoCurriculo_usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);
CREATE SEQUENCE configuracaoImpressaoCurriculo_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
    atualizaPapeisIdsAPartirDe bigint DEFAULT null,
    diasLembretePeriodoExperiencia character varying(20),
    emaildosuportetecnico character varying(40),
    campoExtraColaborador boolean,
    exibirAbaDocumentos boolean DEFAULT true,
    codEmpresaSuporte character varying(10),
    codClienteSuporte character varying(10)
);
ALTER TABLE parametrosdosistema ADD CONSTRAINT parametrosdosistema_pkey PRIMARY KEY (id);
ALTER TABLE parametrosdosistema ADD CONSTRAINT parametrosdosistema_perfil_fk FOREIGN KEY (perfilpadrao_id) REFERENCES perfil(id);
ALTER TABLE parametrosdosistema ADD CONSTRAINT parametrosdosistema_exame_fk FOREIGN KEY (exame_id) REFERENCES exame(id);
CREATE SEQUENCE parametrosdosistema_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE periodoExperiencia (
	id bigint NOT NULL,
	dias int,
	empresa_id bigint
);

ALTER TABLE periodoExperiencia ADD CONSTRAINT periodoExperiencia_pkey PRIMARY KEY(id);
ALTER TABLE periodoExperiencia ADD CONSTRAINT periodoExperiencia_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);
CREATE SEQUENCE periodoExperiencia_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

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
);
CREATE SEQUENCE cliente_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
ALTER TABLE ONLY cliente ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);

CREATE TABLE cargo_etapaseletiva (
    cargo_id bigint NOT NULL,
    etapaseletivas_id bigint NOT NULL
);
ALTER TABLE cargo_etapaseletiva ADD CONSTRAINT cargo_etapaseletiva_cargo_fk FOREIGN KEY (cargo_id) REFERENCES cargo(id); 
ALTER TABLE cargo_etapaseletiva ADD CONSTRAINT cargo_etapaseletiva_etapaseletiva_fk FOREIGN KEY (etapaseletivas_id) REFERENCES etapaseletiva(id); 
