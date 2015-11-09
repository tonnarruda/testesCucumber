INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (646,'ROLE_TAXA_DEMISSAO', 'Taxa de Demissão', '/indicador/indicadorTurnOver/prepareTaxaDeDemissao.action', 11, true, 377);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) select perfil_id, 646 from perfil_papel where papeis_id = 398;--.go
alter sequence papel_sequence restart with 647;--.go