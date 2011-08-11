
--Remover estas 4 linhas quando fechar versão.
delete from migrations where name ilike '%20110810092356%';--.go
delete from migrations where name ilike '%20110809135642%';--.go
delete from perfil_papel where papeis_id in (517,518);--.go
delete from papel where id in (517,518);--.go



INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (517,'RECEBE_MSG_PERIODOEXPERIENCIA', 'Receber Mensagens do Período de Acomp. de Experiência Apenas da Área Cujo Gestor é Responsável.', '#', 4, false, 382);--.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (518,'GERENCIA_MSG_PERIODOEXPERIENCIA', 'Receber Mensagens do Período de Acomp. de Experiência de Todas as Áreas Organizacionais.', '#', 5, false, 382);--.go
alter sequence papel_sequence restart with 519;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=517 WHERE atualizaPapeisIdsAPartirDe is null;--.go


