
update papel set ordem = 4 where id = 504;--.go

alter table papel alter column nome TYPE character varying(150);--.go

alter table papel alter column codigo TYPE character varying(80);--.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (672, 'ROLE_MOV_GESTOR_VISUALIZAR_PROPRIA_OCORRENCIA_PROVIDENCIA', 
'Permitir que o gestor visualize suas próprias ocorrências e providências nas movimentações e relatórios.', '#', 5, false, 373);--.go

alter sequence papel_sequence restart with 673;--.go