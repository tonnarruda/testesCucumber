alter table empresa drop column nomeHomonimoEmpresa; --.go
alter table empresa drop column nomeHomonimo; --.go
alter table empresa add column verificaParentesco character(1) default 'N'; --.go