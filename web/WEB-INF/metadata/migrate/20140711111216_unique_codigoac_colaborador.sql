update colaborador set codigoac = null where trim(codigoac) = ''; --.go

ALTER TABLE colaborador ADD CONSTRAINT no_blank_codigoac_colaborador CHECK(trim(codigoac) <> ''); --.go
ALTER TABLE colaborador ADD CONSTRAINT unique_codigoac_colaborador UNIQUE(codigoac,empresa_id); --.go