ALTER TABLE parametrosdosistema ADD COLUMN bancoconsistente boolean NOT NULL DEFAULT true;--.go
ALTER TABLE parametrosdosistema ADD COLUMN quantidadeconstraints integer DEFAULT 0;--.go
update papel set nome = 'Visualizar mensagem de atualização/inconsistência do sistema' where id = 562;--.go