alter table indicehistorico add column reajusteindice_id bigint;--.go

ALTER TABLE ONLY indicehistorico ADD CONSTRAINT indicehistorico_reajusteindice_fk FOREIGN KEY (reajusteindice_id) REFERENCES reajusteindice(id);--.go