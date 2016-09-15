alter table parametrosdosistema add column autorizacaoGestorNaSolicitacaoPessoal boolean default false;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (683, 'ROLE_MOV_AUTOR_COLAB_SOL_PESSOAL', 'Autorizar Participação do Colaborador na Solicitação de Pessoal', '/captacao/candidatoSolicitacao/prepareAutorizarColabSolicitacaoPessoal.action', 2, true, 359);--.go
INSERT INTO perfil_papel(perfil_id, papeis_id) VALUES(1, 683);--.go
ALTER sequence papel_sequence restart WITH 683;--.go

update papel set ordem=3 where id=500; --.go
update papel set ordem=4 where id=510; --.go

alter table candidatosolicitacao add column statusAutorizacaoGestor character(1);--.go
alter table candidatosolicitacao add column dataAutorizacaoGestor date;--.go
alter table candidatosolicitacao add column obsAutorizacaoGestor text;--.go
alter table candidatosolicitacao add column usuarioSolicitanteAutorizacaoGestor_id bigint;--.go

ALTER TABLE ONLY candidatosolicitacao ADD CONSTRAINT candidatosolicitacao_usuario_fk FOREIGN KEY (usuarioSolicitanteAutorizacaoGestor_id) REFERENCES usuario(id); --.go
