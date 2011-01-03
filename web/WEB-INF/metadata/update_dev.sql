update parametrosdosistema set appversao = '1.1.36.26';--.go

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
