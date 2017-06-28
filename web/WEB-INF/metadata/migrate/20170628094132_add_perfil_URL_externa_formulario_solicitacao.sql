
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (705, 'ROLE_FORMULARIO_SOLICITACAO_EXTERNO', 'Formulário de Solicitação para o Sistema FortesRH', 'https://portaldocliente.fortestecnologia.com.br/portal_autentica_portal.php?location=/portal_solicitacao.php', 18, true, 37); --.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 705);--.go
alter sequence papel_sequence restart with 706; --.go