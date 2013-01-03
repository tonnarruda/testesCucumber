update papel set ordem = ordem + 1 where papelmae_id = 364 and ordem > 4;--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (564,'ROLE_RELATORIO_HISTORICO_FAIXA_SALARIAL', 'Históricos das Faixas Salariais', '/cargosalario/faixaSalarialHistorico/prepareRelatorioHistoricoFaixaSalarial.action', 5, true, NULL, 364);--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=564 WHERE atualizaPapeisIdsAPartirDe is null;--.go
insert into perfil_papel(perfil_id, papeis_id) values (1, 564);--.go
alter sequence papel_sequence restart with 565;--.go