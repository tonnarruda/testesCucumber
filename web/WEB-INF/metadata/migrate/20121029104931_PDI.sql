CREATE TABLE conhecimento_curso(
  conhecimentos_id bigint NOT NULL,
  cursos_id bigint NOT NULL
);--.go
ALTER TABLE conhecimento_curso ADD CONSTRAINT conhecimento_curso_conhecimento_fk FOREIGN KEY (conhecimentos_id) REFERENCES conhecimento (id); --.go
ALTER TABLE conhecimento_curso ADD CONSTRAINT conhecimento_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso (id); --.go


CREATE TABLE habilidade_curso(
  habilidades_id bigint NOT NULL,
  cursos_id bigint NOT NULL
);--.go
ALTER TABLE habilidade_curso ADD CONSTRAINT habilidade_curso_habilidade_fk FOREIGN KEY (habilidades_id) REFERENCES habilidade (id); --.go
ALTER TABLE habilidade_curso ADD CONSTRAINT habilidade_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso (id); --.go


CREATE TABLE atitude_curso(
  atitudes_id bigint NOT NULL,
  cursos_id bigint NOT NULL
);--.go
ALTER TABLE atitude_curso ADD CONSTRAINT atitude_curso_atitude_fk FOREIGN KEY (atitudes_id) REFERENCES atitude (id); --.go
ALTER TABLE atitude_curso ADD CONSTRAINT atitude_curso_curso_fk FOREIGN KEY (cursos_id) REFERENCES curso (id); --.go


alter table avaliacao add column avaliarCompetenciasCargo boolean not null default false;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (563,'ROLE_MOV_PLANO_DESENVOLVIMENTO_INDIVIDUAL', 'Plano de Desenvolvimento Individual (PDI)', '/desenvolvimento/turma/preparePdi.action', 5, true, NULL, 367);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=563 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 563);--.go
alter sequence papel_sequence restart with 564;--.go