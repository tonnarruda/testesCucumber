update papel set papelmae_id=360, ordem=5 where id=73;--.go
update papel set papelmae_id=360, ordem=6 where id=69;--.go

delete from papel where id=527;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=526 WHERE atualizaPapeisIdsAPartirDe is null;--.go

update papel set nome='Painel de Indicadores', url='/indicador/duracaoPreenchimentoVaga/painel.action' where id=461;--.go