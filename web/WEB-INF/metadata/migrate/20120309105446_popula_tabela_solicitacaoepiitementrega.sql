insert into solicitacaoepiitementrega 
select nextval('solicitacaoepiitementrega_sequence'), id, qtdentregue, dataentrega from solicitacaoepi_item where dataentrega is not null;--.go