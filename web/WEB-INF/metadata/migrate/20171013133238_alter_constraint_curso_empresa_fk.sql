delete from atitude_curso where cursos_id in (select id from curso where empresa_id not in (select id from empresa) );--.go
delete from conhecimento_curso where cursos_id in (select id from curso where empresa_id not in (select id from empresa) );--.go
delete from habilidade_curso where cursos_id in (select id from curso where empresa_id not in (select id from empresa) );--.go
delete from certificacao_curso where cursos_id in (select id from curso where empresa_id not in (select id from empresa) );--.go
delete from curso_avaliacaocurso where cursos_id in (select id from curso where empresa_id not in (select id from empresa) );--.go
delete from curso_empresa where cursos_id in (select id from curso where empresa_id not in (select id from empresa) );--.go

delete from colaboradorresposta where colaboradorquestionario_id in (select id from colaboradorquestionario where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) )));--.go
delete from colaboradorquestionario where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) ));--.go
delete from colaboradorturma where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) ));--.go
delete from diaturma where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) ));--.go
delete from turma_avaliacaoturma where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) ));--.go
delete from turmatipodespesa where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) ));--.go
delete from turma_documentoanexo where turma_id in (select id from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) ));--.go
delete from turma where curso_id in (select id from curso where empresa_id not in (select id from empresa) );--.go

delete from curso where empresa_id not in (select id from empresa);--.go

ALTER TABLE ONLY curso DROP CONSTRAINT IF EXISTS curso_empresa;--.go
ALTER TABLE ONLY curso DROP CONSTRAINT IF EXISTS curso_empresa_fk;--.go
ALTER TABLE ONLY curso ADD CONSTRAINT curso_empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa(id);--.go