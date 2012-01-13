alter table solicitacao add column status character varying(1);--.go

UPDATE solicitacao SET status='A' WHERE liberada=true;--.go
UPDATE solicitacao SET status='R' WHERE liberada=false;--.go

alter table solicitacao drop column liberada;--.go