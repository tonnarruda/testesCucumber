INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (522, 'ROLE_INFORM_CANDIDATO', 'Informações do Candidato', '', 15, false, 357); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (523, 'ROLE_INFORM_CANDIDATO_CURRICULO', 'Visualizar Currículo', '', 1, false, 522); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (524, 'ROLE_INFORM_CANDIDATO_HISTORICO', 'Visualizar Histórico', '', 2, false, 522); --.go
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (525, 'ROLE_INFORM_CANDIDATO_COMPETENCIA', 'Visualizar Competência', '', 3, false, 522); --.go
alter sequence papel_sequence restart with 526;--.go
UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe=522 WHERE atualizaPapeisIdsAPartirDe is null;--.go

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 522 from perfil_papel where papeis_id in (2, 496);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 523 from perfil_papel where papeis_id in (2, 496);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 524 from perfil_papel where papeis_id in (2, 496);
insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 525 from perfil_papel where papeis_id in (2, 496);