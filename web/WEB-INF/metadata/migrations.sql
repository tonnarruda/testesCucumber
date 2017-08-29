
alter table empresa add column aderiuaoesocial boolean default false;--.go

update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'pis,|,pis','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'pis,|,pis','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',pis';

update parametrosdosistema set camposColaboradorVisivel = (select regexp_replace(camposColaboradorVisivel, 'vinculo,|,vinculo','') from  parametrosdosistema);
update parametrosdosistema set camposColaboradorVisivel = camposColaboradorVisivel || ',vinculo';

update parametrosdosistema set camposcolaboradorobrigatorio = (select regexp_replace(camposcolaboradorobrigatorio, 'vinculo,|,vinculo','') from  parametrosdosistema);
update parametrosdosistema set camposcolaboradorobrigatorio = camposcolaboradorobrigatorio || ',vinculo';
