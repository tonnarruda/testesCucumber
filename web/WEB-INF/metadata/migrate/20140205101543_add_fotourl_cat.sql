alter table cat add column fotoUrl character varying(200); --.go
alter table cat add column qtdDiasDebitados integer;--.go
alter table cat add column limitacaoFuncional boolean not null default false;--.go
alter table cat add column obsLimitacaoFuncional text;--.go