delete from riscofuncao where historicofuncao_id in (select id from historicofuncao where funcao_id is null);--.go
delete from historicofuncao_curso where historicofuncao_id in (select id from historicofuncao where funcao_id is null);--.go
delete from historicofuncao_epi where historicofuncao_id in (select id from historicofuncao where funcao_id is null);--.go
delete from historicofuncao_exame where historicofuncao_id in (select id from historicofuncao where funcao_id is null);--.go
delete  from historicofuncao where funcao_id is null;--.go

alter table historicoFuncao alter column id set not null;--.go