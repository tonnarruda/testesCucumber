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
	tipo character varying(60)
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
	numero1 Integer
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
	solicitacao_id bigint
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
    atitude character varying(120)
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
    exibirAbaDocumentos boolean DEFAULT true
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

insert into public."usuario" ("id", "nome", "login", "senha", "acessosistema")
values (1, 'Fortes', 'fortes', 'MTIzNA==', true);

alter sequence usuario_sequence restart with 2;

INSERT INTO empresa(ID,NOME,CNPJ,RAZAOSOCIAL,codigoAC,acintegra,emailRemetente,emailRespSetorPessoal,maxcandidatacargo,logourl,exibirsalario,acurlsoap,acurlwsdl) VALUES (1,'Empresa Padrão','00000000','Empresa Padrão',null,false,'rh@empresapadrao.com.br','sp@empresapadrao.com.br', 5,'fortes.gif',true,'http://localhost:1024/soap/IAcPessoal','http://localhost:1024/wsdl/IAcPessoal');
alter sequence empresa_sequence restart with 2;

INSERT INTO estabelecimento (id, nome, complementocnpj, empresa_id) values (1,'Estabelecimento Padrão','0000',1);
alter sequence estabelecimento_sequence restart with 2;


INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (382, 'ROLE_AVALDESEMPENHO', 'Aval. Desempenho', '#', 4, true, 'v', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (365, 'ROLE_T&D', 'T&D', '#', 5, true, 'T', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (373, 'ROLE_COLAB', 'Info. Funcionais', '#', 6, true, 'I', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (75, 'ROLE_SESMT', 'SESMT', '#', 8, true, 'S', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (37, 'ROLE_UTI', 'Utilitários', '#', 9, true, 'U', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (361, 'ROLE_C&S', 'C&S', '#', 2, true, 'C', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (353, 'ROLE_PES', 'Pesquisas', '#', 3, true, 'P', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (357, 'ROLE_R&S', 'R&S', '#', 1, true, 'R', NULL);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (463, 'INATIVOS', 'Inativos', '#', 99, true, 37);

-- Modulo R&S
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (461, 'ROLE_R&S_IND', 'Indicadores', '#', 4, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (358, 'ROLE_R&S_CAD', 'Cadastros', '#', 1, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (2, 'ROLE_CAD_CANDIDATO', 'Candidatos', '/captacao/candidato/list.action', 1, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (3, 'ROLE_CAD_ETAPA', 'Etapas Seletivas', '/captacao/etapaSeletiva/list.action', 2, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (6, 'ROLE_CAD_BDS_EMPRESA', 'Empresas BDS', '/captacao/empresaBds/list.action', 3, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (4, 'ROLE_CAD_AREA', 'Áreas de Interesse', '/geral/areaInteresse/list.action', 4, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (397, 'ROLE_AREAFORMACAO', 'Áreas de Formação', '/geral/areaFormacao/list.action', 5, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (57, 'ROLE_MOTIVO_SOLICITACAO', 'Motivos de Solicitação de Pessoal', '/captacao/motivoSolicitacao/list.action', 6, true, 358);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (359, 'ROLE_R&S_MOV', 'Movimentações', '#', 2, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (21, 'ROLE_MOV_SOLICITACAO', 'Solicitação de Pessoal', '/captacao/solicitacao/list.action', 1, true, 359);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (50, 'ROLE_BD_SOLIDARIO', 'Banco de Dados Solidário', '/captacao/candidato/prepareBusca.action?BDS=true', 2, true, 463);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (360, 'ROLE_R&S_REL', 'Relatórios', '#', 3, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (46, 'ROLE_REL_SOLICITACAO', 'Solicitações Abertas', '/captacao/solicitacao/prepareRelatorio.action', 1, true, 360);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (48, 'ROLE_REL_PROCESSO_SELETIVO', 'Processos Seletivos', '/captacao/solicitacao/prepareRelatorioProcessoSeletivo.action', 2, true, 360);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (424, 'ROLE_REL_AVALIACAO_CANDIDATOS', 'Avaliações de Candidatos', '/captacao/candidato/prepareRelatorioAvaliacaoCandidatos.action', 3, true, 360);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (56, 'ROLE_LIBERA_SOLICITACAO', 'Liberador de Solicitação', '#', 4, false, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (45, 'ROLE_MOV_SOLICITACAO_SELECAO', 'Recrutador(a)', '#', 4, false, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (22, 'ROLE_MOV_SOLICITACAO_CANDIDATO', 'Ver Candidatos da Solicitação', '#', 4, false, 357);
-- Fim R&S

-- Modulo C&S
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (462, 'ROLE_C&S_IND', 'Indicadores', '#', 4, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (362, 'ROLE_C&S_CAD', 'Cadastros', '#', 1, true, 361);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (9, 'ROLE_CAD_AREA', 'Áreas Organizacionais', '/geral/areaOrganizacional/list.action', 1, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (10, 'ROLE_CAD_GRUPO', 'Grupos Ocupacionais', '/cargosalario/grupoOcupacional/list.action', 2, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (5, 'ROLE_CAD_CONHECIMENTO', 'Conhecimentos', '/captacao/conhecimento/list.action', 3, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (11, 'ROLE_CAD_CARGO', 'Cargos e Faixas', '/cargosalario/cargo/list.action', 4, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (404, 'ROLE_CAD_INDICE', 'Índices', '/cargosalario/indice/list.action', 5, true, 362);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (363, 'ROLE_C&S_MOV', 'Movimentações', '#', 2, true, 361);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (26, 'ROLE_MOV_SIMULACAOREAJUSTE', 'Planejamentos de Realinhamentos', '/cargosalario/tabelaReajusteColaborador/list.action', 1, true, 363);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (49, 'ROLE_MOV_SOLICITACAOREAJUSTE', 'Solicitação de Realinhamento de C&S', '/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action', 2, true, 363);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (395, 'ROLE_DISSIDIO', 'Reajuste Coletivo', '/cargosalario/reajusteColaborador/prepareDissidio.action', 3, true, 363);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (478, 'ROLE_VER_AREAS', 'Visualizar todas as Áreas Organizacionais', '#', 0, false, 363);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (364, 'ROLE_C&S_REL', 'Relatórios', '#', 3, true, 361);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (54, 'ROLE_REL_CARGO', 'Descrição de Cargos', '/cargosalario/cargo/prepareRelatorioCargo.action', 1, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (471, 'ROLE_REL_COLAB_CARGO', 'Colaboradores por Cargo', '/cargosalario/cargo/prepareRelatorioColaboradorCargo.action', 2, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (35, 'ROLE_REL_SIMULACAOREAJUSTE', 'Realinhamentos', '/cargosalario/reajusteRelatorio/formFiltro.action', 3, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (396, 'ROLE_REL_AREAORGANIZACIONAL', 'Colaboradores por Área Organizacional', '/geral/areaOrganizacionalRelatorio/formFiltro.action', 4, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (406, 'ROLE_MOV_TABELA', 'Análise de Tabela Salarial', '/cargosalario/faixaSalarialHistorico/analiseTabelaSalarialFiltro.action', 5, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (407, 'ROLE_REL_PROJECAO_SALARIAL', 'Projeção Salarial', '/geral/colaborador/prepareProjecaoSalarialFiltro.action',6, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (473, 'ROLE_REL_SITUACOES', 'Situações', '/cargosalario/historicoColaborador/prepareRelatorioSituacoes.action', 7, true, 364);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (417, 'ROLE_TRANSFERIR_FAIXAS_AC', 'Transferir Faixas entre Cargos', '', 4, false, 361);
-- Fim C&S
-- Modulo Pesquisas
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (355, 'ROLE_PES_MOV', 'Movimentações', '#', 1, true, 353);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (28, 'ROLE_MOV_QUESTIONARIO', 'Pesquisas', '/pesquisa/pesquisa/list.action', 1, true, 355);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (401, 'ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO', 'Pode Respoder Pesquisa Por Outro Usuário', '#', 4, false, 353);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (59, 'ROLE_PESQUISA', 'Pode ver e responder Pesquisa', '#', 4, false, 353);
-- Fim Pesquisas
-- Modulo Aval. Desempenho
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (486, 'ROLE_REL_AVALIACAO', 'Relatórios', '#', 3, true, 382);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (481, 'ROLE_CAD_AVALIACAO', 'Cadastros', '#', 1, true, 382);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (482, 'ROLE_CAD_AVALIACAO', 'Modelos de Avaliação', '/avaliacao/modelo/list.action', 1, true, 481);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (384, 'ROLE_MOV_AVALIACAO', 'Movimentações', '#', 2, true, 382);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (484, 'ROLE_RESPONDE_AVALIACAO', 'Pode ver e responder Aval. Desempenho', '#', 3, false, 382);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (55, 'ROLE_MOV_AVALIACAO', 'Avaliações', '/avaliacao/desempenho/list.action', 1, true, 384);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (483, 'ROLE_MOV_AVALIACAO', 'Responder Avaliações', '/avaliacao/desempenho/avaliacaoDesempenhoQuestionarioList.action', 2, true, 384);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (487, 'ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO', 'Pode Respoder Avaliação Por Outro Usuário', '#', 0, false, 384);
-- Fim Aval. Desempenho
-- Modulo T&D
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (366, 'ROLE_T&D_CAD', 'Cadastros', '#', 1, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (13, 'ROLE_CAD_PRIORIDADETREINAMENTO', 'Prioridades de Treinamento', '/desenvolvimento/prioridadeTreinamento/list.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (23, 'ROLE_MOV_CURSO', 'Cursos/Treinamentos', '/desenvolvimento/curso/list.action', 2, true, 366);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (413, 'ROLE_MOV_AVALIACAO_CURSO', 'Avaliações dos Cursos ', '/desenvolvimento/avaliacaoCurso/list.action', 3, true, 366);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (419, 'ROLE_AVALIACAO_TURMA', 'Modelos de Avaliação de Turma', '/pesquisa/avaliacaoTurma/list.action', 4, true, 366);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (420, 'ROLE_CAD_CERTIFICACAO', 'Certificações', '/desenvolvimento/certificacao/list.action', 5, true, 366);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (367, 'ROLE_T&D_MOV', 'Movimentações', '#', 2, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (64, 'ROLE_MOV_CURSO_DNT', 'DNT', '/desenvolvimento/dnt/list.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (72, 'ROLE_MOV_CURSO_DNT_GESTOR', 'Preenchimento da DNT', '/desenvolvimento/dnt/list.action?gestor=true', 2, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (71, 'ROLE_FREQUENCIA', 'Frequência', '/desenvolvimento/turma/prepareFrequencia.action', 3, true, 367);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (414, 'ROLE_MOV_PLANO_TREINAMENTO', 'Plano de Treinamento', '/desenvolvimento/turma/filtroPlanoTreinamento.action', 4, true, 367);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (368, 'ROLE_T&D_REL', 'Relatórios', '#', 3, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (31, 'ROLE_REL_MATRIZ', 'Matriz de Qualificação', '/desenvolvimento/turma/prepareImprimirMatriz.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (32, 'ROLE_REL_PLANO', 'Plano de Treinamento', '/desenvolvimento/turma/prepareImprimirTurma.action', 2, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (33, 'ROLE_REL_SEM_INDICACAO', 'Colaboradores sem Indicação de Trein.', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorSemIndicacao.action', 3, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (43, 'ROLE_REL_DESENVOLVIMENTO_LISTA_PRESENCA', 'Lista de Frequência', '/desenvolvimento/relatorioPresenca/prepareRelatorio.action', 4, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (61, 'ROLE_CERTIFICADO_CURSO', 'Certificados', '/desenvolvimento/turma/prepareImprimirCertificado.action', 5, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (418, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Cronograma de Treinamento', '/desenvolvimento/turma/filtroCronogramaTreinamento.action', 6, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (415, 'ROLE_REL_COLABORADOR_SEM_TREINAMENTO', 'Colaboradores sem Treinamentos', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action', 7, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (416, 'ROLE_REL_COLABORADOR_COM_TREINAMENTO', 'Colaboradores com Treinamentos', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action?comTreinamento=true', 8, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (421, 'ROLE_REL_MATRIZ_TREINAMENTO', 'Matriz de Treinamentos', '/desenvolvimento/certificacao/matrizTreinamento.action', 9, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (422, 'ROLE_REL_HISTORICO_TREINAMENTOS', 'Histórico de Treinamentos', '/desenvolvimento/colaboradorTurma/prepareFiltroHistoricoTreinamentos.action', 10, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (465, 'ROLE_REL_COLABORADORES_CERTIFICACOES', 'Colaboradores x Certificações', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorCertificacao.action', 11, true, 368);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (454, 'ROLE_T&D_REL', 'Painel de Indicadores', '/desenvolvimento/indicadores/list.action', 4, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (63, 'ROLE_MOV_TURMA', 'Pode Cadastrar Turma', '/desenvolvimento/turma/list.action', 4, false, 365);
-- Fim T&D
-- Modulo Info. Funcionais
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (374, 'ROLE_COLAB_CAD', 'Cadastros', '#', 1, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (15, 'ROLE_CAD_GRUPOGASTO', 'Grupos de Investimento', '/geral/grupoGasto/list.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (16, 'ROLE_CAD_GASTO', 'Investimentos', '/geral/gasto/list.action', 2, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (12, 'ROLE_CAD_BENEFICIO', 'Benefícios', '/geral/beneficio/list.action', 3, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (60, 'ROLE_CAD_MOTIVO_DEMISSAO', 'Motivos de Desligamento', '/geral/motivoDemissao/list.action', 4, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (402, 'ROLE_MOV_QUESTIONARIO', 'Modelos de Entrevistas de Desligamento', '/pesquisa/entrevista/list.action', 5, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (62, 'ROLE_CAD_OCORRENCIA', 'Tipos de Ocorrência', '/geral/ocorrencia/list.action', 6, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (8, 'ROLE_CAD_COLABORADOR', 'Colaboradores', '/geral/colaborador/list.action', 7, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (412, 'ROLE_CAD_INFO_PESSOAL', 'Atualizar meus dados', '/geral/colaborador/prepareUpdateInfoPessoais.action', 8, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (467, 'ROLE_CAD_PERIODOEXPERIENCIA', 'Períodos de Acompanham. de Experiência', '/avaliacao/periodoExperiencia/list.action', 3, true, 384);

-- Modulo Info. Funcionais Movimentacoes
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (469, 'ROLE_COLAB_MOV', 'Movimentações', '#', 2, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (470, 'ROLE_MOV_PERIODOEXPERIENCIA', 'Avaliação do Per. Experiência', '/avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action', 4, true, 384);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (480, 'ROLE_CAD_OCORRENCIA', 'Ocorrências', '/geral/colaboradorOcorrencia/list.action', 2, true, 469);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (29, 'ROLE_MOV_GASTO_GASTOEMPRESA', 'Investimentos da Empresa', '/geral/gastoEmpresa/list.action', 11, true, 463);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (377, 'ROLE_COLAB_REL', 'Relatórios', '#', 3, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (93, 'ROLE_REL_OCORRENCIA', 'Ocorrências', '/geral/ocorrencia/prepareRelatorioOcorrencia.action', 1, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (400, 'ROLE_REL_MOTIVO_DEMISSAO', 'Desligamentos', '/geral/motivoDemissao/prepareRelatorioMotivoDemissao.action', 2, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (403, 'ROLE_MOV_QUESTIONARIO', 'Resultados das Entrevistas', '/pesquisa/questionario/prepareResultadoEntrevista.action', 3, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (423, 'ROLE_REL_ANIVERSARIANTES', 'Aniversariantes do mês', '/geral/colaborador/prepareRelatorioAniversariantes.action', 4, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (472, 'ROLE_REL_ADMITIDOS', 'Admitidos', '/geral/colaborador/prepareRelatorioAdmitidos.action', 5, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (479, 'ROLE_CAD_PERIODOEXPERIENCIA', 'Resultado da Avaliação do Per. Experiência', '/avaliacao/avaliacaoExperiencia/prepareResultado.action', 1, true, 486);
-- Fim Info. Funcionais
-- Modulo Indicadores
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (73, 'ROLE_IND', 'Estatísticas de Vagas por Motivo', '/indicador/duracaoPreenchimentoVaga/prepareMotivo.action', 1, true, 461);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (69, 'ROLE_IND', 'Duração para Preenchimento de Vagas', '/indicador/duracaoPreenchimentoVaga/prepare.action', 2, true, 461);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (47, 'ROLE_REL_PRODUTIVIDADE', 'Produtividade do Setor', '/captacao/produtividade/prepareProdutividade.action', 3, true, 461);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (70, 'ROLE_REL_PROMOCAO', 'Promoções', '/cargosalario/historicoColaborador/prepareRelatorioPromocoes.action', 1, true, 462);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (398, 'ROLE_REL_TURNOVER', 'Turnover (rotatividade)', '/indicador/indicadorTurnOver/prepare.action', 2, true, 462);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (36, 'ROLE_REL_GASTOEMPRESA', 'Investimentos da Empresa', '/geral/gastoEmpresa/prepareImprimir.action', 6, true, 463);
-- Fim Indicadores
-- Modulo Utilitarios
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (390, 'ROLE_UTI', 'Cadastros', '#', 1, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (58, 'ROLE_UTI_EMPRESA', 'Empresas', '/geral/empresa/list.action', 1, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (18, 'ROLE_CAD_PERFIL', 'Perfis', '/acesso/perfil/list.action', 2, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (393, 'ROLE_CAD_ESTABELECIMENTO', 'Estabelecimentos', '/geral/estabelecimento/list.action', 3, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (19, 'ROLE_CAD_USUARIO', 'Usuários', '/acesso/usuario/list.action', 4, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (394, 'ROLE_CAD_BAIRRO', 'Bairros', '/geral/bairro/list.action', 5, true, 390);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (464, 'ROLE_IMPORTA_CADASTROS', 'Importar Cadastros', '/geral/empresa/prepareImportarCadastros.action', 8, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (38, 'ROLE_UTI_SENHA', 'Alterar Senha', '/acesso/usuario/prepareUpdateSenhaUsuario.action', 2, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (485, 'ROLE_CAMPO_EXTRA', 'Campos Extras', '/geral/configuracaoCampoExtra/prepareUpdate.action', 4, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (41, 'ROLE_UTI_CONFIGURACAO', 'Configurações', '/geral/parametrosDoSistema/prepareUpdate.action', 3, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (39, 'ROLE_UTI_AUDITORIA', 'Auditoria', '/security/auditoria/prepareList.action', 5, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (44, 'ROLE_UTI_HISTORICO_VERSAO', 'Histórico de Versões', '/geral/documentoVersao/list.action', 6, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (409, 'ROLE_UTI', 'Enviar Mensagem', '/geral/usuarioMensagem/prepareUpdate.action', 7, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (466, 'ROLE_UTI_EMPRESA', 'Sobre...', '/geral/empresa/sobre.action', 8, true, 37);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (408, 'ROLE_MOV_SOLICITACAO_REALINHAMENTO', 'Pode Solicitar Realinhamento', '', 4, false, 361);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (410, 'RECEBE_ALERTA_SETORPESSOAL', 'Recebe Mensagem do AC Pessoal', '', 5, false, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (411, 'ROLE_VISUALIZAR_PENDENCIA_AC', 'Visualizar as pendências do AC', '', 5, false, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (451, 'ROLE_LOGGING', 'Logs', '/logging/list.action', 8, true, 37);

-- Fim Utilitarios
-- Modulo SESMT
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (385, 'ROLE_SESMT', 'Cadastros', '#', 1, true, 75);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (456, 'ROLE_CAD_PCMSO', 'PCMSO', '#', 9, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (452, 'ROLE_CAD_EVENTO', 'Eventos', '/sesmt/evento/list.action', 1, true, 456);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (453, 'ROLE_CAD_AGENDA', 'Agenda', '/sesmt/agenda/list.action', 2, true, 456);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (76, 'ROLE_CAD_AMBIENTE', 'Ambientes', '/sesmt/ambiente/list.action', 5, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (77, 'ROLE_CAD_TIPO_EPI', 'Categorias de EPI', '/sesmt/tipoEPI/list.action', 1, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (78, 'ROLE_CAD_EPI', 'EPI', '/sesmt/epi/list.action', 2, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (86, 'ROLE_CAD_EPC', 'EPC', '/sesmt/epc/list.action', 3, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (79, 'ROLE_CAD_RISCO', 'Riscos', '/sesmt/risco/list.action', 4, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (89, 'ROLE_CAD_ENGENHEIRO_TRABALHO', 'Engenheiros Responsáveis', '/sesmt/engenheiroResponsavel/list.action', 6, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (90, 'ROLE_CAD_MEDICO_COORDENADOR', 'Médicos Coordenadores', '/sesmt/medicoCoordenador/list.action', 11, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (91, 'ROLE_CAD_CLINICA_AUTORIZADA', 'Clínicas e Médicos Autorizados', '/sesmt/clinicaAutorizada/list.action', 12, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (87, 'ROLE_CAD_EXAME', 'Exames', '/sesmt/exame/list.action', 10, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (427, 'ROLE_CAD_FICHAMEDICA', 'Modelos de Fichas Médicas', '/sesmt/fichaMedica/list.action', 13, true, 385);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (455, 'ROLE_CAD_ELEICAO', 'CIPA', '#', 8, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (437, 'ROLE_CAD_ETAPAPROCESSOELEITORAL', 'Etapas do Processo Eleitoral', '/sesmt/etapaProcessoEleitoral/list.action', 1, true, 455);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (438, 'ROLE_CAD_ELEICAO', 'Eleições', '/sesmt/eleicao/list.action', 2, true, 455);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (439, 'ROLE_CAD_ELEICAO', 'Comissões', '/sesmt/comissao/list.action', 3, true, 455);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (440, 'ROLE_CAD_AFASTAMENTO', 'Motivos de Afastamentos', '/sesmt/afastamento/list.action', 14, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (445, 'ROLE_CAD_EXTINTOR', 'Extintores', '/sesmt/extintor/list.action', 7, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (476, 'ROLE_FUNCAO', 'Funções', '/sesmt/funcao/listFiltro.action', 15, true, 385);


INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (386, 'ROLE_SESMT', 'Movimentações', '#', 2, true, 75);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (426, 'ROLE_CAD_SOLICITACAOEXAME', 'Solicitações/Atendimentos Médicos', '/sesmt/solicitacaoExame/list.action', 5, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (66, 'ROLE_SESMT_MUDANCA_FUNCAO', 'Mudança de Função', '/sesmt/funcao/mudancaFuncaoFiltro.action', 5, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (425, 'ROLE_CAD_PRONTUARIO', 'Registro de Prontuário', '/sesmt/prontuario/list.action', 6, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (428, 'ROLE_CAD_FICHAMEDICA', 'Fichas Médicas', '/sesmt/fichaMedica/listPreenchida.action', 7, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (433, 'ROLE_CAD_SOLICITACAOEPI', 'Solicitação de EPIs', '/sesmt/solicitacaoEpi/list.action', 2, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (435, 'ROLE_CAD_ENTREGAEPI', 'Entrega de EPIs', '/sesmt/solicitacaoEpi/list.action', 3, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (441, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/list.action', 8, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (443, 'ROLE_CAT', 'CATs (Acidentes de Trabalho)', '/sesmt/cat/list.action', 9, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (457, 'ROLE_CAD_EXTINTOR', 'Extintores', '#', 4, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (446, 'ROLE_CAD_EXTINTOR', 'Inspeção', '/sesmt/extintorInspecao/list.action', 1, true, 457);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (447, 'ROLE_CAD_EXTINTOR', 'Manutenção', '/sesmt/extintorManutencao/list.action', 2, true, 457);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (387, 'ROLE_SESMT', 'Relatórios', '#', 3, true, 75);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (488, 'ROLE_CAT', 'CATs (Acidentes de Trabalho)', '/sesmt/cat/prepareRelatorioCats.action', 16, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (388, 'ROLE_PPRA', 'PPRA e LTCAT', '/sesmt/ppra/prepareRelatorio.action', 1, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (389, 'ROLE_CAD_PCMSO', 'PCMSO', '/sesmt/pcmso/prepareRelatorio.action', 7, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (85, 'ROLE_PPP', 'PPP', '/sesmt/ppp/list.action', 2, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (429, 'ROLE_REL_PRONTUARIO', 'Prontuário', '/sesmt/prontuario/prepareRelatorioProntuario.action', 8, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (431, 'ROLE_REL_EXAMES_PREVISTOS', 'Exames Previstos', '/sesmt/exame/prepareRelatorioExamesPrevistos.action', 10, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (432, 'ROLE_REL_FICHA_EPI', 'Ficha de EPI', '/sesmt/epi/prepareImprimirFicha.action', 3, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (434, 'ROLE_CAD_EPICAVENCER', 'EPIs com CA a Vencer', '/sesmt/epi/prepareImprimirVencimentoCa.action', 4, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (436, 'ROLE_REL_EPIVENCIMENTO', 'EPIs com Prazo a Vencer', '/sesmt/solicitacaoEpi/prepareRelatorioVencimentoEpi.action', 5, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (442, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/prepareRelatorioAfastamentos.action', 11, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (448, 'ROLE_CAD_EXTINTOR', 'Extintores - Manutenção e Inspeção', '/sesmt/extintor/prepareRelatorio.action', 6, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (449, 'ROLE_CAD_FICHAMEDICA', 'Resultado de Fichas Médicas', '/sesmt/fichaMedica/prepareResultadoFichaMedica.action', 12, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (450, 'ROLE_CAD_MEDICAORISCO', 'Medição dos Riscos nos Ambientes', '/sesmt/medicaoRisco/list.action', 1, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (477, 'ROLE_AMBIENTE', 'Ambientes e Funções do Colaborador', '/cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action', 5, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (459, 'ROLE_CAD_SOLICITACAOEXAME', 'Atendimentos Médicos', '/sesmt/solicitacaoExame/prepareRelatorioAtendimentosMedicos.action', 14, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (460, 'ROLE_CAD_SOLICITACAOEXAME', 'Exames Realizados', '/sesmt/exame/prepareRelatorioExamesRealizados.action', 15, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (458, 'ROLE_FUNCAO', 'Distribuição de Colaboradores por Função', '/sesmt/funcao/prepareRelatorioQtdPorFuncao.action', 13, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (489, 'ROLE_REL_EPIVENCIMENTO', 'EPIs Entregues', '/sesmt/solicitacaoEpi/prepareRelatorioEntregaEpi.action', 4, true, 387);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (74, 'ROLE_FUNCAO', 'Funções', '/sesmt/funcao/list.action', 4, false, 75);
-- Fim SESMT

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (474, 'ROLE_COMPROU_SESMT', 'Exibir informações do SESMT', '#', 0, false, null);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (475, 'ROLE_CAD_CLIENTE', 'Clientes', '/geral/cliente/list.action', 10, false, null);

alter sequence papel_sequence restart with 490;

insert into public."perfil" ("id", "nome") values (1, 'Administrador');

insert into perfil_papel(perfil_id, papeis_id) values(1, 2);
insert into perfil_papel(perfil_id, papeis_id) values(1, 3);
insert into perfil_papel(perfil_id, papeis_id) values(1, 4);
insert into perfil_papel(perfil_id, papeis_id) values(1, 5);
insert into perfil_papel(perfil_id, papeis_id) values(1, 8);
insert into perfil_papel(perfil_id, papeis_id) values(1, 9);
insert into perfil_papel(perfil_id, papeis_id) values(1, 10);
insert into perfil_papel(perfil_id, papeis_id) values(1, 11);
insert into perfil_papel(perfil_id, papeis_id) values(1, 18);
insert into perfil_papel(perfil_id, papeis_id) values(1, 19);
insert into perfil_papel(perfil_id, papeis_id) values(1, 21);
insert into perfil_papel(perfil_id, papeis_id) values(1, 22);
insert into perfil_papel(perfil_id, papeis_id) values(1, 23);
insert into perfil_papel(perfil_id, papeis_id) values(1, 26);
insert into perfil_papel(perfil_id, papeis_id) values(1, 28);
insert into perfil_papel(perfil_id, papeis_id) values(1, 33);
insert into perfil_papel(perfil_id, papeis_id) values(1, 35);
insert into perfil_papel(perfil_id, papeis_id) values(1, 37);
insert into perfil_papel(perfil_id, papeis_id) values(1, 38);
insert into perfil_papel(perfil_id, papeis_id) values(1, 39);
insert into perfil_papel(perfil_id, papeis_id) values(1, 41);
insert into perfil_papel(perfil_id, papeis_id) values(1, 43);
insert into perfil_papel(perfil_id, papeis_id) values(1, 44);
insert into perfil_papel(perfil_id, papeis_id) values(1, 45);
insert into perfil_papel(perfil_id, papeis_id) values(1, 46);
insert into perfil_papel(perfil_id, papeis_id) values(1, 47);
insert into perfil_papel(perfil_id, papeis_id) values(1, 48);
insert into perfil_papel(perfil_id, papeis_id) values(1, 49);
insert into perfil_papel(perfil_id, papeis_id) values(1, 54);
insert into perfil_papel(perfil_id, papeis_id) values(1, 55);
insert into perfil_papel(perfil_id, papeis_id) values(1, 56);
insert into perfil_papel(perfil_id, papeis_id) values(1, 57);
insert into perfil_papel(perfil_id, papeis_id) values(1, 58);
insert into perfil_papel(perfil_id, papeis_id) values(1, 59);
insert into perfil_papel(perfil_id, papeis_id) values(1, 60);
insert into perfil_papel(perfil_id, papeis_id) values(1, 61);
insert into perfil_papel(perfil_id, papeis_id) values(1, 62);
insert into perfil_papel(perfil_id, papeis_id) values(1, 63);
insert into perfil_papel(perfil_id, papeis_id) values(1, 66);
insert into perfil_papel(perfil_id, papeis_id) values(1, 69);
insert into perfil_papel(perfil_id, papeis_id) values(1, 70);
insert into perfil_papel(perfil_id, papeis_id) values(1, 71);
insert into perfil_papel(perfil_id, papeis_id) values(1, 73);
insert into perfil_papel(perfil_id, papeis_id) values(1, 74);
insert into perfil_papel(perfil_id, papeis_id) values(1, 75);
insert into perfil_papel(perfil_id, papeis_id) values(1, 76);
insert into perfil_papel(perfil_id, papeis_id) values(1, 77);
insert into perfil_papel(perfil_id, papeis_id) values(1, 78);
insert into perfil_papel(perfil_id, papeis_id) values(1, 79);
insert into perfil_papel(perfil_id, papeis_id) values(1, 85);
insert into perfil_papel(perfil_id, papeis_id) values(1, 86);
insert into perfil_papel(perfil_id, papeis_id) values(1, 87);
insert into perfil_papel(perfil_id, papeis_id) values(1, 89);
insert into perfil_papel(perfil_id, papeis_id) values(1, 90);
insert into perfil_papel(perfil_id, papeis_id) values(1, 91);
insert into perfil_papel(perfil_id, papeis_id) values(1, 93);
insert into perfil_papel(perfil_id, papeis_id) values(1, 353);
insert into perfil_papel(perfil_id, papeis_id) values(1, 355);
insert into perfil_papel(perfil_id, papeis_id) values(1, 357);
insert into perfil_papel(perfil_id, papeis_id) values(1, 358);
insert into perfil_papel(perfil_id, papeis_id) values(1, 359);
insert into perfil_papel(perfil_id, papeis_id) values(1, 360);
insert into perfil_papel(perfil_id, papeis_id) values(1, 361);
insert into perfil_papel(perfil_id, papeis_id) values(1, 362);
insert into perfil_papel(perfil_id, papeis_id) values(1, 363);
insert into perfil_papel(perfil_id, papeis_id) values(1, 364);
insert into perfil_papel(perfil_id, papeis_id) values(1, 365);
insert into perfil_papel(perfil_id, papeis_id) values(1, 366);
insert into perfil_papel(perfil_id, papeis_id) values(1, 367);
insert into perfil_papel(perfil_id, papeis_id) values(1, 368);
insert into perfil_papel(perfil_id, papeis_id) values(1, 373);
insert into perfil_papel(perfil_id, papeis_id) values(1, 374);
insert into perfil_papel(perfil_id, papeis_id) values(1, 377);
insert into perfil_papel(perfil_id, papeis_id) values(1, 382);
insert into perfil_papel(perfil_id, papeis_id) values(1, 384);
insert into perfil_papel(perfil_id, papeis_id) values(1, 385);
insert into perfil_papel(perfil_id, papeis_id) values(1, 386);
insert into perfil_papel(perfil_id, papeis_id) values(1, 387);
insert into perfil_papel(perfil_id, papeis_id) values(1, 388);
insert into perfil_papel(perfil_id, papeis_id) values(1, 389);
insert into perfil_papel(perfil_id, papeis_id) values(1, 390);
insert into perfil_papel(perfil_id, papeis_id) values(1, 393);
insert into perfil_papel(perfil_id, papeis_id) values(1, 394);
insert into perfil_papel(perfil_id, papeis_id) values(1, 395);
insert into perfil_papel(perfil_id, papeis_id) values(1, 396);
insert into perfil_papel(perfil_id, papeis_id) values(1, 397);
insert into perfil_papel(perfil_id, papeis_id) values(1, 398);
insert into perfil_papel(perfil_id, papeis_id) values(1, 400);
insert into perfil_papel(perfil_id, papeis_id) values(1, 401);
insert into perfil_papel(perfil_id, papeis_id) values(1, 402);
insert into perfil_papel(perfil_id, papeis_id) values(1, 403);
insert into perfil_papel(perfil_id, papeis_id) values(1, 404);
insert into perfil_papel(perfil_id, papeis_id) values(1, 406);
insert into perfil_papel(perfil_id, papeis_id) values(1, 407);
insert into perfil_papel(perfil_id, papeis_id) values(1, 408);
insert into perfil_papel(perfil_id, papeis_id) values(1, 409);
insert into perfil_papel(perfil_id, papeis_id) values(1, 410);
insert into perfil_papel(perfil_id, papeis_id) values(1, 411);
insert into perfil_papel(perfil_id, papeis_id) values(1, 412);
insert into perfil_papel(perfil_id, papeis_id) values(1, 413);
insert into perfil_papel(perfil_id, papeis_id) values(1, 414);
insert into perfil_papel(perfil_id, papeis_id) values(1, 415);
insert into perfil_papel(perfil_id, papeis_id) values(1, 416);
insert into perfil_papel(perfil_id, papeis_id) values(1, 417);
insert into perfil_papel(perfil_id, papeis_id) values(1, 418);
insert into perfil_papel(perfil_id, papeis_id) values(1, 419);
insert into perfil_papel(perfil_id, papeis_id) values(1, 420);
insert into perfil_papel(perfil_id, papeis_id) values(1, 421);
insert into perfil_papel(perfil_id, papeis_id) values(1, 422);
insert into perfil_papel(perfil_id, papeis_id) values(1, 423);
insert into perfil_papel(perfil_id, papeis_id) values(1, 424);
insert into perfil_papel(perfil_id, papeis_id) values(1, 425);
insert into perfil_papel(perfil_id, papeis_id) values(1, 426);
insert into perfil_papel(perfil_id, papeis_id) values(1, 427);
insert into perfil_papel(perfil_id, papeis_id) values(1, 428);
insert into perfil_papel(perfil_id, papeis_id) values(1, 429);
insert into perfil_papel(perfil_id, papeis_id) values(1, 431);
insert into perfil_papel(perfil_id, papeis_id) values(1, 432);
insert into perfil_papel(perfil_id, papeis_id) values(1, 433);
insert into perfil_papel(perfil_id, papeis_id) values(1, 434);
insert into perfil_papel(perfil_id, papeis_id) values(1, 435);
insert into perfil_papel(perfil_id, papeis_id) values(1, 436);
insert into perfil_papel(perfil_id, papeis_id) values(1, 437);
insert into perfil_papel(perfil_id, papeis_id) values(1, 438);
insert into perfil_papel(perfil_id, papeis_id) values(1, 439);
insert into perfil_papel(perfil_id, papeis_id) values(1, 440);
insert into perfil_papel(perfil_id, papeis_id) values(1, 441);
insert into perfil_papel(perfil_id, papeis_id) values(1, 442);
insert into perfil_papel(perfil_id, papeis_id) values(1, 443);
insert into perfil_papel(perfil_id, papeis_id) values(1, 445);
insert into perfil_papel(perfil_id, papeis_id) values(1, 446);
insert into perfil_papel(perfil_id, papeis_id) values(1, 447);
insert into perfil_papel(perfil_id, papeis_id) values(1, 448);
insert into perfil_papel(perfil_id, papeis_id) values(1, 449);
insert into perfil_papel(perfil_id, papeis_id) values(1, 450);
insert into perfil_papel(perfil_id, papeis_id) values(1, 451);
insert into perfil_papel(perfil_id, papeis_id) values(1, 452);
insert into perfil_papel(perfil_id, papeis_id) values(1, 453);
insert into perfil_papel(perfil_id, papeis_id) values(1, 454);
insert into perfil_papel(perfil_id, papeis_id) values(1, 455);
insert into perfil_papel(perfil_id, papeis_id) values(1, 456);
insert into perfil_papel(perfil_id, papeis_id) values(1, 457);
insert into perfil_papel(perfil_id, papeis_id) values(1, 458);
insert into perfil_papel(perfil_id, papeis_id) values(1, 459);
insert into perfil_papel(perfil_id, papeis_id) values(1, 460);
insert into perfil_papel(perfil_id, papeis_id) values(1, 461);
insert into perfil_papel(perfil_id, papeis_id) values(1, 462);
insert into perfil_papel(perfil_id, papeis_id) values(1, 464);
insert into perfil_papel(perfil_id, papeis_id) values(1, 465);
insert into perfil_papel(perfil_id, papeis_id) values(1, 466);
insert into perfil_papel(perfil_id, papeis_id) values(1, 467);
insert into perfil_papel(perfil_id, papeis_id) values(1, 469);
insert into perfil_papel(perfil_id, papeis_id) values(1, 470);
insert into perfil_papel(perfil_id, papeis_id) values(1, 471);
insert into perfil_papel(perfil_id, papeis_id) values(1, 472);
insert into perfil_papel(perfil_id, papeis_id) values(1, 473);
insert into perfil_papel(perfil_id, papeis_id) values(1, 474);
insert into perfil_papel(perfil_id, papeis_id) values(1, 476);
insert into perfil_papel(perfil_id, papeis_id) values(1, 477);
insert into perfil_papel(perfil_id, papeis_id) values(1, 478);
insert into perfil_papel(perfil_id, papeis_id) values(1, 479);
insert into perfil_papel(perfil_id, papeis_id) values(1, 480);
insert into perfil_papel(perfil_id, papeis_id) values(1, 481);
insert into perfil_papel(perfil_id, papeis_id) values(1, 482);
insert into perfil_papel(perfil_id, papeis_id) values(1, 483);
insert into perfil_papel(perfil_id, papeis_id) values(1, 484);
insert into perfil_papel(perfil_id, papeis_id) values(1, 485);
insert into perfil_papel(perfil_id, papeis_id) values(1, 486);
insert into perfil_papel(perfil_id, papeis_id) values(1, 487);
insert into perfil_papel(perfil_id, papeis_id) values(1, 488);
insert into perfil_papel(perfil_id, papeis_id) values(1, 489);

insert into public."perfil" ("id", "nome") values (2, 'Usuário');

insert into public."perfil_papel" ("perfil_id", "papeis_id") values (2, 37);
insert into public."perfil_papel" ("perfil_id", "papeis_id") values (2, 38);

alter sequence perfil_sequence restart with 3;

insert into public."areaformacao" ("id", "nome") values (1, 'Administrativa');
insert into public."areaformacao" ("id", "nome") values (2, 'Administrativo Comercial');
insert into public."areaformacao" ("id", "nome") values (3, 'Administrativo/ Operacional');
insert into public."areaformacao" ("id", "nome") values (4, 'Agronômica/ Engenharia Agronômica/ Agribusiness');
insert into public."areaformacao" ("id", "nome") values (5, 'Agropecuária/ Veterinária/ Agrobusiness');
insert into public."areaformacao" ("id", "nome") values (6, 'Arquitetura/ Decoração/ Urbanismo');
insert into public."areaformacao" ("id", "nome") values (7, 'Artes');
insert into public."areaformacao" ("id", "nome") values (8, 'Artes Gráficas');
insert into public."areaformacao" ("id", "nome") values (9, 'Atendimento ao Cliente/ Call Center/ Telemarketing');
insert into public."areaformacao" ("id", "nome") values (10, 'Automação Industrial/ Comercial');
insert into public."areaformacao" ("id", "nome") values (11, 'Aviação/ Aeronáutica');
insert into public."areaformacao" ("id", "nome") values (12, 'Bancária/ Private Corporate Bank');
insert into public."areaformacao" ("id", "nome") values (13, 'Biblioteconomia');
insert into public."areaformacao" ("id", "nome") values (14, 'Biologia');
insert into public."areaformacao" ("id", "nome") values (15, 'Biotecnologia/ Biomédicas/ Bioquímica');
insert into public."areaformacao" ("id", "nome") values (16, 'Comercial/ Vendas');
insert into public."areaformacao" ("id", "nome") values (17, 'Comércio Exterior/ Trade/ Importação/ Exportação');
insert into public."areaformacao" ("id", "nome") values (18, 'Compras');
insert into public."areaformacao" ("id", "nome") values (19, 'Construção Civil/ Engenharia Civil');
insert into public."areaformacao" ("id", "nome") values (20, 'Contabilidade');
insert into public."areaformacao" ("id", "nome") values (21, 'Departamento Pessoal');
insert into public."areaformacao" ("id", "nome") values (22, 'Desenho Industrial');
insert into public."areaformacao" ("id", "nome") values (23, 'Economia');
insert into public."areaformacao" ("id", "nome") values (24, 'Educação/ Ensino/ Idiomas');
insert into public."areaformacao" ("id", "nome") values (25, 'Enfermagem');
insert into public."areaformacao" ("id", "nome") values (26, 'Engenharia de Alimentos');
insert into public."areaformacao" ("id", "nome") values (27, 'Engenharia de Materiais');
insert into public."areaformacao" ("id", "nome") values (28, 'Engenharia de Minas');
insert into public."areaformacao" ("id", "nome") values (29, 'Engenharia de Produção/ Industrial');
insert into public."areaformacao" ("id", "nome") values (30, 'Engenharia Elétrica/ Eletrônica');
insert into public."areaformacao" ("id", "nome") values (31, 'Engenharia Mecânica/ Mecatrônica');
insert into public."areaformacao" ("id", "nome") values (32, 'Esportes/ Educação Física');
insert into public."areaformacao" ("id", "nome") values (33, 'Estatística /Matemática /Atuária');
insert into public."areaformacao" ("id", "nome") values (34, 'Estética Corporal');
insert into public."areaformacao" ("id", "nome") values (35, 'Farmácia');
insert into public."areaformacao" ("id", "nome") values (36, 'Financeira/ Administrativa');
insert into public."areaformacao" ("id", "nome") values (37, 'Fisioterapia');
insert into public."areaformacao" ("id", "nome") values (38, 'Fonoaudiologia');
insert into public."areaformacao" ("id", "nome") values (39, 'Geologia /Engenharia Agrimensura');
insert into public."areaformacao" ("id", "nome") values (40, 'Hotelaria/ Turismo');
insert into public."areaformacao" ("id", "nome") values (41, 'Industrial');
insert into public."areaformacao" ("id", "nome") values (42, 'Informática /TI / Engenharia da Computação');
insert into public."areaformacao" ("id", "nome") values (43, 'Internet/ E-Commerce/ E-Business/ Web/ Web Designer');
insert into public."areaformacao" ("id", "nome") values (44, 'Jornalismo');
insert into public."areaformacao" ("id", "nome") values (45, 'Jurídica');
insert into public."areaformacao" ("id", "nome") values (46, 'Logística/ Suprimentos');
insert into public."areaformacao" ("id", "nome") values (47, 'Manutenção');
insert into public."areaformacao" ("id", "nome") values (48, 'Marketing');
insert into public."areaformacao" ("id", "nome") values (49, 'Médico/ Hospitalar');
insert into public."areaformacao" ("id", "nome") values (50, 'Meio Ambiente/ Ecologia/ Engenharia de Meio Ambiente');
insert into public."areaformacao" ("id", "nome") values (51, 'Moda');
insert into public."areaformacao" ("id", "nome") values (52, 'Nutrição');
insert into public."areaformacao" ("id", "nome") values (53, 'Odontologia ');
insert into public."areaformacao" ("id", "nome") values (54, 'Psicologia Clínica/ Hospitalar');
insert into public."areaformacao" ("id", "nome") values (55, 'Publicidade e Propaganda');
insert into public."areaformacao" ("id", "nome") values (56, 'Qualidade');
insert into public."areaformacao" ("id", "nome") values (57, 'Química/ Engenharia Química');
insert into public."areaformacao" ("id", "nome") values (58, 'Recursos Humanos');
insert into public."areaformacao" ("id", "nome") values (59, 'Relações Internacionais');
insert into public."areaformacao" ("id", "nome") values (60, 'Relações Públicas');
insert into public."areaformacao" ("id", "nome") values (61, 'Restaurante');
insert into public."areaformacao" ("id", "nome") values (62, 'Secretariado');
insert into public."areaformacao" ("id", "nome") values (63, 'Segurança do Trabalho');
insert into public."areaformacao" ("id", "nome") values (64, 'Segurança Patrimonial');
insert into public."areaformacao" ("id", "nome") values (65, 'Seguros');
insert into public."areaformacao" ("id", "nome") values (66, 'Serviço Social');
insert into public."areaformacao" ("id", "nome") values (67, 'Técnica');
insert into public."areaformacao" ("id", "nome") values (68, 'Técnico-Comercial');
insert into public."areaformacao" ("id", "nome") values (69, 'Telecomunicações/ Engenharia de Telecomunicações');
insert into public."areaformacao" ("id", "nome") values (70, 'Terapia Ocupacional');
insert into public."areaformacao" ("id", "nome") values (71, 'Têxtil/ Engenharia Têxtil');
insert into public."areaformacao" ("id", "nome") values (72, 'Tradutor/ Intérprete');
insert into public."areaformacao" ("id", "nome") values (73, 'Transportes');
insert into public."areaformacao" ("id", "nome") values (74, 'Zootecnia');

alter sequence areaformacao_sequence restart with 75;

insert into public."idioma" ("id", "nome") values (1, 'Inglês');
insert into public."idioma" ("id", "nome") values (2, 'Espanhol');
insert into public."idioma" ("id", "nome") values (3, 'Francês');
insert into public."idioma" ("id", "nome") values (4, 'Alemão');
insert into public."idioma" ("id", "nome") values (5, 'Italiano');

alter sequence idioma_sequence restart with 6;

INSERT INTO estado VALUES (nextval('estado_sequence'), 'CE', 'Ceará');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PE', 'Pernambuco');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RN', 'Rio Grande do Norte');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RJ', 'Rio de Janeiro');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'SP', 'São Paulo');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RS', 'Rio Grande do Sul');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MG', 'Minas Gerais');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AC', 'Acre');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AL', 'Alagoas');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AM', 'Amazonas');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AP', 'Amapá');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'BA', 'Bahia');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'DF', 'Distrito Federal');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'ES', 'Espírito Santo');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'GO', 'Goiás');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MA', 'Maranhão');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MS', 'Mato Grosso do Sul');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MT', 'Mato Grosso');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PA', 'Pará');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PB', 'Paraíba');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PI', 'Piauí');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PR', 'Paraná');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RO', 'Rondônia');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RR', 'Roraima');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'SC', 'Santa Catarina');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'SE', 'Sergipe');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'TO', 'Tocantins');

insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00015','Alta Floresta D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00023','Ariquemes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00031','Cabixi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00049','Cacoal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00056','Cerejeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00064','Colorado do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00072','Corumbiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00080','Costa Marques');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00098','Espigão D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00106','Guajará-Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00114','Jaru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00122','Ji-Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00130','Machadinho D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00148','Nova Brasilândia D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00155','Ouro Preto do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00189','Pimenta Bueno');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00205','Porto Velho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00254','Presidente Médici');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00262','Rio Crespo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00288','Rolim de Moura');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00296','Santa Luzia D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00304','Vilhena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00320','São Miguel do Guaporé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00338','Nova Mamoré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00346','Alvorada D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00379','Alto Alegre dos Parecis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00403','Alto Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00452','Buritis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00502','Novo Horizonte do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00601','Cacaulândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00700','Campo Novo de Rondônia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00809','Candeias do Jamari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00908','Castanheiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00924','Chupinguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00940','Cujubim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01005','Governador Jorge Teixeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01104','Itapuã do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01203','Ministro Andreazza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01302','Mirante da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01401','Monte Negro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01435','Nova União');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01450','Parecis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01468','Pimenteiras do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01476','Primavera de Rondônia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01484','São Felipe D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01492','São Francisco do Guaporé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01500','Seringueiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01559','Teixeirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01609','Theobroma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01708','Urupá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01757','Vale do Anari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'01807','Vale do Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00013','Acrelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00054','Assis Brasil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00104','Brasiléia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00138','Bujari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00179','Capixaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00203','Cruzeiro do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00252','Epitaciolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00302','Feijó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00328','Jordão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00336','Mâncio Lima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00344','Manoel Urbano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00351','Marechal Thaumaturgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00385','Plácido de Castro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00393','Porto Walter');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00401','Rio Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00427','Rodrigues Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00435','Santa Rosa do Purus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00450','Senador Guiomard');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00500','Sena Madureira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00609','Tarauacá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00708','Xapuri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AC'),'00807','Porto Acre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00029','Alvarães');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00060','Amaturá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00086','Anamã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00102','Anori');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00144','Apuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00201','Atalaia do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00300','Autazes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00409','Barcelos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00508','Barreirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00607','Benjamin Constant');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00631','Beruri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00680','Boa Vista do Ramos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00706','Boca do Acre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00805','Borba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00839','Caapiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'00904','Canutama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01001','Carauari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01100','Careiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01159','Careiro da Várzea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01209','Coari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01308','Codajás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01407','Eirunepé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01506','Envira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01605','Fonte Boa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01654','Guajará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01704','Humaitá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01803','Ipixuna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01852','Iranduba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01902','Itacoatiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'01951','Itamarati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02009','Itapiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02108','Japurá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02207','Juruá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02306','Jutaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02405','Lábrea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02504','Manacapuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02553','Manaquiri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02603','Manaus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02702','Manicoré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02801','Maraã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'02900','Maués');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03007','Nhamundá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03106','Nova Olinda do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03205','Novo Airão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03304','Novo Aripuanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03403','Parintins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03502','Pauini');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03536','Presidente Figueiredo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03569','Rio Preto da Eva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03601','Santa Isabel do Rio Negro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03700','Santo Antônio do Içá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03809','São Gabriel da Cachoeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03908','São Paulo de Olivença');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'03957','São Sebastião do Uatumã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04005','Silves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04062','Tabatinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04104','Tapauá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04203','Tefé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04237','Tonantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04260','Uarini');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04302','Urucará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AM'),'04401','Urucurituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00027','Amajari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00050','Alto Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00100','Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00159','Bonfim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00175','Cantá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00209','Caracaraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00233','Caroebe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00282','Iracema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00308','Mucajaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00407','Normandia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00456','Pacaraima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00472','Rorainópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00506','São João da Baliza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00605','São Luiz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RR'),'00704','Uiramutã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00107','Abaetetuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00131','Abel Figueiredo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00206','Acará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00305','Afuá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00347','Água Azul do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00404','Alenquer');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00503','Almeirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00602','Altamira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00701','Anajás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00800','Ananindeua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00859','Anapu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00909','Augusto Corrêa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'00958','Aurora do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01006','Aveiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01105','Bagre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01204','Baião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01253','Bannach');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01303','Barcarena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01402','Belém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01451','Belterra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01501','Benevides');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01576','Bom Jesus do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01600','Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01709','Bragança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01725','Brasil Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01758','Brejo Grande do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01782','Breu Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01808','Breves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01907','Bujaru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'01956','Cachoeira do Piriá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02004','Cachoeira do Arari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02103','Cametá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02152','Canaã dos Carajás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02202','Capanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02301','Capitão Poço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02400','Castanhal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02509','Chaves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02608','Colares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02707','Conceição do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02756','Concórdia do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02764','Cumaru do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02772','Curionópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02806','Curralinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02855','Curuá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02905','Curuçá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02939','Dom Eliseu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'02954','Eldorado dos Carajás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03002','Faro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03044','Floresta do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03077','Garrafão do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03093','Goianésia do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03101','Gurupá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03200','Igarapé-Açu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03309','Igarapé-Miri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03408','Inhangapi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03457','Ipixuna do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03507','Irituia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03606','Itaituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03705','Itupiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03754','Jacareacanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03804','Jacundá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'03903','Juruti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04000','Limoeiro do Ajuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04059','Mãe do Rio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04109','Magalhães Barata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04208','Marabá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04307','Maracanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04406','Marapanim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04422','Marituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04455','Medicilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04505','Melgaço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04604','Mocajuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04703','Moju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04802','Monte Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04901','Muaná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04950','Nova Esperança do Piriá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'04976','Nova Ipixuna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05007','Nova Timboteua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05031','Novo Progresso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05064','Novo Repartimento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05106','Óbidos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05205','Oeiras do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05304','Oriximiná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05403','Ourém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05437','Ourilândia do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05486','Pacajá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05494','Palestina do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05502','Paragominas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05536','Parauapebas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05551','Pau D''Arco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05601','Peixe-Boi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05635','Piçarra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05650','Placas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05700','Ponta de Pedras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05809','Portel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'05908','Porto de Moz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06005','Prainha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06104','Primavera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06112','Quatipuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06138','Redenção');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06161','Rio Maria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06187','Rondon do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06195','Rurópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06203','Salinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06302','Salvaterra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06351','Santa Bárbara do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06401','Santa Cruz do Arari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06500','Santa Isabel do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06559','Santa Luzia do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06583','Santa Maria das Barreiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06609','Santa Maria do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06708','Santana do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06807','Santarém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'06906','Santarém Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07003','Santo Antônio do Tauá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07102','São Caetano de Odivelas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07151','São Domingos do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07201','São Domingos do Capim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07300','São Félix do Xingu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07409','São Francisco do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07458','São Geraldo do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07466','São João da Ponta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07474','São João de Pirabas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07508','São João do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07607','São Miguel do Guamá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07706','São Sebastião da Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07755','Sapucaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07805','Senador José Porfírio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07904','Soure');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07953','Tailândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07961','Terra Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'07979','Terra Santa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08001','Tomé-Açu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08035','Tracuateua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08050','Trairão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08084','Tucumã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08100','Tucuruí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08126','Ulianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08159','Uruará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08209','Vigia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08308','Viseu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08357','Vitória do Xingu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PA'),'08407','Xinguara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00055','Serra do Navio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00105','Amapá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00154','Pedra Branca do Amaparí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00204','Calçoene');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00212','Cutias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00238','Ferreira Gomes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00253','Itaubal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00279','Vitória do Jari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00303','Macapá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00402','Mazagão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00501','Oiapoque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00535','Porto Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00550','Pracuúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00600','Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00709','Tartarugalzinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AP'),'00808','Laranjal do Jari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'00251','Abreulândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'00301','Aguiarnópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'00350','Aliança do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'00400','Almas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'00707','Alvorada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'01002','Ananás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'01051','Angico');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'01101','Aparecida do Rio Negro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'01309','Aragominas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'01903','Araguacema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02000','Araguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02109','Araguaína');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02158','Araguanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02208','Araguatins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02307','Arapoema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02406','Arraias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02554','Augustinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02703','Aurora do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'02901','Axixá do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03008','Babaçulândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03057','Bandeirantes do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03073','Barra do Ouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03107','Barrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03206','Bernardo Sayão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03305','Bom Jesus do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03602','Brasilândia do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03701','Brejinho de Nazaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03800','Buriti do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03826','Cachoeirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03842','Campos Lindos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03867','Cariri do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03883','Carmolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03891','Carrasco Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'03909','Caseara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'04105','Centenário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'04600','Chapada de Areia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'05102','Chapada da Natividade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'05508','Colinas do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'05557','Combinado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'05607','Conceição do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'06001','Couto de Magalhães');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'06100','Cristalândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'06258','Crixás do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'06506','Darcinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07009','Dianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07108','Divinópolis do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07207','Dois Irmãos do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07306','Dueré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07405','Esperantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07553','Fátima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07652','Figueirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'07702','Filadélfia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'08205','Formoso do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'08254','Fortaleza do Tabocão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'08304','Goianorte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'09005','Goiatins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'09302','Guaraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'09500','Gurupi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'09807','Ipueiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'10508','Itacajá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'10706','Itaguatins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'10904','Itapiratins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'11100','Itaporã do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'11506','Jaú do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'11803','Juarina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'11902','Lagoa da Confusão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'11951','Lagoa do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'12009','Lajeado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'12157','Lavandeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'12405','Lizarda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'12454','Luzinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'12504','Marianópolis do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'12702','Mateiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'12801','Maurilândia do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'13205','Miracema do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'13304','Miranorte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'13601','Monte do Carmo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'13700','Monte Santo do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'13809','Palmeiras do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'13957','Muricilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'14203','Natividade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'14302','Nazaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'14880','Nova Olinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'15002','Nova Rosalândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'15101','Novo Acordo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'15150','Novo Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'15259','Novo Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'15507','Oliveira de Fátima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'15705','Palmeirante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'15754','Palmeirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'16109','Paraíso do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'16208','Paranã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'16307','Pau D''Arco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'16505','Pedro Afonso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'16604','Peixe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'16653','Pequizeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'16703','Colméia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'17008','Pindorama do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'17206','Piraquê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'17503','Pium');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'17800','Ponte Alta do Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'17909','Ponte Alta do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18006','Porto Alegre do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18204','Porto Nacional');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18303','Praia Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18402','Presidente Kennedy');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18451','Pugmil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18501','Recursolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18550','Riachinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18659','Rio da Conceição');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18709','Rio dos Bois');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18758','Rio Sono');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18808','Sampaio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18840','Sandolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18865','Santa Fé do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18881','Santa Maria do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18899','Santa Rita do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'18907','Santa Rosa do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'19004','Santa Tereza do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20002','Santa Terezinha do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20101','São Bento do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20150','São Félix do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20200','São Miguel do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20259','São Salvador do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20309','São Sebastião do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20499','São Valério da Natividade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20655','Silvanópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20804','Sítio Novo do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20853','Sucupira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20903','Taguatinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20937','Taipas do Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'20978','Talismã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'21000','Palmas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'21109','Tocantínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'21208','Tocantinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'21257','Tupirama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'21307','Tupiratins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'22081','Wanderlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'TO'),'22107','Xambioá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00055','Açailândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00105','Afonso Cunha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00154','Água Doce do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00204','Alcântara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00303','Aldeias Altas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00402','Altamira do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00436','Alto Alegre do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00477','Alto Alegre do Pindaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00501','Alto Parnaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00550','Amapá do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00600','Amarante do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00709','Anajatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00808','Anapurus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00832','Apicum-Açu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00873','Araguanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00907','Araioses');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'00956','Arame');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01004','Arari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01103','Axixá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01202','Bacabal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01251','Bacabeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01301','Bacuri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01350','Bacurituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01400','Balsas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01509','Barão de Grajaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01608','Barra do Corda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01707','Barreirinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01731','Belágua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01772','Bela Vista do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01806','Benedito Leite');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01905','Bequimão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01939','Bernardo do Mearim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'01970','Boa Vista do Gurupi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02002','Bom Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02036','Bom Jesus das Selvas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02077','Bom Lugar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02101','Brejo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02150','Brejo de Areia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02200','Buriti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02309','Buriti Bravo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02325','Buriticupu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02358','Buritirana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02374','Cachoeira Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02408','Cajapió');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02507','Cajari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02556','Campestre do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02606','Cândido Mendes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02705','Cantanhede');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02754','Capinzal do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02804','Carolina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'02903','Carutapera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03000','Caxias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03109','Cedral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03125','Central do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03158','Centro do Guilherme');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03174','Centro Novo do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03208','Chapadinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03257','Cidelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03307','Codó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03406','Coelho Neto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03505','Colinas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03554','Conceição do Lago-Açu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03604','Coroatá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03703','Cururupu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03752','Davinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03802','Dom Pedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'03901','Duque Bacelar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04008','Esperantinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04057','Estreito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04073','Feira Nova do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04081','Fernando Falcão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04099','Formosa da Serra Negra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04107','Fortaleza dos Nogueiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04206','Fortuna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04305','Godofredo Viana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04404','Gonçalves Dias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04503','Governador Archer');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04552','Governador Edison Lobão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04602','Governador Eugênio Barros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04628','Governador Luiz Rocha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04651','Governador Newton Bello');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04677','Governador Nunes Freire');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04701','Graça Aranha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04800','Grajaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'04909','Guimarães');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05005','Humberto de Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05104','Icatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05153','Igarapé do Meio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05203','Igarapé Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05302','Imperatriz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05351','Itaipava do Grajaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05401','Itapecuru Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05427','Itinga do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05450','Jatobá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05476','Jenipapo dos Vieiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05500','João Lisboa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05609','Joselândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05658','Junco do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05708','Lago da Pedra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05807','Lago do Junco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05906','Lago Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05922','Lagoa do Mato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05948','Lago dos Rodrigues');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05963','Lagoa Grande do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'05989','Lajeado Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06003','Lima Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06102','Loreto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06201','Luís Domingues');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06300','Magalhães de Almeida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06326','Maracaçumé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06359','Marajá do Sena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06375','Maranhãozinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06409','Mata Roma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06508','Matinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06607','Matões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06631','Matões do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06672','Milagres do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06706','Mirador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06755','Miranda do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06805','Mirinzal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'06904','Monção');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07001','Montes Altos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07100','Morros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07209','Nina Rodrigues');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07258','Nova Colinas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07308','Nova Iorque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07357','Nova Olinda do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07407','Olho d''Água das Cunhãs');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07456','Olinda Nova do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07506','Paço do Lumiar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07605','Palmeirândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07704','Paraibano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07803','Parnarama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'07902','Passagem Franca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08009','Pastos Bons');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08058','Paulino Neves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08108','Paulo Ramos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08207','Pedreiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08256','Pedro do Rosário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08306','Penalva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08405','Peri Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08454','Peritoró');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08504','Pindaré-Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08603','Pinheiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08702','Pio XII');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08801','Pirapemas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'08900','Poção de Pedras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09007','Porto Franco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09056','Porto Rico do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09106','Presidente Dutra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09205','Presidente Juscelino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09239','Presidente Médici');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09270','Presidente Sarney');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09304','Presidente Vargas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09403','Primeira Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09452','Raposa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09502','Riachão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09551','Ribamar Fiquene');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09601','Rosário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09700','Sambaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09759','Santa Filomena do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09809','Santa Helena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'09908','Santa Inês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10005','Santa Luzia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10039','Santa Luzia do Paruá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10104','Santa Quitéria do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10203','Santa Rita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10237','Santana do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10278','Santo Amaro do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10302','Santo Antônio dos Lopes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10401','São Benedito do Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10500','São Bento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10609','São Bernardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10658','São Domingos do Azeitão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10708','São Domingos do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10807','São Félix de Balsas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10856','São Francisco do Brejão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'10906','São Francisco do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11003','São João Batista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11029','São João do Carú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11052','São João do Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11078','São João do Soter');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11102','São João dos Patos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11201','São José de Ribamar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11250','São José dos Basílios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11300','São Luís');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11409','São Luís Gonzaga do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11508','São Mateus do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11532','São Pedro da Água Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11573','São Pedro dos Crentes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11607','São Raimundo das Mangabeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11631','São Raimundo do Doca Bezerra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11672','São Roberto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11706','São Vicente Ferrer');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11722','Satubinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11748','Senador Alexandre Costa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11763','Senador La Rocque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11789','Serrano do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11805','Sítio Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11904','Sucupira do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'11953','Sucupira do Riachão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12001','Tasso Fragoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12100','Timbiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12209','Timon');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12233','Trizidela do Vale');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12274','Tufilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12308','Tuntum');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12407','Turiaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12456','Turilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12506','Tutóia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12605','Urbano Santos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12704','Vargem Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12803','Viana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12852','Vila Nova dos Martírios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'12902','Vitória do Mearim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'13009','Vitorino Freire');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MA'),'14007','Zé Doca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00053','Acauã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00103','Agricolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00202','Água Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00251','Alagoinha do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00277','Alegrete do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00301','Alto Longá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00400','Altos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00459','Alvorada do Gurguéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00509','Amarante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00608','Angical do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00707','Anísio de Abreu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00806','Antônio Almeida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'00905','Aroazes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01002','Arraial');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01051','Assunção do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01101','Avelino Lopes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01150','Baixa Grande do Ribeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01176','Barra D''Alcântara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01200','Barras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01309','Barreiras do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01408','Barro Duro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01507','Batalha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01556','Bela Vista do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01572','Belém do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01606','Beneditinos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01705','Bertolínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01739','Betânia do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01770','Boa Hora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01804','Bocaina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01903','Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01919','Bom Princípio do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01929','Bonfim do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01945','Boqueirão do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01960','Brasileira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'01988','Brejo do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02000','Buriti dos Lopes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02026','Buriti dos Montes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02059','Cabeceiras do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02075','Cajazeiras do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02083','Cajueiro da Praia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02091','Caldeirão Grande do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02109','Campinas do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02117','Campo Alegre do Fidalgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02133','Campo Grande do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02174','Campo Largo do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02208','Campo Maior');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02251','Canavieira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02307','Canto do Buriti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02406','Capitão de Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02455','Capitão Gervásio Oliveira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02505','Caracol');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02539','Caraúbas do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02554','Caridade do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02604','Castelo do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02653','Caxingó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02703','Cocal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02711','Cocal de Telha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02729','Cocal dos Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02737','Coivaras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02752','Colônia do Gurguéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02778','Colônia do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02802','Conceição do Canindé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02851','Coronel José Dias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'02901','Corrente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03008','Cristalândia do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03107','Cristino Castro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03206','Curimatá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03230','Currais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03255','Curralinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03271','Curral Novo do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03305','Demerval Lobão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03354','Dirceu Arcoverde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03404','Dom Expedito Lopes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03420','Domingos Mourão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03453','Dom Inocêncio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03503','Elesbão Veloso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03602','Eliseu Martins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03701','Esperantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03750','Fartura do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03800','Flores do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03859','Floresta do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'03909','Floriano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04006','Francinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04105','Francisco Ayres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04154','Francisco Macedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04204','Francisco Santos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04303','Fronteiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04352','Geminiano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04402','Gilbués');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04501','Guadalupe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04550','Guaribas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04600','Hugo Napoleão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04659','Ilha Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04709','Inhuma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04808','Ipiranga do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'04907','Isaías Coelho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05003','Itainópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05102','Itaueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05151','Jacobina do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05201','Jaicós');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05250','Jardim do Mulato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05276','Jatobá do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05300','Jerumenha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05359','João Costa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05409','Joaquim Pires');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05458','Joca Marques');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05508','José de Freitas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05516','Juazeiro do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05524','Júlio Borges');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05532','Jurema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05540','Lagoinha do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05557','Lagoa Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05565','Lagoa do Barro do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05573','Lagoa de São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05581','Lagoa do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05599','Lagoa do Sítio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05607','Landri Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05706','Luís Correia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05805','Luzilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05854','Madeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05904','Manoel Emídio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'05953','Marcolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06001','Marcos Parente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06050','Massapê do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06100','Matias Olímpio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06209','Miguel Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06308','Miguel Leão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06357','Milton Brandão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06407','Monsenhor Gil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06506','Monsenhor Hipólito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06605','Monte Alegre do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06654','Morro Cabeça no Tempo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06670','Morro do Chapéu do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06696','Murici dos Portelas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06704','Nazaré do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06753','Nossa Senhora de Nazaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06803','Nossa Senhora dos Remédios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06902','Novo Oriente do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'06951','Novo Santo Antônio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07009','Oeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07108','Olho D''Água do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07207','Padre Marcos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07306','Paes Landim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07355','Pajeú do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07405','Palmeira do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07504','Palmeirais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07553','Paquetá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07603','Parnaguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07702','Parnaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07751','Passagem Franca do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07777','Patos do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07801','Paulistana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07850','Pavussu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07900','Pedro II');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07934','Pedro Laurentino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'07959','Nova Santa Rita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08007','Picos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08106','Pimenteiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08205','Pio IX');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08304','Piracuruca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08403','Piripiri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08502','Porto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08551','Porto Alegre do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08601','Prata do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08650','Queimada Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08700','Redenção do Gurguéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08809','Regeneração');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08858','Riacho Frio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08874','Ribeira do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'08908','Ribeiro Gonçalves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09005','Rio Grande do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09104','Santa Cruz do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09153','Santa Cruz dos Milagres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09203','Santa Filomena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09302','Santa Luz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09351','Santana do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09377','Santa Rosa do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09401','Santo Antônio de Lisboa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09450','Santo Antônio dos Milagres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09500','Santo Inácio do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09559','São Braz do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09609','São Félix do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09658','São Francisco de Assis do Piau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09708','São Francisco do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09757','São Gonçalo do Gurguéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09807','São Gonçalo do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09856','São João da Canabrava');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09872','São João da Fronteira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09906','São João da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09955','São João da Varjota');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'09971','São João do Arraial');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10003','São João do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10052','São José do Divino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10102','São José do Peixe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10201','São José do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10300','São Julião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10359','São Lourenço do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10375','São Luis do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10383','São Miguel da Baixa Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10391','São Miguel do Fidalgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10409','São Miguel do Tapuio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10508','São Pedro do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10607','São Raimundo Nonato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10623','Sebastião Barros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10631','Sebastião Leal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10656','Sigefredo Pacheco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10706','Simões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10805','Simplício Mendes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10904','Socorro do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10938','Sussuapara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10953','Tamboril do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'10979','Tanque do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11001','Teresina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11100','União');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11209','Uruçuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11308','Valença do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11357','Várzea Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11407','Várzea Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11506','Vera Mendes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11605','Vila Nova do Piauí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PI'),'11704','Wall Ferraz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00101','Abaiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00150','Acarapé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00200','Acaraú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00309','Acopiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00408','Aiuaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00507','Alcântaras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00606','Altaneira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00705','Alto Santo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00754','Amontada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00804','Antonina do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'00903','Apuiarés');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01000','Aquiraz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01109','Aracati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01208','Aracoiaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01257','Ararendá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01307','Araripe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01406','Aratuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01505','Arneiroz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01604','Assaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01703','Aurora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01802','Baixio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01851','Banabuiú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01901','Barbalha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'01950','Barreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02008','Barro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02057','Barroquinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02107','Baturité');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02206','Beberibe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02305','Bela Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02404','Boa Viagem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02503','Brejo Santo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02602','Camocim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02701','Campos Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02800','Canindé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'02909','Capistrano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03006','Caridade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03105','Cariré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03204','Caririaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03303','Cariús');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03402','Carnaubal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03501','Cascavel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03600','Catarina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03659','Catunda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03709','Caucaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03808','Cedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03907','Chaval');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03931','Choró');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'03956','Chorozinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04004','Coreaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04103','Crateús');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04202','Crato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04236','Croatá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04251','Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04269','Deputado Irapuan Pinheiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04277','Ererê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04285','Eusébio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04301','Farias Brito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04350','Forquilha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04400','Fortaleza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04459','Fortim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04509','Frecheirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04608','General Sampaio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04657','Graça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04707','Granja');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04806','Granjeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04905','Groaíras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04954','Guaiúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05001','Guaraciaba do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05100','Guaramiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05209','Hidrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05233','Horizonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05266','Ibaretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05308','Ibiapina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05332','Ibicuitinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05357','Icapuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05407','Icó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05506','Iguatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05605','Independência');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05654','Ipaporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05704','Ipaumirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05803','Ipu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'05902','Ipueiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06009','Iracema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06108','Irauçuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06207','Itaiçaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06256','Itaitinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06306','Itapagé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06405','Itapipoca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06504','Itapiúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06553','Itarema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06603','Itatira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06702','Jaguaretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06801','Jaguaribara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'06900','Jaguaribe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07007','Jaguaruana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07106','Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07205','Jati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07254','Jijoca de Jericoacoara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07304','Juazeiro do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07403','Jucás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07502','Lavras da Mangabeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07601','Limoeiro do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07635','Madalena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07650','Maracanaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07700','Maranguape');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07809','Marco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'07908','Martinópole');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08005','Massapê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08104','Mauriti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08203','Meruoca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08302','Milagres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08351','Milhã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08377','Miraíma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08401','Missão Velha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08500','Mombaça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08609','Monsenhor Tabosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08708','Morada Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08807','Moraújo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'08906','Morrinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09003','Mucambo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09102','Mulungu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09201','Nova Olinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09300','Nova Russas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09409','Novo Oriente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09458','Ocara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09508','Orós');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09607','Pacajus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09706','Pacatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09805','Pacoti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'09904','Pacujá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10001','Palhano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10100','Palmácia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10209','Paracuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10258','Paraipaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10308','Parambu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10407','Paramoti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10506','Pedra Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10605','Penaforte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10704','Pentecoste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10803','Pereiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10852','Pindoretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10902','Piquet Carneiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'10951','Pires Ferreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11009','Poranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11108','Porteiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11207','Potengi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11231','Potiretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11264','Quiterianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11306','Quixadá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11355','Quixelô');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11405','Quixeramobim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11504','Quixeré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11603','Redenção');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11702','Reriutaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11801','Russas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11900','Saboeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'11959','Salitre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12007','Santana do Acaraú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12106','Santana do Cariri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12205','Santa Quitéria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12304','São Benedito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12403','São Gonçalo do Amarante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12502','São João do Jaguaribe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12601','São Luís do Curu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12700','Senador Pompeu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12809','Senador Sá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'12908','Sobral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13005','Solonópole');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13104','Tabuleiro do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13203','Tamboril');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13252','Tarrafas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13302','Tauá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13351','Tejuçuoca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13401','Tianguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13500','Trairi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13559','Tururu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13609','Ubajara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13708','Umari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13757','Umirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13807','Uruburetama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13906','Uruoca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'13955','Varjota');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'14003','Várzea Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'14102','Viçosa do Ceará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00109','Acari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00208','Açu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00307','Afonso Bezerra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00406','Água Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00505','Alexandria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00604','Almino Afonso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00703','Alto do Rodrigues');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00802','Angicos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'00901','Antônio Martins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01008','Apodi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01107','Areia Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01206','Arês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01305','Augusto Severo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01404','Baía Formosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01453','Baraúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01503','Barcelona');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01602','Bento Fernandes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01651','Bodó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01701','Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01800','Brejinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01859','Caiçara do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'01909','Caiçara do Rio do Vento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02006','Caicó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02105','Campo Redondo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02204','Canguaretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02303','Caraúbas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02402','Carnaúba dos Dantas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02501','Carnaubais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02600','Ceará-Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02709','Cerro Corá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02808','Coronel Ezequiel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'02907','Coronel João Pessoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03004','Cruzeta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03103','Currais Novos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03202','Doutor Severiano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03251','Parnamirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03301','Encanto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03400','Equador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03509','Espírito Santo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03608','Extremoz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03707','Felipe Guerra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03756','Fernando Pedroza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03806','Florânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'03905','Francisco Dantas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04002','Frutuoso Gomes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04101','Galinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04200','Goianinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04309','Governador Dix-Sept Rosado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04408','Grossos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04507','Guamaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04606','Ielmo Marinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04705','Ipanguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04804','Ipueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04853','Itajá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'04903','Itaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05009','Jaçanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05108','Jandaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05207','Janduís');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05306','Januário Cicco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05405','Japi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05504','Jardim de Angicos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05603','Jardim de Piranhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05702','Jardim do Seridó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05801','João Câmara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'05900','João Dias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06007','José da Penha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06106','Jucurutu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06205','Lagoa d''Anta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06304','Lagoa de Pedras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06403','Lagoa de Velhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06502','Lagoa Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06601','Lagoa Salgada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06700','Lajes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06809','Lajes Pintadas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'06908','Lucrécia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07005','Luís Gomes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07104','Macaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07203','Macau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07252','Major Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07302','Marcelino Vieira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07401','Martins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07500','Maxaranguape');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07609','Messias Targino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07708','Montanhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07807','Monte Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'07906','Monte das Gameleiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08003','Mossoró');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08102','Natal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08201','Nísia Floresta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08300','Nova Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08409','Olho-d''Água do Borges');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08508','Ouro Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08607','Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08706','Paraú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08805','Parazinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08904','Parelhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'08953','Rio do Fogo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09100','Passa e Fica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09209','Passagem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09308','Patu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09332','Santa Maria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09407','Pau dos Ferros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09506','Pedra Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09605','Pedra Preta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09704','Pedro Avelino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09803','Pedro Velho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'09902','Pendências');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10009','Pilões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10108','Poço Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10207','Portalegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10256','Porto do Mangue');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10306','Presidente Juscelino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10405','Pureza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10504','Rafael Fernandes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10603','Rafael Godeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10702','Riacho da Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10801','Riacho de Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'10900','Riachuelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11007','Rodolfo Fernandes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11056','Tibau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11106','Ruy Barbosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11205','Santa Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11403','Santana do Matos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11429','Santana do Seridó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11502','Santo Antônio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11601','São Bento do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11700','São Bento do Trairí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11809','São Fernando');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'11908','São Francisco do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12005','São Gonçalo do Amarante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12104','São João do Sabugi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12203','São José de Mipibu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12302','São José do Campestre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12401','São José do Seridó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12500','São Miguel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12559','São Miguel de Touros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12609','São Paulo do Potengi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12708','São Pedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12807','São Rafael');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'12906','São Tomé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13003','São Vicente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13102','Senador Elói de Souza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13201','Senador Georgino Avelino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13300','Serra de São Bento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13359','Serra do Mel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13409','Serra Negra do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13508','Serrinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13557','Serrinha dos Pintos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13607','Severiano Melo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13706','Sítio Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13805','Taboleiro Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'13904','Taipu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14001','Tangará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14100','Tenente Ananias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14159','Tenente Laurentino Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14209','Tibau do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14308','Timbaúba dos Batistas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14407','Touros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14456','Triunfo Potiguar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14506','Umarizal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14605','Upanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14704','Várzea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14753','Venha-Ver');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14803','Vera Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'14902','Viçosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RN'),'15008','Vila Flor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00106','Água Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00205','Aguiar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00304','Alagoa Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00403','Alagoa Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00502','Alagoinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00536','Alcantil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00577','Algodão de Jandaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00601','Alhandra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00700','São João do Rio do Peixe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00734','Amparo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00775','Aparecida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00809','Araçagi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'00908','Arara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01005','Araruna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01104','Areia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01153','Areia de Baraúnas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01203','Areial');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01302','Aroeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01351','Assunção');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01401','Baía da Traição');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01500','Bananeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01534','Baraúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01575','Barra de Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01609','Barra de Santa Rosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01708','Barra de São Miguel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01807','Bayeux');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'01906','Belém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02003','Belém do Brejo do Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02052','Bernardino Batista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02102','Boa Ventura');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02151','Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02201','Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02300','Bom Sucesso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02409','Bonito de Santa Fé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02508','Boqueirão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02607','Igaracy');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02706','Borborema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02805','Brejo do Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'02904','Brejo dos Santos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03001','Caaporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03100','Cabaceiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03209','Cabedelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03308','Cachoeira dos Índios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03407','Cacimba de Areia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03506','Cacimba de Dentro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03555','Cacimbas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03605','Caiçara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03704','Cajazeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03753','Cajazeirinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03803','Caldas Brandão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'03902','Camalaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04009','Campina Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04033','Capim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04074','Caraúbas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04108','Carrapateira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04157','Casserengue');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04207','Catingueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04306','Catolé do Rocha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04355','Caturité');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04405','Conceição');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04504','Condado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04603','Conde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04702','Congo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04801','Coremas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04850','Coxixola');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'04900','Cruz do Espírito Santo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05006','Cubati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05105','Cuité');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05204','Cuitegi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05238','Cuité de Mamanguape');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05279','Curral de Cima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05303','Curral Velho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05352','Damião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05402','Desterro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05501','Vista Serrana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05600','Diamante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05709','Dona Inês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05808','Duas Estradas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'05907','Emas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06004','Esperança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06103','Fagundes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06202','Frei Martinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06251','Gado Bravo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06301','Guarabira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06400','Gurinhém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06509','Gurjão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06608','Ibiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06707','Imaculada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06806','Ingá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'06905','Itabaiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07002','Itaporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07101','Itapororoca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07200','Itatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07309','Jacaraú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07408','Jericó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07507','João Pessoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07606','Juarez Távora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07705','Juazeirinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07804','Junco do Seridó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'07903','Juripiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08000','Juru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08109','Lagoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08208','Lagoa de Dentro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08307','Lagoa Seca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08406','Lastro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08505','Livramento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08554','Logradouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08604','Lucena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08703','Mãe d''Água');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08802','Malta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'08901','Mamanguape');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09008','Manaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09057','Marcação');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09107','Mari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09156','Marizópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09206','Massaranduba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09305','Mataraca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09339','Matinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09370','Mato Grosso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09396','Maturéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09404','Mogeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09503','Montadas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09602','Monte Horebe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09701','Monteiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09800','Mulungu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'09909','Natuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10006','Nazarezinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10105','Nova Floresta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10204','Nova Olinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10303','Nova Palmeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10402','Olho d''Água');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10501','Olivedos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10600','Ouro Velho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10659','Parari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10709','Passagem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10808','Patos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'10907','Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11004','Pedra Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11103','Pedra Lavrada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11202','Pedras de Fogo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11301','Piancó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11400','Picuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11509','Pilar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11608','Pilões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11707','Pilõezinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11806','Pirpirituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'11905','Pitimbu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12002','Pocinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12036','Poço Dantas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12077','Poço de José de Moura');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12101','Pombal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12200','Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12309','Princesa Isabel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12408','Puxinanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12507','Queimadas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12606','Quixabá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12705','Remígio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12721','Pedro Régis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12747','Riachão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12754','Riachão do Bacamarte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12762','Riachão do Poço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12788','Riacho de Santo Antônio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12804','Riacho dos Cavalos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'12903','Rio Tinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13000','Salgadinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13109','Salgado de São Félix');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13158','Santa Cecília');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13208','Santa Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13307','Santa Helena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13356','Santa Inês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13406','Santa Luzia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13505','Santana de Mangueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13604','Santana dos Garrotes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13653','Santarém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13703','Santa Rita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13802','Santa Teresinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13851','Santo André');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13901','São Bento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13927','São Bentinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13943','São Domingos do Cariri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13968','São Domingos de Pombal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'13984','São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14008','São João do Cariri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14107','São João do Tigre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14206','São José da Lagoa Tapada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14305','São José de Caiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14404','São José de Espinharas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14453','São José dos Ramos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14503','São José de Piranhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14552','São José de Princesa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14602','São José do Bonfim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14651','São José do Brejo do Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14701','São José do Sabugi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14800','São José dos Cordeiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'14909','São Mamede');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15005','São Miguel de Taipu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15104','São Sebastião de Lagoa de Roca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15203','São Sebastião do Umbuzeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15302','Sapé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15401','Seridó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15500','Serra Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15609','Serra da Raiz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15708','Serra Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15807','Serra Redonda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15906','Serraria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15930','Sertãozinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'15971','Sobrado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16003','Solânea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16102','Soledade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16151','Sossêgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16201','Sousa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16300','Sumé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16409','Campo de Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16508','Taperoá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16607','Tavares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16706','Teixeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16755','Tenório');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16805','Triunfo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'16904','Uiraúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'17001','Umbuzeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'17100','Várzea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'17209','Vieirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PB'),'17407','Zabelê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00054','Abreu e Lima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00104','Afogados da Ingazeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00203','Afrânio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00302','Agrestina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00401','Água Preta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00500','Águas Belas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00609','Alagoinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00708','Aliança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00807','Altinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'00906','Amaraji');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01003','Angelim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01052','Araçoiaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01102','Araripina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01201','Arcoverde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01300','Barra de Guabiraba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01409','Barreiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01508','Belém de Maria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01607','Belém de São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01706','Belo Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01805','Betânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'01904','Bezerros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02001','Bodocó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02100','Bom Conselho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02209','Bom Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02308','Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02407','Brejão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02506','Brejinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02605','Brejo da Madre de Deus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02704','Buenos Aires');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02803','Buíque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'02902','Cabo de Santo Agostinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03009','Cabrobó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03108','Cachoeirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03207','Caetés');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03306','Calçado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03405','Calumbi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03454','Camaragibe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03504','Camocim de São Félix');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03603','Camutanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03702','Canhotinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03801','Capoeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03900','Carnaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'03926','Carnaubeira da Penha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04007','Carpina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04106','Caruaru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04155','Casinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04205','Catende');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04304','Cedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04403','Chã de Alegria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04502','Chã Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04601','Condado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04700','Correntes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04809','Cortês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'04908','Cumaru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05004','Cupira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05103','Custódia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05152','Dormentes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05202','Escada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05301','Exu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05400','Feira Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05459','Fernando de Noronha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05509','Ferreiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05608','Flores');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05707','Floresta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05806','Frei Miguelinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'05905','Gameleira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06002','Garanhuns');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06101','Glória do Goitá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06200','Goiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06309','Granito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06408','Gravatá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06507','Iati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06606','Ibimirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06705','Ibirajuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06804','Igarassu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'06903','Iguaraci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07000','Inajá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07109','Ingazeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07208','Ipojuca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07307','Ipubi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07406','Itacuruba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07505','Itaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07604','Itamaracá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07653','Itambé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07703','Itapetim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07752','Itapissuma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07802','Itaquitinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07901','Jaboatão dos Guararapes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'07950','Jaqueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08008','Jataúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08057','Jatobá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08107','João Alfredo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08206','Joaquim Nabuco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08255','Jucati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08305','Jupi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08404','Jurema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08453','Lagoa do Carro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08503','Lagoa do Itaenga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08602','Lagoa do Ouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08701','Lagoa dos Gatos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08750','Lagoa Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08800','Lajedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'08909','Limoeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09006','Macaparana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09105','Machados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09154','Manari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09204','Maraial');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09303','Mirandiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09402','Moreno');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09501','Nazaré da Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09600','Olinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09709','Orobó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09808','Orocó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'09907','Ouricuri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10004','Palmares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10103','Palmeirina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10202','Panelas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10301','Paranatama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10400','Parnamirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10509','Passira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10608','Paudalho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10707','Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10806','Pedra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'10905','Pesqueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11002','Petrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11101','Petrolina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11200','Poção');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11309','Pombos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11408','Primavera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11507','Quipapá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11533','Quixaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11606','Recife');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11705','Riacho das Almas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11804','Ribeirão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'11903','Rio Formoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12000','Sairé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12109','Salgadinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12208','Salgueiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12307','Saloá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12406','Sanharó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12455','Santa Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12471','Santa Cruz da Baixa Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12505','Santa Cruz do Capibaribe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12554','Santa Filomena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12604','Santa Maria da Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12703','Santa Maria do Cambucá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12802','Santa Terezinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'12901','São Benedito do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13008','São Bento do Una');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13107','São Caitano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13206','São João');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13305','São Joaquim do Monte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13404','São José da Coroa Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13503','São José do Belmonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13602','São José do Egito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13701','São Lourenço da Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13800','São Vicente Ferrer');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'13909','Serra Talhada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14006','Serrita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14105','Sertânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14204','Sirinhaém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14303','Moreilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14402','Solidão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14501','Surubim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14600','Tabira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14709','Tacaimbó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14808','Tacaratu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'14857','Tamandaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15003','Taquaritinga do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15102','Terezinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15201','Terra Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15300','Timbaúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15409','Toritama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15508','Tracunhaém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15607','Trindade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15706','Triunfo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15805','Tupanatinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'15904','Tuparetama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'16001','Venturosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'16100','Verdejante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'16183','Vertente do Lério');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'16209','Vertentes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'16308','Vicência');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'16407','Vitória de Santo Antão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PE'),'16506','Xexéu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00102','Água Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00201','Anadia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00300','Arapiraca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00409','Atalaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00508','Barra de Santo Antônio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00607','Barra de São Miguel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00706','Batalha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00805','Belém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'00904','Belo Monte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01001','Boca da Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01100','Branquinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01209','Cacimbinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01308','Cajueiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01357','Campestre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01407','Campo Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01506','Campo Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01605','Canapi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01704','Capela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01803','Carneiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'01902','Chã Preta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02009','Coité do Nóia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02108','Colônia Leopoldina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02207','Coqueiro Seco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02306','Coruripe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02355','Craíbas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02405','Delmiro Gouveia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02504','Dois Riachos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02553','Estrela de Alagoas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02603','Feira Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02702','Feliz Deserto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02801','Flexeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'02900','Girau do Ponciano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03007','Ibateguara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03106','Igaci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03205','Igreja Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03304','Inhapi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03403','Jacaré dos Homens');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03502','Jacuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03601','Japaratinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03700','Jaramataia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03809','Joaquim Gomes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'03908','Jundiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04005','Junqueiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04104','Lagoa da Canoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04203','Limoeiro de Anadia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04302','Maceió');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04401','Major Isidoro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04500','Maragogi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04609','Maravilha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04708','Marechal Deodoro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04807','Maribondo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'04906','Mar Vermelho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05002','Mata Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05101','Matriz de Camaragibe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05200','Messias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05309','Minador do Negrão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05408','Monteirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05507','Murici');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05606','Novo Lino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05705','Olho d''Água das Flores');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05804','Olho d''Água do Casado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'05903','Olho d''Água Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06000','Olivença');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06109','Ouro Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06208','Palestina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06307','Palmeira dos Índios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06406','Pão de Açúcar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06422','Pariconha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06448','Paripueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06505','Passo de Camaragibe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06604','Paulo Jacinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06703','Penedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06802','Piaçabuçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'06901','Pilar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07008','Pindoba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07107','Piranhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07206','Poço das Trincheiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07305','Porto Calvo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07404','Porto de Pedras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07503','Porto Real do Colégio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07602','Quebrangulo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07701','Rio Largo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07800','Roteiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'07909','Santa Luzia do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08006','Santana do Ipanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08105','Santana do Mundaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08204','São Brás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08303','São José da Laje');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08402','São José da Tapera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08501','São Luís do Quitunde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08600','São Miguel dos Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08709','São Miguel dos Milagres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08808','São Sebastião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08907','Satuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'08956','Senador Rui Palmeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'09004','Tanque d''Arca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'09103','Taquarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'09152','Teotônio Vilela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'09202','Traipu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'09301','União dos Palmares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'AL'),'09400','Viçosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00100','Amparo de São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00209','Aquidabã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00308','Aracaju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00407','Arauá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00506','Areia Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00605','Barra dos Coqueiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00670','Boquim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'00704','Brejo Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01009','Campo do Brito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01108','Canhoba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01207','Canindé de São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01306','Capela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01405','Carira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01504','Carmópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01603','Cedro de São João');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01702','Cristinápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'01900','Cumbe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02007','Divina Pastora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02106','Estância');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02205','Feira Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02304','Frei Paulo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02403','Gararu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02502','General Maynard');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02601','Gracho Cardoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02700','Ilha das Flores');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02809','Indiaroba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'02908','Itabaiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03005','Itabaianinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03104','Itabi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03203','Itaporanga d''Ajuda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03302','Japaratuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03401','Japoatã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03500','Lagarto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03609','Laranjeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03708','Macambira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03807','Malhada dos Bois');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'03906','Malhador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04003','Maruim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04102','Moita Bonita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04201','Monte Alegre de Sergipe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04300','Muribeca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04409','Neópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04458','Nossa Senhora Aparecida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04508','Nossa Senhora da Glória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04607','Nossa Senhora das Dores');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04706','Nossa Senhora de Lourdes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04805','Nossa Senhora do Socorro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'04904','Pacatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05000','Pedra Mole');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05109','Pedrinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05208','Pinhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05307','Pirambu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05406','Poço Redondo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05505','Poço Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05604','Porto da Folha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05703','Propriá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05802','Riachão do Dantas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'05901','Riachuelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06008','Ribeirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06107','Rosário do Catete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06206','Salgado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06305','Santa Luzia do Itanhy');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06404','Santana do São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06503','Santa Rosa de Lima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06602','Santo Amaro das Brotas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06701','São Cristóvão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06800','São Domingos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'06909','São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'07006','São Miguel do Aleixo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'07105','Simão Dias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'07204','Siriri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'07303','Telha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'07402','Tobias Barreto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'07501','Tomar do Geru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SE'),'07600','Umbaúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00108','Abaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00207','Abaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00306','Acajutiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00355','Adustina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00405','Água Fria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00504','Érico Cardoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00603','Aiquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00702','Alagoinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00801','Alcobaça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'00900','Almadina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01007','Amargosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01106','Amélia Rodrigues');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01155','América Dourada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01205','Anagé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01304','Andaraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01353','Andorinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01403','Angical');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01502','Anguera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01601','Antas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01700','Antônio Cardoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01809','Antônio Gonçalves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01908','Aporá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'01957','Apuarema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02005','Aracatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02054','Araças');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02104','Araci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02203','Aramari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02252','Arataca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02302','Aratuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02401','Aurelino Leal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02500','Baianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02609','Baixa Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02658','Banzaê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02708','Barra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02807','Barra da Estiva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'02906','Barra do Choça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03003','Barra do Mendes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03102','Barra do Rocha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03201','Barreiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03235','Barro Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03300','Barro Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03409','Belmonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03508','Belo Campo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03607','Biritinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03706','Boa Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03805','Boa Vista do Tupim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03904','Bom Jesus da Lapa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'03953','Bom Jesus da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04001','Boninal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04050','Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04100','Boquira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04209','Botuporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04308','Brejões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04407','Brejolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04506','Brotas de Macaúbas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04605','Brumado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04704','Buerarema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04753','Buritirama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04803','Caatiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04852','Cabaceiras do Paraguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'04902','Cachoeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05008','Caculé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05107','Caém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05156','Caetanos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05206','Caetité');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05305','Cafarnaum');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05404','Cairu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05503','Caldeirão Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05602','Camacan');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05701','Camaçari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05800','Camamu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'05909','Campo Alegre de Lourdes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06006','Campo Formoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06105','Canápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06204','Canarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06303','Canavieiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06402','Candeal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06501','Candeias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06600','Candiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06709','Cândido Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06808','Cansanção');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06824','Canudos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06857','Capela do Alto Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06873','Capim Grosso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06899','Caraíbas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'06907','Caravelas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07004','Cardeal da Silva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07103','Carinhanha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07202','Casa Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07301','Castro Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07400','Catolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07509','Catu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07558','Caturama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07608','Central');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07707','Chorrochó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07806','Cícero Dantas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'07905','Cipó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08002','Coaraci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08101','Cocos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08200','Conceição da Feira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08309','Conceição do Almeida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08408','Conceição do Coité');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08507','Conceição do Jacuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08606','Conde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08705','Condeúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08804','Contendas do Sincorá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'08903','Coração de Maria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09000','Cordeiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09109','Coribe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09208','Coronel João Sá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09307','Correntina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09406','Cotegipe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09505','Cravolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09604','Crisópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09703','Cristópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09802','Cruz das Almas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'09901','Curaçá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10008','Dário Meira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10057','Dias d''Ávila');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10107','Dom Basílio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10206','Dom Macedo Costa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10305','Elísio Medrado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10404','Encruzilhada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10503','Entre Rios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10602','Esplanada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10701','Euclides da Cunha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10727','Eunápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10750','Fátima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10776','Feira da Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10800','Feira de Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10859','Filadélfia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'10909','Firmino Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11006','Floresta Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11105','Formosa do Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11204','Gandu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11253','Gavião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11303','Gentio do Ouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11402','Glória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11501','Gongogi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11600','Governador Mangabeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11659','Guajeru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11709','Guanambi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11808','Guaratinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11857','Heliópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'11907','Iaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12004','Ibiassucê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12103','Ibicaraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12202','Ibicoara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12301','Ibicuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12400','Ibipeba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12509','Ibipitanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12608','Ibiquera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12707','Ibirapitanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12806','Ibirapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'12905','Ibirataia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13002','Ibitiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13101','Ibititá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13200','Ibotirama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13309','Ichu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13408','Igaporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13457','Igrapiúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13507','Iguaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13606','Ilhéus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13705','Inhambupe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13804','Ipecaetá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'13903','Ipiaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14000','Ipirá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14109','Ipupiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14208','Irajuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14307','Iramaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14406','Iraquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14505','Irará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14604','Irecê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14653','Itabela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14703','Itaberaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14802','Itabuna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'14901','Itacaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15007','Itaeté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15106','Itagi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15205','Itagibá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15304','Itagimirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15353','Itaguaçu da Bahia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15403','Itaju do Colônia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15502','Itajuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15601','Itamaraju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15700','Itamari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15809','Itambé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'15908','Itanagra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16005','Itanhém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16104','Itaparica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16203','Itapé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16302','Itapebi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16401','Itapetinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16500','Itapicuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16609','Itapitanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16708','Itaquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16807','Itarantim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16856','Itatim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'16906','Itiruçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17003','Itiúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17102','Itororó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17201','Ituaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17300','Ituberá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17334','Iuiú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17359','Jaborandi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17409','Jacaraci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17508','Jacobina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17607','Jaguaquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17706','Jaguarari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17805','Jaguaripe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'17904','Jandaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18001','Jequié');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18100','Jeremoabo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18209','Jiquiriçá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18308','Jitaúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18357','João Dourado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18407','Juazeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18456','Jucuruçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18506','Jussara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18555','Jussari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18605','Jussiape');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18704','Lafaiete Coutinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18753','Lagoa Real');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18803','Laje');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'18902','Lajedão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19009','Lajedinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19058','Lajedo do Tabocal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19108','Lamarão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19157','Lapão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19207','Lauro de Freitas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19306','Lençóis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19405','Licínio de Almeida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19504','Livramento de Nossa Senhora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19603','Macajuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19702','Macarani');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19801','Macaúbas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19900','Macururé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19926','Madre de Deus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'19959','Maetinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20007','Maiquinique');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20106','Mairi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20205','Malhada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20304','Malhada de Pedras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20403','Manoel Vitorino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20452','Mansidão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20502','Maracás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20601','Maragogipe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20700','Maraú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20809','Marcionílio Souza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'20908','Mascote');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21005','Mata de São João');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21054','Matina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21104','Medeiros Neto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21203','Miguel Calmon');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21302','Milagres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21401','Mirangaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21450','Mirante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21500','Monte Santo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21609','Morpará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21708','Morro do Chapéu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21807','Mortugaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'21906','Mucugê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22003','Mucuri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22052','Mulungu do Morro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22102','Mundo Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22201','Muniz Ferreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22250','Muquém de São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22300','Muritiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22409','Mutuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22508','Nazaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22607','Nilo Peçanha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22656','Nordestina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22706','Nova Canaã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22730','Nova Fátima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22755','Nova Ibiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22805','Nova Itarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22854','Nova Redenção');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'22904','Nova Soure');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23001','Nova Viçosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23035','Novo Horizonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23050','Novo Triunfo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23100','Olindina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23209','Oliveira dos Brejinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23308','Ouriçangas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23357','Ourolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23407','Palmas de Monte Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23506','Palmeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23605','Paramirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23704','Paratinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23803','Paripiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'23902','Pau Brasil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24009','Paulo Afonso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24058','Pé de Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24108','Pedrão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24207','Pedro Alexandre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24306','Piatã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24405','Pilão Arcado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24504','Pindaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24603','Pindobaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24652','Pintadas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24678','Piraí do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24702','Piripá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24801','Piritiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'24900','Planaltino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25006','Planalto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25105','Poções');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25204','Pojuca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25253','Ponto Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25303','Porto Seguro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25402','Potiraguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25501','Prado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25600','Presidente Dutra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25709','Presidente Jânio Quadros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25758','Presidente Tancredo Neves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25808','Queimadas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25907','Quijingue');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25931','Quixabeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'25956','Rafael Jambeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26004','Remanso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26103','Retirolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26202','Riachão das Neves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26301','Riachão do Jacuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26400','Riacho de Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26509','Ribeira do Amparo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26608','Ribeira do Pombal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26657','Ribeirão do Largo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26707','Rio de Contas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26806','Rio do Antônio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'26905','Rio do Pires');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27002','Rio Real');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27101','Rodelas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27200','Ruy Barbosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27309','Salinas da Margarida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27408','Salvador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27507','Santa Bárbara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27606','Santa Brígida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27705','Santa Cruz Cabrália');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27804','Santa Cruz da Vitória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'27903','Santa Inês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28000','Santaluz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28059','Santa Luzia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28109','Santa Maria da Vitória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28208','Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28307','Santanópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28406','Santa Rita de Cássia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28505','Santa Teresinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28604','Santo Amaro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28703','Santo Antônio de Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28802','Santo Estêvão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28901','São Desidério');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'28950','São Domingos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29008','São Félix');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29057','São Félix do Coribe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29107','São Felipe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29206','São Francisco do Conde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29255','São Gabriel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29305','São Gonçalo dos Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29354','São José da Vitória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29370','São José do Jacuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29404','São Miguel das Matas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29503','São Sebastião do Passé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29602','Sapeaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29701','Sátiro Dias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29750','Saubara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29800','Saúde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'29909','Seabra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30006','Sebastião Laranjeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30105','Senhor do Bonfim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30154','Serra do Ramalho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30204','Sento Sé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30303','Serra Dourada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30402','Serra Preta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30501','Serrinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30600','Serrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30709','Simões Filho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30758','Sítio do Mato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30766','Sítio do Quinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30774','Sobradinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30808','Souto Soares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'30907','Tabocas do Brejo Velho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31004','Tanhaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31053','Tanque Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31103','Tanquinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31202','Taperoá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31301','Tapiramutá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31350','Teixeira de Freitas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31400','Teodoro Sampaio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31509','Teofilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31608','Teolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31707','Terra Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31806','Tremedal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'31905','Tucano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32002','Uauá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32101','Ubaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32200','Ubaitaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32309','Ubatã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32408','Uibaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32457','Umburanas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32507','Una');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32606','Urandi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32705','Uruçuca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32804','Utinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'32903','Valença');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33000','Valente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33059','Várzea da Roça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33109','Várzea do Poço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33158','Várzea Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33174','Varzedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33208','Vera Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33257','Vereda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33307','Vitória da Conquista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33406','Wagner');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33455','Wanderley');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33505','Wenceslau Guimarães');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'BA'),'33604','Xique-Xique');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00104','Abadia dos Dourados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00203','Abaeté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00302','Abre Campo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00401','Acaiaca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00500','Açucena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00609','Água Boa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00708','Água Comprida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00807','Aguanil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'00906','Águas Formosas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01003','Águas Vermelhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01102','Aimorés');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01201','Aiuruoca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01300','Alagoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01409','Albertina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01508','Além Paraíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01607','Alfenas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01631','Alfredo Vasconcelos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01706','Almenara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01805','Alpercata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'01904','Alpinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02001','Alterosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02050','Alto Caparaó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02100','Alto Rio Doce');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02209','Alvarenga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02308','Alvinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02407','Alvorada de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02506','Amparo do Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02605','Andradas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02704','Cachoeira de Pajeú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02803','Andrelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02852','Angelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'02902','Antônio Carlos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03009','Antônio Dias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03108','Antônio Prado de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03207','Araçaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03306','Aracitaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03405','Araçuaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03504','Araguari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03603','Arantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03702','Araponga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03751','Araporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03801','Arapuá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'03900','Araújos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04007','Araxá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04106','Arceburgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04205','Arcos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04304','Areado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04403','Argirita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04452','Aricanduva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04502','Arinos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04601','Astolfo Dutra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04700','Ataléia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04809','Augusto de Lima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'04908','Baependi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05004','Baldim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05103','Bambuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05202','Bandeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05301','Bandeira do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05400','Barão de Cocais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05509','Barão de Monte Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05608','Barbacena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05707','Barra Longa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'05905','Barroso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06002','Bela Vista de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06101','Belmiro Braga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06200','Belo Horizonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06309','Belo Oriente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06408','Belo Vale');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06507','Berilo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06606','Bertópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06655','Berizal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06705','Betim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06804','Bias Fortes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'06903','Bicas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07000','Biquinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07109','Boa Esperança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07208','Bocaina de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07307','Bocaiúva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07406','Bom Despacho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07505','Bom Jardim de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07604','Bom Jesus da Penha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07703','Bom Jesus do Amparo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07802','Bom Jesus do Galho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'07901','Bom Repouso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08008','Bom Sucesso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08107','Bonfim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08206','Bonfinópolis de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08255','Bonito de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08305','Borda da Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08404','Botelhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08503','Botumirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08552','Brasilândia de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08602','Brasília de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08701','Brás Pires');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08800','Braúnas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'08909','Brasópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09006','Brumadinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09105','Bueno Brandão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09204','Buenópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09253','Bugre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09303','Buritis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09402','Buritizeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09451','Cabeceira Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09501','Cabo Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09600','Cachoeira da Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09709','Cachoeira de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09808','Cachoeira Dourada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'09907','Caetanópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10004','Caeté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10103','Caiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10202','Cajuri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10301','Caldas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10400','Camacho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10509','Camanducaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10608','Cambuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10707','Cambuquira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10806','Campanário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'10905','Campanha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11002','Campestre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11101','Campina Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11150','Campo Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11200','Campo Belo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11309','Campo do Meio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11408','Campo Florido');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11507','Campos Altos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11606','Campos Gerais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11705','Canaã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11804','Canápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'11903','Cana Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12000','Candeias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12059','Cantagalo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12109','Caparaó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12208','Capela Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12307','Capelinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12406','Capetinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12505','Capim Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12604','Capinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12653','Capitão Andrade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12703','Capitão Enéas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12802','Capitólio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'12901','Caputira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13008','Caraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13107','Caranaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13206','Carandaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13305','Carangola');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13404','Caratinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13503','Carbonita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13602','Careaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13701','Carlos Chagas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13800','Carmésia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'13909','Carmo da Cachoeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14006','Carmo da Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14105','Carmo de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14204','Carmo do Cajuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14303','Carmo do Paranaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14402','Carmo do Rio Claro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14501','Carmópolis de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14550','Carneirinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14600','Carrancas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14709','Carvalhópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14808','Carvalhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'14907','Casa Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15003','Cascalho Rico');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15102','Cássia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15201','Conceição da Barra de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15300','Cataguases');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15359','Catas Altas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15409','Catas Altas da Noruega');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15458','Catuji');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15474','Catuti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15508','Caxambu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15607','Cedro do Abaeté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15706','Central de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15805','Centralina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'15904','Chácara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16001','Chalé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16100','Chapada do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16159','Chapada Gaúcha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16209','Chiador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16308','Cipotânea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16407','Claraval');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16506','Claro dos Poções');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16605','Cláudio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16704','Coimbra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16803','Coluna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'16902','Comendador Gomes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17009','Comercinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17108','Conceição da Aparecida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17207','Conceição das Pedras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17306','Conceição das Alagoas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17405','Conceição de Ipanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17504','Conceição do Mato Dentro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17603','Conceição do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17702','Conceição do Rio Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17801','Conceição dos Ouros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17836','Cônego Marinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17876','Confins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'17900','Congonhal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18007','Congonhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18106','Congonhas do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18205','Conquista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18304','Conselheiro Lafaiete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18403','Conselheiro Pena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18502','Consolação');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18601','Contagem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18700','Coqueiral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18809','Coração de Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'18908','Cordisburgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19005','Cordislândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19104','Corinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19203','Coroaci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19302','Coromandel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19401','Coronel Fabriciano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19500','Coronel Murta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19609','Coronel Pacheco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19708','Coronel Xavier Chaves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19807','Córrego Danta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19906','Córrego do Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'19955','Córrego Fundo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20003','Córrego Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20102','Couto de Magalhães de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20151','Crisólita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20201','Cristais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20300','Cristália');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20409','Cristiano Otoni');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20508','Cristina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20607','Crucilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20706','Cruzeiro da Fortaleza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20805','Cruzília');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20839','Cuparaque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20870','Curral de Dentro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'20904','Curvelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21001','Datas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21100','Delfim Moreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21209','Delfinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21258','Delta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21308','Descoberto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21407','Desterro de Entre Rios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21506','Desterro do Melo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21605','Diamantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21704','Diogo de Vasconcelos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21803','Dionísio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'21902','Divinésia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22009','Divino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22108','Divino das Laranjeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22207','Divinolândia de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22306','Divinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22355','Divisa Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22405','Divisa Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22454','Divisópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22470','Dom Bosco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22504','Dom Cavati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22603','Dom Joaquim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22702','Dom Silvério');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22801','Dom Viçoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'22900','Dona Eusébia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23007','Dores de Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23106','Dores de Guanhães');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23205','Dores do Indaiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23304','Dores do Turvo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23403','Doresópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23502','Douradoquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23528','Durandé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23601','Elói Mendes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23700','Engenheiro Caldas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23809','Engenheiro Navarro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23858','Entre Folhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'23908','Entre Rios de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24005','Ervália');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24104','Esmeraldas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24203','Espera Feliz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24302','Espinosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24401','Espírito Santo do Dourado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24500','Estiva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24609','Estrela Dalva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24708','Estrela do Indaiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24807','Estrela do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'24906','Eugenópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25002','Ewbank da Câmara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25101','Extrema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25200','Fama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25309','Faria Lemos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25408','Felício dos Santos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25507','São Gonçalo do Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25606','Felisburgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25705','Felixlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25804','Fernandes Tourinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25903','Ferros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'25952','Fervedouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26000','Florestal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26109','Formiga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26208','Formoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26307','Fortaleza de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26406','Fortuna de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26505','Francisco Badaró');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26604','Francisco Dumont');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26703','Francisco Sá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26752','Franciscópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26802','Frei Gaspar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26901','Frei Inocêncio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'26950','Frei Lagonegro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27008','Fronteira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27057','Fronteira dos Vales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27073','Fruta de Leite');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27107','Frutal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27206','Funilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27305','Galiléia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27339','Gameleiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27354','Glaucilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27370','Goiabeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27388','Goianá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27404','Gonçalves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27503','Gonzaga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27602','Gouveia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27701','Governador Valadares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27800','Grão Mogol');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'27909','Grupiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28006','Guanhães');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28105','Guapé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28204','Guaraciaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28253','Guaraciama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28303','Guaranésia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28402','Guarani');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28501','Guarará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28600','Guarda-Mor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28709','Guaxupé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28808','Guidoval');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'28907','Guimarânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29004','Guiricema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29103','Gurinhatã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29202','Heliodora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29301','Iapu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29400','Ibertioga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29509','Ibiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29608','Ibiaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29657','Ibiracatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29707','Ibiraci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29806','Ibirité');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'29905','Ibitiúra de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30002','Ibituruna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30051','Icaraí de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30101','Igarapé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30200','Igaratinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30309','Iguatama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30408','Ijaci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30507','Ilicínea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30556','Imbé de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30606','Inconfidentes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30655','Indaiabira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30705','Indianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30804','Ingaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'30903','Inhapim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31000','Inhaúma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31109','Inimutaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31158','Ipaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31208','Ipanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31307','Ipatinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31406','Ipiaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31505','Ipuiúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31604','Iraí de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31703','Itabira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31802','Itabirinha de Mantena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'31901','Itabirito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32008','Itacambira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32107','Itacarambi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32206','Itaguara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32305','Itaipé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32404','Itajubá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32503','Itamarandiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32602','Itamarati de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32701','Itambacuri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32800','Itambé do Mato Dentro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'32909','Itamogi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33006','Itamonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33105','Itanhandu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33204','Itanhomi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33303','Itaobim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33402','Itapagipe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33501','Itapecerica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33600','Itapeva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33709','Itatiaiuçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33758','Itaú de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33808','Itaúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'33907','Itaverava');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34004','Itinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34103','Itueta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34202','Ituiutaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34301','Itumirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34400','Iturama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34509','Itutinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34608','Jaboticatubas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34707','Jacinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34806','Jacuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'34905','Jacutinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35001','Jaguaraçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35050','Jaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35076','Jampruca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35100','Janaúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35209','Januária');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35308','Japaraíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35357','Japonvar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35407','Jeceaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35456','Jenipapo de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35506','Jequeri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35605','Jequitaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35704','Jequitibá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35803','Jequitinhonha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'35902','Jesuânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36009','Joaíma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36108','Joanésia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36207','João Monlevade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36306','João Pinheiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36405','Joaquim Felício');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36504','Jordânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36520','José Gonçalves de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36553','José Raydan');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36579','Josenópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36603','Nova União');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36652','Juatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36702','Juiz de Fora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36801','Juramento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36900','Juruaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'36959','Juvenília');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37007','Ladainha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37106','Lagamar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37205','Lagoa da Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37304','Lagoa dos Patos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37403','Lagoa Dourada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37502','Lagoa Formosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37536','Lagoa Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37601','Lagoa Santa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37700','Lajinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37809','Lambari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'37908','Lamim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38005','Laranjal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38104','Lassance');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38203','Lavras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38302','Leandro Ferreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38351','Leme do Prado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38401','Leopoldina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38500','Liberdade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38609','Lima Duarte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38625','Limeira do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38658','Lontra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38674','Luisburgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38682','Luislândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38708','Luminárias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38807','Luz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'38906','Machacalis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39003','Machado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39102','Madre de Deus de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39201','Malacacheta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39250','Mamonas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39300','Manga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39409','Manhuaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39508','Manhumirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39607','Mantena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39706','Maravilhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39805','Mar de Espanha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'39904','Maria da Fé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40001','Mariana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40100','Marilac');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40159','Mário Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40209','Maripá de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40308','Marliéria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40407','Marmelópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40506','Martinho Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40530','Martins Soares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40555','Mata Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40605','Materlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40704','Mateus Leme');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40803','Matias Barbosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40852','Matias Cardoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'40902','Matipó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41009','Mato Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41108','Matozinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41207','Matutina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41306','Medeiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41405','Medina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41504','Mendes Pimentel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41603','Mercês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41702','Mesquita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41801','Minas Novas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'41900','Minduri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42007','Mirabela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42106','Miradouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42205','Miraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42254','Miravânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42304','Moeda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42403','Moema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42502','Monjolos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42601','Monsenhor Paulo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42700','Montalvânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42809','Monte Alegre de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'42908','Monte Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43005','Monte Belo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43104','Monte Carmelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43153','Monte Formoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43203','Monte Santo de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43302','Montes Claros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43401','Monte Sião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43450','Montezuma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43500','Morada Nova de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43609','Morro da Garça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43708','Morro do Pilar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43807','Munhoz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'43906','Muriaé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44003','Mutum');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44102','Muzambinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44201','Nacip Raydan');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44300','Nanuque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44359','Naque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44375','Natalândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44409','Natércia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44508','Nazareno');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44607','Nepomuceno');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44656','Ninheira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44672','Nova Belém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44706','Nova Era');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44805','Nova Lima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'44904','Nova Módica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45000','Nova Ponte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45059','Nova Porteirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45109','Nova Resende');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45208','Nova Serrana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45307','Novo Cruzeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45356','Novo Oriente de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45372','Novorizonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45406','Olaria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45455','Olhos-d''Água');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45505','Olímpio Noronha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45604','Oliveira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45703','Oliveira Fortes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45802','Onça de Pitangui');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45851','Oratórios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45877','Orizânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'45901','Ouro Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46008','Ouro Fino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46107','Ouro Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46206','Ouro Verde de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46255','Padre Carvalho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46305','Padre Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46404','Paineiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46503','Pains');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46552','Pai Pedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46602','Paiva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46701','Palma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46750','Palmópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'46909','Papagaios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47006','Paracatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47105','Pará de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47204','Paraguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47303','Paraisópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47402','Paraopeba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47501','Passabém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47600','Passa Quatro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47709','Passa Tempo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47808','Passa-Vinte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47907','Passos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'47956','Patis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48004','Patos de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48103','Patrocínio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48202','Patrocínio do Muriaé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48301','Paula Cândido');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48400','Paulistas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48509','Pavão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48608','Peçanha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48707','Pedra Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48756','Pedra Bonita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48806','Pedra do Anta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'48905','Pedra do Indaiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49002','Pedra Dourada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49101','Pedralva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49150','Pedras de Maria da Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49200','Pedrinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49309','Pedro Leopoldo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49408','Pedro Teixeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49507','Pequeri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49606','Pequi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49705','Perdigão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49804','Perdizes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49903','Perdões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'49952','Periquito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50000','Pescador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50109','Piau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50158','Piedade de Caratinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50208','Piedade de Ponte Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50307','Piedade do Rio Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50406','Piedade dos Gerais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50505','Pimenta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50539','Pingo-d''Água');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50570','Pintópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50604','Piracema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50703','Pirajuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50802','Piranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'50901','Piranguçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51008','Piranguinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51107','Pirapetinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51206','Pirapora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51305','Piraúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51404','Pitangui');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51503','Piumhi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51602','Planura');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51701','Poço Fundo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51800','Poços de Caldas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'51909','Pocrane');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52006','Pompéu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52105','Ponte Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52131','Ponto Chique');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52170','Ponto dos Volantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52204','Porteirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52303','Porto Firme');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52402','Poté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52501','Pouso Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52600','Pouso Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52709','Prados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52808','Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'52907','Pratápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53004','Pratinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53103','Presidente Bernardes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53202','Presidente Juscelino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53301','Presidente Kubitschek');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53400','Presidente Olegário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53509','Alto Jequitibá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53608','Prudente de Morais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53707','Quartel Geral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53806','Queluzito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'53905','Raposos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54002','Raul Soares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54101','Recreio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54150','Reduto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54200','Resende Costa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54309','Resplendor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54408','Ressaquinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54457','Riachinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54507','Riacho dos Machados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54606','Ribeirão das Neves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54705','Ribeirão Vermelho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54804','Rio Acima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'54903','Rio Casca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55009','Rio Doce');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55108','Rio do Prado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55207','Rio Espera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55306','Rio Manso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55405','Rio Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55504','Rio Paranaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55603','Rio Pardo de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55702','Rio Piracicaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55801','Rio Pomba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'55900','Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56007','Rio Vermelho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56106','Ritápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56205','Rochedo de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56304','Rodeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56403','Romaria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56452','Rosário da Limeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56502','Rubelita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56601','Rubim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56700','Sabará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56809','Sabinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'56908','Sacramento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57005','Salinas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57104','Salto da Divisa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57203','Santa Bárbara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57252','Santa Bárbara do Leste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57278','Santa Bárbara do Monte Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57302','Santa Bárbara do Tugúrio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57336','Santa Cruz de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57377','Santa Cruz de Salinas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57401','Santa Cruz do Escalvado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57500','Santa Efigênia de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57609','Santa Fé de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57658','Santa Helena de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57708','Santa Juliana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57807','Santa Luzia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'57906','Santa Margarida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58003','Santa Maria de Itabira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58102','Santa Maria do Salto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58201','Santa Maria do Suaçuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58300','Santana da Vargem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58409','Santana de Cataguases');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58508','Santana de Pirapama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58607','Santana do Deserto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58706','Santana do Garambéu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58805','Santana do Jacaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58904','Santana do Manhuaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'58953','Santana do Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59001','Santana do Riacho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59100','Santana dos Montes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59209','Santa Rita de Caldas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59308','Santa Rita de Jacutinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59357','Santa Rita de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59407','Santa Rita de Ibitipoca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59506','Santa Rita do Itueto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59605','Santa Rita do Sapucaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59704','Santa Rosa da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59803','Santa Vitória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'59902','Santo Antônio do Amparo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60009','Santo Antônio do Aventureiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60108','Santo Antônio do Grama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60207','Santo Antônio do Itambé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60306','Santo Antônio do Jacinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60405','Santo Antônio do Monte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60454','Santo Antônio do Retiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60504','Santo Antônio do Rio Abaixo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60603','Santo Hipólito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60702','Santos Dumont');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60801','São Bento Abade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60900','São Brás do Suaçuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'60959','São Domingos das Dores');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61007','São Domingos do Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61056','São Félix de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61106','São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61205','São Francisco de Paula');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61304','São Francisco de Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61403','São Francisco do Glória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61502','São Geraldo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61601','São Geraldo da Piedade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61650','São Geraldo do Baixio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61700','São Gonçalo do Abaeté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61809','São Gonçalo do Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'61908','São Gonçalo do Rio Abaixo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62005','São Gonçalo do Sapucaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62104','São Gotardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62203','São João Batista do Glória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62252','São João da Lagoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62302','São João da Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62401','São João da Ponte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62450','São João das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62500','São João del Rei');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62559','São João do Manhuaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62575','São João do Manteninha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62609','São João do Oriente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62658','São João do Pacuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62708','São João do Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62807','São João Evangelista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62906','São João Nepomuceno');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62922','São Joaquim de Bicas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62948','São José da Barra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'62955','São José da Lapa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63003','São José da Safira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63102','São José da Varginha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63201','São José do Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63300','São José do Divino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63409','São José do Goiabal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63508','São José do Jacuri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63607','São José do Mantimento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63706','São Lourenço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63805','São Miguel do Anta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'63904','São Pedro da União');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64001','São Pedro dos Ferros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64100','São Pedro do Suaçuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64209','São Romão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64308','São Roque de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64407','São Sebastião da Bela Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64431','São Sebastião da Vargem Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64472','São Sebastião do Anta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64506','São Sebastião do Maranhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64605','São Sebastião do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64704','São Sebastião do Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64803','São Sebastião do Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'64902','São Sebastião do Rio Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65008','São Tiago');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65107','São Tomás de Aquino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65206','São Thomé das Letras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65305','São Vicente de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65404','Sapucaí-Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65503','Sardoá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65537','Sarzedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65552','Setubinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65560','Sem-Peixe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65578','Senador Amaral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65602','Senador Cortes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65701','Senador Firmino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65800','Senador José Bento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'65909','Senador Modestino Gonçalves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66006','Senhora de Oliveira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66105','Senhora do Porto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66204','Senhora dos Remédios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66303','Sericita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66402','Seritinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66501','Serra Azul de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66600','Serra da Saudade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66709','Serra dos Aimorés');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66808','Serra do Salitre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66907','Serrania');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'66956','Serranópolis de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67004','Serranos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67103','Serro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67202','Sete Lagoas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67301','Silveirânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67400','Silvianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67509','Simão Pereira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67608','Simonésia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67707','Sobrália');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67806','Soledade de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'67905','Tabuleiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68002','Taiobeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68051','Taparuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68101','Tapira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68200','Tapiraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68309','Taquaraçu de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68408','Tarumirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68507','Teixeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68606','Teófilo Otoni');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68705','Timóteo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68804','Tiradentes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'68903','Tiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69000','Tocantins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69059','Tocos do Moji');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69109','Toledo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69208','Tombos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69307','Três Corações');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69356','Três Marias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69406','Três Pontas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69505','Tumiritinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69604','Tupaciguara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69703','Turmalina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69802','Turvolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'69901','Ubá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70008','Ubaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70057','Ubaporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70107','Uberaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70206','Uberlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70305','Umburatiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70404','Unaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70438','União de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70479','Uruana de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70503','Urucânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70529','Urucuia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70578','Vargem Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70602','Vargem Bonita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70651','Vargem Grande do Rio Pardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70701','Varginha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70750','Varjão de Minas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70800','Várzea da Palma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'70909','Varzelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71006','Vazante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71030','Verdelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71071','Veredinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71105','Veríssimo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71154','Vermelho Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71204','Vespasiano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71303','Viçosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71402','Vieiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71501','Mathias Lobato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71600','Virgem da Lapa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71709','Virgínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71808','Virginópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'71907','Virgolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'72004','Visconde do Rio Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'72103','Volta Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MG'),'72202','Wenceslau Braz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00102','Afonso Cláudio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00136','Águia Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00169','Água Doce do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00201','Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00300','Alfredo Chaves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00359','Alto Rio Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00409','Anchieta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00508','Apiacá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00607','Aracruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00706','Atilio Vivacqua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00805','Baixo Guandu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'00904','Barra de São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01001','Boa Esperança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01100','Bom Jesus do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01159','Brejetuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01209','Cachoeiro de Itapemirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01308','Cariacica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01407','Castelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01506','Colatina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01605','Conceição da Barra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01704','Conceição do Castelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01803','Divino de São Lourenço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'01902','Domingos Martins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02009','Dores do Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02108','Ecoporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02207','Fundão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02306','Guaçuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02405','Guarapari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02454','Ibatiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02504','Ibiraçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02553','Ibitirama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02603','Iconha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02652','Irupi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02702','Itaguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02801','Itapemirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'02900','Itarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03007','Iúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03056','Jaguaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03106','Jerônimo Monteiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03130','João Neiva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03163','Laranja da Terra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03205','Linhares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03304','Mantenópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03320','Marataízes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03346','Marechal Floriano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03353','Marilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03403','Mimoso do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03502','Montanha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03601','Mucurici');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03700','Muniz Freire');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03809','Muqui');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'03908','Nova Venécia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04005','Pancas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04054','Pedro Canário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04104','Pinheiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04203','Piúma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04252','Ponto Belo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04302','Presidente Kennedy');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04351','Rio Bananal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04401','Rio Novo do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04500','Santa Leopoldina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04559','Santa Maria de Jetibá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04609','Santa Teresa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04658','São Domingos do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04708','São Gabriel da Palha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04807','São José do Calçado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04906','São Mateus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'04955','São Roque do Canaã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05002','Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05010','Sooretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05036','Vargem Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05069','Venda Nova do Imigrante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05101','Viana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05150','Vila Pavão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05176','Vila Valério');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05200','Vila Velha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'ES'),'05309','Vitória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00100','Angra dos Reis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00159','Aperibé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00209','Araruama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00225','Areal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00233','Armação dos Búzios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00258','Arraial do Cabo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00308','Barra do Piraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00407','Barra Mansa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00456','Belford Roxo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00506','Bom Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00605','Bom Jesus do Itabapoana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00704','Cabo Frio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00803','Cachoeiras de Macacu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00902','Cambuci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00936','Carapebus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'00951','Comendador Levy Gasparian');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01009','Campos dos Goytacazes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01108','Cantagalo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01157','Cardoso Moreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01207','Carmo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01306','Casimiro de Abreu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01405','Conceição de Macabu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01504','Cordeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01603','Duas Barras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01702','Duque de Caxias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01801','Engenheiro Paulo de Frontin');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01850','Guapimirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01876','Iguaba Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'01900','Itaboraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02007','Itaguaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02056','Italva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02106','Itaocara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02205','Itaperuna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02254','Itatiaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02270','Japeri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02304','Laje do Muriaé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02403','Macaé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02452','Macuco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02502','Magé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02601','Mangaratiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02700','Maricá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02809','Mendes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'02908','Miguel Pereira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03005','Miracema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03104','Natividade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03203','Nilópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03302','Niterói');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03401','Nova Friburgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03500','Nova Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03609','Paracambi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03708','Paraíba do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03807','Parati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03856','Paty do Alferes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03906','Petrópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'03955','Pinheiral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04003','Piraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04102','Porciúncula');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04110','Porto Real');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04128','Quatis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04144','Queimados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04151','Quissamã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04201','Resende');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04300','Rio Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04409','Rio Claro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04508','Rio das Flores');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04524','Rio das Ostras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04557','Rio de Janeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04607','Santa Maria Madalena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04706','Santo Antônio de Pádua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04755','São Francisco de Itabapoana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04805','São Fidélis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'04904','São Gonçalo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05000','São João da Barra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05109','São João de Meriti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05133','São José de Ubá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05158','São José do Vale do Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05208','São Pedro da Aldeia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05307','São Sebastião do Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05406','Sapucaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05505','Saquarema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05554','Seropédica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05604','Silva Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05703','Sumidouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05752','Tanguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05802','Teresópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'05901','Trajano de Morais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'06008','Três Rios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'06107','Valença');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'06156','Varre-Sai');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'06206','Vassouras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RJ'),'06305','Volta Redonda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00105','Adamantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00204','Adolfo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00303','Aguaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00402','Águas da Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00501','Águas de Lindóia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00550','Águas de Santa Bárbara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00600','Águas de São Pedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00709','Agudos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00758','Alambari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00808','Alfredo Marcondes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'00907','Altair');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01004','Altinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01103','Alto Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01152','Alumínio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01202','Álvares Florence');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01301','Álvares Machado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01400','Álvaro de Carvalho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01509','Alvinlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01608','Americana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01707','Américo Brasiliense');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01806','Américo de Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'01905','Amparo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02002','Analândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02101','Andradina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02200','Angatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02309','Anhembi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02408','Anhumas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02507','Aparecida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02606','Aparecida d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02705','Apiaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02754','Araçariguama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02804','Araçatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'02903','Araçoiaba da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03000','Aramina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03109','Arandu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03158','Arapeí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03208','Araraquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03307','Araras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03356','Arco-Íris');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03406','Arealva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03505','Areias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03604','Areiópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03703','Ariranha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03802','Artur Nogueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03901','Arujá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'03950','Aspásia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04008','Assis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04107','Atibaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04206','Auriflama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04305','Avaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04404','Avanhandava');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04503','Avaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04602','Bady Bassitt');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04701','Balbinos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04800','Bálsamo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'04909','Bananal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05005','Barão de Antonina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05104','Barbosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05203','Bariri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05302','Barra Bonita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05351','Barra do Chapéu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05401','Barra do Turvo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05500','Barretos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05609','Barrinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05708','Barueri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05807','Bastos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'05906','Batatais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06003','Bauru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06102','Bebedouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06201','Bento de Abreu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06300','Bernardino de Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06359','Bertioga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06409','Bilac');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06508','Birigui');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06607','Biritiba-Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06706','Boa Esperança do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06805','Bocaina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'06904','Bofete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07001','Boituva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07100','Bom Jesus dos Perdões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07159','Bom Sucesso de Itararé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07209','Borá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07308','Boracéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07407','Borborema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07456','Borebi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07506','Botucatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07605','Bragança Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07704','Braúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07753','Brejo Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07803','Brodowski');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'07902','Brotas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08009','Buri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08108','Buritama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08207','Buritizal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08306','Cabrália Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08405','Cabreúva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08504','Caçapava');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08603','Cachoeira Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08702','Caconde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08801','Cafelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'08900','Caiabu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09007','Caieiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09106','Caiuá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09205','Cajamar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09254','Cajati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09304','Cajobi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09403','Cajuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09452','Campina do Monte Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09502','Campinas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09601','Campo Limpo Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09700','Campos do Jordão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09809','Campos Novos Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09908','Cananéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'09957','Canas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10005','Cândido Mota');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10104','Cândido Rodrigues');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10153','Canitar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10203','Capão Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10302','Capela do Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10401','Capivari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10500','Caraguatatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10609','Carapicuíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10708','Cardoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10807','Casa Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'10906','Cássia dos Coqueiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11003','Castilho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11102','Catanduva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11201','Catiguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11300','Cedral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11409','Cerqueira César');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11508','Cerquilho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11607','Cesário Lange');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11706','Charqueada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'11904','Clementina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12001','Colina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12100','Colômbia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12209','Conchal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12308','Conchas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12407','Cordeirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12506','Coroados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12605','Coronel Macedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12704','Corumbataí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12803','Cosmópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'12902','Cosmorama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13009','Cotia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13108','Cravinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13207','Cristais Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13306','Cruzália');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13405','Cruzeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13504','Cubatão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13603','Cunha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13702','Descalvado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13801','Diadema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13850','Dirce Reis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'13900','Divinolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14007','Dobrada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14106','Dois Córregos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14205','Dolcinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14304','Dourado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14403','Dracena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14502','Duartina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14601','Dumont');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14700','Echaporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14809','Eldorado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14908','Elias Fausto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14924','Elisiário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'14957','Embaúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15004','Embu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15103','Embu-Guaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15129','Emilianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15152','Engenheiro Coelho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15186','Espírito Santo do Pinhal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15194','Espírito Santo do Turvo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15202','Estrela d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15301','Estrela do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15350','Euclides da Cunha Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15400','Fartura');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15509','Fernandópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15608','Fernando Prestes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15657','Fernão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15707','Ferraz de Vasconcelos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15806','Flora Rica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'15905','Floreal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16002','Flórida Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16101','Florínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16200','Franca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16309','Francisco Morato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16408','Franco da Rocha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16507','Gabriel Monteiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16606','Gália');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16705','Garça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16804','Gastão Vidigal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16853','Gavião Peixoto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'16903','General Salgado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17000','Getulina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17109','Glicério');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17208','Guaiçara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17307','Guaimbê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17406','Guaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17505','Guapiaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17604','Guapiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17703','Guará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17802','Guaraçaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'17901','Guaraci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18008','Guarani d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18107','Guarantã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18206','Guararapes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18305','Guararema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18404','Guaratinguetá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18503','Guareí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18602','Guariba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18701','Guarujá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18800','Guarulhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18859','Guatapará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'18909','Guzolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19006','Herculândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19055','Holambra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19071','Hortolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19105','Iacanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19204','Iacri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19253','Iaras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19303','Ibaté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19402','Ibirá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19501','Ibirarema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19600','Ibitinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19709','Ibiúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19808','Icém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'19907','Iepê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20004','Igaraçu do Tietê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20103','Igarapava');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20202','Igaratá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20301','Iguape');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20400','Ilhabela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20426','Ilha Comprida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20442','Ilha Solteira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20509','Indaiatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20608','Indiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20707','Indiaporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20806','Inúbia Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'20905','Ipauçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21002','Iperó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21101','Ipeúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21150','Ipiguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21200','Iporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21309','Ipuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21408','Iracemápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21507','Irapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21606','Irapuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21705','Itaberá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21804','Itaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'21903','Itajobi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22000','Itaju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22109','Itanhaém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22158','Itaóca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22208','Itapecerica da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22307','Itapetininga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22406','Itapeva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22505','Itapevi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22604','Itapira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22653','Itapirapuã Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22703','Itápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22802','Itaporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'22901','Itapuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23008','Itapura');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23107','Itaquaquecetuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23206','Itararé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23305','Itariri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23404','Itatiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23503','Itatinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23602','Itirapina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23701','Itirapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23800','Itobi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'23909','Itu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24006','Itupeva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24105','Ituverava');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24204','Jaborandi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24303','Jaboticabal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24402','Jacareí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24501','Jaci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24600','Jacupiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24709','Jaguariúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24808','Jales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'24907','Jambeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25003','Jandira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25102','Jardinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25201','Jarinu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25300','Jaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25409','Jeriquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25508','Joanópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25607','João Ramalho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25706','José Bonifácio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25805','Júlio Mesquita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25854','Jumirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'25904','Jundiaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26001','Junqueirópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26100','Juquiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26209','Juquitiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26308','Lagoinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26407','Laranjal Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26506','Lavínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26605','Lavrinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26704','Leme');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26803','Lençóis Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'26902','Limeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27009','Lindóia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27108','Lins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27207','Lorena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27256','Lourdes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27306','Louveira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27405','Lucélia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27504','Lucianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27603','Luís Antônio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27702','Luiziânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27801','Lupércio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'27900','Lutécia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28007','Macatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28106','Macaubal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28205','Macedônia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28304','Magda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28403','Mairinque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28502','Mairiporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28601','Manduri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28700','Marabá Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28809','Maracaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28858','Marapoama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'28908','Mariápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29005','Marília');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29104','Marinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29203','Martinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29302','Matão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29401','Mauá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29500','Mendonça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29609','Meridiano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29658','Mesópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29708','Miguelópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29807','Mineiros do Tietê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'29906','Miracatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30003','Mira Estrela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30102','Mirandópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30201','Mirante do Paranapanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30300','Mirassol');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30409','Mirassolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30508','Mococa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30607','Moji das Cruzes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30706','Mogi Guaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30805','Moji-Mirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'30904','Mombuca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31001','Monções');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31100','Mongaguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31209','Monte Alegre do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31308','Monte Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31407','Monte Aprazível');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31506','Monte Azul Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31605','Monte Castelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31704','Monteiro Lobato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31803','Monte Mor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'31902','Morro Agudo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32009','Morungaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32058','Motuca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32108','Murutinga do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32157','Nantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32207','Narandiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32306','Natividade da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32405','Nazaré Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32504','Neves Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32603','Nhandeara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32702','Nipoã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32801','Nova Aliança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32827','Nova Campina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32843','Nova Canaã Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32868','Nova Castilho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'32900','Nova Europa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33007','Nova Granada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33106','Nova Guataporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33205','Nova Independência');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33254','Novais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33304','Nova Luzitânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33403','Nova Odessa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33502','Novo Horizonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33601','Nuporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33700','Ocauçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33809','Óleo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'33908','Olímpia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34005','Onda Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34104','Oriente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34203','Orindiúva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34302','Orlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34401','Osasco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34500','Oscar Bressane');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34609','Osvaldo Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34708','Ourinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34757','Ouroeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34807','Ouro Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'34906','Pacaembu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35002','Palestina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35101','Palmares Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35200','Palmeira d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35309','Palmital');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35408','Panorama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35507','Paraguaçu Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35606','Paraibuna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35705','Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35804','Paranapanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'35903','Paranapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36000','Parapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36109','Pardinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36208','Pariquera-Açu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36257','Parisi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36307','Patrocínio Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36406','Paulicéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36505','Paulínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36570','Paulistânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36604','Paulo de Faria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36703','Pederneiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36802','Pedra Bela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'36901','Pedranópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37008','Pedregulho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37107','Pedreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37156','Pedrinhas Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37206','Pedro de Toledo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37305','Penápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37404','Pereira Barreto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37503','Pereiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37602','Peruíbe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37701','Piacatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37800','Piedade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'37909','Pilar do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38006','Pindamonhangaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38105','Pindorama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38204','Pinhalzinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38303','Piquerobi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38501','Piquete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38600','Piracaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38709','Piracicaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38808','Piraju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'38907','Pirajuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39004','Pirangi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39103','Pirapora do Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39202','Pirapozinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39301','Pirassununga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39400','Piratininga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39509','Pitangueiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39608','Planalto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39707','Platina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39806','Poá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'39905','Poloni');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40002','Pompéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40101','Pongaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40200','Pontal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40259','Pontalinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40309','Pontes Gestal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40408','Populina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40507','Porangaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40606','Porto Feliz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40705','Porto Ferreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40754','Potim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40804','Potirendaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40853','Pracinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'40903','Pradópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41000','Praia Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41059','Pratânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41109','Presidente Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41208','Presidente Bernardes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41307','Presidente Epitácio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41406','Presidente Prudente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41505','Presidente Venceslau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41604','Promissão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41653','Quadra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41703','Quatá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41802','Queiroz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'41901','Queluz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42008','Quintana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42107','Rafard');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42206','Rancharia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42305','Redenção da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42404','Regente Feijó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42503','Reginópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42602','Registro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42701','Restinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42800','Ribeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'42909','Ribeirão Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43006','Ribeirão Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43105','Ribeirão Corrente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43204','Ribeirão do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43238','Ribeirão dos Índios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43253','Ribeirão Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43303','Ribeirão Pires');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43402','Ribeirão Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43501','Riversul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43600','Rifaina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43709','Rincão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43808','Rinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'43907','Rio Claro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44004','Rio das Pedras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44103','Rio Grande da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44202','Riolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44251','Rosana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44301','Roseira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44400','Rubiácea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44509','Rubinéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44608','Sabino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44707','Sagres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44806','Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'44905','Sales Oliveira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45001','Salesópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45100','Salmourão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45159','Saltinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45209','Salto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45308','Salto de Pirapora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45407','Salto Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45506','Sandovalina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45605','Santa Adélia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45704','Santa Albertina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'45803','Santa Bárbara d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46009','Santa Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46108','Santa Clara d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46207','Santa Cruz da Conceição');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46256','Santa Cruz da Esperança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46306','Santa Cruz das Palmeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46405','Santa Cruz do Rio Pardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46504','Santa Ernestina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46603','Santa Fé do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46702','Santa Gertrudes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46801','Santa Isabel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'46900','Santa Lúcia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47007','Santa Maria da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47106','Santa Mercedes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47205','Santana da Ponte Pensa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47304','Santana de Parnaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47403','Santa Rita d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47502','Santa Rita do Passa Quatro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47601','Santa Rosa de Viterbo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47650','Santa Salete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47700','Santo Anastácio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47809','Santo André');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'47908','Santo Antônio da Alegria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48005','Santo Antônio de Posse');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48054','Santo Antônio do Aracanguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48104','Santo Antônio do Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48203','Santo Antônio do Pinhal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48302','Santo Expedito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48401','Santópolis do Aguapeí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48500','Santos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48609','São Bento do Sapucaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48708','São Bernardo do Campo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48807','São Caetano do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'48906','São Carlos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49003','São Francisco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49102','São João da Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49201','São João das Duas Pontes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49250','São João de Iracema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49300','São João do Pau d''Alho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49409','São Joaquim da Barra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49508','São José da Bela Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49607','São José do Barreiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49706','São José do Rio Pardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49805','São José do Rio Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49904','São José dos Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'49953','São Lourenço da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50001','São Luís do Paraitinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50100','São Manuel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50209','São Miguel Arcanjo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50308','São Paulo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50407','São Pedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50506','São Pedro do Turvo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50605','São Roque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50704','São Sebastião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50803','São Sebastião da Grama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'50902','São Simão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51009','São Vicente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51108','Sarapuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51207','Sarutaiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51306','Sebastianópolis do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51405','Serra Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51504','Serrana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51603','Serra Negra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51702','Sertãozinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51801','Sete Barras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'51900','Severínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52007','Silveiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52106','Socorro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52205','Sorocaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52304','Sud Mennucci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52403','Sumaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52502','Suzano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52551','Suzanápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52601','Tabapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52700','Tabatinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52809','Taboão da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'52908','Taciba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53005','Taguaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53104','Taiaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53203','Taiúva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53302','Tambaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53401','Tanabi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53500','Tapiraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53609','Tapiratiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53658','Taquaral');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53708','Taquaritinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53807','Taquarituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53856','Taquarivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53906','Tarabai');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'53955','Tarumã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54003','Tatuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54102','Taubaté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54201','Tejupá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54300','Teodoro Sampaio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54409','Terra Roxa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54508','Tietê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54607','Timburi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54656','Torre de Pedra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54706','Torrinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54755','Trabiju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54805','Tremembé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54904','Três Fronteiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'54953','Tuiuti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55000','Tupã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55109','Tupi Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55208','Turiúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55307','Turmalina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55356','Ubarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55406','Ubatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55505','Ubirajara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55604','Uchoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55703','União Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55802','Urânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'55901','Uru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56008','Urupês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56107','Valentim Gentil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56206','Valinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56305','Valparaíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56354','Vargem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56404','Vargem Grande do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56453','Vargem Grande Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56503','Várzea Paulista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56602','Vera Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56701','Vinhedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56800','Viradouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56909','Vista Alegre do Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'56958','Vitória Brasil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'57006','Votorantim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'57105','Votuporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'57154','Zacarias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'57204','Chavantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SP'),'57303','Estiva Gerbi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00103','Abatiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00202','Adrianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00301','Agudos do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00400','Almirante Tamandaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00459','Altamira do Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00509','Altônia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00608','Alto Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00707','Alto Piquiri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00806','Alvorada do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'00905','Amaporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01002','Ampére');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01051','Anahy');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01101','Andirá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01150','Ângulo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01200','Antonina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01309','Antônio Olinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01408','Apucarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01507','Arapongas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01606','Arapoti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01655','Arapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01705','Araruna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01804','Araucária');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01853','Ariranha do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'01903','Assaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02000','Assis Chateaubriand');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02109','Astorga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02208','Atalaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02307','Balsa Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02406','Bandeirantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02505','Barbosa Ferraz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02604','Barracão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02703','Barra do Jacaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02752','Bela Vista da Caroba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02802','Bela Vista do Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'02901','Bituruna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03008','Boa Esperança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03024','Boa Esperança do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03040','Boa Ventura de São Roque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03057','Boa Vista da Aparecida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03107','Bocaiúva do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03156','Bom Jesus do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03206','Bom Sucesso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03222','Bom Sucesso do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03305','Borrazópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03354','Braganey');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03370','Brasilândia do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03404','Cafeara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03453','Cafelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03479','Cafezal do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03503','Califórnia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03602','Cambará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03701','Cambé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03800','Cambira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03909','Campina da Lagoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'03958','Campina do Simão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04006','Campina Grande do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04055','Campo Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04105','Campo do Tenente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04204','Campo Largo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04253','Campo Magro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04303','Campo Mourão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04402','Cândido de Abreu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04428','Candói');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04451','Cantagalo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04501','Capanema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04600','Capitão Leônidas Marques');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04659','Carambeí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04709','Carlópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04808','Cascavel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'04907','Castro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05003','Catanduvas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05102','Centenário do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05201','Cerro Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05300','Céu Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05409','Chopinzinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05508','Cianorte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05607','Cidade Gaúcha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05706','Clevelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05805','Colombo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'05904','Colorado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06001','Congonhinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06100','Conselheiro Mairinck');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06209','Contenda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06308','Corbélia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06407','Cornélio Procópio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06456','Coronel Domingos Soares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06506','Coronel Vivida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06555','Corumbataí do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06571','Cruzeiro do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06605','Cruzeiro do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06704','Cruzeiro do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06803','Cruz Machado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06852','Cruzmaltina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'06902','Curitiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07009','Curiúva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07108','Diamante do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07124','Diamante do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07157','Diamante D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07207','Dois Vizinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07256','Douradina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07306','Doutor Camargo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07405','Enéas Marques');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07504','Engenheiro Beltrão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07520','Esperança Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07538','Entre Rios do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07546','Espigão Alto do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07553','Farol');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07603','Faxinal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07652','Fazenda Rio Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07702','Fênix');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07736','Fernandes Pinheiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07751','Figueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07801','Floraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07850','Flor da Serra do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'07900','Floresta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08007','Florestópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08106','Flórida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08205','Formosa do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08304','Foz do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08320','Francisco Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08403','Francisco Beltrão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08452','Foz do Jordão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08502','General Carneiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08551','Godoy Moreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08601','Goioerê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08650','Goioxim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08700','Grandes Rios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08809','Guaíra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08908','Guairaçá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'08957','Guamiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09005','Guapirama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09104','Guaporema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09203','Guaraci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09302','Guaraniaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09401','Guarapuava');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09500','Guaraqueçaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09609','Guaratuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09658','Honório Serpa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09708','Ibaiti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09757','Ibema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09807','Ibiporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'09906','Icaraíma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10003','Iguaraçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10052','Iguatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10078','Imbaú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10102','Imbituva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10201','Inácio Martins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10300','Inajá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10409','Indianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10508','Ipiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10607','Iporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10656','Iracema do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10706','Irati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10805','Iretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10904','Itaguajé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'10953','Itaipulândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11001','Itambaracá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11100','Itambé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11209','Itapejara d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11258','Itaperuçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11308','Itaúna do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11407','Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11506','Ivaiporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11555','Ivaté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11605','Ivatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11704','Jaboti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11803','Jacarezinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'11902','Jaguapitã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12009','Jaguariaíva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12108','Jandaia do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12207','Janiópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12306','Japira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12405','Japurá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12504','Jardim Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12603','Jardim Olinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12702','Jataizinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12751','Jesuítas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12801','Joaquim Távora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12900','Jundiaí do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'12959','Juranda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13007','Jussara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13106','Kaloré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13205','Lapa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13254','Laranjal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13304','Laranjeiras do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13403','Leópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13429','Lidianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13452','Lindoeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13502','Loanda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13601','Lobato');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13700','Londrina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13734','Luiziana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13759','Lunardelli');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13809','Lupionópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'13908','Mallet');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14005','Mamborê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14104','Mandaguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14203','Mandaguari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14302','Mandirituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14351','Manfrinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14401','Mangueirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14500','Manoel Ribas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14609','Marechal Cândido Rondon');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14708','Maria Helena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14807','Marialva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'14906','Marilândia do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15002','Marilena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15101','Mariluz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15200','Maringá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15309','Mariópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15358','Maripá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15408','Marmeleiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15457','Marquinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15507','Marumbi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15606','Matelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15705','Matinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15739','Mato Rico');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15754','Mauá da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15804','Medianeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15853','Mercedes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'15903','Mirador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16000','Miraselva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16059','Missal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16109','Moreira Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16208','Morretes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16307','Munhoz de Melo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16406','Nossa Senhora das Graças');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16505','Nova Aliança do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16604','Nova América da Colina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16703','Nova Aurora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16802','Nova Cantu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16901','Nova Esperança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'16950','Nova Esperança do Sudoeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17008','Nova Fátima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17057','Nova Laranjeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17107','Nova Londrina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17206','Nova Olímpia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17214','Nova Santa Bárbara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17222','Nova Santa Rosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17255','Nova Prata do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17271','Nova Tebas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17297','Novo Itacolomi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17305','Ortigueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17404','Ourizona');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17453','Ouro Verde do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17503','Paiçandu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17602','Palmas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17701','Palmeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17800','Palmital');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'17909','Palotina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18006','Paraíso do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18105','Paranacity');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18204','Paranaguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18303','Paranapoema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18402','Paranavaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18451','Pato Bragado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18501','Pato Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18600','Paula Freitas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18709','Paulo Frontin');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18808','Peabiru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18857','Perobal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'18907','Pérola');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19004','Pérola d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19103','Piên');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19152','Pinhais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19202','Pinhalão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19251','Pinhal de São Bento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19301','Pinhão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19400','Piraí do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19509','Piraquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19608','Pitanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19657','Pitangueiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19707','Planaltina do Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19806','Planalto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19905','Ponta Grossa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'19954','Pontal do Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20002','Porecatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20101','Porto Amazonas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20150','Porto Barreiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20200','Porto Rico');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20309','Porto Vitória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20333','Prado Ferreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20358','Pranchita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20408','Presidente Castelo Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20507','Primeiro de Maio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20606','Prudentópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20655','Quarto Centenário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20705','Quatiguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20804','Quatro Barras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20853','Quatro Pontes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'20903','Quedas do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21000','Querência do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21109','Quinta do Sol');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21208','Quitandinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21257','Ramilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21307','Rancho Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21356','Rancho Alegre D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21406','Realeza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21505','Rebouças');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21604','Renascença');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21703','Reserva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21752','Reserva do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21802','Ribeirão Claro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'21901','Ribeirão do Pinhal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22008','Rio Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22107','Rio Bom');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22156','Rio Bonito do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22172','Rio Branco do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22206','Rio Branco do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22305','Rio Negro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22404','Rolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22503','Roncador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22602','Rondon');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22651','Rosário do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22701','Sabáudia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22800','Salgado Filho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'22909','Salto do Itararé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23006','Salto do Lontra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23105','Santa Amélia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23204','Santa Cecília do Pavão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23303','Santa Cruz de Monte Castelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23402','Santa Fé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23501','Santa Helena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23600','Santa Inês');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23709','Santa Isabel do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23808','Santa Izabel do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23824','Santa Lúcia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23857','Santa Maria do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23907','Santa Mariana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'23956','Santa Mônica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24004','Santana do Itararé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24020','Santa Tereza do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24053','Santa Terezinha de Itaipu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24103','Santo Antônio da Platina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24202','Santo Antônio do Caiuá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24301','Santo Antônio do Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24400','Santo Antônio do Sudoeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24509','Santo Inácio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24608','São Carlos do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24707','São Jerônimo da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24806','São João');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'24905','São João do Caiuá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25001','São João do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25100','São João do Triunfo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25209','São Jorge d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25308','São Jorge do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25357','São Jorge do Patrocínio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25407','São José da Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25456','São José das Palmeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25506','São José dos Pinhais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25555','São Manoel do Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25605','São Mateus do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25704','São Miguel do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25753','São Pedro do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25803','São Pedro do Ivaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'25902','São Pedro do Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26009','São Sebastião da Amoreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26108','São Tomé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26207','Sapopema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26256','Sarandi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26272','Saudade do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26306','Sengés');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26355','Serranópolis do Iguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26405','Sertaneja');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26504','Sertanópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26603','Siqueira Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26652','Sulina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26678','Tamarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26702','Tamboara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26801','Tapejara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'26900','Tapira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27007','Teixeira Soares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27106','Telêmaco Borba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27205','Terra Boa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27304','Terra Rica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27403','Terra Roxa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27502','Tibagi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27601','Tijucas do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27700','Toledo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27809','Tomazina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27858','Três Barras do Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27882','Tunas do Paraná');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27908','Tuneiras do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27957','Tupãssi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'27965','Turvo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28005','Ubiratã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28104','Umuarama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28203','União da Vitória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28302','Uniflor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28401','Uraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28500','Wenceslau Braz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28534','Ventania');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28559','Vera Cruz do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28609','Verê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28625','Vila Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28633','Doutor Ulysses');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28658','Virmond');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28708','Vitorino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'PR'),'28807','Xambrê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00051','Abdon Batista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00101','Abelardo Luz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00200','Agrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00309','Agronômica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00408','Água Doce');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00507','Águas de Chapecó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00556','Águas Frias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00606','Águas Mornas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00705','Alfredo Wagner');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00754','Alto Bela Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00804','Anchieta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'00903','Angelina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01000','Anita Garibaldi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01109','Anitápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01208','Antônio Carlos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01257','Apiúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01273','Arabutã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01307','Araquari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01406','Araranguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01505','Armazém');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01604','Arroio Trinta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01653','Arvoredo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01703','Ascurra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01802','Atalanta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01901','Aurora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'01950','Balneário Arroio do Silva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02008','Balneário Camboriú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02057','Balneário Barra do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02073','Balneário Gaivota');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02081','Bandeirante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02099','Barra Bonita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02107','Barra Velha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02131','Bela Vista do Toldo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02156','Belmonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02206','Benedito Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02305','Biguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02404','Blumenau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02438','Bocaina do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02453','Bombinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02503','Bom Jardim da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02537','Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02578','Bom Jesus do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02602','Bom Retiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02701','Botuverá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02800','Braço do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02859','Braço do Trombudo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02875','Brunópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'02909','Brusque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03006','Caçador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03105','Caibi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03154','Calmon');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03204','Camboriú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03253','Capão Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03303','Campo Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03402','Campo Belo do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03501','Campo Erê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03600','Campos Novos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03709','Canelinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03808','Canoinhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03907','Capinzal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'03956','Capivari de Baixo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04004','Catanduvas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04103','Caxambu do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04152','Celso Ramos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04178','Cerro Negro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04194','Chapadão do Lageado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04202','Chapecó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04251','Cocal do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04301','Concórdia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04350','Cordilheira Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04400','Coronel Freitas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04459','Coronel Martins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04509','Corupá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04558','Correia Pinto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04608','Criciúma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04707','Cunha Porã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04756','Cunhataí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04806','Curitibanos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'04905','Descanso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05001','Dionísio Cerqueira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05100','Dona Emma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05159','Doutor Pedrinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05175','Entre Rios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05191','Ermo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05209','Erval Velho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05308','Faxinal dos Guedes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05357','Flor do Sertão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05407','Florianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05431','Formosa do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05456','Forquilhinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05506','Fraiburgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05555','Frei Rogério');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05605','Galvão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05704','Garopaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05803','Garuva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'05902','Gaspar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06009','Governador Celso Ramos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06108','Grão Pará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06207','Gravatal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06306','Guabiruba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06405','Guaraciaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06504','Guaramirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06603','Guarujá do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06652','Guatambú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06702','Herval d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06751','Ibiam');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06801','Ibicaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'06900','Ibirama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07007','Içara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07106','Ilhota');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07205','Imaruí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07304','Imbituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07403','Imbuia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07502','Indaial');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07577','Iomerê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07601','Ipira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07650','Iporã do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07684','Ipuaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07700','Ipumirim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07759','Iraceminha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07809','Irani');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07858','Irati');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'07908','Irineópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08005','Itá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08104','Itaiópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08203','Itajaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08302','Itapema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08401','Itapiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08450','Itapoá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08500','Ituporanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08609','Jaborá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08708','Jacinto Machado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08807','Jaguaruna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08906','Jaraguá do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'08955','Jardinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09003','Joaçaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09102','Joinville');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09151','José Boiteux');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09177','Jupiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09201','Lacerdópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09300','Lages');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09409','Laguna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09458','Lajeado Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09508','Laurentino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09607','Lauro Muller');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09706','Lebon Régis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09805','Leoberto Leal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09854','Lindóia do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'09904','Lontras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10001','Luiz Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10035','Luzerna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10050','Macieira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10100','Mafra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10209','Major Gercino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10308','Major Vieira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10407','Maracajá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10506','Maravilha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10555','Marema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10605','Massaranduba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10704','Matos Costa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10803','Meleiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10852','Mirim Doce');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'10902','Modelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11009','Mondaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11058','Monte Carlo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11108','Monte Castelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11207','Morro da Fumaça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11256','Morro Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11306','Navegantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11405','Nova Erechim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11454','Nova Itaberaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11504','Nova Trento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11603','Nova Veneza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11652','Novo Horizonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11702','Orleans');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11751','Otacílio Costa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11801','Ouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11850','Ouro Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11876','Paial');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11892','Painel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'11900','Palhoça');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12007','Palma Sola');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12056','Palmeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12106','Palmitos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12205','Papanduva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12239','Paraíso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12254','Passo de Torres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12270','Passos Maia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12304','Paulo Lopes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12403','Pedras Grandes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12502','Penha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12601','Peritiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12700','Petrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12809','Piçarras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'12908','Pinhalzinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13005','Pinheiro Preto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13104','Piratuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13153','Planalto Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13203','Pomerode');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13302','Ponte Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13351','Ponte Alta do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13401','Ponte Serrada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13500','Porto Belo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13609','Porto União');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13708','Pouso Redondo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13807','Praia Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'13906','Presidente Castelo Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14003','Presidente Getúlio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14102','Presidente Nereu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14151','Princesa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14201','Quilombo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14300','Rancho Queimado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14409','Rio das Antas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14508','Rio do Campo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14607','Rio do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14706','Rio dos Cedros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14805','Rio do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'14904','Rio Fortuna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15000','Rio Negrinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15059','Rio Rufino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15075','Riqueza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15109','Rodeio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15208','Romelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15307','Salete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15356','Saltinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15406','Salto Veloso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15455','Sangão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15505','Santa Cecília');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15554','Santa Helena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15604','Santa Rosa de Lima');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15653','Santa Rosa do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15679','Santa Terezinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15687','Santa Terezinha do Progresso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15695','Santiago do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15703','Santo Amaro da Imperatriz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15752','São Bernardino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15802','São Bento do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'15901','São Bonifácio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16008','São Carlos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16057','São Cristovão do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16107','São Domingos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16206','São Francisco do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16255','São João do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16305','São João Batista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16354','São João do Itaperiú');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16404','São João do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16503','São Joaquim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16602','São José');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16701','São José do Cedro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16800','São José do Cerrito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'16909','São Lourenço do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17006','São Ludgero');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17105','São Martinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17154','São Miguel da Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17204','São Miguel do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17253','São Pedro de Alcântara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17303','Saudades');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17402','Schroeder');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17501','Seara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17550','Serra Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17600','Siderópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17709','Sombrio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17758','Sul Brasil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17808','Taió');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17907','Tangará');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'17956','Tigrinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18004','Tijucas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18103','Timbé do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18202','Timbó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18251','Timbó Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18301','Três Barras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18350','Treviso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18400','Treze de Maio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18509','Treze Tílias');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18608','Trombudo Central');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18707','Tubarão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18756','Tunápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18806','Turvo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18855','União do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18905','Urubici');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'18954','Urupema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19002','Urussanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19101','Vargeão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19150','Vargem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19176','Vargem Bonita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19200','Vidal Ramos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19309','Videira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19358','Vitor Meireles');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19408','Witmarsum');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19507','Xanxerê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19606','Xavantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19705','Xaxim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'SC'),'19853','Zortéa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00059','Água Santa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00109','Agudo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00208','Ajuricaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00307','Alecrim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00406','Alegrete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00455','Alegria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00505','Alpestre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00554','Alto Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00570','Alto Feliz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00604','Alvorada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00638','Amaral Ferrador');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00646','Ametista do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00661','André da Rocha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00703','Anta Gorda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00802','Antônio Prado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00851','Arambaré');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00877','Araricá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'00901','Aratiba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01008','Arroio do Meio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01057','Arroio do Sal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01107','Arroio dos Ratos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01206','Arroio do Tigre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01305','Arroio Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01404','Arvorezinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01503','Augusto Pestana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01552','Áurea');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01602','Bagé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01636','Balneário Pinhal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01651','Barão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01701','Barão de Cotegipe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01750','Barão do Triunfo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01800','Barracão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01859','Barra do Guarita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01875','Barra do Quaraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01909','Barra do Ribeiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01925','Barra do Rio Azul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'01958','Barra Funda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02006','Barros Cassal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02055','Benjamin Constant do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02105','Bento Gonçalves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02154','Boa Vista das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02204','Boa Vista do Buricá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02253','Boa Vista do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02303','Bom Jesus');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02352','Bom Princípio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02378','Bom Progresso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02402','Bom Retiro do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02451','Boqueirão do Leão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02501','Bossoroca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02600','Braga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02659','Brochier');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02709','Butiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02808','Caçapava do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'02907','Cacequi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03004','Cachoeira do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03103','Cachoeirinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03202','Cacique Doble');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03301','Caibaté');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03400','Caiçara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03509','Camaquã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03558','Camargo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03608','Cambará do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03673','Campestre da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03707','Campina das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03806','Campinas do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'03905','Campo Bom');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04002','Campo Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04101','Campos Borges');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04200','Candelária');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04309','Cândido Godói');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04358','Candiota');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04408','Canela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04507','Canguçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04606','Canoas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04630','Capão da Canoa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04663','Capão do Leão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04671','Capivari do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04689','Capela de Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04697','Capitão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04705','Carazinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04713','Caraá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04804','Carlos Barbosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04853','Carlos Gomes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04903','Casca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'04952','Caseiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05009','Catuípe');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05108','Caxias do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05116','Centenário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05124','Cerrito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05132','Cerro Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05157','Cerro Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05173','Cerro Grande do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05207','Cerro Largo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05306','Chapada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05355','Charqueadas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05371','Charrua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05405','Chiapeta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05439','Chuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05447','Chuvisca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05454','Cidreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05504','Ciríaco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05587','Colinas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05603','Colorado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05702','Condor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05801','Constantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05850','Coqueiros do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05871','Coronel Barros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05900','Coronel Bicaco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05959','Cotiporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'05975','Coxilha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06007','Crissiumal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06056','Cristal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06072','Cristal do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06106','Cruz Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06205','Cruzeiro do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06304','David Canabarro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06320','Derrubadas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06353','Dezesseis de Novembro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06379','Dilermando de Aguiar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06403','Dois Irmãos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06429','Dois Irmãos das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06452','Dois Lajeados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06502','Dom Feliciano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06551','Dom Pedro de Alcântara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06601','Dom Pedrito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06700','Dona Francisca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06734','Doutor Maurício Cardoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06759','Doutor Ricardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06767','Eldorado do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06809','Encantado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06908','Encruzilhada do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06924','Engenho Velho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06932','Entre-Ijuís');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06957','Entre Rios do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'06973','Erebango');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07005','Erechim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07054','Ernestina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07104','Herval');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07203','Erval Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07302','Erval Seco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07401','Esmeralda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07450','Esperança do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07500','Espumoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07559','Estação');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07609','Estância Velha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07708','Esteio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07807','Estrela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07815','Estrela Velha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07831','Eugênio de Castro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07864','Fagundes Varela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'07906','Farroupilha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08003','Faxinal do Soturno');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08052','Faxinalzinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08078','Fazenda Vilanova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08102','Feliz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08201','Flores da Cunha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08250','Floriano Peixoto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08300','Fontoura Xavier');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08409','Formigueiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08458','Fortaleza dos Valos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08508','Frederico Westphalen');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08607','Garibaldi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08656','Garruchos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08706','Gaurama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08805','General Câmara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08854','Gentil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'08904','Getúlio Vargas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09001','Giruá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09050','Glorinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09100','Gramado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09126','Gramado dos Loureiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09159','Gramado Xavier');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09209','Gravataí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09258','Guabiju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09308','Guaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09407','Guaporé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09506','Guarani das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09555','Harmonia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09571','Herveiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09605','Horizontina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09654','Hulha Negra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09704','Humaitá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09753','Ibarama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09803','Ibiaçá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09902','Ibiraiaras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'09951','Ibirapuitã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10009','Ibirubá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10108','Igrejinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10207','Ijuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10306','Ilópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10330','Imbé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10363','Imigrante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10405','Independência');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10413','Inhacorá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10439','Ipê');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10462','Ipiranga do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10504','Iraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10538','Itaara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10553','Itacurubi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10579','Itapuca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10603','Itaqui');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10702','Itatiba do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10751','Ivorá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10801','Ivoti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10850','Jaboticaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'10900','Jacutinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11007','Jaguarão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11106','Jaguari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11122','Jaquirana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11130','Jari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11155','Jóia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11205','Júlio de Castilhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11254','Lagoão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11270','Lagoa dos Três Cantos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11304','Lagoa Vermelha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11403','Lajeado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11429','Lajeado do Bugre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11502','Lavras do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11601','Liberato Salzano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11627','Lindolfo Collor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11643','Linha Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11700','Machadinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11718','Maçambara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11734','Mampituba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11759','Manoel Viana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11775','Maquiné');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11791','Maratá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11809','Marau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11908','Marcelino Ramos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'11981','Mariana Pimentel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12005','Mariano Moro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12054','Marques de Souza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12104','Mata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12138','Mato Castelhano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12153','Mato Leitão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12203','Maximiliano de Almeida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12252','Minas do Leão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12302','Miraguaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12351','Montauri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12377','Monte Alegre dos Campos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12385','Monte Belo do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12401','Montenegro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12427','Mormaço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12443','Morrinhos do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12450','Morro Redondo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12476','Morro Reuter');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12500','Mostardas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12609','Muçum');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12617','Muitos Capões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12625','Muliterno');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12658','Não-Me-Toque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12674','Nicolau Vergueiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12708','Nonoai');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12757','Nova Alvorada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12807','Nova Araçá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12906','Nova Bassano');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'12955','Nova Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13003','Nova Bréscia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13011','Nova Candelária');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13037','Nova Esperança do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13060','Nova Hartz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13086','Nova Pádua');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13102','Nova Palma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13201','Nova Petrópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13300','Nova Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13334','Nova Ramada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13359','Nova Roma do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13375','Nova Santa Rita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13391','Novo Cabrais');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13409','Novo Hamburgo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13425','Novo Machado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13441','Novo Tiradentes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13490','Novo Barreiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13508','Osório');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13607','Paim Filho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13656','Palmares do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13706','Palmeira das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13805','Palmitinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13904','Panambi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'13953','Pantano Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14001','Paraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14027','Paraíso do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14035','Pareci Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14050','Parobé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14068','Passa Sete');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14076','Passo do Sobrado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14100','Passo Fundo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14159','Paverama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14209','Pedro Osório');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14308','Pejuçara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14407','Pelotas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14423','Picada Café');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14456','Pinhal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14472','Pinhal Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14498','Pinheirinho do Vale');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14506','Pinheiro Machado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14555','Pirapó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14605','Piratini');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14704','Planalto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14753','Poço das Antas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14779','Pontão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14787','Ponte Preta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14803','Portão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'14902','Porto Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15008','Porto Lucena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15057','Porto Mauá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15073','Porto Vera Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15107','Porto Xavier');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15131','Pouso Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15149','Presidente Lucena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15156','Progresso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15172','Protásio Alves');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15206','Putinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15305','Quaraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15321','Quevedos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15354','Quinze de Novembro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15404','Redentora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15453','Relvado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15503','Restinga Seca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15552','Rio dos Índios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15602','Rio Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15701','Rio Pardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15750','Riozinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15800','Roca Sales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'15909','Rodeio Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16006','Rolante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16105','Ronda Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16204','Rondinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16303','Roque Gonzales');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16402','Rosário do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16428','Sagrada Família');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16436','Saldanha Marinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16451','Salto do Jacuí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16477','Salvador das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16501','Salvador do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16600','Sananduva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16709','Santa Bárbara do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16758','Santa Clara do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16808','Santa Cruz do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16907','Santa Maria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'16956','Santa Maria do Herval');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17004','Santana da Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17103','Santana do Livramento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17202','Santa Rosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17251','Santa Tereza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17301','Santa Vitória do Palmar');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17400','Santiago');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17509','Santo Ângelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17558','Santo Antônio do Palma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17608','Santo Antônio da Patrulha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17707','Santo Antônio das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17756','Santo Antônio do Planalto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17806','Santo Augusto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17905','Santo Cristo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'17954','Santo Expedito do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18002','São Borja');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18051','São Domingos do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18101','São Francisco de Assis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18200','São Francisco de Paula');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18309','São Gabriel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18408','São Jerônimo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18424','São João da Urtiga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18432','São João do Polêsine');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18440','São Jorge');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18457','São José das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18465','São José do Herval');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18481','São José do Hortêncio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18499','São José do Inhacorá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18507','São José do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18606','São José do Ouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18622','São José dos Ausentes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18705','São Leopoldo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18804','São Lourenço do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'18903','São Luiz Gonzaga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19000','São Marcos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19109','São Martinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19125','São Martinho da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19158','São Miguel das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19208','São Nicolau');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19307','São Paulo das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19356','São Pedro da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19372','São Pedro do Butiá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19406','São Pedro do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19505','São Sebastião do Caí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19604','São Sepé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19703','São Valentim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19711','São Valentim do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19737','São Valério do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19752','São Vendelino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19802','São Vicente do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'19901','Sapiranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20008','Sapucaia do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20107','Sarandi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20206','Seberi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20230','Sede Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20263','Segredo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20305','Selbach');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20321','Senador Salgado Filho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20354','Sentinela do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20404','Serafina Corrêa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20453','Sério');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20503','Sertão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20552','Sertão Santana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20578','Sete de Setembro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20602','Severiano de Almeida');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20651','Silveira Martins');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20677','Sinimbu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20701','Sobradinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20800','Soledade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20859','Tabaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'20909','Tapejara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21006','Tapera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21105','Tapes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21204','Taquara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21303','Taquari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21329','Taquaruçu do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21352','Tavares');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21402','Tenente Portela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21436','Terra de Areia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21451','Teutônia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21477','Tiradentes do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21493','Toropi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21501','Torres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21600','Tramandaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21626','Travesseiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21634','Três Arroios');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21667','Três Cachoeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21709','Três Coroas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21808','Três de Maio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21832','Três Forquilhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21857','Três Palmeiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21907','Três Passos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'21956','Trindade do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22004','Triunfo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22103','Tucunduva');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22152','Tunas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22186','Tupanci do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22202','Tupanciretã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22251','Tupandi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22301','Tuparendi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22327','Turuçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22343','Ubiretama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22350','União da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22376','Unistalda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22400','Uruguaiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22509','Vacaria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22525','Vale Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22533','Vale do Sol');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22541','Vale Real');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22558','Vanini');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22608','Venâncio Aires');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22707','Vera Cruz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22806','Veranópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22855','Vespasiano Correa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'22905','Viadutos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23002','Viamão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23101','Vicente Dutra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23200','Victor Graeff');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23309','Vila Flores');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23358','Vila Lângaro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23408','Vila Maria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23457','Vila Nova do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23507','Vista Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23606','Vista Alegre do Prata');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23705','Vista Gaúcha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23754','Vitória das Missões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RS'),'23804','Xangri-lá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'00203','Água Clara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'00252','Alcinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'00609','Amambaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'00708','Anastácio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'00807','Anaurilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'00856','Angélica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'00906','Antônio João');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'01003','Aparecida do Taboado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'01102','Aquidauana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'01243','Aral Moreira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'01508','Bandeirantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'01904','Bataguassu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02001','Bataiporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02100','Bela Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02159','Bodoquena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02209','Bonito');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02308','Brasilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02407','Caarapó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02605','Camapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02704','Campo Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02803','Caracol');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02902','Cassilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'02951','Chapadão do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03108','Corguinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03157','Coronel Sapucaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03207','Corumbá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03256','Costa Rica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03306','Coxim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03454','Deodápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03488','Dois Irmãos do Buriti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03504','Douradina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03702','Dourados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03751','Eldorado');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'03801','Fátima do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04007','Glória de Dourados');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04106','Guia Lopes da Laguna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04304','Iguatemi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04403','Inocência');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04502','Itaporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04601','Itaquiraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04700','Ivinhema');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04809','Japorã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'04908','Jaraguari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05004','Jardim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05103','Jateí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05152','Juti');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05202','Ladário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05251','Laguna Carapã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05400','Maracaju');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05608','Miranda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05681','Mundo Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05707','Naviraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'05806','Nioaque');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06002','Nova Alvorada do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06200','Nova Andradina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06259','Novo Horizonte do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06309','Paranaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06358','Paranhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06408','Pedro Gomes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06606','Ponta Porã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'06903','Porto Murtinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07109','Ribas do Rio Pardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07208','Rio Brilhante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07307','Rio Negro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07406','Rio Verde de Mato Grosso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07505','Rochedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07554','Santa Rita do Pardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07695','São Gabriel do Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07703','Sete Quedas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07802','Selvíria');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07901','Sidrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07935','Sonora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07950','Tacuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'07976','Taquarussu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'08008','Terenos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'08305','Três Lagoas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MS'),'08404','Vicentina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00102','Acorizal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00201','Água Boa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00250','Alta Floresta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00300','Alto Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00359','Alto Boa Vista');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00409','Alto Garças');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00508','Alto Paraguai');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00607','Alto Taquari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'00805','Apiacás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01001','Araguaiana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01209','Araguainha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01258','Araputanga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01308','Arenápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01407','Aripuanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01605','Barão de Melgaço');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01704','Barra do Bugres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01803','Barra do Garças');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'01902','Brasnorte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02504','Cáceres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02603','Campinápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02637','Campo Novo do Parecis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02678','Campo Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02686','Campos de Júlio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02694','Canabrava do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02702','Canarana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02793','Carlinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'02850','Castanheira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03007','Chapada dos Guimarães');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03056','Cláudia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03106','Cocalinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03205','Colíder');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03304','Comodoro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03353','Confresa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03379','Cotriguaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03403','Cuiabá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03452','Denise');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03502','Diamantino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03601','Dom Aquino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03700','Feliz Natal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03809','Figueirópolis D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03858','Gaúcha do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03908','General Carneiro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'03957','Glória D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'04104','Guarantã do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'04203','Guiratinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'04500','Indiavaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'04559','Itaúba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'04609','Itiquira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'04807','Jaciara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'04906','Jangada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05002','Jauru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05101','Juara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05150','Juína');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05176','Juruena');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05200','Juscimeira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05234','Lambari D''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05259','Lucas do Rio Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05309','Luciára');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05507','Vila Bela da Santíssima Trinda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05580','Marcelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05606','Matupá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05622','Mirassol d''Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'05903','Nobres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06000','Nortelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06109','Nossa Senhora do Livramento');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06158','Nova Bandeirantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06182','Nova Lacerda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06208','Nova Brasilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06216','Nova Canaã do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06224','Nova Mutum');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06232','Nova Olímpia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06240','Nova Ubiratã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06257','Nova Xavantina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06265','Novo Mundo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06273','Novo Horizonte do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06281','Novo São Joaquim');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06299','Paranaíta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06307','Paranatinga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06372','Pedra Preta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06422','Peixoto de Azevedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06455','Planalto da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06505','Poconé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06653','Pontal do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06703','Ponte Branca');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06752','Pontes e Lacerda');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06778','Porto Alegre do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06802','Porto dos Gaúchos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06828','Porto Esperidião');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'06851','Porto Estrela');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07008','Poxoréo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07040','Primavera do Leste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07065','Querência');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07107','São José dos Quatro Marcos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07156','Reserva do Cabaçal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07180','Ribeirão Cascalheira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07198','Ribeirãozinho');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07206','Rio Branco');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07248','Santa Carmem');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07263','Santo Afonso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07297','São José do Povo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07305','São José do Rio Claro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07354','São José do Xingu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07404','São Pedro da Cipa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07602','Rondonópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07701','Rosário Oeste');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07750','Salto do Céu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07776','Santa Terezinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07800','Santo Antônio do Leverger');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07859','São Félix do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07875','Sapezal');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07909','Sinop');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07925','Sorriso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07941','Tabaporã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'07958','Tangará da Serra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08006','Tapurah');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08055','Terra Nova do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08105','Tesouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08204','Torixoréu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08303','União do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08402','Várzea Grande');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08501','Vera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08600','Vila Rica');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08808','Nova Guarita');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08857','Nova Marilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08907','Nova Maringá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'MT'),'08956','Nova Monte verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00050','Abadia de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00100','Abadiânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00134','Acreúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00159','Adelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00175','Água Fria de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00209','Água Limpa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00258','Águas Lindas de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00308','Alexânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00506','Aloândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00555','Alto Horizonte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00605','Alto Paraíso de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00803','Alvorada do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00829','Amaralina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00852','Americano do Brasil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'00902','Amorinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01108','Anápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01207','Anhanguera');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01306','Anicuns');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01405','Aparecida de Goiânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01454','Aparecida do Rio Doce');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01504','Aporé');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01603','Araçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01702','Aragarças');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'01801','Aragoiânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'02155','Araguapaz');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'02353','Arenópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'02502','Aruanã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'02601','Aurilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'02809','Avelinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03104','Baliza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03203','Barro Alto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03302','Bela Vista de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03401','Bom Jardim de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03500','Bom Jesus de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03559','Bonfinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03575','Bonópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03609','Brazabrantes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03807','Britânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03906','Buriti Alegre');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03939','Buriti de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'03962','Buritinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04003','Cabeceiras');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04102','Cachoeira Alta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04201','Cachoeira de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04250','Cachoeira Dourada');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04300','Caçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04409','Caiapônia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04508','Caldas Novas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04557','Caldazinha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04607','Campestre de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04656','Campinaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04706','Campinorte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04805','Campo Alegre de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04904','Campos Belos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'04953','Campos Verdes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05000','Carmo do Rio Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05059','Castelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05109','Catalão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05208','Caturaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05307','Cavalcante');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05406','Ceres');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05455','Cezarina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05471','Chapadão do Céu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05497','Cidade Ocidental');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05513','Cocalzinho de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05521','Colinas do Sul');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05703','Córrego do Ouro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05802','Corumbá de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'05901','Corumbaíba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06206','Cristalina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06305','Cristianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06404','Crixás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06503','Cromínia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06602','Cumari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06701','Damianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06800','Damolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'06909','Davinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07105','Diorama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07253','Doverlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07352','Edealina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07402','Edéia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07501','Estrela do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07535','Faina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07600','Fazenda Nova');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07808','Firminópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'07907','Flores de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08004','Formosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08103','Formoso');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08301','Divinópolis de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08400','Goianápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08509','Goiandira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08608','Goianésia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08707','Goiânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08806','Goianira');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'08905','Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09101','Goiatuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09150','Gouvelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09200','Guapó');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09291','Guaraíta');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09408','Guarani de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09457','Guarinos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09606','Heitoraí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09705','Hidrolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09804','Hidrolina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09903','Iaciara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09937','Inaciolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'09952','Indiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10000','Inhumas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10109','Ipameri');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10208','Iporá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10307','Israelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10406','Itaberaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10562','Itaguari');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10604','Itaguaru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10802','Itajá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'10901','Itapaci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11008','Itapirapuã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11206','Itapuranga');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11305','Itarumã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11404','Itauçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11503','Itumbiara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11602','Ivolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11701','Jandaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11800','Jaraguá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'11909','Jataí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12006','Jaupaci');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12055','Jesúpolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12105','Joviânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12204','Jussara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12303','Leopoldo de Bulhões');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12501','Luziânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12600','Mairipotaba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12709','Mambaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12808','Mara Rosa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12907','Marzagão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'12956','Matrinchã');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13004','Maurilândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13053','Mimoso de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13087','Minaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13103','Mineiros');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13400','Moiporá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13509','Monte Alegre de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13707','Montes Claros de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13756','Montividiu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13772','Montividiu do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13806','Morrinhos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13855','Morro Agudo de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'13905','Mossâmedes');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14002','Mozarlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14051','Mundo Novo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14101','Mutunópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14408','Nazário');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14507','Nerópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14606','Niquelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14705','Nova América');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14804','Nova Aurora');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14838','Nova Crixás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14861','Nova Glória');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14879','Nova Iguaçu de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'14903','Nova Roma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15009','Nova Veneza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15207','Novo Brasil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15231','Novo Gama');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15256','Novo Planalto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15306','Orizona');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15405','Ouro Verde de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15504','Ouvidor');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15603','Padre Bernardo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15652','Palestina de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15702','Palmeiras de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15801','Palmelo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'15900','Palminópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'16007','Panamá');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'16304','Paranaiguara');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'16403','Paraúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'16452','Perolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'16809','Petrolina de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'16908','Pilar de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'17104','Piracanjuba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'17203','Piranhas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'17302','Pirenópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'17401','Pires do Rio');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'17609','Planaltina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'17708','Pontalina');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18003','Porangatu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18052','Porteirão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18102','Portelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18300','Posse');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18391','Professor Jamil');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18508','Quirinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18607','Rialma');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18706','Rianápolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18789','Rio Quente');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18805','Rio Verde');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'18904','Rubiataba');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19001','Sanclerlândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19100','Santa Bárbara de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19209','Santa Cruz de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19258','Santa Fé de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19308','Santa Helena de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19357','Santa Isabel');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19407','Santa Rita do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19456','Santa Rita do Novo Destino');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19506','Santa Rosa de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19605','Santa Tereza de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19704','Santa Terezinha de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19712','Santo Antônio da Barra');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19738','Santo Antônio de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19753','Santo Antônio do Descoberto');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19803','São Domingos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'19902','São Francisco de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20009','São João d''Aliança');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20058','São João da Paraúna');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20108','São Luís de Montes Belos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20157','São Luíz do Norte');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20207','São Miguel do Araguaia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20264','São Miguel do Passa Quatro');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20280','São Patrício');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20405','São Simão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20454','Senador Canedo');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20504','Serranópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20603','Silvânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20686','Simolândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'20702','Sítio d''Abadia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21007','Taquaral de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21080','Teresina de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21197','Terezópolis de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21304','Três Ranchos');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21403','Trindade');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21452','Trombas');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21502','Turvânia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21551','Turvelândia');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21577','Uirapuru');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21601','Uruaçu');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21700','Uruana');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21809','Urutaí');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21858','Valparaíso de Goiás');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'21908','Varjão');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'22005','Vianópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'22054','Vicentinópolis');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'22203','Vila Boa');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'GO'),'22302','Vila Propício');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'DF'),'00108','Brasília');

INSERT INTO extintorinspecaoitem VALUES(1, 'Lacre');
INSERT INTO extintorinspecaoitem VALUES(2, 'Selo');
INSERT INTO extintorinspecaoitem VALUES(3, 'Trava');
INSERT INTO extintorinspecaoitem VALUES(4, 'Manômetro');
INSERT INTO extintorinspecaoitem VALUES(5, 'Sinalização Vertical');
INSERT INTO extintorinspecaoitem VALUES(6, 'Sinalização Horizontal');
INSERT INTO extintorinspecaoitem VALUES(7, 'Localização');
INSERT INTO extintorinspecaoitem VALUES(8, 'Alça');
INSERT INTO extintorinspecaoitem VALUES(9, 'Gatilho');
INSERT INTO extintorinspecaoitem VALUES(10, 'Mangueira');

INSERT INTO extintormanutencaoservico VALUES(1, 'Recarga');
INSERT INTO extintormanutencaoservico VALUES(2, 'Pintura');
INSERT INTO extintormanutencaoservico VALUES(3, 'Teste Hidrostático');
INSERT INTO extintormanutencaoservico VALUES(4, 'Manômetro');
INSERT INTO extintormanutencaoservico VALUES(5, 'Substituição de Gatilho');
INSERT INTO extintormanutencaoservico VALUES(6, 'Válvula de Segurança');
INSERT INTO extintormanutencaoservico VALUES(7, 'Substituição de Difusor');
INSERT INTO extintormanutencaoservico VALUES(8, 'Válvula Completa');
INSERT INTO extintormanutencaoservico VALUES(9, 'Mangote');
INSERT INTO extintormanutencaoservico VALUES(10, 'Válvula Cilindro Adicional');

insert into exame (id, nome, periodicidade, periodico, empresa_id) values (1, 'ASO', 0, false, 1);
alter sequence exame_sequence restart with 2;

insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (1,'f','texto1','Campo de Texto 1', 1,'texto');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (2,'f','texto2','Campo de Texto 2', 1,'texto');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (3,'f','texto3','Campo de Texto 3', 1,'texto');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (4,'f','data1','Campo de Data 1', 1,'data');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (5,'f','data2','Campo de Data 2', 1,'data');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (6,'f','data3','Campo de Data 3', 1,'data');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (7,'f','valor1','Campo de Valor 1', 1,'valor');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (8,'f','valor2','Campo de Valor 2', 1,'valor');
insert into configuracaocampoextra (id,ativo,nome,descricao,ordem,tipo) values (9,'f','numero1','Campo de Numero', 1,'numero');

alter sequence configuracaocampoextra_sequence restart with 10;

insert into parametrosdosistema (id, appurl, appcontext, appversao, servidorremprot, emailport, uppercase, enviaremail, perfilpadrao_id, acversaowebservicecompativel, exame_id, diasLembretePeriodoExperiencia, campoextracolaborador)
values (1, 'http://localhost:8080/fortesrh', '/fortesrh', '1.1.31.20', '', '25', false,false, 2, '1.0.1.39', 1, 3, false);

alter sequence parametrosdosistema_sequence restart with 2;
