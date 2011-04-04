update parametrosdosistema set appversao = '1.1.43.34';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (504, 'ROLE_INFO_PAINEL_IND', 'Painel', '/cargosalario/historicoColaborador/painelIndicadores.action', 3, true, 462);--.go
alter sequence papel_sequence restart with 505;--.go