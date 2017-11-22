
ALTER TABLE ONLY medicocoordenador_estabelecimento ADD CONSTRAINT medicocoordenador_estabelecimento_medicocoordenador_fk FOREIGN KEY (medicocoordenador_id) REFERENCES medicocoordenador(id);--.go
ALTER TABLE ONLY medicocoordenador_estabelecimento ADD CONSTRAINT medicocoordenador_estabelecimento_estabelecimento_fk FOREIGN KEY (estabelecimentos_id) REFERENCES estabelecimento(id);--.go

ALTER TABLE engenheiroresponsavel ADD COLUMN estabelecimentoResponsavel CHARACTER VARYING(6) DEFAULT 'TODOS';--.go

ALTER TABLE ONLY engenheiroresponsavel_estabelecimento ADD CONSTRAINT engenheiroresponsavel_estabelecimento_engenheiroresponsavel_fk FOREIGN KEY (engenheiroresponsavel_id) REFERENCES engenheiroresponsavel(id);--.go
ALTER TABLE ONLY engenheiroresponsavel_estabelecimento ADD CONSTRAINT engenheiroresponsavel_estabelecimento_estabelecimento_fk FOREIGN KEY (estabelecimentos_id) REFERENCES estabelecimento(id);--.go

insert into migrations values('20171030132939');--.go