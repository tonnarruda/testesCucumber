
update papel set ordem = 3 where id=396;--.go

update papel set ordem = ordem + 1 where papelmae_id = 364 and ordem >= 5;--.go
update papel set ordem = ordem + 1 where papelmae_id = 364 and ordem >7;--.go
update papel set ordem = ordem + 1 where papelmae_id = 364 and ordem >9;--.go

update papel set ordem = 5 where id=35;--.go
update papel set ordem = 8 where id=407;--.go
update papel set ordem = 10 where id=511;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (561, 'ROLE_REL_COLAB_GRUPOOCUPACIONAL', 'Colaboradores por Grupo Ocupacional', '/cargosalario/cargo/prepareRelatorioColaboradorGrupoOcupacional.action', 4, true, 364);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=561 WHERE atualizaPapeisIdsAPartirDe is null;--.go
alter sequence papel_sequence restart with 562;--.go


