update parametrosdosistema set appversao = '1.1.39.30';--.go

alter table colaborador add column observacaodemissao text;--.go

update colaborador set observacaodemissao=observacao where motivodemissao_id is not null and observacao is not null;--.go