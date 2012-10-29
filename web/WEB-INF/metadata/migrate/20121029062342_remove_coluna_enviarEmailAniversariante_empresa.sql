insert into gerenciadorcomunicacao(id, operacao, meiocomunicacao, enviarpara, destinatario, empresa_id, qtddiaslembrete, permitirresponderavaliacao)  
select nextval('gerenciadorcomunicacao_sequence'), 27, 2, 6, null, id, null, false from empresa where enviaremailaniversariante = true; --.go

alter table empresa drop column enviaremailaniversariante; --.go