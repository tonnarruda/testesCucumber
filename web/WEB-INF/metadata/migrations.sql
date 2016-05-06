create table token(id bigint, hash character varying(32)); --.go
CREATE SEQUENCE token_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

insert into usuario(id, nome, login, senha, acessosistema, caixasmensagens) values(nextval('usuario_sequence'), 'FORTES PESSOAL', 'FORTESPESSOAL', 'QEZvcnRlc1Blc3NvYWw=', true, '');