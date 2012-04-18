alter table turma_avaliacaoturma add column id bigint;--.go
CREATE SEQUENCE turma_avaliacaoturma_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go
update turma_avaliacaoturma set id = nextval('turma_avaliacaoturma_sequence');--.go
alter table turma_avaliacaoturma alter column id set NOT NULL;--.go