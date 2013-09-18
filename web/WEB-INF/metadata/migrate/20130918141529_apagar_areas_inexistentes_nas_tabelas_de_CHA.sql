delete from atitude_areaorganizacional where areaorganizacionals_id not in (select id from areaorganizacional);--.go
delete from conhecimento_areaorganizacional where areaorganizacionals_id not in (select id from areaorganizacional);--.go
delete from habilidade_areaorganizacional where areaorganizacionals_id not in (select id from areaorganizacional);--.go