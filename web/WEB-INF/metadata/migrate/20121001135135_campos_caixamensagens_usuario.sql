insert into migrations (name) values ('20121001135135');--.go
alter table usuario add column caixasMensagens character varying(255);--.go
update usuario set caixasMensagens = '{"caixasDireita":["T","C","F","U"],"caixasEsquerda":["P","D","A","S"],"caixasMinimizadas":[]}';--.go
alter table usuario alter column caixasMensagens set not null; --.go