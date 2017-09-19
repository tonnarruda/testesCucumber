
alter table empresa add column aderiuaoesocial boolean default false;--.go
alter table empresa add column dddCelularAndUFHabilitacaoAtualizados boolean default false;--.go


update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'pis,|,pis','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'pis,|,pis','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',pis';

update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'vinculo,|,vinculo','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',vinculo';

update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'vinculo,|,vinculo','') from  parametrosdosistema);
update parametrosdosistema set camposcolaboradorobrigatorio = camposcolaboradorobrigatorio || ',vinculo';


alter table colaborador add column dddCelular character varying(5); --.go
alter table candidato add column dddCelular character varying(5); --.go

alter table colaborador add column ufHab_id bigint; --.go
alter table candidato add column ufHab_id bigint; --.go

--Bairro
ALTER TABLE bairro ALTER COLUMN nome TYPE character varying(60) USING SUBSTR(nome, 1, 60);--.go;

--Colaborador
UPDATE pg_attribute SET atttypmod = 70+4 WHERE attrelid = 'colaborador'::regclass AND attname = 'nome'; --.go

ALTER TABLE colaborador ALTER COLUMN pai TYPE character varying(70);--.go
ALTER TABLE colaborador ALTER COLUMN mae TYPE character varying(70);--.go
ALTER TABLE colaborador ALTER COLUMN conjuge TYPE character varying(70);--.go
ALTER TABLE colaborador ALTER COLUMN ctpsnumero TYPE character varying(11);--.go
ALTER TABLE colaborador ALTER COLUMN rgorgaoemissor TYPE character varying(20);--.go
ALTER TABLE colaborador ALTER COLUMN numerohab TYPE character varying(12);--.go
ALTER TABLE colaborador ALTER COLUMN logradouro TYPE character varying(80);--.go
ALTER TABLE colaborador ALTER COLUMN complemento TYPE character varying(30);--.go
ALTER TABLE colaborador ALTER COLUMN bairro TYPE character varying(60) USING SUBSTR(bairro, 1, 60);--.go;


--Candidato
ALTER TABLE candidato ALTER COLUMN nome TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN pai TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN mae TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN conjuge TYPE character varying(70);--.go
ALTER TABLE candidato ALTER COLUMN ctpsnumero TYPE character varying(11);--.go
ALTER TABLE candidato ALTER COLUMN rgorgaoemissor TYPE character varying(20);--.go
ALTER TABLE candidato ALTER COLUMN numerohab TYPE character varying(12);--.go
ALTER TABLE candidato ALTER COLUMN logradouro TYPE character varying(80);--.go
ALTER TABLE candidato ALTER COLUMN complemento TYPE character varying(30);--.go
ALTER TABLE candidato ALTER COLUMN bairro TYPE character varying(60);--.go
