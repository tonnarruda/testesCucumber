alter table mensagem add column tipo character varying(1); --.go
alter table mensagem add column colaborador_id bigint;--.go
ALTER TABLE ONLY mensagem ADD CONSTRAINT mensagem_colaborador_fk FOREIGN KEY (colaborador_id) REFERENCES colaborador(id); --.go