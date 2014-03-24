update papel set ordem = 3 where id = 470;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (619, 'ROLE_MOV_RESPOSTASPERIODOEXPERIENCIA', 'Respostas do Acompanhamento do Período de Experiência', '/avaliacao/avaliacaoExperiencia/respostasPeriodoExperienciaList.action', 4, true, 384);--.go
alter sequence papel_sequence restart with 620;--.go

insert into perfil_papel(perfil_id, papeis_id) values(1, 619);--.go