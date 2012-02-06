alter table turma add column liberada boolean default false;--.go

update turma set liberada = true where realizada = true;--.go 