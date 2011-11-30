alter table cat add column ambiente_id bigint;--.go
alter table cat add column naturezaLesao_id bigint;--.go
alter table cat add column horario character varying(5);--.go
alter table cat add column parteAtingida character varying(100);--.go
alter table cat add column foiTreinadoParaFuncao boolean default false;--.go
alter table cat add column usavaEPI boolean default false;--.go
alter table cat add column emitiuCAT boolean default false;--.go
alter table cat add column qtdDiasAfastado integer;--.go
alter table cat add column conclusao text;--.go
alter table cat add column tipoAcidente integer;--.go

ALTER TABLE cat ADD CONSTRAINT cat_ambiente_fk FOREIGN KEY (ambiente_id) REFERENCES ambiente(id);--.go
ALTER TABLE cat ADD CONSTRAINT cat_naturezaLesao_fk FOREIGN KEY (naturezaLesao_id) REFERENCES naturezaLesao(id);--.go