alter table faixasalarialhistorico add column reajustefaixasalarial_id bigint;--.go

ALTER TABLE ONLY faixasalarialhistorico ADD CONSTRAINT faixasalarialhistorico_reajustefaixasalarial_fk FOREIGN KEY (reajustefaixasalarial_id) REFERENCES reajustefaixasalarial(id);--.go