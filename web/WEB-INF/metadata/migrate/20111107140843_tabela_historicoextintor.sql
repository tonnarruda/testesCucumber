CREATE SEQUENCE historicoextintor_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE historicoextintor (
	id bigint NOT NULL DEFAULT nextval('historicoextintor_sequence'),
	extintor_id bigint NOT NULL,
	estabelecimento_id bigint NOT NULL,
	localizacao varchar(50),
	data Date NOT NULL
);--.go

ALTER TABLE historicoextintor ADD CONSTRAINT historicoextintor_pkey PRIMARY KEY (id);--.go
ALTER TABLE historicoextintor ADD CONSTRAINT historicoextintor_extintor_fk FOREIGN KEY (extintor_id) REFERENCES extintor(id);--.go
ALTER TABLE historicoextintor ADD CONSTRAINT historicoextintor_estabelecimento_fk FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id);--.go

INSERT INTO historicoextintor (extintor_id, estabelecimento_id, localizacao, data) 
SELECT 
e.id, 
e.estabelecimento_id, 
e.localizacao, 
LEAST ( CURRENT_DATE, 
       (SELECT MIN(em.saida) FROM extintormanutencao em WHERE em.extintor_id = e.id), 
       (SELECT MIN(ei.data) FROM extintorinspecao ei WHERE ei.extintor_id = e.id)
      ) 
FROM extintor e;--.go

ALTER TABLE extintor DROP COLUMN estabelecimento_id;--.go 
ALTER TABLE extintor DROP COLUMN localizacao;--.go