CREATE TABLE solicitacaoavaliacao (
  id bigint NOT NULL,
  solicitacao_id bigint NOT NULL,
  avaliacao_id bigint NOT NULL,
  respondermoduloexterno boolean NOT NULL default false 
);--.go

ALTER TABLE solicitacaoavaliacao ADD CONSTRAINT solicitacaoavaliacao_pkey PRIMARY KEY(id);--.go
ALTER TABLE solicitacaoavaliacao ADD CONSTRAINT solicitacaoavaliacao_solicitacao_fk FOREIGN KEY (solicitacao_id) REFERENCES solicitacao (id); --.go
ALTER TABLE solicitacaoavaliacao ADD CONSTRAINT solicitacaoavaliacao_avaliacao_fk FOREIGN KEY (avaliacao_id) REFERENCES avaliacao (id); --.go
CREATE SEQUENCE solicitacaoavaliacao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

insert into solicitacaoavaliacao 
select nextval('solicitacaoavaliacao_sequence'), s.id, s.avaliacao_id, coalesce(a.responderavaliacaomoduloexterno, false) 
from solicitacao s 
left join anuncio a on s.id = a.solicitacao_id  
where s.avaliacao_id is not null; --.go

alter table solicitacao drop column avaliacao_id; --.go
alter table anuncio drop column responderavaliacaomoduloexterno; --.go