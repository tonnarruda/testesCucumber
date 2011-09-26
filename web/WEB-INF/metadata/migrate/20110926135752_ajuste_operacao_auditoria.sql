update auditoria set operacao='Inserção' where operacao='save';--.go
update auditoria set operacao='Remoção' where operacao='remove';--.go
update auditoria set operacao='Atualização' where operacao='update';--.go