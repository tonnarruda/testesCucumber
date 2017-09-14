
alter table empresa add column aderiuaoesocial boolean default false;--.go

update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'pis,|,pis','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'pis,|,pis','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',pis';

update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'vinculo,|,vinculo','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',vinculo';

update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'vinculo,|,vinculo','') from  parametrosdosistema);
update parametrosdosistema set camposcolaboradorobrigatorio = camposcolaboradorobrigatorio || ',vinculo';


alter table colaborador add column dddCelular character varying(5); --.go
alter table candidato add column dddCelular character varying(5); --.go

alter table colaborador add column habuf_id bigint; --.go
alter table candidato add column habuf_id bigint; --.go

update colaborador set dddCelular = ddd; --.go 
update candidato set dddCelular = ddd; --.go