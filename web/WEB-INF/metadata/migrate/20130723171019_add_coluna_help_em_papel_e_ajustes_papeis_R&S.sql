alter table papel add column help character varying(500);--.go
update papel set nome= 'Ver todas', papelmae_id = 21, ordem = 1  where id =45;--.go
update papel set nome= 'Aprovar/Reprovar', ordem = 2, papelmae_id=21, help='Com essa opção o usuário verá, na listagem de solicitação de pessoal, apenas as solicitações criadas por ele e as solicitações que tiverem a área organizacional no qual seja responsável ou coresponsável. Isso só ocorre se a opção “Ver todas” não estiver selecionada.'  where id =56;--.go
update papel set nome= 'Visualizar pendências na caixa de mensagem', papelmae_id = 21, ordem = 3  where id = 496;--.go
update papel set ordem= 4 where id =556;--.go
update papel set ordem= 5, papelmae_id=21 where id =22;--.go
update papel set ordem= 1, papelmae_id=22 where id =544;--.go
update papel set papelmae_id = 2 where papelmae_id = 522;--.go
delete from perfil_papel  where papeis_id = 522;--go
delete from papel  where id = 522;--go