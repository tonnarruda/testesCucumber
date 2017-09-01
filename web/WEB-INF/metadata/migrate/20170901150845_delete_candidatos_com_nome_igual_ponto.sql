delete from candidato_conhecimento where candidato_id in (select id from candidato where nome ='.'); --.go
delete from candidato_areainteresse where candidato_id in (select id from candidato where nome ='.'); --.go
delete from candidato_cargo where candidato_id in (select id from candidato where nome ='.'); --.go
delete from candidato where nome ='.'; --.go
