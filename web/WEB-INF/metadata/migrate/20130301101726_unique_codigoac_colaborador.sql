update colaborador set codigoac = null where codigoac = ''; --.go
ALTER TABLE colaborador ADD CONSTRAINT unique_codigoac_colaborador UNIQUE(codigoac,empresa_id); --.go