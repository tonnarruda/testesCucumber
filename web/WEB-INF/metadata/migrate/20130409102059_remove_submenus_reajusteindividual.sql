update papel set 
codigo = 'ROLE_MOV_SOLICITACAOREAJUSTE_COLABORADOR', 
url = '/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action', 
nome = 'Solicitação de Realinhamento para Colaborador' 
where id = 49;--.go

delete from perfil_papel where papeis_id in (567,568,569);--.go
delete from papel where id in (567,568,569);--.go

update papel set codigo = 'ROLE_DISSIDIO_COLABORADOR' where id = 565;--.go
update papel set codigo = 'ROLE_DISSIDIO_FAIXASALARIAL' where id = 566;--.go
update papel set codigo = 'ROLE_DISSIDIO_INDICE' where id = 570;--.go