update papel set ordem = ordem + 1 where papelmae_id = 37 and ordem >= 8 and ordem < 100 and id <> 451;--.go
update papel set ordem = ordem + 1 where papelmae_id = 37 and ordem >= 10 and ordem < 15 and id <> 529;--.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (618, 'ROLE_UTI_EXPORTAR_AC', 'Exportar dados para o AC Pessoal', '/exportacao/prepareExportarAC.action', 17, true, 37);--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 618);--.go
