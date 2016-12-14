
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (692,'ROLE_MOV_LNT', 'Levantamento de Necessidade de Treinamento (LNT)', '/desenvolvimento/lnt/list.action', 7, true, 367);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (693,'ROLE_MOV_LNT_INSERIR', 'Inserir', '#', 1, false, 692);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (694,'ROLE_MOV_LNT_EDITAR', 'Editar', '#', 2, false, 692);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (695,'ROLE_MOV_LNT_EXCLUIR', 'Excluir', '#', 3, false, 692);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (696,'ROLE_MOV_LNT_ADICIONAR_COLABORADORES', 'Adicionar Colaboradores', '#', 4, false, 692);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (697,'ROLE_MOV_LNT_ANALISAR', 'Analisar', '#', 5, false, 692);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (698,'ROLE_MOV_LNT_FINALIZAR', 'Finalizar', '#', 6, false, 692);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (699,'ROLE_MOV_LNT_IMPRIMIR', 'Imprimir', '#', 7, false, 692);--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) 
VALUES (700,'ROLE_MOV_LNT_GERAR_CURSOS_E_TURMAS', 'Gerar Cursos/Turmas', '#', 8, false, 692);--.go

CREATE TABLE lnt (
	id bigint NOT NULL,
	descricao character varying(100),
	dataInicio date NOT NULL,
	dataFim date NOT NULL,
	dataFinalizada date
);--.go

ALTER TABLE lnt ADD CONSTRAINT lnt_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE lnt_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE lnt_empresa (
    lnt_id bigint NOT NULL,
    empresas_id bigint NOT NULL
);--.go

ALTER TABLE ONLY lnt_empresa ADD CONSTRAINT lnt_empresa_lnt_fk FOREIGN KEY (lnt_id) REFERENCES lnt(id);--.go
ALTER TABLE ONLY lnt_empresa ADD CONSTRAINT lnt_empresa_empresa_fk FOREIGN KEY (empresas_id) REFERENCES empresa(id);--.go

CREATE TABLE lnt_areaOrganizacional (
    lnt_id bigint NOT NULL,
    areasOrganizacionais_id bigint NOT NULL
);--.go

ALTER TABLE lnt_areaOrganizacional ADD CONSTRAINT lnt_areaOrganizacional_lnt_fk FOREIGN KEY (lnt_id) REFERENCES LNT(id);--.go
ALTER TABLE lnt_areaOrganizacional ADD CONSTRAINT lnt_areaOrganizacional_areasOrganizacionais_fk FOREIGN KEY (areasOrganizacionais_id) REFERENCES areaOrganizacional(id);--.go

CREATE TABLE cursoLnt (
	id bigint NOT NULL,
	nomeNovoCurso character varying(150),
	conteudoprogramatico text,
	justificativa text,
	custo double precision,
	cargahoraria integer,
	lnt_id bigint NOT NULL,
	curso_id bigint
);--.go

ALTER TABLE cursoLnt ADD CONSTRAINT cursoLnt_pkey PRIMARY KEY(id);--.go
ALTER TABLE cursoLnt ADD CONSTRAINT cursoLnt_lnt_fk FOREIGN KEY (lnt_id) REFERENCES lnt(id);--.go
ALTER TABLE cursoLnt ADD CONSTRAINT cursoLnt_curso_fk FOREIGN KEY (curso_id) REFERENCES curso(id);--.go
CREATE SEQUENCE cursoLnt_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE participanteCursoLnt (
	id bigint NOT NULL,
	colaborador_id bigint NOT NULL,
	cursolnt_id bigint NOT NULL,
	areaorganizacional_id bigint NOT NULL
);--.go

ALTER TABLE participanteCursoLnt ADD CONSTRAINT participanteCursoLnt_pkey PRIMARY KEY(id);--.go
ALTER TABLE participanteCursoLnt ADD CONSTRAINT participanteCursoLnt_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id);--.go
ALTER TABLE participanteCursoLnt ADD CONSTRAINT participanteCursoLnt_cursolnt_fk FOREIGN KEY (cursolnt_id) REFERENCES cursolnt(id);--.go
ALTER TABLE participanteCursoLnt ADD CONSTRAINT participanteCursoLnt_area_fk FOREIGN KEY (areaorganizacional_id) REFERENCES areaorganizacional(id);--.go
CREATE SEQUENCE participanteCursoLnt_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE OR REPLACE FUNCTION ancestrais_areas_ids(IN area_id bigint) RETURNS TABLE(areas_id bigint) AS
$$
	DECLARE 
	BEGIN 
	    RETURN QUERY  
	    WITH RECURSIVE areaorganizacional_recursiva AS ( 
			SELECT id as areaid, areamae_id
			FROM areaorganizacional WHERE id = area_id 
			UNION ALL 
			SELECT ao.id as areaid, ao.areamae_id 
			FROM areaorganizacional ao 
			INNER JOIN areaorganizacional_recursiva ao_r ON ao.id = ao_r.areamae_id 
	    )
	    SELECT distinct areaid FROM areaorganizacional_recursiva ORDER BY areaid; 
	END; 
$$ LANGUAGE plpgsql; --.go


CREATE OR REPLACE FUNCTION descendentes_areas_ids(IN area_id bigint) RETURNS TABLE(areas_id bigint) AS
$$
	DECLARE 
	BEGIN 
	    RETURN QUERY  
	    WITH RECURSIVE areaorganizacional_recursiva AS ( 
		SELECT id as areaid, areamae_id 
		FROM areaorganizacional WHERE id = area_id 
		UNION ALL 
		SELECT ao.id as areaid, ao.areamae_id 
		FROM areaorganizacional ao 
		INNER JOIN areaorganizacional_recursiva ao_r ON ao.areamae_id = ao_r.areaid
	    )
	    SELECT distinct areaid FROM areaorganizacional_recursiva ORDER BY areaid; 
	END; 
$$ LANGUAGE plpgsql; --.go

alter table colaboradorturma add column cursolnt_id bigint;--.go
ALTER TABLE ONLY colaboradorturma ADD CONSTRAINT colaboradorturma_cursolnt_fk FOREIGN KEY (cursolnt_id) REFERENCES cursolnt(id); --.go
ALTER TABLE participanteCursoLnt ADD CONSTRAINT unique_colaboradorId_cursoLntId_participanteCursoLnt UNIQUE(colaborador_id,cursolnt_id); --.go