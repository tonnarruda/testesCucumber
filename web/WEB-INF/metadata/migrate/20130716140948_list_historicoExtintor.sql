ALTER TABLE historicoextintor ALTER COLUMN data TYPE timestamp;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (579, 'ROLE_CAD_LIST_HISTORICOEXTINTOR', 'Troca de Localização', '/sesmt/historicoExtintor/list.action', 3, true, 457);--.go
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 579 from perfil_papel  where papeis_id = 457;--.go
alter sequence papel_sequence restart with 580;--.go