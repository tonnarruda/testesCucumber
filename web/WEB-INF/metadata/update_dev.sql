update parametrosdosistema set appversao = '1.1.31.20';--.go

ALTER TABLE ComissaoMembro ADD COLUMN dataEnt date;--.go

alter table cargo alter column exibirmoduloexterno set default true;--.go